package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.PCsRelation;
import cn.digitalpublishing.util.web.DateUtil;
import cn.digitalpublishing.util.web.IpUtil;

public class PCsRelationDao extends CommonDao<PCsRelation, String> {

	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		
		//分类法要是主分类法 mainCode
		if(!CollectionsUtil.exist(map, "mainCode")||"".equals(map.get("mainCode"))){
			if(flag==0){
				whereString+=" where s.subject.type = 1";
				flag=1;
			}else{
				whereString+=" and s.subject.type = 1";
			}
		}
		if(CollectionsUtil.exist(map, "pppppId")&&!"".equals(map.get("pppppId"))){
			if(flag==0){
				whereString+=" where p.id = ?";
				flag=1;
			}else{
				whereString+=" and p.id = ?";
			}
			condition.add(map.get("pppppId").toString());
		}
		if(CollectionsUtil.exist(map, "inPubId")&&!"".equals(map.get("inPubId"))){
			String[] ary=map.get("inPubId").toString().split(",");
			String in="";
			for (int i = 0; i < ary.length; i++) {
				in+="?,";
				condition.add(ary[i]);
			}
			in=in.endsWith(",")?in.substring(0,in.length()-1):in;
			if(flag==0){
				whereString+=" where p.id in ("+in+")";
				flag=1;
			}else{
				whereString+=" and p.id in ("+in+")";
			}
		}
		if(CollectionsUtil.exist(map, "subId")&&!"".equals(map.get("subId"))){
			if(flag==0){
				whereString+=" where s.id = ?";
				flag=1;
			}else{
				whereString+=" and s.id = ?";
			}
			condition.add(map.get("subId").toString());
		}
		/**
		 * 1.code 分类编码
		 */
		if(CollectionsUtil.exist(map, "code")&&!"".equals(map.get("code"))){
			if(flag==0){
				whereString+=" where s.subject.code = ?";
				flag=1;
			}else{
				whereString+=" and s.subject.code = ?";
			}
			condition.add(map.get("code").toString());
		}
		/**
		 * 1.childCode 子分类编码
		 */
		if(CollectionsUtil.exist(map, "childCode")&&!"".equals(map.get("childCode"))){
			if(flag==0){
				whereString+=" where s.code = ?";
				flag=1;
			}else{
				whereString+=" and s.code = ?";
			}
			condition.add(map.get("childCode").toString());
		}
		/**
		 * 1.type 类型
		 */
		if(CollectionsUtil.exist(map, "type")&&!"".equals(map.get("type"))&&map.get("type")!=null){
			if(flag==0){
				whereString+=" where p.type = ?";
				flag=1;
			}else{
				whereString+=" and p.type = ?";
			}
			condition.add(Integer.valueOf(map.get("type").toString()));
		}

		/**
		 * 1.order A-Z字母排序
		 */
		if(CollectionsUtil.exist(map, "order")&&!"".equals(map.get("order"))){
			if(flag==0){
				whereString+=" where lower(p.title) like ?";
				flag=1;
			}else{
				whereString+=" and lower(p.title) like ?";
			}
			condition.add(map.get("order").toString().toLowerCase()+"%");
		}
		/**
		 * treeCodeLength treeCode 的长度
		 * */
		if(CollectionsUtil.exist(map, "treeCodeLength")&&!"".equals(map.get("treeCodeLength"))){
			if(flag==0){
				whereString+=" where length(s.treeCode) = ?";
				flag=1;
			}else{
				whereString+=" and length(s.treeCode) = ?";
			}
			condition.add(map.get("treeCodeLength"));
		}
		/**
		 * publicationsId 产品ID
		 * */
		if(CollectionsUtil.exist(map, "publicationsId")&&!"".equals(map.get("publicationsId"))){
			if(flag==0){
				whereString+=" where a.publications.id = ?";
				flag=1;
			}else{
				whereString+=" and a.publications.id = ?";
			}
			condition.add(map.get("publicationsId"));
		}
		/**
		 * 1.publisherid
		 */
		if(CollectionsUtil.exist(map, "publisherid")&&!"".equals(map.get("publisherid"))&&map.get("publisherid")!=null){
			if(flag==0){
				whereString+=" where p.publisher.id = ?";
				flag=1;
			}else{
				whereString+=" and p.publisher.id  = ?";
			}
			condition.add(map.get("publisherid").toString());
		}
		/**
		 * mainType
		 * 查询是否是查询子类文件
		 * 比如 书籍 下边有章节
		 * */
		if(CollectionsUtil.exist(map, "mainType")&&!"".equals(map.get("mainType"))){
			if(map.get("mainType").equals("0")){
				if(flag==0){
					whereString+=" where p.publications.id is null ";
					flag=1;
				}else{
					whereString+=" and p.publications.id is null ";
				}
			}
//			condition.add(map.get("letter").toString().toLowerCase());
		}
		/**
		 * 分类法
		 */
		if(CollectionsUtil.exist(map, "subjectId")&&!"".equals(map.get("subjectId"))&&map.get("subjectId")!=null){
			if(!"0".equals(map.get("subjectId"))){
				if(flag==0){
					whereString+=" where s.treeCode like (select cs2.treeCode from BSubject cs2 where cs2.id = ?)||'%' ";
					flag=1;
				}else{
					whereString+=" and s.treeCode like (select cs2.treeCode from BSubject cs2 where cs2.id = ?)||'%' ";
				}
				condition.add(map.get("subjectId").toString());
			}
		}
		/**
		 * 出版年份
		 */
		if(CollectionsUtil.exist(map, "pubYear")&&!"".equals(map.get("pubYear"))&&map.get("pubYear")!=null){
			if(flag==0){
				whereString+=" where substring(p.pubDate,1,4) = ?";
				flag=1;
			}else{
				whereString+=" and substring(p.pubDate,1,4)  = ?";
			}
			condition.add(map.get("pubYear").toString());
		}
		/**
		 * 用于订阅提醒查询
		 * 开始日期
		 */
		if(CollectionsUtil.exist(map, "startDate")&&!"".equals(map.get("startDate"))&&map.get("startDate")!=null){
			if(flag==0){
				whereString+=" where p.createOn >= ?";
				flag=1;
			}else{
				whereString+=" and p.createOn  >= ?";
			}
			condition.add(DateUtil.getUtilDate(map.get("startDate").toString(),"yyyy-MM-dd"));
		}
		/**
		 * 用于订阅提醒查询
		 * 结束日期
		 */
		if(CollectionsUtil.exist(map, "endDate")&&!"".equals(map.get("endDate"))&&map.get("endDate")!=null){
			if(flag==0){
				whereString+=" where p.createOn <= ?";
				flag=1;
			}else{
				whereString+=" and p.createOn  <= ?";
			}
			condition.add(DateUtil.getUtilDate(map.get("endDate").toString(),"yyyy-MM-dd"));
		}
		/**
		 * 用于订阅提醒查询
		 * 分类法treeCode
		 */
		if(CollectionsUtil.exist(map, "subTreeCode")&&!"".equals(map.get("subTreeCode"))&&map.get("subTreeCode")!=null){
			if(flag==0){
				whereString+=" where s.treeCode like ?";
				flag=1;
			}else{
				whereString+=" and s.treeCode like ?";
			}
			condition.add(map.get("subTreeCode").toString()+"%");
		}
		/**
		 * pstatus
		 * */
		if(CollectionsUtil.exist(map, "pstatus")&&!"".equals(map.get("pstatus"))){
			if(flag==0){
				whereString+=" where p.status = ?";
				flag=1;
			}else{
				whereString+=" and p.status = ?";
			}
			condition.add(map.get("pstatus"));
		}
		
		if(CollectionsUtil.exist(map, "pcslist")&&!"".equals(map.get("pcslist"))){
			List<PCsRelation> list=(List<PCsRelation>)map.get("pcslist");
			if(list!=null && list.size()>0){
				String where_ = "";
				String _where = ")";
				String where = "";
				if(flag==0){
					where_=" where s.id in (";
					flag=1;
				}else{
					where_=" and s.id in (";
				}
				for(int i =0;i<list.size();i++){
					where+="?";
					if(i<list.size()-1){
						where+=",";
					}
					condition.add(list.get(i).getSubject().getId());
				}
				whereString += where_ + where + _where;
			}
		}
		if(CollectionsUtil.exist(map, "pTypeArr")&&!"".equals(map.get("pTypeArr"))){
			Integer[] types=(Integer[])map.get("pTypeArr");
			if(types!=null && types.length>0){
				String where_ = "";
				String _where = ")";
				String where = "";
				if(flag==0){
					where_=" where p.type in (";
					flag=1;
				}else{
					where_=" and p.type in (";
				}
				for(int i =0;i<types.length;i++){
					where+="?";
					if(i<types.length-1){
						where+=",";
					}
					condition.add(types[i]);
				}
				whereString += where_ + where + _where;
			}
		}
		/**
		 * unEqualPubId
		 * */
		if(CollectionsUtil.exist(map, "unEqualPubId")&&!"".equals(map.get("unEqualPubId"))){
			if(flag==0){
				whereString+=" where a.publications.id != ?";
				flag=1;
			}else{
				whereString+=" and a.publications.id != ?";
			}
			condition.add(map.get("unEqualPubId"));
		}
		/**
		 * isLicense  1、在已订阅中查询 2 、未订阅中
		 * */
		if(CollectionsUtil.exist(map, "isLicense")&&!"".equals(map.get("isLicense"))&&map.get("isLicense")!=null){
			
			String where1 = "";
			String where2 = "";
			
			if(CollectionsUtil.exist(map, "userId")&&!"".equals(map.get("userId"))){
				where1 = "(select cast(count(*) as string) from LLicense ll where ll.status=1 and ll.user.id=? and ((ll.publications.type not in (2,4,6,7) and (ll.publications.id=p.id or ll.publications.code=p.code)) or (ll.publications.type in (2,4,6,7) and (ll.publications.id=p.id or ll.publications.id=p.publications.id or ll.publications.id=p.volume.id or ll.publications.id=p.issue.id))))";
				condition.add(map.get("userId"));
			}
			if(CollectionsUtil.exist(map, "ip")&&!"".equals(map.get("ip"))){
				where2 = "(select cast(count(*) as string) from LLicenseIp lip where lip.license.status=1 and lip.sip<=? and lip.eip>=? and ((lip.license.publications.type not in (2,4,6,7) and (lip.license.publications.id=p.id or lip.license.publications.code=p.code)) or (lip.license.publications.type in (2,4,6,7) and (lip.license.publications.id=p.id or lip.license.publications.id=p.publications.id or lip.license.publications.id=p.volume.id or lip.license.publications.id=p.issue.id))))";
				condition.add(IpUtil.getLongIp(map.get("ip").toString()));
				condition.add(IpUtil.getLongIp(map.get("ip").toString()));
			}
			if("1".equals(map.get("isLicense").toString())){
				if(flag==0){
					whereString+=" where ";
					if(!"".equals(where1)&&!"".equals(where2)){
						whereString += "("+where1+">0 or "+where2+">0 )";
					}else if(!"".equals(where1)){
						whereString +=  where1+">0 ";
					}else if(!"".equals(where2)){
						whereString +=  where2+">0 ";
					}
					flag=1;
				}else{
					whereString+=" and ";
					if(!"".equals(where1)&&!"".equals(where2)){
						whereString += "("+where1+">0 or "+where2+">0 )";
					}else if(!"".equals(where1)){
						whereString +=  where1+">0 ";
					}else if(!"".equals(where2)){
						whereString +=  where2+">0 ";
					}
				}
			}else{
				if(flag==0){
					whereString+=" where ";
					if(!"".equals(where1)&&!"".equals(where2)){
						whereString += "("+where1+"<=0 and "+where2+"<=0 )";
					}else if(!"".equals(where1)){
						whereString +=  where1+"<=0 ";
					}else if(!"".equals(where2)){
						whereString +=  where2+"<=0 ";
					}
					flag=1;
				}else{
					whereString+=" and ";
					if(!"".equals(where1)&&!"".equals(where2)){
						whereString += "("+where1+"<=0 and "+where2+"<=0 )";
					}else if(!"".equals(where1)){
						whereString +=  where1+"<=0 ";
					}else if(!"".equals(where2)){
						whereString +=  where2+"<=0 ";
					}
				}
			}
		}
		/**
		 * 出版物类型pubType
		 */
		if(CollectionsUtil.exist(map, "pubType")&&!"".equals(map.get("pubType"))&&map.get("pubType")!=null){
			Integer[] types=(Integer[])map.get("pubType");
			if(types!=null && types.length>0){
				String where_ = "";
				String _where = ")";
				String where = "";
				if(flag==0){
					where_=" where a.type in (";
					flag=1;
				}else{
					where_=" and a.type in (";
				}
				for(int i =0;i<types.length;i++){
					where+="?";
					if(i<types.length-1){
						where+=",";
					}
					condition.add(types[i]);
				}
				whereString += where_ + where + _where;
			}
		}
		
		/**
		 * 出版物状态 pubStatus
		 **/
		if(CollectionsUtil.exist(map, "pubStatus")&&!"".equals(map.get("pubStatus"))){
			if(flag==0){
				whereString+=" where a.status =?";
				flag=1;
			}else{
				whereString+=" and a.status =?";
			}
			condition.add(map.get("pubStatus"));
		}
		
		table.put("where",whereString);
		table.put("condition", condition);
		return table;
	}
	
	/**
	 * 获取总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer getCount(Map<String,Object> condition)throws Exception{
		List<PCsRelation> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from PCsRelation a left join a.subject s left join a.publications p";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),CollectionsUtil.exist(condition, "groupBy")?"group by " + condition.get("groupBy") : "", PCsRelation.class.getName());
		}catch(Exception e){
//			e.printStackTrace();
			throw e;
		}
		return list==null?0:list.size()!=1?list.size():Integer.valueOf(list.get(0).getId());
	}

	/**
	 * 分页列表
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PCsRelation> getPagingList(Map<String, Object> condition,
			String sort, int pageCount, int curpage,CUser user,String ip) throws Exception {
		List<PCsRelation> list=null;
		String hql=" from PCsRelation a left join a.subject s left join a.publications p left join p.publisher pu ";
		Map<String,Object> t=this.getWhere(condition);
		//(List<Object>)t.get("condition");
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
		String property="id,subject.id,subject.code,publications.id,publications.title,publications.author,publications.type,publications.cover,publications.publisher.id,publications.publisher.name" +
				",publications.oa,publications.free,publications.inCollection,publications.latest,publications.subscribedIp";
		if(user!=null){
			property += ",publications.subscribedUser,publications.exLicense,publications.buyInDetail,publications.favorite"; 
		}
		property += ",publications.recommand";
		property += ",publications.pubSubject,publications.code";
		property += ",publications.volume.id,publications.volumeCode,publications.issue.id,publications.issueCode,publications.startPage,publications.endPage,publications.year,publications.month,publications.pubDate";
		String field="a.id,s.id,s.code,p.id,p.title,p.author,p.type,p.cover,pu.id,pu.name" +
				",p.oa,p.free" +
//				",(case when exists(select cc.id from PCcRelation cc where cc.publications.id=p.id and cc.collection.status=2) then '1' else '0' end) as incollection" +
				",p.inCollection"+
				",(case when exists(select pub.id from PPublications pub where pub.createOn >=? and pub.id=p.id) then '1' else '0' end) as isnewst" +
				",(case when exists(select lip.id from LLicenseIp lip where (lip.license.publications.id=p.id or lip.license.publications.code=p.code) and lip.sip<=? and lip.eip>=? and lip.license.status=1) then '1' else '0' end) as IPRange";
			if(user!=null){
				field += ",(case when exists(select li.id from LLicense li where li.status=1 and li.user.id=? and (li.publications.id=p.id or li.publications.code=p.code)) then '1' else '0' end) as subscribedUser";
				field += ",(case when exists(select ll.id from LLicense ll where ll.status=2 and ll.user.id=? and (ll.publications.id=p.id or ll.publications.code=p.code)) then '1' else '0' end) as exLicense";
				field += ",(case when exists(select detail.id from OOrderDetail detail where detail.status not in (3,10,99) and detail.user.id=? and (detail.price.publications.id=p.id or detail.price.publications.code=p.code)) then '1' else '0' end) as buyInDetail";//2013-3-13 当一个用户已购买的产品下架后，同时上架另一个相同ISBN的产品，但是ISBN相同的产品不属于同一个提供商（Source），这个时候新上架的产品不能被购买（该用户已经买过相同ISBN的产品）,期刊、卷、期、文章因为ISSN相同不受此限制
				field += ",(case when exists(select cast(count(*) as string) from CFavourites cf where cf.user.id=? and cf.publications.id=p.id ) then '1' else '0' end) as favorite";
			}
			field += ",(select cast(count(*) as string) from BIpRange ip where ip.sip <= ? and ip.eip>=?)";
			field += ",p.pubSubject,p.code";
			field += ",p.volume.id,p.volumeCode,p.issue.id,p.issueCode,p.startPage,p.endPage,p.year,p.month,p.pubDate";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), con.toArray(),sort, PCsRelation.class.getName(),pageCount,curpage*pageCount);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	/**
	 * 列表
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PCsRelation> getList(Map<String, Object> condition,
			String sort) throws Exception {
		List<PCsRelation> list=null;
		String hql=" from PCsRelation a right join a.subject s left join a.publications p ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,subject.id,subject.treeCode,subject.code,subject.name,subject.nameEn,subject.countPP,publications.id,publications.title,publications.type,publications.cover,publications.pubDate";
		String field="a.id,s.id,s.treeCode,s.code,s.name,s.nameEn,(select count(*) from PPublications pp where pp.id=a.publications.id and pp.status=2),p.id,p.title,p.type,p.cover,p.pubDate";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PCsRelation.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public void deleteByCondition(Map<String, Object> condition)throws Exception {
		String hql="delete from PCsRelation a ";
		Map<String,Object> t=this.getWhere(condition);
		try{
			super.hibernateDao.executeHql(hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	public void deleteByPubId(String pubId)throws Exception {
		try{
			this.hibernateDao.executeHql("delete from PCsRelation a where a.publications.id = ? ",new Object[]{pubId});			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 列表
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PCsRelation> getPubListByAlertsInfo(Map<String, Object> condition,
			String sort) throws Exception {
		List<PCsRelation> list=null;
		String hql=" from PCsRelation a right join a.subject s left join a.publications p ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,subject.id,subject.name,publications.id,publications.title";
		String field="a.id,s.id,s.name,p.id,p.title";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PCsRelation.class.getName());
		}catch(Exception e){
//			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<PCsRelation> getTops(Map<String,Object> condition,String sort,Integer number)throws Exception{
		List<PCsRelation> list=null;
		String hql=" from PCsRelation a right join a.subject s left join a.publications p left join p.publisher pu ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,subject.id,subject.code,subject.name,publications.pubDate,publications.type,publications.year,publications.month,publications.id,publications.code,publications.title,publications.cover,publications.buyTimes,publications.publications.id,publications.volume.id,publications.volumeCode,publications.issue.id,publications.issueCode,publications.startPage,publications.endPage,publications.author ,publications.publisher.id,publications.publisher.name";
		String field="a.id,s.id,s.code,s.name,p.pubDate,p.type,p.year,p.month,p.id,p.code,p.title,p.cover,p.buyTimes,p.publications.id,p.volume.id,p.volumeCode,p.issue.id,p.issueCode,p.startPage,p.endPage,p.author,pu.id,pu.name ";
		try{
			if(number!=null&&number>0){
				list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PCsRelation.class.getName(),number,0);
			}else{
				list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PCsRelation.class.getName());
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<PCsRelation> getTopsForAlerts(Map<String,Object> condition,String sort,Integer number)throws Exception{
		List<PCsRelation> list=null;
		String hql=" from PCsRelation a inner join a.subject s inner join a.publications p inner join p.publisher pu ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,subject.id,subject.code,subject.name,publications.pubDate,publications.type,publications.year,publications.month,publications.id,publications.code,publications.title,publications.cover,publications.buyTimes,publications.publications.id,publications.volume.id,publications.volumeCode,publications.issue.id,publications.issueCode,publications.startPage,publications.endPage,publications.author ,publications.publisher.id,publications.publisher.name";
		String field="a.id,s.id,s.code,s.name,p.pubDate,p.type,p.year,p.month,p.id,p.code,p.title,p.cover,p.buyTimes,p.publications.id,p.volume.id,p.volumeCode,p.issue.id,p.issueCode,p.startPage,p.endPage,p.author,pu.id,pu.name ";
		try{
			if(number!=null&&number>0){
				list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PCsRelation.class.getName(),number,0);
			}else{
				list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PCsRelation.class.getName());
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	public Integer getCountForAlerts(Map<String,Object> condition)throws Exception{
		List<PCsRelation> list=null;
		String hql=" from PCsRelation a right join a.subject s left join a.publications p left join p.publisher pu ";
		Map<String,Object> t=this.getWhere(condition);	
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", PCsRelation.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<PCsRelation> getSubStatistic(Map<String,Object> condition)throws Exception{
		List<PCsRelation> list=null;
		String hql=" from PCsRelation a ";
		Map<String,Object> t=this.getWhere(condition);
		String property="subject.treeCode,type";
		String field="a.subject.treeCode,cast(count(*) as int)";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray()," group by a.subject.treeCode order by a.subject.treeCode ", PCsRelation.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	public List<PCsRelation> getDistinctSubject(Map<String,Object> condition) throws Exception{
		List<PCsRelation> list=null;
		String hql=" from PCsRelation a left join a.publications p left join a.subject s";
		Map<String,Object> t=this.getWhere(condition);
		String property="subject.id,subject.name,subject.nameEn";
		String field="distinct s.id,s.name,s.nameEn";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", PCsRelation.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	/**
	 * 通过sql获取PCollection产品包里面所包含的学科分类（优化版）
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PCsRelation> getSubInfoForCol(Map<String,Object> condition,String sort) throws Exception{
		List<PCsRelation> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String property="subject.id,subject.name,subject.nameEn,subject.code,eachCount";
		String field="a.subject.id,max(a.subject.name),max(a.subject.nameEn),max(a.subject.code),cast(count(*) as string)";
		String hql="from PCsRelation a where exists(select 1 from PCcRelation b where a.publications.id=b.publications.id and b.collection.id='"+condition.get("collectionId")+"'";
		if (CollectionsUtil.exist(condition, "language") && !"".equals(condition.get("language")) && condition.get("language") != null) {
			hql += " and b.publications.lang like 'chs'";
		}
		if (CollectionsUtil.exist(condition, "languageEn") && !"".equals(condition.get("languageEn")) && condition.get("languageEn") != null) {
			hql += " and b.publications.lang not like 'chs'";
		}
		hql+=") group by a.subject.id";
	    try {
	    	list=super.hibernateDao.getListByHql(property,field, hql, ((List<Object>)t.get("condition")).toArray(),sort, PCsRelation.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	    return list;
	}
}
