package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.LLicenseIp;

public class LLicenseIpDao extends CommonDao<LLicenseIp, String> {

	@SuppressWarnings("unchecked")
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * ip
		 * */
		if(CollectionsUtil.exist(map, "ip")&&!"".equals(map.get("ip"))){
			if(flag==0){
				whereString+=" where (a.sip <= ? and a.eip>=?) ";
				flag=1;
			}else{
				whereString+=" and (a.sip <= ? and a.eip>=?) ";
			}
			condition.add(map.get("ip"));
			condition.add(map.get("ip"));
		}
		/**
		 * status 状态
		 * */
		if(CollectionsUtil.exist(map, "status")&&!"".equals(map.get("status"))){
			if(flag==0){
				whereString+=" where b.status = ?";
				flag=1;
			}else{
				whereString+=" and b.status = ?";
			}
			condition.add(map.get("status"));
		}
		/**
		 * userId 用户Id
		 * */
		if(CollectionsUtil.exist(map, "userId")&&!"".equals(map.get("userId"))){
			if(flag==0){
				whereString+=" where b.user.id = ?";
				flag=1;
			}else{
				whereString+=" and b.user.id = ?";
			}
			condition.add(map.get("userId"));
		}
		/**
		 * publicationId 产品Id
		 * */
		if(CollectionsUtil.exist(map, "publicationId")&&!"".equals(map.get("publicationId"))){
			if(flag==0){
				whereString+=" where b.publications.id = ?";
				flag=1;
			}else{
				whereString+=" and b.publications.id = ?";
			}
			condition.add(map.get("publicationId"));
		}
		/**
		 * pubParentId 父级Id
		 */
		if(CollectionsUtil.exist(map, "pubParentId")&&!"".equals(map.get("pubParentId"))){
			if(flag==0){
				whereString+=" where b.publications.publications.id = ? ";
				flag=1;
			}else{
				whereString+=" and  b.publications.publications.id = ? ";
			}
			condition.add(map.get("pubParentId"));
		}
		/**
		 * LicenseId
		 * */
		if(CollectionsUtil.exist(map, "licenseId")&&!"".equals(map.get("licenseId"))){
			if(flag==0){
				whereString+=" where b.id = ?";
				flag=1;
			}else{
				whereString+=" and b.id = ?";
			}
			condition.add(map.get("licenseId"));
		}
		
		/**
		 * publicationId 产品Id
		 * */
		if(CollectionsUtil.exist(map, "articleId")&&!"".equals(map.get("articleId"))){
			if(flag==0){
				whereString+=" where (b.publications.id = ? or b.publications.publications.id=? or b.publications.volume.id=? or b.publications.issue.id=?)";
				flag=1;
			}else{
				whereString+=" and (b.publications.id = ? or b.publications.publications.id=? or b.publications.volume.id=? or b.publications.issue.id=?)";
			}
			condition.add(map.get("articleId"));
			condition.add(map.get("articleId"));
			condition.add(map.get("articleId"));
			condition.add(map.get("articleId"));
		}
		
		/**
		 * articleIds 
		 * */
		int flag2 = 0;
		if(CollectionsUtil.exist(map, "articleIds")&&!"".equals(map.get("articleIds"))){
			Map<String,Object> con = (Map<String, Object>) map.get("articleIds");
			String str2 = "";
				if(CollectionsUtil.exist(con, "id")&&!"".equals(con.get("id"))){
					if(flag==0){
						str2+="  b.publications.id = ? ";
						flag2 = 1;
					}else{
						str2+=" or b.publications.id = ? ";
					}
					condition.add(con.get("id"));
				}
				if(CollectionsUtil.exist(con, "pId")&&!"".equals(con.get("pId"))){
					if(flag==0){
						str2+="  b.publications.id = ? ";
						flag2 = 1;
					}else{
						str2+=" or b.publications.id = ? ";
					}
					condition.add(con.get("pId"));
				}
				if(CollectionsUtil.exist(con, "vId")&&!"".equals(con.get("vId"))){
					if(flag==0){
						str2+="  b.publications.id = ? ";
						flag2 = 1;
					}else{
						str2+=" or b.publications.id = ? ";
					}
					condition.add(con.get("vId"));
				}
				if(CollectionsUtil.exist(con, "isId")&&!"".equals(con.get("isId"))){
					if(flag==0){
						str2+="  b.publications.id = ? ";
						flag2 = 1;
					}else{
						str2+=" or b.publications.id = ? ";
					}
					condition.add(con.get("isId"));
				}
				if(!"".equals(str2.trim())){
					if(flag==0){
						whereString += " where ( "+str2+" ) ";
						flag=1;
					}else{
						whereString += " and ( "+str2+" ) ";
					}
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
	public List<LLicenseIp> getList(Map<String,Object> condition,String sort)throws Exception{
		List<LLicenseIp> list=null;
		String hql=" from LLicenseIp a left join a.license b ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,startIp,endIp,license.id,license.status,license.code,license.publications.id,license.publications.code,license.user.id,license.readUrl,license.licenseUId,license.licensePwd,license.accessType,license.accessUIdType,license.complicating";
		String field="a.id,a.startIp,a.endIp,b.id,b.status,b.code,b.publications.id,b.publications.code,b.user.id,b.readUrl,b.licenseUId,b.licensePwd,b.accessType,b.accessUIdType,b.complicating";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, LLicenseIp.class.getName());
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
	public List<LLicenseIp> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<LLicenseIp> list=null;
		String hql=" from LLicenseIp a left join a.license b ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,startIp,endIp,license.id,license.status,license.code,license.publications.id,license.user.id,license.readUrl,license.licenseUId,license.licensePwd,license.accessType,license.accessUIdType";
		String field="a.id,a.startIp,a.endIp,b.id,b.status,b.code,b.publications.id,b.user.id,b.readUrl,b.licenseUId,b.licensePwd,b.accessType,b.accessUIdType";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, LLicenseIp.class.getName(),pageCount,page*pageCount);
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
		List<LLicenseIp> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from LLicenseIp a left join a.license b ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", LLicenseIp.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	/**
	 * 根据机构ID删除LicenseIP
	 * @param instId
	 * @throws Exception
	 */
	public void deleteByInsId(String instId)throws Exception{
		String hql = "";
		try{
			hql = "delete from LLicenseIp a where a.license.id in (select b.id from LLicense b where b.user.institution.id =?)";
			Object[] condition = new Object[]{instId};
			this.hibernateDao.executeHql(hql, condition);
		}catch(Exception e){
			throw e;
		}
	}
	/**
	 * 根据LicenseId删除LicenseIP
	 * @param instId
	 * @throws Exception
	 */
	public void deleteByLicenseId(String licenseId)throws Exception{
		try{
			String hql = "delete from LLicenseIp a where a.license.id =?";
			Object[] condition = new Object[]{licenseId};
			this.hibernateDao.executeHql(hql, condition);
		}catch(Exception e){
			throw e;
		}
	}
}
