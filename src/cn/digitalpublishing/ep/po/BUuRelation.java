package cn.digitalpublishing.ep.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BUuRelation implements Serializable {

	/**
	 * 主键Id
	 */
	private String id;
	/**
	 * 用户类型
	 */
	private CUserType userType;
	/**
	 * 地址
	 */
	private BUrl url;
	/**
	 * 是否允许访问
	 * 1：不允许  2：允许
	 */
	private Integer access;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public CUserType getUserType() {
		return userType;
	}
	public void setUserType(CUserType userType) {
		this.userType = userType;
	}
	public BUrl getUrl() {
		return url;
	}
	public void setUrl(BUrl url) {
		this.url = url;
	}
	public Integer getAccess() {
		return access;
	}
	public void setAccess(Integer access) {
		this.access = access;
	}
	
	
}
