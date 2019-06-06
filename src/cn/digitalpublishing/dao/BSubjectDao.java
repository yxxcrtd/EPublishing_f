package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.BSubject;
import cn.digitalpublishing.util.web.IpUtil;

public class BSubjectDao extends CommonDao<BSubject,String> {
	
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
			whereString+=" where a.type=1 ";
			flag=1;
		}else{
			whereString+=" and a.type=1 ";
		}
		
		/*
		 * treeCodeLength treeCode 的长度
		 * */
		if(CollectionsUtil.exist(map, "treeCodeLength")&&!"".equals(map.get("treeCodeLength"))){
			if(flag==0){
				whereString+=" where length(a.treeCode) = ?";
				flag=1;
			}else{
				whereString+=" and length(a.treeCode) = ?";
			}
			condition.add(map.get("treeCodeLength"));
		}
		/*
		 * code 用来查询子分类
		 * */
		if(CollectionsUtil.exist(map, "code")&&!"".equals(map.get("code"))){
			if(flag==0){
				whereString+=" where lower(b.code) = ?";
				flag=1;
			}else{
				whereString+=" and lower(b.code) = ?";
			}
			condition.add(map.get("code").toString().toLowerCase());
		}
		
		/*
		 * parentId 用来查询子分类
		 * */
		if(CollectionsUtil.exist(map, "parentId")&&!"".equals(map.get("parentId"))){
			if(!"0".equals(map.get("parentId"))&&!"1".equals(map.get("parentId"))){
				if(flag==0){
					whereString+=" where b.id = ?";
					flag=1;
				}else{
					whereString+=" and b.id = ?";
				}
				condition.add(map.get("parentId"));
			}else if("1".equals(map.get("parentId"))){
				if(flag==0){
					whereString+=" where b.id is not null ";
					flag=1;
				}else{
					whereString+=" and b.id is not null ";
				}
			}else{
				if(flag==0){
					whereString+=" where length(b.treeCode) = 3";
					flag=1;
				}else{
					whereString+=" and length(b.treeCode) = 3";
				}
			}
		}
		
		/*
		 * publisher
		 */
		if(CollectionsUtil.exist(map, "publisherId")&&!"".equals(map.get("publisherId"))&&map.get("publisherId")!=null){
			if(flag==0){
				whereString+=" where exists(select cs1.id from PCsRelation cs1 where cs1.publications.status=2 and cs1.publications.publisher.id = ?)";
				flag=1;
			}else{
				whereString+=" and exists(select cs1.id from PCsRelation cs1 where cs1.publications.status=2 and cs1.publications.publisher.id = ?)";
			}
			condition.add(map.get("publisherId"));
		}
		
		/*
		 * code 用来查询子分类
		 * */
		if(CollectionsUtil.exist(map, "userSubject")&&!"".equals(map.get("userSubject"))){
			if(flag==0){
				whereString+=" where not exists(select id from CUserAlerts c where c.subject.id=a.id and c.user.id = ?)";
				flag=1;
			}else{
				whereString+=" and not exists(select id from CUserAlerts c where c.subject.id=a.id and c.user.id = ?)";
			}
			condition.add(map.get("userSubject"));
		}
		
		/*
		 * subCode 用来查询子分类
		 * */
		if(CollectionsUtil.exist(map, "subCode")&&!"".equals(map.get("subCode"))&&map.get("subCode")!=null){
			if(flag==0){
				whereString+=" where lower(a.code) = ?";
				flag=1;
			}else{
				whereString+=" and lower(a.code) = ?";
			}
			condition.add(map.get("subCode").toString().toLowerCase());
		}
		if(CollectionsUtil.exist(map, "subCodeTOO")&&!"".equals(map.get("subCodeTOO"))&&map.get("subCodeTOO")!=null){
			if(flag==0){
				whereString+=" where a.code like  ?";
				flag=1;
			}else{
				whereString+=" and a.code like  ?";
			}
			condition.add(map.get("subCodeTOO")+"%");
		}
	/*	if(CollectionsUtil.exist(map, "subTypeTOO")&&!"".equals(map.get("subTypeTOO"))&&map.get("subTypeTOO")!=null){
			if(flag==0){
				whereString+=" where a.type = ?";
				flag=1;
			}else{
				whereString+=" and a.type = ?";
			}
			condition.add(map.get("subTypeTOO"));
		}*/
		/**
		 * subId 用来查询子分类
		 */
		if(CollectionsUtil.exist(map, "subjectId")&&!"".equals(map.get("subjectId"))){
			if(flag==0){
				whereString+=" where a.id = ?";
				flag=1;
			}else{
				whereString+=" and a.id = ?";
			}
			condition.add(map.get("subjectId"));
		}		
		/**
		 * ptype
		 
		if(CollectionsUtil.exist(map, "ptype")&&!"".equals(map.get("ptype"))&&map.get("ptype")!=null){
			if(flag==0){
				whereString+=" where exists(select cs1.id from PCsRelation cs1 where cs1.publications.status=2 and cs1.publications.type = ?)";
				flag=1;
			}else{
				whereString+=" and exists(select cs1.id from PCsRelation cs1 where cs1.publications.status=2 and cs1.publications.type = ?)";
			}
			condition.add(map.get("ptype"));
		}
		*/
		/**
		 * pubYear
		if(CollectionsUtil.exist(map, "pubYear")&&!"".equals(map.get("pubYear"))&&map.get("pubYear")!=null){
			if(flag==0){
				whereString+=" where exists(select cs1.id from PCsRelation cs1 where cs1.publications.status=2 and substring(cs1.publications.pubDate,1,4) = ?)";
				flag=1;
			}else{
				whereString+=" and exists(select cs1.id from PCsRelation cs1 where cs1.publications.status=2 and substring(cs1.publications.pubDate,1,4) = ?)";
			}
			condition.add(map.get("pubYear"));
		}
		*/
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
	public List<BSubject> getList(Map<String,Object> condition,String sort)throws Exception{
		List<BSubject> list=null;
		String hql=" from BSubject a left join a.subject b ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,name,nameEn,type,treeCode,order,desc,standard,subject.id,subject.code,countPP";
		String field="a.id,a.code,a.name,a.nameEn,a.type,a.treeCode,a.order,a.desc,a.standard,b.id,b.code";
		field += ",'0'";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BSubject.class.getName());
		}catch(Exception e){
//			throw e;
			throw e;
		}
		return list;
	}
	/**
	 * 获取分类下所有子级
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<BSubject> getSubjectByListCode(Map<String,Object> condition,String sort)throws Exception{
		List<BSubject> list=null;
		String hql=" from BSubject a ";
		Map<String,Object> t=this.getWhere(condition);
		String property=" id,code,name,nameEn,treeCode ";
		String field=" a.id,a.code,a.name,a.nameEn,a.treeCode ";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BSubject.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	/**
	 * 获取配置列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<BSubject> getList2(Map<String,Object> condition,String sort)throws Exception{
		List<BSubject> list=null;
		String hql=" from BSubject a left join a.subject b ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,name,nameEn,type,treeCode,order,desc,standard,subject.id,subject.code";
		String field="a.id,a.code,a.name,a.nameEn,a.type,a.treeCode,a.order,a.desc,a.standard,b.id,b.code";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BSubject.class.getName());
		}catch(Exception e){
//			throw e;
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
	public List<BSubject> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<BSubject> list=null;
		String hql=" from BSubject a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,name,nameEn,type,treeCode,order,desc,standard";
		String field="a.id,a.code,a.name,a.nameEn,a.type,a.treeCode,a.order,a.desc,a.standard";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BSubject.class.getName(),pageCount,page*pageCount);
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
		List<BSubject> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from BSubject a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", BSubject.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

	@SuppressWarnings("unchecked")
	public List<BSubject> getSubColListCount(Map<String, Object> condition,
			String sort) throws Exception{
		List<BSubject> list=null;
		String hql=" from BSubject a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,name,nameEn,type,treeCode,order,desc,standard,countPP";
		String field="a.id,a.code,a.name,a.nameEn,a.type,a.treeCode,a.order,a.desc,a.standard," +
				"(select cast(count(*) as string) from PCsRelation cs where cs.publications.status=2 and exists(select cc.publications.id from PCcRelation cc where cc.collection.id='"+condition.get("collectionId")+"' and cc.publications.id=cs.publications.id) and cs.subject.treeCode like a.treeCode||'%') ";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BSubject.class.getName());
		}catch(Exception e){
//			throw e;
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<BSubject> getSubOrderListCount(Map<String, Object> condition,String sort)throws Exception {
		List<BSubject> list=null;
		String hql=" from BSubject a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,name,nameEn,type,treeCode,order,desc,standard,countPP";
		String field="a.id,a.code,a.name,a.nameEn,a.type,a.treeCode,a.order,a.desc,a.standard," +
				"(select cast(count(*) as string) from PCsRelation cs where cs.publications.status=2 and  cs.subject.treeCode like a.treeCode||'%' and substring(cs.subject.treeCode,1,length(a.treeCode))=a.treeCode) ";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BSubject.class.getName());
		}catch(Exception e){
//			throw e;
			throw e;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<BSubject> getStatisticList(Map<String, Object> condition,String sort)throws Exception {
		List<BSubject> list=null;
		String hql=" from BSubject a left join a.subject b ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,name,nameEn,subject.id,type";
		String field="a.id,a.code,a.name,a.nameEn,b.id,";
		String complexfield = "select cast(count(distinct p.id) as int) from PCsRelation cs left join cs.publications p where cs.subject.treeCode like a.treeCode||'%' ";
		if(CollectionsUtil.exist(condition, "publisherId")&&!"".equals(condition.get("publisherId"))&&condition.get("publisherId")!=null){
			complexfield +="and cs.publications.publisher.id = '"+condition.get("publisherId")+"'";
		}
		if(CollectionsUtil.exist(condition, "pubYear")&&!"".equals(condition.get("pubYear"))&&condition.get("pubYear")!=null){
			complexfield +="and substring(cs.publications.pubDate,1,4) = '"+condition.get("pubYear")+"'";
		}
		if(CollectionsUtil.exist(condition, "ptype")&&!"".equals(condition.get("ptype"))&&condition.get("ptype")!=null){
			complexfield +="and p.type = "+condition.get("ptype");
		}
		if(CollectionsUtil.exist(condition, "order")&&!"".equals(condition.get("order"))&&condition.get("order")!=null){
			complexfield +=" and lower(cs.publications.title) like '"+condition.get("order").toString().toLowerCase()+"%'";
		}
		if(CollectionsUtil.exist(condition, "status")&&!"".equals(condition.get("status"))&&condition.get("status")!=null){
			complexfield +="and p.status = "+condition.get("status");
		}else{
			complexfield +="and p.status = 2 ";
		}
		if(CollectionsUtil.exist(condition, "mainType")&&!"".equals(condition.get("mainType"))&&"0".equals(condition.get("mainType"))){			
			complexfield +="and p.publications.id is null ";
		}
		/**
		 * isLicense  1、在已订阅中查询 2 、未订阅中
		 * */
		if(CollectionsUtil.exist(condition, "isLicense")&&!"".equals(condition.get("isLicense"))&&condition.get("isLicense")!=null){
			String where1 = "";
			String where2 = "";
			if(CollectionsUtil.exist(condition, "userId")&&!"".equals(condition.get("userId"))){
				where1 = "(select count(*) from LLicense ll where ll.status=1 and ll.user.id='"+condition.get("userId").toString()+"' and ll.publications.id=p.id)";
			}
			if(CollectionsUtil.exist(condition, "ip")&&!"".equals(condition.get("ip"))){
				where2 = "(select count(*) from LLicenseIp lip where lip.license.status=1 and lip.sip<="+IpUtil.getLongIp(condition.get("ip").toString())+" and lip.eip>="+IpUtil.getLongIp(condition.get("ip").toString())+" and lip.license.publications.id=p.id )";
			}
			if("1".equals(condition.get("isLicense").toString())){
				complexfield+=" and ";
				if(!"".equals(where1)&&!"".equals(where2)){
					complexfield += "("+where1+">0 or "+where2+">0 )";
				}else if(!"".equals(where1)){
					complexfield +=  where1+">0 ";
				}else if(!"".equals(where2)){
					complexfield +=  where2+">0 ";
				}
			}else{
				complexfield+=" and ";
				if(!"".equals(where1)&&!"".equals(where2)){
					complexfield += "("+where1+"<=0 and "+where2+"<=0 )";
				}else if(!"".equals(where1)){
					complexfield +=  where1+"<=0 ";
				}else if(!"".equals(where2)){
					complexfield +=  where2+"<=0 ";
				}
			}
		}
		
		
		field +="("+complexfield+") as num ";
		
		try{
			list=super.hibernateDao.getListByHql(property,field,hql+t.get("where").toString()+" and ("+complexfield+")>0 ", ((List<Object>)t.get("condition")).toArray(),sort, BSubject.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	public List<BSubject> getStatisticAllList(Map<String, Object> map,String sort)throws Exception {
		List<BSubject> list=null;
		try{
			String hql=" from PCsRelation a left join a.subject x ";
			String whereString="";
			List<Object> condition=new ArrayList<Object>();
			int flag=0;				
			//这里目前只用到主分类法
			if(flag==0){
				whereString+=" where x.type=1 ";
				flag=1;
			}else{
				whereString+=" and x.type=1 ";
			}
			if(CollectionsUtil.exist(map, "subjectId")&&!"".equals(map.get("subjectId"))&&map.get("subjectId")!=null){
				if(!"0".equals(map.get("subjectId"))){
					if(flag==0){
						whereString+=" where x.subject.id=?";
						flag=1;
					}else{
						whereString+=" and x.subject.id=?";
					}
					condition.add(map.get("subjectId").toString());
				}
			}
			//1.出版商
			if(CollectionsUtil.exist(map, "publisherId")&&!"".equals(map.get("publisherId"))&&map.get("publisherId")!=null){
				if(flag==0){
					whereString+=" where a.publications.publisher.id = ?";
					flag=1;
				}else{
					whereString+=" and a.publications.publisher.id = ?";
				}
				condition.add(map.get("publisherId"));
			}
			//2.出版年份
			if(CollectionsUtil.exist(map, "pubYear")&&!"".equals(map.get("pubYear"))&&map.get("pubYear")!=null){
				if(flag==0){
					whereString+=" where substring(a.publications.pubDate,1,4) = ?";
					flag=1;
				}else{
					whereString+=" and substring(a.publications.pubDate,1,4) = ?";
				}
				condition.add(map.get("pubYear"));
			}
			//3.类型
			if(CollectionsUtil.exist(map, "ptype")&&!"".equals(map.get("ptype"))&&map.get("ptype")!=null){
				if(flag==0){
					whereString+=" where a.publications.type = ?";
					flag=1;
				}else{
					whereString+=" and a.publications.type = ?";
				}
				condition.add(Integer.valueOf(map.get("ptype").toString()));
			}else{
				if(flag==0){
					whereString+=" where a.publications.type in (1,2)";
					flag=1;
				}else{
					whereString+=" and a.publications.type in (1,2)";
				}
			}
			//4.在订阅范围内
			if(CollectionsUtil.exist(map, "isLicense")&&!"".equals(map.get("isLicense"))&&map.get("isLicense")!=null){
				String where1 = "";
				String where2 = "";
				String where3 = "";//OA/免费
				if(CollectionsUtil.exist(map, "userId")&&!"".equals(map.get("userId"))&&map.get("userId")!=null){
					where1+= " (select ll.publications.id from LLicense ll where ll.status=1 and ll.user.id=? and ll.publications.id=a.publications.id)";
					condition.add(map.get("userId"));
				}
				if(CollectionsUtil.exist(map, "institutionId")&&!"".equals(map.get("institutionId"))&&map.get("institutionId")!=null){
					where2+= " (select lip.publications.id from LLicense lip where lip.status=1 and lip.user.level = 2 and lip.user.institution.id = ? and lip.publications.id=a.publications.id)";
					condition.add(map.get("institutionId"));
				}
				where3 = " (a.publications.oa+a.publications.free)>=3 ";
				if(flag==0){
					if(!"".equals(where1)&&!"".equals(where2)){
						whereString+=" where (a.publications.id in("+where1+") or a.publications.id in ("+where2+") or "+where3+" )";
					}
					if(!"".equals(where1)&&"".equals(where2)){
						whereString+=" where (a.publications.id in ("+where1+") or "+where3+" )";
					}
					if("".equals(where1)&&!"".equals(where2)){
						whereString+=" where (a.publications.id in ("+where2+") or "+where3+" )";
					}
					flag=1;
				}else{
					if(!"".equals(where1)&&!"".equals(where2)){
						whereString+=" and (a.publications.id in("+where1+") or a.publications.id in ("+where2+") or "+where3+" )";
					}
					if(!"".equals(where1)&&"".equals(where2)){
						whereString+=" and (a.publications.id in ("+where1+") or "+where3+" )";
					}
					if("".equals(where1)&&!"".equals(where2)){
						whereString+=" and (a.publications.id in ("+where2+") or "+where3+" )";
					}
				}
			}else{
				if(flag==0){
					whereString+=" where a.publications.status=2 ";
					flag=1;
				}else{
					whereString+=" and a.publications.status=2 ";
				}
			}
			//5.A-Z排序
			if(CollectionsUtil.exist(map, "order")&&!"".equals(map.get("order"))&&map.get("order")!=null){
				if(flag==0){
					whereString+=" where lower(a.publications.title) like ?";
					flag=1;
				}else{
					whereString+=" and lower(a.publications.title) like ?";
				}
				condition.add(map.get("order").toString().toLowerCase()+"%");
			}
			String property="id,code,name,nameEn,subject.id,type,order,standard,countPP";
			String field="(select b.id from BSubject b where b.treeCode=substring(x.treeCode,1,"+Integer.valueOf(map.get("length").toString())+")) as id"
				+",(select b.code from BSubject b where b.treeCode=substring(x.treeCode,1,"+Integer.valueOf(map.get("length").toString())+")) as code"
				+",(select b.name from BSubject b where b.treeCode=substring(x.treeCode,1,"+Integer.valueOf(map.get("length").toString())+")) as name"
				+",(select b.nameEn from BSubject b where b.treeCode=substring(x.treeCode,1,"+Integer.valueOf(map.get("length").toString())+")) as nameEn"
				+",(select b.subject.id from BSubject b where b.treeCode=substring(x.treeCode,1,"+Integer.valueOf(map.get("length").toString())+")) as subjectId"
				+",cast(count(*)as string) as num"
				+",(select length(b.name) from BSubject b where b.treeCode=substring(x.treeCode,1,"+Integer.valueOf(map.get("length").toString())+")) as cn_len"
				+",(select length(b.nameEn) from BSubject b where b.treeCode=substring(x.treeCode,1,"+Integer.valueOf(map.get("length").toString())+")) as en_len"
				+",length(cast(count(*)as string)) as nm_len";
			list=super.hibernateDao.getListByHql(property,field,hql+whereString, condition.toArray()," group by substring(x.treeCode,1,"+Integer.valueOf(map.get("length").toString())+") order by substring(x.treeCode,1,"+Integer.valueOf(map.get("length").toString())+")", BSubject.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
}
