package cn.digitalpublishing.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.SOnsale;
import cn.digitalpublishing.springmvc.form.index.IndexForm;

@Controller
@RequestMapping("/pages")
public class MenuController extends BaseController {
	@RequestMapping(value="/menu")
	public ModelAndView leftData(HttpServletRequest request,HttpServletResponse response,IndexForm form)throws Exception {
		String forwardString="frame/menu";
		Map<String,Object> model = new HashMap<String,Object>();
		try{
			if(request.getParameter("mid")!=null && !"".equals(request.getParameter("mid"))){
				model.put("mid",request.getParameter("mid"));
			}
			if(request.getParameter("type")!=null&&!"".equals(request.getParameter("type"))){
				model.put("type",request.getParameter("type"));
			}
		}catch(Exception e){
            request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}
}
