package cn.digitalpublishing.springmvc.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cn.digitalpublishing.redis.dao.BookDao;
import cn.digitalpublishing.search.LicenseIndexService;
import cn.digitalpublishing.search.PagesIndexService;
import cn.digitalpublishing.search.PublicationsIndexService;
import cn.digitalpublishing.service.BServiceService;
import cn.digitalpublishing.service.BSubjectService;
import cn.digitalpublishing.service.CAccountService;
import cn.digitalpublishing.service.CUserPropService;
import cn.digitalpublishing.service.CUserService;
import cn.digitalpublishing.service.CUserTypePropService;
import cn.digitalpublishing.service.CUserTypeService;
import cn.digitalpublishing.service.ConfigureService;
import cn.digitalpublishing.service.CustomService;
import cn.digitalpublishing.service.LogAOPService;
import cn.digitalpublishing.service.NewsService;
import cn.digitalpublishing.service.OAdvertisementService;
import cn.digitalpublishing.service.OOrderService;
import cn.digitalpublishing.service.PNoteService;
import cn.digitalpublishing.service.PPublicationsService;
import cn.digitalpublishing.service.PRecordService;
import cn.digitalpublishing.service.PRelatedPublisherService;
import cn.digitalpublishing.service.RRecommendService;
import cn.digitalpublishing.service.ResourcesSumService;
import cn.digitalpublishing.service.StatisticService;
import cn.digitalpublishing.service.StatisticsBookSuppliersService;
import cn.digitalpublishing.service.TimeTaskService;

import com.zhima.server.util.mail.SendMail;

public class BaseController extends MultiActionController {

	/**
	 * Log
	 */
	public Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	protected BookDao bookDao;

	@Autowired
	@Qualifier("resourcesSumService")
	protected ResourcesSumService resourcesSumService;

	@Autowired
	@Qualifier("configureService")
	protected ConfigureService configureService;

	@Autowired
	@Qualifier("bSubjectService")
	protected BSubjectService bSubjectService;
	@Autowired
	@Qualifier("pPublicationsService")
	protected PPublicationsService pPublicationsService;
	@Autowired
	@Qualifier("pRecordService")
	protected PRecordService pRecordService;
	@Autowired
	@Qualifier("pNoteService")
	protected PNoteService pNoteService;

	@Autowired
	@Qualifier("customService")
	protected CustomService customService;

	@Autowired
	@Qualifier("sendMail")
	protected SendMail sendMail;

	@Autowired
	@Qualifier("cUserService")
	protected CUserService cUserService;

	@Autowired
	@Qualifier("oOrderService")
	protected OOrderService oOrderService;

	@Autowired
	@Qualifier("rRecommendService")
	protected RRecommendService rRecommendService;

	@Autowired
	@Qualifier("publicationsIndexService")
	protected PublicationsIndexService publicationsIndexService;

	@Autowired
	@Qualifier("timeTaskService")
	protected TimeTaskService timeTaskService;

	@Autowired
	@Qualifier("licenseIndexService")
	protected LicenseIndexService licenseIndexService;

	@Autowired
	@Qualifier("pagesIndexService")
	protected PagesIndexService pagesIndexService;

	@Autowired
	@Qualifier("logAOPService")
	protected LogAOPService logAOPService;

	@Autowired
	@Qualifier("cUserPropService")
	protected CUserPropService cUserPropService;

	@Autowired
	@Qualifier("cUserTypeService")
	protected CUserTypeService cUserTypeService;

	@Autowired
	@Qualifier("cUserTypePropService")
	protected CUserTypePropService cUserTypePropService;

	@Autowired
	@Qualifier("cAccountService")
	protected CAccountService cAccountService;

	@Autowired
	@Qualifier("statisticService")
	protected StatisticService statisticService;

	@Autowired
	@Qualifier("oAdvertisementService")
	protected OAdvertisementService oAdertisementService;

	@Autowired
	@Qualifier("bServiceService")
	protected BServiceService bServiceService;

	@Autowired
	@Qualifier("newsService")
	protected NewsService newsService;

	@Autowired
	@Qualifier("pRelatedPublisherService")
	protected PRelatedPublisherService pRelatedPublisherService;

	@Autowired
	@Qualifier("bookSuppliersService")
	protected StatisticsBookSuppliersService bookSuppliersService;
}
