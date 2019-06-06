package cn.digitalpublishing.ep.po;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class CDirectory implements java.io.Serializable{

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 文件夹名称
	 */
	private String name;
	/**
	 * 用户
	 */
	private CUser user;
	/**
	 *搜索历史 
	 */
	private Set<CSearchHis> searchHises = new HashSet<CSearchHis>();
	
	//自定义字段
	private Integer hisCount;//收藏夹下搜索记录
	
	public Integer getHisCount() {
		return hisCount;
	}
	public void setHisCount(Integer hisCount) {
		this.hisCount = hisCount;
	}
	public Set<CSearchHis> getSearchHises() {
		return searchHises;
	}
	public void setSearchHises(Set<CSearchHis> searchHises) {
		this.searchHises = searchHises;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CUser getUser() {
		return user;
	}
	public void setUser(CUser user) {
		this.user = user;
	}
	
}
