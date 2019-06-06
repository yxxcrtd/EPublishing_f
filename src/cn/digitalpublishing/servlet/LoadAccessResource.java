package cn.digitalpublishing.servlet;

import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;

public class LoadAccessResource {
	
	private LoadAccessResource(){}
	
	public static void loadResource(String url){
		try{
			ServiceFactory serviceFactory;
			serviceFactory=(ServiceFactory)new ServiceFactoryImpl();
			if(serviceFactory.getCUserTypeService()!=null){
				serviceFactory.getCUserTypeService().insertUrl(url);
				serviceFactory.getCUserTypeService().loadAccessResources(url);
			}else{
				System.out.println("YYYYY   Not init  YYYYY");
			}
					
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
