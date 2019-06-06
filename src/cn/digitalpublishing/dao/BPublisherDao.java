package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.BPublisher;

public class BPublisherDao extends CommonDao<BPublisher,String> {
	
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;				
		
		if(CollectionsUtil.exist(map, "existid")&&!"".equals(map.get("existid"))){
			if(flag==0){
				whereString+=" where a.id = ?";
				flag=1;
			}else{
				whereString+=" and a.id = ?";
			}
			condition.add(map.get("existid"));
		}
		if(CollectionsUtil.exist(map, "letter")&&!"".equals(map.get("letter"))){
			if(flag==0){
				whereString+=" where lower(a.letter) = ?";
				flag=1;
			}else{
				whereString+=" and lower(a.letter) = ?";
			}
			condition.add(map.get("letter").toString().toLowerCase());
		}
		if(CollectionsUtil.exist(map, "collectionId")&&!"".equals(map.get("collectionId"))){
			if(flag==0){
				whereString+=" where c.id = ?";
				flag=1;
			}else{
				whereString+=" and c.id = ?";
			}
			condition.add(map.get("collectionId").toString().toLowerCase());
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
	public List<BPublisher> getList(Map<String,Object> condition,String sort)throws Exception{
		List<BPublisher> list=null;
		String hql=" from BPublisher a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,name,nameEn,letter,order,browsePrecent,relationCode,logo,desc";
		String field="a.id,a.code,a.name,a.nameEn,a.letter,a.order,a.browsePrecent,a.relationCode,a.logo,a.desc";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BPublisher.class.getName());
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
	public List<BPublisher> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<BPublisher> list=null;
		String hql=" from BPublisher a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,name,nameEn,letter,order,browsePrecent,relationCode,logo,desc";
		String field="a.id,a.code,a.name,a.nameEn,a.letter,a.order,a.browsePrecent,a.relationCode,a.logo,a.desc";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BPublisher.class.getName(),pageCount,page*pageCount);
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
		List<BPublisher> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from BPublisher a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", BPublisher.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

	public List<BPublisher> getPublisherStatistic(Map<String, Object> condition)throws Exception {
		List<BPublisher> list=null;
		String hql=" from BPublisher a ";
		
//		Map<String,Object> t=this.getWhere(condition);
		List<Object> objList =  new ArrayList<Object>();
		String property="id,code,name,nameEn,letter,order,browsePrecent,relationCode,logo,desc,collectionPubContent";
		String field="a.id,a.code,a.name,a.nameEn,a.letter,a.order,a.browsePrecent,a.relationCode,a.logo,a.desc," +
				"(select cast(count(*) as int) from PCcRelation pcc left join pcc.collection c left join pcc.publications p where pcc.publications.status=2 and a.id=pcc.publications.publisher.id " ;

		/**
		 * 1.collectionId 产品包Id
		 */
		if(CollectionsUtil.exist(condition, "collectionId")&&!"".equals(condition.get("collectionId"))){
			field+=" and pcc.collection.id = ?";
			objList.add(condition.get("collectionId").toString());
		}
		/**
		 * 1.subId 分类Id
		 */
		if(CollectionsUtil.exist(condition, "subId")&&!"".equals(condition.get("subId"))){
			field+=" and exists(select psr.publications.id from PCsRelation psr where psr.subject.subject.id=? and psr.publications.id=p.id)";
			objList.add(condition.get("subId").toString());
		}
		/**
		 * 1.type 类型1-电子书 2-期刊 3-章节
		 */
		if(CollectionsUtil.exist(condition, "type")&&!"".equals(condition.get("type"))){
			field+=" and p.type = ?";
			objList.add(Integer.valueOf(condition.get("type").toString()));
		}
		/**
		 * 5.publisherId
		 */
		if(CollectionsUtil.exist(condition, "publisherId")&&!"".equals(condition.get("publisherId"))){
			field+=" and p.publisher.id = ?";
			objList.add(condition.get("publisherId").toString());
		}
		
		field += ")";
		
		StringBuffer where = new StringBuffer();
		if(CollectionsUtil.exist(condition, "publisherId")&&!"".equals(condition.get("publisherId"))){
			where.append(" where a.id = ?");
			objList.add(condition.get("publisherId"));
		}
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+where.toString(), objList.toArray(),"", BPublisher.class.getName());
		}catch(Exception e){
//			throw e;
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<BPublisher> getDistinctPublisher(Map<String,Object> condition)throws Exception{
		List<BPublisher> list=null;
		try{
			String hql=" from PCcRelation a left join a.collection c left join a.publications p left join p.publisher b";
			Map<String,Object> t=this.getWhere(condition);
			String property="id,name,nameEn";
			String field=" distinct b.id,b.name,b.nameEn";
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", BPublisher.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list;
	}
}
