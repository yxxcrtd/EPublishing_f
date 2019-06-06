package cn.digitalpublishing.ep.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EpubRRecommend entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class RRecommend implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 机构
	 */
	private BInstitution institution;
	/**
	 * 出版物
	 */
	private PPublications publications;
	/**
	 * 机构名称
	 */
	private String name;
	/**
	 * 是否已下单 1-No 2-Yes 3-cancel
	 */
	private Integer isOrder;
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
	 * 荐购细节
	 */
	private Set<RRecommendDetail> recommendDetails = new HashSet<RRecommendDetail>();
	/**
	 * 订单细节
	 */
	private Set<OOrderDetail> orderDetails = new HashSet<OOrderDetail>();

	private Integer recommendDetailCount;
	/**
	 * 最近一次被推荐的时间
	 */
	private Date lastDate;
	/**
	 * 是否购买中（从orderdetail中得到， =0表示不在购买中）
	 */
	private Integer isOrdering;
	/**
	 * 是否机构已购买完成（从licenseIp表中查询得到， =0表示无有效license）
	 */
	private Integer isOrdered;
	/**
	 * 关联的订单详情的状态
	 */
	private Integer orderStatus;
	/**
	 * 暂缓详情
	 */
	private String particulars;
	/**
	 * 专家推荐次数
	 */
	private Integer proCount;
	
	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	public Integer getRecommendDetailCount() {
		return recommendDetailCount;
	}

	public void setRecommendDetailCount(Integer recommendDetailCount) {
		this.recommendDetailCount = recommendDetailCount;
	}

	/** default constructor */
	public RRecommend() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BInstitution getInstitution() {
		return institution;
	}

	public void setInstitution(BInstitution institution) {
		this.institution = institution;
	}

	public PPublications getPublications() {
		return publications;
	}

	public void setPublications(PPublications publications) {
		this.publications = publications;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(Integer isOrder) {
		this.isOrder = isOrder;
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

	public Set<RRecommendDetail> getRecommendDetails() {
		return recommendDetails;
	}

	public void setRecommendDetails(Set<RRecommendDetail> recommendDetails) {
		this.recommendDetails = recommendDetails;
	}

	public Set<OOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Integer getIsOrdering() {
		return isOrdering;
	}

	public void setIsOrdering(Integer isOrdering) {
		this.isOrdering = isOrdering;
	}

	public Integer getIsOrdered() {
		return isOrdered;
	}

	public void setIsOrdered(Integer isOrdered) {
		this.isOrdered = isOrdered;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getProCount() {
		return proCount;
	}

	public void setProCount(Integer proCount) {
		this.proCount = proCount;
	}
	
}