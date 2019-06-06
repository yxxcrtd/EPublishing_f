package cn.digitalpublishing.ep.po;

/**
 * EpubLLicenseIp entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class LLicenseIp implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 授权
	 */
	private LLicense license;
	/**
	 * 开始IP
	 */
	private String startIp;
	/**
	 * 结束IP
	 */
	private String endIp;
	/**
	 * 长整型开始IP
	 */
	private Long sip;
	/**
	 * 长整型结束IP
	 */
	private Long eip;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LLicense getLicense() {
		return license;
	}
	public void setLicense(LLicense license) {
		this.license = license;
	}
	public String getStartIp() {
		return startIp;
	}
	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}
	public String getEndIp() {
		return endIp;
	}
	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}
	public Long getSip() {
		return sip;
	}
	public void setSip(Long sip) {
		this.sip = sip;
	}
	public Long getEip() {
		return eip;
	}
	public void setEip(Long eip) {
		this.eip = eip;
	}
	
	}