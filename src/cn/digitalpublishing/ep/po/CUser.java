package cn.digitalpublishing.ep.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EpubCUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class CUser implements java.io.Serializable {

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 用户类型
	 */
	private CUserType userType;

	/**
	 * 机构
	 */
	private BInstitution institution;
	/**
	 * 用户姓名
	 */
	private String name;
	/**
	 * 用户状态 1-已激活 2-未激活 用户激活后才能将状态变成已激活
	 */
	private Integer status;
	/**
	 * 1-普通用户（用户受IP范围限制） 2-特殊用户（用户不受IP范围限制） 3-默认用户 level=2机构管理员level=5专家用户
	 */
	private Integer level;
	/**
	 * 创建于
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
	 * 发送状态 1-未发送 2-已发送
	 */
	private Integer sendStatus;
	/**
	 * 收藏夹
	 */
	private Set<CFavourites> favouriteses = new HashSet<CFavourites>();
	/**
	 * 订单
	 */
	private Set<OOrder> orders = new HashSet<OOrder>();
	/**
	 * 订单细节
	 */
	private Set<OOrderDetail> orderDetails = new HashSet<OOrderDetail>();
	/**
	 * 荐购
	 */
	private Set<RRecommendDetail> recommendDetails = new HashSet<RRecommendDetail>();
	/**
	 * 用户属性
	 */
	private Set<CUserProp> userProps = new HashSet<CUserProp>();
	/**
	 * 用户授权
	 */
	private Set<LLicense> licenses = new HashSet<LLicense>();
	/**
	 * 账户信息
	 */
	private Set<CAccount> accounts = new HashSet<CAccount>();
	/**
	 * 用户订阅提醒
	 */
	private Set<CUserAlerts> alertss = new HashSet<CUserAlerts>();
	/**
	 * 交易
	 */
	private Set<OTransation> transations = new HashSet<OTransation>();
	/**
	 * 搜索文件夹
	 * */
	private Set<CDirectory> directorys = new HashSet<CDirectory>();
	/**
	 * 搜索历史
	 */
	private Set<CSearchHis> searchHises = new HashSet<CSearchHis>();
	/**
	 * 订阅提醒、用于对已定产品进行更新提醒、续费提醒等
	 */
	private Set<LUserAlertsLog> alertsLog = new HashSet<LUserAlertsLog>();

	private String email;

	private Set<BPDAInfo> pdaInfos;

	/**
	 * 偏好图书
	 */
	private Integer bookPartial;
	/**
	 * 偏好期刊
	 */
	private Integer journalPartial;

	// Constructors
	/**
	 * 院系
	 */
	private String department;
	/**
	 * 职称
	 */
	private String title;
	/**
	 * 电话
	 */
	private String telephone;
	/** default constructor */
	public CUser() {
	}

	public Set<LUserAlertsLog> getAlertsLog() {
		return alertsLog;
	}

	public void setAlertsLog(Set<LUserAlertsLog> alertsLog) {
		this.alertsLog = alertsLog;
	}

	public Set<CSearchHis> getSearchHises() {
		return searchHises;
	}

	public void setSearchHises(Set<CSearchHis> searchHises) {
		this.searchHises = searchHises;
	}

	public Set<CDirectory> getDirectorys() {
		return directorys;
	}

	public void setDirectorys(Set<CDirectory> directorys) {
		this.directorys = directorys;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CUserType getUserType() {
		return userType;
	}

	public void setUserType(CUserType userType) {
		this.userType = userType;
	}

	public BInstitution getInstitution() {
		return institution;
	}

	public void setInstitution(BInstitution institution) {
		this.institution = institution;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Set<CFavourites> getFavouriteses() {
		return favouriteses;
	}

	public void setFavouriteses(Set<CFavourites> favouriteses) {
		this.favouriteses = favouriteses;
	}

	public Set<OOrder> getOrders() {
		return orders;
	}

	public void setOrders(Set<OOrder> orders) {
		this.orders = orders;
	}

	public Set<OOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Set<CUserProp> getUserProps() {
		return userProps;
	}

	public void setUserProps(Set<CUserProp> userProps) {
		this.userProps = userProps;
	}

	public Set<LLicense> getLicenses() {
		return licenses;
	}

	public void setLicenses(Set<LLicense> licenses) {
		this.licenses = licenses;
	}

	public Set<RRecommendDetail> getRecommendDetails() {
		return recommendDetails;
	}

	public void setRecommendDetails(Set<RRecommendDetail> recommendDetails) {
		this.recommendDetails = recommendDetails;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Set<CAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<CAccount> accounts) {
		this.accounts = accounts;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<CUserAlerts> getAlertss() {
		return alertss;
	}

	public void setAlertss(Set<CUserAlerts> alertss) {
		this.alertss = alertss;
	}

	public Set<OTransation> getTransations() {
		return transations;
	}

	public void setTransations(Set<OTransation> transations) {
		this.transations = transations;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Set<BPDAInfo> getPdaInfos() {
		return pdaInfos;
	}

	public void setPdaInfos(Set<BPDAInfo> pdaInfos) {
		this.pdaInfos = pdaInfos;
	}

	public Integer getBookPartial() {
		return bookPartial;
	}

	public void setBookPartial(Integer bookPartial) {
		this.bookPartial = bookPartial;
	}

	public Integer getJournalPartial() {
		return journalPartial;
	}

	public void setJournalPartial(Integer journalPartial) {
		this.journalPartial = journalPartial;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}