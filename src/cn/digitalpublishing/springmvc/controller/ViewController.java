package cn.digitalpublishing.springmvc.controller;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.Param;
import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.com.daxtech.framework.util.StringUtil;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.ep.po.LComplicating;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.PNote;
import cn.digitalpublishing.ep.po.PPage;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.ep.po.PRecord;
import cn.digitalpublishing.springmvc.form.ViewForm;
import cn.digitalpublishing.util.io.FileUtil;
import cn.digitalpublishing.util.io.IOHandler;
import cn.digitalpublishing.util.io.MD5Util;
import cn.digitalpublishing.util.web.DateUtil;
import cn.digitalpublishing.util.web.IpUtil;
import cn.digitalpublishing.util.web.MathHelper;
import cn.digitalpublishing.util.web.PdfHelper;

import com.lowagie.text.pdf.BaseFont;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.zhima.server.model.ResultObject;
import com.zhima.server.util.restful.Converter;

@Controller
@RequestMapping("/pages/view")
public class ViewController extends BaseController {

	@RequestMapping(value = "/form/view")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewForm form) throws Exception {
		String forwardString = "view";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			//			form.getWebUrl()!=null&&!"".equals(form.getWebUrl())
			if (form.getWebUrl() != null && !"".equals(form.getWebUrl())) {
				//不是我们的资源，用IFrame
				String url = new String(Base64.decode(form.getWebUrl()));
				model.put("webUrl", url);
				model.put("available", null);
			} else {
				int falg = 0;
				CUser user = session.getAttribute("mainUser") == null ? null : (CUser) session.getAttribute("mainUser");
				PPublications p = this.pPublicationsService.getPublications(form.getId());
				model.put("available", p.getAvailable());

				//进行判断可读不可读
				if (p != null && (p.getAvailable() == null) || (p.getAvailable() != null && p.getAvailable() != 3)) {
					PPublications chapter = null;
					if (form.getNextPage() != null && form.getNextPage() > 0) {
						chapter = this.pPublicationsService.getChapter(p.getId(), form.getNextPage());
					}

					form.setType(p.getType());
					form.setPublicationsTitle(p.getTitle());
					//判断章节或图书是否免费或开源
					if ((chapter == null || (chapter != null && (chapter.getOa() == null || chapter.getOa() != 2) && (chapter.getFree() == null || chapter.getFree() != 2))) && (p.getOa() == null || p.getOa() != 2) && (p.getFree() == null || p.getFree() != 2)) {//免费或OA不需要走下面的流程
						String md5Str = "";
						Cookie[] cookies = request.getCookies();
						if (cookies != null && cookies.length > 0) {
							for (Cookie cookie : cookies) {
								if ("readCookie".equals(cookie.getName())) {
									md5Str = cookie.getValue();
									break;
								}
							}
						}
						if ("".equals(md5Str)) {
							md5Str = MD5Util.getEncryptedPwd(session.getId());
							Cookie c = new Cookie("readCookie", md5Str);
							c.setMaxAge(60 * 60 * 24 * 365);
							c.setPath(request.getContextPath());
							response.addCookie(c);
						}
						Boolean userCanRead = false;
						if (user != null) {//先检查当前的登录的用户是否有访问权限
							/*Map<String,Object> map2 = new HashMap<String,Object>();
							map2.put("pubId", form.getId());
							map2.put("status", 1);
							map2.put("userid", user.getId());
							map2.put("isTrail", "0");
							List<LLicense> list2 = this.pPublicationsService.getLicenseList(map2,"");
							if(list2!=null&&list2.size()>0){					
								LLicense license = list2.get(0);*/
							LLicense license = this.pPublicationsService.getVaildLicense(p, user, IpUtil.getIp(request));
							if (license != null) {
								userCanRead = true;
								form.setLicenseId(license.getId());
								if (license.getComplicating() != null && license.getComplicating() != 0) {//即有并发限制
									Map<String, Object> uCon = new HashMap<String, Object>();
									uCon.put("licenseId", license.getId());
									uCon.put("sessionId", session.getId());
									boolean b = this.pPublicationsService.isExistComplicating(uCon);
									boolean b2 = true;
									if (!b) {
										//根据Cookie查看是否存在
										Map<String, Object> uCon2 = new HashMap<String, Object>();
										uCon2.put("licenseId", license.getId());
										uCon2.put("macId", md5Str);
										uCon2.put("endTimeNull", null);
										//							uCon2.put("publicationId", form.getId());
										List<LComplicating> listComp = this.pPublicationsService.getComplicatingList(uCon2, " order by a.createOn ");
										if (listComp != null && listComp.size() > 0) {
											LComplicating lcomp = listComp.get(0);
											if (DateUtil.timeDiff(lcomp.getCreateOn(), new Date()) > 30) {//大于30分钟
												//先释放掉，相当于session过期
												Map<String, Object> con3 = new HashMap<String, Object>();
												con3.put("macId", md5Str);
												this.pPublicationsService.deleteComplicatingByCondition(con3);
											} else {
												b2 = false;
											}
										}
										if (b2) {
											//查看并发数量
											uCon = new HashMap<String, Object>();
											uCon.put("licenseId", license.getId());
											uCon.put("endTimeNull", null);
											int count = this.pPublicationsService.getComplicatingCount(uCon);
											if (count >= license.getComplicating()) {
												//访问失败写据访信息
												addLog(p, user, user.getInstitution() == null ? null : user.getInstitution().getId(), null, IpUtil.getIp(request), 2, 2, 0, 2);
												falg = 1;
												request.setAttribute("message", Lang.getLanguage("Controller.Publications.complicating.error", request.getSession().getAttribute("lang").toString()));//超出最大并发数!";);
												forwardString = "error";
											} else {
												//记录下新人
												LComplicating comp = new LComplicating();
												comp.setLicense(license);
												comp.setSessionId(session.getId());
												comp.setUser(license.getUser());
												comp.setMacAddr(md5Str);
												comp.setPubCode(license.getPublications().getCode());
												comp.setCreateOn(new Date());
												comp.setUpdateTime(comp.getCreateOn());//添加更新时间
												comp.setEndTime(null);//结束时间
												this.pPublicationsService.insertComplicating(comp);
												Map<String, Object> compMap = session.getAttribute("compMap") != null ? (Map<String, Object>) session.getAttribute("compMap") : new HashMap<String, Object>();
												compMap.put(license.getId(), license.getId());
												session.setAttribute("compMap", compMap);
											}
										}
									}
								}
							} else {
								falg = 2;//无有效License
							}
						}
						if (userCanRead == false || user == null) {
							/*Map<String, Object> condition = new HashMap<String,Object>();		
							condition.put("publicationId", form.getId());
							condition.put("ip", IpUtil.getLongIp(IpUtil.getIp(request)));
							condition.put("status", 1);
							List<LLicenseIp> list = this.pPublicationsService.getLicenseIpList(condition,"");
							if(list!=null&&list.size()>0){
								LLicense license = list.get(0).getLicense();*/
							Map<String, Object> pageCond = new HashMap<String, Object>();
							pageCond.put("sourceId", p.getId());
							pageCond.put("nextPage", form.getNextPage() == null || form.getNextPage() <= 0 ? 1 : form.getNextPage());
							PPage page = this.pPublicationsService.getPageByCondition(pageCond);
							LLicense license = null;
							if (page != null) {
								license = this.pPublicationsService.getVaildLicense(page, null, IpUtil.getIp(request));
							} else {
								license = this.pPublicationsService.getVaildLicense(p, null, IpUtil.getIp(request));
							}
							if (license != null) {
								form.setLicenseId(license.getId());
								if (license.getComplicating() != null && license.getComplicating() != 0) {//即有并发限制
									Map<String, Object> uCon = new HashMap<String, Object>();
									uCon.put("licenseId", license.getId());
									uCon.put("sessionId", session.getId());
									uCon.put("endTimeNull", null);
									boolean b = this.pPublicationsService.isExistComplicating(uCon);
									boolean b2 = true;
									if (!b) {
										//根据Cookie查看是否存在
										Map<String, Object> uCon2 = new HashMap<String, Object>();
										uCon2.put("licenseId", license.getId());
										uCon2.put("macId", md5Str);
										uCon2.put("endTimeNull", null);
										//							uCon2.put("publicationId", form.getId());
										List<LComplicating> listComp = this.pPublicationsService.getComplicatingList(uCon2, " order by a.createOn ");
										if (listComp != null && listComp.size() > 0) {
											LComplicating lcomp = listComp.get(0);
											if (DateUtil.timeDiff(lcomp.getCreateOn(), new Date()) > 30) {//大于30分钟
												//先释放掉，相当于session过期
												Map<String, Object> con3 = new HashMap<String, Object>();
												con3.put("macId", md5Str);
												this.pPublicationsService.deleteComplicatingByCondition(con3);
											} else {
												b2 = false;
											}
										}
										if (b2) {
											//查看并发数量
											uCon = new HashMap<String, Object>();
											uCon.put("licenseId", license.getId());
											uCon.put("endTimeNull", null);
											int count = this.pPublicationsService.getComplicatingCount(uCon);
											if (count >= license.getComplicating()) {
												addLog(p, user, user.getInstitution() == null ? null : user.getInstitution().getId(), null, IpUtil.getIp(request), 2, 2, 0, 2);
												falg = 1;
												request.setAttribute("message", Lang.getLanguage("Controller.Publications.complicating.error", request.getSession().getAttribute("lang").toString()));//超出最大并发数!";);
												forwardString = "alertError";
											} else {
												//记录下新人
												LComplicating comp = new LComplicating();
												comp.setLicense(license);
												comp.setSessionId(session.getId());
												comp.setUser(license.getUser());
												comp.setMacAddr(md5Str);
												comp.setPubCode(license.getPublications().getCode());
												comp.setCreateOn(new Date());
												comp.setUpdateTime(comp.getCreateOn());//添加更新时间
												comp.setEndTime(null);//结束时间
												this.pPublicationsService.insertComplicating(comp);
												Map<String, Object> compMap = session.getAttribute("compMap") != null ? (Map<String, Object>) session.getAttribute("compMap") : new HashMap<String, Object>();
												compMap.put(license.getId(), license.getId());
												session.setAttribute("compMap", compMap);
											}
										}
									}
								}
							} else {
								falg = 2;//无有效License
							}
						}
					}
				} else {
					falg = 3;//无出版物
					if (p.getAvailable() == 3) {
						falg = 4;
					}
				}
				if (falg == 0) {
					String id = form.getId();
					if (p.getType() == 3) {
						id = p.getPublications().getId();
					}
					form.setNextPage((form.getNextPage() == null || "".equals(form.getNextPage())) ? 1 : Integer.valueOf(form.getNextPage()));
					/**查找要显示的swf*/
					form.setUrl(request.getRequestURL().toString());
					if (p.getFileType() == null || p.getFileType() != 2) {
						Map<String, Object> condition = new HashMap<String, Object>();
						condition.put("sourceId", id);
						if (form.getCount() <= 0) {//如果pdf的总页数没有，则查询
							form.setCount(this.pPublicationsService.getPPageCount(condition));

							//暂时改为从dcc查询总页数
							//							String url = Param.getParam("system.config").get("pageCount").replace("-", ":");
							//							url +=id+".xml";
							//							Converter<Integer> converter = new Converter<Integer>();
							//							ResultObject<Integer> result= converter.xml2Object(url);
							//							if(result.getType()==1){
							//								form.setCount(result.getObj().get(0));
							//							}else{
							//								form.setCount(0);
							//							}
						}
						condition.put("nextPage", form.getNextPage());
						PPage page = this.pPublicationsService.getPageByCondition(condition);
						/**查找要显示的swf-end*/
						if (page != null) {
							//最大拷贝数量

							if (form.getReadCount() < 0) {
								String browsePrecent = null;
								if (p.getType() == 1) {//图书
									browsePrecent = page.getPublications().getPublisher().getBrowsePrecent();
								} else if (p.getType() == 4 || p.getType() == 7) {//文章
									browsePrecent = page.getPublications().getPublisher().getJournalBrowse();
								} else if (p.getType() == 3) {//章节
									browsePrecent = page.getPublications().getPublisher().getBrowsePrecent();
								}
								browsePrecent = browsePrecent == null || "".equals(browsePrecent) ? "0" : browsePrecent;
								if (browsePrecent.startsWith("P")) {
									browsePrecent = browsePrecent.substring(1);
									form.setReadCount((int) MathHelper.round(Double.valueOf(browsePrecent), 0));
								} else {
									Double a = MathHelper.mul(form.getCount(), (Double.valueOf(browsePrecent) / 100));
									form.setReadCount((int) MathHelper.round(a, 0));
								}
							}
							//最大下载数量						
							if (form.getDownloadCount() < 0) {
								String downloadPrecent = null;
								if (p.getType() == 1) {//图书
									downloadPrecent = page.getPublications().getPublisher().getDownloadPercent();
								} else if (p.getType() == 4 || p.getType() == 7) {//文章
									downloadPrecent = page.getPublications().getPublisher().getJournalDownload();
								} else if (p.getType() == 3) {//章节
									downloadPrecent = page.getPublications().getPublisher().getDownloadPercent();
								}
								downloadPrecent = downloadPrecent == null || "".equals(downloadPrecent) ? "0" : downloadPrecent;

								if (downloadPrecent.startsWith("P")) {
									downloadPrecent = downloadPrecent.substring(1);
									form.setDownloadCount((int) MathHelper.round(Double.valueOf(downloadPrecent), 0));
								} else {
									Double a = MathHelper.mul(form.getCount(), (Double.valueOf(downloadPrecent) / 100));
									form.setDownloadCount((int) MathHelper.round(a, 0));
								}

								if (downloadPrecent != null && downloadPrecent.startsWith("100")) {
									model.put("downloadPrecent", 100);
								}
							}
							//最大打印数量
							if (form.getPrintCount() < 0) {
								String printPrecent = null;
								if (p.getType() == 1) {//图书
									printPrecent = page.getPublications().getPublisher().getPrintPercent();
								} else if (p.getType() == 4 || p.getType() == 7) {//文章
									printPrecent = page.getPublications().getPublisher().getJournalPrint();
								} else if (p.getType() == 3) {//章节
									printPrecent = page.getPublications().getPublisher().getPrintPercent();
								}
								printPrecent = printPrecent == null || "".equals(printPrecent) ? "0" : printPrecent;
								if (printPrecent.startsWith("P")) {
									printPrecent = printPrecent.substring(1);
									form.setPrintCount((int) MathHelper.round(Double.valueOf(printPrecent), 0));
								} else {
									Double a = MathHelper.mul(form.getCount(), (Double.valueOf(printPrecent) / 100));
									form.setPrintCount((int) MathHelper.round(a, 0));
								}
							}
							if (user != null) {
								/**跟据sourceId查询标签*/
								Map<String, Object> reMap = new HashMap<String, Object>();
								reMap.put("sourceId", id);
								reMap.put("userId", user.getId());//用户ID
								List<PRecord> rList = this.pRecordService.getPRecordList(reMap, "");
								if (rList != null && rList.size() > 0) {
									form.setRecord(rList.get(0));
								}
								/**跟据sourceId查询标签-end*/
							}
						}
						Integer copyCount = session.getAttribute("copyCount_" + id) == null ? 0 : (Integer) session.getAttribute("copyCount_" + id);
						Integer downloadCount = session.getAttribute("downloadCount_" + id) == null ? 0 : (Integer) session.getAttribute("downloadCount_" + id);
						Integer printCount = session.getAttribute("printCount_" + id) == null ? 0 : (Integer) session.getAttribute("printCount_" + id);
						model.put("copyCount", copyCount);
						model.put("downloadCount", downloadCount);
						model.put("printCount", printCount);
						if (p.getType() == 3) {
							form.setPubId(p.getPublications().getId());
							form.setId(p.getPublications().getId());
						}
						request.setAttribute("licenseId", form.getLicenseId());
						request.setAttribute("publicationsId", id == null ? null : id);
						request.setAttribute("publicationsTitle", form.getPublicationsTitle());
					} else {//epub
							//String basePath=Param.getParam("pdf.directory.config").get("dir").replace("-", ":");
						model.put("epubPath", p.getEpubDir());
						model.put("pub", p);
						//forwardString="epubview";	
						forwardString = "epubReader/reader";
					}
					/*if(p.getJournalType()==2 && p.getType()==7){
					BInstitution bi = session.getAttribute("institution")==null?null:(BInstitution)session.getAttribute("institution");
					this.addLog1(p.getId(), user,  bi.getId(), IpUtil.getIp(request),request,1);//往access表中插一条记录
					}*/
					model.put("pubType", p.getType() == 3 ? 1 : p.getType());
					model.put("beatInterval", Param.getParam("system.config").get("beatInterval"));
				} else {
					/*if(p.getJournalType()==2 && p.getType()==7){
					BInstitution bi = session.getAttribute("institution")==null?null:(BInstitution)session.getAttribute("institution");
					this.addLog1(p.getPublisherId(), user,  bi.getId(), IpUtil.getIp(request),request,2);//往access表中插一条记录
					}*/
					forwardString = "error";
					if (falg == 1) {
						request.setAttribute("prompt", Lang.getLanguage("Global.Lable.Prompt", request.getSession().getAttribute("lang").toString()));
						request.setAttribute("message", Lang.getLanguage("Controller.Publications.complicating.error", request.getSession().getAttribute("lang").toString()));
					} else if (falg == 2) {
						request.setAttribute("prompt", Lang.getLanguage("Global.Lable.Prompt", request.getSession().getAttribute("lang").toString()));
						request.setAttribute("message", Lang.getLanguage("Controller.View.License.NotFind", request.getSession().getAttribute("lang").toString()));
					} else if (falg == 3) {
						request.setAttribute("prompt", Lang.getLanguage("Global.Lable.Prompt", request.getSession().getAttribute("lang").toString()));
						request.setAttribute("message", Lang.getLanguage("Controller.Publications.Prompt.Content.UnFind", request.getSession().getAttribute("lang").toString()));
					} else if (falg == 4) {
						request.setAttribute("prompt", Lang.getLanguage("Global.Lable.Prompt", request.getSession().getAttribute("lang").toString()));
						request.setAttribute("message", Lang.getLanguage("Controller.Publications.Prompt.Content.C", request.getSession().getAttribute("lang").toString()));
					}
				}
				// 添加批量下载限制
				this.setToken(request, response, session);
			}

			// FullText Access
			String publicationsId = request.getAttribute("publicationsId") == null ? null : (String) request.getAttribute("publicationsId");
			if (publicationsId != null) {
				String pagesId = request.getAttribute("pagesId") == null ? null : (String) request.getAttribute("pagesId");
				//查询License
				String licenseId = request.getParameter("licenseId") == null ? null : request.getParameter("licenseId");
				CUser user = null;
				if (request.getSession().getAttribute("mainUser") != null) {
					user = (CUser) request.getSession().getAttribute("mainUser");
				}
				BInstitution institution = null;
				if (request.getSession().getAttribute("institution") != null) {
					institution = (BInstitution) request.getSession().getAttribute("institution");
				} else {
					if (user != null) {
						institution = user.getInstitution() == null ? null : user.getInstitution();
					}
				}
				LAccess access = new LAccess();
				access.setInstitutionId(institution == null ? null : institution.getId());
				access.setAccess(1);//访问状态1-访问成功 2-访问拒绝
				access.setType(2);//操作类型1-访问摘要 2-访问内容 3-检索
				access.setCreateOn(new Date());
				access.setIp(IpUtil.getIp(request));
				access.setPlatform("CNPe");
				access.setYear(StringUtil.formatDate(access.getCreateOn(), "yyyy"));
				access.setMonth(StringUtil.formatDate(access.getCreateOn(), "MM"));
				access.setUserId(user == null ? null : user.getId());
				PPublications publications = new PPublications();
				publications.setId(publicationsId);
				access.setPublications(publications);
				if (licenseId != null && !"".equals(licenseId)) {
					LLicense license = new LLicense();
					license.setId(licenseId);
					access.setLicense(license);
				}
				access.setPageId(pagesId);
				Integer yue = Integer.valueOf(StringUtil.formatDate(new Date(), "MM"));
				switch (yue) {
				case 1: {
					access.setMonth1(access.getMonth1() == null ? 1 : (access.getMonth1() + 1));
					break;
				}
				case 2: {
					access.setMonth2(access.getMonth2() == null ? 1 : (access.getMonth2() + 1));
					break;
				}
				case 3: {
					access.setMonth3(access.getMonth3() == null ? 1 : (access.getMonth3() + 1));
					break;
				}
				case 4: {
					access.setMonth4(access.getMonth4() == null ? 1 : (access.getMonth4() + 1));
					break;
				}
				case 5: {
					access.setMonth5(access.getMonth5() == null ? 1 : (access.getMonth5() + 1));
					break;
				}
				case 6: {
					access.setMonth6(access.getMonth6() == null ? 1 : (access.getMonth6() + 1));
					break;
				}
				case 7: {
					access.setMonth7(access.getMonth7() == null ? 1 : (access.getMonth7() + 1));
					break;
				}
				case 8: {
					access.setMonth8(access.getMonth8() == null ? 1 : (access.getMonth8() + 1));
					break;
				}
				case 9: {
					access.setMonth9(access.getMonth9() == null ? 1 : (access.getMonth9() + 1));
					break;
				}
				case 10: {
					access.setMonth10(access.getMonth10() == null ? 1 : (access.getMonth10() + 1));
					break;
				}
				case 11: {
					access.setMonth11(access.getMonth11() == null ? 1 : (access.getMonth11() + 1));
					break;
				}
				case 12: {
					access.setMonth12(access.getMonth12() == null ? 1 : (access.getMonth12() + 1));
					break;
				}
				default: {
					access.setMonth1(access.getMonth1() == null ? 1 : (access.getMonth1() + 1));
					break;
				}
				}
				logAOPService.addLog(access);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 用于Ajax请求swf
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/viewAjax")
	public void viewAjax(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewForm form) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String md5Str = "";
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if ("readCookie".equals(cookie.getName())) {
						md5Str = cookie.getValue();
						break;
					}
				}
			}
			if ("".equals(md5Str)) {
				md5Str = MD5Util.getEncryptedPwd(session.getId());
				Cookie c = new Cookie("readCookie", md5Str);
				c.setMaxAge(60 * 60 * 24 * 365);
				c.setPath(request.getContextPath());
				response.addCookie(c);
			}
			CUser user = session.getAttribute("mainUser") == null ? null : (CUser) session.getAttribute("mainUser");
			int falg = 0;
			/*if(user==null){
				Map<String, Object> condition = new HashMap<String,Object>();
				condition.put("publicationId", form.getPubId());
				condition.put("ip", IpUtil.getLongIp(IpUtil.getIp(request)));
				condition.put("status", 1);
				List<LLicenseIp> list = this.pPublicationsService.getLicenseIpList(condition,"");
				if(list!=null&&list.size()>0){
					LLicense license = list.get(0).getLicense();
					if(license.getComplicating()!=null&&license.getComplicating()!=0){//即有并发限制
						Map<String,Object> uCon = new HashMap<String,Object>();
						uCon.put("licenseId", license.getId());
						uCon.put("sessionId", session.getId());
						boolean b = this.pPublicationsService.isExistComplicating(uCon);
						boolean b2 = true;
						if(!b){
							//根据Cookie查看是否存在
							Map<String,Object> uCon2 = new HashMap<String,Object>();
							uCon2.put("licenseId", license.getId());
							uCon2.put("macId", md5Str);
							List<LComplicating> listComp = this.pPublicationsService.getComplicatingList(uCon2," order by a.createOn ");
							if(listComp!=null&&listComp.size()>0){
								LComplicating lcomp = listComp.get(0);
								if(DateUtil.timeDiff(lcomp.getCreateOn(), new Date())>30){//大于30分钟
									//先释放掉，相当于session过期
									Map<String,Object> con3 = new HashMap<String,Object>();
									con3.put("macId", md5Str);
									this.pPublicationsService.deleteComplicatingByCondition(con3);
								}else{
									b2 = false;
								}
							}
							if(b2){
								//查看并发数量
								uCon = new HashMap<String,Object>();
								uCon.put("licenseId", license.getId());
								int count = this.pPublicationsService.getComplicatingCount(uCon);
								if(count>=license.getComplicating()){
									falg = 1;
									resultMap.put("error", Lang.getLanguage("Controller.Publications.complicating.error", request.getSession().getAttribute("lang").toString()));//超出最大并发数!";);
								}else{
									//记录下新人
									LComplicating comp = new LComplicating();
									comp.setLicense(license);
									comp.setSessionId(session.getId());
									comp.setUser(license.getUser());
								    comp.setMacAddr(md5Str);
									comp.setPubCode(license.getPublications().getCode());
									comp.setCreateOn(new Date());
									this.pPublicationsService.insertComplicating(comp);
								}
							}
						}
					}
				}else{
					falg=2;//无有效License
				}
			}else{
				Map<String,Object> map2 = new HashMap<String,Object>();
				map2.put("publicationId", form.getPubId());
				map2.put("status", 1);
				map2.put("userid", user.getId());
				map2.put("isTrail", "0");
				List<LLicense> list2 = this.pPublicationsService.getLicenseList(map2,"");
				if(list2!=null&&list2.size()>0){
					LLicense license = list2.get(0);
					if(license.getComplicating()!=null&&license.getComplicating()!=0){//即有并发限制
						Map<String,Object> uCon = new HashMap<String,Object>();
						uCon.put("licenseId", license.getId());
						uCon.put("sessionId", session.getId());
						boolean b = this.pPublicationsService.isExistComplicating(uCon);
						boolean b2 = true;
						if(!b){
							//根据Cookie查看是否存在
							Map<String,Object> uCon2 = new HashMap<String,Object>();
							uCon2.put("licenseId", license.getId());
							uCon2.put("macId", md5Str);
							List<LComplicating> listComp = this.pPublicationsService.getComplicatingList(uCon2," order by a.createOn ");
							if(listComp!=null&&listComp.size()>0){
								LComplicating lcomp = listComp.get(0);
								if(DateUtil.timeDiff(lcomp.getCreateOn(), new Date())>30){//大于30分钟
									//先释放掉，相当于session过期
									Map<String,Object> con3 = new HashMap<String,Object>();
									con3.put("macId", md5Str);
									this.pPublicationsService.deleteComplicatingByCondition(con3);
								}else{
									b2 = false;
								}
							}
							if(b2){
								//查看并发数量
								uCon = new HashMap<String,Object>();
								uCon.put("licenseId", license.getId());
								int count = this.pPublicationsService.getComplicatingCount(uCon);
								if(count>=license.getComplicating()){
									falg = 1;
									resultMap.put("error", Lang.getLanguage("Controller.Publications.complicating.error", request.getSession().getAttribute("lang").toString()));//超出最大并发数!";);
								}else{
									//记录下新人
									LComplicating comp = new LComplicating();
									comp.setLicense(license);
									comp.setSessionId(session.getId());
									comp.setUser(license.getUser());
								    comp.setMacAddr(md5Str);
									comp.setPubCode(license.getPublications().getCode());
									comp.setCreateOn(new Date());
									this.pPublicationsService.insertComplicating(comp);
								}
							}
						}
					}
				}else{
					falg=2;//无有效License
				}
			}*/
			if (falg == 0) {
				String id = form.getPubId();
				form.setNextPage((form.getPageNum() == null || "".equals(form.getPageNum())) ? 1 : Integer.valueOf(form.getPageNum()));
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("sourceId", id);
				condition.put("nextPage", form.getNextPage());
				PPage page = this.pPublicationsService.getPageByCondition(condition);
				/****是否能拷贝****/
				//最大拷贝数量
				if (form.getReadCount() < 0) {
					String browsePrecent = null;
					if (page.getPublications().getType() == 1) {//图书
						browsePrecent = page.getPublications().getPublisher().getBrowsePrecent();
					} else if (page.getPublications().getType() == 4 || page.getPublications().getType() == 7) {//文章
						browsePrecent = page.getPublications().getPublisher().getJournalBrowse();
					}
					browsePrecent = browsePrecent == null || "".equals(browsePrecent) ? "0" : browsePrecent;

					if (browsePrecent.startsWith("P")) {
						browsePrecent = browsePrecent.substring(1);
						form.setReadCount((int) MathHelper.round(Double.valueOf(browsePrecent), 0));
					} else {
						Double a = MathHelper.mul(form.getCount(), (Double.valueOf(browsePrecent) / 100));
						form.setReadCount((int) MathHelper.round(a, 0));
					}
				}
				//这里判断是否可以拷贝
				form.setIsCopy("false");

				if (session != null && session.getAttribute("copyMap") != null) {
					Map<String, Object> copyMap = (Map<String, Object>) session.getAttribute("copyMap");
					if (CollectionsUtil.exist(copyMap, (form.getNextPage().toString() + "_" + id))) {
						form.setIsCopy("true");
					}

				}
				resultMap.put("isCopy", form.getIsCopy());//是否拷贝 ，true：拷贝 ;false：不拷贝
				/*if("true".equalsIgnoreCase(form.getIsCopy())&&session!=null){
				Integer copyCount = session.getAttribute("copyCount")==null?0:(Integer)session.getAttribute("copyCount");
				if(copyCount>=form.getReadCount()){//如果够20页则禁止再继续拷贝
					form.setIsCopy("false");
				}else{
					Map<String,Object> copyMap = session.getAttribute("copyMap")==null?new HashMap<String,Object>():(Map<String,Object>)session.getAttribute("copyMap");
					//如果不存在则保存到session中，计数
					if(!CollectionsUtil.exist(copyMap, (form.getNextPage().toString()+"_"+id))||"".equals(copyMap.get((form.getNextPage().toString()+"_"+id)))){
						copyMap.put(form.getNextPage().toString()+"_"+id, id);
						session.setAttribute("copyMap", copyMap);
						session.setAttribute("copyCount", session.getAttribute("copyCount")==null?1:(Integer)session.getAttribute("copyCount")+1);
					}
				}
				}*/
				resultMap.put("copyCount", (session.getAttribute("copyCount_" + id) == null ? "0" : session.getAttribute("copyCount_" + id).toString()));//已经拷贝的页数
				resultMap.put("maxCopyCount", form.getReadCount());
				/****是否下载****/
				//最大下载数量
				if (form.getDownloadCount() < 0) {
					String downloadPrecent = null;
					if (page.getPublications().getType() == 1) {//图书
						downloadPrecent = page.getPublications().getPublisher().getDownloadPercent();
					} else if (page.getPublications().getType() == 4 || page.getPublications().getType() == 7) {//文章
						downloadPrecent = page.getPublications().getPublisher().getJournalDownload();
					}
					downloadPrecent = downloadPrecent == null || "".equals(downloadPrecent) ? "0" : downloadPrecent;

					if (downloadPrecent.startsWith("P")) {
						downloadPrecent = downloadPrecent.substring(1);
						form.setDownloadCount((int) MathHelper.round(Double.valueOf(downloadPrecent), 0));
					} else {
						Double a = MathHelper.mul(form.getCount(), (Double.valueOf(downloadPrecent) / 100));
						form.setDownloadCount((int) MathHelper.round(a, 0));
					}
				}
				//这里判断是否可以下载
				form.setIsDownload("false");
				Integer downloadCount = session.getAttribute("downloadCount_" + id) == null ? 0 : Integer.valueOf(session.getAttribute("downloadCount_" + id).toString());
				if (session != null) {
					if (downloadCount < form.getDownloadCount()) {
						form.setIsDownload("true");
					} else if (session.getAttribute("downloadMap") != null) {
						Map<String, Object> downloadMap = (Map<String, Object>) session.getAttribute("downloadMap");
						if (CollectionsUtil.exist(downloadMap, (form.getNextPage().toString() + "_" + id))) {
							form.setIsDownload("true");
						}
					}
				}
				resultMap.put("isDownload", form.getIsDownload());//是否可下载 ，true：是 ;false：否
				resultMap.put("downloadCount", downloadCount);//已经下载的页数
				resultMap.put("maxDownloadCount", form.getDownloadCount());//最大下载数量
				/****是否打印****/
				//最大打印数量
				if (form.getPrintCount() < 0) {
					String printPrecent = null;
					if (page.getPublications().getType() == 1) {//图书
						printPrecent = page.getPublications().getPublisher().getPrintPercent();
					} else if (page.getPublications().getType() == 4 || page.getPublications().getType() == 7) {//文章
						printPrecent = page.getPublications().getPublisher().getJournalPrint();
					}
					printPrecent = printPrecent == null || "".equals(printPrecent) ? "0" : printPrecent;

					if (printPrecent.startsWith("P")) {
						printPrecent = printPrecent.substring(1);
						form.setPrintCount((int) MathHelper.round(Double.valueOf(printPrecent), 0));
					} else {
						Double a = MathHelper.mul(form.getCount(), (Double.valueOf(printPrecent) / 100));
						form.setPrintCount((int) MathHelper.round(a, 0));
					}

				}
				//这里判断是否可以打印
				form.setIsPrint("false");
				Integer printCount = session.getAttribute("printCount_" + id) == null ? 0 : Integer.valueOf(session.getAttribute("printCount_" + id).toString());
				if (session != null) {
					if (printCount < form.getPrintCount()) {
						form.setIsPrint("true");
					} else if (session.getAttribute("printMap") != null) {
						Map<String, Object> printMap = (Map<String, Object>) session.getAttribute("printMap");
						if (CollectionsUtil.exist(printMap, (form.getNextPage().toString() + "_" + id))) {
							form.setIsPrint("true");
						}
					}
				}
				resultMap.put("isPrint", form.getIsPrint());//是否打印过 ，true：是 ;false：否
				resultMap.put("printCount_" + id, printCount);//已经打印的页数
				session.setAttribute("maxPrintCount＿" + id, form.getPrintCount());
				resultMap.put("maxPrintCount", form.getPrintCount());//最大打印数量
				if (page != null) {
					if (user != null) {
						/**查询笔记列表-start**/
						Map<String, Object> noteMap1 = new HashMap<String, Object>();
						noteMap1.put("userId", user.getId());//用户ID....正式用的时候这里肯定要要根据userId来看是否有笔记
						noteMap1.put("publicationsId", id == null ? null : id);
						List<PNote> noteList = this.pNoteService.getNoteList(noteMap1, " order by b.number ");
						/**查询笔记列表-end**/

						/**根据PageId查询笔记*/
						Map<String, Object> noteMap = new HashMap<String, Object>();
						noteMap.put("pageId", page.getId());
						noteMap.put("userId", user.getId());//用户ID....正式用的时候这里肯定要要根据userId来看是否有笔记
						List<PNote> nList = this.pNoteService.getNoteList(noteMap, "");
						PNote note = new PNote();
						if (nList != null && nList.size() > 0) {
							note = nList.get(0);
						}
						/**根据PageId查询笔记-end*/

						//对查询的数据进行封装
						resultMap.put("noteList", noteList);
						resultMap.put("noteId", note.getId());//笔记Id
						resultMap.put("noteContent", note.getNoteContent());//笔记内容

					}
				}
				request.setAttribute("pagesId", page != null ? page.getId() : null);
				request.setAttribute("licenseId", form.getLicenseId());
				request.setAttribute("publicationsId", form.getPubId() == null ? null : form.getPubId());
			} else {
				/*if(falg==1){
					resultMap.put("error", Lang.getLanguage("Controller.Publications.complicating.error",request.getSession().getAttribute("lang").toString()));					
				}else if(falg==2){
					resultMap.put("error", Lang.getLanguage("Controller.View.License.NotFind",request.getSession().getAttribute("lang").toString()));					
				}else */if (falg == 3) {
					resultMap.put("error", Lang.getLanguage("Controller.Publications.Prompt.Content.UnFind", request.getSession().getAttribute("lang").toString()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", (e instanceof CcsException) ? ((CcsException) e).getMessage() : e.getMessage());
		}
		try {
			JSONArray json = new JSONArray();
			json.add(resultMap);
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			throw e;
			//			request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
	}

	@RequestMapping(value = "/form/addLable")
	public void addLable(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewForm form) throws Exception {
		String result = "error:" + Lang.getLanguage("PRecord.info.add.error", request.getSession().getAttribute("lang").toString());
		try {
			String id = form.getSourceId();
			Integer pageNum = (form.getPageNum() == null || "".equals(form.getPageNum())) ? 1 : Integer.valueOf(form.getPageNum());
			CUser user = session.getAttribute("mainUser") == null ? null : (CUser) session.getAttribute("mainUser");
			if (user != null) {
				//由于标签只有一个，先清空sourceId..admin 应该是通过session查询出 userId
				this.pRecordService.deleteBySourceId(form.getSourceId(), user.getId());
				PRecord record = new PRecord();
				//UserId、UserName都是从session中获取 ，暂时先写成固定值
				record.setUserId(user.getId());
				record.setUserName(user.getName());

				Map<String, Object> pageCon = new HashMap<String, Object>();
				pageCon.put("sourceId", id);
				pageCon.put("nextPage", pageNum);
				PPage page = this.pPublicationsService.getPageByCondition(pageCon);
				record.setPages(page);

				PPublications pcontent = new PPublications();
				pcontent.setId(id);
				record.setPublications(pcontent);

				record.setCreateDate(new Date());
				this.pRecordService.addRecord(record);
				result = "success:" + Lang.getLanguage("PRecord.info.add.success", request.getSession().getAttribute("lang").toString());//"标签添加成功！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "error:" + ((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
		}
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			throw e;//(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
	}

	@RequestMapping(value = "/form/addNote")
	public void addNote(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewForm form) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		//		String result = Lang.getLanguage("Note.info.add.error");//"error";
		try {
			String id = form.getSourceId();
			CUser user = session.getAttribute("mainUser") == null ? null : (CUser) session.getAttribute("mainUser");
			if (user != null) {
				//判断字段长度
				if (form.getNoteContent().length() > 1000) {
					//内容长度太长
					resultMap.put("error", Lang.getLanguage("Pages.view.Prompt.NoteLength", request.getSession().getAttribute("lang").toString()));
				} else {
					PNote note = new PNote();
					//UserId、UserName都是从session中获取 ，暂时先写成固定值
					note.setUserId(user.getId());
					note.setUserName(user.getName());
					Map<String, Object> pageCon = new HashMap<String, Object>();
					pageCon.put("sourceId", id);
					pageCon.put("nextPage", (form.getPageNum() == null || "".equals(form.getPageNum())) ? 1 : Integer.valueOf(form.getPageNum()));
					PPage page = this.pPublicationsService.getPageByCondition(pageCon);
					note.setPages(page);
					note.setNoteContent(form.getNoteContent());
					PPublications pcontent = new PPublications();
					pcontent.setId(id);
					note.setPublications(pcontent);
					note.setCreateDate(new Date());
					if (form.getId() == null || form.getId().equals("")) {
						this.pNoteService.addNote(note);
					} else {
						note.setId(form.getId());
						this.pNoteService.updateNote(note);
					}
					resultMap.put("success", Lang.getLanguage("Note.info.add.success", request.getSession().getAttribute("lang").toString()));
					resultMap.put("noteId", note.getId());
				}
				//			result = Lang.getLanguage("Note.info.add.success");
			}
		} catch (Exception e) {
			resultMap.put("error", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
		}
		try {
			JSONArray json = new JSONArray();
			json.add(resultMap);
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			throw e;
			//			request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
	}

	/**
	 * 翻页
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/page")
	public void page(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewForm form) throws Exception {
		try {
			String basePath = Param.getParam("pdf.directory.config").get("dir").replace("-", ":");
			String path = "";
			//如果产品Id为空，则直接跳转到缺省页
			String pubId = form.getPubId();
			if (pubId == null || "".equals(pubId)) {//
				path = basePath;
				path += Param.getParam("pdf.directory.config").get("root");
				path += Param.getParam("pdf.directory.config").get("default");
				path += "/default.swf";
			} else {
				//根据产品Id和pageMun查询Page信息
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("sourceId", pubId);
				condition.put("nextPage", Integer.valueOf((form.getPageNum() == null || "".equals(form.getPageNum())) ? "1" : form.getPageNum()));
				PPage page = this.pPublicationsService.getPageByCondition(condition);
				if (page == null) {
					path = basePath;
					path += Param.getParam("pdf.directory.config").get("root");
					path += Param.getParam("pdf.directory.config").get("default");
					path += "/default.swf";
				} else {
					//若请求的页面未获得license则返回一个提示页面
					CUser user = session.getAttribute("mainUser") == null ? null : (CUser) session.getAttribute("mainUser");
					LLicense license = this.pPublicationsService.getVaildLicense(page, user, IpUtil.getIp(request));
					//由于页码改成字符串了，无法判断页码是否在某章节范围中，阅读时会有问题，暂注释相关代码（1）
					//PPublications chapter=this.pPublicationsService.getChapter(pubId, Integer.valueOf((form.getPageNum()==null || "".equals(form.getPageNum()) || !Regex.isMatch(form.getPageNum().trim(), "\\d+"))?"1":form.getPageNum()));
					//文章是否是免费或开源
					Boolean isArticleOF = (page.getPublications().getType() == 1 || page.getPublications().getType() == 4) && ((page.getPublications().getOa() != null && page.getPublications().getOa() == 2) || (page.getPublications().getFree() != null && page.getPublications().getFree() == 2));
					//由于页码改成字符串了，无法判断页码是否在某章节范围中，阅读时会有问题，暂注释相关代码（2）
					//					if(license!=null || isArticleOF || (chapter!=null && (chapter.getOa()==2 || chapter.getFree()==2))){	
					if (license != null || isArticleOF) {
						if (page.getSwf() == null || !FileUtil.isExist(basePath + page.getSwf())) {
							String url = Param.getParam("system.config").get("swfPath").replace("-", ":");
							String id = page.getId();
							url += id + ".xml";
							//System.out.println("########### xml path is here2:" + url);
							Converter<String> converter = new Converter<String>();
							ResultObject<String> result = converter.xml2Object(url);
							if (result.getType() == 1) {
								path = basePath + result.getObj().get(0);
							} else {
								//加载一个默认swf
								path = basePath;
								path += Param.getParam("pdf.directory.config").get("root");
								path += Param.getParam("pdf.directory.config").get("default");
								path += "/default.swf";
							}
							File file = new File(path);
							int m = 0;
							while (!file.exists()) {
								Thread thread = Thread.currentThread();
								thread.sleep(1000);
								if (m >= 30) {
									break;
								} else {
									m++;
								}
							}
							if (!file.exists()) {
								path = basePath;
								path += Param.getParam("pdf.directory.config").get("root");
								path += Param.getParam("pdf.directory.config").get("default");
								path += "/default.swf";
							}
						} else {
							path = basePath + page.getSwf();
						}

					} else {
						path = basePath;
						path += Param.getParam("pdf.directory.config").get("root");
						path += Param.getParam("pdf.directory.config").get("default");
						path += "/noLicense.swf";
					}
				}
			}
			System.out.println("######################swfpath is here ####" + path);
			InputStream in = new FileInputStream(path);
			int i;
			response.setContentType("application/x-shockwave-flash");
			OutputStream out = response.getOutputStream();
			while ((i = in.read()) != -1) {
				out.write(i);
			}
			out.close();
		} catch (Exception e) {
			throw e;
			//e.printStackTrace();
			//			request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
	}

	@RequestMapping(value = "/form/search")
	public void search(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewForm form) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String id = form.getId();
			if (form.getArticleId() != null && !"".equals(form.getArticleId()) && !"null".equalsIgnoreCase(form.getArticleId())) {
				id = form.getArticleId();
			}
			Integer curPage = form.getCurpage() < 0 ? 0 : form.getCurpage();
			Integer pageSize = form.getPageCount() <= 0 ? 10 : form.getPageCount();
			map = this.pagesIndexService.selectPagesByFullText(id, form.getValue(), curPage - 1, pageSize);
		} catch (Exception e) {
			throw e;
			//e.printStackTrace();
			//			request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
		try {
			JSONArray json = new JSONArray();
			json.add(map);
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			throw e;
			//			request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
	}

	public void addLog(String pubid, CUser user, String institutionId, String ip, HttpServletRequest request) throws Exception {

		String Agent = request.getHeader("User-Agent");

		LAccess access = new LAccess();

		access.setAccess(1);//访问状态1-访问成功 2-访问拒绝
		access.setType(4);//操作类型1-访问摘要 2-访问内容 3-检索
		access.setCreateOn(new Date());
		access.setIp(ip);
		access.setPlatform("CNPe");
		access.setClientInfo(Agent);
		access.setYear(StringUtil.formatDate(access.getCreateOn(), "yyyy"));
		access.setMonth(StringUtil.formatDate(access.getCreateOn(), "MM"));
		if ("01".equals(access.getMonth()))
			access.setMonth1(1);
		if ("02".equals(access.getMonth()))
			access.setMonth2(1);
		if ("03".equals(access.getMonth()))
			access.setMonth3(1);
		if ("04".equals(access.getMonth()))
			access.setMonth4(1);
		if ("05".equals(access.getMonth()))
			access.setMonth5(1);
		if ("06".equals(access.getMonth()))
			access.setMonth6(1);
		if ("07".equals(access.getMonth()))
			access.setMonth7(1);
		if ("08".equals(access.getMonth()))
			access.setMonth8(1);
		if ("09".equals(access.getMonth()))
			access.setMonth9(1);
		if ("10".equals(access.getMonth()))
			access.setMonth10(1);
		if ("11".equals(access.getMonth()))
			access.setMonth11(1);
		if ("12".equals(access.getMonth()))
			access.setMonth12(1);
		PPublications publications = new PPublications();
		publications.setId(pubid);
		access.setPublications(publications);

		access.setUserId(user != null ? user.getId() : null);
		access.setInstitutionId(institutionId);
		this.logAOPService.addLog(access);
		//		if(institutionId==null && user ==null){
		//			//机构IP范围外未登录
		//			this.logAOPService.addLog(access);	
		//		}else{
		//			if(institutionId!=null){
		//				if(user!=null){
		//					access.setUserId(user.getId());
		//				}
		//				access.setInstitutionId(institutionId);
		//				this.logAOPService.addLog(access);	
		//				if(user!=null && user.getInstitution()!=null && !institutionId.equals(user.getInstitution().getId())){
		//					access.setUserId(user.getId());
		//					access.setInstitutionId(user.getInstitution().getId());	
		//					this.logAOPService.addLog(access);	
		//				}
		//			}
		//		}
	}

	public void addLog(PPublications pub, CUser user, String institutionId, String keyword, String ip, int t, int a, int fp, int d) throws Exception {

		LAccess access = new LAccess();
		access.setActivity(keyword);
		access.setAccess(a);// 访问状态1-访问成功 2-访问拒绝
		access.setType(t);// 操作类型1-访问摘要 2-访问内容 3-检索
		if (d != 0) {
			access.setRefusedVisitType(d);
		}//1-没有License,2-超出并发数
		access.setCreateOn(new Date());
		access.setIp(ip);
		access.setPlatform("CNPe");
		access.setYear(StringUtil.formatDate(access.getCreateOn(), "yyyy"));
		access.setMonth(StringUtil.formatDate(access.getCreateOn(), "MM"));
		if ("01".equals(access.getMonth()))
			access.setMonth1(1);
		if ("02".equals(access.getMonth()))
			access.setMonth2(1);
		if ("03".equals(access.getMonth()))
			access.setMonth3(1);
		if ("04".equals(access.getMonth()))
			access.setMonth4(1);
		if ("05".equals(access.getMonth()))
			access.setMonth5(1);
		if ("06".equals(access.getMonth()))
			access.setMonth6(1);
		if ("07".equals(access.getMonth()))
			access.setMonth7(1);
		if ("08".equals(access.getMonth()))
			access.setMonth8(1);
		if ("09".equals(access.getMonth()))
			access.setMonth9(1);
		if ("10".equals(access.getMonth()))
			access.setMonth10(1);
		if ("11".equals(access.getMonth()))
			access.setMonth11(1);
		if ("12".equals(access.getMonth()))
			access.setMonth12(1);
		PPublications publications = new PPublications();
		publications.setId(pub.getId());
		access.setPublications(publications);
		if (fp != 0) {
			access.setSearchType(fp);
		}

		if (institutionId == null && user == null) {
			// 机构IP范围外未登录
			this.logAOPService.addLog(access);
		} else {
			if (institutionId != null) {
				if (user != null) {
					access.setUserId(user.getId());
				}
				access.setInstitutionId(institutionId);
				this.logAOPService.addLog(access);
				if (user != null && user.getInstitution() != null && !institutionId.equals(user.getInstitution().getId())) {
					access.setUserId(user.getId());
					access.setInstitutionId(user.getInstitution().getId());
					this.logAOPService.addLog(access);
				}
			}
		}
	}

	/**
	 * 下载PDF单页
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/download")
	public void download(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewForm form) throws Exception {

		Map<String, String> resultMap = new HashMap<String, String>();
		String targetFile = "";
		String ins_Id = null;
		try {
			CUser user = session.getAttribute("mainUser") == null ? null : (CUser) session.getAttribute("mainUser");

			String isFullDownload = request.getParameter("isFull");//是否整篇下载
			if (isFullDownload == null || "".equals(isFullDownload) || "false".equals(isFullDownload)) {
				// 判断token标识是否正确
				if (!this.judgeSessionAndCookie(request, response, session)) {
					response.setContentType("text/html;charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.print("Access denied!");
					out.flush();
					out.close();
					return;
				}
				Map<String, Object> con = new HashMap<String, Object>();
				con.put("id", form.getPubId());
				List<PPublications> list = this.pPublicationsService.getPubSimplePageList(con, "", 1, 0);
				if (list != null && list.size() > 0) {
					PPublications pub = list.get(0);
					LLicense license = null;
					if (pub.getFree() != 2) {
						//查询用户是否已有授权，机构管理员在IP范围外可以下载，其他用户不可以
						license = this.pPublicationsService.getVaildLicense(pub, user != null && user.getLevel() == 2 ? user : null, IpUtil.getIp(request));
					}
					if (license != null) {
						Map<String, Object> condition = new HashMap<String, Object>();
						condition.put("sourceId", form.getPubId());
						condition.put("nextPage", Integer.valueOf((form.getPageNum() == null || "".equals(form.getPageNum())) ? "1" : form.getPageNum()));
						PPage page = this.pPublicationsService.getPageByCondition(condition);

						//如果swf地址有误,则失败
						if (page.getSwf() == null || "".equalsIgnoreCase(page.getSwf())) {
							resultMap.put("error", Lang.getLanguage("Controller.view.download.no", session.getAttribute("lang").toString()));
						} else {
							//获取目标PDF
							targetFile = this.waterPath(page.getSwf(), user, IpUtil.getLongIp(IpUtil.getIp(request)));
							if (!FileUtil.isExist(targetFile)) {
								//不存在生成水印PDF
								String pdfpath = page.getSwf().replace(Param.getParam("pdf.directory.config").get("swf") + "/", Param.getParam("pdf.directory.config").get("pdf") + "/");
								pdfpath = Param.getParam("pdf.directory.config").get("dir").replace("-", ":") + pdfpath.replace(".swf", ".pdf");
								String name = IpUtil.getIp(request);
								BInstitution bi = session.getAttribute("institution") == null ? null : (BInstitution) session.getAttribute("institution");
								if (bi == null && user != null) {
									name = user.getName() + " " + name;
								} else if (bi != null) {
									ins_Id = bi.getId();
									name = bi.getName() + " " + name;
								}
								PdfHelper.textMark(name, pdfpath, targetFile, Color.GRAY, "STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
								if (!FileUtil.isExist(targetFile)) {
									resultMap.put("error", Lang.getLanguage("Controller.view.download.no", session.getAttribute("lang").toString()));
								}
							}
						}
						// 下载本地文件
						String fileName = page.getSwf().substring(page.getSwf().lastIndexOf("/") + 1, page.getSwf().lastIndexOf(".swf"));
						fileName += ".pdf"; // 文件的默认保存名	
						IOHandler.download(targetFile, fileName, request, response);
						this.addLog(form.getPubId(), user, ins_Id, IpUtil.getIp(request), request);
					} else {
						//没有权限
					}
				}

			} else {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("id", form.getPubId());
				List<PPublications> list = this.pPublicationsService.getPubSimplePageList(condition, "", 1, 0);
				if (list != null && list.size() > 0) {
					PPublications pub = list.get(0);
					if (pub.getType() == 4 || pub.getType() == 7) {
						//判断出版社期刊下载设置是否为100%
						if (pub.getPublisher().getJournalDownload() != null && pub.getPublisher().getJournalDownload().startsWith("100")) {
							LLicense license = null;
							if (pub.getOa() != 2 && pub.getFree() != 2) {
								//查询用户是否已有授权，机构管理员在IP范围外可以下载，其他用户不可以
								license = this.pPublicationsService.getVaildLicense(pub, user != null && user.getLevel() == 2 ? user : null, IpUtil.getIp(request));
							}
							if (pub.getOa() == 2 || pub.getFree() == 2 || license != null) {
								if (pub.getPdf() != null && !"".equals(pub.getPdf())) {
									String rootPath = Param.getParam("pdf.directory.config").get("dir").replace("-", ":");
									targetFile = rootPath + pub.getPdf();
									if ((pub.getFileType() == null || pub.getFileType() != 2) && pub.getPdf().toLowerCase().endsWith(".pdf")) {//是pdf文件
										String waterPdf = targetFile.replaceAll("(?i)\\.pdf$", "_water.pdf");
										if (!FileUtil.isExist(waterPdf)) {
											//不存在生成水印PDF																		
											String name = IpUtil.getIp(request);
											BInstitution bi = session.getAttribute("institution") == null ? null : (BInstitution) session.getAttribute("institution");
											if (bi == null && user != null) {
												name = user.getName() + " " + name;
											} else if (bi != null) {
												ins_Id = bi.getId();
												name = bi.getName() + " " + name;
											}
											PdfHelper.textMark(name, targetFile, waterPdf, Color.GRAY, "STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
											if (!FileUtil.isExist(waterPdf)) {
												resultMap.put("error", Lang.getLanguage("Controller.view.download.no", session.getAttribute("lang").toString()));
											}
										}
										String fileName = waterPdf.substring(waterPdf.lastIndexOf("/") + 1, waterPdf.lastIndexOf("_water.pdf"));
										fileName += ".pdf"; // 文件的默认保存名	
										IOHandler.download(waterPdf, fileName, request, response);
										this.addLog(form.getPubId(), user, ins_Id, IpUtil.getIp(request), request);
									} else {
										//不是PDF，没法生成水印，怎么处理？
									}
								} else {
									//没有文件路径
								}
							} else {
								//没有权限下载
							}
						} else {
							//不允许整篇下载
						}
					} else {
						//不是期刊，不能下载全文
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", (e instanceof CcsException) ? ((CcsException) e).getMessage() : e.getMessage());
		}
	}

	/**
	 * 生成带水印PDF地址
	 * @param page
	 * @return
	 */
	public String waterPath(String swf, CUser user, long lip) {
		String path = "";
		try {
			String dir = Param.getParam("pdf.directory.config").get("dir").replace("-", ":");
			//将swf
			String waterPath = swf.substring(0, swf.lastIndexOf("/"));
			waterPath = waterPath.replace(Param.getParam("pdf.directory.config").get("swf"), Param.getParam("pdf.directory.config").get("water"));
			//				waterPath += "/"+user.getInstitution().getId();
			waterPath += "/" + lip;
			String water = swf.substring(swf.lastIndexOf("/"), swf.lastIndexOf(".swf"));
			water += "_water.pdf";
			if (!FileUtil.isExist((dir + waterPath))) {
				FileUtil.newFolder((dir + waterPath));
			}
			path = dir + waterPath + water;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	@RequestMapping(value = "/form/deleteNote")
	public void deleteNote(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewForm form) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		//			String result = Lang.getLanguage("Note.info.add.error");//"error";
		try {
			CUser user = session.getAttribute("mainUser") == null ? null : (CUser) session.getAttribute("mainUser");
			if (user != null) {
				if (form.getId() == null || form.getId().equals("")) {
					resultMap.put("msg", "error");
					resultMap.put("info", Lang.getLanguage("Note.info.delete.error", request.getSession().getAttribute("lang").toString()));
				} else {
					this.pNoteService.deleteNote(form.getId());
					resultMap.put("msg", "success");
					resultMap.put("info", Lang.getLanguage("Note.info.delete.success", request.getSession().getAttribute("lang").toString()));
				}
				//				result = Lang.getLanguage("Note.info.add.success");
			}
		} catch (Exception e) {
			resultMap.put("msg", "error");
			resultMap.put("info", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
		}
		try {
			JSONArray json = new JSONArray();
			json.add(resultMap);
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			throw e;
			//				request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
	}

	/**
	 * 在线阅读 章节--JQuery异步加载
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/tocList")
	public ModelAndView recentlyRead(HttpServletRequest request, HttpServletResponse response, ViewForm form) throws Exception {
		String forwardString = "viewToc";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			form.setUrl(request.getRequestURL().toString());
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("parentid", form.getId());
			condition.put("status", null);//不限制是否上架
			//				condition.put("type", 3);
			List<PPublications> list = this.pPublicationsService.getTocList(condition, " order by a.journalOrder,a.order,a.startPage ");
			if (list != null && list.size() > 0) {//显示章节层级关系
				String lastCode = "";
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getTreecode() != null && !"".equals(list.get(i).getTreecode())) {
						String tcode = list.get(i).getTreecode().substring(0, 3);
						if (tcode.equals(lastCode)) {
							list.get(i).setFullText("hierarchy_li2");//临时存放样式名字用								
						} else {
							list.get(i).setFullText("hierarchy_li1");//临时存放样式名字用
						}
						Integer num = 20 * (list.get(i).getTreecode().length() / 3 - 1);
						list.get(i).setBrowsePrecent(num.toString());//临时存放缩进度用
						lastCode = tcode;
					} else {
						list.get(i).setFullText("hierarchy_li1");//临时存放样式名字用
						Integer num = 20 * (3 / 3 - 1);
						list.get(i).setBrowsePrecent(num.toString());//临时存放缩进度用
					}
				}
			}
			model.put("tocList", (list == null || list.size() <= 0) ? null : list);
			model.put("publicationsId", form.getId());
			if (list != null && list.size() > 0) {
				model.put("pubFileType", list.get(0).getPublications().getFileType());
			}
		} catch (Exception e) {
			e.printStackTrace();
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 快捷工具
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/quickTools")
	public ModelAndView quickTools(HttpServletRequest request, HttpServletResponse response, ViewForm form) throws Exception {
		String forwordString = "viewTool";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			//如果产品Id为空，则直接跳转到缺省页
			String pubId = form.getPubId();
			if (pubId == null || "".equals(pubId)) {//
				//无产品信息
				form = null;
			} else {
				//根据产品Id和pageMun查询Page信息
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("sourceId", pubId);
				condition.put("nextPage", Integer.valueOf((form.getPageNum() == null || "".equals(form.getPageNum())) ? "1" : form.getPageNum()));
				PPage page = this.pPublicationsService.getPageByCondition(condition);
				if (page == null) {
					//无页信息
					form = null;
				} else {
					if (user != null) {
						/**跟据sourceId查询标签*/
						Map<String, Object> reMap = new HashMap<String, Object>();
						reMap.put("sourceId", pubId);
						reMap.put("userId", user.getId());//用户ID
						List<PRecord> rList = this.pRecordService.getPRecordList(reMap, "");
						if (rList != null && rList.size() > 0) {
							form.setRecord(rList.get(0));
						}
						/**跟据sourceId查询标签-end*/
						/**查询笔记列表-start**/
						Map<String, Object> noteMap1 = new HashMap<String, Object>();
						noteMap1.put("userId", user.getId());//用户ID....正式用的时候这里肯定要要根据userId来看是否有笔记
						noteMap1.put("publicationsId", pubId);
						List<PNote> noteList = this.pNoteService.getNoteList(noteMap1, " order by b.number ");
						form.setNoteList(noteList);
						/**查询笔记列表-end**/
						/**根据PageId查询笔记*/
						Map<String, Object> noteMap = new HashMap<String, Object>();
						noteMap.put("pageId", page.getId());
						noteMap.put("userId", user.getId());//用户ID
						List<PNote> nList = this.pNoteService.getNoteList(noteMap, "");
						if (nList != null && nList.size() > 0) {
							form.setNotes(nList.get(0));
						}
						/**根据PageId查询笔记-end*/
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			form = null;
		}
		model.put("toolForm", form);
		return new ModelAndView(forwordString, model);
	}

	/**
	 * 用于Ajax请求拷贝
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/tocCopy")
	public void tocCopy(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewForm form) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String id = form.getPubId();
			form.setNextPage((form.getPageNum() == null || "".equals(form.getPageNum())) ? 1 : Integer.valueOf(form.getPageNum()));
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("sourceId", id);
			condition.put("nextPage", form.getNextPage());
			PPage page = this.pPublicationsService.getPageByCondition(condition);
			/****是否能拷贝****/
			//最大拷贝数量
			if (form.getReadCount() < 0) {
				String browsePrecent = null;
				if (page.getPublications().getType() == 1) {//图书
					browsePrecent = page.getPublications().getPublisher().getBrowsePrecent();
				} else if (page.getPublications().getType() == 4 || page.getPublications().getType() == 7) {//文章
					browsePrecent = page.getPublications().getPublisher().getJournalBrowse();
				}
				browsePrecent = browsePrecent == null || "".equals(browsePrecent) ? "0" : browsePrecent;

				if (browsePrecent.startsWith("P")) {
					browsePrecent = browsePrecent.substring(1);
					form.setPrintCount((int) MathHelper.round(Double.valueOf(browsePrecent), 0));
				} else {
					Double a = MathHelper.mul(form.getCount(), (Double.valueOf(browsePrecent) / 100));
					form.setReadCount((int) MathHelper.round(a, 0));
				}
			}
			//这里判断是否可以拷贝
			if ("true".equalsIgnoreCase(form.getIsCopy()) && session != null) {
				Integer copyCount = session.getAttribute("copyCount_" + id) == null ? 0 : (Integer) session.getAttribute("copyCount_" + id);
				if (copyCount >= form.getReadCount()) {//如果够20页则禁止再继续拷贝
					form.setIsCopy("false");
					resultMap.put("error", Lang.getLanguage("Pages.Viewer.Forbid.Copy", request.getSession().getAttribute("lang").toString()));
				} else {
					Map<String, Object> copyMap = session.getAttribute("copyMap") == null ? new HashMap<String, Object>() : (Map<String, Object>) session.getAttribute("copyMap");
					//如果不存在则保存到session中，计数
					if (!CollectionsUtil.exist(copyMap, (form.getNextPage().toString() + "_" + id)) || "".equals(copyMap.get((form.getNextPage().toString() + "_" + id)))) {
						copyMap.put(form.getNextPage().toString() + "_" + id, id);
						session.setAttribute("copyMap", copyMap);
						session.setAttribute("copyCount_" + id, session.getAttribute("copyCount_" + id) == null ? 1 : (Integer) session.getAttribute("copyCount_" + id) + 1);
					}
				}
			}
			resultMap.put("isCopy", form.getIsCopy());//是否拷贝 ，true：拷贝 ;false：不拷贝
			resultMap.put("copyCount", (session.getAttribute("copyCount_" + id) == null ? "0" : session.getAttribute("copyCount_" + id).toString()));//已经拷贝的页数
			request.setAttribute("pagesId", page != null ? page.getId() : null);
			request.setAttribute("licenseId", form.getLicenseId());
			request.setAttribute("publicationsId", form.getPubId() == null ? null : form.getPubId());
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", (e instanceof CcsException) ? ((CcsException) e).getMessage() : e.getMessage());
		}
		try {
			JSONArray json = new JSONArray();
			json.add(resultMap);
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			throw e;
			//				request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
	}

	/**
	 * 用于Ajax请求下载
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/ajaxDownload")
	public void ajaxDownload(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewForm form) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String id = form.getPubId();
			form.setNextPage((form.getPageNum() == null || "".equals(form.getPageNum())) ? 1 : Integer.valueOf(form.getPageNum()));
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("sourceId", id);
			condition.put("nextPage", form.getNextPage());
			PPage page = this.pPublicationsService.getPageByCondition(condition);
			/****是否能下载****/
			//最大下载数量	
			String downloadPrecent = null;
			if (page.getPublications().getType() == 1) {//图书
				downloadPrecent = page.getPublications().getPublisher().getDownloadPercent();
			} else if (page.getPublications().getType() == 4 || page.getPublications().getType() == 7) {//文章
				downloadPrecent = page.getPublications().getPublisher().getJournalDownload();
			}
			downloadPrecent = downloadPrecent == null || "".equals(downloadPrecent) ? "0" : downloadPrecent;
			if (downloadPrecent.startsWith("P")) {
				downloadPrecent = downloadPrecent.substring(1);
				form.setDownloadCount((int) MathHelper.round(Double.valueOf(downloadPrecent), 0));
			} else {
				Double a = MathHelper.mul(form.getCount(), (Double.valueOf(downloadPrecent) / 100));
				form.setDownloadCount((int) MathHelper.round(a, 0));
			}

			//这里判断是否可以下载
			form.setIsDownload("false");
			if (session != null) {
				Integer downloadCount = session.getAttribute("downloadCount_" + id) == null ? 0 : Integer.valueOf(session.getAttribute("downloadCount_" + id).toString());
				if (downloadCount < form.getDownloadCount()) {
					form.setIsDownload("true");
				} else if (session.getAttribute("downloadMap") != null) {
					Map<String, Object> downloadMap = (Map<String, Object>) session.getAttribute("downloadMap");
					if (CollectionsUtil.exist(downloadMap, (form.getNextPage().toString() + "_" + id))) {
						form.setIsDownload("true");
					} else {
						resultMap.put("error", Lang.getLanguage("Pages.Viewer.Forbid.Download", request.getSession().getAttribute("lang").toString()));
					}
				} else if (downloadCount >= form.getDownloadCount()) {
					resultMap.put("error", Lang.getLanguage("Pages.Viewer.Forbid.Download", request.getSession().getAttribute("lang").toString()));
				}
			}

			if ("true".equals(form.getIsDownload())) {
				Map<String, Object> downloadMap = session.getAttribute("downloadMap") == null ? new HashMap<String, Object>() : (Map<String, Object>) session.getAttribute("downloadMap");
				//如果不存在则保存到session中，计数
				if (!CollectionsUtil.exist(downloadMap, (form.getNextPage().toString() + "_" + id)) || "".equals(downloadMap.get((form.getNextPage().toString() + "_" + id)))) {
					downloadMap.put(form.getNextPage().toString() + "_" + id, id);
					session.setAttribute("downloadMap", downloadMap);
					session.setAttribute("downloadCount_" + id, session.getAttribute("downloadCount_" + id) == null ? 1 : (Integer) session.getAttribute("downloadCount_" + id) + 1);
				}
			}
			resultMap.put("isDownload", form.getIsDownload());//是否可下载
			resultMap.put("downloadCount", (session.getAttribute("downloadCount_" + id) == null ? "0" : session.getAttribute("downloadCount_" + id).toString()));//已经拷贝的页数
			request.setAttribute("pagesId", page != null ? page.getId() : null);
			request.setAttribute("licenseId", form.getLicenseId());
			request.setAttribute("publicationsId", form.getPubId() == null ? null : form.getPubId());
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", (e instanceof CcsException) ? ((CcsException) e).getMessage() : e.getMessage());
		}
		try {
			JSONArray json = new JSONArray();
			json.add(resultMap);
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			throw e;
			//				request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
	}

	/**
	 * 用于Ajax请求打印
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/ajaxPrint")
	public void ajaxPrint(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewForm form) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String id = form.getPubId();
			if (form.getPageNum() != null && !"".equals(form.getPageNum())) {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("sourceId", id);
				condition.put("nextPage", 1);
				PPage page = this.pPublicationsService.getPageByCondition(condition);
				/****是否能下载****/
				//最大下载数量
				String printPrecent = null;
				if (page.getPublications().getType() == 1) {//图书
					printPrecent = page.getPublications().getPublisher().getPrintPercent();
				} else if (page.getPublications().getType() == 4 || page.getPublications().getType() == 7) {//文章
					printPrecent = page.getPublications().getPublisher().getJournalPrint();
				}
				printPrecent = printPrecent == null || "".equals(printPrecent) ? "0" : printPrecent;

				if (printPrecent.startsWith("P")) {
					printPrecent = printPrecent.substring(1);
					form.setPrintCount((int) MathHelper.round(Double.valueOf(printPrecent), 0));
				} else {
					Double a = MathHelper.mul(form.getCount(), (Double.valueOf(printPrecent) / 100));
					form.setPrintCount((int) MathHelper.round(a, 0));
				}

				//这里判断是否可以下载
				form.setIsPrint("true");
				String printTaskStr = "";
				if (session != null) {
					Integer printCount = session.getAttribute("printCount_" + id) == null ? 0 : Integer.valueOf(session.getAttribute("printCount_" + id).toString());
					Integer maxPrintCount = session.getAttribute("maxPrintCount＿" + id) == null ? 0 : Integer.valueOf(session.getAttribute("maxPrintCount＿" + id).toString());
					if (maxPrintCount > 0) {
						//getPageRange用于检查用户输入是否合法，并去除大于最大页数以及重复的页码。

						if (Integer.parseInt(form.getPageNum()) > 0) {
							List<Integer> pagesForPrint = getPageRange(form.getCount(), form.getPageNum());
							if (pagesForPrint != null && pagesForPrint.size() > 0) {
								Map<String, Object> printMap = null;
								List<Integer> printTask = new ArrayList<Integer>();
								Integer newjob = 0;
								if (session.getAttribute("printMap") != null) {
									printMap = (Map<String, Object>) session.getAttribute("printMap");
								} else {
									printMap = new HashMap<String, Object>();
								}
								for (Integer p : pagesForPrint) {
									if (printMap.containsKey(p + "_" + id)) {
										printTask.add(p);
										printTaskStr += p + ",";
									} else {
										if ((printCount + newjob + 1) <= maxPrintCount) {
											printTask.add(p);
											printTaskStr += p + ",";
											newjob++;
										} else {
											//若超过允许打印的最大页数
											form.setIsPrint("false");
											printTaskStr = "";
											newjob = 0;
											resultMap.put("error", Lang.getLanguage("Pages.Viewer.Forbid.Print", request.getSession().getAttribute("lang").toString()));
											break;
										}
									}
								}
								printTaskStr = "".equals(printTaskStr) ? "" : printTaskStr.substring(0, printTaskStr.length() - 1);
								//可以打印，将打印页码列表加入已打印session
								if ("true".equals(form.getIsPrint())) {
									for (Integer p : printTask) {
										if (!printMap.containsKey(p + "_" + id)) {
											printMap.put(p + "_" + id, id);
										}
									}
									session.setAttribute("printMap", printMap);
									session.setAttribute("printCount_" + id, printCount + newjob);
								}
							} else {
								//未输入或输入的页码内容非法
								form.setIsPrint("false");
								printTaskStr = "";
								resultMap.put("error", Lang.getLanguage("Pages.Viewer.Forbid.Print", request.getSession().getAttribute("lang").toString()));
							}
						} else {
							form.setIsPrint("false");

							resultMap.put("error", Lang.getLanguage("Pages.Viewer.Forbid.Print.negative", request.getSession().getAttribute("lang").toString()));
						}
					}
				}
				resultMap.put("isPrint", form.getIsPrint());
				resultMap.put("printTask", printTaskStr);
				resultMap.put("printCount", session.getAttribute("printCount_" + id) != null ? session.getAttribute("printCount_" + id) : 0);
				request.setAttribute("publicationsId", form.getPubId() == null ? null : form.getPubId());
			} else {
				resultMap.put("error", Lang.getLanguage("Controller.View.Print.Error.Range", request.getSession().getAttribute("lang").toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", (e instanceof CcsException) ? ((CcsException) e).getMessage() : e.getMessage());
		}
		try {
			JSONArray json = new JSONArray();
			json.add(resultMap);
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			throw e;
			//				request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
	}

	//从用户输入的打印页码中筛选可打印的页码
	private List<Integer> getPageRange(Integer maxPage, String range) throws Exception {
		Pattern r = Pattern.compile("^\\d+(\\-\\d+)?$");
		List<Integer> list = null;
		try {
			if (range != null && !"".equals(range)) {
				range = range.trim();
				String[] rangeArr = range.split(",");
				if (rangeArr != null && rangeArr.length > 0) {
					list = new ArrayList<Integer>();
					for (String rr : rangeArr) {
						Matcher m = r.matcher(rr);
						if (m.find()) {
							String[] rrr = rr.split("-");
							if (rrr != null) {
								if (rrr.length == 1) {
									list = list == null ? new ArrayList<Integer>() : list;
									Integer p = Integer.valueOf(rrr[0]);
									if (!list.contains(p) && p <= maxPage) {
										list.add(p);
									}
								} else if (rrr.length == 2) {
									list = list == null ? new ArrayList<Integer>() : list;
									Integer p1 = Integer.valueOf(rrr[0]);
									Integer p2 = Integer.valueOf(rrr[1]);
									if (p2 > p1) {
										for (int i = p1; i <= p2; i++) {
											if (!list.contains(i) && i <= maxPage) {
												list.add(i);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {

		}
		if (list != null && list.size() > 1) {
			Collections.sort(list);
		}
		return list;
	}

	/**
	 * 用于Ajax请求下载
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/turning")
	public void turning(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewForm form) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String id = form.getPubId();
			form.setNextPage((form.getPageNum() == null || "".equals(form.getPageNum())) ? 1 : Integer.valueOf(form.getPageNum()));
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("parentid", id);
			condition.put("status", null);
			condition.put("pageNum", form.getNextPage());
			List<PPublications> tocList = this.pPublicationsService.getTocList(condition, "");
			if (tocList != null && tocList.size() > 0) {
				resultMap.put("canTurning", "true");
				resultMap.put("turningTo", tocList.get(0).getPdf());
			} else {
				resultMap.put("canTurning", "false");
				if (form.getNextPage() <= 0) {
					resultMap.put("error", "没有更前的页了");
				} else {
					resultMap.put("error", "没有更后的页了");
				}
			}
			request.setAttribute("publicationsId", form.getPubId() == null ? null : form.getPubId());
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", (e instanceof CcsException) ? ((CcsException) e).getMessage() : e.getMessage());
		}
		try {
			JSONArray json = new JSONArray();
			json.add(resultMap);
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			throw e;
			//				request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
	}

	/**
	 * 接收心跳包
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/beat")
	public void beat(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String licenseId = request.getParameter("l");
			if (licenseId != null && !"".equals(licenseId)) {
				String md5Str = "";
				Cookie[] cookies = request.getCookies();
				if (cookies != null && cookies.length > 0) {
					for (Cookie cookie : cookies) {
						if ("readCookie".equals(cookie.getName())) {
							md5Str = cookie.getValue();
							break;
						}
					}
				}
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("licenseId", licenseId);
				condition.put("macId", md5Str);
				condition.put("endTime", null);
				this.pPublicationsService.beating(condition);
			}
		} catch (Exception e) {
		}
		/*try {
			JSONArray json = new JSONArray();
			json.add(resultMap);
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			throw e;
		//				request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}*/
	}

	@RequestMapping(value = "/form/addEpubLable")
	public void addEpubLable(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewForm form) throws Exception {
		String result = "error:" + Lang.getLanguage("PRecord.info.add.error", request.getSession().getAttribute("lang").toString());
		try {
			String id = form.getSourceId();
			String tag = (form.getTag() == null || "".equals(form.getTag())) ? null : form.getTag();
			CUser user = session.getAttribute("mainUser") == null ? null : (CUser) session.getAttribute("mainUser");
			if (user != null && tag != null) {
				//由于标签只有一个，先清空sourceId..admin 应该是通过session查询出 userId
				this.pRecordService.deleteBySourceId(form.getSourceId(), user.getId());
				PRecord record = new PRecord();
				record.setUserId(user.getId());
				record.setUserName(user.getName());

				Map<String, Object> pageCon = new HashMap<String, Object>();
				pageCon.put("sourceId", id);
				pageCon.put("tag", tag);
				PPage page = this.pPublicationsService.getPageByCondition(pageCon);
				record.setPages(page);

				PPublications pcontent = new PPublications();
				pcontent.setId(id);
				record.setPublications(pcontent);

				record.setCreateDate(new Date());
				this.pRecordService.addRecord(record);
				result = "success:" + Lang.getLanguage("PRecord.info.add.success", request.getSession().getAttribute("lang").toString());//"标签添加成功！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "error:" + ((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
		}
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			throw e;//(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
	}

	@RequestMapping(value = "/form/addEPubNote")
	public void addEPubNote(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewForm form) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		//			String result = Lang.getLanguage("Note.info.add.error");//"error";
		try {
			String id = form.getSourceId();
			CUser user = session.getAttribute("mainUser") == null ? null : (CUser) session.getAttribute("mainUser");
			String tag = (form.getTag() == null || "".equals(form.getTag())) ? null : form.getTag();
			if (user != null && tag != null) {
				//判断字段长度
				if (form.getNoteContent().length() > 1000) {
					//内容长度太长
					resultMap.put("error", Lang.getLanguage("Pages.view.Prompt.NoteLength", request.getSession().getAttribute("lang").toString()));
				} else {
					PNote note = null;
					Map<String, Object> condtion = new HashMap<String, Object>();
					condtion.put("publicationsId", id);
					condtion.put("userId", user.getId());
					condtion.put("tag", tag);
					List<PNote> nlist = this.pNoteService.getNoteList(condtion, "");
					if (nlist == null || nlist.size() == 0) {
						note = new PNote();
						note.setUserId(user.getId());
						note.setUserName(user.getName());
						Map<String, Object> pageCon = new HashMap<String, Object>();
						pageCon.put("sourceId", id);
						pageCon.put("tag", tag);
						PPage page = this.pPublicationsService.getPageByCondition(pageCon);
						note.setPages(page);
						note.setNoteContent(form.getNoteContent());
						PPublications pcontent = new PPublications();
						pcontent.setId(id);
						note.setPublications(pcontent);
						note.setCreateDate(new Date());
						this.pNoteService.addNote(note);
					} else {
						note = nlist.get(0);
						note.setNoteContent(form.getNoteContent());
						this.pNoteService.updateNote(note);
					}

					resultMap.put("success", Lang.getLanguage("Note.info.add.success", request.getSession().getAttribute("lang").toString()));
					resultMap.put("noteId", note.getId());
				}
				//			result = Lang.getLanguage("Note.info.add.success");
			} else {
				resultMap.put("error", Lang.getLanguage("Pages.epubReader.must.login", request.getSession().getAttribute("lang").toString()));
			}
		} catch (Exception e) {
			resultMap.put("error", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
		}
		try {
			JSONArray json = new JSONArray();
			json.add(resultMap);
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			throw e;
			//				request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
	}

	@RequestMapping(value = "/form/deleteEpubNote")
	public void deleteEpubNote(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewForm form) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		//			String result = Lang.getLanguage("Note.info.add.error");//"error";
		try {
			String id = form.getSourceId();
			CUser user = session.getAttribute("mainUser") == null ? null : (CUser) session.getAttribute("mainUser");
			String tag = (form.getTag() == null || "".equals(form.getTag())) ? null : form.getTag();
			if (user != null && tag != null && id != null && !"".equals(id)) {

				Map<String, Object> condtion = new HashMap<String, Object>();
				condtion.put("publicationsId", id);
				condtion.put("userId", user.getId());
				condtion.put("tag", tag);
				List<PNote> plist = this.pNoteService.getNoteList(condtion, "");
				if (plist == null || plist.size() == 0) {
					resultMap.put("msg", "error");
					resultMap.put("info", Lang.getLanguage("Note.info.delete.error", request.getSession().getAttribute("lang").toString()));
				} else {
					this.pNoteService.deleteNote(plist.get(0).getId());
					resultMap.put("msg", "success");
					resultMap.put("info", Lang.getLanguage("Note.info.delete.success", request.getSession().getAttribute("lang").toString()));
				}
			}
		} catch (Exception e) {
			resultMap.put("msg", "error");
			resultMap.put("info", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
		}
		try {
			JSONArray json = new JSONArray();
			json.add(resultMap);
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			throw e;
			//				request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
	}

	@RequestMapping(value = "/form/getEpubNote")
	public void getEpubNote(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewForm form) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//			String result = Lang.getLanguage("Note.info.add.error");//"error";
		try {
			String id = form.getSourceId();
			CUser user = session.getAttribute("mainUser") == null ? null : (CUser) session.getAttribute("mainUser");
			if (user != null && id != null && !"".equals(id)) {

				Map<String, Object> condtion = new HashMap<String, Object>();
				condtion.put("publicationsId", id);
				condtion.put("userId", user.getId());
				List<PNote> plist = this.pNoteService.getNoteList(condtion, "");
				resultMap.put("msg", "success");
				resultMap.put("list", plist);
			}
		} catch (Exception e) {
			resultMap.put("msg", "error");
			resultMap.put("info", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
		}
		JSONArray json = JSONArray.fromObject(resultMap);
		response.setContentType("text/json;charset=utf-8");
		PrintWriter writer = null;
		try {
			// 获取输出流
			writer = response.getWriter();
			writer.print(json.toString());
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private static String key = "0987654321321";

	/**
	 * 向Session和Cookie中放置加密字符串
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	private void setToken(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		String tk = MD5Util.getEncryptedPwd(key);
		session.setAttribute("token", tk);
		System.out.println(tk);
		Cookie cookie = new Cookie("token", "_" + tk);
		String path = "/";
		//cookie.setPath(request.getContextPath()+(request.getContextPath().endsWith("/")?"":"/"));
		cookie.setPath(path);
		response.addCookie(cookie);
	}

	/**
	 * 下载之前对用户token进行判断
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	private boolean judgeSessionAndCookie(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		if (session.getAttribute("token") != null) {
			for (Cookie cookie : request.getCookies()) {
				if ("token".equals(cookie.getName())) {
					String pToken = session.getAttribute("token").toString();
					System.out.println(cookie.getValue() + "\t" + pToken);
					if (cookie.getValue() != null && pToken != null && pToken.equals(cookie.getValue())) {
						setToken(request, response, session);
						//response.flushBuffer();
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 同步access表
	 * @param pubid
	 * @param user
	 * @param institutionId
	 * @param ip
	 * @param request
	 * @throws Exception
	 */
	/*	public void addLog1(String pubid,CUser user,String institutionId,String ip,HttpServletRequest request ,Integer accessa)throws Exception { 
			
			String Agent = request.getHeader("User-Agent");
			
			LAccess access = new LAccess();
			
			access.setAccess(accessa);//访问状态1-访问成功 2-访问拒绝
			access.setType(2);//操作类型1-访问摘要 2-访问内容 3-检索
			access.setCreateOn(new Date());
			access.setIp(ip);
			access.setPlatform("CNPe");
			access.setClientInfo(Agent);
			access.setYear(StringUtil.formatDate(access.getCreateOn(),"yyyy"));
			access.setMonth(StringUtil.formatDate(access.getCreateOn(),"MM"));
			if("01".equals(access.getMonth()))	access.setMonth1(1);
			if("02".equals(access.getMonth()))	access.setMonth2(1);
			if("03".equals(access.getMonth()))	access.setMonth3(1);
			if("04".equals(access.getMonth()))	access.setMonth4(1);
			if("05".equals(access.getMonth()))	access.setMonth5(1);
			if("06".equals(access.getMonth()))	access.setMonth6(1);
			if("07".equals(access.getMonth()))	access.setMonth7(1);
			if("08".equals(access.getMonth()))	access.setMonth8(1);
			if("09".equals(access.getMonth()))	access.setMonth9(1);
			if("10".equals(access.getMonth()))	access.setMonth10(1);
			if("11".equals(access.getMonth()))	access.setMonth11(1);
			if("12".equals(access.getMonth()))	access.setMonth12(1);
			PPublications publications = new PPublications();
			publications.setId(pubid);
			access.setPublications(publications);	
			
			access.setUserId(user!=null?user.getId():null);
			access.setInstitutionId(institutionId);
			this.logAOPService.addLog(access);	
	//			if(institutionId==null && user ==null){
	//				//机构IP范围外未登录
	//				this.logAOPService.addLog(access);	
	//			}else{
	//				if(institutionId!=null){
	//					if(user!=null){
	//						access.setUserId(user.getId());
	//					}
	//					access.setInstitutionId(institutionId);
	//					this.logAOPService.addLog(access);	
	//					if(user!=null && user.getInstitution()!=null && !institutionId.equals(user.getInstitution().getId())){
	//						access.setUserId(user.getId());
	//						access.setInstitutionId(user.getInstitution().getId());	
	//						this.logAOPService.addLog(access);	
	//					}
	//				}
	//			}
		}  */
}
