package cn.digitalpublishing.thread;

import java.util.List;

import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;

public class LicenseListener {

	private ServiceFactory serviceFactory ;

	public LicenseListener (){
		this.serviceFactory=(ServiceFactory)new ServiceFactoryImpl();
	}
	
	public void licenseHandle(){
		try{
			List<String> list= serviceFactory.getTimeTaskService().deleteIndex();
			if(list!=null && !list.isEmpty()){
				for(String id:list){
					serviceFactory.getSolrLicenseIndexService().deleteIndexById(id);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
