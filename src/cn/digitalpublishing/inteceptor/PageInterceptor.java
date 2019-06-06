package cn.digitalpublishing.inteceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.com.daxtech.framework.util.StringUtil;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.util.web.IpUtil;

public class PageInterceptor extends ActionInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

		//查询Page信息
		String publicationsId = request.getAttribute("publicationsId") == null ? null : (String) request.getAttribute("publicationsId");
		if (publicationsId != null) {
			String pagesId = request.getAttribute("pagesId") == null ? null : (String) request.getAttribute("pagesId");
			//查询License
			String licenseId = request.getParameter("licenseId") == null ? null : request.getParameter("licenseId");
			CUser user = null;
			if (request.getSession().getAttribute("mainUser") != null) {
				user = (CUser) request.getSession().getAttribute("mainUser");
			}
			BInstitution institution = null;
			if (request.getSession().getAttribute("institution") != null) {
				institution = (BInstitution) request.getSession().getAttribute("institution");
			} else {
				if (user != null) {
					institution = user.getInstitution() == null ? null : user.getInstitution();
				}
			}

			//由于以前查询太慢现在修改了表结构。2013-9-3 李方华
			//统计查询改为线程每天将统计结果保存到统计结果表中，日志改回每次访问插入一条新数据。2013-11-13 蒋凯
			//先查询是否存在
			/*
			 * Map<String,Object> condition = new HashMap<String,Object>();
			 * condition.put("type", 2);//类型 condition.put("year",
			 * StringUtil.formatDate(new Date(),"yyyy")); // 年份
			 * condition.put("institutionId",
			 * institution==null?null:institution.getId());//机构ID
			 * condition.put("userId", user==null?null:user.getId());//用户Id
			 * condition.put("pubId", publicationsId);//产品Id List<LAccess>
			 * lalist =
			 * this.serviceFactory.getLogAOPService().isExistLog(condition);
			 * if(lalist!=null&&lalist.size()==1){ //如果存在，直接更新 LAccess laccess =
			 * this.serviceFactory.getLogAOPService().getLogInfo(lalist.get(0).
			 * getId()); laccess.setCreateOn(new Date()); Integer yue =
			 * Integer.valueOf(StringUtil.formatDate(new Date(),"MM")); switch
			 * (yue) { case 1:{
			 * laccess.setMonth1(laccess.getMonth1()==null?1:(laccess.getMonth1(
			 * )+1)); break; } case 2:{
			 * laccess.setMonth2(laccess.getMonth2()==null?1:(laccess.getMonth2(
			 * )+1)); break; } case 3:{
			 * laccess.setMonth3(laccess.getMonth3()==null?1:(laccess.getMonth3(
			 * )+1)); break; } case 4:{
			 * laccess.setMonth4(laccess.getMonth4()==null?1:(laccess.getMonth4(
			 * )+1)); break; } case 5:{
			 * laccess.setMonth5(laccess.getMonth5()==null?1:(laccess.getMonth5(
			 * )+1)); break; } case 6:{
			 * laccess.setMonth6(laccess.getMonth6()==null?1:(laccess.getMonth6(
			 * )+1)); break; } case 7:{
			 * laccess.setMonth7(laccess.getMonth7()==null?1:(laccess.getMonth7(
			 * )+1)); break; } case 8:{
			 * laccess.setMonth8(laccess.getMonth8()==null?1:(laccess.getMonth8(
			 * )+1)); break; } case 9:{
			 * laccess.setMonth9(laccess.getMonth9()==null?1:(laccess.getMonth9(
			 * )+1)); break; } case 10:{
			 * laccess.setMonth10(laccess.getMonth10()==null?1:(laccess.
			 * getMonth10()+1)); break; } case 11:{
			 * laccess.setMonth11(laccess.getMonth11()==null?1:(laccess.
			 * getMonth11()+1)); break; } case 12:{
			 * laccess.setMonth12(laccess.getMonth12()==null?1:(laccess.
			 * getMonth12()+1)); break; } default:{
			 * laccess.setMonth1(laccess.getMonth1()==null?1:(laccess.getMonth1(
			 * )+1)); break; } }
			 * this.serviceFactory.getLogAOPService().updateLogInfo(laccess,
			 * laccess.getId(),null); }else if(lalist!=null && lalist.size()>1){
			 * Integer[] monthSum=new Integer[]{0,0,0,0,0,0,0,0,0,0,0,0};
			 * List<LAccess> laalist =
			 * this.serviceFactory.getLogAOPService().getLogOfYear3(condition,
			 * " order by a.createOn desc "); for(Integer i
			 * =0;i<laalist.size();i++){
			 * monthSum[0]+=laalist.get(i).getMonth1()==null?0:laalist.get(i).
			 * getMonth1();
			 * monthSum[1]+=laalist.get(i).getMonth2()==null?0:laalist.get(i).
			 * getMonth2();
			 * monthSum[2]+=laalist.get(i).getMonth3()==null?0:laalist.get(i).
			 * getMonth3();
			 * monthSum[3]+=laalist.get(i).getMonth4()==null?0:laalist.get(i).
			 * getMonth4();
			 * monthSum[4]+=laalist.get(i).getMonth5()==null?0:laalist.get(i).
			 * getMonth5();
			 * monthSum[5]+=laalist.get(i).getMonth6()==null?0:laalist.get(i).
			 * getMonth6();
			 * monthSum[6]+=laalist.get(i).getMonth7()==null?0:laalist.get(i).
			 * getMonth7();
			 * monthSum[7]+=laalist.get(i).getMonth8()==null?0:laalist.get(i).
			 * getMonth8();
			 * monthSum[8]+=laalist.get(i).getMonth9()==null?0:laalist.get(i).
			 * getMonth9();
			 * monthSum[9]+=laalist.get(i).getMonth10()==null?0:laalist.get(i).
			 * getMonth10();
			 * monthSum[10]+=laalist.get(i).getMonth11()==null?0:laalist.get(i).
			 * getMonth11();
			 * monthSum[11]+=laalist.get(i).getMonth12()==null?0:laalist.get(i).
			 * getMonth12(); if(i<laalist.size()-1){
			 * this.serviceFactory.getLogAOPService().deleteAccess(laalist.get(i
			 * ).getId()); }else{ Integer yue =
			 * Integer.valueOf(StringUtil.formatDate(new Date(),"MM"));
			 * monthSum[yue-1]++; laalist.get(i).setMonth1(monthSum[0]);
			 * laalist.get(i).setMonth2(monthSum[1]);
			 * laalist.get(i).setMonth3(monthSum[2]);
			 * laalist.get(i).setMonth4(monthSum[3]);
			 * laalist.get(i).setMonth5(monthSum[4]);
			 * laalist.get(i).setMonth6(monthSum[5]);
			 * laalist.get(i).setMonth7(monthSum[6]);
			 * laalist.get(i).setMonth8(monthSum[7]);
			 * laalist.get(i).setMonth9(monthSum[8]);
			 * laalist.get(i).setMonth10(monthSum[9]);
			 * laalist.get(i).setMonth11(monthSum[10]);
			 * laalist.get(i).setMonth12(monthSum[11]);
			 * this.serviceFactory.getLogAOPService().updateLogInfo(laalist.get(
			 * i), laalist.get(i).getId(),null); } }
			 * 
			 * }else{
			 */
			LAccess access = new LAccess();
			access.setInstitutionId(institution == null ? null : institution.getId());
			access.setAccess(1);//访问状态1-访问成功 2-访问拒绝
			access.setType(2);//操作类型1-访问摘要 2-访问内容 3-检索
			access.setCreateOn(new Date());
			access.setIp(IpUtil.getIp(request));
			access.setPlatform("CNPe");
			access.setYear(StringUtil.formatDate(access.getCreateOn(), "yyyy"));
			access.setMonth(StringUtil.formatDate(access.getCreateOn(), "MM"));
			access.setUserId(user == null ? null : user.getId());
			PPublications publications = new PPublications();
			publications.setId(publicationsId);
			access.setPublications(publications);
			if (licenseId != null && !"".equals(licenseId)) {
				LLicense license = new LLicense();
				license.setId(licenseId);
				access.setLicense(license);
			}
			access.setPageId(pagesId);
			Integer yue = Integer.valueOf(StringUtil.formatDate(new Date(), "MM"));
			switch (yue) {
			case 1: {
				access.setMonth1(access.getMonth1() == null ? 1 : (access.getMonth1() + 1));
				break;
			}
			case 2: {
				access.setMonth2(access.getMonth2() == null ? 1 : (access.getMonth2() + 1));
				break;
			}
			case 3: {
				access.setMonth3(access.getMonth3() == null ? 1 : (access.getMonth3() + 1));
				break;
			}
			case 4: {
				access.setMonth4(access.getMonth4() == null ? 1 : (access.getMonth4() + 1));
				break;
			}
			case 5: {
				access.setMonth5(access.getMonth5() == null ? 1 : (access.getMonth5() + 1));
				break;
			}
			case 6: {
				access.setMonth6(access.getMonth6() == null ? 1 : (access.getMonth6() + 1));
				break;
			}
			case 7: {
				access.setMonth7(access.getMonth7() == null ? 1 : (access.getMonth7() + 1));
				break;
			}
			case 8: {
				access.setMonth8(access.getMonth8() == null ? 1 : (access.getMonth8() + 1));
				break;
			}
			case 9: {
				access.setMonth9(access.getMonth9() == null ? 1 : (access.getMonth9() + 1));
				break;
			}
			case 10: {
				access.setMonth10(access.getMonth10() == null ? 1 : (access.getMonth10() + 1));
				break;
			}
			case 11: {
				access.setMonth11(access.getMonth11() == null ? 1 : (access.getMonth11() + 1));
				break;
			}
			case 12: {
				access.setMonth12(access.getMonth12() == null ? 1 : (access.getMonth12() + 1));
				break;
			}
			default: {
				access.setMonth1(access.getMonth1() == null ? 1 : (access.getMonth1() + 1));
				break;
			}
			}
			this.serviceFactory.getLogAOPService().addLog(access);
			//}
		}
		System.out.println("Page拦截器结束。。。。");
		// TODO Auto-generated method stub  
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView mv) throws Exception {
		System.out.println("Page拦截器工作。。。。");
		String s = "------- Inteceptor --------: HelloWord !";
		System.out.println(s);
		//		mv.addObject("intep",s);  
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		// TODO Auto-generated method stub  
		System.out.println("Page拦截器开启。。。。");
		return true;
	}
}
