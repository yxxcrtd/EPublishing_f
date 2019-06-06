package cn.digitalpublishing.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.MMarkData;
import cn.digitalpublishing.springmvc.form.configure.MarkDataForm;
import cn.digitalpublishing.util.io.IOHandler;

@Controller
@RequestMapping("/pages/markData")
public class MMarkDataController extends BaseController {

	/**
	 * 数据接口
	 * 
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(HttpServletRequest request, Model model) {
		ResultObject<MMarkData> result = null;
		try {
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); // 1-insert
																			// 2-update
			// int operNum =
			// Integer.valueOf(request.getParameter("operNum").toString());

			Converter<MMarkData> converter = new Converter<MMarkData>();
			MMarkData obj = (MMarkData) converter.json2Object(objJson, MMarkData.class.getName());
			if ("2".equals(operType)) {
				this.configureService.updateMarkData(obj, obj.getId(), null);
			} else if ("1".equals(operType)) {
				this.configureService.insertMarkData(obj);
			} else if ("3".equals(operType)) {
				// 删除
				this.configureService.deleteMarkData(obj.getId());
			}
			ObjectUtil<MMarkData> util = new ObjectUtil<MMarkData>();
			obj = util.setNull(obj, new String[] { Set.class.getName() });

			result = new ResultObject<MMarkData>(1, obj, Lang.getLanguage("Controller.MMarkData.insert.manage.success", request.getSession().getAttribute("lang").toString()));// "价格信息维护成功！");//"出版商信息维护成功！");
		} catch (Exception e) {
			// e.printStackTrace();
			result = new ResultObject<MMarkData>(2, Lang.getLanguage("Controller.MMarkData.insert.manage.error", request.getSession().getAttribute("lang").toString()));// "价格信息维护失败！");//"出版商信息维护失败！");
		}
		model.addAttribute("target", result);
	}

	@RequestMapping(value = "/form/manager")
	public ModelAndView manager(HttpServletRequest request, HttpServletResponse response, MarkDataForm form) throws Exception {
		String forwardString = "markData/list";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			if (user != null && (user.getLevel() == 2 || user.getLevel() == 4)) {// 机构管理员或中图管理员
				form.setUrl(request.getRequestURL().toString());
				// form.setInstitutions(this.configureService.getInstitutionList(null,null));
				form.setStatusMap(Param.getParam("Markdata.status", true, request.getSession().getAttribute("lang").toString()));

				// 查找登陆人的机构
				if (form.getCondition() == null) {
					form.setCondition(new HashMap<String, Object>());
				}
				form.getCondition().put("institution", user.getInstitution().getId());

				form.setCount(this.configureService.getMarkDataCount(form.getCondition()));
				List<MMarkData> list = this.configureService.getMarkDataPagingList(form.getCondition(), " order by a.createOn desc ", form.getPageCount(), form.getCurpage());
				model.put("list", list);
			} else {
				request.setAttribute("prompt", Lang.getLanguage("Controller.BInstitutionUpload.edit.tip", request.getSession().getAttribute("lang").toString()));// "提示");
				request.setAttribute("message", Lang.getLanguage("Controller.Cart.checkOut.message.noLogin", request.getSession().getAttribute("lang").toString()));// "您无权进行该操作");
				forwardString = "frame/result";
			}
			model.put("form", form);
		} catch (Exception e) {
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping("/form/download")
	public ModelAndView download(HttpServletRequest request, HttpServletResponse response, MarkDataForm form) throws Exception {
		String forwardString = "";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			if (user != null && (user.getLevel() == 2 || user.getLevel() == 4)) {// 机构管理员或中图管理员
				// 根据Id查询信息
				MMarkData data = this.configureService.getMarkData(form.getId());
				if (data != null && data.getPath() != null) {
					// path是指欲下载的文件的路径。
					String path = Param.getParam("pdf.directory.config").get("dir").replace("-", ":") + data.getPath();
					// 获取文件后缀
					String ext = data.getPath().substring(data.getPath().lastIndexOf(".") + 1).toUpperCase();
					IOHandler.download(path, data.getName() + "." + ext, request, response);
				}
			}
			return null;
		} catch (Exception e) {
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
			return new ModelAndView(forwardString, model);
		}
	}
}
