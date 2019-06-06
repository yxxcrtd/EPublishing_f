package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.PCollection;

public class PCollectionDao extends CommonDao<PCollection, String> {

	private Map<String, Object> getWhere(Map<String, Object> map) {
		Map<String, Object> table = new HashMap<String, Object>();
		String whereString = "";
		List<Object> condition = new ArrayList<Object>();
		int flag = 0;
		if (flag == 0) {
			whereString += " where a.status = 2";
			flag = 1;
		} else {
			whereString += " and a.status = 2";
		}
		/**
		 * 1.homepage
		 */
		if (CollectionsUtil.exist(map, "homepage") && !"".equals(map.get("homepage"))) {
			if (flag == 0) {
				whereString += " where homepage = ?";
				flag = 1;
			} else {
				whereString += " and homepage = ?";
			}
			condition.add(map.get("homepage"));
		}
		/**
		 * 1.ppv
		 */
		if (CollectionsUtil.exist(map, "ppvCollId") && !"".equals(map.get("ppvCollId"))) {
			if (flag == 0) {
				whereString += " where a.id != ?";
				flag = 1;
			} else {
				whereString += " and a.id != ?";
			}
			condition.add(map.get("ppvCollId"));
		}
		/**
		 * 价格>0
		 */
		if (CollectionsUtil.exist(map, "unNullPrice") && !"".equals(map.get("unNullPrice"))) {
			if (flag == 0) {
				whereString += " where a.price>0 ";
				flag = 1;
			} else {
				whereString += " and a.price>0 ";
			}
		}
		if (CollectionsUtil.exist(map, "collectionId") && !"".equals(map.get("collectionId"))) {
			if (flag == 0) {
				whereString += " where id = ?";
				flag = 1;
			} else {
				whereString += " and id = ?";
			}
			condition.add(map.get("collectionId"));
		}
		if (CollectionsUtil.exist(map, "collectionName") && !"".equals(map.get("collectionName"))) {
			if (flag == 0) {
				whereString += " where a.name = ?";
				flag = 1;
			} else {
				whereString += " and a.name = ?";
			}
			condition.add(map.get("collectionName"));
		}

		table.put("where", whereString);
		table.put("condition", condition);
		return table;
	}

	/**
	 * 获取等待列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PCollection> getList(Map<String, Object> condition, String sort) throws Exception {
		List<PCollection> list = null;
		String hql = " from PCollection a ";
		Map<String, Object> t = this.getWhere(condition);
		String property = "id,code,name,desc,price,currency,homepage,createOn,cover,bookNumber";
		String field = "a.id,a.code,a.name,a.desc,a.price,a.currency,a.homepage,a.createOn,a.cover,(select cast(count(*) as string) from PCcRelation pcc where pcc.collection.id=a.id)";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, PCollection.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	/**
	 * 获取分页信息
	 * 
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PCollection> getPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<PCollection> list = null;
		String hql = " from PCollection a ";
		Map<String, Object> t = this.getWhere(condition);
		String property = "id,code,name,desc,price,currency,homepage,createOn,cover,bookNumber,chineseNumber,forginNumber,journalNumber,type7Number";
		String field = "a.id,a.code,a.name,a.desc,a.price,a.currency,a.homepage,a.createOn,a.cover,(select cast(count(*) as string) from PCcRelation pcc where pcc.collection.id=a.id)," + "(select cast(count(*) as string) from PCcRelation pcc where pcc.collection.id=a.id and pcc.publications.lang='chs' and pcc.publications.type=1 and pcc.publications.status=2),"// 中文电子书
				+ "(select cast(count(*) as string) from PCcRelation pcc where pcc.collection.id=a.id and pcc.publications.lang!='chs' and pcc.publications.type=1 and pcc.publications.status=2),"// 英文电子书
				+ "(select cast(count(*) as string) from PCcRelation pcc where pcc.collection.id=a.id and pcc.publications.type = 2 and pcc.publications.status=2)," + "(select cast(count(*) as string) from PCcRelation pcc where pcc.collection.id=a.id and pcc.publications.type = 7 and pcc.publications.status=2)";// 期刊
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, PCollection.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	/**
	 * 获取总数
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer getCount(Map<String, Object> condition) throws Exception {
		List<PCollection> list = null;
		Map<String, Object> t = this.getWhere(condition);
		String hql = " from PCollection a ";
		try {
			list = this.hibernateDao.getListByHql("id", "cast(count(*) as string)", hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), "", PCollection.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list == null ? 0 : Integer.valueOf(list.get(0).getId());
	}
}
