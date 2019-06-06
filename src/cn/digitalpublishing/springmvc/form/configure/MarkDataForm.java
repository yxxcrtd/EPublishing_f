package cn.digitalpublishing.springmvc.form.configure;

import java.util.HashMap;
import java.util.Map;

import cn.digitalpublishing.ep.po.MMarkData;
import cn.digitalpublishing.springmvc.form.BaseForm;

public class MarkDataForm extends BaseForm {

	private MMarkData obj = new MMarkData();
	private Map<String,String> statusMap;//状态列表

	public Map<String, String> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(HashMap<String, String> hashMap) {
		this.statusMap = hashMap;
	}

	public MMarkData getObj() {
		return obj;
	}

	public void setObj(MMarkData obj) {
		this.obj = obj;
	}
	
}
