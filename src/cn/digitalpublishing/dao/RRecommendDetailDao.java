package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.com.daxtech.framework.util.DateUtil;
import cn.digitalpublishing.ep.po.RRecommendDetail;

public class RRecommendDetailDao extends CommonDao<RRecommendDetail, String> {

	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * 1.userId
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
		if(CollectionsUtil.exist(map, "available")&&!"".equals(map.get("available"))){//这是对一个available的条件
			if(flag==0){
				whereString+=" where (e.available <> ? or e.available is null )  ";
				flag=1;
			}else{
				whereString+=" and ( e.available  <> ? or e.available is null ) ";
			}
			condition.add(map.get("available"));
		}
		if(CollectionsUtil.exist(map, "pubtitle")&&!"".equals(map.get("pubtitle"))){
			if(flag==0){
				whereString+=" where c.publications.title like ?";
				flag=1;
			}else{
				whereString+=" and c.publications.title like ?";
			}
			condition.add("%"+map.get("pubtitle").toString() + "%");
		}
//		if(CollectionsUtil.exist(map, "isorder")&&!"".equals(map.get("isorder"))){
//			if(flag==0){
//				whereString+=" where c.isOrder = ?";
//				flag=1;
//			}else{
//				whereString+=" and c.isOrder = ?";
//			}
//			condition.add(Integer.valueOf(map.get("isorder").toString()));
//		}
		if(CollectionsUtil.exist(map, "orderStatus")&&!"".equals(map.get("orderStatus")) && map.get("orderStatus")!=null){
			String cond=null;
			if("3".equals(map.get("orderStatus").toString())){//已取消
				cond=" c.isOrder = '3' ";				
			}else if("4".equals(map.get("orderStatus").toString())){//已购买
				cond=" ((select cast(count(*) as int) from LLicense ll where ll.status=1 and ll.user.institution.id=f.id and ll.publications.id=e.id) >0 )";
			}else if("2".equals(map.get("orderStatus").toString())){//购买中
				cond=" c.isOrder != '3' and ((select cast(count(*) as int) from OOrderDetail od2 where od2.price.publications.id=e.id and od2.status != 99 and od2.status !=3) > 0) "
					+ "and ((select cast(count(*) as int) from LLicense ll where ll.status=1 and ll.user.institution.id=f.id and ll.publications.id=e.id) =0 )";
			}else if("1".equals(map.get("orderStatus").toString())){//未购买
				cond=" c.isOrder != '3' and ((select cast(count(*) as int) from OOrderDetail od3 where od3.recommend.id=c.id) = 0) "
				+ "and ((select cast(count(*) as int) from LLicense ll where ll.status=1 and ll.user.institution.id=f.id and ll.publications.id=e.id) =0 )";
			}
			
			if(flag==0){
				whereString+=" where " + cond;
				flag=1;
			}else{
				whereString+=" and " + cond;
			}
			
		}
		if(CollectionsUtil.exist(map, "isorder2")&&!"".equals(map.get("isorder2"))){
			if(flag==0){
				whereString+=" where c.isOrder != ?";
				flag=1;
			}else{
				whereString+=" and c.isOrder != ?";
			}
			condition.add(Integer.valueOf(map.get("isorder2").toString()));
		}
		/**
		 * 4.recid 
		 */
		if(CollectionsUtil.exist(map, "recid")&&!"".equals(map.get("recid"))){
			if(flag==0){
				whereString+=" where c.id = ?";
				flag=1;
			}else{
				whereString+=" and c.id = ?";
			}
			condition.add(map.get("recid").toString());
		}
		if(CollectionsUtil.exist(map, "pubCode")&&!"".equals(map.get("pubCode"))){
			if(flag==0){
				whereString+=" where e.code like ?";
				flag=1;
			}else{
				whereString+=" and e.code like ?";
			}
			condition.add("%"+(map.get("pubCode").toString() + "%"));
		}
		if(CollectionsUtil.exist(map, "pubId")&&!"".equals(map.get("pubId"))){
			if(flag==0){
				whereString+=" where e.id = ?";
				flag=1;
			}else{
				whereString+=" and e.id = ?";
			}
			condition.add(map.get("pubId"));
		}
		if(CollectionsUtil.exist(map, "insId")&&!"".equals(map.get("insId"))){
			if(flag==0){
				whereString+=" where f.id = ?";
				flag=1;
			}else{
				whereString+=" and f.id = ?";
			}
			condition.add(map.get("insId"));
		}
		if(CollectionsUtil.exist(map, "institutionId")&&!"".equals(map.get("institutionId"))){
			if(flag==0){
				whereString+=" where f.id = ?";
				flag=1;
			}else{
				whereString+=" and f.id = ?";
			}
			condition.add(map.get("institutionId"));
		}
		if(CollectionsUtil.exist(map, "sourceId")&&!"".equals(map.get("sourceId"))){
			if(flag==0){
				whereString+=" where e.sourceId = ?";
				flag=1;
			}else{
				whereString+=" and e.sourceId = ?";
			}
			condition.add(map.get("sourceId"));
		}
		
		if(CollectionsUtil.exist(map, "type")&&!"".equals(map.get("type"))){
			if(flag==0){
				whereString+=" where e.type = ?";
				flag=1;
			}else{
				whereString+=" and e.type = ?";
			}
			condition.add(map.get("type"));
		}
		/**
		 * 书籍类型1-电子书  2-期刊 类型数组
		 */
		if(CollectionsUtil.exist(map, "pubtypes")&&map.get("pubtypes")!=null&&!"".equals(map.get("pubtypes").toString())){
			if(flag==0){
				whereString+=" where e.type in (";
				flag=1;
			}else{
				whereString+=" and e.type in (";
			}
			Integer[] pubtypes = (Integer[])map.get("pubtypes");
			for (int i =0; i<pubtypes.length; i++) {
				if(i<pubtypes.length-1){
					whereString+="?,";
				}else{
					whereString+="?";
				}
				condition.add(pubtypes[i]);
			}
			whereString+=")";
		}
		if(CollectionsUtil.exist(map, "rid")&&!"".equals(map.get("rid"))){
			if(flag==0){
				whereString+=" where c.id = ?";
				flag=1;
			}else{
				whereString+=" and c.id = ?";
			}
			condition.add(map.get("rid"));
		}
		if(CollectionsUtil.exist(map, "isCn")&&!"".equals(map.get("isCn"))){
			Boolean isCn="true".equals(map.get("isCn").toString().toLowerCase());
			if(flag==0){
				whereString+=" where e.lang ";
				flag=1;
			}else{
				whereString+=" and e.lang ";
			}
			whereString += isCn?" = ?" : " <> ? ";
			condition.add("chs");
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
	public List<RRecommendDetail> getList(Map<String,Object> condition,String sort)throws Exception{
		List<RRecommendDetail> list=null;
		String hql=" from RRecommendDetail a left join a.user b left join a.recommend c left join c.publications e left join c.institution f publisher g";
		Map<String,Object> t=this.getWhere(condition);
		List<Object> cond=new ArrayList<Object>();
		
		String property="id,remark,createdby,createdon,user.id,user.name,user.level" +
				",user.email" +
				",recommend.id,recommend.name,recommend.isOrder" +
				",recommend.publications.id,recommend.publications.title,recommend.publications.author,recommend.publications.publisher.name,recommend.publications.code" +
				",recommend.institution.id,recommend.institution.name" +
				",recommend.isOrdering ";		
		
		String field="a.id,a.remark,a.createdby,a.createdon,b.id,b.name,b.level" +
				",(select d.val from CUserProp d where d.user.id=b.id and d.code='email')" +
				",c.id,c.name" +
				",(select od.status from OOrderDetail od where od.recommend.id = c.id)" +
				",e.id,e.title,e.author,g.name,e.code" +
				",f.id,f.name" +
				",(select cast(count(*) as int) from OOrderDetail od where od.price.publications.id=e.id and od.status != 99 and od.status !=3 ) ";
				
		if(CollectionsUtil.exist(condition, "institutionId")){
			property+=",recommend.isOrdered ";
			field+=",(select cast(count(*) as int) from LLicense ll where ll.status=1 and ll.user.institution.id=? and ll.publications.id=e.id )";
			cond.add(condition.get("institutionId"));			
		}
		for(Object o:(List<Object>)t.get("condition")){
			cond.add(o);
		}
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), cond.toArray(),sort, RRecommendDetail.class.getName());
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
	public List<RRecommendDetail> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<RRecommendDetail> list=null;
		String hql=" from RRecommendDetail a left join a.user b left join a.recommend c left join c.publications e left join c.institution f left join e.publisher g";
		Map<String,Object> t=this.getWhere(condition);
		List<Object> cond=new ArrayList<Object>();
		
		String property="id,remark,particulars,createdby,createdon,user.id,user.name,user.level" +
				",user.email" +
				",recommend.id,recommend.name,recommend.isOrder"+
				",recommend.publications.id,recommend.publications.title,recommend.publications.author,recommend.publications.publisher.name,recommend.publications.code,recommend.publications.available,recommend.publications.type" +
				",recommend.institution.id,recommend.institution.name" +
				",recommend.isOrdering ";		
		
		String field="a.id,a.remark,a.particulars,a.createdby,a.createdon,b.id,b.name,b.level" +
				",(select d.val from CUserProp d where d.user.id=b.id and d.code='email')" +
				",c.id,c.name,c.isOrder" +
				",e.id,e.title,e.author,g.name,e.code,e.available,e.type" +
				",f.id,f.name" +
				",(select cast(count(*) as int) from OOrderDetail od where od.price.publications.id=e.id and od.status != 99 and od.status !=3 ) ";
				
		//if(CollectionsUtil.exist(condition, "institutionId")){
			property+=",recommend.isOrdered ";
			field+=",(select cast(count(*) as int) from LLicense ll where ll.status=1 and ll.user.institution.id=? and ll.publications.id=e.id )";
			cond.add(condition.get("institutionId"));			
		//}
		for(Object o:(List<Object>)t.get("condition")){
			cond.add(o);
		}
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), cond.toArray(),sort, RRecommendDetail.class.getName(),pageCount,page*pageCount);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	/**
	 * 获取暂缓购买信息
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<RRecommendDetail> getParticularsList(Map<String,Object> condition,String sort)throws Exception{
		List<RRecommendDetail> list=null;
		String hql=" from RRecommendDetail a left join a.recommend c";
		Map<String,Object> t=this.getWhere(condition);		
		
		String property="id";
		String field="a.id";
		
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, RRecommendDetail.class.getName());
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
		List<RRecommendDetail> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from RRecommendDetail a left join a.user b left join a.recommend c left join c.publications e left join c.institution f";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", RRecommendDetail.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<RRecommendDetail> getCounterPagingForPublisher(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<RRecommendDetail> list=null;
		String hql=" from RRecommendDetail a left join a.recommend c left join c.institution f left join c.publications e left join e.publisher g ";
		Integer minMonth=condition.containsKey("startMonth")&&!"".equals(condition.get("startMonth").toString())?Integer.valueOf(condition.get("startMonth").toString()):12;
		Integer maxMonth=condition.containsKey("endMonth")&&!"".equals(condition.get("endMonth").toString())?Integer.valueOf(condition.get("endMonth").toString()):12;
		condition.put("minMonth" , minMonth);
		condition.put("maxMonth" , maxMonth);
		Map<String,Object> t=this.getWhere(condition);		
		List<Object> cond=new ArrayList<Object>();
		
		String property="recommend.publications.id,recommend.publications.title,recommend.publications.code," +
						"recommend.publications.publisher.name," +
						"year";
		String field="e.id"
				+",(select p.title from PPublications p where p.id=e.id ) as title"
				+",(select p.code from PPublications p where p.id=e.id ) as code,"
				+"(select p.publisher.name from PPublications p where p.id=e.id ) as name" +				
				",(select cast (count(*) as string) from RRecommendDetail rrd where " +
				"rrd.recommend.publications.sourceId = ? and YEAR(rrd.createdon) = ? " +
				"and rrd.recommend.publications.id= e.id) ";
		cond.add(condition.get("sourceId"));
		cond.add(Integer.valueOf(condition.get("year").toString()));
		System.out.println("@@@@@@@@minMonth:"+minMonth);
		System.out.println("@@@@@@@@maxMonth:"+maxMonth);
		
		for(int i=minMonth;i<=maxMonth;i++){
			property+=",month"+i;
			field +=",(select cast (count(*) as string) from RRecommendDetail rrd where " +
					"rrd.recommend.publications.sourceId = ? and YEAR(rrd.createdon) = ? " +
					"and MONTH(rrd.createdon) = ? and rrd.recommend.publications.id= e.id) ";
			cond.add(condition.get("sourceId"));
			cond.add(Integer.valueOf(condition.get("year").toString()));		
			cond.add(i);
		}	
		for(Object o:(List<Object>)t.get("condition")){
			cond.add(o);
		}
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString() ,cond.toArray(),  " group by e.id "+sort, RRecommendDetail.class.getName(),pageCount,page*pageCount);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<RRecommendDetail> getCounterForPublisher(Map<String,Object> condition,String sort)throws Exception{
		List<RRecommendDetail> list=null;
		String hql=" from RRecommendDetail a left join a.recommend c left join c.institution f left join c.publications e left join e.publisher g ";
		Integer minMonth=condition.containsKey("startMonth")&&!"".equals(condition.get("startMonth").toString())?Integer.valueOf(condition.get("startMonth").toString()):12;
		Integer maxMonth=condition.containsKey("endMonth")&&!"".equals(condition.get("endMonth").toString())?Integer.valueOf(condition.get("endMonth").toString()):12;
		condition.put("minMonth" , minMonth);
		condition.put("maxMonth" , maxMonth);
		Map<String,Object> t=this.getWhere(condition);		
		List<Object> cond=new ArrayList<Object>();
		
		String property="recommend.publications.id,recommend.publications.title,recommend.publications.code," +
						"recommend.publications.publisher.name," +
						"year";
		String field="e.id"
				+",(select p.title from PPublications p where p.id=e.id ) as title"
				+",(select p.code from PPublications p where p.id=e.id ) as code,"
				+"(select p.publisher.name from PPublications p where p.id=e.id ) as name" +				
				",(select cast (count(*) as string) from RRecommendDetail rrd where " +
				"rrd.recommend.publications.sourceId = ? and YEAR(rrd.createdon) = ? " +
				"and rrd.recommend.publications.id= e.id) ";
		cond.add(condition.get("sourceId"));
		cond.add(Integer.valueOf(condition.get("year").toString()));
		System.out.println("@@@@@@@@minMonth:"+minMonth);
		System.out.println("@@@@@@@@maxMonth:"+maxMonth);
		
		for(int i=minMonth;i<=maxMonth;i++){
			property+=",month"+i;
			field +=",(select cast (count(*) as string) from RRecommendDetail rrd where " +
					"rrd.recommend.publications.sourceId = ? and YEAR(rrd.createdon) = ? " +
					"and MONTH(rrd.createdon) = ? and rrd.recommend.publications.id= e.id) ";
			cond.add(condition.get("sourceId"));
			cond.add(Integer.valueOf(condition.get("year").toString()));		
			cond.add(i);
		}	
		for(Object o:(List<Object>)t.get("condition")){
			cond.add(o);
		}
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString() ,cond.toArray(),  " group by e.id "+sort, RRecommendDetail.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<RRecommendDetail> getCounterCountForPublisher(Map<String,Object> condition)throws Exception{
		List<RRecommendDetail> list=null;
		String hql=" from RRecommendDetail a left join a.recommend c left join c.institution f left join c.publications e left join e.publisher g ";
		Map<String,Object> t=this.getWhere(condition);		
		List<Object> cond=new ArrayList<Object>();
		
		String property=" id ";
		String field="cast(count(*) as string)";
				
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString() ,((List<Object>)t.get("condition")).toArray(),  " group by e.id ", RRecommendDetail.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
}
