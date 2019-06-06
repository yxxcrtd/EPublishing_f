package cn.digitalpublishing.ep.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * EpubOOrder entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class OOrder implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 订单编号
	 */
	private String code;
	/**
	 * 支付宝交易编号
	 */
	private String tradeNo;
	/**
	 * 用户
	 */
	private CUser user;
	/**
	 * 购买人姓名
	 */
	private String name;
	/**
	 * 购买IP
	 */
	private String ip;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 销售价格
	 */
	private Double salePrice;
	/**
	 * 税金
	 */
	private Double tax;
	/**
	 * 退税价格
	 */
	private Double salePriceExtTax;
	/**
	 * 状态 1-待处理 2-处理中 3-处理完成 4-购买中
	 */
	private Integer status;
	/**
	 * 数量
	 */
	private Integer quantity;
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
	 * 支付类型 1-预存 2-支票 3-alipay
	 */
	private Integer payType;
	/**
	 * 发送状态 1-未发送 2-已发送
	 */
	private Integer sendStatus;
	/**
	 * 订单细节
	 */
	private Set<OOrderDetail> orderDetails = new HashSet<OOrderDetail>();
	/**
	 * 交易
	 */
	private Set<OTransation> transations = new HashSet<OTransation>();

	private List<OOrderDetail> detailList = new ArrayList<OOrderDetail>();
	
	// Constructors

	/** default constructor */
	public OOrder() {
	}

	public List<OOrderDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<OOrderDetail> detailList) {
		this.detailList = detailList;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getSalePriceExtTax() {
		return salePriceExtTax;
	}

	public void setSalePriceExtTax(Double salePriceExtTax) {
		this.salePriceExtTax = salePriceExtTax;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public Set<OOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Set<OTransation> getTransations() {
		return transations;
	}

	public void setTransations(Set<OTransation> transations) {
		this.transations = transations;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	
}