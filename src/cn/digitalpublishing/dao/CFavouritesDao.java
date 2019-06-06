package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.CFavourites;

public class CFavouritesDao extends CommonDao<CFavourites,String> {
	
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;				
		
		if(flag==0){
			whereString += " where a.publications.status=2 ";
			flag=1;
		}else{
			whereString += " and a.publications.status=2 ";
		}
		
		/**
		 * userId 用户ID
		 * */
		if(CollectionsUtil.exist(map, "userId")&&!"".equals(map.get("userId"))){
			if(flag==0){
				whereString+=" where b.id = ? ";
				flag=1;
			}else{
				whereString+=" and b.id = ? ";
			}
			condition.add(map.get("userId"));
		}
		/**
		 * ftype 用户类型
		 * */
		if(CollectionsUtil.exist(map, "ftype")&&!"".equals(map.get("ftype"))){
			if(flag==0){
				whereString+=" where c.type = ? ";
				flag=1;
			}else{
				whereString+=" and c.type = ? ";
			}
			condition.add(map.get("ftype"));
		}
		
		/**
		 * dels 删除ID集合，以逗号分隔
		 * */
		if(CollectionsUtil.exist(map, "dels")&&!"".equals(map.get("dels"))){
			if(flag==0){
				whereString+=" where c.id in (?) ";
				flag=1;
			}else{
				whereString+=" and c.id in (?) ";
			}
			condition.add(map.get("dels"));
		}
		if(CollectionsUtil.exist(map, "available")&&!"".equals(map.get("available"))){//这是对一个available的条件
			if(flag==0){
				whereString+=" where (c.available <> ? or c.available is null )  ";
				flag=1;
			}else{
				whereString+=" and ( c.available  <> ? or c.available is null ) ";
			}
			condition.add(map.get("available"));
		}
		
		if(CollectionsUtil.exist(map, "pid")&&!"".equals(map.get("pid"))){
			if(flag==0){
				whereString+=" where b.id = ? ";
				flag=1;
			}else{
				whereString+=" and b.id = ? ";
			}
			condition.add(map.get("pid"));
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
	public List<CFavourites> getList(Map<String,Object> condition,String sort)throws Exception{
		List<CFavourites> list=null;
		String hql=" from CFavourites a left join a.user b left join a.publications c";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,user.id,user.name,publications.id,publications.title,publications.pubDate,publications.author,publications.type,publications.code";
		String field="a.id,b.id,b.name,c.id,c.title,c.pubDate,c.author,c.type,c.code";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, CFavourites.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public CFavourites getcFavourite(Map<String,Object> condition)throws Exception{
		CFavourites fav=null;
		String hql=" from CFavourites a left join a.publications b";
		Map<String,Object> t=this.getWhere(condition);
		String property="id";
		String field="a.id";
		try{
			List<CFavourites> list= super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", CFavourites.class.getName());
			if(list != null&& list.size()>0)
			{
				fav = list.get(0);
			}
		}catch(Exception e){
			throw e;
		}
		return fav;
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
	public List<CFavourites> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<CFavourites> list=null;
		String hql=" from CFavourites a left join a.user b left join a.publications c left join c.publisher d";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,user.id,user.name,publications.id,publications.title, publications.pubDate,publications.author,publications.type,publications.code,publications.listPrice,publications.lcurr,publications.pubSubject,publications.available,publications.publisher.id,publications.publisher.name";
		String field="a.id,b.id,b.name,c.id,c.title,c.pubDate,c.author,c.type,c.code,c.listPrice,c.lcurr,c.pubSubject,c.available,d.id,d.name";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, CFavourites.class.getName(),pageCount,page*pageCount);
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	/**
	 * 下载
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<CFavourites> getfList(Map<String,Object> condition,String sort)throws Exception{
		List<CFavourites> list=null;
		String hql=" from CFavourites a left join a.user b left join a.publications c left join c.publisher d";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,user.id,user.name,publications.id,publications.title, publications.pubDate,publications.author,publications.type,publications.code,publications.listPrice,publications.lcurr,publications.pubSubject,publications.publisher.id,publications.publisher.name";
		String field="a.id,b.id,b.name,c.id,c.title,c.pubDate,c.author,c.type,c.code,c.listPrice,c.lcurr,c.pubSubject,d.id,d.name";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, CFavourites.class.getName());
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
		List<CFavourites> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from CFavourites a left join a.user b left join a.publications c ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", CFavourites.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

	public void deleteBycondition(String userId,String delId)throws Exception {
		String sql = "delete from epub_c_favourites where U_ID=? and PUB_ID = ?";
		try {
			this.hibernateDao.executeSql(sql,new Object[]{userId,delId});
		} catch (Exception e) {
			throw e;
		}
	}

}
