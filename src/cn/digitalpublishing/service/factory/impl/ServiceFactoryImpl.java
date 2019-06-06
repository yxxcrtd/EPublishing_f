package cn.digitalpublishing.service.factory.impl;

import com.zhima.server.util.mail.SendMail;

import cn.com.daxtech.framework.web.service.SpringBeanService;
import cn.digitalpublishing.search.impl.SolrLicenseIndexService;
import cn.digitalpublishing.service.BSubjectService;
import cn.digitalpublishing.service.CUserService;
import cn.digitalpublishing.service.CUserTypeService;
import cn.digitalpublishing.service.ConfigureService;
import cn.digitalpublishing.service.CustomService;
import cn.digitalpublishing.service.LogAOPService;
import cn.digitalpublishing.service.OOrderService;
import cn.digitalpublishing.service.PNoteService;
import cn.digitalpublishing.service.PPublicationsService;
import cn.digitalpublishing.service.PRecordService;
import cn.digitalpublishing.service.ResourcesSumService;
import cn.digitalpublishing.service.StatisticService;
import cn.digitalpublishing.service.StatisticsBookSuppliersService;
import cn.digitalpublishing.service.TimeTaskService;
import cn.digitalpublishing.service.factory.ServiceFactory;

public class ServiceFactoryImpl implements ServiceFactory {

	public ConfigureService getConfigureService() {
		// TODO Auto-generated method stub
		return (ConfigureService) SpringBeanService.getService("configureService");
	}

	public BSubjectService getBSubjectService() {
		return (BSubjectService) SpringBeanService.getService("bSubjectService");
	}

	public PPublicationsService getPPublicationsService() {
		return (PPublicationsService) SpringBeanService.getService("pPublicationsService");
	}

	@Override
	public PRecordService getPRecordService() {
		return (PRecordService) SpringBeanService.getService("pRecordService");
	}

	@Override
	public PNoteService getPNoteServiceService() {
		return (PNoteService) SpringBeanService.getService("pNoteService");
	}

	@Override
	public CustomService getCustomService() {
		return (CustomService) SpringBeanService.getService("customService");
	}

	@Override
	public SendMail getSendMailService() {
		return (SendMail) SpringBeanService.getService("sendMail");
	}

	@Override
	public CUserService getCUserService() {
		return (CUserService) SpringBeanService.getService("cUserService");
	}

	@Override
	public OOrderService getOOrderService() {
		return (OOrderService) SpringBeanService.getService("oOrderService");
	}

	@Override
	public TimeTaskService getTimeTaskService() {
		return (TimeTaskService) SpringBeanService.getService("timeTaskService");
	}

	@Override
	public SolrLicenseIndexService getSolrLicenseIndexService() {
		// TODO Auto-generated method stub
		return (SolrLicenseIndexService) SpringBeanService.getService("licenseIndexService");
	}

	@Override
	public LogAOPService getLogAOPService() {
		// TODO Auto-generated method stub
		return (LogAOPService) SpringBeanService.getService("logAOPService");
	}

	@Override
	public StatisticService getStatisticService() {
		// TODO Auto-generated method stub
		return (StatisticService) SpringBeanService.getService("statisticService");
	}

	@Override
	public CUserTypeService getCUserTypeService() {
		// TODO Auto-generated method stub
		return (CUserTypeService) SpringBeanService.getService("cUserTypeService");
	}

	@Override
	public ResourcesSumService getResourcesSumService() {
		// TODO Auto-generated method stub
		return (ResourcesSumService) SpringBeanService.getService("resourcesSumService");
	}

	@Override
	public StatisticsBookSuppliersService getBookSuppliersService() {
		// TODO Auto-generated method stub

		return (StatisticsBookSuppliersService) SpringBeanService.getService("bookSuppliersService");
	}

}
