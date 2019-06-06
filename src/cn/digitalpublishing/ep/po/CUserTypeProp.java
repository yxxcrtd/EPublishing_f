package cn.digitalpublishing.ep.po;

import java.util.HashSet;
import java.util.Set;

/**
 * EpubCUserTypeProp entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class CUserTypeProp implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 用户类型
	 */
	private CUserType userType;
	/**
	 * 类型属性编号
	 */
	private String code;
	/**
	 * 类型属性名称
	 */
	private String key;
	/**
	 * 属性 1-有效 2-无效
	 */
	private Integer status;
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
	 * 用户属性
	 */
	private Set<CUserProp> userProps = new HashSet<CUserProp>();

	/**
	 * 发送状态 1-未发送 2-已发送
	 */
	private Integer sendStatus;
	
	// Constructors

	/** default constructor */
	public CUserTypeProp() {
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set<CUserProp> getUserProps() {
		return userProps;
	}

	public void setUserProps(Set<CUserProp> userProps) {
		this.userProps = userProps;
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