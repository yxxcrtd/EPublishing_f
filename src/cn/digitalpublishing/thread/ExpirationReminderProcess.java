package cn.digitalpublishing.thread;

import java.util.Date;

import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;

public class ExpirationReminderProcess extends Thread {
	
	private ServiceFactory serviceFactory ;
	
	private ExpirationReminderCounter counter;
	
	public ExpirationReminderProcess (ExpirationReminderCounter counter){
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
			serviceFactory.getTimeTaskService().autoRenewalAlerts();
			System.out.println("Alerts...."+new Date());
		}catch(Exception e){
			//e.printStackTrace();
		}
	}

}
