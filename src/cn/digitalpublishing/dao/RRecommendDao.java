package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.com.daxtech.framework.util.DateUtil;
import cn.digitalpublishing.ep.po.RRecommend;

public class RRecommendDao extends CommonDao<RRecommend, String> {

	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * 1.institutionid
		 */
		if(CollectionsUtil.exist(map, "institutionid")&&!"".equals(map.get("institutionid"))&&map.get("institutionid")!=null){
			if(flag==0){
				whereString+=" where a.institution.id = ?";
				flag=1;
			}else{
				whereString+=" and a.institution.id = ?";
			}
			condition.add(map.get("institutionid").toString());
		}
		/**
		 * 书籍类型1-电子书  2-期刊 类型数组
		 */
		if(CollectionsUtil.exist(map, "pubtypes")&&map.get("pubtypes")!=null&&!"".equals(map.get("pubtypes").toString())){
			if(flag==0){
				whereString+=" where c.type in (";
				flag=1;
			}else{
				whereString+=" and c.type in (";
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
		
		/**
		 * 2.publicationsid
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
		 * 3.isorder
		 */
//		if(CollectionsUtil.exist(map, "isorder")&&!"".equals(map.get("isorder"))){
//			if(flag==0){
//				whereString+=" where a.isOrder = ?";
//				flag=1;
//			}else{
//				whereString+=" and a.isOrder = ?";
//			}
//			condition.add(Integer.valueOf(map.get("isorder").toString()));
//		}
		if(CollectionsUtil.exist(map, "orderStatus")&&!"".equals(map.get("orderStatus")) && map.get("orderStatus")!=null){
			String cond=null;
			String iid=(CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId")) && map.get("institutionId")!=null)?"?":"b.id";
			
			if("3".equals(map.get("orderStatus").toString())){//已取消
				cond=" a.isOrder = '3' ";				
			}else if("4".equals(map.get("orderStatus").toString())){//已购买
				cond=" ((select cast(count(*) as int) from LLicense ll where ll.status=1 and ll.user.institution.id="+ iid +" and ll.publications.id=c.id) >0 )";
				if("?".equals(iid)){
					condition.add(map.get("institutionId"));
				}
			}else if("2".equals(map.get("orderStatus").toString())){//购买中
				cond=" a.isOrder != '3' and ((select cast(count(*) as int) from OOrderDetail od2 where od2.price.publications.id=c.id and od2.status != 99 and od2.status !=3) > 0) "
				+ "and ((select cast(count(*) as int) from LLicense ll where ll.status=1 and ll.user.institution.id="+ iid +" and ll.publications.id=c.id) =0 )";
				if("?".equals(iid)){
					condition.add(map.get("institutionId"));
				}
			}else if("1".equals(map.get("orderStatus").toString())){//未购买
				cond=" a.isOrder != '3' and ((select cast(count(*) as int) from OOrderDetail od3 where od3.price.publications.id=c.id) = 0) "
					+ "and ((select cast(count(*) as int) from LLicense ll where ll.status=1 and ll.user.institution.id="+ iid +" and ll.publications.id=c.id) =0 )";
				if("?".equals(iid)){
					condition.add(map.get("institutionId"));
				}
			}
			
			if(flag==0){
				whereString+=" where " + cond;
				flag=1;
			}else{
				whereString+=" and " + cond;
			}
			
		}
		
		/**
		 * 4.pubtitle
		 */
		if(CollectionsUtil.exist(map, "pubtitle")&&!"".equals(map.get("pubtitle"))){
			if(flag==0){
				whereString+=" where lower(c.title) like ?";
				flag=1;
			}else{
				whereString+=" and lower(c.title )like ?";
			}
			condition.add(("%"+map.get("pubtitle").toString().toLowerCase() + "%"));
		}
		/**
		 * 5.pubcode
		 */
		if(CollectionsUtil.exist(map, "pubCode")&&!"".equals(map.get("pubCode"))){
			if(flag==0){
				whereString+=" where c.code like ?";
				flag=1;
			}else{
				whereString+=" and c.code like ?";
			}
			condition.add((map.get("pubCode").toString() + "%"));
		}
		/**
		 * type
		 */
		if(CollectionsUtil.exist(map, "type")&&!"".equals(map.get("type"))){
			if(flag==0){
				whereString+=" where c.type = ?";
				flag=1;
			}else{
				whereString+=" and c.type = ?";
			}
			condition.add(map.get("type"));
		}
		if(CollectionsUtil.exist(map, "isCn")&&!"".equals(map.get("isCn"))){
			Boolean isCn=map.get("isCn")!=null && "true".equals(map.get("isCn").toString().toLowerCase());
			Object[] types = new Object[]{"chs","chn","cht"};
			String where_ = "";
			String _where = ")";
			String where = "";
			if(flag==0){
				where_=" where c.lang ";
				flag=1;
			}else{
				where_=" and c.lang ";
			}
			where_ += isCn?" in( " : " not in ( ";
			for(int i =0;i<types.length;i++){
				where+="?";
				if(i<types.length-1){
					where+=",";
				}
				condition.add(types[i]);
			}
			whereString += where_ + where + _where;
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
	public List<RRecommend> getList(Map<String,Object> condition,String sort)throws Exception{
		List<RRecommend> list=null;
		String hql=" from RRecommend a left join a.institution b left join a.publications c left join c.publisher d";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,name,isOrder,createdby,createdon,updatedby,updatedon,institution.id,institution.code,institution.name,publications.id,publications.title,publications.author,publications.publisher.name,publications.code,recommendDetailCount,proCount";
		String field="a.id,a.name,a.isOrder,a.createdby,a.createdon,a.updatedby,a.updatedon,b.id,b.code,b.name,c.id,c.title,c.author,d.name,c.code,a.recommendDetailCount,a.proCount";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, RRecommend.class.getName());
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
	public List<RRecommend> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<RRecommend> list=null;
		String hql=" from RRecommend a left join a.institution b left join a.publications c left join c.publisher d";
		Map<String,Object> t=this.getWhere(condition);
		List<Object> cond=new ArrayList<Object>();

		String property="id,name,particulars,createdby,createdon,updatedby,updatedon,institution.id,institution.code,institution.name,publications.id,publications.title,publications.author,publications.publisher.name,publications.code,publications.type,publications.status,recommendDetailCount" +
				",isOrder,isOrdering,proCount,orderStatus ";
		String field="a.id,a.name,a.particulars,a.createdby,a.createdon,a.updatedby,a.updatedon,b.id,b.code,b.name,c.id,c.title,c.author,d.name,c.code,c.type,c.status,a.recommendDetailCount" +
				",a.isOrder" +
				",(select cast(count(*) as int) from OOrderDetail od where od.price.publications.id=c.id and od.status != 99 and od.status !=3 ) " +
				",a.proCount"+
				",(select od.status from OOrderDetail od where od.recommend.id = a.id)";
		property+=",isOrdered ";
		field+=",(select cast(count(*) as int) from LLicense ll where ll.status=1 and ll.user.institution.id=a.institution.id and ll.publications.id=c.id )";		

		for(Object o:(List<Object>)t.get("condition")){
			cond.add(o);
		}
		
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), cond.toArray(),sort, RRecommend.class.getName(),pageCount,page*pageCount);
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
		List<RRecommend> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from RRecommend a left join a.institution b left join a.publications c";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", RRecommend.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	
}
