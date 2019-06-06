package cn.digitalpublishing.springmvc.controller;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.Param;
import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.BIpRange;
import cn.digitalpublishing.ep.po.BService;
import cn.digitalpublishing.ep.po.BSubject;
import cn.digitalpublishing.ep.po.CSearchHis;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.PPrice;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.springmvc.form.configure.BServiceForm;
import cn.digitalpublishing.springmvc.form.index.IndexForm;
import cn.digitalpublishing.util.CharUtil;
import cn.digitalpublishing.util.io.FileUtil;
import cn.digitalpublishing.util.web.ComparatorSubject;
import cn.digitalpublishing.util.web.DateUtil;
import cn.digitalpublishing.util.web.IpUtil;
import cn.digitalpublishing.util.web.MathHelper;
import cn.digitalpublishing.util.web.SequenceUtil;
import cn.digitalpublishing.util.xml.EPubHelper;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Index Controller
 */
@Controller
public class IndexController extends BaseController {

	/**
	 * 首页
	 */
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request, IndexForm form) throws Exception {

		// Redis中最新的7条数据
		List<String> newBooksList = bookDao.getSet("new7");
		if (0 == newBooksList.size()) {
			Map<String, Object> condition = new HashMap<String, Object>();
			// condition.put("dblang", "eng");
			condition.put("status", 2); // 已上架的
			condition.put("local", 2); // 本地资源
			condition.put("createOn", DateUtil.getMonthEndDay(new Date()));
			List<PPublications> list = pPublicationsService.getNewestPublications(condition, " ORDER BY a.createOn DESC", 7);
			// for (PPublications pub : list) {
			// bookDao.lpush("new7", pub.getId() + pub.getTitle());
			// }
			for (int i = 0; i < list.size(); i++) {
				bookDao.zadd("new7", (i + 1), list.get(i).getId() + list.get(i).getTitle() + "@@@@@author@@@@@" + list.get(i).getAuthor() + "@@@@@pubdate@@@@@" + list.get(i).getPubDate() + "@@@@@publisher@@@@@" + list.get(i).getPublisher().getName() + "@@@@@type@@@@@" + list.get(i).getType());
			}
			// 设置 new7 的过期时间（2个小时更新一次）
			bookDao.expire("new7", 2 * 60 * 60);
		}

		// Map<String, Object> resultMap =
		// publicationsIndexService.searchNewPubs(4, 10);
		// if (resultMap.get("count") != null &&
		// Long.valueOf(resultMap.get("count").toString()) > 0) {
		// List<Map<String, Object>> list = (List<Map<String, Object>>)
		// resultMap.get("result");
		// for (Map<String, Object> idInfo : list) {
		// 根据ID查询产品信息，由于加入了标签，这里不能用get查询
		// PPublications pub =
		// this.pPublicationsService.getPublications(idInfo.get("id").toString());
		// if (pub != null && (pub.getAvailable() == null ? 0 :
		// pub.getAvailable()) != 3) {
		// 往 Redis 中插入最新的7条记录
		// newBooksList = bookDao.getList("new7");
		// if (7 > newBooksList.size()) {
		// bookDao.lpush("new7", pub.getId() + pub.getTitle());
		// }
		// 设置 new7 的过期时间（2个小时更新一次）
		// bookDao.expire("new7", 2 * 60 * 60);
		// }
		// }
		// }

		// selectType用来保存全局的变量，看是全部还是在已订阅中查询（1：已订阅；2：全部）
		String forward = "index";
		if (null == request.getSession().getAttribute("selectType")) {
			request.getSession().setAttribute("selectType", 1);
		}
		BInstitution ins = (BInstitution) request.getSession().getAttribute("institution");
		request.getSession().setAttribute("path", Param.getParam("config.website.path").get("path").replace("-", ":"));
		CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
		if (null == user && null == ins) {
			request.getSession().setAttribute("selectType", "");
		} else {
			request.getSession().setAttribute("selectType", "1");
		}
		// 将特殊机构ID存储入Session <specialInstitutionFlag>

		if (null != ins && null == request.getSession().getAttribute("specialInstitutionFlag")) {
			// 从配置文件中获取特殊机构ID -- 非chs和cht的机构<非chs和cht的机构>
			String spId = Param.getParam("special.eng.institution") != null ? Param.getParam("special.eng.institution").get("id") : null;
			if (null != spId && !"".equals(spId)) {
				// 后台直接判断 特殊机构 中是否包含当前机构ID
				if (null != ins) {
					String specialFlag = spId.contains(ins.getId()) ? "(NOT (language:cht OR language:chs OR language:chi))" : null;
					request.getSession().setAttribute("specialInstitutionFlag", specialFlag);
				}
			}
		}

		forward = null != request.getSession().getAttribute("specialInstitutionFlag") ? "redirect:/pages/publications/readingBookEn" : forward;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("insInfo", null == ins ? null != user ? user.getInstitution().getId() : null : ins.getId());
		map.put("form", form);
		map.put("d", new Date());

		// 移动版判断新闻是否显示
		List<String> config_news = bookDao.getList("config_news");
		map.put("flag", config_news.get(0));
		List<String> menuList = null;
		String lang = (String) request.getSession().getAttribute("lang");
		if ("zh_CN".equals(lang)) {
			menuList = bookDao.getSet("config_menu_zh_CN");
		} else if ("en_US".equals(lang)) {
			menuList = bookDao.getSet("config_menu_en_US");
		}
		if (0 == menuList.size()) {
			map.put("show", "0");
		}
		return new ModelAndView(forward, map);
	}

	/**
	 * 显示资源的详情页
	 */
	@RequestMapping("article/{id}")
	public ModelAndView article(@PathVariable(value = "id") String id, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);

		// 判断静态文件是否存在
		if (!false) {

			// TODO

			map.put("o", "对象");

			// 生成静态文件
			String path = Param.getParam("config.website.path").get("path").replace("-", ":");
			FileUtil.generateHTML("pages/ftl", "Article_zh_CN.ftl", "article_zh_CN.html", map, request.getSession().getServletContext(), path);
			FileUtil.generateHTML("pages/ftl", "Article_en_US.ftl", "article_en_US.html", map, request.getSession().getServletContext(), path);
		}

		return new ModelAndView("ShowArticle", map);
	}

	/**
	 * 之前的首页
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	@RequestMapping(value = "/index_before")
	public ModelAndView showData(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "/index/index";
		System.out.println(forwardString);
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			/*
			 * this.publicationsIndexService.clearAllIndex();
			 * List<PPublications> listrr =
			 * this.pPublicationsService.getPubList(null, "", null, "");
			 * for(PPublications pp:listrr){ int s =
			 * this.publicationsIndexService.indexPublications(pp); }
			 * 
			 * this.licenseIndexService.clearAllIndex(); List<LLicense> listl =
			 * this.pPublicationsService.getLicenseList(null, ""); for (LLicense
			 * lLicense : listl) { PPublications pp =
			 * this.pPublicationsService.getPublications
			 * (lLicense.getPublications().getId());
			 * lLicense.setPublications(pp); int y =
			 * this.licenseIndexService.indexLicenses(lLicense); }
			 */
			if (request.getSession().getAttribute("selectType") == null) {
				request.getSession().setAttribute("selectType", 1);
			}
			// PPublications newestPub = null;
			// PPublications selectedPub = null;
			// PPublications specialPub = null;
			// Map<String,Object> condition=form.getCondition();
			// condition.put("homepage",2);
			// form.setUrl(request.getRequestURL().toString());
			// //获取产品包列表，取前五个
			// List<PCollection> pubCollection
			// =this.pPublicationsService.getPubCollectionPageList(condition,
			// " order by a.createOn DESC ", 5, 0);
			// if(pubCollection!=null&&pubCollection.isEmpty()){
			// pubCollection = null;
			// }
			// model.put("pubCollections", pubCollection);
			// //获取最新产品
			// condition.put("newest",2);
			// List<PPublications> newestList =
			// this.pPublicationsService.getPubSimplePageList(condition,
			// " order by a.updateOn DESC ",
			// form.getPageCount(),form.getCurpage());
			// this.removeCondition(condition, "newest");
			// if(newestList!=null && newestList.size()>0){
			// newestPub = newestList.get(0);
			// }
			// form.setNewest(newestPub);
			// //获取精选产品
			// condition.put("selected",2);
			// List<PPublications> selectedList =
			// this.pPublicationsService.getPubSimplePageList(condition,
			// " order by a.updateOn DESC ",
			// form.getPageCount(),form.getCurpage());
			// this.removeCondition(condition, "selected");
			// if(selectedList!=null && selectedList.size()>0){
			// selectedPub = selectedList.get(0);
			// }
			// form.setSelected(selectedPub);
			// //获取特惠产品
			// condition.put("special",2);
			// List<PPublications> specialList
			// =this.pPublicationsService.getPubSimplePageList(condition,
			// " order by a.updateOn DESC ",
			// form.getPageCount(),form.getCurpage());
			// this.removeCondition(condition, "special");
			//
			// if(specialList!=null && specialList.size()>0){
			// specialPub = specialList.get(0);
			// }
			// form.setSpecial(specialPub);
			// //获取最新的产品数量（根据上架日期统计）
			// this.removeCondition(condition, "homepage");
			// // List<PPublications> pubCountByDateList =
			// this.pPublicationsService.getPubCountPageList(condition,
			// " group by a.createDate order by a.createDate desc ", 5, 0);
			// // model.put("pubCountList", pubCountByDateList);
			// //
			// form.setCount(this.pPublicationsService.getPubCount(condition));
			model.put("form", form);
			// //查询广告位等信息
			// List<BConfiguration> list =
			// this.configureService.getConfigureList(null, "");
			// Map<String,Object> result = new HashMap<String,Object>();
			// if(list!=null&&list.size()>0){
			// for (BConfiguration bConfiguration : list) {
			// result.put(bConfiguration.getCode(), bConfiguration);
			// }
			// }
			// model.put("map", result);
			Map<String, Object> subCondition = new HashMap<String, Object>();
			subCondition.put("treeCodeLength", 6);
			List<BSubject> subList = this.bSubjectService.getSubList(subCondition, " order by a.order ");

			model.put("subList", subList);

		} catch (Exception e) {
			// e.printStackTrace();
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/clearPageIndex")
	public void clearPageIndex() throws Exception {
		this.pagesIndexService.clearAllIndex();
	}

	@RequestMapping(value = "/clearPublicationIndex")
	public void clearPublicationIndex() throws Exception {
		this.publicationsIndexService.clearAllIndex();
	}

	@RequestMapping(value = "/clearLicenseIndex")
	public void clearLicenseIndex() throws Exception {
		this.licenseIndexService.clearAllIndex();
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/generateLicenseIndex")
	public void generateLicenseIndex() throws Exception {
		List<LLicense> listl = this.pPublicationsService.getLicenseList(null, "");
		for (LLicense lLicense : listl) {
			PPublications pp = this.pPublicationsService.getPublications(lLicense.getPublications().getId());
			if (pp.getStatus() == 2) {// 只有上架的图书、期刊、章节、文章、数据库才能增加全文索引
				if (pp.getLocal() == 2) {// 只有本地资源才能增加全文索引
					String nr = "";
					String bpath = Param.getParam("pdf.directory.config").get("dir").replace("-", ":");
					String filePath = bpath + pp.getPdf();
					File pdf = new File(filePath);
					if (pdf.exists()) {
						if (pp.getFileType() == null || pp.getFileType() == 1) {// 获取PDF全文
							PDDocument doc = PDDocument.load(filePath);
							PDFTextStripper stripper = new PDFTextStripper();
							nr = stripper.getText(doc);
							pp.setDoi(nr);
							doc.close();
						} else if (pp.getFileType() == 2) {// 获取EPUB全文
							EPubHelper epubHelper = new EPubHelper(filePath);
							pp.setDoi(epubHelper.getText());
						}
					}
				}
			}
			lLicense.setPublications(pp);
			int y = this.licenseIndexService.indexLicenses(lLicense);
		}
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/generatePublicationsIndex")
	public void generatePublicationsIndex() throws Exception {
		this.publicationsIndexService.clearAllIndex();
		List<PPublications> listrr = this.pPublicationsService.getPubList(null, "", null, "");
		for (PPublications pp : listrr) {
			int s = this.publicationsIndexService.indexPublications(pp);
		}
	}

	// 将上一次的查询条件去掉
	@SuppressWarnings("unused")
	private void removeCondition(Map<String, Object> map, String condition) {
		if (CollectionsUtil.exist(map, condition)) {
			map.remove(condition);
		}
	}

	@RequestMapping("index/search")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "index/search";
		Map<String, Object> model = new HashMap<String, Object>();
		CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
		BInstitution ins = (BInstitution) request.getSession().getAttribute("institution");

		String searchValue2 = request.getParameter("searchValue2");
		searchValue2 = (null == searchValue2 || "".equals(searchValue2)) ? request.getParameter("searchValue") : searchValue2;

		try {
			request.getSession().setAttribute("selectType", "");// selectType
																// 用来保存全局的变量，看是全部还是在已订阅中查询
																// ""-全部 1-已订阅
			if (null != ins || null != user) {
				if ("".equals(form.getLcense())) {
					request.getSession().setAttribute("selectType", "");
				} else {
					request.getSession().setAttribute("selectType", "1");
				}
			}
			String keyword = URLDecoder.decode((null == form.getSearchValue() || "".equals(form.getSearchValue()) ? searchValue2 : form.getSearchValue()), "UTF-8");
			keyword = CharUtil.toSimple(keyword);
			String lang = (String) request.getSession().getAttribute("lang");
			// if(keyword!=null&&!"".equals(keyword)){
			form.setUrl(request.getRequestURL().toString());
			// form.setTaxonomy((form.getTaxonomy()==null||"".equals(form.getTaxonomy()))?null:new
			// String(form.getTaxonomy().getBytes("ISO-8859-1"),"UTF-8"));
			Map<String, String> param = new HashMap<String, String>();
			/** 特殊机构处理 -- START */
			String specialInstitutionFlag = request.getSession().getAttribute("specialInstitutionFlag") != null ? (String) request.getSession().getAttribute("specialInstitutionFlag") : null;
			if (null != specialInstitutionFlag && specialInstitutionFlag.length() > 0) {
				form = this.specialInstitution_handle(form, specialInstitutionFlag);
			}
			/** 特殊机构处理 -- END */
			param.put("language", (form.getLanguage() == null || "".equals(form.getLanguage())) ? null : "\"" + form.getLanguage() + "\"");
			param.put("publisher", (form.getPublisher() == null || "".equals(form.getPublisher())) ? null : "\"" + form.getPublisher() + "\"");
			// param.put("publisher", (form.getPublisher() == null ||
			// "".equals(form.getPublisher())) ? null : form.getPublisher());
			param.put("type", (form.getPubType() == null || "".equals(form.getPubType())) ? null : form.getPubType());
			param.put("pubDate", (form.getPubDate() == null || "".equals(form.getPubDate())) ? null : form.getPubDate() + "*");

			param.put("taxonomy", (form.getTaxonomy() == null || "".equals(form.getTaxonomy())) ? null : "\"" + URLDecoder.decode(form.getTaxonomy(), "UTF-8") + "\"");

			// param.put("taxonomy", (form.getTaxonomy() == null ||
			// "".equals(form.getTaxonomy())) ? null : "\"" + form.getTaxonomy()
			// + "\"");
			// param.put("taxonomyEn", (form.getTaxonomyEn() == null ||
			// "".equals(form.getTaxonomyEn())) ? null : "\"" +
			// form.getTaxonomyEn() + "\"");
			param.put("nochinese", (form.getNochinese() == null || "".equals(form.getNochinese())) ? null : "\"" + form.getNochinese() + "\"");
			param.put("local", (form.getLocal() == null || "".equals(form.getLocal())) ? null : form.getLocal());
			param.put("pubType", (form.getPubType() == null || "".equals(form.getPubType())) ? null : form.getPubType());
			param.put("notLanguage", (null == form.getNotLanguage()) || "".equals(form.getNotLanguage()) ? null : form.getNotLanguage());

			// 在solr中查询 [搜索类型=====0-全文;1-标题;2-作者]
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// form.setSearchsType(form.getSearchsType() == null ? 0 :
			// form.getSearchsType());
			// String keyword = form.getSearchValue();

			if (form.getIsAccurate() != null && form.getIsAccurate() == 2) {// 要查询的内容
																			// 是否精确查找
																			// 1、否
																			// ；2、是
				keyword = "\"" + keyword + "\"";
			}
			/*** 中文 ***/
			if (param.containsKey("taxonomy") && param.get("taxonomy") != null) {
				String[] taxArr = param.get("taxonomy").replace("\"", "").split(",");
				model.put("taxArr", taxArr);
			}
			/*** 英文 ***/
			if (param.containsKey("taxonomyEn") && param.get("taxonomyEn") != null) {
				String[] taxArrEn = param.get("taxonomyEn").replace("\"", "").split(",");
				model.put("taxArrEn", taxArrEn);
			}

			form.setSearchsType(null == form.getSearchsType() ? Integer.valueOf(null == request.getParameter("searchsType2") || "".equals(request.getParameter("searchsType2")) ? request.getParameter("type") : request.getParameter("searchsType2")) : (null == form.getSearchsType() || "".equals(form.getSearchsType()) ? Integer.valueOf(request.getParameter("type")) : form.getSearchsType()));

			if (keyword != null && !"".equals(keyword)) {
				switch (form.getSearchsType()) {
				case 0:
					resultMap = this.publicationsIndexService.searchByAllFullText(keyword, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
					break;
				case 1:
					resultMap = this.publicationsIndexService.searchByTitle(keyword, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
					break;
				case 2:
					resultMap = this.publicationsIndexService.searchByAuthor(keyword, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
					break;
				case 3:
					resultMap = this.publicationsIndexService.searchByISBN(keyword, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
					break;
				case 4:
					resultMap = this.publicationsIndexService.searchByPublisher(keyword, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
					break;
				default:
					resultMap = this.publicationsIndexService.searchByAllFullText(keyword, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
					break;
				}
				// 限制查询结果总数----------开始-----------
				Integer maxCount = Integer.parseInt(Param.getParam("search.config").get("maxCount"));
				Integer allCount = 0;
				if (resultMap.get("count") != null) {
					allCount = Integer.valueOf(resultMap.get("count").toString());
					model.put("queryCount", allCount);// 实际查询结果数量
					// 最多显示1000条
					allCount = maxCount > allCount ? allCount : maxCount;// 分页条显示的数量
				}
				// 限制查询结果总数----------结束-----------
				List<FacetField> facetFields = (List<FacetField>) resultMap.get("facet");
				Map<String, Integer> pubDate = new HashMap<String, Integer>();
				for (FacetField fac : facetFields) {
					if (fac.getName().equals("pubDate")) {
						List<Count> counts = fac.getValues();
						for (Count count : counts) {
							if (count == null || count.getName() == null || count.getName().length() < 4) {
								continue;
							}
							int num = pubDate.get(count.getName().substring(0, 4)) == null ? 0 : Integer.valueOf(pubDate.get(count.getName().substring(0, 4)));
							if (count.getCount() > 0) {
								pubDate.put(count.getName().substring(0, 4).toString(), (num + (int) count.getCount()));
							}
						}
					}

					// 类型处理
					if (fac.getName().equals("type")) {
						List<Count> counts = fac.getValues();
						if (counts != null && counts.size() > 0) {
							int journalIndex = -1;
							int issueIndex = -1;
							for (int i = 0; i < counts.size(); i++) {
								if ("2".equals(counts.get(i).getName())) {
									journalIndex = i;
								} else if ("7".equals(counts.get(i).getName())) {
									issueIndex = i;
								}
							}
							if (issueIndex > -1) {
								long finalJournalCount = 0;
								if (journalIndex > -1) {// 如果存在期时就把期的数量加在期刊的数量上
									finalJournalCount = counts.get(journalIndex).getCount() + counts.get(issueIndex).getCount();
									counts.get(journalIndex).setCount(finalJournalCount);
									counts.remove(issueIndex);
								} else {// 如果期刊不存在，就把期的数量算上期刊上
									finalJournalCount = counts.get(issueIndex).getCount();
									counts.get(issueIndex).setName("2");
								}
							}
							model.put("typeList", counts);
						}
					}
					/*
					 * //中文分类处理 if(fac.getName().equals("taxonomy")){
					 */

					// 中文分类处理
					if (fac.getName().equals("taxonomy")) {

						List<Count> counts = fac.getValues();
						if (counts != null && counts.size() > 0) {
							ComparatorSubject comparator = new ComparatorSubject();
							Collections.sort(counts, comparator);
							model.put("taxonomyList", counts);
						}
					}
					// 英文分类处理
					if (fac.getName().equals("taxonomyEn")) {
						List<Count> counts = fac.getValues();
						if (counts != null && counts.size() > 0) {
							ComparatorSubject comparator = new ComparatorSubject();
							Collections.sort(counts, comparator);
							model.put("taxonomyEnList", counts);
						}
					}
					// 出版社处理
					if (fac.getName().equals("publisher")) {
						List<Count> counts = fac.getValues();
						if (counts != null && counts.size() > 0) {
							ComparatorSubject comparator = new ComparatorSubject();
							Collections.sort(counts, comparator);
							model.put("publisherList", counts);
						}
					}
					// 语种处理
					if (fac.getName().equals("language")) {
						List<Count> counts = fac.getValues();
						if (counts != null && counts.size() > 0) {
							ComparatorSubject comparator = new ComparatorSubject();
							Collections.sort(counts, comparator);
							model.put("languageList", counts);
						}
					}
					/* } */

					model.put("facetFields", facetFields);
					model.put("pubDateMap", SequenceUtil.MapDescToKey(pubDate));
					if (resultMap.get("count") != null && Long.valueOf(resultMap.get("count").toString()) > 0) {
						List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.get("result");
						List<PPublications> resultList = new ArrayList<PPublications>();
						for (Map<String, Object> idInfo : list) {
							// 根据ID查询产品信息
							// 由于加入了标签，这里不能用get查询
							Map<String, Object> condition = new HashMap<String, Object>();
							condition.put("id", idInfo.get("id"));
							List<PPublications> ppList = this.pPublicationsService.getPubList3(condition, " order by a.createOn ", (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
							if (ppList != null && ppList.size() > 0) {
								PPublications pub = ppList.get(0);

								if (idInfo.containsKey("title") && idInfo.get("title") != null && !"".equals("title"))
									pub.setTitle(idInfo.get("title").toString());
								if (idInfo.containsKey("author") && idInfo.get("author") != null && !"".equals("author"))
									pub.setAuthor(idInfo.get("author").toString());
								if (idInfo.containsKey("isbn") && idInfo.get("isbn") != null && !"".equals("isbn"))
									pub.setCode(idInfo.get("isbn").toString());
								if (idInfo.containsKey("copyPublisher") && idInfo.get("copyPublisher") != null && !"".equals("copyPublisher"))
									pub.getPublisher().setName(idInfo.get("copyPublisher").toString());
								if (idInfo.containsKey("remark") && idInfo.get("remark") != null && !"".equals("remark"))
									pub.setRemark(idInfo.get("remark").toString());

								if (idInfo.containsKey("score") && idInfo.get("score") != null && !"".equals("score"))
									pub.setActivity(idInfo.get("score").toString());
								if (user != null) {
									Map<String, Object> con = new HashMap<String, Object>();
									con.put("publicationsid", idInfo.get("id"));
									con.put("status", 2);
									con.put("userTypeId", user.getUserType().getId() == null ? "" : user.getUserType().getId());
									List<PPrice> price = this.pPublicationsService.getPriceList(con);
									int isFreeUser = request.getSession().getAttribute("isFreeUser") == null ? 0 : (Integer) request.getSession().getAttribute("isFreeUser");
									if (isFreeUser != 1) {
										for (int j = 0; j < price.size(); j++) {
											PPrice pr = price.get(j);
											double endPrice = MathHelper.round(MathHelper.mul(pr.getPrice(), 1.13d));
											price.get(j).setPrice(endPrice);
										}
									}
									pub.setPriceList(price);
								}

								// 查询分类
								// Map<String,Object> con2 = new
								// HashMap<String,Object>();
								// con2.put("publicationsId", id);
								// List<PCsRelation> csList =
								// this.bSubjectService.getSubPubList(con2,
								// " order by a.subject.code ");
								// // Set<PCsRelation> set = new
								// HashSet(Arrays.asList(csList));
								// // pub.setCsRelations(set);
								// pub.setCsList(csList);
								resultList.add(pub);
							}
						}
						model.put("pubDateMap", SequenceUtil.MapDescToKey(pubDate));
						// if(resultList!=null&&!resultList.isEmpty()){
						// String[] keywords = form.getSearchValue().split(" ");
						// HighLightHelper tool = new
						// HighLightHelper(form.getPrefixHTML(),form.getSuffixHTML());
						// for(PPublications publication : resultList){
						// publication.setAuthor(tool.getHighLightText(keywords,publication.getAuthor()));
						// publication.setTitle(tool.getHighLightText(keywords,publication.getTitle()));
						// publication.setRemark(tool.getHighLightText(keywords,publication.getRemark()));
						// }
						// }
						for (PPublications pp : resultList) {
							boolean a = this.pPublicationsService.getLLicenseFlag(pp.getId());
							if (a == true) {
								pp.setSubscribedUser(0);
								pp.setSubscribedIp(0);
							}
						}
						form.setCount(allCount);
						model.put("list", resultList);
					} else {
						form.setCount(0);
					}
				}

			}
			boolean msg = (Boolean) (request.getAttribute("msg") == null ? true : request.getAttribute("msg"));
			if (msg) {
				// CUser user =
				// request.getSession().getAttribute("mainUser")==null?null:(CUser)request.getSession().getAttribute("mainUser");
				if (user != null) {
					if (form.getCount() > 0) {
						CSearchHis obj = new CSearchHis();
						obj.setCreateOn(new Date());
						obj.setKeyword(keyword);
						obj.setType(1);// 临时保存...下次登录的时候清空
						obj.setUser(user);
						obj.setKeyType(form.getSearchsType() == null ? 0 : form.getSearchsType());
						this.cUserService.addSearchHistory(obj);
					}
				}
			}
		} catch (Exception e) {
			if ("keywords can't be null".equals(e.getMessage())) {
				request.setAttribute("prompt", Lang.getLanguage("Controller.Index.search.prompt.error", request.getSession().getAttribute("lang").toString()));// 搜索错误提示
				request.setAttribute("message", Lang.getLanguage("Controller.Index.search.keywords.error", request.getSession().getAttribute("lang").toString()));

				forwardString = "frame/result";
			} else {
				request.setAttribute("prompt", Lang.getLanguage("Controller.Index.search.prompt.error", request.getSession().getAttribute("lang").toString()));// 搜索错误提示
				request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
				forwardString = "frame/result";
			}
		}
		if (null != searchValue2) {
			searchValue2 = searchValue2.replaceAll("\"", "");
		}
		form.setSearchValue(URLDecoder.decode(null == form.getSearchValue() || "".equals(form.getSearchValue()) ? searchValue2 : form.getSearchValue(), "UTF-8"));
		form.setSearchValue2(URLDecoder.decode(null == form.getSearchValue() || "".equals(form.getSearchValue()) ? searchValue2 : form.getSearchValue(), "UTF-8"));
		form.setSearchsType2(request.getParameter("searchsType2"));
		model.put("form", form);
		model.put("current", "search");
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 在已订阅中查询
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/index/searchLicense")
	public ModelAndView searchLicense(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "index/search";
		Map<String, Object> model = new HashMap<String, Object>();
		// 2-全部 1-已订阅
		String searchValue2 = request.getParameter("searchValue2");
		searchValue2 = (null == searchValue2 || "".equals(searchValue2)) ? request.getParameter("searchValue") : searchValue2;
		searchValue2 = getValus(searchValue2);
		try {
			request.getSession().setAttribute("selectType", 1);// selectType
																// 用来保存全局的变量，看是全部还是在已订阅中查询

			String keyword = URLDecoder.decode((null == form.getSearchValue() || "".equals(form.getSearchValue()) ? searchValue2 : form.getSearchValue()), "UTF-8");
			keyword = CharUtil.toSimple(keyword);
			CUser user1 = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			if (keyword != null && !"".equals(keyword)) {
				StringBuffer userIds = new StringBuffer();
				// 访问IP
				long ip = IpUtil.getLongIp(IpUtil.getIp(request));
				// 查询机构信息
				Map<String, Object> mapip = new HashMap<String, Object>();
				mapip.put("ip", ip);
				List<BIpRange> lip = this.configureService.getIpRangeList(mapip, "");
				if (lip != null && lip.size() > 0) {
					// 根据机构ID,查询用户
					for (BIpRange bIpRange : lip) {
						Map<String, Object> uc = new HashMap<String, Object>();
						uc.put("institutionId", bIpRange.getInstitution().getId());
						uc.put("level", 2);
						List<CUser> lu = this.cUserService.getUserList(uc, "");
						for (CUser cUser : lu) {
							userIds.append(cUser.getId()).append(",");
						}
					}
				}
				// 查询用户ID
				if (request.getSession().getAttribute("mainUser") != null) {
					CUser user = (CUser) request.getSession().getAttribute("mainUser");
					userIds.append(user.getId()).append(",");
				}
				// userIds.append(Param.getParam("OAFree.uid.config").get("uid")).append(",");
				if ("".equals(userIds.toString())) {
					throw new CcsException("Controller.Index.searchLicense.noLogin");// 未登录用户，无法按照“已订阅”查询
				} else {
					String userId = userIds.substring(0, userIds.toString().lastIndexOf(","));
					form.setUrl(request.getRequestURL().toString());
					Map<String, String> param = new HashMap<String, String>();
					/** 特殊机构处理 -- START */
					String specialInstitutionFlag = request.getSession().getAttribute("specialInstitutionFlag") != null ? (String) request.getSession().getAttribute("specialInstitutionFlag") : null;
					if (null != specialInstitutionFlag && specialInstitutionFlag.length() > 0) {
						form = this.specialInstitution_handle(form, specialInstitutionFlag);
					}
					/** 特殊机构处理 -- END */
					param.put("language", (form.getLanguage() == null || "".equals(form.getLanguage())) ? null : "\"" + form.getLanguage() + "\"");
					param.put("publisher", (form.getPublisher() == null || "".equals(form.getPublisher())) ? null : "\"" + form.getPublisher() + "\"");
					param.put("type", (form.getPubType() == null || "".equals(form.getPubType())) ? null : form.getPubType());
					param.put("pubDate", (form.getPubDate() == null || "".equals(form.getPubDate())) ? null : form.getPubDate() + "*");
					param.put("taxonomy", (form.getTaxonomy() == null || "".equals(form.getTaxonomy())) ? null : "\"" + form.getTaxonomy() + "\"");
					param.put("taxonomyEn", (form.getTaxonomyEn() == null || "".equals(form.getTaxonomyEn())) ? null : "\"" + form.getTaxonomyEn() + "\"");
					param.put("nochinese", (form.getNochinese() == null || "".equals(form.getNochinese())) ? null : "\"" + form.getNochinese() + "\"");
					param.put("local", (form.getLocal() == null || "".equals(form.getLocal())) ? null : form.getLocal());
					param.put("notLanguage", (null == form.getNotLanguage()) || "".equals(form.getNotLanguage()) ? null : form.getNotLanguage());

					// 在solr中查询 [搜索类型=====0-全文;1-标题;2-作者]
					// String keyword = form.getSearchValue();

					if (form.getIsAccurate() != null && form.getIsAccurate() == 2) {// 要查询的内容
																					// 是否精确查找
																					// 1、否
																					// ；2、是
						keyword = "\"" + keyword + "\"";
					}
					Map<String, Object> resultMap = new HashMap<String, Object>();
					String searchsType2 = request.getParameter("searchsType2");
					searchsType2 = getValus(searchsType2);
					form.setSearchsType(form.getSearchsType() == null || "".equals(form.getSearchsType()) ? 0 : form.getSearchsType());
					switch (form.getSearchsType()) {
					case 0:
						resultMap = this.licenseIndexService.searchByAllFullText(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
						break;
					case 1:
						resultMap = this.licenseIndexService.searchByTitle(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
						break;
					case 2:
						resultMap = this.licenseIndexService.searchByAuthor(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
						break;
					case 3:
						resultMap = this.licenseIndexService.searchByISBN(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
						break;
					case 4:
						resultMap = this.licenseIndexService.searchByPublisher(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
						break;
					default:
						resultMap = this.licenseIndexService.searchByAllFullText(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
						break;
					}

					List<FacetField> facetFields = (List<FacetField>) resultMap.get("facet");
					Map<String, Integer> pubDate = new HashMap<String, Integer>();
					for (FacetField fac : facetFields) {
						if (fac.getName().equals("pubDate")) {
							List<Count> counts = fac.getValues();
							for (Count count : counts) {
								if (count == null || count.getName() == null || count.getName().length() < 4) {
									continue;
								}
								int num = pubDate.get(count.getName().substring(0, 4)) == null ? 0 : Integer.valueOf(pubDate.get(count.getName().substring(0, 4)));
								if (count.getCount() > 0) {
									pubDate.put(count.getName().substring(0, 4).toString(), (num + (int) count.getCount()));
								}
							}
						}
						// 类型处理
						if (fac.getName().equals("type")) {
							List<Count> counts = fac.getValues();
							if (counts != null && counts.size() > 0) {
								int journalIndex = -1;
								int issueIndex = -1;
								for (int i = 0; i < counts.size(); i++) {
									if ("2".equals(counts.get(i).getName())) {
										journalIndex = i;
									} else if ("7".equals(counts.get(i).getName())) {
										issueIndex = i;
									}
								}
								if (issueIndex > -1) {
									long finalJournalCount = 0;
									if (journalIndex > -1) {// 如果存在期时就把期的数量加在期刊的数量上
										finalJournalCount = counts.get(journalIndex).getCount() + counts.get(issueIndex).getCount();
										counts.get(journalIndex).setCount(finalJournalCount);
										counts.remove(issueIndex);
									} else {// 如果期刊不存在，就把期的数量算上期刊上
										finalJournalCount = counts.get(issueIndex).getCount();
										counts.get(issueIndex).setName("2");
									}
								}
								model.put("typeList", counts);
							}
						}
						// 中文分类处理
						if (fac.getName().equals("taxonomy")) {
							List<Count> counts = fac.getValues();
							if (counts != null && counts.size() > 0) {
								ComparatorSubject comparator = new ComparatorSubject();
								Collections.sort(counts, comparator);
								model.put("taxonomyList", counts);
							}
						}
						// 英文分类处理
						if (fac.getName().equals("taxonomyEn")) {
							List<Count> counts = fac.getValues();
							if (counts != null && counts.size() > 0) {
								ComparatorSubject comparator = new ComparatorSubject();
								Collections.sort(counts, comparator);
								model.put("taxonomyEnList", counts);
							}
						}
						// 出版社处理
						if (fac.getName().equals("publisher")) {
							List<Count> counts = fac.getValues();
							if (counts != null && counts.size() > 0) {
								ComparatorSubject comparator = new ComparatorSubject();
								Collections.sort(counts, comparator);
								model.put("publisherList", counts);
							}
						}
						// 语种处理
						if (fac.getName().equals("language")) {

							List<Count> counts = fac.getValues();
							if (counts != null && counts.size() > 0) {
								ComparatorSubject comparator = new ComparatorSubject();
								Collections.sort(counts, comparator);
								model.put("languageList", counts);
							}
						}
					}
					model.put("facetFields", facetFields);
					model.put("pubDateMap", SequenceUtil.MapDescToKey(pubDate));
					// 限制查询结果总数----------开始-----------
					Integer maxCount = Integer.parseInt(Param.getParam("search.config").get("maxCount"));
					Integer allCount = 0;
					if (resultMap.get("count") != null) {
						allCount = Integer.valueOf(resultMap.get("count").toString());
						model.put("queryCount", allCount);// 实际查询结果数量
						// 最多显示1000条
						allCount = maxCount > allCount ? allCount : maxCount;// 分页条显示的数量
					}
					// 限制查询结果总数----------结束-----------
					if (resultMap.get("count") != null && Long.valueOf(resultMap.get("count").toString()) > 0) {
						List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.get("result");
						List<PPublications> resultList = new ArrayList<PPublications>();
						for (Map<String, Object> idInfo : list) {
							// 根据ID查询产品信息
							// 由于加入了标签，这里不能用get查询
							String lid = idInfo.get("id").toString();
							String pid = "";
							if (lid.contains("_")) {
								pid = lid.substring(lid.lastIndexOf("_") + 1, lid.length());
							} else {
								LLicense lli = this.pPublicationsService.getLicense(lid);
								if (lli != null) {
									pid = lli.getPublications().getId();
								}
							}
							if (pid != null && !"".equals(pid)) {
								// 根据ID查询产品信息
								// 由于加入了标签，这里不能用get查询
								Map<String, Object> condition = new HashMap<String, Object>();
								condition.put("id", pid);
								condition.put("check", "false");
								List<PPublications> ppList = this.pPublicationsService.getPubList3(condition, " order by a.createOn ", (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
								if (ppList != null && ppList.size() > 0) {
									PPublications pub = ppList.get(0);
									if (idInfo.containsKey("title") && idInfo.get("title") != null && !"".equals("title"))
										pub.setTitle(idInfo.get("title").toString());
									if (idInfo.containsKey("author") && idInfo.get("author") != null && !"".equals("author"))
										pub.setAuthor(idInfo.get("author").toString());
									if (idInfo.containsKey("isbn") && idInfo.get("isbn") != null && !"".equals("isbn"))
										pub.setCode(idInfo.get("isbn").toString());
									if (idInfo.containsKey("copyPublisher") && idInfo.get("copyPublisher") != null && !"".equals("copyPublisher"))
										pub.getPublisher().setName(idInfo.get("copyPublisher").toString());
									if (idInfo.containsKey("remark") && idInfo.get("remark") != null && !"".equals("remark"))
										pub.setRemark(idInfo.get("remark").toString());

									if (idInfo.containsKey("score") && idInfo.get("score") != null && !"".equals("score"))
										pub.setActivity(idInfo.get("score").toString());
									if (user1 != null) {
										Map<String, Object> con = new HashMap<String, Object>();
										con.put("publicationsid", idInfo.get("id"));
										con.put("status", 2);
										con.put("userTypeId", user1.getUserType().getId() == null ? "" : user1.getUserType().getId());
										List<PPrice> price = this.pPublicationsService.getPriceList(con);
										int isFreeUser = request.getSession().getAttribute("isFreeUser") == null ? 0 : (Integer) request.getSession().getAttribute("isFreeUser");
										if (isFreeUser != 1) {
											for (int j = 0; j < price.size(); j++) {
												PPrice pr = price.get(j);
												double endPrice = MathHelper.round(MathHelper.mul(pr.getPrice(), 1.13d));
												price.get(j).setPrice(endPrice);
											}
										}
										pub.setPriceList(price);
									}
									// 查询分类
									// Map<String,Object> con2 = new
									// HashMap<String,Object>();
									// con2.put("publicationsId",
									// lli.getPublications().getId());
									// List<PCsRelation> csList =
									// this.bSubjectService.getSubPubList(con2,
									// " order by a.subject.code ");
									// // Set<PCsRelation> set = new
									// HashSet(Arrays.asList(csList));
									// // pub.setCsRelations(set);
									// pub.setCsList(csList);
									resultList.add(pub);
								}
							}
						}
						model.put("pubDateMap", SequenceUtil.MapDescToKey(pubDate));
						form.setCount(allCount);
						model.put("list", resultList);
					} else {
						form.setCount(0);
					}
					// ----------------------进行高亮--------------
					// form.setKeyMap(this.highLight(form.getSearchsType(),
					// keyword));
					// ----------------------高亮结束--------------
					// 如果查询出了结果，那么要保存搜索关键字
					boolean msg = (Boolean) (request.getAttribute("msg") == null ? true : request.getAttribute("msg"));
					if (msg) {
						CUser cuser = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
						if (cuser != null) {
							if (form.getCount() > 0) {
								CSearchHis obj = new CSearchHis();
								obj.setCreateOn(new Date());
								obj.setKeyword(keyword);
								obj.setType(1);// 临时保存...下次登录的时候清空
								obj.setUser(cuser);
								obj.setKeyType(form.getSearchsType() == null ? 0 : form.getSearchsType());
								this.cUserService.addSearchHistory(obj);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			if ("keywords can't be null".equals(e.getMessage())) {
				request.setAttribute("prompt", Lang.getLanguage("Controller.Index.search.prompt.error", request.getSession().getAttribute("lang").toString()));// 搜索错误提示
				request.setAttribute("message", Lang.getLanguage("Controller.Index.search.keywords.error", request.getSession().getAttribute("lang").toString()));

				forwardString = "frame/result";
			} else {
				request.setAttribute("prompt", Lang.getLanguage("Controller.Index.search.prompt.error", request.getSession().getAttribute("lang").toString()));// 搜索错误提示
				request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
				forwardString = "frame/result";
			}
		}
		form.setSearchValue(URLDecoder.decode(null == form.getSearchValue() || "".equals(form.getSearchValue()) ? searchValue2 : form.getSearchValue(), "UTF-8"));
		form.setSearchValue2(URLDecoder.decode(null == form.getSearchValue() || "".equals(form.getSearchValue()) ? searchValue2 : form.getSearchValue(), "UTF-8"));
		model.put("form", form);
		model.put("current", "searchLicense");
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 在免费OA中查询
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/index/searchOaFree")
	public ModelAndView searchOaFree(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "index/search";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			request.getSession().setAttribute("selectType", 2);// selectType
																// 用来保存全局的变量，看是全部还是在已订阅中查询
																// 2-免费Oa 1-已订阅
			String searchValue2 = request.getParameter("searchValue2");
			searchValue2 = (null == searchValue2 || "".equals(searchValue2)) ? request.getParameter("searchValue") : searchValue2;
			searchValue2 = getValus(searchValue2);
			String keyword = URLDecoder.decode((null == form.getSearchValue() || "".equals(form.getSearchValue()) ? searchValue2 : form.getSearchValue()), "UTF-8");
			keyword = CharUtil.toSimple(keyword);
			CUser user1 = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			if (keyword != null && !"".equals(keyword)) {
				StringBuffer userIds = new StringBuffer();
				// 访问IP
				long ip = IpUtil.getLongIp(IpUtil.getIp(request));
				// 查询机构信息
				Map<String, Object> mapip = new HashMap<String, Object>();
				mapip.put("ip", ip);
				List<BIpRange> lip = this.configureService.getIpRangeList(mapip, "");
				/*
				 * if(lip!=null&&lip.size()>0){ //根据机构ID,查询用户 for (BIpRange
				 * bIpRange : lip) { Map<String,Object> uc = new
				 * HashMap<String,Object>();
				 * uc.put("institutionId",bIpRange.getInstitution().getId() );
				 * uc.put("level", 2); List<CUser> lu =
				 * this.cUserService.getUserList(uc, ""); for (CUser cUser : lu)
				 * { userIds.append(cUser.getId()).append(","); } } }
				 */
				// 查询用户ID 免费的不加 用户id 只加 userIds= oafree
				/*
				 * if(request.getSession().getAttribute("mainUser")!=null){
				 * CUser user =
				 * (CUser)request.getSession().getAttribute("mainUser");
				 * userIds.append(user.getId()).append(","); }
				 */
				userIds.append(Param.getParam("OAFree.uid.config").get("uid")).append(",");
				if ("".equals(userIds.toString())) {
					throw new CcsException("Controller.Index.searchLicense.noLogin");// 未登录用户，无法按照“已订阅”查询
				} else {
					String userId = userIds.substring(0, userIds.toString().lastIndexOf(","));
					form.setUrl(request.getRequestURL().toString());
					Map<String, String> param = new HashMap<String, String>();
					/** 特殊机构处理 -- START */
					String specialInstitutionFlag = request.getSession().getAttribute("specialInstitutionFlag") != null ? (String) request.getSession().getAttribute("specialInstitutionFlag") : null;
					if (null != specialInstitutionFlag && specialInstitutionFlag.length() > 0) {
						form = this.specialInstitution_handle(form, specialInstitutionFlag);
					}
					/** 特殊机构处理 -- END */
					param.put("language", (form.getLanguage() == null || "".equals(form.getLanguage())) ? null : "\"" + form.getLanguage() + "\"");
					param.put("publisher", (form.getPublisher() == null || "".equals(form.getPublisher())) ? null : "\"" + form.getPublisher() + "\"");
					param.put("type", (form.getPubType() == null || "".equals(form.getPubType())) ? null : form.getPubType());
					// param.put("year", (form.getPubDate() == null ||
					// "".equals(form.getPubDate())) ? null : form.getPubDate()
					// + "*");
					param.put("pubDate", (form.getPubDate() == null || "".equals(form.getPubDate())) ? null : form.getPubDate() + "*");
					param.put("taxonomy", (form.getTaxonomy() == null || "".equals(form.getTaxonomy())) ? null : "\"" + form.getTaxonomy() + "\"");
					param.put("taxonomyEn", (form.getTaxonomyEn() == null || "".equals(form.getTaxonomyEn())) ? null : "\"" + form.getTaxonomyEn() + "\"");
					param.put("nochinese", (form.getNochinese() == null || "".equals(form.getNochinese())) ? null : "\"" + form.getNochinese() + "\"");
					param.put("local", (form.getLocal() == null || "".equals(form.getLocal())) ? null : form.getLocal());
					param.put("notLanguage", (null == form.getNotLanguage()) || "".equals(form.getNotLanguage()) ? null : form.getNotLanguage());
					// 在solr中查询 [搜索类型=====0-全文;1-标题;2-作者]
					// String keyword = form.getSearchValue();

					if (form.getIsAccurate() != null && form.getIsAccurate() == 2) {// 要查询的内容
																					// 是否精确查找
																					// 1、否
																					// ；2、是
						keyword = "\"" + keyword + "\"";
					}
					Map<String, Object> resultMap = new HashMap<String, Object>();
					form.setSearchsType(form.getSearchsType() == null || "".equals(form.getSearchsType()) ? 0 : form.getSearchsType());
					switch (form.getSearchsType()) {
					case 0:
						resultMap = this.licenseIndexService.searchByAllFullText(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
						break;
					case 1:
						resultMap = this.licenseIndexService.searchByTitle(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
						break;
					case 2:
						resultMap = this.licenseIndexService.searchByAuthor(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
						break;
					case 3:
						resultMap = this.licenseIndexService.searchByISBN(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
						break;
					case 4:
						resultMap = this.licenseIndexService.searchByPublisher(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
						break;
					default:
						resultMap = this.licenseIndexService.searchByAllFullText(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
						break;
					}

					List<FacetField> facetFields = (List<FacetField>) resultMap.get("facet");

					Map<String, Integer> pubDate = new HashMap<String, Integer>();
					for (FacetField fac : facetFields) {

						if (fac.getName().equals("pubDate")) {
							List<Count> counts = fac.getValues();
							for (Count count : counts) {
								if (count == null || count.getName() == null || count.getName().length() < 4) {
									continue;
								}
								int num = pubDate.get(count.getName().substring(0, 4)) == null ? 0 : Integer.valueOf(pubDate.get(count.getName().substring(0, 4)));
								if (count.getCount() > 0) {
									pubDate.put(count.getName().substring(0, 4).toString(), (num + (int) count.getCount()));
								}
							}
						}
						/*
						 * if(fac.getName().equals("pubDate")){ List<Count>
						 * counts = fac.getValues(); for (Count count : counts)
						 * { if(count==null || count.getName()==null ||
						 * count.getName().length()<4){ continue; } int num =
						 * pubDate
						 * .get(count.getName().substring(0,4))==null?0:Integer
						 * .valueOf
						 * (pubDate.get(count.getName().substring(0,4)));
						 * if(count.getCount()>0){
						 * pubDate.put(count.getName().substring
						 * (0,4).toString(), (num+(int)count.getCount())); } } }
						 */
						// 类型
						if (fac.getName().equals("type")) {
							List<Count> counts = fac.getValues();
							if (counts != null && counts.size() > 0) {
								int journalIndex = -1;
								int issueIndex = -1;
								for (int i = 0; i < counts.size(); i++) {
									if ("2".equals(counts.get(i).getName())) {
										journalIndex = i;
									} else if ("7".equals(counts.get(i).getName())) {
										issueIndex = i;
									}
								}
								if (issueIndex > -1) {
									long finalJournalCount = 0;
									if (journalIndex > -1) {// 如果存在期时就把期的数量加在期刊的数量上
										finalJournalCount = counts.get(journalIndex).getCount() + counts.get(issueIndex).getCount();
										counts.get(journalIndex).setCount(finalJournalCount);
										counts.remove(issueIndex);
									} else {// 如果期刊不存在，就把期的数量算上期刊上
										finalJournalCount = counts.get(issueIndex).getCount();
										counts.get(issueIndex).setName("2");
									}
								}
								model.put("typeList", counts);
							}
						}
						// 中文分类处理
						if (fac.getName().equals("taxonomy")) {
							List<Count> counts = fac.getValues();
							if (counts != null && counts.size() > 0) {
								ComparatorSubject comparator = new ComparatorSubject();
								Collections.sort(counts, comparator);
								model.put("taxonomyList", counts);
							}
						}
						// 英文分类处理
						if (fac.getName().equals("taxonomyEn")) {
							List<Count> counts = fac.getValues();
							if (counts != null && counts.size() > 0) {
								ComparatorSubject comparator = new ComparatorSubject();
								Collections.sort(counts, comparator);
								model.put("taxonomyEnList", counts);
							}
						}
						// 出版社处理
						if (fac.getName().equals("publisher")) {
							List<Count> counts = fac.getValues();
							if (counts != null && counts.size() > 0) {
								ComparatorSubject comparator = new ComparatorSubject();
								Collections.sort(counts, comparator);
								model.put("publisherList", counts);
							}
						}
						// 语种处理
						if (fac.getName().equals("language")) {
							List<Count> counts = fac.getValues();
							if (counts != null && counts.size() > 0) {
								ComparatorSubject comparator = new ComparatorSubject();
								Collections.sort(counts, comparator);
								model.put("languageList", counts);
							}
						}
					}
					model.put("facetFields", facetFields);
					model.put("pubDateMap", SequenceUtil.MapDescToKey(pubDate));
					// 限制查询结果总数----------开始-----------
					Integer maxCount = Integer.parseInt(Param.getParam("search.config").get("maxCount"));
					Integer allCount = 0;
					if (resultMap.get("count") != null) {
						allCount = Integer.valueOf(resultMap.get("count").toString());
						model.put("queryCount", allCount);// 实际查询结果数量
						// 最多显示1000条
						allCount = maxCount > allCount ? allCount : maxCount;// 分页条显示的数量
					}
					// 限制查询结果总数----------结束-----------
					if (resultMap.get("count") != null && Long.valueOf(resultMap.get("count").toString()) > 0) {
						List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.get("result");
						List<PPublications> resultList = new ArrayList<PPublications>();
						for (Map<String, Object> idInfo : list) {
							// 根据ID查询产品信息
							// 由于加入了标签，这里不能用get查询
							String lid = idInfo.get("id").toString();
							String pid = "";
							if (lid.contains("_")) {
								pid = lid.substring(lid.lastIndexOf("_") + 1, lid.length());
							} else {
								LLicense lli = this.pPublicationsService.getLicense(lid);
								if (lli != null) {
									pid = lli.getPublications().getId();
								}
							}
							if (pid != null && !"".equals(pid)) {
								// 根据ID查询产品信息
								// 由于加入了标签，这里不能用get查询
								Map<String, Object> condition = new HashMap<String, Object>();
								condition.put("id", pid);
								// condition.put("check", false);
								condition.put("status", null);
								List<PPublications> ppList = this.pPublicationsService.getPubList3(condition, " order by a.createOn ", (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
								if (ppList != null && ppList.size() > 0) {
									PPublications pub = ppList.get(0);
									if (pub != null && pub.getRemark() != null && "[无简介]".equals(pub.getRemark())) {
										pub.setRemark("");
									}
									if (idInfo.containsKey("title") && idInfo.get("title") != null && !"".equals("title"))
										pub.setTitle(idInfo.get("title").toString());
									if (idInfo.containsKey("author") && idInfo.get("author") != null && !"".equals("author"))
										pub.setAuthor(idInfo.get("author").toString());
									if (idInfo.containsKey("isbn") && idInfo.get("isbn") != null && !"".equals("isbn"))
										pub.setCode(idInfo.get("isbn").toString());
									if (idInfo.containsKey("copyPublisher") && idInfo.get("copyPublisher") != null && !"".equals("copyPublisher"))
										pub.getPublisher().setName(idInfo.get("copyPublisher").toString());
									if (idInfo.containsKey("remark") && idInfo.get("remark") != null && !"".equals("remark"))
										pub.setRemark(idInfo.get("remark").toString());

									if (idInfo.containsKey("score") && idInfo.get("score") != null && !"".equals("score"))
										pub.setActivity(idInfo.get("score").toString());

									if (user1 != null) {
										Map<String, Object> con = new HashMap<String, Object>();
										con.put("publicationsid", idInfo.get("id"));
										con.put("status", 2);
										con.put("userTypeId", user1.getUserType().getId() == null ? "" : user1.getUserType().getId());
										List<PPrice> price = this.pPublicationsService.getPriceList(con);
										int isFreeUser = request.getSession().getAttribute("isFreeUser") == null ? 0 : (Integer) request.getSession().getAttribute("isFreeUser");
										if (isFreeUser != 1) {
											for (int j = 0; j < price.size(); j++) {
												PPrice pr = price.get(j);
												double endPrice = MathHelper.round(MathHelper.mul(pr.getPrice(), 1.13d));
												price.get(j).setPrice(endPrice);
											}
										}
										pub.setPriceList(price);
									}
									// 查询分类
									// Map<String,Object> con2 = new
									// HashMap<String,Object>();
									// con2.put("publicationsId",
									// lli.getPublications().getId());
									// List<PCsRelation> csList =
									// this.bSubjectService.getSubPubList(con2,
									// " order by a.subject.code ");
									// // Set<PCsRelation> set = new
									// HashSet(Arrays.asList(csList));
									// // pub.setCsRelations(set);
									// pub.setCsList(csList);
									resultList.add(pub);
								}
							}
						}
						model.put("pubDateMap", SequenceUtil.MapDescToKey(pubDate));
						form.setCount(allCount);
						model.put("list", resultList);
					} else {
						form.setCount(0);
					}

					// ----------------------进行高亮--------------
					form.setKeyMap(this.highLight(form.getSearchsType(), keyword));
					// ----------------------高亮结束--------------
					// 如果查询出了结果，那么要保存搜索关键字
					boolean msg = (Boolean) (request.getAttribute("msg") == null ? true : request.getAttribute("msg"));
					if (msg) {
						CUser cuser = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
						if (cuser != null) {
							if (form.getCount() > 0) {
								CSearchHis obj = new CSearchHis();
								obj.setCreateOn(new Date());
								obj.setKeyword(keyword);
								obj.setType(1);// 临时保存...下次登录的时候清空
								obj.setUser(cuser);
								obj.setKeyType(form.getSearchsType() == null ? 0 : form.getSearchsType());
								this.cUserService.addSearchHistory(obj);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			if ("keywords can't be null".equals(e.getMessage())) {
				request.setAttribute("prompt", Lang.getLanguage("Controller.Index.search.prompt.error", request.getSession().getAttribute("lang").toString()));// 搜索错误提示
				request.setAttribute("message", Lang.getLanguage("Controller.Index.search.keywords.error", request.getSession().getAttribute("lang").toString()));

				forwardString = "frame/result";
			} else {
				request.setAttribute("prompt", Lang.getLanguage("Controller.Index.search.prompt.error", request.getSession().getAttribute("lang").toString()));// 搜索错误提示
				request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
				forwardString = "frame/result";
			}
		}
		model.put("form", form);
		model.put("current", "searchOaFree");
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 切换语言
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/language")
	public void language(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		try {
			HttpSession session = request.getSession();
			session.setAttribute("lang", form.getLanguage());
			// 拼接页面显示用的HTML代码
			Map<String, CUser> users = (HashMap<String, CUser>) session.getAttribute("otherUser");
			Iterator it = users.entrySet().iterator(); // 取得键对象
			String org = "";
			String ins = "";
			String admin = "";
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry) it.next();
				CUser user1 = (CUser) pairs.getValue();
				if (1 == user1.getLevel() || 5 == user1.getLevel()) {
					ins += "<div class=\"loginboxChild\">" + user1.getName() + "</div>";
				} else if (2 == user1.getLevel() || 3 == user1.getLevel()) {
					org += "<div class=\"loginboxChild\">" + user1.getName() + "</div>";
				} else if (4 == user1.getLevel()) {
					admin += "<div class=\"loginboxChild\">" + user1.getName() + "</div>";
				}
			}
			String cleardiv = "<div class=\"clear\"></div>";
			if (admin != null && admin.length() > 1) {
				admin = "<div class=\"loginboxParent\">" + Lang.getLanguage("Controller.User.login.label.admin", session.getAttribute("lang").toString()) + "</div>" + admin + cleardiv;// 超级管理员：
			}
			if (org != null && org.length() > 1) {
				org = "<div class=\"loginboxParent\">" + Lang.getLanguage("Controller.User.login.label.insUser", session.getAttribute("lang").toString()) + "</div>" + org + cleardiv;// 机构用户：
			}
			if (ins != null && ins.length() > 1) {
				ins = "<div class=\"loginboxParent\">" + Lang.getLanguage("Controller.User.login.label.person", session.getAttribute("lang").toString()) + "</div>" + ins + cleardiv;// 个人用户：
			}
			session.setAttribute("logininfo", admin + org + ins);
		} catch (Exception e) {
			// e.printStackTrace();
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
		}
	}

	/**
	 * 跳转高级搜索页
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index/advancedSearch")
	public ModelAndView advancedSearch(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "index/advancedSearch";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// 查询分类
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("parentId", "1");// 查询父类不为空的
			List<BSubject> subList = this.bSubjectService.getSubList2(condition, " order by a.treeCode ");
			form.setSubList(subList);
		} catch (Exception e) {
			// e.printStackTrace();
			request.setAttribute("prompt", Lang.getLanguage("Controller.Index.search.prompt.error", request.getSession().getAttribute("lang").toString()));// 搜索错误提示
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "frame/result";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 高级搜索
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/index/advancedSearchSubmit")
	public ModelAndView advancedSearchSubmit(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "index/search_advanced";
		CUser user1 = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
		String toTab = request.getSession().getAttribute("selectType").toString();
		String selectFlag = form.getSelectflag();
		if (selectFlag != null) {
			if (selectFlag == "oaFree" || selectFlag.equals("oaFree")) {
				toTab = "2";
			}
			if (selectFlag == "license" || selectFlag.equals("license")) {
				toTab = "1";
			}
			if (selectFlag == "all" || selectFlag.equals("all")) {
				toTab = "";
			}
		}
		// BInstitution ins = (BInstitution)
		// request.getSession().getAttribute("institution");
		// if (null != user1 || null != ins) {
		// form.setLcense("1");
		// }
		String lcense = form.getLcense();
		String lang = (String) request.getSession().getAttribute("lang");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String isCn = form.getIsCn();
			form.setUrl(request.getRequestURL().toString());

			/*********** 查询条件区*开始 ***************/
			Map<String, String> param = new HashMap<String, String>();
			/** 特殊机构处理 -- START */
			String specialInstitutionFlag = request.getSession().getAttribute("specialInstitutionFlag") != null ? (String) request.getSession().getAttribute("specialInstitutionFlag") : null;
			if (null != specialInstitutionFlag && specialInstitutionFlag.length() > 0) {
				form = this.specialInstitution_handle(form, specialInstitutionFlag);
			}
			/** 特殊机构处理 -- END */
			param.put("language", (form.getLanguage() == null || "".equals(form.getLanguage())) ? null : "\"" + form.getLanguage() + "\"");
			param.put("searchValue", (form.getSearchValue() == null || "".equals(form.getSearchValue())) ? null : URLDecoder.decode(form.getSearchValue(), "UTF-8"));
			param.put("keywordCondition", (form.getKeywordCondition() == null || "".equals(form.getKeywordCondition())) ? null : form.getKeywordCondition().toString());
			param.put("notKeywords", (form.getNotKeywords() == null || "".equals(form.getNotKeywords())) ? null : form.getNotKeywords());
			param.put("title", (form.getTitle() == null || "".equals(form.getTitle())) ? null : form.getTitle());
			param.put("author", (form.getAuthor() == null || "".equals(form.getAuthor())) ? null : form.getAuthor());
			param.put("code", (form.getCode() == null || "".equals(form.getCode())) ? null : form.getCode());
			param.put("taxonomy", (form.getTaxonomy() == null || "".equals(form.getTaxonomy())) ? null : "\"" + form.getTaxonomy() + "\"");
			param.put("taxonomyEn", (form.getTaxonomyEn() == null || "".equals(form.getTaxonomyEn())) ? null : "\"" + form.getTaxonomyEn() + "\"");
			param.put("pubType", (form.getPubType() == null || "".equals(form.getPubType())) ? null : form.getPubType());
			param.put("pubDateStart", (form.getPubDateStart() == null || "".equals(form.getPubDateStart())) ? null : form.getPubDateStart());
			param.put("pubDateEnd", (form.getPubDateEnd() == null || "".equals(form.getPubDateEnd())) ? null : form.getPubDateEnd());
			// param.put("publisher", (form.getPublisher() == null ||
			// "".equals(form.getPublisher())) ? null : "\"" +
			// form.getPublisher() + "\"");
			param.put("publisher", (form.getPublisher() == null || "".equals(form.getPublisher())) ? null : form.getPublisher());
			// param.put("type",
			// (form.getPubType()==null||"".equals(form.getPubType()))?null:form.getPubType());
			param.put("pubDate", (form.getPubDate() == null || "".equals(form.getPubDate())) ? null : form.getPubDate() + "*");
			// 首字母
			param.put("prefixWord", (form.getPrefixWord() == null || "".equals(form.getPrefixWord())) ? null : form.getPrefixWord());
			// 本地资源查找条件
			param.put("local", (form.getLocal() == null || "".equals(form.getLocal())) ? null : form.getLocal());
			// 非语言
			param.put("notLanguage", (null == form.getNotLanguage()) || "".equals(form.getNotLanguage()) ? null : form.getNotLanguage());
			// 最新资源排序检索
			param.put("sortFlag", (null == form.getSortFlag()) || "".equals(form.getSortFlag()) ? null : form.getSortFlag());
			// 判断是是否在外文电子书界面
			param.put("isCn", (null == isCn || "".equals(isCn) ? null : isCn));
			/*********** 查询条件区*结束 ***************/
			/*** 中文 ***/
			if (param.containsKey("taxonomy") && param.get("taxonomy") != null) {
				String[] taxArr = param.get("taxonomy").replace("\"", "").split(",");
				model.put("taxArr", taxArr);
			}
			/*** 英文 ***/
			if (param.containsKey("taxonomyEn") && param.get("taxonomyEn") != null) {
				String[] taxArrEn = param.get("taxonomyEn").replace("\"", "").split(",");
				model.put("taxArrEn", taxArrEn);
			}

			Map<String, Object> resultMap = new HashMap<String, Object>();
			String userId = "";
			if (toTab == "" || toTab.equals("")) {// 按照已订阅查询
				request.getSession().setAttribute("selectType", "");// selectType
																	// 用来保存全局的变量，看是全部还是在已订阅中查询
																	// 2-全部
																	// 1-已订阅、

				resultMap = this.publicationsIndexService.advancedSearch(form.getCurpage(), form.getPageCount(), param, form.getSearchOrder(), isCn);
				// 限制查询结果总数----------开始-----------
				Integer maxCount = Integer.parseInt(Param.getParam("search.config").get("maxCount"));
				Integer allCount = 0;
				if (resultMap.get("count") != null) {
					allCount = Integer.valueOf(resultMap.get("count").toString());
					model.put("queryCount", allCount);// 实际查询结果数量

					// 最多显示1000条
					allCount = maxCount > allCount ? allCount : maxCount;// 分页条显示的数量
				}
				// 限制查询结果总数----------结束-----------
				if (allCount > 0) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.get("result");
					List<PPublications> resultList = new ArrayList<PPublications>();
					for (Map<String, Object> idInfo : list) {
						// 根据ID查询产品信息
						// 由于加入了标签，这里不能用get查询
						Map<String, Object> condition = new HashMap<String, Object>();
						condition.put("id", idInfo.get("id"));
						// condition.put("check","false");
						condition.put("status", null);
						condition.put("available", 3);
						List<PPublications> ppList = this.pPublicationsService.getPubList3(condition, " order by a.createOn ", (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
						if (ppList != null && ppList.size() > 0) {
							PPublications pub = ppList.get(0);
							if (pub != null && pub.getRemark() != null && "[无简介]".equals(pub.getRemark())) {
								pub.setRemark("");
							}
							if (idInfo.containsKey("title") && idInfo.get("title") != null && !"".equals("title"))
								pub.setTitle(idInfo.get("title").toString());
							if (idInfo.containsKey("author") && idInfo.get("author") != null && !"".equals("author"))
								pub.setAuthor(idInfo.get("author").toString());
							if (idInfo.containsKey("isbn") && idInfo.get("isbn") != null && !"".equals("isbn"))
								pub.setCode(idInfo.get("isbn").toString());
							if (null != idInfo.get("copyPublisher") && idInfo.containsKey("copyPublisher") && !"".equals("copyPublisher")) {
								pub.getPublisher().setName(idInfo.get("copyPublisher").toString());
							}
							if (idInfo.containsKey("remark") && idInfo.get("remark") != null && !"".equals("remark"))
								pub.setRemark(idInfo.get("remark").toString());

							if (idInfo.containsKey("score") && idInfo.get("score") != null && !"".equals("score"))
								pub.setActivity(idInfo.get("score").toString());

							if (user1 != null) {
								Map<String, Object> con = new HashMap<String, Object>();
								con.put("publicationsid", idInfo.get("id"));
								con.put("status", 2);
								con.put("userTypeId", user1 == null ? "1" : user1.getUserType() == null ? "1" : user1.getUserType().getId());
								// con.put("userTypeId",
								// user1.getUserType().getId()==null?"":user1.getUserType().getId());
								List<PPrice> price = this.pPublicationsService.getPriceList(con);
								int isFreeUser = request.getSession().getAttribute("isFreeUser") == null ? 0 : (Integer) request.getSession().getAttribute("isFreeUser");
								if (isFreeUser != 1) {
									for (int j = 0; j < price.size(); j++) {
										PPrice pr = price.get(j);
										double endPrice = MathHelper.round(MathHelper.mul(pr.getPrice(), 1.13d));
										price.get(j).setPrice(endPrice);
									}
								}
								pub.setPriceList(price);
							}
							// 查询分类
							// Map<String,Object> con2 = new
							// HashMap<String,Object>();
							// con2.put("publicationsId", pub.getId());
							// List<PCsRelation> csList =
							// this.bSubjectService.getSubPubList(con2,
							// " order by a.subject.code ");
							// pub.setCsList(csList);
							resultList.add(pub);
						}
					}
					// ----------------------进行高亮--------------
					/*
					 * if(form.getSearchValue()!=null&&!"".equals(form.
					 * getSearchValue())){ form.setKeyMap(this.highLight(0,
					 * form.getSearchValue())); }
					 */
					// ----------------------高亮结束--------------
					// model.put("pubDateMap", pubDate);
					form.setCount(allCount);
					model.put("list", resultList);
				} else {
					form.setCount(0);
				}
			} else if (toTab == "1" || toTab.equals("1")) {
				CUser user = (CUser) request.getSession().getAttribute("mainUser");
				request.getSession().setAttribute("selectType", 1);// selectType
																	// 用来保存全局的变量，看是全部还是在已订阅中查询
																	// 2-全部
																	// 1-已订阅
				StringBuffer userIds = new StringBuffer();
				// 访问IP
				long ip = IpUtil.getLongIp(IpUtil.getIp(request));
				// 查询机构信息
				Map<String, Object> mapip = new HashMap<String, Object>();
				mapip.put("ip", ip);
				List<BIpRange> lip = this.configureService.getIpRangeList(mapip, "");
				if (lip != null && lip.size() > 0) {
					// 根据机构ID,查询用户
					for (BIpRange bIpRange : lip) {
						Map<String, Object> uc = new HashMap<String, Object>();
						// uc.put("institutionId",bIpRange.getInstitution().getId()
						// );
						if (user != null && user.getLevel() == 2) {
							uc.put("institutionId", user.getInstitution().getId());
						} else {
							uc.put("institutionId", bIpRange.getInstitution().getId());
						}
						uc.put("insStatus", 1);// 1-机构未被禁用状态
						uc.put("level", 2);
						List<CUser> lu = this.cUserService.getUserList(uc, "");
						for (CUser cUser : lu) {
							userIds.append(cUser.getId()).append(",");
						}
					}
				}
				// 查询用户ID
				if (request.getSession().getAttribute("mainUser") != null) {
					user = (CUser) request.getSession().getAttribute("mainUser");
					userIds.append(user.getId()).append(",");
				}
				// userIds.append(Param.getParam("OAFree.uid.config").get("uid")).append(",");
				if ("".equals(userIds.toString())) {
					throw new CcsException("Controller.Index.searchLicense.noLogin");// 未登录用户，无法按照“已订阅”查询
				} else {
					userId = userIds.substring(0, userIds.toString().lastIndexOf(","));
					// 在solr中查询 [搜索类型=====0-全文;1-标题;2-作者]
					Integer coverType = 1;// 区分免费开源和已订阅
					resultMap = this.licenseIndexService.advancedSearch(coverType, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
					if (resultMap.get("count") != null && Long.valueOf(resultMap.get("count").toString()) > 0) {
						// 限制查询结果总数----------开始-----------
						Integer maxCount = Integer.parseInt(Param.getParam("search.config").get("maxCount"));
						Integer allCount = 0;
						if (resultMap.get("count") != null) {
							allCount = Integer.valueOf(resultMap.get("count").toString());
							model.put("queryCount", allCount);// 实际查询结果数量
							// 最多显示1000条
							allCount = maxCount > allCount ? allCount : maxCount;// 分页条显示的数量
						}
						List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.get("result");
						List<PPublications> resultList = new ArrayList<PPublications>();
						// 这里根据LicenseId查询产品
						for (Map<String, Object> idInfo : list) {
							String lid = idInfo.get("id").toString();
							String pid = "";
							if (lid.contains("_")) {
								pid = lid.substring(lid.lastIndexOf("_") + 1, lid.length());
							} else {
								LLicense lli = this.pPublicationsService.getLicense(lid);
								if (lli != null) {
									pid = lli.getPublications().getId();
								}
							}
							if (pid != null && !"".equals(pid)) {
								// 根据ID查询产品信息
								// 由于加入了标签，这里不能用get查询
								Map<String, Object> condition = new HashMap<String, Object>();
								condition.put("id", pid);
								condition.put("status", null);
								condition.put("available", 3);
								List<PPublications> ppList = this.pPublicationsService.getPubList3(condition, " order by a.createOn ", (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
								if (ppList != null && ppList.size() > 0) {
									PPublications pub = ppList.get(0);
									if (pub != null && pub.getRemark() != null && "[无简介]".equals(pub.getRemark())) {
										pub.setRemark("");
									}
									if (idInfo.containsKey("title") && idInfo.get("title") != null && !"".equals("title"))
										pub.setTitle(idInfo.get("title").toString());
									if (idInfo.containsKey("author") && idInfo.get("author") != null && !"".equals("author"))
										pub.setAuthor(idInfo.get("author").toString());
									if (idInfo.containsKey("isbn") && idInfo.get("isbn") != null && !"".equals("isbn"))
										pub.setCode(idInfo.get("isbn").toString());
									if (idInfo.containsKey("copyPublisher") && idInfo.get("copyPublisher") != null && !"".equals("copyPublisher"))
										pub.getPublisher().setName(idInfo.get("copyPublisher").toString());
									if (idInfo.containsKey("remark") && idInfo.get("remark") != null && !"".equals("remark"))
										pub.setRemark(idInfo.get("remark").toString());

									if (idInfo.containsKey("score") && idInfo.get("score") != null && !"".equals("score"))
										pub.setActivity(idInfo.get("score").toString());
									if (user1 != null) {
										Map<String, Object> con = new HashMap<String, Object>();
										con.put("publicationsid", idInfo.get("id"));
										con.put("status", 2);
										con.put("userTypeId", user1.getUserType().getId() == null ? "" : user1.getUserType().getId());
										List<PPrice> price = this.pPublicationsService.getPriceList(con);
										int isFreeUser = request.getSession().getAttribute("isFreeUser") == null ? 0 : (Integer) request.getSession().getAttribute("isFreeUser");
										if (isFreeUser != 1) {
											for (int j = 0; j < price.size(); j++) {
												PPrice pr = price.get(j);
												double endPrice = MathHelper.round(MathHelper.mul(pr.getPrice(), 1.13d));
												price.get(j).setPrice(endPrice);
											}
										}
										pub.setPriceList(price);
									}
									// 查询分类
									// Map<String,Object> con2 = new
									// HashMap<String,Object>();
									// con2.put("publicationsId", pub.getId());
									// List<PCsRelation> csList =
									// this.bSubjectService.getSubPubList(con2,
									// " order by a.subject.code ");
									// pub.setCsList(csList);
									resultList.add(pub);
								}
							}
						}
						// ----------------------进行高亮--------------
						/*
						 * if(form.getSearchValue()!=null&&!"".equals(form.
						 * getSearchValue())){ form.setKeyMap(this.highLight(0,
						 * form.getSearchValue())); }
						 */
						// ----------------------高亮结束--------------
						form.setCount(allCount);
						model.put("list", resultList);
					} else {
						form.setCount(0);
					}
				}
			} else if (toTab == "2" || toTab.equals("2")) {// 开源、免费查询
				request.getSession().setAttribute("selectType", "2");// selectType
																		// 用来保存全局的变量，看是全部还是在已订阅中查询
																		// 2-全部
																		// 1-已订阅、
				String oafree = "";
				Integer coverType = 2;// 区分 免费开源和已订阅 查询
				Map<String, String> oafreeMap = new HashMap<String, String>();
				oafreeMap = Param.getParam("OAFree.uid.config");
				oafree = oafreeMap.get("uid");
				resultMap = this.licenseIndexService.advancedSearch(coverType, oafree, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
				// resultMap =
				// this.publicationsIndexService.advancedSearch(oafree,form.getCurpage(),
				// form.getPageCount(),param,form.getSearchOrder());

				if (resultMap.get("count") != null && Long.valueOf(resultMap.get("count").toString()) > 0) {
					// 限制查询结果总数----------开始-----------
					Integer maxCount = Integer.parseInt(Param.getParam("search.config").get("maxCount"));
					Integer allCount = 0;
					if (resultMap.get("count") != null) {
						allCount = Integer.valueOf(resultMap.get("count").toString());
						model.put("queryCount", allCount);// 实际查询结果数量
						// 最多显示1000条
						allCount = maxCount > allCount ? allCount : maxCount;// 分页条显示的数量
					}
					// 限制查询结果总数----------结束-----------
					List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.get("result");
					List<PPublications> resultList = new ArrayList<PPublications>();
					for (Map<String, Object> idInfo : list) {
						// 根据ID查询产品信息
						// 由于加入了标签，这里不能用get查询
						Map<String, Object> condition = new HashMap<String, Object>();
						String oafreeid = "";
						if (idInfo.get("id").toString().startsWith("oafree_")) {
							oafreeid = idInfo.get("id").toString().replaceAll("oafree_", "");
						}
						condition.put("id", oafreeid);
						// condition.put("check","false");
						condition.put("status", null);
						condition.put("available", 3);
						// condition.put("oafree",2);
						List<PPublications> ppList = this.pPublicationsService.getPubList3(condition, " order by a.createOn ", (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
						if (ppList != null && ppList.size() > 0) {
							PPublications pub = ppList.get(0);
							if (pub != null && pub.getRemark() != null && "[无简介]".equals(pub.getRemark())) {
								pub.setRemark("");
							}
							if (idInfo.containsKey("title") && idInfo.get("title") != null && !"".equals("title"))
								pub.setTitle(idInfo.get("title").toString());
							if (idInfo.containsKey("author") && idInfo.get("author") != null && !"".equals("author"))
								pub.setAuthor(idInfo.get("author").toString());
							if (idInfo.containsKey("isbn") && idInfo.get("isbn") != null && !"".equals("isbn"))
								pub.setCode(idInfo.get("isbn").toString());
							if (idInfo.containsKey("copyPublisher") && idInfo.get("copyPublisher") != null && !"".equals("copyPublisher"))
								pub.getPublisher().setName(idInfo.get("copyPublisher").toString());
							if (idInfo.containsKey("remark") && idInfo.get("remark") != null && !"".equals("remark"))
								pub.setRemark(idInfo.get("remark").toString());

							if (idInfo.containsKey("score") && idInfo.get("score") != null && !"".equals("score"))
								pub.setActivity(idInfo.get("score").toString());

							if (user1 != null) {
								Map<String, Object> con = new HashMap<String, Object>();
								con.put("publicationsid", idInfo.get("id"));
								con.put("status", 2);
								con.put("userTypeId", user1 == null ? "1" : user1.getUserType() == null ? "1" : user1.getUserType().getId());
								// con.put("userTypeId",
								// user1.getUserType().getId()==null?"":user1.getUserType().getId());
								List<PPrice> price = this.pPublicationsService.getPriceList(con);
								int isFreeUser = request.getSession().getAttribute("isFreeUser") == null ? 0 : (Integer) request.getSession().getAttribute("isFreeUser");
								if (isFreeUser != 1) {
									for (int j = 0; j < price.size(); j++) {
										PPrice pr = price.get(j);
										double endPrice = MathHelper.round(MathHelper.mul(pr.getPrice(), 1.13d));
										price.get(j).setPrice(endPrice);
									}
								}
								pub.setPriceList(price);
							}
							// 查询分类
							// Map<String,Object> con2 = new
							// HashMap<String,Object>();
							// con2.put("publicationsId", pub.getId());
							// List<PCsRelation> csList =
							// this.bSubjectService.getSubPubList(con2,
							// " order by a.subject.code ");
							// pub.setCsList(csList);
							resultList.add(pub);
						}
					}
					// ----------------------进行高亮--------------
					/*
					 * if(form.getSearchValue()!=null&&!"".equals(form.
					 * getSearchValue())){ form.setKeyMap(this.highLight(0,
					 * form.getSearchValue())); }
					 */
					// ----------------------高亮结束--------------
					// model.put("pubDateMap", pubDate);
					form.setCount(allCount);
					model.put("list", resultList);
				} else {
					form.setCount(0);
				}
			}
			List<FacetField> facetFields = (List<FacetField>) resultMap.get("facet");
			Map<String, Integer> pubDate = new HashMap<String, Integer>();
			for (FacetField fac : facetFields) {
				if (fac.getName().equals("pubDate")) {
					List<Count> counts = fac.getValues();
					for (Count count : counts) {
						if (count == null || count.getName() == null || count.getName().length() < 4) {
							continue;
						}
						int num = pubDate.get(count.getName().substring(0, 4)) == null ? 0 : Integer.valueOf(pubDate.get(count.getName().substring(0, 4)));
						if (count.getCount() > 0) {
							pubDate.put(count.getName().substring(0, 4).toString(), (num + (int) count.getCount()));
						}
					}
				}
				// 中文分类处理
				if (fac.getName().equals("taxonomy")) { // 过滤中文取相同分类 yangheqing
														// 2014-05-27
					List<Count> counts = fac.getValues();
					if (param.containsKey("taxonomy") && param.get("taxonomy") != null && !"".equals(param.get("taxonomy"))) {
						String[] taxArr = param.get("taxonomy").replace("\"", "").split(",");

						for (int i = counts.size() - 1; i >= 0; i--) {
							/*** 中文 ***/
							String subCode = taxArr[taxArr.length - 1].split(" ")[0];
							if (!counts.get(i).toString().toLowerCase().startsWith(subCode.toLowerCase())) {
								counts.remove(i);
							}
						}
					} else {
						/*
						 * for(int i=counts.size()-1;i>=0;i--){
						 *//*** 中文 ***/
						/*
						 * if(counts.get(i).toString().split(" ")[0].toString().
						 * length()>1){ counts.remove(i); } }
						 */
					}
					if (counts != null && counts.size() > 0) {
						ComparatorSubject comparator = new ComparatorSubject();
						Collections.sort(counts, comparator);
						model.put("taxonomyList", counts);
					}
				}
				// 英文分类处理
				if (fac.getName().equals("taxonomyEn")) {
					List<Count> counts = fac.getValues();
					if (param.containsKey("taxonomyEn") && param.get("taxonomyEn") != null && !"".equals(param.get("taxonomyEn"))) {
						String[] taxArr = param.get("taxonomyEn").replace("\"", "").split(",");

						for (int i = counts.size() - 1; i >= 0; i--) {
							String subCode = taxArr[taxArr.length - 1].split(" ")[0];
							if (!counts.get(i).toString().toLowerCase().startsWith(subCode.toLowerCase())) {
								counts.remove(i);
							}
						}
					} else {
						/*
						 * for(int i=counts.size()-1;i>=0;i--){
						 * if(counts.get(i). toString().split(" "
						 * )[0].toString().length()>1){ counts.remove(i); } }
						 */
					}
					if (counts != null && counts.size() > 0) {
						ComparatorSubject comparator = new ComparatorSubject();
						Collections.sort(counts, comparator);
						model.put("taxonomyEnList", counts);
					}
				}

				// 出版社处理
				if (fac.getName().equals("publisher")) {
					List<Count> counts = fac.getValues();
					if (counts != null && counts.size() > 0) {
						ComparatorSubject comparator = new ComparatorSubject();
						Collections.sort(counts, comparator);
						model.put("publisherList", counts);
					}
				}
				// 类型处理
				if (fac.getName().equals("type")) {
					List<Count> counts = fac.getValues();
					if (counts != null && counts.size() > 0) {
						int journalIndex = -1;
						int issueIndex = -1;
						for (int i = 0; i < counts.size(); i++) {
							if ("2".equals(counts.get(i).getName())) {
								journalIndex = i;
							} else if ("7".equals(counts.get(i).getName())) {
								issueIndex = i;
							}
						}
						if (issueIndex > -1) {
							long finalJournalCount = 0;
							if (journalIndex > -1) {// 如果存在期时就把期的数量加在期刊的数量上
								finalJournalCount = counts.get(journalIndex).getCount() + counts.get(issueIndex).getCount();
								counts.get(journalIndex).setCount(finalJournalCount);
								counts.remove(issueIndex);
							} else {// 如果期刊不存在，就把期的数量算上期刊上
								finalJournalCount = counts.get(issueIndex).getCount();
								counts.get(issueIndex).setName("2");
							}
						}
						model.put("typeList", counts);
					}
				}

				// 语种处理
				if (fac.getName().equals("language")) {

					List<Count> counts = fac.getValues();
					if (counts != null && counts.size() > 0) {
						ComparatorSubject comparator = new ComparatorSubject();
						Collections.sort(counts, comparator);
						model.put("languageList", counts);
					}
				}

				if (model.get("queryCount") == null) {
					model.put("queryCount", 0);
				}
				model.put("facetFields", facetFields);
				model.put("pubDateMap", SequenceUtil.MapDescToKey(pubDate));
			}
			if (form.getSearchValue() != null && !"".equals(form.getSearchValue()) && form.getCount() > 0) {
				CUser cuser = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
				if (cuser != null) {
					if (form.getCount() > 0) {
						CSearchHis obj = new CSearchHis();
						obj.setCreateOn(new Date());
						obj.setKeyword(form.getSearchValue());
						obj.setType(1);// 临时保存...下次登录的时候清空
						obj.setUser(cuser);
						obj.setKeyType(form.getSearchsType() == null ? 0 : form.getSearchsType());
						this.cUserService.addSearchHistory(obj);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("prompt", Lang.getLanguage("Controller.Index.search.prompt.error", request.getSession().getAttribute("lang").toString()));// 搜索错误提示
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "frame/result";
		}
		if (toTab == "" || toTab.equals("")) {
			model.put("current", "search");
		} else if (toTab == "1" || toTab.equals("1")) {
			model.put("current", "searchLicense");
		} else if (toTab == "2" || toTab.equals("2")) {
			model.put("current", "searchOaFree");
		}
		// 用于回显搜索关键词
		if (null != form.getSearchValue() && !"".equals(form.getSearchValue().toString())) {
			form.setSearchValue(URLDecoder.decode(form.getSearchValue(), "UTF-8"));
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	@SuppressWarnings("unused")
	private Map<String, String> highLight(int type, String keyword) throws Exception {
		Map<String, String> keyMap = new HashMap<String, String>();
		String key = "";
		String[] result = null;
		String[] types = { "fullText", "title", "author", "isbn", "publisher", "remark" };
		switch (type) {
		case 0:
			for (int i = 1; i < types.length; i++) {
				String field = types[i];
				key = "";
				result = this.publicationsIndexService.IKKewword(field, keyword);
				for (int j = 0; j < result.length; j++) {
					key += "\"" + result[j] + "\",";
				}
				keyMap.put(field, key.trim().substring(0, key.lastIndexOf(",")));
			}
			break;
		case 1:
			key = "";
			result = this.publicationsIndexService.IKKewword("title", keyword);
			for (int j = 0; j < result.length; j++) {
				key += "\"" + result[j] + "\",";
			}
			keyMap.put("title", key.trim().substring(0, key.lastIndexOf(",")));
			break;
		case 2:
			key = "";
			result = this.publicationsIndexService.IKKewword("author", keyword);
			for (int j = 0; j < result.length; j++) {
				key += "\"" + result[j] + "\",";
			}
			keyMap.put("author", key.trim().substring(0, key.lastIndexOf(",")));
			break;
		case 3:
			key = "";
			result = this.publicationsIndexService.IKKewword("isbn", keyword);
			for (int j = 0; j < result.length; j++) {
				key += "\"" + result[j] + "\",";
			}
			keyMap.put("isbn", key.trim().substring(0, key.lastIndexOf(",")));
			break;
		case 4:
			key = "";
			result = this.publicationsIndexService.IKKewword("copyPublisher", keyword);
			for (int j = 0; j < result.length; j++) {
				key += "\"" + result[j] + "\",";
			}
			keyMap.put("publisher", key.trim().substring(0, key.lastIndexOf(",")));
			break;
		default:
			for (int i = 1; i < types.length; i++) {
				String field = types[i];
				key = "";
				result = this.publicationsIndexService.IKKewword(field, keyword);
				for (int j = 0; j < result.length; j++) {
					key += "\"" + result[j] + "\",";
				}
				keyMap.put(field, key.trim().substring(0, key.lastIndexOf(",")));
			}
			break;
		}
		return keyMap;
	}

	/**
	 * 中文页搜索热词 JQuery异步加载
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pages/index/cnHotWordss")
	public ModelAndView hotWordss(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "index/index/cnHotWords";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			form.setUrl(request.getRequestURL().toString());
			// 在日志表中查询出搜索最多的
			// 1、Ip范围内 根据机构来查询
			// 2、Ip范围外 根据全局来查询
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("type", 3);// 操作类型1-访问摘要 2-访问内容 3-检索
			String ins_Id = "";
			// Ip范围内
			if (request.getSession().getAttribute("institution") != null) {
				if (request.getSession().getAttribute("mainUser") != null) {
					CUser user = (CUser) request.getSession().getAttribute("mainUser");
					if (user.getLevel() != 2) {// 不是图书馆管理员
						ins_Id = ((BInstitution) request.getSession().getAttribute("institution")).getId();
					}
				} else {
					ins_Id = ((BInstitution) request.getSession().getAttribute("institution")).getId();
				}
			} // IP范围外的全部看到的是全局的
			condition.put("institutionId", ins_Id);
			condition.put("keywordNotNull", 1);
			condition.put("isCn", true);
			// 取前6个
			List<LAccess> list = this.logAOPService.getcnLogOfHotWords(condition, " group by a.activity order by count(*) desc ", 9, 0);
			model.put("list", list);
			model.put("form", form);
		} catch (Exception e) {
			e.printStackTrace();
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 首页热读资源 JQuery异步加载
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pages/index/hotReading")
	public ModelAndView hotReading(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "index/index/hotReading";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			form.setUrl(request.getRequestURL().toString());
			// 在日志表中查询出搜索最多的
			// 1、Ip范围内 根据机构来查询
			// 2、Ip范围外 根据全局来查询
			Integer num = 4;
			if (request.getParameter("num") != null && !"".equals(request.getParameter("num").toString())) {
				num = Integer.valueOf(request.getParameter("num").toString());
			}
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("type", 2);// 操作类型1-访问摘要 2-访问内容 3-检索
			condition.put("pubStatus", 2);
			/*
			 * condition.put("author", "null"); condition.put("publisher",
			 * "null");
			 */
			String ins_Id = "";
			/*
			 * //Ip范围内
			 * if(request.getSession().getAttribute("institution")!=null){
			 * if(request.getSession().getAttribute("mainUser")!=null){ CUser
			 * user = (CUser)request.getSession().getAttribute("mainUser");
			 * if(user.getLevel()!=2){//不是图书馆管理员 ins_Id =
			 * ((BInstitution)request.
			 * getSession().getAttribute("institution")).getId(); } }else{
			 * ins_Id =
			 * ((BInstitution)request.getSession().getAttribute("institution"
			 * )).getId(); } }//IP范围外的全部看到的是全局的 condition.put("institutionId",
			 * ins_Id);
			 */
			// IP范围内查看当前机构的4条热读资源
			if (request.getSession().getAttribute("institution") != null) {
				condition.put("institutionId", ((BInstitution) request.getSession().getAttribute("institution")).getId());
				condition.put("license", "true");// IP范围内时仅显示license有效的出版物，ip范围外不考虑license是否有效
			} else {// IP范围外查看全局的
				if (request.getSession().getAttribute("mainUser") != null) {
					CUser user = (CUser) request.getSession().getAttribute("mainUser");
					if (user.getLevel() == 2) {// IP范围外，机构管理员查看当前机构的4条热读资源
						condition.put("institutionId", user.getInstitution().getId());
						condition.put("license", "true");
					} else {
						condition.put("institutionId", ins_Id);
					}
				} else {
					condition.put("institutionId", ins_Id);
				}
			}
			List<LAccess> list = this.logAOPService.getLogOfHotReading(condition, " group by a.publications.id order by count(*) desc ", num, 0);
			map.put("pubList", list);
			map.put("form", form);
			// map.put("request", request);
		} catch (Exception e) {
			e.printStackTrace();
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, map);
	}

	/**
	 * 首页最近阅读JQuery异步加载
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pages/index/recentlyRead")
	public ModelAndView recentlyRead(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "index/index/recentlyRead";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			form.setUrl(request.getRequestURL().toString());
			// 1、当用户登陆时查询
			// 2、在日志表中查询最近阅读的4本书
			Integer num = 5;
			if (request.getParameter("num") != null && !"".equals(request.getParameter("num").toString())) {
				num = Integer.valueOf(request.getParameter("num").toString());
			}
			CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			List<LAccess> list = null;
			if (user != null) {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("type", 2);// 操作类型: 1-访问摘要， 2-访问内容， 3-检索
				condition.put("pubStatus", 2);// 书籍状态：1-未上架， 2-已上架
				condition.put("userId", user.getId());
				condition.put("maxDate", "true");
				condition.put("license", "true");
				condition.put("available", 3);// 选用状态:1-不可用(中图未选用)
												// 2-可用（中图已经选用）3-政治原因 4-版权原因
												// 取前4个
				list = this.logAOPService.getLogOfRecentlyRead(condition, " order by a.createOn desc ", num, 0);
			}
			model.put("list", list);
			model.put("form", form);
		} catch (Exception e) {
			e.printStackTrace();
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 全部搜索List下载
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/index/searchDownList")
	public ModelAndView searchDownList(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "index/search";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String keyword = form.getSearchValue();
			keyword = CharUtil.toSimple(keyword);
			CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			if (user != null && (user.getLevel() == 2 || user.getLevel() == 5)) {
				Integer c = 20;
				if (user.getLevel() == 2)
					c = 50;
				if (keyword != null && !"".equals(keyword)) {
					form.setUrl(request.getRequestURL().toString());
					Map<String, String> param = new HashMap<String, String>();
					/** 特殊机构处理 -- START */
					String specialInstitutionFlag = request.getSession().getAttribute("specialInstitutionFlag") != null ? (String) request.getSession().getAttribute("specialInstitutionFlag") : null;
					if (null != specialInstitutionFlag && specialInstitutionFlag.length() > 0) {
						form = this.specialInstitution_handle(form, specialInstitutionFlag);
					}
					/** 特殊机构处理 -- END */
					param.put("language", (form.getLanguage() == null || "".equals(form.getLanguage())) ? null : "\"" + form.getLanguage() + "\"");
					param.put("publisher", (form.getPublisher() == null || "".equals(form.getPublisher())) ? null : "\"" + form.getPublisher() + "\"");
					param.put("type", (form.getPubType() == null || "".equals(form.getPubType())) ? null : form.getPubType());
					param.put("pubDate", (form.getPubDate() == null || "".equals(form.getPubDate())) ? null : form.getPubDate() + "*");
					param.put("taxonomy", (form.getTaxonomy() == null || "".equals(form.getTaxonomy())) ? null : "\"" + form.getTaxonomy() + "\"");
					param.put("taxonomyEn", (form.getTaxonomyEn() == null || "".equals(form.getTaxonomyEn())) ? null : "\"" + form.getTaxonomyEn() + "\"");

					param.put("nochinese", (form.getNochinese() == null || "".equals(form.getNochinese())) ? null : "\"" + form.getNochinese() + "\"");
					param.put("local", (form.getLocal() == null || "".equals(form.getLocal())) ? null : form.getLocal());
					param.put("notLanguage", (null == form.getNotLanguage()) || "".equals(form.getNotLanguage()) ? null : form.getNotLanguage());

					// 在solr中查询 [搜索类型=====0-全文;1-标题;2-作者]
					Map<String, Object> resultMap = new HashMap<String, Object>();
					form.setSearchsType(form.getSearchsType() == null || "".equals(form.getSearchsType()) ? 0 : form.getSearchsType());
					if (form.getIsAccurate() != null && form.getIsAccurate() == 2) {// 要查询的内容
																					// 是否精确查找
																					// 1、否
																					// ；2、是
						keyword = "\"" + keyword + "\"";
					}
					switch (form.getSearchsType()) {
					case 0:
						resultMap = this.publicationsIndexService.searchByAllFullText(keyword, form.getCurpage(), c, param, form.getSearchOrder());
						break;
					case 1:
						resultMap = this.publicationsIndexService.searchByTitle(keyword, form.getCurpage(), c, param, form.getSearchOrder());
						break;
					case 2:
						resultMap = this.publicationsIndexService.searchByAuthor(keyword, form.getCurpage(), c, param, form.getSearchOrder());
						break;
					case 3:
						resultMap = this.publicationsIndexService.searchByISBN(keyword, form.getCurpage(), c, param, form.getSearchOrder());
						break;
					case 4:
						resultMap = this.publicationsIndexService.searchByPublisher(keyword, form.getCurpage(), c, param, form.getSearchOrder());
						break;
					default:
						resultMap = this.publicationsIndexService.searchByAllFullText(keyword, form.getCurpage(), c, param, form.getSearchOrder());
						break;
					}

					// ***************************生成Excel******************************//
					// 输出的excel文件工作表名
					String worksheet = "Product_List";
					// excel工作表的标题
					StringBuffer sb = new StringBuffer();
					// sb.append("Title;");
					// sb.append("ISBN;");
					// sb.append("ISSN;");
					// sb.append("code;");
					// sb.append("Type;");
					// sb.append("Author;");
					// sb.append("Publisher;");
					// sb.append("Remark;");

					sb.append("ISBN/ISSN;");
					sb.append("标题;");
					sb.append("作者;");
					sb.append("出版社;");
					sb.append("出版年;");
					sb.append("币制价格;");
					sb.append("简介;");
					sb.append("卷;");
					sb.append("期;");
					sb.append("语种;");
					sb.append("类型;");
					sb.append("PISBN/EISSN;");

					String title[] = sb.toString().split(";");
					WritableWorkbook workbook;
					OutputStream os = response.getOutputStream();
					response.reset();// 清空输出流
					response.setHeader("Content-disposition", "attachment; filename=" + worksheet + ".xls");// 设定输出文件头
					response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型

					workbook = Workbook.createWorkbook(os);

					WritableSheet sheet = workbook.createSheet(worksheet, 0);

					for (int i = 0; i < title.length; i++) {
						// Label(列号,行号 ,内容 )
						sheet.addCell(new Label(i, 0, title[i]));
					}
					if (resultMap.get("count") != null && Long.valueOf(resultMap.get("count").toString()) > 0) {
						List<Map<String, String>> list = (List<Map<String, String>>) resultMap.get("result");

						int row = 1;
						// 循环list 查询单个产品下边的价格列表
						// 1-电子书 2-期刊 3-章节 4-文章 5-数据库 6-期刊卷 7 期刊期
						// 99产品包(用来在订单明细中区分)
						for (Map<String, String> idInfo : list) {
							// 根据ID查询产品信息
							// 由于加入了标签，这里不能用get查询
							Map<String, Object> condition = new HashMap<String, Object>();
							condition.put("id", idInfo.get("id"));
							condition.put("available", 3);
							condition.put("status", null);
							List<PPublications> ppList = this.pPublicationsService.getPubList2(condition, " order by a.createOn ", (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
							if (ppList != null && ppList.size() > 0) {
								PPublications pub = ppList.get(0);
								if (pub != null && pub.getRemark() != null && "[无简介]".equals(pub.getRemark())) {
									pub.setRemark("");
								}
								sheet.addCell(new Label(0, row, pub.getCode() != null ? pub.getCode() : ""));
								sheet.addCell(new Label(1, row, pub.getTitle() != null ? pub.getTitle() : ""));
								sheet.addCell(new Label(2, row, pub.getAuthor() != null ? pub.getAuthor() : ""));
								sheet.addCell(new Label(3, row, pub.getPublisher() == null ? "" : pub.getPublisher().getName()));
								sheet.addCell(new Label(4, row, pub.getPubDate() != null ? pub.getPubDate() : ""));
								String str1 = pub.getLcurr() != null ? pub.getLcurr() : "";
								str1 += pub.getListPrice() != null ? pub.getListPrice().toString() : "";
								sheet.addCell(new Label(5, row, str1));
								String pubRemark = "";
								StringBuffer aaa = new StringBuffer();
								if (pub.getRemark() != null) {
									pubRemark = pub.getRemark();
									pubRemark = pubRemark.replaceAll("<([^>]*)>", "").replaceAll("[\\t\\n\\r]", "").replace(" ", "");
								}

								sheet.addCell(new Label(6, row, pubRemark));
								sheet.addCell(new Label(7, row, pub.getVolumeCode() != null ? pub.getVolumeCode() : ""));
								sheet.addCell(new Label(8, row, pub.getIssueCode() != null ? pub.getIssueCode() : ""));
								sheet.addCell(new Label(9, row, pub.getLang() != null ? pub.getLang() : "eng"));
								sheet.addCell(new Label(10, row, pub.getType() != null ? pub.getType().toString() : ""));
								if (pub.getType() == 1) {// 图书
									sheet.addCell(new Label(11, row, pub.getSisbn() != null ? pub.getSisbn() : ""));
								} else if (pub.getType() == 2 || pub.getType() == 4 || pub.getType() == 6 || pub.getType() == 7) {// 期刊=2
																																	// 卷=6
																																	// 期=7
																																	// 文章=4
									sheet.addCell(new Label(11, row, pub.getPissn() != null ? pub.getPissn() : ""));
								}

								row++;

								// sheet.addCell(new Label(0, row,
								// pub.getTitle()));
								// if (pub.getType() == 1 || pub.getType() == 3)
								// {
								// sheet.addCell(new Label(1, row,
								// pub.getCode()));
								// } else if (pub.getType() == 2 ||
								// pub.getType() == 4 || pub.getType() == 6 ||
								// pub.getType() == 7) {
								// sheet.addCell(new Label(2, row,
								// pub.getCode()));
								// } else if (pub.getType() == 5) {
								// sheet.addCell(new Label(3, row,
								// pub.getCode()));
								// }
								// if (pub.getType() == 1) {
								// sheet.addCell(new Label(4, row,
								// Lang.getLanguage("Pages.User.Favorites.Table.Label.type1",
								// request.getSession().getAttribute("lang").toString())));
								// } else if (pub.getType() == 2) {
								// sheet.addCell(new Label(4, row,
								// Lang.getLanguage("Pages.User.Favorites.Table.Label.type2",
								// request.getSession().getAttribute("lang").toString())));
								// } else if (pub.getType() == 3) {
								// sheet.addCell(new Label(4, row,
								// Lang.getLanguage("Pages.User.Favorites.Table.Label.type3",
								// request.getSession().getAttribute("lang").toString())));
								// } else if (pub.getType() == 4) {
								// sheet.addCell(new Label(4, row,
								// Lang.getLanguage("Pages.User.Favorites.Table.Label.type4",
								// request.getSession().getAttribute("lang").toString())));
								// } else if (pub.getType() == 5) {
								// sheet.addCell(new Label(4, row,
								// Lang.getLanguage("Pages.User.Favorites.Table.Label.type5",
								// request.getSession().getAttribute("lang").toString())));
								// } else if (pub.getType() == 6) {
								// sheet.addCell(new Label(4, row,
								// Lang.getLanguage("Pages.User.Favorites.Table.Label.type6",
								// request.getSession().getAttribute("lang").toString())));
								// } else if (pub.getType() == 7) {
								// sheet.addCell(new Label(4, row,
								// Lang.getLanguage("Pages.User.Favorites.Table.Label.type7",
								// request.getSession().getAttribute("lang").toString())));
								// }
								// sheet.addCell(new Label(5, row,
								// pub.getAuthor()));
								// sheet.addCell(new Label(6, row,
								// pub.getPublisher() == null ? "" :
								// pub.getPublisher().getName()));
								// sheet.addCell(new Label(7, row,
								// pub.getRemark()));
								// row++;
							}
						}
					}
					workbook.write();
					workbook.close();
					os.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("prompt", Lang.getLanguage("Controller.Index.search.prompt.error", request.getSession().getAttribute("lang").toString()));// 搜索错误提示
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "frame/result";
			model.put("form", form);
			return new ModelAndView(forwardString, model);
		}
		return null;
	}

	/**
	 * 已订阅搜索List下载
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/index/searchLicenseDownList")
	public ModelAndView searchLicenseDownList(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "index/search";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String keyword = form.getSearchValue();
			keyword = CharUtil.toSimple(keyword);
			CUser user1 = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			if (user1 != null && (user1.getLevel() == 2 || user1.getLevel() == 5)) {
				Integer c = 20;
				if (user1.getLevel() == 2)
					c = 50;
				if (keyword != null && !"".equals(keyword)) {
					StringBuffer userIds = new StringBuffer();
					// 访问IP
					long ip = IpUtil.getLongIp(IpUtil.getIp(request));
					// 查询机构信息
					Map<String, Object> mapip = new HashMap<String, Object>();
					mapip.put("ip", ip);
					List<BIpRange> lip = this.configureService.getIpRangeList(mapip, "");
					if (lip != null && lip.size() > 0) {
						// 根据机构ID,查询用户
						for (BIpRange bIpRange : lip) {
							Map<String, Object> uc = new HashMap<String, Object>();
							uc.put("institutionId", bIpRange.getInstitution().getId());
							uc.put("level", 2);
							List<CUser> lu = this.cUserService.getUserList(uc, "");
							for (CUser cUser : lu) {
								userIds.append(cUser.getId()).append(",");
							}
						}
					}
					// 查询用户ID
					if (request.getSession().getAttribute("mainUser") != null) {
						CUser user = (CUser) request.getSession().getAttribute("mainUser");
						userIds.append(user.getId()).append(",");
					}
					userIds.append(Param.getParam("OAFree.uid.config").get("uid")).append(",");
					if ("".equals(userIds.toString())) {
						throw new CcsException("Controller.Index.searchLicense.noLogin");// 未登录用户，无法按照“已订阅”查询
					} else {
						String userId = userIds.substring(0, userIds.toString().lastIndexOf(","));
						form.setUrl(request.getRequestURL().toString());
						Map<String, String> param = new HashMap<String, String>();
						/** 特殊机构处理 -- START */
						String specialInstitutionFlag = request.getSession().getAttribute("specialInstitutionFlag") != null ? (String) request.getSession().getAttribute("specialInstitutionFlag") : null;
						if (null != specialInstitutionFlag && specialInstitutionFlag.length() > 0) {
							form = this.specialInstitution_handle(form, specialInstitutionFlag);
						}
						/** 特殊机构处理 -- END */
						param.put("language", (form.getLanguage() == null || "".equals(form.getLanguage())) ? null : "\"" + form.getLanguage() + "\"");
						param.put("publisher", (form.getPublisher() == null || "".equals(form.getPublisher())) ? null : "\"" + form.getPublisher() + "\"");
						param.put("type", (form.getPubType() == null || "".equals(form.getPubType())) ? null : form.getPubType());
						param.put("pubDate", (form.getPubDate() == null || "".equals(form.getPubDate())) ? null : form.getPubDate() + "*");
						param.put("taxonomy", (form.getTaxonomy() == null || "".equals(form.getTaxonomy())) ? null : "\"" + form.getTaxonomy() + "\"");
						param.put("taxonomyEn", (form.getTaxonomyEn() == null || "".equals(form.getTaxonomyEn())) ? null : "\"" + form.getTaxonomyEn() + "\"");
						param.put("local", (form.getLocal() == null || "".equals(form.getLocal())) ? null : form.getLocal());
						param.put("notLanguage", (null == form.getNotLanguage()) || "".equals(form.getNotLanguage()) ? null : form.getNotLanguage());
						// 在solr中查询 [搜索类型=====0-全文;1-标题;2-作者]
						// String keyword = form.getSearchValue();

						if (form.getIsAccurate() != null && form.getIsAccurate() == 2) {// 要查询的内容
																						// 是否精确查找
																						// 1、否
																						// ；2、是
							keyword = "\"" + keyword + "\"";
						}
						Map<String, Object> resultMap = new HashMap<String, Object>();
						form.setSearchsType(form.getSearchsType() == null || "".equals(form.getSearchsType()) ? 0 : form.getSearchsType());
						switch (form.getSearchsType()) {
						case 0:
							resultMap = this.licenseIndexService.searchByAllFullText(keyword, userId, form.getCurpage(), c, param, form.getSearchOrder());
							break;
						case 1:
							resultMap = this.licenseIndexService.searchByTitle(keyword, userId, form.getCurpage(), c, param, form.getSearchOrder());
							break;
						case 2:
							resultMap = this.licenseIndexService.searchByAuthor(keyword, userId, form.getCurpage(), c, param, form.getSearchOrder());
							break;
						case 3:
							resultMap = this.licenseIndexService.searchByISBN(keyword, userId, form.getCurpage(), c, param, form.getSearchOrder());
							break;
						case 4:
							resultMap = this.licenseIndexService.searchByPublisher(keyword, userId, form.getCurpage(), c, param, form.getSearchOrder());
							break;
						default:
							resultMap = this.licenseIndexService.searchByAllFullText(keyword, userId, form.getCurpage(), c, param, form.getSearchOrder());
							break;
						}

						// ***************************生成Excel******************************//
						// 输出的excel文件工作表名
						String worksheet = "Product_List";
						// excel工作表的标题
						StringBuffer sb = new StringBuffer();

						sb.append("ISBN/ISSN;");
						sb.append("标题;");
						sb.append("作者;");
						sb.append("出版社;");
						sb.append("出版年;");
						sb.append("币制价格;");
						sb.append("简介;");
						sb.append("卷;");
						sb.append("期;");
						sb.append("语种;");
						sb.append("类型;");
						sb.append("PISBN/EISSN;");

						String title[] = sb.toString().split(";");
						WritableWorkbook workbook;
						OutputStream os = response.getOutputStream();
						response.reset();// 清空输出流
						response.setHeader("Content-disposition", "attachment; filename=" + worksheet + ".xls");// 设定输出文件头
						response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型

						workbook = Workbook.createWorkbook(os);

						WritableSheet sheet = workbook.createSheet(worksheet, 0);

						for (int i = 0; i < title.length; i++) {
							// Label(列号,行号 ,内容 )
							sheet.addCell(new Label(i, 0, title[i]));
						}
						if (resultMap.get("count") != null && Long.valueOf(resultMap.get("count").toString()) > 0) {
							List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.get("result");
							int row = 1;
							// 循环list 查询单个产品下边的价格列表
							// 1-电子书 2-期刊 3-章节 4-文章 5-数据库 6-期刊卷 7 期刊期
							// 99产品包(用来在订单明细中区分)
							// 这里根据LicenseId查询产品
							for (Map<String, Object> idInfo : list) {
								// License 2013-8-7 李方华 由于加入了OA和免费，它的id规则
								// UID_PID
								String lid = idInfo.get("id").toString();
								String pid = "";
								if (lid.contains("_")) {
									pid = lid.substring(lid.lastIndexOf("_") + 1, lid.length());
								} else {
									LLicense lli = this.pPublicationsService.getLicense(lid);
									if (lli != null) {
										pid = lli.getPublications().getId();
									}
								}

								// 根据ID查询产品信息
								// 由于加入了标签，这里不能用get查询
								if (pid != null && !"".equals(pid)) {
									Map<String, Object> condition = new HashMap<String, Object>();
									condition.put("id", pid);
									condition.put("check", "false");
									List<PPublications> ppList = this.pPublicationsService.getPubList2(condition, " order by a.createOn ", (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
									if (ppList != null && ppList.size() > 0) {
										PPublications pub = ppList.get(0);

										sheet.addCell(new Label(0, row, pub.getCode() != null ? pub.getCode() : ""));
										sheet.addCell(new Label(1, row, pub.getTitle() != null ? pub.getTitle() : ""));
										sheet.addCell(new Label(2, row, pub.getAuthor() != null ? pub.getAuthor() : ""));
										sheet.addCell(new Label(3, row, pub.getPublisher() == null ? "" : pub.getPublisher().getName()));
										sheet.addCell(new Label(4, row, pub.getPubDate() != null ? pub.getPubDate() : ""));
										String str1 = pub.getLcurr() != null ? pub.getLcurr() : "";
										str1 += pub.getListPrice() != null ? pub.getListPrice().toString() : "";
										sheet.addCell(new Label(5, row, str1));
										String pubRemark = "";
										if (pub.getRemark() != null) {
											pubRemark = pub.getRemark();
											pubRemark = pubRemark.replaceAll("<([^>]*)>", "").replaceAll("[\\t\\n\\r]", "").replace(" ", "");
										}
										sheet.addCell(new Label(6, row, pubRemark));
										sheet.addCell(new Label(7, row, pub.getVolumeCode() != null ? pub.getVolumeCode() : ""));
										sheet.addCell(new Label(8, row, pub.getIssueCode() != null ? pub.getIssueCode() : ""));
										sheet.addCell(new Label(9, row, pub.getLang() != null ? pub.getLang() : "eng"));
										sheet.addCell(new Label(10, row, pub.getType() != null ? pub.getType().toString() : ""));
										if (pub.getType() == 1) {// 图书
											sheet.addCell(new Label(11, row, pub.getSisbn() != null ? pub.getSisbn() : ""));
										} else if (pub.getType() == 2 || pub.getType() == 4 || pub.getType() == 6 || pub.getType() == 7) {// 期刊=2
																																			// 卷=6
																																			// 期=7
																																			// 文章=4
											sheet.addCell(new Label(11, row, pub.getPissn() != null ? pub.getPissn() : ""));
										}

										row++;
									}
								}
							}
						}
						workbook.write();
						workbook.close();
						os.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("prompt", Lang.getLanguage("Controller.Index.search.prompt.error", request.getSession().getAttribute("lang").toString()));// 搜索错误提示
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "frame/result";
			model.put("form", form);
			return new ModelAndView(forwardString, model);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/index/searchOAFreeDownList")
	public ModelAndView searchOAFreeDownList(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "index/search";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			request.getSession().setAttribute("selectType", 2);// selectType
			// 用来保存全局的变量，看是全部还是在已订阅中查询
			// 2-免费Oa 1-已订阅
			String keyword = form.getSearchValue();
			keyword = CharUtil.toSimple(keyword);
			CUser user1 = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			if (user1 != null && (user1.getLevel() == 2 || user1.getLevel() == 5)) {
				Integer c = 20;
				if (user1.getLevel() == 2)
					c = 50;
				if (keyword != null && !"".equals(keyword)) {
					StringBuffer userIds = new StringBuffer();
					// 访问IP
					long ip = IpUtil.getLongIp(IpUtil.getIp(request));
					// 查询机构信息
					Map<String, Object> mapip = new HashMap<String, Object>();
					mapip.put("ip", ip);
					List<BIpRange> lip = this.configureService.getIpRangeList(mapip, "");
					/*
					 * if(lip!=null&&lip.size()>0){ //根据机构ID,查询用户 for (BIpRange
					 * bIpRange : lip) { Map<String,Object> uc = new
					 * HashMap<String,Object>();
					 * uc.put("institutionId",bIpRange.getInstitution().getId()
					 * ); uc.put("level", 2); List<CUser> lu =
					 * this.cUserService.getUserList(uc, ""); for (CUser cUser :
					 * lu) { userIds.append(cUser.getId()).append(","); } } }
					 */
					// 查询用户ID 免费的不加 用户id 只加 userIds= oafree
					/*
					 * if(request.getSession().getAttribute("mainUser")!=null){
					 * CUser user =
					 * (CUser)request.getSession().getAttribute("mainUser");
					 * userIds.append(user.getId()).append(","); }
					 */
					userIds.append(Param.getParam("OAFree.uid.config").get("uid")).append(",");
					if ("".equals(userIds.toString())) {
						throw new CcsException("Controller.Index.searchLicense.noLogin");// 未登录用户，无法按照“已订阅”查询
					} else {
						String userId = userIds.substring(0, userIds.toString().lastIndexOf(","));
						form.setUrl(request.getRequestURL().toString());
						Map<String, String> param = new HashMap<String, String>();
						/** 特殊机构处理 -- START */
						String specialInstitutionFlag = request.getSession().getAttribute("specialInstitutionFlag") != null ? (String) request.getSession().getAttribute("specialInstitutionFlag") : null;
						if (null != specialInstitutionFlag && specialInstitutionFlag.length() > 0) {
							form = this.specialInstitution_handle(form, specialInstitutionFlag);
						}
						/** 特殊机构处理 -- END */
						param.put("language", (form.getLanguage() == null || "".equals(form.getLanguage())) ? null : "\"" + form.getLanguage() + "\"");
						param.put("publisher", (form.getPublisher() == null || "".equals(form.getPublisher())) ? null : "\"" + form.getPublisher() + "\"");
						param.put("type", (form.getPubType() == null || "".equals(form.getPubType())) ? null : form.getPubType());
						param.put("year", (form.getPubDate() == null || "".equals(form.getPubDate())) ? null : form.getPubDate() + "*");
						param.put("taxonomy", (form.getTaxonomy() == null || "".equals(form.getTaxonomy())) ? null : "\"" + form.getTaxonomy() + "\"");
						param.put("taxonomyEn", (form.getTaxonomyEn() == null || "".equals(form.getTaxonomyEn())) ? null : "\"" + form.getTaxonomyEn() + "\"");
						param.put("local", (form.getLocal() == null || "".equals(form.getLocal())) ? null : form.getLocal());
						param.put("notLanguage", (null == form.getNotLanguage()) || "".equals(form.getNotLanguage()) ? null : form.getNotLanguage());
						// 在solr中查询 [搜索类型=====0-全文;1-标题;2-作者]
						// String keyword = form.getSearchValue();

						if (form.getIsAccurate() != null && form.getIsAccurate() == 2) {// 要查询的内容
							// 是否精确查找
							// 1、否
							// ；2、是
							keyword = "\"" + keyword + "\"";
						}
						Map<String, Object> resultMap = new HashMap<String, Object>();
						form.setSearchsType(form.getSearchsType() == null || "".equals(form.getSearchsType()) ? 0 : form.getSearchsType());
						switch (form.getSearchsType()) {
						case 0:
							resultMap = this.licenseIndexService.searchByAllFullText(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
							break;
						case 1:
							resultMap = this.licenseIndexService.searchByTitle(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
							break;
						case 2:
							resultMap = this.licenseIndexService.searchByAuthor(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
							break;
						case 3:
							resultMap = this.licenseIndexService.searchByISBN(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
							break;
						case 4:
							resultMap = this.licenseIndexService.searchByPublisher(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
							break;
						default:
							resultMap = this.licenseIndexService.searchByAllFullText(keyword, userId, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
							break;
						}
						// ***************************生成Excel******************************//
						// 输出的excel文件工作表名
						String worksheet = "Product_List";
						// excel工作表的标题
						StringBuffer sb = new StringBuffer();

						sb.append("ISBN/ISSN;");
						sb.append("标题;");
						sb.append("作者;");
						sb.append("出版社;");
						sb.append("出版年;");
						sb.append("币制价格;");
						sb.append("简介;");
						sb.append("卷;");
						sb.append("期;");
						sb.append("语种;");
						sb.append("类型;");
						sb.append("PISBN/EISSN;");

						String title[] = sb.toString().split(";");
						WritableWorkbook workbook;
						OutputStream os = response.getOutputStream();
						response.reset();// 清空输出流
						response.setHeader("Content-disposition", "attachment; filename=" + worksheet + ".xls");// 设定输出文件头
						response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型

						workbook = Workbook.createWorkbook(os);

						WritableSheet sheet = workbook.createSheet(worksheet, 0);

						for (int i = 0; i < title.length; i++) {
							// Label(列号,行号 ,内容 )
							sheet.addCell(new Label(i, 0, title[i]));
						}
						if (resultMap.get("count") != null && Long.valueOf(resultMap.get("count").toString()) > 0) {
							List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.get("result");
							int row = 1;
							// 循环list 查询单个产品下边的价格列表
							// 1-电子书 2-期刊 3-章节 4-文章 5-数据库 6-期刊卷 7 期刊期
							// 99产品包(用来在订单明细中区分)
							// 这里根据LicenseId查询产品
							for (Map<String, Object> idInfo : list) {
								// License 2013-8-7 李方华 由于加入了OA和免费，它的id规则
								// UID_PID
								String lid = idInfo.get("id").toString();
								String pid = "";
								if (lid.contains("_")) {
									pid = lid.substring(lid.lastIndexOf("_") + 1, lid.length());
								} else {
									LLicense lli = this.pPublicationsService.getLicense(lid);
									if (lli != null) {
										pid = lli.getPublications().getId();
									}
								}

								// 根据ID查询产品信息
								// 由于加入了标签，这里不能用get查询
								if (pid != null && !"".equals(pid)) {
									Map<String, Object> condition = new HashMap<String, Object>();
									condition.put("id", pid);
									condition.put("check", "false");
									List<PPublications> ppList = this.pPublicationsService.getPubList2(condition, " order by a.createOn ", (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
									if (ppList != null && ppList.size() > 0) {

										PPublications pub = ppList.get(0);
										if (pub != null && pub.getRemark() != null && "[无简介]".equals(pub.getRemark())) {
											pub.setRemark("");
										}
										sheet.addCell(new Label(0, row, pub.getCode() != null ? pub.getCode() : ""));
										sheet.addCell(new Label(1, row, pub.getTitle() != null ? pub.getTitle() : ""));
										sheet.addCell(new Label(2, row, pub.getAuthor() != null ? pub.getAuthor() : ""));
										sheet.addCell(new Label(3, row, pub.getPublisher() == null ? "" : pub.getPublisher().getName()));
										sheet.addCell(new Label(4, row, pub.getPubDate() != null ? pub.getPubDate() : ""));
										String str1 = pub.getLcurr() != null ? pub.getLcurr() : "";
										str1 += pub.getListPrice() != null ? pub.getListPrice().toString() : "";
										sheet.addCell(new Label(5, row, str1));
										String pubRemark = "";
										if (pub.getRemark() != null) {
											pubRemark = pub.getRemark();
											pubRemark = pubRemark.replaceAll("<([^>]*)>", "").replaceAll("[\\t\\n\\r]", "").replace(" ", "");
										}
										sheet.addCell(new Label(6, row, pubRemark));
										sheet.addCell(new Label(7, row, pub.getVolumeCode() != null ? pub.getVolumeCode() : ""));
										sheet.addCell(new Label(8, row, pub.getIssueCode() != null ? pub.getIssueCode() : ""));
										sheet.addCell(new Label(9, row, pub.getLang() != null ? pub.getLang() : "eng"));
										sheet.addCell(new Label(10, row, pub.getType() != null ? pub.getType().toString() : ""));
										if (pub.getType() == 1) {// 图书
											sheet.addCell(new Label(11, row, pub.getSisbn() != null ? pub.getSisbn() : ""));
										} else if (pub.getType() == 2 || pub.getType() == 4 || pub.getType() == 6 || pub.getType() == 7) {// 期刊=2
																																			// 卷=6
																																			// 期=7
																																			// 文章=4
											sheet.addCell(new Label(11, row, pub.getPissn() != null ? pub.getPissn() : ""));
										}

										row++;
									}
								}
							}
						}
						workbook.write();
						workbook.close();
						os.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("prompt", Lang.getLanguage("Controller.Index.search.prompt.error", request.getSession().getAttribute("lang").toString()));// 搜索错误提示
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "frame/result";
			model.put("form", form);
			return new ModelAndView(forwardString, model);
		}
		return null;
	}

	/**
	 * 高级搜索List下载
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index/advancedSearchDownList")
	public ModelAndView advancedSearchDownList(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "index/search_advanced";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String isCn = form.getIsCn();
			CUser user1 = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			form.setUrl(request.getRequestURL().toString());
			List<PPublications> resultList = new ArrayList<PPublications>();
			/*********** 查询条件区*开始 ***************/
			Map<String, String> param = new HashMap<String, String>();
			/** 特殊机构处理 -- START */
			String specialInstitutionFlag = request.getSession().getAttribute("specialInstitutionFlag") != null ? (String) request.getSession().getAttribute("specialInstitutionFlag") : null;
			if (null != specialInstitutionFlag && specialInstitutionFlag.length() > 0) {
				form = this.specialInstitution_handle(form, specialInstitutionFlag);
			}
			/** 特殊机构处理 -- END */
			param.put("language", (form.getLanguage() == null || "".equals(form.getLanguage())) ? null : "\"" + form.getLanguage() + "\"");
			param.put("searchValue", (form.getSearchValue() == null || "".equals(form.getSearchValue())) ? null : form.getSearchValue());
			param.put("keywordCondition", (form.getKeywordCondition() == null || "".equals(form.getKeywordCondition())) ? null : form.getKeywordCondition().toString());
			param.put("notKeywords", (form.getNotKeywords() == null || "".equals(form.getNotKeywords())) ? null : form.getNotKeywords());
			param.put("title", (form.getTitle() == null || "".equals(form.getTitle())) ? null : form.getTitle());
			param.put("author", (form.getAuthor() == null || "".equals(form.getAuthor())) ? null : form.getAuthor());
			param.put("code", (form.getCode() == null || "".equals(form.getCode())) ? null : form.getCode());
			param.put("taxonomy", (form.getTaxonomy() == null || "".equals(form.getTaxonomy())) ? null : "\"" + form.getTaxonomy() + "\"");
			param.put("taxonomyEn", (form.getTaxonomyEn() == null || "".equals(form.getTaxonomyEn())) ? null : "\"" + form.getTaxonomyEn() + "\"");
			param.put("pubType", (form.getPubType() == null || "".equals(form.getPubType())) ? null : form.getPubType());
			param.put("pubDateStart", (form.getPubDateStart() == null || "".equals(form.getPubDateStart())) ? null : form.getPubDateStart());
			param.put("pubDateEnd", (form.getPubDateEnd() == null || "".equals(form.getPubDateEnd())) ? null : form.getPubDateEnd());
			param.put("publisher", (form.getPublisher() == null || "".equals(form.getPublisher())) ? null : "\"" + form.getPublisher() + "\"");
			param.put("local", (form.getLocal() == null || "".equals(form.getLocal())) ? null : form.getLocal());
			param.put("notLanguage", (null == form.getNotLanguage()) || "".equals(form.getNotLanguage()) ? null : form.getNotLanguage());
			param.put("isCn", (null == isCn || "".equals(isCn) ? null : isCn));
			// param.put("type",
			// (form.getPubType()==null||"".equals(form.getPubType()))?null:form.getPubType());
			param.put("year", (form.getPubDate() == null || "".equals(form.getPubDate())) ? null : form.getPubDate() + "*");
			/*********** 查询条件区*结束 ***************/
			/*** 中文 ***/
			if (param.containsKey("taxonomy") && param.get("taxonomy") != null) {
				String[] taxArr = param.get("taxonomy").replace("\"", "").split(",");
				model.put("taxArr", taxArr);
			}
			/*** 英文 ***/
			if (param.containsKey("taxonomyEn") && param.get("taxonomyEn") != null) {
				String[] taxArrEn = param.get("taxonomyEn").replace("\"", "").split(",");
				model.put("taxArrEn", taxArrEn);
			}

			Map<String, Object> resultMap = new HashMap<String, Object>();
			String userId = "";
			if (form.getLcense() == null || "".equals(form.getLcense())) {
				String oafree = "";
				resultMap = this.publicationsIndexService.advancedSearch(form.getCurpage(), 50, param, form.getSearchOrder(), isCn);
				if (resultMap.get("count") != null && Long.valueOf(resultMap.get("count").toString()) > 0) {
					List<Map<String, String>> list = (List<Map<String, String>>) resultMap.get("result");
					for (Map<String, String> idInfo : list) {
						// 根据ID查询产品信息
						// 由于加入了标签，这里不能用get查询
						Map<String, Object> condition = new HashMap<String, Object>();
						condition.put("id", idInfo.get("id"));
						condition.put("check", "false");
						List<PPublications> ppList = this.pPublicationsService.getPubList2(condition, " order by a.createOn ", (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
						if (ppList != null && ppList.size() > 0) {
							PPublications pub = ppList.get(0);
							if (pub != null && pub.getRemark() != null && "[无简介]".equals(pub.getRemark())) {
								pub.setRemark("");
							}
							resultList.add(pub);
						}
					}
				}
			} else if (Integer.parseInt(form.getLcense()) == 1) {
				CUser user = (CUser) request.getSession().getAttribute("mainUser");
				request.getSession().setAttribute("selectType", 1);// selectType
																	// 用来保存全局的变量，看是全部还是在已订阅中查询
																	// 2-全部
																	// 1-已订阅
				StringBuffer userIds = new StringBuffer();
				// 访问IP
				long ip = IpUtil.getLongIp(IpUtil.getIp(request));
				// 查询机构信息
				Map<String, Object> mapip = new HashMap<String, Object>();
				mapip.put("ip", ip);
				List<BIpRange> lip = this.configureService.getIpRangeList(mapip, "");
				if (lip != null && lip.size() > 0) {
					// 根据机构ID,查询用户
					for (BIpRange bIpRange : lip) {
						Map<String, Object> uc = new HashMap<String, Object>();
						// uc.put("institutionId",bIpRange.getInstitution().getId()
						// );
						if (user != null && user.getLevel() == 2) {
							uc.put("institutionId", user.getInstitution().getId());
						} else {
							uc.put("institutionId", bIpRange.getInstitution().getId());
						}
						uc.put("insStatus", 1);// 1-机构未被禁用状态
						uc.put("level", 2);
						List<CUser> lu = this.cUserService.getUserList(uc, "");
						for (CUser cUser : lu) {
							userIds.append(cUser.getId()).append(",");
						}
					}
				}
				// 查询用户ID
				if (request.getSession().getAttribute("mainUser") != null) {
					user = (CUser) request.getSession().getAttribute("mainUser");
					userIds.append(user.getId()).append(",");
				}
				userIds.append(Param.getParam("OAFree.uid.config").get("uid")).append(",");
				if ("".equals(userIds.toString())) {
					throw new CcsException("Controller.Index.searchLicense.noLogin");// 未登录用户，无法按照“已订阅”查询
				} else {
					userId = userIds.substring(0, userIds.toString().lastIndexOf(","));
					// 在solr中查询 [搜索类型=====0-全文;1-标题;2-作者]
					Integer coverType = 1;
					resultMap = this.licenseIndexService.advancedSearch(coverType, userId, form.getCurpage(), 50, param, form.getSearchOrder());
					if (resultMap.get("count") != null && Long.valueOf(resultMap.get("count").toString()) > 0) {
						List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.get("result");
						// 这里根据LicenseId查询产品
						for (Map<String, Object> idInfo : list) {
							// License 2013-8-7 李方华 由于加入了OA和免费，它的id规则 UID_PID
							String lid = idInfo.get("id").toString();
							String pid = "";
							if (lid.contains("_")) {
								pid = lid.substring(lid.lastIndexOf("_") + 1, lid.length());
							} else {
								LLicense lli = this.pPublicationsService.getLicense(lid);
								if (lli != null) {
									pid = lli.getPublications().getId();
								}
							}
							if (pid != null && !"".equals(pid)) {
								// 根据ID查询产品信息
								// 由于加入了标签，这里不能用get查询
								Map<String, Object> condition = new HashMap<String, Object>();
								condition.put("id", pid);
								condition.put("status", null);
								condition.put("available", 3);
								List<PPublications> ppList = this.pPublicationsService.getPubList2(condition, " order by a.createOn ", (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
								if (ppList != null && ppList.size() > 0) {
									PPublications pub = ppList.get(0);
									if (pub != null && pub.getRemark() != null && "[无简介]".equals(pub.getRemark())) {
										pub.setRemark("");
									}
									resultList.add(pub);
								}
							}
						}
					}

				}
			} else if (Integer.parseInt(form.getLcense()) == 2) {// 开源、免费查询
				request.getSession().setAttribute("selectType", "2");// selectType
				// 用来保存全局的变量，看是全部还是在已订阅中查询
				// 2-全部
				// 1-已订阅、
				String oafree = "";
				Integer coverType = 2;// 区分 免费开源和已订阅 查询
				Map<String, String> oafreeMap = new HashMap<String, String>();
				oafreeMap = Param.getParam("OAFree.uid.config");
				oafree = oafreeMap.get("uid");
				resultMap = this.licenseIndexService.advancedSearch(coverType, oafree, form.getCurpage(), 50, param, form.getSearchOrder());
				// resultMap =
				// this.publicationsIndexService.advancedSearch(oafree,form.getCurpage(),
				// form.getPageCount(),param,form.getSearchOrder());

				if (resultMap.get("count") != null && Long.valueOf(resultMap.get("count").toString()) > 0) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.get("result");
					for (Map<String, Object> idInfo : list) {
						// 根据ID查询产品信息
						// 由于加入了标签，这里不能用get查询
						Map<String, Object> condition = new HashMap<String, Object>();
						String oafreeid = "";
						if (idInfo.get("id").toString().startsWith("oafree_")) {
							oafreeid = idInfo.get("id").toString().replaceAll("oafree_", "");
						}
						condition.put("id", oafreeid);
						// condition.put("check","false");
						condition.put("status", null);
						condition.put("available", 3);
						// condition.put("oafree",2);
						List<PPublications> ppList = this.pPublicationsService.getPubList3(condition, " order by a.createOn ", (CUser) request.getSession().getAttribute("mainUser"), IpUtil.getIp(request));
						if (ppList != null && ppList.size() > 0) {
							PPublications pub = ppList.get(0);
							if (pub != null && pub.getRemark() != null && "[无简介]".equals(pub.getRemark())) {
								pub.setRemark("");
							}
							if (idInfo.containsKey("title") && idInfo.get("title") != null && !"".equals("title"))
								pub.setTitle(idInfo.get("title").toString());
							if (idInfo.containsKey("author") && idInfo.get("author") != null && !"".equals("author"))
								pub.setAuthor(idInfo.get("author").toString());
							if (idInfo.containsKey("isbn") && idInfo.get("isbn") != null && !"".equals("isbn"))
								pub.setCode(idInfo.get("isbn").toString());
							if (idInfo.containsKey("copyPublisher") && idInfo.get("copyPublisher") != null && !"".equals("copyPublisher"))
								pub.getPublisher().setName(idInfo.get("copyPublisher").toString());
							if (idInfo.containsKey("remark") && idInfo.get("remark") != null && !"".equals("remark"))
								pub.setRemark(idInfo.get("remark").toString());

							if (idInfo.containsKey("score") && idInfo.get("score") != null && !"".equals("score"))
								pub.setActivity(idInfo.get("score").toString());

							if (user1 != null) {
								Map<String, Object> con = new HashMap<String, Object>();
								con.put("publicationsid", idInfo.get("id"));
								con.put("status", 2);
								con.put("userTypeId", user1 == null ? "1" : user1.getUserType() == null ? "1" : user1.getUserType().getId());
								// con.put("userTypeId",
								// user1.getUserType().getId()==null?"":user1.getUserType().getId());
								List<PPrice> price = this.pPublicationsService.getPriceList(con);
								int isFreeUser = request.getSession().getAttribute("isFreeUser") == null ? 0 : (Integer) request.getSession().getAttribute("isFreeUser");
								if (isFreeUser != 1) {
									for (int j = 0; j < price.size(); j++) {
										PPrice pr = price.get(j);
										double endPrice = MathHelper.round(MathHelper.mul(pr.getPrice(), 1.13d));
										price.get(j).setPrice(endPrice);
									}
								}
								pub.setPriceList(price);
							}
							resultList.add(pub);
						}
					}
				}
			}
			// ***************************生成Excel******************************//
			// 输出的excel文件工作表名
			String worksheet = "Product_List";
			// excel工作表的标题
			StringBuffer sb = new StringBuffer();
			// sb.append("Title;");
			// sb.append("ISBN;");
			// sb.append("ISSN;");
			// sb.append("code;");
			// sb.append("Type;");
			// sb.append("Author;");
			// sb.append("Publisher;");
			// sb.append("Remark;");

			sb.append("ISBN/ISSN;");
			sb.append("标题;");
			sb.append("作者;");
			sb.append("出版社;");
			sb.append("出版年;");
			sb.append("币制价格;");
			sb.append("简介;");
			sb.append("卷;");
			sb.append("期;");
			sb.append("语种;");
			sb.append("类型;");
			sb.append("PISBN/EISSN;");

			String title[] = sb.toString().split(";");
			WritableWorkbook workbook;
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename=" + worksheet + ".xls");// 设定输出文件头
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型

			workbook = Workbook.createWorkbook(os);

			WritableSheet sheet = workbook.createSheet(worksheet, 0);

			for (int i = 0; i < title.length; i++) {
				// Label(列号,行号 ,内容 )
				sheet.addCell(new Label(i, 0, title[i]));
			}
			if (resultList != null && resultList.size() > 0) {
				int row = 1;
				// 循环list 查询单个产品下边的价格列表
				// 1-电子书 2-期刊 3-章节 4-文章 5-数据库 6-期刊卷 7 期刊期 99产品包(用来在订单明细中区分)
				// 这里根据LicenseId查询产品
				for (PPublications pub : resultList) {

					if (pub != null && pub.getRemark() != null && "[无简介]".equals(pub.getRemark())) {
						pub.setRemark("");
					}
					sheet.addCell(new Label(0, row, pub.getCode() != null ? pub.getCode() : ""));
					sheet.addCell(new Label(1, row, pub.getTitle() != null ? pub.getTitle() : ""));
					sheet.addCell(new Label(2, row, pub.getAuthor() != null ? pub.getAuthor() : ""));
					sheet.addCell(new Label(3, row, pub.getPublisher() == null ? "" : pub.getPublisher().getName()));
					sheet.addCell(new Label(4, row, pub.getPubDate() != null ? pub.getPubDate() : ""));
					String str1 = pub.getLcurr() != null ? pub.getLcurr() : "";
					str1 += pub.getListPrice() != null ? pub.getListPrice().toString() : "";
					sheet.addCell(new Label(5, row, str1));
					String pubRemark = "";
					if (pub.getRemark() != null) {
						pubRemark = pub.getRemark();
						pubRemark = pubRemark.replaceAll("<([^>]*)>", "").replaceAll("[\\t\\n\\r]", "").replace(" ", "");
					}
					sheet.addCell(new Label(6, row, pubRemark));
					sheet.addCell(new Label(7, row, pub.getVolumeCode() != null ? pub.getVolumeCode() : ""));
					sheet.addCell(new Label(8, row, pub.getIssueCode() != null ? pub.getIssueCode() : ""));
					sheet.addCell(new Label(9, row, pub.getLang() != null ? pub.getLang() : "eng"));
					sheet.addCell(new Label(10, row, pub.getType() != null ? pub.getType().toString() : ""));
					if (pub.getType() == 1) {// 图书
						sheet.addCell(new Label(11, row, pub.getSisbn() != null ? pub.getSisbn() : ""));
					} else if (pub.getType() == 2 || pub.getType() == 4 || pub.getType() == 6 || pub.getType() == 7) {// 期刊=2
																														// 卷=6
																														// 期=7
																														// 文章=4
						sheet.addCell(new Label(11, row, pub.getPissn() != null ? pub.getPissn() : ""));
					}

					row++;
					// if (pub != null && pub.getRemark() != null &&
					// "[无简介]".equals(pub.getRemark())) {
					// pub.setRemark("");
					// }
					// sheet.addCell(new Label(0, row, pub.getTitle()));
					// if (pub.getType() == 1 || pub.getType() == 3) {
					// sheet.addCell(new Label(1, row, pub.getCode()));
					// } else if (pub.getType() == 2 || pub.getType() == 4 ||
					// pub.getType() == 6 || pub.getType() == 7) {
					// sheet.addCell(new Label(2, row, pub.getCode()));
					// } else if (pub.getType() == 5) {
					// sheet.addCell(new Label(3, row, pub.getCode()));
					// }
					// if (pub.getType() == 1) {
					// sheet.addCell(new Label(4, row,
					// Lang.getLanguage("Pages.User.Favorites.Table.Label.type1",
					// request.getSession().getAttribute("lang").toString())));
					// } else if (pub.getType() == 2) {
					// sheet.addCell(new Label(4, row,
					// Lang.getLanguage("Pages.User.Favorites.Table.Label.type2",
					// request.getSession().getAttribute("lang").toString())));
					// } else if (pub.getType() == 3) {
					// sheet.addCell(new Label(4, row,
					// Lang.getLanguage("Pages.User.Favorites.Table.Label.type3",
					// request.getSession().getAttribute("lang").toString())));
					// } else if (pub.getType() == 4) {
					// sheet.addCell(new Label(4, row,
					// Lang.getLanguage("Pages.User.Favorites.Table.Label.type4",
					// request.getSession().getAttribute("lang").toString())));
					// } else if (pub.getType() == 5) {
					// sheet.addCell(new Label(4, row,
					// Lang.getLanguage("Pages.User.Favorites.Table.Label.type5",
					// request.getSession().getAttribute("lang").toString())));
					// } else if (pub.getType() == 6) {
					// sheet.addCell(new Label(4, row,
					// Lang.getLanguage("Pages.User.Favorites.Table.Label.type6",
					// request.getSession().getAttribute("lang").toString())));
					// } else if (pub.getType() == 7) {
					// sheet.addCell(new Label(4, row,
					// Lang.getLanguage("Pages.User.Favorites.Table.Label.type7",
					// request.getSession().getAttribute("lang").toString())));
					// }
					// sheet.addCell(new Label(5, row, pub.getAuthor()));
					// sheet.addCell(new Label(6, row,
					// pub.getPublisher().getName()));
					// sheet.addCell(new Label(7, row, pub.getRemark()));
					// row++;
				}
			}
			workbook.write();
			workbook.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("prompt", Lang.getLanguage("Controller.Index.search.prompt.error", request.getSession().getAttribute("lang").toString()));// 搜索错误提示
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "frame/result";
			model.put("form", form);
			return new ModelAndView(forwardString, model);
		}
		return null;
	}

	/**
	 * 首页搜索热词 JQuery异步加载
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pages/index/titleView")
	public ModelAndView titleView(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "index/index/titleView";
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView(forwardString, model);
	}

	/**
	 * 免费资源/试用资源 JQuery 异步加载
	 */
	@RequestMapping("free")
	public ModelAndView free(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "index/index/freePub";
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			form.setUrl(request.getRequestURL().toString());
			String allfree = request.getParameter("allfree");
			String ins_Id = "";
			Integer num = 4;
			CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			List<PPublications> list = null;
			Map<String, Object> condition = new HashMap<String, Object>();

			condition.put("free", 2); // 免费状态 1-不免费 ；2-免费

			condition.put("f", 1);// 海外版资源 0-不是；1-是
			// IP范围外
			if ("true".equals(allfree) && !allfree.equals("null") && allfree != "null") { // condition.put("author",
																							// "null");
				// condition.put("publisher",
				// "null");
				if (request.getParameter("freelang") != null) {
					condition.put("freelang", request.getParameter("freelang").toLowerCase()); // 语种
				}
				condition.put("type", request.getParameter("type")); // 类型
				condition.put("publisher", request.getParameter("publisher")); // 出版社
				condition.put("pDate", request.getParameter("pDate")); // 出版日期
				form.setCount(this.pPublicationsService.getDatabaseCount(condition));
				list = this.pPublicationsService.getPubSimplePageList(condition, " order by a.createOn ", form.getPageCount(), form.getCurpage()); // form.getPageCount()
				List<PPublications> lists = this.pPublicationsService.getPubSimplePageList(condition, " order by a.createOn "); // form.getPageCount()
				Map<String, Object> langMap = new HashMap<String, Object>();
				for (PPublications pPublications : lists) {
					String lang = pPublications.getLang();
					if (lang != null && !"".equals(lang)) {
						condition.put("freelang", lang.toLowerCase());
						if (!langMap.containsKey(lang.toUpperCase())) {
							langMap.put(lang.toUpperCase(), this.pPublicationsService.getPubCount(condition));
						}
					}
				}
				condition.remove("freelang");
				model.put("langMap", langMap);
				if (request.getParameter("freelang") != null) {
					condition.put("freelang", request.getParameter("freelang").toLowerCase()); // 语种
				}
				Map<String, Object> publisherMap = new HashMap<String, Object>();
				for (PPublications pPublications : lists) {
					String ppName = pPublications.getPublisher().getName();
					condition.put("ppName", ppName);
					if (!publisherMap.containsKey(ppName)) {
						publisherMap.put(ppName, this.pPublicationsService.getPubCount(condition));
					}
				}
				condition.remove("ppName");
				model.put("publisherMap", publisherMap);
				condition.put("publisher", request.getParameter("publisher")); // 出版社

				Map<String, Object> pubDateMap = new TreeMap<String, Object>();
				for (PPublications pPublications : lists) {
					String pDate = pPublications.getPubDate();
					pDate = pDate.substring(0, 4);
					condition.put("pDate", pDate + "%");
					if (!pubDateMap.containsKey(pDate)) {// Integer.parseInt(pDate)
						pubDateMap.put(pDate, this.pPublicationsService.getPubCount(condition));
						System.out.println(pDate + ":" + pubDateMap.get(pDate));
					}
				}
				condition.remove("pDate");
				model.put("pubDateMap", pubDateMap);
				condition.remove("freeType");
				model.put("list", list);
				condition.remove("type");

				if (request.getParameter("freelang") != null) {
					condition.put("freelang", request.getParameter("freelang").toLowerCase()); // 语种
				}
				condition.put("publisher", request.getParameter("publisher")); // 出版社
				condition.put("pDate", request.getParameter("pDate")); // 出版日期
				if (request.getParameter("type") != null && !"".equals(request.getParameter("type"))) {
					condition.put("type", request.getParameter("type"));
					if (request.getParameter("type").equals("1")) {
						model.put("bookCount", this.pPublicationsService.getPubCount(condition));
					} else if (request.getParameter("type").equals("2")) {
						model.put("jouranlCount", this.pPublicationsService.getPubCount(condition));
					} else if (request.getParameter("type").equals("4")) {
						model.put("articleCount", this.pPublicationsService.getPubCount(condition));
					}
				} else {
					condition.put("type", 1);
					model.put("bookCount", this.pPublicationsService.getPubCount(condition));
					condition.put("type", 2);
					model.put("jouranlCount", this.pPublicationsService.getPubCount(condition));
					condition.put("type", 3);
					model.put("chapterCount", this.pPublicationsService.getPubCount(condition));
					condition.put("type", 4);
					model.put("articleCount", this.pPublicationsService.getPubCount(condition));
				}

				forwardString = "index/freeList";
			} else {
				// IP 范围内
				BInstitution ins = (BInstitution) request.getSession().getAttribute("institution");

				if (null != ins) {
					if (request.getSession().getAttribute("mainUser") != null) {
						if (2 != user.getLevel()) { // 不是图书馆管理员
							ins_Id = ins.getId();
						}
					} else {
						ins_Id = ins.getId();
					}
				}

				if ("".equals(ins_Id) && user == null) {
					list = this.pPublicationsService.getPubSimplePageList(condition, " order by a.createOn ", 4, 0);
					model.put("list", list);
				} else {
					if ("".equals(ins_Id) && user != null && user.getLevel() != 2) {
						list = this.pPublicationsService.getPubSimplePageList(condition, " order by a.createOn desc", num, 0);
						model.put("list", list);
					} else {
						condition.put("status", 1); // 1 是 License 有效
						if (user != null && user.getLevel() == 2) {
							ins_Id = user.getInstitution().getId();
						}
						condition.put("institutionId", ins_Id);
						// condition.put("isTrail", "1");
						condition.put("myisTrial", 1);
						// condition.put("author", " ");
						// condition.put("publisherName", " ");
						List<LLicense> list2 = this.customService.getLicensePagingListForIndex(condition, " order by a.createdon desc ", num, 0);
						if (list2 != null && list2.size() > 0) {
							model.put("list", list2);
							forwardString = "index/index/licenseList";
						} else {
							condition.remove("status");
							condition.remove("institutionId");
							condition.remove("isTrail");
							condition.put("publisher", "null");
							list = this.pPublicationsService.getPubSimplePageList(condition, " order by a.createOn desc", num, 0);
							model.put("list", list);
						}
					}

				}
				// condition.put("publisher", "null");
				// list=this.pPublicationsService.getPubSimplePageList(condition,
				// " order by a.createOn desc",num,0);
			}
			model.put("form", form);
		} catch (Exception e) {
			e.printStackTrace();
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/pages/index/trialList")
	public ModelAndView trialList(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "index/trialList";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			form.setUrl(request.getRequestURL().toString());
			CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			Map<String, Object> condition = new HashMap<String, Object>();
			String ins_Id = "";
			if (request.getSession().getAttribute("institution") != null) {
				if (request.getSession().getAttribute("mainUser") != null) {
					user = (CUser) request.getSession().getAttribute("mainUser");
					if (user.getLevel() == 2) {// 不是图书馆管理员
						ins_Id = user.getInstitution().getId();
					}
				} else {
					ins_Id = ((BInstitution) request.getSession().getAttribute("institution")).getId();
				}
			} else {
				if (request.getSession().getAttribute("mainUser") != null) {
					user = (CUser) request.getSession().getAttribute("mainUser");
					if (user.getLevel() == 2) {// 不是图书馆管理员
						ins_Id = user.getInstitution().getId();
					}
				}
			}

			condition.put("status", 1);// license有效
			condition.put("institutionId", ins_Id);
			condition.put("myisTrial", 1);
			if (request.getParameter("freelang") != null) {
				condition.put("freelang", request.getParameter("freelang").toLowerCase());// 语种
			}
			condition.put("trialType", request.getParameter("type"));// 类型
			condition.put("trypublisherName", request.getParameter("publisher"));// 出版社
			condition.put("pDate", request.getParameter("pDate"));// 出版日期
			Integer[] tt = { 1, 2, 4 };

			condition.put("typeArr", tt);
			form.setCount(this.pPublicationsService.getTrialCount(condition));
			List<PPublications> list = this.pPublicationsService.getTrialList(condition, " order by d.createOn ", form.getPageCount(), form.getCurpage()); // form.getPageCount()
			List<PPublications> lists = this.pPublicationsService.getTrialList(condition, " order by d.createOn "); // form.getPageCount()

			Map<String, Object> langMap = new HashMap<String, Object>();
			for (PPublications pPublications : lists) {
				String lang = pPublications.getLang();
				if (lang != null && !"".equals(lang)) {
					condition.put("freelang", lang.toLowerCase());
					if (!langMap.containsKey(lang.toUpperCase())) {
						langMap.put(lang.toUpperCase(), this.pPublicationsService.getTrialCount(condition));
					}
				}
			}
			condition.remove("freelang");
			model.put("langMap", langMap);
			if (request.getParameter("freelang") != null) {
				condition.put("freelang", request.getParameter("freelang").toLowerCase());// 语种
			}

			Map<String, Object> publisherMap = new HashMap<String, Object>();
			for (PPublications pPublications : lists) {
				String ppName = pPublications.getPublisher().getName();
				condition.put("trypublisherName", ppName);
				if (!publisherMap.containsKey(ppName)) {
					publisherMap.put(ppName, this.pPublicationsService.getTrialCount(condition));
				}
			}
			condition.remove("trypublisherName");
			model.put("publisherMap", publisherMap);
			condition.put("trypublisherName", request.getParameter("publisher"));// 出版社

			Map<String, Object> pubDateMap = new TreeMap<String, Object>();
			for (PPublications pPublications : lists) {
				String pDate = pPublications.getPubDate();
				if (pDate != null && !"".equals(pDate)) {
					pDate = pDate.substring(0, 4);
					condition.put("pDate", pDate);
					if (!pubDateMap.containsKey(pDate)) {// Integer.parseInt(pDate)
						pubDateMap.put(pDate, this.pPublicationsService.getTrialCount(condition));
					}
				} else {
					System.out.println("=========================NUll=====================================");
					System.out.println("this:" + pDate + "," + pPublications.getId() + "," + pPublications.getCode());
				}

			}
			condition.remove("pDate");
			model.put("pubDateMap", pubDateMap);
			condition.remove("trialType");

			if (request.getParameter("freelang") != null) {
				condition.put("freelang", request.getParameter("freelang").toLowerCase());// 语种
			}
			condition.put("trypublisherName", request.getParameter("publisher"));// 出版社
			condition.put("pDate", request.getParameter("pDate"));// 出版日期
			if (request.getParameter("type") != null && !"".equals(request.getParameter("type"))) {
				condition.put("trialType", request.getParameter("type"));
				if (request.getParameter("type").equals("1")) {
					model.put("bookCount", this.pPublicationsService.getTrialCount(condition));
				} else if (request.getParameter("type").equals("2")) {
					model.put("jouranlCount", this.pPublicationsService.getTrialCount(condition));
				} else if (request.getParameter("type").equals("4")) {
					model.put("articleCount", this.pPublicationsService.getTrialCount(condition));
				}
			} else {
				condition.put("trialType", 1);
				model.put("bookCount", this.pPublicationsService.getTrialCount(condition));
				condition.put("trialType", 2);
				model.put("jouranlCount", this.pPublicationsService.getTrialCount(condition));
				condition.put("trialType", 3);
				model.put("chapterCount", this.pPublicationsService.getTrialCount(condition));
				condition.put("trialType", 4);
				model.put("articleCount", this.pPublicationsService.getTrialCount(condition));
			}
			model.put("langMap", langMap);
			model.put("publisherMap", publisherMap);
			model.put("pubDateMap", pubDateMap);
			model.put("list", list);
			model.put("form", form);
		} catch (Exception e) {
			e.printStackTrace();
			forwardString = "error";
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
		}

		return new ModelAndView(forwardString, model);
	}

	/*
	 * @RequestMapping(value = "/pages/index/trialList") public ModelAndView
	 * trialList(HttpServletRequest request, HttpServletResponse response,
	 * IndexForm form) throws Exception { String forwardString =
	 * "index/trialList"; Map<String, Object> model = new HashMap<String,
	 * Object>(); try { form.setUrl(request.getRequestURL().toString()); CUser
	 * user = request.getSession().getAttribute("mainUser") == null ? null :
	 * (CUser) request.getSession().getAttribute("mainUser"); Map<String,
	 * Object> condition = new HashMap<String, Object>(); String ins_Id = ""; if
	 * (request.getSession().getAttribute("institution") != null) { if
	 * (request.getSession().getAttribute("mainUser") != null) { user = (CUser)
	 * request.getSession().getAttribute("mainUser"); if (user.getLevel() == 2)
	 * {// 不是图书馆管理员 ins_Id = user.getInstitution().getId(); } } else { ins_Id =
	 * ((BInstitution)
	 * request.getSession().getAttribute("institution")).getId(); } } else { if
	 * (request.getSession().getAttribute("mainUser") != null) { user = (CUser)
	 * request.getSession().getAttribute("mainUser"); if (user.getLevel() == 2)
	 * {// 不是图书馆管理员 ins_Id = user.getInstitution().getId(); } } }
	 * 
	 * condition.put("status", 1);// license有效 condition.put("institutionId",
	 * ins_Id); condition.put("myisTrial", 1); if
	 * (request.getParameter("freelang") != null) { condition.put("freelang",
	 * request.getParameter("freelang").toLowerCase());// 语种 }
	 * condition.put("trialType", request.getParameter("type"));// 类型
	 * condition.put("trypublisherName", request.getParameter("publisher"));//
	 * 出版社 condition.put("pDate", request.getParameter("pDate"));// 出版日期
	 * 
	 * form.setCount(this.customService.getTrialListCount(condition));
	 * List<LLicense> list =
	 * this.customService.getLicensePagingListForIndex(condition,
	 * " order by a.createdon desc ", form.getPageCount(), form.getCurpage());
	 * 
	 * condition.clear(); condition.put("status", 1); // license有效
	 * condition.put("institutionId", ins_Id); condition.put("isTrail", "1"); if
	 * (request.getParameter("freelang") != null) { condition.put("freelang",
	 * request.getParameter("freelang").toLowerCase()); // 语种 }
	 * condition.put("trialType", request.getParameter("type")); // 类型
	 * condition.put("trypublisherName", request.getParameter("publisher"));
	 * condition.put("pDate", request.getParameter("pDate"));
	 * 
	 * List<PPublications> ppList = new ArrayList<PPublications>();
	 * List<LLicense> list1 = this.customService.getLicenseList(condition,
	 * " order by a.createdon desc "); for (LLicense lLicense : list1) { //
	 * PPublications p =
	 * this.pPublicationsService.getPublications(lLicense.getPublications().
	 * getId()); PPublications p =
	 * this.pPublicationsService.findById(lLicense.getPublications().getId());
	 * ppList.add(p); } Map<String, Object> langMap = new HashMap<String,
	 * Object>(); Map<String, Object> publisherMap = new HashMap<String,
	 * Object>(); Map<String, Object> pubDateMap = new TreeMap<String,
	 * Object>(); int bookCount = 0; int jouranlCount = 0; int chapterCount = 0;
	 * int articleCount = 0; for (PPublications pPublications : ppList) { if
	 * (null == pPublications) { break; } if (pPublications.getType() == 1) {
	 * bookCount++; } else if (pPublications.getType() == 2 || 6 ==
	 * pPublications.getType() || 7 == pPublications.getType()) {
	 * jouranlCount++; } else if (pPublications.getType() == 3) {
	 * chapterCount++; } else if (pPublications.getType() == 4) {
	 * articleCount++; }
	 * 
	 * // 语种 if (pPublications.getLang() != null &&
	 * !"".equals(pPublications.getLang().toString())) { if (langMap.size() > 0
	 * && langMap.containsKey(pPublications.getLang())) { int count =
	 * Integer.parseInt(langMap.get(pPublications.getLang()).toString());
	 * count++; langMap.put(pPublications.getLang(), count); } else {
	 * langMap.put(pPublications.getLang(), 1); } }
	 * 
	 * // 出版社 if (pPublications.getPublisher().getName() != null &&
	 * !"".equals(pPublications.getPublisher().getName().toString())) { if
	 * (publisherMap.size() > 0 &&
	 * publisherMap.containsKey(pPublications.getPublisher().getName())) { int
	 * count =
	 * Integer.parseInt(publisherMap.get(pPublications.getPublisher().getName())
	 * .toString()); count++;
	 * publisherMap.put(pPublications.getPublisher().getName(), count); } else {
	 * publisherMap.put(pPublications.getPublisher().getName(), 1); } }
	 * 
	 * // 出版时间 if (pPublications.getPubDate() != null &&
	 * !"".equals(pPublications.getPubDate().toString())) { if
	 * (pubDateMap.size() > 0 &&
	 * pubDateMap.containsKey(pPublications.getPubDate().substring(0, 4))) { int
	 * count =
	 * Integer.parseInt(pubDateMap.get(pPublications.getPubDate().substring(0,
	 * 4)).toString()); count++;
	 * pubDateMap.put(pPublications.getPubDate().substring(0, 4), count); } else
	 * { pubDateMap.put(pPublications.getPubDate().substring(0, 4), 1); } }
	 * 
	 * } condition.put("type", 1); model.put("bookCount",
	 * Integer.toString(bookCount)); condition.put("type", 2);
	 * model.put("jouranlCount", Integer.toString(jouranlCount));
	 * condition.put("type", 3); model.put("chapterCount",
	 * Integer.toString(chapterCount)); condition.put("type", 4);
	 * model.put("articleCount", Integer.toString(articleCount));
	 * model.put("langMap", langMap); model.put("publisherMap", publisherMap);
	 * model.put("pubDateMap", pubDateMap); model.put("list", list);
	 * model.put("form", form); } catch (Exception e) { e.printStackTrace();
	 * forwardString = "error"; request.setAttribute("message", (e instanceof
	 * CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(),
	 * request.getSession().getAttribute("lang").toString()) : e.getMessage());
	 * }
	 * 
	 * return new ModelAndView(forwardString, model); }
	 */

	/**
	 * 服务
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/pages/index/service")
	public ModelAndView service(HttpServletRequest request, HttpServletResponse response, BServiceForm form) throws Exception {
		String forwardString = "index/index/service";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			form.setUrl(request.getRequestURL().toString());
			Integer num = 3;
			CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			List<BService> list = null;
			Map<String, Object> condition = new HashMap<String, Object>();
			// 取3个
			list = this.bServiceService.getPubSimplePageList(condition, " ", num, 0);
			model.put("list", list);
			model.put("form", form);
		} catch (Exception e) {
			e.printStackTrace();
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/pages/suggest")
	public void suggest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = "{}";
		try {
			String q = request.getParameter("q");
			if (q != null && !"".equals(q)) {
				q = URLEncoder.encode(q, "utf-8");
				q = q.toLowerCase();
				json = this.publicationsIndexService.suggest(q);
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	/**
	 * 首页热读资源-静态化
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pages/index/hotReading1")
	public void hotReading1(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			form.setUrl(request.getRequestURL().toString());
			Integer num = 4;
			if (request.getParameter("num") != null && !"".equals(request.getParameter("num").toString())) {
				num = Integer.valueOf(request.getParameter("num").toString());
			}
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("type", 2);// 操作类型1-访问摘要 2-访问内容 3-检索
			condition.put("pubStatus", 2);
			String ins_Id = "";
			if (request.getSession().getAttribute("institution") != null) {
				ins_Id = ((BInstitution) request.getSession().getAttribute("institution")).getId();
				condition.put("institutionId", ins_Id);
				condition.put("license", "true");// IP范围内时仅显示license有效的出版物，ip范围外不考虑license是否有效
				map.put("license", true); // 放到 FreeMarker 模板中
			} else {// IP范围外查看全局的
				if (request.getSession().getAttribute("mainUser") != null) {
					CUser user = (CUser) request.getSession().getAttribute("mainUser");
					if (user.getLevel() == 2) {// IP范围外，机构管理员查看当前机构的4条热读资源
						ins_Id = user.getInstitution().getId();
						condition.put("institutionId", ins_Id);
						condition.put("license", "true");
						map.put("license", true); // 放到 FreeMarker 模板中
					} else {
						condition.put("institutionId", ins_Id);
					}
				} else {
					condition.put("institutionId", ins_Id);
					map.put("license", false); // 放到 FreeMarker 模板中
				}
			}
			List<LAccess> list = this.logAOPService.getLogOfHotReading(condition, " group by a.publications.id order by count(*) desc ", num, 0);
			if (list.size() < 4) {
				condition.remove("license");
				condition.remove("institutionId");
				list = this.logAOPService.getLogOfHotReading(condition, " group by a.publications.id order by count(*) desc ", num, 0);
			}
			map.put("pubList", list);
			map.put("form", form);
			map.put("request", request);
			String path = Param.getParam("config.website.path.indexHotReading").get("path").replace("-", ":");
			if (ins_Id == null || ins_Id.equals("")) {
				FileUtil.generateHTML("pages/ftl", "IndexHotReading.ftl", "indexHotReading_all.html", map, request.getSession().getServletContext(), path);
			} else {
				FileUtil.generateHTML("pages/ftl", "IndexHotReading.ftl", "indexHotReading_" + ins_Id + ".html", map, request.getSession().getServletContext(), path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 快速检索结果页面 所有资源总数异步加载值
	 * 
	 * @param request
	 * @param response
	 * @param formsearchOaCount
	 *            (
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index/allByAjax", method = RequestMethod.POST)
	@ResponseBody
	public String searchAllCountForAjax(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		long allCount = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
		BInstitution ins = (BInstitution) request.getSession().getAttribute("institution");

		String searchValue2 = request.getParameter("searchValue2");
		searchValue2 = (null == searchValue2 || "".equals(searchValue2)) ? request.getParameter("searchValue") : searchValue2;

		try {
			request.getSession().setAttribute("selectType", "");// selectType
																// 用来保存全局的变量，看是全部还是在已订阅中查询
																// ""-全部 1-已订阅
			if (null != ins || null != user) {
				if ("".equals(form.getLcense())) {
					request.getSession().setAttribute("selectType", "");
				} else {
					request.getSession().setAttribute("selectType", "1");
				}
			}
			String keyword = URLDecoder.decode((null == form.getSearchValue() || "".equals(form.getSearchValue()) ? searchValue2 : form.getSearchValue()), "UTF-8");
			keyword = CharUtil.toSimple(keyword);
			String lang = (String) request.getSession().getAttribute("lang");
			// if(keyword!=null&&!"".equals(keyword)){
			form.setUrl(request.getRequestURL().toString());
			// form.setTaxonomy((form.getTaxonomy()==null||"".equals(form.getTaxonomy()))?null:new
			// String(form.getTaxonomy().getBytes("ISO-8859-1"),"UTF-8"));
			Map<String, String> param = new HashMap<String, String>();
			/** 特殊机构处理 -- START */
			String specialInstitutionFlag = request.getSession().getAttribute("specialInstitutionFlag") != null ? (String) request.getSession().getAttribute("specialInstitutionFlag") : null;
			if (null != specialInstitutionFlag && specialInstitutionFlag.length() > 0) {
				form = this.specialInstitution_handle(form, specialInstitutionFlag);
			}
			/** 特殊机构处理 -- END */
			param.put("language", (form.getLanguage() == null || "".equals(form.getLanguage())) ? null : "\"" + form.getLanguage() + "\"");
			param.put("publisher", (form.getPublisher() == null || "".equals(form.getPublisher())) ? null : "\"" + form.getPublisher() + "\"");
			// param.put("publisher", (form.getPublisher() == null ||
			// "".equals(form.getPublisher())) ? null : form.getPublisher());
			param.put("type", (form.getPubType() == null || "".equals(form.getPubType())) ? null : form.getPubType());
			param.put("pubDate", (form.getPubDate() == null || "".equals(form.getPubDate())) ? null : form.getPubDate() + "*");

			param.put("taxonomy", (form.getTaxonomy() == null || "".equals(form.getTaxonomy())) ? null : "\"" + URLDecoder.decode(form.getTaxonomy(), "UTF-8") + "\"");

			// param.put("taxonomy", (form.getTaxonomy() == null ||
			// "".equals(form.getTaxonomy())) ? null : "\"" + form.getTaxonomy()
			// + "\"");
			// param.put("taxonomyEn", (form.getTaxonomyEn() == null ||
			// "".equals(form.getTaxonomyEn())) ? null : "\"" +
			// form.getTaxonomyEn() + "\"");
			param.put("nochinese", (form.getNochinese() == null || "".equals(form.getNochinese())) ? null : "\"" + form.getNochinese() + "\"");
			param.put("local", (form.getLocal() == null || "".equals(form.getLocal())) ? null : form.getLocal());
			param.put("pubType", (form.getPubType() == null || "".equals(form.getPubType())) ? null : form.getPubType());
			param.put("notLanguage", (null == form.getNotLanguage()) || "".equals(form.getNotLanguage()) ? null : form.getNotLanguage());

			// 在solr中查询 [搜索类型=====0-全文;1-标题;2-作者]
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// form.setSearchsType(form.getSearchsType() == null ? 0 :
			// form.getSearchsType());
			// String keyword = form.getSearchValue();

			if (form.getIsAccurate() != null && form.getIsAccurate() == 2) {// 要查询的内容
																			// 是否精确查找
																			// 1、否
																			// ；2、是
				keyword = "\"" + keyword + "\"";
			}
			/*** 中文 ***/
			if (param.containsKey("taxonomy") && param.get("taxonomy") != null) {
				String[] taxArr = param.get("taxonomy").replace("\"", "").split(",");
				model.put("taxArr", taxArr);
			}
			/*** 英文 ***/
			if (param.containsKey("taxonomyEn") && param.get("taxonomyEn") != null) {
				String[] taxArrEn = param.get("taxonomyEn").replace("\"", "").split(",");
				model.put("taxArrEn", taxArrEn);
			}

			form.setSearchsType(null == form.getSearchsType() ? Integer.valueOf(null == request.getParameter("searchsType2") || "".equals(request.getParameter("searchsType2")) ? request.getParameter("type") : request.getParameter("searchsType2")) : (null == form.getSearchsType() || "".equals(form.getSearchsType()) ? Integer.valueOf(request.getParameter("type")) : form.getSearchsType()));

			if (keyword != null && !"".equals(keyword)) {
				switch (form.getSearchsType()) {
				case 0:
					resultMap = this.publicationsIndexService.searchByAllFullText(keyword, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
					break;
				case 1:
					resultMap = this.publicationsIndexService.searchByTitle(keyword, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
					break;
				case 2:
					resultMap = this.publicationsIndexService.searchByAuthor(keyword, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
					break;
				case 3:
					resultMap = this.publicationsIndexService.searchByISBN(keyword, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
					break;
				case 4:
					resultMap = this.publicationsIndexService.searchByPublisher(keyword, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
					break;
				default:
					resultMap = this.publicationsIndexService.searchByAllFullText(keyword, form.getCurpage(), form.getPageCount(), param, form.getSearchOrder());
					break;
				}

				Integer maxCount = Integer.parseInt(Param.getParam("search.config").get("maxCount"));
				if (resultMap.get("count") != null) {
					allCount = Long.valueOf(resultMap.get("count").toString());
					// 最多显示1000条
				} else {
					allCount = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(allCount);
	}

	/**
	 * 快速检索结果页面 开源免费资源总数异步加载值
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/index/oaFreeByAjax", method = RequestMethod.POST)
	public String searchOaCount(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		long allCount = 0;
		try {
			String searchValues = request.getParameter("searchValue2");
			String searchValue2 = getValus(searchValues);
			String keyword = (null == form.getSearchValue() || "".equals(form.getSearchValue()) ? searchValue2 : form.getSearchValue());
			keyword = CharUtil.toSimple(keyword);
			CUser user1 = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			if (keyword != null && !"".equals(keyword)) {
				StringBuffer userIds = new StringBuffer();
				// 访问IP
				long ip = IpUtil.getLongIp(IpUtil.getIp(request));
				// 查询机构信息
				Map<String, Object> mapip = new HashMap<String, Object>();
				mapip.put("ip", ip);
				List<BIpRange> lip = this.configureService.getIpRangeList(mapip, "");
				/*
				 * if(lip!=null&&lip.size()>0){ //根据机构ID,查询用户 for (BIpRange
				 * bIpRange : lip) { Map<String,Object> uc = new
				 * HashMap<String,Object>();
				 * uc.put("institutionId",bIpRange.getInstitution().getId() );
				 * uc.put("level", 2); List<CUser> lu =
				 * this.cUserService.getUserList(uc, ""); for (CUser cUser : lu)
				 * { userIds.append(cUser.getId()).append(","); } } }
				 */
				// 查询用户ID 免费的不加 用户id 只加 userIds= oafree
				/*
				 * if(request.getSession().getAttribute("mainUser")!=null){
				 * CUser user =
				 * (CUser)request.getSession().getAttribute("mainUser");
				 * userIds.append(user.getId()).append(","); }
				 */
				userIds.append(Param.getParam("OAFree.uid.config").get("uid")).append(",");
				if ("".equals(userIds.toString())) {
					throw new CcsException("Controller.Index.searchLicense.noLogin");// 未登录用户，无法按照“已订阅”查询
				} else {
					String userId = userIds.substring(0, userIds.toString().lastIndexOf(","));
					form.setUrl(request.getRequestURL().toString());
					Map<String, String> param = new HashMap<String, String>();
					/** 特殊机构处理 -- START */
					String specialInstitutionFlag = request.getSession().getAttribute("specialInstitutionFlag") != null ? (String) request.getSession().getAttribute("specialInstitutionFlag") : null;
					if (null != specialInstitutionFlag && specialInstitutionFlag.length() > 0) {
						form = this.specialInstitution_handle(form, specialInstitutionFlag);
					}
					/** 特殊机构处理 -- END */
					param.put("language", (form.getLanguage() == null || "".equals(form.getLanguage())) ? null : "\"" + form.getLanguage() + "\"");
					param.put("publisher", (form.getPublisher() == null || "".equals(form.getPublisher())) ? null : "\"" + form.getPublisher() + "\"");
					param.put("type", (form.getPubType() == null || "".equals(form.getPubType())) ? null : form.getPubType());
					param.put("year", (form.getPubDate() == null || "".equals(form.getPubDate())) ? null : form.getPubDate() + "*");
					param.put("taxonomy", (form.getTaxonomy() == null || "".equals(form.getTaxonomy())) ? null : "\"" + form.getTaxonomy() + "\"");
					param.put("taxonomyEn", (form.getTaxonomyEn() == null || "".equals(form.getTaxonomyEn())) ? null : "\"" + form.getTaxonomyEn() + "\"");

					param.put("nochinese", (form.getNochinese() == null || "".equals(form.getNochinese())) ? null : "\"" + form.getNochinese() + "\"");
					param.put("local", (form.getLocal() == null || "".equals(form.getLocal())) ? null : form.getLocal());
					param.put("notLanguage", (null == form.getNotLanguage()) || "".equals(form.getNotLanguage()) ? null : form.getNotLanguage());
					// 在solr中查询 [搜索类型=====0-全文;1-标题;2-作者]
					// String keyword = form.getSearchValue();

					if (form.getIsAccurate() != null && form.getIsAccurate() == 2) {// 要查询的内容
																					// 是否精确查找
																					// 1、否
																					// ；2、是
						keyword = "\"" + keyword + "\"";
					}
					Map<String, Object> resultMap = new HashMap<String, Object>();
					form.setSearchsType(form.getSearchsType() == null || "".equals(form.getSearchsType()) ? 0 : form.getSearchsType());
					switch (form.getSearchsType()) {
					case 0:
						resultMap = this.licenseIndexService.searchByAllFullText(keyword, userId, 0, form.getPageCount(), param, form.getSearchOrder());
						break;
					case 1:
						resultMap = this.licenseIndexService.searchByTitle(keyword, userId, 0, form.getPageCount(), param, form.getSearchOrder());
						break;
					case 2:
						resultMap = this.licenseIndexService.searchByAuthor(keyword, userId, 0, form.getPageCount(), param, form.getSearchOrder());
						break;
					case 3:
						resultMap = this.licenseIndexService.searchByISBN(keyword, userId, 0, form.getPageCount(), param, form.getSearchOrder());
						break;
					case 4:
						resultMap = this.licenseIndexService.searchByPublisher(keyword, userId, 0, form.getPageCount(), param, form.getSearchOrder());
						break;
					default:
						resultMap = this.licenseIndexService.searchByAllFullText(keyword, userId, 0, form.getPageCount(), param, form.getSearchOrder());
						break;
					}

					List<FacetField> facetFields = (List<FacetField>) resultMap.get("facet");
					Map<String, Integer> pubDate = new HashMap<String, Integer>();
					for (FacetField fac : facetFields) {

						if (fac.getName().equals("year")) {
							List<Count> counts = fac.getValues();
							if (counts != null && counts.size() > 0) {
								ComparatorSubject comparator = new ComparatorSubject();
								// SequenceUtil.MapDescToKey(counts);
								Collections.sort(counts, comparator);
								model.put("yearList", counts);
							}
						}
						// 限制查询结果总数----------开始-----------

						if (resultMap.get("count") != null) {
							allCount = Long.valueOf(null == resultMap.get("count") ? "0" : resultMap.get("count").toString());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(allCount);
	}

	/**
	 * 快速检索结果页面 已订阅资源总数异步加载值
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/index/lcenseInsByAjax", method = RequestMethod.POST)
	public String searchLicenseCount(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		long allCount = 0;
		try {
			String searchValues = request.getParameter("searchValue2");
			String searchValue2 = getValus(searchValues);
			String keyword = (null == form.getSearchValue() || "".equals(form.getSearchValue()) ? searchValue2 : form.getSearchValue());
			keyword = CharUtil.toSimple(keyword);
			CUser user1 = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			if (keyword != null && !"".equals(keyword)) {
				StringBuffer userIds = new StringBuffer();
				// 访问IP
				long ip = IpUtil.getLongIp(IpUtil.getIp(request));
				// 查询机构信息
				Map<String, Object> mapip = new HashMap<String, Object>();
				mapip.put("ip", ip);
				List<BIpRange> lip = this.configureService.getIpRangeList(mapip, "");
				if (lip != null && lip.size() > 0) {
					// 根据机构ID,查询用户
					for (BIpRange bIpRange : lip) {
						Map<String, Object> uc = new HashMap<String, Object>();
						uc.put("institutionId", bIpRange.getInstitution().getId());
						uc.put("level", 2);
						List<CUser> lu = this.cUserService.getUserList(uc, "");
						for (CUser cUser : lu) {
							userIds.append(cUser.getId()).append(",");
						}
					}
				}
				// 查询用户ID
				if (request.getSession().getAttribute("mainUser") != null) {
					CUser user = (CUser) request.getSession().getAttribute("mainUser");
					userIds.append(user.getId()).append(",");
				}
				// userIds.append(Param.getParam("OAFree.uid.config").get("uid")).append(",");
				if ("".equals(userIds.toString())) {
					// throw new
					// CcsException("Controller.Index.searchLicense.noLogin");//
					// 未登录用户，无法按照“已订阅”查询
				} else {
					String userId = userIds.substring(0, userIds.toString().lastIndexOf(","));
					form.setUrl(request.getRequestURL().toString());
					Map<String, String> param = new HashMap<String, String>();
					/** 特殊机构处理 -- START */
					String specialInstitutionFlag = request.getSession().getAttribute("specialInstitutionFlag") != null ? (String) request.getSession().getAttribute("specialInstitutionFlag") : null;
					if (null != specialInstitutionFlag && specialInstitutionFlag.length() > 0) {
						form = this.specialInstitution_handle(form, specialInstitutionFlag);
					}
					/** 特殊机构处理 -- END */
					param.put("language", (form.getLanguage() == null || "".equals(form.getLanguage())) ? null : "\"" + form.getLanguage() + "\"");
					param.put("publisher", (form.getPublisher() == null || "".equals(form.getPublisher())) ? null : "\"" + form.getPublisher() + "\"");
					param.put("type", (form.getPubType() == null || "".equals(form.getPubType())) ? null : form.getPubType());
					param.put("pubDate", (form.getPubDate() == null || "".equals(form.getPubDate())) ? null : form.getPubDate() + "*");
					param.put("taxonomy", (form.getTaxonomy() == null || "".equals(form.getTaxonomy())) ? null : "\"" + form.getTaxonomy() + "\"");
					param.put("taxonomyEn", (form.getTaxonomyEn() == null || "".equals(form.getTaxonomyEn())) ? null : "\"" + form.getTaxonomyEn() + "\"");
					param.put("nochinese", (form.getNochinese() == null || "".equals(form.getNochinese())) ? null : "\"" + form.getNochinese() + "\"");
					param.put("local", (form.getLocal() == null || "".equals(form.getLocal())) ? null : form.getLocal());
					param.put("notLanguage", (null == form.getNotLanguage()) || "".equals(form.getNotLanguage()) ? null : form.getNotLanguage());
					// 在solr中查询 [搜索类型=====0-全文;1-标题;2-作者]
					// String keyword = form.getSearchValue();

					if (form.getIsAccurate() != null && form.getIsAccurate() == 2) {// 要查询的内容
																					// 是否精确查找
																					// 1、否
																					// ；2、是
						keyword = "\"" + keyword + "\"";
					}
					Map<String, Object> resultMap = new HashMap<String, Object>();
					String searchsType2 = request.getParameter("searchsType2");
					searchsType2 = getValus(searchsType2);
					form.setSearchsType(form.getSearchsType() == null || "".equals(form.getSearchsType()) ? 0 : form.getSearchsType());
					switch (form.getSearchsType()) {
					case 0:
						resultMap = this.licenseIndexService.searchByAllFullText(keyword, userId, 0, form.getPageCount(), param, form.getSearchOrder());
						break;
					case 1:
						resultMap = this.licenseIndexService.searchByTitle(keyword, userId, 0, form.getPageCount(), param, form.getSearchOrder());
						break;
					case 2:
						resultMap = this.licenseIndexService.searchByAuthor(keyword, userId, 0, form.getPageCount(), param, form.getSearchOrder());
						break;
					case 3:
						resultMap = this.licenseIndexService.searchByISBN(keyword, userId, 0, form.getPageCount(), param, form.getSearchOrder());
						break;
					case 4:
						resultMap = this.licenseIndexService.searchByPublisher(keyword, userId, 0, form.getPageCount(), param, form.getSearchOrder());
						break;
					default:
						resultMap = this.licenseIndexService.searchByAllFullText(keyword, userId, 0, form.getPageCount(), param, form.getSearchOrder());
						break;
					}

					if (resultMap.get("count") != null) {
						allCount = Long.valueOf(null == resultMap.get("count") ? "0" : resultMap.get("count").toString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(allCount);
	}

	/**
	 * 高级检索结果页面 全部资源总数异步加载值
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/index/advancedAllByAjax", method = RequestMethod.POST)
	public String advancedSearchAll(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		long allCount = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		String lang = (String) request.getSession().getAttribute("lang");
		try {
			String isCn = form.getIsCn();
			CUser user1 = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			form.setUrl(request.getRequestURL().toString());
			/*********** 查询条件区*开始 ***************/
			Map<String, String> param = new HashMap<String, String>();
			/** 特殊机构处理 -- START */
			String specialInstitutionFlag = request.getSession().getAttribute("specialInstitutionFlag") != null ? (String) request.getSession().getAttribute("specialInstitutionFlag") : null;
			if (null != specialInstitutionFlag && specialInstitutionFlag.length() > 0) {
				form = this.specialInstitution_handle(form, specialInstitutionFlag);
			}
			/** 特殊机构处理 -- END */
			param.put("language", (form.getLanguage() == null || "".equals(form.getLanguage())) ? null : "\"" + form.getLanguage() + "\"");
			param.put("searchValue", (form.getSearchValue() == null || "".equals(form.getSearchValue())) ? null : URLDecoder.decode(form.getSearchValue(), "UTF-8"));
			param.put("keywordCondition", (form.getKeywordCondition() == null || "".equals(form.getKeywordCondition())) ? null : form.getKeywordCondition().toString());
			param.put("notKeywords", (form.getNotKeywords() == null || "".equals(form.getNotKeywords())) ? null : form.getNotKeywords());
			param.put("title", (form.getTitle() == null || "".equals(form.getTitle())) ? null : form.getTitle());
			param.put("author", (form.getAuthor() == null || "".equals(form.getAuthor())) ? null : form.getAuthor());
			param.put("code", (form.getCode() == null || "".equals(form.getCode())) ? null : form.getCode());
			param.put("taxonomy", (form.getTaxonomy() == null || "".equals(form.getTaxonomy())) ? null : "\"" + URLDecoder.decode(form.getTaxonomy(), "UTF-8") + "\"");
			param.put("taxonomy", (form.getTaxonomy() == null || "".equals(form.getTaxonomy())) ? null : "\"" + URLDecoder.decode(form.getTaxonomy(), "UTF-8") + "\"");

			param.put("taxonomyEn", (form.getTaxonomyEn() == null || "".equals(form.getTaxonomyEn())) ? null : "\"" + form.getTaxonomyEn() + "\"");
			param.put("pubType", (form.getPubType() == null || "".equals(form.getPubType())) ? null : form.getPubType());
			param.put("pubDateStart", (form.getPubDateStart() == null || "".equals(form.getPubDateStart())) ? null : form.getPubDateStart());
			param.put("pubDateEnd", (form.getPubDateEnd() == null || "".equals(form.getPubDateEnd())) ? null : form.getPubDateEnd());
			param.put("publisher", (form.getPublisher() == null || "".equals(form.getPublisher())) ? null : form.getPublisher());
			// param.put("publisher", (form.getPublisher() == null ||
			// "".equals(form.getPublisher())) ? null : "\"" +
			// form.getPublisher() + "\"");
			// param.put("type",(form.getPubType()==null||"".equals(form.getPubType()))?null:form.getPubType());
			param.put("pubDate", (form.getPubDate() == null || "".equals(form.getPubDate())) ? null : form.getPubDate() + "*");
			// 首字母
			param.put("prefixWord", (form.getPrefixWord() == null || "".equals(form.getPrefixWord())) ? null : form.getPrefixWord());
			param.put("local", (form.getLocal() == null || "".equals(form.getLocal())) ? null : form.getLocal());
			param.put("notLanguage", (null == form.getNotLanguage()) || "".equals(form.getNotLanguage()) ? null : form.getNotLanguage());
			/*** 中文 ***/
			if (param.containsKey("taxonomy") && param.get("taxonomy") != null) {
				String[] taxArr = param.get("taxonomy").replace("\"", "").split(",");
				model.put("taxArr", taxArr);
			}
			/*** 英文 ***/
			if (param.containsKey("taxonomyEn") && param.get("taxonomyEn") != null) {
				String[] taxArrEn = param.get("taxonomyEn").replace("\"", "").split(",");
				model.put("taxArrEn", taxArrEn);
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String userId = "";
			resultMap = this.publicationsIndexService.advancedSearch(0, form.getPageCount(), param, form.getSearchOrder(), isCn);
			allCount = Long.valueOf(null == resultMap.get("count") ? "0" : resultMap.get("count").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(allCount);
	}

	/**
	 * 高级检索结果页面 开源免费资源总数异步加载值
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/index/advancedOaFreeByAjax", method = RequestMethod.POST)
	public String advancedSearchOAFree(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		long allCount = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		String lang = (String) request.getSession().getAttribute("lang");
		try {
			String isCn = form.getIsCn();
			CUser user1 = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			form.setUrl(request.getRequestURL().toString());
			/*********** 查询条件区*开始 ***************/
			Map<String, String> param = new HashMap<String, String>();
			/** 特殊机构处理 -- START */
			String specialInstitutionFlag = request.getSession().getAttribute("specialInstitutionFlag") != null ? (String) request.getSession().getAttribute("specialInstitutionFlag") : null;
			if (null != specialInstitutionFlag && specialInstitutionFlag.length() > 0) {
				form = this.specialInstitution_handle(form, specialInstitutionFlag);
			}
			/** 特殊机构处理 -- END */
			param.put("language", (form.getLanguage() == null || "".equals(form.getLanguage())) ? null : "\"" + form.getLanguage() + "\"");
			param.put("searchValue", (form.getSearchValue() == null || "".equals(form.getSearchValue())) ? null : URLDecoder.decode(form.getSearchValue(), "UTF-8"));
			param.put("keywordCondition", (form.getKeywordCondition() == null || "".equals(form.getKeywordCondition())) ? null : form.getKeywordCondition().toString());
			param.put("notKeywords", (form.getNotKeywords() == null || "".equals(form.getNotKeywords())) ? null : form.getNotKeywords());
			param.put("title", (form.getTitle() == null || "".equals(form.getTitle())) ? null : form.getTitle());
			param.put("author", (form.getAuthor() == null || "".equals(form.getAuthor())) ? null : form.getAuthor());
			param.put("code", (form.getCode() == null || "".equals(form.getCode())) ? null : form.getCode());
			param.put("taxonomy", (form.getTaxonomy() == null || "".equals(form.getTaxonomy())) ? null : "\"" + URLDecoder.decode(form.getTaxonomy(), "UTF-8") + "\"");

			param.put("taxonomyEn", (form.getTaxonomyEn() == null || "".equals(form.getTaxonomyEn())) ? null : "\"" + form.getTaxonomyEn() + "\"");
			param.put("pubType", (form.getPubType() == null || "".equals(form.getPubType())) ? null : form.getPubType());
			param.put("pubDateStart", (form.getPubDateStart() == null || "".equals(form.getPubDateStart())) ? null : form.getPubDateStart());
			param.put("pubDateEnd", (form.getPubDateEnd() == null || "".equals(form.getPubDateEnd())) ? null : form.getPubDateEnd());
			// param.put("publisher", (form.getPublisher() == null ||
			// "".equals(form.getPublisher())) ? null : form.getPublisher());
			// param.put("publisher", (form.getPublisher() == null ||
			// "".equals(form.getPublisher())) ? null : "\"" +
			// form.getPublisher() + "\"");
			param.put("publisher", (form.getPublisher() == null || "".equals(form.getPublisher())) ? null : form.getPublisher());
			// param.put("type",
			// (form.getPubType()==null||"".equals(form.getPubType()))?null:form.getPubType());
			param.put("pubDate", (form.getPubDate() == null || "".equals(form.getPubDate())) ? null : form.getPubDate() + "*");
			// 首字母
			param.put("prefixWord", (form.getPrefixWord() == null || "".equals(form.getPrefixWord())) ? null : form.getPrefixWord());
			param.put("local", (form.getLocal() == null || "".equals(form.getLocal())) ? null : form.getLocal());
			param.put("notLanguage", (null == form.getNotLanguage()) || "".equals(form.getNotLanguage()) ? null : form.getNotLanguage());
			/*** 中文 ***/
			if (param.containsKey("taxonomy") && param.get("taxonomy") != null) {
				String[] taxArr = param.get("taxonomy").replace("\"", "").split(",");
				model.put("taxArr", taxArr);
			}
			/*** 英文 ***/
			if (param.containsKey("taxonomyEn") && param.get("taxonomyEn") != null) {
				String[] taxArrEn = param.get("taxonomyEn").replace("\"", "").split(",");
				model.put("taxArrEn", taxArrEn);
			}
			// 判断是否为左侧点击的链接<出版商>
			if (null != form.getPublisher() && !"".equals(form.getPublisher()) && form.getPublisher().startsWith("_")) {
				form.setPublisher(form.getPublisher().substring(1));
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String userId = "";
			String oafree = "";
			Integer coverType = 2;// 区分 免费开源和已订阅 查询
			Map<String, String> oafreeMap = new HashMap<String, String>();
			oafreeMap = Param.getParam("OAFree.uid.config");
			oafree = oafreeMap.get("uid");
			resultMap = this.licenseIndexService.advancedSearch(coverType, oafree, 0, form.getPageCount(), param, form.getSearchOrder());
			allCount = Long.valueOf(null == resultMap.get("count") ? "0" : resultMap.get("count").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(allCount);
	}

	/**
	 * 高级检索结果页面 已订阅资源总数异步加载值
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/index/advancedLcenseInsByAjax", method = RequestMethod.POST)
	public String advancedSearchLcense(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		long allCount = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		String lang = (String) request.getSession().getAttribute("lang");
		try {
			String isCn = form.getIsCn();
			CUser user1 = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			form.setUrl(request.getRequestURL().toString());
			/*********** 查询条件区*开始 ***************/
			Map<String, String> param = new HashMap<String, String>();
			/** 特殊机构处理 -- START */
			String specialInstitutionFlag = request.getSession().getAttribute("specialInstitutionFlag") != null ? (String) request.getSession().getAttribute("specialInstitutionFlag") : null;
			if (null != specialInstitutionFlag && specialInstitutionFlag.length() > 0) {
				form = this.specialInstitution_handle(form, specialInstitutionFlag);
			}
			/** 特殊机构处理 -- END */
			param.put("language", (form.getLanguage() == null || "".equals(form.getLanguage())) ? null : "\"" + form.getLanguage() + "\"");
			param.put("searchValue", (form.getSearchValue() == null || "".equals(form.getSearchValue())) ? null : URLDecoder.decode(form.getSearchValue(), "UTF-8"));
			param.put("keywordCondition", (form.getKeywordCondition() == null || "".equals(form.getKeywordCondition())) ? null : form.getKeywordCondition().toString());
			param.put("notKeywords", (form.getNotKeywords() == null || "".equals(form.getNotKeywords())) ? null : form.getNotKeywords());
			param.put("title", (form.getTitle() == null || "".equals(form.getTitle())) ? null : form.getTitle());
			param.put("author", (form.getAuthor() == null || "".equals(form.getAuthor())) ? null : form.getAuthor());
			param.put("code", (form.getCode() == null || "".equals(form.getCode())) ? null : form.getCode());

			param.put("taxonomy", (form.getTaxonomy() == null || "".equals(form.getTaxonomy())) ? null : "\"" + URLDecoder.decode(form.getTaxonomy(), "UTF-8") + "\"");

			param.put("taxonomyEn", (form.getTaxonomyEn() == null || "".equals(form.getTaxonomyEn())) ? null : "\"" + form.getTaxonomyEn() + "\"");
			param.put("pubType", (form.getPubType() == null || "".equals(form.getPubType())) ? null : form.getPubType());
			param.put("pubDateStart", (form.getPubDateStart() == null || "".equals(form.getPubDateStart())) ? null : form.getPubDateStart());
			param.put("pubDateEnd", (form.getPubDateEnd() == null || "".equals(form.getPubDateEnd())) ? null : form.getPubDateEnd());
			// param.put("publisher", (form.getPublisher() == null ||
			// "".equals(form.getPublisher())) ? null : "\"" +
			// form.getPublisher() + "\"");
			param.put("publisher", (form.getPublisher() == null || "".equals(form.getPublisher())) ? null : form.getPublisher());
			// param.put("type", (form.getPubType() == null ||
			// "".equals(form.getPubType())) ? null : form.getPubType());
			param.put("pubDate", (form.getPubDate() == null || "".equals(form.getPubDate())) ? null : form.getPubDate() + "*");
			// 首字母
			param.put("prefixWord", (form.getPrefixWord() == null || "".equals(form.getPrefixWord())) ? null : form.getPrefixWord());
			param.put("local", (form.getLocal() == null || "".equals(form.getLocal())) ? null : form.getLocal());
			param.put("notLanguage", (null == form.getNotLanguage()) || "".equals(form.getNotLanguage()) ? null : form.getNotLanguage());
			// 判断是是否在外文电子书界面
			param.put("isCn", (null == isCn || "".equals(isCn) ? null : isCn));
			/*********** 查询条件区*结束 ***************/

			/*** 中文 ***/
			if (param.containsKey("taxonomy") && param.get("taxonomy") != null) {
				String[] taxArr = param.get("taxonomy").replace("\"", "").split(",");
				model.put("taxArr", taxArr);
			}
			/*** 英文 ***/
			if (param.containsKey("taxonomyEn") && param.get("taxonomyEn") != null) {
				String[] taxArrEn = param.get("taxonomyEn").replace("\"", "").split(",");
				model.put("taxArrEn", taxArrEn);
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String userId = "";
			CUser user = (CUser) request.getSession().getAttribute("mainUser");
			StringBuffer userIds = new StringBuffer();
			// 访问IP
			long ip = IpUtil.getLongIp(IpUtil.getIp(request));
			// 查询机构信息
			Map<String, Object> mapip = new HashMap<String, Object>();
			mapip.put("ip", ip);
			List<BIpRange> lip = this.configureService.getIpRangeList(mapip, "");
			if (lip != null && lip.size() > 0) {
				// 根据机构ID,查询用户
				for (BIpRange bIpRange : lip) {
					Map<String, Object> uc = new HashMap<String, Object>();
					// uc.put("institutionId",bIpRange.getInstitution().getId()
					// );
					if (user != null && user.getLevel() == 2) {
						uc.put("institutionId", user.getInstitution().getId());
					} else {
						uc.put("institutionId", bIpRange.getInstitution().getId());
					}
					uc.put("insStatus", 1);// 1-机构未被禁用状态
					uc.put("level", 2);
					List<CUser> lu = this.cUserService.getUserList(uc, "");
					for (CUser cUser : lu) {
						userIds.append(cUser.getId()).append(",");
					}
				}
			}
			// 查询用户ID
			if (request.getSession().getAttribute("mainUser") != null) {
				user = (CUser) request.getSession().getAttribute("mainUser");
				userIds.append(user.getId()).append(",");
			}
			// userIds.append(Param.getParam("OAFree.uid.config").get("uid")).append(",");
			if ("".equals(userIds.toString())) {
				// throw new
				// CcsException("Controller.Index.searchLicense.noLogin");//未登录用户，无法按照“已订阅”查询
			} else {
				userId = userIds.substring(0, userIds.toString().lastIndexOf(","));
				// 在solr中查询 [搜索类型=====0-全文;1-标题;2-作者]
				Integer coverType = 1;// 区分免费开源和已订阅
				resultMap = this.licenseIndexService.advancedSearch(coverType, userId, 0, form.getPageCount(), param, form.getSearchOrder());
				allCount = Long.valueOf(null == resultMap.get("count") ? "0" : resultMap.get("count").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(allCount);
	}

	// 特殊机构处理
	private IndexForm specialInstitution_handle(IndexForm form, String specialInstitutionFlag) {
		if (null != form) {
			form.setNotLanguage(specialInstitutionFlag);
			form.setPubType("1"); // 图书类别
			form.setLocal("2"); // 查询本地资源
		}
		return form;
	}

	/**
	 * 处理重复的字符串工具
	 * 
	 * @param valus
	 * @return
	 */
	public static String getValus(String valus) {
		String value = "";
		String[] str = valus.split(",");
		Set set = new TreeSet();
		for (int i = 0; i < str.length; i++) {
			set.add(str[i]);
		}
		str = (String[]) set.toArray(new String[0]);
		for (int i = 0; i < str.length; i++) {
			value = str[i];
		}
		return value;
	}

}
