package cn.digitalpublishing.springmvc.form.custom;

import java.util.List;

import cn.digitalpublishing.ep.po.CUserType;
import cn.digitalpublishing.ep.po.CUserTypeProp;
import cn.digitalpublishing.springmvc.form.BaseForm;

public class UserTypePropForm extends BaseForm {

	/**
	 * 用户类型
	 */
	private CUserType userType;
	private String[] orders;
	private String[] ids;
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	public String[] getOrders() {
		return orders;
	}
	public void setOrders(String[] orders) {
		this.orders = orders;
	}
	/**
	 * 类型属性编号
	 */
	private String pid;
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	private String code;
	private CUserTypeProp obj;
	public CUserTypeProp getObj() {
		return obj;
	}
	public void setObj(CUserTypeProp obj) {
		this.obj = obj;
	}
	private List<CUserTypeProp> list;
	public List<CUserTypeProp> getList() {
		return list;
	}
	public void setList(List<CUserTypeProp> list) {
		this.list = list;
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
}
