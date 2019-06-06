package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.PPriceType;

public class PriceTypeDao extends CommonDao<PPriceType, String> {

	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * 1.code
		 */
		if(CollectionsUtil.exist(map, "code")&&!"".equals(map.get("code"))){
			boolean isPrecise=CollectionsUtil.exist(map, "isPrecise")?Boolean.valueOf(map.get("isPrecise").toString()):true;
			if(isPrecise){
				if(flag==0){
					whereString+=" where a.code = ?";
					flag=1;
				}else{
					whereString+=" and a.code = ?";
				}
				condition.add(map.get("code"));
			}else{
				if(flag==0){
					whereString+=" where a.code like ?";
					flag=1;
				}else{
					whereString+=" and a.code like ?";
				}
				condition.add("%"+map.get("code")+"%");
			}
		}
		
		/**
		 * 2.name
		 */
		if(CollectionsUtil.exist(map, "name")&&!"".equals(map.get("name"))){
			if(flag==0){
				whereString+=" where a.name like ?";
				flag=1;
			}else{
				whereString+=" and a.name like ?";
			}
			condition.add("%"+map.get("name")+"%");
		}
		
		/**
		 * 3.uniqueId
		 */
		if(CollectionsUtil.exist(map, "uniqueId")&&!"".equals(map.get("uniqueId"))){
			if(flag==0){
				whereString+=" where a.id <> ?";
				flag=1;
			}else{
				whereString+=" and a.id <> ?";
			}
			condition.add(map.get("uniqueId"));
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
	public List<PPriceType> getList(Map<String,Object> condition,String sort)throws Exception{
		List<PPriceType> list=null;
		String hql=" from PPriceType a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,name,code";
		String field="a.id,a.name,a.code";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PPriceType.class.getName());
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
	public List<PPriceType> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<PPriceType> list=null;
		String hql=" from PPriceType a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,name,code";
		String field="a.id,a.name,a.code";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PPriceType.class.getName(),pageCount,page*pageCount);
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
		List<PPriceType> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from PPriceType a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", PPriceType.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
}
