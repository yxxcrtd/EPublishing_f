package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.BPDACounter;

public class BPDACounterDao extends CommonDao<BPDACounter, String> {

	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * 1.id
		 */
		if(CollectionsUtil.exist(map, "id")&&!"".equals(map.get("id"))){
			if(flag==0){
				whereString+=" where a.id = ?";
				flag=1;
			}else{
				whereString+=" and a.id = ?";
			}
			condition.add(map.get("id"));
		}
		if(CollectionsUtil.exist(map, "pubId")&&!"".equals(map.get("pubId"))){
			if(flag==0){
				whereString+=" where pub.id = ?";
				flag=1;
			}else{
				whereString+=" and pub.id = ?";
			}
			condition.add(map.get("pubId"));
		}
		if(CollectionsUtil.exist(map, "pubStatus")&&!"".equals(map.get("pubStatus"))){
			if(flag==0){
				whereString+=" where pub.status = ?";
				flag=1;
			}else{
				whereString+=" and pub.status = ?";
			}
			condition.add(map.get("pubStatus"));
		}
		if(CollectionsUtil.exist(map, "insId")&&!"".equals(map.get("insId"))){
			if(flag==0){
				whereString+=" where ins.id = ?";
				flag=1;
			}else{
				whereString+=" and ins.id = ?";
			}
			condition.add(map.get("insId"));
		}	
		if(CollectionsUtil.exist(map, "userId")&&!"".equals(map.get("userId"))){
			if(flag==0){
				whereString+=" where u.id = ?";
				flag=1;
			}else{
				whereString+=" and u.id = ?";
			}
			condition.add(map.get("userId"));
		}
		if(CollectionsUtil.exist(map, "pdaId")&&!"".equals(map.get("pdaId"))){
			if(flag==0){
				whereString+=" where info.id = ?";
				flag=1;
			}else{
				whereString+=" and info.id = ?";
			}
			condition.add(map.get("pdaId"));
		}
		if(CollectionsUtil.exist(map, "status")&&!"".equals(map.get("status"))){
			if(flag==0){
				whereString+=" where a.status = ?";
				flag=1;
			}else{
				whereString+=" and a.status = ?";
			}
			condition.add(map.get("status"));
		}
		if(CollectionsUtil.exist(map, "isReady")&&!"".equals(map.get("isReady"))){
			if(flag==0){
				whereString+=" where a.value >= info.times ";
				flag=1;
			}else{
				whereString+=" and a.value >= info.times ";
			}
		}
		table.put("where",whereString);
		table.put("condition", condition);
		return table;
	}
	
	/**
	 * 获取PDA计数列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<BPDACounter> getList(Map<String,Object> condition,String sort)throws Exception{
		List<BPDACounter> list=null;
		String hql=" from BPDACounter a left join a.publications pub left join a.pdaInfo info left join info.institution ins left join info.user u ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,value,status,startDate,endDate,publications.id,pdaInfo.id,pdaInfo.times,pdaInfo.operation,pdaInfo.payment ,pdaInfo.institution.id,pdaInfo.user.id";
		String field="a.id,a.value,a.status,a.startDate,a.endDate,pub.id,info.id,info.times,info.operation,info.payment,ins.id,u.id";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BPDACounter.class.getName());
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
	public List<BPDACounter> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<BPDACounter> list=null;
		String hql=" from BPDACounter a left join a.publications pub left join a.pdaInfo info left join info.institution ins left join info.user u ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,value,status,startDate,endDate,publications.id,pdaInfo.id,pdaInfo.operation,pdaInfo.payment ,pdaInfo.instition.id,pdaInfo.user.id";
		String field="a.id,a.value,a.status,a.startDate,a.endDate,pub.id,info.id,info.operation,info.payment,ins.id,u.id";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BPDACounter.class.getName(),pageCount,page*pageCount);
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
		List<BPDACounter> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from BPDACounter a left join a.publications pub left join a.pdaInfo info left join info.institution ins left join info.user u ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", BPDACounter.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	
	

}
