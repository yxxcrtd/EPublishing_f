package cn.digitalpublishing.ep.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BService implements Serializable{

	private String id;
	
	private String title;
	
	private String content;
	
	private Integer type;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
