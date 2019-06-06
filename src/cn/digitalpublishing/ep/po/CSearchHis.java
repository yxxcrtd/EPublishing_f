package cn.digitalpublishing.ep.po;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class CSearchHis implements Serializable {

	private String id;
	private String keyword;//搜索关键字
	private Integer type;//1-临时的 2-永久的
	private Date createOn;//创建时间
	private CUser user;//用户
	private CDirectory directory;//搜索文件夹
	private Integer keyType;//搜索范围 0-全文、1-标题、2-作者、3-ISBN/ISSN、4-出版商
	public Integer getKeyType() {
		return keyType;
	}
	public void setKeyType(Integer keyType) {
		this.keyType = keyType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
	public CUser getUser() {
		return user;
	}
	public void setUser(CUser user) {
		this.user = user;
	}
	public CDirectory getDirectory() {
		return directory;
	}
	public void setDirectory(CDirectory directory) {
		this.directory = directory;
	}
	
}
