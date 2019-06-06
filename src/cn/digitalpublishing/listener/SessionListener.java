package cn.digitalpublishing.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;

public class SessionListener implements HttpSessionListener,HttpSessionAttributeListener{

	ServiceFactory serviceFactory = (ServiceFactory)new ServiceFactoryImpl();
	
	@Override
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
//		HttpSession session = arg0.getSession();
//		session.setMaxInactiveInterval(30);//设置session过期时间，以秒为单位
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		//清空用户下占用的并发
		try {
			HttpSession session = arg0.getSession();
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("sessionId", session.getId());
			this.serviceFactory.getPPublicationsService().deleteComplicatingByCondition(condition);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
