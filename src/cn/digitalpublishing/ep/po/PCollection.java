package cn.digitalpublishing.ep.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EpubPCollection entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class PCollection implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 产品包编号
	 */
	private String code;
	/**
	 * 产品包名称
	 */
	private String name;
	/**
	 * 产品包描述
	 */
	private String desc;
	/**
	 * 产品包价格
	 */
	private String price;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 是否放在首页 1-否 2-是
	 */
	private Integer homepage;
	/**
	 * 创建时间
	 */
	private Date createOn;
	/**
	 * 封面
	 */
	private String cover;
	/**
	 * 1-未上架 2-已上架
	 */
	private Integer status;
	/**
	 * 产品包和产品关系
	 */
	private Set<PCcRelation> ccRelations = new HashSet<PCcRelation>();
	/**
	 * 产品包和订单关系
	 */
	private Set<OOrderDetail> orderDetails = new HashSet<OOrderDetail>();
	/**
	 * LLicense
	 */
	private Set<LLicense> license = new HashSet<LLicense>();

	// Constructors
	/**
	 * 图书数量
	 */
	private String bookNumber;
	/**
	 * 中文书数量
	 */
	private String chineseNumber;
	/**
	 * 英文书数量
	 */
	private String forginNumber;
	/**
	 * 期刊数量
	 */
	private String journalNumber;

	/**
	 * 期刊期数量
	 */
	private String type7Number;

	public String getType7Number() {
		return type7Number;
	}

	public void setType7Number(String type7Number) {
		this.type7Number = type7Number;
	}

	public String getChineseNumber() {
		return chineseNumber;
	}

	public void setChineseNumber(String chineseNumber) {
		this.chineseNumber = chineseNumber;
	}

	public String getForginNumber() {
		return forginNumber;
	}

	public void setForginNumber(String forginNumber) {
		this.forginNumber = forginNumber;
	}

	public String getJournalNumber() {
		return journalNumber;
	}

	public void setJournalNumber(String journalNumber) {
		this.journalNumber = journalNumber;
	}

	public Set<LLicense> getLicense() {
		return license;
	}

	public void setLicense(Set<LLicense> license) {
		this.license = license;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBookNumber() {
		return bookNumber;
	}

	public void setBookNumber(String bookNumber) {
		this.bookNumber = bookNumber;
	}

	/** default constructor */
	public PCollection() {
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getHomepage() {
		return homepage;
	}

	public void setHomepage(Integer homepage) {
		this.homepage = homepage;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Set<PCcRelation> getCcRelations() {
		return ccRelations;
	}

	public void setCcRelations(Set<PCcRelation> ccRelations) {
		this.ccRelations = ccRelations;
	}

	public Set<OOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

}