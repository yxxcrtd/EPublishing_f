package cn.digitalpublishing.springmvc.form.product;

import java.util.List;

import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.springmvc.form.BaseForm;

/**
 * @author lifh
 *
 */
public class PPublicationsForm extends BaseForm {

	private PPublications obj;
	
	private List<PPublications> publications;	
	
	private String lcurr;
	
	private Double price;
	
	private CUser recommendUser;
	
	private Boolean isRxpert;
	
	private String pubYear;
	
	private String publisherId;
	
	/**
	 * 分类编号
	 */
	private String code;
	/**
	 * 产品类型 1-书籍 2 期刊
	 */
	private String type;
	/**
	 * 父分类编号
	 */
	private String pCode;
	/**
	 * 排序A-Z
	 */
	private String order;
	private String azUrl;//A-Z使用的url
	
	private String subParentId = "0";
	
	private String isReady;//用于判断是否是已订阅
	
	private String letter;//A-Z
	
	private String orderDesc;//排序
	
	private Integer nextPage;
	
	private String isLicense;//是否是已订阅
	
	private String volumeId; //卷ID
	
	private String issueId; //期ID
	
	private String parentId;//刊ID
	private String pbookID;
	private String chaperID;
	private Integer chaperShow;
	
	private Integer subscribedIp;
	private Integer subscribedUser;
	private Integer oa;
	private Integer free;
	private Integer favorite;
	private Integer journalType;
	private Integer periodicType;

	public PPublications getObj() {
		return obj;
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

	public void setObj(PPublications obj) {
		this.obj = obj;
	}

	public List<PPublications> getPublications() {
		return publications;
	}

	public void setPublications(List<PPublications> publications) {
		this.publications = publications;
	}

	public String getLcurr() {
		return lcurr;
	}

	public void setLcurr(String lcurr) {
		this.lcurr = lcurr;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public CUser getRecommendUser() {
		return recommendUser;
	}

	public void setRecommendUser(CUser recommendUser) {
		this.recommendUser = recommendUser;
	}

	public Boolean getIsRxpert() {
		return isRxpert;
	}

	public void setIsRxpert(Boolean isRxpert) {
		this.isRxpert = isRxpert;
	}

	public String getPubYear() {
		return pubYear;
	}

	public void setPubYear(String pubYear) {
		this.pubYear = pubYear;
	}

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getAzUrl() {
		return azUrl;
	}

	public void setAzUrl(String azUrl) {
		this.azUrl = azUrl;
	}

	public String getSubParentId() {
		return subParentId;
	}

	public void setSubParentId(String subParentId) {
		this.subParentId = subParentId;
	}

	public String getIsReady() {
		return isReady;
	}

	public void setIsReady(String isReady) {
		this.isReady = isReady;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	public String getIsLicense() {
		return isLicense;
	}

	public void setIsLicense(String isLicense) {
		this.isLicense = isLicense;
	}

	public String getVolumeId() {
		return volumeId;
	}

	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPbookID() {
		return pbookID;
	}

	public void setPbookID(String pbookID) {
		this.pbookID = pbookID;
	}

	public String getChaperID() {
		return chaperID;
	}

	public void setChaperID(String chaperID) {
		this.chaperID = chaperID;
	}

	public Integer getChaperShow() {
		return chaperShow;
	}

	public void setChaperShow(Integer chaperShow) {
		this.chaperShow = chaperShow;
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

	public Integer getFavorite() {
		return favorite;
	}

	public void setFavorite(Integer favorite) {
		this.favorite = favorite;
	}
	
	
}