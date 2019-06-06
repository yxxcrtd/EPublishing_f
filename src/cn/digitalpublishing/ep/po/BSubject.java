package cn.digitalpublishing.ep.po;

import java.util.HashSet;
import java.util.Set;

/**
 * EpubBSubject entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class BSubject implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 父分类法
	 */
	private BSubject subject;
	/**
	 * 编号  -唯一
	 */
	private String code;
	/**
	 * 中文名
	 */
	private String name;
	/**
	 * 英文名
	 */
	private String nameEn;
	/**
	 * 类型 1-主要分类法 2-其他分类法
	 */
	private Integer type;
	/**
	 * 属性结构编号 如果父分类法是001 则子分类法是001001、001002......
	 */
	private String treeCode;
	/**
	 * 排序
	 */
	private Integer order;
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 标准 1-标准 2-自定义
	 */
	private Integer standard;
	/**
	 * 子分类法
	 */
	private Set<BSubject> subjects = new HashSet<BSubject>();
	/**
	 * 分类法和出版物关系
	 */
	private Set<PCsRelation> csRelations = new HashSet<PCsRelation>();
	
	/**
	 * 用户订阅提醒
	 */
	private Set<CUserAlerts> alertss = new HashSet<CUserAlerts>();

	/**
	 * 用来记录分类下有多少产品
	 */
	private Integer countPP;
	// Constructors

	/** default constructor */
	public BSubject() {
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

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTreeCode() {
		return treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getStandard() {
		return standard;
	}

	public void setStandard(Integer standard) {
		this.standard = standard;
	}

	public Set<PCsRelation> getCsRelations() {
		return csRelations;
	}

	public void setCsRelations(Set<PCsRelation> csRelations) {
		this.csRelations = csRelations;
	}

	public Set<BSubject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<BSubject> subjects) {
		this.subjects = subjects;
	}

	public Integer getCountPP() {
		return countPP;
	}

	public void setCountPP(Integer countPP) {
		this.countPP = countPP;
	}

	public Set<CUserAlerts> getAlertss() {
		return alertss;
	}

	public void setAlertss(Set<CUserAlerts> alertss) {
		this.alertss = alertss;
	}
}