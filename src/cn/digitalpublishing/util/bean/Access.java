package cn.digitalpublishing.util.bean;

import java.util.HashMap;
import java.util.Map;

public class Access {
	
	private static Map<String,Map<String,Boolean>> resource = null;
	
	private static Object obj=new Object();
	
	private Access(){}
	
	public static Map<String,Map<String,Boolean>> getResource(){
		if(resource==null){
			synchronized(obj){
				if(resource==null){
					resource = new HashMap<String,Map<String,Boolean>>();
				}
			}
		}
		return resource;
	}
	
	public Boolean permit(String type,String url){
		Boolean exist = true;
		if(getResource()!=null&&getResource().containsKey(type)&&getResource().get(type).containsKey(url)){
			exist = getResource().get(type).get(url);
		}
		return exist;
	}

}
