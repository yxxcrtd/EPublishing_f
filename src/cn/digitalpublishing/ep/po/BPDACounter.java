package cn.digitalpublishing.ep.po;

import java.util.Date;
import java.util.Set;

@SuppressWarnings("serial")
public class BPDACounter implements java.io.Serializable {

	private String id;
	/**
	 * PDA设置信息
	 */
	private BPDAInfo pdaInfo;
	/**
	 * 出版物
	 */
	private PPublications publications;
	/**
	 * 开始时间(第一次计数时间)
	 */
	private Date startDate;
	/**
	 * 结束时间(最后一次计数时间)
	 */
	private Date endDate;		
	/**
	 * 状态 1-有效 2-无效
	 */
	private Integer status;
	/**
	 * 计数值
	 */
	private Integer value;
	/**
	 * 订单详情
	 */
	private Set<OOrderDetail> orderDetails;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BPDAInfo getPdaInfo() {
		return pdaInfo;
	}

	public void setPdaInfo(BPDAInfo pdaInfo) {
		this.pdaInfo = pdaInfo;
	}

	public PPublications getPublications() {
		return publications;
	}

	public void setPublications(PPublications publications) {
		this.publications = publications;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Set<OOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
}