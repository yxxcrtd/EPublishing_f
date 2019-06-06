package cn.digitalpublishing.inteceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.com.daxtech.framework.util.StringUtil;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;
import cn.digitalpublishing.util.web.IpUtil;

public class SearchInterceptor extends ActionInterceptor {

	private ServiceFactory serviceFactory;

	public SearchInterceptor() {
		this.serviceFactory = (ServiceFactory) new ServiceFactoryImpl();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
		List<PPublications> list = request.getAttribute("list") == null ? null : (List<PPublications>) request.getAttribute("list");
		String keyword = request.getParameter("searchValue");
		CUser user = null;
		if (request.getSession().getAttribute("mainUser") != null) {
			user = (CUser) request.getSession().getAttribute("mainUser");
		}
		String userId = user == null ? null : user.getId();
		BInstitution institution = null;
		if (request.getSession().getAttribute("institution") != null) {
			institution = (BInstitution) request.getSession().getAttribute("institution");
		} else {
			if (user != null) {
				institution = user.getInstitution() == null ? null : user.getInstitution();
			}
		}
		if (list != null && list.size() > 0) {
			for (PPublications pPublications : list) {
				//由于以前查询太慢现在修改了表结构。2013-9-3 李方华
				//先查询是否存在
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("type", 3);//类型
				condition.put("year", StringUtil.formatDate(new Date(), "yyyy")); // 年份
				condition.put("institutionId", institution == null ? null : institution.getId());//机构ID
				condition.put("userId", userId);//用户Id
				condition.put("pubId", pPublications.getId());//产品Id
				condition.put("keyword", keyword);
				List<LAccess> lalist = this.serviceFactory.getLogAOPService().isExistLog(condition);
				LAccess access = null;
				if (lalist != null && lalist.size() == 1) {
					//如果存在，直接更新
					access = this.serviceFactory.getLogAOPService().getLogInfo(lalist.get(0).getId());
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
					access.setCreateOn(new Date());
					this.serviceFactory.getLogAOPService().updateLogInfo(access, access.getId(), null);
				} else if (lalist != null && lalist.size() > 1) {
					Integer[] monthSum = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
					List<LAccess> laalist = this.serviceFactory.getLogAOPService().getLogOfYear3(condition, " order by a.createOn desc ");
					for (Integer i = 0; i < laalist.size(); i++) {
						monthSum[0] += laalist.get(i).getMonth1() == null ? 0 : laalist.get(i).getMonth1();
						monthSum[1] += laalist.get(i).getMonth2() == null ? 0 : laalist.get(i).getMonth2();
						monthSum[2] += laalist.get(i).getMonth3() == null ? 0 : laalist.get(i).getMonth3();
						monthSum[3] += laalist.get(i).getMonth4() == null ? 0 : laalist.get(i).getMonth4();
						monthSum[4] += laalist.get(i).getMonth5() == null ? 0 : laalist.get(i).getMonth5();
						monthSum[5] += laalist.get(i).getMonth6() == null ? 0 : laalist.get(i).getMonth6();
						monthSum[6] += laalist.get(i).getMonth7() == null ? 0 : laalist.get(i).getMonth7();
						monthSum[7] += laalist.get(i).getMonth8() == null ? 0 : laalist.get(i).getMonth8();
						monthSum[8] += laalist.get(i).getMonth9() == null ? 0 : laalist.get(i).getMonth9();
						monthSum[9] += laalist.get(i).getMonth10() == null ? 0 : laalist.get(i).getMonth10();
						monthSum[10] += laalist.get(i).getMonth11() == null ? 0 : laalist.get(i).getMonth11();
						monthSum[11] += laalist.get(i).getMonth12() == null ? 0 : laalist.get(i).getMonth12();
						if (i < laalist.size() - 1) {
							this.serviceFactory.getLogAOPService().deleteAccess(laalist.get(i).getId());
						} else {
							Integer yue = Integer.valueOf(StringUtil.formatDate(new Date(), "MM"));
							monthSum[yue - 1]++;
							laalist.get(i).setMonth1(monthSum[0]);
							laalist.get(i).setMonth2(monthSum[1]);
							laalist.get(i).setMonth3(monthSum[2]);
							laalist.get(i).setMonth4(monthSum[3]);
							laalist.get(i).setMonth5(monthSum[4]);
							laalist.get(i).setMonth6(monthSum[5]);
							laalist.get(i).setMonth7(monthSum[6]);
							laalist.get(i).setMonth8(monthSum[7]);
							laalist.get(i).setMonth9(monthSum[8]);
							laalist.get(i).setMonth10(monthSum[9]);
							laalist.get(i).setMonth11(monthSum[10]);
							laalist.get(i).setMonth12(monthSum[11]);
							this.serviceFactory.getLogAOPService().updateLogInfo(laalist.get(i), laalist.get(i).getId(), null);
						}
					}

				} else {
					access = new LAccess();
					access.setActivity(keyword);
					access.setAccess(1);//访问状态1-访问成功 2-访问拒绝
					access.setType(3);//操作类型1-访问摘要 2-访问内容 3-检索
					access.setCreateOn(new Date());
					access.setIp(IpUtil.getIp(request));
					access.setPlatform("CNPe");
					access.setYear(StringUtil.formatDate(access.getCreateOn(), "yyyy"));
					access.setMonth(StringUtil.formatDate(access.getCreateOn(), "MM"));
					access.setUserId(userId);
					access.setInstitutionId(institution == null ? null : institution.getId());
					PPublications publications = new PPublications();
					publications.setId(pPublications.getId());
					access.setPublications(publications);
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
				}
			}
		}
		//String s = "------- Inteceptor --------: Search !"+list.size()+keyword;  
		//System.out.println(s);  
		System.out.println("Search拦截器结束。。。。");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView mv) throws Exception {
		System.out.println("Search拦截器工作。。。。");
		String s = "------- Inteceptor --------: Search !";
		System.out.println(s);
		//		mv.addObject("intep",s);  
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		System.out.println("Search拦截器开启。。。。");
		return true;
	}

}
