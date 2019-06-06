package cn.digitalpublishing.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.ccsit.restful.tool.Converter;
import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.ResultObject;
import cn.com.daxtech.framework.util.ObjectUtil;
import cn.digitalpublishing.ep.po.BPublisher;
import cn.digitalpublishing.springmvc.form.configure.BPublisherForm;

@Controller
@RequestMapping("/pages/publishers")
public class BPublisherController extends BaseController {
	
	@RequestMapping(value="/form/list/{letter}")
	public ModelAndView list(@PathVariable String letter, HttpServletRequest request,HttpServletResponse response, BPublisherForm form)throws Exception {
		String forwardString="publishers/list";
		Map<String,Object> model = new HashMap<String,Object>();
		try{
			Map<String,Object> condition =form.getCondition();
			condition.put("letter",letter);
			form.setUrl(request.getRequestURL().toString());
			form.setCount(this.configureService.getPublisherCount(condition));
			form.setLetter(letter);
			List<BPublisher> list = this.configureService.getPublisherPagingList(condition," order by a.name ",form.getPageCount(),form.getCurpage());		
			model.put("letter",letter.toUpperCase());
			model.put("list", list);
			model.put("form", form);
		}catch(Exception e){
            request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		return new ModelAndView(forwardString, model);
	}
	@RequestMapping(value="/form/list")
	public ModelAndView list( HttpServletRequest request,HttpServletResponse response, BPublisherForm form)throws Exception {
		String forwardString="publishers/list";
		Map<String,Object> model = new HashMap<String,Object>();
		try{
			form.setUrl(request.getRequestURL().toString());
			form.setCount(this.configureService.getPublisherCount(form.getCondition()));
			List<BPublisher> list = this.configureService.getPublisherPagingList(form.getCondition()," order by a.name ",form.getPageCount(),form.getCurpage());
			model.put("list", list);
			
		}catch(Exception e){
            request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(HttpServletRequest request, Model model){
		ResultObject<BPublisher> result=null;
		try{
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); //1-insert 2-update
			Converter<BPublisher> converter=new Converter<BPublisher>();
			BPublisher obj=(BPublisher)converter.json2Object(objJson, BPublisher.class.getName());
			if("2".equals(operType)){
				this.configureService.updatePublisher(obj,obj.getId(), null);
			}else if ("1".equals(operType)){
				this.configureService.insertPublisher(obj);
			}else{
				//删除
			}
			ObjectUtil<BPublisher> util=new ObjectUtil<BPublisher>();
			obj=util.setNull(obj, new String[]{Set.class.getName()});
			result=new ResultObject<BPublisher>(1,obj,Lang.getLanguage("publisher.info.maintain.success",request.getSession().getAttribute("lang").toString()));//"出版商信息维护成功！");
		}catch(Exception e){
			result=new ResultObject<BPublisher>(2,Lang.getLanguage("publisher.info.maintain.error",request.getSession().getAttribute("lang").toString()));//"出版商信息维护失败！");
		}
		model.addAttribute("target",result);
	}
	
	@RequestMapping(value="/page")
	public ModelAndView page(HttpServletRequest request,HttpServletResponse response, BPublisherForm form)throws Exception {
		String forwardString="publishers/page";
		Map<String,Object> model = new HashMap<String,Object>();
		try{
			String pageUrl=request.getParameter("n");
			if(pageUrl!=null && !"".equals(pageUrl)){
				model.put("pageUrl", "/ppage/"+pageUrl);
			}			
			form.setUrl(request.getRequestURL().toString());
			model.put("form", form);
		}catch(Exception e){
            request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		return new ModelAndView(forwardString, model);
	}
}