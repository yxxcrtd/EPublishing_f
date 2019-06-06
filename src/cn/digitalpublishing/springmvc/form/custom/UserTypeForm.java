package cn.digitalpublishing.springmvc.form.custom;

import cn.digitalpublishing.ep.po.CUserType;
import cn.digitalpublishing.springmvc.form.BaseForm;

public class UserTypeForm extends BaseForm {

	
	private String pid;
	private String code;
	private String name;
	private CUserType obj;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CUserType getObj() {
		return obj;
	}
	public void setObj(CUserType obj) {
		this.obj = obj;
	}
	
}
