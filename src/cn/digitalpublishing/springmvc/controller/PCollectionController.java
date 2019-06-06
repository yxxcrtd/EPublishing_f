package cn.digitalpublishing.springmvc.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.ccsit.restful.tool.Converter;
import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.Param;
import cn.com.daxtech.framework.model.ResultObject;
import cn.com.daxtech.framework.util.ObjectUtil;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.BPublisher;
import cn.digitalpublishing.ep.po.BSubject;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.PCcRelation;
import cn.digitalpublishing.ep.po.PCollection;
import cn.digitalpublishing.ep.po.PCsRelation;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.springmvc.form.product.PCollectionForm;
import cn.digitalpublishing.util.io.FileUtil;
import cn.digitalpublishing.util.web.IpUtil;

@Controller
@RequestMapping("/pages/collection")
public class PCollectionController extends BaseController {

	@RequestMapping(value = "/form/prepareManager")
	public ModelAndView prepareManager(HttpServletRequest request, HttpServletResponse response, PCollectionForm form) throws Exception {
		String forwardString = "collection/list";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("form", form);
		} catch (Exception e) {
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/form/manager")
	public ModelAndView manager(HttpServletRequest request, HttpServletResponse response, PCollectionForm form) throws Exception {
		String forwardString = "collection/colListRight";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String cur = request.getParameter("curpage");
			if(cur!=null&&!"".equals(cur)){
				form.setCurpage(Integer.parseInt(cur));
			}
			form.setUrl(request.getRequestURL().toString());
			// 根根据ID查询产品包下边的产品
			Map<String, Object> condition = new HashMap<String, Object>();
			String ppvCollId = Param.getParam("ppvCollection.config") != null ? Param.getParam("ppvCollection.config").get("id") : null;
			condition.put("ppvCollId", ppvCollId);
			List<PCollection> pcList = this.pPublicationsService.getCollectionPagingList(condition, " order by a.name ", form.getPageCount(), form.getCurpage());
			form.setCount(this.pPublicationsService.getCollectionCount(condition));
			// PPV产品包特殊对待，页面判断当前是否ppv包;
//			String ppvCollId = Param.getParam("ppvCollection.config") != null ? Param.getParam("ppvCollection.config").get("id") : null;
			model.put("ppvCollId", ppvCollId);
			model.put("pcList", pcList);
			model.put("form", form);
		} catch (Exception e) {
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/form/list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response, PCollectionForm form) throws Exception {
		String forwardString = "collection/test";
		Map<String, Object> model = new HashMap<String, Object>();
		Map<BPublisher, Integer> publisherCount = new HashMap<BPublisher, Integer>();
		boolean inLicense = false;
		boolean inDetail = false;
		try {
			form.setUrl(request.getRequestURL().toString());
			// 根根据ID查询产品包下边的产品
			Map<String, Object> condition = new HashMap<String, Object>();
			String collectionId = request.getParameter("id") != null && !"".equals(request.getParameter("id").toString()) ? request.getParameter("id").toString() : "";
			PCollection collection = pPublicationsService.getCollection(collectionId);
			model.put("pCollection", collection);
			if ("".equals(collectionId)) {
				throw new CcsException("collection.pk.id.not.exist");
			}
			CUser user = (CUser) request.getSession().getAttribute("mainUser");
			if (user != null) {
				condition.put("status", 1);
				condition.put("collectionId", collectionId);
				condition.put("userid", user.getId());
				if (request.getSession().getAttribute("institution") != null || user.getLevel() == 2) {// 在机构范围内或者是机构管理员
																										// 需要判断该机构是否对产品进行了
																										// 购买-->加入购物车
																										// 的判断
					String insId = user.getLevel() == 2 ? user.getInstitution().getId() : request.getSession().getAttribute("institution") == null ? user.getInstitution().getId() : ((BInstitution) request.getSession().getAttribute("institution")).getId();
					condition.remove("userid");
					condition.put("uidOrinsid", user.getId() + "," + insId);
				}
				inLicense = this.customService.getLicenseCount(condition) > 0;
				condition.clear();
				if (!inLicense) {
					condition.put("collectionId", collectionId);
					condition.put("newuserid", user.getId());
					if (request.getSession().getAttribute("institution") != null || user.getLevel() == 2) {// 在机构范围内或者是机构管理员
																											// 需要判断该机构是否对产品进行了
																											// 购买-->加入购物车
																											// 的判断
						String insId = user.getLevel() == 2 ? user.getInstitution().getId() : request.getSession().getAttribute("institution") == null ? user.getInstitution().getId() : ((BInstitution) request.getSession().getAttribute("institution")).getId();
						condition.remove("newuserid");
						condition.put("uidOrinsid", user.getId() + "," + insId);
					}
					inDetail = this.oOrderService.getDetailList(condition) > 0;
				}
			}
			List<PCcRelation> pccList = this.pPublicationsService.getPccPagingList(condition, " order by p.title ", form.getPageCount(), form.getCurpage(), (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
			model.put("pccList", pccList);
			form.setCount(this.pPublicationsService.getPccCount(condition));
			form.setInLicense(inLicense);
			form.setInOrderDetail(inDetail);
			form.setCollectionId(collectionId);
			// 出版社
			condition.clear();
			condition.put("collectionId", collectionId);
			condition.put("type", "");
			condition.put("type", "");
			List<BPublisher> pubStatistic = this.configureService.getPublisherStatistic(condition);
			model.put("pubStatistic", pubStatistic);
			// 学科分类
			condition.put("treeCodeLength", 6);
			List<BSubject> subList = this.bSubjectService.getSubColListCount(condition, "");// 产品包在分类中的相关产品的数量
			model.put("subList", subList);
			condition.remove("treeCodeLength");
			//类型统计
			List<PCcRelation> typeStatic = this.pPublicationsService.getCcTypeStatistic(condition);
			model.put("typeStatic", typeStatic);
			if (form.getStatistic() == null || form.getStatistic().isEmpty()) {
				form.setStatistic(this.pPublicationsService.getCcTypeLangStatistic(condition));
			}
			//语种统计
			List<PCcRelation> langStatic = this.pPublicationsService.getCcLangStatistic(condition);
			model.put("langStatic", langStatic);

			model.put("_r", new Date().getTime());
			String ppvCollId = Param.getParam("ppvCollection.config") != null ? Param.getParam("ppvCollection.config").get("id") : null;
			model.put("ppvCollId", ppvCollId);

		} catch (Exception e) {
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";

		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 数据接口
	 * 
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(HttpServletRequest request, Model model) {
		ResultObject<PCollection> result = null;
		try {
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); // 1-insert
																			// 2-update
			Converter<PCollection> converter = new Converter<PCollection>();
			PCollection obj = (PCollection) converter.json2Object(objJson, PCollection.class.getName());
			// 查询是否存在该产品包
			PCollection pc = this.pPublicationsService.getCollection(obj.getId());
			if ("2".equals(operType)) {
				if (pc != null && !"".equals(pc.getId())) {
					this.pPublicationsService.updateCollection(obj, obj.getId(), null);
				}
			} else if ("1".equals(operType)) {
				if (pc != null && !"".equals(pc.getId())) {
					// 更新产品中icCllection字段，字段值累加
					this.pPublicationsService.updateCollection(obj, obj.getId(), null);
				} else {
					// 更新产品中icCllection字段，字段值累加
					this.pPublicationsService.insertCollection(obj);
				}
			} else {
				// 删除
			}
			ObjectUtil<PCollection> util = new ObjectUtil<PCollection>();
			obj = util.setNull(obj, new String[] { Set.class.getName() });
			result = new ResultObject<PCollection>(1, obj, Lang.getLanguage("Controller.PCollection.insert.manage.success", request.getSession().getAttribute("lang").toString()));// "产品包信息维护成功！");//"出版商信息维护成功！");
		} catch (Exception e) {
			result = new ResultObject<PCollection>(2, Lang.getLanguage("Controller.PCollection.insert.manage.error", request.getSession().getAttribute("lang").toString()));// "产品包信息维护失败！");//"出版商信息维护失败！");
		}
		model.addAttribute("target", result);
	}

	@RequestMapping(value = "/form/cover")
	public void cover(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		String coverPath = "";
		try {
			String basePath = Param.getParam("pdf.directory.config").get("dir").replace("-", ":");
			String id = request.getParameter("id");
			if (id != null && !"".equals(id)) {
				PCollection pub = this.pPublicationsService.getCollection(id);
				if (pub.getCover() != null && !"".equals(pub.getCover())) {
					coverPath = basePath + "/" + pub.getCover();
				} else {
					coverPath = basePath + "/images/noimg.jpg";
				}
				if (FileUtil.isExist(coverPath)) {
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

	@RequestMapping(value = "/form/listForRight")
	public ModelAndView listForRight(HttpServletRequest request, HttpServletResponse response, PCollectionForm form) throws Exception {
		String forwardString = "collection/listForRight";
		Map<String, Object> model = new HashMap<String, Object>();
		String insId = "";
		try {
			form.setUrl(request.getRequestURL().toString());
			// 根根据ID查询产品包下边的产品
			Map<String, Object> condition = new HashMap<String, Object>();
			String collectionId = form.getCollectionId();
			if ("".equals(collectionId)) {
				throw new CcsException("collection.pk.id.not.exist");
			}
			condition.put("collectionId", collectionId);
			CUser user = (CUser) request.getSession().getAttribute("mainUser");
			if (user != null) {
				if (request.getSession().getAttribute("institution") != null || user.getLevel() == 2) {
					insId = user.getLevel() == 2 ? user.getInstitution().getId() : request.getSession().getAttribute("institution") == null ? user.getInstitution().getId() : ((BInstitution) request.getSession().getAttribute("institution")).getId();
					condition.put("ins_id", insId);
				}
			}
			if (form.getMarktype() == null)
				form.setMarktype(Integer.parseInt(request.getSession().getAttribute("pcMarkType").toString()));
			if (form.getTypeValue() == null)
				form.setTypeValue(request.getSession().getAttribute("pcTypeValue").toString());
			switch (form.getMarktype()) {
			case 1:
				break;
			case 2:// 产品类型
				condition.put("type", form.getTypeValue());
				break;
			case 3:// 语种
				if (form.getTypeValue().equals("CHS")) {
					condition.put("lang", "CHS");
				} else {
					condition.put("-lang", "CHS");
				}
				break;
			case 4:// 出版社
				condition.put("publisherId", form.getTypeValue());
				break;
			case 5:// 学科分类
				condition.put("ssubId", form.getTypeValue());
				break;
			case 100:
				condition.put("searchValue", form.getTypeValue());
				break;
			case 101:
				condition.put("searchTitle", form.getTypeValue());
				break;
			case 102:
				condition.put("searchAuthor", form.getTypeValue());
				break;
			case 103:
				condition.put("searchCode", form.getTypeValue());
				break;
			case 104:
				condition.put("searchPublisherName", form.getTypeValue());
				break;
			default:
				break;
			}
			long d1 = new Date().getTime();
			List<PCcRelation> pccList = this.pPublicationsService.getPccPagingList(condition, " order by p.title ", form.getPageCount(), form.getCurpage(), (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
			form.setCount(this.pPublicationsService.getPccCount(condition));
			long d2 = new Date().getTime();
			System.out.println((d2 - d1) / 1000);
			condition.clear();

			request.getSession().setAttribute("pcMarkType", form.getMarktype());
			request.getSession().setAttribute("pcTypeValue", form.getTypeValue());
			model.put("form", form);
			model.put("pccList", pccList);
			return new ModelAndView(forwardString, model);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = "/form/listForPPV")
	public ModelAndView listForPPV(HttpServletRequest request, HttpServletResponse response, PCollectionForm form) throws Exception {
		String forwardString = "ppv/ppvRight";
		Map<String, Object> model = new HashMap<String, Object>();
		String insId = "";
		try {
			form.setUrl(request.getRequestURL().toString());
			// 根根据ID查询产品包下边的产品
			Map<String, Object> condition = new HashMap<String, Object>();
			String collectionId = form.getCollectionId();
			if ("".equals(collectionId)) {
				throw new CcsException("collection.pk.id.not.exist");
			}
			condition.put("collectionId", collectionId);
			CUser user = (CUser) request.getSession().getAttribute("mainUser");
			if (user != null) {
				if (request.getSession().getAttribute("institution") != null || user.getLevel() == 2) {
					insId = user.getLevel() == 2 ? user.getInstitution().getId() : request.getSession().getAttribute("institution") == null ? user.getInstitution().getId() : ((BInstitution) request.getSession().getAttribute("institution")).getId();
					condition.put("ins_id", insId);
				}
			}
			// 出版社
			if (form.getPublisherId() != null && !form.getPublisherId().equals("")) {
				condition.put("publisherId", form.getPublisherId());
			}
			// 学科分类
			if (form.getSubjectId() != null && !form.getSubjectId().equals("")) {
				condition.put("subId", form.getSubjectId());
			}
			// 语言
			if (form.getLangId() != null && !form.getLangId().equals("")) {
				condition.put("lang", form.getLangId());
			}
			long d1 = new Date().getTime();
			List<PCcRelation> pccList = this.pPublicationsService.getPccPagingList(condition, " order by p.title ", form.getPageCount(), form.getCurpage(), (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
			form.setCount(this.pPublicationsService.getPccCount(condition));
			long d2 = new Date().getTime();
			System.out.println((d2 - d1) / 1000);
			condition.clear();

			request.getSession().setAttribute("pcMarkType", form.getMarktype());
			request.getSession().setAttribute("pcTypeValue", form.getTypeValue());
			model.put("form", form);
			model.put("pccList", pccList);
			return new ModelAndView(forwardString, model);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping("form/getInLicense")
	public void getInLicense(HttpServletRequest request, HttpServletResponse response, PCollectionForm form) throws Exception {
		String result = "";
		Map<String, Object> condition = new HashMap<String, Object>();
		List<LLicense> list = null;
		try {
			CUser user = (CUser) request.getSession().getAttribute("mainUser");
			condition.put("u_id", user.getId());
			condition.put("colId", form.getCollectionId());
			list = this.customService.getPubInCollection(condition, "");
			for (LLicense lic : list) {
				result += lic.getPublications().getTitle() + "<br/>";
			}
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 外文文章单篇购买（获取名为'PPV期刊包'的打包集产品）
	 */
	@RequestMapping("form/getPPV")
	public ModelAndView getPPV(HttpServletRequest request, HttpServletResponse response, PCollectionForm form) throws Exception {
		String forwardString = "ppv/ppv";
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> condition = new HashMap<String, Object>();
		// 左侧菜单
		// 1，学科分类
		List<PCsRelation> pcsList = null;
		// 2，出版社(出版商)
		List<PPublications> pubList = null;
		// 3，出版社(语言)
		List<PPublications> pubList1 = null;
		String cn = request.getParameter("cn");
		if ("yes".equals(cn)) {
			condition.put("language", "ch%");
			form.setLangId("chs");
		} else {
			condition.put("languageEn", "ch%");
			form.setLangId("eng");
		}
		try {
			condition.put("collectionName", "PPV期刊包");
			List<PCollection> list = this.pPublicationsService.getPCollectionList(condition, "");
			if (list.size() < 0) {
				throw new CcsException("collection.pk.id.not.exist");
			}
			String collectionId = list.get(0).getId();
			condition.put("collectionId", collectionId);
			pcsList = this.pPublicationsService.getSubInfoForCol(condition, " order by cast(count(*) as int) desc ");
			pubList = this.pPublicationsService.getpublisherInfoForCol(condition, " order by cast(count(*) as int) desc ");
			pubList1 = this.pPublicationsService.getLangInfoForCol(condition, " order by cast(count(*) as int) desc ");
			model.put("pcsList", pcsList);
			model.put("pubList", pubList);
			model.put("pubList1", pubList1);
			form.setCollectionId(collectionId);
			model.put("form", form);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 外文电子期刊二级页面-外文文章单篇购买的四条信息
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("form/getPPV4")
	public ModelAndView getPPV4(HttpServletRequest request, HttpServletResponse response, PCollectionForm form) throws Exception {
		String forwardString = "ppv/journalArticle4";
		Map<String, Object> model = new HashMap<String, Object>();
		String insId = "";
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("collectionName", "PPV期刊包");
			String cn = request.getParameter("cn");
			if ("yes".equals(cn)) {
				condition.put("language", "ch%");
				forwardString = "ppv/journalArticle4cn";
			}else{
				condition.put("languageEn", "ch%");
			}
			List<PCollection> list = this.pPublicationsService.getPCollectionList(condition, "");
			if (list.size() < 0) {
				throw new CcsException("collection.pk.id.not.exist");
			}
			String collectionId = list.get(0).getId();
			condition.put("collectionId", collectionId);

			CUser user = (CUser) request.getSession().getAttribute("mainUser");
			if (user != null) {
				if (request.getSession().getAttribute("institution") != null || user.getLevel() == 2) {
					insId = user.getLevel() == 2 ? user.getInstitution().getId() : request.getSession().getAttribute("institution") == null ? user.getInstitution().getId() : ((BInstitution) request.getSession().getAttribute("institution")).getId();
					condition.put("ins_id", insId);
				}
			}
			List<PCcRelation> pccList = this.pPublicationsService.getPccPagingList(condition, " order by p.createOn desc", 4, 0, (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
			model.put("form", form);
			model.put("pccList", pccList);
			return new ModelAndView(forwardString, model);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
