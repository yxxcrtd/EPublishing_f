package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.CUserProp;

public class CUserPropDao extends CommonDao<CUserProp, String> {

	private Map<String, Object> getWhere(Map<String, Object> map) {
		Map<String, Object> table = new HashMap<String, Object>();
		String whereString = "";
		List<Object> condition = new ArrayList<Object>();
		int flag = 0;
		if (CollectionsUtil.exist(map, "code") && !"".equals(map.get("code"))) {
			if (flag == 0) {
				whereString += " where a.code = ? ";
				flag = 1;
			} else {
				whereString += " and a.code = ? ";
			}
			condition.add(map.get("code"));
		}
		//用于校验邮箱唯一性
		if (CollectionsUtil.exist(map, "email") && !"".equals(map.get("email"))) {
			if (flag == 0) {
				whereString += " where a.val = ?";
				flag = 1;
			} else {
				whereString += " and a.val = ?";
			}
			condition.add(map.get("email"));
		}
		if (CollectionsUtil.exist(map, "userId") && !"".equals(map.get("userId"))) {
			if (flag == 0) {
				whereString += " where b.id = ? ";
				flag = 1;
			} else {
				whereString += " and b.id = ? ";
			}
			condition.add(map.get("userId"));
		}
		if (CollectionsUtil.exist(map, "val") && !"".equals(map.get("val"))) {
			if (flag == 0) {
				whereString += " where a.val = ?";
				flag = 1;
			} else {
				whereString += " and a.val = ?";
			}
			condition.add(map.get("val"));
		}

		if (CollectionsUtil.exist(map, "tid") && !"".equals(map.get("val"))) {
			if (flag == 0) {
				whereString += " where b.id = ?";
				flag = 1;
			} else {
				whereString += " and b.id = ?";
			}
			condition.add(map.get("tid"));
		}
		if (CollectionsUtil.exist(map, "sendStatus") && !"".equals(map.get("sendStatus"))) {
			if (flag == 0) {
				whereString += " where a.sendStatus = ?";
				flag = 1;
			} else {
				whereString += " and a.sendStatus = ?";

			}
			condition.add(map.get("sendStatus"));
		}
		table.put("where", whereString);
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
	public Integer getCount(Map<String, Object> condition) throws Exception {
		List<CUserProp> list = null;
		Map<String, Object> t = this.getWhere(condition);
		//String hql = " from CUserProp a left join a.user b";
		String hql = " from CUserProp a";
		try {
			list = this.hibernateDao.getListByHql("id", "a.id", hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), "", CUserProp.class.getName());
		} catch (Exception e) {
			throw e;
		}
		//return list == null ? 0 : Integer.valueOf(list.get(0).getId());
		return (null != list && 0 < list.size()) ? list.size() : 0;
	}

	/**
	 * 获取用户属性信息列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<CUserProp> getList(Map<String, Object> condition, String sort) throws Exception {
		List<CUserProp> list = null;
		String hql = " from CUserProp a left join a.user b left join a.userTypeProp c ";
		Map<String, Object> t = this.getWhere(condition);
		String property = "id,code,key,val,display,svalue,must,user.id,user.name,userTypeProp.id";
		String field = "a.id,a.code,a.key,a.val,a.display,a.svalue,a.must,b.id,b.name,c.id";
		try {
			list = this.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, CUserProp.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<CUserProp> getCUserPropList(Map<String, Object> condition) throws Exception {
		List<CUserProp> list = null;
		String hql = " from CUserProp a left join a.userTypeProp b ";
		Map<String, Object> t = this.getWhere(condition);
		String property = "id,code,key,val,display,svalue,must";
		String field = "a.id,a.code,a.key,a.val,a.display,a.svalue,a.must";
		try {
			list = this.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), null, CUserProp.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

}
