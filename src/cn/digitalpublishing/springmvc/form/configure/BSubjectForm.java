package cn.digitalpublishing.springmvc.form.configure;

import cn.digitalpublishing.springmvc.form.BaseForm;

public class BSubjectForm extends BaseForm {

	/**
	 * 分类编码
	 */
	private String code;
	private String pCode;
	/**
	 * 类型 1-电子书 2-期刊 3-章节
	 */
	private String type;
	/**
	 * A-Z排序
	 */
	private String order;
	
	private String publisherId;
	
	private String pubYear;
	
	private String subParentId;
	
	private String azUrl;//A-Zurl
	
	private String isLicense;//是否订阅 1、已订阅 2、未订阅
	
	private String treeCode;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getPubYear() {
		return pubYear;
	}

	public void setPubYear(String pubYear) {
		this.pubYear = pubYear;
	}

	public String getSubParentId() {
		return subParentId;
	}

	public void setSubParentId(String subParentId) {
		this.subParentId = subParentId;
	}

	public String getAzUrl() {
		return azUrl;
	}

	public void setAzUrl(String azUrl) {
		this.azUrl = azUrl;
	}

	public String getIsLicense() {
		return isLicense;
	}

	public void setIsLicense(String isLicense) {
		this.isLicense = isLicense;
	}

	public String getTreeCode() {
		return treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}
}
