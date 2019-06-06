package cn.digitalpublishing.springmvc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.Param;
import cn.digitalpublishing.ep.po.BConfiguration;
import cn.digitalpublishing.springmvc.form.configure.BConfigurationForm;
import cn.digitalpublishing.util.io.FileUtil;

@Controller
@RequestMapping("/pages/configuration")
public class BConfigurationController extends BaseController {

	@RequestMapping("/form/manager")
	public ModelAndView manager(HttpServletRequest request,HttpServletRequest response,BConfigurationForm form)throws Exception{
		String forwardString="user/configuration";
		Map<String,Object> model = new HashMap<String,Object>();
		try{
//			CUser user = request.getSession().getAttribute("mainUser")==null?null:(CUser)request.getSession().getAttribute("mainUser");
			form.setUrl(request.getRequestURL().toString());
			//查询配置信息
			Map<String,Object> condition = new HashMap<String,Object>();
			List<BConfiguration> list = this.configureService.getConfigureList(condition," order by a.code ");
//			form.setCount(this.configureService.getConfigureCount(condition));
			model.put("list", list);
			model.put("form", form);
		}catch(Exception e){
            request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		return new ModelAndView(forwardString, model);
	}
	@RequestMapping("/form/edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletRequest response,BConfigurationForm form)throws Exception{
		String forwardString="";
		Map<String,Object> model = new HashMap<String,Object>();
		try{
			String id = request.getParameter("configCode").toString();
			String type = request.getParameter("type").toString();
			form.setObj(this.configureService.getConfigure(id));
			if(form.getObj()==null){
				form.setObj(new BConfiguration());
				form.setYyyy(1);//状态是1 时是需要新增
			}
			form.getObj().setId(id);
			form.getObj().setCode(id);
			form.getObj().setKey(Param.getParam("base.setting").get(id));
			form.getObj().setType(Integer.valueOf(type));
			if(form.getObj().getType()==1){
				if(form.getObj().getVal()==null||"".equals(form.getObj().getVal())){
					String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
					url += "/pages/configuration/form/view?configCode="+form.getObj().getCode();
					form.getObj().setVal(url);
				}
				form.setContent(form.getObj().getContent());
				forwardString="configure/editContentConfig";
			}else if(form.getObj().getType()==3){
				forwardString="configure/uploadConfig";
			}else{
				forwardString="configure/editConfig";
			}
			model.put("form", form);
		}catch(Exception e){
            request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		return new ModelAndView(forwardString, model);
	}
	@RequestMapping(value="/form/editSubmit")
	public ModelAndView editSubmit(HttpServletRequest request,HttpServletResponse response,BConfigurationForm form) throws Exception {
		HashMap<String,Object> model = new HashMap<String,Object>();
		String forwardString="frame/result";
		try{
//			form.getObj().setContent(request.getParameter("content")!=null?request.getParameter("content").toString():"");
			if(form.getYyyy()!=null&&form.getYyyy()==1){
				this.configureService.addConfigure(form.getObj());
				request.setAttribute("prompt", Lang.getLanguage("Controller.BConfiguration.editSubmit.prompt.success", request.getSession().getAttribute("lang").toString()));//"平台设置成功提示");
				request.setAttribute("message",Lang.getLanguage("Controller.BConfiguration.editSubmit.message.success", request.getSession().getAttribute("lang").toString()));//"平台设置成功，您的修改已经生效！");
			}else{
				String[] properties=null;
				this.configureService.updateConfigure(form.getObj(), form.getObj().getId(), properties);
				request.setAttribute("prompt", Lang.getLanguage("Controller.BConfiguration.editSubmit.prompt.success", request.getSession().getAttribute("lang").toString()));//"平台设置成功提示");
				request.setAttribute("message",Lang.getLanguage("Controller.BConfiguration.editSubmit.message.success", request.getSession().getAttribute("lang").toString()));//"平台设置成功，您的修改已经生效！");
			}
		}catch(Exception e){
			request.setAttribute("prompt", Lang.getLanguage("Controller.BConfiguration.editSubmit.prompt.error", request.getSession().getAttribute("lang").toString()));//"平台设置失败提示");
			request.setAttribute("message", (e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
		model.put("form", form);
		return new ModelAndView(forwardString,model);
	}
	
	@RequestMapping(value="/form/uploadSubmit")
	public ModelAndView processForm(@ModelAttribute(value="form") BConfigurationForm form,BindingResult result,HttpServletRequest request){
		HashMap<String,Object> model = new HashMap<String,Object>();
		String forwardString="frame/result";
		if(!result.hasErrors()){
			try {				
//				BInstitution institution=this.customService.getInstitution(form.getId());
//				institution.setLogoUrl(form.getObj().getLogoUrl());
//				institution.setLogoNote(form.getObj().getLogoNote());
				//文件上传
				FileOutputStream outputStream = null;
				String fileName = form.getFile().getOriginalFilename();
				if(fileName!=null && fileName.length()>0){
					String format = fileName.substring(fileName.indexOf(".")+1).toLowerCase();
					if(form.getFormat().indexOf(format)<0){
						throw new CcsException("institution.info.logo.format.error");
					}
					String webRoot = request.getSession().getServletContext().getRealPath("");		
					if(form.getObj().getCode().equalsIgnoreCase("pic1")){
						// /images/cnpiec_logo_zh_CN.png"
						String filePath_ZH = Param.getParam("upload.directory.config").get("logoPathZH");
						// /images/cnpiec_logo_en_US.png"
						String filePath_EN = Param.getParam("upload.directory.config").get("logoPathEN");
						// FileUtil.newFolder(webRoot + savePath);
						
						// 中文版
						outputStream = new FileOutputStream(new File(webRoot+filePath_ZH));
						outputStream.write(form.getFile().getFileItem().get());
						outputStream.close();
						
						// 英文版
						outputStream = new FileOutputStream(new File(webRoot+filePath_EN));
						outputStream.write(form.getFile().getFileItem().get());
						outputStream.close();
						
						form.getObj().setContent(filePath_ZH);
					}else{
						
//						String appRoot=request.getContextPath();
						String savePath = Param.getParam("upload.directory.config").get("platform");
						if(!FileUtil.isExist(webRoot + savePath)){
							FileUtil.newFolder(webRoot + savePath);
						}
						String filePath = savePath + "/" + form.getObj().getCode() +"." +format; 
						outputStream = new FileOutputStream(new File(webRoot+filePath));
						outputStream.write(form.getFile().getFileItem().get());
						outputStream.close();
						
						form.getObj().setContent(filePath);
					}
//					保存logo位置到数据库
					if(form.getYyyy()!=null&&form.getYyyy()==1){
						this.configureService.addConfigure(form.getObj());
						request.setAttribute("prompt", Lang.getLanguage("Controller.BConfiguration.editSubmit.prompt.success", request.getSession().getAttribute("lang").toString()));//"平台设置成功提示");
						request.setAttribute("message",Lang.getLanguage("Controller.BConfiguration.editSubmit.message.success", request.getSession().getAttribute("lang").toString()));//"平台设置成功，您的修改已经生效！");
					}else{
						String[] properties=null;
						this.configureService.updateConfigure(form.getObj(), form.getObj().getId(), properties);
						request.setAttribute("prompt", Lang.getLanguage("Controller.BConfiguration.editSubmit.prompt.success", request.getSession().getAttribute("lang").toString()));//"平台设置成功提示");
						request.setAttribute("message",Lang.getLanguage("Controller.BConfiguration.editSubmit.message.success", request.getSession().getAttribute("lang").toString()));//"平台设置成功，您的修改已经生效！");
					}   
				}
			}catch (Exception e){
				e.printStackTrace();
				request.setAttribute("prompt", Lang.getLanguage("Controller.BConfiguration.editSubmit.prompt.error", request.getSession().getAttribute("lang").toString()));//"平台设置失败提示");
				request.setAttribute("message", (e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			}
		}else{
			request.setAttribute("prompt", Lang.getLanguage("Controller.BConfiguration.editSubmit.prompt.error", request.getSession().getAttribute("lang").toString()));//"平台设置失败提示");
			request.setAttribute("message", result.getFieldErrors());
		}
		model.put("form", form);
		return new ModelAndView(forwardString,model);

	}
	
	@RequestMapping(value="/form/view")
	public ModelAndView view(HttpServletRequest request,HttpServletResponse response,BConfigurationForm form) throws Exception {
		HashMap<String,Object> model = new HashMap<String,Object>();
		String forwardString="user/viewConfigure";
		try{
			BConfiguration obj = new BConfiguration();
			if(form.getConfigCode()!=null&&!"".equals(form.getConfigCode())){
				Map<String,Object> condition = new HashMap<String,Object>();
				condition.put("code", form.getConfigCode());
				List<BConfiguration> list = this.configureService.getConfigureList(condition, "");
				if(list!=null&&list.size()>0){
					obj = list.get(0);
				}
			}
			model.put("obj", obj);
			model.put("form", form);
		}catch(Exception e){
			//e.printStackTrace();
			request.setAttribute("message", (e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
		return new ModelAndView(forwardString,model);
	}
}
