package cn.digitalpublishing.remote.service;

import java.util.HashMap;
import java.util.Map;

public class RestService {
	
	private Map<String,String> rest = new HashMap<String,String>();
	
	public RestService(Map<String,String> rest){
		this.rest = rest;
	}
	
	public Map<String,String> getServices(){
		return rest;
	}
	
	public String getService(String key){
		return rest.get(key);
	}

}
