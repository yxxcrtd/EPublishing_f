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
import cn.digitalpublishing.ep.po.OExchange;

@Controller
@RequestMapping("/pages/exchange")
public class OExchangeController extends BaseController {

	/**
	 * 数据接口
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(HttpServletRequest request, Model model){
		ResultObject<OExchange> result=null;
		try{
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); //1-insert 2-update
			Converter<OExchange> converter=new Converter<OExchange>();
			OExchange obj=(OExchange)converter.json2Object(objJson, OExchange.class.getName());
			if("2".equals(operType)){
				this.oOrderService.updateExchange(obj,obj.getId(), null);
			}else if ("1".equals(operType)){
				this.oOrderService.insertExchange(obj);
			}else{
				//删除
				this.oOrderService.deleteExchange(obj.getId());
			}
			ObjectUtil<OExchange> util=new ObjectUtil<OExchange>();
			obj=util.setNull(obj, new String[]{Set.class.getName()});
			result=new ResultObject<OExchange>(1,obj,Lang.getLanguage("Controller.OExchange.insert.success",request.getSession().getAttribute("lang").toString()));//"汇率信息维护成功！");
		}catch(Exception e){
			result=new ResultObject<OExchange>(2,Lang.getLanguage("Controller.OExchange.insert.error",request.getSession().getAttribute("lang").toString()));//"汇率信息维护失败！");
		}
		model.addAttribute("target",result);
	}
}
