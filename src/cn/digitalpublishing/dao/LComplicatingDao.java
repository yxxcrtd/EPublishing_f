package cn.digitalpublishing.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.LComplicating;
import cn.digitalpublishing.ep.po.LLicense;

public class LComplicatingDao extends CommonDao<LComplicating, String> {

	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		/**
		 * licenseId
		 * */
		if(CollectionsUtil.exist(map, "licenseId")&&!"".equals(map.get("licenseId"))){
			if(flag==0){
				whereString+=" where c.id = ?";
				flag=1;
			}else{
				whereString+=" and c.id = ?";
			}
			condition.add(map.get("licenseId"));
		}
		/**
		 * sessionId
		 * */
		if(CollectionsUtil.exist(map, "sessionId")&&!"".equals(map.get("sessionId"))){
			if(flag==0){
				whereString+=" where a.sessionId = ?";
				flag=1;
			}else{
				whereString+=" and a.sessionId = ?";
			}
			condition.add(map.get("sessionId"));
		}
		/**
		 * publicationId
		 * */
		if(CollectionsUtil.exist(map, "publicationId")&&!"".equals(map.get("publicationId"))){
			if(flag==0){
				whereString+=" where p.id = ?";
				flag=1;
			}else{
				whereString+=" and p.id = ?";
			}
			condition.add(map.get("publicationId"));
		}
		/**
		 * macId
		 * */
		if(CollectionsUtil.exist(map, "macId")&&!"".equals(map.get("macId"))){
			if(flag==0){
				whereString+=" where a.macAddr = ? ";
				flag=1;
			}else{
				whereString+=" and a.macAddr = ? ";
			}
			condition.add(map.get("macId"));
		}
		/**
		 * articleId
		 * */
		if(CollectionsUtil.exist(map, "articleId")&&!"".equals(map.get("articleId"))){
			if(flag==0){
				whereString+=" where (p.id = ? or p.publications.id=? or p.volume.id=? or p.issue.id=?";
				flag=1;
			}else{
				whereString+=" and (p.id = ? or p.publications.id=? or p.volume.id=? or p.issue.id=?";
			}
			condition.add(map.get("articleId"));
		}
		/**
		 * macId
		 * */
		if(CollectionsUtil.exist(map, "endTimeNull")&&map.get("endTimeNull")==null){
			if(flag==0){
				whereString+=" where a.endTime is null ";
				flag=1;
			}else{
				whereString+=" and a.endTime is null ";
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
	public List<LComplicating> getList(Map<String,Object> condition,String sort)throws Exception{
		List<LComplicating> list=null;
		String hql=" from LComplicating a left join a.user b left join a.license c left join c.publications p ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,license.id,license.publications.id,license.publications.code,user.id,user.name,pubCode,macAddr,sessionId,createOn";
		String field="a.id,c.id,p.id,p.code,b.id,b.name,a.pubCode,a.macAddr,a.sessionId,a.createOn";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, LComplicating.class.getName());
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
	public List<LComplicating> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<LComplicating> list=null;
		String hql=" from LComplicating a left join a.user b left join a.license c left join c.publications p ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,license.id,license.publications.id,license.publications.code,user.id,user.name,pubCode,macAddr,sessionId";
		String field="a.id,c.id,p.id,p.code,b.id,b.name,pubCode,macAddr,sessionId";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, LComplicating.class.getName(),pageCount,page*pageCount);
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
		List<LComplicating> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from LComplicating a left join a.user b left join a.license c left join c.publications p ";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", LComplicating.class.getName());
		}catch(Exception e){
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
	public void deleteByCondition(Map<String, Object> condition) throws Exception{
		String hql = "delete from epub_l_complicating ";
		String whereString="";
		List<Object> prop=new ArrayList<Object>();
		int flag=0;
		/**
		 * licenseId
		 * */
		if(CollectionsUtil.exist(condition, "licenseId")&&!"".equals(condition.get("licenseId"))){
			if(flag==0){
				whereString+=" where LICENSE_ID = ?";
				flag=1;
			}else{
				whereString+=" and LICENSE_ID = ?";
			}
			prop.add(condition.get("licenseId"));
		}
		/**
		 * sessionId
		 * */
		if(CollectionsUtil.exist(condition, "sessionId")&&!"".equals(condition.get("sessionId"))){
			if(flag==0){
				whereString+=" where COM_SESSION_ID = ?";
				flag=1;
			}else{
				whereString+=" and COM_SESSION_ID = ?";
			}
			prop.add(condition.get("sessionId"));
		}
		/**
		 * macId
		 * */
		if(CollectionsUtil.exist(condition, "macId")&&!"".equals(condition.get("macId"))){
			if(flag==0){
				whereString+=" where COM_MAC = ?";
				flag=1;
			}else{
				whereString+=" and COM_MAC = ?";
			}
			prop.add(condition.get("macId"));
		}
		try {
			this.hibernateDao.executeSql(hql+whereString, prop.toArray());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void beatWhereExists(Map<String, Object> condition) throws Exception{		
		String hql = "update LComplicating l set l.updateTime = ? ";
		String whereString="";
		int flag=0;
		List<Object> prop=new ArrayList<Object>();
		Date now=new Date();
		prop.add(now);
		if(CollectionsUtil.exist(condition, "licenseId")&&!"".equals(condition.get("licenseId"))){
			if(flag==0){
				whereString+=" where l.license.id = ?";
				flag=1;
			}else{
				whereString+=" and l.license.id = ?";
			}
			prop.add(condition.get("licenseId"));
		}
		if(CollectionsUtil.exist(condition, "macId")&&!"".equals(condition.get("macId"))){
			if(flag==0){
				whereString+=" where l.macAddr = ?";
				flag=1;
			}else{
				whereString+=" and l.macAddr = ?";
			}
			prop.add(condition.get("macId"));
		}//更新时间
		if(CollectionsUtil.exist(condition, "interval")&&condition.get("interval")==null){
			Integer interval=(Integer)condition.get("interval");
			Calendar   rightNow   =   Calendar.getInstance();		
			rightNow.add(Calendar.SECOND,-interval);// 
			if(flag==0){
				whereString+=" where l.endTime > ?";
				flag=1;
			}else{
				whereString+=" and l.endTime > ?";
			}
			prop.add(rightNow.getTime());
		}
		

		try{
			this.hibernateDao.executeHql(hql + whereString,prop.toArray());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void deleteDead(Integer sec) throws Exception{
		Calendar   rightNow   =   Calendar.getInstance();		
		rightNow.add(Calendar.SECOND,-sec);// 
				
		//String hql = "delete from LComplicating l where l.createOn < ? ";
		String hql = "update LComplicating l set l.endTime = ? where l.updateTime < ? ";
		List<Object> prop=new  ArrayList<Object>();
		Date now=new Date();
		prop.add(now);
		prop.add(rightNow.getTime());
		try{
			this.hibernateDao.executeHql(hql ,prop.toArray());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}	
	}	
	
	public void endWhereExists(Map<String, Object> condition) throws Exception{		
		
		String hql = "update LComplicating l set l.endTime = ? ";
		String whereString="";
		int flag=0;
		List<Object> prop=new ArrayList<Object>();
		Date now=new Date();
		prop.add(now);
		if(CollectionsUtil.exist(condition, "licenseId")&&!"".equals(condition.get("licenseId"))){
			if(flag==0){
				whereString+=" where l.license.id = ?";
				flag=1;
			}else{
				whereString+=" and l.license.id = ?";
			}
			prop.add(condition.get("licenseId"));
		}
		if(CollectionsUtil.exist(condition, "macId")&&!"".equals(condition.get("macId"))){
			if(flag==0){
				whereString+=" where l.macAddr = ?";
				flag=1;
			}else{
				whereString+=" and l.macAddr = ?";
			}
			prop.add(condition.get("macId"));
		}//更新时间
		if(CollectionsUtil.exist(condition, "endTime")&&(condition.get("endTime")==null)){
			if(flag==0){
				whereString+=" where l.endTime is null";
				flag=1;
			}else{
				whereString+=" and l.endTime is null";
			}
			//prop.add(condition.get("endTime"));
		}
		

		try{
			this.hibernateDao.executeHql(hql + whereString,prop.toArray());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
