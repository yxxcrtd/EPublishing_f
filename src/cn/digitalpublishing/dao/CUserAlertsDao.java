package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.CUserAlerts;

public class CUserAlertsDao extends CommonDao<CUserAlerts, String> {
	@SuppressWarnings("unchecked")
	private Map<String, Object> getWhere(Map<String, Object> map) {
		Map<String, Object> table = new HashMap<String, Object>();
		String whereString = "";
		List<Object> condition = new ArrayList<Object>();
		int flag = 0;
		/**
		 * 订阅类型 1-出版物;2-主题学科
		 */
		if (CollectionsUtil.exist(map, "type") && !"".equals(map.get("type"))) {
			if (flag == 0) {
				whereString += " where a.type = ? ";
				flag = 1;
			} else {
				whereString += " and a.type = ? ";
			}
			condition.add(map.get("type"));
		}
		/**
		 * 提醒频率 1-立即;2-每天;3-每周;4-每月
		 */
		if (CollectionsUtil.exist(map, "frequency") && !"".equals(map.get("frequency"))) {
			if (flag == 0) {
				whereString += " where a.frequency = ?";
				flag = 1;
			} else {
				whereString += " and a.frequency = ?";
			}
			condition.add(map.get("frequency"));
		}
		/**
		 * 用户Id
		 */
		if (CollectionsUtil.exist(map, "userId") && !"".equals(map.get("userId"))) {
			if (flag == 0) {
				whereString += " where c.id = ?";
				flag = 1;
			} else {
				whereString += " and c.id = ?";
			}
			condition.add(map.get("userId"));
		}
		/**
		 * 分类法Id
		 */
		if (CollectionsUtil.exist(map, "subjectId") && !"".equals(map.get("subjectId"))) {
			if (flag == 0) {
				whereString += " where b.id = ?";
				flag = 1;
			} else {
				whereString += " and b.id = ?";
			}
			condition.add(map.get("subjectId"));
		}

		/**
		 * 学科主题对应的treeCode
		 * */
		if (CollectionsUtil.exist(map, "treeCodes") && !"".equals(map.get("treeCodes"))) {
			if (map.get("treeCodes") instanceof List) {
				List<String> list = (List<String>) map.get("treeCodes");
				String where_ = "";
				String _where = ")";
				String where = "";
				if (flag == 0) {
					where_ = " where a.treeCode in (";
					flag = 1;
				} else {
					where_ = " and a.treeCode in (";
				}
				for (int i = 0; i < list.size(); i++) {
					where += "?";
					if (i < list.size() - 1) {
						where += ",";
					}
					condition.add(list.get(i));
				}
				whereString += where_ + where + _where;
			}
		}

		if (CollectionsUtil.exist(map, "id") && !"".equals(map.get("id"))) {
			if (flag == 0) {
				whereString += " where a.id = ?";
				flag = 1;
			} else {
				whereString += " and a.id = ?";
			}
			condition.add(map.get("id"));
		}
		table.put("where", whereString);
		table.put("condition", condition);
		return table;
	}

	/**
	 * 获取用户属性信息列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<CUserAlerts> getList(Map<String, Object> condition, String sort) throws Exception {
		List<CUserAlerts> list = null;
		String hql = " from CUserAlerts a left join a.subject b left join a.user c ";
		Map<String, Object> t = this.getWhere(condition);
		String property = "id,frequency,type,treeCode,subject.id,subject.code,subject.name,subject.nameEn,user.id,user.name";
		String field = "a.id,a.frequency,a.type,a.treeCode,b.id,b.code,b.name,b.nameEn,c.id,c.name";
		try {
			list = this.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, CUserAlerts.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public void deleteAlertsByTreeCode(String treeCode, String userId) throws Exception {
		String sql = "delete from CUserAlerts a  where a.subject.id in (select id from BSubject b  where b.treeCode like ?) and user.id=?";
		try {
			this.hibernateDao.executeHql(sql, new Object[] { treeCode + "%", userId });
		} catch (Exception e) {
			throw e;
		}
	}
}
