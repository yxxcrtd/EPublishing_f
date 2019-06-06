package cn.digitalpublishing.ep.po;

import java.util.Date;

/**
 * EpubCUserProp entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class CUserProp implements java.io.Serializable {

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
	 * 用户类型属性
	 */
	private CUserTypeProp userTypeProp;
	/**
	 * 编号
	 */
	private String code;
	/**
	 * 关键字
	 */
	private String key;
	/**
	 * 取值
	 */
	private String val;
	/**
	 * 排序
	 */
	private Integer order;
	/**
	 * 显示方式
	 */
	private String display;
	/**
	 * 来源类型 1-参数文件 2-实体
	 */
	private Integer stype;
	/**
	 * 来源值
	 */
	private String svalue;
	/**
	 * 是否必填
	 */
	private Integer must;
	/**
	 * 创建人
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
	 * 发送状态 1-未发送 2-已发送
	 */
	private Integer sendStatus;

	// Constructors

	/** default constructor */
	public CUserProp() {
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

	public CUserTypeProp getUserTypeProp() {
		return userTypeProp;
	}

	public void setUserTypeProp(CUserTypeProp userTypeProp) {
		this.userTypeProp = userTypeProp;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public Integer getStype() {
		return stype;
	}

	public void setStype(Integer stype) {
		this.stype = stype;
	}

	public String getSvalue() {
		return svalue;
	}

	public void setSvalue(String svalue) {
		this.svalue = svalue;
	}

	public Integer getMust() {
		return must;
	}

	public void setMust(Integer must) {
		this.must = must;
	}

	
	public Integer getSendStatus () {
	
		return sendStatus;
	}

	
	public void setSendStatus (Integer sendStatus) {
	
		this.sendStatus = sendStatus;
	}

	
}