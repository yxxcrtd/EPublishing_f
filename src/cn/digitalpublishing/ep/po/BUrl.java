package cn.digitalpublishing.ep.po;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@SuppressWarnings("serial")
public class BUrl implements Serializable {

	/**
	 * 主键Id
	 */
	private String id;
	/**
	 * 地址
	 */
	private String url;
	
	private Integer isaccess;

	private Set<BUuRelation> uuRelations=new HashSet<BUuRelation>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Set<BUuRelation> getUuRelations() {
		return uuRelations;
	}
	public void setUuRelations(Set<BUuRelation> uuRelations) {
		this.uuRelations = uuRelations;
	}
	public Integer getIsaccess() {
		return isaccess;
	}
	public void setIsaccess(Integer isaccess) {
		this.isaccess = isaccess;
	}
	
	
}
