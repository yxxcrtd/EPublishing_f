package cn.digitalpublishing.ep.po;

import java.io.Serializable;
import java.util.Date;

import cn.digitalpublishing.springmvc.form.BaseForm;

@SuppressWarnings("serial")
public class PContentRelation implements Serializable{
	private String id;
	private String mark;
	private PPublications separateCon;// 选中的期刊
	private PPublications issueCon;// 填写的期刊，多个issn
	private String occurTime;//发生的时间
	private String commentCon;//备注
	private Date createDate;//创建时间
	private Date updateDate;//修改时间

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	public PPublications getSeparateCon() {
		return separateCon;
	}
	public void setSeparateCon(PPublications separateCon) {
		this.separateCon = separateCon;
	}
	public PPublications getIssueCon() {
		return issueCon;
	}
	public void setIssueCon(PPublications issueCon) {
		this.issueCon = issueCon;
	}
	public String getOccurTime() {
		return occurTime;
	}
	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}
	
	public String getCommentCon() {
		return commentCon;
	}
	public void setCommentCon(String commentCon) {
		this.commentCon = commentCon;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
}
