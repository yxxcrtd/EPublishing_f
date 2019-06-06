package cn.digitalpublishing.springmvc.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.ResultObject;
import cn.com.daxtech.framework.util.ObjectUtil;

import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.BPublisher;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.ep.po.RRecommend;
import cn.digitalpublishing.ep.po.RRecommendDetail;
import cn.digitalpublishing.springmvc.form.custom.RRecommendForm;
import cn.digitalpublishing.springmvc.form.favourites.FavouritesForm;


@Controller
@RequestMapping("/pages/recommend")
public class RecommendController  extends BaseController {

	@RequestMapping(value="/form/edit")
	public ModelAndView favoritesDelete( HttpServletRequest request,HttpServletResponse response,HttpSession session, RRecommendForm form)throws Exception {
		String forwardString="frame/recommend";
		Map<String,Object> model = new HashMap<String,Object>();
		try{
			PPublications pub = this.pPublicationsService.getPublications(form.getPubid());
			form.getObj().setPublications(pub);
			CUser user = session.getAttribute("recommendUser")==null?new CUser():(CUser)session.getAttribute("recommendUser");
			form.setRecommendUser(user);
			model.put("form", form);
		}catch(Exception e){
			e.printStackTrace();
            request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
		return new ModelAndView(forwardString,model);
	}
	
		@RequestMapping(value="/form/submit")
		public void submit(HttpServletRequest request,HttpServletResponse response,HttpSession session, RRecommendForm form)throws Exception {
			String result;
			try{
				boolean msg = (Boolean) (request.getAttribute("msg")==null?true:request.getAttribute("msg"));
				if(msg){
					CUser mainUser=(CUser) session.getAttribute("mainUser");
					BInstitution ins=(BInstitution)session.getAttribute("institution");
					if(ins==null && mainUser!=null){
						ins=mainUser.getInstitution();
					}
				if(ins!=null){//当前IP在机构IP范围内
					
					
					CUser user= session.getAttribute("recommendUser")==null?null:((CUser)session.getAttribute("recommendUser"));
					
					if (user!=null){//取得了荐购用户
						PPublications pub=this.pPublicationsService.getPublications(form.getPubid());
						if(pub !=null){
							int count=0;
							//荐购用户若不是默认用户，需检查其是否曾向该图书馆推荐过该书
							if(request.getSession().getAttribute("ipUserId")==null || !user.getId().equals(request.getSession().getAttribute("ipUserId")))
							{
								Map<String,Object> condition= new HashMap<String,Object>();
								condition.put("pubId",pub.getId());
								condition.put("insId", ins.getId());
								condition.put("userId",user.getId());
								condition.put("isorder2",3);
								count=this.rRecommendService.getRecommendDetailCount(condition);
							}
							//List<RRecommendDetail> listRRD=this.rRecommendService.getRecommendDetailList(condition);
							//用户未向某机构推荐过某书，或
							if(count==0){
								RRecommend c=new RRecommend();	
								c.setName(pub.getTitle());
								c.setPublications(pub);
								c.setInstitution(ins);
								c.setIsOrder(1);
								c.setCreatedby(user.getName());
								c.setCreatedon(new Date());
								c.setUpdatedby(user.getName());
								c.setUpdatedon(new Date());
								c.setRecommendDetailCount(1);
								c.setProCount(user.getLevel()==5?1:0);
								
								RRecommendDetail d=new RRecommendDetail();
								d.setUser(user);
								d.setRecommend(c);
								d.setRemark(form.getNote());
								d.setCreatedby(user.getName());
								d.setCreatedon(new Date());
								
								Map<String,Object> condition=new HashMap<String,Object>();
								condition.put("publicationsid",pub.getId());
								condition.put("institutionid", ins.getId());
								condition.put("isorder","1");
								this.rRecommendService.insertRecommend(c,d, condition);
								
								result="success:" +Lang.getLanguage("recommend.promp.submit.success",request.getSession().getAttribute("lang").toString());//提交成功
							}else{
								result="error:" +Lang.getLanguage("recommend.promp.submit.error.repeat",request.getSession().getAttribute("lang").toString());//重复推荐
							}
						}else{
							result="error:" +Lang.getLanguage("recommend.promp.submit.error.pubiderror",request.getSession().getAttribute("lang").toString());//出版物ID不存在
						}
					}else{
						result="error:" +Lang.getLanguage("recommend.promp.submit.error.notlogin",request.getSession().getAttribute("lang").toString());//未登录
					}
				}else{
					result="error:" +Lang.getLanguage("recommend.promp.submit.error.outrange",request.getSession().getAttribute("lang").toString());//不在IP范围中
				}
				}else{
					result="error:" +Lang.getLanguage("Conteoller.Global.prompt.info",request.getSession().getAttribute("lang").toString());//保存失败
				}
			}catch(Exception e){
				System.out.println("==Recommend==");
				e.printStackTrace();
				result="error:" + ((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			}
			try {
			      response.setContentType("text/html;charset=UTF-8");
				  PrintWriter out = response.getWriter();
				  out.print(result);
				  out.flush();
				  out.close();
		    } catch (Exception e) {
		    	e.printStackTrace();
		    	form.setMsg((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		    }
		}
		@RequestMapping(value="/form/suspend")
		public void suspend(HttpServletRequest request,HttpServletResponse response, RRecommendForm form)throws Exception {
			String result = "error:"+Lang.getLanguage("Global.Prompt.set.error", request.getSession().getAttribute("lang").toString());
			try{
				this.rRecommendService.suspendRecommend(form.getId(),form.getParticulars());
				result = "success:"+Lang.getLanguage("Global.Prompt.set.success", request.getSession().getAttribute("lang").toString());
			}catch(Exception e){
				result="error:" + ((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			}
			try {
			      response.setContentType("text/html;charset=UTF-8");
				  PrintWriter out = response.getWriter();
				  out.print(result);
				  out.flush();
				  out.close();
		    } catch (Exception e) {
		    	e.printStackTrace();
		    	form.setMsg((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		    }
		}
		
		@RequestMapping(value = "/getRDetailLogForPublisher/{sourceId}/{year}/{startMonth}/{endMonth}")
		public void getRDetailLogForPublisher(@PathVariable String sourceId,@PathVariable String year,@PathVariable String startMonth,@PathVariable String endMonth, HttpServletRequest request, Model model){
			ResultObject<RRecommendDetail> result=null;
			try{
				Map<String,Object> condition = new HashMap<String,Object>();
				condition.put("sourceId",sourceId);
				condition.put("year",year);
				condition.put("startMonth",startMonth);
				condition.put("endMonth",endMonth);
				List<RRecommendDetail> list=null;
				Boolean paging = Boolean.valueOf((request.getParameter("Paging")==null?"false":(!"false".equalsIgnoreCase(request.getParameter("Paging").toString())&&!"true".equalsIgnoreCase(request.getParameter("Paging").toString())?"false":request.getParameter("Paging").toString())));
				
				if(paging){
					int pageCount = 10 ;
					int curpage = 0 ;
					pageCount = request.getParameter("pageCount")==null?10:Integer.valueOf(request.getParameter("pageCount").toString());
					curpage = request.getParameter("curpage")==null?10:Integer.valueOf(request.getParameter("curpage").toString());
					list=this.logAOPService.getCounterPagingForPublisher(condition, "", pageCount, curpage);
				}else{
					if(request.getParameter("Paging")!=null&&"count".equalsIgnoreCase(request.getParameter("Paging").toString())){
						list = this.logAOPService.getCounterCountForPublisher(condition);
						if(list == null||list.isEmpty()){
							list = new ArrayList<RRecommendDetail>();
							RRecommendDetail detail = new RRecommendDetail();
							detail.setId("0");
							list.add(detail);
						}else{
							int num = list.size();
							list = new ArrayList<RRecommendDetail>();
							RRecommendDetail detail = new RRecommendDetail();
							detail.setId(String.valueOf(num));
							list.add(detail);
						}
					}else{
						list = this.logAOPService.getCounterForPublisher(condition,"");
					}
				}				
				
				if(list!=null&&!list.isEmpty()){
					ObjectUtil<RRecommendDetail> util=new ObjectUtil<RRecommendDetail>();
					ObjectUtil<RRecommend> rutil=new ObjectUtil<RRecommend>();
					ObjectUtil<PPublications> putil=new ObjectUtil<PPublications>();
					ObjectUtil<BPublisher> bputil=new ObjectUtil<BPublisher>();
					ObjectUtil<BInstitution> biutil=new ObjectUtil<BInstitution>();
					
					for(int i=0;i<list.size();i++){						
						util.setNull(list.get(i), new String[]{Set.class.getName(),List.class.getName()});
						if(list.get(i).getRecommend()!=null){
							rutil.setNull(list.get(i).getRecommend(), new String[]{Set.class.getName(),List.class.getName()});
							if(list.get(i).getRecommend().getPublications()!=null){
								putil.setNull(list.get(i).getRecommend().getPublications(), new String[]{Set.class.getName(),List.class.getName()});
								if(list.get(i).getRecommend().getPublications().getPublisher()!=null){
									bputil.setNull(list.get(i).getRecommend().getPublications().getPublisher(), new String[]{Set.class.getName(),List.class.getName()});
								}
							}
							if(list.get(i).getRecommend().getInstitution()!=null){
								biutil.setNull(list.get(i).getRecommend().getInstitution(), new String[]{Set.class.getName(),List.class.getName()});
							}
						}	
					}
				}
				result=new ResultObject<RRecommendDetail>(1,list,Lang.getLanguage("Controller.User.getLog.query.success",request.getSession().getAttribute("lang").toString()));//"获取订单详情列表成功！");
			}catch(Exception e){
				e.printStackTrace();
				result=new ResultObject<RRecommendDetail>(2,(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			}
			model.addAttribute("target",result);
		}
}
