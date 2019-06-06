package cn.digitalpublishing.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import cn.digitalpublishing.ep.po.PCcRelation;
import cn.digitalpublishing.ep.po.PPublications;

@Controller
@RequestMapping("/pages/ccRelation")
public class PCcRelationController extends BaseController {

	/**
	 * 数据接口
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(HttpServletRequest request, Model model){
		ResultObject<PCcRelation> result=null;
		try{ 
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); //1-insert 2-update
			Integer operNum  = Integer.valueOf(request.getParameter("operNum").toString());//0是首次访问，要先删除在插入；其他直接插入
			Converter<PCcRelation> converter=new Converter<PCcRelation>();
			PCcRelation obj=(PCcRelation)converter.json2Object(objJson, PCcRelation.class.getName());
			if(operNum<=0){
				//先删除
				Map<String, Object> condition = new HashMap<String,Object>();
				condition.put("collectionId", obj.getCollection().getId());
				this.pPublicationsService.deletePccRelaction(condition);
			}
			if("2".equals(operType)){
				this.pPublicationsService.updateCcRelation(obj,obj.getId(), null);
			}else if ("1".equals(operType)){
				this.pPublicationsService.insertCcRelation(obj);
			}else{
				//删除
			}
			ObjectUtil<PCcRelation> util=new ObjectUtil<PCcRelation>();
			obj=util.setNull(obj, new String[]{Set.class.getName(),List.class.getName()});
			result=new ResultObject<PCcRelation>(1,obj,Lang.getLanguage("Controller.PCcRelation.insert.manage.success",request.getSession().getAttribute("lang").toString()));//"产品包与产品信息维护成功！");//"产品包与产品信息维护成功！");
			if(obj.getPublications()!=null){
				ObjectUtil<PPublications> util1=new ObjectUtil<PPublications>();
				PPublications parent = obj.getPublications();
				parent=util1.setNull(parent, new String[]{Set.class.getName(),List.class.getName()});
				parent.setPriceList(null);
				obj.setPublications(parent);
			}
		}catch(Exception e){
			result=new ResultObject<PCcRelation>(2,Lang.getLanguage("Controller.PCcRelation.insert.manage.error",request.getSession().getAttribute("lang").toString()));//"产品包与产品信息维护失败！");//"产品包与产品信息维护失败！");
		}
		model.addAttribute("target",result);
	}
	
}
