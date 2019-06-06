package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.OCurrency;

public class OCurrencyDao extends CommonDao<OCurrency, String> {

	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * 1.type
		 */
//		if(CollectionsUtil.exist(map, "type")&&!"".equals(map.get("type"))){
//			if(flag==0){
//				whereString+=" where type = ?";
//				flag=1;
//			}else{
//				whereString+=" and type = ?";
//			}
//			condition.add(map.get("type").toString());
//		}
		
		
		table.put("where",whereString);
		table.put("condition", condition);
		return table;
	}
	
	/**
	 * 获取等待列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<OCurrency> getList(Map<String,Object> condition,String sort)throws Exception{
		List<OCurrency> list=null;
		String hql=" from OCurrency a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,desc";
		String field="a.id,a.code,a.desc";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, OCurrency.class.getName());
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
	public List<OCurrency> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<OCurrency> list=null;
		String hql=" from OCurrency a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,desc";
		String field="a.id,a.code,a.desc";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, OCurrency.class.getName(),pageCount,page*pageCount);
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
		List<OCurrency> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from OCurrency a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", OCurrency.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	
}
