package cn.digitalpublishing.service;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.ep.po.RRecommendDetail;

/**
 * @author lifh
 * 日志服务
 */
public interface LogAOPService extends BaseService {
	
	public void addLog(LAccess access)throws Exception;

	/**
	 * 查询访问统计
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYear(Map<String,Object> condition,String sort)throws Exception;

	/**
	 * 查询搜索记录
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYearToSearch(Map<String, Object> condition,
			String sort)throws Exception;

	/**
	 * 查询内容浏览记录
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYearToPage(Map<String, Object> condition,
			String sort)throws Exception;
	/**
	 * 查询提供商的资源被访问情况
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYearForSource(Map<String,Object> condition,String sort)throws Exception;
	/**
	 * 查询提供商的资源被访问总数
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYearCountForSource(Map<String,Object> condition)throws Exception;
	/**
	 * 查询提供商的资源被访问情况(分页)
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYearPagingForSource(Map<String,Object> condition,String sort,int pageCount, int curpage)throws Exception;
	
	/**
	 * 查询提供商的资源被访问情况
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfPubForSource(Map<String,Object> condition,String sort)throws Exception;
	/**
	 * 查询提供商的资源被访问总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfPubCountForSource(Map<String,Object> condition)throws Exception;
	/**
	 * 查询提供商的资源被访问情况(分页)
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfPubPagingForSource(Map<String,Object> condition,String sort,int pageCount, int curpage)throws Exception;
	/**
	 * 统计出版社的图书被推荐的次数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<RRecommendDetail> getCounterForPublisher(Map<String,Object> condition,String sort)throws Exception;
	/**
	 * 分页统计出版社的图书被推荐的次数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<RRecommendDetail> getCounterPagingForPublisher(Map<String,Object> condition,String sort,int pageCount, int curpage)throws Exception;
	/**
	 * 统计出版社的图书被推荐次数总计
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<RRecommendDetail> getCounterCountForPublisher(Map<String,Object> condition)throws Exception;
	/**
	 * 查询访问统计（分页）
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYearPaging(Map<String,Object> condition,String sort,int pageCount, int curpage)throws Exception;
	/**
	 * 查询搜索统计（分页）
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYearToSearchPaging(Map<String, Object> condition,String sort,int pageCount, int curpage)throws Exception;
	/**
	 * 查询浏览统计（分页）
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYearToPagePaging(Map<String, Object> condition,String sort,int pageCount,int curpage) throws Exception ;
	/**
	 * 查询总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getCount(Map<String,Object> condition,String group) throws Exception;
	/**
	 * 查询count结果
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getNormalCount(Map<String,Object> condition) throws Exception;
	/**
	 * 按统计次数倒序返回列表
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getTopList(Map<String,Object> condition,Integer number) throws Exception;
	/**
	 * 查询是否存在
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> isExistLog(Map<String, Object> condition)throws Exception;

	/**
	 * 根据Id查询 日志信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public LAccess getLogInfo(String id)throws Exception;

	/**
	 * 更新日志信息
	 * @param laccess
	 * @throws Exception
	 */
	public void updateLogInfo(LAccess laccess,String id ,String[] properties)throws Exception;
	
	/**
	 * 查询访问统计2（分页）
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYearPaging2(Map<String,Object> condition,String sort,int pageCount, int curpage)throws Exception;
	/**
	 * 查询访问统计3（分页）
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYear3(Map<String,Object> condition,String sort)throws Exception;
	/**
	 * 查询搜索统计2（分页）
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYearToSearchPaging2(Map<String, Object> condition,String sort,int pageCount, int curpage)throws Exception;
	/**
	 * 查询浏览统计2（分页）
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYearToPagePaging2(Map<String, Object> condition,String sort,int pageCount,int curpage) throws Exception ;
	/**
	 * 查询浏览统计4（分页）
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYearToPagePaging4(Map<String, Object> condition,String sort,int pageCount,int curpage) throws Exception ;
	
	/**
	 * 查询访问统计
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYear2(Map<String,Object> condition,String sort)throws Exception;

	/**
	 * 查询搜索记录
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYearToSearch2(Map<String, Object> condition,String sort)throws Exception;

	/**
	 * 查询内容浏览记录
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfYearToPage2(Map<String, Object> condition,String sort)throws Exception;

	/**
	 * 热词搜索
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curPage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfHotWords(Map<String, Object> condition,String sort, int pageCount, int curPage)throws Exception;
	/**
	 * cn热词搜索
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curPage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getcnLogOfHotWords(Map<String, Object> condition,String sort, int pageCount, int curPage)throws Exception;
	/**
	 * 热读资源
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curPage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfHotReading(Map<String, Object> condition,String sort, int pageCount, int curPage)throws Exception;
	/**
	 * 最近阅读
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curPage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getLogOfRecentlyRead(Map<String, Object> condition,String sort, int pageCount, int curPage) throws Exception;
	/**
	 * 删除日志
	 * @param condition
	 * @throws Exception
	 */
	public void deleteAccess(String id)throws Exception;
	/**
	 * 
	 * @param condition
	 * @param string
	 * @return
	 * @throws Exception 
	 */
	public Integer getCount2(Map<String, Object> condition, String string) throws Exception;
	/**
	 * 期刊COUNTER统计1
	 * @param condition
	 * @param string
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception 
	 */
	public List<LAccess> getJournalReport1(Map<String, Object> condition,String string, int pageCount, int curpage) throws Exception;
	/**
	 * 期刊COUNTER统计,按年统计
	 * @param condition
	 * @param string
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getJournalReportYOP(Map<String, Object> condition,String string, int pageCount, int curpage) throws Exception;
	/**
	 * 统计：1-常规检索 , 2-高级检索 , 3-分类法点击
	 * @param condition
	 * @param string
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<LAccess> getJournalReport2(Map<String, Object> condition,String string, int pageCount, int curpage) throws Exception;
	
	
	public List<LAccess> getJournalHotReading(Map<String, Object> condition,String sort, int pageCount, int curPage)throws Exception;

	public List<LAccess> getLogOfHotReadingForRecommad(Map<String, Object> condition,
			String sort, int pageCount, int curPage) throws Exception;
}
