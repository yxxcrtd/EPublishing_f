package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.po.BSource;

public class BSourceDao extends CommonDao<BSource,String> {
	
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * 1.id
		 */
		if(CollectionsUtil.exist(map, "name")&&!"".equals(map.get("name"))){
			if(flag==0){
				whereString+=" where lower(a.name) like ?";
				flag=1;
			}else{
				whereString+=" and lower(a.name) like ?";
			}
			condition.add("%"+map.get("name").toString().trim().toLowerCase()+"%");
		}
		
		/**
		 * 2.code
		 */
		if(CollectionsUtil.exist(map, "code")&&!"".equals(map.get("code"))){
			boolean isPrecise=CollectionsUtil.exist(map, "isPrecise")?Boolean.valueOf(map.get("isPrecise").toString()):true;
			if(isPrecise){
				if(flag==0){
					whereString+=" where lower(a.code) = ?";
					flag=1;
				}else{
					whereString+=" and lower(a.code) = ?";
				}
				condition.add(map.get("code").toString().trim().toLowerCase());
			}else{
				if(flag==0){
					whereString+=" where lower(a.code) like ?";
					flag=1;
				}else{
					whereString+=" and lower(a.code) like ?";
				}
				condition.add("%"+map.get("code").toString().trim().toLowerCase()+"%");
			}
		}
		/**
		 * 3.publisherId
		 */
		if(CollectionsUtil.exist(map, "publisherId")&&!"".equals(map.get("publisherId"))){
			if(flag==0){
				whereString+=" where b.id = ?";
				flag=1;
			}else{
				whereString+=" and b.id = ?";
			}
			condition.add(map.get("publisherId"));
		}
		/**
		 * 1-未免审 2-免审  trial
		 */
		if(CollectionsUtil.exist(map, "trial")&&!"".equals(map.get("trial"))&&map.get("trial")!=null){
			boolean isPrecise=CollectionsUtil.exist(map, "isPrecise")?Boolean.valueOf(map.get("isPrecise").toString()):true;
			if(isPrecise){
				if(flag==0){
					whereString+=" where (a.trial = ? or a.trial is null) ";
					flag=1;
				}else{
					whereString+=" and (a.trial = ? or a.trial is null)";
				}
			}else{
				if(flag==0){
					whereString+=" where a.trial = ?  ";
					flag=1;
				}else{
					whereString+=" and a.trial = ? ";
				}
			}
			condition.add(map.get("trial"));
		}
		
		/**
		 * 4.uniqueId
		 */
		if(CollectionsUtil.exist(map, "uniqueId")&&!"".equals(map.get("uniqueId"))){
			if(flag==0){
				whereString+=" where a.id <> ?";
				flag=1;
			}else{
				whereString+=" and a.id <> ?";
			}
			condition.add(map.get("uniqueId"));
		}
		
		/**
		 * 5.bPubType 1.图书数据
		 */
		if(CollectionsUtil.exist(map, "bPubType")&&!"".equals(map.get("bPubType"))&&map.get("bPubType")!=null){
			if(Boolean.valueOf(map.get("bPubType").toString())){
				if(flag==0){
					whereString+=" where exists(select c.id from TPubTempRelation c where c.source.id=a.id and c.pubType=1 and c.status=2)";
					flag=1;
				}else{
					whereString+=" and exists(select c.id from TPubTempRelation c where c.source.id=a.id and c.pubType=1 and c.status=2)";
				}
			}
		}
		
		/**
		 * 6.jPubType 2.期刊数据
		 */
		if(CollectionsUtil.exist(map, "jPubType")&&!"".equals(map.get("jPubType"))&&map.get("jPubType")!=null){
			if(Boolean.valueOf(map.get("jPubType").toString())){
				if(flag==0){
					whereString+=" where exists(select d.id from TPubTempRelation d where d.source.id=a.id and d.pubType=2 and d.status=2)";
					flag=1;
				}else{
					whereString+=" and exists(select d.id from TPubTempRelation d where d.source.id=a.id and d.pubType=2 and d.status=2)";
				}
			}
		}
				
		/**
		 * 7.type
		 */
		if(CollectionsUtil.exist(map, "type")&&!"".equals(map.get("type"))&&map.get("type")!=null){
			if(flag==0){
				whereString+=" where a.type = ?";
				flag=1;
			}else{
				whereString+=" and a.type = ?";
			}
			condition.add(map.get("type"));
		}
		/**
		 * 7.type
		 */
		if(CollectionsUtil.exist(map, "pubCode")&&!"".equals(map.get("pubCode"))&&map.get("pubCode")!=null){
			if(flag==0){
				whereString+=" where b.code = ?";
				flag=1;
			}else{
				whereString+=" and b.code = ?";
			}
			condition.add(map.get("pubCode"));
		}
		
		/**
		 * 8.sendStauts
		 */
		if(CollectionsUtil.exist(map, "sendStatus")&&map.get("sendStatus")!=null){
			if(Integer.valueOf(map.get("sendStatus").toString())==2){
				if(flag==0){
					whereString+=" where a.sendStatus = ?";
					flag=1;
				}else{
					whereString+=" and a.sendStatus = ?";
				}
				condition.add(map.get("sendStatus"));
			}else if(Integer.valueOf(map.get("sendStatus").toString())==1){
				if(flag==0){
					whereString+=" where (a.sendStatus = ? or a.sendStatus is null) ";
					flag=1;
				}else{
					whereString+=" and (a.sendStatus = ? or a.sendStatus is null) ";
				}
				condition.add(map.get("sendStatus"));
			}
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
	public List<BSource> getList(Map<String,Object> condition,String sort)throws Exception{
		List<BSource> list=null;
		String hql=" from BSource a left join a.publisher b";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,name,code,type,publisher.id,publisher.name";
		String field="a.id,a.name,a.code,a.type,b.id,b.name,b.code";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BSource.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<BSource> getDistinctList(Map<String,Object> condition,String sort)throws Exception{
		List<BSource> list=null;
		String hql=" from BSource a left join a.publisher b";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,trial,name,code,type,publisher.id,publisher.name";
		String field="distinct a.id,a.trial,a.name,a.code,a.type,b.id,b.name";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BSource.class.getName());
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
	public List<BSource> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
 		List<BSource> list=null;
		String hql=" from BSource a left join a.publisher b";
		Map<String,Object> t=this.getWhere(condition);
		String property=" id,trial,name,code,type,publisher.name,unPassed,passed ";
		String field=" a.id,a.trial,a.name,a.code,a.type,b.name,(case when exists(select e.id from TPubTempRelation e where e.source.id=a.id and e.pubType=1 and e.status=2) then 1 else 0 end) as bPubType,(case when exists(select f.id from TPubTempRelation f where f.source.id=a.id and f.pubType=2 and f.status=2) then 1 else 0 end) as jPubType ";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BSource.class.getName(),pageCount,page*pageCount);
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
		List<BSource> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from BSource a left join a.publisher b";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", BSource.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	
}