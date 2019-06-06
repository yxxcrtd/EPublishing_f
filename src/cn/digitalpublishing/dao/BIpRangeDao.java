package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.BIpRange;

public class BIpRangeDao extends CommonDao<BIpRange,String> {
	
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;				
		
		/**
		 * ip
		 * */
		if(CollectionsUtil.exist(map, "ip")&&!"".equals(map.get("ip"))){
			if(flag==0){
				whereString+=" where (a.sip <= ? and a.eip>=?) ";
				flag=1;
			}else{
				whereString+=" and (a.sip <= ? and a.eip>=?) ";
			}
			condition.add(map.get("ip"));
			condition.add(map.get("ip"));
		}
		/**
		 * institutionId
		 */
		if(CollectionsUtil.exist(map, "institutionId")&&!"".equals(map.get("institutionId"))){
			if(flag==0){
				whereString+=" where b.id = ?";
				flag=1;
			}else{
				whereString+=" and b.id = ? ";
			}
			condition.add(map.get("institutionId"));
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
	public List<BIpRange> getList(Map<String,Object> condition,String sort)throws Exception{
		List<BIpRange> list=null;
		String hql=" from BIpRange a left join a.institution b";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,institution.id,institution.name,startIp,endIp,sip,eip,institution.logo,institution.logoUrl,institution.logoNote";
		String field="a.id,b.id,b.name,a.startIp,a.endIp,a.sip,a.eip,b.logo,b.logoUrl,b.logoNote";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BIpRange.class.getName());
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
	public List<BIpRange> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<BIpRange> list=null;
		String hql=" from BIpRange a left join a.institution b";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,institution.id,institution.name,startIp,endIp,sip,eip,institution.logo,institution.logoUrl,institution.logoNote";
		String field="a.id,b.id,b.name,a.startIp,a.endIp,a.sip,a.eip,b.logo,b.logoUrl,b.logoNote";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BIpRange.class.getName(),pageCount,page*pageCount);
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
		List<BIpRange> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from BIpRange a left join a.institution b";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", BIpRange.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

}
