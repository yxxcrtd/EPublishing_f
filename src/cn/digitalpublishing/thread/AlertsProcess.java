package cn.digitalpublishing.thread;

import java.util.Date;

import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;


public class AlertsProcess extends Thread {
	
	private ServiceFactory serviceFactory ;
	
	private AlertsCounter counter;
	
	public AlertsProcess (AlertsCounter counter){
		this.serviceFactory=(ServiceFactory)new ServiceFactoryImpl();
		this.counter=counter;
	}
	
	@Override
	public void run(){
		this.send();
		counter.countDown();
	}
	
	private void send(){
		try{
			serviceFactory.getTimeTaskService().autoHandleAlerts();
			System.out.println("Alerts...."+new Date());
		}catch(Exception e){
			//e.printStackTrace();
		}
	}

}
