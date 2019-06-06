package cn.digitalpublishing.ep.po;

import java.util.Set;

/**
 * EpubPPage entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class PPage implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 出版物
	 */
	private PPublications publications;
	/**
	 * pdf路径
	 */
	private String pdf;
	/**
	 * swf路径
	 */
	private String swf;
	/**
	 * 页号
	 */
	private Integer number;
	/**
	 * 水印
	 */
	private String mark;
	/**
	 * 笔记
	 */
	private Set<PNote> notes ;
	/**
	 * 标签
	 */
	private Set<PRecord> records;

	// Constructors
	//自定义字段
	private String fullText;//全文

	/** default constructor */
	public PPage() {
	}

	public String getFullText() {
		return fullText;
	}

	public void setFullText(String fullText) {
		this.fullText = fullText;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PPublications getPublications() {
		return publications;
	}

	public void setPublications(PPublications publications) {
		this.publications = publications;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public String getSwf() {
		return swf;
	}

	public void setSwf(String swf) {
		this.swf = swf;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Set<PNote> getNotes() {
		return notes;
	}

	public void setNotes(Set<PNote> notes) {
		this.notes = notes;
	}

	public Set<PRecord> getRecords() {
		return records;
	}

	public void setRecords(Set<PRecord> records) {
		this.records = records;
	}

	
}