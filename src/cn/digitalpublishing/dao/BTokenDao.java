package cn.digitalpublishing.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.BToken;
import cn.digitalpublishing.util.web.DateUtil;

public class BTokenDao extends CommonDao<BToken,String> {
	
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;				
		
		if(CollectionsUtil.exist(map, "id")&&!"".equals(map.get("id"))){
			if(flag==0){
				whereString+=" where a.id = ? ";
				flag=1;
			}else{
				whereString+=" and a.id = ? ";
			}
			condition.add(map.get("id"));
		}
		/**
		 * userId 用户ID
		 * */
		if(CollectionsUtil.exist(map, "userId")&&!"".equals(map.get("userId"))){
			if(flag==0){
				whereString+=" where a.userId = ? ";
				flag=1;
			}else{
				whereString+=" and a.userId = ? ";
			}
			condition.add(map.get("userId"));
		}
		if(CollectionsUtil.exist(map, "type")&&!"".equals(map.get("type"))){
			if(flag==0){
				whereString+=" where a.type = ? ";
				flag=1;
			}else{
				whereString+=" and a.type = ? ";
			}
			condition.add(map.get("type"));
		}
		if(CollectionsUtil.exist(map, "status")&&!"".equals(map.get("status"))){
			if(flag==0){
				whereString+=" where a.status = ? ";
				flag=1;
			}else{
				whereString+=" and a.status = ? ";
			}
			condition.add(map.get("status"));
		}
		if(CollectionsUtil.exist(map, "withinMinutes")&&!"".equals(map.get("withinMinutes"))){
			Integer min=Integer.parseInt(map.get("withinMinutes").toString());
			Date beforeTime=DateUtil.getDateBeforeMinutes(min);
			if(flag==0){
				whereString+=" where a.createOn > ? ";
				flag=1;
			}else{
				whereString+=" and a.createOn > ? ";
			}
			condition.add(beforeTime);
		}
		table.put("where",whereString);
		table.put("condition", condition);
		return table;
	}
	
	/**
	 * 获取配置列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<BToken> getList(Map<String,Object> condition,String sort)throws Exception{
		List<BToken> list=null;
		String hql=" from BToken a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id";
		String field="a.id";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BToken.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public BToken getToken(Map<String,Object> condition,String sort)throws Exception{
		List<BToken> list=null;
		String hql=" from BToken a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,type,status,userId,createOn ";
		String field="a.id,a.type,a.status,a.userId,a.createOn ";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BToken.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list!=null && !list.isEmpty()?list.get(0):null;
	}
	/**
	 * 获取总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer getCount(Map<String,Object> condition)throws Exception{
		List<BToken> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from BToken a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", BToken.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	
	public void disableTokens(Map<String,Object> condition)throws Exception{
		Map<String,Object> t=this.getWhere(condition);
		String hql=" update BToken a set a.status =  " + BToken.STATUS_CLOSE + " ";
		try{
			this.hibernateDao.executeHql(hql + t.get("where").toString(), ((List<Object>)t.get("condition")).toArray());
		}catch(Exception e){
			throw e;
		}		
	}
}
