package cn.digitalpublishing.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;


public class PDAInterceptor extends ActionInterceptor {
	
	@Override  
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object o, Exception e)throws Exception {  
		
		
		System.out.println("Page拦截器结束。。。。");  
		// TODO Auto-generated method stub  
	}  

	@Override  
	public void postHandle(HttpServletRequest request, HttpServletResponse response,Object o, ModelAndView mv) throws Exception {  
		System.out.println("PDA拦截器工作。。。。");   
//		mv.addObject("intep",s);  
	}  
	
	@Override  
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object o) throws Exception {  
		// TODO Auto-generated method stub  
		System.out.println("PDA拦截器开启。。。。");    
		return true;  
	}  
}
