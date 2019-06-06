package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.CAccount;

public class CAccountDao extends CommonDao<CAccount,String> {
	
	private Map<String,Object> getWhere(Map<String,Object> map){
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;				
		/**
		 * 账户名
		 */
		if(CollectionsUtil.exist(map, "uid")&&!"".equals(map.get("uid"))){
			if(flag==0){
				whereString+=" where lower(a.uid) like ? ";
				flag=1;
			}else{
				whereString+=" and lower(a.uid) like ? ";
			}
			condition.add("%"+map.get("uid").toString().trim().toLowerCase()+"%");
		}
		if(CollectionsUtil.exist(map, "loguid")&&!"".equals(map.get("loguid"))){
			if(flag==0){
				whereString+=" where a.uid = ?";
				flag=1;
			}else{
				whereString+=" and a.uid = ?";
			}
			condition.add(map.get("loguid"));
		}
		if(CollectionsUtil.exist(map, "loginid")&&!"".equals(map.get("loginid"))){
			String regex="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
			if (map.get("loginid").toString().matches(regex)) {
				if(flag==0){
					whereString+=" where d.val like ?";
					flag=1;
				}else{
					whereString+=" and d.val like ?";
				}
			} else {
				if(flag==0){
					whereString+=" where a.uid like ?";
					flag=1;
				}else{
					whereString+=" and a.uid like ?";
				}
			}
			condition.add(map.get("loginid"));
		}
		if(CollectionsUtil.exist(map, "pwd")&&!"".equals(map.get("pwd"))){
			if(flag==0){
				whereString+=" where a.pwd = ?";
				flag=1;
			}else{
				whereString+=" and a.pwd = ?";
			}
			condition.add(map.get("pwd"));
		}
		/**
		 * 用户id
		 */
		if(CollectionsUtil.exist(map, "userId")&&!"".equals(map.get("userId"))){
			if(flag==0){
				whereString+=" where b.id = ? ";
				flag=1;
			}else{
				whereString+=" and b.id = ? ";
			}
			condition.add(map.get("userId"));
		}
		/**
		 * 用户姓名
		 */
		if(CollectionsUtil.exist(map, "userName")&&!"".equals(map.get("userName"))){
			if(flag==0){
				whereString+=" where lower( b.name) like ?";
				flag=1;
			}else{
				whereString+=" and lower(b.name) like ?";
			}
			condition.add("%"+map.get("userName").toString().trim().toLowerCase()+"%");
		}
		/**
		 * 用户类型id 
		 * */
		if(CollectionsUtil.exist(map, "userTypeId")&&!"".equals(map.get("userTypeId"))){
			if(flag==0){
				whereString+=" where b.userType.id = ? ";
				flag=1;
			}else{
				whereString+=" and b.userType.id = ? ";
			}
			condition.add(map.get("userTypeId"));
		}
		/**
		 * 状态
		 */
		if(CollectionsUtil.exist(map, "status")&&!"".equals(map.get("status"))&&map.get("status")!=null){
			if(flag==0){
				whereString+=" where a.status = ?";
				flag=1;
			}else{
				whereString+=" and a.status = ?";
			}
			condition.add(Integer.parseInt(map.get("status").toString()));
		}
		/**
		 * 机构id 
		 * */
		if(CollectionsUtil.exist(map, "institutionId")&&!"".equals(map.get("institutionId"))){
			if(flag==0){
				whereString+=" where b.institution.id = ? ";
				flag=1;
			}else{
				whereString+=" and b.institution.id = ? ";
			}
			condition.add(map.get("institutionId"));
		}
		if(CollectionsUtil.exist(map, "sendStatus")&&map.get("sendStatus")!=null&&!"".equals(map.get("sendStatus"))){
			if(flag==0){
				whereString+=" where b.sendStatus = ? ";
				flag=1;
			}else{
				whereString+=" and b.sendStatus = ? ";
			}
			condition.add(map.get("sendStatus"));
		}
		
		/**
		 * 用户类型组
		 * */
		if(CollectionsUtil.exist(map, "userTypeIds")&&!"".equals(map.get("userTypeIds"))){
			if(map.get("userTypeIds") instanceof Object[]){
				Object[] types = (Object[])map.get("userTypeIds");
				String where_ = "";
				String _where = ")";
				String where = "";
				if(flag==0){
					where_=" where b.userType.id in (";
					flag=1;
				}else{
					where_=" and b.userType.id in (";
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
		
		table.put("where",whereString);
		table.put("condition", condition);
		return table;
	}	
	/**
	 * 获取登陆信息列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<CAccount> getList(Map<String,Object> condition,String sort)throws Exception{
		List<CAccount> list=null;
		String hql=" from CAccount a left join a.user b left join b.institution c left join b.userProps d";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,uid,pwd,status,createdon,user.id,user.name,user.userType.id,user.userType.name,user.institution.id,user.institution.name,user.level,user.department,user.title,user.telephone";//,createdby,user.userProps.val";
		String field="a.id,a.uid,a.pwd,a.status,a.createdon,b.id,b.name,b.userType.id,b.userType.name,c.id,c.name,b.level,b.department,b.title,b.telephone";//,(select d.val from CUserProp d where d.user.id=b.id and d.code='freetax'),d.val";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, CAccount.class.getName());
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
	public List<CAccount> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<CAccount> list=null;
		String hql=" from CAccount a left join a.user b left join b.institution c ";
		Map<String,Object> t=this.getWhere(condition);
		String property="id,uid,pwd,status,createdon,user.id,user.name,user.userType.id,user.userType.name,user.institution.id,user.institution.name,user.level,createdby,updatedby,user.createdby,user.updatedby";
		String field="a.id,a.uid,a.pwd,a.status,a.createdon,b.id,b.name,b.userType.id,b.userType.name,c.id,c.name,b.level,(select d.val from CUserProp d where d.user.id=b.id and d.code='freetax'),(select e.val from CUserProp e where e.user.id=b.id and e.code='department'),(select cast(count(*) as string) from RRecommendDetail f where f.user.id=b.id),(select cast(count(*) as string) from RRecommendDetail g where g.user.id=b.id and g.recommend.isOrder=2)";
		try{
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, CAccount.class.getName(),pageCount,page*pageCount);
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
		List<CAccount> list=null;
		Map<String,Object> t=this.getWhere(condition);
		String hql=" from CAccount a left join a.user b";
		try{
			list=this.hibernateDao.getListByHql("id","cast(count(*) as string)", hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),"", CAccount.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list==null?0:Integer.valueOf(list.get(0).getId());
	}
}
