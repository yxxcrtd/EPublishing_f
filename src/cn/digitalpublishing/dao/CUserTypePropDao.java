package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.CUserTypeProp;

public class CUserTypePropDao extends CommonDao<CUserTypeProp,String> {
	
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;				
		
		if(CollectionsUtil.exist(map, "existid")&&!"".equals(map.get("existid"))){
			if(flag==0){
				whereString+=" where a.id != ?";
				flag=1;
			}else{
				whereString+=" and a.id != ?";
			}
			condition.add(map.get("existid"));
		}
		/**
		 * userTypeCode 用户类型编码：1，个人；2，机构
		 */
		if(CollectionsUtil.exist(map, "userTypeCode")&&!"".equals(map.get("userTypeCode"))){
			if(flag==0){
				whereString+=" where b.code = ?";
				flag=1;
			}else{
				whereString+=" and b.code = ?";
			}
			condition.add(map.get("userTypeCode"));
		}
		
		/**
		 * 状态：1，在用；2，失效
		 */
		if(CollectionsUtil.exist(map, "status")&&!"".equals(map.get("status"))){
			if(flag==0){
				whereString+=" where a.status = ?";
				flag=1;
			}else{
				whereString+=" and a.status = ?";
			}
			condition.add(map.get("status"));
		}
		/**
		 * b.id
		 */
		if(CollectionsUtil.exist(map, "id")&&!"".equals(map.get("id"))){
			if(flag==0){
				whereString+=" where b.id = ?";
				flag=1;
			}else{
				whereString+=" and b.id = ?";
			}
			condition.add(map.get("id"));
		}
		
		/**
		 * a.id
		 */
		if(CollectionsUtil.exist(map, "aid")&&!"".equals(map.get("aid"))){
			if(flag==0){
				whereString+=" where a.id = ?";
				flag=1;
			}else{
				whereString+=" and a.id = ?";
			}
			condition.add(map.get("aid"));
		}
		
		if(CollectionsUtil.exist(map, "code")&&!"".equals(map.get("id"))){
			if(flag==0){
				whereString+=" where a.code = ?";
				flag=1;
			}else{
				whereString+=" and a.code = ?";
			}
			condition.add(map.get("code"));
		}
		
		if(CollectionsUtil.exist(map, "key")&&!"".equals(map.get("id"))){
			if(flag==0){
				whereString+=" where a.key = ?";
				flag=1;
			}else{
				whereString+=" and a.key = ?";
			}
			condition.add(map.get("key"));
		}
		if(CollectionsUtil.exist(map, "sendStatus") && !"".equals(map.get("sendStatus")))
		{
			if(flag == 0)
			{
				whereString += " where a.sendStatus = ?";
				flag = 1;
			}else
			{
				 whereString += " and a.sendStatus = ?";
				
			}
			 condition.add(map.get("sendStatus"));
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
	public List<CUserTypeProp> getList(Map<String,Object> condition,String sort)throws Exception{
		List<CUserTypeProp> list=null;
		String hql=" from CUserTypeProp a left join a.userType b";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,key,status,order,display,stype,svalue,must,userType.id";
		String field="a.id,a.code,a.key,a.status,a.order,a.display,a.stype,a.svalue,a.must,b.id";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, CUserTypeProp.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	/**
	 * 获取分页信息
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<CUserTypeProp> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<CUserTypeProp> list=null;
		String hql=" from CUserTypeProp a left join a.userType b";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,key,display,svalue";
		String field="a.id,a.code,a.key,a.display,a.svalue";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, CUserTypeProp.class.getName(),pageCount,page*pageCount);
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	/**
	 * 获取总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer getCount(Map<String,Object> condition)throws Exception{
		List<CUserTypeProp> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from CUserTypeProp a left join a.userType b";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", CUserTypeProp.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<CUserTypeProp> getCUserTypeProp(Map<String,Object> condition,String sort)throws Exception{
		List<CUserTypeProp> list=null;
		String hql=" from CUserTypeProp a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,key,status,order,display,stype,svalue,must";
		String field="a.id,a.code,a.key,a.status,a.order,a.display,a.stype,a.svalue,a.must";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, CUserTypeProp.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list;
	}

}
