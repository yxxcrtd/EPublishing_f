package cn.digitalpublishing.ep.po;

import java.io.Serializable;

/**
 * 相关出版社
 * @author LiuYe
 *
 */
public class PRelatedPublisher implements Serializable{
	
	/**
	 * ID
	 */
	private String id;
	/**
	 * 名称
	 */
	private String title;
	/**
	 * LOGO
	 */
	private String picPath;
	/**
	 * 超链接
	 */
	private String url;
	/**
	 * 发布位置
	 */
	private int position;
	/**
	 * 排序
	 */
	private int orderMark;
	
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
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getOrderMark() {
		return orderMark;
	}
	public void setOrderMark(int orderMark) {
		this.orderMark = orderMark;
	}
	
}
