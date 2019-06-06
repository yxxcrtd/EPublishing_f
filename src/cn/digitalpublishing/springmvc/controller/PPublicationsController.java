package cn.digitalpublishing.springmvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import cn.ccsit.restful.tool.Converter;
import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.Param;
import cn.com.daxtech.framework.model.ResultObject;
import cn.com.daxtech.framework.util.ObjectUtil;
import cn.com.daxtech.framework.util.StringUtil;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.BIpRange;
import cn.digitalpublishing.ep.po.BPublisher;
import cn.digitalpublishing.ep.po.BSubject;
import cn.digitalpublishing.ep.po.CFavourites;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.ep.po.LComplicating;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.LLicenseIp;
import cn.digitalpublishing.ep.po.OExchange;
import cn.digitalpublishing.ep.po.OOrderDetail;
import cn.digitalpublishing.ep.po.PCcRelation;
import cn.digitalpublishing.ep.po.PContentRelation;
import cn.digitalpublishing.ep.po.PCsRelation;
import cn.digitalpublishing.ep.po.PPrice;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.ep.po.PPublicationsRelation;
import cn.digitalpublishing.ep.po.ResourcesSum;
import cn.digitalpublishing.redis.po.Book;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;
import cn.digitalpublishing.springmvc.form.custom.LLicenseForm;
import cn.digitalpublishing.springmvc.form.index.IndexForm;
import cn.digitalpublishing.springmvc.form.product.PPublicationsForm;
import cn.digitalpublishing.util.io.FileUtil;
import cn.digitalpublishing.util.io.IOHandler;
import cn.digitalpublishing.util.io.MD5Util;
import cn.digitalpublishing.util.web.DateUtil;
import cn.digitalpublishing.util.web.IpUtil;
import cn.digitalpublishing.util.web.MathHelper;
import cn.digitalpublishing.util.web.dawsonEncryption;
import cn.digitalpublishing.util.xml.EPubHelper;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("pages/publications")
public class PPublicationsController extends BaseController {

	int x = 0;
	Map<String, Object> already = new HashMap<String, Object>();
	List<PPublicationsRelation> relation = new ArrayList<PPublicationsRelation>();
	Set<String> hang = new HashSet<String>();

	@RequestMapping(value = "/insertCount", method = RequestMethod.POST)
	public void insertCount(HttpServletRequest request, Model model) {
		try {
			int bookCount = Integer.parseInt(request.getParameter("bookCount")
					.toString());
			int bookCountEn = Integer.parseInt(request.getParameter(
					"bookCountEn").toString());
			int enJournal = Integer.parseInt(request.getParameter("enJournal")
					.toString());
			int cnJournal = Integer.parseInt(request.getParameter("cnJournal")
					.toString());

			int journalsCount = Integer.parseInt(request.getParameter(
					"journalsCount").toString());
			int articleCount = Integer.parseInt(request.getParameter(
					"articleCount").toString());
			int sumCount = Integer.parseInt(request.getParameter("sumCount")
					.toString());
			ResourcesSum obj = new ResourcesSum();
			obj.setBookCount(bookCount);
			obj.setBookCountEn(bookCountEn);
			obj.setCnJournal(cnJournal);
			obj.setEnJournal(enJournal);
			obj.setJournalsCount(journalsCount);
			obj.setArticleCount(articleCount);
			obj.setSumCount(sumCount);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("type", 1);
			List<ResourcesSum> list = this.resourcesSumService.getList(
					condition, null);
			if (list != null && list.size() > 0) {
				this.resourcesSumService.updateResourcesSum(obj, list.get(0)
						.getId(), null);
			} else {
				obj.setType(1);
				obj.setId("1");
				this.resourcesSumService.insertRecommend(obj);
			}

			ResultObject<BPublisher> result = new ResultObject<BPublisher>(1,
					Lang.getLanguage("publisher.info.maintain.success", request
							.getSession().getAttribute("lang").toString()));// "出版商信息维护成功！");
			model.addAttribute("target", result);
		} catch (Exception e) {
			// result=new
			// ResultObject<BPublisher>(2,Lang.getLanguage("publisher.info.maintain.error",request.getSession().getAttribute("lang").toString()));//"出版商信息维护失败！");
		}
	}

	@RequestMapping(value = "/form/{publisherid}/list")
	public ModelAndView list(@PathVariable String publisherid,
			HttpServletRequest request, HttpServletResponse response,
			PPublicationsForm form) throws Exception {
		String forwardString = "publications/list";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = request.getSession() == null ? null : (CUser) request
					.getSession().getAttribute("mainUser");
			if (form.getIsLicense() != null && !form.getIsLicense().equals("")) {
				request.getSession().setAttribute("selectType",
						form.getIsLicense());
			} else {
				request.getSession().setAttribute("selectType", "");
			}
			if (request.getParameter("sub") != null
					&& !"".equals(request.getParameter("sub").toString())) {
				form.setSubParentId(request.getParameter("sub").toString());
				BSubject parent = this.bSubjectService.getSubject(form
						.getSubParentId());
				if (parent.getSubject().getTreeCode().length() == 3) {
					model.put("parentId", "");
				} else {
					model.put("parentId", parent.getSubject().getId());
				}
				if (parent.getTreeCode().length() == 3) {
					model.put("subId", "");
				} else {
					model.put("subId", parent.getId());
				}
			} else {
				form.setSubParentId("0");
			}
			if (request.getParameter("ptype") != null
					&& !"".equals(request.getParameter("ptype").toString())) {
				form.setType(request.getParameter("ptype").toString());
			}
			if (request.getParameter("pubYear") != null
					&& !"".equals(request.getParameter("pubYear").toString())) {
				form.setPubYear(request.getParameter("pubYear").toString());
			}
			Map<String, Object> condition = form.getCondition();
			condition.put("publisherid", publisherid);
			// condition.put("mainType","0");
			condition.put("subjectId", form.getSubParentId());
			condition.put("ptype", form.getType());
			condition.put("pubYear", form.getPubYear());
			condition.put("order",
					form.getLetter() == null ? "" : form.getLetter());

			condition.put("isLicense", form.getIsLicense());// 是否订阅
			condition.put("userId", user == null ? "" : user.getId());// 用户ID
			condition.put("ip", IpUtil.getIp(request));// 用户ID
			if (form.getIsLicense() == null || "".equals(form.getIsLicense())) {
				condition.put("status", 2);
			} else {
				condition.put("check", "false");
			}
			condition.put("dtype", 5);// 排除数据库
			String sort = "order by a.id";
			if (form.getOrderDesc() != null
					&& "upDesc".equalsIgnoreCase(form.getOrderDesc())) {
				sort = " order by a.updateOn desc ";
			} else if (form.getOrderDesc() != null
					&& "pubDesc".equalsIgnoreCase(form.getOrderDesc())) {
				sort = " order by a.pubDate desc ";
			}
			form.setUrl(request.getRequestURL().toString());

			condition.put("typeArr", new Integer[] { 1, 2 });// 只查询期刊和图书
			condition.put("level", 2);
			condition.put("licenseStatus", 1);
			String ins_Id = null;
			if (request.getSession().getAttribute("institution") != null) {
				ins_Id = ((BInstitution) request.getSession().getAttribute(
						"institution")).getId();
			}
			condition.put("institutionId", ins_Id);
			List<PPublications> list = null;
			if (form.getIsLicense() == null || "".equals(form.getIsLicense())) {
				form.setCount(this.pPublicationsService
						.getPubCountO1(condition));
				list = this.pPublicationsService.getPubPageList(condition, " ",
						form.getPageCount(), form.getCurpage(), user, IpUtil
								.getIp(request).toString());
			} else {
				// 由于要查询出OA和免费
				condition.put("oafreeUid", Param.getParam("OAFree.uid.config")
						.get("uid"));
				form.setCount(this.pPublicationsService
						.getPubSubscriptionCount(condition));
				list = this.pPublicationsService.getPubSubscriptionPageList(
						condition, " ", form.getPageCount(), form.getCurpage(),
						user, IpUtil.getIp(request).toString());
				// list =
				// this.pPublicationsService.getLicensePubPagingList(condition,
				// " ",
				// form.getPageCount(),form.getCurpage(),user,IpUtil.getIp(request).toString());
			}
			// form.setCount(this.pPublicationsService.getPubCount(condition));
			// List<PPublications> list =
			// this.pPublicationsService.getPubPageList2(condition,sort,form.getPageCount(),form.getCurpage(),user,IpUtil.getIp(request));
			for (int i = 0; i < list.size(); i++) {
				PPublications pub = list.get(i);
				if (user != null) {
					Map<String, Object> con = new HashMap<String, Object>();
					con.put("publicationsid", pub.getId());
					con.put("status", 2);
					con.put("userTypeId",
							user.getUserType().getId() == null ? "" : user
									.getUserType().getId());
					List<PPrice> price = this.pPublicationsService
							.getPriceList(con);
					int isFreeUser = request.getSession().getAttribute(
							"isFreeUser") == null ? 0 : (Integer) request
							.getSession().getAttribute("isFreeUser");
					if (isFreeUser != 1) {
						for (int j = 0; j < price.size(); j++) {
							PPrice pr = price.get(j);
							double endPrice = MathHelper.round(MathHelper.mul(
									pr.getPrice(), 1.13d));
							price.get(j).setPrice(endPrice);
						}
					}
					list.get(i).setPriceList(price);
				}
			}
			form.setCurCount(list != null ? list.size() : 0);
			BPublisher publisher = this.configureService
					.getPublisher(publisherid);
			model.put("publisher", publisher);
			model.put("list", list);
			model.put("form", form);

			model.put("publisherid", publisherid);
			model.put("subjectId", form.getSubParentId());
			model.put("ptype", form.getType());
			model.put("pubYear", form.getPubYear());
		} catch (Exception e) {
			request.setAttribute(
					"message",
					(e instanceof CcsException) ? Lang.getLanguage(
							((CcsException) e).getPrompt(), request
									.getSession().getAttribute("lang")
									.toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/form/article/{publicationsid}")
	public ModelAndView article(@PathVariable String publicationsid, HttpServletRequest request, HttpServletResponse response, HttpSession session, PPublicationsForm form) throws Exception {
		CUser user = null == request.getSession().getAttribute("mainUser") ? null : (CUser) request.getSession().getAttribute("mainUser");
		BInstitution institution = null == (BInstitution) session.getAttribute("institution") ? null == user ? null : user.getInstitution() : (BInstitution) session.getAttribute("institution");
		// BInstitution institution2 = null == (BInstitution) request.getSession().getAttribute("institution") ? user.getInstitution() : (BInstitution) request.getSession().getAttribute("institution");

		String forwardString = "publications/article";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Boolean permission = false;
			Integer licenseStatus = null;
			Double coeff = 0.0; // listprice系数
			Double coeff1 = 0.0; // 并发系数
			Double cny = 0.0; // 返给页面的人民币价格
			Double rate = 0.0; // 汇率

			String ins_Id = "";
			List<PPrice> pricelist = null;
			Map<String, Object> condition = new HashMap<String, Object>();
			if (user != null) {
				condition.put("publicationsid", publicationsid);
				condition.put("status", 2);
				condition.put("userTypeId", user.getUserType().getId() == null ? "" : user.getUserType().getId());
				pricelist = this.pPublicationsService.getPriceList(condition);
			}

			PPublications pub = new PPublications();
			// 由于加入了标签，这里不能用get查询
			Map<String, Object> s = new HashMap<String, Object>();
			s.put("id", publicationsid);
			s.put("lang", request.getSession().getAttribute("lang"));
			if ("1".equals(form.getIsReady())) {
				s.put("status", null);
			} else { // 在IsReady不等于1时，要去判断这个产品的License是否存在，尽管产品已经下架，如果存在也可以浏览
				Map<String, Object> licenseCondition = new HashMap<String, Object>();
				licenseCondition.put("status", 1);// 有效
				licenseCondition.put("pubId", publicationsid);// 图书ID
				ins_Id = null;

				if (request.getSession().getAttribute("institution") != null) {
					ins_Id = ((BInstitution) request.getSession().getAttribute("institution")).getId();
				}
				licenseCondition.put("institutionId", ins_Id);
				licenseCondition.put("userId", user == null ? "" : user.getId()); // 用户ID
				licenseCondition.put("level", 2);
				licenseCondition.put("isTrail", "0");
				List<LLicense> licenseList = this.pPublicationsService.getLicenseList(licenseCondition, "");
				if (licenseList != null && !licenseList.isEmpty()) {
					s.put("status", null);
					permission = true;
					licenseStatus = licenseList.get(0).getStatus();// 区分章节的购买，如果图书购买，章节免费看
					//coeff = licenseList.get(0).getCoefficient();
					//coeff1 = licenseList.get(0).getCoefficient1();
				}
			}
			List<PPublications> ppList = this.pPublicationsService.getPubList(s, " order by a.createOn ", (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
			if (ppList != null && ppList.size() > 0) {
				pub = ppList.get(0);
				// 这里是因为政治原因的问题，直接给它提示错误
				String ppp = pub.getId();
				if (ppp != null) {
					PPublications pp = this.pPublicationsService.getPublications(ppp);
					pub.setAvailable(pp.getAvailable());
					if (pp.getAvailable() != null && pp.getAvailable() == 3) {
						request.setAttribute("message", Lang.getLanguage("Controller.Publications.Prompt.Content.pp", request.getSession().getAttribute("lang").toString()));
						forwardString = "error";
						model.put("form", form);
						return new ModelAndView(forwardString, model);
					}
					
					// 非人民币和美元的币制转换成人民币
					String lcurr = pp.getLcurr();
					coeff = pp.getCoefficient();
					coeff1 = pp.getCoefficient1();
					if (null != lcurr && !"USD".equals(lcurr) && !"CNY".equals(lcurr)) {
						Map<String,Object> exCondition = new HashMap<String,Object>();
						exCondition.put("xcurr", lcurr);
						OExchange exchange = oOrderService.getExchangeByCondition(exCondition);
						if (null != exchange) {
							rate = Double.valueOf(exchange.getRate());
							cny = round(MathHelper.mul(pp.getListPrice(), rate));
							System.out.println(lcurr + " 的汇率是：" + rate + " 转换成人民币是：" + cny);
							
							exCondition.put("xcurr", "USD");
							OExchange e = oOrderService.getExchangeByCondition(exCondition);
							rate = Double.valueOf(e.getRate());
							cny = round(MathHelper.div(cny, rate));
							System.out.println("人民币兑美元的汇率是：" + rate + " 转换成美元是：" + cny);
						}
					}
				}
				
				// 若通过搜索结果列表访问该页面，记录搜索日志 蒋凯 2013-11-15
				if (request.getParameter("fp") != null && !"".equals(request.getParameter("fp").toString())) {
					this.addLog(pub, user, ins_Id, request.getParameter("sv"), IpUtil.getIp(request), Integer.parseInt(request.getParameter("fp")));
				}
				model.put("d", new Date());
				if (pub.getType() == 4) {
					request.setAttribute("form", form);
					request.getRequestDispatcher("/pages/publications/form/journalarticle/" + publicationsid).forward(request, response);
					return new ModelAndView(forwardString, model);
				} else if (pub.getType() == 2) {
					request.setAttribute("form", form);
					request.getRequestDispatcher("/pages/publications/form/journaldetail/" + publicationsid).forward(request, response);
					return new ModelAndView(forwardString, model);
				} else if (pub.getType() == 6) {
					request.setAttribute("form", form);
					request.getRequestDispatcher("/pages/publications/form/journaldetail/" + pub.getPublications().getId() + "?volumeId=" + publicationsid).forward(request, response);
					return new ModelAndView(forwardString, model);
				} else if (pub.getType() == 7) {
					request.setAttribute("form", form);
					request.getRequestDispatcher("/pages/publications/form/journaldetail/" + pub.getPublications().getId() + "?issueId=" + publicationsid).forward(request, response);
					return new ModelAndView(forwardString, model);
				} else if (pub.getType() == 5) {
					request.setAttribute("form", form);
					request.getRequestDispatcher("/pages/publications/form/database").forward(request, response);
					return new ModelAndView(forwardString, model);
				}
				
				// isReady 用于 在产品下架后 仍然可以继续阅读时使用
				if ("1".equals(form.getIsReady()) || pub.getStatus() == 2
						|| permission) {
					if (pub.getPubDate() != null
							&& pub.getPubDate().length() > 4) {
						pub.setPubDate(pub.getPubDate().substring(0, 4));// 页面值显示年份
					}
					String lang = pub.getLang();
					if (lang == null && pub.getType() == 3
							&& pub.getPublications() != null
							&& pub.getPublications().getLang() != null) {
						lang = pub.getPublications().getPublications()
								.getLang();
					}
					pub.setLang(lang == null ? "" : lang.toUpperCase());// 语言大写显示
					// 查询分类
					Map<String, Object> conn = new HashMap<String, Object>();
					conn.put("publicationsId", pub.getId());
					pub.setCsList(this.bSubjectService.getSubPubList(conn,
							" order by a.subject.code "));
					form.setObj(pub); // 产品信息
					if (pricelist != null && !pricelist.isEmpty()) {
						if (session.getAttribute("isFreeUser") == null
								|| (Integer) session.getAttribute("isFreeUser") != 1) {
							for (PPrice p : pricelist) {
								p.setPrice(round(MathHelper.mul(p.getPrice(),
										1.13)));
							}
						}
					} else {
						// 商品未标价
					}
					condition.clear();
					condition.put("statusnew", 2);
					condition.put("parentidNew", form.getObj().getId());
					List<PPublications> list = null;
					if (pub.getType() == 1) {

						// list =
						// this.pPublicationsService.getPubList(condition,
						// "order by a.startPage",(CUser)request.getSession().getAttribute("mainUser"),IpUtil.getIp(request));
						list = this.pPublicationsService.getPubList(
								condition,
								" order by a.treecode,a.order,a.journalOrder ",
								(CUser) request.getSession().getAttribute(
										"mainUser"), IpUtil.getIp(request));
						if (list != null && list.size() > 0) {// 显示章节层级关系
							form.setChaperShow(1);
							String lastCode = "";
							for (int i = 0; i < list.size(); i++) {
								String remark = list.get(i).getRemark() != null ? list
										.get(i).getRemark()
										.replace("<![CDATA[", "")
										.replace("]]>", "")
										: list.get(i).getRemark();
								list.get(i).setRemark(remark);
								if (pub.getHomepage() == null
										|| pub.getHomepage() == 1) {// 验证是否允许章节购买跳转不同显示页面章节列表1-toc;2-正常列表

									if (list.get(i).getTreecode() != null
											&& !"".equals(list.get(i)
													.getTreecode())) {
										String tcode = list.get(i)
												.getTreecode().substring(0, 3);
										if (tcode.equals(lastCode)) {
											if (i > 0) {
												// list.get(i-1).setFullText("chapt_author_100");//临时存放样式名字用chapt_p2
												list.get(i).setFullText(
														"chapt_p2");// 临时存放样式名字用
											}
										} else {
											list.get(i).setFullText("chapt_p1");
										}
										Integer num = 30 * (list.get(i)
												.getTreecode().length() / 3 - 1) + 10;
										list.get(i).setBrowsePrecent(
												num.toString());// 临时存放缩进度用
										lastCode = tcode;
									} else {
										list.get(i).setFullText("chapt_p1");
										Integer num = 30 * (3 / 3 - 1) + 10;
										list.get(i).setBrowsePrecent(
												num.toString());// 临时存放缩进度用
									}

								} else {
									form.setChaperShow(2);
								}
							}

						}
					}

					CUser ruser = (CUser) session.getAttribute("recommendUser");

					form.setRecommendUser(ruser);

					// 产品包列表
					condition.clear();
					condition.put("publicationsid", pub.getId());
					List<PCcRelation> pcclist = null;
					if (pub.getType() == 1) {
						pcclist = this.pPublicationsService.getPccList(condition, "", (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
					}
					// 查询用户是否具有有效license
					LLicense license = this.pPublicationsService.getVaildLicense(pub, user, IpUtil.getIp(request));
					/*
					 * LLicense license=null;
					 * if(session.getAttribute("mainUser")!=null){ CUser
					 * mUser=(CUser)session.getAttribute("mainUser");
					 * condition.clear(); condition.put("pubId",publicationsid);
					 * condition.put("status", 1);//有效的license
					 * condition.put("userId", mUser.getId()); List<LLicense>
					 * licenselist
					 * =this.pPublicationsService.getLicenseList(condition,
					 * " order by a.accessUIdType "); if(licenselist !=null &&
					 * !licenselist.isEmpty()){ license=licenselist.get(0);
					 * }else{ condition.remove("userId");
					 * if(request.getRemoteAddr()!=null){
					 * condition.put("ip",IpUtil.getLongIp(
					 * request.getRemoteAddr().toString())); List<LLicenseIp>
					 * lliplist= this.customService.getLicenseIpList(condition,
					 * " order by b.accessUIdType "); if(lliplist!=null &&
					 * !lliplist.isEmpty()){
					 * license=lliplist.get(0).getLicense(); } } } }
					 */

					/*
					 * 查询同分类中购买次数最多的前4个商品开始 condition.clear();
					 * condition.put("publicationsId", pub.getId());
					 * List<PCsRelation>
					 * pcslist=this.bSubjectService.getSubPubList(condition,
					 * ""); condition.clear(); condition.put("pcslist",
					 * pcslist); condition.put("pstatus", 2); List<PCsRelation>
					 * toplist = null; if(pub.getType()==1){
					 * toplist=this.bSubjectService
					 * .getTops(condition," order by p.buyTimes desc "
					 * ,5,0,(CUser
					 * )request.getSession().getAttribute("mainUser"),
					 * IpUtil.getIp(request)); }
					 */

					// 查询购买过此书的图书馆
					List<BInstitution> subedInslist = null;
					if (session.getAttribute("mainUser") != null) {
						CUser mUser = (CUser) session.getAttribute("mainUser");
						if (mUser.getLevel() == 5 && pub.getType() == 1) {// 中图管理员可以查看
							condition.clear();
							condition.put("pubId", pub.getId());
							subedInslist = this.configureService
									.getInstitutionList(condition, "");
						}
					}

					condition.clear();
					Integer AllReadTimes = 0;
					Integer InsReadTimes = 0;
					condition.put("type", 2);
					condition.put("pubId", pub.getId());
					condition.put("access", 1);
					if (pub.getType() == 1) {
						AllReadTimes = this.logAOPService
								.getNormalCount(condition);
					}

					if (institution != null && pub.getType() == 1) {
						condition.put("institutionId", institution.getId());
						InsReadTimes = this.logAOPService
								.getNormalCount(condition);
					}

					if (pub.getOa() != 2 && pub.getFree() != 2
							&& license == null) {
						// 没有访问权限,计数
						this.oOrderService
								.addPDACounter(pub, institution, user);
					}
					model.put("id", pub.getId());
					model.put("InsReadTimes", InsReadTimes);
					model.put("AllReadTimes", AllReadTimes);
					model.put("subedInslist", subedInslist);
					// model.put("toplist",toplist);
					// model.put("license", license);
					model.put("pcclist", pcclist);
					model.put("pricelist", pricelist);
					model.put("list", list);
					model.put("form", form);
					model.put("isfav", form.getObj().getFavorite());
					model.put("licenseStatus", licenseStatus);// 图书是否已经购买
					model.put("coeff", coeff);
					model.put("coeff1", coeff1);
					model.put("cny", cny);
				} else {
					forwardString = "frame/result";
					request.setAttribute("prompt", Lang.getLanguage(
							"Controller.Publications.Prompt.Title.NotFind",
							request.getSession().getAttribute("lang")
									.toString()));
					request.setAttribute("message", Lang.getLanguage(
							"Controller.Publications.Prompt.Content.OffSale",
							request.getSession().getAttribute("lang")
									.toString()));
				}
			} else {
				forwardString = "frame/result";
				request.setAttribute("prompt", Lang.getLanguage(
						"Controller.Publications.Prompt.Title.NotFind", request
								.getSession().getAttribute("lang").toString()));
				request.setAttribute("message", Lang.getLanguage(
						"Controller.Publications.Prompt.Content.UnFind",
						request.getSession().getAttribute("lang").toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(
					"message",
					(e instanceof CcsException) ? Lang.getLanguage(
							((CcsException) e).getPrompt(), request
									.getSession().getAttribute("lang")
									.toString()) : e.getMessage());
			forwardString = "error";
		}
		model.put("form", form);
		request.setAttribute("id", form.getId());

		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/form/charperView")
	public ModelAndView article(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			PPublicationsForm form) throws Exception {
		String forwardString = "publications/charper";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String id = request.getParameter("id");
			String pid = request.getParameter("pid");
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("cherperid", id);
			List<PPublications> list = null;
			// list = this.pPublicationsService.getPubLists(condition,
			// " order by a.treecode,a.order,a.startPage ");
			list = this.pPublicationsService.getPubList(condition,
					" order by a.treecode,a.order,a.startPage ",
					(CUser) request.getSession().getAttribute("mainUser"),
					IpUtil.getIp(request));
			if (list != null && list.size() > 0) {
				list.get(0).setRemark(
						list.get(0).getRemark().replace("<![CDATA[", "")
								.replace("]]>", ""));
				form.setObj(list.get(0));
				;
			}
			condition.clear();
			condition.put("publicationsid", id);
			condition.put("status", 2);
			List<PPrice> pricelist = this.pPublicationsService
					.getPriceList(condition);
			if (pricelist != null && !pricelist.isEmpty()) {

				for (PPrice p : pricelist) {
					p.setPrice(round(MathHelper.mul(p.getPrice(), 1.13)));
				}

			} else {
				// 商品未标价
			}
			form.setPbookID(pid);
			form.setChaperID(id);
			condition.clear();
			condition.put("parentid", pid);
			condition.put("charperIDD", id);
			List<PPublications> Plist = null;
			// Plist = this.pPublicationsService.getPubLists(condition,
			// " order by a.treecode,a.order,a.startPage ");
			Plist = this.pPublicationsService.getPubList(condition,
					" order by a.treecode,a.order,a.startPage ",
					(CUser) request.getSession().getAttribute("mainUser"),
					IpUtil.getIp(request));
			condition.clear();
			condition.put("id", pid);
			List<PPublications> Plists = this.pPublicationsService.getPubList(
					condition, " order by a.treecode,a.order,a.startPage ",
					(CUser) request.getSession().getAttribute("mainUser"),
					IpUtil.getIp(request));
			if (Plists != null && Plists.size() > 0) {
				form.setSubscribedIp(Plists.get(0).getSubscribedIp());
				form.setSubscribedUser(Plists.get(0).getSubscribedUser());
				form.setOa(Plists.get(0).getOa());
				form.setFree(Plists.get(0).getFree());
				form.setFavorite(Plists.get(0).getFavorite());
			}
			model.put("list", list);
			model.put("Plist", Plist);
			model.put("pricelist", pricelist);
			model.put("Plists", Plists);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(
					"message",
					(e instanceof CcsException) ? Lang.getLanguage(
							((CcsException) e).getPrompt(), request
									.getSession().getAttribute("lang")
									.toString()) : e.getMessage());
			forwardString = "error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletRequest request, Model model) {
		ResultObject<PPublications> result = null;
		try {
			String objJson = request.getParameter("obj").toString();
			Converter<PPublications> converter = new Converter<PPublications>();
			PPublications obj = (PPublications) converter.json2Object(objJson,
					PPublications.class.getName());
			PPublications o = this.pPublicationsService.getPublications(obj
					.getId());
			if (o != null) {
				/*
				 * o.setNewest(obj.getNewest()!=0?obj.getNewest():o.getNewest());
				 * 这个字段已经废弃，变成SWF 阅读字段
				 */
				o.setSelected(obj.getSelected() != 0 ? obj.getSelected() : o
						.getSelected());
				o.setSpecial(obj.getSpecial() != 0 ? obj.getSpecial() : o
						.getSpecial());
				o.setOa(obj.getOa() != 0 ? obj.getOa() : o.getOa());
				o.setFree(obj.getFree() != 0 ? obj.getFree() : o.getFree());
				o.setHomepage(obj.getHomepage() != 0 ? obj.getHomepage() : o
						.getHomepage());
				this.pPublicationsService.updatePublications(o, obj.getId(),
						null);
			}
			ObjectUtil<PPublications> util = new ObjectUtil<PPublications>();
			obj = util.setNull(obj, new String[] { Set.class.getName(),
					List.class.getName() });
			if (obj.getPublications() != null) {
				util.setNull(
						obj.getPublications(),
						new String[] { Set.class.getName(),
								List.class.getName() });
			}
			if (obj.getVolume() != null) {
				util.setNull(
						obj.getVolume(),
						new String[] { Set.class.getName(),
								List.class.getName() });
			}
			if (obj.getIssue() != null) {
				util.setNull(obj.getIssue(), new String[] {
						Set.class.getName(), List.class.getName() });
			}
			if (obj.getPublisher() != null) {
				ObjectUtil<BPublisher> util2 = new ObjectUtil<BPublisher>();
				util2.setNull(
						obj.getPublisher(),
						new String[] { Set.class.getName(),
								List.class.getName() });
			}
			result = new ResultObject<PPublications>(1, obj, Lang.getLanguage(
					"Controller.Publications.Rest.Maintain.Succ", request
							.getSession().getAttribute("lang").toString()));// "出版商信息维护成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResultObject<PPublications>(2, Lang.getLanguage(
					"Controller.Publications.Rest.Maintain.Failed", request
							.getSession().getAttribute("lang").toString()));// "出版商信息维护失败！");
		}
		model.addAttribute("target", result);

	}

	@RequestMapping(value = "/updatejournals", method = RequestMethod.POST)
	public void updatejournals(HttpServletRequest request, Model model) {
		ResultObject<PPublications> result = null;
		try {
			String objJson = request.getParameter("obj").toString();
			Converter<PPublications> converter = new Converter<PPublications>();
			PPublications obj = (PPublications) converter.json2Object(objJson,
					PPublications.class.getName());
			PPublications o = this.pPublicationsService.getPublications(obj
					.getId());

			Map<String, Object> vals = new HashMap<String, Object>();
			vals.put("free", obj.getFree());
			vals.put("oa", obj.getOa());
			this.pPublicationsService.updatePublications1(vals,
					new String[] { obj.getId() });
			this.pPublicationsService.updateJournalp(vals,
					new String[] { obj.getId() });

			ObjectUtil<PPublications> util = new ObjectUtil<PPublications>();
			obj = util.setNull(obj, new String[] { Set.class.getName(),
					List.class.getName() });
			if (obj.getPublications() != null) {
				util.setNull(
						obj.getPublications(),
						new String[] { Set.class.getName(),
								List.class.getName() });
			}
			if (obj.getVolume() != null) {
				util.setNull(
						obj.getVolume(),
						new String[] { Set.class.getName(),
								List.class.getName() });
			}
			if (obj.getIssue() != null) {
				util.setNull(obj.getIssue(), new String[] {
						Set.class.getName(), List.class.getName() });
			}
			if (obj.getPublisher() != null) {
				ObjectUtil<BPublisher> util2 = new ObjectUtil<BPublisher>();
				util2.setNull(
						obj.getPublisher(),
						new String[] { Set.class.getName(),
								List.class.getName() });
			}
			result = new ResultObject<PPublications>(1, obj, Lang.getLanguage(
					"Controller.Publications.Rest.Maintain.Succ", request
							.getSession().getAttribute("lang").toString()));// "出版商信息维护成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResultObject<PPublications>(2, Lang.getLanguage(
					"Controller.Publications.Rest.Maintain.Failed", request
							.getSession().getAttribute("lang").toString()));// "出版商信息维护失败！");
		}
		model.addAttribute("target", result);

	}

	/**
	 * 数据接口
	 * 
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(HttpServletRequest request, Model model) {
		ResultObject<PPublications> result = null;
		try {
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); // 1-insert
																			// 2-update
			int operNum = Integer.valueOf(request.getParameter("operNum")
					.toString()); // 0-删除章节
									// 其他-不删除
			Converter<PPublications> converter = new Converter<PPublications>();
			PPublications obj = (PPublications) converter.json2Object(objJson,
					PPublications.class.getName());
			if (operNum <= 0) {// 章节 只新增或更新不删除
				// 删除子类
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("parentid", obj.getId());
				condition.put("BookPublications", obj.getId());
				// condition.put("check","false");
				/*
				 * List<PPrice>
				 * pList=this.pPublicationsService.getPriceList(condition);
				 * if(pList!=null&&pList.size()>0){ for(PPrice pobj:pList){
				 * this.pPublicationsService.deleteChaper(pobj.getId());//删除章节价格
				 * } }
				 */
				// this.pPublicationsService.deleteChildPublication(condition);//删除章节
				// 由存储过程来执行，在DCC操作

			}
			this.pPublicationsService.deletePcsRelation(obj.getId());// 删除原有分类关系
			if ("2".equals(operType)) {
				PPublications pp = this.pPublicationsService
						.getPublications(obj.getId());
				obj.setStartVolume(pp.getStartVolume());
				obj.setEndVolume(pp.getEndVolume());
				this.pPublicationsService.updatePublications(obj, obj.getId(),
						null);
			} else if ("1".equals(operType)) {
				// 先查询看商品是否存在
				try {
					this.pPublicationsService.uploads(obj);
				} catch (Exception pe) {
					throw new CcsException("Controller.Rest.Pub.Failed");
				}
			} else {
				// 删除
			}
			int status = 0;
			if (obj.getStatus() == 1) {
				// 减少上线统计
				if (obj.getCreateDate() != null
						&& !"".equals(obj.getCreateDate())) {
					this.statisticService.decreaseSOnsale(obj.getCreateDate());
				}
				// 下架删除索引
				if (obj.getType() != 5) {// 1-电子书 2-期刊 3-章节 4-文章 5-数据库 6-期刊卷 7
											// 期刊期
					try {
						this.publicationsIndexService.deleteIndexById(obj
								.getId());
					} catch (Exception se) {
						se.printStackTrace();
						throw new CcsException(
								"Controller.Solr.Index.Delete.Failed");
					}
				}
			} else {
				// 增加上线统计
				if (obj.getCreateDate() != null
						&& !"".equals(obj.getCreateDate())) {
					this.statisticService.increaseSOnsale(obj.getCreateDate());
				}
				/** 所有的索引创建 都在DCC中执行 **/
				if (obj.getPublications() == null || obj.getType() == 4) {
					// 创建索引
					if (obj.getType() == 1
							|| obj.getType() == 2
							|| obj.getType() == 3
							|| obj.getType() == 4
							|| (obj.getJournalType() != null
									&& obj.getJournalType() == 2 && obj
									.getType() == 7)) {// 1-电子书
						// 2-期刊
						// 3-章节
						// 4-文章
						// 5-数据库
						// 6-期刊卷
						// 7
						// 期刊期
						try {
							if (obj.getStatus() == 2) {// 只有上架的图书、期刊、章节、文章、数据库才能增加全文索引
								if (obj.getLocal() == 2) {// 只有本地资源才能增加全文索引
									String nr = "";
									String bpath = Param
											.getParam("pdf.directory.config")
											.get("dir").replace("-", ":");
									String filePath = bpath + obj.getPdf();
									File pdf = new File(filePath);
									if (pdf.exists()) {
										if (obj.getFileType() == null
												|| obj.getFileType() == 1) {// 获取PDF全文
											PDDocument doc = PDDocument
													.load(filePath);
											PDFTextStripper stripper = new PDFTextStripper();
											nr = stripper.getText(doc);
											obj.setFullText(nr);
											doc.close();
										} else if (obj.getFileType() == 2) {// 获取EPUB全文
											EPubHelper epubHelper = new EPubHelper(
													filePath);
											obj.setFullText(epubHelper
													.getText());
										}
									}
								}
							}
							status = this.publicationsIndexService
									.indexPublications(obj);
						} catch (Exception se) {
							throw new CcsException(
									"Controller.Solr.Index.Create.Failed");
						}
					}
				}
			}
			ObjectUtil<PPublications> util = new ObjectUtil<PPublications>();
			obj = util.setNull(obj, new String[] { Set.class.getName(),
					List.class.getName() });
			// if(obj.getPublications()!=null){
			// PPublications parent = obj.getPublications();
			// parent=util.setNull(parent, new
			// String[]{Set.class.getName(),List.class.getName()});
			// parent.setPriceList(null);
			// obj.setPublications(parent);
			// }
			if (obj.getPublications() != null) {
				// ObjectUtil<PPublications> util1=new
				// ObjectUtil<PPublications>();
				util.setNull(
						obj.getPublications(),
						new String[] { Set.class.getName(),
								List.class.getName() });
			}
			if (obj.getVolume() != null) {
				// ObjectUtil<PPublications> util2=new
				// ObjectUtil<PPublications>();
				util.setNull(
						obj.getVolume(),
						new String[] { Set.class.getName(),
								List.class.getName() });
			}
			if (obj.getIssue() != null) {
				// ObjectUtil<PPublications> util2=new
				// ObjectUtil<PPublications>();
				util.setNull(obj.getIssue(), new String[] {
						Set.class.getName(), List.class.getName() });
			}
			if (obj.getPublisher() != null) {
				ObjectUtil<BPublisher> util2 = new ObjectUtil<BPublisher>();
				util2.setNull(
						obj.getPublisher(),
						new String[] { Set.class.getName(),
								List.class.getName() });
			}
			if (obj.getFullText() != null) {
				obj.setFullText(null);
			}
			if (status == 0) {
				if (obj.getTitle() != null) {
					obj.setTitle(obj.getTitle().trim());
				}
				result = new ResultObject<PPublications>(1, obj,
						Lang.getLanguage(
								"Controller.Publications.Rest.Maintain.Succ",
								request.getSession().getAttribute("lang")
										.toString()));// "出版商信息维护成功！");
			} else {
				result = new ResultObject<PPublications>(2, Lang.getLanguage(
						"Controller.Publications.Rest.Maintain.Failed", request
								.getSession().getAttribute("lang").toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResultObject<PPublications>(2,
					(e instanceof CcsException) ? Lang.getLanguage(
							((CcsException) e).getPrompt(), request
									.getSession().getAttribute("lang")
									.toString()) : Lang.getLanguage(
							"Controller.Publications.Rest.Maintain.Failed",
							request.getSession().getAttribute("lang")
									.toString()));// "出版商信息维护失败！");
		}
		model.addAttribute("target", result);
	}

	/**
	 * 数据接口（仅更新Metadata）
	 * 
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/syncMetadata", method = RequestMethod.POST)
	public void syncMetadata(HttpServletRequest request, Model model) {
		ResultObject<PPublications> result = null;
		try {
			String objJson = request.getParameter("obj").toString();
			String index = request.getParameter("index");
			Boolean reIndex = index != null
					&& "yes".equals(index.toLowerCase()) ? true : false; // 是否重新生成索引
			Converter<PPublications> converter = new Converter<PPublications>();
			PPublications obj = (PPublications) converter.json2Object(objJson,
					PPublications.class.getName());
			int status = 0;

			PPublications pp = this.pPublicationsService.getPublications(obj
					.getId());

			if (obj.getTitle() != null || obj.getAuthor() != null
					|| obj.getChineseTitle() != null || obj.getRemark() != null
					|| obj.getPubDate() != null || obj.getHisbn() != null) {
				if (obj.getTitle() != null && !"".equals(obj.getTitle())) {
					pp.setTitle(obj.getTitle());
				} else if ("[CLEAR]".equals(obj.getTitle())) {
					pp.setTitle("");
				}
				if (obj.getAuthor() != null && !"".equals(obj.getAuthor())) {
					pp.setAuthor(obj.getAuthor());
				} else if ("[CLEAR]".equals(obj.getAuthor())) {
					pp.setAuthor("");
				}
				if (obj.getChineseTitle() != null
						&& !"".equals(obj.getChineseTitle())) {
					pp.setChineseTitle(obj.getChineseTitle());
				} else if ("[CLEAR]".equals(obj.getChineseTitle())) {
					pp.setChineseTitle("");
				}
				if (obj.getRemark() != null && !"".equals(obj.getRemark())) {
					pp.setRemark(obj.getRemark());
				} else if ("[CLEAR]".equals(obj.getRemark())) {
					pp.setRemark("");
				}
				if (obj.getPubDate() != null && !"".equals(obj.getPubDate())) {
					pp.setPubDate(obj.getPubDate());
				} else if ("[CLEAR]".equals(obj.getPubDate())) {
					pp.setPubDate("");
				}
				if (obj.getCover() != null && !"".equals(obj.getCover())) {
					pp.setCover(obj.getCover());
				} else if ("[CLEAR]".equals(obj.getCover())) {
					pp.setCover("");
				}
				if (obj.getLcurr() != null && !"".equals(obj.getLcurr())) {
					pp.setLcurr(obj.getLcurr());
				} else if ("[CLEAR]".equals(obj.getLcurr())) {
					pp.setLcurr("");
				}
				if (obj.getListPrice() != null) {
					pp.setListPrice(obj.getListPrice());
				}

				if (obj.getHisbn() != null && !"".equals(obj.getHisbn())) {
					pp.setHisbn(obj.getHisbn());
				} else if ("[CLEAR]".equals(obj.getHisbn())) {
					pp.setHisbn("");
				}
				if (obj.getSisbn() != null && !"".equals(obj.getSisbn())) {
					pp.setSisbn(obj.getSisbn());
				} else if ("[CLEAR]".equals(obj.getSisbn())) {
					pp.setSisbn("");
				}
				this.pPublicationsService.updatePublications(pp, pp.getId(),
						null);
			}
			if (reIndex) {
				try {
					if (pp.getStatus() == 2
							&& (pp.getType() == 1 || pp.getType() == 2 || pp
									.getType() == 4)) {// 只有上架的图书、期刊、章节、文章、数据库才能增加全文索引
						if (pp.getLocal() == 2) {// 只有本地资源才能增加全文索引
							String nr = "";
							String bpath = Param
									.getParam("pdf.directory.config")
									.get("dir").replace("-", ":");
							String filePath = bpath + obj.getPdf();
							File pdf = new File(filePath);
							if (pdf.exists()) {
								if (pp.getFileType() == null
										|| pp.getFileType() == 1) {// 获取PDF全文
									PDDocument doc = PDDocument.load(filePath);
									PDFTextStripper stripper = new PDFTextStripper();
									nr = stripper.getText(doc);
									pp.setDoi(nr);
									doc.close();
								} else if (obj.getFileType() == 2) {// 获取EPUB全文
									EPubHelper epubHelper = new EPubHelper(
											filePath);
									pp.setDoi(epubHelper.getText());
								}
							}
						}
					}
					status = this.publicationsIndexService
							.indexPublications(pp);
				} catch (Exception se) {
					throw new CcsException(
							"Controller.Solr.Index.Create.Failed");
				}
			}
			ObjectUtil<PPublications> util = new ObjectUtil<PPublications>();
			obj = util.setNull(obj, new String[] { Set.class.getName(),
					List.class.getName() });
			if (obj.getPublications() != null) {
				util.setNull(
						obj.getPublications(),
						new String[] { Set.class.getName(),
								List.class.getName() });
			}
			if (obj.getVolume() != null) {
				util.setNull(
						obj.getVolume(),
						new String[] { Set.class.getName(),
								List.class.getName() });
			}
			if (obj.getIssue() != null) {
				util.setNull(obj.getIssue(), new String[] {
						Set.class.getName(), List.class.getName() });
			}
			if (obj.getPublisher() != null) {
				ObjectUtil<BPublisher> util2 = new ObjectUtil<BPublisher>();
				util2.setNull(
						obj.getPublisher(),
						new String[] { Set.class.getName(),
								List.class.getName() });
			}
			if (status == 0) {
				result = new ResultObject<PPublications>(1, obj,
						Lang.getLanguage(
								"Controller.Publications.Rest.Maintain.Succ",
								request.getSession().getAttribute("lang")
										.toString()));// "出版商信息维护成功！");
			} else {
				result = new ResultObject<PPublications>(2, Lang.getLanguage(
						"Controller.Publications.Rest.Maintain.Failed", request
								.getSession().getAttribute("lang").toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResultObject<PPublications>(2,
					(e instanceof CcsException) ? Lang.getLanguage(
							((CcsException) e).getPrompt(), request
									.getSession().getAttribute("lang")
									.toString()) : Lang.getLanguage(
							"Controller.Publications.Rest.Maintain.Failed",
							request.getSession().getAttribute("lang")
									.toString()));// "出版商信息维护失败！");
		}
		model.addAttribute("target", result);
	}

	/**
	 * 按照出版物A-Z
	 * 
	 * @param subCode
	 *            用来查找子分类、all为全部父分类
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/list")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		String forwardString = "publications/detailList";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = request.getSession() == null ? null : (CUser) request
					.getSession().getAttribute("mainUser");
			if (form.getLcense() == null) {
				form.setLcense(request.getSession().getAttribute("selectType") == null ? ""
						: request.getSession().getAttribute("selectType")
								.toString());
			}
			request.getSession().setAttribute("selectType",
					form.getLcense() == null ? "" : form.getLcense());
			form.setUrl(request.getRequestURL().toString());
			model.put("pCode", form.getpCode());
			BSubject subject = this.bSubjectService.getSubject(form
					.getSubParentId());
			// 查询子分类
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("publisherid", form.getPublisherId());
			// condition.put("mainType","0");
			// condition.put("subjectId", form.getSubParentId());

			condition.put("subjectCode",
					subject == null ? null : subject.getTreeCode());
			condition.put("type", form.getPubType());
			condition.put("pubYear", form.getPubDate());
			// condition.put("order",
			// form.getSearchOrder()==null?"":form.getSearchOrder());
			if (form.getLcense() == null || "".equals(form.getLcense())) {
				condition.put("status", 2);
			} else {
				condition.put("check", "false");
			}
			condition.put("isLicense",
					form.getLcense() == null ? "" : form.getLcense());// 是否订阅
			condition.put("userId", user == null ? "" : user.getId());// 用户ID
			condition.put("ip", IpUtil.getIp(request));// 用户ID

			condition.put("typeArr", new Integer[] { 1, 2 });// 只查询期刊和图书
			condition.put("level", 2);
			condition.put("licenseStatus", 1);
			String ins_Id = null;
			if (request.getSession().getAttribute("institution") != null) {
				ins_Id = ((BInstitution) request.getSession().getAttribute(
						"institution")).getId();
			}
			// condition.put("institutionId", ins_Id);
			List<PPublications> pubList = null;
			long starttime = System.currentTimeMillis();
			if (form.getLcense() == null || "".equals(form.getLcense())) {
				pubList = this.pPublicationsService.getPubPageList(condition,
						"", form.getPageCount(), form.getCurpage(), user,
						IpUtil.getIp(request).toString());
			} else {
				// 由于要查询出OA和免费
				condition.put("oafreeUid", Param.getParam("OAFree.uid.config")
						.get("uid"));
				pubList = this.pPublicationsService.getPubSubscriptionPageList(
						condition, "", form.getPageCount(), form.getCurpage(),
						user, IpUtil.getIp(request).toString());
			}
			long endtime = System.currentTimeMillis();
			// System.out.println("XXXXXXXXXX Execute Time:" + (endtime -
			// starttime));
			// 循环list 查询单个产品下边的价格列表
			if (pubList != null) {
				for (int i = 0; i < pubList.size(); i++) {
					if (user != null) {
						Map<String, Object> con = new HashMap<String, Object>();
						con.put("publicationsid", pubList.get(i).getId());
						con.put("status", 2);
						con.put("userTypeId",
								user.getUserType().getId() == null ? "" : user
										.getUserType().getId());
						List<PPrice> price = this.pPublicationsService
								.getPriceList(con);
						int isFreeUser = request.getSession().getAttribute(
								"isFreeUser") == null ? 0 : (Integer) request
								.getSession().getAttribute("isFreeUser");
						if (isFreeUser != 1) {
							for (int j = 0; j < price.size(); j++) {
								PPrice pr = price.get(j);
								double endPrice = MathHelper.round(MathHelper
										.mul(pr.getPrice(), 1.13d));
								price.get(j).setPrice(endPrice);
							}
						}
						pubList.get(i).setPriceList(price);
					}
				}
			}
			/**/
			form.setCurCount(pubList != null ? pubList.size() : 0);
			if (form.getLcense() == null || "".equals(form.getLcense())) {
				form.setCount(this.pPublicationsService
						.getPubCountO1(condition));
			} else {
				form.setCount(this.pPublicationsService
						.getPubSubscriptionCount(condition));
			}
			form.setSearchOrder(form.getSearchOrder());

			model.put("pubList", pubList);

		} catch (Exception e) {
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 列表页 产品信息下载
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/downList")
	public ModelAndView downList(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		String forwardString = "";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = request.getSession() == null ? null : (CUser) request
					.getSession().getAttribute("mainUser");
			int pageCount = form.getPageCount();
			if (user != null) {
				if (user.getLevel() == 5)
					pageCount = 20;
				if (user.getLevel() == 2)
					pageCount = 50;
			}
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("publisherid", form.getPublisherId());
			condition.put("subjectId", form.getSubParentId());
			condition.put("type", form.getPubType());
			condition.put("pubYear", form.getPubDate());
			if (form.getLcense() == null || "".equals(form.getLcense())) {
				condition.put("status", 2);
			} else {
				condition.put("check", "false");
			}
			condition.put("isLicense",
					form.getLcense() == null ? "" : form.getLcense());// 是否订阅
			condition.put("userId", user == null ? "" : user.getId());// 用户ID
			condition.put("ip", IpUtil.getIp(request));// 用户ID
			condition.put("typeArr", new Integer[] { 1, 2 });// 只查询期刊和图书
			condition.put("level", 2);
			condition.put("licenseStatus", 1);
			String ins_Id = null;
			if (request.getSession().getAttribute("institution") != null) {
				ins_Id = ((BInstitution) request.getSession().getAttribute(
						"institution")).getId();
			}
			condition.put("institutionId", ins_Id);
			List<PPublications> pubList = null;
			long starttime = System.currentTimeMillis();
			if (form.getLcense() == null || "".equals(form.getLcense())) {
				pubList = this.pPublicationsService
						.getPubPageList(
								condition,
								form.getSearchOrder() == null ? ""
										: (" order by a.title " + form
												.getSearchOrder()), pageCount,
								form.getCurpage(), user, IpUtil.getIp(request)
										.toString());
			} else {
				// 由于要查询出OA和免费
				condition.put("oafreeUid", Param.getParam("OAFree.uid.config")
						.get("uid"));
				pubList = this.pPublicationsService
						.getPubSubscriptionPageList(
								condition,
								form.getSearchOrder() == null ? ""
										: (" order by d.title " + form
												.getSearchOrder()), pageCount,
								form.getCurpage(), user, IpUtil.getIp(request)
										.toString());
			}
			long endtime = System.currentTimeMillis();

			// ***************************生成Excel******************************//
			// 输出的excel文件工作表名
			String worksheet = "Product_List";
			// excel工作表的标题
			StringBuffer sb = new StringBuffer();
			sb.append("Title;");
			sb.append("ISBN;");
			sb.append("ISSN;");
			sb.append("code;");
			sb.append("Type;");
			sb.append("Author;");
			sb.append("Publisher;");
			sb.append("Remark;");

			String title[] = sb.toString().split(";");
			WritableWorkbook workbook;
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ worksheet + ".xls");// 设定输出文件头
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型

			workbook = Workbook.createWorkbook(os);

			WritableSheet sheet = workbook.createSheet(worksheet, 0);

			for (int i = 0; i < title.length; i++) {
				// Label(列号,行号 ,内容 )
				sheet.addCell(new Label(i, 0, title[i]));
			}
			int row = 1;
			// 循环list 查询单个产品下边的价格列表
			// 1-电子书 2-期刊 3-章节 4-文章 5-数据库 6-期刊卷 7 期刊期 99产品包(用来在订单明细中区分)
			if (pubList != null) {
				for (PPublications pub : pubList) {
					sheet.addCell(new Label(0, row, pub.getTitle()));
					if (pub.getType() == 1 || pub.getType() == 3) {
						sheet.addCell(new Label(1, row, pub.getCode()));
					} else if (pub.getType() == 2 || pub.getType() == 4
							|| pub.getType() == 6 || pub.getType() == 7) {
						sheet.addCell(new Label(2, row, pub.getCode()));
					} else if (pub.getType() == 5) {
						sheet.addCell(new Label(3, row, pub.getCode()));
					}
					if (pub.getType() == 1) {
						sheet.addCell(new Label(4, row, Lang.getLanguage(
								"Pages.User.Favorites.Table.Label.type1",
								request.getSession().getAttribute("lang")
										.toString())));
					} else if (pub.getType() == 2) {
						sheet.addCell(new Label(4, row, Lang.getLanguage(
								"Pages.User.Favorites.Table.Label.type2",
								request.getSession().getAttribute("lang")
										.toString())));
					} else if (pub.getType() == 3) {
						sheet.addCell(new Label(4, row, Lang.getLanguage(
								"Pages.User.Favorites.Table.Label.type3",
								request.getSession().getAttribute("lang")
										.toString())));
					} else if (pub.getType() == 4) {
						sheet.addCell(new Label(4, row, Lang.getLanguage(
								"Pages.User.Favorites.Table.Label.type4",
								request.getSession().getAttribute("lang")
										.toString())));
					} else if (pub.getType() == 5) {
						sheet.addCell(new Label(4, row, Lang.getLanguage(
								"Pages.User.Favorites.Table.Label.type5",
								request.getSession().getAttribute("lang")
										.toString())));
					} else if (pub.getType() == 6) {
						sheet.addCell(new Label(4, row, Lang.getLanguage(
								"Pages.User.Favorites.Table.Label.type6",
								request.getSession().getAttribute("lang")
										.toString())));
					} else if (pub.getType() == 7) {
						sheet.addCell(new Label(4, row, Lang.getLanguage(
								"Pages.User.Favorites.Table.Label.type7",
								request.getSession().getAttribute("lang")
										.toString())));
					}
					sheet.addCell(new Label(5, row, pub.getAuthor()));
					sheet.addCell(new Label(6, row, pub.getPublisher()
							.getName()));
					sheet.addCell(new Label(7, row, pub.getRemark()));
					row++;
				}
			}
			workbook.write();
			workbook.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
			model.put("form", form);
			return new ModelAndView(forwardString, model);
		}
		return null;
	}

	/**
	 * 按分类法统计forJquery异步加载
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/subjectStat")
	public ModelAndView subjectStat(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		String forwardString = "publications/subjectStat";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String ins_Id = null;
			if (request.getSession().getAttribute("institution") != null) {
				ins_Id = ((BInstitution) request.getSession().getAttribute(
						"institution")).getId();
			}
			CUser user = request.getSession() == null ? null : (CUser) request
					.getSession().getAttribute("mainUser");
			form.setUrl(request.getRequestURL().toString());
			model.put("pCode", form.getpCode());
			// 分类法统计
			Map<String, Object> subCondition = new HashMap<String, Object>();
			subCondition.put("publisherId", form.getPublisherId());
			if (form.getCode() == null || "".equals(form.getCode())) {
				// subCondition.put("parentId",form.getSubParentId());
				subCondition.put("length", 6);
			} else {
				subCondition.put("length", 9);
			}
			subCondition.put("subjectId", form.getSubParentId());
			subCondition.put("ptype", form.getType());
			subCondition.put("pubYear", form.getPubYear());
			subCondition.put("order",
					form.getOrder() == null ? "" : form.getOrder());
			subCondition.put("isLicense", form.getIsLicense());// 是否订阅
			subCondition.put("userId", user == null ? "" : user.getId());// 用户ID
			subCondition.put("ip", IpUtil.getIp(request));// 用户ID
			subCondition.put("mainType", "0");
			subCondition.put("publisherId", form.getPublisherId());
			subCondition.put("institutionId", ins_Id);
			if (form.getIsLicense() == null || "".equals(form.getIsLicense())) {
				subCondition.put("status", 2);
			} else {
				subCondition.put("check", "false");
				// 由于要查询出OA和免费
				// subCondition.put("oafreeUid",
				// Param.getParam("OAFree.uid.config").get("uid"));
			}
			List<BSubject> subStatistic = this.bSubjectService
					.getStatisticSubAllList(subCondition, "");
			model.put("statistic", subStatistic);
			model.put("publisherid", form.getPublisherId());
			model.put("subjectId", form.getSubParentId());
			model.put("ptype", form.getType());
			model.put("pubYear", form.getPubYear());
			model.put("order", form.getOrder());
			model.put("code", form.getCode());
			model.put("length", subCondition.get("length"));
			model.put("r_", new Date().getTime());
			model.put("isLicense", form.getIsLicense());
			model.put("parentTaxonomy", form.getParentTaxonomy());
			model.put("parentTaxonomyEn", form.getParentTaxonomyEn());
		} catch (Exception e) {
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 按类型统计forJquery异步加载
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/typeStat")
	public ModelAndView typeStat(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		String forwardString = "publications/typeStat";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = request.getSession() == null ? null : (CUser) request
					.getSession().getAttribute("mainUser");
			form.setUrl(request.getRequestURL().toString());
			String ins_Id = null;
			if (request.getSession().getAttribute("institution") != null) {
				ins_Id = ((BInstitution) request.getSession().getAttribute(
						"institution")).getId();
			}
			model.put("pCode", form.getpCode());
			// 类型统计
			Map<String, Object> typeCondition = new HashMap<String, Object>();
			typeCondition.put("publisherid", form.getPublisherId());
			// typeCondition.put("mainType","0");
			typeCondition.put("subjectId", form.getSubParentId());
			typeCondition.put("type", form.getType());
			typeCondition.put("pubYear", form.getPubYear());
			typeCondition.put("order",
					form.getOrder() == null ? "" : form.getOrder());
			typeCondition.put("isLicense", form.getIsLicense());// 是否订阅
			typeCondition.put("userId", user == null ? "" : user.getId());// 用户ID
			typeCondition.put("ip", IpUtil.getIp(request));// 用户ID
			typeCondition.put("typeArr", new Integer[] { 1, 2 });// 只查询期刊和图书
			typeCondition.put("level", 2);
			typeCondition.put("licenseStatus", 1);
			// typeCondition.put("institutionId", ins_Id);
			if (form.getIsLicense() == null || "".equals(form.getIsLicense())) {
				typeCondition.put("status", 2);
			} else {
				typeCondition.put("check", "false");
			}
			List<PPublications> typeStatistic = null;
			if (form.getIsLicense() == null || "".equals(form.getIsLicense())) {
				typeStatistic = this.pPublicationsService
						.getTypeStatistic(typeCondition);
			} else {
				// 由于要查询出OA和免费
				typeCondition.put("oafreeUid",
						Param.getParam("OAFree.uid.config").get("uid"));
				typeStatistic = this.pPublicationsService
						.getSubscriptionTypeStatistic(typeCondition);
			}
			model.put("typeStatistic", typeStatistic);

			model.put("publisherid", form.getPublisherId());
			model.put("subjectId", form.getSubParentId());
			model.put("ptype", form.getType());
			model.put("pubYear", form.getPubYear());
			model.put("order", form.getOrder());
			model.put("r_", new Date().getTime());
			model.put("isLicense", form.getIsLicense());
		} catch (Exception e) {
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 按年度统计forJquery异步加载
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/yearStat")
	public ModelAndView yearStat(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		String forwardString = "publications/yearStat";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = request.getSession() == null ? null : (CUser) request
					.getSession().getAttribute("mainUser");
			form.setUrl(request.getRequestURL().toString());
			String ins_Id = null;
			if (request.getSession().getAttribute("institution") != null) {
				ins_Id = ((BInstitution) request.getSession().getAttribute(
						"institution")).getId();
			}
			model.put("pCode", form.getpCode());
			// 年份统计
			Map<String, Object> yearCondition = new HashMap<String, Object>();
			yearCondition.put("publisherid", form.getPublisherId());
			yearCondition.put("mainType", "0");
			yearCondition.put("subjectId", form.getSubParentId());
			yearCondition.put("type", form.getType());
			yearCondition.put("pubYear", form.getPubYear());
			yearCondition.put("order",
					form.getOrder() == null ? "" : form.getOrder());
			yearCondition.put("isLicense", form.getIsLicense());// 是否订阅
			yearCondition.put("userId", user == null ? "" : user.getId());// 用户ID
			yearCondition.put("ip", IpUtil.getIp(request));// 用户ID
			yearCondition.put("typeArr", new Integer[] { 1, 2 });// 只查询期刊和图书
			yearCondition.put("level", 2);
			yearCondition.put("licenseStatus", 1);
			yearCondition.put("institutionId", ins_Id);
			if (form.getIsLicense() == null || "".equals(form.getIsLicense())) {
				yearCondition.put("status", 2);
			} else {
				yearCondition.put("check", "false");
			}
			List<PPublications> yearStatistic = null;
			if (form.getIsLicense() == null || "".equals(form.getIsLicense())) {
				yearStatistic = this.pPublicationsService
						.getYearStatistic(yearCondition);
			} else {
				// 由于要查询出OA和免费
				yearCondition.put("oafreeUid",
						Param.getParam("OAFree.uid.config").get("uid"));
				yearStatistic = this.pPublicationsService
						.getSubscriptionYearStatistic(yearCondition);
			}
			model.put("yearStatistic", yearStatistic);
			model.put("publisherid", form.getPublisherId());
			model.put("subjectId", form.getSubParentId());
			model.put("ptype", form.getType());
			model.put("pubYear", form.getPubYear());
			model.put("order", form.getOrder());
			model.put("r_", new Date().getTime());
			model.put("isLicense", form.getIsLicense());
		} catch (Exception e) {
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 按出版商统计forJquery异步加载
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/publisherStat")
	public ModelAndView publisherStat(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		String forwardString = "publications/publisherStat";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = request.getSession() == null ? null : (CUser) request
					.getSession().getAttribute("mainUser");
			form.setUrl(request.getRequestURL().toString());
			String ins_Id = null;
			if (request.getSession().getAttribute("institution") != null) {
				ins_Id = ((BInstitution) request.getSession().getAttribute(
						"institution")).getId();
			}
			model.put("pCode", form.getpCode());
			// 出版商统计
			Map<String, Object> pubCondition = new HashMap<String, Object>();
			pubCondition.put("publisherid", form.getPublisherId());
			// pubCondition.put("mainType","0");
			pubCondition.put("subjectId", form.getSubParentId());
			pubCondition.put("type", form.getType());
			pubCondition.put("pubYear", form.getPubYear());
			pubCondition.put("order",
					form.getOrder() == null ? "" : form.getOrder());
			pubCondition.put("isLicense", form.getIsLicense());// 是否订阅
			pubCondition.put("userId", user == null ? "" : user.getId());// 用户ID
			pubCondition.put("ip", IpUtil.getIp(request));// 用户ID
			pubCondition.put("typeArr", new Integer[] { 1, 2 });// 只查询期刊和图书
			pubCondition.put("level", 2);
			pubCondition.put("licenseStatus", 1);
			pubCondition.put("institutionId", ins_Id);
			if (form.getIsLicense() == null || "".equals(form.getIsLicense())) {
				pubCondition.put("status", 2);
			} else {
				pubCondition.put("check", "false");
			}
			List<PPublications> pubStatistic = null;
			if (form.getIsLicense() == null || "".equals(form.getIsLicense())) {
				pubStatistic = this.pPublicationsService
						.getPublisherStatistic(pubCondition);
			} else {
				// 由于要查询出OA和免费
				pubCondition.put("oafreeUid",
						Param.getParam("OAFree.uid.config").get("uid"));
				pubStatistic = this.pPublicationsService
						.getSubscriptionPublisherStatistic(pubCondition);
			}
			model.put("pubStatistic", pubStatistic);
			model.put("publisherid", form.getPublisherId());
			model.put("subjectId", form.getSubParentId());
			model.put("ptype", form.getType());
			model.put("pubYear", form.getPubYear());
			model.put("order", form.getOrder());
			model.put("r_", new Date().getTime());
			model.put("isLicense", form.getIsLicense());
		} catch (Exception e) {
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 保留两位小数
	 * 
	 * @param value
	 * @return
	 */
	private Double round(Double value) {
		BigDecimal bd = new BigDecimal(value);
		//bd = bd.setScale(2, BigDecimal.ROUND_CEILING); // 3.1415 >> 3.15
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP); // 3.1415 >> 3.14
		return bd.doubleValue();
	}

	/**
	 * 查找在线阅读时需要的url
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/getUrl")
	public void getUrl(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			PPublicationsForm form) throws Exception {
		String result = "error;"
				+ Lang.getLanguage("Controller.Publications.UnFind", request
						.getSession().getAttribute("lang").toString());
		try {
			int falg = 0;
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
			PPublications pub = this.pPublicationsService.getPublications(form
					.getId());

			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("publicationId", pub.getId());
			condition.put("ip", IpUtil.getLongIp(IpUtil.getIp(request)));
			condition.put("status", 1);
			List<LLicenseIp> list = this.pPublicationsService.getLicenseIpList(
					condition, "");
			// 章节本身没有license的话，继续查一下其父级书是否有license
			// 为了能优先使用其本身的license避免占用书的并发数，这里分两次查询
			if ((list == null || list.isEmpty()) && pub.getType() == 3) {
				condition.clear();
				condition.put("pubParentId", pub.getPublications().getId());
				condition.put("ip", IpUtil.getLongIp(IpUtil.getIp(request)));
				condition.put("status", 1);
				list = this.pPublicationsService
						.getLicenseIpList(condition, "");
			}
			if (list != null && list.size() > 0) {
				LLicense license = list.get(0).getLicense();
				if (license.getComplicating() != null
						&& license.getComplicating() != 0) {// 即有并发限制
					// 释放占用的并发
					String beatInterval = Param.getParam("system.config")
							.get("beatInterval").trim();
					this.pPublicationsService.deleteDead(Integer
							.valueOf(beatInterval) + 10);
					Map<String, Object> uCon = new HashMap<String, Object>();
					uCon.put("licenseId", license.getId());
					uCon.put("sessionId", session.getId());
					uCon.put("endTimeNull", null);
					boolean b = this.pPublicationsService
							.isExistComplicating(uCon);
					if (!b) {
						uCon = new HashMap<String, Object>();
						uCon.put("licenseId", license.getId());
						uCon.put("endTimeNull", null);
						int count = this.pPublicationsService
								.getComplicatingCount(uCon);
						if (count >= license.getComplicating()) {
							falg = 1;
							result = "error;"
									+ Lang.getLanguage(
											"Controller.Publications.complicating.error",
											request.getSession()
													.getAttribute("lang")
													.toString());// 超出最大并发数!";
						} else {
							// 记录下新人
							LComplicating comp = new LComplicating();
							comp.setLicense(license);
							comp.setSessionId(session.getId());
							comp.setUser(license.getUser());
							comp.setMacAddr(md5Str);
							comp.setPubCode(license.getPublications().getCode());
							comp.setCreateOn(new Date());
							comp.setUpdateTime(comp.getCreateOn());// 添加更新时间
							comp.setEndTime(null);// 结束时间
							this.pPublicationsService.insertComplicating(comp);
							Map<String, Object> compMap = session
									.getAttribute("compMap") != null ? (Map<String, Object>) session
									.getAttribute("compMap")
									: new HashMap<String, Object>();
							compMap.put(license.getId(), license.getId());
							session.setAttribute("compMap", compMap);
						}
						// }
					}
				}
				if (falg == 0) {
					if (list.get(0).getLicense().getReadUrl()
							.indexOf("/pages/view/form/view") > -1) {
						// 查询机构信息
						Map<String, Object> con = new HashMap<String, Object>();
						con.put("ip", IpUtil.getLongIp(IpUtil.getIp(request)));
						List<BIpRange> li = this.configureService
								.getIpRangeList(con, "");
						if (li != null && li.size() > 0) {
							result = "success;"
									+ list.get(0).getLicense().getReadUrl()
									+ "&licenseId="
									+ list.get(0).getLicense().getId()
									+ "&institutionId="
									+ li.get(0).getInstitution().getId();
						} else {
							result = "success;"
									+ list.get(0).getLicense().getReadUrl()
									+ "&licenseId="
									+ list.get(0).getLicense().getId();
						}
						if (pub.getType() == 4) {// 4-文章
							result += "&articleId=" + form.getId();
						}
					} else {
						String url = list.get(0).getLicense().getReadUrl();
						if (url.indexOf("dawson") > -1) {
							String code = list.get(0).getLicense()
									.getPublications().getCode();
							String lang = request.getSession()
									.getAttribute("lang").toString().split("_")[1]
									.toLowerCase();
							url = dawsonEncryption.getEncryptOnlineReadUrl(
									code, lang);
						}
						String code = new String(Base64.encode(url.getBytes()));
						result = "success;" + code;
					}
				}
			} else {
				CUser user = request.getSession().getAttribute("mainUser") == null ? null
						: (CUser) request.getSession().getAttribute("mainUser");
				if (user != null) {
					Map<String, Object> map2 = new HashMap<String, Object>();
					map2.put("pubId", pub.getId());
					map2.put("status", 1);
					map2.put("userid", user.getId());
					map2.put("isTrail", "0");
					List<LLicense> list2 = this.pPublicationsService
							.getLicenseList(map2, "");
					// 章节本身没有license的话，继续查一下其父级书是否有license
					// 为了能优先使用其本身的license避免占用书的并发数，这里分两次查询
					if ((list == null || list.isEmpty()) && pub.getType() == 3) {
						map2.clear();
						map2.put("pubParentId", pub.getPublications().getId());
						map2.put("status", 1);
						map2.put("userid", user.getId());
						map2.put("isTrail", "0");
						list2 = this.pPublicationsService.getLicenseList(map2,
								"");
					}
					if (list2 != null && list2.size() > 0) {
						LLicense license = list2.get(0);
						if (license.getComplicating() != null
								&& license.getComplicating() != 0) {// 即有并发限制
							Map<String, Object> uCon = new HashMap<String, Object>();
							uCon.put("licenseId", license.getId());
							uCon.put("sessionId", session.getId());
							uCon.put("endTimeNull", null);
							// uCon.put("publicationId", form.getId());
							boolean b = this.pPublicationsService
									.isExistComplicating(uCon);
							boolean b2 = true;
							if (!b) {
								// 根据Cookie查看是否存在
								Map<String, Object> uCon2 = new HashMap<String, Object>();
								uCon2.put("licenseId", license.getId());
								uCon2.put("macId", md5Str);
								uCon2.put("endTimeNull", null);
								// uCon2.put("publicationId", form.getId());
								List<LComplicating> listComp = this.pPublicationsService
										.getComplicatingList(uCon2,
												" order by a.createOn ");
								if (listComp != null && listComp.size() > 0) {
									LComplicating lcomp = listComp.get(0);
									if (DateUtil.timeDiff(lcomp.getCreateOn(),
											new Date()) > 30) {// 大于30分钟
										// 先释放掉，相当于session过期
										Map<String, Object> con3 = new HashMap<String, Object>();
										con3.put("macId", md5Str);
										this.pPublicationsService
												.deleteComplicatingByCondition(con3);
									} else {
										b2 = false;
									}
								}
								if (b2) {
									// 查看并发数量
									uCon = new HashMap<String, Object>();
									uCon.put("licenseId", license.getId());
									int count = this.pPublicationsService
											.getComplicatingCount(uCon);
									if (count >= license.getComplicating()) {
										falg = 1;
										result = "error;"
												+ Lang.getLanguage(
														"Controller.Publications.complicating.error",
														request.getSession()
																.getAttribute(
																		"lang")
																.toString());// 超出最大并发数!";
									} else {
										// 记录下新人
										LComplicating comp = new LComplicating();
										comp.setLicense(license);
										comp.setSessionId(session.getId());
										comp.setUser(license.getUser());
										comp.setMacAddr(md5Str);
										comp.setPubCode(license
												.getPublications().getCode());
										comp.setCreateOn(new Date());
										comp.setUpdateTime(comp.getCreateOn());// 添加更新时间
										comp.setEndTime(null);// 结束时间
										this.pPublicationsService
												.insertComplicating(comp);
										Map<String, Object> compMap = session
												.getAttribute("compMap") != null ? (Map<String, Object>) session
												.getAttribute("compMap")
												: new HashMap<String, Object>();
										compMap.put(license.getId(),
												license.getId());
										session.setAttribute("compMap", compMap);
									}
								}
							}
						}
						if (falg == 0) {
							if (license.getReadUrl().indexOf(
									"/pages/view/form/view") > -1) {
								// 查询机构信息
								// license.getReadUrl().indexOf("/pages/view/form/view")
								// > -1
								String institutionId = user.getInstitution() == null ? null
										: user.getInstitution().getId();
								if (institutionId != null
										&& !"".equalsIgnoreCase(institutionId)) {
									result = "success;"
											+ list2.get(0).getReadUrl()
											+ "&licenseId="
											+ list2.get(0).getId()
											+ "&institutionId=" + institutionId;
								} else {
									result = "success;"
											+ list2.get(0).getReadUrl()
											+ "&licenseId="
											+ list2.get(0).getId();
								}
								if (pub.getType() == 4) {// 4-文章
									result += "&articleId=" + form.getId();
								}
							} else {
								String url = list2.get(0).getReadUrl();
								if (url.indexOf("dawson") > -1) {
									String code = list2.get(0)
											.getPublications().getCode();
									String lang = request.getSession()
											.getAttribute("lang").toString()
											.split("_")[1].toLowerCase();
									url = dawsonEncryption
											.getEncryptOnlineReadUrl(code, lang);
								}
								String code = new String(Base64.encode(url
										.getBytes()));
								result = "success;" + code;
							}

						}
					}
				}
			}
			if (form.getNextPage() != null && form.getNextPage() > 0) {
				result += "&nextPage="
						+ request.getParameter("nextPage").toString();
			}
			// 需要屏蔽
			/*
			 * result = result.replaceFirst("http://.+?\\.com(/EPublishing)?",
			 * "");
			 */
		} catch (Exception e) {
			e.printStackTrace();
			result = "error;"
					+ ((e instanceof CcsException) ? Lang.getLanguage(
							((CcsException) e).getPrompt(), request
									.getSession().getAttribute("lang")
									.toString()) : e.getMessage());
		}
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	/**
	 * 分页
	 * */
	@RequestMapping(value = "/form/split")
	public ModelAndView split(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		String forwardString = "publications/detailList";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String basePath = request.getSession().getServletContext()
					.getRealPath("");
			this.pPublicationsService.generatePages(form.getId(), basePath);
		} catch (Exception e) {
			// e.printStackTrace();
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 该方法用于计算出版物被购买的次数并回写至publications.buyTimes中，运行一次即可。
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/calctimes")
	public void CalcBuyTimes(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			if (request.getSession().getAttribute("mainUser") != null) {
				CUser user = (CUser) request.getSession().getAttribute(
						"mainUser");
				if (user.getLevel() == 4) {
					List<LLicense> list = this.pPublicationsService
							.getLicenseStat();
					if (list != null && list.size() > 0) {
						for (LLicense pub : list) {
							if (pub.getType() != null && pub.getType() > 0
									&& pub.getId() != null
									&& !"".equals(pub.getId())) {
								PPublications p = this.pPublicationsService
										.getPublications(pub.getId());
								p.setBuyTimes(pub.getType());
								this.pPublicationsService.updatePublications(p,
										p.getId(), null);
								System.out.print("id:" + p.getId() + ",times:"
										+ p.getBuyTimes());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 释放并发占用
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/release")
	public void release(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			PPublicationsForm form) throws Exception {
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
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("publicationId", form.getId());
			condition.put("ip", IpUtil.getLongIp(IpUtil.getIp(request)));
			condition.put("status", 1);
			List<LLicenseIp> list = this.pPublicationsService.getLicenseIpList(
					condition, "");
			if (list != null && list.size() > 0) {
				LLicense license = list.get(0).getLicense();
				if (license.getComplicating() != null
						&& license.getComplicating() != 0) {// 即有并发限制
					Map<String, Object> uCon = new HashMap<String, Object>();
					uCon.put("licenseId", license.getId());
					uCon.put("macId", md5Str);
					this.pPublicationsService
							.deleteComplicatingByCondition(uCon);
				}
			} else {
				CUser user = request.getSession().getAttribute("mainUser") == null ? null
						: (CUser) request.getSession().getAttribute("mainUser");
				if (user != null) {
					Map<String, Object> map2 = new HashMap<String, Object>();
					map2.put("pubId", form.getId());
					map2.put("status", 1);
					map2.put("userid", user.getId());
					List<LLicense> list2 = this.pPublicationsService
							.getLicenseList(map2, "");
					if (list2 != null && list2.size() > 0) {
						LLicense license = list2.get(0);
						if (license.getComplicating() != null
								&& license.getComplicating() != 0) {// 即有并发限制
							Map<String, Object> uCon = new HashMap<String, Object>();
							uCon.put("licenseId", license.getId());
							uCon.put("macId", md5Str);
							this.pPublicationsService
									.deleteComplicatingByCondition(uCon);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * AJAX查询图书列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/queryPubs")
	public void queryPubs(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		try {
			String n = request.getParameter("n");
			Integer number = (n == null || "".equals(n)) ? 0 : Integer
					.valueOf(n);
			form.getCondition().put("issueList", "true");
			form.getCondition().put("parentid", form.getParentId());
			form.getCondition().put("pYear", form.getPubYear());
			form.getCondition().put("type", form.getType());
			List<PPublications> list = this.pPublicationsService
					.getSimpleList(
							form.getCondition(),
							" group by a.id, a.volumeCode,a.issueCode,a.month,a.year,a.code,a.pubDate,a.journalOrder order by a.journalOrder desc ,a.pubDate,a.year ,a.month ,a.code ",
							number);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("pubtype", 7);
			JSONObject json = JSONObject.fromObject(map);
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
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(
					"message",
					(e instanceof CcsException) ? Lang.getLanguage(
							((CcsException) e).getPrompt(), request
									.getSession().getAttribute("lang")
									.toString()) : e.getMessage());
		}
	}

	/**
	 * AJAX查询卷期列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/issue")
	public ModelAndView issue(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		String forwardString = "publications/issue";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
//			int curpage =Integer.parseInt(request.getParameter("page"));
//			form.setCurpage(curpage);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("pubYear", form.getPubYear());
			condition.put("isParentid", form.getParentId());
			condition.put("type", form.getType());
			form.setPageCount(6);
			form.setUrl(request.getRequestURL().toString());
			form.setCount(this.pPublicationsService.getPubCount(condition));
//			List<PPublications> lists = this.pPublicationsService
//					.getSimpleList(condition, "order by a.month desc ", 0);getSimplePagingList
			List<PPublications> lists = this.pPublicationsService
					.getSimplePagingList(condition, "order by a.month desc ", form.getPageCount(),form.getCurpage());
			CUser user = request.getSession() == null ? null : (CUser) request
					.getSession().getAttribute("mainUser");
			List<PPublications> list = new ArrayList<PPublications>();
			Map<String ,Object> cond = new HashMap<String,Object>();
			
			cond.put("status", 2);
			if(user!=null&&user.getUserType()!=null){
				cond.put("userTypeId",
						user.getUserType().getId() == null ? "" : user
								.getUserType().getId());
			}
			
			List<PPrice> pricelist=null;
			for (int i = 0; i < lists.size(); i++) {
				cond.put("publicationsid", lists.get(i).getId());
				pricelist = this.pPublicationsService
						.getPriceList(cond);
				// PPublications journal =
				// this.pPublicationsService.getPublications(list.get(i).getId());
				Map<String, Object> cond2 = new HashMap<String, Object>();
				cond2.put("isLicense2", "true");
				cond2.put("orOnSale", "true");
				cond2.put("check", "false");
				cond2.put("ip", IpUtil.getIp(request));
				if (user != null) {
					cond2.put("userId", user.getId());
				}
				cond2.put("id", lists.get(i).getId());
				List<PPublications> lpub = this.pPublicationsService
						.getArticleList(cond2, "", user, IpUtil.getIp(request));
				if (lpub != null && lpub.size() == 1) {
					if (lpub.get(0).getRemark() != null
							&& "[无简介]".equals(lpub.get(0).getRemark())) {
						lpub.get(0).setRemark("");
					}
					lpub.get(0).setPriceList(pricelist);
					list.add(lpub.get(0));
				}
			}
			model.put("form", form);
			model.put("issueList", list);
			model.put("id", form.getParentId());
			/*
			 * JSONObject json = JSONObject.fromObject(model);
			 * 
			 * response.setContentType("text/json;charset=utf-8"); PrintWriter
			 * writer = null; try { // 获取输出流 writer = response.getWriter();
			 * writer.print(json.toString()); } catch (IOException e) { //
			 * e.printStackTrace(); throw e; } finally { if (writer != null) {
			 * writer.close(); } }
			 */

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(
					"message",
					(e instanceof CcsException) ? Lang.getLanguage(
							((CcsException) e).getPrompt(), request
									.getSession().getAttribute("lang")
									.toString()) : e.getMessage());
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 数据库
	 * 
	 * @param subCode
	 *            用来查找子分类、all为全部父分类
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/database")
	public ModelAndView databaseList(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		String forwardString = "publications/databaseList";
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			CUser user = request.getSession() == null ? null : (CUser) request
					.getSession().getAttribute("mainUser");
			form.setUrl(request.getRequestURL().toString());
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("type", 5);
			condition.put("order",
					form.getOrder() == null ? "" : form.getOrder());
			condition.put("pstatus", 2);
			condition.put("isLicense", form.getIsLicense());// 是否订阅
			condition.put("userId", user == null ? "" : user.getId());// 用户ID
			condition.put("ip", IpUtil.getIp(request));// 用户ID
			String sort = "";
			if (form.getOrderDesc() != null
					&& "titleAsc".equalsIgnoreCase(form.getOrderDesc())) {
				sort = " order by a.title ";
			} else if (form.getOrderDesc() != null
					&& "pubDesc".equalsIgnoreCase(form.getOrderDesc())) {
				sort = " order by a.pubDate desc ";
			} else {
				form.setOrderDesc("titleAsc");// 默认按字母排序
				sort = " order by a.title ";
			}
			form.setCount(this.pPublicationsService.getDatabaseCount(condition));
			List<PPublications> pubList = this.pPublicationsService
					.getDatabasePageList(condition, sort, form.getPageCount(),
							form.getCurpage(), user, IpUtil.getIp(request));
			// 循环list 查询单个产品下边的价格列表
			for (int i = 0; i < pubList.size(); i++) {
				PPublications pcs = pubList.get(i);
				if (user != null) {
					Map<String, Object> con = new HashMap<String, Object>();
					con.put("publicationsid", pcs.getId());
					con.put("status", 2);
					con.put("userTypeId",
							user.getUserType().getId() == null ? "" : user
									.getUserType().getId());
					List<PPrice> price = this.pPublicationsService
							.getPriceList(con);
					int isFreeUser = request.getSession().getAttribute(
							"isFreeUser") == null ? 0 : (Integer) request
							.getSession().getAttribute("isFreeUser");
					if (isFreeUser != 1) {
						for (int j = 0; j < price.size(); j++) {
							PPrice pr = price.get(j);
							double endPrice = MathHelper.round(MathHelper.mul(
									pr.getPrice(), 1.13d));
							if (pr.getStatus() == 2) {
								price.get(j).setPrice(endPrice);
							}
						}
					}
					pubList.get(i).setPriceList(price);
				}
				// 查询购买机构
				Map<String, Object> con2 = new HashMap<String, Object>();
				con2.put("pubId", pcs.getId());
				pubList.get(i).setInstitutionList(
						this.pPublicationsService.getLicenseInstitutionList(
								con2, ""));
			}

			model.put("r_", new Date().getTime());
			model.put("list", pubList);
			model.put("form", form);
		} catch (Exception e) {
			e.printStackTrace();
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/form/journal")
	public ModelAndView jlist(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		String forwardString = "publications/jlist";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = request.getSession() == null ? null : (CUser) request
					.getSession().getAttribute("mainUser");
			String url = request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getRequestURI();
			int flag = 0;
			if (form.getSubParentId() != null
					&& !form.getSubParentId().equals("")) {
				if (flag == 0) {
					url += "?subParentId=" + form.getSubParentId();
					flag = 1;
				} else {
					url += "&subParentId=" + form.getSubParentId();
				}
			}
			if (form.getIsLicense() != null && !form.getIsLicense().equals("")) {
				if (flag == 0) {
					url += "?isLicense=" + form.getIsLicense();
					flag = 1;
				} else {
					url += "&isLicense=" + form.getIsLicense();
				}
			}
			form.setAzUrl(url);
			form.setUrl(request.getRequestURL().toString());
			// 查询子分类
			Map<String, Object> condition = new HashMap<String, Object>();

			// 根据分类code查询产品
			// condition.put("childCode",
			// form.getCode()==null?"":form.getCode());
			condition.put("type", 2);
			condition.put("order",
					form.getOrder() == null ? "" : form.getOrder());
			// condition.put("mainType","0");
			// condition.put("subjectId", form.getSubParentId());
			condition.put("pstatus", 2);

			condition.put("isLicense", form.getIsLicense());// 是否订阅
			condition.put("userId", user == null ? "" : user.getId());// 用户ID
			condition.put("ip", IpUtil.getIp(request));// 用户ID
			String sort = "";
			if (form.getOrderDesc() != null
					&& "titleAsc".equalsIgnoreCase(form.getOrderDesc())) {
				sort = " order by a.title ";
			} else if (form.getOrderDesc() != null
					&& "pubDesc".equalsIgnoreCase(form.getOrderDesc())) {
				sort = " order by a.pubDate desc ";
			} else {
				form.setOrderDesc("titleAsc");// 默认按字母排序
				sort = " order by a.title ";
			}
			List<PPublications> pubList = this.pPublicationsService
					.getPubPageList(condition, sort, form.getPageCount(),
							form.getCurpage(), user, IpUtil.getIp(request));
			// 循环list 查询单个产品下边的价格列表
			for (int i = 0; i < pubList.size(); i++) {
				PPublications pcs = pubList.get(i);
				if (user != null) {
					Map<String, Object> con = new HashMap<String, Object>();
					con.put("publicationsid", pcs.getId());
					con.put("status", 2);
					con.put("userTypeId",
							user.getUserType().getId() == null ? "" : user
									.getUserType().getId());
					List<PPrice> price = this.pPublicationsService
							.getPriceList(con);
					int isFreeUser = request.getSession().getAttribute(
							"isFreeUser") == null ? 0 : (Integer) request
							.getSession().getAttribute("isFreeUser");
					if (isFreeUser != 1) {
						for (int j = 0; j < price.size(); j++) {
							PPrice pr = price.get(j);
							double endPrice = MathHelper.round(MathHelper.mul(
									pr.getPrice(), 1.13d));
							if (pr.getStatus() == 2) {
								price.get(j).setPrice(endPrice);
							}
						}
					}
					pubList.get(i).setPriceList(price);
				}
				// 查询分类
				Map<String, Object> con2 = new HashMap<String, Object>();
				con2.put("publicationsId", pcs.getId());
				List<PCsRelation> csList = this.bSubjectService.getSubPubList(
						con2, " order by a.subject.code ");
				pubList.get(i).setCsList(csList);
			}

			// 分类法统计
			Map<String, Object> subCondition = new HashMap<String, Object>();
			// subCondition.put("publisherId",form.getPublisherId());
			// if(form.getCode()==null||"".equals(form.getCode())){
			subCondition.put("parentId", form.getSubParentId());
			// }else{
			// subCondition.put("subjectId",form.getSubParentId());
			// }
			subCondition.put("ptype", 2);
			// subCondition.put("pubYear",form.getPubYear());
			subCondition.put("order",
					form.getOrder() == null ? "" : form.getOrder());
			subCondition.put("isLicense", form.getIsLicense());// 是否订阅
			subCondition.put("userId", user == null ? "" : user.getId());// 用户ID
			subCondition.put("ip", IpUtil.getIp(request));// 用户ID
			List<BSubject> subStatistic = this.bSubjectService
					.getStatisticList(subCondition, " order by a.treeCode ");

			form.setCurCount(pubList != null ? pubList.size() : 0);
			form.setCount(this.pPublicationsService.getPubCount(condition));
			form.setOrder(form.getOrder());
			model.put("subStatistic", subStatistic);
			model.put("pubList", pubList);
			model.put("form", form);

			// //分类法统计
			// Map<String,Object> subCondition = new HashMap<String,Object>();
			// subCondition.put("publisherId",form.getPublisherId());
			// if(form.getCode()==null||"".equals(form.getCode())){
			// subCondition.put("parentId",form.getSubParentId());
			// }else{
			// subCondition.put("subjectId",form.getSubParentId());
			// }
			// subCondition.put("ptype",form.getType());
			// subCondition.put("pubYear",form.getPubYear());
			// subCondition.put("order",
			// form.getOrder()==null?"":form.getOrder());
			// subCondition.put("isLicense", form.getIsLicense());//是否订阅
			// subCondition.put("userId", user==null?"":user.getId());//用户ID
			// subCondition.put("ip", IpUtil.getIp(request));//用户ID
			// List<BSubject> subStatistic =
			// this.bSubjectService.getStatisticList(subCondition,
			// " order by a.treeCode ");
			//
			// model.put("statistic", subStatistic);
			//
			// model.put("subjectId", form.getSubParentId());
			model.put("order", form.getOrder());
			model.put("r_", new Date().getTime());
		} catch (Exception e) {
			request.setAttribute(
					"message",
					(e instanceof CcsException) ? Lang.getLanguage(
							((CcsException) e).getPrompt(), request
									.getSession().getAttribute("lang")
									.toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/form/journaldetail/{journalId}")
	public ModelAndView jdetaillist(@PathVariable String journalId,
			HttpServletRequest request, HttpServletResponse response,
			PPublicationsForm form) throws Exception {
		String forwardString = "publications/journaldetail";
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			Integer insStatus = 1;
			form.setUrl(request.getRequestURL().toString());
			CUser user = request.getSession() == null ? null : (CUser) request
					.getSession().getAttribute("mainUser");
			PPublications journal = this.pPublicationsService
					.getPublications(journalId);
			if (journal.getRemark() != null
					&& "[无简介]".equals(journal.getRemark())) {
				journal.setRemark("");
			}
			if (user != null) {
				Map<String, Object> usercon = new HashMap<String, Object>();
				if (user.getInstitution() != null) {// 登陆用户属于机构用户，判断类型 2-机构用户
													// 3-默认用户 5-专家用户
					// 判断所属机构是否被禁用
					usercon.put("insId", user.getInstitution().getId());
					BInstitution ins = this.configureService
							.getInstitution(user.getInstitution().getId());
					if (ins != null) {
						if (ins.getStatus() != null && ins.getStatus() == 2) {// 所属机构被禁用
							insStatus = ins.getStatus();

						}
					}
				}
			}
			if (journal.getType() == 2) {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("parentid", journal.getId());
				if (journal.getJournalType() != null
						&& journal.getJournalType() == 2) {

					condition.put("type", 7);// 期
				} else {
					condition.put("type", 4);// 文章

				}
				condition.put("isLicense2", "true");// 上架的
				condition.put("orOnSale", "true");
				condition.put("check", "true");
				condition.put("ip", IpUtil.getIp(request));
				if (user != null) {
					condition.put("userId", user.getId());
				}
				Map<String, Object> pcr = new HashMap<String, Object>();
				// 填写的issn 相当于合刊
				pcr.put("issueCon", journalId);
				pcr.put("mark", "1");
				List<PContentRelation> issuemark1 = this.pPublicationsService
						.getPContentRelaionList(pcr, "");
				if (issuemark1 != null && issuemark1.size() > 0) {
					for (PContentRelation iss1 : issuemark1) {
						iss1.setMark("2");
					}
				}
				// 填写的issn,相当于分刊
				pcr = new HashMap<String, Object>();
				pcr.put("issueCon", journalId);
				pcr.put("mark", "2");
				List<PContentRelation> issuemark2 = this.pPublicationsService
						.getPContentRelaionList(pcr, "");
				if (issuemark2 != null && issuemark2.size() > 0) {
					for (PContentRelation iss2 : issuemark2) {
						iss2.setMark("21");
					}
				}
				// 分刊
				pcr = new HashMap<String, Object>();
				pcr.put("separateCon", journalId);
				pcr.put("mark", "1");
				List<PContentRelation> separateConmark1 = this.pPublicationsService
						.getPContentRelaionList(pcr, "");
				if (separateConmark1 != null && separateConmark1.size() > 0) {
					for (PContentRelation sc1 : separateConmark1) {
						sc1.setMark("12");
					}
				}
				// 合刊
				pcr = new HashMap<String, Object>();
				pcr.put("separateCon", journalId);
				pcr.put("mark", "2");
				List<PContentRelation> separateConmark2 = this.pPublicationsService
						.getPContentRelaionList(pcr, "");
				if (separateConmark2 != null && separateConmark2.size() > 0) {
					for (PContentRelation sc2 : separateConmark2) {
						sc2.setMark("1");
					}
				}
				// 变更列表
				pcr = new HashMap<String, Object>();
				pcr.put("separateCon", journalId);
				pcr.put("mark", "3");
				List<PContentRelation> modifyList1 = this.pPublicationsService
						.getPContentRelaionList(pcr, "");
				if (modifyList1 != null && modifyList1.size() > 0) {
					for (PContentRelation mo1 : modifyList1) {
						mo1.setMark("3");
					}
				}
				// 变更列表
				pcr = new HashMap<String, Object>();
				pcr.put("issueCon", journalId);
				pcr.put("mark", "3");
				List<PContentRelation> modifyList2 = this.pPublicationsService
						.getPContentRelaionList(pcr, "");
				if (modifyList2 != null && modifyList2.size() > 0) {
					for (PContentRelation mo1 : modifyList2) {
						mo1.setMark("31");
					}
				}
				// 分刊列表
				ArrayList<PContentRelation> pcrList = new ArrayList<PContentRelation>();// 有改变后的列表
				ArrayList<PContentRelation> pcrList1 = new ArrayList<PContentRelation>();// 改变前的列表
				// 合刊列表
				ArrayList<PContentRelation> pcrList2 = new ArrayList<PContentRelation>();// 有改变后的列表
				ArrayList<PContentRelation> pcrList3 = new ArrayList<PContentRelation>();// 改变前的列表
				// 变更列表
				ArrayList<PContentRelation> pcrList4 = new ArrayList<PContentRelation>();// 有改变后的列表
				ArrayList<PContentRelation> pcrList5 = new ArrayList<PContentRelation>();// 改变前的列表
				if (issuemark1 != null && issuemark1.size() > 0) {
					pcrList3.add(issuemark1.get(0));
				}
				if (issuemark2 != null && issuemark2.size() > 0) {
					pcrList3.add(issuemark2.get(0));
				}
				if (separateConmark1 != null && separateConmark1.size() > 0) {
					pcrList1.add(separateConmark1.get(0));
				}
				if (separateConmark2 != null && separateConmark2.size() > 0) {
					pcrList1.add(separateConmark2.get(0));
				}
				if (modifyList1 != null && modifyList1.size() > 0) {
					pcrList5.add(modifyList1.get(0));
				}
				if (modifyList2 != null && modifyList2.size() > 0) {
					pcrList5.add(modifyList2.get(0));
				}
				pcrList4.addAll(modifyList1);
				pcrList4.addAll(modifyList2);
				pcrList2.addAll(issuemark1);
				pcrList2.addAll(issuemark2);
				// pcrList2.addAll(modifyList2);
				pcrList.addAll(separateConmark1);
				pcrList.addAll(separateConmark2);
				// pcrList.addAll(modifyList1);
				// pcr.put("id", journalId);
				// List<PContentRelation> pcrList =
				// this.pPublicationsService.getPContentRelaionList(pcr,"");
				if (pcrList1 != null && pcrList1.size() > 0) {
					if (pcrList != null && pcrList.size() > 0) {
						model.put("pcrlist", pcrList);
					} else {
						model.put("pcrlist", null);
					}
					model.put("pcrlist1", pcrList1);
				} else {
					model.put("pcrlist1", null);
				}
				if (pcrList3 != null && pcrList3.size() > 0) {
					if (pcrList2 != null && pcrList2.size() > 0) {
						model.put("pcrlist2", pcrList2);
					} else {
						model.put("pcrlist2", null);
					}
					model.put("pcrlist3", pcrList3);
				} else {
					model.put("pcrlist3", null);
				}
				if (pcrList5 != null && pcrList5.size() > 0) {
					if (pcrList4 != null && pcrList4.size() > 0) {
						model.put("pcrlist4", pcrList4);
					} else {
						model.put("pcrlist4", null);
					}
					model.put("pcrlist5", pcrList5);
				} else {
					model.put("pcrlist5", null);
				}
				Map<String, Object> cond2 = new HashMap<String, Object>();
				cond2.put("isLicense2", "true");
				cond2.put("orOnSale", "true");
				cond2.put("check", "false");
				cond2.put("ip", IpUtil.getIp(request));
				if (user != null) {
					cond2.put("userId", user.getId());
				}
				if (form.getVolumeId() != null
						&& !"".equals(form.getVolumeId())) {
					condition.put("volumeId", form.getVolumeId());
					cond2.put("id", form.getVolumeId());
				} else if (form.getIssueId() != null
						&& !"".equals(form.getIssueId())) {
					condition.put("issueId", form.getIssueId());
					cond2.put("id", form.getIssueId());
				} else {
					cond2.put("id", journal.getId());
				}
				List<PPublications> lpub = this.pPublicationsService
						.getArticleList(cond2, "", user, IpUtil.getIp(request));
				String year=null;
				if (lpub != null && lpub.size() == 1) {
					if (lpub.get(0).getRemark() != null
							&& "[无简介]".equals(lpub.get(0).getRemark())) {
						lpub.get(0).setRemark("");
						year=lpub.get(0).getYear();
						lpub.get(0).setYear(null);
					}
					form.setObj(lpub.get(0));
				}

				// 文章列表（出版时间倒序）

				form.setCount(this.pPublicationsService.getPubCount(condition));
				List<PPublications> list = this.pPublicationsService
						.getArticlePagingList(condition,
								" order by a.pubDate desc ",
								form.getPageCount(), form.getCurpage(), user,
								IpUtil.getIp(request));
				if (list != null && list.size() > 0) {
					if (user != null) {
						Map<String, Object> pCondition = new HashMap<String, Object>();
						pCondition.put("status", 2);
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).getRemark() != null
									&& "[无简介]".equals(list.get(i).getRemark())) {
								list.get(i).setRemark("");
							}
							pCondition.put("publicationsid", list.get(i)
									.getId());
							pCondition.put("status", 2);
							pCondition.put("userTypeId", user.getUserType()
									.getId() == null ? "" : user.getUserType()
									.getId());
							List<PPrice> plist = this.pPublicationsService
									.getPriceList(pCondition);
							list.get(i).setPriceList(plist);
						}
					}
				}

				if (user != null) {
					// 查询价格列表
					condition.clear();
					condition.put("publicationsid", form.getObj().getId());
					condition.put("status", 2);
					condition.put("userTypeId",
							user.getUserType().getId() == null ? "" : user
									.getUserType().getId());
					List<PPrice> pricelist = this.pPublicationsService
							.getPriceList(condition);
					List<PPrice> delList = new ArrayList<PPrice>();
					if (pricelist != null && !pricelist.isEmpty()) {
						if (form.getObj().getType() == 2) {// 期刊
							if (request.getSession().getAttribute("isFreeUser") == null
									|| (Integer) request.getSession()
											.getAttribute("isFreeUser") != 1) {
								for (PPrice p : pricelist) {
									Map<String, Object> detailCondition = new HashMap<String, Object>();
									detailCondition.put("priceId", p.getId());
									// 如果是机构管理员用户，则直接通过机构Id查询
									if (user.getLevel() == 2) {
										detailCondition.put("institutionId",
												user.getInstitution().getId());
									} else {
										// 其他用户根据用户自己的Id查询，是否可以购买
										detailCondition.put("userid",
												user.getId());
									}
									// 查询购物车中是否存在
									// detailCondition.put("orderNull",
									// "1");//没有生成订单的明细
									detailCondition.put("statusArry",
											new Integer[] { 1, 2, 4 });// 状态
																		// 1-未处理
																		// 2-已付款未开通
																		// 3-已付款已开通
																		// 4-处理中
																		// 10-未付款已开通
																		// 99-已取消
									List<OOrderDetail> odetailList = this.oOrderService
											.getDetailListForAddCrat(detailCondition);
									if (odetailList != null
											&& odetailList.size() > 0) {
										delList.add(p);
									} else {
										// 在明细中没有找到
										// 查找Licesne，License中是否有有效的 1-有效 2-无效
										detailCondition.put("status", 1);
										List<LLicense> licenseList = this.oOrderService
												.getLicenseForAddCart(detailCondition);
										if (licenseList != null
												&& licenseList.size() > 0) {
											delList.add(p);
										} else {
											p.setPrice(round(MathHelper.mul(
													p.getPrice(), 1.13d)));
										}
									}
								}
							} else {
								for (PPrice p : pricelist) {
									Map<String, Object> detailCondition = new HashMap<String, Object>();
									detailCondition.put("priceId", p.getId());
									// 如果是机构管理员用户，则直接通过机构Id查询
									if (user.getLevel() == 2) {
										detailCondition.put("institutionId",
												user.getInstitution().getId());
									} else {
										// 其他用户根据用户自己的Id查询，是否可以购买
										detailCondition.put("userid",
												user.getId());
									}
									// 查询购物车中是否存在
									// detailCondition.put("orderNull",
									// "1");//没有生成订单的明细
									detailCondition.put("statusArry",
											new Integer[] { 1, 2, 4 });// 状态
																		// 1-未处理
																		// 2-已付款未开通
																		// 3-已付款已开通
																		// 4-处理中
																		// 10-未付款已开通
																		// 99-已取消
									List<OOrderDetail> odetailList = this.oOrderService
											.getDetailListForAddCrat(detailCondition);
									if (odetailList != null
											&& odetailList.size() > 0) {
										delList.add(p);
									} else {
										// 在明细中没有找到
										// 查找Licesne，License中是否有有效的 1-有效 2-无效
										detailCondition.put("status", 1);
										List<LLicense> licenseList = this.oOrderService
												.getLicenseForAddCart(detailCondition);
										if (licenseList != null
												&& licenseList.size() > 0) {
											delList.add(p);
										} else {
											continue;
										}
									}
								}
							}
						} else {
							if (request.getSession().getAttribute("isFreeUser") == null
									|| (Integer) request.getSession()
											.getAttribute("isFreeUser") != 1) {
								for (PPrice p : pricelist) {
									p.setPrice(round(MathHelper.mul(
											p.getPrice(), 1.13d)));
								}
							}
						}
					}
					if (delList != null && delList.size() > 0) {
						pricelist.removeAll(delList);
					}
					form.getObj().setPriceList(pricelist);
					model.put("pricelist", pricelist);
				}

				// 查询分类
				Map<String, Object> conn = new HashMap<String, Object>();
				conn.put("publicationsId", form.getObj().getId());
				form.getObj().setCsList(
						this.bSubjectService.getSubPubList(conn,
								" order by a.subject.code "));

				CUser rUser = (CUser) request.getSession().getAttribute(
						"recommendUser");
				form.setRecommendUser(rUser);
				condition.clear();
				condition.put("parentid", journal.getId());
				condition.put("isLicense2", "true");
				condition.put("orOnSale", "true");
				condition.put("check", "false");
				condition.put("ip", IpUtil.getIp(request));
				if (user != null) {
					condition.put("userId", user.getId());
				}
				condition.put("justYear", "true");
				List<PPublications> yearList = null;
				yearList = this.pPublicationsService.getSimpleList(condition,
						" group by a.year order by a.year desc ", 0);

				Calendar c = Calendar.getInstance();
				condition.clear();
				condition.put("year", String.valueOf(c.get(Calendar.YEAR)));
				condition.put(
						"month",
						(c.get(Calendar.MONTH) + 1) < 10 ? "0"
								+ String.valueOf(c.get(Calendar.MONTH) + 1)
								: String.valueOf(c.get(Calendar.MONTH) + 1));
				if (journal.getJournalType() != null
						&& journal.getJournalType() == 2) {
					condition.put("pubType", 7);// 期
				} else {

					condition.put("pubType", 4);// 文章
				}
				condition.put("pubParentId", journal.getId());
				condition.put("pubStatus", 2);
				condition.put("type", 2);
				List<LAccess> accList = this.logAOPService.getTopList(
						condition, 5);

				String currYear = null;
				/*Integer min = 0;
				if (yearList.size() > 0) {
					for (int i = 0; i < yearList.size(); i++) {
						if (Integer.parseInt(yearList.get(i).getYear()) > min)
							min = Integer.parseInt(yearList.get(i).getYear());
					}
					currYear = String.valueOf(min);
				} else {
					currYear = form.getObj().getYear();
				}*/
				String yy = request.getParameter("pubYear");
				if ((form.getObj() != null) && (form.getObj().getYear() != null)) {
			         currYear = form.getObj().getYear();
			    } else {
			       Integer min = Integer.valueOf(0);
			        if (yearList.size() > 0) {
			           for (int i = 0; i < yearList.size(); i++)
			             if (Integer.parseInt(((PPublications)yearList.get(i)).getYear()) > min.intValue())
			                min = Integer.valueOf(Integer.parseInt(((PPublications)yearList.get(i)).getYear()));
			        }
			          currYear = String.valueOf(min);
			    }
				if(yy!=null&&!"".equals(yy)){
					currYear=yy;
				}
				condition.clear();
				condition.put("parentid", journal.getId());
				condition.put("isLicense2", "true");// 已上架的或已订阅的
				condition.put("orOnSale", "true");
				condition.put("check", "true");
				condition.put("ip", IpUtil.getIp(request));
				if (user != null) {
					condition.put("userId", user.getId());
				}
				condition.put("type", 6);// 卷
				Integer volCount = this.pPublicationsService
						.getPubCount(condition);// 期刊中的已上架的卷总数
				condition.put("type", 7);// 期
				Integer issCount = this.pPublicationsService
						.getPubCount(condition);// 期刊中的已上架的期总数
				condition.put("type", 4);// 文章
				Integer artCount = this.pPublicationsService
						.getPubCount(condition);// 期刊中的已上架的文章总数

				LLicense license = this.pPublicationsService.getVaildLicense(
						journal, user, IpUtil.getIp(request));
				if (journal.getOa() != 1 && journal.getFree() != 1
						&& license == null) {
					BInstitution institution = (BInstitution) request
							.getSession().getAttribute("institution");
					// 没有访问权限,计数
					this.oOrderService
							.addPDACounter(journal, institution, user);
				}
				//上一期，下一期
				
				Map<String, Object> s = new HashMap<String, Object>();
				s.put("id", journalId);
				s.put("isLicense2", "true");
				s.put("orOnSale", "true");
				s.put("check", "false");
				s.put("ip", IpUtil.getIp(request));
				if (user != null) {
					s.put("userId", user.getId());
				}
				List<PPublications> ppList = this.pPublicationsService
						.getArticleList(s, " order by a.issueCode ", (CUser) request
								.getSession().getAttribute("mainUser"), IpUtil
								.getIp(request));
				if (ppList != null && !ppList.isEmpty()) {
					PPublications pub = ppList.get(0);
					String prevArticleId = null;
					String nextArticleId = null;
					// 查询前一篇文章
					Map<String, Object> condition1 = new HashMap<String, Object>();
					condition1.put("parentid", journalId);
					condition1.put("pYear", year);
					condition1.put("prevJour", form.getObj().getMonth());
					condition1.put("isLicense2", "true");
					condition1.put("orOnSale", "true");
					condition1.put("ip", IpUtil.getIp(request));
					if (user != null) {
						condition1.put("userId", user.getId());
					}
					condition1.put("check", "false");
					List<PPublications> prevList = this.pPublicationsService
							.getSimpleList(condition1,
									" order by a.month desc ", 1);
					if (prevList != null && prevList.size() == 1) {
						prevArticleId = prevList.get(0).getId();
					}
					// 查询后一篇文章
					condition1.clear();
					condition1.put("parentid", journalId);
					condition1.put("pYear", year);
					condition1.put("nextJour", form.getObj().getMonth());
					condition1.put("isLicense2", "true");
					condition1.put("orOnSale", "true");
					condition1.put("check", "false");
					condition1.put("ip", IpUtil.getIp(request));
					if (user != null) {
						condition1.put("userId", user.getId());
					}
					List<PPublications> nextList = this.pPublicationsService
							.getSimpleList(condition1, " order by a.month asc ", 1);
					if (nextList != null && nextList.size() == 1) {
						nextArticleId = nextList.get(0).getId();
					}
					model.put("prevId", prevArticleId);
					model.put("nextId", nextArticleId);
				}
				
				

				int pubtype = 0;
				if (journal.getJournalType() != null
						&& journal.getJournalType() == 2
						&& form.getObj().getType() == 7) {
					pubtype = form.getObj().getType();
				}
				model.put("pubtype", pubtype);
				model.put("volCount", volCount);
				model.put("issCount", issCount);
				model.put("artCount", artCount);
				model.put("currYear", currYear);
				if(year==null||year.equals("")){
					year=currYear;
				}
				model.put("year", year);
				model.put("form", form);
				model.put("list", list);
				model.put("ylist", yearList);
				model.put("alist", accList);
				model.put("journal", journal);
				model.put("insStatus", insStatus);
			} else {
				// 输入的不是期刊ID
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(
					"message",
					(e instanceof CcsException) ? Lang.getLanguage(
							((CcsException) e).getPrompt(), request
									.getSession().getAttribute("lang")
									.toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	public List<PPublicationsRelation> recursion(String id) throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();

		Set<String> none = new HashSet<String>();
		condition.put("id", id);
		condition.put("id", id);
		boolean flag = false;
		List<PPublicationsRelation> list = this.pPublicationsService
				.getPPublicationsRelationList(condition, null);
		for (PPublicationsRelation o : list) {
			System.out.println(o.getId() + "*****************************");
		}
		already.put(id, 1);
		for (PPublicationsRelation o : list) {
			hang.add(o.getId());
			if (hang.size() > x) {
				relation.add(o);
				x++;
			}
			none.add(o.getIssueCon().getId());
			none.add(o.getSeparateCon().getId());
			Iterator iter = none.iterator();

			while (iter.hasNext()) {
				String snone = (String) iter.next();
				for (String key : already.keySet()) {
					if (snone.equals(key)) {
						flag = true;
					}
				}
				if (flag == false) {
					recursion(snone);
				} else {
					flag = false;
				}
			}
		}
		return list;
	}

	@RequestMapping(value = "/form/journalarticle/{articleId}")
	public ModelAndView journalArticle(@PathVariable String articleId,
			HttpServletRequest request, HttpServletResponse response,
			PPublicationsForm form) throws Exception {
		String forwardString = "publications/journalarticle";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = request.getSession().getAttribute("mainUser") == null ? null
					: (CUser) request.getSession().getAttribute("mainUser");
			// 由于加入了标签，这里不能用get查询
			Map<String, Object> s = new HashMap<String, Object>();
			s.put("id", articleId);
			s.put("isLicense2", "true");
			s.put("orOnSale", "true");
			s.put("check", "false");
			s.put("ip", IpUtil.getIp(request));
			if (user != null) {
				s.put("userId", user.getId());
			}
			// if("1".equals(form.getIsReady())){
			// s.put("status", null);
			// }

			List<PPublications> ppList = this.pPublicationsService
					.getArticleList(s, " order by a.createOn ", (CUser) request
							.getSession().getAttribute("mainUser"), IpUtil
							.getIp(request));
			// ppList=this.pPublicationsService.getPubList(s,
			// " order by a.createOn ",
			// (CUser)request.getSession().getAttribute("mainUser"),IpUtil.getIp(request));
			if (ppList != null && !ppList.isEmpty()) {
				PPublications pub = ppList.get(0);
				String prevArticleId = null;
				String nextArticleId = null;
				// 查询前一篇文章
				Map<String, Object> condition1 = new HashMap<String, Object>();
				condition1.put("issueId", pub.getIssue().getId());
				condition1.put("prevPage", pub.getStartPage());
				condition1.put("isLicense2", "true");
				condition1.put("orOnSale", "true");
				condition1.put("ip", IpUtil.getIp(request));
				if (user != null) {
					condition1.put("userId", user.getId());
				}
				condition1.put("check", "false");
				List<PPublications> prevList = this.pPublicationsService
						.getSimpleList(condition1,
								" order by a.startPage desc ", 1);
				if (prevList != null && prevList.size() == 1) {
					prevArticleId = prevList.get(0).getId();
				}
				// 查询后一篇文章
				condition1.clear();
				condition1.put("issueId", pub.getIssue().getId());
				condition1.put("nextPage", pub.getStartPage());
				condition1.put("isLicense2", "true");
				condition1.put("orOnSale", "true");
				condition1.put("check", "false");
				condition1.put("ip", IpUtil.getIp(request));
				if (user != null) {
					condition1.put("userId", user.getId());
				}
				List<PPublications> nextList = this.pPublicationsService
						.getSimpleList(condition1, " order by a.startPage ", 1);
				if (nextList != null && nextList.size() == 1) {
					nextArticleId = nextList.get(0).getId();
				}
				List<PPrice> pricelist = null;
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("publicationsid", articleId);
				condition.put("status", 2);
				if (user != null) {
					condition.put("userTypeId",
							user.getUserType().getId() == null ? "" : user
									.getUserType().getId());
					pricelist = this.pPublicationsService
							.getPriceList(condition);
				}
				pub.setLang(pub.getLang() == null ? "ENG" : pub.getLang()
						.toUpperCase());// 语言大写显示
				// 查询分类
				Map<String, Object> conn = new HashMap<String, Object>();
				conn.put("publicationsId", pub.getId());
				pub.setCsList(this.bSubjectService.getSubPubList(conn,
						" order by a.subject.code "));
				form.setObj(pub); // 产品信息
				if (pricelist != null && !pricelist.isEmpty()) {
					if (request.getSession().getAttribute("isFreeUser") == null
							|| (Integer) request.getSession().getAttribute(
									"isFreeUser") != 1) {
						for (PPrice p : pricelist) {
							p.setPrice(round(MathHelper.mul(p.getPrice(), 1.13d)));
						}
					}
				}

				CUser ruser = (CUser) request.getSession().getAttribute(
						"recommendUser");
				form.setRecommendUser(ruser);

				String[] keywords = null;
				String[] reference = null;
				if (pub.getKeywords() != null
						&& !"".equals(pub.getKeywords().trim())) {
					keywords = pub.getKeywords().split("\n");
				}
				if (pub.getReference() != null
						&& !"".equals(pub.getReference().trim())) {
					reference = pub.getReference().split("\n");
				}

				// 查询用户的有效license
				LLicense license = this.pPublicationsService.getVaildLicense(
						pub, user, IpUtil.getIp(request));
				/*
				 * LLicense license=null;
				 * if(request.getSession().getAttribute("mainUser")!=null){
				 * CUser
				 * mUser=(CUser)request.getSession().getAttribute("mainUser");
				 * condition.clear(); condition.put("articleId",articleId);
				 * condition.put("status", 1);//有效的license
				 * condition.put("userId", mUser.getId()); List<LLicense>
				 * licenselist
				 * =this.pPublicationsService.getLicenseList(condition,
				 * " order by a.accessUIdType "); if(licenselist !=null &&
				 * !licenselist.isEmpty()){ license=licenselist.get(0); }else{
				 * condition.remove("userId");
				 * if(request.getRemoteAddr()!=null){
				 * condition.put("ip",IpUtil.getLongIp(
				 * request.getRemoteAddr().toString())); List<LLicenseIp>
				 * lliplist= this.customService.getLicenseIpList(condition,
				 * " order by b.accessUIdType "); if(lliplist!=null &&
				 * !lliplist.isEmpty()){ license=lliplist.get(0).getLicense(); }
				 * } } }
				 */

				// ----------查询同分类中购买次数最多的前5个商品开始
				/*
				 * condition.clear(); condition.put("publicationsId",
				 * pub.getId()); List<PCsRelation>
				 * pcslist=this.bSubjectService.getSubPubList(condition, "");
				 * List<PCsRelation> toplist=null; if(pcslist!=null &&
				 * pcslist.size()>0){ condition.clear();
				 * condition.put("pcslist", pcslist); condition.put("pstatus",
				 * 2); condition.put("unEqualPubId", pub.getId());
				 * condition.put("pTypeArr", new Integer[]{4});//只在期刊文章中查找
				 * toplist=this.bSubjectService.getTops(condition,
				 * " order by p.buyTimes desc "
				 * ,5,0,(CUser)request.getSession().getAttribute
				 * ("mainUser"),IpUtil.getIp(request)); }
				 */
				// -----------查询同分类中购买次数最多的前5个商品结束

				// 文章的相关内容：为了速度相关内容这里改为从solr中查询标题相关的同分类前5篇文章
				condition.clear();
				condition.put("publicationsId", pub.getId());
				String query = "title:"
						+ ClientUtils.escapeQueryChars(pub.getTitle())
						+ " AND NOT id:\"" + pub.getId() + "\"";
				List<PCsRelation> pcslist = this.bSubjectService.getSubPubList(
						condition, "");
				if (pcslist != null && pcslist.size() > 0) {
					for (Integer i = 0; i < pcslist.size(); i++) {
						if (i == 0) {
							query += " AND (";
						}
						query += "taxonomy:\""
								+ pcslist.get(i).getSubject().getCode() + " "
								+ pcslist.get(i).getSubject().getName() + "\"";
						// query+="taxonomyEn:\"" +
						// pcslist.get(i).getSubject().getCode()+ " " +
						// pcslist.get(i).getSubject().getNameEn() + "\"" ;
						if (i < pcslist.size() - 1) {
							query += " OR ";
						} else {
							query += ")";
						}
					}
				}
				Map<String, String> param = new HashMap<String, String>();
				param.put("type", "4");
				Map<String, Object> resultMap = publicationsIndexService
						.searchByQueryString(query, 0, 5, param, "");
				if (resultMap.get("count") != null
						&& Long.valueOf(resultMap.get("count").toString()) > 0) {
					List<PPublications> resultList = new ArrayList<PPublications>();
					List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap
							.get("result");
					for (Map<String, Object> idInfo : list) {
						// 根据ID查询产品信息
						PPublications pub1 = this.pPublicationsService
								.getPublications(idInfo.get("id").toString());
						// map.put("cherperid", idInfo.get("id").toString());
						// PPublications pub1 = (PPublications)
						// pPublicationsService.getPublications(map);
						if (pub1 != null) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("pid", idInfo.get("id").toString());
							// map.put("", );
							CFavourites f = customService.getFavourite(map);
							if (null != f) {
								pub1.setFavorite(1);
							} else {
								pub1.setFavorite(0);
							}
							resultList.add(pub1);
						}
					}
					model.put("samelist", resultList);
				}

				if (pub.getOa() != 1 && pub.getFree() != 1 && license == null) {
					BInstitution institution = (BInstitution) request
							.getSession().getAttribute("institution");
					// 没有访问权限,计数
					this.oOrderService.addPDACounter(pub, institution, user);
				}

				model.put("keywords", keywords);
				model.put("reference", reference);
				model.put("prevId", prevArticleId);
				model.put("nextId", nextArticleId);
				// model.put("toplist",toplist);
				model.put("license", license);
				model.put("pricelist", pricelist);
				model.put("isfav", form.getObj().getFavorite());
				// TODO 以后再修改
				String remark = form.getObj().getRemark().replaceAll("\t", "")
						.replaceAll("\n", "");
				if ("<abstract></abstract>".equals(remark)) {
					form.getObj().setRemark("");
				}
				model.put("form", form);

			} else {
				forwardString = "frame/result";
				request.setAttribute("prompt", Lang.getLanguage(
						"Controller.Publications.Prompt.Title.NotFind", request
								.getSession().getAttribute("lang").toString()));
				request.setAttribute("message", Lang.getLanguage(
						"Controller.Publications.Prompt.Content.UnFind",
						request.getSession().getAttribute("lang").toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(
					"message",
					(e instanceof CcsException) ? Lang.getLanguage(
							((CcsException) e).getPrompt(), request
									.getSession().getAttribute("lang")
									.toString()) : e.getMessage());
			forwardString = "error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 文章可阅读的情况下可下载PDF
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/download")
	public void download(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			PPublicationsForm form) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			// 如果id不存在,则失败
			if (form.getId() == null || "".equalsIgnoreCase(form.getId())) {
				resultMap.put("error", Lang.getLanguage(
						"Controller.view.download.no",
						session.getAttribute("lang").toString()));
			} else {
				// 获取目标PDF
				PPublications p = this.pPublicationsService
						.getPublications(form.getId());
				if (p == null || p.getPdf() == null || "".equals(p.getPdf())) {
					resultMap.put("error", Lang.getLanguage(
							"Controller.view.download.no", session
									.getAttribute("lang").toString()));
				} else {
					String targetPath = Param.getParam("pdf.directory.config")
							.get("dir").replace("-", ":")
							+ p.getPdf();
					if (!FileUtil.isExist(targetPath)) {
						resultMap.put("error", Lang.getLanguage(
								"Controller.view.download.no", session
										.getAttribute("lang").toString()));
					} else {
						// 下载本地文件 文件的默认保存名
						String fileName = p.getPdf().substring(
								p.getPdf().lastIndexOf("/") + 1,
								p.getPdf().length());
						IOHandler.download(targetPath, fileName, request,
								response);
					}
				}
			}
			if (resultMap.get("error") != null
					&& !"".equals(resultMap.get("error"))) {
				String result = "<script type=\"text/javascript\">" + "alert('"
						+ resultMap.get("error") + "');" + "window.close();"
						+ "</script>";
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print(result);
				out.flush();
				out.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(
					"error",
					(e instanceof CcsException) ? ((CcsException) e)
							.getMessage() : e.getMessage());
		}
	}

	/**
	 * AJAX查询资源统计数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/pubsCounter")
	public ModelAndView pubsCounter(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forwardString = "index/index/resource_all";
		Map<String, Object> model = new HashMap<String, Object>();
		// List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			String w = request.getParameter("w");
			Integer width = Integer.valueOf(w);
			List<ResourcesSum> listsum = this.resourcesSumService.getList(null,
					"");
			if (listsum != null && listsum.size() > 0) {
				model.put("bookCount", listsum.get(0).getBookCount());
				model.put("journalsCount", listsum.get(0).getJournalsCount());
				model.put("articleCount", listsum.get(0).getArticleCount());
				model.put("sumCount", listsum.get(0).getSumCount());
				model.put("book", 1);
				model.put("journals", 2);
				model.put("article", 4);
				model.put("bwidth", width * listsum.get(0).getBookCount()
						/ listsum.get(0).getSumCount());
				model.put("jwidth", width * listsum.get(0).getJournalsCount()
						/ listsum.get(0).getSumCount());
				model.put("awidth", width * listsum.get(0).getArticleCount()
						/ listsum.get(0).getSumCount());

				/*
				 * String w=request.getParameter("w"); Integer
				 * width=Integer.valueOf(w); String
				 * lang=request.getSession().getAttribute("lang").toString();
				 * Map<String,Object> condition=new HashMap<String,Object>();
				 * //查询所有资源数量(已上架的) condition.put("status", 2);
				 * Map<String,Object>
				 * map=this.pPublicationsService.getPubTypeCount(condition);
				 * if(map!=null && map.size()>0){ Integer allCount=0;
				 * if(map.containsKey("all")){ allCount=(Integer)map.get("all");
				 * Map<String,Object> map1=new HashMap<String, Object>();
				 * map1.put("key",
				 * Lang.getLanguage("Page.Frame.Count.Lable.Total",lang));
				 * map1.put("val", allCount); list.add(map1); }
				 * if(map.containsKey("1")){ Map<String,Object> map1=new
				 * HashMap<String, Object>(); Integer
				 * count=(Integer)map.get("1"); map1.put("key",
				 * Lang.getLanguage("Pages.Index.Lable.Book",lang));
				 * map1.put("val", map.get("1")); map1.put("width",
				 * width*count/allCount); map1.put("type",1); list.add(map1); }
				 * 暂不显示章节数量 王超已同意2013-12-30 if(map.containsKey("3")){
				 * Map<String,Object> map1=new HashMap<String, Object>();
				 * Integer count=(Integer)map.get("3"); map1.put("key",
				 * Lang.getLanguage("Pages.Index.Lable.Chapter",lang));
				 * map1.put("val", map.get("3")); map1.put("width",
				 * width*count/allCount); list.add(map1); }
				 * if(map.containsKey("2")){ Map<String,Object> map1=new
				 * HashMap<String, Object>(); Integer
				 * count=(Integer)map.get("2"); map1.put("key",
				 * Lang.getLanguage("Pages.Index.Lable.Journal",lang));
				 * map1.put("val", map.get("2")); map1.put("width",
				 * width*count/allCount); map1.put("type",2); list.add(map1); }
				 * if(map.containsKey("4")){ Map<String,Object> map1=new
				 * HashMap<String, Object>(); Integer
				 * count=(Integer)map.get("4"); map1.put("key",
				 * Lang.getLanguage("Pages.Index.Lable.Article",lang));
				 * map1.put("val", map.get("4")); map1.put("width",
				 * width*count/allCount); map1.put("type",4); list.add(map1); }
				 * }
				 */
				// model.put("list", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			forwardString = "error";
			request.setAttribute(
					"message",
					(e instanceof CcsException) ? Lang.getLanguage(
							((CcsException) e).getPrompt(), request
									.getSession().getAttribute("lang")
									.toString()) : e.getMessage());
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * AJAX查询资源统计数据--首页最新资源
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("lastPubs")
	public ModelAndView lastPubs(HttpServletRequest request, HttpServletResponse response, PPublicationsForm form) throws Exception {
		String forwardString = "index/index/pubList";
		Map<String, Object> model = new HashMap<String, Object>();
		form.setUrl(request.getRequestURL().toString());
		Map<String, Object> resultMap = publicationsIndexService.searchNewPubs(4, 10);
		try {
			CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			Map<String, Object> condition = new HashMap<String, Object>();
			String ins_Id = "";
			Integer num = 5;
			if (request.getParameter("num") != null && !"".equals(request.getParameter("num").toString())) {
				num = Integer.valueOf(request.getParameter("num").toString());
			}
			// Ip范围内
			if (request.getSession().getAttribute("institution") != null) {
				if (request.getSession().getAttribute("mainUser") != null) {
					user = (CUser) request.getSession().getAttribute("mainUser");
					ins_Id = ((BInstitution) request.getSession().getAttribute("institution")).getId();
				} else {
					ins_Id = ((BInstitution) request.getSession().getAttribute("institution")).getId();
				}
			} else {// IP范围外，全局的。
				if (request.getSession().getAttribute("mainUser") != null) {
					user = (CUser) request.getSession()
							.getAttribute("mainUser");
					if (user.getLevel() == 2) {// IP范围外，机构管理员登录显示最新订阅
						ins_Id = user.getInstitution().getId();
					}
				} else {
					condition.put("institutionId", ins_Id);
				}
			}
			List<PPublications> resultList = new ArrayList<PPublications>();
			// IP范围外的全部看到的是全局的
			if ("".equals(ins_Id)) {
				if (request.getParameter("news") != null
						&& "true".equals(request.getParameter("news")
								.toString())) {
					// resultMap=publicationsIndexService.searchNewPubs(form.getCurpage(),form.getPageCount());
					resultMap = publicationsIndexService.searchNewPubs(4,
							form.getPageCount());
					forwardString = "index/lastPubList";
					Integer allCount = 0;
					if (resultMap.get("count") != null) {
						allCount = Integer.valueOf(resultMap.get("count")
								.toString());
						allCount = 1000 > allCount ? allCount : 1000;
					}
					if (resultMap.get("count") != null
							&& Long.valueOf(resultMap.get("count").toString()) > 0) {
						List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap
								.get("result");
						for (Map<String, Object> idInfo : list) {
							// 根据ID查询产品信息
							// 由于加入了标签，这里不能用get查询
							PPublications pub = this.pPublicationsService
									.getPublications(idInfo.get("id")
											.toString());
							if (pub != null
									&& (pub.getAvailable() == null ? 0 : pub
											.getAvailable()) != 3) {
								if (pub.getRemark() != null
										&& "[无简介]".equals(pub.getRemark())) {
									pub.setRemark("");
								}
								resultList.add(pub);
								/*
								 * if(resultList.size()>=4){ break; }
								 */
							}
							// 左侧条件查询
							Map<String, Object> langMap = new HashMap<String, Object>();
							Map<String, Object> publisherMap = new HashMap<String, Object>();
							Map<String, Object> pubDateMap = new TreeMap<String, Object>();
							int bookCount = 0;
							int jouranlCount = 0;
							int chapterCount = 0;
							int articleCount = 0;
							for (PPublications pPublications : resultList) {
								if (pPublications.getType() == 1) {
									bookCount++;
								} else if (pPublications.getType() == 2) {
									jouranlCount++;
								} else if (pPublications.getType() == 3) {
									chapterCount++;
								} else if (pPublications.getType() == 4) {
									articleCount++;
								}
								if (pPublications.getLang() != null
										&& !"".equals(pPublications.getLang()
												.toString())) {
									if (langMap.size() > 0
											&& langMap
													.containsKey(pPublications
															.getLang())) {
										int count = Integer.parseInt(langMap
												.get(pPublications.getLang())
												.toString());
										count++;
										langMap.put(pPublications.getLang()
												.toUpperCase(), count);
									} else {
										langMap.put(pPublications.getLang()
												.toUpperCase(), 1);
									}
								}

								if (pPublications.getPublisher().getName() != null
										&& !"".equals(pPublications
												.getPublisher().getName()
												.toString())) {
									if (publisherMap.size() > 0
											&& publisherMap
													.containsKey(pPublications
															.getPublisher()
															.getName())) {
										int count = Integer
												.parseInt(publisherMap.get(
														pPublications
																.getPublisher()
																.getName())
														.toString());
										count++;
										publisherMap.put(pPublications
												.getPublisher().getName(),
												count);
									} else {
										publisherMap.put(pPublications
												.getPublisher().getName(), 1);
									}
								}

								if (pPublications.getPubDate() != null
										&& !"".equals(pPublications
												.getPubDate().toString())) {
									if (pubDateMap.size() > 0
											&& pubDateMap
													.containsKey(pPublications
															.getPubDate()
															.substring(0, 4))) {
										int count = Integer.parseInt(pubDateMap
												.get(pPublications.getPubDate()
														.substring(0, 4))
												.toString());
										count++;
										pubDateMap.put(pPublications
												.getPubDate().substring(0, 4),
												count);
									} else {
										pubDateMap.put(pPublications
												.getPubDate().substring(0, 4),
												1);
									}
								}

							}
							condition.put("type", 1);
							model.put("bookCount", Integer.toString(bookCount));
							condition.put("type", 2);
							model.put("jouranlCount",
									Integer.toString(jouranlCount));
							condition.put("type", 3);
							model.put("chapterCount",
									Integer.toString(chapterCount));
							condition.put("type", 4);
							model.put("articleCount",
									Integer.toString(articleCount));
							model.put("langMap", langMap);
							model.put("publisherMap", publisherMap);
							model.put("pubDateMap", pubDateMap);
						}
						form.setCount(allCount);
						model.put("list", resultList);
					} else {
						form.setCount(0);
					}

				} else {

					resultMap = publicationsIndexService.searchNewPubs(0, 5);

					if (resultMap.get("count") != null
							&& Long.valueOf(resultMap.get("count").toString()) > 0) {
						List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap
								.get("result");
						for (Map<String, Object> idInfo : list) {
							// 根据ID查询产品信息
							// 由于加入了标签，这里不能用get查询
							PPublications pub = this.pPublicationsService
									.getPublications(idInfo.get("id")
											.toString());
							if (pub != null
									&& (pub.getAvailable() == null ? 0 : pub
											.getAvailable()) != 3) {
								if (pub.getRemark() != null
										&& "[无简介]".equals(pub.getRemark())) {
									pub.setRemark("");
								}
								resultList.add(pub);
								if (resultList.size() >= 4) {
									break;
								}
							}
						}
						model.put("list", resultList);
					}
					Map<String, Object> publisherMap = new HashMap<String, Object>();
					for (PPublications pPublications : resultList) {
						String ppName = pPublications.getPublisher().getName();
						condition.put("ppName", ppName);
						if (!publisherMap.containsKey(ppName)) {
							publisherMap.put(ppName, this.pPublicationsService
									.getPubCount(condition));
						}
					}
					condition.remove("ppName");
					model.put("publisherMap", publisherMap);

					Map<String, Object> pubDateMap = new TreeMap<String, Object>();
					for (PPublications pPublications : resultList) {
						String pDate = pPublications.getPubDate();
						pDate = pDate.substring(0, 4);
						condition.put("pDate", "%" + pDate + "%");
						if (!pubDateMap.containsKey(pDate)) {// Integer.parseInt(pDate)
							pubDateMap.put(pDate, this.pPublicationsService
									.getPubCount(condition));
						}
					}
					condition.remove("pDate");
					model.put("pubDateMap", pubDateMap);
				}
				/*
				 * if(resultMap.get("count")!=null&&Long.valueOf(resultMap.get(
				 * "count").toString())>0){ List<PPublications> resultList = new
				 * ArrayList<PPublications>(); List<Map<String,Object>> list =
				 * (List<Map<String,Object>>)resultMap.get("result");
				 * for(Map<String,Object> idInfo:list){ //根据ID查询产品信息
				 * //由于加入了标签，这里不能用get查询 PPublications
				 * pub=this.pPublicationsService
				 * .getPublications(idInfo.get("id").toString()); if(pub!=null
				 * && (pub.getAvailable()==null?0:pub.getAvailable())!=3){
				 * if(pub.getRemark()!=null && "[无简介]".equals(pub.getRemark())){
				 * pub.setRemark(""); } resultList.add(pub);
				 * if(resultList.size()>=4){ break; } } } model.put("list",
				 * resultList); }
				 */

			} else if (ins_Id != null && !ins_Id.equals("")
					&& request.getParameter("news") != null
					&& "true".equals(request.getParameter("news").toString())) {

				if (request.getParameter("news") != null
						&& "true".equals(request.getParameter("news")
								.toString())) {
					// resultMap=publicationsIndexService.searchNewPubs(form.getCurpage(),form.getPageCount());
					resultMap = publicationsIndexService.searchNewPubs(4,
							form.getPageCount());
					forwardString = "index/lastPubList";
					Integer allCount = 0;
					if (resultMap.get("count") != null) {
						allCount = Integer.valueOf(resultMap.get("count")
								.toString());
						allCount = 1000 > allCount ? allCount : 1000;
					}
					if (resultMap.get("count") != null
							&& Long.valueOf(resultMap.get("count").toString()) > 0) {
						List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap
								.get("result");
						for (Map<String, Object> idInfo : list) {
							// 根据ID查询产品信息
							// 由于加入了标签，这里不能用get查询
							PPublications pub = this.pPublicationsService
									.getPublications(idInfo.get("id")
											.toString());
							if (pub != null
									&& (pub.getAvailable() == null ? 0 : pub
											.getAvailable()) != 3) {
								if (pub.getRemark() != null
										&& "[无简介]".equals(pub.getRemark())) {
									pub.setRemark("");
								}
								resultList.add(pub);
								/*
								 * if(resultList.size()>=4){ break; }
								 */
							}
							// 左侧条件查询
							Map<String, Object> langMap = new HashMap<String, Object>();
							Map<String, Object> publisherMap = new HashMap<String, Object>();
							Map<String, Object> pubDateMap = new TreeMap<String, Object>();
							int bookCount = 0;
							int jouranlCount = 0;
							int chapterCount = 0;
							int articleCount = 0;
							for (PPublications pPublications : resultList) {
								if (pPublications.getType() == 1) {
									bookCount++;
								} else if (pPublications.getType() == 2) {
									jouranlCount++;
								} else if (pPublications.getType() == 3) {
									chapterCount++;
								} else if (pPublications.getType() == 4) {
									articleCount++;
								}
								if (pPublications.getLang() != null
										&& !"".equals(pPublications.getLang()
												.toString())) {
									if (langMap.size() > 0
											&& langMap
													.containsKey(pPublications
															.getLang())) {
										int count = Integer.parseInt(langMap
												.get(pPublications.getLang())
												.toString());
										count++;
										langMap.put(pPublications.getLang()
												.toUpperCase(), count);
									} else {
										langMap.put(pPublications.getLang()
												.toUpperCase(), 1);
									}
								}

								if (pPublications.getPublisher().getName() != null
										&& !"".equals(pPublications
												.getPublisher().getName()
												.toString())) {
									if (publisherMap.size() > 0
											&& publisherMap
													.containsKey(pPublications
															.getPublisher()
															.getName())) {
										int count = Integer
												.parseInt(publisherMap.get(
														pPublications
																.getPublisher()
																.getName())
														.toString());
										count++;
										publisherMap.put(pPublications
												.getPublisher().getName(),
												count);
									} else {
										publisherMap.put(pPublications
												.getPublisher().getName(), 1);
									}
								}

								if (pPublications.getPubDate() != null
										&& !"".equals(pPublications
												.getPubDate().toString())) {
									if (pubDateMap.size() > 0
											&& pubDateMap
													.containsKey(pPublications
															.getPubDate()
															.substring(0, 4))) {
										int count = Integer.parseInt(pubDateMap
												.get(pPublications.getPubDate()
														.substring(0, 4))
												.toString());
										count++;
										pubDateMap.put(pPublications
												.getPubDate().substring(0, 4),
												count);
									} else {
										pubDateMap.put(pPublications
												.getPubDate().substring(0, 4),
												1);
									}
								}

							}
							condition.put("type", 1);
							model.put("bookCount", Integer.toString(bookCount));
							condition.put("type", 2);
							model.put("jouranlCount",
									Integer.toString(jouranlCount));
							condition.put("type", 3);
							model.put("chapterCount",
									Integer.toString(chapterCount));
							condition.put("type", 4);
							model.put("articleCount",
									Integer.toString(articleCount));
							model.put("langMap", langMap);
							model.put("publisherMap", publisherMap);
							model.put("pubDateMap", pubDateMap);
						}
						form.setCount(allCount);
						model.put("list", resultList);
					} else {
						form.setCount(0);
					}

				} else {

					resultMap = publicationsIndexService.searchNewPubs(4, 10);

					if (resultMap.get("count") != null
							&& Long.valueOf(resultMap.get("count").toString()) > 0) {
						List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap
								.get("result");
						for (Map<String, Object> idInfo : list) {
							// 根据ID查询产品信息
							// 由于加入了标签，这里不能用get查询
							PPublications pub = this.pPublicationsService
									.getPublications(idInfo.get("id")
											.toString());
							if (pub != null
									&& (pub.getAvailable() == null ? 0 : pub
											.getAvailable()) != 3) {
								if (pub.getRemark() != null
										&& "[无简介]".equals(pub.getRemark())) {
									pub.setRemark("");
								}
								resultList.add(pub);
								if (resultList.size() >= 4) {
									break;
								}
							}
						}
						model.put("list", resultList);
					}
					Map<String, Object> publisherMap = new HashMap<String, Object>();
					for (PPublications pPublications : resultList) {
						String ppName = pPublications.getPublisher().getName();
						condition.put("ppName", ppName);
						if (!publisherMap.containsKey(ppName)) {
							publisherMap.put(ppName, this.pPublicationsService
									.getPubCount(condition));
						}
					}
					condition.remove("ppName");
					model.put("publisherMap", publisherMap);

					Map<String, Object> pubDateMap = new TreeMap<String, Object>();
					for (PPublications pPublications : resultList) {
						String pDate = pPublications.getPubDate();
						pDate = pDate.substring(0, 4);
						condition.put("pDate", "%" + pDate + "%");
						if (!pubDateMap.containsKey(pDate)) {// Integer.parseInt(pDate)
							pubDateMap.put(pDate, this.pPublicationsService
									.getPubCount(condition));
						}
					}
					condition.remove("pDate");
					model.put("pubDateMap", pubDateMap);
				}
				/*
				 * if(resultMap.get("count")!=null&&Long.valueOf(resultMap.get(
				 * "count").toString())>0){ List<PPublications> resultList = new
				 * ArrayList<PPublications>(); List<Map<String,Object>> list =
				 * (List<Map<String,Object>>)resultMap.get("result");
				 * for(Map<String,Object> idInfo:list){ //根据ID查询产品信息
				 * //由于加入了标签，这里不能用get查询 PPublications
				 * pub=this.pPublicationsService
				 * .getPublications(idInfo.get("id").toString()); if(pub!=null
				 * && (pub.getAvailable()==null?0:pub.getAvailable())!=3){
				 * if(pub.getRemark()!=null && "[无简介]".equals(pub.getRemark())){
				 * pub.setRemark(""); } resultList.add(pub);
				 * if(resultList.size()>=4){ break; } } } model.put("list",
				 * resultList); }
				 */

			} else {
				condition.put("status", 1);// license有效
				condition.put("searchType", new Integer[] { 1, 2 });
				condition.put("institutionId", ins_Id);
				condition.put("isTrail", "0");
				List<LLicense> list = this.customService
						.getLicensePagingListForIndex(condition,
								" order by a.createdon desc ", 4, 0);
				if (list.size() >= 4) {
					model.put("list", list);
					forwardString = "index/index/licenseList2";
				} else {
					resultMap = publicationsIndexService.searchNewPubs(4, 10);

					if (resultMap.get("count") != null
							&& Long.valueOf(resultMap.get("count").toString()) > 0) {
						List<Map<String, Object>> list1 = (List<Map<String, Object>>) resultMap
								.get("result");
						for (Map<String, Object> idInfo : list1) {
							// 根据ID查询产品信息
							// 由于加入了标签，这里不能用get查询
							PPublications pub = this.pPublicationsService
									.getPublications(idInfo.get("id")
											.toString());
							if (pub != null
									&& (pub.getAvailable() == null ? 0 : pub
											.getAvailable()) != 3) {
								if (pub.getRemark() != null
										&& "[无简介]".equals(pub.getRemark())) {
									pub.setRemark("");
								}
								resultList.add(pub);
								if (resultList.size() >= 4) {
									break;
								}
							}
						}
						model.put("list", resultList);
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			forwardString = "error";
			request.setAttribute(
					"message",
					(e instanceof CcsException) ? Lang.getLanguage(
							((CcsException) e).getPrompt(), request
									.getSession().getAttribute("lang")
									.toString()) : e.getMessage());
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/lastMorePubs")
	public ModelAndView lastMorePubs(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		String forwardString = "index/index/newPubList2";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = request.getSession().getAttribute("mainUser") == null ? null
					: (CUser) request.getSession().getAttribute("mainUser");
			Map<String, Object> condition = new HashMap<String, Object>();
			String ins_Id = "";
			if (request.getSession().getAttribute("institution") != null) {
				if (request.getSession().getAttribute("mainUser") != null) {
					user = (CUser) request.getSession()
							.getAttribute("mainUser");
					if (user.getLevel() != 2) {// 不是图书馆管理员
						ins_Id = ((BInstitution) request.getSession()
								.getAttribute("institution")).getId();
					}
				} else {
					ins_Id = ((BInstitution) request.getSession().getAttribute(
							"institution")).getId();
				}
			}

			condition.put("status", 1);// license有效
			condition.put("searchType", new Integer[] { 1, 2 });
			condition.put("institutionId", ins_Id);

			condition.put("isTrail", "0");
			/*
			 * condition.put("author", " "); condition.put("publisher", " ");
			 */
			model.put("list", this.customService.getLicensePagingListForIndex(
					condition, " order by a.createdon desc ",
					form.getPageCount(), form.getCurpage()));

			/*
			 * condition.put("pubtype", 1); model.put("bookCount",
			 * this.pPublicationsService.getPubCount(condition));
			 * condition.put("pubtype", 2); model.put("jouranlCount",
			 * this.pPublicationsService.getPubCount(condition));
			 * condition.put("pubtype", 3); model.put("chapterCount",
			 * this.pPublicationsService.getPubCount(condition));
			 * condition.put("pubtype", 4); model.put("articleCount",
			 * this.pPublicationsService.getPubCount(condition));
			 */

		} catch (Exception e) {
			e.printStackTrace();
			forwardString = "error";
			request.setAttribute(
					"message",
					(e instanceof CcsException) ? Lang.getLanguage(
							((CcsException) e).getPrompt(), request
									.getSession().getAttribute("lang")
									.toString()) : e.getMessage());
		}

		return new ModelAndView(forwardString, model);
	}

	public void addLog(PPublications pub, CUser user, String institutionId,
			String keyword, String ip, int fp) throws Exception {
		addLog(pub, user, institutionId, keyword, ip, 3, 1, fp, 0);
	}

	public void addLog(PPublications pub, CUser user, String institutionId,
			String keyword, String ip, int t, int a, int fp, int d)
			throws Exception {

		LAccess access = new LAccess();
		access.setActivity(keyword);
		access.setAccess(a);// 访问状态1-访问成功 2-访问拒绝
		access.setType(t);// 操作类型1-访问摘要 2-访问内容 3-检索
		if (d != 0) {
			access.setRefusedVisitType(d);
		} // 1-没有License,2-超出并发数
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
				if (user != null && user.getInstitution() != null
						&& !institutionId.equals(user.getInstitution().getId())) {
					access.setUserId(user.getId());
					access.setInstitutionId(user.getInstitution().getId());
					this.logAOPService.addLog(access);
				}
			}
		}
	}

	/**
	 * 接收DCC上传过来的EPub文件并解压至指定目录中
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/receiveEpubFile")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String tempPath = "/temp/buffer/"; // 临时文件目录
			String webRoot = request.getSession().getServletContext()
					.getRealPath("");// 网站根目录
			String basePath = Param.getParam("pdf.directory.config").get("dir")
					.replace("-", ":");// 文件根目录
			File tempPathFile = new File(basePath + tempPath);
			if (!tempPathFile.exists()) {
				tempPathFile.mkdirs();
			}
			String pubId = request.getParameter("pubId");
			if (pubId != null && !"".equals(pubId)) {
				PPublications publications = this.pPublicationsService
						.getPublications(pubId);
				if (publications != null && publications.getPath() != null
						&& !"".equals(publications.getPath())) {
					File uploadFile = new File(basePath
							+ publications.getPath());
					if (!uploadFile.exists()) {
						uploadFile.mkdirs();
					}
					// Create a factory for disk-based file items
					DiskFileItemFactory factory = new DiskFileItemFactory();
					// Set factory constraints
					factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
					factory.setRepository(tempPathFile);// 设置缓冲区目录
					// Create a new file upload handler
					ServletFileUpload upload = new ServletFileUpload(factory);
					// Set overall request size constraint
					upload.setSizeMax(41943040); // 设置最大文件尺寸，这里是40MB
					List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
					Iterator<FileItem> i = items.iterator();
					while (i.hasNext()) {
						FileItem fi = (FileItem) i.next();
						String fileName = fi.getName();
						if (fileName != null) {
							File fullFile = new File(fi.getName());
							File savedFile = new File(basePath
									+ publications.getPath(),
									fullFile.getName());
							fi.write(savedFile);
							// System.out.println("XXXXXXXXXXXXXXXXXXX File Name XXXXXXXXXXXXXXXXXX:"
							// + fullFile.getName());
							// System.out.println("XXXXXXXXXXXXXXXXXXX File Save XXXXXXXXXXXXXXXXXX:"
							// + basePath + fullFile.getName());
						}
					}
					System.out.print("upload succeed");

					if (FileUtil.isExist(basePath + publications.getPdf())) {
						FileUtil.unZip(basePath + publications.getPdf(),
								webRoot + publications.getEpubDir());
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// 可以跳转出错页面
			// e.printStackTrace();
		}
	}

	@RequestMapping("form/cover")
	public void cover(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws Exception {
		String coverPath = "";
		try {
			String basePath = Param.getParam("pdf.directory.config").get("dir")
					.replace("-", ":");
			String id = request.getParameter("id");
			String type = request.getParameter("t");
			Boolean useDefault = false;
			if (id != null && !"".equals(id)) {
				PPublications pub = this.pPublicationsService
						.getPublications(id);
				if (pub.getCover() != null && !"".equals(pub.getCover())) {
					coverPath = basePath + "/" + pub.getCover();
				} else {
					coverPath = basePath + "/images/noimg.jpg";
					useDefault = true;
				}
				if (FileUtil.isExist(coverPath)) {
					if (!useDefault && type != null && !"".equals(type)) {// 若使用的不是默认图片并且传递了参数type
						String size = Param.getParam("publications.cover.type")
								.get(type);
						if (size != null && !"".equals(size)) {// 参数type是一个有效的参数（在Param.properties文件中设置过）
							String ext = FileUtil.getFix(coverPath);// 获取后缀名
							String withOutExt = FileUtil.clearExt(coverPath);// 去掉后缀名
							String thumbPath = withOutExt + "_" + size + "."
									+ ext;// 拼接缩略图路径
							if (FileUtil.isExist(thumbPath)) {// 缩略图是否存在
								coverPath = thumbPath;// 显示缩略图
							}
						}
					}
					InputStream in = new FileInputStream(coverPath);
					int i;
					response.setContentType("image/jpeg");
					OutputStream out = response.getOutputStream();
					while ((i = in.read()) != -1) {
						out.write(i);
					}
					out.close();
				}
			}
		} catch (Exception e) {
			// coverPath="D://upload/1.gif";//设置没有图片的封面写死的或者从数据库里区都行
		}
	}

	/**
	 * 二级 英文页面
	 */
	@RequestMapping(value = "readingBookEn")
	public ModelAndView readingBookEn(HttpServletRequest request,
			HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "publications/enBookList";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			BInstitution ins = (BInstitution) request.getSession()
					.getAttribute("institution");
			form.setUrl(request.getRequestURL().toString());
			// 总数
			List<ResourcesSum> sumList = this.resourcesSumService.getList(null,
					"");
			if (sumList != null && sumList.size() > 0) {
				model.put("count", sumList.get(0).getBookCountEn());
				// System.out.println("英文：" + sumList.get(0).getSumCount());
			}
			// 左侧学科分类
			Map<String, Object> subCondition = new HashMap<String, Object>();
			subCondition.put("treeCodeLength", 6);
			List<BSubject> subList = this.bSubjectService.getSubList(
					subCondition, " order by a.order ");
			model.put("insInfo", ins == null ? null : ins.getId());
			model.put("subList", subList);
			model.put("form", form);
		} catch (Exception e) {
			e.printStackTrace();
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 二级页面-热读资源【非静态化】
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @RequestMapping(value = "hotReadingBook") public ModelAndView
	 *                       hotReadingBook(HttpServletRequest request,
	 *                       HttpServletResponse response, IndexForm form)
	 *                       throws Exception { String forwardString =
	 *                       "publications/index/hotReading"; Map<String,
	 *                       Object> model = new HashMap<String, Object>(); try
	 *                       { Map<String, Object> hotcondition = new
	 *                       HashMap<String, Object>(); hotcondition.put("type",
	 *                       2); hotcondition.put("pubStatus", 2);// 上架
	 *                       hotcondition.put("pubType", 1);
	 * 
	 *                       String en = request.getParameter("enBook"); if
	 *                       ("yes".equals(en)) { hotcondition.put("languageEn",
	 *                       "ch%"); forwardString =
	 *                       "publications/index/hotReadingEn"; } else {
	 *                       hotcondition.put("language", "ch%"); } if
	 *                       (request.getSession().getAttribute("institution")
	 *                       != null) { hotcondition.put("institutionId",
	 *                       ((BInstitution)
	 *                       request.getSession().getAttribute("institution"
	 *                       )).getId()); hotcondition.put("license", "true");//
	 *                       IP范围内时仅显示license有效的出版物，ip范围外不考虑license是否有效 }
	 *                       List<LAccess> hotlist =
	 *                       this.logAOPService.getLogOfHotReading(hotcondition,
	 *                       " group by a.publications.id order by count(*) desc "
	 *                       , 24, 0); if (hotlist != null &&
	 *                       !hotlist.isEmpty()) { for (LAccess a : hotlist) {
	 *                       Map<String, Object> pubCondition = new
	 *                       HashMap<String, Object>(); pubCondition.put("id",
	 *                       a.getPublications().getId()); List<PPublications>
	 *                       pubList = this.pPublicationsService.getPubList3(
	 *                       pubCondition, " order by a.createOn ", (CUser)
	 *                       request.getSession().getAttribute("mainUser"),
	 *                       IpUtil.getIp(request));
	 *                       a.setPublications(pubList.get(0)); } } //
	 *                       model.put("en",
	 *                       en!=null?Integer.parseInt(en):null);
	 *                       model.put("hotlist", hotlist); } catch (Exception
	 *                       e) { e.printStackTrace(); form.setMsg((e instanceof
	 *                       CcsException) ? Lang.getLanguage(((CcsException)
	 *                       e).getPrompt(),
	 *                       request.getSession().getAttribute("lang"
	 *                       ).toString()) : e.getMessage()); forwardString =
	 *                       "error"; } return new ModelAndView(forwardString,
	 *                       model); }
	 */
	/**
	 * 中/外文电子书二级页面-热读资源-静态化
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "hotReadingBook")
	public void hotReadingBook(HttpServletRequest request,
			HttpServletResponse response, IndexForm form) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Map<String, Object> hotcondition = new HashMap<String, Object>();
			BInstitution ins = (BInstitution) request.getSession()
					.getAttribute("institution");
			int num;
			hotcondition.put("type", 2);
			hotcondition.put("pubStatus", 2);// 上架
			hotcondition.put("pubType", 1);
			String en = request.getParameter("enBook");
			if ("yes".equals(en)) {
				hotcondition.put("languageEn", "ch%");
				num = 18;
			} else {
				hotcondition.put("language", "ch%");
				num = 24;
			}
			if (request.getSession().getAttribute("institution") != null) {
				hotcondition.put("institutionId", ((BInstitution) request
						.getSession().getAttribute("institution")).getId());
				hotcondition.put("license", "true");// IP范围内时仅显示license有效的出版物，ip范围外不考虑license是否有效
			}
			List<LAccess> hotlist = this.logAOPService.getLogOfHotReading(
					hotcondition,
					" group by a.publications.id order by count(*) desc ", num,
					0);
			if (hotlist != null && !hotlist.isEmpty()) {
				for (LAccess a : hotlist) {
					Map<String, Object> pubCondition = new HashMap<String, Object>();
					pubCondition.put("id", a.getPublications().getId());
					List<PPublications> pubList = this.pPublicationsService
							.getPubList3(
									pubCondition,
									" order by a.createOn ",
									(CUser) request.getSession().getAttribute(
											"mainUser"), IpUtil.getIp(request));
					a.setPublications(pubList.get(0));
				}
			}
			model.put("hotlist", hotlist);
			model.put("request", request);
			String path = Param.getParam("config.website.path.hotReading")
					.get("path").replace("-", ":");
			if ("yes".equals(en)) {
				if (ins == null) {
					FileUtil.generateHTML("pages/ftl",
							"English_HotReading.ftl",
							"english_hotReading_all.html", model, request
									.getSession().getServletContext(), path);
				} else {
					FileUtil.generateHTML("pages/ftl",
							"English_HotReading.ftl", "english_hotReading_"
									+ ins.getId() + ".html", model, request
									.getSession().getServletContext(), path);
				}
			} else {
				if (ins == null) {
					FileUtil.generateHTML("pages/ftl",
							"Chinese_HotReading.ftl",
							"chinese_hotReading_all.html", model, request
									.getSession().getServletContext(), path);
				} else {
					FileUtil.generateHTML("pages/ftl",
							"Chinese_HotReading.ftl", "chinese_hotReading_"
									+ ins.getId() + ".html", model, request
									.getSession().getServletContext(), path);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 中电子期刊二级页面-热读资源-静态化
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "hotReadingJournalCn")
	public void hotReadingJournalCn(HttpServletRequest request,
			HttpServletResponse response, IndexForm form) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Map<String, Object> hotcondition = new HashMap<String, Object>();
			BInstitution ins = (BInstitution) request.getSession()
					.getAttribute("institution");
			int num;
			hotcondition.put("type", 2);
			hotcondition.put("pubStatus", 2);// 上架
			hotcondition.put("pubType", 2);
			/* hotcondition.put("journalType", 2); */
			String en = request.getParameter("enBook");
			if ("yes".equals(en)) {
				hotcondition.put("languageEn", "ch%");
				num = 18;
			} else {
				hotcondition.put("language", "ch%");
				num = 24;
			}
			if (request.getSession().getAttribute("institution") != null) {
				hotcondition.put("institutionId", ((BInstitution) request
						.getSession().getAttribute("institution")).getId());
				hotcondition.put("license", "true");// IP范围内时仅显示license有效的出版物，ip范围外不考虑license是否有效
			}
			List<LAccess> hotlist = this.logAOPService.getLogOfHotReading(
					hotcondition,
					" group by a.publications.id order by count(*) desc ", num,
					0);
			if (hotlist != null && !hotlist.isEmpty()) {
				for (LAccess a : hotlist) {
					Map<String, Object> pubCondition = new HashMap<String, Object>();
					pubCondition.put("id", a.getPublications().getId());
					List<PPublications> pubList = this.pPublicationsService
							.getPubList3(
									pubCondition,
									" order by a.createOn ",
									(CUser) request.getSession().getAttribute(
											"mainUser"), IpUtil.getIp(request));
					a.setPublications(pubList.get(0));
				}
			}
			model.put("hotlist", hotlist);
			model.put("request", request);
			String path = Param
					.getParam("config.website.path.hotReadingJournalCn")
					.get("path").replace("-", ":");
			if ("yes".equals(en)) {
				if (ins == null) {
					FileUtil.generateHTML("pages/ftl",
							"English_HotReading.ftl",
							"english_hotReading_all.html", model, request
									.getSession().getServletContext(), path);
				} else {
					FileUtil.generateHTML("pages/ftl",
							"English_HotReading.ftl", "english_hotReading_"
									+ ins.getId() + ".html", model, request
									.getSession().getServletContext(), path);
				}
			} else {
				if (ins == null) {
					FileUtil.generateHTML("pages/ftl",
							"Chinese_HotReading.ftl",
							"chinese_hotReading_all.html", model, request
									.getSession().getServletContext(), path);
				} else {
					FileUtil.generateHTML("pages/ftl",
							"Chinese_HotReading.ftl", "chinese_hotReading_"
									+ ins.getId() + ".html", model, request
									.getSession().getServletContext(), path);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 期刊主页
	 * 
	 * @param subCode
	 *            用来查找子分类、all为全部父分类
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/index")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		String forwardString = "publications/journalList";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			form.setUrl(request.getRequestURL().toString());
			// 期刊的资源总数
			List<ResourcesSum> listsum = this.resourcesSumService.getList(null,
					"");
			if (listsum != null && listsum.size() > 0) {
				model.put("journalsCount", listsum.get(0).getEnJournal());
				model.put("articlesCount", listsum.get(0).getArticleCount());
			}
			Map<String, Object> condition = new HashMap<String, Object>();
			// 可读资源 1。机构外且未登录的 免费和开源 2.机构内登录或未登录都是 显示试用和购买的
			CUser user = request.getSession() == null ? null : (CUser) request
					.getSession().getAttribute("mainUser");
			if (request.getSession().getAttribute("mainUser") == null
					&& request.getSession().getAttribute("institution") == null) {
				condition.put("free", 2);
				condition.put("oa", 2);
				List<PPublications> list = this.pPublicationsService
						.getpublicationsList(condition, null);
				model.put("list", list);
			}

			// 左侧学科分类
			Map<String, Object> subCondition = new HashMap<String, Object>();
			subCondition.put("treeCodeLength", 6);
			List<BSubject> subList = this.bSubjectService.getSubList(
					subCondition, " order by a.order ");

			model.put("subList", subList);

			// 获取机构ID
			BInstitution ins = (BInstitution) request.getSession()
					.getAttribute("institution");
			model.put("insInfo", ins == null ? null : ins.getId());
		} catch (Exception e) {
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/form/indexCn")
	public ModelAndView indexCn(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		String forwardString = "publications/journalListCn";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			form.setUrl(request.getRequestURL().toString());
			// 期刊的资源总数
			List<ResourcesSum> listsum = this.resourcesSumService.getList(null,
					"");
			if (listsum != null && listsum.size() > 0) {
				model.put("journalsCount", listsum.get(0).getCnJournal());
				// model.put("articlesCount", listsum.get(0).getArticleCount());
			}
			Map<String, Object> condition = new HashMap<String, Object>();
			// 可读资源 1。机构外且未登录的 免费和开源 2.机构内登录或未登录都是 显示试用和购买的
			CUser user = request.getSession() == null ? null : (CUser) request
					.getSession().getAttribute("mainUser");
			if (request.getSession().getAttribute("mainUser") == null
					&& request.getSession().getAttribute("institution") == null) {
				condition.put("free", 2);
				condition.put("oa", 2);
				List<PPublications> list = this.pPublicationsService
						.getpublicationsList(condition, null);
				model.put("list", list);
			}

			// 左侧学科分类
			Map<String, Object> subCondition = new HashMap<String, Object>();
			subCondition.put("treeCodeLength", 6);
			List<BSubject> subList = this.bSubjectService.getSubList(
					subCondition, " order by a.order ");

			model.put("subList", subList);

			// 获取机构ID
			BInstitution ins = (BInstitution) request.getSession()
					.getAttribute("institution");
			String insInfo = "";
			if (null == ins) {
				if (null != user) {
					insInfo = user.getInstitution().getId();
				}
			}
			model.put("insInfo", ins == null ? "".equals(insInfo) ? null
					: insInfo : ins.getId());
		} catch (Exception e) {
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 最新资源
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/lastPubsBook")
	public ModelAndView lastPubsBook(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		String forwardString = "publications/index/pubList";
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			String ins_Id = "";
			Integer num = 24;
			CUser cs = (CUser) request.getSession().getAttribute("mainUser");
			if (request.getParameter("num") != null
					&& !"".equals(request.getParameter("num").toString())) {
				num = Integer.valueOf(request.getParameter("num").toString());
			}
			if (request.getSession().getAttribute("institution") != null) {
				if (request.getSession().getAttribute("mainUser") != null) {
					CUser user = (CUser) request.getSession().getAttribute(
							"mainUser");
					if (user.getLevel() == 2) {// 不是图书馆管理员
						ins_Id = ((BInstitution) request.getSession()
								.getAttribute("institution")).getId();
					}
				} else {
					ins_Id = ((BInstitution) request.getSession().getAttribute(
							"institution")).getId();
				}
			}
			String isCn = request.getParameter("isCn");
			// System.out.println(isCn);

			// IP范围外的全部看到的是全局的
			if ("".equals(ins_Id)) {
				// condition.put("status", 2);//已上架
				// condition.put("typeArr", new Integer[]{1,2});
				// model.put("list",this.pPublicationsService.getPubSimplePageList(condition,
				// " order by a.createDate desc ",num,0));
				if (cs != null && cs.getLevel() == 2) {
					condition.put("isCn", isCn);
					condition.put("status", 1);// license有效
					ins_Id = cs.getInstitution().getId();
					// condition.put("searchType",new Integer[]{1,2});
					condition.put("institutionId", ins_Id);
					condition.put("isTrail", "0");
					condition.put("pubType", 1);
					List<LLicense> licenseList = this.customService
							.getLicensePagingListForIndex(condition,
									" order by a.createdon desc ", num, 0);
					if (licenseList.size() < 24) {
						condition.clear();
						Map<String, Object> resultMap = null;
						if ("false".equals(isCn)) {
							resultMap = publicationsIndexService
									.searchNewBooksEn(0, num);
						} else {
							resultMap = publicationsIndexService
									.searchNewBooks(0, num);
						}

						if (resultMap.get("count") != null
								&& Long.valueOf(resultMap.get("count")
										.toString()) > 0) {
							List<PPublications> resultList = new ArrayList<PPublications>();
							List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap
									.get("result");
							for (Map<String, Object> idInfo : list) {
								// 根据ID查询产品信息
								// 由于加入了标签，这里不能用get查询
								PPublications pub = this.pPublicationsService
										.getPublications(idInfo.get("id")
												.toString());
								if (pub != null
										&& (pub.getAvailable() == null ? 0
												: pub.getAvailable()) != 3) {
									if (pub.getRemark() != null
											&& "[无简介]".equals(pub.getRemark())) {
										pub.setRemark("");
									}
									resultList.add(pub);
								}
							}
							model.put("list", resultList);
						}

					} else {
						model.put("list", licenseList);
						forwardString = "publications/index/licenseList";
					}

				} else {
					Map<String, Object> resultMap = null;
					if ("false".equals(isCn)) {
						resultMap = publicationsIndexService.searchNewBooksEn(
								0, num);
					} else {
						resultMap = publicationsIndexService.searchNewBooks(0,
								num);
					}

					if (resultMap.get("count") != null
							&& Long.valueOf(resultMap.get("count").toString()) > 0) {
						List<PPublications> resultList = new ArrayList<PPublications>();
						List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap
								.get("result");
						for (Map<String, Object> idInfo : list) {
							// 根据ID查询产品信息
							// 由于加入了标签，这里不能用get查询
							PPublications pub = this.pPublicationsService
									.getPublications(idInfo.get("id")
											.toString());
							if (pub != null
									&& (pub.getAvailable() == null ? 0 : pub
											.getAvailable()) != 3) {
								if (pub.getRemark() != null
										&& "[无简介]".equals(pub.getRemark())) {
									pub.setRemark("");
								}
								resultList.add(pub);
							}
						}
						model.put("list", resultList);
					}
				}

			} else {
				condition.put("isCn", isCn);
				condition.put("status", 1);// license有效
				// condition.put("searchType",new Integer[]{1,2});
				condition.put("institutionId", ins_Id);
				condition.put("isTrail", "0");
				condition.put("pubType", 1);
				List<LLicense> licenseList = this.customService
						.getLicensePagingListForIndex(condition,
								" order by a.createdon desc ", num, 0);
				if (licenseList.size() >= 24) {
					model.put("list", licenseList);
					forwardString = "publications/index/licenseList";
				} else {
					condition.clear();
					Map<String, Object> resultMap = null;
					if ("false".equals(isCn)) {
						resultMap = publicationsIndexService.searchNewBooksEn(
								0, num);
					} else {
						resultMap = publicationsIndexService.searchNewBooks(0,
								num);
					}

					if (resultMap.get("count") != null
							&& Long.valueOf(resultMap.get("count").toString()) > 0) {
						List<PPublications> resultList = new ArrayList<PPublications>();
						List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap
								.get("result");
						for (Map<String, Object> idInfo : list) {
							// 根据ID查询产品信息
							// 由于加入了标签，这里不能用get查询
							PPublications pub = this.pPublicationsService
									.getPublications(idInfo.get("id")
											.toString());
							if (pub != null
									&& (pub.getAvailable() == null ? 0 : pub
											.getAvailable()) != 3) {
								if (pub.getRemark() != null
										&& "[无简介]".equals(pub.getRemark())) {
									pub.setRemark("");
								}
								resultList.add(pub);
							}
						}
						model.put("list", resultList);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			forwardString = "error";
			request.setAttribute(
					"message",
					(e instanceof CcsException) ? Lang.getLanguage(
							((CcsException) e).getPrompt(), request
									.getSession().getAttribute("lang")
									.toString()) : e.getMessage());
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 最新资源journal
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/lastPubsJournalCn")
	public void lastPubsJournalCn(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = (CUser) request.getSession().getAttribute("mainUser");
			BInstitution ins = (BInstitution) request.getSession()
					.getAttribute("institution");
			long ip = IpUtil.getLongIp(IpUtil.getIp(request));
			// 查询机构信息
			// Map<String,Object> mapip = new HashMap<String,Object>();
			Map<String, Object> ucMap = new HashMap<String, Object>();
			String cn = request.getParameter("cn");
			if ("yes".equals(cn)) {
				ucMap.put("language", "ch%");
			} else {
				ucMap.put("languageEn", "ch%");
			}
			ucMap.put("ip", ip);
			/* ucMap.put("type", 4); */
			ucMap.put("ava", 2);
			ucMap.put("pStatus", 2);
			ucMap.put("pType", 2); // 期刊
			ucMap.put("noLicense", true);
			ucMap.put("noOrder", true);
			if (ins != null) {
				ucMap.put("pInsId", ins.getId());
			}
			ucMap.put("pUserId", user == null ? null : user.getId());
			List<LAccess> list = this.logAOPService
					.getLogOfHotReadingForRecommad(ucMap,
							" group by b.id order by count(a.id) desc ", 24, 0);
			model.put("list", list);
			model.put("request", request);
			String path = Param.getParam("config.website.path").get("path")
					.replace("-", ":");
			FileUtil.generateHTML("pages/ftl", "Journal_ReadableNewCn.ftl",
					"Journal_ReadableNewCn.html", model, request.getSession()
							.getServletContext(), path);
		} catch (Exception e) {
			e.printStackTrace();
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
		}
		model.put("form", form);
	}

	/**
	 * 二级页面-外文电子期刊-热读文章
	 * 
	 * @param
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 * @RequestMapping(value = "/hotReading") public ModelAndView
	 *                       hotReading(HttpServletRequest request,
	 *                       HttpServletResponse response, PPublicationsForm
	 *                       form) throws Exception { String forwardString =
	 *                       "publications/index/hotReadingArticle"; Map<String,
	 *                       Object> model = new HashMap<String, Object>(); try
	 *                       { // 热读资源 Map<String, Object> hotcondition = new
	 *                       HashMap<String, Object>(); hotcondition.put("type",
	 *                       2); hotcondition.put("pubStatus", 2);// 上架
	 *                       hotcondition.put("pubtype", 4);// 期刊
	 *                       //hotcondition.put("author", " ");
	 *                       hotcondition.put("publisher",* " "); String ins_Id
	 *                       = ""; // Ip范围内 if
	 *                       (request.getSession().getAttribute("institution")
	 *                       != null) { if
	 *                       (request.getSession().getAttribute("mainUser") !=
	 *                       null) { CUser user = (CUser)
	 *                       request.getSession().getAttribute("mainUser"); if
	 *                       (user.getLevel() != 2) {// 不是图书馆管理员 ins_Id =
	 *                       ((BInstitution)
	 *                       request.getSession().getAttribute("institution"
	 *                       )).getId(); } } else { ins_Id = ((BInstitution)
	 *                       request
	 *                       .getSession().getAttribute("institution")).getId();
	 *                       } }// IP范围外的全部看到的是全局的
	 *                       hotcondition.put("institutionId", ins_Id); if
	 *                       (request.getSession().getAttribute("institution")
	 *                       != null) { hotcondition.put("institutionId",
	 *                       ((BInstitution)
	 *                       request.getSession().getAttribute("institution"
	 *                       )).getId()); hotcondition.put("license", "true");//
	 *                       IP范围内时仅显示license有效的出版物，ip范围外不考虑license是否有效 }
	 *                       List<LAccess> hotlist =
	 *                       this.logAOPService.getJournalHotReading
	 *                       (hotcondition,
	 *                       " group by a.publications.id order by count(*) desc "
	 *                       , 12, 0); if (hotlist != null &&
	 *                       !hotlist.isEmpty()) { for (LAccess a : hotlist) {
	 *                       Map<String, Object> pubCondition = new
	 *                       HashMap<String, Object>(); pubCondition.put("id",
	 *                       a.getPublications().getId()); List<PPublications>
	 *                       pubList = this.pPublicationsService.getArticleList(
	 *                       pubCondition, " order by a.createOn ", (CUser)
	 *                       request.getSession().getAttribute("mainUser"),
	 *                       IpUtil.getIp(request)); if (pubList != null &&
	 *                       !pubList.isEmpty()) {
	 *                       a.setPublications(pubList.get(0)); } } }
	 *                       model.put("hotlist", hotlist); } catch (Exception
	 *                       e) { form.setMsg((e instanceof CcsException) ?
	 *                       Lang.getLanguage(((CcsException) e).getPrompt(),
	 *                       request
	 *                       .getSession().getAttribute("lang").toString()) :
	 *                       e.getMessage()); forwardString = "error"; }
	 *                       model.put("form", form); return new
	 *                       ModelAndView(forwardString, model); }
	 */
	/**
	 * 二级页面-外文电子期刊-热读文章-静态化
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/hotReading")
	public void hotReading(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// 热读资源
			Map<String, Object> hotcondition = new HashMap<String, Object>();
			hotcondition.put("type", 2);
			hotcondition.put("pubStatus", 2);// 上架
			// hotcondition.put("pubtype", 2);// 期刊
			// hotcondition.put("author", " "); hotcondition.put("publisher",*
			// " ");
			String cn = request.getParameter("cn");
			int page = 12;
			if ("yes".equals(cn)) {
				hotcondition.put("language", "ch%");
				hotcondition.put("pubtype", 7);// 期刊
				page = 24;
			} else {
				hotcondition.put("languageEn", "ch%");
				hotcondition.put("pubtype", 4);// 文章
			}
			String ins_Id = "";
			// Ip范围内
			CUser user = (CUser) request.getSession().getAttribute("mainUser");
			BInstitution ins = (BInstitution) request.getSession()
					.getAttribute("institution");
			if (null == ins) {
				if (null != user) {
					ins = user.getInstitution();
				}
			}

			if (ins != null) {
				if (user != null) {
					// CUser user = (CUser)
					// request.getSession().getAttribute("mainUser");
					if (user.getLevel() != 2) {// 不是图书馆管理员
						ins_Id = ins.getId();
					}
				} else {
					ins_Id = ins.getId();
				}
			} // IP范围外的全部看到的是全局的
				// hotcondition.put("institutionId", ins.getId());
			if (ins != null) {
				hotcondition.put("institutionId", ins.getId());
				hotcondition.put("license", "true");// IP范围内时仅显示license有效的出版物，ip范围外不考虑license是否有效
			}
			List<LAccess> hotlist = this.logAOPService.getJournalHotReading(
					hotcondition,
					" group by a.publications.id order by count(*) desc ",
					page, 0);
			if (hotlist != null && !hotlist.isEmpty()) {
				for (LAccess a : hotlist) {
					Map<String, Object> pubCondition = new HashMap<String, Object>();
					pubCondition.put("id", a.getPublications().getId());
					if ("yes".equals(cn)) {
						pubCondition.put("language", "ch%");
					} else {
						pubCondition.put("languageEn", "ch%");
					}
					List<PPublications> pubList = this.pPublicationsService
							.getArticleList(pubCondition,
									" order by a.createOn ", user,
									IpUtil.getIp(request));
					if (pubList != null && !pubList.isEmpty()) {
						a.setPublications(pubList.get(0));
					}
				}
			}
			model.put("hotlist", hotlist);
			model.put("request", request);
			model.put("license", ins == null ? "false" : "true");
			String path = Param
					.getParam("config.website.path.journal_hotReading")
					.get("path").replace("-", ":");
			if ("yes".equals(cn)) {
				FileUtil.generateHTML("pages/ftl",
						"Journal_HotReadingArticleCn.ftl",
						"Journal_HotReadingArticle_all_cn.html", model, request
								.getSession().getServletContext(), path);
			} else {
				if (ins != null && ins.getId() != null
						&& !ins.getId().equals("")) {
					FileUtil.generateHTML("pages/ftl",
							"Journal_HotReadingArticle.ftl",
							"Journal_HotReadingArticle_" + ins.getId()
									+ ".html", model, request.getSession()
									.getServletContext(), path);
				} else {
					FileUtil.generateHTML("pages/ftl",
							"Journal_HotReadingArticle.ftl",
							"Journal_HotReadingArticle_all.html", model,
							request.getSession().getServletContext(), path);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.put("form", form);
	}

	/**
	 * 期刊主页可读资源
	 * 
	 * @param
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/readableResource")
	public ModelAndView readableResource(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		String forwardString = "publications/index/readableResource";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = request.getSession() == null ? null : (CUser) request
					.getSession().getAttribute("mainUser");
			Map<String, Object> condition = form.getCondition();

			// condition.put("isLicense", form.getIsLicense());//是否订阅
			condition.put("userId", user == null ? "" : user.getId());// 用户ID
			condition.put("ip", IpUtil.getIp(request));// 用户ID
			if (form.getIsLicense() == null || "".equals(form.getIsLicense())) {
				condition.put("status", 2);
			} else {
				condition.put("check", "false");
			}
			condition.put("dtype", 5);// 排除数据库
			String sort = "order by a.id";
			if (form.getOrderDesc() != null
					&& "upDesc".equalsIgnoreCase(form.getOrderDesc())) {
				sort = " order by a.updateOn desc ";
			} else if (form.getOrderDesc() != null
					&& "pubDesc".equalsIgnoreCase(form.getOrderDesc())) {
				sort = " order by a.pubDate desc ";
			}
			form.setUrl(request.getRequestURL().toString());

			condition.put("typeArr", new Integer[] { 1, 2 });// 只查询期刊和图书
			condition.put("level", 2);
			condition.put("licenseStatus", 1);
			String ins_Id = null;
			if (request.getSession().getAttribute("institution") != null) {
				ins_Id = ((BInstitution) request.getSession().getAttribute(
						"institution")).getId();
			}
			condition.put("institutionId", ins_Id);
			List<PPublications> list = null;
			if (form.getIsLicense() == null || "".equals(form.getIsLicense())) {
				form.setCount(this.pPublicationsService
						.getPubCountO1(condition));
				list = this.pPublicationsService.getPubPageList(condition, " ",
						form.getPageCount(), form.getCurpage(), user, IpUtil
								.getIp(request).toString());
			} else {
				// 由于要查询出OA和免费
				condition.put("oafreeUid", Param.getParam("OAFree.uid.config")
						.get("uid"));
				form.setCount(this.pPublicationsService
						.getPubSubscriptionCount(condition));
				list = this.pPublicationsService.getPubSubscriptionPageList(
						condition, " ", form.getPageCount(), form.getCurpage(),
						user, IpUtil.getIp(request).toString());
				// list =
				// this.pPublicationsService.getLicensePubPagingList(condition,
				// " ",
				// form.getPageCount(),form.getCurpage(),user,IpUtil.getIp(request).toString());
			}
			model.put("subjectId", form.getSubParentId());
			model.put("ptype", form.getType());
			model.put("pubYear", form.getPubYear());
		} catch (Exception e) {
			request.setAttribute(
					"message",
					(e instanceof CcsException) ? Lang.getLanguage(
							((CcsException) e).getPrompt(), request
									.getSession().getAttribute("lang")
									.toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 期刊主页推荐期刊
	 * 
	 * @param
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/recJournal")
	public ModelAndView recJournal(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			PPublicationsForm form) throws Exception {
		String forwardString = "publications/index/recommend";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = (CUser) request.getSession().getAttribute("mainUser");
			BInstitution ins = (BInstitution) request.getSession()
					.getAttribute("institution");
			long ip = IpUtil.getLongIp(IpUtil.getIp(request));
			// 查询机构信息
			// Map<String,Object> mapip = new HashMap<String,Object>();
			Map<String, Object> ucMap = new HashMap<String, Object>();
			ucMap.put("ip", ip);
			/* ucMap.put("type", 4); */
			ucMap.put("ava", 2);
			ucMap.put("pStatus", 2);
			ucMap.put("pType", 2); // 期刊
			ucMap.put("noLicense", true);
			ucMap.put("noOrder", true);
			if (ins != null) {
				ucMap.put("pInsId", ins.getId());
			}
			ucMap.put("pUserId", user == null ? null : user.getId());
			List<LAccess> list = this.logAOPService
					.getLogOfHotReadingForRecommad(ucMap,
							" group by b.id order by count(a.id) desc ", 24, 0);
			model.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
		/*
		 * String forwardString="publications/index/recommend";
		 * Map<String,Object> model = new HashMap<String,Object>(); try{ CUser
		 * user = (CUser) session.getAttribute("mainUser"); Map<String,Object>
		 * recJcondition = new HashMap<String,Object>(); String type =
		 * request.getParameter("type"); if("2".equals(type)){
		 * recJcondition.put("recommendType", type); } List<Recommend> recList =
		 * this.pPublicationsService.getRecommendList(recJcondition,"",12,0);
		 * if(recList!=null && !recList.isEmpty()){ for(Recommend a:recList){
		 * Map<String,Object> pubCondition=new HashMap<String, Object>();
		 * System.out.println(a.getPublications().getId()); Map<String, Object>
		 * condition = new HashMap<String, Object>(); condition.put("parentid",
		 * a.getPublications().getId()); condition.put("isLicense2",
		 * "true");//已上架的或已订阅的 condition.put("orOnSale", "true");
		 * condition.put("check", "false"); condition.put("ip",
		 * IpUtil.getIp(request)); if(user!=null){ condition.put("userId",
		 * user.getId()); } condition.put("type", 6);//卷 Integer
		 * volCount=this.pPublicationsService
		 * .getPubCount(condition);//期刊中的已上架的卷总数 condition.put("type",7);//期
		 * Integer
		 * issCount=this.pPublicationsService.getPubCount(condition);//期刊中的已上架的期总数
		 * condition.put("type", 4);//文章 Integer
		 * artCount=this.pPublicationsService
		 * .getPubCount(condition);//期刊中的已上架的文章总数
		 * 
		 * System.out.println(volCount); System.out.println(issCount);
		 * System.out.println(artCount);
		 * pubCondition.put("id",a.getPublications().getId());
		 * List<PPublications>
		 * pubList=this.pPublicationsService.getArticleList(pubCondition,
		 * " order by a.createOn ",
		 * (CUser)request.getSession().getAttribute("mainUser"
		 * ),IpUtil.getIp(request));
		 * 
		 * if(pubList!=null &&!pubList.isEmpty()){
		 * pubList.get(0).setVolumeCode(volCount+"");
		 * pubList.get(0).setIssueCode(issCount+"");
		 * pubList.get(0).setTreecode(artCount+"");
		 * a.setPublications(pubList.get(0)); } } } model.put("recList",
		 * recList); }catch(Exception e){ form.setMsg((e instanceof
		 * CcsException)
		 * ?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession
		 * ().getAttribute("lang").toString()):e.getMessage());
		 * forwardString="error"; } model.put("form", form); return new
		 * ModelAndView(forwardString, model);
		 */
	}

	@RequestMapping(value = "/form/recJournalStatic")
	public void recJournalStatic(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			PPublicationsForm form) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = (CUser) request.getSession().getAttribute("mainUser");
			BInstitution ins = (BInstitution) request.getSession()
					.getAttribute("institution");
			if (null == ins) {
				if (null != user) {
					ins = user.getInstitution();
				}
			}
			long ip = IpUtil.getLongIp(IpUtil.getIp(request));
			// 查询机构信息
			// Map<String,Object> mapip = new HashMap<String,Object>();
			Map<String, Object> ucMap = new HashMap<String, Object>();
			ucMap.put("ip", ip);
			/* ucMap.put("type", 4); */
			ucMap.put("ava", 2);
			ucMap.put("pStatus", 2);
			ucMap.put("pType", 2); // 期刊
			String cn = request.getParameter("cn");
			if ("yes".equals(cn)) {
				ucMap.put("language", "ch%");
			} else {
				ucMap.put("languageEn", "ch%");
			}
			if (ins != null) {
				ucMap.put("noLicense", true);
			}
			ucMap.put("noOrder", true);
			if (ins != null) {
				ucMap.put("pInsId", ins.getId());
			}
			ucMap.put("pUserId", user == null ? null : user.getId());
			List<LAccess> list = this.logAOPService
					.getLogOfHotReadingForRecommad(ucMap,
							" group by b.id order by count(a.id) desc ", 24, 0);
			model.put("list", list);
			model.put("request", request);
			String path = Param
					.getParam("config.website.path.journal_recJournal")
					.get("path").replace("-", ":");

			if ("yes".equals(cn)) {
				if (ins == null) {
					FileUtil.generateHTML("pages/ftl", "RecJournalCn.ftl",
							"recJournal_all_cn.html", model, request
									.getSession().getServletContext(), path);
				} else {
					FileUtil.generateHTML("pages/ftl", "RecJournalCn.ftl",
							"recJournal_" + ins.getId() + "_cn.html", model,
							request.getSession().getServletContext(), path);
				}
			} else {
				if (ins == null) {
					FileUtil.generateHTML("pages/ftl", "RecJournal.ftl",
							"recJournal_all.html", model, request.getSession()
									.getServletContext(), path);
				} else {
					FileUtil.generateHTML("pages/ftl", "RecJournal.ftl",
							"recJournal_" + ins.getId() + ".html", model,
							request.getSession().getServletContext(), path);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
		}
		model.put("form", form);
	}

	/**
	 * 外文电子期刊二级页面-可读资源-无静态化
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 * @throws Exception
	 * @RequestMapping(value = "/form/readable") public ModelAndView
	 *                       readable(HttpServletRequest request,
	 *                       HttpServletResponse response, HttpSession session,
	 *                       LLicenseForm form) throws Exception { String
	 *                       forwardString = "publications/index/readable";
	 *                       Map<String, Object> model = new HashMap<String,
	 *                       Object>(); try { CUser user = (CUser)
	 *                       session.getAttribute("mainUser"); Map<String,
	 *                       Object> condition = form.getCondition();
	 *                       condition.put("available", 3);// 这是过滤图书是政治原因的条件
	 *                       condition.put("ptype", 2);// 类型-2
	 *                       condition.put("isOaorFree", 2);// 免费或者开源
	 * 
	 *                       List<PPublications> list = null; form.setRange(2);
	 * 
	 *                       list = this.pPublicationsService.getpubListIsNew(
	 *                       condition, " ", 24, 0); if (list != null &&
	 *                       !list.isEmpty()) { for (PPublications a : list) {
	 *                       Map<String, Object> pubCondition = new
	 *                       HashMap<String, Object>(); Map<String, Object>
	 *                       condition2 = form.getCondition();
	 *                       condition2.put("parentid", a.getId());
	 *                       condition2.put("isLicense2", "true");// 已上架的或已订阅的
	 *                       condition2.put("orOnSale", "true");
	 *                       condition2.put("check", "false");
	 *                       condition2.put("ip", IpUtil.getIp(request)); if
	 *                       (user != null) { condition2.put("userId",
	 *                       user.getId()); } condition2.put("type", 6);// 卷
	 *                       Integer volCount =
	 *                       this.pPublicationsService.getPubCount
	 *                       (condition2);// 期刊中的已上架的卷总数 condition2.put("type",
	 *                       7);// 期 Integer issCount =
	 *                       this.pPublicationsService
	 *                       .getPubCount(condition2);// 期刊中的已上架的期总数
	 *                       condition2.put("type", 4);// 文章 Integer artCount =
	 *                       this
	 *                       .pPublicationsService.getPubCount(condition2);//
	 *                       期刊中的已上架的文章总数
	 * 
	 *                       pubCondition.put("id", a.getId());
	 *                       List<PPublications> pubList =
	 *                       this.pPublicationsService
	 *                       .getArticleList(pubCondition,
	 *                       " order by a.createOn ", (CUser)
	 *                       request.getSession().getAttribute("mainUser"),
	 *                       IpUtil.getIp(request)); if (pubList != null &&
	 *                       !pubList.isEmpty()) {
	 *                       pubList.get(0).setVolumeCode(volCount + "");
	 *                       pubList.get(0).setIssueCode(issCount + "");
	 *                       pubList.get(0).setTreecode(artCount + ""); a =
	 *                       pubList.get(0); } } }
	 * 
	 *                       model.put("list", list); } catch (Exception e) {
	 *                       e.printStackTrace(); form.setMsg((e instanceof
	 *                       CcsException) ? Lang.getLanguage(((CcsException)
	 *                       e).getPrompt(),
	 *                       request.getSession().getAttribute("lang"
	 *                       ).toString()) : e.getMessage()); }
	 *                       model.put("form", form); return new
	 *                       ModelAndView(forwardString, model); }
	 */
	/**
	 * 外文电子期刊二级页面-可读资源-静态化
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/readable")
	public void readable(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, LLicenseForm form)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = (CUser) session.getAttribute("mainUser");
			Map<String, Object> condition = form.getCondition();
			condition.put("available", 3);// 这是过滤图书是政治原因的条件
			condition.put("ptype", 2);// 类型-2
			condition.put("isOaorFree", 2);// 免费或者开源

			List<PPublications> list = null;
			form.setRange(2);
			String cn = request.getParameter("cn");
			if ("yes".equals(cn)) {
				condition.put("language", "ch%");
			} else {
				condition.put("languageEn", "ch%");
			}
			list = this.pPublicationsService.getpubListIsNew(condition, " ",
					24, 0);
			if (list != null && !list.isEmpty()) {
				for (PPublications a : list) {
					Map<String, Object> pubCondition = new HashMap<String, Object>();
					Map<String, Object> condition2 = form.getCondition();
					condition2.put("parentid", a.getId());
					condition2.put("isLicense2", "true");// 已上架的或已订阅的
					condition2.put("orOnSale", "true");
					condition2.put("check", "false");
					condition2.put("ip", IpUtil.getIp(request));
					if (user != null) {
						condition2.put("userId", user.getId());
					}
					condition2.put("type", 6);// 卷
					Integer volCount = this.pPublicationsService
							.getPubCount(condition2);// 期刊中的已上架的卷总数
					condition2.put("type", 7);// 期
					Integer issCount = this.pPublicationsService
							.getPubCount(condition2);// 期刊中的已上架的期总数
					condition2.put("type", 4);// 文章
					Integer artCount = this.pPublicationsService
							.getPubCount(condition2);// 期刊中的已上架的文章总数
					if ("yes".equals(cn)) {
						condition.put("language", "ch%");
					} else {
						condition.put("languageEn", "ch%");
					}
					pubCondition.put("id", a.getId());
					List<PPublications> pubList = this.pPublicationsService
							.getArticleList(
									pubCondition,
									" order by a.createOn ",
									(CUser) request.getSession().getAttribute(
											"mainUser"), IpUtil.getIp(request));
					if (pubList != null && !pubList.isEmpty()) {
						pubList.get(0).setVolumeCode(volCount + "");
						pubList.get(0).setIssueCode(issCount + "");
						pubList.get(0).setTreecode(artCount + "");
						a = pubList.get(0);
					}
				}
			}
			model.put("list", list);
			model.put("request", request);
			String path = Param.getParam("config.website.path").get("path")
					.replace("-", ":");
			if ("yes".equals(cn)) {
				FileUtil.generateHTML("pages/ftl", "Journal_ReadableCn.ftl",
						"Journal_ReadableCn.html", model, request.getSession()
								.getServletContext(), path);
			} else {
				FileUtil.generateHTML("pages/ftl", "Journal_Readable.ftl",
						"Journal_Readable.html", model, request.getSession()
								.getServletContext(), path);
			}

		} catch (Exception e) {
			e.printStackTrace();
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
		}
		model.put("form", form);
	}

	/**
	 * 获取资源
	 * 
	 * @param
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "form/getResource")
	public ModelAndView getResource(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			PPublicationsForm form) throws Exception {
		String forwardString = "";
		Map<String, Object> model = new HashMap<String, Object>();
		boolean inLicense = false;
		String ins_Id = "";
		try {
			if (request.getParameter("pubid") != null
					&& !"".equals(request.getParameter("pubid"))) {
				CUser user = request.getSession().getAttribute("mainUser") == null ? null
						: (CUser) request.getSession().getAttribute("mainUser");
				Map<String, Object> condition = form.getCondition();
				String id = request.getParameter("pubid");
				model.put("id", id);
				PPublications pub = this.pPublicationsService
						.getPublications(id);
				if (pub != null) {
					if (user != null) {
						if (request.getSession().getAttribute("institution") != null) {
							condition.put("status", 1);
							condition.put("pubId", pub.getId());
							if (request.getSession()
									.getAttribute("institution") != null
									|| user.getLevel() == 2) {
								String insId = user.getLevel() == 2 ? user
										.getInstitution().getId() : request
										.getSession().getAttribute(
												"institution") == null ? user
										.getInstitution().getId()
										: ((BInstitution) request.getSession()
												.getAttribute("institution"))
												.getId();
								condition.put("institutionId", insId);
							} else {
								condition.put("userid", user.getId());
							}
							inLicense = this.customService
									.getLicenseResourceCount(condition) > 0;
							// if (!inLicense) {
							// condition.remove("pubId");
							// condition.put("pubCode", pub.getCode());
							// inLicense =
							// this.customService.getLicenseResourceCount(condition)
							// > 0;
							// }
							model.put("pub", pub);
							if (user.getLevel() == 4) {// 中图管理员
								// 阅读
								/*
								 * response.setContentType(
								 * "text/html; charset=gb2312");
								 * response.sendRedirect
								 * (request.getContextPath() +
								 * "/pages/view/form/view?id="+form.getId());
								 */
								// 直接跳转到阅读页
								response.setContentType("text/html; charset=gb2312");
								response.sendRedirect(request.getContextPath()
										+ "/pages/view/form/view?id="
										+ form.getId());
							} else {
								// Boolean
								// isFavourite=pub.getFavorite()>0;//是否已经已经被用户收藏过
								// 是否可以访问
								/*
								 * System.out.println(pub.getSubscribedIp());
								 * System.out.println(pub.getSubscribedUser());
								 */
								// System.out.println(id);
								// System.out.println(pub.getOa());
								// System.out.println(pub.getFree());

								Boolean isLicense = inLicense
										|| pub.getOa() == 2
										|| pub.getFree() == 2;
								// Boolean isLicense = pub.getOa()==2 ||
								// pub.getFree()==2;
								Boolean isRecommand = false;
								BInstitution recIns = null;
								if (!isLicense) {
									// 没有访问权限时才可能需要显示推荐按钮
									// 优先推荐给用户直接相关的机构
									recIns = user.getInstitution() != null ? user
											.getInstitution()
											: (BInstitution) request
													.getSession().getAttribute(
															"institution");
									if (recIns != null) {
										isRecommand = true;
									}
								}
								if (isLicense) {
									if (pub.getType() == 2
											|| pub.getType() == 4) {
										model.put("pageCode", "p1");
										// 显示选择阅读或下载的对话框
										forwardString = "publications/pop1";
									} else if (pub.getType() == 1
											|| pub.getType() == 3) {
										/*
										 * //直接跳转到阅读页 response.setContentType(
										 * "text/html; charset=gb2312");
										 * response
										 * .sendRedirect(request.getContextPath
										 * () +
										 * "/pages/view/form/view?id="+form.
										 * getId());
										 */
										// 直接跳转到阅读页
										model.put("pageCode", "p5");
										forwardString = "publications/pop1";
									} else if (pub.getType() == 7) {
										model.put("pageCode", "p1");
										// 显示选择阅读或下载的对话框
										forwardString = "publications/pop1";
									}
								} else {
									// 访问失败写据访信息
									addLog(pub, user, recIns == null ? null
											: recIns.getId(), null,
											IpUtil.getIp(request), 2, 2, 0, 1);
									if (user.getLevel() == 2) {
										model.put("pageCode", "p2");
										// 购买
										forwardString = "publications/pop1";
									} else if (user.getLevel() == 1
											|| user.getLevel() == 5) {
										if (pub.getType() == 1) {
											model.put("pageCode", "p3");
											// 推荐
											forwardString = "publications/pop1";
										} else if (pub.getType() == 4) {
											model.put("pageCode", "p4");
											// 推荐,添加购物车
											forwardString = "publications/pop1";
										} else if (pub.getType() == 2) {
											model.put("pageCode", "p3");
											// 推荐
											forwardString = "publications/pop1";
										} else if (pub.getType() == 7) {
											model.put("pageCode", "p1");
											// 显示选择阅读或下载的对话框
											forwardString = "publications/pop1";
										}

									}
								}
							}
						} else {
							condition.put("status", 1);
							condition.put("pubId", pub.getId());
							if (request.getSession()
									.getAttribute("institution") != null
									|| user.getLevel() == 2) {
								String insId = user.getLevel() == 2 ? user
										.getInstitution().getId() : request
										.getSession().getAttribute(
												"institution") == null ? user
										.getInstitution().getId()
										: ((BInstitution) request.getSession()
												.getAttribute("institution"))
												.getId();
								condition.put("institutionId", insId);
							} else {
								condition.put("userid", user.getId());
							}
							inLicense = this.customService
									.getLicenseResourceCount(condition) > 0;
							if (!inLicense) {
								// condition.remove("pubId");
								condition.put("pubCode", pub.getCode());
								inLicense = this.customService
										.getLicenseResourceCount(condition) > 0;
							}
							model.put("pub", pub);
							Boolean isLicense = inLicense || pub.getOa() == 2
									|| pub.getFree() == 2;
							// Boolean isLicense = pub.getOa()==2 ||
							// pub.getFree()==2;
							Boolean isRecommand = false;
							BInstitution recIns = null;
							if (isLicense) {
								if (pub.getType() == 2 || pub.getType() == 4) {
									model.put("pageCode", "p1");
									// 显示选择阅读或下载的对话框
									forwardString = "publications/pop1";
								} else if (pub.getType() == 1
										|| pub.getType() == 3) {
									/*
									 * //直接跳转到阅读页 response.setContentType(
									 * "text/html; charset=gb2312");
									 * response.sendRedirect
									 * (request.getContextPath() +
									 * "/pages/view/form/view?id="
									 * +form.getId());
									 */
									// 直接跳转到阅读页
									model.put("pageCode", "p5");
									forwardString = "publications/pop1";
								} else if (pub.getType() == 7) {
									model.put("pageCode", "p1");
									// 显示选择阅读或下载的对话框
									forwardString = "publications/pop1";
								}
							} else {
								Map<String, Object> detailCondition = new HashMap<String, Object>();
								// 如果是机构管理员用户，则直接通过机构Id查询
								if (user.getLevel() == 2) {
									detailCondition.put("institutionId", user
											.getInstitution().getId());
								} else {
									// 其他用户根据用户自己的Id查询，是否可以购买
									detailCondition.put("userid", user.getId());
								}
								// 查询购物车中是否存在

								detailCondition.put("isPubid", pub.getId());
								detailCondition.put("statusArry",
										new Integer[] { 1, 2, 4 });// 状态 1-未处理
																	// 2-已付款未开通
																	// 3-已付款已开通
																	// 4-处理中
																	// 10-未付款已开通
																	// 99-已取消
								List<OOrderDetail> odetailList = this.oOrderService
										.getDetailListForAddCrat(detailCondition);
								if (odetailList != null
										&& odetailList.size() > 0) {
									if (odetailList.get(0).getStatus() == 1) {
										// 资源处于已下单状态时，获取资源提示“您已下定此资源，请耐心等待授权开通
										model.put("pageCode", "s1");
										forwardString = "publications/pop1";
									} else if (odetailList.get(0).getStatus() == 4) {
										// 资源处于购物车中时，获取资源提示“该资源已存在您的购物车中，请到购物车进行结算
										model.put("pageCode", "s2");
										forwardString = "publications/pop1";
									}

								} else {

									model.put("pageCode", "p2");
									// 购买
									forwardString = "publications/pop1";
								}

							}
						}
					} else {
						// 没有用户,在IP范围内，已订阅的资源可以阅读，IP范围外提示登录
						// Ip范围内
						if (request.getSession().getAttribute("institution") != null) {
							ins_Id = ((BInstitution) request.getSession()
									.getAttribute("institution")).getId();
							condition.put("status", 1);
							condition.put("pubId", pub.getId());
							condition.put("institutionId", ins_Id);
							inLicense = this.customService
									.getLicenseResourceCount(condition) > 0;
							// if (!inLicense) {
							// condition.remove("pubId");
							// condition.put("pubCode", pub.getCode());
							// inLicense =
							// this.customService.getLicenseResourceCount(condition)
							// > 0;
							// }
							model.put("pub", pub);
							Boolean isLicense = inLicense || pub.getOa() == 2
									|| pub.getFree() == 2;
							if (isLicense) {
								if (pub.getType() == 1 || pub.getType() == 3) {
									model.put("pageCode", "p5");
									// 阅读
									forwardString = "publications/pop1";
								} else if (pub.getType() == 4
										|| pub.getType() == 2) {
									model.put("pageCode", "p1");
									// 显示选择阅读或下载的对话框
									forwardString = "publications/pop1";
									// 访问失败写据访信息
									addLog(pub, user, ins_Id, null,
											IpUtil.getIp(request), 2, 2, 0, 1);

								} else if (pub.getType() == 7) {
									model.put("pageCode", "p1");
									// 显示选择阅读或下载的对话框
									forwardString = "publications/pop1";
									// 访问失败写据访信息
									addLog(pub, user, ins_Id, null,
											IpUtil.getIp(request), 2, 2, 0, 1);

								}
							} else if (!isLicense) {
								model.put("pageCode", "p3");
								// 推荐
								forwardString = "publications/pop1";
							}
						} else {// IP范围外未登录，只需要判断是否为开源免费资源
							/*
							 * condition.put("status", 1);
							 * condition.put("pubId", pub.getId());
							 * condition.put("institutionId", ins_Id);
							 * condition.put("isTrial", "1"); inLicense =
							 * this.customService
							 * .getLicenseResourceCount(condition) > 0; if
							 * (!inLicense) { condition.remove("pubId");
							 * condition.put("pubCode", pub.getCode());
							 * inLicense =
							 * this.customService.getLicenseResourceCount
							 * (condition) > 0; }
							 */
							model.put("pub", pub);
							Boolean isLicense = inLicense || pub.getOa() == 2
									|| pub.getFree() == 2;
							if (isLicense) {
								model.put("pageCode", "p5");
								// 阅读
								forwardString = "publications/pop1";
							} else {
								// 访问失败写据访信息
								addLog(pub, user, null, null,
										IpUtil.getIp(request), 2, 2, 0, 1);

								forwardString = "publications/pop1";
								model.put("pageCode", "p6");
							}
						}
					}
				} else {
					// 未找到出版物
				}
			} else {
				// 没有出版物id
			}
		} catch (Exception e) {
			e.printStackTrace();
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
		}

		return new ModelAndView(forwardString, model);
	}

	/**
	 * 二级 中文页面
	 */
	@RequestMapping(value = "readingBook")
	public ModelAndView readingBook(HttpServletRequest request,
			HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "publications/cnBookList";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			BInstitution ins = (BInstitution) request.getSession()
					.getAttribute("institution");
			CUser user = request.getSession().getAttribute("mainUser") == null ? null
					: (CUser) request.getSession().getAttribute("mainUser");
			if (null == ins && null != user && 2 == user.getLevel()) {
				ins = user.getInstitution();
			}
			form.setUrl(request.getRequestURL().toString());
			// 总数
			List<ResourcesSum> sumList = this.resourcesSumService.getList(null,
					"");
			if (sumList != null && sumList.size() > 0) {
				model.put("count", sumList.get(0).getBookCount());
				// System.out.println("中文：" + sumList.get(0).getSumCount());
			}
			// 左侧学科分类
			Map<String, Object> subCondition = new HashMap<String, Object>();
			subCondition.put("treeCodeLength", 6);
			List<BSubject> subList = this.bSubjectService.getSubList(
					subCondition, " order by a.order ");
			model.put("insInfo", ins == null ? null : ins.getId());
			model.put("subList", subList);
			model.put("form", form);
		} catch (Exception e) {
			e.printStackTrace();
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(
					((CcsException) e).getPrompt(), request.getSession()
							.getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 中/外文电子书二级页面-编辑推荐
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping("editorRecommends")
	public void editorRecommends(HttpServletRequest request,
			HttpServletResponse response, IndexForm form) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		ServiceFactory serviceFactory = new ServiceFactoryImpl();
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			List<BInstitution> binsList = serviceFactory.getConfigureService()
					.getInstitutionList(condition, "");
			CUser user = request.getSession().getAttribute("mainUser") == null ? null
					: (CUser) request.getSession().getAttribute("mainUser");
			BInstitution ins = (BInstitution) request.getSession()
					.getAttribute("institution");
			if (null == ins && null != user && 2 == user.getLevel()) {
				ins = user.getInstitution();
			}
			long ip = IpUtil.getLongIp(IpUtil.getIp(request));
			// model.put("insInfo", ins == null ? "" : ins.getId());
			// 查询机构信息
			// Map<String,Object> mapip = new HashMap<String,Object>();
			Map<String, Object> ucMap = new HashMap<String, Object>();

			ucMap.put("ip", ip);
			ucMap.put("type", 2);
			ucMap.put("ava", 2);
			ucMap.put("pStatus", 2);
			ucMap.put("pType", 1);
			ucMap.put("noLicense", true);
			ucMap.put("noOrder", true);
			ucMap.put("pInsId", ins == null ? null : ins.getId());
			String en = request.getParameter("enBook");
			List<LAccess> editorRecommendsList = new ArrayList<LAccess>();
			if ("yes".equals(en)) {
				ucMap.put("isCn", false);
				editorRecommendsList = this.logAOPService
						.getLogOfHotReadingForRecommad(ucMap,
								" group by b.id order by count(a.id) desc ",
								12, 0);
			} else {
				ucMap.put("isCn", true);
				editorRecommendsList = this.logAOPService
						.getLogOfHotReadingForRecommad(ucMap,
								" group by b.id order by count(a.id) desc ",
								24, 0);
			}
			model.put("editorRecommendsList", editorRecommendsList);
			model.put("request", request);
			String path = Param.getParam("config.website.path.editor")
					.get("path").replace("-", ":");
			if ("yes".equals(en)) {
				if (ins == null) {
					FileUtil.generateHTML("pages/ftl", "English_Editor.ftl",
							"english_editor_all.html", model, request
									.getSession().getServletContext(), path);
				} else {
					FileUtil.generateHTML("pages/ftl", "English_Editor.ftl",
							"english_editor_" + ins.getId() + ".html", model,
							request.getSession().getServletContext(), path);
				}
			} else {
				if (ins == null) {
					FileUtil.generateHTML("pages/ftl", "Chinese_Editor.ftl",
							"chinese_editor_all.html", model, request
									.getSession().getServletContext(), path);
				} else {
					FileUtil.generateHTML("pages/ftl", "Chinese_Editor.ftl",
							"chinese_editor_" + ins.getId() + ".html", model,
							request.getSession().getServletContext(), path);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 中/外文电子期刊二级页面-编辑推荐
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping("editorRecommendsJournal")
	public void editorRecommendsJournal(HttpServletRequest request,
			HttpServletResponse response, IndexForm form) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = (CUser) request.getSession().getAttribute("mainUser");
			BInstitution ins = (BInstitution) request.getSession()
					.getAttribute("institution");
			long ip = IpUtil.getLongIp(IpUtil.getIp(request));
			// model.put("insInfo", ins == null ? "" : ins.getId());
			// 查询机构信息
			// Map<String,Object> mapip = new HashMap<String,Object>();
			Map<String, Object> ucMap = new HashMap<String, Object>();
			ucMap.put("ip", ip);
			ucMap.put("type", 2);
			/* ucMap.put("journalType", 2); */
			ucMap.put("ava", 2);
			ucMap.put("pStatus", 2);
			ucMap.put("pType", 2);
			ucMap.put("noLicense", true);
			ucMap.put("noOrder", true);
			ucMap.put("pInsId", ins == null ? null : ins.getId());
			ucMap.put("pUserId", user == null ? null : user.getId());
			String en = request.getParameter("enBook");
			List<LAccess> editorRecommendsList = new ArrayList<LAccess>();
			if ("yes".equals(en)) {
				ucMap.put("isCn", false);
				editorRecommendsList = this.logAOPService
						.getLogOfHotReadingForRecommad(ucMap,
								" group by b.id order by count(a.id) desc ",
								12, 0);
			} else {
				ucMap.put("isCn", true);
				editorRecommendsList = this.logAOPService
						.getLogOfHotReadingForRecommad(ucMap,
								" group by b.id order by count(a.id) desc ",
								24, 0);
			}
			model.put("editorRecommendsList", editorRecommendsList);
			model.put("request", request);
			String path = Param.getParam("config.website.path.editor")
					.get("path").replace("-", ":");
			if ("yes".equals(en)) {
				if (ins == null) {
					FileUtil.generateHTML("pages/ftl", "English_Editor.ftl",
							"english_editor_all.html", model, request
									.getSession().getServletContext(), path);
				} else {
					FileUtil.generateHTML("pages/ftl", "English_Editor.ftl",
							"english_editor_" + ins.getId() + ".html", model,
							request.getSession().getServletContext(), path);
				}
			} else {
				if (ins == null) {
					FileUtil.generateHTML("pages/ftl", "Chinese_Editor.ftl",
							"chinese_editor_all.html", model, request
									.getSession().getServletContext(), path);
				} else {
					FileUtil.generateHTML("pages/ftl", "Chinese_Editor.ftl",
							"chinese_editor_" + ins.getId() + ".html", model,
							request.getSession().getServletContext(), path);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = "free", method = RequestMethod.POST)
	public void freePubForIndex(HttpServletRequest request, Model model) {
		Map<String, Object> condition = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<PPublications> list = null;
		try {
			condition.put("status", 2); // 已上架
			condition.put("free", 2); // 免费
			list = this.pPublicationsService.getPubSimplePageList(condition,
					" order by a.createOn DESC", 4, 0);
			map.put("pubList", list);
			map.put("request", request);
			String path = Param.getParam("config.website.path").get("path")
					.replace("-", ":");
			FileUtil.generateHTML("pages/ftl", "Free_zh_CN.ftl",
					"free_zh_CN.html", map, request.getSession()
							.getServletContext(), path);
			FileUtil.generateHTML("pages/ftl", "Free_en_US.ftl",
					"free_en_US.html", map, request.getSession()
							.getServletContext(), path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/isPubExist", method = RequestMethod.POST)
	public void isPubExist(HttpServletRequest request, Model model) {
		ResultObject<Integer> result = null;
		try {
			Integer status = 1;
			String pubId = request.getParameter("id").toString();
			if (pubId != null && !"".equals(pubId)) {
				PPublications pub = this.pPublicationsService
						.getPublications(pubId);
				if (pub != null) {
					status = 2;
				}
			}
			result = new ResultObject<Integer>(1, status, "");
		} catch (Exception e) {
			result = new ResultObject<Integer>(2, "");
		}
		model.addAttribute("target", result);
	}

	/**
	 * @RequestMapping(value="form/forArticleRight") public ModelAndView
	 *                                               forArticleRight
	 *                                               (HttpServletRequest
	 *                                               request,HttpServletResponse
	 *                                               response,PPublicationsForm
	 *                                               form)throws Exception{
	 *                                               String forwardString=
	 *                                               "publications/articleRight"
	 *                                               ; Map<String,Object>
	 *                                               condition = new
	 *                                               HashMap<String,Object>();
	 *                                               Map<String, Object> model =
	 *                                               new HashMap<String,
	 *                                               Object>(); List<LAccess>
	 *                                               list= null;
	 *                                               List<PPublications> pubList
	 *                                               = new
	 *                                               ArrayList<PPublications>();
	 *                                               try {
	 *                                               condition.put("rightpubid",
	 *                                               form.getId()); CUser user=
	 *                                               (CUser)request.getSession()
	 *                                               .getAttribute("mainUser");
	 *                                               condition
	 *                                               .put("rightuserid",
	 *                                               user==null
	 *                                               ?" ":user.getId()); list =
	 *                                               this.pPublicationsService.
	 *                                               forArticleRight(condition,
	 *                                               "GROUP BY publications.id ORDER BY cast(count(*) as string) desc"
	 *                                               ,6,form.getCurpage());
	 *                                               for(LAccess lac:list){
	 *                                               pubList.add(this.
	 *                                               pPublicationsService
	 *                                               .getPublications
	 *                                               (lac.getPublications
	 *                                               ().getId())); }
	 *                                               if(pubList.size()<6){
	 *                                               condition.put("type", 1);
	 *                                               condition.put("status", 1);
	 *                                               List<PPublications>
	 *                                               afterList =
	 *                                               this.pPublicationsService
	 *                                               .getpublicationsPagingList
	 *                                               (condition,
	 *                                               " order by a.createOn desc"
	 *                                               , 6-list.size(),
	 *                                               form.getCurpage());
	 *                                               for(PPublications
	 *                                               obj:afterList){
	 *                                               pubList.add(obj); } }
	 *                                               model.put("list", pubList);
	 *                                               } catch (Exception e) {
	 *                                               throw e; } return new
	 *                                               ModelAndView
	 *                                               (forwardString,model); }
	 */

	@RequestMapping(value = "form/forArticleRight")
	public void forArticleRight(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<LAccess> list = null;
		List<PPublications> pubList = new ArrayList<PPublications>();
		try {
			condition.put("rightpubid", form.getId());
			CUser user = (CUser) request.getSession().getAttribute("mainUser");
			condition.put("rightuserid", user == null ? " " : user.getId());
			list = this.pPublicationsService
					.forArticleRight(
							condition,
							"GROUP BY publications.id ORDER BY cast(count(*) as string) desc",
							7, form.getCurpage());
			for (LAccess lac : list) {
				pubList.add(this.pPublicationsService.getPublications(lac
						.getPublications().getId()));
			}
			if (pubList.size() < 6) {
				condition.clear();
				condition.put("type", 1);
				condition.put("status", 2);
				List<PPublications> afterList = this.pPublicationsService
						.getpublicationsPagingList(condition,
								" order by a.updateOn desc", 7 - list.size(),
								form.getCurpage());
				for (PPublications obj : afterList) {
					pubList.add(obj);
				}
			}
			map.put("list", pubList);
			map.put("request", request);
			map.put("pubId", form.getId());
			String path = Param.getParam("config.website.path.article")
					.get("path").replace("-", ":");
			FileUtil.generateHTML("pages/ftl", "ArticleRight.ftl", form.getId()
					+ "_r.html", map, request.getSession().getServletContext(),
					path);
		} catch (Exception e) {
			throw e;
		}
	}

	@RequestMapping(value = "form/forArticleRightByRedis")
	public ModelAndView forArticleRightByRedis(HttpServletRequest request,
			PPublicationsForm form) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Book book = null;
		boolean isExistUser = false;
		boolean isExistBook = false;
		List<Book> pubList = new ArrayList<Book>();
		List<String> userIdList = new ArrayList<String>();
		List<String> booksList = new ArrayList<String>();

		// 主要步骤一，给当前图书的浏览次数 + 1。实现思路：往 Redis 增加一条计数（不登录用户也增加，为了增加该图书的权重值，规则是：在当前
		// bookId 前加下划线）
		Long count = bookDao.incr("_" + form.getId());

		CUser user = (CUser) request.getSession().getAttribute("mainUser");
		userIdList = bookDao.getSet(form.getId());
		if (null != user) {
			// 主要步骤二，记录读过当前图书的用户列表。实现思路：往 userList 中 lpush
			// 保存一条浏览用户。如果该用户已经存在，则跳出循环，否则插入一条记录
			if (0 == userIdList.size()) {
				bookDao.zadd(form.getId(), user.getId());
			} else {
				for (String uId : userIdList) {
					if (uId.equalsIgnoreCase(user.getId())) {
						isExistUser = true;
						break;
					}
				}
				if (!isExistUser) {
					bookDao.zadd(form.getId(), user.getId());
				}
			}

			// 主要步骤三，记录当前登录用户读过那些书。实现思路：往 bookList 中 lpush 保存一条用户读过的 bookId，如果该
			// bookId 存在，则跳出循环，否则插入一条记录
			booksList = bookDao.getSet(user.getId());
			if (0 == booksList.size()) {
				bookDao.zadd(user.getId(), form.getId() + form.getTitle());
			} else {
				for (String bId : booksList) {
					if (bId.startsWith(form.getId())) {
						isExistBook = true;
						break;
					}
				}
				if (!isExistBook) {
					bookDao.zadd(user.getId(), form.getId() + form.getTitle());
				}
			}
		}

		// 主要步骤四，提取读过当前图书的用户（先不包括当前用户，如果数量不够再取当前用户的读过记录）的读过记录
		for (String uId : userIdList) {
			booksList = bookDao.getSet(uId);

			// if (!uId.equals(user.getId())) {
			// System.out.println("====当前登录的用户Id：" + uId);
			for (String books : booksList) {
				// System.out.println("====该用户读过的书列表：" + books);
				book = new Book(books.substring(0, 32), books.substring(32,
						books.length()), count); // TODO
													// count暂不准确
				pubList.add(book);
				if (7 == pubList.size()) {
					map.put("currentId", form.getId());
					map.put("list", pubList);
					return new ModelAndView("publications/articleRight", map);
				}
			}
			// }
		}

		if (0 == pubList.size()) {
			List<String> newBooksList = new ArrayList<String>();
			newBooksList = bookDao.getSet("new7");
			for (String books : newBooksList) {
				book = new Book(books.substring(0, 32), books.substring(32, books.lastIndexOf("@@@@@author@@@@@")), count);
				pubList.add(book);
			}
		}
		map.put("list", pubList);
		map.put("currentId", form.getId());
		return new ModelAndView("publications/articleRight", map);
	}

	/**
	 * 1，这是 JSP 的实现
	 * 
	 * @RequestMapping(value="form/forArticleBottomList") public ModelAndView
	 *                                                    forArticleBottomList
	 *                                                    (HttpServletRequest
	 *                                                    request
	 *                                                    ,HttpServletResponse
	 *                                                    response
	 *                                                    ,PPublicationsForm
	 *                                                    form)throws Exception{
	 *                                                    String
	 *                                                    forwardString="publications/articleBottomList"
	 *                                                    ; Map<String,Object>
	 *                                                    condition = new
	 *                                                    HashMap
	 *                                                    <String,Object>();
	 *                                                    Map<String, Object>
	 *                                                    model = new
	 *                                                    HashMap<String,
	 *                                                    Object>(); String
	 *                                                    pubId =
	 *                                                    request.getParameter
	 *                                                    ("id");
	 *                                                    condition.put("publicationsId"
	 *                                                    , pubId);
	 *                                                    PPublications pub =
	 *                                                    this
	 *                                                    .pPublicationsService
	 *                                                    .getPublications
	 *                                                    (pubId);
	 *                                                    List<PCsRelation>
	 *                                                    pcslist
	 *                                                    =this.bSubjectService
	 *                                                    .getSubPubList
	 *                                                    (condition, "");
	 *                                                    condition.clear();
	 *                                                    condition
	 *                                                    .put("pcslist",
	 *                                                    pcslist);
	 *                                                    condition.put
	 *                                                    ("pstatus", 2);
	 *                                                    List<PCsRelation>
	 *                                                    toplist = null;
	 *                                                    if(pub.getType()==1){
	 *                                                    toplist
	 *                                                    =this.bSubjectService
	 *                                                    .getTops(condition,
	 *                                                    " order by p.buyTimes desc "
	 *                                                    ,
	 *                                                    5,0,(CUser)request.getSession
	 *                                                    ().getAttribute(
	 *                                                    "mainUser"
	 *                                                    ),IpUtil.getIp(request
	 *                                                    )); }
	 * 
	 *                                                    model.put("toplist",
	 *                                                    toplist); return new
	 *                                                    ModelAndView
	 *                                                    (forwardString,model);
	 *                                                    }
	 */

	// 2，这是 FreeMarker 的实现
	@RequestMapping(value = "form/forArticleBottomList")
	public void forArticleBottomList(HttpServletRequest request,
			HttpServletResponse response, PPublicationsForm form)
			throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		String pubId = request.getParameter("id");
		condition.put("publicationsId", pubId);
		PPublications pub = this.pPublicationsService.getPublications(pubId);
		List<PCsRelation> pcslist = this.bSubjectService.getSubPubList(
				condition, "");
		condition.clear();
		condition.put("pcslist", pcslist);
		condition.put("pstatus", 2);
		List<PCsRelation> toplist = null;
		if (pub.getType() == 1) {
			toplist = this.bSubjectService.getTops(condition,
					" order by p.buyTimes desc ", 5, 0, (CUser) request
							.getSession().getAttribute("mainUser"), IpUtil
							.getIp(request));
		}
		map.put("toplist", toplist);
		map.put("request", request);
		map.put("pubId", pubId);
		map.put("pub", pub);
		String path = Param.getParam("config.website.path.article").get("path")
				.replace("-", ":");
		FileUtil.generateHTML("pages/ftl", "ArticleBottom.ftl", form.getId()
				+ "_b.html", map, request.getSession().getServletContext(),
				path);
	}

	// 3，这是 Redis 的实现
	@RequestMapping(value = "form/forArticleBottomListByRedis")
	public ModelAndView forArticleBottomListByRedis(HttpServletRequest request, HttpServletResponse response, PPublicationsForm form) throws Exception {
		String forwardString = "publications/articleBottomList";
		Book book = null;
		Map<String, Object> condition = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		List<Book> toplist = new ArrayList<Book>();
		String pubId = request.getParameter("id");
		condition.put("publicationsId", pubId);
		List<PCsRelation> pcslist = this.bSubjectService.getSubPubList(condition, "");
		for (PCsRelation r : pcslist) {
			// System.out.println("=====" + r.getSubject().getId());
			List<String> booksList = bookDao.getSet(r.getSubject().getId());
			for (String b : booksList) {
				book = new Book(b.substring(0, 8), b.substring(8, 40), b.substring(40, b.lastIndexOf("@@@@@author@@@@@")), b.substring(b.lastIndexOf("@@@@@author@@@@@") + 16, b.lastIndexOf("@@@@@pubdate@@@@@")), b.substring(b.lastIndexOf("@@@@@pubdate@@@@@") + 17, b.lastIndexOf("@@@@@publisher@@@@@")), b.substring(b.lastIndexOf("@@@@@publisher@@@@@") + 19, b.length()));
				toplist.add(book);
			}
		}
		model.put("currentId", pubId);
		model.put("toplist", toplist);
		return new ModelAndView(forwardString, model);
	}

	// Redis 统计分类销量
	@RequestMapping("redisStatSubject")
	public void redisStatSubject() throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		List<BSubject> list = bSubjectService.getSubList(condition, "");
		System.out.println("分类总数：" + list.size());
		for (BSubject subject : list) {
			System.out.println("当前分类：" + subject.getId() + " - " + subject.getCode() + " - " + subject.getName());
			bookDao.delete(subject.getId());
			List<PPublications> PublicationsList = pPublicationsService.getBuyTimesInfo(subject.getId(), " ORDER BY a.buyTimes DESC ", 5);
			for (PPublications pub : PublicationsList) {
				int d = null == pub.getBuyTimes() ? 0 : pub.getBuyTimes();
				System.out.println("  插入Redis：" + subject.getId() + " - " + pub.getTitle());
				bookDao.zadd(subject.getId(), String.format("%08d", d) + pub.getId() + pub.getTitle() + "@@@@@author@@@@@" + pub.getAuthor() + "@@@@@pubdate@@@@@" + pub.getPubDate() + "@@@@@publisher@@@@@" + pub.getPublisher().getName());
			}
		}
		System.out.println("OK");
	}

	/**
	 * 批量生成中文电子书-各个机构编辑推荐资源
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping("editorRecommendsChineseAll")
	public void editorRecommendsChineseAll(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		ServiceFactory serviceFactory = new ServiceFactoryImpl();
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			Map<String, Object> ucMap = new HashMap<String, Object>();
			ucMap.put("type", 2);
			ucMap.put("ava", 2);
			ucMap.put("pStatus", 2);
			ucMap.put("pType", 1);
			ucMap.put("noLicense", true);
			ucMap.put("noOrder", true);
			ucMap.put("isCn", true);
			List<BInstitution> binsList = serviceFactory.getConfigureService().getInstitutionList(condition, "");
			List<LAccess> editorRecommendsList = new ArrayList<LAccess>();
			for (BInstitution ins : binsList) {
				ucMap.put("pInsId", ins == null ? null : ins.getId());
				editorRecommendsList = this.logAOPService.getLogOfHotReadingForRecommad(ucMap, " group by b.id order by count(a.id) desc ", 24, 0);
				model.put("editorRecommendsList", editorRecommendsList);
				model.put("request", request);
				String path = Param.getParam("config.website.path.editor").get("path").replace("-", ":");
				FileUtil.generateHTML("pages/ftl", "Chinese_Editor.ftl", "chinese_editor_" + ins.getId() + ".html", model, request.getSession().getServletContext(), path);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 批量生成外文电子书-各个机构编辑推荐资源
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping("editorRecommendsEnglishAll")
	public void editorRecommendsEnglishAll(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		ServiceFactory serviceFactory = new ServiceFactoryImpl();
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			Map<String, Object> ucMap = new HashMap<String, Object>();
			ucMap.put("type", 2);
			ucMap.put("ava", 2);
			ucMap.put("pStatus", 2);
			ucMap.put("pType", 1);
			ucMap.put("noLicense", true);
			ucMap.put("noOrder", true);
			ucMap.put("isCn", false);
			List<BInstitution> binsList = serviceFactory.getConfigureService().getInstitutionList(condition, "");
			List<LAccess> editorRecommendsList = new ArrayList<LAccess>();
			for (BInstitution ins : binsList) {
				ucMap.put("pInsId", ins == null ? null : ins.getId());
				editorRecommendsList = this.logAOPService.getLogOfHotReadingForRecommad(ucMap, " group by b.id order by count(a.id) desc ", 12, 0);
				model.put("editorRecommendsList", editorRecommendsList);
				model.put("request", request);
				String path = Param.getParam("config.website.path.editor").get("path").replace("-", ":");
				FileUtil.generateHTML("pages/ftl", "English_Editor.ftl", "english_editor_" + ins.getId() + ".html", model, request.getSession().getServletContext(), path);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 批量生成外文电子书-各个机构热读资源
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "hotReadingBookChineseAll")
	public void hotReadingBookChineseAll(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		ServiceFactory serviceFactory = new ServiceFactoryImpl();
		try {
			Map<String, Object> hotcondition = new HashMap<String, Object>();
			Map<String, Object> condition = new HashMap<String, Object>();
			hotcondition.put("type", 2);
			hotcondition.put("pubStatus", 2);// 上架
			hotcondition.put("pubType", 1);
			hotcondition.put("language", "ch%");
			List<BInstitution> binsList = serviceFactory.getConfigureService().getInstitutionList(condition, "");
			for (BInstitution ins : binsList) {
				hotcondition.put("institutionId", ins == null ? null : ins.getId());
				hotcondition.put("license", "true");
				List<LAccess> hotlist = this.logAOPService.getLogOfHotReading(hotcondition, " group by a.publications.id order by count(*) desc ", 24, 0);
				if (hotlist.size() < 24) {
					hotcondition.remove("institutionId");
					hotcondition.remove("license");
					hotlist = this.logAOPService.getLogOfHotReading(hotcondition, " group by a.publications.id order by count(*) desc ", 24, 0);
				}
				model.put("hotlist", hotlist);
				model.put("request", request);
				String path = Param.getParam("config.website.path.hotReading").get("path").replace("-", ":");
				FileUtil.generateHTML("pages/ftl", "Chinese_HotReading.ftl", "chinese_hotReading_" + ins.getId() + ".html", model, request.getSession().getServletContext(), path);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 批量生成外文电子书-各个机构热读资源
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "hotReadingBookEnglishAll")
	public void hotReadingBookEnglishAll(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		ServiceFactory serviceFactory = new ServiceFactoryImpl();
		try {
			Map<String, Object> hotcondition = new HashMap<String, Object>();
			Map<String, Object> condition = new HashMap<String, Object>();
			hotcondition.put("type", 2);
			hotcondition.put("pubStatus", 2);// 上架
			hotcondition.put("pubType", 1);
			hotcondition.put("languageEn", "ch%");
			List<BInstitution> binsList = serviceFactory.getConfigureService().getInstitutionList(condition, "");
			for (BInstitution ins : binsList) {
				hotcondition.put("institutionId", ins == null ? null : ins.getId());
				hotcondition.put("license", "true");
				List<LAccess> hotlist = this.logAOPService.getLogOfHotReading(hotcondition, " group by a.publications.id order by count(*) desc ", 18, 0);
				if (hotlist.size() < 18) {
					hotcondition.remove("institutionId");
					hotcondition.remove("license");
					hotlist = this.logAOPService.getLogOfHotReading(hotcondition, " group by a.publications.id order by count(*) desc ", 18, 0);
				}
				model.put("hotlist", hotlist);
				model.put("request", request);
				String path = Param.getParam("config.website.path.hotReading").get("path").replace("-", ":");
				FileUtil.generateHTML("pages/ftl", "English_HotReading.ftl", "english_hotReading_" + ins.getId() + ".html", model, request.getSession().getServletContext(), path);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 批量生成中文期刊-各个机构推荐期刊
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "recJournalStaticCnAll")
	public void recJournalStaticCnAll(HttpServletRequest request,
			HttpServletResponse response, IndexForm form) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		ServiceFactory serviceFactory = new ServiceFactoryImpl();
		try {
			Map<String, Object> hotcondition = new HashMap<String, Object>();
			Map<String, Object> condition = new HashMap<String, Object>();
			hotcondition.put("ava", 2);
			hotcondition.put("language", "ch%");
			hotcondition.put("pType", 2); // 期刊
			hotcondition.put("pStatus", 2);
			hotcondition.put("noLicense", true);
			hotcondition.put("noOrder", true);
			List<BInstitution> binsList = serviceFactory.getConfigureService()
					.getInstitutionList(condition, "");
			List<LAccess> lists = this.logAOPService
					.getLogOfHotReadingForRecommad(hotcondition,
							" group by b.id order by count(a.id) desc ", 24, 0);
			for (BInstitution ins : binsList) {
				hotcondition.put("pInsId", ins.getId());
				List<LAccess> list = this.logAOPService
						.getLogOfHotReadingForRecommad(hotcondition,
								" group by b.id order by count(a.id) desc ",
								24, 0);
				if (list.size() < 12) {
					model.put("list", lists);
				} else {
					model.put("list", list);
				}
				model.put("request", request);
				String path = Param
						.getParam("config.website.path.journal_recJournal")
						.get("path").replace("-", ":");
				FileUtil.generateHTML("pages/ftl", "RecJournalCn.ftl",
						"recJournal_" + ins.getId() + "_cn.html", model,
						request.getSession().getServletContext(), path);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 批量生成中文期刊-各个机构热读期刊
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "hotReadingCnAll")
	public void hotReadingCnAll(HttpServletRequest request,
			HttpServletResponse response, IndexForm form) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		ServiceFactory serviceFactory = new ServiceFactoryImpl();
		try {
			Map<String, Object> hotcondition = new HashMap<String, Object>();
			Map<String, Object> condition = new HashMap<String, Object>();
			hotcondition.put("type", 2);
			hotcondition.put("pubStatus", 2);// 上架
			hotcondition.put("language", "ch%");
			hotcondition.put("pubtype", 7);// 期刊
			List<BInstitution> binsList = serviceFactory.getConfigureService()
					.getInstitutionList(condition, "");
			List<LAccess> hotlists = this.logAOPService.getJournalHotReading(
					hotcondition,
					" group by a.publications.id order by count(*) desc ", 12,
					0);
			String path = Param
					.getParam("config.website.path.journal_hotReading")
					.get("path").replace("-", ":");
			model.put("hotlist", hotlists);
			model.put("request", request);
			FileUtil.generateHTML("pages/ftl",
					"Journal_HotReadingArticleCn.ftl",
					"Journal_HotReadingArticle_all_cn.html", model, request
							.getSession().getServletContext(), path);
			model.remove("hotlist");
			for (BInstitution ins : binsList) {
				hotcondition.put("institutionId",
						ins == null ? null : ins.getId());
				hotcondition.put("license", "true");
				List<LAccess> hotlist = this.logAOPService
						.getJournalHotReading(
								hotcondition,
								" group by a.publications.id order by count(*) desc ",
								12, 0);
				if (hotlist.size() < 12) {
					model.put("hotlist", hotlists);
				} else {
					model.put("hotlist", hotlist);
				}
				// model.put("request", request);
				FileUtil.generateHTML(
						"pages/ftl",
						"Journal_HotReadingArticleCn.ftl",
						"Journal_HotReadingArticle_" + ins.getId() + "_cn.html",
						model, request.getSession().getServletContext(), path);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 生成中文期刊-最新期刊
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@RequestMapping(value = "lastPubsJournalCnAll")
	public void lastPubsJournalCnAll(HttpServletRequest request,
			HttpServletResponse response, IndexForm form) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			long ip = IpUtil.getLongIp(IpUtil.getIp(request));
			condition.put("ip", ip);
			condition.put("ava", 2);
			condition.put("language", "ch%");
			condition.put("pStatus", 2);
			condition.put("pType", 2); // 期刊
			// condition.put("noLicense", true);
			// condition.put("noOrder", true);
			List<LAccess> list = this.logAOPService
					.getLogOfHotReadingForRecommad(condition,
							" group by b.id order by count(a.id) desc ", 24, 0);
			model.put("list", list);
			model.put("request", request);
			String path = Param.getParam("config.website.path").get("path")
					.replace("-", ":");
			FileUtil.generateHTML("pages/ftl", "Journal_ReadableNewCn.ftl",
					"Journal_ReadableNewCn.html", model, request.getSession()
							.getServletContext(), path);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/*
	 * http://www.cnpereading.com/pages/publications/editorRecommendsEnglishAll
	 * http://www.cnpereading.com/pages/publications/editorRecommendsChineseAll
	 * http://www.cnpereading.com/pages/publications/hotReadingBookChineseAll
	 * http://www.cnpereading.com/pages/publications/hotReadingBookEnglishAll
	 * 
	 * http://www.cnpereading.com/pages/publications/hotReadingCnAll
	 * http://www.cnpereading.com/pages/publications/recJournalStaticCnAll
	 * http://www.cnpereading.com/pages/publications/lastPubsJournalCnAll
	 */
}
