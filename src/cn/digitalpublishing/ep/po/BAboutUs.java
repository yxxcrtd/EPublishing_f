package cn.digitalpublishing.ep.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BAboutUs implements Serializable{

	private String id;
	
	private String content;
	
	private int type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
