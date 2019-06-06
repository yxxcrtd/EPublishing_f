package cn.digitalpublishing.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.Param;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.BIpRange;
import cn.digitalpublishing.ep.po.CAccount;
import cn.digitalpublishing.ep.po.CFavourites;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.CUserAlerts;
import cn.digitalpublishing.ep.po.CUserProp;
import cn.digitalpublishing.ep.po.CUserType;
import cn.digitalpublishing.ep.po.CUserTypeProp;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.LLicenseIp;
import cn.digitalpublishing.ep.po.LUserAlertsLog;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.ep.po.UPRelation;
import cn.digitalpublishing.ep.po.UPayment;
import cn.digitalpublishing.service.CustomService;
import cn.digitalpublishing.util.web.DateUtil;

public class CustomServiceImpl extends BaseServiceImpl implements CustomService {

	@Override
	public void insertFavourites(CFavourites obj) throws Exception {
		try {
			this.daoFacade.getcFavouritesDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "favourites.info.add.error", e);// 新增收藏信息失败！
		}
	}

	public CFavourites getFavourite(Map<String, Object> condition) throws Exception {
		CFavourites obj = null;
		try {
			obj = (CFavourites) this.daoFacade.getcFavouritesDao().getcFavourite(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "favourites.info.get.error", e);// 获取收藏信息失败！
		}
		return obj;
	}

	@Override
	public void insertCUser(CUser obj) throws Exception {
		try {
			this.daoFacade.getcUserDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "user.info.add.error", e);// 新增用户信息失败！
		}
	}

	@Override
	public void insertCUserProp(CUserProp obj) throws Exception {
		try {
			this.daoFacade.getCUserPropDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "userProp.info.add.error", e);// 新增用户属性信息失败！
		}
	}

	@Override
	public List<CUserTypeProp> getUserTypePropList(Map<String, Object> condition, String sort) throws Exception {
		List<CUserTypeProp> list = null;
		try {
			list = this.daoFacade.getcUserTypePropDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "TypeProp.info.list.error", e);// 获取用户类型属性信息列表失败！
		}
		return list;
	}

	public CUser getUser(String id) throws Exception {
		CUser obj = null;
		try {
			obj = (CUser) this.daoFacade.getcUserDao().get(CUser.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "user.info.get.error", e);// 获取用户信息失败！
		}
		return obj;
	}

	public CAccount getAccount(Map<String, Object> condition) throws Exception {
		CAccount obj = null;
		try {
			List<CAccount> list = (List<CAccount>) this.daoFacade.getcAccountDao().getList(condition, "");
			if (list != null && !list.isEmpty()) {
				obj = list.get(0);
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "account.info.get.error", e);// 获取用户登陆信息失败！
		}
		return obj;
	}

	@Override
	public CUserTypeProp getUserTypePropById(String id) throws Exception {
		CUserTypeProp obj = null;
		try {
			obj = (CUserTypeProp) this.daoFacade.getcUserTypeDao().get(CUserTypeProp.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "userProp.info.get.error", e);// 获取用户类型属性信息失败！
		}
		return obj;
	}

	@Override
	public void insertAccount(CAccount obj) throws Exception {
		try {
			this.daoFacade.getcAccountDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "account.info.add.error", e);// 新增账户信息失败！
		}
	}

	@Override
	public CAccount getAccountById(String id) throws Exception {
		CAccount obj = null;
		try {
			obj = (CAccount) this.daoFacade.getcAccountDao().get(CAccount.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "account.info.get.error", e);// 获取账户信息失败！
		}
		return obj;
	}

	@Override
	public void updateAccount(CAccount obj, String id, String[] properties) throws Exception {
		try {
			this.daoFacade.getcAccountDao().update(obj, CAccount.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "identity.info.update.error", e);// 修改用户信息失败！
		}

	}

	@Override
	public void updateUser(CUser obj, String id, String[] properties) throws Exception {
		try {
			this.daoFacade.getcUserDao().update(obj, CUser.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "identity.info.update.error", e);// 修改用户信息失败！
		}

	}

	@Override
	public boolean checkEmailExist(String userId, String email) throws Exception {
		boolean existEmail = false;
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("code", "email");
			condition.put("val", email);
			//condition.put("userId", userId);
			int count = this.daoFacade.getCUserPropDao().getCount(condition);
			if (0 > count) {
				existEmail = true;
			}

			List<CUserProp> list = this.daoFacade.getCUserPropDao().getList(condition, null);
			CUserProp userProp = null;
			if (list != null && list.size() != 0) {
				userProp = list.get(0);
				if (userId == null) {// 新注册用户
					if (count > 0) {
						existEmail = true;
					}
				} else {// 修改用户信息
					if (!userProp.getUser().getId().equals(userId)) {// 邮箱信息变化
						if (count > 0) {
							existEmail = true;
						}
					} else {// 邮箱信息为变化
						if (count > 1) {
							existEmail = true;
						}
					}
				}
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "userProp.info.check.email.error", e);// 验证用户邮箱唯一失败！
		}
		return existEmail;
	}

	public List<CUserProp> getUserPropList(Map<String, Object> condition, String sort) throws Exception {
		List<CUserProp> list = null;
		try {
			list = this.daoFacade.getCUserPropDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "userProp.list.get.error", e);// 获取用户属性列表失败！
		}
		return list;
	}

	@Override
	public boolean checkUidExist(String userId, String Uid) throws Exception {
		boolean existUid = false;
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("uid", Uid);
			int count = this.daoFacade.getcAccountDao().getCount(condition);
			if (userId == null) {// 新注册用户
				if (count > 0) {
					existUid = true;
				}
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "account.info.check.uid.error", e);// 验证账号唯一失败！
		}
		return existUid;
	}

	@Override
	public List<CAccount> getAccountList(Map<String, Object> condition, String sort) throws Exception {
		List<CAccount> list = null;
		try {
			list = this.daoFacade.getcAccountDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Account.info.list.error", e);// 获取账户信息列表失败！
		}
		return list;
	}

	@Override
	public List<BInstitution> getInstitutionList(Map<String, Object> condition, String sort) throws Exception {

		List<BInstitution> list = null;
		try {
			list = this.daoFacade.getbInstitutionDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "Institution.info.list.error", e);// 获取机构信息列表失败！
		}
		return list;

	}

	@Override
	public BInstitution getInstitution(String id) throws Exception {
		BInstitution obj = null;
		try {
			obj = (BInstitution) this.daoFacade.getbInstitutionDao().get(BInstitution.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Institution.info.get.error", e);// 获取机构信息失败！
		}
		return obj;
	}

	@Override
	public void updateCUserProp(CUserProp obj, String id, String[] properties) throws Exception {
		try {
			this.daoFacade.getCUserPropDao().update(obj, CUserProp.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "userProp.info.update.error", e);// 修改用户信息失败！
		}
	}

	@Override
	public CUserProp getUserPropById(String id) throws Exception {
		CUserProp obj = null;
		try {
			obj = (CUserProp) this.daoFacade.getCUserPropDao().get(CUserProp.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "userProp.info.get.error", e);// 获取用户属性信息失败！
		}
		return obj;
	}

	@Override
	public CUserType getUserTypeByCode(String code) throws Exception {
		CUserType obj = null;
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("code", code);
			List<CUserType> list = this.daoFacade.getcUserTypeDao().getList(condition, null);
			if (list != null && list.size() != 0) {
				obj = list.get(0);
			} else {
				throw new CcsException("userType.info.not.exist");// 获取此编码值的用户属性信息存在！
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "userType.info.get.error", e);// 获取用户属性信息失败！
		}
		return obj;
	}

	@Override
	public List<CUser> getUserPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<CUser> list = null;
		try {
			list = this.daoFacade.getcUserDao().getPagingList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "user.info.pagelist.error", e);// 获取用户信息分页列表失败！
		}
		return list;
	}

	@Override
	public List<CAccount> getAccountPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<CAccount> list = null;
		try {
			list = this.daoFacade.getcAccountDao().getPagingList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "account.info.pagelist.error", e);// 获取账户信息分页列表失败！
		}
		return list;
	}

	@Override
	public Integer getAccountCount(Map<String, Object> condition, String sort) throws Exception {
		int count = 0;
		try {
			count = this.daoFacade.getcAccountDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "account.info.list.count", e);// 获取账户信息总数失败！
		}
		return count;
	}

	@Override
	public void insertLicense(LLicense obj) throws Exception {
		try {
			this.daoFacade.getlLicenseDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "license.info.add.error", e);// 新增订阅信息失败！
		}
	}

	@Override
	public void insertLicenseIp(LLicenseIp obj) throws Exception {
		try {
			this.daoFacade.getlLicenseIpDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "licenseip.info.add.error", e);// 新增订阅IP信息失败！
		}
	}

	@Override
	public List<LLicense> getLicenseList(Map<String, Object> condition, String sort) throws Exception {
		List<LLicense> list = null;
		try {
			list = this.daoFacade.getlLicenseDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "License.info.list.error", e);// 获取订阅信息列表失败！
		}
		return list;
	}

	@Override
	public LLicense getLicense(String id) throws Exception {
		LLicense obj = null;
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("lId", id);
			List<LLicense> list = this.getLicenseList(condition, "");
			if (list != null && list.size() > 0) {
				obj = list.get(0);
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "License.info.get.error", e);// 获取订阅信息失败！
		}
		return obj;
	}

	@Override
	public List<LLicense> getLicensePagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<LLicense> list = null;
		try {
			list = this.daoFacade.getlLicenseDao().getPagingList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "License.info.pagelist.error", e);// 获取订阅信息分页列表失败！
		}
		return list;
	}

	public List<LLicense> getCartPagingListDown(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {

		try {
			return this.daoFacade.getlLicenseDao().getCartPagingListDown(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Integer getLicenseCount(Map<String, Object> condition) throws Exception {
		int count = 0;
		try {
			count = this.daoFacade.getlLicenseDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "License.info.list.count", e);// 获取订阅信息总数失败！
		}
		return count;
	}

	@Override
	public Integer getLicenseResourceCount(Map<String, Object> condition) throws Exception {
		int count = 0;
		try {
			count = this.daoFacade.getlLicenseDao().getResouceCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "License.info.list.count", e);// 获取订阅信息总数失败！
		}
		return count;
	}

	@Override
	public Integer getAllLicenseCount(Map<String, Object> condition, String sort) throws Exception {
		int count = 0;
		try {
			count = this.daoFacade.getlLicenseDao().getAllCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "License.info.list.count", e);// 获取订阅信息总数失败！
		}
		return count;
	}

	@Override
	public List<LLicense> getAllLicensePagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<LLicense> list = null;
		try {
			list = this.daoFacade.getlLicenseDao().getAllPagingList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "License.info.pagelist.error", e);// 获取订阅信息分页列表失败！
		}
		return list;
	}

	@Override
	public List<LLicenseIp> getLicenseIpList(Map<String, Object> condition, String sort) throws Exception {
		List<LLicenseIp> list = null;
		try {
			list = this.daoFacade.getlLicenseIpDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "LicenseIp.info.list.error", e);// 获取订阅IP信息列表失败！
		}
		return list;
	}

	@Override
	public List<LLicenseIp> getLicenseIpPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<LLicenseIp> list = null;
		try {
			list = this.daoFacade.getlLicenseIpDao().getPagingList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "LicenseIp.info.pagelist.error", e);// 获取订阅IP信息分页列表失败！
		}
		return list;
	}

	@Override
	public Integer getLicenseIpCount(Map<String, Object> condition) throws Exception {
		int count = 0;
		try {
			count = this.daoFacade.getlLicenseIpDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "LicenseIp.info.list.count", e);// 获取订阅IP信息总数失败！
		}
		return count;
	}

	@Override
	public void handleLicenseIp(String instId) throws Exception {
		try {
			// 1.删除订阅IP
			this.daoFacade.getlLicenseIpDao().deleteByInsId(instId);
			// 2.获取机构订阅
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("institutionId", instId);
			condition.put("isTrail", "0");
			condition.put("level", 2);
			List<LLicense> licenseList = this.daoFacade.getlLicenseDao().getList(condition, "");
			// 3.获取机构IP
			List<BIpRange> ipRangeList = this.daoFacade.getbIpRangeDao().getList(condition, "");
			// 4.写入订阅IP
			if (licenseList != null && !licenseList.isEmpty() && ipRangeList != null && !ipRangeList.isEmpty()) {
				for (LLicense license : licenseList) {
					for (BIpRange ipRange : ipRangeList) {
						LLicenseIp licenseIp = new LLicenseIp();
						licenseIp.setLicense(license);
						licenseIp.setEip(ipRange.getEip());
						licenseIp.setEndIp(ipRange.getEndIp());
						licenseIp.setSip(ipRange.getSip());
						licenseIp.setStartIp(ipRange.getStartIp());
						this.daoFacade.getlLicenseIpDao().insert(licenseIp);
					}
				}
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "LicenseIp.info.update.in.bulk.error", e);// 批量更新订阅IP失败！
		}
	}

	@Override
	public List<CUserAlerts> getCUserAlertsList(Map<String, Object> condition, String sort) throws Exception {
		List<CUserAlerts> list = null;
		try {
			list = this.daoFacade.getCUserAlertsDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "alerts.info.list.error", e);// 获取提醒信息列表失败！
		}
		return list;
	}

	@Override
	public void insertUserAlerts(CUserAlerts obj) throws Exception {
		try {
			this.daoFacade.getCUserAlertsDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "alerts.info.add.error", e);// 新增订阅提醒信息失败！
		}
	}

	@Override
	public void updateUserAlerts(CUserAlerts obj) throws Exception {
		try {
			this.daoFacade.getCUserAlertsDao().update(obj, CUserAlerts.class.getName(), obj.getId(), null);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "alerts.info.add.error", e);// 新增订阅提醒信息失败！
		}
	}

	@Override
	public void deleteCUserAlerts(String alertsId) throws Exception {
		try {
			this.daoFacade.getCUserAlertsDao().delete(CUserAlerts.class.getName(), alertsId);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "alerts.info.delete.error", e);// 删除订阅提醒信息失败！
		}
	}

	@Override
	public void deleteAlertsByTreeCode(String treeCode, String userId) throws Exception {
		try {
			this.daoFacade.getCUserAlertsDao().deleteAlertsByTreeCode(treeCode, userId);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "alerts.info.delete.treeCode.error", e);// 删除订阅提醒信息失败！
		}
	}

	@Override
	public void AddTrials(Object[] pubs, Object[] users, Integer period, String createBy, Integer accessType, String accessName, String accessPassword) throws Exception {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("status", 1);
			for (Object uid : users) {
				for (Object pid : pubs) {
					condition.put("userid", uid);
					condition.put("pubId", pid);
					Integer isExist = this.daoFacade.getlLicenseDao().getCount(condition);
					if (isExist == 0) {// 不存在同用户同出版物的有效License
						Date sdate = new Date();
						Date edate = null;
						CUser user = (CUser) this.daoFacade.getcUserDao().get(CUser.class.getName(), uid.toString());
						PPublications pub = (PPublications) this.daoFacade.getpPublicationsDao().get(PPublications.class.getName(), pid.toString());
						LLicense ll = new LLicense();
						ll.setPublications(pub);
						if (pub.getLocal() == 2) {// 本地资源
							String accessUrl = Param.getParam("system.config").get("accessUrl");
							accessUrl = accessUrl.replace("-", ":");
							ll.setReadUrl(accessUrl + "/pages/view/form/view?id=" + pub.getId());
						} else {
							ll.setReadUrl(pub.getWebUrl());
						}
						ll.setUser(user);
						ll.setAccessUIdType(user.getLevel() == 2 ? 2 : 1);
						ll.setCode(pub.getCode());
						ll.setCreatedby(createBy);
						ll.setCreatedon(sdate);
						ll.setStatus(1);// 有效
						ll.setType(2);// 限时授权
						ll.setIsTrial(1);// 试用
						ll.setStartTime(sdate);
						switch (period) {
						case 1:
							edate = DateUtil.getDealedDate(sdate, 0, 1, 0, 0, 0, 0);
							break;
						case 2:
							edate = DateUtil.getDealedDate(sdate, 0, 2, 0, 0, 0, 0);
							break;
						case 3:
							edate = DateUtil.getDealedDate(sdate, 0, 3, 0, 0, 0, 0);
							break;
						case 4:
							edate = DateUtil.getDealedDate(sdate, 0, 6, 0, 0, 0, 0);
							break;
						default:
							break;
						}
						ll.setEndTime(edate);
						ll.setTrialPeriod(period);
						ll.setComplicating(1);

						this.daoFacade.getlLicenseDao().insert(ll);// 插入试用授权记录
						if (user.getLevel() == 2) {// 机构管理员用户，写LicenseIp表
							Map<String, Object> condition1 = new HashMap<String, Object>();
							condition1.put("institutionId", user.getInstitution().getId());
							List<BIpRange> ipr = this.daoFacade.getbIpRangeDao().getList(condition1, "");
							if (ipr != null && ipr.size() > 0) {
								for (BIpRange ip : ipr) {
									LLicenseIp lip = new LLicenseIp();
									lip.setStartIp(ip.getStartIp());
									lip.setSip(ip.getSip());
									lip.setEndIp(ip.getEndIp());
									lip.setEip(ip.getEip());
									lip.setLicense(ll);
									this.daoFacade.getlLicenseIpDao().insert(lip);
								}
							}
						}

						LUserAlertsLog alert = new LUserAlertsLog();
						alert.setUserName(user.getName());
						alert.setUser(user);
						alert.setPublicationsName(pub.getTitle());
						alert.setIsbn(pub.getCode());
						alert.setEmail(user.getEmail());
						alert.setPublications(pub);
						alert.setAlertDate(DateUtil.getDateStr("yyyy-MM-dd", DateUtil.getUtilDate(DateUtil.getEndMonthDate(sdate, ll.getTrialPeriod() - 1), "yyyy-MM-dd")));
						alert.setAlertStatus(1);
						alert.setAlertType(1);// 1、续费提醒 2、最新上线订阅的期刊文章提醒
						alert.setCreatedon(new Date());
						this.daoFacade.getlUserAlertsLogDao().insert(alert);// 插入提醒记录
					}
				}
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Trial.Info.Add.Failed", e);// 添加试用信息失败
		}
	}

	public void UpdateTrials(String id, String pubId, String userId, Integer period, Integer accessType, String accessName, String accessPassword) throws Exception {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("lId", id);
			condition.put("isTrial", 1);// 必须指明是试用授权
			List<LLicense> list = this.getLicenseList(condition, "");
			if (list != null && list.size() == 1) {
				LLicense obj = list.get(0);
				if (obj.getIsTrial() == 1 && obj.getStatus() == 2) {// 该条试用信息已经失效
					throw new CcsException("Trial.Info.Update.Failed.Invalid");// 已失效不能修改
				}
				String oldUserId = obj.getUser().getId();
				String oldPubId = obj.getPublications().getId();
				CUser user = (CUser) this.daoFacade.getcUserDao().get(CUser.class.getName(), userId);
				PPublications pub = (PPublications) this.daoFacade.getpPublicationsDao().get(PPublications.class.getName(), pubId);
				obj.setPublications(pub);
				if (obj.getPublications().getLocal() == 2) {// 本地资源
					String accessUrl = Param.getParam("system.config").get("accessUrl");
					accessUrl = accessUrl.replace("-", ":");
					obj.setReadUrl(accessUrl + "/pages/view/form/view?id=" + obj.getPublications().getId());
				} else {
					obj.setReadUrl(obj.getPublications().getWebUrl());
				}
				obj.setUser(user);
				obj.setAccessUIdType(user.getLevel() == 2 ? 2 : 1);
				this.daoFacade.getlLicenseIpDao().deleteByLicenseId(obj.getId());// 删除LicenseIp
				if (user.getLevel() == 2) {// 若用户是机构管理员，重新写LicenseIp
					Map<String, Object> condition1 = new HashMap<String, Object>();
					condition1.put("institutionId", user.getInstitution().getId());
					List<BIpRange> ipr = this.daoFacade.getbIpRangeDao().getList(condition1, "");
					if (ipr != null && ipr.size() > 0) {
						for (BIpRange ip : ipr) {
							LLicenseIp lip = new LLicenseIp();
							lip.setStartIp(ip.getStartIp());
							lip.setSip(ip.getSip());
							lip.setEndIp(ip.getEndIp());
							lip.setEip(ip.getEip());
							lip.setLicense(obj);
							this.daoFacade.getlLicenseIpDao().insert(lip);
						}
					}
				}
				obj.setAccessType(accessType);
				obj.setLicenseUId(accessName);
				obj.setLicensePwd(accessPassword);
				if (obj.getTrialPeriod() != period) {
					obj.setTrialPeriod(period);
					switch (period) {
					case 1:
						obj.setEndTime(DateUtil.getDealedDate(obj.getStartTime(), 0, 1, 0, 0, 0, 0));
						break;
					case 2:
						obj.setEndTime(DateUtil.getDealedDate(obj.getStartTime(), 0, 2, 0, 0, 0, 0));
						break;
					case 3:
						obj.setEndTime(DateUtil.getDealedDate(obj.getStartTime(), 0, 3, 0, 0, 0, 0));
						break;
					case 4:
						obj.setEndTime(DateUtil.getDealedDate(obj.getStartTime(), 0, 6, 0, 0, 0, 0));
						break;
					default:
						break;
					}
				}
				this.daoFacade.getlLicenseDao().update(obj, LLicense.class.getName(), obj.getId(), null);
				condition.clear();
				condition.put("pubId", oldPubId);
				condition.put("userId", oldUserId);
				List<LUserAlertsLog> alertslist = this.daoFacade.getlUserAlertsLogDao().getList(condition, "");
				if (alertslist != null && alertslist.size() > 0) {
					LUserAlertsLog alert = alertslist.get(0);
					alert.setUserName(user.getName());
					alert.setUser(user);
					alert.setPublicationsName(pub.getTitle());
					alert.setIsbn(pub.getCode());
					alert.setEmail(user.getEmail());
					alert.setPublications(pub);
					alert.setAlertDate(DateUtil.getDateStr("yyyy-MM-dd", DateUtil.getUtilDate(DateUtil.getEndMonthDate(obj.getStartTime(), obj.getTrialPeriod() - 1), "yyyy-MM-dd")));
					alert.setAlertStatus(1);
					alert.setAlertType(pub.getType());
					this.daoFacade.getlUserAlertsLogDao().update(alert, LUserAlertsLog.class.getName(), alert.getId(), null);// 插入提醒记录
				}

			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Trial.Info.Update.Failed", e);// 修改试用授权信息失败
		}
	}

	public void CloseTrials(Object[] ids) throws Exception {
		try {
			if (ids != null && ids.length > 0) {
				Map<String, Object> condition = new HashMap<String, Object>();
				List<LLicense> list;
				condition.put("isTrial", 1);
				condition.put("status", 1);
				for (Object id : ids) {
					condition.put("lId", id);
					list = this.getLicenseList(condition, "");
					if (list != null && list.size() == 1) {
						list.get(0).setStatus(2);
						this.daoFacade.getlLicenseDao().update(list.get(0), LLicense.class.getName(), list.get(0).getId(), null);
					}
				}
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Trial.Info.Close.Failed", e);// 关闭试用授权信息失败
		}
	}

	@Override
	public void saveOrUpdateUPRelation(String operType, UPRelation obj) throws Exception {
		try {
			if ("0".equals(operType)) {// 全部删除
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("userTypeId", obj.getId());
				this.daoFacade.getUpRelationDao().deleteByCondtion(condition);
			} else if ("1".equals(operType)) {
				// 先删除，后增加
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("userTypeId", obj.getUserType().getId());
				this.daoFacade.getUpRelationDao().deleteByCondtion(condition);
				this.daoFacade.getUpRelationDao().insert(obj);
			} else {// if("2".equals(operType)){
					// 第二次操作 ,直接插入
				this.daoFacade.getUpRelationDao().insert(obj);
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "UPRelation.Info.add.Failed", e);// 保存失败！
		}
	}

	@Override
	public void updateLicense(LLicense obj, String id, String[] properties) throws Exception {
		try {
			this.daoFacade.getlLicenseDao().update(obj, LLicense.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "UPRelation.Info.add.Failed", e);// 保存失败！
		}
	}

	@Override
	public void saveOrUpdateUPayment(String operType, UPayment obj) throws Exception {
		try {
			if ("0".equals(operType)) {// 全部删除
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("uTypeId", obj.getId());
				this.daoFacade.getuPaymentDao().deleteByCondtion(condition);
			} else if ("1".equals(operType)) {
				// 先删除，后增加
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("uTypeId", obj.getUserType().getId());
				this.daoFacade.getuPaymentDao().deleteByCondtion(condition);
				this.daoFacade.getuPaymentDao().insert(obj);
			} else {// if("2".equals(operType)){
					// 第二次操作 ,直接插入
				this.daoFacade.getuPaymentDao().insert(obj);
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "UPayment.Info.handle.Failed", e);// 处理失败！
		}
	}

	@Override
	public List<UPayment> getPaymentList(Map<String, Object> condition, String sort) throws Exception {
		List<UPayment> list = null;
		try {
			list = this.daoFacade.getuPaymentDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "UPayment.List.get.Failed", e);// 保存失败！
		}
		return list;
	}

	@Override
	public Integer getTrialListCount(Map<String, Object> condition) throws Exception {
		int count = 0;
		try {
			count = this.daoFacade.getlLicenseDao().getTrialCount1(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "License.info.list.count", e);// 获取订阅信息总数失败！
		}
		return count;
	}

	@Override
	public List<LLicense> getLicensePagingListForIndex(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<LLicense> list = null;
		try {
			list = this.daoFacade.getlLicenseDao().getPagingListForIndex(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "License.info.pagelist.error", e);// 获取订阅信息分页列表失败！
		}
		return list;
	}

	@Override
	public List<LLicense> getCanReadLicensePagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<LLicense> list = null;
		try {
			list = this.daoFacade.getlLicenseDao().getCanReadPagingList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage() : "License.info.pagelist.error", e);// 获取订阅信息分页列表失败！
		}
		return list;
	}

	public List<LLicense> getPubInCollection(Map<String, Object> condition, String sort) throws Exception {
		try {
			return this.daoFacade.getlLicenseDao().getPubInCollection(condition, sort);
		} catch (Exception e) {
			throw e;
		}
	}
}
