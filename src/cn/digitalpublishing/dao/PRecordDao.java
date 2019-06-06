package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.PRecord;

public class PRecordDao extends CommonDao<PRecord, String> {

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
	public List<PRecord> getList(Map<String,Object> condition,String sort)throws Exception{
		List<PRecord> list=null;
		String hql=" from PRecord a left join a.pages p ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,userId,userName,createDate,pages.number";
		String field="a.id,a.userId,a.userName,a.createDate,p.number";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PRecord.class.getName());
		}catch(Exception e){
//			throw e;
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
	public List<PRecord> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<PRecord> list=null;
		String hql=" from PRecord a left join a.pages p ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,userId,userName,createDate,pages.number";
		String field="a.id,a.userId,a.userName,a.createDate,p.number";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PRecord.class.getName(),pageCount,page*pageCount);
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
		List<PRecord> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from PRecord a left join a.pages p ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", PRecord.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

	public void deleteBySourceId(String sourceId, String userId) throws Exception{
		try {
			this.hibernateDao.executeSql("delete from EPUB_P_RECORD where PUB_ID=? and USER_ID=?", new Object[]{sourceId,userId});
		} catch (Exception e) {
			throw e;
		}
	}
}
