package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.CAlertsWaitingQueue;

public class CAlertsWaitingQueueDao extends CommonDao<CAlertsWaitingQueue,String>  {
	/**
	 * 查询条件
	 * @param map
	 * @return
	 */
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * 1.alertsType 提醒类别：1-出版物；2-学科主题
		 */
		if(CollectionsUtil.exist(map, "alertsType")&&!"".equals(map.get("alertsType"))){
			if(flag==0){
				whereString+=" where a.alertsType = ?";
				flag=1;
			}else{
				whereString+=" and a.alertsType = ?";
			}
			condition.add(Integer.valueOf(map.get("alertsType").toString()));
		}
		/**
		 * 2.alertsFrequency 提醒频率：1-立即;2-每天;3-每周;4-每月
		 */
		if(CollectionsUtil.exist(map, "alertsFrequency")&&!"".equals(map.get("alertsFrequency"))){
			if(flag==0){
				whereString+=" where a.alertsFrequency = ?";
				flag=1;
			}else{
				whereString+=" and a.alertsFrequency = ?";
			}
			condition.add(map.get("alertsFrequency").toString());
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
	public List<CAlertsWaitingQueue> getList(Map<String,Object> condition,String sort)throws Exception{
		List<CAlertsWaitingQueue> list=null;
		String hql=" from CAlertsWaitingQueue a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,userName,subjectName,publicationsId,publicationsName,alertsType,alertsFrequency,isbn,email";
		String field="a.id,a.userName,a.subjectName,a.publicationsId,a.publicationsName,a.alertsType,a.alertsFrequency,a.isbn,a.email";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, CAlertsWaitingQueue.class.getName());
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
	public List<CAlertsWaitingQueue> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<CAlertsWaitingQueue> list=null;
		String hql=" from CAlertsWaitingQueue a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,userName,subjectName,publicationsId,publicationsName,alertsType,alertsFrequency,isbn,email";
		String field="a.id,a.userName,a.subjectName,a.publicationsId,a.publicationsName,a.alertsType,a.alertsFrequency,a.isbn,a.email";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, CAlertsWaitingQueue.class.getName(),pageCount,page*pageCount);
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
		List<CAlertsWaitingQueue> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from CAlertsWaitingQueue a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", CAlertsWaitingQueue.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
}
