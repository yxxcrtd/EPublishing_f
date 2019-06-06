package cn.digitalpublishing.ep.po;

/**
 * EpubPCcRelation entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class PCcRelation implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 产品集合
	 */
	private PCollection collection;
	/**
	 * 出版物
	 */
	private PPublications publications;
	/**
	 * 并发数
	 */
	private Integer complicating;
	/**
	 * 有效时间 单位月
	 */
	private Integer effective;
	/**
	 * 1-永久授权 2-限时授权
	 */
	private Integer type;
	/**
	 * 声明个变量，sql查询的时候做缓存用，非表字段，如需调用 请赋值（刘冶）
	 */
	private String eachCount;
	// Constructors

	/** default constructor */
	public PCcRelation() {
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getComplicating() {
		return complicating;
	}

	public void setComplicating(Integer complicating) {
		this.complicating = complicating;
	}

	public Integer getEffective() {
		return effective;
	}

	public void setEffective(Integer effective) {
		this.effective = effective;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PCollection getCollection() {
		return collection;
	}

	public void setCollection(PCollection collection) {
		this.collection = collection;
	}

	public PPublications getPublications() {
		return publications;
	}

	public void setPublications(PPublications publications) {
		this.publications = publications;
	}

	public String getEachCount() {
		return eachCount;
	}

	public void setEachCount(String eachCount) {
		this.eachCount = eachCount;
	}
}