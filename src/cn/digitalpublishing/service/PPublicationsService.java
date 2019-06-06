package cn.digitalpublishing.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.BPublisher;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.ep.po.LComplicating;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.LLicenseIp;
import cn.digitalpublishing.ep.po.PCcRelation;
import cn.digitalpublishing.ep.po.PCollection;
import cn.digitalpublishing.ep.po.PContentRelation;
import cn.digitalpublishing.ep.po.PCsRelation;
import cn.digitalpublishing.ep.po.PPage;
import cn.digitalpublishing.ep.po.PPrice;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.ep.po.PPublicationsRelation;
import cn.digitalpublishing.ep.po.Recommend;

public interface PPublicationsService extends BaseService {
	/**
	 * 根据出版社ID 查询是否存在分页信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<PPage> getPageList(String id) throws Exception;

	/**
	 * 获取出版物实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PPublications getPublications(String id) throws Exception;

	/**
	 * 获取分页列表
	 * 
	 * @param condtion
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getPubPageList(Map<String, Object> condition, String sort, Integer pageCount,
			Integer page, CUser user, String ip) throws Exception;

	/**
	 * 获取已订阅分页列表
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
	public List<PPublications> getSubscribedPagingList(Map<String, Object> condition, String sort, Integer pageCount,
			Integer page, CUser user, String ip) throws Exception;

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
	public List<PPublications> getPubSimplePageList(Map<String, Object> condition, String sort, Integer pageCount,
			Integer page) throws Exception;

	/**
	 * 简单列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getPubSimplePageList(Map<String, Object> condition, String sort) throws Exception;

	/**
	 * 资源列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getTrialList(Map<String, Object> condition, String sort) throws Exception;

	/**
	 * 
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getpubListIsNew(Map<String, Object> condition, String sort, Integer pageCount,
			Integer page) throws Exception;

	/**
	 * 获取已订阅分页列表
	 * 
	 * @param condtion
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getPubSubscriptionPageList(Map<String, Object> condition, String sort, Integer pageCount,
			Integer page, CUser user, String ip) throws Exception;

	/**
	 * 获取资源分页列表
	 * 
	 * @param condtion
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getTrialList(Map<String, Object> condition, String sort, Integer pageCount, Integer page)
			throws Exception;

	/**
	 * 获取分页列表2
	 * 
	 * @param condtion
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getPubPageList2(Map<String, Object> condition, String sort, Integer pageCount,
			Integer page, CUser user, String ip) throws Exception;

	/**
	 * 获取出版物列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getPubList(Map<String, Object> condition, String sort, CUser user, String ip)
			throws Exception;

	public List<PPublications> getPubLists(Map<String, Object> condition, String sort) throws Exception;

	/**
	 * 获取出版物列表2
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getPubList2(Map<String, Object> condition, String sort, CUser user, String ip)
			throws Exception;

	/**
	 * 根据日期获取统计数量分页列表
	 * 
	 * @param condtion
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getPubCountPageList(Map<String, Object> condition, String sort, Integer pageCount,
			Integer page) throws Exception;

	/**
	 * 获取已订阅产品总数
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getPubSubscriptionCount(Map<String, Object> condition) throws Exception;

	/**
	 * 获取产品总数
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getPubCount(Map<String, Object> condition) throws Exception;

	/**
	 * 获取产品总数(优化)
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getPubCountO1(Map<String, Object> condition) throws Exception;

	/**
	 * 获取试用资源总数
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getTrialCount(Map<String, Object> condition) throws Exception;

	/**
	 * 查询产品和产品包分页列表
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PCcRelation> getPccPagingList(Map<String, Object> condition, String sort, int pageCount, int page,
			CUser user, String ip) throws Exception;

	/**
	 * 查询产品和产品包列表
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PCcRelation> getPccList(Map<String, Object> condition, String sort, CUser user, String ip)
			throws Exception;

	/**
	 * 查询产品包和产品总数
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int getPccCount(Map<String, Object> condition) throws Exception;

	/**
	 * 查询产品包分页列表
	 * 
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PCollection> getPubCollectionPageList(Map<String, Object> condition, String sort, Integer pageCount,
			Integer page) throws Exception;

	/**
	 * 获取产品包总数
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getPubCollectionCount(Map<String, Object> condition) throws Exception;

	/**
	 * 获取产品分页总数
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int getPPageCount(Map<String, Object> condition) throws Exception;

	/**
	 * 根据条件查询分页信息
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public PPage getPageByCondition(Map<String, Object> condition) throws Exception;

	/**
	 * 查询出版物价格
	 * 
	 * @param publicationid
	 * @return
	 * @throws Exception
	 */
	public PPrice getPriceByCondition(Map<String, Object> condition) throws Exception;

	public PPrice getPrice(Map<String, Object> condition) throws Exception;

	/**
	 * 查询出版物价格列表
	 * 
	 * @param publicationid
	 * @return
	 * @throws Exception
	 */
	public List<PPrice> getPriceList(Map<String, Object> condition) throws Exception;

	/**
	 * 查询产品包分页列表
	 * 
	 * @param condition
	 * @param string
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<PCollection> getCollectionPagingList(Map<String, Object> condition, String sort, int pageCount,
			int curpage) throws Exception;

	/**
	 * 查询产品包数量
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int getCollectionCount(Map<String, Object> condition) throws Exception;

	/**
	 * 更新产品包
	 * 
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updateCollection(PCollection obj, String id, String[] properties) throws Exception;

	/**
	 * 插入产品包
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public void insertCollection(PCollection obj) throws Exception;

	/**
	 * 更新产品与分类关系
	 * 
	 * @param obj
	 * @param id
	 * @param properties
	 */
	public void updateCsRelation(PCsRelation obj, String id, String[] properties) throws Exception;

	/**
	 * 插入产品与分类关系
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public void insertCsRelation(PCsRelation obj) throws Exception;

	/**
	 * 更新产品信息
	 * 
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updatePublications(PPublications obj, String id, String[] properties) throws Exception;

	/**
	 * 更新期刊产品信息
	 * 
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updatePublications1(Map<String, Object> vals, String[] ids) throws Exception;

	/**
	 * 期刊下的信息更新
	 * 
	 * @param vals
	 * @param ids
	 * @throws Exception
	 */
	public void updateJournalp(Map<String, Object> vals, String[] ids) throws Exception;

	/**
	 * 更新期刊产品信息
	 * 
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updatePublicatios(PPublications pub) throws Exception;

	/**
	 * 查询期刊下的子集
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getpublicationsList(Map<String, Object> condition, String sort) throws Exception;

	/**
	 * 插入产品信息
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public void insertPublications(PPublications obj) throws Exception;

	/**
	 * 更新价格信息
	 * 
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updatePrice(PPrice obj, String id, String[] properties) throws Exception;

	// /**
	// * 更新价格字段信息
	// * @param obj
	// */
	// public void updatePrice(Map<String, Object> condition)throws Exception;
	/**
	 * 插入价格信息
	 * 
	 * @param obj
	 */
	public void insertPrice(PPrice obj) throws Exception;

	/**
	 * 更新产品包与产品关系
	 * 
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updateCcRelation(PCcRelation obj, String id, String[] properties) throws Exception;

	/**
	 * 简单查询产品包与产品管理列表
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PCcRelation> getPccListSimple(Map<String, Object> condition) throws Exception;

	/**
	 * 插入产品包与产品关系
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public void insertCcRelation(PCcRelation obj) throws Exception;

	/**
	 * 删除价格
	 * 
	 * @param pm
	 * @throws Exception
	 */
	public void deletePrice(String id) throws Exception;

	/**
	 * 删除章节价格
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteChaper(String id) throws Exception;

	/**
	 * 删除价格
	 * 
	 * @param condition
	 * @throws Exception
	 */
	public void deleteAllPrice(Map<String, Object> condition) throws Exception;

	/**
	 * 根据ID查询产品包信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PCollection getCollection(Serializable id) throws Exception;

	/**
	 * 根据条件删除
	 * 
	 * @param condition
	 * @throws Exception
	 */
	public void deletePccRelaction(Map<String, Object> condition) throws Exception;

	/**
	 * 删除分类产品关联关系
	 * 
	 * @param condition
	 * @throws Exception
	 */
	public void deletePcsRelation(String pubId) throws Exception;

	/**
	 * 删除分类产品关联关系
	 * 
	 * @param condition
	 * @throws Exception
	 */
	public void deletePcsRelation(Map<String, Object> condition) throws Exception;

	/**
	 * 删除子类
	 * 
	 * @param condition
	 * @throws Exception
	 */
	public void deleteChildPublication(Map<String, Object> condition) throws Exception;

	/**
	 * 查询阅读信息
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LLicenseIp> getLicenseIpList(Map<String, Object> condition, String sort) throws Exception;

	/**
	 * 按出版物类型统计
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getTypeStatistic(Map<String, Object> condition) throws Exception;

	/**
	 * 按出版年限统计
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getYearStatistic(Map<String, Object> condition) throws Exception;

	/**
	 * 按出版物类型统计
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getSubscriptionTypeStatistic(Map<String, Object> condition) throws Exception;

	/**
	 * 按出版年限统计
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getSubscriptionYearStatistic(Map<String, Object> condition) throws Exception;

	/**
	 * 
	 * @param string
	 * @param string2
	 * @throws Exception
	 */
	public void generatePages(String string, String string2) throws Exception;

	/**
	 * 按出版社统计
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getPublisherStatistic(Map<String, Object> condition) throws Exception;

	/**
	 * 按出版社统计
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getSubscriptionPublisherStatistic(Map<String, Object> condition) throws Exception;

	/**
	 * 根据ISBN编号获取出版物信息
	 * 
	 * @param isbn
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getPPublicationsByISBN(String isbn) throws Exception;

	PPublications findById(String id) throws Exception;

	/**
	 * 查询订阅列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LLicense> getLicenseList(Map<String, Object> condition, String sort) throws Exception;

	/**
	 * 根据ID查询License
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public LLicense getLicense(String id) throws Exception;

	/**
	 * 按ID查询分页信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PPage getPages(String id) throws Exception;

	/**
	 * 查询指定的多个类别下购买次数最多的前几个publication
	 * 
	 * @param list
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public List<PCsRelation> getHotPubs(List<PCsRelation> list, Integer number) throws Exception;

	/**
	 * 增加publication的成功购买次数1次
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void addBuyTimes(String id) throws Exception;

	/**
	 * 查询图书被查阅次数
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getCounterOfRead(Map<String, Object> condition) throws Exception;

	/**
	 * 判断是否存在并发
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public boolean isExistComplicating(Map<String, Object> condition) throws Exception;

	/**
	 * 查询并发表中数量
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int getComplicatingCount(Map<String, Object> condition) throws Exception;

	/**
	 * 插入并发记录
	 * 
	 * @param comp
	 * @throws Exception
	 */
	public void insertComplicating(LComplicating obj) throws Exception;

	/**
	 * 按照条件删除并发记录
	 * 
	 * @param condition
	 * @throws Exception
	 */
	public void deleteComplicatingByCondition(Map<String, Object> condition) throws Exception;

	/**
	 * 查询简单图书列表
	 * 
	 * @param condition
	 * @param sort
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getSimpleList(Map<String, Object> condition, String sort, Integer number)
			throws Exception;

	/**
	 * 查询分页列表
	 * 
	 * @param condition
	 * @param sort
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getSimplePagingList(Map<String, Object> condition, String sort, Integer pageCount,
			Integer page) throws Exception;

	/**
	 * 查询期刊分页列表
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
	public List<PPublications> getArticlePagingList(Map<String, Object> condition, String sort, Integer pageCount,
			Integer page, CUser user, String ip) throws Exception;

	/**
	 * 查询期刊列表
	 * 
	 * @param condition
	 * @param sort
	 * @param user
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getArticleList(Map<String, Object> condition, String sort, CUser user, String ip)
			throws Exception;

	/**
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getDatabaseList(Map<String, Object> condition, String sort, CUser user, String ip)
			throws Exception;

	/**
	 * 查看机构订阅情况
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<BInstitution> getLicenseInstitutionList(Map<String, Object> condition, String sort) throws Exception;

	/**
	 * 数据库查询
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
	public List<PPublications> getDatabasePageList(Map<String, Object> condition, String sort, Integer pageCount,
			Integer page, CUser user, String ip) throws Exception;

	/**
	 * 查询并发表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LComplicating> getComplicatingList(Map<String, Object> condition, String sort) throws Exception;

	/**
	 * 产品同步操作
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public void uploads(PPublications obj) throws Exception;

	/**
	 * 期刊开始 卷号/年
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String getStartVolume(String id) throws Exception;

	/**
	 * 期刊结束 卷号/年
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String getEndVolume(String id) throws Exception;

	/**
	 * 查询数据库总数
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int getDatabaseCount(Map<String, Object> condition) throws Exception;

	/**
	 * 获取分类法统计
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PCsRelation> getSubStatistic(Map<String, Object> condition) throws Exception;

	/**
	 * 查询已订阅产品包括 OA和free
	 * 
	 * @param condition
	 * @param string
	 * @param pageCount
	 * @param curpage
	 * @param user
	 * @param string2
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getLicensePubPagingList(Map<String, Object> condition, String sort, Integer pageCount,
			Integer page, CUser user, String ip) throws Exception;

	/**
	 * 查询已订阅产品包括 OA和free总数
	 * 
	 * @param condition
	 * @param string
	 * @param pageCount
	 * @param curpage
	 * @param user
	 * @param string2
	 * @return
	 * @throws Exception
	 */
	public int getLicenseCount(Map<String, Object> condition) throws Exception;

	/**
	 * 获取章节列表
	 * 
	 * @param conToc
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getTocList(Map<String, Object> conToc, String sort) throws Exception;

	/**
	 * 获取资源的有效License
	 * 
	 * @param pub
	 * @param user
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public LLicense getVaildLicense(PPublications pub, CUser user, String ip) throws Exception;

	/**
	 * 获取页的有效License
	 * 
	 * @param page
	 * @param user
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public LLicense getVaildLicense(PPage pub, CUser user, String ip) throws Exception;

	/**
	 * 获取各类型的数量集合
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getPubTypeCount(Map<String, Object> condition) throws Exception;

	/**
	 * 获取出版物列表3
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getPubList3(Map<String, Object> condition, String sort, CUser user, String ip)
			throws Exception;

	/**
	 * 处理心跳包
	 * 
	 * @param condition
	 * @throws Exception
	 */
	public void beating(Map<String, Object> condition) throws Exception;

	/**
	 * 删除指定秒数内未更新过的并发
	 * 
	 * @param sec
	 * @throws Exception
	 */
	public void deleteDead(Integer sec) throws Exception;

	public List<LLicense> getLicenseStat() throws Exception;

	public void getEnd(Map<String, Object> uCon) throws Exception;

	/**
	 * 查询指定页所属的章节
	 * 
	 * @param pubId
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	public PPublications getChapter(String pubId, int pageNo) throws Exception;

	/**
	 * 查询期刊关列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<PPublicationsRelation> getPPublicationsRelationList(Map<String, Object> condition, String sort)
			throws Exception;

	/**
	 * 查询期刊关列表
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<Recommend> getRecommendList(Map<String, Object> condition, String sort, Integer pageCount, Integer page)
			throws Exception;

	/**
	 * 获取出版社
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BPublisher getPublisher(String id) throws Exception;

	/**
	 * 本方法为通过collectionID获取下面产品所属的不同出版而设计
	 * 
	 * @author LiuYe
	 * @param condition
	 * @return
	 */
	public List<BPublisher> getDistinctPublisher(Map<String, Object> condition) throws Exception;

	public List<PCsRelation> getDistinctSubject(Map<String, Object> condition) throws Exception;

	/**
	 * 查询pcontentrelation信息 （分刊，合刊）
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<PContentRelation> getPContentRelaionList(Map<String, Object> condition, String sort) throws Exception;

	public List<PPublications> getpublicationsPagingList(Map<String, Object> condition, String sort, Integer pageCount,
			Integer curPage) throws Exception;

	public List<LAccess> forArticleRight(Map<String, Object> condition, String sort, Integer pageCount, Integer curPage)
			throws Exception;

	/**
	 * 查询打包集
	 */
	public List<PCollection> getPCollectionList(Map<String, Object> condition, String sort) throws Exception;

	/**
	 * 通过sql获取PCollection产品包里面所包含的分类（优化版）
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PCsRelation> getSubInfoForCol(Map<String, Object> condition, String sort) throws Exception;

	/**
	 * 通过sql获取PCollection产品包里面所包含的出版社（优化版）
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getpublisherInfoForCol(Map<String, Object> condition, String sort) throws Exception;

	/**
	 * 通过sql获取PCollection产品包里面所包含的语言（优化版）
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getLangInfoForCol(Map<String, Object> condition, String sort) throws Exception;

	public List<PPublications> getPagingList3(Map<String, Object> condition, String sort, Integer pageCount,
			Integer page) throws Exception;

	/**
	 * 获取某分类下产品的购买次数信息
	 * 
	 * @param subjectId
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<PPublications> getBuyTimesInfo(String subjectId, String sort, Integer pubNum) throws Exception;

	List<PPublications> getNewestPublications(Map<String, Object> condition, String sort, long count) throws Exception;

	List<PPublications> getPublicationsNew(Map<String, Object> condition, String sort, Integer pageCount, Integer page)
			throws Exception;

	/**
	 * 产品包内语种统计
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PCcRelation> getCcLangStatistic(Map<String, Object> condition) throws Exception;

	/**
	 * 产品包内类型统计
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PCcRelation> getCcTypeStatistic(Map<String, Object> condition) throws Exception;

	/**
	 * 产品包内类型雨中统计
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<PCcRelation> getCcTypeLangStatistic(Map<String, Object> condition) throws Exception;

	PPublications getPublications(Map<String, Object> condition) throws Exception;

	public PPublications findByIdNew(String id) throws Exception;

	public boolean getLLicenseFlag(String id) throws Exception;

}
