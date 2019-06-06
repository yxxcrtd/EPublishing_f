package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.UPRelation;

public class UPRelationDao extends CommonDao<UPRelation, String> {

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
		 * 1.userId
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
		 * 1.typeId 
		 */
		if(CollectionsUtil.exist(map, "typeId")&&!"".equals(map.get("typeId"))){
			if(flag==0){
				whereString+=" where c.id = ?";
				flag=1;
			}else{
				whereString+=" and c.id = ?";
			}
			condition.add(map.get("typeId"));
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
	public List<UPRelation> getList(Map<String,Object> condition,String sort)throws Exception{
		List<UPRelation> list=null;
		String hql=" from UPRelation a left join a.userType b left join a.priceType c ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,userType.id,userType.name,userType.code,priceType.id,priceType.code,priceType.name";
		String field="a.id,b.id,b.name,b.code,c.id,c.code,c.name";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, UPRelation.class.getName());
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
	public List<UPRelation> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<UPRelation> list=null;
		String hql=" from UPRelation a left join a.userType b left join a.priceType c ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,userType.id,userType.name,userType.code,priceType.id,priceType.code,priceType.name";
		String field="a.id,b.id,b.name,b.code,c.id,c.code,c.name";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, UPRelation.class.getName(),pageCount,page*pageCount);
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
		List<UPRelation> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from UPRelation a left join a.userType b left join a.priceType c ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", UPRelation.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

	public void deleteByCondtion(Map<String, Object> condition) throws Exception{
		try {
			String hql = "delete from UPRelation a ";
			List<Object> list = new ArrayList<Object>();
			int flag = 0;
			/**
			 * 1.userTypeId
			 */
			if(CollectionsUtil.exist(condition, "userTypeId")&&!"".equals(condition.get("userTypeId"))){
				if(flag==0){
					hql+=" where a.userType.id = ?";
					flag=1;
				}else{
					hql+=" and a.userType.id = ?";
				}
				list.add(condition.get("userTypeId"));
			}
			this.hibernateDao.executeHql(hql, list.toArray());
		} catch (Exception e) {
			throw e;
		}
	}
	
}
