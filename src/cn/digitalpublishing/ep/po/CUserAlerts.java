package cn.digitalpublishing.ep.po;

import java.util.Date;

/**
 * EpubCUserProp entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class CUserAlerts implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 用户
	 */
	private CUser user;
	/**
	 * 分类项
	 */
	private BSubject subject;
	
	/**
	 * 订阅类型
	 * 1-出版物;2-主题学科
	 */
	private Integer type;
	/**
	 * 提醒频率
	 * 1-立即;2-每天;3-每周;4-每月
	 */
	private Integer frequency;
	/**
	 * 创建时间
	 */
	private Date createdon;
	
	private String treeCode;
	
	/**
	 * 发送状态 1-未发送 2-已发送
	 */
	private Integer sendStatus;
	
	public String getTreeCode() {
		return treeCode;
	}
	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public CUser getUser() {
		return user;
	}
	public void setUser(CUser user) {
		this.user = user;
	}
	public BSubject getSubject() {
		return subject;
	}
	public void setSubject(BSubject subject) {
		this.subject = subject;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public Date getCreatedon() {
		return createdon;
	}
	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}
	
	public Integer getSendStatus () {
	
		return sendStatus;
	}
	
	public void setSendStatus (Integer sendStatus) {
	
		this.sendStatus = sendStatus;
	}
}