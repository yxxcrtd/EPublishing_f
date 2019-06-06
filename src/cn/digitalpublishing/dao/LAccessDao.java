package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.util.web.DateUtil;

public class LAccessDao extends CommonDao<LAccess, String> {

	/**
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unused")
	private Map<String, Object> getWhere(Map<String, Object> map) {
		Map<String, Object> table = new HashMap<String, Object>();
		String whereString = "";
		List<Object> condition = new ArrayList<Object>();
		int flag = 0;

		// 生成统计所需的月份参数
		if (CollectionsUtil.exist(map, "minMonth") && CollectionsUtil.exist(map, "maxMonth") && CollectionsUtil.exist(map, "year")) {
			condition.add(map.get("year"));
			if (map.get("type") != null) {
				condition.add(map.get("type"));
			}
			if (map.get("types") != null) {
				Integer[] types = (Integer[]) map.get("types");
				for (int i = 0; i < types.length; i++) {
					condition.add(types[i]);
				}
			}
			condition.add(map.get("access"));
			if (CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId"))) {
				condition.add(map.get("institutionId"));
			}

			Integer minMonth = Integer.valueOf(map.get("minMonth").toString());
			Integer maxMonth = Integer.valueOf(map.get("maxMonth").toString());
			for (Integer i = minMonth; i <= maxMonth; i++) {
				condition.add(i < 10 ? "0" + i.toString() : i.toString());
				condition.add(map.get("year"));
				if (map.get("type") != null) {
					condition.add(map.get("type"));
				}
				if (map.get("types") != null) {
					Integer[] types = (Integer[]) map.get("types");
					for (int m = 0; m < types.length; m++) {
						condition.add(types[m]);
					}
				}
				condition.add(map.get("access"));
				if (CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId"))) {
					condition.add(map.get("institutionId"));
				}

				boolean isPrecise = CollectionsUtil.exist(map, "isPrecise") ? Boolean.valueOf(map.get("isPrecise").toString()) : false;
				if (isPrecise) {
					condition.add(map.get("lang"));
				}
			}
		}

		/**
		 * 1.机构ID
		 */
		if (CollectionsUtil.exist(map, "isInstitutionId") && !"".equals(map.get("isInstitutionId"))) {
			if (flag == 0) {
				whereString += " where a.institutionId = ?";
				flag = 1;
			} else {
				whereString += " and a.institutionId = ?";
			}
			condition.add(map.get("isInstitutionId"));
		}
		/**
		 * 1.类型
		 */
		if (CollectionsUtil.exist(map, "type") && !"".equals(map.get("type"))) {
			if (flag == 0) {
				whereString += " where a.type = ?";
				flag = 1;
			} else {
				whereString += " and a.type = ?";
			}
			condition.add(map.get("type"));
		}
		if (CollectionsUtil.exist(map, "author") && !"".equals(map.get("author")) && map.get("author") != null) {

			if (flag == 0) {
				whereString += " where a.publications.author != ? ";
				flag = 1;
			} else {
				whereString += " and a.publications.author != ? ";
			}
			condition.add(map.get("author"));

		}

		if (CollectionsUtil.exist(map, "language") && !"".equals(map.get("language")) && map.get("language") != null) {

			if (flag == 0) {
				whereString += " where a.publications.lang like ? ";
				flag = 1;
			} else {
				whereString += " and a.publications.lang like ? ";
			}
			condition.add(map.get("language"));

		}

		if (CollectionsUtil.exist(map, "languageEn") && !"".equals(map.get("languageEn")) && map.get("languageEn") != null) {

			if (flag == 0) {
				whereString += " where a.publications.lang not like ? ";
				flag = 1;
			} else {
				whereString += " and a.publications.lang not like ? ";
			}
			condition.add(map.get("languageEn"));

		}

		if (CollectionsUtil.exist(map, "publisher") && !"".equals(map.get("publisher")) && map.get("publisher") != null) {

			if (flag == 0) {
				whereString += " where a.publications.publisher.name != ? ";
				flag = 1;
			} else {
				whereString += " and a.publications.publisher.name != ? ";
			}
			condition.add(map.get("publisher"));

		}
		if (CollectionsUtil.exist(map, "available") && !"".equals(map.get("available"))) {// 这是对一个available的条件
			if (flag == 0) {
				whereString += " where (a.publications.available <> ? or a.publications.available is null )  ";
				flag = 1;
			} else {
				whereString += " and ( a.publications.available  <> ? or a.publications.available is null ) ";
			}
			condition.add(map.get("available"));
		}
		/**
		 * 海外版资源f=1
		 */
		if (CollectionsUtil.exist(map, "f") && !"".equals(map.get("f")) && map.get("f") != null) {
			if (flag == 0) {
				whereString += " where a.publications.f = ?";
				flag = 1;
			} else {
				whereString += " and a.publications.f  = ?";
			}
			condition.add(Integer.parseInt(map.get("f").toString()));
		}
		/**
		 * 书籍lang1-中文 2-外文
		 */
		if (CollectionsUtil.exist(map, "lang") && map.get("lang") != null) {
			boolean isPrecisebook = CollectionsUtil.exist(map, "isPrecisebook") ? Boolean.valueOf(map.get("isPrecisebook").toString()) : false;
			String in = "";
			if (isPrecisebook) {
				if (flag == 0) {
					whereString += " where a.publications.lang in (";
					flag = 1;
				} else {
					whereString += " and a.publications.lang in (";
				}
			} else {
				if (flag == 0) {
					whereString += " where a.publications.lang not in (";
					flag = 1;
				} else {
					whereString += " and a.publications.lang not in (";
				}
			}

			String[] langs = (String[]) map.get("lang");
			for (int i = 0; i < langs.length; i++) {
				if (i < langs.length - 1) {
					whereString += "?,";
				} else {
					whereString += "?";
				}
				condition.add(langs[i]);
			}
			whereString += ")";
		}
		/**
		 * 书籍类型1-电子书 2-期刊 类型数组
		 */
		if (CollectionsUtil.exist(map, "pubtypes") && map.get("pubtypes") != null) {
			if (flag == 0) {
				whereString += " where a.publications.type in (";
				flag = 1;
			} else {
				whereString += " and a.publications.type in (";
			}
			Integer[] pubtypes = (Integer[]) map.get("pubtypes");
			for (int i = 0; i < pubtypes.length; i++) {
				if (i < pubtypes.length - 1) {
					whereString += "?,";
				} else {
					whereString += "?";
				}
				condition.add(pubtypes[i]);
			}
			whereString += ")";
		}

		/**
		 * 书籍类型1-电子书 2-期刊
		 */
		if (CollectionsUtil.exist(map, "pubtype") && !"".equals(map.get("pubtype"))) {
			if (flag == 0) {
				whereString += " where a.publications.type = ?";
				flag = 1;
			} else {
				whereString += " and a.publications.type = ?";
			}
			condition.add(map.get("pubtype"));
		}

		/**
		 * 2.访问成功
		 */
		if (CollectionsUtil.exist(map, "access") && !"".equals(map.get("access"))) {
			if (flag == 0) {
				whereString += " where a.access = ?";
				flag = 1;
			} else {
				whereString += " and a.access = ?";
			}
			condition.add(map.get("access"));
		}
		/**
		 * 3.年份
		 */
		if (CollectionsUtil.exist(map, "year") && !"".equals(map.get("year"))) {
			if (flag == 0) {
				whereString += " where a.year = ?";
				flag = 1;
			} else {
				whereString += " and a.year = ?";
			}
			condition.add(map.get("year"));
		}
		/**
		 * 4.机构
		 */
		if (CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId"))) {
			if (map.get("institutionId") == null) {
				if (flag == 0) {
					whereString += " where a.institutionId is null ";
					flag = 1;
				} else {
					whereString += " and a.institutionId is null ";
				}
			} else {
				if (flag == 0) {
					whereString += " where a.institutionId = ?";
					flag = 1;
				} else {
					whereString += " and a.institutionId = ?";
				}
				condition.add(map.get("institutionId"));
			}
		}
		if (CollectionsUtil.exist(map, "institutionId2") && !"".equals(map.get("institutionId2"))) {
			if (map.get("institutionId2") == null) {
				if (flag == 0) {
					whereString += " where a.institutionId is null ";
					flag = 1;
				} else {
					whereString += " and a.institutionId is null ";
				}
			} else {
				if (flag == 0) {
					whereString += " where a.institutionId = ?";
					flag = 1;
				} else {
					whereString += " and a.institutionId = ?";
				}
				condition.add(map.get("institutionId2"));
			}
		}

		/**
		 * 5.提供商ID
		 */
		if (CollectionsUtil.exist(map, "sourceId") && !"".equals(map.get("sourceId"))) {
			if (flag == 0) {
				whereString += " where a.publications.sourceId = ?";
				flag = 1;
			} else {
				whereString += " and a.publications.sourceId = ?";
			}
			condition.add(map.get("sourceId"));
		}

		if (CollectionsUtil.exist(map, "createOn") && !"".equals(map.get("createOn"))) {
			if (flag == 0) {
				whereString += " where a.createOn > ?";
				flag = 1;
			} else {
				whereString += " and a.createOn > ?";
			}
			condition.add(DateUtil.getDealedDate(new Date(), 0, 0, (Integer) map.get("createOn"), 0, 0, 0));
		}

		// /**
		// * 6.startMonth
		// */
		// if(CollectionsUtil.exist(map,
		// "startMonth")&&!"".equals(map.get("startMonth"))){
		// if(flag==0){
		// whereString+=" where a.month >= ?";
		// flag=1;
		// }else{
		// whereString+=" and a.month >= ?";
		// }
		// condition.add(map.get("startMonth"));
		// }
		// /**
		// * 7.endMonth
		// */
		// if(CollectionsUtil.exist(map,
		// "endMonth")&&!"".equals(map.get("endMonth"))){
		// if(flag==0){
		// whereString+=" where a.month <= ?";
		// flag=1;
		// }else{
		// whereString+=" and a.month <= ?";
		// }
		// condition.add(map.get("endMonth"));
		// }
		if (CollectionsUtil.exist(map, "startMonth") && !"".equals(map.get("startMonth")) && map.get("startMonth") != null && CollectionsUtil.exist(map, "endMonth") && !"".equals(map.get("endMonth")) && map.get("endMonth") != null) {
			Integer minMonth = Integer.valueOf(map.get("startMonth").toString());
			Integer maxMonth = Integer.valueOf(map.get("endMonth").toString());
			if (flag == 0) {
				whereString += " where (";
				flag = 1;
			} else {
				whereString += " and (";
			}
			for (Integer i = minMonth; i <= maxMonth; i++) {
				if (i < maxMonth) {
					whereString += " a.month" + i + ">0 or ";
				} else {
					whereString += " a.month" + i + " >0 ";
				}
			}
			whereString += " )";
		}
		/**
		 * 8.类型数组
		 */
		if (CollectionsUtil.exist(map, "types") && map.get("types") != null) {
			if (flag == 0) {
				whereString += " where a.type in (";
				flag = 1;
			} else {
				whereString += " and a.type in (";
			}
			Integer[] types = (Integer[]) map.get("types");
			for (int i = 0; i < types.length; i++) {
				if (i < types.length - 1) {
					whereString += "?,";
				} else {
					whereString += "?";
				}
				condition.add(types[i]);
			}
			whereString += ")";
		}
		/**
		 * 9.图书ID
		 */
		if (CollectionsUtil.exist(map, "pubId") && !"".equals(map.get("pubId"))) {
			if (flag == 0) {
				whereString += " where a.publications.id = ?";
				flag = 1;
			} else {
				whereString += " and a.publications.id = ?";
			}
			condition.add(map.get("pubId"));
		}
		if (CollectionsUtil.exist(map, "month") && !"".equals(map.get("month"))) {
			if (flag == 0) {
				whereString += " where a.month = ?";
				flag = 1;
			} else {
				whereString += " and a.month = ?";
			}
			condition.add(map.get("month"));
		}
		if (CollectionsUtil.exist(map, "pubType") && !"".equals(map.get("pubType"))) {
			if (flag == 0) {
				whereString += " where a.publications.type = ?";
				flag = 1;
			} else {
				whereString += " and a.publications.type = ?";
			}
			condition.add(map.get("pubType"));
		}
		if (CollectionsUtil.exist(map, "pubParentId") && !"".equals(map.get("pubParentId"))) {
			if (flag == 0) {
				whereString += " where a.publications.publications.id = ?";
				flag = 1;
			} else {
				whereString += " and a.publications.publications.id = ?";
			}
			condition.add(map.get("pubParentId"));
		}
		if (CollectionsUtil.exist(map, "pubStatus") && !"".equals(map.get("pubStatus"))) {
			if (flag == 0) {
				whereString += " where a.publications.status = ?";
				flag = 1;
			} else {
				whereString += " and a.publications.status = ?";
			}
			condition.add(map.get("pubStatus"));
		}
		/**
		 * 用户Id userId
		 */
		if (CollectionsUtil.exist(map, "userId") && !"".equals(map.get("userId"))) {
			if (map.get("userId") == null) {
				if (flag == 0) {
					whereString += " where a.userId is null ";
					flag = 1;
				} else {
					whereString += " and a.userId is null ";
				}
			} else {
				if (flag == 0) {
					whereString += " where a.userId = ?";
					flag = 1;
				} else {
					whereString += " and a.userId = ?";
				}
				condition.add(map.get("userId"));
			}
		}
		/**
		 * 关键字
		 */
		if (CollectionsUtil.exist(map, "keyword") && !"".equals(map.get("keyword"))) {
			if (flag == 0) {
				whereString += " where a.activity = ?";
				flag = 1;
			} else {
				whereString += " and a.activity = ?";
			}
			condition.add(map.get("keyword"));
		}
		if (CollectionsUtil.exist(map, "keywordNotNull")) {
			if (flag == 0) {
				whereString += " where (a.activity is not null or a.activity = ?)";
				flag = 1;
			} else {
				whereString += " and (a.activity is not null or a.activity = ?)";
			}
			condition.add("");
		}
		if (CollectionsUtil.exist(map, "publicationStatus") && !"".equals(map.get("publicationStatus"))) {
			if (flag == 0) {
				whereString += " where b.status = ?";
				flag = 1;
			} else {
				whereString += " and b.status = ?";
			}
			condition.add(map.get("publicationStatus"));
		}
		if (CollectionsUtil.exist(map, "maxDate") && !"".equals(map.get("maxDate"))) {
			if (flag == 0) {
				whereString += " where a.createOn = (select max(b.createOn) from LAccess b where b.access=a.access and b.publications.id=a.publications.id and b.type=a.type) ";
				flag = 1;
			} else {
				whereString += " and a.createOn = (select max(b.createOn) from LAccess b where b.access=a.access and b.publications.id=a.publications.id and b.type=a.type) ";
			}
		}
		if (CollectionsUtil.exist(map, "license") && !"".equals(map.get("license")) && (CollectionsUtil.exist(map, "userId") && !"".equals(map.get("userId"))) || (CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId")))) {
			if (CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId"))) {
				if (flag == 0) {
					whereString += " where exists(select lip.id from LLicenseIp lip where (lip.license.publications.id=a.publications.id or lip.license.publications.code=a.publications.code) and lip.license.status=1 and lip.license.user.level=2 and lip.license.user.institution.id=?)";
					flag = 1;
				} else {
					whereString += " and exists(select lip.id from LLicenseIp lip where (lip.license.publications.id=a.publications.id or lip.license.publications.code=a.publications.code) and lip.license.status=1 and lip.license.user.level=2 and lip.license.user.institution.id=?)";
				}
				condition.add(map.get("institutionId"));
			} else if (CollectionsUtil.exist(map, "userId") && !"".equals(map.get("userId"))) {
				if (flag == 0) {
					whereString += " where exists(select li.id from LLicense li where (li.publications.id=a.publications.id or li.publications.code=a.publications.code) and li.status=1 and li.user.id=?)";
					flag = 1;
				} else {
					whereString += " and exists(select li.id from LLicense li where (li.publications.id=a.publications.id or li.publications.code=a.publications.code) and li.status=1 and li.user.id=?)";
				}
				condition.add(map.get("userId"));
			}
		}
		if (CollectionsUtil.exist(map, "oaStatus") && !"".equals(map.get("oaStatus"))) {
			if (flag == 0) {
				whereString += " where a.publications.publications.oa = ?";
				flag = 1;
			} else {
				whereString += " and a.publications.publications.oa = ?";
			}
			condition.add(map.get("oaStatus"));
		}
		if (CollectionsUtil.exist(map, "searchTypeNotNull") && !"".equals(map.get("searchTypeNotNull"))) {
			if (flag == 0) {
				whereString += " where a.searchType is not null";
				flag = 1;
			} else {
				whereString += " and a.searchType is not null";
			}
		}

		if (CollectionsUtil.exist(map, "isCn") && !"".equals(map.get("isCn")) && map.get("isCn") != null) {
			boolean lang = (Boolean) map.get("isCn");
			if (flag == 0) {
				whereString += " where " + (lang ? "" : " NOT ") + " b.lang = ? ";
				flag = 1;
			} else {
				whereString += " and " + (lang ? "" : " NOT ") + " b.lang = ? ";
			}
			condition.add("chs");
		}
		if (CollectionsUtil.exist(map, "ava") && !"".equals(map.get("ava")) && map.get("ava") != null) {
			if (flag == 0) {
				whereString += " where b.available = ? ";
				flag = 1;
			} else {
				whereString += " and b.available = ? ";
			}
			condition.add(map.get("ava"));
		}
		if (CollectionsUtil.exist(map, "pStatus") && !"".equals(map.get("pStatus")) && map.get("pStatus") != null) {
			if (flag == 0) {
				whereString += " where b.status = ? ";
				flag = 1;
			} else {
				whereString += " and b.status = ? ";
			}
			condition.add(map.get("pStatus"));
		}
		if (CollectionsUtil.exist(map, "pType") && !"".equals(map.get("pType")) && map.get("pType") != null) {
			if (flag == 0) {
				whereString += " where b.type = ? ";
				flag = 1;
			} else {
				whereString += " and b.type = ? ";
			}
			condition.add(map.get("pType"));
		}
		if (CollectionsUtil.exist(map, "noLicense") && !"".equals(map.get("noLicense")) && map.get("noLicense") != null) {
			String insId = null;
			String userId = null;
			if (CollectionsUtil.exist(map, "pInsId") && !"".equals(map.get("pInsId")) && map.get("pInsId") != null) {
				insId = map.get("pInsId").toString();
			}
			if (CollectionsUtil.exist(map, "pUserId") && !"".equals(map.get("pUserId")) && map.get("pUserId") != null) {
				insId = map.get("pUserId").toString();
			}
			String sql = " not exists(select ll.id from LLicense ll inner join ll.user cu where ll.publications.id=b.id and ll.status=? ";
			condition.add(1);
			if (insId != null || userId != null) {
				sql += " and (";
				boolean subFlag = false;
				if (insId != null) {
					sql += " cu.institution.id=?";
					condition.add(insId);
					subFlag = true;
				}
				if (userId != null) {
					sql += (subFlag ? " or " : "") + " cu.institution.id=?";
					condition.add(userId);
				}
				sql += ")";
			}
			sql += ")";
			if (flag == 0) {
				whereString += " where " + sql;
				flag = 1;
			} else {
				whereString += " and " + sql;
			}
		}

		if (CollectionsUtil.exist(map, "noOrder") && !"".equals(map.get("noOrder")) && map.get("noOrder") != null) {
			String insId = null;
			String userId = null;
			if (CollectionsUtil.exist(map, "pInsId") && !"".equals(map.get("pInsId")) && map.get("pInsId") != null) {
				insId = map.get("pInsId").toString();
			}
			if (CollectionsUtil.exist(map, "pUserId") && !"".equals(map.get("pUserId")) && map.get("pUserId") != null) {
				insId = map.get("pUserId").toString();
			}
			String sql = " not exists(select od.id from OOrderDetail od inner join od.price ppr inner join od.user oou " + " where ppr.publications.id=b.id and od.status=? ";
			condition.add(99);
			if (insId != null || userId != null) {
				sql += " and (";
				boolean subFlag = false;
				if (insId != null) {
					sql += " oou.institution.id=?";
					condition.add(insId);
					subFlag = true;
				}
				if (userId != null) {
					sql += (subFlag ? " or " : "") + " oou.institution.id=?";
					condition.add(userId);
				}
				sql += ")";
			}
			sql += ")";
			if (flag == 0) {
				whereString += " where " + sql;
				flag = 1;
			} else {
				whereString += " and " + sql;
			}
		}
		table.put("where", whereString);
		table.put("condition", condition);
		return table;
	}

	/**
	 * 获取统计列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getCounterList(Map<String, Object> condition, String sort) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		condition.put("minMonth", minMonth);
		condition.put("maxMonth", maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,publications.title,publications.code,publications.publisher.name,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;// ,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12
										// ";
		}
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id )" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )";
		field += ",(select cast(count(la.id) as string) from LAccess la where la.year=? and la.type=? and la.access=? and la.publications.id=a.publications.id ";
		if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
			field += " and la.institutionId=? ";
		}
		field += ")";
		field += ",";
		for (int i = minMonth; i <= maxMonth; i++) {
			if (i < maxMonth) {
				field += "(select cast (count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? and la.type=? and la.access=? and pb.id=a.publications.id ";
				if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
					field += " and la.institutionId=? ";
				}
				field += " ),";
			} else {
				field += "(select cast(count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? and la.type=? and la.access=? and pb.id=a.publications.id ";
				if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
					field += " and la.institutionId=? ";
				}
				field += " )";
			}
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by a.publications.id " + sort, LAccess.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 获取统计列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getCounterPagingList(Map<String, Object> condition, String sort, int pageCount, int curpage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		condition.put("minMonth", minMonth);
		condition.put("maxMonth", maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,publications.author,publications.pubDate,publications.cover,publications.lang,publications.title,publications.code,publications.publisher.name,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;// ,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12
										// ";
		}
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id)" + ",(select p.author from PPublications p where p.id=a.publications.id )" + ",(select p.pubDate from PPublications p where p.id=a.publications.id )" + ",(select p.cover from PPublications p where p.id=a.publications.id )" + ",(select p.lang from PPublications p where p.id=a.publications.id )" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )";
		field += ",(select cast(count(la.id) as string) from LAccess la where la.year=? and la.type=? and la.access=? and la.publications.id=a.publications.id ";
		if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
			field += " and la.institutionId=? ";
		}
		field += ")";
		field += ",";
		for (int i = minMonth; i <= maxMonth; i++) {
			if (i < maxMonth) {
				field += "(select cast (count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? and la.type=? and la.access=? and pb.id=a.publications.id ";
				if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
					field += " and la.institutionId=? ";
				}
				field += " ),";
			} else {
				field += "(select cast(count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? and la.type=? and la.access=? and pb.id=a.publications.id ";
				if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
					field += " and la.institutionId=? ";
				}
				field += " )";
			}
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by a.publications.id " + sort, LAccess.class.getName(), pageCount, curpage * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public List<LAccess> getCounterListToSearch(Map<String, Object> condition, String sort) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		condition.put("minMonth", minMonth);
		condition.put("maxMonth", maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,activity,publications.title,publications.code,publications.publisher.name,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;// ,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12
										// ";
		}
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id )" + ",a.activity" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )" + ",(select cast(count(la.id) as string) from LAccess la where la.year=? and la.type=? and la.access=? and la.publications.id=a.publications.id and la.activity=a.activity ";
		if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
			field += " and la.institutionId=? ";
		}
		field += "),";
		for (int i = minMonth; i <= maxMonth; i++) {
			if (i < maxMonth) {
				field += "(select cast (count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? and la.type=? and la.access=? and pb.id=a.publications.id and la.activity=a.activity";
				if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
					field += " and la.institutionId=? ";
				}
				field += " ),";
			} else {
				field += "(select cast(count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? and la.type=? and la.access=? and pb.id=a.publications.id  and la.activity=a.activity";
				if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
					field += " and la.institutionId=? ";
				}
				field += " )";
			}
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by a.publications.id,a.activity " + sort, LAccess.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public List<LAccess> getCounterPagingListToSearch(Map<String, Object> condition, String sort, int pageCount, int curpage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		condition.put("minMonth", minMonth);
		condition.put("maxMonth", maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,publications.author,publications.pubDate,publications.cover,publications.lang,activity,publications.title,publications.code,publications.publisher.name,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;// ,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12
										// ";
		}
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id)" + ",(select p.author from PPublications p where p.id=a.publications.id )" + ",(select p.pubDate from PPublications p where p.id=a.publications.id )" + ",(select p.cover from PPublications p where p.id=a.publications.id )" + ",(select p.lang from PPublications p where p.id=a.publications.id )" + ",a.activity" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )" + ",(select cast(count(la.id) as string) from LAccess la where la.year=? and la.type=? and la.access=? and la.publications.id=a.publications.id and la.activity=a.activity ";
		if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
			field += " and la.institutionId=? ";
		}
		field += "),";
		for (int i = minMonth; i <= maxMonth; i++) {
			if (i < maxMonth) {
				field += "(select cast (count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? and la.type=? and la.access=? and pb.id=a.publications.id and la.activity=a.activity";
				if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
					field += " and la.institutionId=? ";
				}
				field += " ),";
			} else {
				field += "(select cast(count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? and la.type=? and la.access=? and pb.id=a.publications.id  and la.activity=a.activity";
				if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
					field += " and la.institutionId=? ";
				}
				field += " )";
			}
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by a.publications.id,a.activity " + sort, LAccess.class.getName(), pageCount, curpage * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public List<LAccess> getCounterListToPage(Map<String, Object> condition, String sort) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		condition.put("minMonth", minMonth);
		condition.put("maxMonth", maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,activity,publications.title,publications.code,publications.publisher.name,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;// ,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12
										// ";
		}
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id )" + ",a.institutionId" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )" + ",(select cast(count(la.id) as string) from LAccess la where la.year=? and la.type=? and la.access=? and la.publications.id=a.publications.id and la.institutionId=a.institutionId ";
		if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
			field += " and la.institutionId=? ";
		}
		field += "),";
		for (int i = minMonth; i <= maxMonth; i++) {
			if (i < maxMonth) {
				field += "(select cast (count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? and la.type=? and la.access=? and pb.id=a.publications.id and la.institutionId=a.institutionId";
				if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
					field += " and la.institutionId=? ";
				}
				field += " ),";
			} else {
				field += "(select cast(count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? and la.type=? and la.access=? and pb.id=a.publications.id  and la.institutionId=a.institutionId ";
				if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
					field += " and la.institutionId=? ";
				}
				field += " )";
			}
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by a.publications.id,a.institutionId  " + sort, LAccess.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public List<LAccess> getCounterPagingListToPage(Map<String, Object> condition, String sort, int pageCount, int curpage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		condition.put("minMonth", minMonth);
		condition.put("maxMonth", maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,publications.author,publications.pubDate,publications.cover,publications.lang,activity,publications.title,publications.code,publications.publisher.name,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;// ,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12
										// ";
		}
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id)" + ",(select p.author from PPublications p where p.id=a.publications.id )" + ",(select p.pubDate from PPublications p where p.id=a.publications.id )" + ",(select p.cover from PPublications p where p.id=a.publications.id )" + ",(select p.lang from PPublications p where p.id=a.publications.id )" + ",a.institutionId" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )" + ",(select cast(count(la.id) as string) from LAccess la where la.year=? and la.type=? and la.access=? and la.publications.id=a.publications.id and la.institutionId=a.institutionId ";
		if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
			field += " and la.institutionId=? ";
		}
		field += "),";
		for (int i = minMonth; i <= maxMonth; i++) {
			if (i < maxMonth) {
				field += "(select cast (count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? and la.type=? and la.access=? and pb.id=a.publications.id and la.institutionId=a.institutionId";
				if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
					field += " and la.institutionId=? ";
				}
				field += " ),";
			} else {
				field += "(select cast(count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? and la.type=? and la.access=? and pb.id=a.publications.id  and la.institutionId=a.institutionId ";
				if (condition.get("institutionId") != null && !"".equalsIgnoreCase(condition.get("institutionId").toString())) {
					field += " and la.institutionId=? ";
				}
				field += " )";
			}
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by a.publications.id,a.institutionId  " + sort, LAccess.class.getName(), pageCount, curpage * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<LAccess> getCounterListForSource(Map<String, Object> condition, String sort) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		condition.put("minMonth", minMonth);
		condition.put("maxMonth", maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,activity,publications.title,publications.code,publications.publisher.name,year,";
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id )" + ",a.activity" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )" + ",(select cast (count(la.id) as string) from LAccess la left join la.publications pb where la.year=? ";
		field += "and la.type=? ";
		field += "and la.access=? and pb.id=a.publications.id and la.activity=a.activity and pb.sourceId=?";
		field += " ),";
		for (int i = minMonth; i <= maxMonth; i++) {
			if (i < maxMonth) {
				property += "month" + i + ",";
				field += "(select cast (count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? and la.type=? and la.access=? and pb.id=a.publications.id and la.activity=a.activity and pb.sourceId=?";
				field += " ),";
			} else {
				property += "month" + i + "";
				field += "(select cast(count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? and la.type=? and la.access=? and pb.id=a.publications.id  and la.activity=a.activity and pb.sourceId=?";
				field += " )";
			}
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by a.publications.id,a.activity " + sort, LAccess.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<LAccess> getCounterListCountForSource(Map<String, Object> condition) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id ";
		String field = "cast(count(*) as string)";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by a.publications.id,a.activity ", LAccess.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<LAccess> getCounterPagingListForSource(Map<String, Object> condition, String sort, int pageCount, int page) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		condition.put("minMonth", minMonth);
		condition.put("maxMonth", maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,activity,publications.title,publications.code,publications.publisher.name,year,";
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id )" + ",a.activity" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )" + ",(select cast (count(la.id) as string) from LAccess la left join la.publications pb where la.year=? ";
		field += "and la.type=? ";
		field += "and la.access=? and pb.id=a.publications.id and la.activity=a.activity and pb.sourceId=?";
		field += " ),";
		for (int i = minMonth; i <= maxMonth; i++) {
			if (i < maxMonth) {
				property += "month" + i + ",";
				field += "(select cast (count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? and la.type=? and la.access=? and pb.id=a.publications.id and la.activity=a.activity and pb.sourceId=?";
				field += " ),";
			} else {
				property += "month" + i + "";
				field += "(select cast(count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? and la.type=? and la.access=? and pb.id=a.publications.id  and la.activity=a.activity and pb.sourceId=?";
				field += " )";
			}
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by a.publications.id,a.activity " + sort, LAccess.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 获取统计列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getPubCounterForSource(Map<String, Object> condition, String sort) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		condition.put("minMonth", minMonth);
		condition.put("maxMonth", maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,publications.title,publications.code,publications.publisher.name,year,";
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id )" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )" + ",(select cast(count(a.id) as string) from LAccess la where la.year=? ";
		if (condition.get("type") != null) {
			field += "and la.type=? ";
		}
		if (condition.get("types") != null) {
			Integer[] types = (Integer[]) condition.get("types");
			field += "and la.type in (";
			for (int i = 0; i < types.length; i++) {
				if (i < types.length - 1) {
					field += "?,";
				} else {
					field += "?";
				}
			}
			field += ")";
		}
		field += "and la.access=? and la.publications.id=a.publications.id and la.publications.sourceId=?";
		field += "),";
		System.out.println("@@@@@@@@minMonth:" + minMonth);
		System.out.println("@@@@@@@@maxMonth:" + maxMonth);
		for (int i = minMonth; i <= maxMonth; i++) {
			if (i < maxMonth) {
				property += "month" + i + ",";
				field += "(select cast (count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? ";
				if (condition.get("type") != null) {
					field += "and la.type=? ";
				}
				if (condition.get("types") != null) {
					Integer[] types = (Integer[]) condition.get("types");
					field += "and la.type in (";
					for (int m = 0; m < types.length; m++) {
						if (m < types.length - 1) {
							field += "?,";
						} else {
							field += "?";
						}
					}
					field += ")";
				}
				field += "and la.access=? and pb.id=a.publications.id and pb.sourceId=?";
				field += " ),";
			} else {
				property += "month" + i + "";
				field += "(select cast (count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? ";
				if (condition.get("type") != null) {
					field += "and la.type=? ";
				}
				if (condition.get("types") != null) {
					Integer[] types = (Integer[]) condition.get("types");
					field += "and la.type in (";
					for (int m = 0; m < types.length; m++) {
						if (m < types.length - 1) {
							field += "?,";
						} else {
							field += "?";
						}
					}
					field += ")";
				}
				field += "and la.access=? and pb.id=a.publications.id and pb.sourceId=?";
				field += " )";
			}
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by a.publications.id " + sort, LAccess.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 获取统计总数
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getPubCounterCountForSource(Map<String, Object> condition) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id ";
		String field = "cast(count(*) as string)";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by a.publications.id ", LAccess.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 获取统计列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getPubCounterPagingForSource(Map<String, Object> condition, String sort, int pageCount, int page) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		condition.put("minMonth", minMonth);
		condition.put("maxMonth", maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,publications.title,publications.code,publications.publisher.name,year,";
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id )" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )" + ",(select cast(count(a.id) as string) from LAccess la where la.year=? ";
		if (condition.get("type") != null) {
			field += "and la.type=? ";
		}
		if (condition.get("types") != null) {
			Integer[] types = (Integer[]) condition.get("types");
			field += "and la.type in (";
			for (int i = 0; i < types.length; i++) {
				if (i < types.length - 1) {
					field += "?,";
				} else {
					field += "?";
				}
			}
			field += ")";
		}
		field += "and la.access=? and la.publications.id=a.publications.id and la.publications.sourceId=?";
		field += "),";
		System.out.println("@@@@@@@@minMonth:" + minMonth);
		System.out.println("@@@@@@@@maxMonth:" + maxMonth);
		for (int i = minMonth; i <= maxMonth; i++) {
			if (i < maxMonth) {
				property += "month" + i + ",";
				field += "(select cast (count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? ";
				if (condition.get("type") != null) {
					field += "and la.type=? ";
				}
				if (condition.get("types") != null) {
					Integer[] types = (Integer[]) condition.get("types");
					field += "and la.type in (";
					for (int m = 0; m < types.length; m++) {
						if (m < types.length - 1) {
							field += "?,";
						} else {
							field += "?";
						}
					}
					field += ")";
				}
				field += "and la.access=? and pb.id=a.publications.id and pb.sourceId=?";
				field += " ),";
			} else {
				property += "month" + i + "";
				field += "(select cast (count(la.id) as string) from LAccess la left join la.publications pb where la.month=? and la.year=? ";
				if (condition.get("type") != null) {
					field += "and la.type=? ";
				}
				if (condition.get("types") != null) {
					Integer[] types = (Integer[]) condition.get("types");
					field += "and la.type in (";
					for (int m = 0; m < types.length; m++) {
						if (m < types.length - 1) {
							field += "?,";
						} else {
							field += "?";
						}
					}
					field += ")";
				}
				field += "and la.access=? and pb.id=a.publications.id and pb.sourceId=?";
				field += " )";
			}
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by a.publications.id " + sort, LAccess.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 获取统计总数
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer getGroupCount(Map<String, Object> condition, String group) throws Exception {
		int result = 0;
		String hql = " from LAccess a ";// left join a.publications b ";
		Map<String, Object> t = this.getWhere(condition);
		String property = " id ";
		String field = " cast(count(*) as string) ";
		try {
			List<LAccess> list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), group, LAccess.class.getName());
			if (list != null) {
				result = list.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Integer getCount(Map<String, Object> condition) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";
		Map<String, Object> t = this.getWhere(condition);
		try {
			list = this.hibernateDao.getListByHql("id", "cast(count(*) as string)", hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), "", LAccess.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list == null ? 0 : Integer.valueOf(list.get(0).getId());
	}

	@SuppressWarnings("unchecked")
	public List<LAccess> getTopList(Map<String, Object> condition, Integer number) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";
		Map<String, Object> t = this.getWhere(condition);
		List<Object> cond = new ArrayList<Object>();
		String wa = " where ";
		String property = " publications.id,publications.type,publications.title,publications.code,publications.volumeCode," + "publications.issueCode,publications.year,publications.month,publications.startPage," + "publications.endPage,publications.author,publications.publisher.name,year";
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id )" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select p.volumeCode from PPublications p where p.id=a.publications.id )" + ",(select p.issueCode from PPublications p where p.id=a.publications.id )" + ",(select p.year from PPublications p where p.id=a.publications.id )" + ",(select p.month from PPublications p where p.id=a.publications.id )" + ",(select p.startPage from PPublications p where p.id=a.publications.id )" + ",(select p.endPage from PPublications p where p.id=a.publications.id )" + ",(select p.author from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )" + ",(select cast(count(la.id) as string) from LAccess la ";
		if (condition.get("type") != null) {
			field += wa + " la.type=? ";
			wa = " and ";
			cond.add(condition.get("type"));
		}
		if (condition.get("year") != null) {
			field += wa + " la.year=? ";
			wa = " and ";
			cond.add(condition.get("year"));
		}
		if (condition.get("month") != null) {
			field += wa + " la.month=? ";
			wa = " and ";
			cond.add(condition.get("month"));
		}
		if (condition.get("types") != null) {
			Integer[] types = (Integer[]) condition.get("types");
			field += wa + " la.type in (";
			wa = " and ";
			for (int i = 0; i < types.length; i++) {
				if (i < types.length - 1) {
					field += "?,";
				} else {
					field += "?";
				}
				cond.add(types[i]);
			}
			field += ")";
		}
		if (condition.get("access") != null) {
			field += wa + " la.access= ? ";
			wa = " and ";
			cond.add(condition.get("access"));
		}
		if (condition.get("sourceId") != null) {
			field += wa + " la.publications.sourceId= ? ";
			wa = " and ";
			cond.add(condition.get("sourceId"));
		}
		if (condition.get("parentId") != null) {
			field += wa + " la.publications.publications.id= ? ";
			wa = " and ";
			cond.add(condition.get("pubParentId"));
		}
		if (condition.get("pubStatus") != null) {
			field += wa + " la.publications.publications.status= ? ";
			wa = " and ";
			cond.add(condition.get("pubStatus"));
		}
		if (condition.get("pubType") != null) {
			field += wa + " la.publications.publications.type= ? ";
			wa = " and ";
			cond.add(condition.get("pubType"));
		}
		field += "and la.publications.id=a.publications.id";
		field += ")";
		List<Object> tcond = (List<Object>) t.get("condition");
		if (tcond != null && tcond.size() > 0) {
			for (Object o : tcond) {
				cond.add(o);
			}
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), cond.toArray(), " group by a.publications.id order by a.year desc ", LAccess.class.getName(), number, 0);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 询是否存在访问内容日志
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> isExistLogForPage(Map<String, Object> condition) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Map<String, Object> t = this.getWhere(condition);
		String property = " id ";
		String field = " a.id ";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), null, LAccess.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 查询浏览统计2
	 * 
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getCounterPagingListToPage2(Map<String, Object> condition, String sort, int pageCount, int curpage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		// condition.put("minMonth" , minMonth);
		// condition.put("maxMonth" , maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,publications.author,publications.pubDate,publications.cover,publications.lang,activity,publications.title,publications.code,publications.publisher.name";// ,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;// ,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12
										// ";
		}
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id)" + ",(select p.author from PPublications p where p.id=a.publications.id )" + ",(select p.pubDate from PPublications p where p.id=a.publications.id )" + ",(select p.cover from PPublications p where p.id=a.publications.id )" + ",(select p.lang from PPublications p where p.id=a.publications.id )" + ",a.institutionId" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )";
		// ",(select cast(count(la.id) as string) from LAccess la where
		// la.year=? and la.type=? and la.access=? and
		// la.publications.id=a.publications.id and
		// la.institutionId=a.institutionId ";
		// if(condition.get("institutionId")!=null&&!"".equalsIgnoreCase(condition.get("institutionId").toString())){
		// field += " and la.institutionId=? ";
		// }
		// field += ")";
		for (int i = minMonth; i <= maxMonth; i++) {
			field += ",a.month" + i;
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), "  " + sort, LAccess.class.getName(), pageCount, curpage * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 查询浏览统计3
	 * 
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getCounterPagingListToPage3(Map<String, Object> condition, String sort, int pageCount, int curpage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		// condition.put("minMonth" , minMonth);
		// condition.put("maxMonth" , maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,publications.author,publications.pubDate,publications.cover,publications.lang,publications.title,publications.code,publications.publisher.name";// ,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;// ,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12
										// ";
		}
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id)" + ",(select p.author from PPublications p where p.id=a.publications.id )" + ",(select p.pubDate from PPublications p where p.id=a.publications.id )" + ",(select p.cover from PPublications p where p.id=a.publications.id )" + ",(select p.lang from PPublications p where p.id=a.publications.id )" +
				// ",a.institutionId" +
				",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )";
		// ",(select cast(count(la.id) as string) from LAccess la where
		// la.year=? and la.type=? and la.access=? and
		// la.publications.id=a.publications.id and
		// la.institutionId=a.institutionId ";
		// if(condition.get("institutionId")!=null&&!"".equalsIgnoreCase(condition.get("institutionId").toString())){
		// field += " and la.institutionId=? ";
		// }
		// field += ")";
		condition.remove("startMonth");
		condition.remove("endMonth");
		condition.remove("institutionId");
		List<Object> con = new ArrayList<Object>();
		for (int i = minMonth; i <= maxMonth; i++) {
			Map<String, Object> tt = this.getWhere(condition);
			for (Object o : (List<Object>) tt.get("condition")) {
				con.add(o);
			}
			con.add(String.format("%02d", i));
			field += ",(select cast(count(*) as string) from LAccess la " + tt.get("where").toString().replace("a.", "la.") + " and  la.publications.id=a.publications.id and la.month=?)";
		}
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		try {
			if (pageCount == 0 && curpage == 0) {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName());
			} else {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName(), pageCount, curpage * pageCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 查询浏览统计4
	 * 
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getCounterPagingListToPage4(Map<String, Object> condition, String sort, int pageCount, int curpage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		// condition.put("minMonth" , minMonth);
		// condition.put("maxMonth" , maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,publications.author,publications.pubDate,publications.cover,publications.lang,publications.title,publications.code,publications.publisher.name";// ,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;// ,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12
										// ";
		}
		String field = " " + " a.refusedVisitType,a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id)" + ",(select p.author from PPublications p where p.id=a.publications.id )" + ",(select p.pubDate from PPublications p where p.id=a.publications.id )" + ",(select p.cover from PPublications p where p.id=a.publications.id )" + ",(select p.lang from PPublications p where p.id=a.publications.id )" +
				// ",a.institutionId" +
				",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )";
		// ",(select cast(count(la.id) as string) from LAccess la where
		// la.year=? and la.type=? and la.access=? and
		// la.publications.id=a.publications.id and
		// la.institutionId=a.institutionId ";
		// if(condition.get("institutionId")!=null&&!"".equalsIgnoreCase(condition.get("institutionId").toString())){
		// field += " and la.institutionId=? ";
		// }
		// field += ")";
		condition.remove("startMonth");
		condition.remove("endMonth");
		condition.remove("institutionId");
		List<Object> con = new ArrayList<Object>();
		for (int i = minMonth; i <= maxMonth; i++) {
			Map<String, Object> tt = this.getWhere(condition);
			for (Object o : (List<Object>) tt.get("condition")) {
				con.add(o);
			}
			con.add(String.format("%02d", i));
			field += ",(select cast(count(*) as string) from LAccess la " + tt.get("where").toString().replace("a.", "la.") + " and  la.publications.id=a.publications.id and la.refusedVisitType=a.refusedVisitType and la.month=?)";
		}
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		try {
			if (pageCount == 0 && curpage == 0) {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName());
			} else {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName(), pageCount, curpage * pageCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 查询访问统计2（分页）
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getCounterPagingList2(Map<String, Object> condition, String sort, int pageCount, int curpage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		// condition.put("minMonth" , minMonth);
		// condition.put("maxMonth" , maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,publications.author,publications.pubDate,publications.cover,publications.lang,publications.title,publications.code,publications.publisher.name";// ,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;// ,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12
										// ";
		}
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id)" + ",(select p.author from PPublications p where p.id=a.publications.id )" + ",(select p.pubDate from PPublications p where p.id=a.publications.id )" + ",(select p.cover from PPublications p where p.id=a.publications.id )" + ",(select p.lang from PPublications p where p.id=a.publications.id )" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )";
		// field += ",(select cast(count(la.id) as string) from LAccess la where
		// la.year=? and la.type=? and la.access=? and
		// la.publications.id=a.publications.id ";
		// if(condition.get("institutionId")!=null&&!"".equalsIgnoreCase(condition.get("institutionId").toString())){
		// field += " and la.institutionId=? ";
		// }
		// field += ")";
		for (int i = minMonth; i <= maxMonth; i++) {
			field += ",a.month" + i;
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " " + sort, LAccess.class.getName(), pageCount, curpage * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 查询访问统计3（分页）
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getCounterPagingList3(Map<String, Object> condition, String sort, int pageCount, int curpage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		// condition.put("minMonth" , minMonth);
		// condition.put("maxMonth" , maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,publications.author,publications.pubDate,publications.cover,publications.lang,publications.title,publications.code,publications.publisher.name";// ,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;// ,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12
										// ";
		}
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id)" + ",(select p.author from PPublications p where p.id=a.publications.id )" + ",(select p.pubDate from PPublications p where p.id=a.publications.id )" + ",(select p.cover from PPublications p where p.id=a.publications.id )" + ",(select p.lang from PPublications p where p.id=a.publications.id )" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )";
		// field += ",(select cast(count(la.id) as string) from LAccess la where
		// la.year=? and la.type=? and la.access=? and
		// la.publications.id=a.publications.id ";
		// if(condition.get("institutionId")!=null&&!"".equalsIgnoreCase(condition.get("institutionId").toString())){
		// field += " and la.institutionId=? ";
		// }
		// field += ")";
		condition.remove("startMonth");
		condition.remove("endMonth");
		condition.remove("institutionId");
		List<Object> con = new ArrayList<Object>();
		for (int i = minMonth; i <= maxMonth; i++) {
			Map<String, Object> tt = this.getWhere(condition);
			for (Object o : (List<Object>) tt.get("condition")) {
				con.add(o);
			}
			con.add(String.format("%02d", i));
			field += ",(select cast(count(*) as string) from LAccess la " + tt.get("where").toString().replace("a.", "la.") + " and  la.publications.id=a.publications.id and la.month=?)";
		}
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		try {
			if (pageCount == 0 && curpage == 0) {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName());
			} else {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName(), pageCount, curpage * pageCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 查询访问统计10（分页）
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getCounterPagingList10(Map<String, Object> condition, String sort, int pageCount, int curpage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		Map<String, Object> t = this.getWhere(condition);
		String property = " institutionId,year,platform,month,publications.id,publications.type,publications.author,publications.pubDate,publications.title,publications.code,publications.eissn,publications.lang,publications.publisher.name";// ,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;
		}
		String field = " " + "a.institutionId" + ",a.year" + ",a.platform" + ",a.month" + ",a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id)" + ",(select p.author from PPublications p where p.id=a.publications.id )" + ",(select p.pubDate from PPublications p where p.id=a.publications.id )" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select p.eissn from PPublications p where p.id=a.publications.id )" + ",(select p.lang from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )";

		condition.remove("startMonth");
		condition.remove("endMonth");

		List<Object> con = new ArrayList<Object>();
		for (int i = minMonth; i <= maxMonth; i++) {
			Map<String, Object> tt = this.getWhere(condition);
			for (Object o : (List<Object>) tt.get("condition")) {
				con.add(o);
			}
			con.add(String.format("%02d", i));
			field += ",(select cast(count(*) as string) from LAccess la " + tt.get("where").toString().replace("a.", "la.") + " and  la.publications.id=a.publications.id and la.month=?)";
		}

		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		try {
			if (pageCount == 0 && curpage == 0) {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName());
			} else {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName(), pageCount, curpage * pageCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 查询搜索统计2（分页）
	 * 
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getCounterPagingListToSearch2(Map<String, Object> condition, String sort, int pageCount, int curpage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		// condition.put("minMonth" , minMonth);
		// condition.put("maxMonth" , maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,publications.author,publications.pubDate,publications.cover,publications.lang,activity,publications.title,publications.code,publications.publisher.name";// ,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;// ,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12
										// ";
		}
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id)" + ",(select p.author from PPublications p where p.id=a.publications.id )" + ",(select p.pubDate from PPublications p where p.id=a.publications.id )" + ",(select p.cover from PPublications p where p.id=a.publications.id )" + ",(select p.lang from PPublications p where p.id=a.publications.id )" + ",a.activity" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )";

		/*
		 * field += ",("; for(int i=minMonth;i<=maxMonth;i++){ if(i<maxMonth){
		 * field += "a.month"+i+"+"; }else{ field += "a.month"+i; } } field +=
		 * ") as year";
		 */
		for (int i = minMonth; i <= maxMonth; i++) {
			field += ",a.month" + i;
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, LAccess.class.getName(), pageCount, curpage * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 查询搜索统计3（分页）
	 * 
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getCounterPagingListToSearch3(Map<String, Object> condition, String sort, int pageCount, int curpage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		// condition.put("minMonth" , minMonth);
		// condition.put("maxMonth" , maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,publications.author,publications.pubDate,publications.cover,publications.lang,publications.title,publications.code,publications.publisher.name";// ,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;// ,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12
										// ";
		}
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id)" + ",(select p.author from PPublications p where p.id=a.publications.id )" + ",(select p.pubDate from PPublications p where p.id=a.publications.id )" + ",(select p.cover from PPublications p where p.id=a.publications.id )" + ",(select p.lang from PPublications p where p.id=a.publications.id )" +
				// ",a.activity" +
				",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )";

		condition.remove("startMonth");
		condition.remove("endMonth");
		List<Object> con = new ArrayList<Object>();
		for (int i = minMonth; i <= maxMonth; i++) {
			Map<String, Object> tt = this.getWhere(condition);
			for (Object o : (List<Object>) tt.get("condition")) {
				con.add(o);
			}
			con.add(String.format("%02d", i));
			field += ",(select cast(count(*) as string) from LAccess la " + tt.get("where").toString().replace("a.", "la.") + " and  la.publications.id=a.publications.id and la.month=?)";
		}
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		try {
			if (pageCount == 0 && curpage == 0) {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName());
			} else {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName(), pageCount, curpage * pageCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 查询搜索记录
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getCounterListToPage2(Map<String, Object> condition, String sort) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		// condition.put("minMonth" , minMonth);
		// condition.put("maxMonth" , maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,activity,publications.title,publications.code,publications.publisher.name";// ,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;// ,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12
										// ";
		}
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id )" + ",a.institutionId" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )";
		// ",(select cast(count(la.id) as string) from LAccess la where
		// la.year=? and la.type=? and la.access=? and
		// la.publications.id=a.publications.id and
		// la.institutionId=a.institutionId ";
		// if(condition.get("institutionId")!=null&&!"".equalsIgnoreCase(condition.get("institutionId").toString())){
		// field += " and la.institutionId=? ";
		// }
		// field += "),";
		for (int i = minMonth; i <= maxMonth; i++) {
			field += ",a.month" + i;
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, LAccess.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 查询搜索记录
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getCounterListToSearch2(Map<String, Object> condition, String sort) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		// condition.put("minMonth" , minMonth);
		// condition.put("maxMonth" , maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,activity,publications.title,publications.code,publications.publisher.name";// ,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;// ,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12
										// ";
		}
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id )" + ",a.activity" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )";
		// ",(select cast(count(la.id) as string) from LAccess la where
		// la.year=? and la.type=? and la.access=? and
		// la.publications.id=a.publications.id and la.activity=a.activity ";
		// if(condition.get("institutionId")!=null&&!"".equalsIgnoreCase(condition.get("institutionId").toString())){
		// field += " and la.institutionId=? ";
		// }
		// field += "),";
		for (int i = minMonth; i <= maxMonth; i++) {
			field += ",a.month" + i;
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, LAccess.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 查询访问统计
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getCounterList2(Map<String, Object> condition, String sort) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		// condition.put("minMonth" , minMonth);
		// condition.put("maxMonth" , maxMonth);
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,publications.title,publications.code,publications.publisher.name";// ,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;// ,month2,month3,month4,month5,month6,month7,month8,month9,month10,month11,month12
										// ";
		}
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id )" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )";
		// field += ",(select cast(count(la.id) as string) from LAccess la where
		// la.year=? and la.type=? and la.access=? and
		// la.publications.id=a.publications.id ";
		// if(condition.get("institutionId")!=null&&!"".equalsIgnoreCase(condition.get("institutionId").toString())){
		// field += " and la.institutionId=? ";
		// }
		// field += ")";
		// field += ",";
		for (int i = minMonth; i <= maxMonth; i++) {
			field += ",a.month" + i;
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, LAccess.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curPage
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getLogOfHotWords(Map<String, Object> condition, String sort, int pageCount, int curPage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";
		Map<String, Object> t = this.getWhere(condition);
		String property = " activity ";
		String field = "a.activity";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, LAccess.class.getName(), pageCount, curPage * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curPage
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getcnLogOfHotWords(Map<String, Object> condition, String sort, int pageCount, int curPage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a left join a.publications b ";
		Map<String, Object> t = this.getWhere(condition);
		String property = " activity ";
		String field = "a.activity";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, LAccess.class.getName(), pageCount, curPage * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<LAccess> getLogOfHotReading(Map<String, Object> condition, String sort, int pageCount, int curPage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,publications.lang,publications.author,publications.pubDate,publications.cover,publications.title,publications.code,publications.remark,publications.publisher.name,publications.publisher.id";
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id)" + ",(select p.lang from PPublications p where p.id=a.publications.id)" + ",(select p.author from PPublications p where p.id=a.publications.id )" + ",(select p.pubDate from PPublications p where p.id=a.publications.id )" + ",(select p.cover from PPublications p where p.id=a.publications.id )" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select p.remark from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )" + ",(select pp.id from PPublications p left join p.publisher pp where p.id=a.publications.id )";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, LAccess.class.getName(), pageCount, curPage * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<LAccess> getLogOfRecentlyRead(Map<String, Object> condition, String sort, int pageCount, int curPage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";
		Map<String, Object> t = this.getWhere(condition);
		String property = "publications.id,publications.type,publications.author,publications.pubDate,publications.cover,publications.title," + "publications.code,publications.available,publications.remark,publications.publisher.name,publications.publisher.id,publications.free,publications.oa,createOn";
		String field = " a.publications.id,a.publications.type,a.publications.author,a.publications.pubDate,a.publications.cover,a.publications.title," + "a.publications.code,a.publications.available,a.publications.remark,a.publications.publisher.name,a.publications.publisher.id,a.publications.free,a.publications.oa,a.createOn";

		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, LAccess.class.getName(), pageCount, curPage * pageCount);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<LAccess> getCounterList3(Map<String, Object> condition, String sort) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;

		Map<String, Object> t = this.getWhere(condition);
		String property = " id,publications.id,publications.type,publications.author,publications.pubDate,publications.cover,publications.lang,publications.title,publications.code,publications.publisher.name ";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;
		}
		String field = " a.id,a.publications.id,a.publications.type,a.publications.author,a.publications.pubDate,a.publications.cover" + ",a.publications.lang,a.publications.title,a.publications.code,a.publications.publisher.name ";
		for (int i = minMonth; i <= maxMonth; i++) {
			field += ",a.month" + i;
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " " + sort, LAccess.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public Integer getGroupCount2(Map<String, Object> condition, String group) throws Exception {
		int result = 0;
		String hql = " from LAccess a ";// left join a.publications b ";
		Map<String, Object> t = this.getWhere(condition);
		String property = " id ";
		String field = " cast(count(*) as string) ";
		try {
			List<LAccess> list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), group, LAccess.class.getName());
			if (list != null) {
				result = list.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	/**
	 * COUNTER统计报表1
	 * 
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getJournalReport1(Map<String, Object> condition, String sort, int pageCount, int curpage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;

		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.publications.id,publications.publications.title,publications.publications.code,publications.publications.publisher.name";// ,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;
		}
		String field = " " + "a.publications.publications.id" + ",(select p.title from PPublications p where p.id=a.publications.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.publications.id )";

		condition.remove("startMonth");
		condition.remove("endMonth");
		List<Object> con = new ArrayList<Object>();
		for (int i = minMonth; i <= maxMonth; i++) {
			Map<String, Object> tt = this.getWhere(condition);
			for (Object o : (List<Object>) tt.get("condition")) {
				con.add(o);
			}
			con.add(String.format("%02d", i));
			field += ",(select cast(count(*) as string) from LAccess la " + tt.get("where").toString().replace("a.", "la.") + " and  la.publications.publications.id=a.publications.publications.id and la.month=?)";
		}
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		try {
			if (pageCount == 0 && curpage == 0) {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName());
			} else {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName(), pageCount, curpage * pageCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * COUNTER统计报表 (按年统计)
	 * 
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getJournalReportYOP(Map<String, Object> condition, String sort, int pageCount, int curpage) throws Exception {

		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minYear = Integer.valueOf(condition.get("startYear").toString());
		Integer maxYear = Integer.valueOf(condition.get("endYear").toString());

		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.publications.id,publications.publications.title,publications.publications.code,publications.publications.publisher.name,year";
		for (int i = 1; i <= (maxYear - minYear + 1); i++) {
			property += ",month" + i;
		}
		String field = "a.publications.publications.id" + ",(select p.title from PPublications p where p.id=a.publications.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.publications.id )" + ",(select cast(count(*) as string) from PPublications p where p.publications.id=a.publications.publications.id and p.type=4)";// 期刊中的文章数量

		condition.remove("startYear");
		condition.remove("endYear");
		List<Object> con = new ArrayList<Object>();
		for (Integer i = maxYear; i >= minYear; i--) {
			Map<String, Object> tt = this.getWhere(condition);
			for (Object o : (List<Object>) tt.get("condition")) {
				con.add(o);
			}
			con.add(i.toString());
			field += ",(select cast(count(*) as string) from LAccess la " + tt.get("where").toString().replace("a.", "la.") + " and  la.publications.publications.id=a.publications.publications.id and la.year=?)";
		}
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		try {
			if (pageCount == 0 && curpage == 0) {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName());
			} else {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName(), pageCount, curpage * pageCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 统计点击期刊
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getJournalReport2(Map<String, Object> condition, String sort, int pageCount, int curpage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// left join a.publications b ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;

		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.publications.id,searchType,publications.publications.title,publications.publications.code,publications.publications.publisher.name";// ,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;
		}
		String field = " " + "a.publications.publications.id,a.searchType" + ",(select p.title from PPublications p where p.id=a.publications.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.publications.id )";

		condition.remove("startMonth");
		condition.remove("endMonth");
		List<Object> con = new ArrayList<Object>();
		for (int i = minMonth; i <= maxMonth; i++) {
			Map<String, Object> tt = this.getWhere(condition);
			for (Object o : (List<Object>) tt.get("condition")) {
				con.add(o);
			}
			con.add(String.format("%02d", i));
			field += ",(select cast(count(*) as string) from LAccess la " + tt.get("where").toString().replace("a.", "la.") + " and  la.publications.publications.id=a.publications.publications.id and la.month=? and la.searchType=a.searchType)";
		}
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		try {
			if (pageCount == 0 && curpage == 0) {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName());
			} else {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName(), pageCount, curpage * pageCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<LAccess> getJournalHotReading(Map<String, Object> condition, String sort, int pageCount, int curPage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";
		Map<String, Object> t = this.getWhere(condition);
		String property = "publications.id,publications.type,publications.author,publications.pubDate,publications.title,publications.code,publications.remark,publications.publisher.name,publications.publisher.id,publications.year,publications.volumeCode";
		String field = " " + "a.publications.id" + ",(select p.type from PPublications p where p.id=a.publications.id)" + ",(select p.author from PPublications p where p.id=a.publications.id )" + ",(select p.pubDate from PPublications p where p.id=a.publications.id )" + ",(select p.title from PPublications p where p.id=a.publications.id )" + ",(select p.code from PPublications p where p.id=a.publications.id )" + ",(select p.remark from PPublications p where p.id=a.publications.id )" + ",(select pp.name from PPublications p left join p.publisher pp where p.id=a.publications.id )" + ",(select pp.id from PPublications p left join p.publisher pp where p.id=a.publications.id )" + ",(select p.year from PPublications p where p.id=a.publications.id)" + ",(select p.volumeCode from PPublications p where p.id=a.publications.id)";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, LAccess.class.getName(), pageCount, curPage * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<LAccess> getCounterPagingList4(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// inner join a.content b inner join
										// a.content.source c inner join
										// a.content.publisher d ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		Map<String, Object> t = this.getWhere(condition);
		String property = " refusedVisitType,content.id,content.title,content.type,content.author,content.isbn,content.issn,content.eissn,content.publisher.name ";

		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;
		}
		// String field="
		// a.id,a.type,b.id,b.title,b.type,b.author,b.isbn,b.issn,b.eissn,c.id,c.name,d.id,d.name
		// ";
		String field = " " + " a.refusedVisitType,a.content.id" + ",(select p.title from PContent p where p.id=a.content.id )" + ",(select p.type from PContent p where p.id=a.content.id )" + ",(select p.author from PContent p where p.id=a.content.id )" + ",(select p.isbn from PContent p where p.id=a.content.id )" + ",(select p.issn from PContent p where p.id=a.content.id )" + ",(select p.eissn from PContent p where p.id=a.content.id )" + ",(select pp.name from PContent p left join p.publisher pp where p.id=a.content.id )";
		// field += ",(select la.refusedVisitType from LAccess la where la.type
		// = ? and la.content.type in (?,?) and la.access = ? and la.year = ?
		// and la.content.source.id = ? and la.month=? and
		// la.content.id=a.content.id )";
		condition.remove("startMonth");
		condition.remove("endMonth");

		List<Object> con = new ArrayList<Object>();

		for (int i = minMonth; i <= maxMonth; i++) {
			Map<String, Object> tt = this.getWhere(condition);

			for (Object o : (List<Object>) tt.get("condition")) {
				con.add(o);
			}
			con.add(String.format("%02d", i));
			field += ",(select cast(count(*) as string) from LAccess la " + tt.get("where").toString().replace("a.", "la.") + " and  la.content.id=a.content.id and la.refusedVisitType=a.refusedVisitType and la.month=?)";
		}

		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}

		try {
			if (pageCount == 0 && page == 0) {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName());
			} else {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName(), pageCount, page * pageCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	public List<LAccess> getCounterPagingList5(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a ";// inner join a.content b inner join
										// a.content.source c inner join
										// a.content.publisher d ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		Map<String, Object> t = this.getWhere(condition);
		String property = " content.id,content.title,content.type,content.author,content.isbn,content.issn,content.eissn,content.publisher.name ";

		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;
		}
		// String field="
		// a.id,a.type,b.id,b.title,b.type,b.author,b.isbn,b.issn,b.eissn,c.id,c.name,d.id,d.name
		// ";
		String field = " " + " a.content.id" + ",(select p.title from PContent p where p.id=a.content.id )" + ",(select p.type from PContent p where p.id=a.content.id )" + ",(select p.author from PContent p where p.id=a.content.id )" + ",(select p.isbn from PContent p where p.id=a.content.id )" + ",(select p.issn from PContent p where p.id=a.content.id )" + ",(select p.eissn from PContent p where p.id=a.content.id )" + ",(select pp.name from PContent p left join p.publisher pp where p.id=a.content.id )";
		// field += ",(select la.refusedVisitType from LAccess la where la.type
		// = ? and la.content.type in (?,?) and la.access = ? and la.year = ?
		// and la.content.source.id = ? and la.month=? and
		// la.content.id=a.content.id )";
		condition.remove("startMonth");
		condition.remove("endMonth");

		List<Object> con = new ArrayList<Object>();

		for (int i = minMonth; i <= maxMonth; i++) {
			Map<String, Object> tt = this.getWhere(condition);

			for (Object o : (List<Object>) tt.get("condition")) {
				con.add(o);
			}
			con.add(String.format("%02d", i));
			field += ",(select cast(count(*) as string) from LAccess la " + tt.get("where").toString().replace("a.", "la.") + " and  la.content.id=a.content.id and la.month=?)";
		}

		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}

		try {
			if (pageCount == 0 && page == 0) {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName());
			} else {
				list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName(), pageCount, page * pageCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;

	}

	/**
	 * 获取统计总数
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer getCount(Map<String, Object> condition, String group) throws Exception {
		int result = 0;
		String hql = " from LAccess a ";// inner join a.content b inner join
										// a.content.source c inner join
										// a.content.publisher d";
		Map<String, Object> t = this.getWhere(condition);
		String property = " id ";
		String field = " cast(count(*) as string) ";
		try {
			List<LAccess> list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), group, LAccess.class.getName());
			if (list != null) {
				result = list.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public void executeLaccess(Integer type) throws Exception {
		try {
			this.hibernateDao.executeSql("{call T_TEST_PUB_LACCESS(?)}", new Object[] { type });
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public List<LAccess> forArticleRight(Map<String, Object> condition, String sort, Integer pageCount, Integer curPage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a where a.userId IN(SELECT DISTINCT userId from LAccess b where b.publications.type=1 and b.publications.id='" + condition.get("rightpubid") + "' and b.userId<>'" + condition.get("rightuserid") + "')" + "and a.publications.id<>'" + condition.get("rightpubid") + "' and a.publications.type=1 and a.publications.status=2";
		Map<String, Object> t = this.getWhere(condition);
		String property = " id,publications.id ";
		String field = " cast(count(*) as string),a.publications.id ";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, LAccess.class.getName(), pageCount, curPage * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<LAccess> getLogOfHotReadingForRecommad(Map<String, Object> condition, String sort, int pageCount, int curPage) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a left join a.publications b left join b.publisher c ";
		Map<String, Object> t = this.getWhere(condition);
		String property = " publications.id,publications.type,publications.lang,publications.author,publications.pubDate,publications.cover,publications.title,publications.code,publications.remark,publications.publisher.name,publications.publisher.id,publications.startVolume,publications.endVolume";
		String field = " b.id " + ",(select p.type from PPublications p where p.id=b.id)" + ",(select p.lang from PPublications p where p.id=b.id)" + ",(select p.author from PPublications p where p.id=b.id )" + ",(select p.pubDate from PPublications p where p.id=b.id )" + ",(select p.cover from PPublications p where p.id=b.id )" + ",(select p.title from PPublications p where p.id=b.id )" + ",(select p.code from PPublications p where p.id=b.id )" + ",(select p.remark from PPublications p where p.id=b.id )" + ",(select p.publisher.name from PPublications p where p.id=b.id )" + ",(select p.publisher.id from PPublications p where p.id=b.id ) " + ",(select p.startVolume from PPublications p where p.id=b.id ) " + ",(select p.endVolume from PPublications p where p.id=b.id ) ";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, LAccess.class.getName(), pageCount, curPage * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 查询访问统计10（分页）
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LAccess> getCounterList11(Map<String, Object> condition, String sort) throws Exception {
		List<LAccess> list = null;
		String hql = " from LAccess a inner join a.publications b inner join a.publications.publisher c ";
		Integer minMonth = condition.containsKey("startMonth") && !"".equals(condition.get("startMonth").toString()) ? Integer.valueOf(condition.get("startMonth").toString()) : 12;
		Integer maxMonth = condition.containsKey("endMonth") && !"".equals(condition.get("endMonth").toString()) ? Integer.valueOf(condition.get("endMonth").toString()) : 12;
		Map<String, Object> t = this.getWhere(condition);
		String property = " institutionId,year,platform,month,publications.id,publications.type,publications.author,publications.pubDate," + "publications.title,publications.code,publications.eissn,publications.lang,publications.publisher.name ";// ,year";
		for (int i = minMonth; i <= maxMonth; i++) {
			property += ",month" + i;
		}
		String field = "a.institutionId,a.year,a.platform,a.month,b.id,b.type,b.author,b.pubDate,b.title,b.code,b.eissn,b.lang,c.name ";

		condition.remove("startMonth");
		condition.remove("endMonth");

		List<Object> con = new ArrayList<Object>();
		for (int i = minMonth; i <= maxMonth; i++) {
			field += ",a.month" + i;
		}

		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, LAccess.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}
}
