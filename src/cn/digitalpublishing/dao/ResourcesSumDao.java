package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.ResourcesSum;

public class ResourcesSumDao extends CommonDao<ResourcesSum, String> {
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;				
		
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
	 * 获资源总数列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<ResourcesSum> getList(Map<String,Object> condition,String sort)throws Exception{
		List<ResourcesSum> list=null;
		String hql=" from ResourcesSum a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,bookCount,bookCountEn,journalsCount,articleCount,sumCount,type,cnJournal,enJournal";
		String field="a.id,a.bookCount,a.bookCountEn,a.journalsCount,a.articleCount,a.sumCount,a.type,a.cnJournal,a.enJournal";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, ResourcesSum.class.getName());
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
	public List<ResourcesSum> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<ResourcesSum> list=null;
		String hql=" from ResourcesSum a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,bookCount,bookCountEn,journalsCount,articleCount,sumCount,type";
		String field="a.id,a.bookCount,a.bookCountEn,a.journalsCount,a.articleCount,a.sumCount,a.type";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, ResourcesSum.class.getName(),pageCount,page*pageCount);
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
		List<ResourcesSum> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from ResourcesSum a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", ResourcesSum.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

}
