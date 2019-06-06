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
import cn.digitalpublishing.ep.po.PPriceType;

@Controller
@RequestMapping("/pages/priceType")
public class PriceTypeController extends BaseController {

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(HttpServletRequest request, Model model){
		ResultObject<PPriceType> result=null;
		try{
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); //1-insert 2-update
			Converter<PPriceType> converter=new Converter<PPriceType>();
			PPriceType obj=(PPriceType)converter.json2Object(objJson, PPriceType.class.getName());
			if("2".equals(operType)){
				this.configureService.updatePriceType(obj,obj.getId(), null);
			}else if ("1".equals(operType)){
				this.configureService.insertPriceType(obj);
			}else{
				//删除
				this.configureService.deletePriceType(obj.getId());
			}
			ObjectUtil<PPriceType> util=new ObjectUtil<PPriceType>();
			obj=util.setNull(obj, new String[]{Set.class.getName()});
			result=new ResultObject<PPriceType>(1,obj,Lang.getLanguage("Controller.PPriceType.insert.success",request.getSession().getAttribute("lang").toString()));//"价格类型信息维护成功！");
		}catch(Exception e){
			result=new ResultObject<PPriceType>(2,Lang.getLanguage("Controller.PPriceType.insert.error",request.getSession().getAttribute("lang").toString()));//"价格类型信息维护失败！");
		}
		model.addAttribute("target",result);
	}
}
