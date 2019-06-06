package cn.digitalpublishing.service.factory;

import com.zhima.server.util.mail.SendMail;

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

public interface ServiceFactory {

	public ResourcesSumService getResourcesSumService();

	public ConfigureService getConfigureService();

	/**
	 * 分类法服务
	 * @return
	 */
	public BSubjectService getBSubjectService();

	/**
	 * 产品服务
	 * @return
	 */
	public PPublicationsService getPPublicationsService();

	/**
	 * 标签服务
	 * @return
	 */
	public PRecordService getPRecordService();

	/**
	 * 笔记服务
	 * @return
	 */
	public PNoteService getPNoteServiceService();

	/**
	 * 客户服务
	 * @return
	 */
	public CustomService getCustomService();

	/**
	 * 邮件服务
	 * @return
	 */
	public SendMail getSendMailService();

	/**
	 * 用户服务
	 * @return
	 */
	public CUserService getCUserService();

	/**
	 * 订单服务
	 * @return
	 */
	public OOrderService getOOrderService();

	/**
	 * 订阅提醒服务（定时任务）
	 * @return
	 */
	public TimeTaskService getTimeTaskService();

	/**
	 * 索引服务
	 * @return
	 */
	public SolrLicenseIndexService getSolrLicenseIndexService();

	/**
	 * 日志服务
	 * @return
	 */
	public LogAOPService getLogAOPService();

	/**
	 * 统计服务
	 * @return
	 */
	public StatisticService getStatisticService();

	/**
	 * 用户类型服务
	 * @return
	 */
	public CUserTypeService getCUserTypeService();

	/**
	 * 图书统计
	 */
	public StatisticsBookSuppliersService getBookSuppliersService();

}
