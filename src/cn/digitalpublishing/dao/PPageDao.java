package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.PPage;

public class PPageDao extends CommonDao<PPage, String> {

	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * 1.sourceId
		 */
		if(CollectionsUtil.exist(map, "sourceId")&&!"".equals(map.get("sourceId"))){
				if(flag==0){
					whereString+=" where a.publications.id = ? ";
					flag=1;
				}else{
					whereString+=" and a.publications.id = ? ";
				}
				condition.add(map.get("sourceId").toString());
		}
		/**
		 * 1.contentId
		 */
		if(CollectionsUtil.exist(map, "contentId")&&!"".equals(map.get("contentId"))){
				if(flag==0){
					whereString+=" where b.id = ? ";
					flag=1;
				}else{
					whereString+=" and b.id = ? ";
				}
				condition.add(map.get("contentId").toString());
		}

		/**
		 * 1.nextPage
		 */
		if(CollectionsUtil.exist(map, "nextPage")&&!"".equals(map.get("nextPage"))){
				if(flag==0){
					whereString+=" where a.number = ? ";
					flag=1;
				}else{
					whereString+=" and a.number = ? ";
				}
				condition.add(map.get("nextPage"));
		}
		if(CollectionsUtil.exist(map, "tag")&&!"".equals(map.get("tag"))){
			if(flag==0){
				whereString+=" where a.mark = ? ";
				flag=1;
			}else{
				whereString+=" and a.mark = ? ";
			}
			condition.add(map.get("tag"));
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
	public List<PPage> getList(Map<String,Object> condition,String sort)throws Exception{
		List<PPage> list=null;
		String hql=" from PPage a left join a.publications b left join b.publisher pp ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,pdf,swf,number,mark,publications.type,publications.publisher.browsePrecent,publications.publisher.downloadPercent,publications.publisher.printPercent,"
				+"publications.publisher.journalBrowse,publications.publisher.journalPrint,publications.publisher.journalDownload,publications.id,publications.oa,publications.free";
		String field="a.id,a.pdf,a.swf,a.number,a.mark,b.type,pp.browsePrecent,pp.downloadPercent,pp.printPercent,pp.journalBrowse,pp.journalPrint,pp.journalDownload,b.id,b.oa,b.free";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PPage.class.getName());
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
	public List<PPage> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<PPage> list=null;
		String hql=" from PPage a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,pdf,swf,number,mark";
		String field="a.id,a.pdf,a.swf,a.number,a.mark";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PPage.class.getName(),pageCount,page*pageCount);
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
		List<PPage> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from PPage a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", PPage.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

	@SuppressWarnings("unchecked")
	public void deleteByCondition(Map<String, Object> condition)throws Exception {
		Map<String,Object> t=this.getWhere(condition);
		String hql="delete from PPage a ";
		try{
			this.hibernateDao.executeHql(hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray());
		}catch(Exception e){
			throw e;
		}
	}
}
