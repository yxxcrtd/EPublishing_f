package cn.digitalpublishing.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;


@SuppressWarnings("serial")
public class InitAccessResource extends HttpServlet {
	
	public void init()throws ServletException{
		try{
			ServiceFactory serviceFactory;
			serviceFactory=(ServiceFactory)new ServiceFactoryImpl();
			if(serviceFactory.getCUserTypeService()!=null)
				serviceFactory.getCUserTypeService().loadAccessResources("");
			else
				System.out.println("XXXXXX           Not init     XXXXXXXXXXXXXX");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
