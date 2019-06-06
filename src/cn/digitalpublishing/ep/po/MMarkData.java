package cn.digitalpublishing.ep.po;

import java.util.Date;

public class MMarkData {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 文件名
	 */
	private String name;
	/**
	 * 文件路径
	 */
	private String path;
	/**
	 * 创建日期
	 */
	private String createDate;
	/**
	 * 创建日期
	 */
	private Date createOn;
	/**
	 * 创建用户
	 */
	private String createBy;
	/**
	 * 1-有效 2-失效
	 */
	private Integer status;
	/**
	 * 机构
	 */
	private BInstitution institution;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Date getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BInstitution getInstitution() {
		return institution;
	}
	public void setInstitution(BInstitution institution) {
		this.institution = institution;
	} 

}
