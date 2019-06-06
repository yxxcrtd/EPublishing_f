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
import cn.digitalpublishing.ep.po.BIpRange;

@Controller
@RequestMapping("/pages/ipRange")
public class BIpRangeController extends BaseController {

	/**
	 * 数据接口
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(HttpServletRequest request, Model model){
		ResultObject<BIpRange> result=null;
		try{
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); //1-insert 2-update
			Converter<BIpRange> converter=new Converter<BIpRange>();
			BIpRange obj=(BIpRange)converter.json2Object(objJson, BIpRange.class.getName());
			if("2".equals(operType)){
				BIpRange ip = this.configureService.getIpRange(obj.getId());
				if(ip!=null){
					this.configureService.updateIpRange(obj,obj.getId(), null);
				}else{
					this.configureService.insertIpRange(obj);
				}
			}else if ("1".equals(operType)){
				//1.写入IP信息
				this.configureService.insertIpRange(obj);
			}else{
				//删除
				BIpRange ip = this.configureService.getIpRange(obj.getId());
				obj.setInstitution(ip.getInstitution());
				this.configureService.deleteIpRange(obj.getId());
			}
			//2.写入License信息 逻辑：先删除后新增，先删除拥有这个License的用户所在机构的所有产品的LicenseIP，然后再增加，如果用户没有机构则不处理。
			this.customService.handleLicenseIp(obj.getInstitution().getId());
			ObjectUtil<BIpRange> util=new ObjectUtil<BIpRange>();
			obj=util.setNull(obj, new String[]{Set.class.getName()});
			result=new ResultObject<BIpRange>(1,obj,Lang.getLanguage("ipRange.info.maintain.success",request.getSession().getAttribute("lang").toString()));//"IP范围信息维护成功！");
		}catch(Exception e){
			result=new ResultObject<BIpRange>(2,Lang.getLanguage("ipRange.info.maintain.error",request.getSession().getAttribute("lang").toString()));//"IP范围信息维护失败！");
		}
		model.addAttribute("target",result);
	}
}
