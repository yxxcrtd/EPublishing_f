package cn.digitalpublishing.springmvc.form.configure;
import cn.digitalpublishing.ep.po.BService;
import cn.digitalpublishing.springmvc.form.BaseForm;

/**
 * 服务
 * 
 * @author LiuYe
 * 
 */
public class BServiceForm extends BaseForm {

	private BService obj = new BService();
	private String id;
	private String title;
	private String content;
	public BService getObj() {
		return obj;
	}
	public void setObj(BService obj) {
		this.obj = obj;
	}
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
	
}
