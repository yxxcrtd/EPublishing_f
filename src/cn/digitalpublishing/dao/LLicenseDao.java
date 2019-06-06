package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.util.web.IpUtil;

public class LLicenseDao extends CommonDao<LLicense, String> {

	private Map<String, Object> getWhere(Map<String, Object> map) {
		Map<String, Object> table = new HashMap<String, Object>();
		String whereString = "";
		List<Object> condition = new ArrayList<Object>();
		int flag = 0;
		/**
		 * searchType
		 */
		if (CollectionsUtil.exist(map, "searchType") && !"".equals(map.get("searchType"))) {
			Integer[] types = (Integer[]) map.get("searchType");
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
		 * 出版年份
		 */
		if (CollectionsUtil.exist(map, "pubYear") && !"".equals(map.get("pubYear")) && map.get("pubYear") != null) {
			if (flag == 0) {
				whereString += " where substring(a.publications.pubDate,1,4) = ?";
				flag = 1;
			} else {
				whereString += " and substring(a.publications.pubDate,1,4)  = ?";
			}
			condition.add(map.get("pubYear").toString());
		}

		/**
		 * 资源类型
		 */
		if (CollectionsUtil.exist(map, "pType") && !"".equals(map.get("pType")) && map.get("pType") != null) {
			if (flag == 0) {
				whereString += " where d.type = ?";
				flag = 1;
			} else {
				whereString += " and d.type  = ?";
			}
			condition.add(Integer.valueOf(map.get("pType").toString()));
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
					whereString += " where d.type in (2,6,7)";
				} else {
					whereString += " where d.type = ?";
					condition.add(Integer.valueOf(map.get("trialType").toString()));
				}
			}

		}

		if (CollectionsUtil.exist(map, "lId") && !"".equals(map.get("lId"))) {
			if (flag == 0) {
				whereString += " where a.id = ?";
				flag = 1;
			} else {
				whereString += " and a.id = ?";
			}
			condition.add(map.get("lId").toString());
		}
		if (CollectionsUtil.exist(map, "userid") && !"".equals(map.get("userid"))) {
			if (flag == 0) {
				whereString += " where b.id = ?";
				flag = 1;
			} else {
				whereString += " and b.id = ?";
			}
			condition.add(map.get("userid").toString());
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

		/*
		 * if(CollectionsUtil.exist(map,
		 * "isTrail")&&!"".equals(map.get("isTrail")) &&
		 * map.get("isTrail")!=null){ if(flag==0){ whereString+=
		 * " where a.isTrial = ?"; flag=1; }else{ whereString+=
		 * " and a.isTrial = ?"; } condition.add(map.get("isTrail")); }
		 */

		if (CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId")) && map.get("institutionId") != null) {
			if (flag == 0) {
				whereString += " where c.id = ?";
				flag = 1;
			} else {
				whereString += " and c.id = ?";
			}
			condition.add(map.get("institutionId").toString());
		}
		if (CollectionsUtil.exist(map, "uidOrinsid") && !"".equals(map.get("institutionId")) && map.get("institutionId") != null) {
			String[] ary = map.get("uidOrinsid").toString().split(",");
			if (flag == 0) {
				whereString += " where ( b.id = ? or c.id=? )";
				flag = 1;
			} else {
				whereString += " and (b.id = ? or c.id=? ) ";
			}
			condition.add(ary[0]);
			condition.add(ary[1]);
		}

		if (CollectionsUtil.exist(map, "level") && !"".equals(map.get("level"))) {
			if (flag == 0) {
				whereString += " where b.level = ?";
				flag = 1;
			} else {
				whereString += " and b.level = ?";
			}
			condition.add(map.get("level"));
		}
		if (CollectionsUtil.exist(map, "pubId") && !"".equals(map.get("pubId"))) {
			if (flag == 0) {
				whereString += " where d.id = ?";
				flag = 1;
			} else {
				whereString += " and d.id = ?";
			}
			condition.add(map.get("pubId").toString());
		}
		if (CollectionsUtil.exist(map, "type") && !"".equals(map.get("type"))) {
			if (flag == 0) {
				whereString += " where a.type = ?";
				flag = 1;
			} else {
				whereString += " and a.type = ?";
			}
			condition.add(map.get("type"));
		}
		if (CollectionsUtil.exist(map, "ptype") && !"".equals(map.get("ptype"))) {
			if (flag == 0) {
				whereString += " where d.type = ?";
				flag = 1;
			} else {
				whereString += " and d.type = ?";
			}
			condition.add(map.get("ptype"));
		}
		if (CollectionsUtil.exist(map, "insIdin") && !"".equals(map.get("insIdin"))) {
			String[] ary = map.get("insIdin").toString().split(",");
			String inCon = "";
			for (int i = 0; i < ary.length; i++) {
				inCon += "?,";
				condition.add(ary[i]);
			}
			inCon = "(" + inCon.substring(0, inCon.length() - 1) + ")";
			if (flag == 0) {
				whereString += " where c.id in " + inCon + "";
				flag = 1;
			} else {
				whereString += " and c.id in " + inCon + "";
			}
		}
		if (CollectionsUtil.exist(map, "endTime") && !"".equals(map.get("endTime"))) {
			if (flag == 0) {
				whereString += " where a.endTime < ?";
				flag = 1;
			} else {
				whereString += " and a.endTime < ?";
			}
			condition.add(map.get("endTime"));
		}
		if (CollectionsUtil.exist(map, "end_time") && !"".equals(map.get("end_time"))) {
			if (flag == 0) {
				whereString += " where a.endTime > ?";
				flag = 1;
			} else {
				whereString += " and a.endTime > ?";
			}
			condition.add(map.get("end_time"));
		}
		if (CollectionsUtil.exist(map, "available") && !"".equals(map.get("available"))) {// 这是对一个available的条件
			if (flag == 0) {
				whereString += " where ( d.available <> ?  or d.available is null )  ";
				flag = 1;
			} else {
				whereString += " and  ( d.available <> ?  or d.available is null ) ";
			}
			condition.add(map.get("available"));
		}
		// 若未传入isTrial条件则默认查询非试用License
		if (CollectionsUtil.exist(map, "isTrial")) {
			if (!"".equals(map.get("isTrail")) && map.get("isTrail") != null) {
				if (flag == 0) {
					whereString += " where a.isTrial = ?";
					flag = 1;
				} else {
					whereString += " and a.isTrial = ?";
				}
				condition.add(map.get("isTrial"));
			}
			// } else if ("0".equals(map.get("isTrail"))) {
			//
			// } else {
			// if (flag == 0) {
			// whereString += " where (a.isTrial = 2 or a.isTrial is null)";
			// flag = 1;
			// } else {
			// whereString += " and (a.isTrial = 2 or a.isTrial is null)";
			// }

		}
		// 试用期限
		if (CollectionsUtil.exist(map, "trialPeriod") && !"".equals(map.get("trialPeriod")) && map.get("trialPeriod") != null) {
			if (flag == 0) {
				whereString += " where a.trialPeriod = ?";
				flag = 1;
			} else {
				whereString += " and a.trialPeriod = ?";
			}
			condition.add(map.get("trialPeriod"));
		}

		if (CollectionsUtil.exist(map, "pubCode") && !"".equals(map.get("pubCode")) && map.get("pubCode") != null) {
			if (flag == 0) {
				whereString += " where d.code like ?";
				flag = 1;
			} else {
				whereString += " and d.code like ?";
			}
			condition.add(map.get("pubCode") + "%");
		}
		if (CollectionsUtil.exist(map, "code") && !"".equals(map.get("code")) && map.get("code") != null) {
			if (flag == 0) {
				whereString += " where a.code like ? or d.code like ? ";
				flag = 1;
			} else {
				whereString += " and a.code like ? or d.code like ? ";
			}
			condition.add(map.get("code") + "%");
			condition.add(map.get("code") + "%");
		}
		if (CollectionsUtil.exist(map, "userName") && !"".equals(map.get("userName")) && map.get("userName") != null) {
			if (flag == 0) {
				whereString += " where b.name like ?";
				flag = 1;
			} else {
				whereString += " and b.name like ?";
			}
			condition.add(map.get("userName") + "%");
		}
		// 期刊，授权可继承
		if (CollectionsUtil.exist(map, "articleId") && !"".equals(map.get("articleId"))) {
			if (flag == 0) {
				whereString += " where (d.id = ? or d.publications.id=? or d.volume.id=? or d.issue.id=?)";
				flag = 1;
			} else {
				whereString += " and (d.id = ? or d.publications.id=? or d.volume.id=? or d.issue.id=?)";
			}
			condition.add(map.get("articleId").toString());
			condition.add(map.get("articleId").toString());
			condition.add(map.get("articleId").toString());
			condition.add(map.get("articleId").toString());
		}
		/**
		 * articleIds
		 */
		int flag2 = 0;
		if (CollectionsUtil.exist(map, "articleIds") && !"".equals(map.get("articleIds"))) {
			Map<String, Object> con = (Map<String, Object>) map.get("articleIds");
			String str2 = "";
			if (CollectionsUtil.exist(con, "id") && !"".equals(con.get("id"))) {
				if (flag2 == 0) {
					str2 += "  d.id = ? ";
					flag2 = 1;
				} else {
					str2 += " or d.id = ? ";
				}
				condition.add(con.get("id"));
			}
			if (CollectionsUtil.exist(con, "pId") && !"".equals(con.get("pId"))) {
				if (flag2 == 0) {
					str2 += "  d.id = ? ";
					flag2 = 1;
				} else {
					str2 += " or d.id = ? ";
				}
				condition.add(con.get("pId"));
			}
			if (CollectionsUtil.exist(con, "vId") && !"".equals(con.get("vId"))) {
				if (flag2 == 0) {
					str2 += "  d.id = ? ";
					flag2 = 1;
				} else {
					str2 += " or d.id = ? ";
				}
				condition.add(con.get("vId"));
			}
			if (CollectionsUtil.exist(con, "isId") && !"".equals(con.get("isId"))) {
				if (flag2 == 0) {
					str2 += "  d.id = ? ";
					flag2 = 1;
				} else {
					str2 += " or d.id = ? ";
				}
				condition.add(con.get("isId"));
			}
			if (!"".equals(str2.trim())) {
				if (flag == 0) {
					whereString += " where ( " + str2 + " ) ";
					flag = 1;
				} else {
					whereString += " and ( " + str2 + " ) ";
				}
			}
		}

		/**
		 * 判断License是否存在
		 */
		if (CollectionsUtil.exist(map, "userId") && !"".equals(map.get("userId")) && map.get("userId") != null && CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId")) && map.get("institutionId") != null && CollectionsUtil.exist(map, "level") && !"".equals(map.get("level")) && map.get("level") != null) {
			if (flag == 0) {
				whereString += " where (b.id = ? or ( c.id = ? and b.level = ? ))";
				flag = 1;
			} else {
				whereString += " and (b.id = ? or ( c.id = ? and b.level = ? ))";
			}
			condition.add(map.get("userId"));
			condition.add(map.get("institutionId"));
			condition.add(map.get("level"));
		} else if (CollectionsUtil.exist(map, "userId") && !"".equals(map.get("userId")) && map.get("userId") != null) {
			if (flag == 0) {
				whereString += " where b.id = ?";
				flag = 1;
			} else {
				whereString += " and b.id = ?";
			}
			condition.add(map.get("userId"));
		} else if (CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId")) && map.get("institutionId") != null && CollectionsUtil.exist(map, "level") && !"".equals(map.get("level")) && map.get("level") != null) {
			if (flag == 0) {
				whereString += " where c.id = ? and b.level = ?";
				flag = 1;
			} else {
				whereString += " and c.id = ? and b.level = ?";
			}
			condition.add(map.get("institutionId"));
			condition.add(map.get("level"));
		}
		// else{
		// if(flag==0){
		// whereString +=" where c.id = '0' ";
		// flag=1;
		// }else{
		// whereString+=" and c.id = '0' ";
		// }
		// }

		/**
		 * collectionId
		 */
		if (CollectionsUtil.exist(map, "collectionId") && !"".equals(map.get("collectionId")) && map.get("collectionId") != null) {
			if (flag == 0) {
				whereString += " where col.id = ? ";
				flag = 1;
			} else {
				whereString += " and col.id = ? ";
			}
			condition.add(map.get("collectionId"));
		}
		/**
		 * userIdIn
		 */
		if (CollectionsUtil.exist(map, "userIdIn") && !"".equals(map.get("userIdIn")) && map.get("userIdIn") != null) {
			String[] ary = map.get("userIdIn").toString().split(",");
			String userIdin = "";
			for (int i = 0; i < ary.length; i++) {
				userIdin += "?,";
				condition.add(ary[i]);
			}
			userIdin = userIdin.endsWith(",") ? userIdin.substring(0, userIdin.length() - 1) : userIdin;
			userIdin = "(" + userIdin + ")";
			if (flag == 0) {
				whereString += " where col.id in " + userIdin + " ";
				flag = 1;
			} else {
				whereString += " and col.id in " + userIdin + " ";
			}
		}
		/**
		 * collections
		 */
		if (CollectionsUtil.exist(map, "collections") && !"".equals(map.get("collections")) && map.get("collections") != null) {
			if (flag == 0) {
				whereString += " where d.id is null ";
				flag = 1;
			} else {
				whereString += " and d.id is null ";
			}
		}
		/**
		 * priceId
		 */
		if (CollectionsUtil.exist(map, "priceId") && !"".equals(map.get("priceId")) && map.get("priceId") != null) {
			if (flag == 0) {
				whereString += " where pp.id = ? ";
				flag = 1;
			} else {
				whereString += " and pp.id = ? ";
			}
			condition.add(map.get("priceId"));
		}

		/**
		 * pubParentId
		 */
		if (CollectionsUtil.exist(map, "pubParentId") && !"".equals(map.get("pubParentId")) && map.get("pubParentId") != null) {
			if (flag == 0) {
				whereString += " where e.id = ? ";
				flag = 1;
			} else {
				whereString += " and e.id = ? ";
			}
			condition.add(map.get("pubParentId"));
		}
		if (CollectionsUtil.exist(map, "pubType") && !"".equals(map.get("pubType"))) {
			if (flag == 0) {
				whereString += " where d.type = ?";
				flag = 1;
			} else {
				whereString += " and d.type = ?";
			}
			condition.add(map.get("pubType"));
		}
		if (CollectionsUtil.exist(map, "oafree") && !"".equals(map.get("oafree")) && map.get("oafree") != null) {
			if (flag == 0) {
				whereString += " where (d.oa = 2 or d.free=2) ";
				flag = 1;
			} else {
				whereString += " or (d.oa = 2 or d.free=2) ";
			}
		}
		if (CollectionsUtil.exist(map, "author") && !"".equals(map.get("author")) && map.get("author") != null) {

			if (flag == 0) {
				whereString += " where a.author != ? ";
				flag = 1;
			} else {
				whereString += " and a.author != ? ";
			}
			condition.add(map.get("author"));

		}
		if (CollectionsUtil.exist(map, "publisher") && !"".equals(map.get("publisher")) && map.get("publisher") != null) {

			if (flag == 0) {
				whereString += " where b.name != ? ";
				flag = 1;
			} else {
				whereString += " and b.name != ? ";
			}
			condition.add(map.get("publisher"));

		}
		if (CollectionsUtil.exist(map, "ppublisher") && !"".equals(map.get("ppublisher")) && map.get("ppublisher") != null) {

			if (flag == 0) {
				whereString += " where e.name != ? ";
				flag = 1;
			} else {
				whereString += " and e.name != ? ";
			}
			condition.add(map.get("ppublisher"));
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

		if (CollectionsUtil.exist(map, "isCn") && !"".equals(map.get("isCn"))) {
			Boolean isCn = map.get("isCn") != null && "true".equals(map.get("isCn").toString().toLowerCase());
			Object[] types = new Object[] { "chs", "chn", "cht" };
			String where_ = "";
			String _where = ")";
			String where = "";
			if (flag == 0) {
				where_ = " where d.lang ";
				flag = 1;
			} else {
				where_ = " and d.lang ";
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

	/**
	 * 获取等待列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LLicense> getList(Map<String, Object> condition, String sort) throws Exception {
		List<LLicense> list = null;
		String hql = " from LLicense a left join a.user b left join a.user.institution c left join a.publications d left join a.publications.publisher e left join a.collection col ";
		Map<String, Object> t = this.getWhere(condition);
		String property = "complicating, coefficient, coefficient1, publications.listPrice,isTrial,trialPeriod,readUrl,id,code,createdby,createdon,status,type,startTime,endTime,licenseUId,licensePwd,accessType,accessUIdType,user.id,user.name,user.institution.id,user.institution.name,publications.id,publications.code,publications.title," + "publications.author,publications.remark,publications.type,publications.createOn,publications.pubDate,publications.pubSubject,publications.publisher.id,publications.publisher.name,publications.pubSubjectEn,publications.local" + ",collection.id,collection.code,collection.name,collection.price,collection.currency,collection.createOn,collection.desc" + ",publications.createDate";
		String field = "a.complicating, a.coefficient, a.coefficient1, d.listPrice,a.isTrial,a.trialPeriod,a.readUrl,a.id,a.code,a.createdby,a.createdon,a.status,a.type,a.startTime,a.endTime,a.licenseUId,a.licensePwd,a.accessType,a.accessUIdType,b.id,b.name,c.id,c.name,d.id,d.code,d.title,d.author,d.remark,d.type,d.createOn,d.pubDate,d.pubSubject,e.id,e.name,d.pubSubjectEn,d.local" + ",col.id,col.code,col.name,col.price,col.currency,col.createOn,col.desc" + ",d.createDate";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, LLicense.class.getName());
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
	public List<LLicense> getPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<LLicense> list = null;
		String hql = " from LLicense a left join a.user b left join a.user.institution c left join a.publications d left join d.publisher e left join a.collection col ";
		Map<String, Object> t = this.getWhere(condition);
		String property = "isTrial,trialPeriod,id,code,createdby,createdon,status,type,startTime,endTime,licenseUId,licensePwd,accessType,accessUIdType,user.id,user.name,publications.type,publications.publications.id,publications.id,publications.code,publications.sisbn,publications.eissn,publications.available,publications.title,publications.author,publications.pubDate,publications.cover,publications.publisher.name,publications.publisher.nameEn" + ",collection.id,collection.code,collection.name,collection.price,collection.currency,collection.createOn,collection.desc";
		String field = "a.isTrial,a.trialPeriod,a.id,a.code,a.createdby,a.createdon,a.status,a.type,a.startTime,a.endTime,a.licenseUId,a.licensePwd,a.accessType,a.accessUIdType,b.id,b.name,d.type,d.id,d.publications.id,d.code,d.sisbn,d.eissn,d.available,d.title,d.author,d.pubDate,d.cover,e.name,e.nameEn" + ",col.id,col.code,col.name,col.price,col.currency,col.createOn,col.desc";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, LLicense.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<LLicense> getCartPagingListDown(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<LLicense> list = null;
		Map<String, Object> t = this.getWhere(condition);
		String property = "publications.id";
		String field = "a.publications.id";
		try {

			// SELECT a.PUB_ID,count(*) as eachCount
			String hql = "from LLicense a where EXISTS (" + "select 1 from LLicense b where a.user.id=b.user.id AND EXISTS(SELECT 1 FROM PCsRelation c WHERE b.publications.id=c.publications.id AND EXISTS(SELECT 1 FROM BSubject d WHERE c.subject.id=d.id and d.code like '" + condition.get("mark") + "%'))" + ")";
			list = super.hibernateDao.getListByHql(property, field, hql, ((List<Object>) t.get("condition")).toArray(), sort, LLicense.class.getName(), pageCount, page * pageCount);
			return list;
		} catch (Exception e) {
			throw e;
		}
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
		List<LLicense> list = null;
		Map<String, Object> t = this.getWhere(condition);
		String hql = " from LLicense a left join a.user b left join a.user.institution c left join a.publications d left join a.collection col";
		try {
			list = this.hibernateDao.getListByHql("id", "cast(count(*) as string)", hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), "", LLicense.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list == null ? 0 : Integer.valueOf(list.get(0).getId());
	}

	@SuppressWarnings("unchecked")
	public Integer getResouceCount(Map<String, Object> condition) throws Exception {
		List<LLicense> list = null;
		Map<String, Object> t = this.getWhere(condition);
		String hql = " from LLicense a left join a.user b left join a.user.institution c left join a.publications d left join a.collection col";
		try {
			list = this.hibernateDao.getListByHql("id", "cast(count(*) as string)", hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), "", LLicense.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list == null ? 0 : Integer.valueOf(list.get(0).getId());
	}

	public Integer getAllCount(Map<String, Object> condition) throws Exception {
		List<LLicense> list = null;
		List<Object> con = new ArrayList<Object>();
		String hql = " from LLicense a left join a.user b left join a.user.institution c left join a.publications d ";
		String where = " where a.status = ? ";
		con.add(condition.get("status"));
		if (CollectionsUtil.exist(condition, "userid") && condition.get("userid") != null && CollectionsUtil.exist(condition, "institutionId") && condition.get("institutionId") != null) {
			where += " and (b.id = ? or ( c.id = ? and b.level = ? ))";
			con.add(condition.get("userid"));
			con.add(condition.get("institutionId"));
			con.add(condition.get("level"));
		} else if (CollectionsUtil.exist(condition, "userid") && condition.get("userid") != null && !(CollectionsUtil.exist(condition, "institutionId") && condition.get("institutionId") != null)) {
			where += " and b.id = ? ";
			con.add(condition.get("userid"));
		} else if (!(CollectionsUtil.exist(condition, "userid") && condition.get("userid") != null) && CollectionsUtil.exist(condition, "institutionId") && condition.get("institutionId") != null) {
			where += " and c.id = ? and b.level = ? ";
			con.add(condition.get("institutionId"));
			con.add(condition.get("level"));
		}
		hql += where;
		try {
			list = this.hibernateDao.getListByHql("id", "cast(count(*) as string)", hql, con.toArray(), "", LLicense.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list == null ? 0 : Integer.valueOf(list.get(0).getId());
	}

	@SuppressWarnings("unchecked")
	public List<LLicense> getAllPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<LLicense> list = null;
		List<Object> con = new ArrayList<Object>();
		String hql = " from LLicense a left join a.user b left join a.user.institution c left join a.publications d ";
		String where = " where a.status = ? and (d.oa=2 or d.free=2) ";
		con.add(condition.get("status"));
		if (CollectionsUtil.exist(condition, "oafree") && condition.get("oafree") != null) {
			where += " and (d.oa=2 or d.free=2)";
		}
		if (CollectionsUtil.exist(condition, "userid") && condition.get("userid") != null && CollectionsUtil.exist(condition, "institutionId") && condition.get("institutionId") != null) {
			where += " and (b.id = ? or ( c.id = ? and b.level = ? ))";
			con.add(condition.get("userid"));
			con.add(condition.get("institutionId"));
			con.add(condition.get("level"));
		} else if (CollectionsUtil.exist(condition, "userid") && condition.get("userid") != null && !(CollectionsUtil.exist(condition, "institutionId") && condition.get("institutionId") != null)) {
			where += " and b.id = ? ";
			con.add(condition.get("userid"));
		} else if (!(CollectionsUtil.exist(condition, "userid") && condition.get("userid") != null) && CollectionsUtil.exist(condition, "institutionId") && condition.get("institutionId") != null) {
			where += " and c.id = ? and b.level = ? ";
			con.add(condition.get("institutionId"));
			con.add(condition.get("level"));
		}

		hql += where;
		String property = " isTrial,id,code,createdby,createdon,status,type,startTime,endTime,licenseUId,licensePwd,accessType,accessUIdType,user.id,user.name,publications.id,publications.code,publications.title ";
		String field = " a.isTrial,a.id,a.code,a.createdby,a.createdon,a.status,a.type,a.startTime,a.endTime,a.licenseUId,a.licensePwd,a.accessType,a.accessUIdType,b.id,b.name,d.id,d.code,d.title ";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql, con.toArray(), sort, LLicense.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	/**
	 * 为购买期刊的时候查询是否可以购买
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LLicense> getLicenseForAddCart(Map<String, Object> condition) throws Exception {
		List<LLicense> list = null;
		String hql = " from LLicense a left join a.user b left join a.user.institution c left join a.publications d left join a.pprice pp ";
		Map<String, Object> t = this.getWhere(condition);
		String property = "id";
		String field = "a.id";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), " ", LLicense.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	/**
	 * 获取试用资源总数
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer getTrialCount(Map<String, Object> condition) throws Exception {
		List<LLicense> list = null;
		Map<String, Object> t = this.getWhere(condition);
		String hql = " from LLicense a left join a.user.institution c left join a.publications d left join d.publisher p";
		try {
			list = this.hibernateDao.getListByHql("id", "cast(count(*) as string)", hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), "", LLicense.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list == null ? 0 : Integer.valueOf(list.get(0).getId());
	}

	public Integer getTrialCount1(Map<String, Object> condition) throws Exception {
		List<LLicense> list = null;
		Map<String, Object> t = this.getWhere(condition);
		String hql = " from LLicense a left join a.user.institution c left join a.publications d left join d.publisher e";
		try {
			list = this.hibernateDao.getListByHql("id", "cast(count(*) as string)", hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), "", LLicense.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list == null ? 0 : Integer.valueOf(list.get(0).getId());
	}

	public Integer getTrialCount2(Map<String, Object> condition) throws Exception {
		List<LLicense> list = null;
		Map<String, Object> t = this.getWhere(condition);
		String hql = " from LLicense a left join a.user.institution c left join a.publications d left join d.publisher e";
		try {
			list = this.hibernateDao.getListByHql("id", "cast(count(*) as string)", hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), "", LLicense.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list == null ? 0 : Integer.valueOf(list.get(0).getId());
	}

	/**
	 * 获取分页信息(用于首页显示)
	 * 
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LLicense> getPagingListForIndex(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<LLicense> list = null;
		String hql = " from LLicense a left join a.user.institution c left join a.publications d left join d.publisher e";
		Map<String, Object> t = this.getWhere(condition);
		String property = "publications.id,publications.lang,publications.startVolume,publications.endVolume,publications.pubDate,publications.author,publications.code,publications.title,publications.type,publications.sisbn,publications.hisbn," + "publications.remark,publications.cover,publications.publisher.id,publications.publisher.name ";
		String field = "d.id,d.lang,d.startVolume,d.endVolume,d.pubDate,d.author,d.code,d.title,d.type,d.sisbn,d.hisbn,d.remark,d.cover,e.id,e.name ";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, LLicense.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public List<LLicense> getPubValidLicense(PPublications pub, CUser user, String ip) throws Exception {
		if (pub == null || (user == null && (ip == null || "".equals(ip))))
			return null;
		List<LLicense> list = null;
		String hql = " from LLicense a left join a.user u left join u.institution i left join a.publications p ";
		List<Object> con = new ArrayList<Object>();

		con.add(pub.getId());
		con.add(pub.getCode());
		String whereString = " where a.status = 1 and (p.id=? or p.code=?) and (";

		Boolean flag = false;
		// 用户直接购买
		if (user != null) {
			whereString += " u.id=? ";
			con.add(user.getId());
			flag = true;
		}
		// 用户所属机构的管理员购买
		if (ip != null && !"".equals(ip)) {
			if (flag) {
				whereString += " or (exists(select ipr.id from BIpRange ipr where ipr.institution.id=i.id and ipr.sip<=? and ipr.eip>=?) and u.level=2) ";
			} else {
				whereString += " (exists(select ipr.id from BIpRange ipr where ipr.institution.id=i.id and ipr.sip<=? and ipr.eip>=?) and u.level=2) ";
			}
			con.add(IpUtil.getLongIp(ip));
			con.add(IpUtil.getLongIp(ip));
			flag = true;
		}
		// 当前ip所属的机构管理员购买
		if (ip != null && !"".equals(ip)) {
			if (flag) {
				whereString += " or (exists(select lip.id from LLicenseIp lip where lip.license.id=a.id and lip.sip<=? and lip.eip>=?) and u.level=2) ";
			} else {
				whereString += " (exists(select lip.id from LLicenseIp lip where lip.license.id=a.id and lip.sip<=? and lip.eip>=?) and u.level=2) ";
			}
			con.add(IpUtil.getLongIp(ip));
			con.add(IpUtil.getLongIp(ip));
			flag = true;
		}
		whereString += ")";
		String property = " id,code,createdby,createdon,status,type,startTime,endTime,licenseUId,licensePwd,accessType,accessUIdType,complicating,user.id,user.name,publications.id,publications.code,publications.title ";
		String field = " a.id,a.code,a.createdby,a.createdon,a.status,a.type,a.startTime,a.endTime,a.licenseUId,a.licensePwd,a.accessType,a.accessUIdType,a.complicating,u.id,u.name,p.id,p.code,p.title ";

		try {
			list = super.hibernateDao.getListByHql(property, field, hql + whereString, con.toArray(), " ", LLicense.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<LLicense> getLicenseStat() throws Exception {
		List<LLicense> list = null;
		String hql = " from LLicense a left join a.publications d ";

		String property = "id,type";
		String field = "d.id,cast(count(*) as int)";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql, null, " group by d.id ", LLicense.class.getName());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	private Map<String, Object> getWhereForCanRead(Map<String, Object> map) {
		Map<String, Object> table = new HashMap<String, Object>();
		String whereString = "";
		List<Object> condition = new ArrayList<Object>();
		int flag = 1;

		whereString += " where a.status=1 ";

		if (CollectionsUtil.exist(map, "userId") && !"".equals(map.get("userId")) && map.get("userId") != null && CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId")) && map.get("institutionId") != null && CollectionsUtil.exist(map, "level") && !"".equals(map.get("level")) && map.get("level") != null) {
			if (flag == 0) {
				whereString += " where (b.id = ? or ( c.id = ? and b.level = ? ) or (d.oa = 2 or d.free=2)) ";
				flag = 1;
			} else {
				whereString += " and (b.id = ? or ( c.id = ? and b.level = ? ) or (d.oa = 2 or d.free=2))";
			}
			condition.add(map.get("userId"));
			condition.add(map.get("institutionId"));
			condition.add(map.get("level"));
		} else if (CollectionsUtil.exist(map, "userId") && !"".equals(map.get("userId")) && map.get("userId") != null) {
			if (flag == 0) {
				whereString += " where (b.id = ?  or (d.oa = 2 or d.free=2))";
				flag = 1;
			} else {
				whereString += " and (b.id = ?  or (d.oa = 2 or d.free=2)) ";
			}
			condition.add(map.get("userId"));
		} else if (CollectionsUtil.exist(map, "institutionId") && !"".equals(map.get("institutionId")) && map.get("institutionId") != null && CollectionsUtil.exist(map, "level") && !"".equals(map.get("level")) && map.get("level") != null) {
			if (flag == 0) {
				whereString += " where ((c.id = ? and b.level = ?)   or (d.oa = 2 or d.free=2))";
				flag = 1;
			} else {
				whereString += " and ((c.id = ? and b.level = ?) or (d.oa = 2 or d.free=2))";
			}
			condition.add(map.get("institutionId"));
			condition.add(map.get("level"));
		}

		if (CollectionsUtil.exist(map, "pubType") && !"".equals(map.get("pubType")) && map.get("pubType") != null) {
			if (flag == 0) {
				whereString += " where d.type = ?";
				flag = 1;
			} else {
				whereString += " and d.type = ?";
			}
			condition.add(map.get("pubType"));
		}

		if (CollectionsUtil.exist(map, "publisherName") && !"".equals(map.get("publisherName")) && map.get("publisherName") != null) {
			if (flag == 0) {
				whereString += " where d.publisher.name is not null";
				flag = 1;
			} else {
				whereString += " and d.publisher.name is not null";
			}
		}

		if (CollectionsUtil.exist(map, "trypublisherName") && !"".equals(map.get("trypublisherName")) && map.get("trypublisherName") != null) {
			if (flag == 0) {
				whereString += " where e.name is not null";
				flag = 1;
			} else {
				whereString += " and e.name is not null";
			}
		}
		if (CollectionsUtil.exist(map, "startVolum") && !"".equals(map.get("startVolum")) && map.get("startVolum") != null) {
			if (flag == 0) {
				whereString += " where d.startVolume is not null";
				flag = 1;
			} else {
				whereString += " and d.startVolume is not null";
			}
		}

		if (CollectionsUtil.exist(map, "endVolum") && !"".equals(map.get("endVolum")) && map.get("endVolum") != null) {
			if (flag == 0) {
				whereString += " where d.endVolume is not null";
				flag = 1;
			} else {
				whereString += " and d.endVolume is not null";
			}
		}

		if (CollectionsUtil.exist(map, "available") && !"".equals(map.get("available"))) {// 这是对一个available的条件
			if (flag == 0) {
				whereString += " where ( d.available <> ?  or d.available is null )  ";
				flag = 1;
			} else {
				whereString += " and  ( d.available <> ?  or d.available is null ) ";
			}
			condition.add(map.get("available"));
		}

		table.put("where", whereString);
		table.put("condition", condition);
		return table;
	}

	@SuppressWarnings("unchecked")
	public List<LLicense> getCanReadPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<LLicense> list = null;
		String hql = " from LLicense a left join a.user b left join a.user.institution c left join a.publications d left join a.collection col left join d.publisher e ";
		Map<String, Object> t = this.getWhereForCanRead(condition);
		String property = "isTrial,trialPeriod,id,code,createdby,createdon,status,type,startTime,endTime,licenseUId,licensePwd,accessType,accessUIdType,user.id,user.name,publications.id,publications.code,publications.available,publications.title,publications.startVolume,publications.endVolume,publications.publisher.name" + ",collection.id,collection.code,collection.name,collection.price,collection.currency,collection.createOn,collection.desc";
		String field = "a.isTrial,a.trialPeriod,a.id,a.code,a.createdby,a.createdon,a.status,a.type,a.startTime,a.endTime,a.licenseUId,a.licensePwd,a.accessType,a.accessUIdType,b.id,b.name,d.id,d.code,d.available,d.title,d.startVolume,d.endVolume,e.name" + ",col.id,col.code,col.name,col.price,col.currency,col.createOn,col.desc";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql + t.get("where").toString(), ((List<Object>) t.get("condition")).toArray(), sort, LLicense.class.getName(), pageCount, page * pageCount);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<LLicense> getPubInCollection(Map<String, Object> condition, String sort) throws Exception {
		List<LLicense> list = null;
		String property = "publications.id,publications.title";
		String field = "b.id,b.title,";
		Map<String, Object> t = this.getWhere(condition);
		try {
			String hql = " from LLicense a left join a.publications b where b.id in(select c.publications.id from PCcRelation c where c.collection.id='" + condition.get("colId") + "') and ((a.user.id='" + condition.get("u_id") + "' and a.status=1) or b.free=2 or b.oa=2) ";
			list = super.hibernateDao.getListByHql(property, field, hql, ((List<Object>) t.get("condition")).toArray(), sort, LLicense.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public boolean getLLicenseFlag(String id) throws Exception {
		boolean flag = false;
		List<LLicense> list = null;
		String hql = " from LLicense a where pub_id='" + id + "'";
		String property = "endTime";
		String field = "a.endTime";
		try {
			list = super.hibernateDao.getListByHql(property, field, hql, null, null, LLicense.class.getName());
			if (list.size() > 0) {
				Date endTime = list.get(0).getEndTime();
				Date todayTime = new Date();
				if(endTime!=null && !endTime.equals("")){
					flag = endTime.before(todayTime) ? true : false;
				}
				
			}

		} catch (Exception e) {
			throw e;
		}
		return flag;
	}
}
