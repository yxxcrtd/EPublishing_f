package cn.digitalpublishing.ep.po;

import java.util.Date;

@SuppressWarnings("serial")
public class Recommend  implements java.io.Serializable {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 产品Id
	 */
	private PPublications publications;
	/**
	 * 推荐类型  2 期刊
	 */
	private String recommendType;
	/**
	 * 排序
	 */
	private String recommendOrder;
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PPublications getPublications() {
		return publications;
	}
	public void setPublications(PPublications publications) {
		this.publications = publications;
	}
	public String getRecommendType() {
		return recommendType;
	}
	public void setRecommendType(String recommendType) {
		this.recommendType = recommendType;
	}
	public String getRecommendOrder() {
		return recommendOrder;
	}
	public void setRecommendOrder(String recommendOrder) {
		this.recommendOrder = recommendOrder;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
}
