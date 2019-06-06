package cn.digitalpublishing.search;

import java.util.Map;

import cn.digitalpublishing.ep.po.LLicense;

public interface LicenseIndexService {
	
	/**
	 * 为授权创建索引
	 * 
	 * @param publications
	 *            书籍对象
	 * 
	 * @return 索引结果状态码 0表示成功
	 * @throws Exception
	 */
	public int indexLicenses(LLicense license) throws Exception;
	
	/**
	 * 按标题搜索
	 * 
	 * @param title
	 *            标题关键字
	 * @param userId
	 *            用户ID 用逗号分隔            
	 * @param pn
	 *            页码
	 * @param size
	 *            每页查询结果集数量
	 * @param queryParam
	 *            过滤结果集的查询条件，key为参数名，value为参数值
	 * @return Map&lt;String, Object&gt;，该map长度为2，key1="count",返回全部结果数量</br>
	 *         key2="result"，返回List&lt;String&gt;，为结果集的主键列表
	 * 
	 * @throws Exception
	 */
	public Map<String, Object> searchByTitle(String title,String userId, int pn, int size,
			Map<String, String> queryParam,String order) throws Exception;

	/**
	 * 按作者搜索
	 * 
	 * @param author
	 *            作者名称关键字
	 * @param userId
	 *            用户ID 用逗号分隔             
	 * @param pn
	 *            页码
	 * @param size
	 *            每页查询结果集数量
	 * @param queryParam
	 *            过滤结果集的查询条件，key为参数名，value为参数值
	 * @return Map&lt;String, Object&gt;，该map长度为2，key1="count",返回全部结果数量</br>
	 *         key2="result"，返回List&lt;String&gt;，为结果集的主键列表
	 * @throws Exception
	 */
	public Map<String, Object> searchByAuthor(String author,String userId, int pn, int size,
			Map<String, String> queryParam,String order) throws Exception;

	/**
	 * 按ISBN搜索
	 * 
	 * @param ISBN
	 *            ISBN编号
	 * @param userId
	 *            用户ID 用逗号分隔            
	 * @param pn
	 *            页码
	 * @param size
	 *            每页查询结果集数量
	 * @param queryParam
	 *            过滤结果集的查询条件，key为参数名，value为参数值
	 * @return Map&lt;String, Object&gt;，该map长度为2，key1="count",返回全部结果数量</br>
	 *         key2="result"，返回List&lt;String&gt;，为结果集的主键列表
	 * @throws Exception
	 */
	public Map<String, Object> searchByISBN(String ISBN,String userId, int pn, int size,
			Map<String, String> queryParam,String order) throws Exception;

	/**
	 * 按出版商名称搜索
	 * 
	 * @param publisher
	 *            出版商名称关键字
	 * @param userId
	 *            用户ID 用逗号分隔           
	 * @param pn
	 *            页码
	 * @param size
	 *            每页查询结果集数量
	 * @param queryParam
	 *            过滤结果集的查询条件，key为参数名，value为参数值
	 * @return Map&lt;String, Object&gt;，该map长度为2，key1="count",返回全部结果数量</br>
	 *         key2="result"，返回List&lt;String&gt;，为结果集的主键列表
	 * @throws Exception
	 */
	public Map<String, Object> searchByPublisher(String publisher,String userId, int pn,
			int size, Map<String, String> queryParam,String order) throws Exception;

	/**
	 * 全部字段的全文检索
	 * 
	 * @param keywords
	 *            搜索关键字
	 * @param userId
	 *            用户ID 用逗号分隔              
	 * @param pn
	 *            页码
	 * @param size
	 *            每页查询结果集数量
	 * @param queryParam
	 *            过滤结果集的查询条件，key为参数名，value为参数值
	 * @return Map&lt;String, Object&gt;，该map长度为2，key1="count",返回全部结果数量</br>
	 *         key2="result"，返回List&lt;String&gt;，为结果集的主键列表
	 * @throws Exception
	 */
	public Map<String, Object> searchByAllFullText(String keywords,String userId, int pn,
			int size, Map<String, String> queryParam,String order) throws Exception;

	/**
	 * 删除索引
	 * 
	 * @param id
	 *            索引ID
	 * @return 操作结果状态码 0表示成功
	 */
	public int deleteIndexById(String id) throws Exception;

	/**
	 * 清除所有索引文件，慎用
	 */
	public void clearAllIndex() throws Exception;

	/**
	 * 高级搜索
	 * @param userId
	 * @param curpage
	 * @param pageCount
	 * @param param
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> advancedSearch(String userId, int curpage,
			int pageCount, Map<String, String> param, String order)throws Exception;

	/**
	 * 通过IKAnalyzer 获得 查询关键字 拆分后的最小单元
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public String[] IKKewword(String field ,String keyword)throws Exception;

	Map<String, Object> advancedSearch(Integer coverType, String userId,
			int curpage, int pageCount, Map<String, String> param, String order)
			throws Exception;
}
