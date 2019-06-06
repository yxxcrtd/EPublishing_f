package cn.digitalpublishing.springmvc.form.configure;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.digitalpublishing.ep.po.BConfiguration;
import cn.digitalpublishing.springmvc.form.BaseForm;

public class BConfigurationForm extends BaseForm {

	private BConfiguration obj = new BConfiguration();

	private String content;
	private String configCode;
	
	private Integer yyyy;//状态是1的时候进行新增操作
	
	private CommonsMultipartFile file = null;
	private String format = "jpg|gif|png|bmp";
	private String mid;
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public BConfiguration getObj() {
		return obj;
	}
	public void setObj(BConfiguration obj) {
		this.obj = obj;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getConfigCode() {
		return configCode;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	public Integer getYyyy() {
		return yyyy;
	}
	public void setYyyy(Integer yyyy) {
		this.yyyy = yyyy;
	}
	public CommonsMultipartFile getFile() {
		return file;
	}
	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
}
