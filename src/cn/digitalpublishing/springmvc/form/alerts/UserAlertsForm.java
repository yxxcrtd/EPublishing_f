package cn.digitalpublishing.springmvc.form.alerts;

import cn.digitalpublishing.springmvc.form.BaseForm;

public class UserAlertsForm  extends BaseForm{
//	出版物isbn编码，用于查询出版物信息
	private String code;
//	出版物名称
	private String pubTitle;
//	出版物类型
//	private Integer pubType;
//	出版物中图分类法（学科主题）
	private String subName;
//	出版物isbn/issn
	private String isbn;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPubTitle() {
		return pubTitle;
	}

	public void setPubTitle(String pubTitle) {
		this.pubTitle = pubTitle;
	}


	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
