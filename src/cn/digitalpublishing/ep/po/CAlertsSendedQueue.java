package cn.digitalpublishing.ep.po;

import java.util.Date;

/**
 * MailSendedQueue entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class CAlertsSendedQueue implements java.io.Serializable {

	// Fields

	private String id;
	private String userId;
	private String userName;
	private String subjectName;
	private String publicationsId;
	private String publicationsName;
	private Integer alertsType;
	private Integer alertsFrequency;
	private String isbn;
	private String email;
	private Date createOn;

	// Constructors

	/** default constructor */
	public CAlertsSendedQueue() {
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getPublicationsName() {
		return publicationsName;
	}

	public void setPublicationsName(String publicationsName) {
		this.publicationsName = publicationsName;
	}

	public Integer getAlertsType() {
		return alertsType;
	}

	public void setAlertsType(Integer alertsType) {
		this.alertsType = alertsType;
	}

	public Integer getAlertsFrequency() {
		return alertsFrequency;
	}

	public void setAlertsFrequency(Integer alertsFrequency) {
		this.alertsFrequency = alertsFrequency;
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

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getPublicationsId() {
		return publicationsId;
	}

	public void setPublicationsId(String publicationsId) {
		this.publicationsId = publicationsId;
	}
}