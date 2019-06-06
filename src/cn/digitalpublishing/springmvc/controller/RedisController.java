package cn.digitalpublishing.springmvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.ep.po.ResourcesSum;
import cn.digitalpublishing.redis.po.Book;
import cn.digitalpublishing.springmvc.form.index.IndexForm;

/**
 * All Redis Controller
 */
@Controller
public class RedisController extends BaseController {

	/**
	 * 显示新闻
	 */
	@RequestMapping("news/{id:[\\d]+}")
	public ModelAndView news(@PathVariable(value = "id") int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return new ModelAndView("ShowNews", map);
	}

	/**
	 * 首页服务
	 */
	@RequestMapping("service")
	public ModelAndView service(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String lang = (String) request.getSession().getAttribute("lang");
		List<String> serviceList = null;
		if ("zh_CN".equals(lang)) {
			serviceList = bookDao.getSet("service_zh_CN");
		} else if ("en_US".equals(lang)) {
			serviceList = bookDao.getSet("service_en_US");
		}
		model.put("lang", lang);
		model.put("serviceList", serviceList);
		return new ModelAndView("ftl/Service.ftl", model);
	}

	/**
	 * 显示服务
	 */
	@RequestMapping("service/{id:[\\d]+}")
	public ModelAndView service(@PathVariable(value = "id") int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return new ModelAndView("ShowService", map);

	}

	/**
	 * 显示服务详情页
	 */
	@RequestMapping("service/d{id:[\\d]+}")
	public ModelAndView serviceDetail(@PathVariable(value = "id") int id, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		String lang = (String) request.getSession().getAttribute("lang");
		List<String> serviceList = null;
		if ("zh_CN".equals(lang)) {
			serviceList = bookDao.getSet("service_d_zh_CN");
		} else if ("en_US".equals(lang)) {
			serviceList = bookDao.getSet("service_d_en_US");
		}
		model.put("lang", lang);
		model.put("serviceList", serviceList);
		model.put("id", id);
		return new ModelAndView("ftl/ServiceDetail.ftl", model);
	}

	/**
	 * 显示合作出版社
	 */
	@RequestMapping("press/{id:[\\d]+}")
	public ModelAndView pressById(@PathVariable(value = "id") int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return new ModelAndView("ShowPressById", map);
	}

	/**
	 * 1，显示帮助页面
	 */
	@RequestMapping("help")
	public String help() {
		return "ShowHelp";
	}

	/**
	 * 1，显示帮助内容
	 */
	@RequestMapping("helpDetail")
	public @ResponseBody String help(HttpServletRequest request) {
		return getInfoFromRedis(request, "help");
	}

	/**
	 * 2，显示关于我们页面
	 */
	@RequestMapping("aboutus")
	public String aboutus() {
		return "ShowAboutUs";
	}

	/**
	 * 2，显示关于我们页面内容
	 */
	@RequestMapping("aboutusDetail")
	public @ResponseBody String aboutus(HttpServletRequest request) {
		return getInfoFromRedis(request, "aboutus");
	}

	/**
	 * 3，显示页脚版权信息
	 */
	@RequestMapping("footer")
	public @ResponseBody String footer(HttpServletRequest request) {
		return getInfoFromRedis(request, "footer");
	}

	/**
	 * 4，显示注册协议
	 */
	@RequestMapping("agreement")
	public String agreement() {
		return "ShowAgreement";
	}

	/**
	 * 4，显示页脚版权信息
	 */
	@RequestMapping("agreementDetail")
	public @ResponseBody String agreement(HttpServletRequest request) {
		return getInfoFromRedis(request, "agreement");
	}

	/**
	 * 5，显示页面顶部鼠标移上的分类法
	 */
	@RequestMapping("category")
	public ModelAndView category(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String lang = (String) request.getSession().getAttribute("lang");
		List<String> categoryList = null;
		if ("zh_CN".equals(lang)) {
			categoryList = bookDao.getSet("category_zh_CN");
		} else if ("en_US".equals(lang)) {
			categoryList = bookDao.getSet("category_en_US");
		}
		model.put("lang", lang);
		model.put("categoryList", categoryList);
		return new ModelAndView("ftl/Category.ftl", model);
	}

	/**
	 * 6，显示首页上的学科分类
	 */
	@RequestMapping("subject")
	public ModelAndView subject(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String lang = (String) request.getSession().getAttribute("lang");
		List<String> subjectList = null;
		if ("zh_CN".equals(lang)) {
			subjectList = bookDao.getSet("category_zh_CN");
		} else if ("en_US".equals(lang)) {
			subjectList = bookDao.getSet("category_en_US");
		}
		model.put("lang", lang);
		model.put("subjectList", subjectList);
		return new ModelAndView("ftl/Subject.ftl", model);
	}

	/**
	 * 7，显示二级菜单
	 */
	@RequestMapping("menu")
	public ModelAndView menu(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String lang = (String) request.getSession().getAttribute("lang");
		List<String> menuList = null;
		if ("zh_CN".equals(lang)) {
			menuList = bookDao.getSet("config_menu_zh_CN");
		} else if ("en_US".equals(lang)) {
			menuList = bookDao.getSet("config_menu_en_US");
		}
		model.put("lang", lang);
		model.put("menuList", menuList);
		return new ModelAndView("ftl/Menu.ftl", model);
	}

	/**
	 * 显示合作出版社
	 */
	@RequestMapping("press/{html}")
	public ModelAndView press(@PathVariable(value = "html") String html) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("html", html);
		return new ModelAndView("ShowPress", map);
	}

	/**
	 * 首页中文资源总数
	 */
	@RequestMapping("stat_zh_CN")
	public @ResponseBody String stat_zh_CN() throws Exception {
		String stat_zh_CN = bookDao.getStringByKey("stat_zh_CN");
		if ("".equals(stat_zh_CN) || null == stat_zh_CN) {
			List<ResourcesSum> sum = resourcesSumService.getList(null, "");
			bookDao.setValueByKey("stat_zh_CN", "<div class=\"mb50 oh\"><h1 class=\"indexHtit\"><span class=\"titFb\"><a class=\"ico4\" href=\"javascript:;\">资源总数</a></span></h1><p class=\"mb5\">资源总数 <span>" + sum.get(0).getSumCount() + "</span></p><a href=\"/index/advancedSearchSubmit?pubType=1\"><p class=\"data\"><span class=\"d_name\">图书</span><span class=\"d_number\">" + (sum.get(0).getBookCount() + sum.get(0).getBookCountEn()) + "</span></p></a><a href=\"/index/advancedSearchSubmit?pubType=2\"><p class=\"data\"><span class=\"d_name\">期刊</span><span class=\"d_number\">" + sum.get(0).getJournalsCount() + "</span></p></a><a href=\"/index/advancedSearchSubmit?pubType=4\"><p class=\"data\"><span class=\"d_name\">文章</span><span class=\"d_number\">" + sum.get(0).getArticleCount() + "</span></p></a></div>");
			stat_zh_CN = bookDao.getStringByKey("stat_zh_CN");
		}
		return stat_zh_CN;
	}

	/**
	 * 首页英文资源总数
	 */
	@RequestMapping("stat_en_US")
	public @ResponseBody String stat_en_US() throws Exception {
		String stat_en_US = bookDao.getStringByKey("stat_en_US");
		if ("".equals(stat_en_US) || null == stat_en_US) {
			List<ResourcesSum> sum = resourcesSumService.getList(null, "");
			bookDao.setValueByKey("stat_en_US", "<div class=\"mb50 oh\"><h1 class=\"indexHtit\"><span class=\"titFb\"><a class=\"ico4\" href=\"javascript:;\">Total</a></span></h1><p class=\"mb5\">Total <span>" + sum.get(0).getSumCount() + "</span></p><a href=\"/index/advancedSearchSubmit?pubType=1\"><p class=\"data\"><span class=\"d_name\">Book</span><span class=\"d_number\">" + (sum.get(0).getBookCount() + sum.get(0).getBookCountEn()) + "</span></p></a><a href=\"/index/advancedSearchSubmit?pubType=2\"><p class=\"data\"><span class=\"d_name\">Journal</span><span class=\"d_number\">" + sum.get(0).getJournalsCount() + "</span></p></a><a href=\"/index/advancedSearchSubmit?pubType=4\"><p class=\"data\"><span class=\"d_name\">Article</span><span class=\"d_number\">" + sum.get(0).getArticleCount() + "</span></p></a></div>");
			stat_en_US = bookDao.getStringByKey("stat_en_US");
		}
		return stat_en_US;
	}

	/**
	 * 首页检索热词
	 */
	@RequestMapping("hotWords")
	public @ResponseBody String hotWords(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String hotWords = bookDao.getStringByKey("hotWords");
		if ("".equals(hotWords) || null == hotWords) {
			try {
				form.setUrl(request.getRequestURL().toString());
				// 在日志表中查询出搜索最多的（1、Ip范围内 根据机构来查询；2、Ip范围外 根据全局来查询）
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("type", 3); // 操作类型1-访问摘要 2-访问内容 3-检索
				String ins_Id = "";

				// IP范围内
				if (request.getSession().getAttribute("institution") != null) {
					if (request.getSession().getAttribute("mainUser") != null) {
						CUser user = (CUser) request.getSession().getAttribute("mainUser");
						if (user.getLevel() != 2) {// 不是图书馆管理员
							ins_Id = ((BInstitution) request.getSession().getAttribute("institution")).getId();
						}
					} else {
						ins_Id = ((BInstitution) request.getSession().getAttribute("institution")).getId();
					}
				}

				// IP范围外的全部看到的是全局的
				condition.put("institutionId", ins_Id);
				condition.put("keywordNotNull", 1);
				condition.put("createOn", -30); // 30 天前的搜索数据

				// 取9个
				List<LAccess> list = logAOPService.getLogOfHotWords(condition, " GROUP BY a.activity ORDER BY count(*) DESC ", 9, 0);

				// FreeMarker的实现
				// Map<String, Object> map = new HashMap<String, Object>();
				// map.put("hotList", list);
				// String path =
				// Param.getParam("config.website.path").get("path").replace("-",
				// ":");
				// FileUtil.generateHTML("pages/ftl", "HotWord.ftl",
				// "hotWords.html", map,
				// request.getSession().getServletContext(), path);

				// Redis 实现
				// <a class="span1" href="javascript:;"
				// onclick="searchByCondition('searchValue', '美国科研出版社')"
				// title="美国科研出版社">美国科研出版社</a>
				bookDao.setValueByKey("hotWords", "");
				for (int i = 0; i < list.size(); i++) {
					bookDao.append("hotWords", "<a class=\"span" + (i + 1) + "\" href=\"index/search?type=1&isAccurate=1&searchValue2='" + list.get(i).getActivity() + "'\" title=\"" + list.get(i).getActivity() + "\">" + list.get(i).getActivity() + "</a>");
				}
				bookDao.expire("hotWords", 24 * 60 * 60);
				hotWords = bookDao.getStringByKey("hotWords");
			} catch (Exception e) {
				e.printStackTrace();
				form.setMsg((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			}
		}
		return hotWords;
	}

	/**
	 * 显示首页热读资源
	 */
	@RequestMapping("indexHotReading")
	public ModelAndView indexHotReading(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Book> pubList = new ArrayList<Book>();
		List<String> indexHotReading = new ArrayList<String>();
		Book book = null;

		// 获取 Session 中的用户（CUser）信息和机构（BInstitution）信息
		CUser user = (CUser) request.getSession().getAttribute("mainUser");
		BInstitution institution = (BInstitution) request.getSession().getAttribute("institution");
		institution = null == institution ? null != user ? user.getInstitution() : null : institution;
		if (null != user && null != institution) {
			indexHotReading = bookDao.getSet(institution.getId());
			if (indexHotReading.size() == 0) {
				indexHotReading = bookDao.getSet("indexHotReading");
			}
		} else {
			indexHotReading = bookDao.getSet("indexHotReading");
		}
		for (String s : indexHotReading) {
			book = new Book(s.substring(0, 32), s.substring(32, s.lastIndexOf("@@@@@author@@@@@")), s.substring(s.lastIndexOf("@@@@@author@@@@@") + 16, s.lastIndexOf("@@@@@pubdate@@@@@")), s.substring(s.lastIndexOf("@@@@@pubdate@@@@@") + 17, s.lastIndexOf("@@@@@publisher@@@@@")), s.substring(s.lastIndexOf("@@@@@publisher@@@@@") + 19, s.lastIndexOf("@@@@@type@@@@@")), Integer.valueOf(s.substring(s.lastIndexOf("@@@@@type@@@@@") + 14, s.length())));
			pubList.add(book);
		}
		model.put("pubList", pubList);
		model.put("obj", "zh_CN".equals((String) request.getSession().getAttribute("lang")) ? "图书" : "book");
		return new ModelAndView("ftl/IndexHotReading.ftl", model);
	}

	/**
	 * 首页最新资源
	 */
	@RequestMapping("indexNewest")
	public ModelAndView indexNewest(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Book> pubList = new ArrayList<Book>();
		List<String> indexNewest = null;
		Book book = null;
		indexNewest = bookDao.getSet("new5");
		for (String s : indexNewest) {
			book = new Book(s.substring(0, 32), s.substring(32, s.lastIndexOf("@@@@@author@@@@@")), s.substring(s.lastIndexOf("@@@@@author@@@@@") + 16, s.lastIndexOf("@@@@@pubdate@@@@@")), s.substring(s.lastIndexOf("@@@@@pubdate@@@@@") + 17, s.lastIndexOf("@@@@@publisher@@@@@")), s.substring(s.lastIndexOf("@@@@@publisher@@@@@") + 19, s.lastIndexOf("@@@@@type@@@@@")), Integer.valueOf(s.substring(s.lastIndexOf("@@@@@type@@@@@") + 14, s.length())));
			pubList.add(book);
		}
		model.put("pubList", pubList);
		return new ModelAndView("/ftl/IndexNewest.ftl", model);
	}

	/**
	 * 根据不同的key获取不同的Value
	 */
	private String getInfoFromRedis(HttpServletRequest request, String obj) {
		String lang = (String) request.getSession().getAttribute("lang");
		List<String> s = null;
		if ("zh_CN".equals(lang)) {
			s = bookDao.getList(obj + "_zh_CN");
		} else if ("en_US".equals(lang)) {
			s = bookDao.getList(obj + "_en_US");
		}
		return 0 == s.size() ? "" : s.get(0);
	}

	/**
	 * 外文电子书编辑推荐
	 */
	@RequestMapping("enBookEditor")
	public ModelAndView enBookEditor(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Book> editorRecommendsList = new ArrayList<Book>();
		List<String> enBookEditor = new ArrayList<String>();
		Book book = null;

		// 获取 Session 中的用户（CUser）信息和机构（BInstitution）信息
		CUser user = (CUser) request.getSession().getAttribute("mainUser");
		BInstitution institution = (BInstitution) request.getSession().getAttribute("institution");
		institution = institution == null ? user != null ? user.getInstitution() : null : institution;
		if (institution != null) {
			enBookEditor = bookDao.getSet(institution.getId() + "__Editor__EN");
			if (enBookEditor.size() == 0) {
				enBookEditor = bookDao.getSet("enBookEditor");
			}
		} else {
			enBookEditor = bookDao.getSet("enBookEditor");
			// enBookEditor =
			// bookDao.getSet("40288a9c4e4e30b7014e52f87ab84fd9__Editor__EN");

		}
		for (String s : enBookEditor) {

			book = new Book(s.substring(0, 32), s.substring(32, s.lastIndexOf("##author##")), s.substring(s.lastIndexOf("##author##") + 10, s.lastIndexOf("##publisher##")), s.substring(s.lastIndexOf("##publisher##") + 13, s.lastIndexOf("##cover##")), s.substring(s.lastIndexOf("##cover##") + 9, s.length()) == null || s.substring(s.lastIndexOf("##cover##") + 9, s.length()).equals("") || s.substring(s.lastIndexOf("##cover##") + 9, s.length()).equals("null") ? 0 : 1);
			editorRecommendsList.add(book);
		}
		model.put("editorRecommendsList", editorRecommendsList);
		return new ModelAndView("ftl/English_Editor.ftl", model);
	}

	/**
	 * 外文电子书--热读资源
	 */
	@RequestMapping("enBookHotReading")
	public ModelAndView enBookHotReading(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Book> hotList = new ArrayList<Book>();
		List<String> enBookHotReading = new ArrayList<String>();
		Book book = null;

		// 获取 Session 中的用户（CUser）信息和机构（BInstitution）信息
		CUser user = (CUser) request.getSession().getAttribute("mainUser");
		BInstitution institution = (BInstitution) request.getSession().getAttribute("institution");
		institution = institution == null ? user != null ? user.getInstitution() : null : institution;
		if (institution != null) {
			enBookHotReading = bookDao.getSet(institution.getId() + "__enbookList__HotReading__EN");
			if (enBookHotReading.size() == 0) {
				enBookHotReading = bookDao.getSet("enbookList__HotReading__EN");
			}
		} else {
			enBookHotReading = bookDao.getSet("enbookList__HotReading__EN");
		}
		for (String s : enBookHotReading) {
			book = new Book(s.substring(0, 32), s.substring(32, s.lastIndexOf("##author##")), s.substring(s.lastIndexOf("##author##") + 10, s.lastIndexOf("##publisher##")), s.substring(s.lastIndexOf("##publisher##") + 13, s.lastIndexOf("##cover##")), s.substring(s.lastIndexOf("##cover##") + 9, s.length()) == null || s.substring(s.lastIndexOf("##cover##") + 9, s.length()).equals("") || s.substring(s.lastIndexOf("##cover##") + 9, s.length()).equals("null") ? 0 : 1);
			hotList.add(book);
		}
		model.put("hotList", hotList);
		return new ModelAndView("ftl/English_HotReading.ftl", model);
	}

	/**
	 * 中文电子书编辑推荐
	 */
	@RequestMapping("cnBookEditor")
	public ModelAndView cnBookEditor(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Book> editorRecommendsList = new ArrayList<Book>();
		List<String> enBookEditor = new ArrayList<String>();
		Book book = null;

		// 获取 Session 中的用户（CUser）信息和机构（BInstitution）信息
		CUser user = (CUser) request.getSession().getAttribute("mainUser");
		BInstitution institution = (BInstitution) request.getSession().getAttribute("institution");
		institution = institution == null ? user != null ? user.getInstitution() : null : institution;
		if (institution != null) {
			enBookEditor = bookDao.getSet(institution.getId() + "__Editor__CN");
			if (enBookEditor.size() == 0) {
				enBookEditor = bookDao.getSet("cnBookEditor");
			}
		} else {
			enBookEditor = bookDao.getSet("cnBookEditor");
		}
		for (String s : enBookEditor) {
			book = new Book(s.substring(0, 32), s.substring(32, s.lastIndexOf("##author##")), s.substring(s.lastIndexOf("##author##") + 10, s.lastIndexOf("##publisher##")), s.substring(s.lastIndexOf("##publisher##") + 13, s.lastIndexOf("##cover##")), s.substring(s.lastIndexOf("##cover##") + 9, s.length()) == null || s.substring(s.lastIndexOf("##cover##") + 9, s.length()).equals("") || s.substring(s.lastIndexOf("##cover##") + 9, s.length()).equals("null") ? 0 : 1);
			editorRecommendsList.add(book);
		}
		model.put("editorRecommendsList", editorRecommendsList);
		return new ModelAndView("ftl/Chinese_Editor.ftl", model);
	}

	/**
	 * 中文电子书--热读资源
	 */
	@RequestMapping("cnBookHotReading")
	public ModelAndView cnBookHotReading(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Book> hotList = new ArrayList<Book>();
		List<String> cnBookHotReading = new ArrayList<String>();
		Book book = null;

		// 获取 Session 中的用户（CUser）信息和机构（BInstitution）信息
		CUser user = (CUser) request.getSession().getAttribute("mainUser");
		BInstitution institution = (BInstitution) request.getSession().getAttribute("institution");
		institution = institution == null ? user != null ? user.getInstitution() : null : institution;
		if (institution != null) {
			cnBookHotReading = bookDao.getSet(institution.getId() + "__cnbookList__HotReading__CN");
			if (cnBookHotReading.size() == 0) {
				cnBookHotReading = bookDao.getSet("cnbookList__HotReading__CN");
			}
		} else {
			cnBookHotReading = bookDao.getSet("cnbookList__HotReading__CN");
		}
		for (String s : cnBookHotReading) {
			book = new Book(s.substring(0, 32), s.substring(32, s.lastIndexOf("##author##")), s.substring(s.lastIndexOf("##author##") + 10, s.lastIndexOf("##publisher##")), s.substring(s.lastIndexOf("##publisher##") + 13, s.lastIndexOf("##cover##")), s.substring(s.lastIndexOf("##cover##") + 9, s.length()) == null || s.substring(s.lastIndexOf("##cover##") + 9, s.length()).equals("") || s.substring(s.lastIndexOf("##cover##") + 9, s.length()).equals("null") ? 0 : 1);
			hotList.add(book);
		}
		model.put("hotList", hotList);
		return new ModelAndView("ftl/Chinese_HotReading.ftl", model);
	}

	/**
	 * 外文电子期刊--可读资源
	 */
	@RequestMapping("journalReadable")
	public ModelAndView journalReadable(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Book> list = new ArrayList<Book>();
		Book book = null;
		List<String> readabel = bookDao.getSet("journalReadbleList");
		for (String s : readabel) {
			book = new Book(s.substring(0, 32), s.substring(32, s.lastIndexOf("##oa##")), s.substring(s.lastIndexOf("##oa##") + 6, s.lastIndexOf("##free##")), s.substring(s.lastIndexOf("##free##") + 8, s.lastIndexOf("##publisher##")), s.substring(s.lastIndexOf("##publisher##") + 13, s.lastIndexOf("##start##")), s.substring(s.lastIndexOf("##start##") + 9, s.lastIndexOf("##end##")), s.substring(s.lastIndexOf("##end##") + 7, s.length()));
			list.add(book);
		}
		model.put("list", list);
		return new ModelAndView("ftl/Journal_Readable.ftl", model);
	}

	/**
	 * 外文电子期刊--推荐期刊
	 */
	@RequestMapping("journalRecommend")
	public ModelAndView journalRecommend(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Book> list = new ArrayList<Book>();
		List<String> journalRecommend = new ArrayList<String>();
		Book book = null;
		// 获取 Session 中的用户（CUser）信息和机构（BInstitution）信息
		CUser user = (CUser) request.getSession().getAttribute("mainUser");
		BInstitution institution = (BInstitution) request.getSession().getAttribute("institution");
		institution = institution == null ? user != null ? user.getInstitution() : null : institution;
		if (institution != null) {
			if (journalRecommend.size() == 0) {
				journalRecommend = bookDao.getSet("JournalRecommend");
			}
		} else {
			journalRecommend = bookDao.getSet("JournalRecommend");
		}
		for (String s : journalRecommend) {
			book = new Book(s.substring(0, 32), s.substring(32, s.lastIndexOf("##publisher##")), s.substring(s.lastIndexOf("##publisher##") + 13, s.lastIndexOf("##start##")), s.substring(s.lastIndexOf("##start##") + 9, s.lastIndexOf("##end##")), s.substring(s.lastIndexOf("##end##") + 7, s.lastIndexOf("##license##")), Integer.parseInt(s.substring(s.lastIndexOf("##license##") + 11, s.lastIndexOf("##oa##"))), s.substring(s.lastIndexOf("##oa##") + 6, s.lastIndexOf("##free##")), s.substring(s.lastIndexOf("##free##") + 8, s.length()));
			list.add(book);
		}
		model.put("list", list);
		return new ModelAndView("ftl/RecJournal.ftl", model);
	}

	/**
	 * 外文电子期刊--热读文章
	 */
	@RequestMapping("journalHotReading")
	public ModelAndView journalHotReading(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Book> hotlist = new ArrayList<Book>();
		List<String> hotReading = new ArrayList<String>();
		Book book = null;
		// 获取 Session 中的用户（CUser）信息和机构（BInstitution）信息
		CUser user = (CUser) request.getSession().getAttribute("mainUser");
		BInstitution institution = (BInstitution) request.getSession().getAttribute("institution");
		institution = institution == null ? user != null ? user.getInstitution() : null : institution;
		if (institution != null) {
			System.out.println("===用户===" + user.getName() + "=====机构===" + institution.getName() + " - " + institution.getId());
			hotReading = bookDao.getSet(institution.getId() + "__JournalHotReadingArticle");
			if (hotReading.size() == 0) {
				hotReading = bookDao.getSet("JournalHotReadingArticle");
			}
		} else {
			hotReading = bookDao.getSet("JournalHotReadingArticle");
		}
		for (String s : hotReading) {
			System.out.println(s);

			book = new Book(s.substring(0, 32), s.substring(32, s.lastIndexOf("##parentId##")), s.substring(s.lastIndexOf("##parentId##") + 12, s.lastIndexOf("##parentTitle##")), s.substring(s.lastIndexOf("##parentTitle##") + 15, s.lastIndexOf("##year##")), s.substring(s.lastIndexOf("##year##") + 8, s.lastIndexOf("##volumeCode##")), s.substring(s.lastIndexOf("##volumeCode##") + 14, s.lastIndexOf("##issueCode##")), s.substring(s.lastIndexOf("##issueCode##") + 13, s.lastIndexOf("##startPage##")), s.substring(s.lastIndexOf("##startPage##") + 13, s.lastIndexOf("##endPage##")), s.substring(s.lastIndexOf("##endPage##") + 11, s.lastIndexOf("##publisher##")), s.substring(s.lastIndexOf("##publisher##") + 13, s.length()));
			hotlist.add(book);

		}
		model.put("license", institution == null ? "false" : "true");
		model.put("hotlist", hotlist);
		return new ModelAndView("ftl/Journal_HotReadingArticle.ftl", model);
	}
}
