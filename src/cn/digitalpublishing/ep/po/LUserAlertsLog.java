package cn.digitalpublishing.ep.po;

import java.util.Date;

/**
 * 用来存储提醒续费、最新章节上线
 * @author lifh
 *
 */
@SuppressWarnings("serial")
public class LUserAlertsLog implements java.io.Serializable {

	private String id;
	private String userName;//用户名
	private String publicationsName;//出版物名称
	private String isbn;
	private String email;//用户邮箱
	private Date createdon;
	private String alertDate;//提醒日期 提前一个月
	private Integer alertStatus;//提醒状态 1、未提醒 2、已提醒 3、提醒失败
	private Integer alertType;//提醒类型  1、续费提醒 2、最新章节、文章
	private String alertContent;//提醒内容
	private CUser user;
	private PPublications publications;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPublicationsName() {
		return publicationsName;
	}
	public void setPublicationsName(String publicationsName) {
		this.publicationsName = publicationsName;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreatedon() {
		return createdon;
	}
	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}
	public String getAlertDate() {
		return alertDate;
	}
	public void setAlertDate(String alertDate) {
		this.alertDate = alertDate;
	}
	public Integer getAlertStatus() {
		return alertStatus;
	}
	public void setAlertStatus(Integer alertStatus) {
		this.alertStatus = alertStatus;
	}
	public Integer getAlertType() {
		return alertType;
	}
	public void setAlertType(Integer alertType) {
		this.alertType = alertType;
	}
	public String getAlertContent() {
		return alertContent;
	}
	public void setAlertContent(String alertContent) {
		this.alertContent = alertContent;
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
