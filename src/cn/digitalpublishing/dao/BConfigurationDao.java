package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.BConfiguration;

public class BConfigurationDao extends CommonDao<BConfiguration,String> {
	
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;				
		
		/**
		 * code编码
		 * */
		if(CollectionsUtil.exist(map, "code")&&!"".equals(map.get("code"))){
			if(flag==0){
				whereString+=" where lower(a.code) = ?";
				flag=1;
			}else{
				whereString+=" and lower(a.code) = ?";
			}
			condition.add(map.get("code").toString().toLowerCase());
		}
		/**
		 * type类型 1、基础参数；2、页面显示参数
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
	public List<BConfiguration> getList(Map<String,Object> condition,String sort)throws Exception{
		List<BConfiguration> list=null;
		String hql=" from BConfiguration a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,key,val,type,content";
		String field="a.id,a.code,a.key,a.val,a.type,a.content";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BConfiguration.class.getName());
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
	public List<BConfiguration> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<BConfiguration> list=null;
		String hql=" from BConfiguration a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,key,val,type,content";
		String field="a.id,a.code,a.key,a.val,a.type,a.content";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BConfiguration.class.getName(),pageCount,page*pageCount);
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
		List<BConfiguration> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from BConfiguration a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", BConfiguration.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

}
