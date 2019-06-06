package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.BSubject;
import cn.digitalpublishing.ep.po.MMarkData;

public class MMarkDataDao extends CommonDao<MMarkData, String> {

	/**
	 * @param map
	 * @return
	 */
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;				
		//这里目前只用到主分类法
		if(flag==0){
			whereString+=" where a.status=1 ";
			flag=1;
		}else{
			whereString+=" and a.status=1 ";
		}
		
		/*
		 * institution
		 * */
		if(CollectionsUtil.exist(map, "institution")&&!"".equals(map.get("institution"))){
			if(flag==0){
				whereString+=" where a.institution.id = ?";
				flag=1;
			}else{
				whereString+=" and a.institution.id = ?";
			}
			condition.add(map.get("institution"));
		}
		
		table.put("where",whereString);
		table.put("condition", condition);
		return table;
	}
	
	/**
	 * 获取总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getCount(Map<String, Object> condition) throws Exception{
		List<MMarkData> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from MMarkData a  left join a.institution b ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", MMarkData.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

	/**
	 * 获取分页列表
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<MMarkData> getPagingList(Map<String, Object> condition,
			String sort, int pageCount, int curpage)throws Exception {
		List<MMarkData> list = null;
		String hql=" from MMarkData a left join a.institution b ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,name,path,createDate,createOn, createBy,status,institution.id,institution.name,institution.code";
		String field="a.id,a.name,a.path,a.createDate,a.createOn,a.createBy,a.status,b.id,b.name,b.code";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, MMarkData.class.getName(),pageCount,curpage*pageCount);
		}catch(Exception e){
			throw e;
		}
		return list;
	}

}
