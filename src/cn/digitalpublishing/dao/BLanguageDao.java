package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.BLanguage;

public class BLanguageDao extends CommonDao<BLanguage,String> {
	
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
	public List<BLanguage> getList(Map<String,Object> condition,String sort)throws Exception{
		List<BLanguage> list=null;
		String hql=" from BLanguage a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,objName,propName,propValue";
		String field="a.id,a.code,a.objName,a.propName,a.propValue";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BLanguage.class.getName());
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
	public List<BLanguage> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<BLanguage> list=null;
		String hql=" from BLanguage a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,objName,propName,propValue";
		String field="a.id,a.code,a.objName,a.propName,a.propValue";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BLanguage.class.getName(),pageCount,page*pageCount);
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
		List<BLanguage> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from BLanguage a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", BLanguage.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

}
