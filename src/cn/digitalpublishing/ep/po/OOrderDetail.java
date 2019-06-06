package cn.digitalpublishing.ep.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EpubOOrderDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class OOrderDetail implements java.io.Serializable {
	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 用户信息
	 */
	private CUser user;
	/**
	 * 价格信息
	 */
	private PPrice price;
	/**
	 * 订单
	 */
	private OOrder order;
	/**
	 * 消费记录
	 */
	private Set<OTransation> transation = new HashSet<OTransation>();;
	/**
	 * 父细节信息
	 */
	private OOrderDetail detail;
	/**
	 * 产品序号 1、2、3、4
	 */
	private Integer odetailNum;
	/**
	 * 创建人
	 */
	private String createdby;
	/**
	 * 创建时间
	 */
	private Date createdon;
	/**
	 * 修改人
	 */
	private String updatedby;
	/**
	 * 修改时间
	 */
	private Date updatedon;
	/**
	 * 商品名
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
	 * 数量
	 */
	private Integer quantity;
	/**
	 * 类型 1-电子书 2-期刊 3-章节 4-文章 5-数据库 6-期刊卷 7 期刊期  99-产品包
	 */
	private Integer itemType;
	/**
	 * 产品编号
	 */
	private String productCode;
	/**
	 * 状态 1-未处理 2-已付款未开通 3-已付款已开通 4-处理中 10-未付款已开通  99-已取消
	 */
	private Integer status;
	/**
	 * 发送状态 1-未发送 2-已发送
	 */
	private Integer sendStatus;
	/**
	 * 产品包
	 */
	private PCollection collection;
	/**
	 * 推荐
	 */
	private RRecommend recommend;
	/**
	 * 子订单细节
	 */
	private Set<OOrderDetail> detials = new HashSet<OOrderDetail>();

	// Constructors
	/**
	 * 结算价
	 */
	private Double settledPrice;
	/**
	 * PDA信息
	 */
	private BPDACounter pdaCounter;
	
	/** default constructor */
	public OOrderDetail() {
	}

	public Set<OTransation> getTransation() {
		return transation;
	}

	public void setTransation(Set<OTransation> transation) {
		this.transation = transation;
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

	public PPrice getPrice() {
		return price;
	}

	public void setPrice(PPrice price) {
		this.price = price;
	}

	public OOrder getOrder() {
		return order;
	}

	public void setOrder(OOrder order) {
		this.order = order;
	}

	public Integer getOdetailNum() {
		return odetailNum;
	}

	public void setOdetailNum(Integer odetailNum) {
		this.odetailNum = odetailNum;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getStatus() {
		return status;
	}
	/**
	 * 状态 1-未处理 2-已付款未开通 3-已付款已开通 4-处理中 99-已取消
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public OOrderDetail getDetail() {
		return detail;
	}

	public void setDetail(OOrderDetail detail) {
		this.detail = detail;
	}

	public Set<OOrderDetail> getDetials() {
		return detials;
	}

	public void setDetials(Set<OOrderDetail> detials) {
		this.detials = detials;
	}

	public PCollection getCollection() {
		return collection;
	}

	public void setCollection(PCollection collection) {
		this.collection = collection;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public RRecommend getRecommend() {
		return recommend;
	}

	public void setRecommend(RRecommend recommend) {
		this.recommend = recommend;
	}

	public Double getSettledPrice() {
		return settledPrice;
	}

	public void setSettledPrice(Double settledPrice) {
		this.settledPrice = settledPrice;
	}

	public BPDACounter getPdaCounter() {
		return pdaCounter;
	}

	public void setPdaCounter(BPDACounter pdaCounter) {
		this.pdaCounter = pdaCounter;
	}
	
}