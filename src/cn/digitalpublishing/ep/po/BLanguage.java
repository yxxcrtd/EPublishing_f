package cn.digitalpublishing.ep.po;

/**
 * EpubBLanguage entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class BLanguage implements java.io.Serializable {

	// Fields
    /**
     * 主键
     */
	private String id;
	/**
	 * 编号
	 */
	private String code;
	/**
	 * 属性
	 */
	private String propName;
	/**
	 * 属性值
	 */
	private String propValue;
	/**
	 * 对象名
	 */
	private String objName;

	// Constructors

	/** default constructor */
	public BLanguage() {
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

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getPropValue() {
		return propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	
}