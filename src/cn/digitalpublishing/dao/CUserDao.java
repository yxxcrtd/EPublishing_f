package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.CUser;

public class CUserDao extends CommonDao<CUser, String> {

	private Map<String, Object> getWhere(Map<String, Object> map) {
		Map<String, Object> table = new HashMap<String, Object>();
		String whereString = "";
		List<Object> condition = new ArrayList<Object>();
		int flag = 0;

		/**
		 * level 
		 * */
		if (CollectionsUtil.exist(map, "level") && !"".equals(map.get("level"))) {
			if (flag == 0) {
				whereString += " where a.level = ? ";
				flag = 1;
			} else {
				whereString += " and a.level = ? ";
			}
			condition.add(map.get("level"));
		}
		if (CollectionsUtil.exist(map, "notlevel") && !"".equals(map.get("notlevel"))) {
			if (flag == 0) {
				whereString += " where a.level != ? ";
				flag = 1;
			} else {
				whereString += " and a.level != ? ";
			}
			condition.add(map.get("notlevel"));
		}
		if (CollectionsUtil.exist(map, "levelin") && !"".equals(map.get("levelin"))) {
			if (map.get("levelin") instanceof Object[]) {
				Object[] types = (Object[]) map.get("levelin");
				String where_ = "";
				String _where = ")";
				String where = "";
				if (flag == 0) {
					where_ = " where a.level in (";
					flag = 1;
				} else {
					where_ = " and a.level in (";
				}
				for (int i = 0; i < types.length; i++) {
					where += "?";
					if (i < types.length - 1) {
						where += ",";
					}
					condition.add(types[i]);
				}
				whereString += where_ + where + _where;
			}
		}
		/**
		 * userId 
		 * */
		if (CollectionsUtil.exist(map, "userId") && !"".equals(map.get("userId"))) {
			if (flag == 0) {
				whereString += " where a.id = ? ";
				flag = 1;
			} else {
				whereString += " and a.id = ? ";
			}
			condition.add(map.get("userId"));
		}
		/**
		 * institutionId 
		 * */
		if (CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId"))) {
			if (flag == 0) {
				whereString += " where c.id = ? ";
				flag = 1;
			} else {
				whereString += " and c.id = ? ";
			}
			condition.add(map.get("institutionId"));
		}
		/**
		 * userTypeId 
		 * */
		if (CollectionsUtil.exist(map, "userTypeId") && !"".equals(map.get("userTypeId"))) {
			if (flag == 0) {
				whereString += " where b.id = ? ";
				flag = 1;
			} else {
				whereString += " and b.id = ? ";
			}
			condition.add(map.get("userTypeId"));
		}
		/**
		 * 发送状态 sendStatus
		 */
		if (CollectionsUtil.exist(map, "sendStatus") && !"".equals(map.get("sendStatus")) && map.get("sendStatus") != null) {
			if (Integer.valueOf(map.get("sendStatus").toString()) == 1) {
				if (flag == 0) {
					whereString += " where (a.sendStatus = ? or a.sendStatus is null)";
					flag = 1;
				} else {
					whereString += " and (a.sendStatus = ? or a.sendStatus is null)";
				}
				condition.add(map.get("sendStatus"));
			} else {
				if (flag == 0) {
					whereString += " where a.sendStatus = ? ";
					flag = 1;
				} else {
					whereString += " and a.sendStatus = ? ";
				}
				condition.add(map.get("sendStatus"));
			}
		}

		/**
		 * pUserName
		 * */
		if (CollectionsUtil.exist(map, "pUserName") && !"".equals(map.get("pUserName"))) {
			if (flag == 0) {
				whereString += " where a.name like ? ";
				flag = 1;
			} else {
				whereString += " and a.name like ? ";
			}
			condition.add("%" + map.get("pUserName") + "%");
		}
		/**
		 * status
		 * */
		if (CollectionsUtil.exist(map, "status") && !"".equals(map.get("status"))) {
			if (flag == 0) {
				whereString += " where a.status = ? ";
				flag = 1;
			} else {
				whereString += " and a.status = ? ";
			}
			condition.add(map.get("status"));
		}
		if (CollectionsUtil.exist(map, "typeCode") && !"".equals(map.get("typeCode"))) {
			if (flag == 0) {
				whereString += " where b.code = ? ";
				flag = 1;
			} else {
				whereString += " and b.code = ? ";
			}
			condition.add(map.get("typeCode").toString());
		}
		table.put("where", whereString);
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
	public List<CUser> getList(Map<String, Object> condition, String sort) throws Exception {
		List<CUser> list = null;
		String hql = " from CUser a left join a.userType b left join a.institution c";
		Map<String, Object> t = this.getWhere(condition);
		String property = " email,id,name,userType.id,userType.name,userType.code,institution.id,institution.name,status,createdby,createdon,updatedby,updatedon,institution.logo,institution.logoUrl,institution.logoNote,level,sendStatus, institution.status";
		String field = "a.email,a.id,a.name,b.id,b.name,b.code,c.id,c.name,a.status,a.createdby,a.createdon,a.updatedby,a.updatedon,c.logo,c.logoUrl,c.logoNote,a.level,a.sendStatus, c.status";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, CUser.class.getName());
		} catch (Exception e) {
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
	public List<CUser> getPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<CUser> list = null;
		String hql = " from CUser a left join a.userType b left join a.institution c";
		Map<String, Object> t = this.getWhere(condition);
		String property = "id,name,userType.id,userType.name,userType.code,institution.id,institution.name,status,createdby,createdon,updatedby,updatedon,institution.logo,institution.logoUrl,institution.logoNote,level";
		String field = "a.id,a.name,b.id,b.name,b.code,c.id,c.name,a.status,a.createdby,a.createdon,a.updatedby,a.updatedon,c.logo,c.logoUrl,c.logoNote,a.level";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, CUser.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
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
	public Integer getCount(Map<String, Object> condition) throws Exception {
		List<CUser> list = null;
		Map<String, Object> t = this.getWhere(condition);
		String hql = " from CUser a left join a.userType b left join a.institution c";
		try {
			list = this.hibernateDao.getListByHql("id", "cast(count(*) as string)", hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), "", CUser.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list == null ? 0 : Integer.valueOf(list.get(0).getId());
	}

}
