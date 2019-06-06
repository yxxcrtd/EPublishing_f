package cn.digitalpublishing.ep.po;

import java.util.Set;

@SuppressWarnings("serial")
public class BPDAInfo implements java.io.Serializable {

	private String id;	
	/**
	 * 次数
	 */
	private Integer times;
	/**
	 * 操作方式 1-只加入购物车 2-直接生成订单
	 */
	private Integer operation;
	/**
	 * 付款方式 1-预付款 2-支票
	 */
	private Integer payment;
	/**
	 * 购买图书 1-选中 2-未选
	 */
	private Integer book;
	/**
	 * 购买期刊 1-选中 2-未选
	 */
	private Integer journal;
	/**
	 * 购买文章 1-选中 2-未选
	 */
	private Integer article;
	/**
	 * 类型 1-机构 2-个人
	 */
	private Integer type;
	/**
	 * 状态 1-有效 2-无效
	 */
	private Integer status;
	/**
	 * 机构信息
	 */
	private BInstitution institution;
	/**
	 * 身份信息
	 */
	private CUser user;
	
	private Set<BPDACounter> pdaCounters;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public Integer getOperation() {
		return operation;
	}
	public void setOperation(Integer operation) {
		this.operation = operation;
	}
	public Integer getPayment() {
		return payment;
	}
	public void setPayment(Integer payment) {
		this.payment = payment;
	}
	public Integer getBook() {
		return book;
	}
	public void setBook(Integer book) {
		this.book = book;
	}
	public Integer getJournal() {
		return journal;
	}
	public void setJournal(Integer journal) {
		this.journal = journal;
	}
	public Integer getArticle() {
		return article;
	}
	public void setArticle(Integer article) {
		this.article = article;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BInstitution getInstitution() {
		return institution;
	}
	public void setInstitution(BInstitution institution) {
		this.institution = institution;
	}
	public CUser getUser() {
		return user;
	}
	public void setUser(CUser user) {
		this.user = user;
	}
	public Set<BPDACounter> getPdaCounters() {
		return pdaCounters;
	}
	public void setPdaCounters(Set<BPDACounter> pdaCounters) {
		this.pdaCounters = pdaCounters;
	}
	
}