package cn.digitalpublishing.springmvc.form.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.BUrl;
import cn.digitalpublishing.ep.po.BUuRelation;
import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.springmvc.form.BaseForm;

public class UAccessForm extends BaseForm{
	
	private String userRols;
	private String urlid_01;

	private String[] urlIds;
	
	private BUrl burl =new BUrl();
	 private BUuRelation obj=new BUuRelation();
	
	

	public BUuRelation getObj() {
		return obj;
	}

	public void setObj(BUuRelation obj) {
		this.obj = obj;
	}

	public String getUrlid_01() {
		return urlid_01;
	}

	public void setUrlid_01(String urlid_01) {
		this.urlid_01 = urlid_01;
	}

	public BUrl getBurl() {
		return burl;
	}

	public void setBurl(BUrl burl) {
		this.burl = burl;
	}

	public String getUserRols() {
		return userRols;
	}

	public void setUserRols(String userRols) {
		this.userRols = userRols;
	}

	public String[] getUrlIds() {
		return urlIds;
	}

	public void setUrlIds(String[] urlIds) {
		this.urlIds = urlIds;
	}
	
	
}
