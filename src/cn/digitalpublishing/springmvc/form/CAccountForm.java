package cn.digitalpublishing.springmvc.form;

import java.util.Date;

import cn.digitalpublishing.ep.po.CUser;

public class CAccountForm extends BaseForm {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 用户
	 */
	private CUser user;
	/**
	 * 登录用户名
	 */
	private String uid;
	/**
	 * 登录密码
	 */
	private String pwd;
	/**
	 * 用户状态 1-已激活 2-未激活
	 * 用户激活后才能将状态变成已激活
	 */
	private Integer status;
	/**
	 * 创建于
	 */
	private String createdby;
	/**
	 * 创建时间
	 */
	private Date createdon;
	/**
	 * 更新人
	 */
	private String updatedby;
	/**
	 * 更新时间
	 */
	private Date updatedon;
	/**
	 * 是否选中了记住帐号checked-选中
	 */
	private String rmb;
	private String beforPath;

	public String getBeforPath() {
		return beforPath;
	}

	public void setBeforPath(String beforPath) {
		this.getCondition().put("beforPath", beforPath.trim());
		this.beforPath = beforPath;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.getCondition().put("uid", uid.trim());
		this.uid = uid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.getCondition().put("pwd", pwd);
		this.pwd = pwd;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

	public String getRmb() {
		return rmb;
	}

	public void setRmb(String rmb) {
		this.getCondition().put("rmb", rmb);
		this.rmb = rmb;
	}

}