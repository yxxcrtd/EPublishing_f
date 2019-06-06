package cn.digitalpublishing.ep.po;

import java.io.Serializable;
import java.sql.Clob;
import java.util.Date;

/**
 * 新闻
 * @author LiuYe
 *
 */
public class PNews implements Serializable{
/**
 新闻ID         主键
新闻标题       字符型（128）
新闻来源       字符型（64）
新闻外部连接   字符型（128）
新闻内容       文本型
新闻发布时间   日期（系统当前时间）
 */
	/**
	 * 新闻ID
	 */
	private String id;
	/**
	 * 新闻标题（128）
	 */
	private String title;
	/**
	 * 新闻来源（64）
	 */
	private String source;
	/**
	 * 新闻外部链接（128）
	 */
	private String outUrl;
	/**
	 * 新闻内容（文本型）
	 */
	private String content;
	/**
	 * 新闻作者
	 */
	private String author;
	/**
	 * 新闻发布时间（系统当前时间）
	 */
	private Date createDate;
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getOutUrl() {
		return outUrl;
	}
	public void setOutUrl(String outUrl) {
		this.outUrl = outUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
}
