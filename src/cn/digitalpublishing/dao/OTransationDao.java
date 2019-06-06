package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.OTransation;

public class OTransationDao extends CommonDao<OTransation,String> {
	
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;				
		
		if(CollectionsUtil.exist(map, "userId")&&!"".equals(map.get("userId"))){
			if(flag==0){
				whereString+=" where b.id = ?";
				flag=1;
			}else{
				whereString+=" and b.id = ?";
			}
			condition.add(map.get("userId"));
		}
		if(CollectionsUtil.exist(map,"total")&&!"".equals(map.get("total"))){
			if(">0".equals(map.get("total"))){
				if(flag==0){
					whereString+=" where a.amount > ?";
					flag=1;
				}else{
					whereString+=" and a.amount > ?";
				}
			}else if("<0".equals(map.get("total"))){
				if(flag==0){
					whereString+=" where a.amount < ?";
					flag=1;
				}else{
					whereString+=" and a.amount < ?";
				}
			}
			condition.add(0d);
		}
		if(CollectionsUtil.exist(map, "type")&&!"".equals(map.get("type"))){
			if(Integer.valueOf(map.get("type").toString())==1){
				if(flag==0){
					whereString+=" where a.type = ?";
					flag=1;
				}else{
					whereString+=" and a.type = ?";
				}
				condition.add(1);
			}else if(Integer.valueOf(map.get("type").toString())==2){
				if(flag==0){
					whereString+=" where (a.type = ? or a.type = ? or a.type = ?) ";
					flag=1;
				}else{
					whereString+=" and (a.type = ? or a.type = ? or a.type = ?) ";
				}
				condition.add(2);
				condition.add(3);
				condition.add(4);
			}else if(Integer.valueOf(map.get("type").toString())==3){
				if(flag==0){
					whereString+=" where a.type = ? ";
					flag=1;
				}else{
					whereString+=" and a.type = ? ";
				}
				condition.add(5);
			}
		}
		if(CollectionsUtil.exist(map, "detailId")&&!"".equals(map.get("detailId"))){
			if(flag==0){
				whereString+=" where d.id = ?";
				flag=1;
			}else{
				whereString+=" and d.id = ?";
			}
			condition.add(map.get("detailId"));
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
	public List<OTransation> getList(Map<String,Object> condition,String sort)throws Exception{
		List<OTransation> list=null;
		String hql=" from OTransation a left join a.user b left join a.order c left join a.orderDetail d ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,orderCode,amount,type,createdby,createdon,updatedby,updatedon,user.id,user.name,user.level,order.id,order.name,order.payType";
		String field="a.id,a.orderCode,a.amount,a.type,a.createdby,a.createdon,a.updatedby,a.updatedon,b.id,b.name,b.level,c.id,c.name,c.payType";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, OTransation.class.getName());
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
	public List<OTransation> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<OTransation> list=null;
		String hql=" from OTransation a left join a.user b left join a.order c";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,orderCode,amount,type,createdby,createdon,updatedby,updatedon,user.id,user.name,user.level,order.id,order.name,order.payType";
		String field="a.id,a.orderCode,a.amount,a.type,a.createdby,a.createdon,a.updatedby,a.updatedon,b.id,b.name,b.level,c.id,c.name,c.payType";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, OTransation.class.getName(),pageCount,page*pageCount);
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<OTransation> getLogPagingList(Map<String,Object> condition,Integer pageCount,Integer page)throws Exception{
		List<OTransation> list=null;
		String hql=" from OTransation a left join a.user b left join a.order c";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,orderCode,type,amount,createdon,user.id,user.name,order.id,order.name";
		String field="a.id,a.orderCode,a.type,a.amount,a.createdon,b.id,b.name,c.id,c.name";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray()," order by a.createdon desc ", OTransation.class.getName(),pageCount,page*pageCount);
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
	public Integer getLogCount(Map<String,Object> condition)throws Exception{
		List<OTransation> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from OTransation a left join a.user b left join a.order c";
		try{
			list=this.hibernateDao.getListByHql("orderCode","a.orderCode", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray()," order by a.createdon", OTransation.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:list.size();
	}
	/**
	 * 获取总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer getCount(Map<String,Object> condition)throws Exception{
		List<OTransation> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from OTransation a left join a.user b left join a.order c";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", OTransation.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	
	public double getTotal(Map<String,Object> condition)throws Exception{
		List<OTransation> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from OTransation a  left join a.user b left join a.orderDetail d ";
		try{
			list=this.hibernateDao.getListByHql(" amount "," sum(a.amount) ", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", OTransation.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0d:(list.get(0).getAmount()==null?0d:list.get(0).getAmount());
	}
}
