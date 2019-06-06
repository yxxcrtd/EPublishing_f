package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.SOnsale;

public class SOnsaleDao extends CommonDao<SOnsale,String> {
	
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * 1.total
		 */
		if(CollectionsUtil.exist(map, "total")&&!"".equals(map.get("total"))&&map.get("total")!=null){
			if(flag==0){
				whereString+=" where a.total > ?";
				flag=1;
			}else{
				whereString+=" and a.total > ?";
			}
			condition.add(map.get("total"));
		}
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
	public List<SOnsale> getList(Map<String,Object> condition,String sort)throws Exception{
		List<SOnsale> list=null;
		String hql=" from SOnsale a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="saleDate,total";
		String field="a.saleDate,a.total";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, SOnsale.class.getName());
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
	public List<SOnsale> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<SOnsale> list=null;
		String hql=" from SOnsale a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="saleDate,total";
		String field="a.saleDate,a.total";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, SOnsale.class.getName(),pageCount,page*pageCount);
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
		List<SOnsale> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from SOnsale a ";
		try{
			list=this.hibernateDao.getListByHql("total","count(*)",hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", SOnsale.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getTotal());
	}
	
	@SuppressWarnings("unchecked")
	public Integer getTotal(Map<String,Object> condition)throws Exception{
		List<SOnsale> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from SOnsale a ";
		try{
			list=this.hibernateDao.getListByHql("total","cast(sum(a.total) as int)",hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", SOnsale.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getTotal());
	}

}
