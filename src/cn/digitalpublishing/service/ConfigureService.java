package cn.digitalpublishing.service;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.BConfiguration;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.BIpRange;
import cn.digitalpublishing.ep.po.BPublisher;
import cn.digitalpublishing.ep.po.BToken;
import cn.digitalpublishing.ep.po.CUserProp;
import cn.digitalpublishing.ep.po.CUserType;
import cn.digitalpublishing.ep.po.CUserTypeProp;
import cn.digitalpublishing.ep.po.LUserAlertsLog;
import cn.digitalpublishing.ep.po.MMarkData;
import cn.digitalpublishing.ep.po.PPage;
import cn.digitalpublishing.ep.po.PPriceType;
import cn.digitalpublishing.po.BSource;



public interface ConfigureService extends BaseService {
	
	/**
	 * 获取出版商信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BPublisher getPublisher(String id)throws Exception;
	/**
	 * 写入出版商信息
	 * @param obj
	 * @throws Exception
	 */
	public void insertPublisher(BPublisher obj)throws Exception;
	/**
	 * 更新出版商信息
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updatePublisher(BPublisher obj,String id,String[] properties)throws Exception;
	/**
	 * 获取出版商列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<BPublisher> getPublisherList(Map<String,Object> condition,String sort)throws Exception;
	/**
	 * 获取分页列表
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<BPublisher> getPublisherPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception;
	/**
	 * 获取总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getPublisherCount(Map<String,Object> condition)throws Exception;

	/**
	 * 获取IP范围列表
	 * @param condition
	 * @param string
	 * @throws Exception
	 */
	public List<BIpRange> getIpRangeList(Map<String, Object> condition, String sort)throws Exception;
	
	/**
	 * 更新IP范围
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updateIpRange(BIpRange obj, String id, String[] properties)throws Exception;
	/**
	 * 插入IP范围
	 * @param obj
	 * @throws Exception
	 */
	public void insertIpRange(BIpRange obj)throws Exception;
	/**
	 * 删除IP范围
	 * @param id
	 * @throws Exception
	 */
	public void deleteIpRange(String id) throws Exception;
	/**
	 * 获取IP范围
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BIpRange getIpRange(String id) throws Exception;
	/**
	 * 获取机构信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BInstitution getInstitution(String id)throws Exception;
	/**
	 * 更新机构信息
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updateInstitution(BInstitution obj, String id, String[] properties)throws Exception;
	/**
	 * 插入机构信息
	 * @param obj
	 * @throws Exception
	 */
	public void insertInstitution(BInstitution obj)throws Exception;
	/**
	 * 删除IP范围
	 * @param id
	 * @throws Exception
	 */
	public void deleteInstitution(String id) throws Exception;
	/**
	 * 更新分页信息
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updatePage(PPage obj, String id, String[] properties)throws Exception;
	/**
	 * 插入分页信息
	 * @param obj
	 * @throws Exception
	 */
	public void insertPage(PPage obj)throws Exception;
	/**
	 * 删除分页信息
	 * @param condition
	 * @throws Exception
	 */
	public void deletePage(Map<String, Object> condition)throws Exception;
	/**
	 * 查询配置信息分页列表
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<BConfiguration> getConfigurePagingList(Map<String, Object> condition, String sort, int pageCount,int curpage)throws Exception;
	/**
	 * 查询配置信息的总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int getConfigureCount(Map<String, Object> condition)throws Exception;
	/**
	 * 查询配置信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BConfiguration getConfigure(String id)throws Exception;
	/**
	 * 修改基础配置信息
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updateConfigure(BConfiguration obj, String id,String[] properties)throws Exception;
	/**
	 * 添加基础配置信息
	 * @param obj
	 * @throws Exception
	 */
	public void addConfigure(BConfiguration obj)throws Exception;
	/**
	 * 获取基础配置列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<BConfiguration> getConfigureList(Map<String, Object> condition,String sort)throws Exception;
	/**
	 * 更新Mark数据
	 * @param obj
	 * @param id
	 * @param object
	 * @throws Exception
	 */
	public void updateMarkData(MMarkData obj, String id, String[] properties)throws Exception;
	/**
	 * 插入Mark数据
	 * @param obj
	 * @throws Exception
	 */
	public void insertMarkData(MMarkData obj)throws Exception;
	/**
	 * 获取机构列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<BInstitution> getInstitutionList(Map<String,Object> condition, String sort)throws Exception;
	/**
	 * 获取Mark数据总数量
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int getMarkDataCount(Map<String, Object> condition)throws Exception;
	/**
	 * 获取Mark数据分页列表
	 * @param condition
	 * @param string
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<MMarkData> getMarkDataPagingList(Map<String, Object> condition,
			String string, int pageCount, int curpage)throws Exception;
	/**
	 * 根据Id获取Mark数据信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MMarkData getMarkData(String id)throws Exception;
	/**
	 * 删除Mark数据
	 * @param id
	 * @throws Exception
	 */
	public void deleteMarkData(String id)throws Exception;
	public List<BPublisher> getPublisherStatistic(Map<String, Object> condition)throws Exception;
	
	/*
	 * 更新价格类型信息
	 */
	public void updatePriceType(PPriceType obj,String id,String[] properties)throws Exception;
	public List<PPriceType> getPriceTypeList(Map<String, Object> condition, String sort)throws Exception;
	public PPriceType getPriceType(String id)throws Exception;
	public void insertPriceType(PPriceType obj)throws Exception;
	public void deletePriceType(String id)throws Exception;
	
	public List<CUserType> getUserTypeList(Map<String, Object> condition, String sort)throws Exception;
	
	public void updateUserType(CUserType obj,String id,String[] properties)throws Exception;
	public List<CUserTypeProp> getUserTypeProp(Map<String, Object> condition, String sort)throws Exception;
	public void updateUserTypeProp(CUserTypeProp obj,String id,String[] properties)throws Exception;
	
	public void addUserTypeProp(CUserTypeProp obj)throws Exception;
	public void deleteUserTypeProp(String id)throws Exception;
	public void batchSaveRelation(String[] orders,String[] ids) throws Exception;
	
	public List<CUserProp> getCUserProp(Map<String, Object> condition)throws Exception;
	public CUserType getUserType(Map<String, Object> condition)throws Exception;
	
	/**
	 * 添加订阅提醒记录
	 * @param log
	 * @throws Exception
	 */
	public void addUserAlertsLog(LUserAlertsLog log)throws Exception;
	/**
	 * 获取来源信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BSource getBSource(String id)throws Exception;
	public List<BSource> getBSourceList(Map<String,Object> condition,String sort)throws Exception;
	
	/**
	 * 获取机构信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BInstitution getBInstitution(String id)throws Exception;
	
	/**
	 * 生成一个Token
	 * @param type
	 * @param userId
	 * @throws Exception
	 */
	public BToken createToken(int type,String userId)throws Exception;
	/**
	 * 获取Token
	 * @param tokenId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public BToken getVaildToken(String tokenId,int type)throws Exception;
	/**
	 * 将一个Token状态设置为失效
	 * @param tokenId
	 * @throws Exception
	 */
	public void disableToken(String tokenId)throws Exception;
	public Integer getIpCount(Map<String, Object> conds) throws Exception;
}
