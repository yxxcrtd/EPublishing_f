package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.BUrl;

public class BUrlDao extends CommonDao<BUrl, String> {

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
		 * 2.access 
		 */
		if(CollectionsUtil.exist(map, "url")&&!"".equals(map.get("url"))){
			if(flag==0){
				whereString+=" where a.url = ?";
				flag=1;
			}else{
				whereString+=" and a.url = ?";
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
	public List<BUrl> getList(Map<String,Object> condition,String sort)throws Exception{
		List<BUrl> list=null;
		String hql=" from BUrl a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,url";
		String field="a.id,a.url";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BUrl.class.getName());
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
	public List<BUrl> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<BUrl> list=null;
		String hql=" from BUrl a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,url,isaccess";
		String field="a.id,a.url,(case when exists(select b.id from BUuRelation b where b.access=1 and b.url.id=a.id and b.userType.id='"+ condition.get("userTypeId") +"')  then 1 else 2 end) as laccess "; 
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BUrl.class.getName(),pageCount,page*pageCount);
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
		List<BUrl> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from BUrl a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", BUrl.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	
	

}
