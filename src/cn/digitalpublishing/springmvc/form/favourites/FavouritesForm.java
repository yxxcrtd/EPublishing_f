package cn.digitalpublishing.springmvc.form.favourites;

import java.util.HashMap;

import cn.digitalpublishing.springmvc.form.BaseForm;

public class FavouritesForm extends BaseForm {
	
	private String order ;
	private Integer ftype ;
	private HashMap<String,String> type = new HashMap<String,String>();
	/**
	 * 删除Id集合，以, 逗号隔开
	 */
	private String dels ;
	
	private String[] publicationsIds;
	public String[] getPublicationsIds() {
		return publicationsIds;
	}
	public void setPublicationsIds(String[] publicationsIds) {
		this.publicationsIds = publicationsIds;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getDels() {
		return dels;
	}
	public void setDels(String dels) {
		this.dels = dels;
	}
	public Integer getFtype() {
		return ftype;
	}
	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}
	public HashMap<String, String> getType() {
		return type;
	}
	public void setType(HashMap<String, String> type) {
		this.type = type;
	}


}
