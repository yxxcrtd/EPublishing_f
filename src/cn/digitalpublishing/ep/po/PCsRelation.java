package cn.digitalpublishing.ep.po;

/**
 * EpubPCsRelation entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class PCsRelation implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 分类法
	 */
	private BSubject subject;
	/**
	 * 出版物
	 */
	private PPublications publications;
	/**
	 * 记录关联产品的状态
	 */
	private Integer status;
	/**
	 * 记录关联产品的类型
	 */
	private Integer type;

	// Constructors
	/**
	 * 声明个变量，sql查询的时候做缓存用，非表字段，如需调用 请赋值（刘冶）
	 */
	private String eachCount;
	
	/** default constructor */
	public PCsRelation() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BSubject getSubject() {
		return subject;
	}

	public void setSubject(BSubject subject) {
		this.subject = subject;
	}

	public PPublications getPublications() {
		return publications;
	}

	public void setPublications(PPublications publications) {
		this.publications = publications;
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

	public String getEachCount() {
		return eachCount;
	}

	public void setEachCount(String eachCount) {
		this.eachCount = eachCount;
	}
}