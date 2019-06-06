package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.OOrderDetail;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.util.web.IpUtil;

public class OOrderDetailDao extends CommonDao<OOrderDetail, String> {

	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * itemType 订单ID  类型 1-电子书 2-期刊 3-章节 4-文章 5-数据库 6-期刊卷 7 期刊期  99-产品包
		 */
		if(CollectionsUtil.exist(map, "itemType")&&!"".equals(map.get("itemType"))){
			if(flag==0){
				whereString+=" where a.itemType = ?";
				flag=1;
			}else{
				whereString+=" and a.itemType = ?";
			}
			condition.add(map.get("itemType"));
		}
		/**
		 * institutionId 机构ID
		 */
		
		if(CollectionsUtil.exist(map, "institutionId")&&!"".equals(map.get("institutionId"))){
			if(flag==0){
				whereString+=" where exists(select t.id from CUser t where t.institution.id=? and t.id=d.id) ";
				flag=1;
			}else{
				whereString+=" and  exists(select t.id from CUser t where t.institution.id=? and t.id=d.id) ";
			}
			condition.add(map.get("institutionId").toString());
		}
		/**
		 * 1.orderId 订单ID
		 */
		if(CollectionsUtil.exist(map, "orderId")&&!"".equals(map.get("orderId"))){
			if(flag==0){
				whereString+=" where b.id = ?";
				flag=1;
			}else{
				whereString+=" and b.id = ?";
			}
			condition.add(map.get("orderId").toString());
		}
		/**
		 * 1.orderNull 无订单号，即在购物车中
		 */
		if(CollectionsUtil.exist(map, "orderNull")&&!"".equals(map.get("orderNull"))){
			if(flag==0){
				whereString+=" where b.id is null ";
				flag=1;
			}else{
				whereString+=" and b.id is null ";
			}
		}
		/**
		 * 2.parentId 父ID
		 */
		if(CollectionsUtil.exist(map, "parentId")&&!"".equals(map.get("parentId"))){
			if(map.get("parentId").equals("0")){
				if(flag==0){
					whereString+=" where a.detail.id is null ";
					flag=1;
				}else{
					whereString+=" and a.detail.id is null ";
				}
			}else{
				if(flag==0){
					whereString+=" where a.detail.id = ?";
					flag=1;
				}else{
					whereString+=" and a.detail.id = ?";
				}
				condition.add(map.get("parentId").toString());
			}
		}
		/**
		 * 3.userid
		 */
		if(CollectionsUtil.exist(map, "userid")&&!"".equals(map.get("userid"))){
			if(flag==0){
				whereString+=" where d.id = ?";
				flag=1;
			}else{
				whereString+=" and d.id = ?";
			}
			condition.add(map.get("userid").toString());
		}
		if(CollectionsUtil.exist(map, "productCode")&&!"".equals(map.get("productCode"))){
			if(flag==0){
				whereString+=" where a.productCode = ?";
				flag=1;
			}else{
				whereString+=" and a.productCode = ?";
			}
			condition.add(map.get("productCode").toString());
		}
		/**
		 * 4.publicationsid
		 */
		if(CollectionsUtil.exist(map, "publicationsid")&&!"".equals(map.get("publicationsid"))){
			if(flag==0){
				whereString+=" where c.publications.id = ?";
				flag=1;
			}else{
				whereString+=" and c.publications.id = ?";
			}
			condition.add(map.get("publicationsid").toString());
		}
		/**
		 * 4.status
		 */
		if(CollectionsUtil.exist(map, "status")&&!"".equals(map.get("status"))){
			if(flag==0){
				whereString+=" where a.status = ?";
				flag=1;
			}else{
				whereString+=" and a.status = ?";
			}
			condition.add(map.get("status"));
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
		/**
		 * 4.priceId
		 */
		if(CollectionsUtil.exist(map, "priceId")&&!"".equals(map.get("priceId"))&&map.get("priceId")!=null){
			if(flag==0){
				whereString+=" where c.id = ?";
				flag=1;
			}else{
				whereString+=" and c.id = ?";
			}
			condition.add(map.get("priceId"));
		}
		/**
		 * collectionId 判断打包集是否已经购买
		 */
		if(CollectionsUtil.exist(map, "collectionId")&&!"".equals(map.get("collectionId"))){
			if(flag==0){
				whereString+=" where c.id = ?";
				flag=1;
			}else{
				whereString+=" and c.id = ?";
			}
			condition.add(map.get("collectionId"));
		}
		/**
		 * 3.newuserid
		 */
		if(CollectionsUtil.exist(map, "newuserid")&&!"".equals(map.get("newuserid"))){
			if(flag==0){
				whereString+=" where d.id = ?";
				flag=1;
			}else{
				whereString+=" and d.id = ?";
			}
			condition.add(map.get("newuserid").toString());
		}
		
		if(CollectionsUtil.exist(map, "isPubid")&&!"".equals(map.get("isPubid"))){
			if(flag==0){
				whereString+=" where e.id = ?";
				flag=1;
			}else{
				whereString+=" and e.id = ?";
			}
			condition.add(map.get("isPubid").toString());
		}
		/**
		 * newinstitutionid
		 */
		if(CollectionsUtil.exist(map, "newinstitutionid")&&!"".equals(map.get("newinstitutionid"))){
			if(flag==0){
				whereString+=" where d.institution.id = ?";
				flag=1;
			}else{
				whereString+=" and d.institution.id = ?";
			}
			condition.add(map.get("newinstitutionid"));
		}
		if(CollectionsUtil.exist(map, "uidOrinsid")&&!"".equals(map.get("uidOrinsid"))){
			String[] ary=map.get("uidOrinsid").toString().split(",");
			if(flag==0){
				whereString+=" where (d.id = ? or d.institution.id = ? ) ";
				flag=1;
			}else{
				whereString+=" and (d.id = ? or d.institution.id = ? ) ";
			}
			condition.add(ary[0]);
			condition.add(ary[1]);
		}
		
		if(CollectionsUtil.exist(map, "newpublicationsid")&&!"".equals(map.get("newpublicationsid"))){
			if(flag==0){
				whereString+=" where b.publications.id = ?";
				flag=1;
			}else{
				whereString+=" and b.publications.id = ?";
			}
			condition.add(map.get("newpublicationsid"));
		}
		if(CollectionsUtil.exist(map, "pricestatus")&&!"".equals(map.get("pricestatus"))){
			if(flag==0){
				whereString+=" where b.status = ?";
				flag=1;
			}else{
				whereString+=" and b.status = ?";
			}
			condition.add(map.get("pricestatus"));
		}

		if(CollectionsUtil.exist(map, "level")&&!"".equals(map.get("level"))){
			if(flag==0){
				whereString+=" where d.level = ?";
				flag=1;
			}else{
				whereString+=" and d.level = ?";
			}
			condition.add(map.get("level"));
		}

//		if(CollectionsUtil.exist(map, "level")&&!"".equals(map.get("level"))){
//			if(flag==0){
//				whereString+=" where c.level = ?";
//				flag=1;
//			}else{
//				whereString+=" and c.level = ?";
//			}
//			condition.add(map.get("level"));
//		}

		if(CollectionsUtil.exist(map, "newUserId")&&!"".equals(map.get("newUserId"))){
			String in="";
			String[] ary=map.get("newUserId").toString().split(",");
			for (int i = 0; i < ary.length; i++) {
				in+="?"+",";
				condition.add(ary[i]);
			}
			in="("+in.substring(0,in.length()-1)+")";
			if(flag==0){
				whereString+=" where c.id in "+in+"";
				flag=1;
			}else{
				whereString+=" and c.id in "+in+"";
			}
		}
		/**
		 * 4.statusArry
		 */
		if(CollectionsUtil.exist(map, "statusArry")&&!"".equals(map.get("statusArry"))&&map.get("statusArry")!=null){
			Integer[] arry = (Integer[]) map.get("statusArry");
			if(arry!=null && arry.length>0){
				String where_ = "";
				String _where = ")";
				String where = "";
				if(flag==0){
					where_=" where a.status in (";
					flag=1;
				}else{
					where_=" and a.status in (";
				}
				for(int i =0;i<arry.length;i++){
					where+="?";
					if(i<arry.length-1){
						where+=",";
					}
					condition.add(arry[i]);
				}
				whereString += where_ + where + _where;
			}
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
	public List<OOrderDetail> getList(Map<String,Object> condition,String sort)throws Exception{
		List<OOrderDetail> list=null;
		String hql=" from OOrderDetail a left join a.order b left join a.price c left join a.user d left join d.institution u left join c.publications e left join e.publications pp left join a.collection f left join e.publisher er left join c.priceType pt ";
		Map<String,Object> t=this.getWhere(condition);
		String property="price.currency,price.price,order.id,order.payType,order.code,price.complicating,id,odetailNum,createdby,createdon,updatedby,updatedon,name,ip" +
				",currency,salePrice,tax,salePriceExtTax,quantity,itemType,productCode,status,settledPrice" +
				",price.id,price.code,price.type,price.publications.id,price.publications.title,price.publications.local" +
				",price.publications.code,price.publications.webUrl,user.id,user.name,user.level,collection.id" +
				",collection.name,collection.code,collection.currency,collection.price,collection.createOn" +
				",price.publications.sisbn,price.publications.hisbn"+
				",price.publications.cover"+
				",price.publications.publisher.name,price.publications.publisher.nameEn" +
				",price.publications.pubDate" +
				",price.publications.author" +
				",price.publications.type" +
				",price.publications.publications.id" +
				",price.effective" +
				",price.complicating" +
				",price.category,price.priceType.code"+
				",user.institution.id";
		String field="c.currency,c.price,b.id,b.payType,b.code,c.complicating,a.id,a.odetailNum,a.createdby,a.createdon,a.updatedby,a.updatedon,a.name,a.ip" +
				",a.currency,a.salePrice,a.tax,a.salePriceExtTax,a.quantity,a.itemType,a.productCode,a.status,a.settledPrice" +
				",c.id,c.code,c.type,e.id,e.title,e.local,e.code,e.webUrl,d.id,d.name,d.level,f.id" +
				",f.name,f.code,f.currency,f.price,f.createOn"+
				",e.sisbn,e.hisbn" +
				",e.cover" +
				",er.name,er.nameEn"+
				",e.pubDate" +
				",e.author" +
				",e.type" +
				",pp.id" +
				",c.effective" +
				",c.complicating"+
				",c.category,pt.code"+
				",u.id";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, OOrderDetail.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e; 
		}
		return list;
	}
	/**
	 * 同步订单详情查询
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<OOrderDetail> getOrderDetailsList(Map<String,Object> condition,String sort)throws Exception{
		List<OOrderDetail> list=null;
		String hql=" from OOrderDetail a left join a.order b left join a.price c left join a.user d  left join c.publications e  left join a.collection f  ";
		Map<String,Object> t=this.getWhere(condition);
		String property="price.currency,price.price,order.id,order.payType,order.code,price.complicating,id,odetailNum,createdby,createdon,updatedby,updatedon,name,ip" +
				",currency,salePrice,tax,salePriceExtTax,quantity,itemType,productCode,status,settledPrice" +
				",price.id,price.code,price.type,price.publications.id,price.publications.title,price.publications.local" +
				",price.publications.code,price.publications.webUrl,user.id,user.name,user.level,collection.id" +
				",collection.name,collection.code,collection.currency,collection.price,collection.createOn" +
				",price.publications.sisbn,price.publications.hisbn"+
				",price.publications.cover"+
				
				",price.publications.pubDate" +
				",price.publications.author" +
				",price.publications.type" +
				
				",price.effective" +
				",price.complicating" +
				",price.category ";
		String field="c.currency,c.price,b.id,b.payType,b.code,c.complicating,a.id,a.odetailNum,a.createdby,a.createdon,a.updatedby,a.updatedon,a.name,a.ip" +
				",a.currency,a.salePrice,a.tax,a.salePriceExtTax,a.quantity,a.itemType,a.productCode,a.status,a.settledPrice" +
				",c.id,c.code,c.type,e.id,e.title,e.local,e.code,e.webUrl,d.id,d.name,d.level,f.id" +
				",f.name,f.code,f.currency,f.price,f.createOn"+
				",e.sisbn,e.hisbn" +
				",e.cover" +
				
				",e.pubDate" +
				",e.author" +
				",e.type" +
				
				",c.effective" +
				",c.complicating"+
				",c.category " ;
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, OOrderDetail.class.getName());
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
	public List<OOrderDetail> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<OOrderDetail> list=null;
		String hql=" from OOrderDetail a left join a.order b left join a.price c left join a.user d left join c.publications e left join a.collection f left join e.publisher er left join c.priceType pt ";
		Map<String,Object> t=this.getWhere(condition);
		String property="price.complicating,id,odetailNum,createdby,createdon,updatedby,updatedon,name,ip" +
				",currency,salePrice,tax,salePriceExtTax,quantity,itemType,productCode,status,settledPrice" +
				",price.id,price.type,price.publications.id,price.publications.title,price.publications.local" +
				",price.publications.code,price.publications.webUrl,user.id,user.name,user.level,collection.id,collection.name,collection.code,collection.price,collection.createOn,collection.desc";
		String field="c.complicating,a.id,a.odetailNum,a.createdby,a.createdon,a.updatedby,a.updatedon,a.name,a.ip" +
				",a.currency,a.salePrice,a.tax,a.salePriceExtTax,a.quantity,a.itemType,a.productCode,a.status,a.settledPrice" +
				",c.id,c.type,e.id,e.title,e.local,e.code,e.webUrl,d.id,d.name,d.level,f.id,f.name,f.code,f.price,f.createOn,f.desc";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, OOrderDetail.class.getName(),pageCount,page*pageCount);
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
		List<OOrderDetail> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from OOrderDetail a left join a.order b left join a.price c left join a.user d";
		try{
 			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", OOrderDetail.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

	/**
	 * 用于购买的时候判断这个期刊是否还可以再买
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<OOrderDetail> getDetailListForAddCart(Map<String, Object> condition)throws Exception {
		List<OOrderDetail> list=null;
		String hql=" from OOrderDetail a left join a.order b left join a.price c left join a.user d left join c.publications e ";
		Map<String,Object> t=this.getWhere(condition);
		String property=" id,status ";
		String field=" a.id,a.status ";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray()," ", OOrderDetail.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e; 
		}
		return list;
	}
	
	/**
	 * 查询是否有进行中的订单
	 * @param pub
	 * @param user
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public List<OOrderDetail> getPubProcessOrder(PPublications pub,CUser user,BInstitution ins)throws Exception{
		if(pub==null || (user==null && ins==null ))
			return null;
		List<OOrderDetail> list=null;
		String hql=" from OOrderDetail a left join a.user u left join u.institution i left join a.price p left join p.publications pub ";
		List<Object> con = new ArrayList<Object>();		
		
		con.add(pub.getId());
		con.add(pub.getCode());				
		String whereString=" where a.status not in (3,10,99) and (pub.id=? or pub.code=?) and (" ;
		
		Boolean flag=false;
		//用户直接购买
		if(user!=null){
			whereString +=" u.id=? ";
			con.add(user.getId());		
			flag=true;
		}
		//用户所属机构的管理员购买
		if(ins!=null && !"".equals(ins)){			
			if(flag){
				whereString +=" or (i.id = ? and u.level = 2)";
			}else{
				whereString += " (i.id = ? and u.level = 2)";
			}
			flag=true;
			con.add(ins.getId());
		}
		
		whereString+=")";
		String property=" id ";
		String field=" a.id ";
		
		try{
			list=super.hibernateDao.getListByHql(property,field, hql + whereString, con.toArray()," ", OOrderDetail.class.getName());			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	/**
	 * 判断产品是否在购物车中
	 */
	public Integer getDetailList(Map<String,Object> condition)throws Exception{
		List<OOrderDetail> list = null;
		String hql=" from OOrderDetail a left join a.price b left join a.collection c left join a.user d";
		Map<String,Object> t = this.getWhere(condition);
		try {
			list=super.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray()," ", OOrderDetail.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
}
