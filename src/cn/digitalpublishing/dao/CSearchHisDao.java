package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.CSearchHis;

public class CSearchHisDao extends CommonDao<CSearchHis, String> {

	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;				
		
		/**
		 * type类型 1、临时；2、永久
		 * */
		if(CollectionsUtil.exist(map, "type")&&!"".equals(map.get("type"))){
			if(flag==0){
				whereString+=" where a.type = ?";
				flag=1;
			}else{
				whereString+=" and a.type = ?";
			}
			condition.add(map.get("type"));
		}
		/**
		 * userId
		 * */
		if(CollectionsUtil.exist(map, "userId")&&!"".equals(map.get("userId"))){
			if(flag==0){
				whereString+=" where a.user.id = ?";
				flag=1;
			}else{
				whereString+=" and a.user.id = ?";
			}
			condition.add(map.get("userId"));
		}
		/**
		 * dirId
		 * */
		if(CollectionsUtil.exist(map, "dirId")&&!"".equals(map.get("dirId"))){
			if(flag==0){
				whereString+=" where a.directory.id = ?";
				flag=1;
			}else{
				whereString+=" and a.directory.id = ?";
			}
			condition.add(map.get("dirId"));
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
	public List<CSearchHis> getList(Map<String,Object> condition,String sort)throws Exception{
		List<CSearchHis> list=null;
		String hql=" from CSearchHis a left join a.directory b";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,keyword,type,createOn,directory.id,directory.name,keyType";
		String field="a.id,a.keyword,a.type,a.createOn,b.id,b.name,a.keyType";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, CSearchHis.class.getName());
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
	public List<CSearchHis> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<CSearchHis> list=null;
		String hql=" from CSearchHis a left join a.directory b";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,keyword,type,createOn,directory.id,directory.name,keyType";
		String field="a.id,a.keyword,a.type,a.createOn,b.id,b.name,a.keyType";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, CSearchHis.class.getName(),pageCount,page*pageCount);
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
		List<CSearchHis> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from CSearchHis a left join a.directory b";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", CSearchHis.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

	@SuppressWarnings("unchecked")
	public void deleteByCondition(Map<String, Object> condition)throws Exception {
		String hql = " delete from CSearchHis a ";
		Map<String,Object> t = this.getWhere(condition);
		try {
			this.hibernateDao.executeHql(hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray());
		} catch (Exception e) {
			throw e;
		}
	}
	
}
