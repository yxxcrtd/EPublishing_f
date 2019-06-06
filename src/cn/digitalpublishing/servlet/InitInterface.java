package  cn.digitalpublishing.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import cn.digitalpublishing.config.ProcessQueue;

@SuppressWarnings("serial")
public class InitInterface extends HttpServlet {
	
	public void init()throws ServletException{
		try{
			String interfaceService=getInitParameter("interfaceService");
			if(interfaceService!=null&&!"".equals(interfaceService)){
				ProcessQueue.interfaceService = interfaceService;
			}
			ProcessQueue.webRoot = this.getServletConfig().getServletContext().getRealPath("");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
