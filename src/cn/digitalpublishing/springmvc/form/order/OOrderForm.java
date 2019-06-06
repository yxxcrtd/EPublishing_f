package cn.digitalpublishing.springmvc.form.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.OOrder;
import cn.digitalpublishing.ep.po.OOrderDetail;
import cn.digitalpublishing.ep.po.UPayment;
import cn.digitalpublishing.springmvc.form.BaseForm;

public class OOrderForm extends BaseForm {

	private OOrder obj;
	private OOrderDetail detail;
	/**
	 *订单状态 1-待处理 2-处理中 3-处理完成 
	 */
	private String status;
	/**
	 * 多条状态，如：1,2,3
	 */
	private String allStatus;

	/**
	 * 订单明细中父ID
	 */
	private String parentId;
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String alipay;
	private List<UPayment> payments;
	//付款方式
	private String paytype;

	private String balance;

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	//存储国家名称
	private HashMap<String, String> countryMap = new HashMap<String, String>();
	//存储省份名称
	private HashMap<String, String> provinceMap = new HashMap<String, String>();
	private Map<String, String> values = new HashMap<String, String>();

	public List<UPayment> getPayments() {
		return payments;
	}

	public void setPayments(List<UPayment> payments) {
		this.payments = payments;
	}

	public OOrder getObj() {
		return obj;
	}

	public void setObj(OOrder obj) {
		this.obj = obj;
	}

	public OOrderDetail getDetail() {
		return detail;
	}

	public void setDetail(OOrderDetail detail) {
		this.detail = detail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAllStatus() {
		return allStatus;
	}

	public void setAllStatus(String allStatus) {
		this.allStatus = allStatus;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public HashMap<String, String> getCountryMap() {
		return countryMap;
	}

	public void setCountryMap(HashMap<String, String> countryMap) {
		this.countryMap = countryMap;
	}

	public HashMap<String, String> getProvinceMap() {
		return provinceMap;
	}

	public void setProvinceMap(HashMap<String, String> provinceMap) {
		this.provinceMap = provinceMap;
	}

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

}
