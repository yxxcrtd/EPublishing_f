package cn.digitalpublishing.service;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.ep.po.SSupplier;

public interface StatisticsBookSuppliersService extends BaseService {
	/**
	 * 提供商TOC访问列表
	 */
	public List<SSupplier> getTocList(Map<String, Object> condition ,String sort,Integer pageCount,Integer page)throws Exception;
	/**
	 * 提供商全文访问列表
	 */
	public List<SSupplier> getFullAccessList(Map<String, Object> condition ,String sort,Integer pageCount,Integer page)throws Exception;
	/**
	 * 提供商全文拒访列表
	 */
	public List<SSupplier> getFullRefusedList(Map<String, Object> condition ,String sort,Integer pageCount,Integer page)throws Exception;
	/**
	 * 提供商下载列表
	 */
	public List<SSupplier> getDownloadList(Map<String, Object> condition ,String sort,Integer pageCount,Integer page)throws Exception;
	/**
	 * 提供商搜索列表
	 */
	public List<SSupplier> getSearchList(Map<String, Object> condition ,String sort,Integer pageCount,Integer page)throws Exception;
	
	/**
	 * 统计查询
	 */
	public List<SSupplier> getSuppList(Map<String, Object> condition,String sort)throws Exception;
	/**
	 * 获取总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getSSupplierCount(Map<String,Object> condition)throws Exception;
	/**
	 * 获取分组总数
	 */
	public Integer getSSupplierCountGroupby(Map<String, Object> condition,String group)throws Exception;
	/**
	 * 图书/期刊全文统计
	 */
	public List<LAccess> getBookStatisticalList(Map<String, Object> condition ,String sort,Integer pageCount,Integer page)throws Exception;
	/**
	 * 图书/期刊拒访统计
	 */
	public List<LAccess> getRefuseBookJournalList(Map<String, Object> condition ,String sort,Integer pageCount,Integer page)throws Exception;
	/**
	 * 图书/期刊搜索
	 */
	public List<LAccess> getSearchesBookJournalList(Map<String, Object> condition ,String sort,Integer pageCount,Integer page)throws Exception;
	/**
	 * 获取总数
	 */
	public Integer getLaccessCount(Map<String, Object> condition,String group)throws Exception;
	/**
	 * 提供商统计
	 * @throws Exception
	 */
	public void getSupplier(Integer type,Integer access,Integer stype,String stimes,String year,String sourceid)throws Exception;
	/**
	 * 同步线程cepe Laccess 
	 */
	public void getSyncLaccess()throws Exception;
	/**
	 * 下载图书全文浏览
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getDownloadBookFull(Map<String, Object> condition,String sort,int pageCount,int page) throws Exception ;
	/**
	 * 下载图书 期刊拒访浏览
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getDownloadRefuseBookJournal(Map<String, Object> condition,String sort,int pageCount,int page) throws Exception ;
	/**
	 * 下载图书 期刊 搜索
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getDownloadSearchesBookJournal(Map<String, Object> condition,String sort,int pageCount,int page) throws Exception ;
	
	
	public void getSupplierFast(Integer type, Integer access, Integer stype, String stimes, String year, String sourceid) throws Exception; 
}
