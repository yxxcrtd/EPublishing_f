package cn.digitalpublishing.springmvc.form.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.OOrderDetail;
import cn.digitalpublishing.ep.po.UPayment;
import cn.digitalpublishing.springmvc.form.BaseForm;

public class OOrderDetailForm extends BaseForm {
	/**
	 *订单状态 1-待处理 2-处理中 3-处理完成 
	 */
	private OOrderDetail obj=new OOrderDetail();
	
	private String id;
	
	private String status;	
	
	private String priceId;
	
	private String pubId;
	
	private String ip;
	
	private String parentId;
	
	private String collectionId;
	
	private Integer quantity;
	
	private Integer isFreeTax=2;
	
	private Double totalPrice=0d;
	
	private String detailIds;
	
	private String subtype;
	/**
	 *商品种类 1-普通出版物，2-产品包
	 */
	private Integer kind=1;
	
	//用于存储类型属性的值
	private String[] propsValue;
	//存储国家名称
	private HashMap<String, String> countryMap = new HashMap<String, String>();
	//存储省份名称
		private HashMap<String, String> provinceMap = new HashMap<String, String>();
	//用于存储类型属性Id集合（在用户注册页面将全部类型属性存储到用户属性中）
	private String[]typePropIds;
	
	private Map<String,String> values = new HashMap<String,String>();
	//付款方式
	private String paytype;
	
	//荐购信息ID
	private String recId;
	
	private List<UPayment> payments;
	//支付宝信息MAP
	public static Map<String,String> alipayMap = new HashMap<String,String>();
	//余额
	private double balance=0d;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public OOrderDetail getObj() {
		return obj;
	}
	public void setObj(OOrderDetail obj) {
		this.obj = obj;
	}
	public String getPriceId() {
		return priceId;
	}
	public void setPriceId(String priceId) {
		this.getCondition().put("priceId", priceId);
		this.priceId = priceId;
	}
	public String getPubId() {
		return pubId;
	}
	public void setPubId(String pubId) {
		this.getCondition().put("publicationsid", pubId);
		this.pubId = pubId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(String collectionId) {
		this.getCondition().put("collectionId", collectionId);
		this.collectionId = collectionId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getIsFreeTax() {
		return isFreeTax;
	}
	public void setIsFreeTax(Integer isFreeTax) {
		this.isFreeTax = isFreeTax;
	}
	/**
	 * 	商品种类 1-普通出版物，2-产品包
	 * @return
	 */
	public Integer getKind() {
		return kind;
	}
	public void setKind(Integer kind) {
		this.kind = kind;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public String[] getPropsValue() {
		return propsValue;
	}
	public void setPropsValue(String[] propsValue) {
		this.propsValue = propsValue;
	}
	public HashMap<String, String> getCountryMap() {
		return countryMap;
	}
	public void setCountryMap(HashMap<String, String> countryMap) {
		this.countryMap = countryMap;
	}
	public Map<String, String> getValues() {
		return values;
	}
	public void setValues(Map<String, String> values) {
		this.values = values;
	}
	public String[] getTypePropIds() {
		return typePropIds;
	}
	public void setTypePropIds(String[] typePropIds) {
		this.typePropIds = typePropIds;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getRecId() {
		return recId;
	}
	public void setRecId(String recId) {
		this.recId = recId;
	}
	public List<UPayment> getPayments() {
		return payments;
	}
	public void setPayments(List<UPayment> payments) {
		this.payments = payments;
	}
	public static Map<String, String> getAlipayMap() {
		return alipayMap;
	}
	public static void setAlipayMap(Map<String, String> alipayMap) {
		OOrderDetailForm.alipayMap = alipayMap;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public HashMap<String, String> getProvinceMap() {
		return provinceMap;
	}
	public void setProvinceMap(HashMap<String, String> provinceMap) {
		this.provinceMap = provinceMap;
	}
	public String getDetailIds() {
		return detailIds;
	}
	public void setDetailIds(String detailIds) {
		this.detailIds = detailIds;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	
}
