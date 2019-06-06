package cn.digitalpublishing.ep.po;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class OTransation implements Serializable {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 用户
	 */
	private CUser user;
	/**
	 * 订单
	 */
	private OOrder order;
	/**
	 * 订单明细
	 */
	private OOrderDetail orderDetail; 
	/**
	 * 订单编号
	 */
	private String orderCode;
	/**
	 * 金额
	 */
	private Double amount;
	/**
	 * 类型 1-存款 2-预存消费 3-支付宝消费 4-支票消费 5-预存冻结 
	 */
	private Integer type;
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
	
	public OOrderDetail getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(OOrderDetail orderDetail) {
		this.orderDetail = orderDetail;
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
	public OOrder getOrder() {
		return order;
	}
	public void setOrder(OOrder order) {
		this.order = order;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getType() {
		return type;
	}
	/**
	 * 类型 1-存款 2-预存消费 3-支付宝消费 4-支票消费 5-预存冻结
	 */
	public void setType(Integer type) {
		this.type = type;
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

}
