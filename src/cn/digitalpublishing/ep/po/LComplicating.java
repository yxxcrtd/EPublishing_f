package cn.digitalpublishing.ep.po;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class LComplicating implements Serializable {

	private String id;
	private LLicense license;
	private CUser user;
	private String pubCode;//出版物编码
	private String macAddr;//MAC地址 ，改成记录Cookie信息
	private String sessionId;//session 的ID
	private Date createOn;//创建时间
	private Date updateTime;//更新时间
	private Date endTime;//结束时间
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LLicense getLicense() {
		return license;
	}
	public void setLicense(LLicense license) {
		this.license = license;
	}
	public CUser getUser() {
		return user;
	}
	public void setUser(CUser user) {
		this.user = user;
	}
	public String getPubCode() {
		return pubCode;
	}
	public void setPubCode(String pubCode) {
		this.pubCode = pubCode;
	}
	public String getMacAddr() {
		return macAddr;
	}
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}
	
}
