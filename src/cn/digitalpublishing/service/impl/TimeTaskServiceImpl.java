package cn.digitalpublishing.service.impl;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.Param;
import cn.digitalpublishing.ep.po.CAlertsSendedQueue;
import cn.digitalpublishing.ep.po.CAlertsWaitingQueue;
import cn.digitalpublishing.ep.po.CUserAlerts;
import cn.digitalpublishing.ep.po.CUserProp;
import cn.digitalpublishing.ep.po.LUserAlertsLog;
import cn.digitalpublishing.ep.po.PCsRelation;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.service.TimeTaskService;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;
import cn.digitalpublishing.util.web.DateUtil;

public class TimeTaskServiceImpl extends BaseServiceImpl implements TimeTaskService {

//	public void handleAlertsWaitingQueue() throws Exception {
//		
//		Map<String,Object> condition = new HashMap<String,Object>();
//		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
//		List<CAlertsWaitingQueue> list = this.daoFacade.getAlertsWaitingQueueDao().getList(condition, null);
//		for(CAlertsWaitingQueue alertsWQ:list){
//			if(alertsWQ.getAlertsFrequency()==2){
//				//发邮件
//				boolean sendSuccess = this.sendEmail(alertsWQ.getEmail(), alertsWQ.getUserId(), alertsWQ.getUserName(), alertsWQ.getPublicationsId(),Param.getParam("mail.template.alerts",true).get("alerts"));
//				if(sendSuccess){
//					//保存到已发送列表
//					//发送邮件成功后，将等待队列中的提醒信息删除，并向已发送队列新增一条记录
//					this.daoFacade.getAlertsWaitingQueueDao().delete(CAlertsWaitingQueue.class.getName(), alertsWQ.getId());
//					this.insertAlertsSQ(alertsWQ);
//				}
//			}else if(alertsWQ.getAlertsFrequency()==3){
//				//每周发送提醒
//				//首先判断当前是否是每周周一
//				if(dateFormat.format(new Date()).equals(DateUtil.getMonthStartDay(new Date()))){
//					//发邮件
//					boolean sendSuccess = this.sendEmail(alertsWQ.getEmail(), alertsWQ.getUserId(), alertsWQ.getUserName(), alertsWQ.getPublicationsId(),Param.getParam("mail.template.alerts",true).get("alerts"));
//					if(sendSuccess){
//						//发送邮件成功后，将等待队列中的提醒信息删除，并向已发送队列新增一条记录
//						this.daoFacade.getAlertsWaitingQueueDao().delete(CAlertsWaitingQueue.class.getName(), alertsWQ.getId());
//						this.insertAlertsSQ(alertsWQ);
//					}
//				}
//			} else if(alertsWQ.getAlertsFrequency()==4){
//				//每月发送提醒
//				//首先判断当前是否是每月第一天
//				if(dateFormat.format(new Date()).equals(DateUtil.getMonthStartDay(new Date()))){
//					//发邮件
//					boolean sendSuccess = this.sendEmail(alertsWQ.getEmail(), alertsWQ.getUserId(), alertsWQ.getUserName(), alertsWQ.getPublicationsId(),Param.getParam("mail.template.alerts",true).get("alerts"));
//					if(sendSuccess){
//						//发送邮件成功后，将等待队列中的提醒信息删除，并向已发送队列新增一条记录
//						this.daoFacade.getAlertsWaitingQueueDao().delete(CAlertsWaitingQueue.class.getName(), alertsWQ.getId());
//						this.insertAlertsSQ(alertsWQ);
//					}
//				}
//			}
//		}
//	}

	@Override
	public void autoHandleAlerts() throws Exception {
		Map<String,Object> condition = new HashMap<String,Object>();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
		List<CUserAlerts> list = this.daoFacade.getCUserAlertsDao().getList(condition, " order by c.id ");//将同一用户的提醒排到一起方便后面多个学科一起发邮件
		if(list!=null && list.size()>0){
			condition.clear();
			List<ArrayList<PPublications>> alertQueue=new ArrayList<ArrayList<PPublications>>();	
			List<PCsRelation> complateQueue=new ArrayList<PCsRelation>();
			CUserProp  userProp = null;
			Integer alertCount=0;
			for(int index=0;index<list.size();index++){
				CUserAlerts alerts=list.get(index);
				if(userProp==null){
					condition.put("code", "email");
					condition.put("userId", alerts.getUser().getId());
					userProp = this.daoFacade.getCUserPropDao().getList(condition, null).get(0);
					condition.clear();
				}
				if(alerts.getFrequency()==2){
					//获取昨天上架的新书
					condition.put("startDate",DateUtil.getYesterDayDate(new Date()));
					condition.put("endDate",DateUtil.getYesterDayDate(new Date()));
				}else if(alerts.getFrequency()==3){
					//每周发送提醒
					//首先判断当前是否是每周周一
					if(dateFormat.format(new Date()).equals(DateUtil.getWeekMonday(new Date()))){
						//获取上周上架的新书
						condition.put("startDate", DateUtil.getBeforeWeekMonday(new Date()));
						condition.put("endDate", DateUtil.getBeforeWeekSunday(new Date()));
					}
				} else if(alerts.getFrequency()==4){
					//每月发送提醒
					//首先判断当前是否是每月第一天
					if(dateFormat.format(new Date()).equals(DateUtil.getMonthStartDay(new Date()))){
						//获取上月上架的新书
						condition.put("startDate", DateUtil.getBeforeMonthStartDay(new Date()));
						condition.put("endDate", DateUtil.getBeforeMonthEndDay(new Date()));						
					}
				}else{
					continue;
				}					
				
				//查询用户订阅的学科主题下新书
				condition.put("subTreeCode", alerts.getTreeCode());				
				condition.put("pTypeArr", new Integer[]{1,2,4,7});
				List<PCsRelation> csRelations = this.daoFacade.getpCsRelationDao().getTopsForAlerts(condition, " order by p.createOn desc ",100);
				
				if(csRelations!=null && csRelations.size()>0){
					alertCount+=csRelations.size();
					ArrayList<PPublications> publications = new ArrayList<PPublications>();
					Set<String> pubSet=new HashSet<String>();
					complateQueue.addAll(csRelations);
					for(PCsRelation csRelation : csRelations){
						//期刊的期、文章，归到刊下面进行提示
						String validId=csRelation.getPublications().getType()==1?csRelation.getPublications().getId():csRelation.getPublications().getPublications().getId();
						if(validId!=null && !pubSet.contains(validId)){
							pubSet.add(validId);
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
				//若此提醒是最后一条提醒，或与上一提醒的用户不相同，立即发送提醒邮件，否则暂存需提醒内容,直到不同时，再一起发送
				if(index>=list.size()-1 || !alerts.getUser().getId().equals(list.get(index+1).getUser().getId())){
					if(alertQueue!=null && !alertQueue.isEmpty()){
						boolean flag=sendEmailBetter(userProp.getVal(), alerts.getUser().getId(), alerts.getUser().getName(),alertCount, alertQueue, Param.getParam("mail.template.alerts",true).get("alerts"));
						if(flag && complateQueue!=null && !complateQueue.isEmpty()){
							for(PCsRelation pub : complateQueue){
								CAlertsSendedQueue alertsSQ = new CAlertsSendedQueue();
								alertsSQ.setUserId(alerts.getUser().getId());
								alertsSQ.setUserName(alerts.getUser().getName());
								alertsSQ.setSubjectName(alerts.getSubject().getName());
								alertsSQ.setPublicationsId(pub.getId());
								alertsSQ.setPublicationsName(pub.getPublications().getTitle());
								alertsSQ.setAlertsType(alerts.getType());
								alertsSQ.setAlertsFrequency(alerts.getFrequency());
								alertsSQ.setEmail(userProp.getVal());
								alertsSQ.setCreateOn(new Date());
								this.daoFacade.getAlertsSendedQueueDao().insert(alertsSQ);
							}
						}						
					}
					//重置中间值
					userProp=null;
					alertCount=0;
					alertQueue=new ArrayList<ArrayList<PPublications>>();	
					complateQueue=new ArrayList<PCsRelation>();
				}	
			}
		}
	}
		
	/**
	 * 管理员输入isbn编号，获取对应的出版物并给订阅该类学科的用户发送邮件
	 */
	private boolean sendEmail(String email, String userId,String userName,Integer count,List<PPublications> publications,String templateName) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String today = formatter.format(new Date());
		Map<String, String> body = new HashMap<String, String>();
		body.put("username", userName);
		body.put("content1", "您好");
		body.put("content2", "易阅通向您推荐新书，请点击下面链接进行查阅详细信息!");
		//邮件中不支持link标签来引用css
		String hrefs ="<style> .outerUl{ width:1000px; list-style: none; overflow:hidden; }";
		hrefs+=" .outerUl li{ width:320px; height:120px; float:left; padding:0 0 10px 2px; position: relative; list-style-type: none !important; overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}";
		hrefs+=" .list{ width:220px; float:right; font-family:'Microsoft Yahei','Tahoma','Simsun'; font-size:12px; line-height:22px;}";
		hrefs+=" .outerUl li img{height:120px;width:90px}";
		hrefs+="</style>";
		hrefs+="<ul class=\"outerUl\">";
			int j=0;
			Map<String,Object> recedPubs=new HashMap<String,Object>();
			String defaultCover="http://www.cnpereading.com/images/noimg.jpg";
			for(int i=0;i<publications.size();i++){
				if(j<10){
					
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
		body.put("hrefs",  hrefs.replace(";", "#@@#").replace(":", "#!!#"));
		body.put("content3","近期共上架新书"+count+"本，请进行浏览!");
//		body.put("hrefs",  URLEncoder.encode(hrefs));
		body.put("date", today);
		System.out.println(hrefs);
		Map<String, String> title = new HashMap<String, String>();
		title.put("username", userName);
		
		//发送邮件
		ServiceFactory serviceFactory=(ServiceFactory)new ServiceFactoryImpl();
		return serviceFactory.getSendMailService().sendMail(title, body, "EPublishing", email, userId, templateName);
	}
	
	/**
	 * 根据管理员输入的isbn编号获取对应的学科主题subject
	 * 根据此subject的treeCode长度去查找可能用户已订阅的提醒主题。
	 * 1.如果此subject的treeCode长度为6位，则为一级主题。
	 * 2.如果此subject的treeCode长度为9位，则为二级主题。
	 * @param subjectTreeCode
	 * @return
	 */
	private List<String> getTreeCodes(String subjectTreeCode){
		List<String> treeCodes= new ArrayList<String>();
		//获取管理员输入的isbn对应的主题学科treeCode长度
		int codeLength = subjectTreeCode.length();
		//判断为几级主题，0-一级学科；1-二级学科；以此类推
		int codeCount = (codeLength-6)/3;
		while(codeCount>-1){
			treeCodes.add(subjectTreeCode.substring(0, 6+codeCount*3));
			codeCount--;
		}
		return treeCodes;
		
	}
	
	public static void main(String []s){
//		String subjectTreeCode= "001001002003";
//		List<String> treeCodes= new ArrayList<String>();
//		//获取管理员输入的isbn对应的主题学科treeCode长度
//		int codeLength = subjectTreeCode.length();
//		//判断为几级主题，0-一级学科；1-二级学科；以此类推
//		int codeCount = (codeLength-6)/3;
//		while(codeCount>-1){
//			treeCodes.add(subjectTreeCode.substring(0, 6+codeCount*3));
//			codeCount--;
//		}
//		DateUtil.getMonthStartDay(new Date())
//		System.out.printf(DateUtil.getBeforeMonthStartDay(new Date()));
//		System.out.printf(DateUtil.getYesterDayDate(new Date()));
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
		System.out.println(DateUtil.getWeekMonday(new Date()));
		System.out.println(dateFormat.format(new Date()));
		System.out.println(dateFormat.format(new Date()).equals(DateUtil.getWeekMonday(new Date())));
	}

	
	public List<String> deleteIndex()throws Exception{
		List<String> result=null;
		try{			
			Map<String,Object> condition=new HashMap<String,Object>();
			condition.put("endTime", new Date());
			condition.put("status", 1);
			condition.put("type", 2);
			List<LLicense> list=this.daoFacade.getlLicenseDao().getList(condition, "");
			if(list!=null && list.size()>0){
				result=new ArrayList<String>();
				for(LLicense license:list){
					license.setStatus(2);//令license失效
					result.add(license.getId());
				}
			}			
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	@Override
	public void autoRenewalAlerts() throws Exception {
		try {
			String template = Param.getParam("mail.template.renew",true).get("renew");
			//查询LUserAlertsLog表中，状态为1
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(new Date());
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("status", 1);
			condition.put("date", date);
			List<LUserAlertsLog> list = this.daoFacade.getlUserAlertsLogDao().getList(condition, " order by a.createdon desc ");
			if(list!=null&&list.size()>0){
				for (LUserAlertsLog alert : list) {
					PPublications publications = (PPublications)this.daoFacade.getpPublicationsDao().get(PPublications.class.getName(), alert.getPublications().getId());
					//发送邮件
					Map<String,String> title = new HashMap<String,String>();
					title.put("username", alert.getUserName());
					Map<String, String> body = new HashMap<String, String>();
					body.put("username", alert.getUserName());
					body.put("content1", "您好");
					if(alert.getAlertType()==1){
						body.put("content2", "易阅通提醒，下列产品即将到期，请及时续费!");
						if(publications.getType()==5){
							body.put("hrefs", "http://www.cnpereading.com/pages/publications/form/database");
						}else{
							body.put("hrefs", "http://www.cnpereading.com/pages/publications/form/article/"+publications.getId());
						}
					}else if(alert.getAlertType()==2){
						body.put("content2", "易阅通向您发送最新订阅信息，请点击下面链接进行查阅详细信息!");
						body.put("hrefs", "http://www.cnpereading.com/pages/publications/form/article/"+publications.getId());
						template = Param.getParam("mail.template.renew",true).get("newest");
					}
					body.put("content3",publications.getTitle());
	//				body.put("hrefs",  URLEncoder.encode(hrefs));
					body.put("date", date);
					ServiceFactory serviceFactory=(ServiceFactory)new ServiceFactoryImpl();
					boolean b = serviceFactory.getSendMailService().sendMail(title, body, "EPublishing", alert.getEmail(), alert.getUser().getId(), template);
					if(b){
						alert.setAlertStatus(2);
					}else{
						alert.setAlertStatus(3);
					}
					this.daoFacade.getlUserAlertsLogDao().update(alert, LUserAlertsLog.class.getName(), alert.getId(), null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 管理员输入isbn编号，获取对应的出版物并给订阅该类学科的用户发送邮件
	 */
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
			int j=0;
			Map<String,Object> recedPubs=new HashMap<String,Object>();
			String defaultCover="http://www.cnpereading.com/images/noimg.jpg";
			for(List<PPublications> publications:queue){
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
//			body.put("content3", "近期共上架新书"+count+"本，请进行浏览!");
//			body.put("hrefs",  URLEncoder.encode(hrefs));
			body.put("date", today);
			System.out.println(hrefs);
			Map<String, String> title = new HashMap<String, String>();
			title.put("username", userName);
		
		//发送邮件
		ServiceFactory serviceFactory=(ServiceFactory)new ServiceFactoryImpl();
		System.out.println("=======================发送：" + email + " " + userId + " " + templateName);
		return serviceFactory.getSendMailService().sendMail(title, body, "EPublishing", email, userId, templateName);
	}
}
