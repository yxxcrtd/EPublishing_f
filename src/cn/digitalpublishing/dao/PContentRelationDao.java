package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.PContentRelation;
import cn.digitalpublishing.ep.po.PCsRelation;
import cn.digitalpublishing.util.web.DateUtil;
import cn.digitalpublishing.util.web.IpUtil;

public class PContentRelationDao extends CommonDao<PContentRelation, String> {

	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;
		
		/**
		 * 1.separateCon 选中的期刊（是否合刊分刊）
		 */
		if(CollectionsUtil.exist(map, "separateCon")&&!"".equals(map.get("separateCon"))){
			if(flag==0){
				whereString+=" where p.id = ?";
				flag=1;
			}else{
				whereString+=" and p.id = ?";
			}
			condition.add(map.get("separateCon").toString());
		}
		/**
		 * 2.issueCon 填写的issn（是否合刊分刊）
		 */
		if(CollectionsUtil.exist(map, "issueCon")&&!"".equals(map.get("issueCon"))){
			if(flag==0){
				whereString+=" where i.id = ?";
				flag=1;
			}else{
				whereString+=" and i.id = ?";
			}
			condition.add(map.get("issueCon").toString());
		}
		/**
		 * 3.mark表识 1-分刊 2-合刊
		 */
		if(CollectionsUtil.exist(map, "mark")&&!"".equals(map.get("mark"))){
			if(flag==0){
				whereString+=" where a.mark = ?";
				flag=1;
			}else{
				whereString+=" and a.mark = ?";
			}
			condition.add(map.get("mark").toString());
		}
		/**
		 *	4.id
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
		List<PContentRelation> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from PContentRelation a left join a.separateCon p left join a.issueCon i";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),CollectionsUtil.exist(condition, "groupBy")?"group by " + condition.get("groupBy") : "", PCsRelation.class.getName());
		}catch(Exception e){
//			e.printStackTrace();
			throw e;
		}
		return list==null?0:list.size()!=1?list.size():Integer.valueOf(list.get(0).getId());
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
	public List<PContentRelation> getList(Map<String, Object> condition,
			String sort) throws Exception {
		List<PContentRelation> list=null;
		String hql=" from PContentRelation a left join a.separateCon p left join a.issueCon i";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,mark,occurTime,separateCon.id,separateCon.title,separateCon.type,separateCon.pubDate,separateCon.code,issueCon.code,issueCon.title,issueCon.id";
		String field="a.id,a.mark,a.occurTime,p.id,p.title,p.type,p.pubDate,p.code,i.code,i.title,i.id";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PContentRelation.class.getName());
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
	public List<PContentRelation> getPubListByAlertsInfo(Map<String, Object> condition,
			String sort) throws Exception {
		List<PContentRelation> list=null;
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
	public List<PContentRelation> getTops(Map<String,Object> condition,String sort,Integer number)throws Exception{
		List<PContentRelation> list=null;
		String hql=" from PCsRelation a right join a.subject s left join a.publications p left join p.publisher pu ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,subject.id,subject.name,publications.pubDate,publications.type,publications.year,publications.month,publications.id,publications.code,publications.title,publications.cover,publications.buyTimes,publications.publications.id,publications.volume.id,publications.volumeCode,publications.issue.id,publications.issueCode,publications.startPage,publications.endPage,publications.author ,publications.publisher.id,publications.publisher.name";
		String field="a.id,s.id,s.name,p.pubDate,p.type,p.year,p.month,p.id,p.code,p.title,p.cover,p.buyTimes,p.publications.id,p.volume.id,p.volumeCode,p.issue.id,p.issueCode,p.startPage,p.endPage,p.author,pu.id,pu.name ";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, PCsRelation.class.getName(),number,0);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PContentRelation> getSubStatistic(Map<String,Object> condition)throws Exception{
		List<PContentRelation> list=null;
		String hql=" from PContentRelation a ";
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
}
