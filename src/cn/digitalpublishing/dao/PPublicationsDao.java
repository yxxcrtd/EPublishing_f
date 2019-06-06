package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.com.daxtech.framework.util.StringUtil;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.util.web.DateUtil;
import cn.digitalpublishing.util.web.IpUtil;

public class PPublicationsDao extends CommonDao<PPublications, String> {

	private Map<String, Object> getWhere(Map<String, Object> map) {
		Map<String, Object> table = new HashMap<String, Object>();
		String whereString = "";
		List<Object> condition = new ArrayList<Object>();
		int flag = 0;

		if ((CollectionsUtil.exist(map, "eeissnNew")) && (CollectionsUtil.exist(map, "ppissnNew"))) {
			if (flag == 0) {
				whereString = whereString + " where (a.code = ?  or a.eissn= ?  ) ";
				flag = 1;
			} else {
				whereString = whereString + " and  (a.code = ? or a.eissn= ?)  ";
			}
			condition.add(map.get("eeissnNew").toString());
			condition.add(map.get("ppissnNew").toString());
		}

		/**
		 * isInstitutionid
		 */
		if ((CollectionsUtil.exist(map, "isInstitutionid")) && map.get("isInstitutionid") != null && !"".equals(map.get("isInstitutionid"))) {
			if (flag == 0) {
				whereString = whereString + " where b.id = ? ";
				flag = 1;
			} else {
				whereString = whereString + " and  b.id = ?  ";
			}
			condition.add(map.get("isInstitutionid").toString());
		}
		/**
		 * 分类法
		 */
		if (CollectionsUtil.exist(map, "subjectId") && !"".equals(map.get("subjectId")) && map.get("subjectId") != null) {
			if (!"0".equals(map.get("subjectId"))) {
				if (flag == 0) {
					whereString += " where exists(select cs1.id from PCsRelation cs1 left join cs1.subject cs2 left join cs1.publications cs3 where cs3.status=2 and cs3.id = a.id and cs2.treeCode like (select cs2.treeCode from BSubject cs2 where cs2.id = ?)||'%') ";
					flag = 1;
				} else {
					whereString += " and exists(select cs1.id from PCsRelation cs1 left join cs1.subject cs2 left join cs1.publications cs3 where cs3.status=2 and cs3.id = a.id and cs2.treeCode like (select cs2.treeCode from BSubject cs2 where cs2.id = ?)||'%') ";
				}
				condition.add(map.get("subjectId").toString());
			}
		}

		/**
		 * 分类法Code
		 */
		if (CollectionsUtil.exist(map, "subjectCode") && !"".equals(map.get("subjectCode")) && map.get("subjectCode") != null) {
			if (flag == 0) {
				whereString += " where exists(select cs1.id from PCsRelation cs1 join cs1.subject cs2 join cs1.publications cs3 where cs3.status=2 and cs3.id = a.id and cs2.treeCode like ?) ";
				flag = 1;
			} else {
				whereString += " and exists(select cs1.id from PCsRelation cs1 join cs1.subject cs2 join cs1.publications cs3 where cs3.status=2 and cs3.id = a.id and cs2.treeCode like ?) ";
			}
			condition.add(map.get("subjectCode").toString() + "%");
		}
		/**
		 * 
		 */
		if (CollectionsUtil.exist(map, "pCode") && !"".equals(map.get("pCode"))) {
			if (flag == 0) {
				whereString += " where a.code like ?";
				flag = 1;
			} else {
				whereString += " and a.code like ?";
			}
			condition.add("%" + map.get("pCode") + "%");
		}
		/**
		 * 1.order A-Z字母排序
		 */
		if (CollectionsUtil.exist(map, "order") && !"".equals(map.get("order"))) {
			if (flag == 0) {
				whereString += " where lower(a.title) like ?";
				flag = 1;
			} else {
				whereString += " and lower(a.title) like ?";
			}
			condition.add(map.get("order").toString().toLowerCase() + "%");
		}

		/**
		 * isLicense 1、在已订阅中查询 2 、未订阅中
		 */
		if (CollectionsUtil.exist(map, "isLicense") && !"".equals(map.get("isLicense")) && map.get("isLicense") != null) {

			/*
			 * String where1 = ""; String where2 = ""; String where3 = "";
			 * 
			 * if(CollectionsUtil.exist(map,
			 * "userId")&&!"".equals(map.get("userId"))){ where1 =
			 * "exists(select ll.id from LLicense ll where ll.status=1 and ll.user.id=? and (ll.publications.id=a.id or ll.publications.code=a.code))"
			 * ; condition.add(map.get("userId")); }
			 * if(CollectionsUtil.exist(map, "ip")&&!"".equals(map.get("ip"))){
			 * where2 =
			 * "exists(select lip.id from LLicenseIp lip where lip.license.status=1 and lip.sip<=? and lip.eip>=? and (lip.license.publications.id=a.id or lip.license.publications.code=a.code))"
			 * ; condition.add(IpUtil.getLongIp(map.get("ip").toString()));
			 * condition.add(IpUtil.getLongIp(map.get("ip").toString())); }
			 * //产品本身是OA或免费 where3 = " a.oa=2 or a.free=2 ";
			 * if(CollectionsUtil.exist(map,
			 * "orOnSale")&&"true".equals(map.get("orOnSale"))){
			 * where1="".equals(where1)?" a.status=2 ":" a.status=2 or " +
			 * where1; } if(flag==0){ whereString+=" where ";
			 * if(!"".equals(where1)&&!"".equals(where2)){ whereString +=
			 * "("+where1+" or "+where2+" or "+where3+")"; }else
			 * if(!"".equals(where1)){ whereString += "("+where1+" or "
			 * +where3+")"; }else if(!"".equals(where2)){ whereString +=
			 * "("+where2+" or "+where3+")"; } flag=1; }else{ whereString+=
			 * " and "; if(!"".equals(where1)&&!"".equals(where2)){ whereString
			 * += "("+where1+" or "+where2+" or "+where3+")"; }else
			 * if(!"".equals(where1)){ whereString += "("+where1+" or "
			 * +where3+")"; }else if(!"".equals(where2)){ whereString += "("+
			 * where2+" or "+where3+")"; } }
			 */
		}
		/**
		 * isLicense 1、在已订阅中查询 2 、未订阅中
		 */
		if (CollectionsUtil.exist(map, "isLicense2") && !"".equals(map.get("isLicense2")) && map.get("isLicense2") != null) {
			String where1 = "";
			String where2 = "";
			String where3 = "";

			if (CollectionsUtil.exist(map, "userId") && !"".equals(map.get("userId"))) {
				where1 = "exists(select ll.id from LLicense ll where ll.status=1 and ll.user.id=? and (ll.publications.id=a.id or ll.publications.code=a.code))";
				condition.add(map.get("userId"));
			}
			if (CollectionsUtil.exist(map, "ip") && !"".equals(map.get("ip"))) {
				where2 = "exists(select lip.id from LLicenseIp lip where lip.license.status=1 and lip.sip<=? and lip.eip>=? and (lip.license.publications.id=a.id or lip.license.publications.code=a.code))";
				condition.add(IpUtil.getLongIp(map.get("ip").toString()));
				condition.add(IpUtil.getLongIp(map.get("ip").toString()));
			}
			// 产品本身是OA或免费
			where3 = " a.oa=2 or a.free=2 ";
			if (CollectionsUtil.exist(map, "orOnSale") && "true".equals(map.get("orOnSale"))) {
				where1 = "".equals(where1) ? " a.status=2 " : " a.status=2 or " + where1;
			}
			if (flag == 0) {
				whereString += " where ";
				if (!"".equals(where1) && !"".equals(where2)) {
					whereString += "(" + where1 + " or " + where2 + " or " + where3 + ")";
				} else if (!"".equals(where1)) {
					whereString += "(" + where1 + " or " + where3 + ")";
				} else if (!"".equals(where2)) {
					whereString += "(" + where2 + " or " + where3 + ")";
				}
				flag = 1;
			} else {
				whereString += " and ";
				if (!"".equals(where1) && !"".equals(where2)) {
					whereString += "(" + where1 + " or " + where2 + " or " + where3 + ")";
				} else if (!"".equals(where1)) {
					whereString += "(" + where1 + " or " + where3 + ")";
				} else if (!"".equals(where2)) {
					whereString += "(" + where2 + " or " + where3 + ")";
				}
			}
		}

		if (CollectionsUtil.exist(map, "Subscribed") && !"".equals(map.get("Subscribed")) && map.get("Subscribed") != null) {
			String where1 = "";
			String where2 = "";
			String where3 = "";

			if (CollectionsUtil.exist(map, "userId") && !"".equals(map.get("userId"))) {
				where1 = "exists(select ll.id from LLicense ll where ll.status=1 and ll.user.id=? and ll.publications.id=a.id)";
				condition.add(map.get("userId"));
			}
			if (CollectionsUtil.exist(map, "ip") && !"".equals(map.get("ip"))) {
				where2 = "exists(select lip.id from LLicenseIp lip where lip.license.status=1 and lip.sip<=? and lip.eip>=? and lip.license.publications.id=a.id)";
				condition.add(IpUtil.getLongIp(map.get("ip").toString()));
				condition.add(IpUtil.getLongIp(map.get("ip").toString()));
			}
			// 产品本身是OA或免费
			where3 = " a.oa=2 or a.free=2 ";

			if (flag == 0) {
				whereString += " where ";
				if (!"".equals(where1) && !"".equals(where2)) {
					whereString += "(" + where1 + " or " + where2 + " or " + where3 + ")";
				} else if (!"".equals(where1)) {
					whereString += "(" + where1 + " or " + where3 + ")";
				} else if (!"".equals(where2)) {
					whereString += "(" + where2 + " or " + where3 + ")";
				} else {
					whereString += "(" + where3 + ")";
				}
				flag = 1;
			} else {
				whereString += " and ";
				if (!"".equals(where1) && !"".equals(where2)) {
					whereString += "(" + where1 + " or " + where2 + " or " + where3 + ")";
				} else if (!"".equals(where1)) {
					whereString += "(" + where1 + " or " + where3 + ")";
				} else if (!"".equals(where2)) {
					whereString += "(" + where2 + " or " + where3 + ")";
				} else {
					whereString += "(" + where3 + ")";
				}
			}
		}

		if (CollectionsUtil.exist(map, "volumeId") && !"".equals(map.get("volumeId"))) {
			if (flag == 0) {
				whereString += " where a.volume.id = ?";
				flag = 1;
			} else {
				whereString += " and a.volume.id  = ?";
			}
			condition.add(map.get("volumeId").toString());
		}
		if (CollectionsUtil.exist(map, "issueId") && !"".equals(map.get("issueId")) && map.get("issueId") != null) {
			if (flag == 0) {
				whereString += " where a.issue.id = ?";
				flag = 1;
			} else {
				whereString += " and a.issue.id  = ?";
			}
			condition.add(map.get("issueId").toString());
		}
		if (CollectionsUtil.exist(map, "prevPage") && !"".equals(map.get("prevPage"))) {
			if (flag == 0) {
				whereString += " where (a.startPage is not null and a.startPage < ?)";
				flag = 1;
			} else {
				whereString += " and (a.startPage is not null and a.startPage < ?)";
			}
			condition.add(map.get("prevPage"));
		}
		if (CollectionsUtil.exist(map, "nextPage") && !"".equals(map.get("nextPage"))) {
			if (flag == 0) {
				whereString += " where (a.startPage is not null and a.startPage > ?)";
				flag = 1;
			} else {
				whereString += " and (a.startPage is not null and a.startPage > ?)";
			}
			condition.add(map.get("nextPage"));
		}
		if (CollectionsUtil.exist(map, "prevJour") && !"".equals(map.get("prevJour"))) {
			if (flag == 0) {
				whereString += " where (a.month is not null and a.month < ?)";
				flag = 1;
			} else {
				whereString += " and (a.month is not null and a.month < ?)";
			}
			condition.add(map.get("prevJour"));
		}
		if (CollectionsUtil.exist(map, "nextJour") && !"".equals(map.get("nextJour"))) {
			if (flag == 0) {
				whereString += " where (a.month is not null and a.month > ?)";
				flag = 1;
			} else {
				whereString += " and (a.month is not null and a.month > ?)";
			}
			condition.add(map.get("nextJour"));
		}
		if (CollectionsUtil.exist(map, "startTreeCode") && !"".equals(map.get("startTreeCode"))) {
			if (flag == 0) {
				whereString += " where exists(select pcs.publications.id from PCsRelation pcs where pcs.publications.id=a.id and pcs.subject.treeCode like ?)";
				flag = 1;
			} else {
				whereString += " and exists(select pcs.publications.id from PCsRelation pcs where pcs.publications.id=a.id and pcs.subject.treeCode like ?)";
			}
			condition.add(map.get("startTreeCode") + "%");
		}

		if (CollectionsUtil.exist(map, "status") && map.get("status") == null) {
			// 不增加此查询条件
		} else if (CollectionsUtil.exist(map, "status") && !"".equals(map.get("status"))) {
			if (flag == 0) {
				whereString += " where a.status=? ";
				flag = 1;
			} else {
				whereString += " and a.status=? ";
			}
			condition.add(map.get("status"));
		} else {
			if (CollectionsUtil.exist(map, "check") && "false".equals(map.get("check"))) {

			} else {
				if (flag == 0) {
					whereString += " where a.status=2 ";
					flag = 1;
				} else {
					whereString += " and a.status=2 ";
				}
			}
		}

		if (CollectionsUtil.exist(map, "publisheridnotnull") && !"".equals(map.get("publisheridnotnull")) && map.get("publisheridnotnull") != null) {
			if (flag == 0) {
				whereString += " where b.id is not null";
				flag = 1;
			} else {
				whereString += " and b.id is not null";
			}
		}

		/**
		 * 出版年份
		 */
		if (CollectionsUtil.exist(map, "pubYear1") && !"".equals(map.get("pubYear1")) && map.get("pubYear1") != null) {
			if (flag == 0) {
				whereString += " where substring(a.pubDate,0,4) = ?";
				flag = 1;
			} else {
				whereString += " and substring(a.pubDate,0,4)  = ?";
			}
			condition.add(map.get("pubYear1").toString());
			/*
			 * condition.add(DateUtil.getUtilDate(map.get("pubYear1").toString()
			 * , "yyyy"));
			 */
		}
		/**
		 * 出版年份
		 */
		if (CollectionsUtil.exist(map, "pubYear") && !"".equals(map.get("pubYear")) && map.get("pubYear") != null) {
			if (flag == 0) {
				whereString += " where substring(a.pubDate,0,4) = ?";
				flag = 1;
			} else {
				whereString += " and substring(a.pubDate,0,4)  = ?";
			}
			condition.add(map.get("pubYear").toString());
			/*
			 * condition.add(DateUtil.getUtilDate(map.get("pubYear").toString(),
			 * "yyyy"));
			 */

		}
		/**
		 * 年份
		 */
		if (CollectionsUtil.exist(map, "pYear") && !"".equals(map.get("pYear")) && map.get("pYear") != null) {
			if (flag == 0) {
				whereString += " where a.year = ?";
				flag = 1;
			} else {
				whereString += " and a.year = ?";
			}
			condition.add(map.get("pYear").toString());
		}

		/**
		 * 1.publisherid
		 */
		if (CollectionsUtil.exist(map, "publisherid") && !"".equals(map.get("publisherid")) && map.get("publisherid") != null) {
			if (flag == 0) {
				whereString += " where a.publisher.id = ?";
				flag = 1;
			} else {
				whereString += " and a.publisher.id  = ?";
			}
			condition.add(map.get("publisherid").toString());
		}

		/**
		 * 3.parentid
		 */
		if (CollectionsUtil.exist(map, "parentid") && !"".equals(map.get("parentid"))) {
			if (flag == 0) {
				whereString += " where a.publications.id = ?";
				flag = 1;
			} else {
				whereString += " and a.publications.id  = ?";
			}
			condition.add(map.get("parentid").toString());
		}
		/**
		 * 3.parentid
		 */
		if (CollectionsUtil.exist(map, "isParentid") && !"".equals(map.get("isParentid"))) {
			if (flag == 0) {
				whereString += " where a.publications.id = ?";
				flag = 1;
			} else {
				whereString += " and a.publications.id  = ?";
			}
			condition.add(map.get("isParentid").toString());
		}
		/**
		 * 3.cherperid
		 */
		if (CollectionsUtil.exist(map, "cherperid") && !"".equals(map.get("cherperid"))) {
			if (flag == 0) {
				whereString += " where a.id = ?";
				flag = 1;
			} else {
				whereString += " and a.id  = ?";
			}
			condition.add(map.get("cherperid").toString());
		}
		/**
		 * 3.charperIDD
		 */
		if (CollectionsUtil.exist(map, "charperIDD") && !"".equals(map.get("charperIDD"))) {
			if (flag == 0) {
				whereString += " where a.id != ?";
				flag = 1;
			} else {
				whereString += " and a.id  != ?";
			}
			condition.add(map.get("charperIDD").toString());
		}

		/**
		 * 4.homepage
		 */
		if (CollectionsUtil.exist(map, "homepage") && !"".equals(map.get("homepage"))) {
			if (flag == 0) {
				whereString += " where a.homepage = ?";
				flag = 1;
			} else {
				whereString += " and a.homepage = ?";
			}
			condition.add(map.get("homepage"));
		}
		/**
		 * 5.newest
		 */
		if (CollectionsUtil.exist(map, "newest") && !"".equals(map.get("newest"))) {
			if (flag == 0) {
				whereString += " where a.newest = ?";
				flag = 1;
			} else {
				whereString += " and a.newest = ?";
			}
			condition.add(map.get("newest"));
		}
		/**
		 * 6.selected
		 */
		if (CollectionsUtil.exist(map, "selected") && !"".equals(map.get("selected"))) {
			if (flag == 0) {
				whereString += " where a.selected = ?";
				flag = 1;
			} else {
				whereString += " and a.selected = ?";
			}
			condition.add(map.get("selected"));
		}
		/**
		 * 7.special
		 */
		if (CollectionsUtil.exist(map, "special") && !"".equals(map.get("special"))) {
			if (flag == 0) {
				whereString += " where a.special = ?";
				flag = 1;
			} else {
				whereString += " and a.special = ?";
			}
			condition.add(map.get("special"));
		}
		/**
		 * mainType 查询是否是查询子类文件 比如 书籍 下边有章节
		 */
		if (CollectionsUtil.exist(map, "mainType") && !"".equals(map.get("mainType")) && map.get("mainType") != null) {
			if (map.get("mainType").equals("0")) {
				if (flag == 0) {
					whereString += " where a.publications.id is null ";
					flag = 1;
				} else {
					whereString += " and a.publications.id is null ";
				}
			}
			// condition.add(map.get("letter").toString().toLowerCase());
		}

		/**
		 * 资源类型
		 */
		if (CollectionsUtil.exist(map, "type") && !"".equals(map.get("type")) && map.get("type") != null) {
			if (flag == 0) {
				whereString += " where a.type = ?";
				flag = 1;
			} else {
				whereString += " and a.type  = ?";
			}
			condition.add(Integer.parseInt(map.get("type").toString()));
		}
		/**
		 * 海外版资源f=1
		 */
		if (CollectionsUtil.exist(map, "f") && !"".equals(map.get("f")) && map.get("f") != null) {
			if (flag == 0) {
				whereString += " where a.f = ?";
				flag = 1;
			} else {
				whereString += " and a.f  = ?";
			}
			condition.add(Integer.parseInt(map.get("f").toString()));
		}

		if (CollectionsUtil.exist(map, "freeType") && !"".equals(map.get("freeType")) && map.get("freeType") != null) {
			if (flag == 0) {
				whereString += " where a.type <= ?";
				flag = 1;
			} else {
				whereString += " and a.type  <= ?";
			}
			condition.add(Integer.valueOf(map.get("freeType").toString()));
		}
		if (CollectionsUtil.exist(map, "freelang") && !"".equals(map.get("freelang")) && map.get("freelang") != null) {
			if (flag == 0) {
				whereString += " where a.lang like ?";
				flag = 1;
			} else {
				whereString += " and a.lang like ?";
			}
			condition.add(map.get("freelang"));
		}

		if (CollectionsUtil.exist(map, "ppName") && !"".equals(map.get("ppName")) && map.get("ppName") != null) {
			if (flag == 0) {
				whereString += " where b.name = ?";
				flag = 1;
			} else {
				whereString += " and b.name = ?";
			}
			condition.add(map.get("ppName").toString());
		}
		if (CollectionsUtil.exist(map, "pDate") && !"".equals(map.get("pDate")) && map.get("pDate") != null) {
			if (flag == 0) {
				whereString += " where a.pubDate like ?";
				flag = 1;
			} else {
				whereString += " and a.pubDate like ?";
			}
			condition.add("%" + map.get("pDate").toString() + "%");
		}
		/**
		 * 图书类型
		 */
		if (CollectionsUtil.exist(map, "ptype") && !"".equals(map.get("ptype")) && map.get("ptype") != null) {
			if (flag == 0) {
				whereString += " where a.type = ?";
				flag = 1;
			} else {
				whereString += " and a.type  = ?";
			}
			condition.add(Integer.valueOf(map.get("ptype").toString()));
		}

		if (CollectionsUtil.exist(map, "isTrial") && !"".equals(map.get("isTrial")) && map.get("isTrial") != null) {
			if (flag == 0) {
				whereString += " where d.isTrial = ?";
				flag = 1;
			} else {
				whereString += " and d.isTrial  = ?";
			}
			condition.add(Integer.valueOf(map.get("isTrial").toString()));
		}

		/**
		 * 排除图书类型
		 */
		if (CollectionsUtil.exist(map, "dtype") && !"".equals(map.get("dtype")) && map.get("dtype") != null) {
			if (flag == 0) {
				whereString += " where a.type != ?";
				flag = 1;
			} else {
				whereString += " and a.type  != ?";
			}
			condition.add(Integer.valueOf(map.get("dtype").toString()));
		}

		/**
		 * ISBN
		 */
		if (CollectionsUtil.exist(map, "ISBN") && !"".equals(map.get("ISBN"))) {
			if (flag == 0) {
				whereString += " where a.code = ?";
				flag = 1;
			} else {
				whereString += " and a.code  = ?";
			}
			condition.add(map.get("ISBN").toString());
		}

		if (CollectionsUtil.exist(map, "typeArr") && !"".equals(map.get("typeArr"))) {
			Integer[] types = (Integer[]) map.get("typeArr");
			if (types != null && types.length > 0) {
				String where_ = "";
				String _where = ")";
				String where = "";
				if (flag == 0) {
					where_ = " where a.type in (";
					flag = 1;
				} else {
					where_ = " and a.type in (";
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
		 * 2.id
		 */
		if (CollectionsUtil.exist(map, "id") && !"".equals(map.get("id"))) {

			if (flag == 0) {
				whereString += " where a.id = ?";
				flag = 1;
			} else {
				whereString += " and a.id  = ?";
			}
			condition.add(map.get("id").toString());
		}
		if (CollectionsUtil.exist(map, "publicationsesid") && !"".equals(map.get("publicationsesid"))) {

			if (flag == 0) {
				whereString += " where b.id = ?";
				flag = 1;
			} else {
				whereString += " and b.id  = ?";
			}
			condition.add(map.get("publicationsesid").toString());
		}
		if (CollectionsUtil.exist(map, "parentidNew") && !"".equals(map.get("parentidNew"))) {

			if (flag == 0) {
				whereString += " where c.id = ?";
				flag = 1;
			} else {
				whereString += " and c.id  = ?";
			}
			condition.add(map.get("parentidNew").toString());
		}
		if (CollectionsUtil.exist(map, "statusnew") && !"".equals(map.get("statusnew"))) {

			if (flag == 0) {
				whereString += " where a.status = ?";
				flag = 1;
			} else {
				whereString += " and a.status  = ?";
			}
			condition.add(map.get("statusnew"));
		}

		if (CollectionsUtil.exist(map, "pvolumeCode") && !"".equals(map.get("pvolumeCode"))) {

			if (flag == 0) {
				whereString += " where a.volumeCode = ?";
				flag = 1;
			} else {
				whereString += " and a.volumeCode = ?";
			}
			condition.add(map.get("pvolumeCode").toString());
		} // 这是政治原因的条件
		if (CollectionsUtil.exist(map, "available") && !"".equals(map.get("available"))) {// 这是对一个available的条件
			if (flag == 0) {
				whereString += " where ( a.available <> ?  or  a.available is null )  ";
				flag = 1;
			} else {
				whereString += " and  (  a.available <> ?  or  a.available is null ) ";
			}
			condition.add(map.get("available"));
		}

		if (CollectionsUtil.exist(map, "pissueCode") && !"".equals(map.get("pissueCode"))) {

			if (flag == 0) {
				whereString += " where a.issueCode = ?";
				flag = 1;
			} else {
				whereString += " and a.issueCode = ?";
			}
			condition.add(map.get("pissueCode").toString());
		}

		if (CollectionsUtil.exist(map, "pageNum") && !"".equals(map.get("pageNum")) && map.get("pageNum") != null) {

			if (flag == 0) {
				whereString += " where a.startPage = ? ";
				flag = 1;
			} else {
				whereString += " and a.startPage = ? ";
			}
			condition.add(map.get("pageNum"));

		}
		if (CollectionsUtil.exist(map, "free") && !"".equals(map.get("free")) && map.get("free") != null) {

			if (flag == 0) {
				whereString += " where a.free = ? ";
				flag = 1;
			} else {
				whereString += " and a.free = ? ";
			}
			condition.add(map.get("free"));

		}

		if (CollectionsUtil.exist(map, "isOaorFree") && !"".equals(map.get("isOaorFree")) && map.get("isOaorFree") != null) {

			if (flag == 0) {
				whereString += " where ( a.free = ? or a.oa = ? ) ";
				flag = 1;
			} else {
				whereString += " and ( a.free = ? or a.oa = ? )  ";
			}
			condition.add(map.get("isOaorFree"));
			condition.add(map.get("isOaorFree"));

		}
		if (CollectionsUtil.exist(map, "author") && !"".equals(map.get("author")) && map.get("author") != null) {

			if (flag == 0) {
				whereString += " where a.author is not null ";
				flag = 1;
			} else {
				whereString += " and a.author is not null ";
			}
			condition.add(map.get("author"));

		}
		if (CollectionsUtil.exist(map, "publisher") && !"".equals(map.get("publisher")) && map.get("publisher") != null) {

			if (flag == 0) {
				whereString += " where b.name = ? ";
				flag = 1;
			} else {
				whereString += " and b.name = ? ";
			}
			condition.add(map.get("publisher"));

		}

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

		if (CollectionsUtil.exist(map, "oa") && !"".equals(map.get("oa")) && map.get("oa") != null) {

			if (flag == 0) {
				whereString += " where a.oa = ? ";
				flag = 1;
			} else {
				whereString += " and a.oa = ? ";
			}
			condition.add(map.get("oa"));

		}

		// 查询语言（从数据库中查询会用到）
		if (CollectionsUtil.exist(map, "dblang") && !"".equals(map.get("dblang")) && null != map.get("dblang")) {
			if (0 == flag) {
				whereString += " WHERE a.lang = ? ";
				flag = 1;
			} else {
				whereString += " AND a.lang = ? ";
			}
			condition.add(map.get("dblang"));
		}

		// 本地资源（从数据库中查询会用到）
		if (CollectionsUtil.exist(map, "local") && !"".equals(map.get("local")) && null != map.get("local")) {
			if (0 == flag) {
				whereString += " WHERE a.local = ? ";
				flag = 1;
			} else {
				whereString += " AND a.local = ? ";
			}
			condition.add(map.get("local"));
		}

		// 查询时间限制（从数据库中查询会用到）
		if (CollectionsUtil.exist(map, "createOn") && !"".equals(map.get("createOn")) && null != map.get("createOn")) {
			if (0 == flag) {
				whereString += " WHERE a.createOn BETWEEN TO_DATE('" + DateUtil.getBeforeMonthStartDay(new Date()) + "', 'yyyy-MM-dd') AND TO_DATE(?, 'yyyy-MM-dd')";
				flag = 1;
			} else {
				whereString += " AND a.createOn BETWEEN TO_DATE('" + DateUtil.getBeforeMonthStartDay(new Date()) + "', 'yyyy-MM-dd') AND TO_DATE(?, 'yyyy-MM-dd')";
			}
			condition.add(map.get("createOn"));
		}

		// 查询时间限制（从数据库中查询会用到）
		if (CollectionsUtil.exist(map, "createOnNewIndex") && !"".equals(map.get("createOnNewIndex")) && null != map.get("createOnNewIndex")) {
			if (0 == flag) {
				whereString += " WHERE a.createOn IS NOT NULL";
				flag = 1;
			} else {
				whereString += " AND a.createOn IS NOT NULL";
			}
		}

		table.put("where", whereString);
		table.put("condition", condition);
		return table;
	}

	/**
	 * 获取出版物列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getList(Map<String, Object> condition, String sort, CUser user, String ip) throws Exception {
		String sname = condition == null || condition.get("lang") == null || "zh_CN".equals(condition.get("lang").toString()) ? "name" : "nameEn";
		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publisher b left join a.publications c ";
		// 求当前时间前的2周以前的日期
		String targetDate = this.getDistryDate();
		Map<String, Object> t = this.getWhere(condition);
		List<Object> con = new ArrayList<Object>();
		con.add(cn.com.daxtech.framework.util.DateUtil.toDate(this.getDistryDate(), "yyyy-MM-dd"));
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		if (user != null) {
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
		}
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String property = "pubDate,periodicType,journalType,keywords,treecode,order,activity,volume.id,issue.id,year,month,volumeCode,issueCode,chineseTitle,sisbn,hisbn,status,id,publisher.id,publisher.name,title,author,code,listPrice,lcurr,browsePrecent,type" + ",pubSubject,cover,createOn,updateOn,pdf,path,lang,remark,homepage,newest,selected,special,pubDate,startPage,endPage,fileType" + ",oa,free,local,homepage" + ",startVolume" + ",endVolume" + ",inCollection,latest,subscribedIp" + ",publications.id " + ",publications.code " + ",publications.sisbn " + ",publications.hisbn " + ",publications.title " + ",publications.pubSubject ";
		if (user != null) {
			property += ",subscribedUser,exLicense,buyInDetail,favorite";
		}
		property += ",recommand";
		String field = " a.pubDate,a.periodicType,a.journalType,a.keywords,a.treecode,a.order,a.activity,a.volume.id,a.issue.id,a.year,a.month,a.volumeCode,a.issueCode,a.chineseTitle,a.sisbn,a.hisbn,a.status,a.id,b.id,b.name,a.title,a.author,a.code,a.listPrice,a.lcurr,a.browsePrecent,a.type" + ",a.pubSubject,a.cover,a.createOn,a.updateOn,a.pdf,a.path,a.lang,a.remark,a.homepage,a.newest,a.selected,a.special,a.pubDate,a.startPage,a.endPage,a.fileType" + ",a.oa,a.free,a.local,a.homepage" + ",a.startVolume" + ",a.endVolume" +
				// ",(select max(concat(t.year,'-',t.volumeCode)) from
				// PPublications t where t.publications.id=a.id and t.type=6 )"
				// +
				// ",(select distinct l.readUrl from LLicense l where
				// l.publications.id=a.id and l.status=1)"
				// +
				// ",(case when exists(select cc.id from PCcRelation cc where
				// cc.publications.id=a.id and cc.collection.status=2) then '1'
				// else '0' end) as inCollection"
				// +
		",a.inCollection" + ",(case when exists(select pub.id from PPublications pub where pub.createOn >=? and pub.id=a.id) then '1' else '0' end) as isnewst" +
				// ",'0'";
				",(case when exists(select lip.id from LLicenseIp lip where (lip.license.publications.id=a.id or lip.license.publications.code=a.code) and lip.sip<=? and lip.eip>=? and lip.license.status=1) then '1' else '0' end) as IPRange" + ",c.id " + ",c.code " + ",c.sisbn " + ",c.hisbn " + ",c.title " + ",c.pubSubject";
		if (user != null) {
			field += ",(case when exists(select li.id from LLicense li where li.status=1 and li.user.id=? and (li.publications.id=a.id or li.publications.code=a.code)) then '1' else '0' end) as subscribedUser";
			field += ",(case when exists(select ll.id from LLicense ll where ll.status=2 and ll.user.id=? and (ll.publications.id=a.id or ll.publications.code=a.code)) then '1' else '0' end) as exLicense";
			field += ",(case when exists(select detail.id from OOrderDetail detail where detail.status not in (3,10,99) and detail.user.id=? and (detail.price.publications.id=a.id or detail.price.publications.code=a.code)) then '1' else '0' end) as buyInDetail";// 2013-3-13
																																																																		// 当一个用户已购买的产品下架后，同时上架另一个相同ISBN的产品，但是ISBN相同的产品不属于同一个提供商（Source），这个时候新上架的产品不能被购买（该用户已经买过相同ISBN的产品）,期刊、卷、期、文章因为ISSN相同不受此限制
			field += ",(case when exists(select cf.id from CFavourites cf where cf.user.id=? and cf.publications.id=a.id ) then '1' else '0' end) as favorite";
		}
		// field += ",'0'";
		field += ",(case when exists(select ip.id from BIpRange ip where ip.sip <= ? and ip.eip>=?) then '1' else '0' end) as recommend";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, PPublications.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 获取出版物列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getLists(Map<String, Object> condition, String sort) throws Exception {

		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publisher b left join a.publications c ";

		String targetDate = this.getDistryDate();
		Map<String, Object> t = this.getWhere(condition);

		String property = " treecode,order,activity,volume.id,issue.id,year,month,volumeCode,issueCode,chineseTitle,sisbn,hisbn,status,id,publisher.id,publisher.name,title,author,code,listPrice,lcurr,browsePrecent,type" + ",pubSubject,cover,createOn,updateOn,pdf,path,lang,remark,homepage,newest,selected,special,pubDate,startPage,endPage,fileType" + ",oa,free,local,homepage" + ",startVolume" + ",endVolume,publications.id ";

		String field = " a.treecode,a.order,a.activity,a.volume.id,a.issue.id,a.year,a.month,a.volumeCode,a.issueCode,a.chineseTitle,a.sisbn,a.hisbn,a.status,a.id,b.id,b.name,a.title,a.author,a.code,a.listPrice,a.lcurr,a.browsePrecent,a.type" + ",a.pubSubject,a.cover,a.createOn,a.updateOn,a.pdf,a.path,a.lang,a.remark,a.homepage,a.newest,a.selected,a.special,a.pubDate,a.startPage,a.endPage,a.fileType" + ",a.oa,a.free,a.local,a.homepage" + ",a.startVolume" + ",a.endVolume,c.id ";

		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, PPublications.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 获取出版物列表2
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getList2(Map<String, Object> condition, String sort, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publisher b left join a.publications p ";
		Map<String, Object> t = this.getWhere(condition);
		// 求当前时间前的2周以前的日期
		List<Object> con = new ArrayList<Object>();
		con.add(cn.com.daxtech.framework.util.DateUtil.toDate(this.getDistryDate(), "yyyy-MM-dd"));
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		if (user != null) {
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
		}
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String property = "publications.id,publications.title,publisher.id,publisher.name,publisher.nameEn,id,title,chineseTitle,author,code,browsePrecent,type,year,pubDate,remark,pdf,cover,browsePrecent,startPage,endPage" + ",authorAlias,oa,free,local,listPrice,lcurr,volumeCode,issueCode,lang,sisbn,pissn " + ",startVolume" + ",endVolume" + ",inCollection,latest,subscribedIp";
		if (user != null) {
			property += ",subscribedUser,exLicense,buyInDetail,favorite";
		}
		property += ",recommand";// ,buyTimes";
		String field = "p.id,p.title,b.id,b.name,b.nameEn,a.id,a.title,a.chineseTitle,a.author,a.code,a.browsePrecent,a.type,a.year,a.pubDate,a.remark,a.pdf,a.cover,a.browsePrecent,a.startPage,a.endPage" + ",a.author,a.oa,a.free,a.local,a.listPrice,a.lcurr,a.volumeCode,a.issueCode,a.lang,a.sisbn,a.pissn " + ",a.startVolume" + ",a.endVolume" +
				// ",(case when exists(select cc.id from PCcRelation cc where
				// cc.publications.id=a.id and cc.collection.status=2) then '1'
				// else '0' end) as inCollection"
				// +
		",a.inCollection" + ",(case when exists(select pub.id from PPublications pub where pub.createOn >=? and pub.id=a.id) then '1' else '0' end) as isnewst" + ",(case when exists(select lip.id from LLicenseIp lip where (lip.license.publications.id=a.id or lip.license.publications.code=a.code) and lip.sip<=? and lip.eip>=? and lip.license.status=1) then '1' else '0' end) as IPRange";
		if (user != null) {
			field += ",(case when exists(select li.id from LLicense li where li.status=1 and li.user.id=? and (li.publications.id=a.id or li.publications.code=a.code)) then '1' else '0' end) as subscribedUser";
			field += ",(case when exists(select ll.id from LLicense ll where ll.status=2 and ll.user.id=? and (ll.publications.id=a.id or ll.publications.code=a.code)) then '1' else '0' end) as exLicense";
			field += ",(case when exists(select detail.id from OOrderDetail detail where detail.status not in (3,10,99) and detail.user.id=? and (detail.price.publications.id=a.id or detail.price.publications.code=a.code)) then '1' else '0' end) as buyInDetail";// 2013-3-13
																																																																		// 当一个用户已购买的产品下架后，同时上架另一个相同ISBN的产品，但是ISBN相同的产品不属于同一个提供商（Source），这个时候新上架的产品不能被购买（该用户已经买过相同ISBN的产品）,期刊、卷、期、文章因为ISSN相同不受此限制
			field += ",(case when exists(select cf.id from CFavourites cf where cf.user.id=? and cf.publications.id=a.id ) then '1' else '0' end) as favorite";
		}
		field += ",(case when exists(select ip.id from BIpRange ip where ip.sip <= ? and ip.eip>=?) then '1' else '0' end) as recommend";
		// field +=
		// ",(select cast(count(*) as string) from LLicense lll where
		// lll.publications.id=a.id or lll.publications.code=a.code)";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, PPublications.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PPublications> getSubscribedPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a join a.publisher b ";
		// 求当前时间前的2周以前的日期
		Map<String, Object> t = this.getWhere(condition);
		List<Object> con = new ArrayList<Object>();
		Date date = cn.com.daxtech.framework.util.DateUtil.toDate(this.getDistryDate(), "yyyy-MM-dd");
		System.out.println(StringUtil.formatDate(date, "yyyy-MM-dd"));
		con.add(date);
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		if (user != null) {
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
		}
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String property = "volume.id,issue.id,year,month,volumeCode,issueCode,chineseTitle,sisbn,hisbn,status,id,publisher.id,publisher.name,title,author,code,listPrice,lcurr,browsePrecent,type" + ",pubSubject,cover,createOn,updateOn,pdf,path,lang,remark,homepage,newest,selected,special,pubDate,startPage,endPage" + ",oa,free,local" + ",startVolume" + ",endVolume" + ",inCollection,latest,subscribedIp";
		if (user != null) {
			property += ",subscribedUser,exLicense,buyInDetail,favorite";
		}
		property += ",recommand";
		String field = "a.volume.id,a.issue.id,a.year,a.month,a.volumeCode,a.issueCode,a.chineseTitle,a.sisbn,a.hisbn,a.status,a.id,b.id,b.name,a.title,a.author,a.code,a.listPrice,a.lcurr,a.browsePrecent,a.type" + ",a.pubSubject,a.cover,a.createOn,a.updateOn,a.pdf,a.path,a.lang,a.remark,a.homepage,a.newest,a.selected,a.special,a.pubDate,a.startPage,a.endPage" + ",a.oa,a.free,a.local" + ",a.startVolume" + ",a.endVolume" +
				// ",(case when exists(select cc.id from PCcRelation cc where
				// cc.publications.id=a.id and cc.collection.status=2) then '1'
				// else '0' end) as inCollection"
				// +
		",a.inCollection" + ",(case when a.createOn >=? then '1' else '0' end) as isnewst" + ",(case when exists(select lip.id from LLicenseIp lip where (lip.license.publications.id=a.id or lip.license.publications.code=a.code) and lip.sip<=? and lip.eip>=? and lip.license.status=1) then '1' else '0' end) as IPRange";
		if (user != null) {
			field += ",(case when exists(select li.id from LLicense li where li.status=1 and li.user.id=? and (li.publications.id=a.id or li.publications.code=a.code)) then '1' else '0' end) as subscribedUser";
			field += ",(case when exists(select ll.id from LLicense ll where ll.status=2 and ll.user.id=? and (ll.publications.id=a.id or ll.publications.code=a.code)) then '1' else '0' end) as exLicense";
			field += ",(case when exists(select detail.id from OOrderDetail detail where detail.status not in (3,10,99) and detail.user.id=? and (detail.price.publications.id=a.id or detail.price.publications.code=a.code)) then '1' else '0' end) as buyInDetail";// 2013-3-13
																																																																		// 当一个用户已购买的产品下架后，同时上架另一个相同ISBN的产品，但是ISBN相同的产品不属于同一个提供商（Source），这个时候新上架的产品不能被购买（该用户已经买过相同ISBN的产品）,期刊、卷、期、文章因为ISSN相同不受此限制
			field += ",(case when exists(select cf.id from CFavourites cf where cf.user.id=? and cf.publications.id=a.id ) then '1' else '0' end) as favorite";
		}
		field += ",(case when exists(select ip.id from BIpRange ip where ip.sip <= ? and ip.eip>=?) then '1' else '0' end) as recommend";

		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, PPublications.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
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
	public List<PPublications> getPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a join a.publisher b ";
		// 求当前时间前的2周以前的日期
		Map<String, Object> t = this.getWhere(condition);
		List<Object> con = new ArrayList<Object>();
		Date date = cn.com.daxtech.framework.util.DateUtil.toDate(this.getDistryDate(), "yyyy-MM-dd");
		System.out.println(StringUtil.formatDate(date, "yyyy-MM-dd"));
		con.add(date);
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		if (user != null) {
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
		}
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String property = "volume.id,issue.id,year,month,volumeCode,issueCode,chineseTitle,sisbn,hisbn,status,id,publisher.id,publisher.name,title,author,code,listPrice,lcurr,browsePrecent,type" + ",pubSubject,cover,createOn,updateOn,pdf,path,lang,remark,homepage,newest,selected,special,pubDate,startPage,endPage" + ",oa,free,local" + ",startVolume" + ",endVolume" + ",inCollection,latest,subscribedIp";
		if (user != null) {
			property += ",subscribedUser,exLicense,buyInDetail,favorite";
		}
		property += ",recommand";
		String field = "a.volume.id,a.issue.id,a.year,a.month,a.volumeCode,a.issueCode,a.chineseTitle,a.sisbn,a.hisbn,a.status,a.id,b.id,b.name,a.title,a.author,a.code,a.listPrice,a.lcurr,a.browsePrecent,a.type" + ",a.pubSubject,a.cover,a.createOn,a.updateOn,a.pdf,a.path,a.lang,a.remark,a.homepage,a.newest,a.selected,a.special,a.pubDate,a.startPage,a.endPage" + ",a.oa,a.free,a.local" + ",a.startVolume" + ",a.endVolume" +
				// ",(case when exists(select cc.id from PCcRelation cc where
				// cc.publications.id=a.id and cc.collection.status=2) then '1'
				// else '0' end) as inCollection"
				// +
		",a.inCollection" + ",(case when a.createOn >=? then '1' else '0' end) as isnewst" + ",(case when exists(select lip.id from LLicenseIp lip where (lip.license.publications.id=a.id or lip.license.publications.code=a.code) and lip.sip<=? and lip.eip>=? and lip.license.status=1) then '1' else '0' end) as IPRange";
		if (user != null) {
			field += ",(case when exists(select li.id from LLicense li where li.status=1 and li.user.id=? and (li.publications.id=a.id or li.publications.code=a.code)) then '1' else '0' end) as subscribedUser";
			field += ",(case when exists(select ll.id from LLicense ll where ll.status=2 and ll.user.id=? and (ll.publications.id=a.id or ll.publications.code=a.code)) then '1' else '0' end) as exLicense";
			field += ",(case when exists(select detail.id from OOrderDetail detail where detail.status not in (3,10,99) and detail.user.id=? and (detail.price.publications.id=a.id or detail.price.publications.code=a.code)) then '1' else '0' end) as buyInDetail";// 2013-3-13
																																																																		// 当一个用户已购买的产品下架后，同时上架另一个相同ISBN的产品，但是ISBN相同的产品不属于同一个提供商（Source），这个时候新上架的产品不能被购买（该用户已经买过相同ISBN的产品）,期刊、卷、期、文章因为ISSN相同不受此限制
			field += ",(case when exists(select cf.id from CFavourites cf where cf.user.id=? and cf.publications.id=a.id ) then '1' else '0' end) as favorite";
		}
		field += ",(case when exists(select ip.id from BIpRange ip where ip.sip <= ? and ip.eip>=?) then '1' else '0' end) as recommend";

		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, PPublications.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 简单分页列表
	 * 
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @param user
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getSimplePagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publisher b ";
		// 求当前时间前的2周以前的日期
		Map<String, Object> t = this.getWhere(condition);
		List<Object> con = new ArrayList<Object>();
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String property = "f,startVolume,endVolume,pdf,volume.id,issue.id,year,month,volumeCode,issueCode,chineseTitle,sisbn,hisbn,status,id,publisher.id,publisher.name,title,author,code,listPrice,lcurr,type" + ",pubSubject,cover,createOn,updateOn,pdf,path,lang,remark,homepage,newest,selected,special,pubDate,startPage,endPage" + ",oa,free,local,sisbn,hisbn,publisher.id,publisher.browsePrecent,publisher.downloadPercent,publisher.printPercent,publisher.journalBrowse,publisher.journalPrint,publisher.journalDownload";
		String field = "a.f,a.startVolume,a.endVolume,a.pdf,a.volume.id,a.issue.id,a.year,a.month,a.volumeCode,a.issueCode,a.chineseTitle,a.sisbn,a.hisbn,a.status,a.id,b.id,b.name,a.title,a.author,a.code,a.listPrice,a.lcurr,a.type" + ",a.pubSubject,a.cover,a.createOn,a.updateOn,a.pdf,a.path,a.lang,a.remark,a.homepage,a.newest,a.selected,a.special,a.pubDate,a.startPage,a.endPage" + ",a.oa,a.free,a.local,a.sisbn,a.hisbn,b.id,b.browsePrecent,b.downloadPercent,b.printPercent,b.journalBrowse,b.journalPrint,b.journalDownload";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, PPublications.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 简单列表(无分页)
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getSimplePagingList(Map<String, Object> condition, String sort) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publisher b ";
		// 求当前时间前的2周以前的日期
		Map<String, Object> t = this.getWhere(condition);
		List<Object> con = new ArrayList<Object>();
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String property = "startVolume,endVolume,pdf,volume.id,issue.id,year,month,volumeCode,issueCode,chineseTitle,sisbn,hisbn,status,id,publisher.id,publisher.name,title,author,code,listPrice,lcurr,type" + ",pubSubject,cover,createOn,updateOn,pdf,path,lang,remark,homepage,newest,selected,special,pubDate,startPage,endPage" + ",oa,free,local,sisbn,hisbn,publisher.id,publisher.browsePrecent,publisher.downloadPercent,publisher.printPercent,publisher.journalBrowse,publisher.journalPrint,publisher.journalDownload";
		String field = "a.startVolume,a.endVolume,a.pdf,a.volume.id,a.issue.id,a.year,a.month,a.volumeCode,a.issueCode,a.chineseTitle,a.sisbn,a.hisbn,a.status,a.id,b.id,b.name,a.title,a.author,a.code,a.listPrice,a.lcurr,a.type" + ",a.pubSubject,a.cover,a.createOn,a.updateOn,a.pdf,a.path,a.lang,a.remark,a.homepage,a.newest,a.selected,a.special,a.pubDate,a.startPage,a.endPage" + ",a.oa,a.free,a.local,a.sisbn,a.hisbn,b.id,b.browsePrecent,b.downloadPercent,b.printPercent,b.journalBrowse,b.journalPrint,b.journalDownload";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, PPublications.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 获取出版物列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getListIsNew(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {

		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publisher b ";

		Map<String, Object> t = this.getWhere(condition);

		String property = " id,publisher.id,publisher.name,title,oa,free,startVolume,endVolume";

		String field = "a.id,b.id,b.name,a.title,a.oa,a.free,a.startVolume,a.endVolume";

		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, PPublications.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 查询子出版社下的卷，文章
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getpublicationsList(Map<String, Object> condition, String sort) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publications b ";
		// 求当前时间前的2周以前的日期
		Map<String, Object> t = this.getWhere(condition);

		String property = " id,oa,free ";
		String field = " a.id,a.oa,a.free ";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, PPublications.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PPublications> getpublicationsPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer curPage) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publications b ";
		// 求当前时间前的2周以前的日期
		Map<String, Object> t = this.getWhere(condition);
		String property = " id,title,cover ";
		String field = " a.id,a.title,a.cover ";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, PPublications.class.getName(), pageCount, pageCount * curPage);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	private Map<String, Object> getSubscriptionWhere(Map<String, Object> map) {
		Map<String, Object> table = new HashMap<String, Object>();
		String whereString = "";
		List<Object> condition = new ArrayList<Object>();
		int flag = 0;
		/**
		 * 分类法
		 */
		if (CollectionsUtil.exist(map, "subjectId") && !"".equals(map.get("subjectId")) && map.get("subjectId") != null) {
			if (!"0".equals(map.get("subjectId"))) {
				if (flag == 0) {
					whereString += " where exists(select cs1.id from PCsRelation cs1 left join cs1.subject cs2 left join cs1.publications cs3 where cs3.id = d.id and cs2.treeCode like (select cs2.treeCode from BSubject cs2 where cs2.id = ?)||'%') ";
					flag = 1;
				} else {
					whereString += " and exists(select cs1.id from PCsRelation cs1 left join cs1.subject cs2 left join cs1.publications cs3 where cs3.id = d.id and cs2.treeCode like (select cs2.treeCode from BSubject cs2 where cs2.id = ?)||'%') ";
				}
				condition.add(map.get("subjectId").toString());
			}
		}
		/**
		 * 分类法Code
		 */
		if (CollectionsUtil.exist(map, "subjectCode") && !"".equals(map.get("subjectCode")) && map.get("subjectCode") != null) {
			if (flag == 0) {
				whereString += " where exists(select cs1.id from PCsRelation cs1 join cs1.subject cs2 join cs1.publications cs3 where cs3.status=2 and cs3.id = d.id and cs2.treeCode like ?) ";
				flag = 1;
			} else {
				whereString += " and exists(select cs1.id from PCsRelation cs1 join cs1.subject cs2 join cs1.publications cs3 where cs3.status=2 and cs3.id = d.id and cs2.treeCode like ?) ";
			}
			condition.add(map.get("subjectCode").toString() + "%");
		}
		/**
		 * 
		 */
		if (CollectionsUtil.exist(map, "pCode") && !"".equals(map.get("pCode"))) {
			if (flag == 0) {
				whereString += " where d.code like ?";
				flag = 1;
			} else {
				whereString += " and d.code like ?";
			}
			condition.add("%" + map.get("pCode") + "%");
		}
		/**
		 * 1.order A-Z字母排序
		 */
		if (CollectionsUtil.exist(map, "order") && !"".equals(map.get("order"))) {
			if (flag == 0) {
				whereString += " where lower(d.title) like ?";
				flag = 1;
			} else {
				whereString += " and lower(d.title) like ?";
			}
			condition.add(map.get("order").toString().toLowerCase() + "%");
		}

		if (CollectionsUtil.exist(map, "volumeId") && !"".equals(map.get("volumeId"))) {
			if (flag == 0) {
				whereString += " where d.volume.id = ?";
				flag = 1;
			} else {
				whereString += " and d.volume.id  = ?";
			}
			condition.add(map.get("volumeId").toString());
		}
		if (CollectionsUtil.exist(map, "issueId") && !"".equals(map.get("issueId"))) {
			if (flag == 0) {
				whereString += " where d.issue.id = ?";
				flag = 1;
			} else {
				whereString += " and d.issue.id  = ?";
			}
			condition.add(map.get("issueId").toString());
		}
		if (CollectionsUtil.exist(map, "prevPage") && !"".equals(map.get("prevPage"))) {
			if (flag == 0) {
				whereString += " where (d.startPage is not null and d.startPage < ?)";
				flag = 1;
			} else {
				whereString += " and (d.startPage is not null and d.startPage < ?)";
			}
			condition.add(map.get("prevPage"));
		}
		if (CollectionsUtil.exist(map, "nextPage") && !"".equals(map.get("nextPage"))) {
			if (flag == 0) {
				whereString += " where (d.startPage is not null and d.startPage > ?)";
				flag = 1;
			} else {
				whereString += " and (d.startPage is not null and d.startPage > ?)";
			}
			condition.add(map.get("nextPage"));
		}
		if (CollectionsUtil.exist(map, "startTreeCode") && !"".equals(map.get("startTreeCode"))) {
			if (flag == 0) {
				whereString += " where exists(select pcs.publications.id from PCsRelation pcs where pcs.publications.id=d.id and pcs.subject.treeCode like ?)";
				flag = 1;
			} else {
				whereString += " and exists(select pcs.publications.id from PCsRelation pcs where pcs.publications.id=d.id and pcs.subject.treeCode like ?)";
			}
			condition.add(map.get("startTreeCode") + "%");
		}

		if (CollectionsUtil.exist(map, "status") && map.get("status") == null) {
			// 不增加此查询条件
		} else if (CollectionsUtil.exist(map, "status") && !"".equals(map.get("status"))) {
			if (flag == 0) {
				whereString += " where d.status=? ";
				flag = 1;
			} else {
				whereString += " and d.status=? ";
			}
			condition.add(map.get("status"));
		} else {
			if (CollectionsUtil.exist(map, "check") && "false".equals(map.get("check"))) {

			} else {
				if (flag == 0) {
					whereString += " where d.status=2 ";
					flag = 1;
				} else {
					whereString += " and d.status=2 ";
				}
			}
		}
		/**
		 * 出版年份
		 */
		if (CollectionsUtil.exist(map, "pubYear") && !"".equals(map.get("pubYear")) && map.get("pubYear") != null) {
			if (flag == 0) {
				whereString += " where substring(d.pubDate,0,4) = ?";
				flag = 1;
			} else {
				whereString += " and substring(d.pubDate,0,4)  = ?";
			}
			condition.add(map.get("pubYear").toString());
			/*
			 * condition.add(DateUtil.getUtilDate(map.get("pubYear").toString(),
			 * "yyyy"));
			 */

		}
		/**
		 * 1.publisherid
		 */
		if (CollectionsUtil.exist(map, "publisherid") && !"".equals(map.get("publisherid")) && map.get("publisherid") != null) {
			if (flag == 0) {
				whereString += " where d.publisher.id = ?";
				flag = 1;
			} else {
				whereString += " and d.publisher.id  = ?";
			}
			condition.add(map.get("publisherid").toString());
		}

		/**
		 * 3.parentid
		 */
		if (CollectionsUtil.exist(map, "parentid") && !"".equals(map.get("parentid"))) {
			if (flag == 0) {
				whereString += " where d.publications.id = ?";
				flag = 1;
			} else {
				whereString += " and d.publications.id  = ?";
			}
			condition.add(map.get("parentid").toString());
		}
		/**
		 * 4.homepage
		 */
		if (CollectionsUtil.exist(map, "homepage") && !"".equals(map.get("homepage"))) {
			if (flag == 0) {
				whereString += " where d.homepage = ?";
				flag = 1;
			} else {
				whereString += " and d.homepage = ?";
			}
			condition.add(map.get("homepage"));
		}
		/**
		 * 5.newest
		 */
		if (CollectionsUtil.exist(map, "newest") && !"".equals(map.get("newest"))) {
			if (flag == 0) {
				whereString += " where d.newest = ?";
				flag = 1;
			} else {
				whereString += " and d.newest = ?";
			}
			condition.add(map.get("newest"));
		}
		/**
		 * 6.selected
		 */
		if (CollectionsUtil.exist(map, "selected") && !"".equals(map.get("selected"))) {
			if (flag == 0) {
				whereString += " where d.selected = ?";
				flag = 1;
			} else {
				whereString += " and d.selected = ?";
			}
			condition.add(map.get("selected"));
		}
		/**
		 * 7.special
		 */
		if (CollectionsUtil.exist(map, "special") && !"".equals(map.get("special"))) {
			if (flag == 0) {
				whereString += " where d.special = ?";
				flag = 1;
			} else {
				whereString += " and d.special = ?";
			}
			condition.add(map.get("special"));
		}
		/**
		 * mainType 查询是否是查询子类文件 比如 书籍 下边有章节
		 */
		if (CollectionsUtil.exist(map, "mainType") && !"".equals(map.get("mainType"))) {
			if (map.get("mainType").equals("0")) {
				if (flag == 0) {
					whereString += " where d.publications.id is null ";
					flag = 1;
				} else {
					whereString += " and d.publications.id is null ";
				}
			}
			// condition.add(map.get("letter").toString().toLowerCase());
		}

		/**
		 * 图书类型
		 */
		if (CollectionsUtil.exist(map, "type") && !"".equals(map.get("type")) && map.get("type") != null) {
			if (flag == 0) {
				whereString += " where d.type = ?";
				flag = 1;
			} else {
				whereString += " and d.type  = ?";
			}
			condition.add(Integer.valueOf(map.get("type").toString()));
		}
		/**
		 * 图书类型
		 */
		if (CollectionsUtil.exist(map, "ptype") && !"".equals(map.get("ptype")) && map.get("ptype") != null) {
			if (flag == 0) {
				whereString += " where d.type = ?";
				flag = 1;
			} else {
				whereString += " and d.type  = ?";
			}
			condition.add(Integer.valueOf(map.get("ptype").toString()));
		}

		/**
		 * ISBN
		 */
		if (CollectionsUtil.exist(map, "ISBN") && !"".equals(map.get("ISBN"))) {
			if (flag == 0) {
				whereString += " where d.code = ?";
				flag = 1;
			} else {
				whereString += " and d.code  = ?";
			}
			condition.add(map.get("ISBN").toString());
		}

		if (CollectionsUtil.exist(map, "typeArr") && !"".equals(map.get("typeArr"))) {
			Integer[] types = (Integer[]) map.get("typeArr");
			if (types != null && types.length > 0) {
				String where_ = "";
				String _where = ")";
				String where = "";
				if (flag == 0) {
					where_ = " where d.type in (";
					flag = 1;
				} else {
					where_ = " and d.type in (";
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
		 * 2.id
		 */
		if (CollectionsUtil.exist(map, "id") && !"".equals(map.get("id"))) {
			if (flag == 0) {
				whereString += " where d.id = ?";
				flag = 1;
			} else {
				whereString += " and d.id  = ?";
			}
			condition.add(map.get("id").toString());
		}

		/**
		 * 2.licenseStatus
		 */
		if (CollectionsUtil.exist(map, "licenseStatus") && !"".equals(map.get("licenseStatus")) && map.get("licenseStatus") != null) {
			if (flag == 0) {
				whereString += " where a.status = ?";
				flag = 1;
			} else {
				whereString += " and a.status  = ?";
			}
			condition.add(map.get("licenseStatus"));
		}

		if (CollectionsUtil.exist(map, "userId") && !"".equals(map.get("userId")) && map.get("userId") != null && CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId")) && map.get("institutionId") != null && CollectionsUtil.exist(map, "oafreeUid") && !"".equals(map.get("oafreeUid")) && map.get("oafreeUid") != null) {
			if (flag == 0) {
				whereString += " where (b.id in ( ?,?) or ( c.id = ? and b.level = ? ))";
				flag = 1;
			} else {
				whereString += " and (b.id in ( ?,?) or ( c.id = ? and b.level = ? ))";
			}
			condition.add(map.get("userId"));
			condition.add(map.get("oafreeUid"));
			condition.add(map.get("institutionId"));
			condition.add(map.get("level"));
		} else if (CollectionsUtil.exist(map, "userId") && !"".equals(map.get("userId")) && map.get("userId") != null && CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId")) && map.get("institutionId") != null) {
			if (flag == 0) {
				whereString += " where (b.id = ? or ( c.id = ? and b.level = ? ))";
				flag = 1;
			} else {
				whereString += " and (b.id = ? or ( c.id = ? and b.level = ? ))";
			}
			condition.add(map.get("userId"));
			condition.add(map.get("institutionId"));
			condition.add(map.get("level"));
		} else if (CollectionsUtil.exist(map, "oafreeUid") && !"".equals(map.get("oafreeUid")) && map.get("oafreeUid") != null && CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId")) && map.get("institutionId") != null) {
			if (flag == 0) {
				whereString += " where (b.id = ? or ( c.id = ? and b.level = ? ))";
				flag = 1;
			} else {
				whereString += " and (b.id = ? or ( c.id = ? and b.level = ? ))";
			}
			condition.add(map.get("oafreeUid"));
			condition.add(map.get("institutionId"));
			condition.add(map.get("level"));
		} else if (CollectionsUtil.exist(map, "oafreeUid") && !"".equals(map.get("oafreeUid")) && map.get("oafreeUid") != null && CollectionsUtil.exist(map, "userId") && !"".equals(map.get("userId")) && map.get("userId") != null) {
			if (flag == 0) {
				whereString += " where b.id in (?,?) ";
				flag = 1;
			} else {
				whereString += " and b.id in (?,?) ";
			}
			condition.add(map.get("oafreeUid"));
			condition.add(map.get("userId"));
		} else if (CollectionsUtil.exist(map, "userId") && !"".equals(map.get("userId")) && map.get("userId") != null) {
			if (flag == 0) {
				whereString += " where b.id = ?";
				flag = 1;
			} else {
				whereString += " and b.id = ?";
			}
			condition.add(map.get("userId"));
		} else if (CollectionsUtil.exist(map, "oafreeUid") && !"".equals(map.get("oafreeUid")) && map.get("oafreeUid") != null) {
			if (flag == 0) {
				whereString += " where b.id = ?";
				flag = 1;
			} else {
				whereString += " and b.id = ?";
			}
			condition.add(map.get("oafreeUid"));
		} else if (CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId")) && map.get("institutionId") != null) {
			if (flag == 0) {
				whereString += " where (c.id = ? and b.level = ?) ";
				flag = 1;
			} else {
				whereString += " and (c.id = ? and b.level = ?) ";
			}

			condition.add(map.get("institutionId"));
			condition.add(map.get("level"));
		} else {
			if (flag == 0) {
				whereString += " where c.id = '0' ";
				flag = 1;
			} else {
				whereString += " and c.id = '0' ";
			}

		}
		if (CollectionsUtil.exist(map, "isCn") && !"".equals(map.get("isCn"))) {
			Boolean isCn = map.get("isCn") != null && "true".equals(map.get("isCn").toString().toLowerCase());
			Object[] types = new Object[] { "chs", "chn", "cht" };
			String where_ = "";
			String _where = ")";
			String where = "";
			if (flag == 0) {
				where_ = " where c.lang ";
				flag = 1;
			} else {
				where_ = " and c.lang ";
			}
			where_ += isCn ? " in( " : " not in ( ";
			for (int i = 0; i < types.length; i++) {
				where += "?";
				if (i < types.length - 1) {
					where += ",";
				}
				condition.add(types[i]);
			}
			whereString += where_ + where + _where;
		}
		table.put("where", whereString);
		table.put("condition", condition);
		return table;
	}

	@SuppressWarnings("unchecked")
	public List<PPublications> getPagingList2(Map<String, Object> condition, String sort, Integer pageCount, Integer page, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publisher b left join a.publications p ";
		// 求当前时间前的2周以前的日期
		String targetDate = this.getDistryDate();
		Map<String, Object> t = this.getWhere(condition);
		List<Object> con = new ArrayList<Object>();
		con.add(cn.com.daxtech.framework.util.DateUtil.toDate(this.getDistryDate(), "yyyy-MM-dd"));
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		if (user != null) {
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
		}
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String property = "publications.id,publications.title,publisher.id,publisher.name,publisher.nameEn,id,title,chineseTitle,author,code,browsePrecent,type,year,pubDate,remark,pdf,cover,browsePrecent,startPage,endPage" + ",authorAlias,oa,free,local" + ",startVolume" + ",endVolume" + ",inCollection,latest,subscribedIp";
		if (user != null) {
			property += ",subscribedUser,exLicense,buyInDetail,favorite";
		}
		property += ",recommand,buyTimes";
		String field = "p.id,p.title,b.id,b.name,b.nameEn,a.id,a.title,a.chineseTitle,a.author,a.code,a.browsePrecent,a.type,a.year,a.pubDate,a.remark,a.pdf,a.cover,a.browsePrecent,a.startPage,a.endPage" + ",a.author,a.oa,a.free,a.local" + ",a.startVolume" + ",a.endVolume" +
				// ",(select cast(count(*) as string) from PCcRelation cc where
				// cc.publications.id=a.id and cc.collection.status=2)"
				// +
		",a.inCollection" + ",(select cast(count(*) as string) from PPublications pub where pub.createOn>=? and pub.id=a.id)" + ",(select cast(count(*) as string) from LLicenseIp lip where lip.license.status=1 and lip.sip<=? and lip.eip>=? and ((lip.license.publications.type not in (2,4,6,7) and (lip.license.publications.id=a.id or lip.license.publications.code=a.code)) or (lip.license.publications.type in (2,4,6,7) and (lip.license.publications.id=a.id or lip.license.publications.id=a.publications.id or lip.license.publications.id=a.volume.id or lip.license.publications.id=a.issue.id))))";
		if (user != null) {
			field += ",(select cast(count(*) as string) from LLicense li where li.status=1 and li.user.id=? and ((li.publications.type not in (2,4,6,7) and (li.publications.id=a.id or li.publications.code=a.code)) or (li.publications.type in (2,4,6,7) and (li.publications.id=a.id or li.publications.id=a.publications.id or li.publications.id=a.volume.id or li.publications.id=a.issue.id))))";
			field += ",(select cast(count(*) as string) from LLicense ll where ll.status=2 and ll.user.id=? and ((ll.publications.type not in (2,4,6,7) and (ll.publications.id=a.id or ll.publications.code=a.code)) or (ll.publications.type in (2,4,6,7) and (ll.publications.id=a.id or ll.publications.id=a.publications.id or ll.publications.id=a.volume.id or ll.publications.id=a.issue.id))))";
			field += ",(select cast(count(*) as string) from OOrderDetail detail where detail.status not in (3,10,99) and detail.user.id=? and ((detail.price.publications.type not in (2,4,6,7) and (detail.price.publications.id=a.id or detail.price.publications.code=a.code)) or (detail.price.publications.type in (2,4,6,7) and (detail.price.publications.id=a.id or detail.price.publications.id=a.publications.id or detail.price.publications.id=a.volume.id or detail.price.publications.id=a.issue.id))))";// 2013-3-13
																																																																																																																														// 当一个用户已购买的产品下架后，同时上架另一个相同ISBN的产品，但是ISBN相同的产品不属于同一个提供商（Source），这个时候新上架的产品不能被购买（该用户已经买过相同ISBN的产品）,期刊、卷、期、文章因为ISSN相同不受此限制
			field += ",(select cast(count(*) as string) from CFavourites cf where cf.user.id=? and cf.publications.id=a.id )";
		}
		field += ",(select cast(count(*) as string) from BIpRange ip where ip.sip <= ? and ip.eip>=?)";
		field += ",(select cast(count(*) as string) from LLicense lll where lll.publications.id=a.id or lll.publications.code=a.code)";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, PPublications.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PPublications> getPagingList3(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<PPublications> list = null;
		Map<String, Object> t = this.getWhere(condition);
		String property = " id,title,author,publisher.name,publisher.nameEn ";
		String field = " a.id,a.title,a.author,b.name,b.nameEn ";
		String hql = " from PPublications a left join a.publisher b ";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, PPublications.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	/**
	 * 获取已订阅分页信息
	 * 
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getSubscriptionPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		Map<String, Object> t = this.getSubscriptionWhere(condition);
		List<Object> con = new ArrayList<Object>();
		con.add(cn.com.daxtech.framework.util.DateUtil.toDate(this.getDistryDate(), "yyyy-MM-dd"));
		if (user != null) {
			con.add(user.getId());
		}
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String hql = " from LLicense a left join a.user b left join b.institution c left join a.publications d ";

		String property = "volume.id,issue.id,year,month,volumeCode,issueCode,chineseTitle,sisbn,hisbn,status,id,publisher.id,publisher.name,title,author,code,listPrice,lcurr,browsePrecent,type" + ",pubSubject,cover,createOn,updateOn,pdf,path,lang,remark,homepage,newest,selected,special,pubDate,startPage,endPage" + ",oa,free,local" + ",startVolume" + ",endVolume" + ",inCollection,latest" + ",subscribedIp";
		if (user != null) {
			property += ",subscribedUser,exLicense,buyInDetail,favorite";
		}
		property += ",recommand";
		String field = "d.volume.id,d.issue.id,d.year,d.month,d.volumeCode,d.issueCode,d.chineseTitle,d.sisbn,d.hisbn,d.status,d.id,d.publisher.id,d.publisher.name,d.title,d.author,d.code,d.listPrice,d.lcurr,d.browsePrecent,d.type" + ",d.pubSubject,d.cover,d.createOn,d.updateOn,d.pdf,d.path,d.lang,d.remark,d.homepage,d.newest,d.selected,d.special,d.pubDate,d.startPage,d.endPage" + ",d.oa,d.free,d.local" + ",d.startVolume" + ",d.endVolume" + ",d.inCollection" + ",(case when d.createOn >=? then '1' else '0' end) as isnewst" +
				// ",'0'";
				",'1' as IPRange";
		if (user != null) {
			field += ",'1' as subscribedUser";
			field += ",'1' as exLicense";
			field += ",'1' as buyInDetail";// 2013-3-13
											// 当一个用户已购买的产品下架后，同时上架另一个相同ISBN的产品，但是ISBN相同的产品不属于同一个提供商（Source），这个时候新上架的产品不能被购买（该用户已经买过相同ISBN的产品）,期刊、卷、期、文章因为ISSN相同不受此限制
			field += ",(case when exists(select cf.id from CFavourites cf where cf.user.id=? and cf.publications.id=d.id ) then '1' else '0' end) as favorite";
		}
		// field += ",'0'";
		field += ",(case when exists(select ip.id from BIpRange ip where ip.sip <= ? and ip.eip>=?) then '1' else '0' end) as recommend";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, PPublications.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 获取订阅总数
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer getSubscriptionCount(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		Map<String, Object> t = this.getSubscriptionWhere(condition);
		String hql = " from LLicense a left join a.user b left join b.institution c left join a.publications d ";
		try {
			list = this.hibernateDao.getListByHql("id", "cast(count(*) as string)", hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), "", PPublications.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list == null ? 0 : Integer.valueOf(list.get(0).getId());
	}

	/**
	 * 简单试用资源列表(无分页)
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getTrialList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<PPublications> list = null;
		// String hql = " from PPublications a left join a.publisher b ";
		String hql = " from LLicense a left join a.user.institution c left join a.publications d left join d.publisher e";
		// 求当前时间前的2周以前的日期
		Map<String, Object> t = this.getTrialWhere(condition);
		List<Object> con = new ArrayList<Object>();
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String property = "startVolume,endVolume,pdf,volume.id,issue.id,year,month,volumeCode,issueCode,chineseTitle,sisbn,hisbn,status,id,publisher.id,publisher.name,title,author,code,listPrice,lcurr,type" + ",pubSubject,cover,createOn,updateOn,pdf,path,lang,remark,homepage,newest,selected,special,pubDate,startPage,endPage" + ",oa,free,local,sisbn,hisbn,publisher.id,publisher.browsePrecent,publisher.downloadPercent,publisher.printPercent,publisher.journalBrowse,publisher.journalPrint,publisher.journalDownload";
		String field = "d.startVolume,d.endVolume,d.pdf,d.volume.id,d.issue.id,d.year,d.month,d.volumeCode,d.issueCode,d.chineseTitle,d.sisbn,d.hisbn,d.status,d.id,e.id,e.name,d.title,d.author,d.code,d.listPrice,d.lcurr,d.type" + ",d.pubSubject,d.cover,d.createOn,d.updateOn,d.pdf,d.path,d.lang,d.remark,d.homepage,d.newest,d.selected,d.special,d.pubDate,d.startPage,d.endPage" + ",d.oa,d.free,d.local,d.sisbn,d.hisbn,e.id,e.browsePrecent,e.downloadPercent,e.printPercent,e.journalBrowse,e.journalPrint,e.journalDownload";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, PPublications.class.getName(), pageCount, pageCount * page);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 简单试用资源列表(无分页)
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getTrialList(Map<String, Object> condition, String sort) throws Exception {
		List<PPublications> list = null;
		// String hql = " from PPublications a left join a.publisher b ";
		String hql = " from LLicense a left join a.user.institution c left join a.publications d left join d.publisher e";
		// 求当前时间前的2周以前的日期
		Map<String, Object> t = this.getTrialWhere(condition);
		List<Object> con = new ArrayList<Object>();
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String property = "startVolume,endVolume,pdf,volume.id,issue.id,year,month,volumeCode,issueCode,chineseTitle,sisbn,hisbn,status,id,publisher.id,publisher.name,title,author,code,listPrice,lcurr,type" + ",pubSubject,cover,createOn,updateOn,pdf,path,lang,remark,homepage,newest,selected,special,pubDate,startPage,endPage" + ",oa,free,local,sisbn,hisbn,publisher.id,publisher.browsePrecent,publisher.downloadPercent,publisher.printPercent,publisher.journalBrowse,publisher.journalPrint,publisher.journalDownload";
		String field = "d.startVolume,d.endVolume,d.pdf,d.volume.id,d.issue.id,d.year,d.month,d.volumeCode,d.issueCode,d.chineseTitle,d.sisbn,d.hisbn,d.status,d.id,e.id,e.name,d.title,d.author,d.code,d.listPrice,d.lcurr,d.type" + ",d.pubSubject,d.cover,d.createOn,d.updateOn,d.pdf,d.path,d.lang,d.remark,d.homepage,d.newest,d.selected,d.special,d.pubDate,d.startPage,d.endPage" + ",d.oa,d.free,d.local,d.sisbn,d.hisbn,e.id,e.browsePrecent,e.downloadPercent,e.printPercent,e.journalBrowse,e.journalPrint,e.journalDownload";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, PPublications.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 获取订阅试用资源
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer getTrialCount(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		Map<String, Object> t = this.getTrialWhere(condition);
		String hql = " from LLicense a left join a.user.institution c left join a.publications d left join d.publisher e";
		try {
			list = this.hibernateDao.getListByHql("id", "cast(count(*) as string)", hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), "", PPublications.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list == null ? 0 : Integer.valueOf(list.get(0).getId());
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
		List<PPublications> list = null;
		Map<String, Object> t = this.getWhere(condition);
		String hql = " from PPublications a left join a.publisher b";
		try {
			list = this.hibernateDao.getListByHql("id", "cast(count(*) as string)", hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), "", PPublications.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list == null ? 0 : Integer.valueOf(list.get(0).getId());
	}

	/**
	 * 获取总数(优化查询)
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer getCountO1(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		Map<String, Object> t = this.getWhere(condition);
		String hql = " from PPublications a join a.publisher b";
		try {
			list = this.hibernateDao.getListByHql("id", "cast(count(*) as string)", hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), "", PPublications.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list == null ? 0 : Integer.valueOf(list.get(0).getId());
	}

	@SuppressWarnings("unchecked")
	public List<PPublications> getCountPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a ";
		Map<String, Object> t = this.getWhere(condition);
		String property = "id,title";
		String field = "cast(count(*) as string),a.createDate";
		try {
			list = this.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, PPublications.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public void deleteByCondition(Map<String, Object> condition) throws Exception {
		String hql = " delete from PPublications a ";
		Map<String, Object> t = this.getWhere(condition);
		try {
			this.hibernateDao.executeHql(hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray());
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public List<PPublications> getTypeStatistic(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a ";
		Map<String, Object> t = this.getWhere(condition);
		String property = "type,id";
		String field = "a.type,cast(count(*) as string) as num";
		try {
			list = this.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by a.type ", PPublications.class.getName(), 5, 0);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PPublications> getYearStatistic(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a ";
		Map<String, Object> t = this.getWhere(condition);
		String property = "pubDate,id";
		String field = "substring(a.pubDate,1,4) as pubDate,cast(count(*) as string) as num";
		try {
			list = this.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by substring(a.pubDate,1,4) order by substring(a.pubDate,1,4) desc ", PPublications.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PPublications> getPublisherStatistic(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a ";
		Map<String, Object> t = this.getWhere(condition);
		String property = "publisher.name,id,publisher.id,startPage,inCollection";
		String field = "(select c.name from BPublisher c where c.id=a.publisher.id) as publisher" + ",cast(count(*) as string) as num,a.publisher.id" + ",(select length(c.name) from BPublisher c where c.id=a.publisher.id) as pub_len" + ",length(cast(count(*) as string)) as nm_len";
		try {
			list = this.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by a.publisher.id order by publisher", PPublications.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PPublications> getSubscriptionTypeStatistic(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		Map<String, Object> t = this.getSubscriptionWhere(condition);
		String hql = " from LLicense a left join a.user b left join b.institution c left join a.publications d ";
		String property = "type,id";
		String field = "d.type,cast(count(*) as string) as num";
		try {
			list = this.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by d.type ", PPublications.class.getName(), 5, 0);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PPublications> getSubscriptionYearStatistic(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		Map<String, Object> t = this.getSubscriptionWhere(condition);
		String hql = " from LLicense a left join a.user b left join b.institution c left join a.publications d ";
		String property = "pubDate,id";
		String field = "substring(d.pubDate,1,4) as pubDate,cast(count(*) as string) as num";
		try {
			list = this.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by substring(d.pubDate,1,4) order by substring(d.pubDate,1,4) desc ", PPublications.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PPublications> getSubscriptionPublisherStatistic(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		Map<String, Object> t = this.getSubscriptionWhere(condition);
		String hql = " from LLicense a left join a.user b left join b.institution c left join a.publications d ";
		String property = "publisher.name,id,publisher.id,startPage,inCollection";
		String field = "(select c.name from BPublisher c where c.id=d.publisher.id) as publisher" + ",cast(count(*) as string) as num,d.publisher.id" + ",(select length(c.name) from BPublisher c where c.id=d.publisher.id) as pub_len" + ",length(cast(count(*) as string)) as nm_len";
		try {
			list = this.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " group by d.publisher.id order by publisher", PPublications.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PPublications> getPPublicationsByISBN(String isbn) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a ";
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("ISBN", isbn);
		condition.put("status", 2);
		Map<String, Object> t = this.getWhere(condition);
		String property = "chineseTitle,id,title,type,code,pubSubject";
		String field = "a.chineseTitle,a.id,a.title,a.type,a.code,a.pubSubject";
		try {
			list = this.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), null, PPublications.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public PPublications findById(String id) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publisher b ";
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("id", id);
		condition.put("status", 2);
		Map<String, Object> t = this.getWhere(condition);
		String property = "chineseTitle,id,title,type,code,pubSubject, lang, pubDate, publisher.name";
		String field = "a.chineseTitle,a.id,a.title,a.type,a.code,a.pubSubject, a.lang, a.pubDate, b.name";
		try {
			list = this.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), null, PPublications.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return null != list && !list.isEmpty() ? list.get(0) : null;
	}

	public PPublications findByIdNew(String id) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publisher b left join a.publications c";
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("id", id);
		condition.put("status", 2);
		Map<String, Object> t = this.getWhere(condition);
		String property = "chineseTitle,id,title,type,code,pubSubject, lang, pubDate,year,volumeCode,issueCode,startPage,endPage,publisher.name,publications.id,publications.title";
		String field = "a.chineseTitle,a.id,a.title,a.type,a.code,a.pubSubject, a.lang, a.pubDate,a.year,a.volumeCode,a.issueCode,a.startPage,a.endPage,b.name,c.id, c.title";
		try {
			list = this.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), null, PPublications.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return null != list && !list.isEmpty() ? list.get(0) : null;
	}

	/**
	 * 获取热门出版物列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getHots(Map<String, Object> condition, String sort, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publisher b  ";
		Map<String, Object> t = this.getWhere(condition);
		// 求当前时间前的2周以前的日期
		List<Object> con = new ArrayList<Object>();
		con.add(cn.com.daxtech.framework.util.DateUtil.toDate(this.getDistryDate(), "yyyy-MM-dd"));
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		if (user != null) {
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
		}
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String property = "authorAlias,chineseTitle,pubSubjectEn,sisbn,hisbn,status,publisher.name,id,publisher.id,publisher.name,title,author,code,listPrice,lcurr,browsePrecent,type" + ",pubSubject,cover,createOn,updateOn,pdf,path,lang,remark,homepage,newest,selected,special,pubDate,startPage,endPage" + ",oa,free,local" +
				// ",allSubjectName" +
				",inCollection,latest,subscribedIp";
		if (user != null) {
			property += ",subscribedUser,exLicense,buyInDetail,favorite";
		}
		property += ",recommand,buyTimes";
		String field = "a.author,a.chineseTitle,a.pubSubjectEn,a.sisbn,a.hisbn,a.status,b.name,a.id,b.id,b.name,a.title,a.author,a.code,a.listPrice,a.lcurr,a.browsePrecent,a.type" + ",a.pubSubject,a.cover,a.createOn,a.updateOn,a.pdf,a.path,a.lang,a.remark,a.homepage,a.newest,a.selected,a.special,a.pubDate,a.startPage,a.endPage" + ",a.oa,a.free,a.local" +
				// ",(select cast(group_concat(cs.subject."+ sname +
				// ") as string) from PCsRelation cs where
				// cs.publications.id=a.id) "
				// +
				// ",(select cast(count(*) as string) from PCcRelation cc where
				// cc.publications.id=a.id and cc.collection.status=2)"
				// +
		",a.inCollection" + ",(select cast(count(*) as string) from PPublications pub where pub.createOn>=? and pub.id=a.id)" + ",(select cast(count(*) as string) from LLicenseIp lip where lip.license.status=1 and lip.sip<=? and lip.eip>=? and ((lip.license.publications.type not in (2,4,6,7) and (lip.license.publications.id=a.id or lip.license.publications.code=a.code)) or (lip.license.publications.type in (2,4,6,7) and (lip.license.publications.id=a.id or lip.license.publications.publications.id=a.id or lip.license.publications.volume.id=a.id or lip.license.publications.issue.id=a.id))))";
		if (user != null) {
			field += ",(select cast(count(*) as string) from LLicense li where li.status=1 and li.user.id=? and ((li.publications.type not in (2,4,6,7) and (li.publications.id=a.id or li.publications.code=a.code)) or (li.publications.type in (2,4,6,7) and (li.publications.id=a.id or li.publications.publications.id=a.id or li.publications.volume.id=a.id or li.publications.issue.id=a.id))))";
			field += ",(select cast(count(*) as string) from LLicense ll where ll.status=2 and ll.user.id=? and ((ll.publications.type not in (2,4,6,7) and (ll.publications.id=a.id or ll.publications.code=a.code)) or (ll.publications.type in (2,4,6,7) and (ll.publications.id=a.id or ll.publications.publications.id=a.id or ll.publications.volume.id=a.id or ll.publications.issue.id=a.id))))";
			field += ",(select cast(count(*) as string) from OOrderDetail detail where detail.status not in (3,10,99) and detail.user.id=? and ((detail.price.publications.type not in (2,4,6,7) and (detail.price.publications.id=a.id or detail.price.publications.code=a.code)) or (detail.price.publications.type in (2,4,6,7) and (detail.price.publications.id=a.id or detail.price.publications.publications.id=a.id or detail.price.publications.volume.id=a.id or detail.price.publications.issue.id=a.id))))";// 2013-3-13
																																																																																																																														// 当一个用户已购买的产品下架后，同时上架另一个相同ISBN的产品，但是ISBN相同的产品不属于同一个提供商（Source），这个时候新上架的产品不能被购买（该用户已经买过相同ISBN的产品）,期刊、卷、期、文章因为ISSN相同不受此限制
			field += ",(select cast(count(*) as string) from CFavourites cf where cf.user.id=? and cf.publications.id=a.id )";
		}
		field += ",(select cast(count(*) as string) from BIpRange ip where ip.sip <= ? and ip.eip>=?)";
		field += ",(select cast(count(*) as string) from LLicense lll where lll.publications.id=a.id or lll.publications.code=a.code)";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, PPublications.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PPublications> getSimplePubList(Map<String, Object> condition, String sort, Integer number) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a ";
		Map<String, Object> t = this.getWhere(condition);
		String property = null;
		String field = null;

		if (condition.containsKey("justYear")) {
			property = " year ";
			field = " a.year ";
		} else if (condition.containsKey("issueList")) {
			property = "id,volumeCode,issueCode,month,journalOrder";
			field = "a.id,a.volumeCode,a.issueCode,a.month,a.journalOrder";
		} else {
			property = "id,title,code,volume.id,volumeCode,issue.id,issueCode,year,month,pdf,startPage";
			field = "a.id,a.title,a.code,a.volume.id,a.volumeCode,a.issue.id,a.issueCode,a.year,a.month,a.pdf,a.startPage";
		}
		try {
			if (number > 0) {
				list = this.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, PPublications.class.getName(), number, 0);
			} else {
				list = this.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, PPublications.class.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PPublications> getSimplePageList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a ";
		Map<String, Object> t = this.getWhere(condition);
		String property = null;
		String field = null;

		if (condition.containsKey("justYear")) {
			property = " year ";
			field = " a.year ";
		} else if (condition.containsKey("issueList")) {
			property = "id,volumeCode,issueCode,month,journalOrder";
			field = "a.id,a.volumeCode,a.issueCode,a.month,a.journalOrder";
		} else {
			property = "id,title,code,volume.id,volumeCode,issue.id,issueCode,year,month,pdf,startPage";
			field = "a.id,a.title,a.code,a.volume.id,a.volumeCode,a.issue.id,a.issueCode,a.year,a.month,a.pdf,a.startPage";
		}
		try {
			list = this.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, PPublications.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/*
	 * @SuppressWarnings("unchecked") public List<PPublications>
	 * getSimplePubListIssue(Map<String, Object> condition, String sort, Integer
	 * pageCount, Integer curPage) throws Exception { List<PPublications> list =
	 * null; String hql = " from PPublications a "; Map<String, Object> t =
	 * this.getWhere(condition); String property = "id,year,issueCode"; String
	 * field = "a.id,a.year,a.issueCode"; try { list =
	 * this.hibernateDao.getListByHql(property, field, hql +
	 * t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(),
	 * " group by substring(a.pubDate,1,4) order by substring(a.pubDate,1,4) desc "
	 * , PPublications.class.getName()); } catch (Exception e) { throw e; }
	 * return list; }
	 */

	/**
	 * 获取期刊列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getArticleList(Map<String, Object> condition, String sort, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publisher b left join a.publications c ";
		Map<String, Object> t = this.getWhere(condition);
		List<Object> con = new ArrayList<Object>();
		con.add(cn.com.daxtech.framework.util.DateUtil.toDate(this.getDistryDate(), "yyyy-MM-dd"));
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		if (user != null) {
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
		}
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String property = "journalType,periodicType,volume.id,issue.id,year,month,volumeCode,issueCode,chineseTitle,sisbn,hisbn,status,id,publisher.id,publisher.name,title,author,code,listPrice,lcurr,browsePrecent,type" + ",pubSubject,cover,createOn,updateOn,pubDate,pdf,path,lang,remark,homepage,newest,selected,special,pubDate,startPage,endPage" + ",oa,free,local" + ",startVolume" + ",endVolume" + ",inCollection,latest,subscribedIp" + ",keywords,reference,doi" + ",publications.id,publications.code,publications.title,eissn";
		if (user != null) {
			property += ",subscribedUser,exLicense,buyInDetail,favorite";
		}
		property += ",recommand";
		String field = "a.journalType,a.periodicType,a.volume.id,a.issue.id,a.year,a.month,a.volumeCode,a.issueCode,a.chineseTitle,a.sisbn,a.hisbn,a.status,a.id,b.id,b.name,a.title,a.author,a.code,a.listPrice,a.lcurr,a.browsePrecent,a.type" + ",a.pubSubject,a.cover,a.createOn,a.updateOn,a.pubDate,a.pdf,a.path,a.lang,a.remark,a.homepage,a.newest,a.selected,a.special,a.pubDate,a.startPage,a.endPage" + ",a.oa,a.free,a.local" + ",a.startVolume" + ",a.endVolume" +
				// ",(select max(concat(t.year,'-',t.volumeCode)) from
				// PPublications t where t.publications.id=a.id and t.type=6 )"
				// +
				// ",(select distinct l.readUrl from LLicense l where
				// l.publications.id=a.id and l.status=1)"
				// +
				// ",(case when exists(select cc.id from PCcRelation cc where
				// cc.publications.id=a.id and cc.collection.status=2) then '1'
				// else '0' end) as inCollection"
				// +
		",a.inCollection" + ",(case when exists(select pub.id from PPublications pub where pub.createOn >=? and pub.id=a.id) then '1' else '0' end) as isnewst" +
				// ",'0'";
				",(case when exists(select lip.id from LLicenseIp lip where (lip.license.publications.id=a.id or lip.license.publications.code=a.code) and lip.sip<=? and lip.eip>=? and lip.license.status=1) then '1' else '0' end) as IPRange" + ",a.keywords,a.reference,a.doi" + ",c.id,c.code,c.title,a.eissn";

		if (user != null) {
			field += ",(case when exists(select li.id from LLicense li where li.status=1 and li.user.id=? and (li.publications.id=a.id or li.publications.code=a.code)) then '1' else '0' end) as subscribedUser";
			field += ",(case when exists(select ll.id from LLicense ll where ll.status=2 and ll.user.id=? and (ll.publications.id=a.id or ll.publications.code=a.code)) then '1' else '0' end) as exLicense";
			field += ",(case when exists(select detail.id from OOrderDetail detail where detail.status not in (3,10,99) and detail.user.id=? and (detail.price.publications.id=a.id or detail.price.publications.code=a.code)) then '1' else '0' end) as buyInDetail";// 2013-3-13
																																																																		// 当一个用户已购买的产品下架后，同时上架另一个相同ISBN的产品，但是ISBN相同的产品不属于同一个提供商（Source），这个时候新上架的产品不能被购买（该用户已经买过相同ISBN的产品）,期刊、卷、期、文章因为ISSN相同不受此限制
			field += ",(case when exists(select cf.id from CFavourites cf where cf.user.id=? and cf.publications.id=a.id ) then '1' else '0' end) as favorite";
		}
		// field += ",'0'";
		field += ",(case when exists(select ip.id from BIpRange ip where ip.sip <= ? and ip.eip>=?) then '1' else '0' end) as recommend";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, PPublications.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 获取期刊列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getArticlePagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publisher b left join a.publications c";
		// 求当前时间前的2周以前的日期
		String targetDate = this.getDistryDate();
		Map<String, Object> t = this.getWhere(condition);
		List<Object> con = new ArrayList<Object>();
		con.add(cn.com.daxtech.framework.util.DateUtil.toDate(this.getDistryDate(), "yyyy-MM-dd"));
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		if (user != null) {
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
		}
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String property = "publications.id,publications.journalOrder,publications.code,volume.id,issue.id,year,month,volumeCode,issueCode,chineseTitle,sisbn,hisbn,status,id,publisher.id,publisher.name,title,author,code,listPrice,lcurr,browsePrecent,type" + ",journalType,periodicType,pubSubject,cover,createOn,updateOn,pdf,path,lang,remark,homepage,newest,selected,special,pubDate,startPage,endPage" + ",oa,free,local" + ",startVolume" + ",endVolume" + ",inCollection,latest,subscribedIp" + ",keywords,reference,doi" + ",publications.id,publications.code,publisher.journalDownload";
		if (user != null) {
			property += ",subscribedUser,exLicense,buyInDetail,favorite";
		}
		property += ",recommand";
		String field = "a.publications.id,a.journalOrder,a.publications.code,a.volume.id,a.issue.id,a.year,a.month,a.volumeCode,a.issueCode,a.chineseTitle,a.sisbn,a.hisbn,a.status,a.id,b.id,b.name,a.title,a.author,a.code,a.listPrice,a.lcurr,a.browsePrecent,a.type" + ",a.journalType,a.periodicType,a.pubSubject,a.cover,a.createOn,a.updateOn,a.pdf,a.path,a.lang,a.remark,a.homepage,a.newest,a.selected,a.special,a.pubDate,a.startPage,a.endPage" + ",a.oa,a.free,a.local" + ",a.startVolume" + ",a.endVolume" +
				// ",(select max(concat(t.year,'-',t.volumeCode)) from
				// PPublications t where t.publications.id=a.id and t.type=6 )"
				// +
				// ",(select distinct l.readUrl from LLicense l where
				// l.publications.id=a.id and l.status=1)"
				// +
				// ",(case when exists(select cc.id from PCcRelation cc where
				// cc.publications.id=a.id and cc.collection.status=2) then '1'
				// else '0' end) as inCollection"
				// +
		",a.inCollection" + ",(case when exists(select pub.id from PPublications pub where pub.createOn >=? and pub.id=a.id) then '1' else '0' end) as isnewst" +
				// ",'0'";
				",(case when exists(select lip.id from LLicenseIp lip where (lip.license.publications.id=a.id or lip.license.publications.code=a.code) and lip.sip<=? and lip.eip>=? and lip.license.status=1) then '1' else '0' end) as IPRange" + ",a.keywords,a.reference,a.doi" + ",c.id,c.code,b.journalDownload";
		if (user != null) {
			field += ",(case when exists(select li.id from LLicense li where li.status=1 and li.user.id=? and (li.publications.id=a.id or li.publications.code=a.code)) then '1' else '0' end) as subscribedUser";
			field += ",(case when exists(select ll.id from LLicense ll where ll.status=2 and ll.user.id=? and (ll.publications.id=a.id or ll.publications.code=a.code)) then '1' else '0' end) as exLicense";
			field += ",(case when exists(select detail.id from OOrderDetail detail where detail.status not in (3,10,99) and detail.user.id=? and (detail.price.publications.id=a.id or detail.price.publications.code=a.code)) then '1' else '0' end) as buyInDetail";// 2013-3-13
																																																																		// 当一个用户已购买的产品下架后，同时上架另一个相同ISBN的产品，但是ISBN相同的产品不属于同一个提供商（Source），这个时候新上架的产品不能被购买（该用户已经买过相同ISBN的产品）,期刊、卷、期、文章因为ISSN相同不受此限制
			field += ",(case when exists(select cf.id from CFavourites cf where cf.user.id=? and cf.publications.id=a.id ) then '1' else '0' end) as favorite";
		}
		// field += ",'0'";
		field += ",(case when exists(select ip.id from BIpRange ip where ip.sip <= ? and ip.eip>=?) then '1' else '0' end) as recommend";

		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, PPublications.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getDatabaseList(Map<String, Object> condition, String sort, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publisher b ";
		// 求当前时间前的2周以前的日期
		String targetDate = this.getDistryDate();
		Map<String, Object> t = this.getWhere(condition);
		List<Object> con = new ArrayList<Object>();
		con.add(cn.com.daxtech.framework.util.DateUtil.toDate(this.getDistryDate(), "yyyy-MM-dd"));
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		if (user != null) {
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
		}
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String property = "volume.id,issue.id,year,month,volumeCode,issueCode,chineseTitle,sisbn,hisbn,status,id,publisher.id,publisher.name,title,author,code,listPrice,lcurr,browsePrecent,type" + ",pubSubject,cover,createOn,updateOn,pdf,path,lang,remark,homepage,newest,selected,special,pubDate,startPage,endPage" + ",oa,free,local,inCollection,latest,subscribedIp";
		if (user != null) {
			property += ",subscribedUser,exLicense,buyInDetail,favorite";
		}
		property += ",recommand";
		String field = "a.volume.id,a.issue.id,a.year,a.month,a.volumeCode,a.issueCode,a.chineseTitle,a.sisbn,a.hisbn,a.status,a.id,b.id,b.name,a.title,a.author,a.code,a.listPrice,a.lcurr,a.browsePrecent,a.type" + ",a.pubSubject,a.cover,a.createOn,a.updateOn,a.pdf,a.path,a.lang,a.remark,a.homepage,a.newest,a.selected,a.special,a.pubDate,a.startPage,a.endPage" + ",a.oa,a.free,a.local" +
				// ",(case when exists(select cc.id from PCcRelation cc where
				// cc.publications.id=a.id and cc.collection.status=2) then '1'
				// else '0' end) as inCollection"
				// +
		",a.inCollection" + ",(case when exists(select pub.id from PPublications pub where pub.createOn >=? and pub.id=a.id) then '1' else '0' end) as isnewst" +
				// ",'0'";
				",(case when exists(select lip.id from LLicenseIp lip where (lip.license.publications.id=a.id or lip.license.publications.code=a.code) and lip.sip<=? and lip.eip>=? and lip.license.status=1) then '1' else '0' end) as IPRange";
		if (user != null) {
			field += ",(case when exists(select li.id from LLicense li where li.status=1 and li.user.id=? and (li.publications.id=a.id or li.publications.code=a.code)) then '1' else '0' end) as subscribedUser";
			field += ",(case when exists(select ll.id from LLicense ll where ll.status=2 and ll.user.id=? and (ll.publications.id=a.id or ll.publications.code=a.code)) then '1' else '0' end) as exLicense";
			field += ",(case when exists(select detail.id from OOrderDetail detail where detail.status not in (3,10,99) and detail.user.id=? and (detail.price.publications.id=a.id or detail.price.publications.code=a.code)) then '1' else '0' end) as buyInDetail";// 2013-3-13
																																																																		// 当一个用户已购买的产品下架后，同时上架另一个相同ISBN的产品，但是ISBN相同的产品不属于同一个提供商（Source），这个时候新上架的产品不能被购买（该用户已经买过相同ISBN的产品）,期刊、卷、期、文章因为ISSN相同不受此限制
			field += ",(case when exists(select cf.id from CFavourites cf where cf.user.id=? and cf.publications.id=a.id ) then '1' else '0' end) as favorite";
		}
		// field += ",'0'";
		field += ",(case when exists(select ip.id from BIpRange ip where ip.sip <= ? and ip.eip>=?) then '1' else '0' end) as recommend";

		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, PPublications.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
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
	public List<PPublications> getDatabasePagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publisher b ";
		// 求当前时间前的2周以前的日期
		String targetDate = this.getDistryDate();
		Map<String, Object> t = this.getWhere(condition);
		List<Object> con = new ArrayList<Object>();
		con.add(cn.com.daxtech.framework.util.DateUtil.toDate(this.getDistryDate(), "yyyy-MM-dd"));
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		if (user != null) {
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
		}
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String property = "volume.id,issue.id,year,month,volumeCode,issueCode,chineseTitle,sisbn,hisbn,status,id,publisher.id,publisher.name,title,author,code,listPrice,lcurr,browsePrecent,type" + ",pubSubject,cover,createOn,updateOn,pdf,path,lang,remark,homepage,newest,selected,special,pubDate,startPage,endPage" + ",oa,free,local,inCollection,latest,subscribedIp";
		if (user != null) {
			property += ",subscribedUser,exLicense,buyInDetail,favorite";
		}
		property += ",recommand";
		String field = "a.volume.id,a.issue.id,a.year,a.month,a.volumeCode,a.issueCode,a.chineseTitle,a.sisbn,a.hisbn,a.status,a.id,b.id,b.name,a.title,a.author,a.code,a.listPrice,a.lcurr,a.browsePrecent,a.type" + ",a.pubSubject,a.cover,a.createOn,a.updateOn,a.pdf,a.path,a.lang,a.remark,a.homepage,a.newest,a.selected,a.special,a.pubDate,a.startPage,a.endPage" + ",a.oa,a.free,a.local" +
				// ",(case when exists(select cc.id from PCcRelation cc where
				// cc.publications.id=a.id and cc.collection.status=2) then '1'
				// else '0' end) as inCollection"
				// +
		",a.inCollection" + ",(case when exists(select pub.id from PPublications pub where pub.createOn >=? and pub.id=a.id) then '1' else '0' end) as isnewst" +
				// ",'0'";
				",(case when exists(select lip.id from LLicenseIp lip where (lip.license.publications.id=a.id or lip.license.publications.code=a.code) and lip.sip<=? and lip.eip>=? and lip.license.status=1) then '1' else '0' end) as IPRange";
		if (user != null) {
			field += ",(case when exists(select li.id from LLicense li where li.status=1 and li.user.id=? and (li.publications.id=a.id or li.publications.code=a.code)) then '1' else '0' end) as subscribedUser";
			field += ",(case when exists(select ll.id from LLicense ll where ll.status=2 and ll.user.id=? and (ll.publications.id=a.id or ll.publications.code=a.code)) then '1' else '0' end) as exLicense";
			field += ",(case when exists(select detail.id from OOrderDetail detail where detail.status not in (3,10,99) and detail.user.id=? and (detail.price.publications.id=a.id or detail.price.publications.code=a.code)) then '1' else '0' end) as buyInDetail";// 2013-3-13
																																																																		// 当一个用户已购买的产品下架后，同时上架另一个相同ISBN的产品，但是ISBN相同的产品不属于同一个提供商（Source），这个时候新上架的产品不能被购买（该用户已经买过相同ISBN的产品）,期刊、卷、期、文章因为ISSN相同不受此限制
			field += ",(case when exists(select cf.id from CFavourites cf where cf.user.id=? and cf.publications.id=a.id ) then '1' else '0' end) as favorite";
		}
		// field += ",'0'";
		field += ",(case when exists(select ip.id from BIpRange ip where ip.sip <= ? and ip.eip>=?) then '1' else '0' end) as recommend";

		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, PPublications.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public String getStartVolume(String id) throws Exception {
		String startVolume = "";
		try {
			String hql = " from PPublications a where a.publications.id=? and a.type=6 ";
			List<PPublications> list = this.hibernateDao.getListByHql("id", "min(concat(a.year,'-',a.volumeCode))", hql, new Object[] { id }, "", PPublications.class.getName());
			if (list != null && list.size() > 0) {
				startVolume = list.get(0).getId();
			}
		} catch (Exception e) {
			throw e;
		}
		return startVolume;
	}

	public String getEndVolume(String id) throws Exception {
		String endVolume = "";
		try {
			String hql = " from PPublications a where a.publications.id=? and a.type=6 ";
			List<PPublications> list = this.hibernateDao.getListByHql("id", "max(concat(a.year,'-',a.volumeCode))", hql, new Object[] { id }, "", PPublications.class.getName());
			if (list != null && list.size() > 0) {
				endVolume = list.get(0).getId();
			}
		} catch (Exception e) {
			throw e;
		}
		return endVolume;
	}

	public int getDatabaseCount(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		Map<String, Object> t = this.getWhere(condition);
		String hql = " from PPublications a left join a.publisher b";
		try {
			list = this.hibernateDao.getListByHql("id", "cast(count(*) as string)", hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), "", PPublications.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list == null ? 0 : Integer.valueOf(list.get(0).getId());
	}

	/**
	 * 获取已订阅或OAFree的分页信息
	 * 
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getLicensePagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publisher b left join ";
		// 求当前时间前的2周以前的日期
		Map<String, Object> t = this.getWhere(condition);
		List<Object> con = new ArrayList<Object>();
		con.add(cn.com.daxtech.framework.util.DateUtil.toDate(this.getDistryDate(), "yyyy-MM-dd"));
		if (CollectionsUtil.exist(condition, "institutionId") && condition.get("institutionId") != null && !"".equals(condition.get("institutionId"))) {
			con.add(IpUtil.getLongIp(ip));
			con.add(IpUtil.getLongIp(ip));
		}
		if (user != null) {
			// con.add(user.getId());
			// con.add(user.getId());
			// con.add(user.getId());
			con.add(user.getId());
		}
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String property = "year,month,chineseTitle,status,id,publisher.id,publisher.name,title,author,code,type" + ",cover,remark,homepage,newest,selected,special,pubDate" + ",oa,free,local" + ",startVolume" + ",endVolume" + ",inCollection,latest,subscribedIp";
		if (user != null) {
			property += ",subscribedUser,exLicense,buyInDetail,favorite";
		}
		property += ",recommand";
		String field = "a.year,a.month,a.chineseTitle,a.status,a.id,b.id,b.name,a.title,a.author,a.code,a.type" + ",a.cover,a.remark,a.homepage,a.newest,a.selected,a.special,a.pubDate" + ",a.oa,a.free,a.local" + ",a.startVolume" + ",a.endVolume" +
				// ",(case when exists(select cc.id from PCcRelation cc where
				// cc.publications.id=a.id and cc.collection.status=2 ) then '1'
				// else '0' end) as inCollection"
				// +
		",a.inCollection" + ",(case when exists(select pub.id from PPublications pub where pub.id=a.id and pub.createOn >=?  ) then '1' else '0' end) as isnewst";
		if (CollectionsUtil.exist(condition, "institutionId") && condition.get("institutionId") != null && !"".equals(condition.get("institutionId"))) {
			field += ",(case when exists(select lip.id from LLicenseIp lip where lip.license.publications.code=a.code and lip.license.status=1  and lip.sip<=? and lip.eip>=?  ) then '1' else '0' end) as IPRange";
		} else {
			field += ",'1'";
		}
		if (user != null) {
			field += ",'1'";
			field += ",'1'";
			field += ",'1'";// 2013-3-13
							// 当一个用户已购买的产品下架后，同时上架另一个相同ISBN的产品，但是ISBN相同的产品不属于同一个提供商（Source），这个时候新上架的产品不能被购买（该用户已经买过相同ISBN的产品）,期刊、卷、期、文章因为ISSN相同不受此限制
			field += ",(case when exists(select cf.id from CFavourites cf where cf.publications.id=a.id and cf.user.id=?   ) then '1' else '0' end) as favorite";
		}
		field += ",(case when exists(select ip.id from BIpRange ip where ip.sip <= ? and ip.eip>=?) then '1' else '0' end) as recommend";

		String _hql = t.get("where").toString();
		/**
		 * license
		 */
		if (CollectionsUtil.exist(condition, "license") && condition.get("license") != null && !"".equals(condition.get("license"))) {
			_hql += " and ((a.oa+a.free)>=3 ";
			if (CollectionsUtil.exist(condition, "institutionId") && condition.get("institutionId") != null && !"".equals(condition.get("institutionId"))) {
				_hql += " or exists(select lip.id from LLicenseIp lip where lip.license.publications.code=a.code and lip.license.status=1 and lip.sip<=? and lip.eip>=?  ) ";
				con.add(IpUtil.getLongIp(ip));
				con.add(IpUtil.getLongIp(ip));
			}
			if (user != null) {
				_hql += " or exists(select ll.publications.id from LLicense ll where ll.publications.code=a.code and ll.status=1 and ll.user.id=?   ) ";
				con.add(user.getId());
			}

			_hql += " )";
		}
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + _hql, con.toArray(), sort, PPublications.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 获取已订阅或OAFree的信息总数
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer getLicenseCount(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		// Map<String,Object> t=this.getSubscriptionWhere(condition);
		String hql = " from PPublications a left join a.publisher b ";
		// 求当前时间前的2周以前的日期
		Map<String, Object> t = this.getWhere(condition);
		List<Object> con = new ArrayList<Object>();
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}
		String _hql = "";
		/**
		 * license
		 */
		if (CollectionsUtil.exist(condition, "license") && condition.get("license") != null && !"".equals(condition.get("license"))) {
			_hql += " and ((a.oa+a.free)>=3 ";
			if (CollectionsUtil.exist(condition, "institutionId") && condition.get("institutionId") != null && !"".equals(condition.get("institutionId"))) {
				_hql += " or exists(select lip.id from LLicenseIp lip where lip.license.status=1 and lip.license.publications.code=a.code and lip.sip<=? and lip.eip>=?  ) ";
				con.add(IpUtil.getLongIp(condition.get("ip").toString()));
				con.add(IpUtil.getLongIp(condition.get("ip").toString()));
			}
			if (CollectionsUtil.exist(condition, "userId") && condition.get("userId") != null && !"".equals(condition.get("userId"))) {
				_hql += " or exists(select ll.publications.id from LLicense ll where ll.status=1 and ll.publications.code=a.code and ll.user.id=?   ) ";
				con.add(condition.get("userId"));
			}
			_hql += " )";
		}
		try {
			list = this.hibernateDao.getListByHql("id", "cast(count(*) as string)", hql + t.get("where").toString() + _hql, con.toArray(), "", PPublications.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list == null ? 0 : Integer.valueOf(list.get(0).getId());
	}

	/**
	 * 获取章节信息
	 * 
	 * @param conToc
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getTocList(Map<String, Object> condition, String sort) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a ";
		Map<String, Object> t = this.getWhere(condition);
		String property = "treecode,order,id,title,startPage,publications.id,publications.fileType,pdf,journalOrder";
		String field = "a.treecode,a.order,a.id,a.title,a.startPage,a.publications.id,a.publications.fileType,a.pdf,a.journalOrder";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, PPublications.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 获取各类型总数
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getTypeCount(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a ";
		Map<String, Object> t = this.getWhere(condition);
		String property = "type,startPage";
		String field = "a.type,cast(count(*) as string)";
		Object o1 = t.get("condition");
		List<Object> o3 = (List<Object>) o1;
		Object[] o2 = o3.toArray();
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), o2, " group by a.type ", PPublications.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 获取出版物列表3
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PPublications> getList3(Map<String, Object> condition, String sort, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		String hql = " from PPublications a left join a.publisher b left join a.publications p ";
		Map<String, Object> t = this.getWhere(condition);
		// 求当前时间前的2周以前的日期
		List<Object> con = new ArrayList<Object>();
		con.add(cn.com.daxtech.framework.util.DateUtil.toDate(this.getDistryDate(), "yyyy-MM-dd"));
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		if (user != null) {
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
			con.add(user.getId());
		}
		con.add(IpUtil.getLongIp(ip));
		con.add(IpUtil.getLongIp(ip));
		for (Object o : (List<Object>) t.get("condition")) {
			con.add(o);
		}

		String property = "periodicType,journalType,publications.id,publications.title,publisher.id,publisher.name,publisher.nameEn,id,title,chineseTitle,author,code,browsePrecent,type,year,pubDate,remark,available,pdf,cover,browsePrecent,startPage,endPage" + ",authorAlias,oa,free,local" + ",startVolume" + ",endVolume" + ",inCollection,latest,subscribedIp";
		if (user != null) {
			property += ",subscribedUser,exLicense,buyInDetail,favorite";
		}
		property += ",recommand";
		String field = "a.periodicType,a.journalType,p.id,p.title,b.id,b.name,b.nameEn,a.id,a.title,a.chineseTitle,a.author,a.code,a.browsePrecent,a.type,a.year,a.pubDate,a.remark,a.available,a.pdf,a.cover,a.browsePrecent,a.startPage,a.endPage" + ",a.author,a.oa,a.free,a.local" + ",a.startVolume" + ",a.endVolume" + ",a.inCollection" + ",(case when exists(select pub.id from PPublications pub where pub.createOn >=? and pub.id=a.id) then '1' else '0' end) as isnewst" + ",(case when exists(select lll.id from LLicense lll where (lll.publications.id=a.id or lll.publications.code=a.code) and lll.status=1 and " + "(exists(select ipr.id from BIpRange ipr where ipr.institution.id=lll.user.institution.id and ipr.sip<=? and ipr.eip>=?) and lll.user.level=2" + " or " + "exists(select lip.id from LLicenseIp lip where lip.license.id=lll.id and lip.sip<=? and lip.eip>=?) and lll.user.level=2)) then '1' else '0' end) as IPRange";
		if (user != null) {
			field += ",(case when exists(select li.id from LLicense li where li.status=1 and li.user.id=? and (li.publications.id=a.id or li.publications.code=a.code)) then '1' else '0' end) as subscribedUser";
			field += ",(case when exists(select ll.id from LLicense ll where ll.status=2 and ll.user.id=? and (ll.publications.id=a.id or ll.publications.code=a.code)) then '1' else '0' end) as exLicense";
			field += ",(case when exists(select detail.id from OOrderDetail detail where detail.status not in (3,10,99) and detail.user.id=? and (detail.price.publications.id=a.id or detail.price.publications.code=a.code)) then '1' else '0' end) as buyInDetail";// 2013-3-13
																																																																		// 当一个用户已购买的产品下架后，同时上架另一个相同ISBN的产品，但是ISBN相同的产品不属于同一个提供商（Source），这个时候新上架的产品不能被购买（该用户已经买过相同ISBN的产品）,期刊、卷、期、文章因为ISSN相同不受此限制
			field += ",(case when exists(select cf.id from CFavourites cf where cf.user.id=? and cf.publications.id=a.id ) then '1' else '0' end) as favorite";
		}
		field += ",(case when exists(select ip.id from BIpRange ip where ip.sip <= ? and ip.eip>=?) then '1' else '0' end) as recommend";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), con.toArray(), sort, PPublications.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 跟新期刊开源、免费
	 * 
	 * @param vals
	 * @param ids
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateJournals(Map<String, Object> vals, String[] ids) throws Exception {
		try {

			if (ids != null && vals != null && !vals.isEmpty()) {
				List<Object> condition = new ArrayList<Object>();
				String set = "";
				String where = "";
				for (Map.Entry<String, Object> entry : vals.entrySet()) {
					set += entry.getKey() + "=?,";
					condition.add(entry.getValue());
				}
				if (!"".equals(set.trim()) && ",".equals(set.substring(set.length() - 1))) {
					set = set.substring(0, set.length() - 1);
				}
				for (String id : ids) {
					if (!"".equals(id)) {
						where += "?,";
						condition.add(id);
					}
				}
				if (!"".equals(where.trim()) && ",".equals(where.substring(where.length() - 1))) {
					where = where.substring(0, where.length() - 1);
				}
				if (!"".equals(where.trim()) && !"".equals(set.trim())) {
					String hql = "update PPublications set homepage = 2," + set + " where id in (" + where + ")";
					this.hibernateDao.executeHql(hql, condition.toArray());
				}
			}

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 期刊下的
	 * 
	 * @param vals
	 * @param ids
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateJournalp(Map<String, Object> vals, String[] ids) throws Exception {
		try {

			if (ids != null && vals != null && !vals.isEmpty()) {
				List<Object> condition = new ArrayList<Object>();
				String set = "";
				String where = "";
				for (Map.Entry<String, Object> entry : vals.entrySet()) {
					set += "a." + entry.getKey() + "=?,";
					condition.add(entry.getValue());
				}
				if (!"".equals(set.trim()) && ",".equals(set.substring(set.length() - 1))) {
					set = set.substring(0, set.length() - 1);
				}
				for (String id : ids) {
					if (!"".equals(id)) {
						where += "?,";
						condition.add(id);
					}
				}
				if (!"".equals(where.trim()) && ",".equals(where.substring(where.length() - 1))) {
					where = where.substring(0, where.length() - 1);
				}
				if (!"".equals(where.trim()) && !"".equals(set.trim())) {
					String hql = "update PPublications a  set a.homepage = 2," + set + " where a.publications.id in (" + where + ")";
					this.hibernateDao.executeHql(hql, condition.toArray());
				}
			}

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 查询页码所在的章节
	 * 
	 * @param pubId
	 * @param pageNum
	 * @return
	 * @throws Exception
	 */
	public PPublications getChapter(String pubId, int pageNum) throws Exception {
		PPublications pub = null;
		if (pubId == null || "".equals(pubId) || pageNum <= 0) {
			return pub;
		}
		try {
			String hql = " from PPublications a ";

			List<Object> con = new ArrayList<Object>();

			String whereString = " where a.publications.id = ? and cast(a.startPage as integer) <=? and cast(a.endPage as integer) >=? and a.type=? ";
			con.add(pubId);
			con.add(pageNum);
			con.add(pageNum);
			con.add(3);
			String property = " id,code,type,oa,free,publications.id ";
			String field = " a.id,a.code,a.type,a.oa,a.free,publications.id ";
			List<PPublications> list = this.hibernateDao.getListByHql(property, field, hql + whereString, con.toArray(), "", PPublications.class.getName());
			if (list != null && list.size() > 0) {
				pub = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return pub;
	}

	/**
	 * 通过sql获取PCollection产品包里面所包含的出版社（优化版）
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getpublisherInfoForCol(Map<String, Object> condition, String sort) throws Exception {
		List<PPublications> list = null;
		Map<String, Object> t = this.getWhere(condition);
		String property = "publisher.id,publisher.name,publisher.nameEn,eachCount";
		String field = "a.publisher.id,max(a.publisher.name),max(a.publisher.nameEn),cast(count(*) as string)";
		String hql = " from PPublications a where exists(select 1 from PCcRelation b where a.id=b.publications.id and b.collection.id='" + condition.get("collectionId") + "'";
		if (CollectionsUtil.exist(condition, "language") && !"".equals(condition.get("language")) && condition.get("language") != null) {
			hql += " and b.publications.lang like 'chs'";
		}
		if (CollectionsUtil.exist(condition, "languageEn") && !"".equals(condition.get("languageEn")) && condition.get("languageEn") != null) {
			hql += " and b.publications.lang not like 'chs'";
		}
		hql += ") group by a.publisher.id";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql, ((List<Object>) t.get("condition")).toArray(), sort, PPublications.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	 * 通过sql获取PCollection产品包里面所包含的语言（优化版）
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getLangInfoForCol(Map<String, Object> condition, String sort) throws Exception {
		List<PPublications> list = null;
		Map<String, Object> t = this.getWhere(condition);
		String property = "lang,eachCount";
		String field = "a.lang,cast(count(*) as string)";
		String hql = " from PPublications a where exists(select 1 from PCcRelation b where a.id=b.publications.id and b.collection.id='" + condition.get("collectionId") + "' ";
		if (CollectionsUtil.exist(condition, "language") && !"".equals(condition.get("language")) && condition.get("language") != null) {
			hql += " and b.publications.lang like 'chs'";
		}
		if (CollectionsUtil.exist(condition, "languageEn") && !"".equals(condition.get("languageEn")) && condition.get("languageEn") != null) {
			hql += " and b.publications.lang not like 'chs'";
		}
		hql += ") group by a.lang";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql, ((List<Object>) t.get("condition")).toArray(), sort, PPublications.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public List<PPublications> getBuyTimesInfo(String subjectId, String sort, Integer pubNum) throws Exception {
		List<PPublications> list = null;
		Object[] condition = new Object[] { subjectId, 1, 2, 2 }; // 1是图书；2是本地资源；2是已上架
		String property = "id,title,author,pubDate,startVolume,endVolume,publisher.name,publisher.nameEn,buyTimes";
		String field = "a.id,a.title,a.author,a.pubDate,a.startVolume,a.endVolume,p.name,p.nameEn,a.buyTimes";
		String hql = " from PPublications a inner join a.publisher p where exists(select r.id from PCsRelation r where r.publications.id=a.id and r.subject.id=?) and a.type=? and a.local = ? and a.status = ?";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql, condition, sort, PPublications.class.getName(), pubNum, 0);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	// 从 EPUB_P_Publications 表中的实时获取最新N条记录
	@SuppressWarnings("unchecked")
	public List<PPublications> getNewestPublications(Map<String, Object> condition, String sort, long count) throws Exception {
		List<PPublications> list = null;
		String hql = " FROM PPublications a left join a.publisher b ";
		Map<String, Object> t = getWhere(condition);
		String property = "id, title, author, type, pubDate, publisher.name";
		String field = "a.id, a.title, a.author, a.type, a.pubDate, b.name";
		try {
			list = hibernateDao.getListByHql(property, field, hql + t.get("where").toString() + " AND ROWNUM <= " + count, ((List<Object>) t.get("condition")).toArray(), sort, PPublications.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public List<PPublications> getPagingListNew(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<PPublications> list = null;
		Map<String, Object> t = this.getWhere(condition);
		String property = " id,title,type,lang,author,createOn,pubDate,pubSubject,publisher.name,publisher.nameEn ";
		String field = " a.id,a.title,a.type,a.lang,a.author,a.createOn,a.pubDate,a.pubSubject,b.name,b.nameEn ";
		String hql = " from PPublications a left join a.publisher b ";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, PPublications.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public PPublications getPublications(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		Map<String, Object> t = getWhere(condition);
		String hql = " from PPublications a left join a.favouriteses f";
		String property = " id,title,type,lang,author,createOn,pubDate,pubSubject ";
		String field = " a.id,a.title,a.type,a.lang,a.author,a.createOn,a.pubDate,a.pubSubject ";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), "", PPublications.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return null == list ? null : list.get(0);
	}

	private Map<String, Object> getTrialWhere(Map<String, Object> map) {
		Map<String, Object> table = new HashMap<String, Object>();
		String whereString = "";
		List<Object> condition = new ArrayList<Object>();
		int flag = 0;
		/**
		 * searchType
		 */
		if (CollectionsUtil.exist(map, "typeArr") && !"".equals(map.get("typeArr"))) {
			Integer[] types = (Integer[]) map.get("typeArr");
			if (types != null && types.length > 0) {
				String where_ = "";
				String _where = ")";
				String where = "";
				if (flag == 0) {
					where_ = " where d.type in (";
					flag = 1;
				} else {
					where_ = " and d.type in (";
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
		 * 语种
		 */
		if (CollectionsUtil.exist(map, "freelang") && !"".equals(map.get("freelang")) && map.get("freelang") != null) {
			if (flag == 0) {
				whereString += " where d.lang like ?";
				flag = 1;
			} else {
				whereString += " and d.lang like ?";
			}
			condition.add(map.get("freelang"));
		}
		/*
		 * if (CollectionsUtil.exist(map, "pDate") &&
		 * !"".equals(map.get("pDate")) && map.get("pDate") != null) { if (flag
		 * == 0) { whereString += " where a.pubDate like ?"; flag = 1; } else {
		 * whereString += " and a.pubDate like ?"; } condition.add("%" +
		 * map.get("pDate").toString() + "%"); }
		 */
		if (CollectionsUtil.exist(map, "pDate") && !"".equals(map.get("pDate")) && map.get("pDate") != null) {
			if (flag == 0) {
				whereString += " where d.pubDate like ?";
				flag = 1;
			} else {
				whereString += " and d.pubDate like ?";
			}
			condition.add("%" + map.get("pDate").toString() + "%");
		}

		/**
		 * 资源类型
		 */
		if (CollectionsUtil.exist(map, "trialType") && !"".equals(map.get("trialType")) && map.get("trialType") != null) {
			if (flag == 0) {
				if (map.get("trialType").equals("2")) {
					whereString += " where d.type in (2,6,7)";
				} else {
					whereString += " where d.type = ?";
					condition.add(Integer.valueOf(map.get("trialType").toString()));
				}

				flag = 1;
			} else {
				if (map.get("trialType") == "2") {
					whereString += " and d.type in (2,6,7)";
				} else {
					whereString += " and d.type = ?";
					condition.add(Integer.valueOf(map.get("trialType").toString()));
				}
			}

		}

		if (CollectionsUtil.exist(map, "status") && !"".equals(map.get("status")) && map.get("status") != null) {
			if (flag == 0) {
				whereString += " where a.status = ?";
				flag = 1;
			} else {
				whereString += " and a.status = ?";
			}
			condition.add(map.get("status"));
		}
		// 首页是否为试用资源
		if (CollectionsUtil.exist(map, "myisTrial") && !"".equals(map.get("myisTrial")) && map.get("myisTrial") != null) {
			if (flag == 0) {
				whereString += " where a.isTrial = ?";
				flag = 1;
			} else {
				whereString += " and a.isTrial = ?";
			}
			condition.add(map.get("myisTrial"));
		}

		if (CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId")) && map.get("institutionId") != null) {
			if (flag == 0) {
				whereString += " where c.id = ?";
				flag = 1;
			} else {
				whereString += " and c.id = ?";
			}
			condition.add(map.get("institutionId").toString());
		}

		if (CollectionsUtil.exist(map, "trypublisherName") && !"".equals(map.get("trypublisherName")) && map.get("trypublisherName") != null) {
			if (flag == 0) {
				whereString += " where e.name = ?";
				flag = 1;
			} else {
				whereString += " and e.name = ?";
			}
			condition.add(map.get("trypublisherName"));
		}

		table.put("where", whereString);
		table.put("condition", condition);
		return table;
	}

}
