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
import cn.digitalpublishing.ep.po.OOrder;
import cn.digitalpublishing.ep.po.PPrice;
import cn.digitalpublishing.ep.po.PPriceType;
import cn.digitalpublishing.ep.po.PPublications;

@Controller
@RequestMapping("/pages/price")
public class PPriceController extends BaseController {

	/**
	 * 数据接口
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(HttpServletRequest request, Model model){
		ResultObject<PPrice> result=null;
		try{ 
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); //1-insert 2-update
//			int operNum = Integer.valueOf(request.getParameter("operNum").toString());
			
			Converter<PPrice> converter=new Converter<PPrice>();
			PPrice obj=(PPrice)converter.json2Object(objJson, PPrice.class.getName());
			//2012-12-13由于价格和订单明细关联，所以不能进行删除
//			if(operNum<=0){
//				//删除 //2012-12-7 目前一个商品只有一个价格，所以先删除全部价格，在插入
//				Map<String,Object> condition = new HashMap<String,Object>();
//				condition.put("publicationsid", obj.getPublications().getId());
//				this.pPublicationsService.deletePrice(condition);
//			}
			if("2".equals(operType)){
				this.pPublicationsService.updatePrice(obj,obj.getId(), null);
			}else if ("1".equals(operType)){
				//插入
				//根据产品ID查询价格信息
//				Map<String,Object> map = new HashMap<String,Object>();
//				map.put("publicationsid", obj.getPublications().getId());
//				PPrice p = this.pPublicationsService.getPriceByCondition(map);
//				if(p==null){
					this.pPublicationsService.insertPrice(obj);
//				}else{
//					obj.setId(p.getId());
//					this.pPublicationsService.updatePrice(obj,p.getId(), null);
//				}
			}else{
				//删除
				this.pPublicationsService.deletePrice(obj.getId());
			}
			ObjectUtil<PPrice> util=new ObjectUtil<PPrice>();
			obj=util.setNull(obj, new String[]{Set.class.getName(),List.class.getName()});
			if(obj.getPublications()!=null){
				ObjectUtil<PPublications> util1=new ObjectUtil<PPublications>();
				util1.setNull(obj.getPublications(),new String[]{Set.class.getName(),List.class.getName()});
			}
			if(obj.getPriceType()!=null){
				ObjectUtil<PPriceType> util2=new ObjectUtil<PPriceType>();
				util2.setNull(obj.getPriceType(),new String[]{Set.class.getName(),List.class.getName()});
			}
			result=new ResultObject<PPrice>(1,obj,Lang.getLanguage("Controller.PPrice.insert.manage.success",request.getSession().getAttribute("lang").toString()));//"价格信息维护成功！");//"出版商信息维护成功！");
		}catch(Exception e){
			//e.printStackTrace();
			result=new ResultObject<PPrice>(2,Lang.getLanguage("Controller.PPrice.insert.manage.error",request.getSession().getAttribute("lang").toString()));//"价格信息维护失败！");//"出版商信息维护失败！");
		}
		model.addAttribute("target",result);
	}
	
	
	/**
	 * 数据接口
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/deleteAllPrice", method = RequestMethod.POST)
	public void deleteAll(HttpServletRequest request, Model model){
		ResultObject<PPublications> result=null;
		try{ 
			String operType = request.getParameter("operType").toString(); //1-delete
			String objJson = request.getParameter("publications").toString();
			Converter<cn.digitalpublishing.ep.po.PPublications> converter=new Converter<cn.digitalpublishing.ep.po.PPublications>();
			PPublications obj=(PPublications)converter.json2Object(objJson, PPublications.class.getName());
			if ("1".equals(operType)){
				//删除
				Map<String,Object> condition = new HashMap<String,Object>();
				condition.put("publicationsid",obj.getId());
				condition.put("del", "1");
				this.pPublicationsService.deleteAllPrice(condition);
			}
			
			result=new ResultObject<PPublications>(1,Lang.getLanguage("Controller.PPrice.insert.manage.success",request.getSession().getAttribute("lang").toString()));//"价格信息维护成功！");//"出版商信息维护成功！");
		}catch(Exception e){
			e.printStackTrace();
			result=new ResultObject<PPublications>(2,Lang.getLanguage("Controller.PPrice.insert.manage.error",request.getSession().getAttribute("lang").toString()));//"价格信息维护失败！");//"出版商信息维护失败！");
		}
		model.addAttribute("target",result);
	}
	
	@RequestMapping(value = "/syncPrice", method = RequestMethod.POST)
	public void syncPrice(HttpServletRequest request, Model model){
		ResultObject<PPrice> result=null;
		try{ 
			String list = request.getParameter("obj").toString();
			Converter<PPrice> converter=new Converter<PPrice>();
			PPrice p=(PPrice)converter.json2Object(list, PPrice.class.getName());
				Map<String,Object> condition = new HashMap<String,Object>();
				condition.put("priceId",p.getId());
				//condition.put("status", p.getStatus());
//				condition.put("del", "1");
				//存在-更新,不存在-添加
				if(this.pPublicationsService.getPrice(condition) != null)
				{
					this.pPublicationsService.updatePrice(p,p.getId(),null);
				}else{
					this.pPublicationsService.insertPrice(p);
				}
				ObjectUtil<PPrice> util=new ObjectUtil<PPrice>();
				p=util.setNull(p, new String[]{Set.class.getName(),List.class.getName()});
				if(p.getPublications()!=null){
					ObjectUtil<PPublications> util1=new ObjectUtil<PPublications>();
					util1.setNull(p.getPublications(),new String[]{Set.class.getName(),List.class.getName()});
				}
				if(p.getPriceType()!=null){
					ObjectUtil<PPriceType> util2=new ObjectUtil<PPriceType>();
					util2.setNull(p.getPriceType(),new String[]{Set.class.getName(),List.class.getName()});
				}
			result=new ResultObject<PPrice>(1,p,Lang.getLanguage("Controller.PPrice.insert.manage.success",request.getSession().getAttribute("lang").toString()));//"价格信息维护成功！"
		}catch(Exception e){
			e.printStackTrace();
			result=new ResultObject<PPrice>(2,Lang.getLanguage("Controller.PPrice.insert.manage.error",request.getSession().getAttribute("lang").toString()));//"价格信息维护失败！"
		}
		model.addAttribute("target",result);
	}
}
