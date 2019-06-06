package cn.digitalpublishing.ep.po;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class BToken implements Serializable {
	/**
	 * 找回密码
	 */
	public static final int TYPE_FINDPWD=1;
	/**
	 * 有效
	 */
	public static final int STATUS_OPEN=1;
	/**
	 * 无效
	 */
	public static final int STATUS_CLOSE=2;
	
	private String id;
	/**
	 * token类型 1：找回密码
	 */
	private int type;
	/**
	 * 状态：是否有效
	 */
	private int status;
	/**
	 * 创建时间
	 */
	private Date createOn;
	/**
	 * 关联的用户id
	 */
	private String userId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
