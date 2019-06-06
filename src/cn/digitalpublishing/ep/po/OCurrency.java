package cn.digitalpublishing.ep.po;

/**
 * EpubOCurrency entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class OCurrency implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 货币编号
	 */
	private String code;
	/**
	 * 描述
	 */
	private String desc;

	// Constructors

	/** default constructor */
	public OCurrency() {
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}