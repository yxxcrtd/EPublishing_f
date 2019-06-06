package cn.digitalpublishing.ep.po;

import java.util.Date;

/**
 * EpubCFavourites entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class CFavourites implements java.io.Serializable {

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
	 * 创建时间
	 */
	private Date createDate;

	// Constructors

	/** default constructor */
	public CFavourites() {
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	
}