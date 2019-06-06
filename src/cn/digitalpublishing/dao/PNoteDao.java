package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.PNote;

public class PNoteDao extends CommonDao<PNote, String> {

	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * 1.pageId
		 */
		if(CollectionsUtil.exist(map, "pageId")&&!"".equals(map.get("pageId"))){
				if(flag==0){
					whereString+=" where a.pages.id = ? ";
					flag=1;
				}else{
					whereString+=" and a.pages.id = ? ";
				}
				condition.add(map.get("pageId").toString());
		}
		/**
		 * 1.userId
		 */
		if(CollectionsUtil.exist(map, "userId")&&!"".equals(map.get("userId"))){
			if(flag==0){
				whereString+=" where a.userId = ? ";
				flag=1;
			}else{
				whereString+=" and a.userId = ? ";
			}
			condition.add(map.get("userId").toString());
		}
		/**
		 * 1.publicationsId
		 */
		if(CollectionsUtil.exist(map, "publicationsId")&&!"".equals(map.get("publicationsId"))){
			if(flag==0){
				whereString+=" where b.publications.id = ? ";
				flag=1;
			}else{
				whereString+=" and b.publications.id = ? ";
			}
			condition.add(map.get("publicationsId").toString());
		}
		if(CollectionsUtil.exist(map, "tag")&&!"".equals(map.get("tag"))){
			if(flag==0){
				whereString+=" where b.mark = ? ";
				flag=1;
			}else{
				whereString+=" and b.mark = ? ";
			}
			condition.add(map.get("tag").toString());
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
	public List<PNote> getList(Map<String,Object> condition,String sort)throws Exception{
		List<PNote> list=null;
		String hql=" from PNote a left join a.pages b ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,noteContent,updateDate,userId,userName,createDate,pages.id,pages.number,pages.mark";
		String field="a.id,a.noteContent,a.updateDate,a.userId,a.userName,a.createDate,b.id,b.number,b.mark";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PNote.class.getName());
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
	public List<PNote> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<PNote> list=null;
		String hql=" from PNote a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,noteContent,updateDate,userId,userName,createDate";
		String field="a.id,a.noteContent,a.updateDate,a.userId,a.userName,a.createDate";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PNote.class.getName(),pageCount,page*pageCount);
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
		List<PNote> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from PNote a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", PNote.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
}
