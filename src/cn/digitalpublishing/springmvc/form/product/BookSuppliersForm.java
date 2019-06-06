package cn.digitalpublishing.springmvc.form.product;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.springmvc.form.BaseForm;
public class BookSuppliersForm extends BaseForm {
	
	private LAccess obj =new LAccess();
	private String id;
	private LLicense license;
	private PPublications publications;
	private String ip;//访问IP
    private String userId;//访问用户ID，如果有就记录，没有就不记录
    private String institutionId;//访问机构ID，如果有就记录，没有就不记录
	private Date createOn;//创建时间
    private Integer type;//操作类型1-访问摘要 2-访问内容 3-检索
    private String platform;//平台名称CNPe
    private Integer access;//访问状态1-访问成功 2-访问拒绝
    private String activity;//操作行为(类型是搜索时，记录搜索关键字)

    private String month;
    private String pageId;
    private Integer searchType;//1-常规检索 , 2-高级检索 , 3-分类法点击
    
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
    
    private Integer pubtype;//1-图书,2-期刊
	private String year="2013";
	private String[] month2Display;
	private String startMonth="01";
	private String endMonth="12";
	private int startYear=2013;
	private int endYear=2030;
	private List<String> yearList = new ArrayList<String>();
	private List<String> monthList = new ArrayList<String>();
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
	public String getInstitutionId() {
		return institutionId;
	}
	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
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
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public Integer getSearchType() {
		return searchType;
	}
	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
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
	public LAccess getObj() {
		return obj;
	}
	public void setObj(LAccess obj) {
		this.obj = obj;
	}
	public String[] getMonth2Display() {
		return month2Display;
	}
	public void setMonth2Display(String[] month2Display) {
		this.month2Display = month2Display;
	}
	public String getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}
	public String getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}
	public int getStartYear() {
		return startYear;
	}
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	public int getEndYear() {
		return endYear;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	public List<String> getYearList() {
		return yearList;
	}
	public void setYearList(List<String> yearList) {
		this.yearList = yearList;
	}
	public List<String> getMonthList() {
		return monthList;
	}
	public void setMonthList(List<String> monthList) {
		this.monthList = monthList;
	}
	public Integer getPubtype() {
		return pubtype;
	}
	public void setPubtype(Integer pubtype) {
		this.pubtype = pubtype;
	}
    

}
