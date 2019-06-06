package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.OAdvertisement;

/**
 * 广告
 * @author LiuYe
 *
 */
public class OAdvertisementDao extends CommonDao<OAdvertisement,String>{



	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * 1.position
		 */
		if(CollectionsUtil.exist(map, "position")&&!"".equals(map.get("position"))){
				if(flag==0){
					whereString+=" where a.position = ? ";
					flag=1;
				}else{
					whereString+=" and a.position = ? ";
				}
				condition.add(map.get("position"));
		}
		/**
		 * 2.status
		 */
		if(CollectionsUtil.exist(map, "status")&&!"".equals(map.get("status"))){
				if(flag==0){
					whereString+=" where a.status = ? ";
					flag=1;
				}else{
					whereString+=" and a.status = ? ";
				}
				condition.add(map.get("status"));
		}
		if(CollectionsUtil.exist(map, "inDate")&&!"".equals(map.get("inDate"))){
			if(flag==0){
				whereString+=" where a.startTime <= TO_DATE(?,'yyyy-MM-dd') and a.endTime >= TO_DATE(?,'yyyy-MM-dd')";
				flag=1;
			}else{
				whereString+=" and   a.startTime <= TO_DATE(?,'yyyy-MM-dd') and a.endTime >= TO_DATE(?,'yyyy-MM-dd')";
			}
			condition.add(map.get("inDate"));
			condition.add(map.get("inDate"));
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
	public List<OAdvertisement> getList(Map<String,Object> condition,String sort)throws Exception{
		List<OAdvertisement> list=null;
		String hql=" from OAdvertisement a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,category,title,url,content,position,status,startTime,endTime,orderBy,createTime,file,view";
		String field="a.id,a.category,a.title,a.url,a.content,a.position,a.status,a.startTime,a.endTime,a.orderBy,a.createTime,a.file,a.view";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, OAdvertisement.class.getName());
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
	public List<OAdvertisement> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<OAdvertisement> list=null;
		String hql=" from OAdvertisement a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,category,title,url,content,position,status,startTime,endTime,orderBy,createTime,file,view";
		String field="a.id,a.category,a.title,a.url,a.content,a.position,a.status,a.startTime,a.endTime,a.orderBy,a.createTime,a.file,a.view";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, OAdvertisement.class.getName(),pageCount,page*pageCount);
		}catch(Exception e){
			e.printStackTrace();
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
		List<OAdvertisement> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from OAdvertisement a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", OAdvertisement.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

}
