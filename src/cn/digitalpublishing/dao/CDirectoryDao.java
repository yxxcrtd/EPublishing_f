package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.CDirectory;

public class CDirectoryDao extends CommonDao<CDirectory, String> {

	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;				
		
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
		 * userId1
		 * */
		if(CollectionsUtil.exist(map, "userId1")&&!"".equals(map.get("userId1"))){
			if(flag==0){
				whereString+=" where a.id != ?";
				flag=1;
			}else{
				whereString+=" and a.id != ?";
			}
			condition.add(map.get("userId1"));
		}
		/**
		 * name
		 * */
		if(CollectionsUtil.exist(map, "name")&&!"".equals(map.get("name"))){
			if(flag==0){
				whereString+=" where a.name = ?";
				flag=1;
			}else{
				whereString+=" and a.name = ?";
			}
			condition.add(map.get("name"));
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
	public List<CDirectory> getList(Map<String,Object> condition,String sort)throws Exception{
		List<CDirectory> list=null;
		String hql=" from CDirectory a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,name,hisCount";
		String field="a.id,a.name,(select cast(count(*) as string) from CSearchHis cs where cs.directory.id=a.id)";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, CDirectory.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	/**
	 * 获取配置列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<CDirectory> getList1(Map<String,Object> condition,String sort)throws Exception{
		List<CDirectory> list=null;
		String hql=" from CDirectory a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,name";
		String field="a.id,a.name";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, CDirectory.class.getName());
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
	public List<CDirectory> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<CDirectory> list=null;
		String hql=" from CDirectory a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,name,hisCount";
		String field="a.id,a.name,(select cast(count(*) as string) from CSearchHis cs where cs.directory.id=a.id)";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, CDirectory.class.getName(),pageCount,page*pageCount);
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
		List<CDirectory> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from CDirectory a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", CDirectory.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
}
