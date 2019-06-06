package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.LUserAlertsLog;

public class LUserAlertsLogDao extends CommonDao<LUserAlertsLog, String> {

	/**
	 * @param map
	 * @return
	 */
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;	
		
		
		/**
		 * 1.id
		 */
		if(CollectionsUtil.exist(map, "id")&&!"".equals(map.get("id"))){
			if(flag==0){
				whereString+=" where a.id = ?";
				flag=1;
			}else{
				whereString+=" and a.id = ?";
			}
			condition.add(map.get("id"));
		}
		/**
		 * 2.pubId
		 */
		if(CollectionsUtil.exist(map, "pubId")&&!"".equals(map.get("pubId"))){
			if(flag==0){
				whereString+=" where b.id = ?";
				flag=1;
			}else{
				whereString+=" and b.id = ?";
			}
			condition.add(map.get("pubId"));
		}
		/**
		 * 2.status
		 */
		if(CollectionsUtil.exist(map, "status")&&!"".equals(map.get("status"))){
			if(flag==0){
				whereString+=" where a.alertStatus = ?";
				flag=1;
			}else{
				whereString+=" and a.alertStatus = ?";
			}
			condition.add(map.get("status"));
		}
		/**
		 * 2.date
		 */
		if(CollectionsUtil.exist(map, "date")&&!"".equals(map.get("date"))){
			if(flag==0){
				whereString+=" where a.alertDate = ?";
				flag=1;
			}else{
				whereString+=" and a.alertDate = ?";
			}
			condition.add(map.get("date"));
		}
		/**
		 * 3.userId
		 */
		if(CollectionsUtil.exist(map, "userId")&&!"".equals(map.get("userId"))){
			if(flag==0){
				whereString+=" where c.id = ?";
				flag=1;
			}else{
				whereString+=" and c.id = ?";
			}
			condition.add(map.get("userId"));
		}
		table.put("where",whereString);
		table.put("condition", condition);
		return table;
	}
	/**
	 * 获取列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LUserAlertsLog> getList(Map<String,Object> condition,String sort)throws Exception{
		List<LUserAlertsLog> list=null;
		String hql=" from LUserAlertsLog a left join a.publications b left join a.user c";
		Map<String,Object> t=this.getWhere(condition);		
		String property="id,userName,publicationsName,isbn,email,createdon,alertDate,alertStatus,alertType,alertContent,publications.id,user.id";
		String field="a.id,a.userName,a.publicationsName,a.isbn,a.email,a.createdon,a.alertDate,a.alertStatus,a.alertType,a.alertContent,b.id,c.id";				
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString() , ((List<Object>)t.get("condition")).toArray(), sort, LUserAlertsLog.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	/**
	 * 获取分页列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LUserAlertsLog> getPagingList(Map<String,Object> condition,String sort,int pageCount, int curpage)throws Exception{
		List<LUserAlertsLog> list=null;
		String hql=" from LUserAlertsLog a left join a.publications b left join a.user c";
		Map<String,Object> t=this.getWhere(condition);		
		String property="id,userName,publicationsName,isbn,email,createdon,alertDate,alertStatus,alertType,alertContent,publications.id,user.id";
		String field="a.id,a.userName,a.publicationsName,a.isbn,a.email,a.createdon,a.alertDate,a.alertStatus,a.alertType,a.alertContent,b.id,c.id";				
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString() , ((List<Object>)t.get("condition")).toArray(),sort, LUserAlertsLog.class.getName(),pageCount,curpage*pageCount);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public Integer getCount(Map<String,Object> condition)throws Exception{
		List<LUserAlertsLog> list=null;
		String hql=" from LUserAlertsLog a ";
		Map<String,Object> t=this.getWhere(condition);		
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", LUserAlertsLog.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
}
