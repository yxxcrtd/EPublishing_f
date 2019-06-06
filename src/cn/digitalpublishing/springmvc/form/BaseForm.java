package cn.digitalpublishing.springmvc.form;

import java.util.HashMap;
import java.util.Map;

public class BaseForm {
	
	private String action = "";
	
	private String formAction = "";
	
	private int count = 0;

	private int pageCount = 10;
	
	private int curpage = 0;
	
	private int curCount = 0;
	
	private Map<String,Object> condition = new HashMap<String,Object>();
	
	private String id;
	
	private String msg;
	
	private String url;
	
	private String isSuccess;
	
	private String returnValue;
	
	private String prefixHTML = "<font class=\"highlight\">"; 
	
    private String suffixHTML = "</font>"; 
    
    //搜索条件开始
    private String language;//语言
    private Integer searchsType;
	private String searchValue;
	private String searchValue2;
	private String pubType;
	private String publisher;
	private String pubDate;
	private String taxonomy;
	private String taxonomyEn;
	private String searchOrder;
	private String lcense;
	
	private String code;
	private String pCode;
	private String publisherId;
	private String subParentId;
	private String parentTaxonomy;
	private String parentTaxonomyEn;
	
	// For Redis
	private String title;
	
	// 搜索条件结束
	private String searchsType2;
	public String getSearchsType2() {
		return searchsType2;
	}

	public void setSearchsType2(String searchsType2) {
		this.searchsType2 = searchsType2;
	}

	public String getLanguage() {
		return language;
	}

	public Integer getSearchsType() {
		return searchsType;
	}

	public void setSearchsType(Integer searchsType) {
		this.searchsType = searchsType;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getSearchValue2() {
		return searchValue2;
	}

	public void setSearchValue2(String searchValue2) {
		this.searchValue2 = searchValue2;
	}

	public String getPubType() {
		return pubType;
	}

	public void setPubType(String pubType) {
		this.pubType = pubType;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getTaxonomy() {
		return taxonomy;
	}

	public void setTaxonomy(String taxonomy) {
		this.taxonomy = taxonomy;
	}

	public String getTaxonomyEn() {
		return taxonomyEn;
	}

	public void setTaxonomyEn(String taxonomyEn) {
		this.taxonomyEn = taxonomyEn;
	}

	public String getSearchOrder() {
		return searchOrder;
	}

	public void setSearchOrder(String searchOrder) {
		this.searchOrder = searchOrder;
	}

	public String getLcense() {
		return lcense;
	}

	public void setLcense(String lcense) {
		this.lcense = lcense;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getSubParentId() {
		return subParentId;
	}

	public void setSubParentId(String subParentId) {
		this.subParentId = subParentId;
	}

	public String getParentTaxonomy() {
		return parentTaxonomy;
	}

	public void setParentTaxonomy(String parentTaxonomy) {
		this.parentTaxonomy = parentTaxonomy;
	}

	public String getParentTaxonomyEn() {
		return parentTaxonomyEn;
	}

	public void setParentTaxonomyEn(String parentTaxonomyEn) {
		this.parentTaxonomyEn = parentTaxonomyEn;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getFormAction() {
		return formAction;
	}

	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public Map<String, Object> getCondition() {
		return condition;
	}

	public void setCondition(Map<String, Object> condition) {
		this.condition = condition;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public int getCurpage() {
		return curpage;
	}

	public void setCurpage(int curpage) {
		this.curpage = curpage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPrefixHTML() {
		return prefixHTML;
	}

	public void setPrefixHTML(String prefixHTML) {
		this.prefixHTML = prefixHTML;
	}

	public String getSuffixHTML() {
		return suffixHTML;
	}

	public void setSuffixHTML(String suffixHTML) {
		this.suffixHTML = suffixHTML;
	}

	public int getCurCount() {
		return curCount;
	}

	public void setCurCount(int curCount) {
		this.curCount = curCount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
