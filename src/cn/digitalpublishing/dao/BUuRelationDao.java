package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.BUuRelation;

public class BUuRelationDao extends CommonDao<BUuRelation, String> {

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
		 * 1.userTypeId
		 */
		if(CollectionsUtil.exist(map, "userTypeId")&&!"".equals(map.get("userTypeId"))){
			if(flag==0){
				whereString+=" where b.id = ?";
				flag=1;
			}else{
				whereString+=" and b.id = ?";
			}
			condition.add(map.get("userTypeId"));
		}
		/**
		 * 2.urlId 
		 */
		if(CollectionsUtil.exist(map, "urlId")&&!"".equals(map.get("urlId"))){
			if(flag==0){
				whereString+=" where c.id = ?";
				flag=1;
			}else{
				whereString+=" and c.id = ?";
			}
			condition.add(map.get("urlId"));
		}
		/**
		 * 2.access 
		 */
		if(CollectionsUtil.exist(map, "access")&&!"".equals(map.get("access"))){
			if(flag==0){
				whereString+=" where a.access = ?";
				flag=1;
			}else{
				whereString+=" and a.access = ?";
			}
			condition.add(map.get("access"));
		}
		
		/**
		 * 2.access 
		 */
		if(CollectionsUtil.exist(map, "url")&&!"".equals(map.get("url"))){
			if(flag==0){
				whereString+=" where c.url = ?";
				flag=1;
			}else{
				whereString+=" and c.url = ?";
			}
			condition.add(map.get("url"));
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
	public List<BUuRelation> getList(Map<String,Object> condition,String sort)throws Exception{
		List<BUuRelation> list=null;
		String hql=" from BUuRelation a left join a.userType b left join a.url c ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,access,userType.id,userType.name,userType.code,url.id,url.url";
		String field="a.id,a.access,b.id,b.name,b.code,c.id,c.url";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BUuRelation.class.getName());
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
	public List<BUuRelation> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<BUuRelation> list=null;
		String hql=" from BUuRelation a left join a.userType b left join a.url c ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,access,userType.id,userType.name,userType.code,url.id,url.url";
		String field="a.id,a.access,b.id,b.name,b.code,c.id,c.url";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BUuRelation.class.getName(),pageCount,page*pageCount);
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
		List<BUuRelation> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from BUuRelation a left join a.userType b left join a.url c ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", BUuRelation.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

}
