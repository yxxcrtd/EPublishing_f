package cn.digitalpublishing.redis.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Book implements Serializable {

	private String id;

	private String title;

	// 书的访问量 或 购买次数
	private long count;

	private String author;

	private String date;

	private String publisher;

	private int type;
	private String oa;
	private String free;
	private String startVolume;
	private String endVolume;
	private int license;

	private String parentId;
	private String parentTitle;
	private String year;
	private String volumeCode;
	private String issueCode;
	private String startPage;
	private String endPage;
	private int coverFlag;

	public Book(String id, String title, String author, String publisher, int coverFlag) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.coverFlag = coverFlag;
	}

	public Book(String id, String title, String parentId, String parentTitle, String year, String volumeCode,
			String issueCode, String startPage, String endPage, String publisher) {
		super();
		this.id = id;
		this.title = title;
		this.parentId = parentId;
		this.parentTitle = parentTitle;
		this.year = year;
		this.volumeCode = volumeCode;
		this.issueCode = issueCode;
		this.startPage = startPage;
		this.endPage = endPage;
		this.publisher = publisher;

	}

	public Book(String id, String title, String publisher, String startVolume, String endVolume, int license, String oa,
			String free) {
		super();
		this.id = id;
		this.title = title;
		this.publisher = publisher;
		this.startVolume = startVolume;
		this.endVolume = endVolume;
		this.license = license;
		this.oa = oa;
		this.free = free;
	}

	public Book(String id, String title, String oa, String free, String publisher, String startVolume,
			String endVolume) {
		super();
		this.id = id;
		this.oa = oa;
		this.free = free;
		this.title = title;
		this.publisher = publisher;
		this.startVolume = startVolume;
		this.endVolume = endVolume;
	}

	public Book() {

	}

	public Book(String id, String title, long count) {
		super();
		this.id = id;
		this.title = title;
		this.count = count;
	}

	public Book(String id, String title, String author, String date, String publisher) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.date = date;
		this.publisher = publisher;
	}

	public Book(String id, String title, String author, String date, String publisher, int type) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.date = date;
		this.publisher = publisher;
		this.type = type;
	}

	public Book(String id, String title, String author) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
	}

	public Book(String count, String id, String title, String author, String date, String publisher) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.date = date;
		this.publisher = publisher;
	}

	@Override
	public String toString() {
		return id + "_|_" + title + "_|_" + count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOa() {
		return oa;
	}

	public void setOa(String oa) {
		this.oa = oa;
	}

	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
	}

	public String getStartVolume() {
		return startVolume;
	}

	public void setStartVolume(String startVolume) {
		this.startVolume = startVolume;
	}

	public String getEndVolume() {
		return endVolume;
	}

	public void setEndVolume(String endVolume) {
		this.endVolume = endVolume;
	}

	public int getLicense() {
		return license;
	}

	public void setLicense(int license) {
		this.license = license;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentTitle() {
		return parentTitle;
	}

	public void setParentTitle(String parentTitle) {
		this.parentTitle = parentTitle;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getVolumeCode() {
		return volumeCode;
	}

	public void setVolumeCode(String volumeCode) {
		this.volumeCode = volumeCode;
	}

	public String getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(String issueCode) {
		this.issueCode = issueCode;
	}

	public String getStartPage() {
		return startPage;
	}

	public void setStartPage(String startPage) {
		this.startPage = startPage;
	}

	public String getEndPage() {
		return endPage;
	}

	public void setEndPage(String endPage) {
		this.endPage = endPage;
	}

	public int getCoverFlag() {
		return coverFlag;
	}

	public void setCoverFlag(int coverFlag) {
		this.coverFlag = coverFlag;
	}

}
