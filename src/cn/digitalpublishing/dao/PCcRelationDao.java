package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.PCcRelation;
import cn.digitalpublishing.util.web.IpUtil;

public class PCcRelationDao extends CommonDao<PCcRelation, String> {

	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		if(flag==0){
			whereString += " where a.publications.status=2 ";
			flag=1;
		}else{
			whereString += " and a.publications.status=2 ";
		}
		/**
		 * 1.collectionId 产品包Id
		 */
		if(CollectionsUtil.exist(map, "collectionId")&&!"".equals(map.get("collectionId"))){
			if(flag==0){
				whereString+=" where c.id = ?";
				flag=1;
			}else{
				whereString+=" and c.id = ?";
			}
			condition.add(map.get("collectionId").toString());
		}
		/**
		 * 1.subId 分类Id
		 */
		if(CollectionsUtil.exist(map, "ssubId")&&!"".equals(map.get("ssubId"))){
			if(flag==0){
				whereString+=" where exists(select psr.publications.id from PCsRelation psr where (psr.subject.subject.id=? or psr.subject.id=? ) and psr.publications.id=p.id)";
				flag=1;
			}else{
				whereString+=" and exists(select psr.publications.id from PCsRelation psr where (psr.subject.subject.id=? or psr.subject.id=? ) and psr.publications.id=p.id)";
			}
			condition.add(map.get("ssubId").toString());
			condition.add(map.get("ssubId").toString());
		}
		
		/**
		 * 1.subId 分类Id
		 */
		if(CollectionsUtil.exist(map, "subId")&&!"".equals(map.get("subId"))){
			if(flag==0){
				whereString+=" where exists(select psr.publications.id from PCsRelation psr where  psr.subject.id=?  and psr.publications.id=p.id)";
				flag=1;
			}else{
				whereString+=" and exists(select psr.publications.id from PCsRelation psr where  psr.subject.id=?  and psr.publications.id=p.id)";
			}
			condition.add(map.get("subId").toString());
		}
		
		/**
		 * 1.type 类型1-电子书 2-期刊 3-章节
		 */
		if(CollectionsUtil.exist(map, "type")&&!"".equals(map.get("type"))){
			if(flag==0){
				whereString+=" where p.type = ? ";
				flag=1;
			}else{
				whereString+=" and p.type = ?";
			}
			condition.add(Integer.valueOf(map.get("type").toString()));
		}
		/**
		 * 4.publicationsid
		 */
		if(CollectionsUtil.exist(map, "publicationsid")&&!"".equals(map.get("publicationsid"))){
			if(flag==0){
				whereString+=" where p.id = ? ";
				flag=1;
			}else{
				whereString+=" and p.id = ?";
			}
			condition.add(map.get("publicationsid").toString());
		}
		
		/**
		 * 5.publisherId
		 */
		if(CollectionsUtil.exist(map, "publisherId")&&!"".equals(map.get("publisherId"))){
			if(flag==0){
				whereString+=" where p.publisher.id = ? ";
				flag=1;
			}else{
				whereString+=" and p.publisher.id = ?";
			}
			condition.add(map.get("publisherId").toString());
		}
		
		/**
		 * collectionStatus
		 */
		if(CollectionsUtil.exist(map, "collectionStatus")&&!"".equals(map.get("collectionStatus"))){
			if(flag==0){
				whereString+=" where c.status = ? ";
				flag=1;
			}else{
				whereString+=" and c.status = ? ";
			}
			condition.add(map.get("collectionStatus"));
		}
		/**
		 * lang语言种类
		 */
		if(CollectionsUtil.exist(map, "lang")&&!"".equals(map.get("lang"))){
			if(flag==0){
				whereString+=" where lower(p.lang) = ? ";
				flag=1;
			}else{
				whereString+=" and lower(p.lang) = ? ";
			}
			condition.add(map.get("lang").toString().toLowerCase());
		}
		/**
		 * -lang语言种类
		 */
		if(CollectionsUtil.exist(map, "-lang")&&!"".equals(map.get("-lang"))){
			if(flag==0){
				whereString+=" where p.lang != ? ";
				flag=1;
			}else{
				whereString+=" and p.lang != ? ";
			}
			condition.add(map.get("-lang"));
		}
		if (CollectionsUtil.exist(map, "language") && !"".equals(map.get("language")) && map.get("language") != null) {

			if (flag == 0) {
				whereString += " where p.lang like ? ";
				flag = 1;
			} else {
				whereString += " and p.lang like ? ";
			}
			condition.add(map.get("language"));

		}

		if (CollectionsUtil.exist(map, "languageEn") && !"".equals(map.get("languageEn")) && map.get("languageEn") != null) {

			if (flag == 0) {
				whereString += " where p.lang not like ? ";
				flag = 1;
			} else {
				whereString += " and p.lang not like ? ";
			}
			condition.add(map.get("languageEn"));

		}
		if(CollectionsUtil.exist(map, "searchValue")&&!"".equals(map.get("searchValue"))){
			if(flag==0){
				whereString+=" where LOWER(p.title) like ? or LOWER(p.author) like ? or LOWER(p.code) like ? or LOWER(p.publisher.name) like ? or LOWER(p.publisher.nameEn) like ? ";
				flag=1;
			}else{
				whereString+=" and LOWER(p.title) like ? or LOWER(p.author) like ? or LOWER(p.code) like ? or LOWER(p.publisher.name) like ? or LOWER(p.publisher.nameEn) like ? ";
			}
			condition.add("%"+map.get("searchValue").toString().toLowerCase()+"%");
			condition.add("%"+map.get("searchValue").toString().toLowerCase()+"%");
			condition.add("%"+map.get("searchValue").toString().toLowerCase()+"%");
			condition.add("%"+map.get("searchValue").toString().toLowerCase()+"%");
			condition.add("%"+map.get("searchValue").toString().toLowerCase()+"%");
		}
		
		if(CollectionsUtil.exist(map, "searchTitle")&&!"".equals(map.get("searchTitle"))){
			if(flag==0){
				whereString+=" where LOWER(p.title) like ? ";
				flag=1;
			}else{
				whereString+=" and LOWER(p.title) like ? ";
			}
			condition.add("%"+map.get("searchTitle").toString().toLowerCase()+"%");
		}
		
		if(CollectionsUtil.exist(map, "searchAuthor")&&!"".equals(map.get("searchAuthor"))){
			if(flag==0){
				whereString+=" where LOWER(p.author) like ? ";
				flag=1;
			}else{
				whereString+=" and LOWER(p.author) like ? ";
			}
			condition.add("%"+map.get("searchAuthor").toString().toLowerCase()+"%");
		}
		
		if(CollectionsUtil.exist(map, "searchCode")&&!"".equals(map.get("searchCode"))){
			if(flag==0){
				whereString+=" where LOWER(p.code) like ? ";
				flag=1;
			}else{
				whereString+=" and LOWER(p.code) like ? ";
			}
			condition.add("%"+map.get("searchCode").toString().toLowerCase()+"%");
		}
		
		if(CollectionsUtil.exist(map, "searchPublisherName")&&!"".equals(map.get("searchPublisherName"))){
			if(flag==0){
				whereString+=" where LOWER(p.publisher.nameEn) like ? or LOWER(p.publisher.name) like ? ";
				flag=1;
			}else{
				whereString+=" and LOWER(p.publisher.nameEn) like ? or LOWER(p.publisher.name) like ? ";
			}
			condition.add("%"+map.get("searchPublisherName").toString().toLowerCase()+"%");
			condition.add("%"+map.get("searchPublisherName").toString().toLowerCase()+"%");
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
//	@SuppressWarnings("unchecked")
//	public List<PCcRelation> getList(Map<String,Object> condition,String sort)throws Exception{
//		List<PCcRelation> list=null;
//		String hql=" from PCcRelation a ";
//		Map<String,Object> t=this.getWhere(condition);
//		String property="id,code,desc";
//		String field="a.id,a.code,a.desc";
//		try{
//			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PCcRelation.class.getName());
//		}catch(Exception e){
//			throw e;
//		}
//		return list;
//	}
	
	/**
	 * 获取分页信息
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 * @author LiuYe刘冶 加了些注释 因为发现这些查询条件的限制 导致结果不正确
	 */
	@SuppressWarnings("unchecked")
	public List<PCcRelation> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page,CUser user,String ip)throws Exception{
		List<PCcRelation> list=null;
		String hql=" from PCcRelation a left join a.collection c left join a.publications p ";
		String targetDate = this.getDistryDate();
		Map<String,Object> t=this.getWhere(condition);
		List<Object> con = new ArrayList<Object>();
		if(user!=null){
			con.add(user.getId());
		}
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		for(Object o:(List<Object>)t.get("condition")){
			con.add(o);
		}
		String property="publications.activity,publications.volume.id,publications.issue.id,publications.year,publications.month,publications.volumeCode,publications.issueCode,publications.chineseTitle,publications.sisbn,publications.hisbn" +
				",collection.currency,collection.price,id,collection.id,collection.desc,collection.name,publications.id,publications.title" +
				",publications.authorAlias,publications.author,publications.code,publications.cover,publications.pubDate" +
				",publications.publisherName,publications.publisherId" +
				",publications.type" +
				",publications.startVolume" +
				",publications.endVolume" +
				",publications.oa,publications.free,publications.inCollection";
				if(user!=null){
					property +=",publications.subscribedIp"+ 
							",publications.subscribedUser" +
							",publications.favorite"; 
				}
				if(CollectionsUtil.exist(condition, "ins_id")||!"".equals(condition.get("ins_id"))){
					property+=",publications.inLicense"+
				           ",publications.inDetail"+
				           ",publications.insfavorite"; 
				}
		property += ",publications.recommand";
		String field="p.activity,p.volume.id,p.issue.id,p.year,p.month,p.volumeCode,p.issueCode,p.chineseTitle,p.sisbn,p.hisbn" +
				",c.currency,c.price,a.id,c.id,c.desc,c.name,p.id,p.title" +
				",p.author,p.author,p.code,p.cover,p.pubDate" +
				",(select pp.publisher.name from PPublications pp left join pp.publisher where pp.id=p.id)" +
				",(select pp.publisher.id from PPublications pp left join pp.publisher where pp.id=p.id)" +
				",p.type" +
				",p.startVolume" +
				",p.endVolume" +
				",p.oa,p.free" +
				",'1'" ;
		if(user!=null){
				field += ",(case when exists(select li.id from LLicense li where li.status=1 and li.user.id='"+user.getId()+"' and (li.publications.id=p.id or li.publications.code=p.code)) then '1' else '0' end) as inLicense";
				field += ",(case when exists(select od.id from OOrderDetail od where od.user.id='"+user.getId()+"' and (od.price.publications.id=p.id or od.productCode=p.code)) then '1' else '0' end) as inDetail";
			    field += ",(case when exists(select cf.id from CFavourites cf where cf.user.id=? and cf.publications.id=p.id ) then '1' else '0' end) as favorite";
			}
		if(CollectionsUtil.exist(condition, "ins_id")||!"".equals(condition.get("ins_id"))){
			field += ",(case when exists(select li.id from LLicense li where li.status=1 and (li.user.institution.id='"+condition.get("ins_id")+"' ) and (li.publications.id=p.id or li.publications.code=p.code)) then '1' else '0' end) as insInLicense";
			field += ",(case when exists(select od.id from OOrderDetail od where od.user.institution.id='"+condition.get("ins_id")+"' and (od.price.publications.id=p.id or od.productCode=p.code)) then '1' else '0' end) as insInDetail";
			field += ",(case when exists(select cf.id from CFavourites  cf where cf.user.institution.id='"+condition.get("ins_id")+"' and cf.publications.id=p.id ) then '1' else '0' end) as insfavorite";
		}
			field += ",(case when exists(select ip.id from BIpRange ip where ip.sip <= ? and ip.eip>=?) then '1' else '0' end) as recommend";
				
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), con.toArray(),sort, PCcRelation.class.getName(),pageCount,page*pageCount);
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
	public List<PCcRelation> getList(Map<String,Object> condition,String sort,CUser user,String ip)throws Exception{
		List<PCcRelation> list=null;
		String hql=" from PCcRelation a left join a.collection c left join a.publications p ";
		// 求当前时间前的2周以前的日期
		String targetDate = this.getDistryDate();
		Map<String,Object> t=this.getWhere(condition);
		List<Object> con = new ArrayList<Object>();
		con.add(cn.com.daxtech.framework.util.DateUtil.toDate(this.getDistryDate(), "yyyy-MM-dd"));
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		if(user!=null){
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
		}
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		for(Object o:(List<Object>)t.get("condition")){
			con.add(o);
		}
		String property="complicating,effective,id,collection.id,collection.desc,collection.name,publications.id,publications.title" +
				",publications.author,publications.code,publications.cover,publications.pubDate" +
				",publications.publisherName,publications.publisherId,publications.subjectName,publications.type" +
				",publications.oa,publications.free,publications.inCollection,publications.latest,publications.subscribedIp";
		if(user!=null){
			property += ",publications.subscribedUser,publications.exLicense,publications.buyInDetail,publications.favorite"; 
		}
		property += ",publications.recommand";
		String field="a.complicating,a.effective,a.id,c.id,c.desc,c.name,p.id,p.title" +
				",p.author,p.code,p.cover,p.pubDate" +
				",(select pp.publisher.name from PPublications pp left join pp.publisher where pp.id=p.id)" +
				",(select pp.publisher.id from PPublications pp left join pp.publisher where pp.id=p.id)" +
				",p.pubSubject" +
				",p.type,p.oa,p.free" +
				",'1'" +
				",(select cast(count(*) as string) from PPublications pub where pub.createOn>=? and pub.id=p.id)"+ 
				",(select cast(count(*) as string) from LLicenseIp lip where lip.license.status=1 and lip.sip<=? and lip.eip>=? and lip.license.publications.id=a.id )";
				if(user!=null){
					field += ",(select cast(count(*) as string) from LLicense li where li.status=1 and li.user.id=? and li.publications.id=a.id )";
					field += ",(select cast(count(*) as string) from LLicense ll where ll.status=2 and ll.user.id=? and ll.publications.id=p.id )";
					field += ",(select cast(count(*) as string) from OOrderDetail detail where detail.status not in (3,10,99) and detail.user.id=? and detail.price.publications.id=p.id)";
					field += ",(select cast(count(*) as string) from CFavourites cf where cf.user.id=? and cf.publications.id=p.id )";
				}
				field += ",(select cast(count(*) as string) from BIpRange ip where ip.sip <=? and ip.eip>=?)";
				
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), con.toArray(),sort, PCcRelation.class.getName());
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
		List<PCcRelation> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from PCcRelation a left join a.collection c left join a.publications p";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", PCcRelation.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

	@SuppressWarnings("unchecked")
	public void deleteByCondition(Map<String, Object> condition)throws Exception {
		String sql=" delete from PCcRelation a where a.collection.id =?";
		try{
			this.hibernateDao.executeHql(sql,new Object[]{condition.get("collectionId").toString()});
		}catch(Exception e){
			throw e;
		}
	}
	
	public List<PCcRelation> getListSimple(Map<String,Object> condition)throws Exception{
		List<PCcRelation> list=null;
		try{
			String hql=" from PCcRelation a left join a.collection c left join a.publications p ";
			Map<String,Object> t=this.getWhere(condition);
			String property="id,collection.id,collection.name,publications.id,publications.title,publications.code,complicating,effective";
			String field="a.id,c.id,c.name,p.id,p.title,p.code,a.complicating,a.effective";
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", PCcRelation.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<PCcRelation> getPubList(Map<String,Object> condition ,String sort) throws Exception{
		List<PCcRelation> list=null;
		String property=" publications.id ";
		String field=" b.id ";
		Map<String,Object> t=this.getWhere(condition);
		try {
			String hql=" from PCcRelation a left join a.publications b left join a.collection c ";
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PCcRelation.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
	/**
	 * 产品包内语种统计
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PCcRelation> getLangStatistic(Map<String,Object> condition)throws Exception{
		List<PCcRelation> list=null;
		String property=" publications.lang,effective ";
		String field=" upper(p.lang),cast(count(*) as int) ";
		Map<String,Object> t=this.getWhere(condition);
		try {
			String hql=" from PCcRelation a join a.publications p join a.collection c ";
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray()," group by p.lang order by p.lang ", PCcRelation.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
	/**
	 * 产品包内类型统计
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PCcRelation> getTypeStatistic(Map<String,Object> condition)throws Exception{
		List<PCcRelation> list=null;
		String property=" publications.type,effective ";
		String field=" p.type,cast(count(*) as int) ";
		Map<String,Object> t=this.getWhere(condition);
		try {
			String hql=" from PCcRelation a join a.publications p join a.collection c ";
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray()," group by p.type order by p.type ", PCcRelation.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<PCcRelation> getCcTypeLangStatistic(Map<String,Object> condition)throws Exception{
		List<PCcRelation> list=null;
		String property=" publications.type,publications.lang,effective ";
		String field=" p.type,p.lang,cast(count(*) as int) ";
		Map<String,Object> t=this.getWhere(condition);
		try {
			String hql=" from PCcRelation a join a.publications p join a.collection c ";
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray()," group by p.type,p.lang order by p.type,p.lang ", PCcRelation.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
}
