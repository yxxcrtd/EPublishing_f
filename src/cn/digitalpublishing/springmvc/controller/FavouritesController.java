package cn.digitalpublishing.springmvc.controller;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.Param;
import cn.digitalpublishing.ep.po.CFavourites;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.springmvc.form.favourites.FavouritesForm;

/**
 * 收藏 Controller
 */
@Controller
@RequestMapping("/pages/favourites")
public class FavouritesController extends BaseController {

	// 收藏与取消收藏
	@RequestMapping("/form/commit")
	public @ResponseBody String favourite(HttpServletRequest request) throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		String result = "";
		try {
			CUser user = null == request.getSession().getAttribute("mainUser") ? null : (CUser) request.getSession().getAttribute("mainUser");
			if (null != user) {
				String publicationsId = request.getParameter("pubId");
				PPublications pub = new PPublications();
				pub.setId(publicationsId);

				condition.put("pid", publicationsId);
				if (null != customService.getFavourite(condition)) {
					condition.clear();
					condition.put("dels", publicationsId);
					condition.put("userId", user.getId());
					cUserService.deleteFavorites(condition);
					result = "del";
				} else {
					CFavourites favourite = new CFavourites();
					favourite.setUser(user);
					favourite.setPublications(pub);
					favourite.setCreateDate(new Date());
					customService.insertFavourites(favourite);
					result = "success";
				}
			} else {
				result = "nologin";
			}
		} catch (Exception e) {
			result = "error";
		}
		return result;
	}

	@RequestMapping(value = "/form/batchCommit")
	public void batchCommit(HttpServletRequest request, HttpServletResponse response, FavouritesForm form) throws Exception {
		String result = "";
		try {
			CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			if (user != null) {
				String publicationsIds = request.getParameter("pubIds");
				String srcIds = request.getParameter("srcIds").replace("@", ",");
				String[] pids = publicationsIds.split("@");
				String[] sids = srcIds.split(",");
				if (sids != null && sids.length > 0) {
					Map<String, Object> condition = new HashMap<String, Object>();
					condition.put("dels", srcIds);
					condition.put("userId", user.getId());
					this.cUserService.deleteFavorites(condition);
				}
				if (pids != null && pids.length > 0) {
					for (int i = 0; i < pids.length; i++) {
						PPublications pub = new PPublications();
						pub.setId(pids[i]);
						Map<String, Object> condition = new HashMap<String, Object>();
						condition.put("pid", pids[i]);
						condition.put("userId", user.getId());
						// 已经收藏的出版物不再插入
						if (this.customService.getFavourite(condition) == null) {
							CFavourites favourite = new CFavourites();
							favourite.setUser(user);
							favourite.setPublications(pub);
							favourite.setCreateDate(new Date());
							this.customService.insertFavourites(favourite);
						}
					}
					result = "success:" + Lang.getLanguage("Controller.Favourites.commit.success", request.getSession().getAttribute("lang").toString());// 收藏成功！";
				}
			} else {
				result = "error:" + Lang.getLanguage("Controller.Favourites.commit.noUser.error", request.getSession().getAttribute("lang").toString());// 收藏成功！";
			}
		} catch (Exception e) {
			result = "false:" + ((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
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
	 * 用户收藏夹管理
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/favorites")
	public ModelAndView favorites(HttpServletRequest request, HttpServletResponse response, HttpSession session, FavouritesForm form) throws Exception {
		String forwardString = "user/favorites";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			// CUser user = new CUser();
			// user.setId("086389937b1f1030a8ee2610e0325a2b");
			List<CFavourites> list = new ArrayList<CFavourites>();
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("available", 3);
			if (user != null) {
				condition.put("userId", user.getId());
				String sort = " order by c.title asc ";// titleAsc-标题升序;titleDesc-标题降序;dateDesc-最新优先;dateAsc-最早优先;
				if (form.getOrder() != null) {
					if (form.getOrder().equalsIgnoreCase("titleAsc")) {
						sort = " order by c.title asc ";
					} else if (form.getOrder().equalsIgnoreCase("titleDesc")) {
						sort = " order by c.title desc ";
					} else if (form.getOrder().equalsIgnoreCase("dateDesc")) {
						sort = " order by c.createOn desc ";
					} else if (form.getOrder().equalsIgnoreCase("dateAsc")) {
						sort = " order by c.createOn asc ";
					} else if (form.getOrder().equalsIgnoreCase("createAsc")) {
						sort = " order by a.createDate asc ";
					} else if (form.getOrder().equalsIgnoreCase("createDesc")) {
						sort = " order by a.createDate desc ";
					}
				}
				if (form.getFtype() != null && !form.getFtype().equals(0)) {
					condition.put("ftype", form.getFtype());
				}
				list = this.cUserService.getFavoutitesPagingList(condition, sort, form.getPageCount(), form.getCurpage());
				form.setCount(this.cUserService.getFavoutitesCount(condition));
			} else {
				request.setAttribute("prompt", Lang.getLanguage("Controller.BInstitutionUpload.edit.tip", request.getSession().getAttribute("lang").toString()));// "提示");
				request.setAttribute("message", Lang.getLanguage("Controller.Cart.checkOut.message.noLogin", request.getSession().getAttribute("lang").toString()));// "您无权进行该操作");
				forwardString = "frame/result";
			}
			form.setType(Param.getParam("PPublications.type.status", true, request.getSession().getAttribute("lang").toString()));
			form.setUrl(request.getRequestURL().toString());
			model.put("list", list);
			model.put("form", form);
		} catch (Exception e) {
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/form/downloadfavorites")
	public ModelAndView downloadfavorites(HttpServletRequest request, HttpServletResponse response, FavouritesForm form) throws Exception {
		String forwardString = "user/favorites";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			List<CFavourites> list = new ArrayList<CFavourites>();
			Map<String, Object> condition = new HashMap<String, Object>();
			if (user != null) {
				condition.put("userId", user.getId());
				String sort = " order by c.title asc ";// titleAsc-标题升序;titleDesc-标题降序;dateDesc-最新优先;dateAsc-最早优先;
				if (form.getOrder() != null) {
					if (form.getOrder().equalsIgnoreCase("titleAsc")) {
						sort = " order by c.title asc ";
					} else if (form.getOrder().equalsIgnoreCase("titleDesc")) {
						sort = " order by c.title desc ";
					} else if (form.getOrder().equalsIgnoreCase("dateDesc")) {
						sort = " order by c.createOn desc ";
					} else if (form.getOrder().equalsIgnoreCase("dateAsc")) {
						sort = " order by c.createOn asc ";
					} else if (form.getOrder().equalsIgnoreCase("createAsc")) {
						sort = " order by a.createDate asc ";
					} else if (form.getOrder().equalsIgnoreCase("createDesc")) {
						sort = " order by a.createDate desc ";
					}
				}
				if (form.getFtype() != null && !form.getFtype().equals(0)) {
					condition.put("ftype", form.getFtype());
				}
				list = this.cUserService.getfList(condition, sort);
				form.setCount(this.cUserService.getFavoutitesCount(condition));
			}
			form.setType(Param.getParam("PPublications.type.status", true, request.getSession().getAttribute("lang").toString()));
			form.setUrl(request.getRequestURL().toString());
			// 输出的excel文件工作表名
			String worksheet = "Search_Statistics_";
			// excel工作表的标题
			StringBuffer sb = new StringBuffer();
			sb.append("Title;");
			sb.append("name;");
			sb.append("author;");
			sb.append("code;");
			sb.append("listPrice;");
			sb.append("lcurr;");
			sb.append("pubDate;");
			sb.append("pubSubject;");
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
			int row = 1;
			for (CFavourites c : list) {
				sheet.addCell(new Label(0, row, c.getPublications().getTitle() != null ? c.getPublications().getTitle().replace("<article-title>", "").replace("</article-title>", "") : ""));
				sheet.addCell(new Label(1, row, c.getPublications().getPublisher().getName()));
				sheet.addCell(new Label(2, row, c.getPublications().getAuthor()));
				sheet.addCell(new Label(3, row, c.getPublications().getCode()));
				sheet.addCell(new Label(4, row, c.getPublications().getListPrice() == 0 ? "" : c.getPublications().getListPrice().toString()));
				sheet.addCell(new Label(5, row, c.getPublications().getLcurr()));
				sheet.addCell(new Label(6, row, c.getPublications().getPubDate()));
				sheet.addCell(new Label(7, row, c.getPublications().getPubSubject()));
				row++;
			}
			workbook.write();
			workbook.close();
			os.close();

		} catch (Exception e) {
			forwardString = "error";
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			return new ModelAndView(forwardString, model);
		}
		return null;

	}

	@RequestMapping(value = "/form/favorites/delete")
	public void favoritesDelete(HttpServletRequest request, HttpServletResponse response, HttpSession session, FavouritesForm form) throws Exception {
		// String forwardString="user/favorites";
		// Map<String,Object> model = new HashMap<String,Object>();
		String result = "error:" + Lang.getLanguage("Controller.User.deleteDir.del.error", request.getSession().getAttribute("lang").toString());
		try {
			CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			// CUser user = new CUser();
			// user.setId("086389937b1f1030a8ee2610e0325a2b");
			if (user != null) {
				if (form.getDels() != null) {
					Map<String, Object> condition = new HashMap<String, Object>();
					condition.put("userId", user.getId());
					condition.put("dels", form.getDels());
					this.cUserService.deleteFavorites(condition);
					result = "success:" + Lang.getLanguage("Controller.User.saveSearch.del.success", request.getSession().getAttribute("lang").toString());
				} else {
					result = "error:" + Lang.getLanguage("Pages.User.Favorites.Prompt.deleteNull", request.getSession().getAttribute("lang").toString());
				}

			} else {
				result = "error:" + Lang.getLanguage("Controller.view.login.no", request.getSession().getAttribute("lang").toString());
			}

		} catch (Exception e) {
			result = "error:" + ((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
		}
		// return this.favorites(request, response, session, form);
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
}
