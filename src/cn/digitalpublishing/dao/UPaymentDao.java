package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.UPayment;

public class UPaymentDao extends CommonDao<UPayment, String> {

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
		/**
		 * 1.uTypeId
		 */
		if(CollectionsUtil.exist(map, "userTypeId")&&!"".equals(map.get("userTypeId"))){
			if(flag==0){
				whereString+=" where b.id = ?";
				flag=1;
			}else{
				whereString+=" and b.id = ?";
			}
			condition.add(map.get("userTypeId").toString());
		}
		/**
		 * 3.payType
		 */
		if(CollectionsUtil.exist(map, "payType")&&!"".equals(map.get("payType"))){
			if(flag==0){
				whereString+=" where a.payType = ?";
				flag=1;
			}else{
				whereString+=" and a.payType = ?";
			}
			condition.add(map.get("payType"));
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
	public List<UPayment> getList(Map<String,Object> condition,String sort)throws Exception{
		List<UPayment> list=null;
		String hql=" from UPayment a left join a.userType b ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,payType,userType.id,userType.name,userType.code";
		String field="a.id,a.payType,b.id,b.name,b.code";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, UPayment.class.getName());
		}catch(Exception e){
			e.printStackTrace();
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
	public List<UPayment> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<UPayment> list=null;
		String hql=" from UPayment a left join a.userType b ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,payType,userType.id,userType.name,userType.code";
		String field="a.id,a.payType,b.id,b.name,b.code";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, UPayment.class.getName(),pageCount,page*pageCount);
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
		List<UPayment> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from UPayment a left join a.userType b ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", UPayment.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

	public void deleteByCondtion(Map<String, Object> condition) throws Exception{
		try {
			String hql = "delete from UPayment a ";
			List<Object> list = new ArrayList<Object>();
			int flag = 0;
			/**
			 * 1.uTypeId
			 */
			if(CollectionsUtil.exist(condition, "uTypeId")&&!"".equals(condition.get("uTypeId"))){
				if(flag==0){
					hql+=" where a.userType.id = ?";
					flag=1;
				}else{
					hql+=" and a.userType.id = ?";
				}
				list.add(condition.get("uTypeId"));
			}
			
			this.hibernateDao.executeHql(hql, list.toArray());
		} catch (Exception e) {
			throw e;
		}
	}
	
}
