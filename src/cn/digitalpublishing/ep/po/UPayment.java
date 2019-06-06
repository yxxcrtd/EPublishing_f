package cn.digitalpublishing.ep.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UPayment implements Serializable {

	private String id;
	private CUserType userType;//用户类型
	private String payType;//支付方式 1-预存账户支付 2-线下支付 3-支付宝支付
	
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
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
}
