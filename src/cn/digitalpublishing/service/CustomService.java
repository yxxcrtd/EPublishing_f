package cn.digitalpublishing.service;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.CAccount;
import cn.digitalpublishing.ep.po.CFavourites;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.CUserAlerts;
import cn.digitalpublishing.ep.po.CUserProp;
import cn.digitalpublishing.ep.po.CUserType;
import cn.digitalpublishing.ep.po.CUserTypeProp;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.LLicenseIp;
import cn.digitalpublishing.ep.po.UPRelation;
import cn.digitalpublishing.ep.po.UPayment;

public interface CustomService extends BaseService {
	/**
	 * 新增收藏信息
	 * @param obj
	 * @throws Exception
	 */
	public void insertFavourites(CFavourites obj)throws Exception;
	
	/**
	 * 根据出版物Id取收藏信息
	 * @param obj
	 * @throws Exception
	 */
	public CFavourites getFavourite(Map<String,Object> condition)throws Exception;
	/**
	 * 新增用户信息
	 * @param obj
	 * @throws Exception
	 */
	public void insertCUser(CUser obj) throws Exception;
	/**
	 * 新增用户属性
	 * @param obj
	 * @throws Exception
	 */
	public void insertCUserProp(CUserProp obj) throws Exception;

	
	/**
	 * 获取类型属性集合
	 * 根据用户类型获取相应的属性集合
	 * 类型：1，个人；2，机构
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<CUserTypeProp> getUserTypePropList(Map<String,Object> condition,String sort)throws Exception;

	/**
	 * 获取用户实体
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public CUser getUser(String id)throws Exception;
	
	/**
	 * 获取用户登陆信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CAccount getAccount(Map<String,Object> condition)throws Exception;

	
	/**
	 * 获取用户类型属性
	 * @param id
	 * @return
	 */
	public CUserTypeProp getUserTypePropById(String id) throws Exception;
	
	/**
	 * 新增账户信息
	 * @param obj
	 * @throws Exception
	 */
	public void insertAccount(CAccount obj) throws Exception;
	/**
	 * 获取账户信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CAccount getAccountById(String id)throws Exception;
	/**
	 * 修改账户信息
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updateAccount(CAccount obj, String id,String[] properties) throws Exception;
	/**
	 * 修改用户信息
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updateUser(CUser obj, String id,String[] properties)throws Exception;
	
	/**
	 * 验证用户注册邮箱的唯一约束
	 * @return
	 * @throws Exception
	 */
	public boolean checkEmailExist(String userId,String email)throws Exception;
	/**
	 * 验证用户注册账号的唯一约束
	 * @return
	 * @throws Exception
	 */
	public boolean checkUidExist(String userId,String Uid)throws Exception;
	/**
	 * 获取账户集合
	 * 注：gongguanghui 用此方法根据uid获取该账户信息，并通过user查询对应的email信息，提供找回密码功能。
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<CAccount> getAccountList(Map<String,Object> condition,String sort)throws Exception;
	/**
	 * 获取用户属性集合
	 * 注：gongguanghui 用此方法根据email获取该用户属性信息，并通过user查询对应的email信息，提供找回密码功能。
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<CUserProp> getUserPropList(Map<String,Object> condition,String sort)throws Exception;
	
	/**
	 * 获取机构信息列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<BInstitution> getInstitutionList(Map<String,Object> condition,String sort)throws Exception;
	/**
	 * 获取机构信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BInstitution getInstitution(String id) throws Exception ;
	
	/**
	 * 修改用户属性
	 * @param obj
	 * @throws Exception
	 */
	public void updateCUserProp(CUserProp obj, String id, String[] properties) throws Exception;
	/**
	 * 获取用户属性
	 * @param id
	 * @return
	 */
	public CUserProp getUserPropById(String id) throws Exception;
	
	/**
	 * 通过类别编码code获取用户类型信息
	 * 由于code在表中唯一，所以可以根据code获取对应的用户类型信息
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public CUserType getUserTypeByCode(String code)throws Exception;
	/**
	 * 获取用户信息分页列表
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<CUser> getUserPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception;
	/**
	 * 获取账户信息分页列表
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<CAccount> getAccountPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception;
	/**
	 * 获取账户信息总数
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public Integer getAccountCount(Map<String,Object> condition,String sort) throws Exception;
	/**
	 * 插入订阅信息
	 * @param obj
	 * @throws Exception
	 */
	public void insertLicense(LLicense obj)throws Exception;
	/**
	 * 插入订阅IP信息
	 * @param obj
	 * @throws Exception
	 */
	public void insertLicenseIp(LLicenseIp obj)throws Exception;
	/**
	 * 获取订阅集合
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LLicense> getLicenseList(Map<String,Object> condition,String sort)throws Exception;
	/**
	 * 获取订阅
	 * @param id
	 * @return
	 * @throws Exceptions
	 */
	public LLicense getLicense(String id)throws Exception;
	/**
	 * 获取订阅信息分页列表
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<LLicense> getLicensePagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception;
	/**
	 * 获取订阅信息总数
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public Integer getLicenseCount(Map<String,Object> condition) throws Exception;
	/**
	 * 获取订阅信息总数2
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public Integer getLicenseResourceCount(Map<String,Object> condition) throws Exception;
	/**
	 * 获取用户和用户所处机构的订阅信息分页列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LLicense> getAllLicensePagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception;
	/**
	 * 获取用户和用户所处机构的订阅信息总数
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public Integer getAllLicenseCount(Map<String, Object> condition, String sort)	throws Exception;
	/**
	 * 获取订阅IP集合
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LLicenseIp> getLicenseIpList(Map<String,Object> condition,String sort)throws Exception;
	/**
	 * 获取订阅IP信息分页列表
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<LLicenseIp> getLicenseIpPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception;
	/**
	 * 获取订阅IP信息总数
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public Integer getLicenseIpCount(Map<String,Object> condition) throws Exception;
	/**
	 * 处理LicenseIp
	 * @param instId
	 * @throws Exception
	 */
	public void handleLicenseIp(String instId)throws Exception;
	/**
	 * 新增用户订阅提醒
	 * @param obj
	 * @throws Exception
	 */
	public void insertUserAlerts(CUserAlerts obj) throws Exception;
	/**
	 * 修改用户订阅提醒
	 * @param obj
	 * @throws Exception
	 */
	public void updateUserAlerts(CUserAlerts obj) throws Exception;
	/**
	 * 获取用户订阅提醒集合
	 * 1.type-1，查询出版物信息订阅
	 * 2.type-2，查询主题学科信息订阅
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<CUserAlerts> getCUserAlertsList(Map<String,Object> condition,String sort) throws Exception;
	/**
	 * 删除用户订阅提醒信息
	 * @param alertsId
	 * @throws Exception
	 */
	public void deleteCUserAlerts(String alertsId) throws Exception;
	
	/**
	 * 根据subject的treeCode删除订阅提醒信息
	 * 订阅一级学科主题时，将已经订阅的下级学科主题先删除，然后进行订阅一级学科主题
	 * @param treeCode
	 * @throws Exception
	 */
	public void deleteAlertsByTreeCode(String treeCode ,String userId)throws Exception;
	/**
	 * 添加试用信息
	 * @param pubs
	 * @param users
	 * @param period
	 * @throws Exception
	 */
	public void AddTrials(Object[] pubs,Object[] users,Integer period,String createBy,Integer accessType,String accessName,String accessPassword)throws Exception;
	/**
	 * 更新试用信息
	 * @param id
	 * @param pubId
	 * @param userId
	 * @param period
	 * @throws Exception
	 */
	public void UpdateTrials(String id,String pubId,String userId,Integer period,Integer accessType,String accessName,String accessPassword)throws Exception;
	/**
	 * 关闭试用授权
	 * @param ids
	 * @throws Exception
	 */
	public void CloseTrials(Object[] ids)throws Exception;
	/**
	 * 保存或更新用户和价格类型关系
	 * @param operType
	 * @param obj
	 * @throws Exception
	 */
	public void saveOrUpdateUPRelation(String operType, UPRelation obj)throws Exception;

	/**
	 * 更新License
	 * @param obj
	 * @throws Exception
	 */
	public void updateLicense(LLicense obj,String id ,String[] properties)throws Exception;
	/**
	 * 保存或更新用户和支付方式关系
	 * @param operType
	 * @param obj
	 * @throws Exception
	 */
	public void saveOrUpdateUPayment(String operType, UPayment obj) throws Exception;
	/**
	 * 获取支付类型
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<UPayment> getPaymentList(Map<String,Object> condition ,String sort)throws Exception;
	/**
	 * 获取分页授权数据(首页显示)
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<LLicense> getLicensePagingListForIndex(Map<String, Object> condition,
			String sort, Integer pageCount, Integer page) throws Exception;
	/**
	 * 查询可阅读的license
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<LLicense> getCanReadLicensePagingList(Map<String, Object> condition,
			String sort, Integer pageCount, Integer page) throws Exception;

	public List<LLicense> getCartPagingListDown(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception;
	public List<LLicense> getPubInCollection(Map<String,Object> condition,String sort)throws Exception;
	
	public Integer getTrialListCount(Map<String, Object> condition)throws Exception;
}
