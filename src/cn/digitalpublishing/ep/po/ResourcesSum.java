package cn.digitalpublishing.ep.po;

public class ResourcesSum {
	/**
	 * 资源总数ID
	 */
	private String id;
	/**
	 * 图书总数（中文）
	 */
	private Integer bookCount;
	/**
	 * 图书总数(英文)
	 */
	private Integer bookCountEn;
	/**
	 * 期刊总数（中文）
	 */
	private Integer cnJournal;
	/**
	 * 期刊总数（英文）
	 */
	private Integer enJournal;
	/**
	 * 期刊总数
	 */
	private Integer journalsCount;
	/**
	 * 图书总数
	 */
	private Integer articleCount;
	/**
	 * 文章总数
	 */
	private Integer sumCount;
	/**
	 * 总数 状态 1-有数据直接update,
	 */
	private Integer  type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getBookCount() {
		return bookCount;
	}
	public void setBookCount(Integer bookCount) {
		this.bookCount = bookCount;
	}
	public Integer getJournalsCount() {
		return journalsCount;
	}
	public void setJournalsCount(Integer journalsCount) {
		this.journalsCount = journalsCount;
	}
	public Integer getArticleCount() {
		return articleCount;
	}
	public void setArticleCount(Integer articleCount) {
		this.articleCount = articleCount;
	}
	public Integer getSumCount() {
		return sumCount;
	}
	public void setSumCount(Integer sumCount) {
		this.sumCount = sumCount;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getBookCountEn() {
		return bookCountEn;
	}
	public void setBookCountEn(Integer bookCountEn) {
		this.bookCountEn = bookCountEn;
	}
	public Integer getCnJournal() {
		return cnJournal;
	}
	public void setCnJournal(Integer cnJournal) {
		this.cnJournal = cnJournal;
	}
	public Integer getEnJournal() {
		return enJournal;
	}
	public void setEnJournal(Integer enJournal) {
		this.enJournal = enJournal;
	}
	
}
