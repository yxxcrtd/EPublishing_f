package cn.digitalpublishing.springmvc.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ccsit.restful.tool.Converter;
import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.ResultObject;
import cn.com.daxtech.framework.util.ObjectUtil;
import cn.digitalpublishing.ep.po.BInstitution;

@Controller
@RequestMapping("/pages/institution")
public class BInstitutionController extends BaseController {

	/**
	 * 数据接口
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(HttpServletRequest request, Model model){
		ResultObject<BInstitution> result=null;
		try{
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); //1-insert 2-update
			Converter<BInstitution> converter=new Converter<BInstitution>();
			BInstitution obj=(BInstitution)converter.json2Object(objJson, BInstitution.class.getName());
			if("2".equals(operType)){
				obj.setLogo(null);
				obj.setLogoNote(null);
				obj.setLogoUrl(null);
				this.configureService.updateInstitution(obj,obj.getId(), null);
			}else if ("1".equals(operType)){
				this.configureService.insertInstitution(obj);
			}else{
				//删除
				this.configureService.deleteInstitution(obj.getId());
			}
			ObjectUtil<BInstitution> util=new ObjectUtil<BInstitution>();
			obj=util.setNull(obj, new String[]{Set.class.getName()});
			result=new ResultObject<BInstitution>(1,obj,Lang.getLanguage("institution.info.maintain.success",request.getSession().getAttribute("lang").toString()));//"机构信息维护成功！");
		}catch(Exception e){
			result=new ResultObject<BInstitution>(2,(e instanceof CcsException) ? ((CcsException)e).getPrompt():"institution.info.maintain.error");//"机构信息维护失败！");
		}
		model.addAttribute("target",result);
	}
	
}
