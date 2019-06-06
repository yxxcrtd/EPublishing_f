package cn.digitalpublishing.ep.po;

@SuppressWarnings("serial")
public class BConfiguration implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 编号 唯一
	 */
	private String code;
	/**
	 * 关键字
	 */
	private String key;
	/**
	 * 值
	 */
	private String val;
	/**
	 * 配置类型，1、基础参数配置信息（放置 基础币种  基础关税比率）；2、基础显示信息（放置 帮助、联系方式等页面静态显示的内容）
	 */
	private Integer type;
	/**
	 * 配置内容，比如 帮助的html代码等
	 */
	private String content;
	/**
	 * 主配置
	private BConfiguration config;
	*/
	/**
	 * 子配置
	private Set<BConfiguration> configs;
	*/

	// Constructors

	/** default constructor */
	public BConfiguration() {
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/** full constructor */
	public BConfiguration(String code, String key, String val) {
		this.code = code;
		this.key = key;
		this.val = val;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}
	// Property accessors
}