package cn.digitalpublishing.ep.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告
 * 
 * @author LiuYe
 * 
 */
@SuppressWarnings("serial")
public class OAdvertisement implements Serializable {

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 广告类型
	 * 1-图片广告（默认选择）text 2-代码类的广告 kindeditor 3-文字形式的广告 kindeditor
	 */
	private int category;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 地址
	 */
	private String url;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 投放位置
	 * 1-首页顶部（可滚动）2-首页底部（单图，不可滚动）3-二级中文页面 4-二级英文页面 5-二级期刊页面【大于1就动】
	 */
	private int position;
	/**
	 * 状态
	 */
	private int status;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 排序
	 */
	private int orderBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 存储路径
	 */
	private String file;
	/**
	 * 点击量
	 */
	private int view;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

}
