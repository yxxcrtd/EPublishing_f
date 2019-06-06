package cn.digitalpublishing.service.impl;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.Param;
import cn.com.daxtech.framework.util.StringUtil;
import cn.digitalpublishing.config.ProcessQueue;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.BIpRange;
import cn.digitalpublishing.ep.po.BPDACounter;
import cn.digitalpublishing.ep.po.BPDAInfo;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.CUserProp;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.LLicenseIp;
import cn.digitalpublishing.ep.po.LUserAlertsLog;
import cn.digitalpublishing.ep.po.OCurrency;
import cn.digitalpublishing.ep.po.OExchange;
import cn.digitalpublishing.ep.po.OOrder;
import cn.digitalpublishing.ep.po.OOrderDetail;
import cn.digitalpublishing.ep.po.OTransation;
import cn.digitalpublishing.ep.po.PCcRelation;
import cn.digitalpublishing.ep.po.PPrice;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.service.OOrderService;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;
import cn.digitalpublishing.util.io.FileUtil;
import cn.digitalpublishing.util.web.MathHelper;
import cn.digitalpublishing.util.web.RandomCodeUtil;

import com.zhima.server.model.ResultObject;
import com.zhima.server.util.restful.Converter;

public class OOrderServiceImpl extends BaseServiceImpl implements OOrderService {
	
	@Override
	public void updateOrder(OOrder obj,String id,String[] properties)throws Exception{
		try {
			this.daoFacade.getoOrderDao().update(obj,OOrder.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "order.info.update.error", e);//更新订单信息失败！
		}
	}
	@Override
	public List<OOrder> getOrderList(Map<String, Object> condition,
			String sort) throws Exception {
		List<OOrder> list = null ;
		try {
			list = this.daoFacade.getoOrderDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "order.list.get.error", e);//获取订单列表失败！
		}
		return list;
	}

	@Override
	public OOrder getOrder(String id) throws Exception {
		OOrder order = null;
		try {
			order = (OOrder)this.daoFacade.getoOrderDao().get(OOrder.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "order.info.get.error", e);//获取订单列表失败！
		}
		return order;
	}
	
	@Override
	public void insertOrderDetail(OOrderDetail obj) throws Exception {
		try {
			this.daoFacade.getoOrderDetailDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "odetail.info.add.error", e);//新增订单明细失败！
		}
	}	
	
	@Override
	public void updateOrderDetail(OOrderDetail obj,String id,String[] properties)throws Exception{
		try {
			this.daoFacade.getoOrderDetailDao().update(obj, OOrderDetail.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "odetail.info.update.error", e);//更新订单明细失败！
		}
	}
	
	@Override
	public void insertOrderDetailCollection(OOrderDetail obj)throws Exception{
		try {
			this.daoFacade.getoOrderDetailDao().insert(obj);
			Map<String,Object> condition=new HashMap<String,Object>();
			condition.put("collectionId", obj.getCollection().getId());
			List<PCcRelation> list=super.getDaoFacade().getpCcRelationDao().getList(condition, "",null,"127.0.0.1");
			if(list!=null && list.size()>0){
				Date d=new Date();
				for(PCcRelation relation:list){		
					OOrderDetail detail=new OOrderDetail();
					detail.setUser(obj.getUser());
					detail.setQuantity(obj.getQuantity());//数量
					detail.setCreatedby(obj.getCreatedby());
					detail.setCreatedon(d);
					detail.setUpdatedby(obj.getUpdatedby());
					detail.setUpdatedon(d);
					detail.setName(relation.getPublications().getTitle());
					detail.setIp(obj.getIp());
					detail.setCurrency(obj.getCurrency());
					detail.setItemType(relation.getPublications().getType());
					detail.setStatus(4);
					detail.setProductCode(relation.getPublications().getCode());	
					detail.setCollection(obj.getCollection());
					detail.setDetail(obj);
					detail.setRecommend(obj.getRecommend());
					this.insertOrderDetail(detail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "odetail.info.add.error", e);//新增订单明细失败！
		}
	}
	/**
	 * 删除前先删除其子项
	 */
	@Override	
	public void deleteOrderDetail(String id) throws Exception {
		try {
			Map<String,Object> condition =new HashMap<String,Object>();
			condition.put("parentId", id);
			List<OOrderDetail> list=this.daoFacade.getoOrderDetailDao().getList(condition,"");
			if(list!=null && list.size()>0){
				for(OOrderDetail d:list){
					this.daoFacade.getoOrderDetailDao().delete(OOrderDetail.class.getName(), d.getId());
				}
			}
//			OOrderDetail detail=this.getOrderDetail(id);
//			if(detail.getPdaCounter()!=null){
//				BPDACounter counter=(BPDACounter) this.daoFacade.getbPDACounterDao().get(BPDACounter.class.getName(),detail.getPdaCounter().getId());
//				counter.setStatus(1);
//				this.daoFacade.getbPDACounterDao().update(counter,BPDACounter.class.getName(), counter.getId(),null);
//			}
			this.daoFacade.getoOrderDetailDao().delete(OOrderDetail.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "odetail.info.delete.error", e);//删除订单明细失败！
		}
	}
	
	@Override
	public void deleteOrderDetail(List<OOrderDetail> list) throws Exception {
		// TODO Auto-generated method stub
		try {
			if(list!=null && list.size()>0){
				for(OOrderDetail d:list){
					this.daoFacade.getoOrderDetailDao().delete(OOrderDetail.class.getName(), d.getId());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "odetail.info.delete.error", e);
		}
		
	}

	
	@Override
	public List<OOrderDetail> getDetailList(Map<String, Object> condition, String sort)
			throws Exception {
		List<OOrderDetail> list = null;
		try {
			list = this.daoFacade.getoOrderDetailDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "odetail.list.get.error", e);//获取订单明细列表失败！
		}
		return list;
	}
	@Override
	public List<OOrderDetail> getOrderDetailPagingList(Map<String, Object> condition,
			String sort, int pageCount, int curpage) throws Exception {
		List<OOrderDetail> list = null;
		try {
			list = this.daoFacade.getoOrderDetailDao().getPagingList(condition, sort, pageCount, curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "odetail.paginglist.get.error", e);//获取订单明细列表失败！
		}
		return list;
	}
	@Override
	public OOrderDetail getOrderDetail(String id)throws Exception{
		OOrderDetail detail = null;
		try {
			detail = (OOrderDetail)this.daoFacade.getoOrderDetailDao().get(OOrderDetail.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "odetail.info.get.error", e);//获取订单明细失败！
		}
		return detail;
	}
	@Override
	public int getOrderDetailCount(Map<String, Object> condition) throws Exception {
		int num = 0;
		try {
			num = this.daoFacade.getoOrderDetailDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "odetail.count.get.error", e);//获取订单明细总数失败！
		}
		return num;
	}

	public List<OCurrency> getCurrencyList(Map<String, Object> condition, String sort)throws Exception {
		List<OCurrency> list = null;
		try {
			list = this.daoFacade.getoCurrencyDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "currency.list.get.error", e);//获取币种列表失败！
		}
		return list;
	}


	public void updateCurrency(OCurrency obj, String id, String[] properties)throws Exception {
		try {
			this.daoFacade.getoCurrencyDao().update(obj,OCurrency.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "currency.info.update.error", e);//更新币种列表失败！
		}
	}


	public void insertCurrency(OCurrency obj) throws Exception {
		try {
			this.daoFacade.getoCurrencyDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "currency.info.add.error", e);//新增币种列表失败！
		}
	}
	

	public void deleteCurrency(String id) throws Exception {
		try {
			this.daoFacade.getoCurrencyDao().delete(OCurrency.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "currency.info.delete.error", e);//删除币种列表失败！
		}
	}
	
	public List<OExchange> getExchangeList(Map<String, Object> condition, String sort)throws Exception {
		List<OExchange> list = null;
		try {
			list = this.daoFacade.getoExchangeDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "exchange.list.get.error", e);//获取汇率列表失败！
		}
		return list;
	}
	
	public OExchange getExchangeByCondition(Map<String, Object> condition)throws Exception {
		OExchange obj=null;
		try {
			List<OExchange> list = this.getExchangeList(condition, "");
			if(list!=null && list.size()>0){
				obj=list.get(0);
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "exchange.info.get.error", e);//获取汇率信息失败！
		}
		return obj;
	}

	public void updateExchange(OExchange obj, String id, String[] properties)throws Exception {
		try {
			this.daoFacade.getoExchangeDao().update(obj,OExchange.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "exchange.info.update.error", e);//更新汇率信息失败！
		}
	}


	public void insertExchange(OExchange obj) throws Exception {
		try {
			this.daoFacade.getoExchangeDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "exchange.info.add.error", e);//新增汇率信息失败！
		}
	}
	

	public void deleteExchange(String id) throws Exception {
		try {
			this.daoFacade.getoExchangeDao().delete(OExchange.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "exchange.info.delete.error", e);//删除汇率信息失败！
		}
	}

	@Override
	public int getOrderCount(Map<String, Object> condition) throws Exception {
		int num = 0;
		try {
			num = this.daoFacade.getoOrderDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "order.info.count.error", e);//获取订单信息总数失败！
		}
		return num;
	}

	@Override
	public List<OOrder> getOrderPagingList(Map<String, Object> condition,
			String sort, int pageCount, int curpage) throws Exception {
		List<OOrder> list = null;
		try {
			list = this.daoFacade.getoOrderDao().getPagingList(condition, sort, pageCount, curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "order.paginglist.get.error", e);//获取订单分页列表失败
		}
		return list;
	}
	
	@Override
	public String insertOrderWithDetails(HttpServletRequest request, List<OOrderDetail> list,CUser user,String ip,Integer payType)throws Exception{
		OOrder order=new OOrder();
		try {
			if(list!=null && list.size()>0){
				Date d=new Date();
				double totalprice=0d;
				double totaltax=0d;
				double totalextprice=0d;
				for(OOrderDetail ood:list){
					totalprice=MathHelper.add(totalprice, ood.getSalePrice());
					totaltax=MathHelper.add(totaltax, ood.getTax());
					totalextprice=MathHelper.add(totalextprice, ood.getSalePriceExtTax());
				}
				//生成新订单
				order.setUser(new CUser());
				order.getUser().setId(user.getId());
				order.setName(user.getName());
				order.setIp(ip);
				order.setCurrency("RMB");
				order.setSalePrice(totalprice);
				order.setTax(totaltax);
				order.setSalePriceExtTax(totalextprice);
				order.setStatus(1);
				order.setQuantity(list.size());
				order.setCreatedby(user.getName());
				order.setCreatedon(d);
				order.setUpdatedby(user.getName());
				order.setUpdatedon(d);
				order.setPayType(payType);//付款方式
				String random=RandomCodeUtil.generateRandomNumber(1);
				order.setCode(StringUtil.formatDate(new Date(),"yyyyMMddhhmmddsss")+random);
				order.setSendStatus(1);			
				this.daoFacade.getoOrderDao().insert(order);				
//				Boolean isAllComplete=true;
				for(OOrderDetail ood:list){//循环订单下的明细,回写新生成的订单ID到明细中					
					if(ood.getCollection()!=null){//明细是一个产品包
						Map<String,Object> condition =new HashMap<String,Object>();
						condition.put("parentId", ood.getId());
						List<OOrderDetail> plist=this.getDetailList(condition,"");//得到产品包中的产品列表
						//this.insertLicenseFromCollection(ood,request);
						if(plist!=null && plist.size()>0){
							for(OOrderDetail sub:plist){
								sub.setOrder(order);//回写订单ID
								//sub.setPrice(null);
//								if(payType==1){//预存款支付
//									sub.setStatus(3);//设置处理状态为3-已完成									
//								}else{
									sub.setStatus(1);//明细处理状态置为1-未处理
//									isAllComplete=false;
//								}
								this.daoFacade.getoOrderDetailDao().update(sub, OOrderDetail.class.getName(), sub.getId(), null);
							}
						}		
//						if(payType==1){
//							ood.setStatus(3);
//						}else{
							ood.setStatus(1);
//						}
					}else{//明细是单个出版物						
//						if(payType==1 && ood.getPrice().getPublications().getLocal()==2){//预存款支付且是本地资源
//							ood.setStatus(3);//设置处理状态为3-已完成			
//							//this.insertLicenseFromOODetail(ood,request);//生成License
//						}else if(payType==1 && ood.getPrice().getPublications().getLocal()==1 ){//预存款支付，远程资源
//							ood.setStatus(2);//设置状态为2-已结算
//							isAllComplete=false;
//						}else{
//							isAllComplete=false;
							ood.setStatus(1);//明细处理状态置为1-未处理
//						}						
					}
					ood.setOrder(order);
					
					this.daoFacade.getoOrderDetailDao().update(ood, OOrderDetail.class.getName(), ood.getId(), null);
					
				}
//				order.setStatus(isAllComplete?3:1); //若订单中的所有明细项目的状态均为已完成，订单状态为已完成
//				this.daoFacade.getoOrderDao().update(order, OOrder.class.getName(), order.getId(),null);
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "order.info.add.error", e);//新增订单信息失败！
		}
		return order.getId();
	}
	

	public List<OTransation> getTransationList(Map<String, Object> condition, String sort)throws Exception {
		List<OTransation> list = null;
		try {
			list = this.daoFacade.getoTransationDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "tran.list.get.error", e);//获取消费记录信息失败！
		}
		return list;
	}
	public double getUserTotalPay(String userId)throws Exception{
		double result=0d;
		try{
			Map<String, Object> condition =new HashMap<String,Object>();
			condition.put("userId", userId);
			condition.put("type",2);//筛查支出项目
			condition.put("total", "<0");
			result=daoFacade.getoTransationDao().getTotal(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "tran.log.get.error", e);//获取用户预存账户余额信息失败
		}
		return result;
	}
	public double getUserTotalDeposit(String userId)throws Exception{
		double result=0d;
		try{
			Map<String, Object> condition =new HashMap<String,Object>();
			condition.put("userId", userId);
			condition.put("type",1);//筛查存入项目
			result=daoFacade.getoTransationDao().getTotal(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "tran.log.get.error", e);//获取用户预存账户余额信息失败
		}
		return result;
	}
	public double getUserTotalFreeze(String userId)throws Exception{
		double result=0d;
		try{
			Map<String, Object> condition =new HashMap<String,Object>();
			condition.put("userId", userId);
			condition.put("type",3);//筛查存入项目
			result=daoFacade.getoTransationDao().getTotal(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "tran.log.get.error", e);//获取用户预存账户余额信息失败
		}
		return result;
	}
	public double getUserBalance (String userId)throws Exception{
		double result=0d;
		try{
			Map<String, Object> condition =new HashMap<String,Object>();
			condition.put("userId", userId);
			result=daoFacade.getoTransationDao().getTotal(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "tran.balance.get.error", e);//获取用户预存账户余额信息失败
		}
		return result;
	}
	public void insertTransation(OTransation obj) throws Exception {
		try {
			this.daoFacade.getoTransationDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "tran.info.add.error", e);//新增消费记录信息失败
		}
	}
	
	public List<OTransation> getTransationLogPagingList(Map<String,Object> condition, int pageCount, int curpage)throws Exception{
		try{
			return this.daoFacade.getoTransationDao().getLogPagingList(condition, pageCount, curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "tran.paginglist.get.error", e);//获取消费记录分页列表失败
		}
	}
	
	
	@Override
	public Integer getLogCount(Map<String, Object> condition) throws Exception {
		int num = 0;
		try {
			num = this.daoFacade.getoTransationDao().getLogCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "tran.count.get.error", e);//获取消费记录总数失败
		}
		return num;
	}
	

	private void insertLicenseFromOODetail(OOrderDetail detail,HttpServletRequest request)throws Exception{
		try{
			LLicense license=new LLicense();
			license.setUser(detail.getUser());
			license.setPublications(detail.getPrice().getPublications());
			license.setCode(detail.getPrice().getPublications().getCode());
			license.setCreatedby(detail.getUser().getName());
			license.setCreatedon(new Date());
			license.setStatus(1);
			if(detail.getPrice().getPublications().getLocal()==1){//不是本地资源
				license.setReadUrl(detail.getPrice().getPublications().getWebUrl());
			}else{//是本地资源
				license.setReadUrl(request.getContextPath()+ "/pages/view/form/view?id=" + detail.getPrice().getPublications().getId());
			}
			if(detail.getPrice().getType()==1){//商品在产品包中或用户选择的购买价格为永久授权
				license.setType(1);//永久授权
				license.setStartTime(new Date());
			}else{
				//限时授权部分暂缓
	//			license.setType(obj.getPrice().getType());//商品属于一个产品包，类型为永久授权
	//			license.setStartTime(new Date());
	//			license.setEndTime(new Date());
			}			
			this.daoFacade.getlLicenseDao().insert(license);
			
			//增加一次成功购买次数
			ServiceFactory serviceFactory=(ServiceFactory)new ServiceFactoryImpl();
			serviceFactory.getPPublicationsService().addBuyTimes(license.getPublications().getId());
			
			Map<String,Object> condition=new HashMap<String,Object>();
			condition.put("userId", detail.getUser().getId());
			List<CUser> users=this.daoFacade.getcUserDao().getList(condition, "");
			if(users!=null && users.size()>0){
				if(users.get(0).getLevel()==2){//机构管理员用户												
					Map<String,Object> condition1 =new HashMap<String,Object>();
					condition1.put("institutionId", users.get(0).getInstitution().getId());
					List<BIpRange> ipr=this.daoFacade.getbIpRangeDao().getList(condition, "");
					if(ipr!=null && ipr.size()>0){
						for(BIpRange ip:ipr){
							LLicenseIp lip=new LLicenseIp();
							lip.setStartIp(ip.getStartIp());
							lip.setSip(ip.getSip());
							lip.setEndIp(ip.getEndIp());
							lip.setEip(ip.getEip());
							lip.setLicense(license);
							this.daoFacade.getlLicenseIpDao().insert(lip);
						}
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
	}
	
	private void insertLicenseFromCollection(OOrderDetail detail,HttpServletRequest request)throws Exception{
		try{
			Map<String,Object> condition=new HashMap<String,Object>();
			condition.put("collectionId", detail.getCollection().getId());
			List<PCcRelation> pcc=this.daoFacade.getpCcRelationDao().getListSimple(condition);
			List<BIpRange> ipr=null;
			
			condition.clear();
			condition.put("userId", detail.getUser().getId());
			List<CUser> users=this.daoFacade.getcUserDao().getList(condition, "");
			if(users.get(0).getLevel()==2){//机构管理员用户											
				condition.clear();
				condition.put("institutionId", users.get(0).getInstitution().getId());
				ipr=this.daoFacade.getbIpRangeDao().getList(condition, "");
			}
			if(pcc!=null && pcc.size()>0){								
				for(PCcRelation relation :pcc){
					PPublications pub= relation.getPublications();
					
					LLicense license=new LLicense();
					license.setUser(detail.getUser());
					license.setPublications(pub);
					license.setCode(pub.getCode());
					license.setCreatedby(detail.getUser().getName());
					license.setCreatedon(new Date());
					license.setStatus(1);
					license.setType(1);//永久授权
					license.setStartTime(new Date());
					license.setReadUrl(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+
							request.getContextPath()+ "/pages/view/form/view?id=" + pub.getId());
					//限时授权部分暂缓
					this.daoFacade.getlLicenseDao().insert(license);
					
					//增加一次成功购买次数
					ServiceFactory serviceFactory=(ServiceFactory)new ServiceFactoryImpl();
					serviceFactory.getPPublicationsService().addBuyTimes(license.getPublications().getId());
					
					if(ipr!=null && ipr.size()>0){//用户是机构管理员，添加LicenseIp信息
						for(BIpRange ip:ipr){
							LLicenseIp lip=new LLicenseIp();
							lip.setStartIp(ip.getStartIp());
							lip.setSip(ip.getSip());
							lip.setEndIp(ip.getEndIp());
							lip.setEip(ip.getEip());
							lip.setLicense(license);
							this.daoFacade.getlLicenseIpDao().insert(lip);
						}						
					}
				}
			}	
		}catch(Exception e){
			throw e;
		}
	}
	
	public void addLockedTransation(OOrderDetail detail,Double price,Date date)throws Exception{
		try{
			OTransation transation=new OTransation();			
			transation.setUser(detail.getUser());
			transation.setOrder(detail.getOrder());
			transation.setOrderDetail(detail);
			transation.setOrderCode(detail.getOrder().getCode());
			transation.setType(5);//消费类型为预存冻结		
			transation.setAmount(price);		
			transation.setCreatedby(detail.getUser().getName());
			transation.setCreatedon(date);
			transation.setUpdatedby(detail.getUser().getName());
			transation.setUpdatedon(date);
			this.insertTransation(transation);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 生成预存消费记录(解除冻结，增加预存消费记录)
	 * @param detail
	 * @param date
	 * @throws Exception
	 */
	public void addStoredTransation(OOrderDetail detail,Date date)throws Exception{
		try{   			
			Map<String,Object> condition=new HashMap<String,Object>();
			condition.put("detailId", detail.getId());
			condition.put("type", 3);//查询因此订单详情冻结的余额
			List<OTransation> tlist=this.daoFacade.getoTransationDao().getList(condition,"");
			if(tlist!=null){
				addLockedTransation(detail,MathHelper.sub(0d,tlist.get(0).getAmount()),date);//添加一条金额为正数的冻结记录以抵消提交订单时生成的负值冻结记录	
				if(detail.getSettledPrice()!=0){					
					OTransation transation=new OTransation();
					transation.setUser(detail.getUser());
					transation.setOrder(detail.getOrder());
					transation.setOrderDetail(detail);
					transation.setOrderCode(detail.getOrder().getCode());
					transation.setAmount(MathHelper.sub(0d,detail.getSettledPrice()));//取订单详情中的价格的负值		
					transation.setCreatedby(detail.getUser().getName());
					transation.setCreatedon(date);
					transation.setUpdatedby(detail.getUser().getName());
					transation.setUpdatedon(date);						
					transation.setType(2);//消费类型为预存消费
					this.insertTransation(transation);
				}
			}else{
				throw new CcsException("Order.Detail.Transation.Info.Miss");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public void addOtherTransation(OOrderDetail detail,Date date)throws Exception{
		try{
//			Map<String,Object> condition=new HashMap<String,Object>();
//			condition.put("detailId", detail.getId());
//			condition.put("type", 3);//查询因此订单详情冻结的余额
//			List<OTransation> tlist=this.daoFacade.getoTransationDao().getList(condition,"");
//			if(tlist!=null && tlist.size()>0){
//				addLockedTransation(detail,MathHelper.sub(0d,tlist.get(0).getAmount()),date);//添加一条金额为正数的冻结记录以抵消提交订单时生成的负值冻结记录
//			
			if(detail.getSettledPrice()!=0){
				OTransation transation=new OTransation();
				transation.setUser(detail.getUser());
				transation.setOrder(detail.getOrder());
				transation.setOrderDetail(detail);
				transation.setOrderCode(detail.getOrder().getCode());
				transation.setAmount(detail.getSettledPrice());			
				transation.setCreatedby(detail.getUser().getName());
				transation.setCreatedon(date);
				transation.setUpdatedby(detail.getUser().getName());
				transation.setUpdatedon(date);				
				if(detail.getOrder().getPayType()==1){//预存账户支付					
					transation.setType(2);//消费类型为预存消费					
				}else if(detail.getOrder().getPayType()==2){//支票
					transation.setType(4);//消费类型为线下支票消费			
				}else if(detail.getOrder().getPayType()==3){//alipay
					transation.setType(3);//消费类型为AliPay
				}
				this.insertTransation(transation);
				
				OTransation transation2=new OTransation();
				transation2.setUser(detail.getUser());
				transation2.setOrder(detail.getOrder());
				transation2.setOrderDetail(detail);
				transation2.setOrderCode(detail.getOrder().getCode());
				transation2.setAmount(MathHelper.sub(0d,detail.getSettledPrice()));			
				transation2.setCreatedby(detail.getUser().getName());
				transation2.setCreatedon(date);
				transation2.setUpdatedby(detail.getUser().getName());
				transation2.setUpdatedon(date);
				if(detail.getOrder().getPayType()==1){//预存账户支付					
					transation2.setType(2);//消费类型为预存消费					
				}else if(detail.getOrder().getPayType()==2){//支票
					transation2.setType(4);//消费类型为线下支票消费			
				}else if(detail.getOrder().getPayType()==3){//alipay
					transation2.setType(3);//消费类型为AliPay
				}
				this.insertTransation(transation2);
//			}else{
//				throw new CcsException("Order.Detail.Transation.Info.Miss");
//			}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	@SuppressWarnings("unused")
	public InputStream downloadStream() throws Exception {
		InputStream input = null;
		try {
			//查询全部订单
			List<OOrder> list = this.daoFacade.getoOrderDao().getList(null, " order by a.createdon ");
			if(list!=null&&list.size()>0){
				//循环
				String path = Param.getParam("pdf.directory.config").get("dir").replace("-", ":");
				path += Param.getParam("excel.directory.config").get("temp");
				Date date = new Date();
				String name = date.getTime()+".xls";
				FileUtil.newFolder(path);
				Workbook book = Workbook.getWorkbook(new File(path+"/"+name));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return input;
	}
	@Override
	public List<OOrderDetail> getOrderDetailList(Map<String, Object> condition,
			String sort) throws Exception {
		List<OOrderDetail> list = null;
		try {
			list = this.daoFacade.getoOrderDetailDao().getList(condition, sort);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "orderDetail.info.list.error", e);//获取订单明细列表时失败！
		}
		return list;
	}
	
	public void updateLicense(LLicense obj,String id,String[] properties)throws Exception{
		try {
			this.daoFacade.getoOrderDao().update(obj,LLicense.class.getName(), id, properties);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "license.info.update.error", e);//更新License信息失败！
		}
	}
	
	public void insertLicense(LLicense obj)throws Exception{
		try {
			this.daoFacade.getoOrderDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "license.info.insert.error", e);//更新License信息失败！
		}
	}
	
	public List<LUserAlertsLog> getLUserAlertsLogList(Map<String, Object> condition,String sort)throws Exception{
		List<LUserAlertsLog> list = null;
		try {
			list = this.daoFacade.getlUserAlertsLogDao().getList(condition, sort);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "orderDetail.info.list.error", e);//获取订单明细列表时失败！
		}
		return list;
	}
	
	public void updateLUserAlertsLog(Object obj,String className,Serializable id,String[] properties)throws Exception{
		try {
			this.daoFacade.getlUserAlertsLogDao().update(obj,LUserAlertsLog.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "license.info.update.error", e);//更新License信息失败！
		}
	}
	
	public void addCancelTransation(OOrderDetail detail,Date date)throws Exception{
		try{   			
			Map<String,Object> condition=new HashMap<String,Object>();
			condition.put("detailId", detail.getId());
			condition.put("type", 3);//查询因此订单详情冻结的余额
			List<OTransation> tlist=this.daoFacade.getoTransationDao().getList(condition,"");
			//if(tlist!=null && tlist.size()==1){
			if(tlist!=null){
				addLockedTransation(detail,MathHelper.sub(0d,tlist.get(0).getAmount()),date);//添加一条金额为正数的冻结记录以抵消提交订单时生成的负值冻结记录
			}else{
				throw new CcsException("Order.Detail.Transation.Info.Miss");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	public void queryBalance(Map<String,Object> condition)throws Exception{
		
	}
	/**
	 * 用于处理订单详情状态，
	 * operType 为1时仅更新OrderDetail表(由dcc中的授权管理相关页面使用)，不进行其他处理 ，
	 * 			为2时更新OrderDetail表及相关的其他表，（由dcc中的订单管理相关页面使用），
	 * 			为3时表示由dcc中的一键还原调用
	 */
	public OOrderDetail processOrderDetail(String operType,OOrderDetail detail)throws Exception{
		try{
			Integer status=detail.getStatus();
			Integer sendStatus=detail.getSendStatus();
			Double settledPrice=detail.getSettledPrice();			
			OOrderDetail obj1=this.getOrderDetail(detail.getId());
			Boolean tranFlag=detail.getIp()==null || !"noTransation".equals(detail.getIp());//是否需要生成消费记录
			Date d=new Date();
			if("2".equals(operType) || "1".equals(operType)){
				obj1.setStatus(status);		
				obj1.setSendStatus(sendStatus);
				obj1.setSettledPrice(settledPrice);
				obj1.setUpdatedon(d);
				if("2".equals(operType)){
					if(obj1.getStatus()==2 || obj1.getStatus()==3){//已支付						
						if(obj1.getOrder().getPayType()==1){
							if(tranFlag) {
								this.addStoredTransation(obj1, d);
							}
						}else{
							this.addOtherTransation(obj1, d);
						}
						
						ServiceFactory serviceFactory=(ServiceFactory)new ServiceFactoryImpl();
						if(obj1.getCollection()==null|| obj1.getItemType()!=99){
							//增加一次成功购买次数
							serviceFactory.getPPublicationsService().addBuyTimes(obj1.getPrice().getPublications().getId());
						}else{
							Map<String,Object> condition=new HashMap<String,Object>();//如果是产品包，为其中的每一个出版物增加一次购买次数
							condition.put("collectionId", obj1.getCollection().getId());
							List<PCcRelation> pcc=serviceFactory.getPPublicationsService().getPccListSimple(condition);						
							if(pcc!=null && pcc.size()>0){
								for(PCcRelation relation :pcc){
									serviceFactory.getPPublicationsService().addBuyTimes(relation.getPublications().getId());
								}
							}
						}						
					}else if(obj1.getStatus()==99 && obj1.getOrder().getPayType()==1){//已取消
						this.addCancelTransation(obj1, d);
					}
				}
				this.updateOrderDetail(obj1,obj1.getId(), null);
			}else if("3".equals(operType)){//还原				
				if(obj1.getOrder().getPayType()==1){//预存支付时
					if(obj1.getStatus()==1){
						//未处理，不需要操作
					}else if(obj1.getStatus()==2 || obj1.getStatus()==3 || obj1.getStatus()==4){
						//已付款,在消费记录中增加一笔等于因此订单被扣除的类型为预存消费的数额的正数金额
						Map<String,Object> condition=new HashMap<String,Object>();
						condition.put("detailId", obj1.getId());
						condition.put("type", 2);//预存消费
						double amount=this.daoFacade.getoTransationDao().getTotal(condition);
						if(amount<0){
							OTransation transation=new OTransation();
							transation.setUser(obj1.getUser());
							transation.setOrder(obj1.getOrder());
							transation.setOrderDetail(obj1);
							transation.setOrderCode(obj1.getOrder().getCode());
							transation.setType(2);//消费类型	
							transation.setAmount(MathHelper.sub(0,amount));		
							transation.setCreatedby(obj1.getUser().getName());
							transation.setCreatedon(d);
							transation.setUpdatedby(obj1.getUser().getName());
							transation.setUpdatedon(d);
							this.insertTransation(transation);//增加一条正数的预存消费用来抵消被扣除的余额
						}
						addLockedTransation(obj1,MathHelper.sub(0,obj1.getSettledPrice()),d);//重新增加预存冻结
					}else if(obj1.getStatus()==99){
						addLockedTransation(obj1,MathHelper.sub(0,obj1.getSettledPrice()),d);//重新增加预存冻结
					}
				}
				obj1.setStatus(status);
				obj1.setSendStatus(sendStatus);
				this.updateOrderDetail(obj1,obj1.getId(), null);
			}
			return obj1;
		}catch(Exception e){
			throw e;
		}
	}
	@Override
	public List<OOrderDetail> getDetailListForAddCrat(Map<String, Object> condition) throws Exception {
		List<OOrderDetail> list = null;
		try {
			list = this.daoFacade.getoOrderDetailDao().getDetailListForAddCart(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "order.list.get.error", e);//获取订单列表失败！
		}
		return list;
	}
	@Override
	public List<LLicense> getLicenseForAddCart(Map<String, Object> condition)
			throws Exception {
		List<LLicense> list =null;
		try {
			list = this.daoFacade.getlLicenseDao().getLicenseForAddCart(condition);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	@Override
	public void addPDACounter(PPublications pub,BInstitution institution,CUser user)throws Exception{
		try{
			if(pub==null || (pub.getType()!=2 && pub.getType()!=4 && pub.getType()!=1)){				
				return;//出版物类型不在PDA范围中不作处理
			}
			if(institution==null && user==null){
				return;//机构和用户同为null不作处理
			}			
			//先查询是否机构或用户是否有PDA设置
			Integer pdaType=2;//个人
			Map<String,Object> pdaCondition=new HashMap<String, Object>();
			pdaCondition.put("status", 1);		
			if(user!=null){
				pdaCondition.put("userId", user.getId());
			}
			if(institution!=null){
				pdaCondition.put("insId", institution.getId());
				pdaType=1;
			}
			pdaCondition.put("type",pdaType);
			if(pub.getType()==1){pdaCondition.put("book",1);}
			if(pub.getType()==2){pdaCondition.put("journal",1);}
			if(pub.getType()==4){pdaCondition.put("article",1);}
			List<BPDAInfo> infoList=this.daoFacade.getbPDAInfoDao().getList(pdaCondition, "");
			if(infoList==null || infoList.isEmpty()){
				return;//没有相应的PDA设置
			}			
			
			Map<String,Object> condition=new HashMap<String, Object>();
			condition.put("pubId", pub.getId());
			condition.put("pdaId", infoList.get(0).getId());
			condition.put("status", 1);
			List<BPDACounter> counterList=this.daoFacade.getbPDACounterDao().getList(condition, "");			
		
			if(counterList!=null && !counterList.isEmpty()){//已存在且未关闭
				BPDACounter counter=counterList.get(0);
				counter.setValue(counter.getValue()+1);
				counter.setEndDate(new Date());
				this.daoFacade.getbPDACounterDao().update(counter, BPDACounter.class.getName(), counter.getId(), null);				
			}else{//不存在计数则插入
				BPDACounter counter=new BPDACounter();
				counter.setPdaInfo(infoList.get(0));
				counter.setStartDate(new Date());
				counter.setPublications(pub);
				counter.setStatus(1);
				counter.setValue(1);
				counter.setEndDate(new Date());
				this.daoFacade.getbPDACounterDao().insert(counter);
			}
			
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "PDA.Counter.add.error", e);//增加计数失败
		}
	}
	
	public void updatePDAInfo(BPDAInfo obj,String id)throws Exception{
		try{
			this.daoFacade.getbPDAInfoDao().update(obj, BPDAInfo.class.getName(),id, null);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "PDA.Info.update.error", e);//更新PDA信息失败
		}
	}
	
	public void insertPDAInfo(BPDAInfo obj)throws Exception{
		try{
			this.daoFacade.getbPDAInfoDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "PDA.Info.add.error", e);//插入PDA信息失败
		}
	}
	
	public void deletePDAInfo(String id)throws Exception{
		try{
			this.daoFacade.getbPDAInfoDao().delete(BPDAInfo.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "PDA.Info.delete.error", e);//删除PDA信息失败
		}
	}
	
	public void handlePDA() throws Exception{
		try{
			Map<String,Object> condition=new HashMap<String, Object>();
			condition.put("status",1);//PDA计数器未关闭
			condition.put("pubStatus", 2);//出版物已上架
			condition.put("isReady",true);//已达到触发次数
			List<BPDACounter> list=this.daoFacade.getbPDACounterDao().getList(condition," order by a.startDate ");
			if(list!=null && !list.isEmpty()){
				for(BPDACounter counter:list){
					CUser user=counter.getPdaInfo().getUser();
					BInstitution ins=counter.getPdaInfo().getInstitution();
					PPublications pub=(PPublications) this.daoFacade.getpPublicationsDao().get(PPublications.class.getName(), counter.getPublications().getId());
					List<OOrderDetail> odList=this.daoFacade.getoOrderDetailDao().getPubProcessOrder(pub, user, ins);
					if(odList==null || odList.isEmpty()){//没有正在处理的订单
						Map<String,Object> priceCondition=new HashMap<String, Object>();
						priceCondition.put("publicationsid",pub.getId());
						priceCondition.put("typeCode","PACT_PRICE");
						List<PPrice> priceList=this.daoFacade.getpPriceDao().getList(priceCondition, 2, " order by a.price ");
						if(priceList!=null && !priceList.isEmpty()){//存在协议价格
							Date d=new Date();	
							CUser uuser=(CUser) this.daoFacade.getcUserDao().get(CUser.class.getName(), user.getId());
							//*****************计算售价*************************************
							Double salePrice=0d;
							Double tax=0d;
							Double finalPrice=0d;
							//查询用户是否是免税用户
							Map<String ,Object> taxCondition=new HashMap<String,Object>();
							condition.put("userId", user.getId());
							List<CUserProp> props=this.daoFacade.getCUserPropDao().getList(condition,"");
							Boolean isFreeTax=false;							
							if(props!=null && props.size()>0){						
								for(CUserProp prop:props){
									if(prop.getCode()!=null && "freetax".equals(prop.getCode())){
										if("2".equals(prop.getVal())){
											isFreeTax=true;//用户为免税用户
											break;
										}
									}
								}
							}
							if(!"RMB".equals(priceList.get(0).getCurrency())){
								Map<String,Object> exCondition =new HashMap<String,Object>();
								exCondition.put("scurr", "RMB");
								exCondition.put("xcurr", priceList.get(0).getCurrency());
								OExchange exchange= this.getExchangeByCondition(exCondition);
							   //计算汇率转换后的价格
								if(exchange!=null){
									Double rate=Double.valueOf(exchange.getRate());	        
							        salePrice=round(MathHelper.mul(priceList.get(0).getPrice(), rate));
								}else{
									continue; //"未找到对应的汇率RMB->
								}
							}else{        
						        salePrice=round(priceList.get(0).getPrice());	         
							}							
							//计算税额
							if(!isFreeTax){  
								tax=round(MathHelper.mul(salePrice,0.13));
							}
							//最终售价
							finalPrice=MathHelper.add(salePrice,tax);
							//***********计算售价结束**********************************
							
							//操作方式 1-只加入购物车 2-直接生成订单
							
							OOrderDetail detail=new OOrderDetail();	
							detail.setPdaCounter(counter);
							detail.setUser(user);
							detail.setPrice(priceList.get(0));
							detail.setQuantity(1);//数量
							detail.setCreatedby(uuser.getName());
							detail.setCreatedon(d);
							detail.setUpdatedby(uuser.getName());
							detail.setUpdatedon(d);
							detail.setName(pub.getTitle());								
							detail.setCurrency("RMB");
							detail.setItemType(pub.getType());
							detail.setStatus(4);//购物车
							detail.setProductCode(pub.getCode());			
							detail.setSalePrice(salePrice);
							detail.setTax(tax);
							//最终售价
							detail.setSalePriceExtTax(MathHelper.add(salePrice, tax));
							//结算价 默认等于最终售价
							detail.setSettledPrice(detail.getSalePriceExtTax());
							this.insertOrderDetail(detail);
							if(counter.getPdaInfo().getOperation()==2){
								OOrder order=new OOrder();																	
								//生成新订单
								order.setUser(user);
								order.setName(uuser.getName());
								order.setCurrency("RMB");
								order.setSalePrice(salePrice);
								order.setTax(tax);
								order.setSalePriceExtTax(finalPrice);
								order.setStatus(1);
								order.setQuantity(1);
								order.setCreatedby(uuser.getName());
								order.setCreatedon(d);
								order.setUpdatedby(uuser.getName());
								order.setUpdatedon(d);
								order.setPayType(counter.getPdaInfo().getPayment());//付款方式 1 预付  2支票
								order.setCode(StringUtil.formatDate(new Date(),"yyyyMMddhhmmddsss"));
								order.setSendStatus(1);			
								this.daoFacade.getoOrderDao().insert(order);		
								detail.setOrder(order);
								detail.setStatus(1);
								this.daoFacade.getoOrderDetailDao().update(detail, OOrderDetail.class.getName(), detail.getId(), null);	
							}
							counter.setStatus(2);//关闭计数器
							this.daoFacade.getbPDACounterDao().update(counter,BPDACounter.class.getName(), counter.getId(), null);
						}
					}else{
						counter.setStatus(2);//关闭计数器
						this.daoFacade.getbPDACounterDao().update(counter,BPDACounter.class.getName(), counter.getId(), null);
					}
				}
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "PDA.Info.handle.error", e);//处理PDA信息失败
		}
	}
	
	/**
	 * 保留两位小数 如:3.1415 >> 3.15
	 * @param value
	 * @return
	 */
	private Double round(Double value){
		BigDecimal bd = new BigDecimal(value);  
        bd = bd.setScale(2, BigDecimal.ROUND_CEILING);  
        return bd.doubleValue();   
	}
	@Override
	public void  alipayReturnNotity(String out_trade_no, String trade_no)throws Exception {
		try {
			Date date=new Date();
				Map<String,Object> condition = new HashMap<String, Object>();
				condition.put("code",out_trade_no);
				List<OOrder> list=this.daoFacade.getoOrderDao().getList(condition, "");
				if(list!=null&&list.size()>0){
				for(OOrder order :list){
						order.setTradeNo(trade_no);//支付宝回执订单号
						if(order.getSendStatus()==2){
							order.setSendStatus(1);
						}
						Map<String,Object> condition1 = new HashMap<String, Object>();
						condition1.put("orderId",order.getId());	
				List<OOrderDetail>	detailList= this.daoFacade.getoOrderDetailDao().getList(condition1, null);
					if(detailList!=null&&detailList.size()>0){
						for(OOrderDetail detail :detailList){
							if(detail.getStatus()==1){
								detail.setStatus(2);//已付款未开通
							}else if(detail.getStatus()==10){
								detail.setStatus(3);//已结算已开通
							}else if(detail.getStatus()==4){
								detail.setStatus(2);//已付款未开通
							}
							this.daoFacade.getoOrderDetailDao().update(detail, detail.getClass().getName(), detail.getId(), null);
							this.daoFacade.getoOrderDao().update(order, order.getClass().getName(), order.getId(), null);	
							this.addOtherTransation(detail, date);
						}
					}
					/*if(order.getPayType()==3&&"rest".equals(ProcessQueue.interfaceService)){//支付宝支付才会同步数据到DCC
						HashMap<String,Object> map=new HashMap<String,Object>();
						String url = this.restService.getService("alipaynotifyUpdate");
						cn.digitalpublishing.po.OOrder oorder=new cn.digitalpublishing.po.OOrder();
						Converter<cn.digitalpublishing.po.OOrder>converter=new Converter<cn.digitalpublishing.po.OOrder>();
							oorder.setTrade_no(trade_no);
							oorder.setId(order.getId());
						map.put("obj", oorder);
					ResultObject<cn.digitalpublishing.po.OOrder> result=converter.post(url, map);	
					if(result.getType()==2){
						throw new CcsException("content.info.error");
					}
					}*/
				 }  
				 }
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public Integer getDetailList(Map<String, Object> condition)
			throws Exception {
		try {
			return this.daoFacade.getoOrderDetailDao().getDetailList(condition);
		} catch (Exception e) {
			throw e;
		}
	}
	public List<PCcRelation> getPubList(Map<String,Object> condition ,String sort) throws Exception{
		try {
			return this.daoFacade.getpCcRelationDao().getPubList(condition, sort);
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public List<OOrderDetail> getOrderDetailsList(
			Map<String, Object> condition, String sort) throws Exception {
		List<OOrderDetail> list = null;
		try {
			list = this.daoFacade.getoOrderDetailDao().getOrderDetailsList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "odetail.list.get.error", e);//获取订单明细列表失败！
		}
		return list;
	}
}
