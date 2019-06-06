package cn.digitalpublishing.ep.po;

import java.util.HashSet;
import java.util.Set;

/**
 * EpubBInstitution entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class BInstitution implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 编号  唯一的
	 */
	private String code;
	/**
	 * 机构名称
	 */
	private String name;
	/**
	 * 机构名称
	 */
	private String nameen;
	/**
	 * 机构LOGO
	 */
	private String logo;
	/**
	 * LOGO链接
	 */
	private String logoUrl;
	/**
	 * LOGO标致
	 */
	private String logoNote;
	/**
	 * 荐购
	 */
	private Set<RRecommend> recommends = new HashSet<RRecommend>();
	/**
	 * IP范围
	 */
	private Set<BIpRange> ipRanges = new HashSet<BIpRange>();
	/**
	 * 用户
	 */
	private Set<CUser> users = new HashSet<CUser>();
	/**
	 * Mark数据
	 */
	private Set<MMarkData> markdatas;
	/**
	 * pda设置信息
	 */
	private Set<BPDAInfo> pdaInfos;
	/**
	 * 机构状态
	 */
	private Integer status;

	// Constructors

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/** default constructor */
	public BInstitution() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Set<RRecommend> getRecommends() {
		return recommends;
	}

	public void setRecommends(Set<RRecommend> recommends) {
		this.recommends = recommends;
	}

	public Set<BIpRange> getIpRanges() {
		return ipRanges;
	}

	public void setIpRanges(Set<BIpRange> ipRanges) {
		this.ipRanges = ipRanges;
	}

	public Set<CUser> getUsers() {
		return users;
	}

	public void setUsers(Set<CUser> users) {
		this.users = users;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getLogoNote() {
		return logoNote;
	}

	public void setLogoNote(String logoNote) {
		this.logoNote = logoNote;
	}

	public Set<MMarkData> getMarkdatas() {
		return markdatas;
	}

	public void setMarkdatas(Set<MMarkData> markdatas) {
		this.markdatas = markdatas;
	}

	public Set<BPDAInfo> getPdaInfos() {
		return pdaInfos;
	}

	public void setPdaInfos(Set<BPDAInfo> pdaInfos) {
		this.pdaInfos = pdaInfos;
	}

	public String getNameen() {
		return nameen;
	}

	public void setNameen(String nameen) {
		this.nameen = nameen;
	}

	

}