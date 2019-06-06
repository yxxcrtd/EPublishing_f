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
import cn.digitalpublishing.ep.po.PCsRelation;
import cn.digitalpublishing.ep.po.PPublications;

@Controller
@RequestMapping("/pages/csRelation")
public class PCsRelationController extends BaseController {

	/**
	 * 数据接口
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(HttpServletRequest request, Model model){
		ResultObject<PCsRelation> result=null;
		try{ 
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); //1-insert 2-update
			Integer operNum = Integer.valueOf(request.getParameter("operNum").toString()); //0是首次传入，要删除全部信息；其他，直接插入
			Converter<PCsRelation> converter=new Converter<PCsRelation>();
			PCsRelation obj=(PCsRelation)converter.json2Object(objJson, PCsRelation.class.getName());
			if(operNum<=0){
				//删除全部数据
				Map<String,Object> condition = new HashMap<String,Object>();
				condition.put("publicationsId",obj.getPublications().getId());
				condition.put("mainCode","no");
				this.pPublicationsService.deletePcsRelation(condition);
			}
			if("2".equals(operType)){
				this.pPublicationsService.updateCsRelation(obj,obj.getId(), null);
			}else if ("1".equals(operType)){
				this.pPublicationsService.insertCsRelation(obj);
			}else{
				//删除
			}
			ObjectUtil<PCsRelation> util=new ObjectUtil<PCsRelation>();
			obj=util.setNull(obj, new String[]{Set.class.getName(),List.class.getName()});
			if(obj.getPublications()!=null){
				ObjectUtil<PPublications> util1=new ObjectUtil<PPublications>();
				PPublications parent = obj.getPublications();
				parent=util1.setNull(parent, new String[]{Set.class.getName(),List.class.getName()});
				parent.setPriceList(null);
				obj.setPublications(parent);
			}
			result=new ResultObject<PCsRelation>(1,obj,Lang.getLanguage("Controller.PCsRelation.insert.manage.success",request.getSession().getAttribute("lang").toString()));//"产品与分类信息维护成功！");//"出版商信息维护成功！");
		}catch(Exception e){
			result=new ResultObject<PCsRelation>(2,Lang.getLanguage("Controller.PCsRelation.insert.manage.error",request.getSession().getAttribute("lang").toString()));//"产品与分类信息维护失败！");//"出版商信息维护失败！");
		}
		model.addAttribute("target",result);
	}
}
