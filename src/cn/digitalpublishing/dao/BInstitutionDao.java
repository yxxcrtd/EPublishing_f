package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.BInstitution;

public class BInstitutionDao extends CommonDao<BInstitution,String> {
	
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;				
		
		if(CollectionsUtil.exist(map, "existid")&&!"".equals(map.get("existid"))){
			if(flag==0){
				whereString+=" where a.id != ?";
				flag=1;
			}else{
				whereString+=" and a.id != ?";
			}
			condition.add(map.get("existid"));
		}
 		
		if(CollectionsUtil.exist(map, "pubId")&&!"".equals(map.get("pubId"))){
//			if(flag==0){
//				whereString+=" where a.id in (select u.institution.id from CUser u where u.level =2 and u.id in (select l.user.id from LLicense l where l.status=1 and l.publications.id = ?))";
//				flag=1;
//			}else{
//				whereString+=" and a.id in (select u.institution.id from CUser u where u.level =2 and u.id in (select l.user.id from LLicense l where l.status=1 and l.publications.id = ?))";
//			}
			if(flag==0){
				whereString+=" where exists (select l.id from LLicense l left join l.user u left join u.institution i where l.publications.id=? and u.level=2 and i.id=a.id and l.type=1)";
				flag=1;
			}else{
				whereString+=" and exists (select l.id from LLicense l left join l.user u left join u.institution i where l.publications.id=? and u.level=2 and i.id=a.id and l.type=1)";
			}
			condition.add(map.get("pubId"));
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
	public List<BInstitution> getList(Map<String,Object> condition,String sort)throws Exception{
		List<BInstitution> list=null;
		String hql=" from BInstitution a";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,logo,name,logoNote,logoUrl";
		String field="a.id,a.code,a.logo,a.name,a.logoNote,a.logoUrl";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BInstitution.class.getName());
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
	public List<BInstitution> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<BInstitution> list=null;
		String hql=" from BInstitution a";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,logo,name,logoNote,logoUrl";
		String field="a.id,a.code,a.logo,a.name,a.logoNote,a.logoUrl";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BInstitution.class.getName(),pageCount,page*pageCount);
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
		List<BInstitution> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from BInstitution a ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", BInstitution.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}

	@SuppressWarnings("unchecked")
	public List<BInstitution> getlicenseList(Map<String, Object> condition,String sort)throws Exception {
		List<BInstitution> list=null;
		String hql=" from BInstitution a";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,code,logo,name,logoNote,logoUrl";
		String field="a.id,a.code,a.logo,a.name,a.logoNote,a.logoUrl";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, BInstitution.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list;
	}

}
