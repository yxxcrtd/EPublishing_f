package cn.digitalpublishing.inteceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;

public class ActionInterceptor extends HandlerInterceptorAdapter {
	
	ServiceFactory serviceFactory = null;
	
	public ActionInterceptor(){
		serviceFactory=(ServiceFactory)new ServiceFactoryImpl();
	}

}
