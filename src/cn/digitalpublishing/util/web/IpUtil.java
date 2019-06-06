package cn.digitalpublishing.util.web;

import javax.servlet.http.HttpServletRequest;

import cn.com.daxtech.framework.model.Param;

public class IpUtil {
	
	boolean i;
	
	private static final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";  
	
	private static final String REGX_IPB = REGX_IP + "\\-" + REGX_IP;  
	
	public static boolean ipIsValid(String ipSection, String ip) {  
	    if (ipSection == null)  
	        throw new NullPointerException("IP段不能为空！");  
	    if (ip == null)  
	        throw new NullPointerException("IP不能为空！");  
	    ipSection = ipSection.trim();  
	    ip = ip.trim();
	    if (!ipSection.matches(REGX_IPB) || !ip.matches(REGX_IP))  
	        return false;  
	    int idx = ipSection.indexOf('-');  
	    String[] sips = ipSection.substring(0, idx).split("\\.");  
	    String[] sipe = ipSection.substring(idx + 1).split("\\.");  
	    String[] sipt = ip.split("\\.");  
	    long ips = 0L, ipe = 0L, ipt = 0L;  
	    for (int i = 0; i < 4; ++i) {  
	         ips = ips << 8 | Integer.parseInt(sips[i]);  
	         ipe = ipe << 8 | Integer.parseInt(sipe[i]);  
	         ipt = ipt << 8 | Integer.parseInt(sipt[i]);  
	    }
	    if (ips > ipe) {  
	        long t = ips;  
	        ips = ipe;  
	        ipe = t;  
	     }  
	    return ips <= ipt && ipt <= ipe;  
	}
	
	public static Long getLongIp(String ip){
		Long lip = null;
		if (ip == null)  
			return null;
		if (!ip.matches(REGX_IP))  
			return null;
		lip = 0L;
		String[] sipt = ip.split("\\.");  
		for (int i = 0; i < 4; ++i) {
		     lip = lip << 8 | Integer.parseInt(sipt[i]);  
		}
		return lip;
	}
	
	public static void main(String[] args) {  
	    if (ipIsValid("192.168.1.1-192.168.1.10", "192.168.1.1")) {  
	         System.out.println("ip属于该网段");  
	     } else  
	         System.out.println("ip不属于该网段");  
	    
	    System.out.println(getLongIp("123.112.171.91"));
	 }
	
	public static String getIp(HttpServletRequest req){
		String ip="127.0.0.1";
		boolean isProxy = Boolean.valueOf(Param.getParam("system.config")!=null&&Param.getParam("system.config").containsKey("proxy")?Param.getParam("system.config").get("proxy").toString():"false");
		//登陆机构默认用户
	    ip = isProxy?req.getHeader("X-Real-IP"):req.getRemoteAddr();
	    if(ip==null||"null".equals(ip)){
	    	ip=req.getRemoteAddr();
	    }
		return ip;
	}


}
