package cn.digitalpublishing.search;

import java.util.Map;

import cn.digitalpublishing.ep.po.PPage;



public interface PagesIndexService {

	/**
	 * 为分页创建索引
	 * 
	 * @param Pages
	 *            分页对象
	 * 
	 * @return 索引结果状态码 0表示成功
	 * @throws Exception
	 */
	public int indexPages(PPage page) throws Exception;
	/**
	 * 查询全文
	 * 
	 * @param Pages
	 *            分页对象
	 * 
	 * @return 索引结果状态码 0表示成功
	 * @throws Exception
	 */
	public Map<String, Object> selectPagesByFullText(String sourceId,String fulltext,Integer page,Integer pageSize) throws Exception;
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
	 * 根据条件删除索引
	 */
	public void deleteIndexByCondition(String fieidName,String value) throws Exception;
}
