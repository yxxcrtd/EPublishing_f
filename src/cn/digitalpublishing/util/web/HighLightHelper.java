package cn.digitalpublishing.util.web;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

public class HighLightHelper {
	
	private String prefix;
	
	private String suffix;
	
	public HighLightHelper(String prefix,String suffix){
		this.prefix = prefix;
		this.suffix = suffix;
	}
	
	public String getHighLightText(String[] keywords,String text){
		String highLightText = "";
		if(keywords==null){
			return text;
		}
		Vector<Properties> sortResult = sort(assemble(keywords),"desc");
		if(sortResult!=null&&!sortResult.isEmpty()){
			highLightText = text;
			for(Properties p:sortResult){
				highLightText = highLightText.replace(p.getProperty("NAME"),"#"+p.getProperty("ID")+"#");
			}
			for(Properties p:sortResult){
				highLightText = highLightText.replace("#"+p.getProperty("ID")+"#", prefix+p.getProperty("NAME")+suffix);
			}
		}
		return highLightText;
	}
	
	private Map<String,String> assemble(String[] keywords){
		Map<String,String> map = new HashMap<String,String>();
		for(int i = 0;i<keywords.length ; i++){
			if(!"".equals(keywords[i].trim()))
				map.put(keywords[i].trim(),String.valueOf(i));
		}
		return map;
	}
	
	private Vector<Properties> sort(Map<String,String> map,String sort){
		Vector<Properties> v=new Vector<Properties>();
		if(map!=null){
			Properties p;
			String key="";
			String value="";
			for(Iterator<String> e=map.keySet().iterator();e.hasNext();){
				p=new Properties();
				key=e.next();
				value=map.get(key).toString();
				p.setProperty("ID", value);
				p.setProperty("NAME",key);
				v.add(p);
			}
		}
		if(sort.equalsIgnoreCase("asc")){//从低高往高排序
			Collections.sort(v,new Comparator<Properties>(){
				public int compare(Properties o1, Properties o2) {
				       if(Integer.parseInt(o1.getProperty("ID").toString())>Integer.parseInt(o2.getProperty("ID").toString()))
				            return 1;
				       if(Integer.parseInt(o1.getProperty("ID").toString())==Integer.parseInt(o2.getProperty("ID").toString()))
				           return 0;
				       else
				            return -1;
				    }
			});
		}
		if(sort.equalsIgnoreCase("desc")){//从低高往高排序
			Collections.sort(v,new Comparator<Properties>(){
				public int compare(Properties o1, Properties o2) {
				       if(Integer.parseInt(o1.getProperty("ID").toString())<Integer.parseInt(o2.getProperty("ID").toString()))
				            return 1;
				       if(Integer.parseInt(o1.getProperty("ID").toString())==Integer.parseInt(o2.getProperty("ID").toString()))
				           return 0;
				       else
				            return -1;
				    }
			});
		}
		return v;
	}

}
