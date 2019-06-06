package cn.digitalpublishing.springmvc.form.configure;

import cn.digitalpublishing.ep.po.BPublisher;
import cn.digitalpublishing.springmvc.form.BaseForm;

public class BPublisherForm extends BaseForm {
	
	private BPublisher obj=new BPublisher();
	
	private String id;
	
	private String publisherCode;
	
	private String name;
	
	private String nameEn;
	
	private String letter;
	
	private Integer order;
	
	private String browsePrecent;
	
	private String relationCode;
	
	private String logo;
	
	private String desc;

	public BPublisher getObj() {
		return obj;
	}

	public void setObj(BPublisher obj) {
		this.obj = obj;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPublisherCode() {
		return publisherCode;
	}

	public void setPublisherCode(String publisherCode) {
		this.publisherCode = publisherCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getBrowsePrecent() {
		return browsePrecent;
	}

	public void setBrowsePrecent(String browsePrecent) {
		this.browsePrecent = browsePrecent;
	}

	public String getRelationCode() {
		return relationCode;
	}

	public void setRelationCode(String relationCode) {
		this.relationCode = relationCode;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}