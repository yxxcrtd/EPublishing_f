package cn.digitalpublishing.springmvc.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.Param;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.springmvc.form.configure.BInstitutionForm;
import cn.digitalpublishing.util.io.FileUtil;

@Controller
public class BInstitutionUploadController extends BaseController implements HandlerExceptionResolver {

	@RequestMapping("/pages/user/form/logo")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, HttpSession session, BInstitutionForm form) throws Exception {
		String forwardString = "user/setlogo";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (session.getAttribute("mainUser") != null) {
				CUser user = (CUser) session.getAttribute("mainUser");
				if (user.getLevel() == 2) {// 判断主登陆用户是否是机构管理员
					if (user.getInstitution() != null && user.getInstitution().getId() != null && !"".equals(user.getInstitution().getId())) {
						form.setObj(this.customService.getInstitution(user.getInstitution().getId()));
						form.setId(form.getObj().getId());
					} else {// 未找到管理员可管理的机构
						request.setAttribute("prompt", Lang.getLanguage("Controller.BInstitutionUpload.edit.tip", request.getSession().getAttribute("lang").toString()));// "提示");
						request.setAttribute("message", Lang.getLanguage("Controller.BInstitutionUpload.edit.noins", request.getSession().getAttribute("lang").toString()));// "未找到该管理员可管理的机构");
						forwardString = "frame/result";
					}
				} else {
					request.setAttribute("prompt", Lang.getLanguage("Controller.BInstitutionUpload.edit.tip", request.getSession().getAttribute("lang").toString()));// "提示");
					request.setAttribute("message", Lang.getLanguage("Controller.BInstitutionUpload.edit.nocom", request.getSession().getAttribute("lang").toString()));// "您无权进行该操作");
					forwardString = "frame/result";
				}
			} else {
				request.setAttribute("prompt", Lang.getLanguage("Controller.BInstitutionUpload.edit.tip", request.getSession().getAttribute("lang").toString()));// "提示");
				request.setAttribute("message", Lang.getLanguage("Controller.BInstitutionUpload.edit.nologin", request.getSession().getAttribute("lang").toString()));// "该操作需要登录机构管理员用户方可进行操作");
				forwardString = "frame/result";
			}
			model.put("form", form);
		} catch (Exception e) {
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping("/pages/user/form/uploadSubmit")
	public ModelAndView processForm(@ModelAttribute(value = "form") BInstitutionForm form, BindingResult result, HttpServletRequest req, MultipartHttpServletRequest request) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String forwardString = "user/setlogo";
		String deleteFile = "";
		if (!result.hasErrors()) {
			try {
				BInstitution institution = this.customService.getInstitution(form.getId());
				institution.setLogoUrl(form.getObj().getLogoUrl());
				institution.setLogoNote(form.getObj().getLogoNote());
				// 文件上传
				String fileName = form.getFile().getOriginalFilename();
				if (fileName != null && fileName.length() > 0) {
					String format = fileName.substring(fileName.indexOf(".") + 1).toLowerCase();
					if (form.getFormat().indexOf(format) < 0) {
						throw new CcsException("institution.info.logo.format.error");
					}
					String webRoot = req.getSession().getServletContext().getRealPath("");
					String path = Param.getParam("config.website.path.institution.logo").get("path").replace("-", ":");
					String filePath = "";
					List<MultipartFile> files = request.getFiles("file");
					for (MultipartFile file : files) {
						if (!file.isEmpty()) {
							filePath = FileUtil.uploadFile(file, path);
						}
					}
					deleteFile = webRoot + filePath;
					institution.setLogo(filePath);
				}
				this.configureService.updateInstitution(institution, form.getId(), null);
				form.setIsSuccess("true");
				form.setMsg(Lang.getLanguage("institution.info.update.Maintenance.success"));// logo上传成功！
			} catch (Exception e) {
				File file = new File(deleteFile);
				if (file.exists()) {
					file.delete();
				}
				form.setIsSuccess("false");
				form.setMsg((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), req.getSession().getAttribute("lang").toString()) : e.getMessage());
			}
		} else {
			form.setIsSuccess("false");
			form.setMsg(Lang.getLanguage("upload.info.upload.exception"));
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);

	}

	public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse res, Object obj, Exception exception) {
		return null;
	}
}
