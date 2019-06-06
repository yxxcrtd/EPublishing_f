package cn.digitalpublishing.ep.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UPRelation implements Serializable {

	private String id;
	private CUserType userType;//用户类型
	private PPriceType priceType;//价格类型信息
	public CUserType getUserType() {
		return userType;
	}
	public void setUserType(CUserType userType) {
		this.userType = userType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PPriceType getPriceType() {
		return priceType;
	}
	public void setPriceType(PPriceType priceType) {
		this.priceType = priceType;
	}
	
	
}
