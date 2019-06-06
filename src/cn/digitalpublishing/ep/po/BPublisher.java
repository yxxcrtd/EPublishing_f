package cn.digitalpublishing.ep.po;

import java.util.HashSet;
import java.util.Set;

/**
 * EpubBPublisher entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class BPublisher implements java.io.Serializable {
	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 编号  唯一
	 */
	private String code;
	/**
	 * 中文名称
	 */
	// to be indexed (Publisher)
	private String name;
	/**
	 * 英文名
	 */
	private String nameEn;
	/**
	 * 首字母
	 */
	private String letter;
	/**
	 * 排序
	 */
	private Integer order;
	/**
	 * 浏览百分比
	 */
	private String browsePrecent;
	/**
	 * 下载百分比
	 */
	private String downloadPercent;
	/**
	 * 打印百分比
	 */
	private String printPercent;
	/**
	 * 关系编号  如果父出版商是001 则子出版商是001001、001002......
	 */
	private String relationCode;
	/**
	 * Logo
	 */
	private String logo;
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 出版物
	 */
	private Set<PPublications> publicationses = new HashSet<PPublications>();
	
	/**
	 * 期刊拷贝百分比
	 */
	private String journalBrowse;
	/**
	 * 期刊打印百分比
	 */
	private String journalPrint;
	/**
	 * 期刊下载百分比
	 */
	private String  journalDownload;

	// Constructors

	//自定义字段
	/**
	 * 查询 出版商 在产品包中数量
	 */
	private Integer collectionPubContent = 0;
	
	/** default constructor */
	public BPublisher() {
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

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getBrowsePrecent() {
		return browsePrecent;
	}

	public void setBrowsePrecent(String browsePrecent) {
		this.browsePrecent = browsePrecent;
	}

	public String getRelationCode() {
		return relationCode;
	}

	public void setRelationCode(String relationCode) {
		this.relationCode = relationCode;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Set<PPublications> getPublicationses() {
		return publicationses;
	}

	public void setPublicationses(Set<PPublications> publicationses) {
		this.publicationses = publicationses;
	}

	public Integer getCollectionPubContent() {
		return collectionPubContent;
	}

	public void setCollectionPubContent(Integer collectionPubContent) {
		this.collectionPubContent = collectionPubContent;
	}

	public String getDownloadPercent() {
		return downloadPercent;
	}

	public void setDownloadPercent(String downloadPercent) {
		this.downloadPercent = downloadPercent;
	}

	public String getPrintPercent() {
		return printPercent;
	}

	public void setPrintPercent(String printPercent) {
		this.printPercent = printPercent;
	}

	public String getJournalBrowse() {
		return journalBrowse;
	}

	public void setJournalBrowse(String journalBrowse) {
		this.journalBrowse = journalBrowse;
	}

	public String getJournalPrint() {
		return journalPrint;
	}

	public void setJournalPrint(String journalPrint) {
		this.journalPrint = journalPrint;
	}

	public String getJournalDownload() {
		return journalDownload;
	}

	public void setJournalDownload(String journalDownload) {
		this.journalDownload = journalDownload;
	}
	
	

}