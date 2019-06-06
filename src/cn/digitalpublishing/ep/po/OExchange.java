package cn.digitalpublishing.ep.po;

import java.util.Date;

/**
 * EpubOExchange entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class OExchange implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 基准币种
	 */
	private String scurr;
	/**
	 * 转换币种
	 */
	private String xcurr;
	/**
	 * 转换比率
	 */
	private String rate;
	/**
	 * 创建时间
	 */
	private Date createddate;
	/**
	 * 更新时间
	 */
	private Date updateddate;

	// Constructors

	/** default constructor */
	public OExchange() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScurr() {
		return scurr;
	}

	public void setScurr(String scurr) {
		this.scurr = scurr;
	}

	public String getXcurr() {
		return xcurr;
	}

	public void setXcurr(String xcurr) {
		this.xcurr = xcurr;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Date getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Date updateddate) {
		this.updateddate = updateddate;
	}
}