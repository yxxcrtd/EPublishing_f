package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.ep.po.PPublicationsRelation;

public class PPublicationsRelationDao  extends CommonDao<PPublicationsRelation, String> {

	public List<PPublicationsRelation> getPublicationsRelationList(
			Map<String, Object> condition, String sort) throws Exception {
		// TODO Auto-generated method stub
		List<PPublicationsRelation> list=null;
		String hql=" from PPublicationsRelation a left join a.separateCon b left join a.issueCon c ";
		Map<String,Object> t=this.getWhere(condition);
		String property=" id,separateCon.id,issueCon.id,occurTime,commentCon,createDate,updateDate,separateCon.code,issueCon.code,separateCon.title,issueCon.title,separateCon.type,issueCon.type";
		String field=" a.id,b.id,c.id,a.occurTime,a.commentCon,a.createDate,a.updateDate,b.code,c.code,b.title,c.title,b.type,c.type";
		try{
			System.out.println((List<Object>)t.get("condition"));
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PPublicationsRelation.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	private Map<String, Object> getWhere(Map<String, Object> map) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		
		/**
		 * 1.separateCon
		 */
		if(CollectionsUtil.exist(map, "separateCon")&&!"".equals(map.get("separateCon"))&&map.get("separateCon")!=null){
			if(flag==0){
				whereString+=" where a.separateCon.id = ?";
				flag=1;
			}else{
				whereString+=" and a.separateCon.id = ?";
			}
			condition.add(map.get("separateCon"));
		}
		/**
		 * 2.issueCon
		 */
		if(CollectionsUtil.exist(map, "issueCon")&&!"".equals(map.get("issueCon"))&&map.get("issueCon")!=null){
			if(flag==0){
				whereString+=" where a.issueCon.id = ?";
				flag=1;
			}else{
				whereString+=" and a.issueCon.id = ?";
			}
			condition.add(map.get("issueCon"));
		}
		/**
		 * 3.id
		 */
		if(CollectionsUtil.exist(map, "id")&&map.get("id")!=null&&!"".equals(map.get("id"))){
			if(flag==0){
				whereString+=" where a.issueCon.id = ? or a.separateCon.id = ?";
				flag=1;
			}else{
				whereString+=" and a.issueCon.id = ?  or a.separateCon.id = ?";
			}
			condition.add(map.get("id"));
			condition.add(map.get("id"));
		}
		/**
		 * 3.id
		 */
		if(CollectionsUtil.exist(map, "ids")&&map.get("ids")!=null&&!"".equals(map.get("ids"))){
			if(flag==0){
				whereString+=" where a.id in ?";
				flag=1;
			}else{
				whereString+=" and a.id in ?";
			}
			condition.add(map.get("ids"));
		}
		
		table.put("where",whereString);
		table.put("condition", condition);
		return table;
	
	}

}
