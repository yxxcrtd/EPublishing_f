package cn.digitalpublishing.thread;

import java.util.Date;

import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;


public class PDAProcess extends Thread {
	
	private ServiceFactory serviceFactory ;
	
	private PDACounter counter;
	
	public PDAProcess (PDACounter counter){
		this.serviceFactory=(ServiceFactory)new ServiceFactoryImpl();
		this.counter=counter;
	}
	
	@Override
	public void run(){
		Boolean b=false;
		if(b){//测试通过后取消这设置
		this.handle();
		counter.countDown();
		}
	}
	
	private void handle(){
		try{
			serviceFactory.getOOrderService().handlePDA();
			System.out.println("PDA处理中...."+new Date());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
