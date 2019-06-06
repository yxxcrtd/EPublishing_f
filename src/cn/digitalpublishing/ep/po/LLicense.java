package cn.digitalpublishing.ep.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EpubLLicense entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class LLicense implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 用户
	 */
	private CUser user;
	/**
	 * 出版物
	 */
	private PPublications publications;
	/**
	 * 出版物编号
	 */
	private String code;
	/**
	 * 创建人
	 */
	private String createdby;
	/**
	 * 创建时间
	 */
	private Date createdon;
	/**
	 * 1-有效 2-无效
	 */
	private Integer status;
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
	 * 阅读地址
	 */
	private String readUrl;
	/**
	 * 授权IP
	 */
	private Set<LLicenseIp> ips = new HashSet<LLicenseIp>();
	/**
	 * 接入用户名
	 */
	private String licenseUId;
	/**
	 * 接入用户密码
	 */
	private String licensePwd;
	/**
	 * 接入类型 1-IP接入 2-用户名密码接入
	 */
	private Integer accessType;
	/**
	 * 接入用户类型 1-个人用户 2-机构用户
	 */
	private Integer accessUIdType;
	/**
	 * 接入日至信息
	 */
	private Set<LAccess> access = new HashSet<LAccess>();
	/**
	 * 是否是试用授权1-是，2-不是，null-不是
	 */
	private Integer isTrial;
	/**
	 * 试用时长1-1个月，2-2个月，3-3个月，4-半年
	 */
	private Integer trialPeriod;
	/**
	 * 并发数
	 */
	private Integer complicating;
	private Double coefficient; // 系数
	private Double coefficient1; // 并发系数
	/**
	 * 产品包
	 */
	private PCollection collection;
	/**
	 * 价格信息 用来区别购买的是那条价格信息
	 */
	private PPrice pprice;
	// Constructors

	/** default constructor */
	public LLicense() {
	}

	public PPrice getPprice() {
		return pprice;
	}

	public void setPprice(PPrice pprice) {
		this.pprice = pprice;
	}

	public PCollection getCollection() {
		return collection;
	}

	public void setCollection(PCollection collection) {
		this.collection = collection;
	}

	public Integer getComplicating() {
		return complicating;
	}

	public void setComplicating(Integer complicating) {
		this.complicating = complicating;
	}

	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

	public Double getCoefficient1() {
		return coefficient1;
	}

	public void setCoefficient1(Double coefficient1) {
		this.coefficient1 = coefficient1;
	}

	public Set<LAccess> getAccess() {
		return access;
	}

	public void setAccess(Set<LAccess> access) {
		this.access = access;
	}

	public String getReadUrl() {
		return readUrl;
	}

	public void setReadUrl(String readUrl) {
		this.readUrl = readUrl;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Set<LLicenseIp> getIps() {
		return ips;
	}

	public void setIps(Set<LLicenseIp> ips) {
		this.ips = ips;
	}

	public String getLicenseUId() {
		return licenseUId;
	}

	public void setLicenseUId(String licenseUId) {
		this.licenseUId = licenseUId;
	}

	public String getLicensePwd() {
		return licensePwd;
	}

	public void setLicensePwd(String licensePwd) {
		this.licensePwd = licensePwd;
	}

	public Integer getAccessType() {
		return accessType;
	}

	public void setAccessType(Integer accessType) {
		this.accessType = accessType;
	}

	public Integer getAccessUIdType() {
		return accessUIdType;
	}

	public void setAccessUIdType(Integer accessUIdType) {
		this.accessUIdType = accessUIdType;
	}

	public Integer getIsTrial() {
		return isTrial;
	}

	public void setIsTrial(Integer isTrial) {
		this.isTrial = isTrial;
	}

	public Integer getTrialPeriod() {
		return trialPeriod;
	}

	public void setTrialPeriod(Integer trialPeriod) {
		this.trialPeriod = trialPeriod;
	}

}