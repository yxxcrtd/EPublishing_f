package cn.digitalpublishing.springmvc.form;

import java.util.List;

import org.apache.batik.ext.awt.image.codec.PNGDecodeParam;

import cn.digitalpublishing.ep.po.PNote;
import cn.digitalpublishing.ep.po.PPage;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.ep.po.PRecord;


public class ViewForm extends BaseForm {

	private PPage page = new PPage();
	private PRecord record = new PRecord();
	private PNote notes = new PNote();
	
	
	private String publicationsTitle;//产品名称
	private Integer type;
	private String dir;//swf文件路径
	private String value;
	
	private String licenseId;//订阅ID
	private String institutionId;//机构ID
	private Integer readCount=-1;//可以拷贝页数
	private Integer downloadCount=-1;//可以下载页数
	private Integer printCount=-1;//可以打印页数
	private String articleId;//文章ID
	
	private String isCopy;//拷贝
	private String isDownload;//是否可下载
	private String isPrint;//是否可打印
	
	private Integer nextPage;
	private String sourceId;
	private String curPageId;
	private String noteContent;//笔记内容
	private String pubId;
	private String pageNum;
	private List<PNote> noteList;
	private String tag;
	private String webUrl;
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public List<PNote> getNoteList() {
		return noteList;
	}
	public void setNoteList(List<PNote> noteList) {
		this.noteList = noteList;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getPubId() {
		return pubId;
	}
	public void setPubId(String pubId) {
		this.pubId = pubId;
	}
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
	public String getCurPageId() {
		return curPageId;
	}
	public void setCurPageId(String curPageId) {
		this.curPageId = curPageId;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getIsCopy() {
		return isCopy;
	}
	public void setIsCopy(String isCopy) {
		this.isCopy = isCopy;
	}
	public Integer getNextPage() {
		return nextPage;
	}
	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}
	public PPage getPage() {
		return page;
	}
	public void setPage(PPage page) {
		this.page = page;
	}
	public PRecord getRecord() {
		return record;
	}
	public void setRecord(PRecord record) {
		this.record = record;
	}
	public PNote getNotes() {
		return notes;
	}
	public void setNotes(PNote notes) {
		this.notes = notes;
	}
	public String getPublicationsTitle() {
		return publicationsTitle;
	}
	public void setPublicationsTitle(String publicationsTitle) {
		this.publicationsTitle = publicationsTitle;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLicenseId() {
		return licenseId;
	}
	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}
	public String getInstitutionId() {
		return institutionId;
	}
	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}
	public Integer getReadCount() {
		return readCount;
	}
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public Integer getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}
	public Integer getPrintCount() {
		return printCount;
	}
	public void setPrintCount(Integer printCount) {
		this.printCount = printCount;
	}
	public String getIsDownload() {
		return isDownload;
	}
	public void setIsDownload(String isDownload) {
		this.isDownload = isDownload;
	}
	public String getIsPrint() {
		return isPrint;
	}
	public void setIsPrint(String isPrint) {
		this.isPrint = isPrint;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
}
