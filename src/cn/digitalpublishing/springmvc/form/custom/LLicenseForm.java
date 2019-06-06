package cn.digitalpublishing.springmvc.form.custom;


import java.util.Date;

import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.springmvc.form.BaseForm;

public class LLicenseForm extends BaseForm{
	
	private LLicense obj=new LLicense();	
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 出版物编号
	 */
	private String code;
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
	 * 查看范围 1-个人订阅 2-机构订阅 3-所有
	 */
	private Integer range;
	/**
	 * 出版物
	 */
	private String pubTitle;
	/**
	 * 用户
	 */
	private String userName;
	/**
	 * 是否是试用授权1-是，2-不是，null-不是
	 */
	private Integer isTrial;
	/**
	 * 试用时长1-1个月，2-2个月，3-3个月，4-半年
	 */
	private Integer trialPeriod;
	/**
	 * selauser
	 */
	private Object[] selauser;
	/**
	 * selapub
	 */
	private Object[] selapub;
	
	private Object[] selaLicense;
	
	private String who;
	
	private Integer searchType;//
	
	public Integer getSearchType() {
		return searchType;
	}
	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public LLicense getObj() {
		return obj;
	}
	public void setObj(LLicense obj) {
		this.obj = obj;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public Integer getRange() {
		return range;
	}
	public void setRange(Integer range) {
		this.range = range;
	}
	
	public String getPubTitle() {
		return pubTitle;
	}
	public void setPubTitle(String pubTitle) {
		this.pubTitle = pubTitle;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public Object[] getSelauser() {
		return selauser;
	}
	public void setSelauser(Object[] selauser) {
		this.selauser = selauser;
	}
	public Object[] getSelapub() {
		return selapub;
	}
	public void setSelapub(Object[] selapub) {
		this.selapub = selapub;
	}
	public Object[] getSelaLicense() {
		return selaLicense;
	}
	public void setSelaLicense(Object[] selaLicense) {
		this.selaLicense = selaLicense;
	}
	
}
