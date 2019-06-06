package cn.digitalpublishing.util.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 排序
 * @param <T>
 *
 */
public class SequenceUtil {

	/**
	 * 按照Key值升序排列
	 * @param oriMap
	 * @return
	 */
	public static Map<String,Object> MapAscToKey(Map<? extends String, ? extends Object> oriMap){
	        if (oriMap == null || oriMap.isEmpty()) {  
	            return null;  
	        }  
	        Map<String, Object> sortedMap = new TreeMap<String, Object>(new Comparator<String>() {  
	            public int compare(String key1, String key2) {  
	                int intKey1 = 0, intKey2 = 0;  
	                try {  
	                    intKey1 = getInt(key1);  
	                    intKey2 = getInt(key2);  
	                } catch (Exception e) {  
	                    intKey1 = 0;   
	                    intKey2 = 0;  
	                }  
	                return intKey1 - intKey2;  
	            }});  
	        sortedMap.putAll(oriMap);  
	        return sortedMap;  
	}
	public static Map<String,Object> MapDescToKey(Map<? extends String, ? extends Object> oriMap){
        if (oriMap == null || oriMap.isEmpty()) {  
            return null;  
        }  
        Map<String, Object> sortedMap = new TreeMap<String, Object>(new Comparator<String>() {  
            public int compare(String key1, String key2) {  
                int intKey1 = 0, intKey2 = 0;  
                try {  
                    intKey1 = getInt(key1);  
                    intKey2 = getInt(key2);  
                } catch (Exception e) {  
                    intKey1 = 0;   
                    intKey2 = 0;  
                }  
                return -(intKey1 - intKey2);  
            }});  
        sortedMap.putAll(oriMap);  
        return sortedMap;  
}  
	      
	private static int getInt(String str) {  
        int i = 0;  
        try {  
            Pattern p = Pattern.compile("^\\d+");  
            Matcher m = p.matcher(str);  
            if (m.find()) {  
                i = Integer.valueOf(m.group());  
            }  
        } catch (NumberFormatException e) {  
            e.printStackTrace();  
        }  
        return i;  
    }
	
	public static void main(String[] args) {
		Map<String, Object> ss = new HashMap<String,Object>();
		ss.put("2010", "2010");
		ss.put("2009", "2009");
		ss.put("2011", "2011");
		ss.put("2015", "2015");
		ss.put("2008", "2008");
		Map<String,Object> f2 = SequenceUtil.MapDescToKey(ss);
		List<Map.Entry<String,Object>> mappingList = new ArrayList<Map.Entry<String,Object>>(f2.entrySet());
		for(Map.Entry<String,Object> mapping:mappingList){
			 System.out.println(mapping.getKey()+"--->"+mapping.getValue());
		 } 
	}
	
}
