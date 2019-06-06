package cn.digitalpublishing.springmvc.controller;

import java.util.Date;
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
import cn.com.daxtech.framework.model.Param;
import cn.com.daxtech.framework.model.ResultObject;
import cn.com.daxtech.framework.util.ObjectUtil;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.BSubject;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.PCsRelation;
import cn.digitalpublishing.ep.po.PPrice;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.springmvc.form.configure.BSubjectForm;
import cn.digitalpublishing.springmvc.form.product.PPublicationsForm;
import cn.digitalpublishing.util.web.IpUtil;
import cn.digitalpublishing.util.web.MathHelper;

@Controller
@RequestMapping("/pages/subject")
public class BSubjectController extends BaseController {

	/**
	 * 按照分类查询产品
	 * @param subCode 用来查找子分类、all为全部父分类
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/form/list/{subCode}")
	public ModelAndView list(@PathVariable String subCode,HttpServletRequest request,HttpServletResponse response,BSubjectForm form)throws Exception {
		String forwardString="subject/detailList";
		Map<String,Object> model = new HashMap<String,Object>();
		try{
			CUser user = request.getSession()==null?null:(CUser)request.getSession().getAttribute("mainUser");
			subCode = subCode.toLowerCase();
			if(request.getParameter("pageCount")!=null){
				form.setPageCount(Integer.valueOf(request.getParameter("pageCount")));
			}
			if(request.getParameter("curpage")!=null){
				form.setCurpage(Integer.valueOf(request.getParameter("curpage")));
			}
			String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getRequestURI();
			int flag = 0;
			if(form.getType()!=null&&!form.getType().equals("")){
				if(flag == 0){
					url += "?type="+form.getType();
					flag=1;
				}else{
					url += "&type="+form.getType();
				}
			}
			if(form.getPublisherId()!=null&&!form.getPublisherId().equals("")){
				if(flag == 0){
					url += "?publisherid="+form.getPublisherId();
					flag=1;
				}else{
					url += "&publisherid="+form.getPublisherId();
				}
			}
			if(form.getSubParentId()!=null&&!form.getSubParentId().equals("")){
				if(flag == 0){
					url += "?subParentId="+form.getSubParentId();
					flag=1;
				}else{
					url += "&subParentId="+form.getSubParentId();
				}
			}
			if(form.getPubYear()!=null&&!form.getPubYear().equals("")){
				if(flag == 0){
					url += "?pubYear="+form.getPubYear();
					flag=1;
				}else{
					url += "&pubYear="+form.getPubYear();
				}
			}
			if(form.getIsLicense()!=null&&!form.getIsLicense().equals("")){
				if(flag == 0){
					url += "?isLicense="+form.getIsLicense();
					flag=1;
				}else{
					url += "&isLicense="+form.getIsLicense();
				}
			}
			form.setAzUrl(url);
			form.setUrl(request.getRequestURL().toString());
			model.put("code", subCode);
			if(form.getCode()==null||"".equals(form.getCode())){
				model.put("pCode", subCode);
			}else{
				model.put("pCode", form.getCode());
			}
			if(subCode.equalsIgnoreCase("all")&&form.getOrder()==null){
				forwardString="subject/list";
				Map<String,Object> condition = new HashMap<String, Object>();
				condition.put("treeCodeLength", 6);
				List<BSubject> list = this.bSubjectService.getSubList(condition, " order by a.order ");
				if(list!=null&&!list.isEmpty()){
					for(int i=0;i<list.size();i++){
						Map<String,Object> subcondition = new HashMap<String, Object>();
						subcondition.put("subTreeCode", list.get(i).getTreeCode());
						subcondition.put("pstatus",2);
						subcondition.put("pTypeArr",new Integer[]{1,2});
						list.get(i).setCountPP(this.bSubjectService.getSubPubCount(subcondition));
					}
				}
				model.put("list", list);
				model.put("form", form);
			}else{
				BSubject subject = this.bSubjectService.getSubjectByCode(subCode);
				if(subject!=null){
					form.setSubParentId(subject.getId());
					model.put("subName", subject.getName());
					model.put("subNameEn", subject.getNameEn());
					model.put("subjectCode", subject.getCode());
				}
			
				//查询子分类
				Map<String,Object> condition = new HashMap<String, Object>();
				if(form.getCode()==null||"".equals(form.getCode())){
					condition.put("length",6);
				}else{
					condition.put("length",9);
				}
				condition.put("publisherid",form.getPublisherId());
				condition.put("mainType","0");
				condition.put("subjectId", form.getSubParentId());
				condition.put("ptype",form.getType());
				condition.put("pubYear",form.getPubYear());
				condition.put("order", form.getOrder()==null?"":form.getOrder());
				condition.put("isLicense", form.getIsLicense());//是否订阅
				if(form.getIsLicense()==null||"".equals(form.getIsLicense())){
					condition.put("status", 2);
				}else{
					condition.put("check","false");
				}
				condition.put("userId", user==null?"":user.getId());//用户ID
				condition.put("ip", IpUtil.getIp(request));//用户ID
				
				condition.put("typeArr", new Integer[]{1,2});//只查询期刊和图书
				condition.put("level",2);
				condition.put("licenseStatus",1);
				String ins_Id = null;
				if(request.getSession().getAttribute("institution")!=null){
					ins_Id = ((BInstitution)request.getSession().getAttribute("institution")).getId();
				}
				condition.put("institutionId", ins_Id);
				condition.put("license", 1);
				List<PPublications> pubList = null;
				long starttime = System.currentTimeMillis();
				if(form.getIsLicense()==null||"".equals(form.getIsLicense())){
					form.setCount(this.pPublicationsService.getPubCount(condition));
					pubList = this.pPublicationsService.getPubPageList(condition, " order by a.title ", form.getPageCount(),form.getCurpage(),user,IpUtil.getIp(request).toString());
				}else{
					form.setCount(this.pPublicationsService.getPubSubscriptionCount(condition));
					pubList = this.pPublicationsService.getPubSubscriptionPageList(condition, " order by d.title ", form.getPageCount(),form.getCurpage(),user,IpUtil.getIp(request).toString());
//					pubList = this.pPublicationsService.getLicensePubPagingList(condition, " order by a.title ", form.getPageCount(),form.getCurpage(),user,IpUtil.getIp(request).toString());
				}
				long endtime = System.currentTimeMillis();
				System.out.println("XXXXXXXXXX Execute Time:"+(endtime-starttime));
//				condition.put("dtype", 5);//排除数据库
				
//				List<PPublications> pubList = this.pPublicationsService.getPubPageList2(condition, " order by a.title ", form.getPageCount(),form.getCurpage(),user,IpUtil.getIp(request).toString());
				//循环list 查询单个产品下边的价格列表
				if(pubList!=null){
					for(int i=0;i<pubList.size();i++){
						if(user!=null){
							Map<String,Object> con = new HashMap<String,Object>();
							con.put("publicationsid", pubList.get(i).getId());
							con.put("status", 2);
							con.put("userTypeId", user.getUserType().getId()==null?"":user.getUserType().getId());
							List<PPrice> price = this.pPublicationsService.getPriceList(con);
							int isFreeUser = request.getSession().getAttribute("isFreeUser")==null?0:(Integer)request.getSession().getAttribute("isFreeUser");
							if(isFreeUser!=1){
								for(int j=0;j<price.size();j++){
									PPrice pr = price.get(j);
									double endPrice = MathHelper.round(MathHelper.mul(pr.getPrice(), 1.13d));
									price.get(j).setPrice(endPrice);
								}
							}
							pubList.get(i).setPriceList(price);
						}
						//查询分类
	//					Map<String,Object> con2 = new HashMap<String,Object>();
	//					con2.put("publicationsId", pubList.get(i).getId());
	//					List<PCsRelation> csList = this.bSubjectService.getSubPubList(con2, " order by a.subject.code ");
	//					pubList.get(i).setCsList(csList);
					}
				}
				form.setCurCount(pubList!=null?pubList.size():0);
//				form.setCount(this.pPublicationsService.getPubCount(condition));
				model.put("pubList", pubList);
				model.put("form", form);
				model.put("order", (form.getOrder()==null||"".equals(form.getOrder()))?"ALL":form.getOrder());
			}

			//类型统计
			Map<String,Object> typeCondition = new HashMap<String,Object>();
			typeCondition.put("publisherid",form.getPublisherId());
			typeCondition.put("mainType","0");
			typeCondition.put("subjectId", form.getSubParentId());
			typeCondition.put("typeArr",new Integer[]{1,2});
			typeCondition.put("pubYear",form.getPubYear());
			typeCondition.put("order", form.getOrder()==null?"":form.getOrder());
			typeCondition.put("isLicense", form.getIsLicense());//是否订阅
			typeCondition.put("userId", user==null?"":user.getId());//用户ID
			typeCondition.put("ip", IpUtil.getIp(request));//用户ID
			typeCondition.put("type",form.getType());
//			List<PPublications> typeStatistic = this.pPublicationsService.getTypeStatistic(typeCondition);
			typeCondition.put("typeArr", new Integer[]{1,2});//只查询期刊和图书
			typeCondition.put("level",2);
			typeCondition.put("licenseStatus",1);
			if(form.getIsLicense()==null||"".equals(form.getIsLicense())){
				typeCondition.put("status", 2);
			}else{
				typeCondition.put("check","false");
			}
			String ins_Id = null;
			if(request.getSession().getAttribute("institution")!=null){
				ins_Id = ((BInstitution)request.getSession().getAttribute("institution")).getId();
			}
			typeCondition.put("institutionId", ins_Id);
			List<PPublications> typeStatistic = null;
			if(form.getIsLicense()==null||"".equals(form.getIsLicense())){
				typeStatistic = this.pPublicationsService.getTypeStatistic(typeCondition);
			}else{
				typeStatistic = this.pPublicationsService.getSubscriptionTypeStatistic(typeCondition);
			}
			
			model.put("typeStatistic", typeStatistic);
			
			model.put("publisherid", form.getPublisherId());
			model.put("subjectId", form.getSubParentId());
			model.put("type", form.getType());
			model.put("pubYear", form.getPubYear());
			model.put("order", form.getOrder());
			model.put("r_", new Date().getTime());
		}catch(Exception e){e.printStackTrace();
			form.setMsg((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		return new ModelAndView(forwardString, model);
	}
	
	/**
	 * 按照分类查询产品
	 * @param subCode 用来查找子分类、all为全部父分类
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/form/list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,BSubjectForm form)throws Exception {
		String forwardString="publications/detailList";
		Map<String,Object> model = new HashMap<String,Object>();
		try{
			CUser user = request.getSession()==null?null:(CUser)request.getSession().getAttribute("mainUser");
			if(form.getLcense()==null){
				form.setLcense(request.getSession().getAttribute("selectType")==null?"":request.getSession().getAttribute("selectType").toString());
			}
			request.getSession().setAttribute("selectType",form.getLcense()==null?"":form.getLcense());
			String subCode = form.getpCode();
			if(subCode!=null){
				subCode = subCode.toLowerCase();
			}
			form.setUrl(request.getRequestURL().toString());
			model.put("pCode", subCode);
			if(form.getCode()==null||"".equals(form.getCode())){
				model.put("code", subCode);
			}else{
				model.put("code", form.getCode());
			}
			BSubject subject = this.bSubjectService.getSubjectByCode(subCode);
			if(!"1".equals(form.getParentTaxonomy())&&!"1".equals(form.getParentTaxonomyEn())&&(form.getCode()==null||"".equals(form.getCode()))){
				if(subject!=null){
					//保存Parent分类
					form.setParentTaxonomy(subject.getCode()+" "+subject.getName().replace("\n", "").replace("\t", ""));
					form.setParentTaxonomyEn(subject.getCode()+" "+subject.getNameEn().replace("\n", "").replace("\t", ""));
				}
			}else if(!"1".equals(form.getParentTaxonomy())&&!"1".equals(form.getParentTaxonomyEn())){
				//保存Parent分类
				BSubject sub = this.bSubjectService.getSubjectByCode(model.get("code").toString().trim());
				if(sub!=null){
					form.setParentTaxonomy(sub.getCode()+" "+sub.getName().replace("\n", "").replace("\t", ""));
					form.setParentTaxonomyEn(sub.getCode()+" "+sub.getNameEn().replace("\n", "").replace("\t", ""));
				}
			}else{
				form.setParentTaxonomy("");
				form.setParentTaxonomyEn("");
			}
			if(subject!=null){
				form.setSubParentId(subject.getId());
				model.put("subName", subject.getName());
				model.put("subNameEn", subject.getNameEn());
				model.put("subjectCode", subject.getCode());
			}
		
			//查询子分类
			Map<String,Object> condition = new HashMap<String, Object>();
			if(form.getCode()==null||"".equals(form.getCode())){
				condition.put("length",6);
			}else{
				condition.put("length",9);
			}
			condition.put("publisherid",form.getPublisherId());
			condition.put("mainType","0");
			condition.put("subjectCode", subject!=null?subject.getTreeCode():"");
			condition.put("ptype",form.getType());
			condition.put("pubYear",form.getPubYear());
			condition.put("order", form.getSearchOrder()==null?"":form.getSearchOrder());
			if(form.getLcense()==null||"".equals(form.getLcense())){
				condition.put("status", 2);
			}else{
				condition.put("check","false");
			}
			condition.put("isLicense", form.getLcense()==null?"":form.getLcense());//是否订阅
			condition.put("userId", user==null?"":user.getId());//用户ID
			condition.put("ip", IpUtil.getIp(request));//用户ID
			
			condition.put("typeArr", new Integer[]{1,2});//只查询期刊和图书
			condition.put("level",2);
			condition.put("licenseStatus",1);
			String ins_Id = null;
			if(request.getSession().getAttribute("institution")!=null){
				ins_Id = ((BInstitution)request.getSession().getAttribute("institution")).getId();
			}
			condition.put("institutionId", ins_Id);
			List<PPublications> pubList = null;
			long starttime = System.currentTimeMillis();
			if(form.getLcense()==null||"".equals(form.getLcense())){
				form.setCount(this.pPublicationsService.getPubCountO1(condition));
				if(form.getCount()>0){
					pubList = this.pPublicationsService.getPubPageList(condition, " ", form.getPageCount(),form.getCurpage(),user,IpUtil.getIp(request).toString());
				}
			}else{
				//由于要查询出OA和免费
				condition.put("oafreeUid", Param.getParam("OAFree.uid.config").get("uid"));
				form.setCount(this.pPublicationsService.getPubSubscriptionCount(condition));
				if(form.getCount()>0){
					pubList = this.pPublicationsService.getPubSubscriptionPageList(condition, " ", form.getPageCount(),form.getCurpage(),user,IpUtil.getIp(request).toString());
				}
			}
			long endtime = System.currentTimeMillis();
			System.out.println("XXXXXXXXXX Execute Time:"+(endtime-starttime));
//				condition.put("dtype", 5);//排除数据库
			
//				List<PPublications> pubList = this.pPublicationsService.getPubPageList2(condition, " order by a.title ", form.getPageCount(),form.getCurpage(),user,IpUtil.getIp(request).toString());
			//循环list 查询单个产品下边的价格列表
			if(pubList!=null){
				for(int i=0;i<pubList.size();i++){
					if(user!=null){
						Map<String,Object> con = new HashMap<String,Object>();
						con.put("publicationsid", pubList.get(i).getId());
						con.put("status", 2);
						con.put("userTypeId", user.getUserType().getId()==null?"":user.getUserType().getId());
						List<PPrice> price = this.pPublicationsService.getPriceList(con);
						int isFreeUser = request.getSession().getAttribute("isFreeUser")==null?0:(Integer)request.getSession().getAttribute("isFreeUser");
						if(isFreeUser!=1){
							for(int j=0;j<price.size();j++){
								PPrice pr = price.get(j);
								double endPrice = MathHelper.round(MathHelper.mul(pr.getPrice(), 1.13d));
								price.get(j).setPrice(endPrice);
							}
						}
						pubList.get(i).setPriceList(price);
					}
				}
			}
			form.setCurCount(pubList!=null?pubList.size():0);
			model.put("pubList", pubList);
			
		}catch(Exception e){e.printStackTrace();
			form.setMsg((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}
	
	
	/**
	 * 数据接口
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(HttpServletRequest request, Model model){
		ResultObject<BSubject> result=null;
		try{
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); //1-insert 2-update
			Converter<BSubject> converter=new Converter<BSubject>();
			BSubject obj=(BSubject)converter.json2Object(objJson, BSubject.class.getName());
			if("2".equals(operType)){
				this.bSubjectService.updateSubject(obj,obj.getId(), null);
			}else if ("1".equals(operType)){
				this.bSubjectService.insertSubject(obj);
			}else{
				//删除
				this.bSubjectService.deleteSubject(obj.getId());
			}
			ObjectUtil<BSubject> util=new ObjectUtil<BSubject>();
			obj=util.setNull(obj, new String[]{Set.class.getName()});
			result=new ResultObject<BSubject>(1,obj,Lang.getLanguage("subject.info.maintain.success",request.getSession().getAttribute("lang").toString()));//"分类信息维护成功！");
		}catch(Exception e){
			result=new ResultObject<BSubject>(2,Lang.getLanguage("subject.info.maintain.error",request.getSession().getAttribute("lang").toString()));//"分类信息维护失败！");
		}
		model.addAttribute("target",result);
	}
	
	/**
	 * 首页按分类法统计forJquery异步加载
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/subjectAll")
	public ModelAndView subjectAll(HttpServletRequest request,HttpServletResponse response,PPublicationsForm form)throws Exception {
		String forwardString="index/index/subject_all";
		Map<String,Object> model = new HashMap<String,Object>();
		try{
			
			String subCode = form.getpCode();
			if(subCode!=null){
				subCode = subCode.toLowerCase();
			}
			model.put("pCode", subCode);
			if(form.getCode()==null||"".equals(form.getCode())){
				model.put("code", subCode);
			}else{
				model.put("code", form.getCode());
			}
			BSubject subject = this.bSubjectService.getSubjectByCode(subCode);
			if(!"1".equals(form.getParentTaxonomy())&&!"1".equals(form.getParentTaxonomyEn())&&(form.getCode()==null||"".equals(form.getCode()))){
				if(subject!=null){
					//保存Parent分类
					form.setParentTaxonomy(subject.getCode()+" "+subject.getName().replace("\n", "").replace("\t", ""));
					form.setParentTaxonomyEn(subject.getCode()+" "+subject.getNameEn().replace("\n", "").replace("\t", ""));
				}
			}else if(!"1".equals(form.getParentTaxonomy())&&!"1".equals(form.getParentTaxonomyEn())){
				//保存Parent分类
				BSubject sub = this.bSubjectService.getSubjectByCode(model.get("code").toString().trim());
				if(sub!=null){
					form.setParentTaxonomy(sub.getCode()+" "+sub.getName().replace("\n", "").replace("\t", ""));
					form.setParentTaxonomyEn(sub.getCode()+" "+sub.getNameEn().replace("\n", "").replace("\t", ""));
				}
			}else{
				form.setParentTaxonomy("");
				form.setParentTaxonomyEn("");
			}
			if(subject!=null){
				form.setSubParentId(subject.getId());
				model.put("subName", subject.getName());
				model.put("subNameEn", subject.getNameEn());
				model.put("subjectCode", subject.getCode());
			}
			
			form.setUrl(request.getRequestURL().toString());
			Map<String,Object> condition = new HashMap<String, Object>();
			condition.put("treeCodeLength", 6);
			List<BSubject> list = this.bSubjectService.getSubList(condition, " order by a.order ");
			condition = new HashMap<String, Object>();
			condition.put("pubStatus", 2);
			condition.put("pubType",new Integer[]{1,2});
			condition.put("mainCode","all");
			
			@SuppressWarnings("unused")
			List<PCsRelation> rlist = this.pPublicationsService.getSubStatistic(condition);
//			if(list!=null&&!list.isEmpty()){
//				for(int i=0;i<list.size();i++){
//					int num = 0;
//					String treeCode = list.get(i).getTreeCode();
//					for(PCsRelation rs:rlist){
//						String tCode = rs.getSubject().getTreeCode();
//						if(tCode.indexOf(treeCode)==0){
//							num = num +rs.getType();
//						}
//					}
//					list.get(i).setCountPP(num);
//				}
//			}
			model.put("list", list);
			model.put("form", form);
			
			model.put("parentTaxonomy", form.getParentTaxonomy());
			model.put("parentTaxonomyEn", form.getParentTaxonomyEn());
			model.put("subjectId", form.getSubParentId());
		}catch(Exception e){e.printStackTrace();
			form.setMsg((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		return new ModelAndView(forwardString, model);
	}
	
}
