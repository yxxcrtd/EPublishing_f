package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.OExchange;

public class OExchangeDao extends CommonDao<OExchange, String> {
	
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * 1.xcurr
		 */
		if(CollectionsUtil.exist(map, "xcurr")&&!"".equals(map.get("xcurr"))){
			if(flag==0){
				whereString+=" where xcurr = ?";
				flag=1;
			}else{
				whereString+=" and xcurr = ?";
			}
			condition.add(map.get("xcurr").toString());
		}
		/**
		 * 2.scurr
		 */
		if(CollectionsUtil.exist(map, "scurr")&&!"".equals(map.get("scurr"))){
			if(flag==0){
				whereString+=" where scurr = ?";
				flag=1;
			}else{
				whereString+=" and scurr = ?";
			}
			condition.add(map.get("scurr").toString());
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
	public List<OExchange> getList(Map<String,Object> condition,String sort)throws Exception{
		List<OExchange> list=null;
		String hql=" from OExchange a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,scurr,xcurr,rate,createddate,updateddate";
		String field="a.id,a.scurr,a.xcurr,a.rate,a.createddate,a.updateddate";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, OExchange.class.getName());
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
	public List<OExchange> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<OExchange> list=null;
		String hql=" from OExchange a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,scurr,xcurr,rate,createddate,updateddate";
		String field="a.id,a.scurr,a.xcurr,a.rate,a.createddate,a.updateddate";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, OExchange.class.getName(),pageCount,page*pageCount);
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
		List<OExchange> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from OExchange a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", OExchange.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
}
