package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.com.daxtech.framework.util.DateUtil;
import cn.digitalpublishing.ep.po.OOrder;

public class OOrderDao extends CommonDao<OOrder, String> {
	
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * 1.status 状态 1-待处理 2-处理中 3-处理完成 
		 */
		if(CollectionsUtil.exist(map, "status")&&!"".equals(map.get("status"))){
			if(flag==0){
				whereString+=" where a.status = ?";
				flag=1;
			}else{
				whereString+=" and a.status = ?";
			}
			condition.add(Integer.valueOf(map.get("status").toString()));
		}
		/**
		 * startTime 开始时间
		 */
		if(CollectionsUtil.exist(map, "startTime")&&!"".equals(map.get("startTime"))){
			if(flag==0){
				whereString+=" where a.createdon >= ?";
				flag=1;
			}else{
				whereString+=" and a.createdon >= ?";
			}
			condition.add(DateUtil.toDate(map.get("startTime").toString(), "yyyy-MM-dd"));
		}
		/**
		 * endTime 结束时间
		 */
		if(CollectionsUtil.exist(map, "endTime")&&!"".equals(map.get("endTime"))){
			if(flag==0){
				whereString+=" where a.createdon <= ?";
				flag=1;
			}else{
				whereString+=" and a.createdon <= ?";
			}
			condition.add(DateUtil.toDate(map.get("endTime").toString(), "yyyy-MM-dd"));
		}
		/**
		 * 1.allStatus 状态 1-待处理 2-处理中 3-处理完成 .格式：1,2,3
		 */
		if(CollectionsUtil.exist(map, "allStatus")&&!"".equals(map.get("allStatus"))){
			String[] a = map.get("allStatus").toString().split(",");
			String hql = "( a.status=? ";
			condition.add(Integer.valueOf(a[0]));
			for(int i=1;i<a.length;i++){
				hql += " or a.status=? ";
				condition.add(Integer.valueOf(a[i]));
			}
			hql += ")";
			if(flag==0){
				whereString+=" where "+hql;
				flag=1;
			}else{
				whereString+=" and "+hql;
			}
		}
		/**
		 * 1.userId 用户ID
		 */
		if(CollectionsUtil.exist(map, "userId")&&!"".equals(map.get("userId"))){
			if(flag==0){
				whereString+=" where b.id = ?";
				flag=1;
			}else{
				whereString+=" and b.id = ?";
			}
			condition.add(map.get("userId").toString());
		}
		/**
		 * code
		 */
		if(CollectionsUtil.exist(map, "code")&&!"".equals(map.get("code"))){
			if(flag==0){
				whereString+=" where a.code = ?";
				flag=1;
			}else{
				whereString+=" and a.code = ?";
			}
			condition.add(map.get("code").toString());
		}
		
		
		/**
		 * 发送状态 sendStatus
		 */
		if(CollectionsUtil.exist(map, "sendStatus")&&!"".equals(map.get("sendStatus"))&&map.get("sendStatus")!=null){
			if(Integer.valueOf(map.get("sendStatus").toString())==1){
				if(flag==0){
					whereString+=" where (a.sendStatus = ? or a.sendStatus is null)";
					flag=1;
				}else{
					whereString+=" and (a.sendStatus = ? or a.sendStatus is null)";
				}
				condition.add(map.get("sendStatus"));
			}else{
				if(flag==0){
					whereString+=" where a.sendStatus = ? ";
					flag=1;
				}else{
					whereString+=" and a.sendStatus = ? ";
				}
				condition.add(map.get("sendStatus"));
			}
		}
		
		table.put("where",whereString);
		table.put("condition", condition);
		return table;
	}
	
	/**
	 * 获取订单列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<OOrder> getList(Map<String,Object> condition,String sort)throws Exception{
		List<OOrder> list=null;
		String hql=" from OOrder a left join a.user b ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,sendStatus,tradeNo,name,ip,currency,salePrice,tax,salePriceExtTax,status,quantity," +
				"createdby,createdon,updatedby,updatedon,payType,user.id,user.name";
		String field="a.id,a.code,a.sendStatus,a.tradeNo,a.name,a.ip,a.currency,a.salePrice,a.tax,a.salePriceExtTax,a.status,a.quantity," +
				"a.createdby,a.createdon,a.updatedby,a.updatedon,a.payType,b.id,b.name";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, OOrder.class.getName());
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
	public List<OOrder> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<OOrder> list=null;
		String hql=" from OOrder a left join a.user b ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,name,ip,currency,salePrice,tax,salePriceExtTax,status,quantity," +
				"createdby,createdon,updatedby,updatedon,payType";
		String field="a.id,a.code,a.name,a.ip,a.currency,a.salePrice,a.tax,a.salePriceExtTax,a.status,a.quantity," +
				"a.createdby,a.createdon,a.updatedby,a.updatedon,a.payType";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, OOrder.class.getName(),pageCount,page*pageCount);
		}catch(Exception e){
			throw e;
//			e.printStackTrace();
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
		List<OOrder> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from OOrder a left join a.user b ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", OOrder.class.getName());
		}catch(Exception e){
//			e.printStackTrace();
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
}
