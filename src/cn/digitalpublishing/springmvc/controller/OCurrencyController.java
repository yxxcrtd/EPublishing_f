package cn.digitalpublishing.springmvc.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ccsit.restful.tool.Converter;
import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.model.ResultObject;
import cn.com.daxtech.framework.util.ObjectUtil;
import cn.digitalpublishing.ep.po.OCurrency;

@Controller
@RequestMapping("/pages/currency")
public class OCurrencyController extends BaseController {

	/**
	 * 数据接口
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(HttpServletRequest request, Model model){
		ResultObject<OCurrency> result=null;
		try{
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); //1-insert 2-update
			Converter<OCurrency> converter=new Converter<OCurrency>();
			OCurrency obj=(OCurrency)converter.json2Object(objJson, OCurrency.class.getName());
			if("2".equals(operType)){
				this.oOrderService.updateCurrency(obj,obj.getId(), null);
			}else if ("1".equals(operType)){
				this.oOrderService.insertCurrency(obj);
			}else{
				//删除
				this.oOrderService.deleteCurrency(obj.getId());
			}
			ObjectUtil<OCurrency> util=new ObjectUtil<OCurrency>();
			obj=util.setNull(obj, new String[]{Set.class.getName()});
			result=new ResultObject<OCurrency>(1,obj,Lang.getLanguage("Controller.OCurrency.insert.success",request.getSession().getAttribute("lang").toString()));//"币种信息维护成功！");
		}catch(Exception e){
			result=new ResultObject<OCurrency>(2,Lang.getLanguage("Controller.OCurrency.insert.error",request.getSession().getAttribute("lang").toString()));//"币种信息维护失败！");
		}
		model.addAttribute("target",result);
	}
}
