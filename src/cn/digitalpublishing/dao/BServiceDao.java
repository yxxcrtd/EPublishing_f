package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.BService;

/**
 * 广告
 * @author LiuYe
 *
 */
public class BServiceDao extends CommonDao<BService,String>{

	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * 1.title
		 */
		if(CollectionsUtil.exist(map, "title")&&!"".equals(map.get("title"))){
				if(flag==0){
					whereString+=" where a.title = ? ";
					flag=1;
				}else{
					whereString+=" and a.title = ? ";
				}
				condition.add(map.get("title").toString());
		}
		/**
		 * 2.content
		 */
		if(CollectionsUtil.exist(map, "content")&&!"".equals(map.get("content"))){
				if(flag==0){
					whereString+=" where a.content = ? ";
					flag=1;
				}else{
					whereString+=" and a.content = ? ";
				}
				condition.add(map.get("content").toString());
		}
		
		if(CollectionsUtil.exist(map, "type")&&!"".equals(map.get("type"))){
			if(flag==0){
				whereString+=" where a.type = ? ";
				flag=1;
			}else{
				whereString+=" and a.type = ? ";
			}
			condition.add(map.get("type"));
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
	public List<BService> getList(Map<String,Object> condition,String sort)throws Exception{
		List<BService> list=null;
		String hql=" from BService a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,title,content";
		String field="a.id,a.title,a.content";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BService.class.getName());
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
	public List<BService> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<BService> list=null;
		String hql=" from BService a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,title,content,type";
		String field="a.id,a.title,a.content,type";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BService.class.getName(),pageCount,page*pageCount);
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
		List<BService> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from BService a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", BService.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	/**
	 * 简单分页列表
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @param user
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<BService> getSimplePagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<BService> list=null;
		String hql=" from BService a ";
		// 求当前时间前的2周以前的日期
		Map<String,Object> t=this.getWhere(condition);
		List<Object> con = new ArrayList<Object>();
		for(Object o:(List<Object>)t.get("condition")){
			con.add(o);
		}
		String property="id,title,content";
		String field="a.id,a.title,a.content";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), con.toArray(),sort, BService.class.getName(),pageCount,page*pageCount);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
}
