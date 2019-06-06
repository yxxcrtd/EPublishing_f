package cn.digitalpublishing.ep.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * EpubPPublications entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class PPublications implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 父出版物
	 */
	private PPublications publications;
	/**
	 * 出版商
	 */
	private BPublisher publisher;
	/**
	 * 出版物标题
	 */
	// to be indexed（文献题名）
	private String title;
	/**
	 * 子标题
	 */
	private String subTitle;
	/**
	 * 丛书名称
	 */
	private String series;

	/**
	 * 版本号
	 */
	private String edition;

	/**
	 * 译名
	 */
	private String chineseTitle;//译名
	/**
	 * 作者
	 */
	// to be indexed (作者)
	private String author;
	/**
	 * 出版物编号 ISBN ISSN
	 */
	// to be indexed (ISBN)
	private String code;
	/**
	 * 电子期刊编号
	 */
	private String eissn;
	/**
	 * 纸版期刊编号
	 */
	private String pissn;

	/**
	 * 参考价格
	 */
	private Double listPrice;
	/**
	 * 参考价格币种
	 */
	private String lcurr;
	/**
	 * 浏览百分比
	 */
	private String browsePrecent;
	/**
	 * 是否在产品包中 1-不在 2-在
	 */
	private Integer inCollection;
	/**
	 * 类型 1-电子书 2-期刊 3-章节 4-文章 5-数据库 6-期刊卷 7 期刊期 99产品包(用来在订单明细中区分)
	 */
	private Integer type;
	/**
	 * 章节开始页码
	 */
	//private Integer startPage;
	private String startPage;
	/**
	 * 章节结束页码
	 */
	//private Integer endPage;
	private String endPage;
	/**
	 * 封面
	 */
	private String cover;
	/**
	 * 创建时间
	 */
	private Date createOn;
	/**
	 * 更新时间
	 */
	private Date updateOn;
	/**
	 * 期刊订阅日期
	 */
	private Date subscribedDate;
	/**
	 * pdf 存放路径
	 */
	private String pdf;
	/**
	 * 元数据存放路径
	 */
	private String path;
	/**
	 * 语言
	 */
	private String lang;
	/**
	 * 简介
	 */
	// to be indexed (简介)
	private String remark;
	/**
	 * 是否放在主页 1-否 2-是
	 */
	private Integer homepage;
	/**
	 * 是否最新 1-否 2-是
	 */
	private Integer newest;
	/**
	 * 是否精选 1-否 2-是
	 */
	private Integer selected;
	/**
	 * 是否特别优惠
	 */
	private Integer special;
	/**
	 * 出版时间
	 */
	private String pubDate;
	/**
	 * 出版物分类法
	 */
	private String pubSubject;
	/**
	 * 1-未上架 2-已上架
	 */
	private Integer status;
	/**
	 * 开源状态 1、不开源 2、开源
	 */
	private Integer oa;
	/**
	 * 免费状态 1、不免费 ；2、免费
	 */
	private Integer free;

	/**
	 * 是否海外资源（0 默认值，不是海外资源；1，是海外资源）
	 */
	private Integer f;
	private Double coefficient; // 系数
	private Double coefficient1; // 并发系数

	public Integer getF() {
		return f;
	}

	public void setF(Integer f) {
		this.f = f;
	}
	
	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

	public Double getCoefficient1() {
		return coefficient1;
	}

	public void setCoefficient1(Double coefficient1) {
		this.coefficient1 = coefficient1;
	}

	/**
	 * 是不是本地资源 1-否 ；2-是
	 */
	private Integer local;
	/**
	 * 纸版软包装ISBN
	 */
	private String sisbn;
	/**
	 * 纸版硬包装ISBN
	 */
	private String hisbn;
	/**
	 * 出版物分类法
	 */
	private String pubSubjectEn;
	/**
	 * 活动信息
	 */
	private String activity;
	/**
	 * 年份
	 */
	private String year;
	/**
	 * 出版月份
	 */
	private String month;
	/**
	 * 出版日
	 */
	private String day;
	/**
	 * 卷号
	 */
	private String volumeCode;
	/**
	 * 期号
	 */
	private String issueCode;
	/**
	 * 编号
	 */
	private String treecode;
	/**
	 * 排序
	 */
	private Integer order;
	/**
	 * 期刊 卷期 排序
	 */
	private Double journalOrder;
	/**
	 * DOI
	 */
	private String doi;
	private String startVolume;//开始年  1/2006 
	private String endVolume;//结束年 60/2013
	/**
	 * 卷
	 */
	private PPublications volume;
	/**
	 * 期
	 */
	private PPublications issue;
	/**
	 * 卷下的子出版物
	 */
	private Set<PPublications> volumePublications;
	/**
	 * 期下的子出版物
	 */
	private Set<PPublications> issuePublications;
	/**
	 * 出版物和分类法关系
	 */
	private Set<PCsRelation> csRelations = new HashSet<PCsRelation>();
	/**
	 * 授权关系
	 */
	private Set<LLicense> licenses = new HashSet<LLicense>();
	/**
	 * 收藏
	 */
	private Set<CFavourites> favouriteses = new HashSet<CFavourites>();
	/**
	 * 产品包和出版物关系
	 */
	private Set<PCcRelation> ccRelations = new HashSet<PCcRelation>();
	/**
	 * 荐购
	 */
	private Set<RRecommend> recommends = new HashSet<RRecommend>();
	/**
	 * 价格信息
	 */
	private Set<PPrice> prices = new HashSet<PPrice>();
	/**
	 * 页面
	 */
	private Set<PPage> pages = new HashSet<PPage>();
	/**
	 * 接入日至信息
	 */
	private Set<LAccess> access = new HashSet<LAccess>();
	/**
	 * 子出版物
	 */
	private Set<PPublications> publicationses = new HashSet<PPublications>();
	/**
	 * 订阅提醒、用于对已定产品进行更新提醒、续费提醒等
	 */
	private Set<LUserAlertsLog> alertsLog = new HashSet<LUserAlertsLog>();

	// Constructors
	//自定义
	private String subjectName;//分类名称
	private String publisherName;//出版商名称
	private String publisherId;//出版商Id
	private Integer latest;//最新状态
	private Integer subscribedIp;//已订阅 IP
	private Integer subscribedUser;//已订阅个人
	private Integer buyInLicance;//购买-在licance中有效===发现与个人查询语句一样 ，暂时废弃
	private Integer buyInDetail;//购买-在订单明细中 状态<99 
	private Integer buyTimes;//成功购买次数，无论当前是否有效
	private Integer recommand;//荐够
	private Integer favorite;//喜好--放入收藏夹
	private String authorAlias;//作者别名，用来进行高亮处理
	private Integer exLicense;//License过期总数
	private List<PCsRelation> csList = new ArrayList<PCsRelation>();//分类列表
	private List<PPrice> priceList = new ArrayList<PPrice>();
	private List<BInstitution> InstitutionList = new ArrayList<BInstitution>();//购买机构列表
	private String readUrl;//当文章时查出在线阅读地址
	/**
	 * 声明个变量，sql查询的时候做缓存用，非表字段，如需调用 请赋值（刘冶）
	 */
	private String eachCount;
	/**
	 * 资源访问路径
	 */
	private String webUrl;
	/**
	 * 创建时间
	 */
	private String createDate;
	/**
	 * 资源提供商ID
	 */
	private String sourceId;
	/**
	 * 关键词
	 */
	private String keywords;
	/**
	 * 引用文献
	 */
	private String reference;
	/**
	 * 刊类型 1-学术刊 2-杂志刊
	 */
	private Integer journalType;
	/**
	 * 周期类型 1-周刊 2-半月刊 3-月刊 4-季刊 5-半年刊 6-年刊 7-其它
	 */
	private Integer periodicType;
	/**
	 * EPUP解压路径
	 */
	private String epubDir;
	/**
	 * 文件类型1:pdf;2:epub
	 * 
	 * 
	 * 
	 * 
	 */
	private Integer fileType;

	/**
	 * 标识数字标识是哪个字段 如果issn存储的是eissn 这个值填写ecode;如果eissn存储的是eissn 这个值填写eissn;图书信息同理
	 */
	private String ecode;
	/**
	 * 全文
	 */
	private String fullText;

	private Set<BPDACounter> pdaCounters;
	/**
	 * 1-不可用(中图未选用) 2-可用（中图已经选用）3-政治原因 4-版权原因 5-主权声明
	 */
	private Integer available;
	/**
	 * 
	 * 这是假删除的状态
	 */
	private Integer falsedelete;

	/**
	 * 是否已经购买（个人）注:刘冶因具体需求添加，并非实际表机构，其他人员如需要使用，请自行赋值
	 */
	private Integer inLicense;
	/**
	 * 是否在购物车（个人）注:刘冶因具体需求添加，并非实际表机构，其他人员如需要使用，请自行赋值
	 */
	private Integer inDetail;
	/**
	 * 是否已经购买（机构）注:刘冶因具体需求添加，并非实际表机构，其他人员如需要使用，请自行赋值
	 */
	private Integer insInLicense;
	/**
	 * 是否在购物车（机构）注:刘冶因具体需求添加，并非实际表机构，其他人员如需要使用，请自行赋值
	 */
	private Integer insInDetail;
	/**
	 * 是否已收藏
	 */
	private Integer insfavorite;//机构是否收藏
	/**
	 * 
	 * CN
	 */
	private String cn;

	public Integer getFalsedelete() {
		return falsedelete;
	}

	public void setFalsedelete(Integer falsedelete) {
		this.falsedelete = falsedelete;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public String getStartVolume() {
		return startVolume;
	}

	public void setStartVolume(String startVolume) {
		this.startVolume = startVolume;
	}

	public String getEndVolume() {
		return endVolume;
	}

	public void setEndVolume(String endVolume) {
		this.endVolume = endVolume;
	}

	public String getReadUrl() {
		return readUrl;
	}

	public void setReadUrl(String readUrl) {
		this.readUrl = readUrl;
	}

	public Integer getExLicense() {
		return exLicense;
	}

	public void setExLicense(Integer exLicense) {
		this.exLicense = exLicense;
	}

	public List<BInstitution> getInstitutionList() {
		return InstitutionList;
	}

	public void setInstitutionList(List<BInstitution> institutionList) {
		InstitutionList = institutionList;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Set<LUserAlertsLog> getAlertsLog() {
		return alertsLog;
	}

	public void setAlertsLog(Set<LUserAlertsLog> alertsLog) {
		this.alertsLog = alertsLog;
	}

	public String getAuthorAlias() {
		return authorAlias;
	}

	public void setAuthorAlias(String authorAlias) {
		this.authorAlias = authorAlias;
	}

	public String getChineseTitle() {
		return chineseTitle;
	}

	public void setChineseTitle(String chineseTitle) {
		this.chineseTitle = chineseTitle;
	}

	public Set<LAccess> getAccess() {
		return access;
	}

	public void setAccess(Set<LAccess> access) {
		this.access = access;
	}

	public List<PCsRelation> getCsList() {
		return csList;
	}

	public void setCsList(List<PCsRelation> csList) {
		this.csList = csList;
	}

	public Integer getSubscribedIp() {
		return subscribedIp;
	}

	public void setSubscribedIp(Integer subscribedIp) {
		this.subscribedIp = subscribedIp;
	}

	public Integer getSubscribedUser() {
		return subscribedUser;
	}

	public void setSubscribedUser(Integer subscribedUser) {
		this.subscribedUser = subscribedUser;
	}

	public List<PPrice> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<PPrice> priceList) {
		this.priceList = priceList;
	}

	public Integer getBuyInLicance() {
		return buyInLicance;
	}

	public void setBuyInLicance(Integer buyInLicance) {
		this.buyInLicance = buyInLicance;
	}

	public Integer getBuyInDetail() {
		return buyInDetail;
	}

	public void setBuyInDetail(Integer buyInDetail) {
		this.buyInDetail = buyInDetail;
	}

	public Integer getRecommand() {
		return recommand;
	}

	public void setRecommand(Integer recommand) {
		this.recommand = recommand;
	}

	public Integer getFavorite() {
		return favorite;
	}

	public void setFavorite(Integer favorite) {
		this.favorite = favorite;
	}

	public Integer getOa() {
		return oa;
	}

	public void setOa(Integer oa) {
		this.oa = oa;
	}

	public Integer getFree() {
		return free;
	}

	public void setFree(Integer free) {
		this.free = free;
	}

	public Integer getLatest() {
		return latest;
	}

	public void setLatest(Integer latest) {
		this.latest = latest;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	/*
	 * public Integer getStartPage() { return startPage; }
	 * 
	 * public void setStartPage(Integer startPage) { this.startPage = startPage;
	 * }
	 * 
	 * public Integer getEndPage() { return endPage; }
	 * 
	 * public void setEndPage(Integer endPage) { this.endPage = endPage; }
	 */

	public String getPublisherName() {
		return publisherName;
	}

	public String getStartPage() {
		return startPage;
	}

	public void setStartPage(String startPage) {
		this.startPage = startPage;
	}

	public String getEndPage() {
		return endPage;
	}

	public void setEndPage(String endPage) {
		this.endPage = endPage;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/** default constructor */
	public PPublications() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PPublications getPublications() {
		return publications;
	}

	public void setPublications(PPublications publications) {
		this.publications = publications;
	}

	public BPublisher getPublisher() {
		return publisher;
	}

	public void setPublisher(BPublisher publisher) {
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getListPrice() {
		return listPrice;
	}

	public void setListPrice(Double listPrice) {
		this.listPrice = listPrice;
	}

	public String getLcurr() {
		return lcurr;
	}

	public void setLcurr(String lcurr) {
		this.lcurr = lcurr;
	}

	public String getBrowsePrecent() {
		return browsePrecent;
	}

	public void setBrowsePrecent(String browsePrecent) {
		this.browsePrecent = browsePrecent;
	}

	public Integer getInCollection() {
		return inCollection;
	}

	public void setInCollection(Integer inCollection) {
		this.inCollection = inCollection;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public Date getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(Date updateOn) {
		this.updateOn = updateOn;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getHomepage() {
		return homepage;
	}

	public void setHomepage(Integer homepage) {
		this.homepage = homepage;
	}

	public Integer getNewest() {
		return newest;
	}

	public void setNewest(Integer newest) {
		this.newest = newest;
	}

	public Integer getSelected() {
		return selected;
	}

	public void setSelected(Integer selected) {
		this.selected = selected;
	}

	public Integer getSpecial() {
		return special;
	}

	public void setSpecial(Integer special) {
		this.special = special;
	}

	public Set<PCsRelation> getCsRelations() {
		return csRelations;
	}

	public void setCsRelations(Set<PCsRelation> csRelations) {
		this.csRelations = csRelations;
	}

	public Set<LLicense> getLicenses() {
		return licenses;
	}

	public void setLicenses(Set<LLicense> licenses) {
		this.licenses = licenses;
	}

	public Set<CFavourites> getFavouriteses() {
		return favouriteses;
	}

	public void setFavouriteses(Set<CFavourites> favouriteses) {
		this.favouriteses = favouriteses;
	}

	public Set<PCcRelation> getCcRelations() {
		return ccRelations;
	}

	public void setCcRelations(Set<PCcRelation> ccRelations) {
		this.ccRelations = ccRelations;
	}

	public Set<RRecommend> getRecommends() {
		return recommends;
	}

	public void setRecommends(Set<RRecommend> recommends) {
		this.recommends = recommends;
	}

	public Set<PPrice> getPrices() {
		return prices;
	}

	public void setPrices(Set<PPrice> prices) {
		this.prices = prices;
	}

	public Set<PPage> getPages() {
		return pages;
	}

	public void setPages(Set<PPage> pages) {
		this.pages = pages;
	}

	public Set<PPublications> getPublicationses() {
		return publicationses;
	}

	public void setPublicationses(Set<PPublications> publicationses) {
		this.publicationses = publicationses;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getPubSubject() {
		return pubSubject;
	}

	public void setPubSubject(String pubSubject) {
		this.pubSubject = pubSubject;
	}

	public Integer getLocal() {
		return local;
	}

	public void setLocal(Integer local) {
		this.local = local;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getSisbn() {
		return sisbn;
	}

	public void setSisbn(String sisbn) {
		this.sisbn = sisbn;
	}

	public String getHisbn() {
		return hisbn;
	}

	public void setHisbn(String hisbn) {
		this.hisbn = hisbn;
	}

	public String getPubSubjectEn() {
		return pubSubjectEn;
	}

	public void setPubSubjectEn(String pubSubjectEn) {
		this.pubSubjectEn = pubSubjectEn;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getBuyTimes() {
		return buyTimes;
	}

	public void setBuyTimes(Integer buyTimes) {
		this.buyTimes = buyTimes;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getVolumeCode() {
		return volumeCode;
	}

	public void setVolumeCode(String volumeCode) {
		this.volumeCode = volumeCode;
	}

	public String getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(String issueCode) {
		this.issueCode = issueCode;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public PPublications getVolume() {
		return volume;
	}

	public void setVolume(PPublications volume) {
		this.volume = volume;
	}

	public PPublications getIssue() {
		return issue;
	}

	public void setIssue(PPublications issue) {
		this.issue = issue;
	}

	public Set<PPublications> getVolumePublications() {
		return volumePublications;
	}

	public void setVolumePublications(Set<PPublications> volumePublications) {
		this.volumePublications = volumePublications;
	}

	public Set<PPublications> getIssuePublications() {
		return issuePublications;
	}

	public void setIssuePublications(Set<PPublications> issuePublications) {
		this.issuePublications = issuePublications;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getEpubDir() {
		return epubDir;
	}

	public void setEpubDir(String epubDir) {
		this.epubDir = epubDir;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public Set<BPDACounter> getPdaCounters() {
		return pdaCounters;
	}

	public void setPdaCounters(Set<BPDACounter> pdaCounters) {
		this.pdaCounters = pdaCounters;
	}

	public String getFullText() {
		return fullText;
	}

	public void setFullText(String fullText) {
		this.fullText = fullText;
	}

	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getEissn() {
		return eissn;
	}

	public void setEissn(String eissn) {
		this.eissn = eissn;
	}

	public String getTreecode() {
		return treecode;
	}

	public void setTreecode(String treecode) {
		this.treecode = treecode;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Double getJournalOrder() {
		return journalOrder;
	}

	public void setJournalOrder(Double journalOrder) {
		this.journalOrder = journalOrder;
	}

	public Date getSubscribedDate() {
		return subscribedDate;
	}

	public void setSubscribedDate(Date subscribedDate) {
		this.subscribedDate = subscribedDate;
	}

	public Integer getInLicense() {
		return inLicense;
	}

	public void setInLicense(Integer inLicense) {
		this.inLicense = inLicense;
	}

	public Integer getInDetail() {
		return inDetail;
	}

	public void setInDetail(Integer inDetail) {
		this.inDetail = inDetail;
	}

	public Integer getInsfavorite() {
		return insfavorite;
	}

	public void setInsfavorite(Integer insfavorite) {
		this.insfavorite = insfavorite;
	}

	public Integer getInsInLicense() {
		return insInLicense;
	}

	public void setInsInLicense(Integer insInLicense) {
		this.insInLicense = insInLicense;
	}

	public Integer getInsInDetail() {
		return insInDetail;
	}

	public void setInsInDetail(Integer insInDetail) {
		this.insInDetail = insInDetail;
	}

	public String getEachCount() {
		return eachCount;
	}

	public void setEachCount(String eachCount) {
		this.eachCount = eachCount;
	}

	public String getPissn() {
		return pissn;
	}

	public void setPissn(String pissn) {
		this.pissn = pissn;
	}

	public Integer getJournalType() {
		return journalType;
	}

	public void setJournalType(Integer journalType) {
		this.journalType = journalType;
	}

	public Integer getPeriodicType() {
		return periodicType;
	}

	public void setPeriodicType(Integer periodicType) {
		this.periodicType = periodicType;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

}