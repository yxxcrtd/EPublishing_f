package cn.digitalpublishing.service;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.SOnsale;

public interface StatisticService extends BaseService {
	/**
	 * 写入上线统计
	 * @param obj
	 * @throws Exception
	 */
	public void insertSOnsale(SOnsale obj) throws Exception;
	/**
	 * 修改上线统计
	 * @param obj
	 * @param date
	 * @param properties
	 * @throws Exception
	 */
	public void updateSOnsale(SOnsale obj,String date,String[] properties)throws Exception;
	/**
	 * 删除上线统计
	 * @param date
	 * @throws Exception
	 */
	public void deleteSOnsale(String date)throws Exception;
	/**
	 * 获取上线统计
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public SOnsale getSOnsale(String date)throws Exception;
	/**
	 * 获取上线统计列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<SOnsale> getSOnsaleList(Map<String,Object> condition,String sort)throws Exception;
	/**
	 * 获取上线统计分页列表
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<SOnsale> getSOnsalePagingList(Map<String,Object> condition,String sort,int pageCount,int page)throws Exception;
	/**
	 * 获取上线统计总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getSOnsaleCount(Map<String,Object> condition)throws Exception;
	/**
	 * 增加上线统计总数
	 * @param date
	 * @throws Exception
	 */
	public void increaseSOnsale(String date)throws Exception;
	/**
	 * 减少上线统计总数
	 * @param date
	 * @throws Exception
	 */
	public void decreaseSOnsale(String date)throws Exception;
	/**
	 * 获取上线统计总量
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getSOnsaleTotal(Map<String,Object> condition)throws Exception; 
}
