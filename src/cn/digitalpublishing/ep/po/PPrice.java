package cn.digitalpublishing.ep.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EpubPPrice entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class PPrice implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 价格类型
	 */
	private PPriceType priceType;
	/**
	 * 出版物
	 */
	private PPublications publications;
	/**
	 * 价格编号
	 */
	private String code;
	/**
	 * 价格名称
	 */
	private String name;
	/**
	 * 价格
	 */
	private Double price;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 1-永久授权 2-限时授权
	 */
	private Integer type;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 有效时间 单位月
	 */
	private Integer effective;
	
	private Set<OOrderDetail> orderDetails = new HashSet<OOrderDetail>();
	/**
	 * 授权信息
	 */
	private Set<LLicense> license = new HashSet<LLicense>();

	/**
	 * 状态 2-有效 1-无效
	 */
	private Integer status;
	/**
	 * 并发数
	 */
	private Integer complicating;
	/**
	 *1--固定价；2--协议价 
	 */
	private Integer category;
	
	public Set<LLicense> getLicense() {
		return license;
	}

	public void setLicense(Set<LLicense> license) {
		this.license = license;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	/** default constructor */
	public PPrice() {
	}

	public Integer getEffective() {
		return effective;
	}

	public void setEffective(Integer effective) {
		this.effective = effective;
	}

	public Integer getComplicating() {
		return complicating;
	}

	public void setComplicating(Integer complicating) {
		this.complicating = complicating;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PPriceType getPriceType() {
		return priceType;
	}

	public void setPriceType(PPriceType priceType) {
		this.priceType = priceType;
	}

	public PPublications getPublications() {
		return publications;
	}

	public void setPublications(PPublications publications) {
		this.publications = publications;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Set<OOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	
}