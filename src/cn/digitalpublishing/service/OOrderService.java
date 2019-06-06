package cn.digitalpublishing.service;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.BPDAInfo;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.LUserAlertsLog;
import cn.digitalpublishing.ep.po.OCurrency;
import cn.digitalpublishing.ep.po.OExchange;
import cn.digitalpublishing.ep.po.OOrder;
import cn.digitalpublishing.ep.po.OOrderDetail;
import cn.digitalpublishing.ep.po.OTransation;
import cn.digitalpublishing.ep.po.PCcRelation;
import cn.digitalpublishing.ep.po.PPublications;

public interface OOrderService extends BaseService {
	/**
	 * 更新Order信息
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updateOrder(OOrder obj,String id,String[] properties)throws Exception;
	/**
	 * 查询订单列表
	 * @param condition
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public List<OOrder> getOrderList(Map<String, Object> condition, String sort)throws Exception;

	/**
	 * 根据Id查询订单
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public OOrder getOrder(String id)throws Exception;

	/**
	 * 插入订单明细
	 * @param obj
	 * @throws Exception
	 */
	public void insertOrderDetail(OOrderDetail obj)throws Exception;
	
	/**
	 * 修改订单明细
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updateOrderDetail(OOrderDetail obj,String id,String[] properties)throws Exception;
	/**
	 * 插入一个产品包到订单明细
	 * @param obj
	 * @throws Exception
	 */
	public void insertOrderDetailCollection(OOrderDetail obj)throws Exception;
	/**
	 * 删除订单明细
	 * @param obj
	 * @throws Exception
	 */
	public void deleteOrderDetail(String id)throws Exception;
	/**
	 * 订单明细列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<OOrderDetail> getDetailList(Map<String, Object> condition,String sort)throws Exception;
	
	public List<OOrderDetail>getOrderDetailsList(Map<String, Object> condition,String sort)throws Exception;
	/**
	 * 订单明细分页列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<OOrderDetail> getOrderDetailPagingList(Map<String, Object> condition,String sort, int pageCount, int curpage)throws Exception;
	/**
	 * 获取订单明细信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public OOrderDetail getOrderDetail(String id)throws Exception;
	/**
	 * 订单明细数量
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int getOrderDetailCount(Map<String, Object> condition) throws Exception ;
	/**
	 * 获取币种列表
	 * @param condition
	 * @param string
	 * @throws Exception
	 */
	public List<OCurrency> getCurrencyList(Map<String, Object> condition, String sort)throws Exception;
	
	/**
	 * 更新币种
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updateCurrency(OCurrency obj, String id, String[] properties)throws Exception;
	/**
	 * 插入币种
	 * @param obj
	 * @throws Exception
	 */
	public void insertCurrency(OCurrency obj)throws Exception;
	/**
	 * 删除币种
	 * @param id
	 * @throws Exception
	 */
	public void deleteCurrency(String id) throws Exception;
	
	/**
	 * 获取汇率列表
	 * @param condition
	 * @param string
	 * @throws Exception
	 */
	public List<OExchange> getExchangeList(Map<String, Object> condition, String sort)throws Exception;
	/**
	 * 获取汇率信息
	 * @param condition
	 * @param string
	 * @throws Exception
	 */
	public OExchange getExchangeByCondition(Map<String, Object> condition)throws Exception ;
	/**
	 * 更新汇率
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updateExchange(OExchange obj, String id, String[] properties)throws Exception;
	/**
	 * 插入汇率
	 * @param obj
	 * @throws Exception
	 */
	public void insertExchange(OExchange obj)throws Exception;
	/**
	 * 删除汇率
	 * @param id
	 * @throws Exception
	 */
	public void deleteExchange(String id) throws Exception;

	/**
	 * 查询订单数量
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int getOrderCount(Map<String, Object> condition)throws Exception;

	/**
	 * 订单分页列表
	 * @param condition
	 * @param string
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<OOrder>getOrderPagingList(Map<String, Object> condition,String string, int pageCount, int curpage)throws Exception;
	/**
	 * 生成一个订单
	 * @param list
	 * @param user
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public String insertOrderWithDetails(HttpServletRequest request,List<OOrderDetail> list,CUser user,String ip,Integer payType)throws Exception;
	
	/**
	 * 获取消费信息列表
	 * @param condition
	 * @param string
	 * @throws Exception
	 */
	public List<OTransation> getTransationList(Map<String, Object> condition, String sort)throws Exception;
	/**
	 * 获取指定用户的消费统计
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public double getUserTotalPay(String userId)throws Exception;
	/**
	 * 获取指定用户的支出统计
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public double getUserTotalDeposit(String userId)throws Exception;
	/**
	 * 获取指定用户的冻结统计
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public double getUserTotalFreeze(String userId)throws Exception;
	/**
	 * 获取指定用户的余额信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public double getUserBalance (String userId)throws Exception;
	/**
	 * 插入消费信息
	 * @param obj
	 * @throws Exception
	 */
	public void insertTransation(OTransation obj)throws Exception;	
	/**
	 * 获取消费分页列表
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<OTransation> getTransationLogPagingList(Map<String,Object> condition, int pageCount, int curpage)throws Exception;
	/**
	 * 获取消费信息总数（订单为最小单位）
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getLogCount(Map<String, Object> condition) throws Exception;
	/**
	 * 生成冻结消费记录（对价格取负，用于预存购买方式）
	 * @param detail
	 * @param date
	 * @throws Exception
	 */
	public void addLockedTransation(OOrderDetail detail,Double price,Date date)throws Exception;
	/**
	 * 生成预存消费记录(解除冻结，增加预存消费记录)
	 * @param detail
	 * @param date
	 * @throws Exception
	 */
	public void addStoredTransation(OOrderDetail detail,Date date)throws Exception;
	/**
	 * 生成预存消费记录(先增加预存，后按类型增加相应消费记录)
	 * @param detail
	 * @param date
	 * @throws Exception
	 */
	public void addOtherTransation(OOrderDetail detail,Date date)throws Exception;
	/**
	 * 生成订单导出数据流
	 * @return
	 * @throws Exception
	 */
	public InputStream downloadStream()throws Exception;
	/**
	 * 查询订单明细列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<OOrderDetail> getOrderDetailList(Map<String, Object> condition,
			String sort)throws Exception;
	
	/**
	 * 更新LLicense信息
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updateLicense(LLicense obj,String id,String[] properties)throws Exception;
	
	/**
	 * 添加LLicense信息
	 * @param obj
	 * @throws Exception
	 */
	public void insertLicense(LLicense obj)throws Exception;

	public List<LUserAlertsLog> getLUserAlertsLogList(Map<String, Object> condition,String sort)throws Exception;
	public void updateLUserAlertsLog(Object obj,String className,Serializable id,String[] properties)throws Exception;
	/**
	 * 取消一个冻结的预存消费记录
	 * @param detail
	 * @param date
	 * @throws Exception
	 */
	public void addCancelTransation(OOrderDetail detail,Date date)throws Exception;
	/**
	 * 查询预存余额
	 * @param condition
	 * @throws Exception
	 */
	public void queryBalance(Map<String,Object> condition)throws Exception;
	/**
	 * 处理订单详情
	 * @param operType
	 * @param detail
	 * @return
	 * @throws Exception
	 */
	public OOrderDetail processOrderDetail(String operType,OOrderDetail detail)throws Exception;
	/**
	 * 为购买期刊的时候查询是否可以购买
	 * @param detailCondition
	 * @return
	 * @throws Exception
	 */
	public List<OOrderDetail> getDetailListForAddCrat(Map<String, Object> detailCondition)throws Exception;
	/**
	 * 为购买期刊的时候查询是否可以购买
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<LLicense> getLicenseForAddCart(Map<String, Object> condition)throws Exception;
	/**
	 * 增加PDA计数
	 * @param pub
	 * @param institution
	 * @param user
	 * @throws Exception
	 */
	public void addPDACounter(PPublications pub,BInstitution institution,CUser user)throws Exception;
	/**
	 * 更新PDA设置信息
	 * @param obj
	 * @param id
	 * @throws Exception
	 */
	public void updatePDAInfo(BPDAInfo obj,String id)throws Exception;
	/**
	 * 插入PDA设置信息
	 * @param obj
	 * @throws Exception
	 */
	public void insertPDAInfo(BPDAInfo obj)throws Exception;
	/**
	 * 删除PDA设置信息
	 * @param id
	 * @throws Exception
	 */
	public void deletePDAInfo(String id)throws Exception;
	/**
	 * 处理已经达到次数的PDA信息
	 * @throws Exception
	 */
	public void handlePDA() throws Exception;
	/**
	 * 处理支付宝返回的状态信息存储
	 * @throws Exception
	 */
	public void alipayReturnNotity(String out_trade_no,String trade_no) throws Exception;
	public void deleteOrderDetail(List<OOrderDetail> list) throws Exception;
	public Integer getDetailList(Map<String,Object> condition)throws Exception;
	public List<PCcRelation> getPubList(Map<String,Object> condition ,String sort) throws Exception;
}
