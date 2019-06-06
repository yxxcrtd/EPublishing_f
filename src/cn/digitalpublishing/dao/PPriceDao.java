package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.PPrice;

public class PPriceDao extends CommonDao<PPrice, String> {

	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		
		if(!CollectionsUtil.exist(map, "del") && "".equals(map.get("del"))){
			if(flag==0){//已上架
				whereString += " where a.publications.status=2 ";
				flag=1;
			}else{
				whereString += " and a.publications.status=2 ";
			}
			
			if(flag==0){//有效价格
				whereString += " where (a.status=2 or a.status=null )";
				flag=1;
			}else{
				whereString += " and (a.status=2 or a.status=null )";
			}
		}
		/**
		 * 1.publicationsid
		 */
		if(CollectionsUtil.exist(map, "publicationsid")&&!"".equals(map.get("publicationsid"))){
			if(flag==0){
				whereString+=" where a.publications.id = ?";
				flag=1;
			}else{
				whereString+=" and a.publications.id = ?";
			}
			condition.add(map.get("publicationsid").toString());
		}
		/**
		 * 1.publicationss
		 */
		if(CollectionsUtil.exist(map, "BookPublications")&&!"".equals(map.get("BookPublications"))){
			if(flag==0){
				whereString+=" where b.publications.id = ? ";
				flag=1;
			}else{
				whereString+=" and b.publications.id = ? ";
			}
			condition.add(map.get("BookPublications").toString());
		}
		/**
		 * 1.publicationss
		 */
		if(CollectionsUtil.exist(map, "chaperPublications")&&!"".equals(map.get("chaperPublications"))){
			if(flag==0){
				whereString+=" where a.id = ? ";
				flag=1;
			}else{
				whereString+=" and a.id = ? ";
			}
			condition.add(map.get("chaperPublications").toString());
		}
		
		/**
		 * 2.priceId
		 */
		if(CollectionsUtil.exist(map, "priceId")&&!"".equals(map.get("priceId"))){
			if(flag==0){
				whereString+=" where a.id = ?";
				flag=1;
			}else{
				whereString+=" and a.id = ?";
			}
			condition.add(map.get("priceId").toString());
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
		
		if(CollectionsUtil.exist(map, "userTypeId")&&!"".equals(map.get("userTypeId"))){
			if(flag==0){
				whereString+=" where exists(select t.priceType.id from UPRelation t where t.userType.id=? and t.priceType.id=c.id)";
				flag=1;
			}else{
				whereString+=" and exists(select t.priceType.id from UPRelation t where t.userType.id=? and t.priceType.id=c.id)";
			}
			condition.add(map.get("userTypeId"));
		}
		if(CollectionsUtil.exist(map, "typeCode")&&!"".equals(map.get("typeCode"))){
			if(flag==0){
				whereString+=" where c.code = ?";
				flag=1;
			}else{
				whereString+=" and c.code = ?";
			}
			condition.add(map.get("typeCode"));
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
	public List<PPrice> getList(Map<String,Object> condition,int i,String sort)throws Exception{
		List<PPrice> list=null;
		String hql=" from PPrice a left join a.publications b left join a.priceType c ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,publications.id,code,name,price,currency,type,effective,complicating,status,priceType.id,priceType.code,priceType.name";
		String field="a.id,b.id,a.code,a.name,a.price,a.currency,a.type,a.effective,a.complicating,a.status,c.id,c.code,c.name";
		try{
			if(i==2){
				list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString()+" and a.status = 2", ((List<Object>)t.get("condition")).toArray(),sort, PPrice.class.getName());
			}else{
				list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PPrice.class.getName());
			}
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
	public List<PPrice> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<PPrice> list=null;
		String hql=" from PPrice a left join a.publications b ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,publications.id,code,name,price,currency,type,effective,complicating";
		String field="a.id,b.id,a.code,a.name,a.price,a.currency,a.type,a.effective,a.complicating";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PPrice.class.getName(),pageCount,page*pageCount);
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
		List<PPrice> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from PPrice a left join PPublications b ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", PPrice.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	
	/**
	 * 假删除价格信息
	 * @param pm
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteByCondition(Map<String, Object> pm)throws Exception {
		Map<String,Object> t=this.getWhere(pm);
		String hql=" update PPrice a set a.status=1 ";
		try{
			this.hibernateDao.executeHql(hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 删除除章节价格信息
	 * @param pm
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteChaper(Map<String, Object> pm)throws Exception {
		Map<String,Object> t=this.getWhere(pm);
		String hql=" delete from PPrice a ";
		try{
			this.hibernateDao.executeHql(hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}
