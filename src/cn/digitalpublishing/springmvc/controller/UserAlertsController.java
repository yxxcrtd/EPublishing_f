package cn.digitalpublishing.springmvc.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.Param;
import cn.digitalpublishing.ep.po.BSubject;
import cn.digitalpublishing.ep.po.CAlertsSendedQueue;
import cn.digitalpublishing.ep.po.PCsRelation;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;
import cn.digitalpublishing.springmvc.form.alerts.UserAlertsForm;

@Controller
@RequestMapping("/pages/alerts")
public class UserAlertsController  extends BaseController  {
	/**
	 * 跳转到管理员用户推送订阅提醒页面
	 * 管理员通过出版物ISBN号，与订阅相应主题学科的用户进行推送订阅信息
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/form/isbnConfirm")
	public ModelAndView isbnConfirm( HttpServletRequest request,HttpServletResponse response, UserAlertsForm form)throws Exception {
		String forwardString="alerts/isbnConfirm";
		Map<String,Object> model = new HashMap<String,Object>();
		try{
			form.setUrl(request.getRequestURL().toString());
			model.put("form", form);
		}catch(Exception e){
            request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		return new ModelAndView(forwardString, model);
	}
	
//	/**
//	 * 根据出版物ISBN号获取对应出版物详细信息
//	 * @param request
//	 * @param response
//	 * @param form
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/form/showPublication")
//	public ModelAndView showPublication( HttpServletRequest request,HttpServletResponse response, UserAlertsForm form)throws Exception {
//		Map<String,Object> model = new HashMap<String,Object>();
//		try{
//			String isbn = form.getCode()!=null?form.getCode().toString():"";
//			PPublications publication = this.pPublicationsService.getPPublicationsByISBN(isbn);
//			form.setUrl(request.getRequestURL().toString());
//			form.setPubTitle(publication.getTitle());
//			form.setPubType(publication.getType());
//			form.setSubName(publication.getPubSubject());
//			form.setIsbn(publication.getCode());
//			model.put("form", form);
//		}catch(Exception e){
//			//e.printStackTrace();
//            request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
//		}
//		return this.isbnConfirm(request, response, form);
//	}
	
	/**
	 * 根据出版物ISBN号获取对应出版物学科主题信息，向订阅该学科主题的用户，进行发送邮件提醒。
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/form/pushAlerts")
	public ModelAndView pushAlerts( HttpServletRequest request,HttpServletResponse response, UserAlertsForm form)throws Exception {
		Map<String,Object> model = new HashMap<String,Object>();
		String msg =Lang.getLanguage("Controller.Alerts.UserAlerts.pushAlerts.info.error", request.getSession().getAttribute("lang").toString());//请输入正确的ISBN编号信息!";
		try{
			//根据isbn获取出版物信息集合
			String [] isbns = form.getCode().split(",");
			if(isbns != null){
				for(String code:isbns){
					List<PPublications> publications = this.pPublicationsService.getPPublicationsByISBN(code);
					if(publications==null || publications.size()==0){
						form.setMsg(Lang.getLanguage("Controller.Alerts.UserAlerts.pushAlerts.info.error", request.getSession().getAttribute("lang").toString()));
						return this.isbnConfirm(request, response, form);
					}
					//根据出版物信息获取学科主题（分类法）信息
					for(PPublications publication:publications){
						Map<String,Object> condition = new HashMap<String,Object>();
						condition.put("publicationsId", publication.getId());
						List<PCsRelation> csRelations = this.bSubjectService.getSubPubList(condition, null);
						BSubject subject = null;
						if(csRelations!=null || csRelations.size()>0){
							subject = csRelations.get(0).getSubject();
						}
						//给订阅此出版物对应的分类法信息的用户发送提醒信息
//						 msg = this.timeTaskService.pushAlertsToWaitingQueue(subject.getTreeCode(), publication.getId(),publication.getTitle(), code);
					}
					
					form.setMsg(msg);
				}
			}else{
				form.setMsg(msg);
			}
			model.put("form", form);
		}catch(Exception e){
			//e.printStackTrace();
            request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
		return this.isbnConfirm(request, response, form);
	}
	
	@RequestMapping(value="/form/testSend")
	public void testSend( HttpServletRequest request,HttpServletResponse response)throws Exception {
		String forwardString="";
		try{
			String treeCodeParam=request.getParameter("treeCode");
			String mail=request.getParameter("email");
			String[] treeCodeArr=treeCodeParam.split(",");
			Integer alertCount=0;
			List<ArrayList<PPublications>> alertQueue=new ArrayList<ArrayList<PPublications>>();
			
			Map<String,Object> condition=new HashMap<String,Object>();			
			condition.put("pstatus", 2);
			condition.put("pTypeArr", new Integer[]{1,2,4,7});
			
			for(String treeCode:treeCodeArr){
				Set<String> pubSet=new HashSet<String>();
				condition.put("subTreeCode", treeCode);				
				List<PCsRelation> csRelations = this.bSubjectService.getTops(condition, " order by p.createOn desc ",20,0,null,null);
				if(csRelations!=null && csRelations.size()>0){
					alertCount+=this.bSubjectService.getCountForAlerts(condition);
					ArrayList<PPublications> publications = new ArrayList<PPublications> ();
					for(PCsRelation csRelation : csRelations){
						String validId=csRelation.getPublications().getType()==1?csRelation.getPublications().getId():csRelation.getPublications().getPublications().getId();
						if(validId!=null && !pubSet.contains(validId)){
							pubSet.add(validId);
							csRelation.getPublications().setSubjectName(csRelation.getSubject().getCode()+" "+csRelation.getSubject().getName());
							publications.add(csRelation.getPublications());
							if(pubSet.size()>=6){
								//够6条就不用再继续了
								break;
							}
						}				
					}
					if(!publications.isEmpty()){
						alertQueue.add(publications);
					}			
				}
			}
			boolean sendSuccess = this.sendEmailBetter(mail, "402881ba3dcf533e013de218ddbf05ef" , "某某某", alertCount, alertQueue, Param.getParam("mail.template.alerts",true).get("alerts"));
			if(sendSuccess){
				response.getWriter().write("succ");
			}else{
				response.getWriter().write("fail");
			}
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().write(e.getMessage());
		}
		
	}
	private boolean sendEmailBetter(String email, String userId,String userName,Integer count,List<ArrayList<PPublications>> queue,String templateName) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String today = formatter.format(new Date());
		Map<String, String> body = new HashMap<String, String>();
		body.put("username", userName);
		body.put("content1", "您好");
		body.put("content2", "您在易阅通上订阅的内容有更新，以下是部分新增资源");
		//邮件中不支持link标签来引用css
		String hrefs ="<style> .outerUl{ width:930px; list-style: none; overflow:hidden; }";
		hrefs+=" .outerUl li{ width:300px; height:120px; float:left; padding:0 0 10px 2px; position: relative; list-style-type: none !important; overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}";
		hrefs+=" .list{ width:200px; margin-left: 6px; float:left; font-family:'Microsoft Yahei','Tahoma','Simsun'; font-size:12px; line-height:22px;}";
		hrefs+=" .outerUl li img{ float:left; height:120px;width:90px }";
		hrefs+=" .subject{ font-weight: bold; font-size:18px; padding-left: 40px}";
		hrefs+="</style>";		
			
			Map<String,Object> recedPubs=new HashMap<String,Object>();
			String defaultCover="http://www.cnpereading.com/images/noimg.jpg";
			for(List<PPublications> publications:queue){
				int j=0;
				hrefs+="<span class=\"subject\">"+ publications.get(0).getSubjectName() +"</span>";
				hrefs+="<ul class=\"outerUl\">";
				for(int i=0;i<publications.size();i++){
					if(j<6){						
						if((publications.get(i).getType()==1 || publications.get(i).getType()==2) && !recedPubs.containsKey(publications.get(i).getId())){
							String cover=publications.get(i).getCover()==null || "".equals(publications.get(i).getCover().trim())?defaultCover:("http://www.cnpereading.com/pages/publications/form/cover?id="+publications.get(i).getId() + "&t=1");
							recedPubs.put(publications.get(i).getId(),null);
							j++;
							hrefs+="<li><img src=\""+cover+"\"/>";
							hrefs+="<div class=\"list\"><div><a title=\""+publications.get(i).getTitle().replace("\"", "\'") +"\" href=\"http://www.cnpereading.com/pages/publications/form/article/"+publications.get(i).getId()+"\">";
							hrefs+=publications.get(i).getTitle() + "</a></div>";
							hrefs+="<div>"+publications.get(i).getCode()+"</div>";
							hrefs+=publications.get(i).getAuthor()!=null?("<div>"+publications.get(i).getAuthor()+"</div>"):"";
							hrefs+="<div>"+publications.get(i).getPublisher().getName()+"</div></div></li>";
						}else if((publications.get(i).getType()==4 || publications.get(i).getType()==7) && !recedPubs.containsKey(publications.get(i).getPublications().getId())){
							String cover=publications.get(i).getPublications().getCover()==null || "".equals(publications.get(i).getPublications().getCover().trim())?defaultCover:("http://www.cnpereading.com/pages/publications/form/cover?id="+publications.get(i).getPublications().getId() + "&t=1");
							recedPubs.put(publications.get(i).getPublications().getId(),null);
							j++;
							hrefs+="<li><img src=\""+cover+"\"/>";
							hrefs+="<div class=\"list\"><div><a title=\""+publications.get(i).getPublications().getTitle().replace("\"", "\'") +"\" href=\"http://www.cnpereading.com/pages/publications/form/article/"+publications.get(i).getPublications().getId()+"\">";
							hrefs+=publications.get(i).getPublications().getTitle() + "</a></div>";
							hrefs+="<div>"+publications.get(i).getPublications().getCode()+"</div>";
							hrefs+="<div>"+publications.get(i).getPublisher().getName()+"</div></div></li>";
						}
					}else{
						break;
					}
				}
				hrefs+="</ul>";
			}
		body.put("hrefs",  hrefs.replace(";", "#@@#").replace(":", "#!!#"));
//		body.put("content3", "近期共上架新书"+count+"本，请进行浏览!");
//		body.put("hrefs",  URLEncoder.encode(hrefs));
		body.put("date", today);
		System.out.println(hrefs);
		Map<String, String> title = new HashMap<String, String>();
		title.put("username", userName);
		
		//发送邮件
		ServiceFactory serviceFactory=(ServiceFactory)new ServiceFactoryImpl();
		return serviceFactory.getSendMailService().sendMail(title, body, "EPublishing", email, userId, templateName);
	}
}
