package cn.digitalpublishing.ep.po;

import java.util.HashSet;
import java.util.Set;

/**
 * EpubCUserType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class CUserType implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 类型编号
	 */
	private String code;
	/**
	 * 类型名称
	 */
	private String name;
	/**
	 * 类型属性
	 */
	private Set<CUserTypeProp> userTypeProps = new HashSet<CUserTypeProp>();
	/**
	 * 用户
	 */
	private Set<CUser> users = new HashSet<CUser>();
	/**
	 * 价格类型关联表
	 */
	private Set<UPRelation> upRelation = new HashSet<UPRelation>();
	/**
	 * 用户类型与支付方式关系表
	 */
	private Set<UPayment> uPayments = new HashSet<UPayment>();
	
	/**
	 * 发送状态 1-未发送 2-已发送
	 */
	private Integer sendStatus;
	/**
	 * 用户与Url关系
	 */
	private Set<BUuRelation> uuRelations=new HashSet<BUuRelation>();
	// Constructors

	/** default constructor */
	public CUserType() {
	}

	public Set<UPRelation> getUpRelation() {
		return upRelation;
	}

	public void setUpRelation(Set<UPRelation> upRelation) {
		this.upRelation = upRelation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<CUserTypeProp> getUserTypeProps() {
		return userTypeProps;
	}

	public void setUserTypeProps(Set<CUserTypeProp> userTypeProps) {
		this.userTypeProps = userTypeProps;
	}

	public Set<CUser> getUsers() {
		return users;
	}

	public void setUsers(Set<CUser> users) {
		this.users = users;
	}

	
	public Integer getSendStatus () {
	
		return sendStatus;
	}

	
	public void setSendStatus (Integer sendStatus) {
	
		this.sendStatus = sendStatus;
	}

	public Set<UPayment> getuPayments() {
		return uPayments;
	}

	public void setuPayments(Set<UPayment> uPayments) {
		this.uPayments = uPayments;
	}

	public Set<UPayment> getUPayments() {
		return uPayments;
	}

	public void setUPayments(Set<UPayment> uPayments) {
		this.uPayments = uPayments;
	}

	public Set<BUuRelation> getUuRelations() {
		return uuRelations;
	}

	public void setUuRelations(Set<BUuRelation> uuRelations) {
		this.uuRelations = uuRelations;
	}

}