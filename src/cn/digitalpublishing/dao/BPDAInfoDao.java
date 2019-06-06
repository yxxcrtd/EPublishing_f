package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.BPDAInfo;

public class BPDAInfoDao extends CommonDao<BPDAInfo, String> {

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
		if(CollectionsUtil.exist(map, "userId")&&!"".equals(map.get("userId"))){
			if(flag==0){
				whereString+=" where u.id = ?";
				flag=1;
			}else{
				whereString+=" and u.id = ?";
			}
			condition.add(map.get("userId"));
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
		if(CollectionsUtil.exist(map, "book")&&!"".equals(map.get("book"))){
			if(flag==0){
				whereString+=" where a.book = ?";
				flag=1;
			}else{
				whereString+=" and a.book = ?";
			}
			condition.add(map.get("book"));
		}
		if(CollectionsUtil.exist(map, "journal")&&!"".equals(map.get("journal"))){
			if(flag==0){
				whereString+=" where a.journal = ?";
				flag=1;
			}else{
				whereString+=" and a.journal = ?";
			}
			condition.add(map.get("journal"));
		}
		if(CollectionsUtil.exist(map, "article")&&!"".equals(map.get("article"))){
			if(flag==0){
				whereString+=" where a.article = ?";
				flag=1;
			}else{
				whereString+=" and a.article = ?";
			}
			condition.add(map.get("article"));
		}
		if(CollectionsUtil.exist(map, "type")&&!"".equals(map.get("type"))){
			if(flag==0){
				whereString+=" where a.type = ?";
				flag=1;
			}else{
				whereString+=" and a.type = ?";
			}
			condition.add(map.get("type"));
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
	public List<BPDAInfo> getList(Map<String,Object> condition,String sort)throws Exception{
		List<BPDAInfo> list=null;
		String hql=" from BPDAInfo a left join a.user u left join a.institution ins ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,times,operation,payment,book,journal,article,type,status";
		String field="a.id,a.times,a.operation,a.payment,a.book,a.journal,a.article,a.type,a.status";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BPDAInfo.class.getName());
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
	public List<BPDAInfo> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<BPDAInfo> list=null;
		String hql=" from BPDAInfo a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,times,operation,payment,book,journal,article,type,status";
		String field="a.id,a.times,a.operation,a.payment,a.book,a.journal,a.article,a.type,a.status";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BPDAInfo.class.getName(),pageCount,page*pageCount);
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
		List<BPDAInfo> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from BPDAInfo a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", BPDAInfo.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	
	

}
