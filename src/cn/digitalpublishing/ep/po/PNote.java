package cn.digitalpublishing.ep.po;

import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
public class PNote implements Serializable {

	/**
	 * 主键Id
	 */
	private String id;
	/**
	 * 笔记内容
	 */
	private String noteContent;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * page_id
	 */
	private PPage pages ;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 源文件ID
	 */
	private PPublications publications;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public PPage getPages() {
		return pages;
	}
	public void setPages(PPage pages) {
		this.pages = pages;
	}
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
	public PPublications getPublications() {
		return publications;
	}
	public void setPublications(PPublications publications) {
		this.publications = publications;
	}
}
