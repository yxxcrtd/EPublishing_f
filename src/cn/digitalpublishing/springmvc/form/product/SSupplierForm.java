package cn.digitalpublishing.springmvc.form.product;

import cn.digitalpublishing.springmvc.form.BaseForm;

public class SSupplierForm extends BaseForm {
	private String id;//统计ID
	private String instituationid;//机构ID
	private String sourceid;//提供商ID		
	private String contentid;//出版物ID
	private String title;//出版物标题
	private Integer type;//出版物类型 1-图书 2-期刊 3-章节 4-文章
	private String author;//出版物作者
	private String isbn;//ISBN
	private String issn;//ISSN
	private String eissn;//EISSN
	private String pubName;//出版商名称
	private String sdate;//Date(YYYY-MM)
	private Integer year;//年(YYYY)
	private Integer month;//月(MM)
	private String platform;//平台名称（CNPe）
	private Integer fullAccess;//全文访问（月）统计总数
	private Integer fullRefused;//全文拒访（月）统计总数
	private Integer refusedLicense;//全文访问没有license（月）统计总数
	private Integer refusedConcurrent;//全文访问超并发数（月）统计总数
	private Integer search;//搜索（月）统计总数
	private Integer searchStandard;//搜索（标准搜索）（月）统计总数
	private Integer searchFederal;//搜索（联邦搜索）（月）统计总数
	private Integer toc;//TOC访问（月）统计总数
	private Integer download;//下载(月)统计总数
	private int count;//总数
	private int counta;//只用存储临时值用
	private int countb;//只用存储临时值用
	private String startyear;//年(YYYY)
	private String endtyear;//年(YYYY)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSourceid() {
		return sourceid;
	}
	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}
	public String getContentid() {
		return contentid;
	}
	public void setContentid(String contentid) {
		this.contentid = contentid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getIssn() {
		return issn;
	}
	public void setIssn(String issn) {
		this.issn = issn;
	}
	public String getEissn() {
		return eissn;
	}
	public void setEissn(String eissn) {
		this.eissn = eissn;
	}
	public String getPubName() {
		return pubName;
	}
	public void setPubName(String pubName) {
		this.pubName = pubName;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public Integer getFullAccess() {
		return fullAccess;
	}
	public void setFullAccess(Integer fullAccess) {
		this.fullAccess = fullAccess;
	}
	public Integer getFullRefused() {
		return fullRefused;
	}
	public void setFullRefused(Integer fullRefused) {
		this.fullRefused = fullRefused;
	}
	public Integer getRefusedLicense() {
		return refusedLicense;
	}
	public void setRefusedLicense(Integer refusedLicense) {
		this.refusedLicense = refusedLicense;
	}
	public Integer getRefusedConcurrent() {
		return refusedConcurrent;
	}
	public void setRefusedConcurrent(Integer refusedConcurrent) {
		this.refusedConcurrent = refusedConcurrent;
	}
	public Integer getSearch() {
		return search;
	}
	public void setSearch(Integer search) {
		this.search = search;
	}
	public Integer getSearchStandard() {
		return searchStandard;
	}
	public void setSearchStandard(Integer searchStandard) {
		this.searchStandard = searchStandard;
	}
	public Integer getSearchFederal() {
		return searchFederal;
	}
	public void setSearchFederal(Integer searchFederal) {
		this.searchFederal = searchFederal;
	}
	public Integer getToc() {
		return toc;
	}
	public void setToc(Integer toc) {
		this.toc = toc;
	}
	public Integer getDownload() {
		return download;
	}
	public void setDownload(Integer download) {
		this.download = download;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCounta() {
		return counta;
	}
	public void setCounta(int counta) {
		this.counta = counta;
	}
	public int getCountb() {
		return countb;
	}
	public void setCountb(int countb) {
		this.countb = countb;
	}
	public String getStartyear() {
		return startyear;
	}
	public void setStartyear(String startyear) {
		this.startyear = startyear;
	}
	public String getEndtyear() {
		return endtyear;
	}
	public void setEndtyear(String endtyear) {
		this.endtyear = endtyear;
	}
	public String getInstituationid() {
		return instituationid;
	}
	public void setInstituationid(String instituationid) {
		this.instituationid = instituationid;
	}
	

}
