package cn.digitalpublishing.ep.po;

import java.io.Serializable;
import java.util.Date;

import cn.digitalpublishing.po.PContent;

/**
 * @author lifh
 *接入日至信息
 */
@SuppressWarnings("serial")
public class LAccess implements Serializable {

	private String id;
	private LLicense license;
	private PPublications publications;
	private String ip;//访问IP
    private String userId;//访问用户ID，如果有就记录，没有就不记录
    private String institutionId;//访问机构ID，如果有就记录，没有就不记录
	private Date createOn;//创建时间
    private Integer type;//操作类型1-访问摘要 2-访问内容 3-检索4-下载
    private String platform;//平台名称CNPe
    private Integer access;//访问状态1-访问成功 2-访问拒绝
    private String activity;//操作行为(类型是搜索时，记录搜索关键字)
    private String clientInfo;//用户客户端信息
	private String year;
    private String month;
    private String pageId;
    private Integer searchType;//1-常规检索 , 2-高级检索 , 3-分类法点击
    private Integer refusedVisitType; //1-没有License,2-超出并发数
    private Integer syncType ;//1-未同步,2-已同步 
    
    private Integer month1;
    private Integer month2;
    private Integer month3;
    private Integer month4;
    private Integer month5;
    private Integer month6;
    private Integer month7;
    private Integer month8;
    private Integer month9;
    private Integer month10;
    private Integer month11;
    private Integer month12;
    

	public Integer getSyncType() {
		return syncType;
	}
	public void setSyncType(Integer syncType) {
		this.syncType = syncType;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LLicense getLicense() {
		return license;
	}
	public void setLicense(LLicense license) {
		this.license = license;
	}
	public PPublications getPublications() {
		return publications;
	}
	public void setPublications(PPublications publications) {
		this.publications = publications;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public Integer getAccess() {
		return access;
	}
	public void setAccess(Integer access) {
		this.access = access;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
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
    public String getInstitutionId() {
		return institutionId;
	}
	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}
	public Integer getMonth1() {
		return month1;
	}
	public void setMonth1(Integer month1) {
		this.month1 = month1;
	}
	public Integer getMonth2() {
		return month2;
	}
	public void setMonth2(Integer month2) {
		this.month2 = month2;
	}
	public Integer getMonth3() {
		return month3;
	}
	public void setMonth3(Integer month3) {
		this.month3 = month3;
	}
	public Integer getMonth4() {
		return month4;
	}
	public void setMonth4(Integer month4) {
		this.month4 = month4;
	}
	public Integer getMonth5() {
		return month5;
	}
	public void setMonth5(Integer month5) {
		this.month5 = month5;
	}
	public Integer getMonth6() {
		return month6;
	}
	public void setMonth6(Integer month6) {
		this.month6 = month6;
	}
	public Integer getMonth7() {
		return month7;
	}
	public void setMonth7(Integer month7) {
		this.month7 = month7;
	}
	public Integer getMonth8() {
		return month8;
	}
	public void setMonth8(Integer month8) {
		this.month8 = month8;
	}
	public Integer getMonth9() {
		return month9;
	}
	public void setMonth9(Integer month9) {
		this.month9 = month9;
	}
	public Integer getMonth10() {
		return month10;
	}
	public void setMonth10(Integer month10) {
		this.month10 = month10;
	}
	public Integer getMonth11() {
		return month11;
	}
	public void setMonth11(Integer month11) {
		this.month11 = month11;
	}
	public Integer getMonth12() {
		return month12;
	}
	public void setMonth12(Integer month12) {
		this.month12 = month12;
	}
	public Integer getSearchType() {
		return searchType;
	}
	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}
	public Integer getRefusedVisitType() {
		return refusedVisitType;
	}
	public void setRefusedVisitType(Integer refusedVisitType) {
		this.refusedVisitType = refusedVisitType;
	}	
	 public String getClientInfo() {
		return clientInfo;
	}
	public void setClientInfo(String clientInfo) {
		this.clientInfo = clientInfo;
	}
}
