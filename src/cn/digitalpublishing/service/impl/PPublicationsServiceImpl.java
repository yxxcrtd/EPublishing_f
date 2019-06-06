package cn.digitalpublishing.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.Param;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.BPublisher;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.ep.po.LComplicating;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.LLicenseIp;
import cn.digitalpublishing.ep.po.LUserAlertsLog;
import cn.digitalpublishing.ep.po.PCcRelation;
import cn.digitalpublishing.ep.po.PCollection;
import cn.digitalpublishing.ep.po.PContentRelation;
import cn.digitalpublishing.ep.po.PCsRelation;
import cn.digitalpublishing.ep.po.PPage;
import cn.digitalpublishing.ep.po.PPrice;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.ep.po.PPublicationsRelation;
import cn.digitalpublishing.ep.po.Recommend;
import cn.digitalpublishing.service.PPublicationsService;
import cn.digitalpublishing.util.io.FileUtil;
import cn.digitalpublishing.util.web.DateUtil;

public class PPublicationsServiceImpl extends BaseServiceImpl implements PPublicationsService {

	public PPublications getPublications(String id) throws Exception {
		PPublications obj = null;
		try {
			obj = (PPublications) this.daoFacade.getpPublicationsDao().get(PPublications.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.info.get.error", e);// 获取出版物信息失败！
		}
		return obj;
	}

	@Override
	public List<PPublications> getPubPageList(Map<String, Object> condition, String sort, Integer pageCount, Integer page, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getPagingList(condition, sort, pageCount, page, user, ip);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.paginglist.get.error", e);// 获取出版物分页列表信息失败！
		}
		return list;
	}

	@Override
	public List<PPublications> getSubscribedPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getSubscribedPagingList(condition, sort, pageCount, page, user, ip);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.paginglist.get.error", e);// 获取出版物分页列表信息失败！
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
	@Override
	public List<PPublications> getPubSimplePageList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getSimplePagingList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.paginglist.get.error", e);// 获取出版物分页列表信息失败！
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
	@Override
	public List<PPublications> getPubSimplePageList(Map<String, Object> condition, String sort) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getSimplePagingList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.paginglist.get.error", e);// 获取出版物分页列表信息失败！
		}
		return list;
	}

	/**
	 * 资源列表(无分页)
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PPublications> getTrialList(Map<String, Object> condition, String sort) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getTrialList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.paginglist.get.error", e);// 获取出版物分页列表信息失败！
		}
		return list;
	}

	@Override
	public List<PPublications> getPubSubscriptionPageList(Map<String, Object> condition, String sort, Integer pageCount, Integer page, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getSubscriptionPagingList(condition, sort, pageCount, page, user, ip);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.paginglist.get.error", e);// 获取出版物分页列表信息失败！
		}
		return list;
	}

	@Override
	public List<PPublications> getTrialList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getTrialList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.paginglist.get.error", e);// 获取出版物分页列表信息失败！
		}
		return list;
	}

	public List<PPublications> getPubPageList2(Map<String, Object> condition, String sort, Integer pageCount, Integer page, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getPagingList2(condition, sort, pageCount, page, user, ip);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.paginglist.get.error", e);// 获取出版物分页列表信息失败！
		}
		return list;
	}

	public List<PPublications> getPubList(Map<String, Object> condition, String sort, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getList(condition, sort, user, ip);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.list.get.error", e);// 获取出版物列表信息失败！
		}
		return list;
	}

	public List<PPublications> getPubLists(Map<String, Object> condition, String sort) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getLists(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.list.get.error", e);// 获取出版物列表信息失败！
		}
		return list;
	}

	@Override
	public Integer getPubSubscriptionCount(Map<String, Object> condition) throws Exception {
		Integer num = 0;
		try {
			num = this.daoFacade.getpPublicationsDao().getSubscriptionCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.count.get.error", e);// 获取出版物信息总数失败！
		}
		return num;
	}

	public List<PPublications> getPubList2(Map<String, Object> condition, String sort, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getList2(condition, sort, user, ip);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.list.get.error", e);// 获取出版物列表信息失败！
		}
		return list;
	}

	public Integer getPubCount(Map<String, Object> condition) throws Exception {
		Integer num = 0;
		try {
			num = this.daoFacade.getpPublicationsDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.count.get.error", e);// 获取出版物信息总数失败！
		}
		return num;
	}

	public Integer getPubCountO1(Map<String, Object> condition) throws Exception {
		Integer num = 0;
		try {
			num = this.daoFacade.getpPublicationsDao().getCountO1(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.count.get.error", e);// 获取出版物信息总数失败！
		}
		return num;
	}

	public Integer getTrialCount(Map<String, Object> condition) throws Exception {
		Integer num = 0;
		try {
			num = this.daoFacade.getpPublicationsDao().getTrialCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.count.get.error", e);// 获取出版物信息总数失败！
		}
		return num;
	}

	public List<PCcRelation> getPccPagingList(Map<String, Object> condition, String sort, int pageCount, int page, CUser user, String ip) throws Exception {
		List<PCcRelation> list = null;
		try {
			list = this.daoFacade.getpCcRelationDao().getPagingList(condition, sort, pageCount, page, user, ip);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "pcc.paginglist.get.error", e);// 获取产品包关系信息分页列表失败！
		}
		return list;
	}

	public List<PCcRelation> getPccList(Map<String, Object> condition, String sort, CUser user, String ip) throws Exception {
		List<PCcRelation> list = null;
		try {
			list = this.daoFacade.getpCcRelationDao().getList(condition, sort, user, ip);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "pcc.list.get.error", e);// 获取产品包关系信息列表失败！
		}
		return list;
	}

	public List<PCcRelation> getPccListSimple(Map<String, Object> condition) throws Exception {
		List<PCcRelation> list = null;
		try {
			list = this.daoFacade.getpCcRelationDao().getListSimple(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "pcc.list.get.error", e);// 获取产品包关系信息列表失败！
		}
		return list;
	}

	public int getPccCount(Map<String, Object> condition) throws Exception {
		int num = 0;
		try {
			num = this.daoFacade.getpCcRelationDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "pcc.count.get.error", e);// 获取产品包关系信息总数失败！
		}
		return num;
	}

	public Integer getPubCollectionCount(Map<String, Object> condition) throws Exception {
		int num = 0;
		try {
			num = this.daoFacade.getpCollectionDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "collection.paginglist.get.error", e);// 获取产品包分页信息列表失败！
		}
		return num;
	}

	public List<PCollection> getPubCollectionPageList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<PCollection> list = null;
		try {
			list = this.daoFacade.getpCollectionDao().getPagingList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "collection.count.get.error", e);// 获取产品包信息总数失败！
		}
		return list;
	}

	@Override
	public List<PPublications> getPubCountPageList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getCountPagingList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publictions.count.get.error", e);// 获取产品信息总数失败！
		}
		return list;
	}

	@Override
	public int getPPageCount(Map<String, Object> condition) throws Exception {
		int num = 0;
		try {
			num = this.daoFacade.getpPageDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "page.count.get.error", e);// 获取页总数失败！
		}
		return num;
	}

	@Override
	public PPage getPageByCondition(Map<String, Object> condition) throws Exception {
		PPage page = null;
		try {
			List<PPage> list = this.daoFacade.getpPageDao().getList(condition, "");
			if (list != null && list.size() > 0) {
				page = list.get(0);
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "page.info.get.error", e);// 获取PDF分页信息失败！
		}
		return page;
	}

	@Override
	public List<PCollection> getCollectionPagingList(Map<String, Object> condition, String sort, int pageCount, int curpage) throws Exception {
		List<PCollection> list = null;
		try {
			list = this.daoFacade.getpCollectionDao().getPagingList(condition, sort, pageCount, curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "collection.paginglist.get.error", e);// 获取产品包分页列表失败
		}
		return list;
	}

	@Override
	public int getCollectionCount(Map<String, Object> condition) throws Exception {
		int num = 0;
		try {
			num = this.daoFacade.getpCollectionDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "collection.count.get.error", e);// 获取产品包总数失败
		}
		return num;
	}

	public PPrice getPriceByCondition(Map<String, Object> condition) throws Exception {
		PPrice obj = null;
		try {
			List<PPrice> list = this.getPriceList(condition);
			if (list != null && list.size() > 0) {
				obj = list.get(0);
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "price.info.get.error", e);// 获取出版物价格信息失败！
		}
		return obj;
	}

	public PPrice getPrice(Map<String, Object> condition) throws Exception {
		PPrice price = null;
		try {
			List<PPrice> list = this.getDaoFacade().getpPriceDao().getList(condition, 0, " ");
			if (list != null && list.size() > 0) {
				price = list.get(0);
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "price.list.get.error", e);// 获取价格信息列表失败！
		}
		return price;
	}

	public List<PPrice> getPriceList(Map<String, Object> condition) throws Exception {
		List<PPrice> prices = null;
		try {
			prices = this.getDaoFacade().getpPriceDao().getList(condition, 2, "");
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "price.list.get.error", e);// 获取价格信息列表失败！
		}
		return prices;
	}

	@Override
	public void updateCollection(PCollection obj, String id, String[] properties) throws Exception {
		try {
			this.daoFacade.getpCollectionDao().update(obj, PCollection.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "collection.info.update.error", e);// 更新产品包信息失败！
		}
	}

	@Override
	public void insertCollection(PCollection obj) throws Exception {
		try {
			this.daoFacade.getpCollectionDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Collection.info.add.error", e);// 添加产品包信息失败！
		}
	}

	@Override
	public void updateCsRelation(PCsRelation obj, String id, String[] properties) throws Exception {
		try {
			this.daoFacade.getpCsRelationDao().update(obj, PCsRelation.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "pcs.info.update.error", e);// 更新分类法关系失败！
		}
	}

	@Override
	public void insertCsRelation(PCsRelation obj) throws Exception {
		try {
			this.daoFacade.getpCsRelationDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "pcs.info.add.error", e);// 添加分类法关系失败！
		}
	}

	@Override
	public void updatePublications(PPublications obj, String id, String[] properties) throws Exception {
		try {
			this.daoFacade.getpPublicationsDao().update(obj, PPublications.class.getName(), id, properties);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.info.update.error", e);// 更新出版物信息失败！
		}
	}

	@Override
	public void insertPublications(PPublications obj) throws Exception {
		try {
			this.daoFacade.getpPublicationsDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.info.add.error", e);// 新增出版物信息失败！
		}
	}

	@Override
	public void updatePrice(PPrice obj, String id, String[] properties) throws Exception {
		try {
			this.daoFacade.getpPriceDao().update(obj, PPrice.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "price.info.update.error", e);// 更新价格信息失败！
		}
	}

	@Override
	public void insertPrice(PPrice obj) throws Exception {
		try {
			this.daoFacade.getpPriceDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "price.info.add.error", e);// 新增价格信息失败！
		}
	}

	@Override
	public void updateCcRelation(PCcRelation obj, String id, String[] properties) throws Exception {
		try {
			// 更新产品信息表
			// 因为在产品包更新的时候已经将以前的数据删除,删除
			PPublications pub = (PPublications) this.daoFacade.getpPublicationsDao().get(PPublications.class.getName(), obj.getPublications().getId());
			if (pub != null) {
				pub.setInCollection(pub.getInCollection() == null || pub.getInCollection() == 0 ? 0 : (pub.getInCollection() - 1));
				this.daoFacade.getpPublicationsDao().update(pub, PPublications.class.getName(), pub.getId(), null);
			}
			// this.daoFacade.getpCcRelationDao().update(obj,PCcRelation.class.getName(),
			// id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "pcc.info.update.error", e);// 更新产品包关系失败！
		}
	}

	@Override
	public void insertCcRelation(PCcRelation obj) throws Exception {
		try {
			// 更新产品信息表
			// 因为在产品包更新的时候已经将以前的数据删除，现在只要增加就行
			PPublications pub = (PPublications) this.daoFacade.getpPublicationsDao().get(PPublications.class.getName(), obj.getPublications().getId());
			if (pub != null) {
				pub.setInCollection(pub.getInCollection() == null ? 1 : (pub.getInCollection() + 1));
				this.daoFacade.getpPublicationsDao().update(pub, PPublications.class.getName(), pub.getId(), null);
			}
			this.daoFacade.getpCcRelationDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "pcc.info.add.error", e);// 新增产品包关系失败！
		}
	}

	@Override
	public void deletePrice(String id) throws Exception {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("priceId", id);
			this.daoFacade.getpPriceDao().deleteByCondition(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "price.info.delete.error", e);// 删除价格信息失败！
		}
	}

	public void deleteChaper(String id) throws Exception {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("chaperPublications", id);// 根据图书ID删除关联的章节价格
			this.daoFacade.getpPriceDao().deleteChaper(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "price.info.delete.error", e);// 删除价格信息失败！
		}

	}

	public void deleteAllPrice(Map<String, Object> condition) throws Exception {
		try {
			this.daoFacade.getpPriceDao().deleteByCondition(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "price.info.delete.error", e);// 删除价格信息失败！
		}
	}

	@Override
	public PCollection getCollection(Serializable id) throws Exception {
		PCollection collection = null;
		try {
			collection = (PCollection) this.daoFacade.getpCollectionDao().get(PCollection.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Collection.info.get.error", e);// 删除产品包信息失败！
		}
		return collection;
	}

	@Override
	public void deletePccRelaction(Map<String, Object> condition) throws Exception {
		try {
			this.daoFacade.getpCcRelationDao().deleteByCondition(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "pcc.info.delete.error", e);// 删除产品包关系信息失败！
		}
	}

	@Override
	public void deletePcsRelation(String pubId) throws Exception {
		try {
			this.daoFacade.getpCsRelationDao().deleteByPubId(pubId);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "pcs.info.delete.error", e);// 删除分类法关系信息失败！
		}
	}

	@Override
	public void deletePcsRelation(Map<String, Object> condition) throws Exception {
		try {
			this.daoFacade.getpCsRelationDao().deleteByCondition(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "pcs.info.delete.error", e);// 删除分类法关系信息失败！
		}
	}

	@Override
	public void deleteChildPublication(Map<String, Object> condition) throws Exception {
		try {
			this.daoFacade.getpPublicationsDao().deleteByCondition(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.child.info.delete.error", e);// 删除出版物信息失败
		}
	}

	@Override
	public List<LLicenseIp> getLicenseIpList(Map<String, Object> condition, String sort) throws Exception {
		List<LLicenseIp> list = null;
		try {
			list = this.daoFacade.getlLicenseIpDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "licenseip.list.get.error", e);// 获取授权IP列表失败
		}
		return list;
	}

	@Override
	public List<PPublications> getTypeStatistic(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getTypeStatistic(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publictions.type.statistic.error", e);// 按出版物类型统计失败！
		}
		return list;
	}

	@Override
	public List<PPublications> getSubscriptionTypeStatistic(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getSubscriptionTypeStatistic(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publictions.type.statistic.error", e);// 按出版物类型统计失败！
		}
		return list;
	}

	@Override
	public void generatePages(String id, String basePath) throws Exception {
		String sourcePath = "";
		String pdfPath = "";
		String swfPath = "";
		try {
			basePath = Param.getParam("pdf.directory.config").get("dir").replace("-", ":");
			// 查询信息
			PPublications source = (PPublications) this.daoFacade.getpPublicationsDao().get(PPublications.class.getName(), id);
			if (source.getPdf() == null) {
				new CcsException("Collection.info.pdf.null.error");
			} else {
				sourcePath = basePath + source.getPdf();
				// 地址=basePath+/metadata+来源+detail+isbn+/pdf|swf|watermarkpdf+(isbn+i.pdf|swf)
				pdfPath = Param.getParam("pdf.directory.config").get("root") + source.getPublisher().getCode() + Param.getParam("pdf.directory.config").get("detail") + source.getCode() + Param.getParam("pdf.directory.config").get("pdf");
				FileUtil.newFolder(basePath + pdfPath);
				// 将分页转换成swf
				swfPath = Param.getParam("pdf.directory.config").get("root") + source.getPublisher().getCode() + Param.getParam("pdf.directory.config").get("detail") + source.getCode() + Param.getParam("pdf.directory.config").get("swf");
				FileUtil.newFolder(basePath + swfPath);
				PdfReader reader = new PdfReader(sourcePath);
				int pageCount = reader.getNumberOfPages();
				com.lowagie.text.Document doc = new com.lowagie.text.Document(reader.getPageSize(1));
				// 循环生成新PDF
				for (int j = 1; j <= pageCount; j++) {
					// 处理信息
					PPage pdf = new PPage();
					// 生成的pdf的命名为 ：isbn_i
					String pdfName = source.getCode() + "_" + j + ".pdf";
					PdfCopy copy = new PdfCopy(doc, new FileOutputStream(basePath + pdfPath + "/" + pdfName));
					doc.open();
					doc.newPage();
					PdfImportedPage page = copy.getImportedPage(reader, j);
					copy.addPage(page);
					doc.close();
					pdf.setNumber(j);// 当前页
					pdf.setPdf(pdfPath + "/" + pdfName);// 当前页路径

					String path = basePath + pdf.getPdf();
					String swfName = pdfName.replace(".pdf", ".swf");
					// D:/xxx/xxx 这里边的路径地址中的D后边的冒号，因为跟配置文件中的冒号冲突，暂时改成用 - 减号代替
					String cmd = Param.getParam("pdf.pages.cmd.config").get("win").replace("-", ":") + " \"" + path + "\"" + " -o " + "\"" + basePath + swfPath + "/" + swfName + "\"" + " -f -T 9 -t -s storeallcharacters -s linknameurl";
					Runtime.getRuntime().exec(cmd);
					pdf.setSwf(swfPath + "/" + swfName);

					// 保存Content ID
					pdf.setPublications(source);
					pdf.setId(source.getCode() + j);
					this.daoFacade.getpPageDao().insert(pdf);
					if (pdf.getPdf() != null && !"".equals(pdf.getPdf())) {
						String bpath = Param.getParam("pdf.directory.config").get("dir").replace("-", ":");
						String filePath = bpath + pdf.getPdf();
						File pdfs = new File(filePath);
						if (pdfs.exists()) {
							String nr = "";
							PDDocument docc = PDDocument.load(filePath);
							PDFTextStripper stripper = new PDFTextStripper();
							nr = stripper.getText(docc);
							// nr = nr.replace(" ", "");
							pdf.setFullText(nr);
							doc.close();
							this.pagesIndexService.indexPages(pdf);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "page.info.generate.error", e);// 生成pdf页面失败
		}
	}

	@Override
	public List<PPublications> getYearStatistic(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getYearStatistic(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publictions.year.statistic.error", e);// 按出版物出版年份统计失败！
		}
		return list;
	}

	@Override
	public List<PPublications> getSubscriptionYearStatistic(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getSubscriptionYearStatistic(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publictions.year.statistic.error", e);// 按出版物出版年份统计失败！
		}
		return list;
	}

	public List<PPublications> getPublisherStatistic(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getPublisherStatistic(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publictions.publisher.statistic.error", e);// 按出版物出版商统计失败！
		}
		return list;
	}

	@Override
	public List<PPublications> getSubscriptionPublisherStatistic(Map<String, Object> condition) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getSubscriptionPublisherStatistic(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publictions.publisher.statistic.error", e);// 按出版物出版商统计失败！
		}
		return list;
	}

	@Override
	public List<PPublications> getPPublicationsByISBN(String isbn) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getPPublicationsByISBN(isbn);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publictions.info.get.error", e);// 获取出版物信息失败！
		}
		return list;
	}

	@Override
	public PPublications findById(String id) throws Exception {
		PPublications p = null;
		try {
			p = daoFacade.getpPublicationsDao().findById(id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publictions.info.get.error", e);// 获取出版物信息失败！
		}
		return p;
	}

	@Override
	public PPublications findByIdNew(String id) throws Exception {
		PPublications p = null;
		try {
			p = daoFacade.getpPublicationsDao().findByIdNew(id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publictions.info.get.error", e);// 获取出版物信息失败！
		}
		return p;
	}

	@Override
	public List<LLicense> getLicenseList(Map<String, Object> condition, String sort) throws Exception {
		List<LLicense> list = null;
		try {
			list = this.daoFacade.getlLicenseDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "license.list.get.error", e);// 获取授权信息列表失败
		}
		return list;
	}

	@Override
	public LLicense getLicense(String id) throws Exception {
		LLicense obj = null;
		try {
			obj = (LLicense) this.daoFacade.getlLicenseDao().get(LLicense.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "license.obj.get.error", e);// 获取授权信息失败
		}
		return obj;
	}

	@Override
	public PPage getPages(String id) throws Exception {
		PPage pPage = null;
		try {
			pPage = (PPage) this.daoFacade.getpPageDao().get(PPage.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "ppage.obj.get.error", e);// 获取分页信息失败
		}
		return pPage;
	}

	@Override
	public List<PCsRelation> getHotPubs(List<PCsRelation> pcslist, Integer number) throws Exception {
		List<PCsRelation> list = null;
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("pcslist", pcslist);
			list = (List<PCsRelation>) this.daoFacade.getpCsRelationDao().getTops(condition, " order by p.buyTimes desc ", 5);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.list.get.error", e);// 获取出版物列表信息失败！
		}
		return list;
	}

	@Override
	public void addBuyTimes(String id) throws Exception {
		PPublications pub = (PPublications) this.daoFacade.getpPublicationsDao().get(PPublications.class.getName(), id);
		if (pub != null) {
			pub.setBuyTimes(pub.getBuyTimes() == null ? 1 : pub.getBuyTimes() + 1);
			this.daoFacade.getpPublicationsDao().update(pub, PPublications.class.getName(), id, null);
		}
	}

	@Override
	public Integer getCounterOfRead(Map<String, Object> condition) throws Exception {
		Integer result = 0;
		try {
			result = this.daoFacade.getlAccessDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "log.info.get.error", e);// 获取日至信息失败！
		}
		return result;
	}

	@Override
	public boolean isExistComplicating(Map<String, Object> condition) throws Exception {
		boolean b = false;
		try {
			int count = 0;
			count = this.daoFacade.getlComplicatingDao().getCount(condition);
			if (count > 0) {
				b = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public int getComplicatingCount(Map<String, Object> condition) throws Exception {
		int num = 0;
		try {
			num = this.daoFacade.getlComplicatingDao().getCount(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public void insertComplicating(LComplicating obj) throws Exception {
		try {
			this.daoFacade.getlComplicatingDao().insert(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteComplicatingByCondition(Map<String, Object> condition) throws Exception {
		try {
			this.daoFacade.getlComplicatingDao().deleteByCondition(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PPublications> getSimpleList(Map<String, Object> condition, String sort, Integer number) throws Exception {
		List<PPublications> list = null;
		try {
			list = (List<PPublications>) this.daoFacade.getpPublicationsDao().getSimplePubList(condition, sort, number);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.list.get.error", e);// 获取出版物列表信息失败！
		}
		return list;
	}

	@Override
	public List<PPublications> getSimplePagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<PPublications> list = null;
		try {
			list = (List<PPublications>) this.daoFacade.getpPublicationsDao().getSimplePageList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.list.get.error", e);// 获取出版物列表信息失败！
		}
		return list;
	}

	@Override
	/*
	 * public List<PPublications> getSimpleListIssue(Map<String,Object>
	 * condition,String sort,Integer pageCount,Integer curPage)throws Exception{
	 * List<PPublications> list=null; try{
	 * list=(List<PPublications>)this.daoFacade
	 * .getpPublicationsDao().getSimplePubListIssue(condition, sort, pageCount,
	 * curPage); } catch (Exception e) { throw new CcsException((e instanceof
	 * CcsException) ? ((CcsException)e).getPrompt() :
	 * "publications.list.get.error", e);//获取出版物列表信息失败！ } return list; }
	 */
	public List<PPublications> getArticleList(Map<String, Object> condition, String sort, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getArticleList(condition, sort, user, ip);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.list.get.error", e);// 获取出版物列表信息失败！
		}
		return list;
	}

	public List<PPublications> getArticlePagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getArticlePagingList(condition, sort, pageCount, page, user, ip);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.list.get.error", e);// 获取出版物列表信息失败！
		}
		return list;
	}

	@Override
	public List<PPublications> getDatabaseList(Map<String, Object> condition, String sort, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getDatabaseList(condition, sort, user, ip);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.list.get.error", e);// 获取出版物列表信息失败！
		}
		return list;
	}

	@Override
	public List<BInstitution> getLicenseInstitutionList(Map<String, Object> condition, String sort) throws Exception {
		List<BInstitution> list = null;
		try {
			list = this.daoFacade.getbInstitutionDao().getlicenseList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Institution.list.get.error", e);// 获取机构信息失败！
		}
		return list;
	}

	@Override
	public List<PPublications> getDatabasePageList(Map<String, Object> condition, String sort, Integer pageCount, Integer page, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getDatabasePagingList(condition, sort, pageCount, page, user, ip);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "collection.count.get.error", e);// 获取产品包信息总数失败！
		}
		return list;
	}

	@Override
	public List<LComplicating> getComplicatingList(Map<String, Object> condition, String sort) throws Exception {
		List<LComplicating> list = null;
		try {
			list = this.daoFacade.getlComplicatingDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "complicating.info.list.error", e);// 获取并发列表信息失败！
		}
		return list;
	}

	@Override
	public void uploads(PPublications obj) throws Exception {
		try {
			PPublications pp = this.getPublications(obj.getId());
			if (pp == null || "".equals(pp.getId())) {
				this.insertPublications(obj);
				// 更新期刊的开始和结束年份
				if (obj.getType() == 6) {// 卷上传
					PPublications p1 = this.getPublications(obj.getPublications().getId());
					p1.setStartVolume(this.getStartVolume(p1.getId()));
					p1.setEndVolume(this.getEndVolume(p1.getId()));
					this.updatePublications(p1, p1.getId(), null);
				}
			} else {
				obj.setStartVolume(pp.getStartVolume());
				obj.setEndVolume(pp.getEndVolume());
				this.updatePublications(obj, obj.getId(), null);
			}
			// 若是文章，则添加到需要LUserAlertsLog中，用来进行更新提醒
			if ((pp == null || "".equals(pp.getId())) && obj.getType() == 4) {// 类型
																				// 1-电子书
																				// 2-期刊
																				// 3-章节
																				// 4-文章
																				// 5-数据库
																				// 6-期刊卷
																				// 7
																				// 期刊期
																				// 99产品包(用来在订单明细中区分)
				Map<String, Object> condition = new HashMap<String, Object>();
				Map<String, Object> con = new HashMap<String, Object>();
				con.put("id", obj.getId());
				con.put("pId", obj.getPublications().getId());
				con.put("vId", obj.getVolume().getId());
				con.put("isId", obj.getIssue().getId());
				condition.put("articleIds", con);
				condition.put("status", 1);
				List<LLicense> list = this.getLicenseList(condition, "");
				if (list != null && list.size() > 0) {
					for (LLicense lLicense : list) {
						// 限时授权出版物，将进行续费提醒
						LUserAlertsLog log = new LUserAlertsLog();
						log.setUserName(lLicense.getUser().getName());// 用户名
						log.setPublicationsName(lLicense.getPublications().getTitle());// 出版物名称
						log.setIsbn(lLicense.getPublications().getCode());
						log.setEmail(lLicense.getUser().getEmail());// 用户邮箱
						log.setCreatedon(new Date());
						log.setAlertDate(DateUtil.getNowDate("yyyy-MM-dd"));// 提醒日期
																			// 提前一个月
						log.setAlertStatus(1);// 提醒状态 1、未提醒 2、已提醒 3、提醒失败
						log.setAlertType(2);// 提醒类型 1、续费提醒 2、最新章节、文章
						log.setUser(lLicense.getUser());
						log.setPublications(lLicense.getPublications());
						this.daoFacade.getlUserAlertsLogDao().insert(obj);
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Controller.Publications.Rest.Maintain.Failed", e);// 获取并发列表信息失败！
		}
	}

	@Override
	public String getStartVolume(String id) throws Exception {
		String startVolume = "";
		try {
			startVolume = this.daoFacade.getpPublicationsDao().getStartVolume(id);
			if (!"".equals(startVolume)) {
				String a[] = startVolume.split("-");
				if (a.length == 2) {
					startVolume = a[1] + "/" + a[0];
				}
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Controller.Publications.Rest.Maintain.Failed", e);// 获取并发列表信息失败！
		}
		return startVolume;
	}

	@Override
	public String getEndVolume(String id) throws Exception {
		String endVolume = "";
		try {
			endVolume = this.daoFacade.getpPublicationsDao().getEndVolume(id);
			if (!"".equals(endVolume)) {
				String a[] = endVolume.split("-");
				if (a.length == 2) {
					endVolume = a[1] + "/" + a[0];
				}
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Controller.Publications.Rest.Maintain.Failed", e);// 获取并发列表信息失败！
		}
		return endVolume;
	}

	@Override
	public int getDatabaseCount(Map<String, Object> condition) throws Exception {
		int num = 0;
		try {
			num = this.daoFacade.getpPublicationsDao().getDatabaseCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "database.count.get.error", e);// 获取数据库信息总数失败！
		}
		return num;
	}

	/**
	 * 获取分类法统计
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PCsRelation> getSubStatistic(Map<String, Object> condition) throws Exception {
		List<PCsRelation> list = null;
		try {
			list = this.daoFacade.getpCsRelationDao().getSubStatistic(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "subject.pub..statistic.get.error", e);// 获取分类法统计失败！
		}
		return list;
	}

	@Override
	public List<PPublications> getLicensePubPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer page, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getLicensePagingList(condition, sort, pageCount, page, user, ip);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.paginglist.get.error", e);// 获取出版物分页列表信息失败！
		}
		return list;
	}

	@Override
	public int getLicenseCount(Map<String, Object> condition) throws Exception {
		int num = 0;
		try {
			num = this.daoFacade.getpPublicationsDao().getLicenseCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.count.get.error", e);// 获取信息总数失败！
		}
		return num;
	}

	@Override
	public List<PPublications> getTocList(Map<String, Object> conToc, String sort) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getTocList(conToc, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.list.get.error", e);// 获取出版物列表信息失败！
		}
		return list;
	}

	public LLicense getVaildLicense(PPublications pub, CUser user, String ip) throws Exception {
		LLicense license = null;
		try {
			List<LLicense> list = this.daoFacade.getlLicenseDao().getPubValidLicense(pub, user, ip);
			if (list != null && list.size() > 0) {
				license = list.get(0);
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "license.info.get.error", e);// 获取信息总数失败！
		}
		return license;
	}

	public LLicense getVaildLicense(PPage page, CUser user, String ip) throws Exception {
		LLicense license = null;
		try {
			List<LLicense> list = this.daoFacade.getlLicenseDao().getPubValidLicense(page.getPublications(), user, ip);
			if (list != null && list.size() > 0) {
				license = list.get(0);// 若书有license使用书的license
			} else {
				// 查找页所属的章节，若章节有license，使用章节的license

				// 由于页码改成字符串了，无法判断页码是否在某章节范围中，阅读时会有问题，暂注释相关代码（3）
				// PPublications
				// pub=this.daoFacade.getpPublicationsDao().getChapter(page.getPublications().getId(),page.getNumber());
				// if(pub!=null){
				// list =
				// this.daoFacade.getlLicenseDao().getPubValidLicense(pub, user,
				// ip);
				// if(list!=null && list.size()>0){
				// license=list.get(0);//若书有license使用书的license
				// }
				// }
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "license.info.get.error", e);// 获取信息总数失败！
		}
		return license;
	}

	public Map<String, Object> getPubTypeCount(Map<String, Object> condition) throws Exception {
		Map<String, Object> map = null;
		try {
			List<PPublications> list = this.daoFacade.getpPublicationsDao().getTypeCount(condition);
			if (list != null && list.size() > 0) {
				Integer allCount = 0;
				map = new HashMap<String, Object>();
				for (PPublications pub : list) {
					if (pub.getType() == 1 || pub.getType() == 2 || pub.getType() == 4) {
						map.put(pub.getType().toString(), pub.getStartPage());
						// allCount+=pub.getStartPage();
						allCount += Integer.valueOf(pub.getStartPage());
					}
				}
				map.put("all", allCount);
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.count.get.error", e);// 获取出版物信息总数失败！
		}
		return map;
	}

	public List<PPublications> getPubList3(Map<String, Object> condition, String sort, CUser user, String ip) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getList3(condition, sort, user, ip);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.list.get.error", e);// 获取出版物列表信息失败！
		}
		return list;
	}

	public void beating(Map<String, Object> condition) throws Exception {
		try {
			this.daoFacade.getlComplicatingDao().beatWhereExists(condition);
		} catch (Exception e) {
			throw e;
		}
	}

	public void deleteDead(Integer sec) throws Exception {
		try {
			this.daoFacade.getlComplicatingDao().deleteDead(sec);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void updatePublications1(Map<String, Object> vals, String[] ids) throws Exception {
		try {
			this.daoFacade.getpPublicationsDao().updateJournals(vals, ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.info.update.error", e);// 更新出版物信息失败！
		}

	}

	@Override
	public List<LLicense> getLicenseStat() throws Exception {
		List<LLicense> list = null;
		try {
			list = this.daoFacade.getlLicenseDao().getLicenseStat();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.info.update.error", e);// 更新出版物信息失败！
		}
		return list;
	}

	@Override
	public List<PPublications> getpublicationsList(Map<String, Object> condition, String sort) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getpublicationsList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.paginglist.get.error", e);// 获取出版物分页列表信息失败！
		}
		return list;
	}

	@Override
	public void updatePublicatios(PPublications pub) throws Exception {
		try {
			this.daoFacade.getpPublicationsDao().update(pub, PPublications.class.getName(), pub.getId(), null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.info.update.error", e);// 更新出版物信息失败！
		}

	}

	@Override
	public void updateJournalp(Map<String, Object> vals, String[] ids) throws Exception {
		try {
			this.daoFacade.getpPublicationsDao().updateJournalp(vals, ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.info.update.error", e);// 更新出版物信息失败！
		}

	}

	@Override
	public void getEnd(Map<String, Object> uCon) throws Exception {
		try {
			this.daoFacade.getlComplicatingDao().endWhereExists(uCon);
		} catch (Exception e) {
			throw e;
		}
	}

	public PPublications getChapter(String pubId, int pageNo) throws Exception {
		try {
			return this.daoFacade.getPPublicationsDao().getChapter(pubId, pageNo);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<PPublicationsRelation> getPPublicationsRelationList(Map<String, Object> condition, String sort) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<PPublicationsRelation> list = null;
		try {
			list = this.daoFacade.getpPublicationsRelationDao().getPublicationsRelationList(condition, sort);
		} catch (Exception e) {
			// 获取产品信息分页列表失败！
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Content.data.page.list.error", e);// 获取产品信息分页列表失败！
		}
		return list;
	}

	@Override
	public List<Recommend> getRecommendList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		// TODO Auto-generated method stub
		List<Recommend> list = null;
		try {
			list = this.daoFacade.getRecommendDao().getRecommendList(condition, sort, pageCount, page);
		} catch (Exception e) {
			// 获取产品信息分页列表失败！
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Content.data.page.list.error", e);// 获取产品信息分页列表失败！
		}
		return list;
	}

	@Override
	public List<PContentRelation> getPContentRelaionList(Map<String, Object> condition, String sort) throws Exception {
		// TODO Auto-generated method stub
		List<PContentRelation> list = null;
		try {
			list = this.daoFacade.getpContentRelationDao().getList(condition, sort);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public BPublisher getPublisher(String id) throws Exception {
		try {
			return (BPublisher) this.daoFacade.getbPublisherDao().get(BPublisher.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Content.data.page.list.error", e);
		}

	}

	@Override
	public List<BPublisher> getDistinctPublisher(Map<String, Object> condition) throws Exception {
		try {
			return this.daoFacade.getbPublisherDao().getDistinctPublisher(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Content.data.page.list.error", e);
		}
	}

	@Override
	public List<PCsRelation> getDistinctSubject(Map<String, Object> condition) throws Exception {
		try {
			return this.daoFacade.getpCsRelationDao().getDistinctSubject(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Content.data.page.list.error", e);
		}
	}

	@Override
	public List<PPublications> getpublicationsPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer curPage) throws Exception {
		try {
			return this.daoFacade.getpPublicationsDao().getpublicationsPagingList(condition, sort, pageCount, curPage);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<LAccess> forArticleRight(Map<String, Object> condition, String sort, Integer pageCount, Integer curPage) throws Exception {
		try {
			return this.daoFacade.getlAccessDao().forArticleRight(condition, sort, pageCount, curPage);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public List<PCollection> getPCollectionList(Map<String, Object> condition, String sort) throws Exception {
		try {
			return this.daoFacade.getpCollectionDao().getList(condition, sort);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 通过sql获取PCollection产品包里面所包含的分类（优化版）
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PCsRelation> getSubInfoForCol(Map<String, Object> condition, String sort) throws Exception {
		try {
			return this.daoFacade.getpCsRelationDao().getSubInfoForCol(condition, sort);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 通过sql获取PCollection产品包里面所包含的出版社（优化版）
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getpublisherInfoForCol(Map<String, Object> condition, String sort) throws Exception {
		try {
			return this.daoFacade.getpPublicationsDao().getpublisherInfoForCol(condition, sort);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 通过sql获取PCollection产品包里面所包含的语言（优化版）
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getLangInfoForCol(Map<String, Object> condition, String sort) throws Exception {
		try {
			return this.daoFacade.getpPublicationsDao().getLangInfoForCol(condition, sort);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<PPublications> getPagingList3(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		try {
			return this.daoFacade.getpPublicationsDao().getPagingList3(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<PPublications> getpubListIsNew(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getListIsNew(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.paginglist.get.error", e);// 获取出版物分页列表信息失败！
		}
		return list;
	}

	public List<PPublications> getBuyTimesInfo(String subjectId, String sort, Integer pubNum) throws Exception {
		List<PPublications> list = null;
		try {
			list = this.daoFacade.getpPublicationsDao().getBuyTimesInfo(subjectId, sort, pubNum);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "publications.paginglist.get.error", e);// 获取出版物分页列表信息失败！
		}
		return list;
	}

	@Override
	public List<PPage> getPageList(String id) throws Exception {
		List<PPage> list = null;
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("contentId", id);
			list = this.daoFacade.getpPageDao().getList(condition, "");

		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "ppage.obj.get.error", e);// 获取出版物列表信息失败！
		}
		return list;

	}

	@Override
	public List<PPublications> getNewestPublications(Map<String, Object> condition, String sort, long count) throws Exception {
		List<PPublications> list = null;
		try {
			list = daoFacade.getPPublicationsDao().getNewestPublications(condition, sort, count);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcsException("获取 EPUB_P_Publications 表中最新记录 失败！");
		}
		return list;
	}

	@Override
	public List<PPublications> getPublicationsNew(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<PPublications> list = null;
		try {
			list = daoFacade.getPPublicationsDao().getPagingListNew(condition, sort, pageCount, page);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcsException("获取 EPUB_P_Publications 表中最新记录 失败！");
		}
		return list;
	}

	@Override
	public List<PCcRelation> getCcLangStatistic(Map<String, Object> condition) throws Exception {
		// TODO Auto-generated method stub
		List<PCcRelation> list = null;
		try {
			list = daoFacade.getpCcRelationDao().getLangStatistic(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "pcollection.get.lang.statistic.error", e);// 获取产品包内语种统计失败
		}
		return list;
	}

	@Override
	public List<PCcRelation> getCcTypeStatistic(Map<String, Object> condition) throws Exception {
		// TODO Auto-generated method stub
		List<PCcRelation> list = null;
		try {
			list = daoFacade.getpCcRelationDao().getTypeStatistic(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "pcollection.get.type.statistic.error", e);// 获取产品包内类型统计失败
		}
		return list;
	}

	@Override
	public List<PCcRelation> getCcTypeLangStatistic(Map<String, Object> condition) throws Exception {
		// TODO Auto-generated method stub
		List<PCcRelation> list = null;
		try {
			list = daoFacade.getpCcRelationDao().getCcTypeLangStatistic(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "pcollection.get.type.lang.statistic.error", e);// 获取产品包内类型语种统计失败
		}
		return list;
	}

	@Override
	public PPublications getPublications(Map<String, Object> condition) throws Exception {
		PPublications p = null;
		try {
			p = daoFacade.getPPublicationsDao().getPublications(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	@Override
	public boolean getLLicenseFlag(String id) throws Exception {
		boolean flag = false;
		try {
			flag = daoFacade.getlLicenseDao().getLLicenseFlag(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
