package cn.digitalpublishing.service;

import java.util.List;

public interface TimeTaskService {
	/**
	 * 处理用户学科主题订阅提醒功能
	 * 1.定时器每天凌晨一点执行查询操作（查询提醒等待队列表），如果有数据，进行发送邮件提醒
	 * 2.按照提醒频率进行发送任务
	 * 		a.频率-立即 的已经在提交提醒请求时已经发送完成。
	 * 		b.频率-每天 的在凌晨一点进行发送。
	 * 		c.频率-每周 的在每周一的凌晨一点进行发送。
	 * 		d.频率-每月 的在每月第一天的凌晨一点进行发送。
	 * 3.发送订阅提醒后，删除等待队列中信息，并向完成队列插入一条记录信息
	 * @throws Exception
	 */
//	public void handleAlertsWaitingQueue()throws Exception;
	
	/**
	 * 通过学科主题ID获取已经订阅该学科主题（分类法）的用户提醒信息，
	 * 如果是提醒频率-立即，立即发送邮件到用户邮箱，
	 * 如果提醒频率为其他三种（每天、每周、每月），将此类提醒信息加入等待提醒队列
	 * @param subjectId
	 * @throws Exception
	 */
//	public String pushAlertsToWaitingQueue(String subjectTreeCode,String pubId,String pubName,String isbn)throws Exception;
	
	/**
	 * 系统自动进行查询提醒表（alerts），进行对新上架的产品向订阅该学科主题的用户发送新书上架提醒邮件
	 * 
	 * 1.定时器每天凌晨一点执行查询操作
	 * 2.按照提醒频率进行发送任务
	 * 		a.频率-每天 的在凌晨一点进行发送前一天的。
	 * 		b.频率-每周 的在每周一的凌晨一点进行发送前一周的。
	 * 		c.频率-每月 的在每月第一天的凌晨一点进行发送前一个月的。
	 * @throws Exception
	 */
	public void autoHandleAlerts()throws Exception;
	
	
	public List<String> deleteIndex()throws Exception;


	/**
	 * 系统自动进行查询（LUserAlertsLog），进行续订提醒
	 * 
	 * @throws Exception
	 */
	public void autoRenewalAlerts()throws Exception;
}
