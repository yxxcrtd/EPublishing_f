package cn.digitalpublishing.service;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.CDirectory;
import cn.digitalpublishing.ep.po.CFavourites;
import cn.digitalpublishing.ep.po.CSearchHis;
import cn.digitalpublishing.ep.po.CUser;

public interface CUserService extends BaseService {

	/**
	 * 查询用户列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	List<CUser> getUserList(Map<String, Object> condition, String sort)throws Exception;
	
	/**
	 * 查询收藏夹
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<CFavourites> getFavoutitesList(Map<String, Object> condition,String sort)throws Exception;

	/**
	 * 删除收藏夹
	 * @param condition
	 * @throws Exception
	 */
	public void deleteFavorites(Map<String, Object> condition)throws Exception;

	/**
	 * 插入搜索记录
	 * @param searchValue
	 * @throws Exception
	 */
	public void addSearchHistory(CSearchHis obj)throws Exception;

	/**
	 * 查询搜索历史列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<CSearchHis> getSearchHistoryList(Map<String, Object> condition,String sort)throws Exception;

	/**
	 * 查询文件夹列表
	 * @param object
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<CDirectory> getDirtoryList(Map<String,Object> condition, String sort)throws Exception;
	public List<CDirectory> getDirtoryList1(Map<String,Object> condition, String sort)throws Exception;

	/**
	 * 更新搜索结果保存
	 * @param his
	 * @param id
	 * @throws Exception
	 */
	public void updateSearchHis(CSearchHis his, String id,String[] properties)throws Exception;

	/**
	 * 搜索收藏夹
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CDirectory getDirtory(String id)throws Exception;

	/**
	 * 更新收藏夹
	 * @param dir
	 * @param id
	 * @throws Exception
	 */
	public void updateDirectory(CDirectory obj, String id,String[] properties)throws Exception;

	/**
	 * 添加收藏夹
	 * @param obj
	 * @throws Exception
	 */
	public void addDirectory(CDirectory obj)throws Exception;

	/**
	 * 删除搜索历史
	 * @param id
	 * @throws Exception
	 */
	public void deleteSearchHis(String id)throws Exception;

	/**
	 * 根据条件删除搜索历史
	 * @param condition
	 * @throws Exception
	 */
	public void deleteSearchHisByCondition(Map<String, Object> condition)throws Exception;

	/**
	 * 删除文件夹
	 * @param id
	 * @throws Exception
	 */
	public void deleteDirectory(String id)throws Exception;

	/**
	 * 根据ID查询用户信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CUser getUser(String id)throws Exception;
	
	/**
	 * 查询分页用户列表
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<CUser> getUserPagingList(Map<String, Object> condition, String sort,Integer pageCount, Integer page) throws Exception;
	/**
	 * 获取收藏的分页列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<CFavourites> getFavoutitesPagingList(Map<String, Object> condition,String sort,int pageCount,int curPage)throws Exception;
	/**
	 * 下载excel
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<CFavourites> getfList(Map<String, Object> condition,String sort)throws Exception;

	/**
	 * 获取收藏的总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int getFavoutitesCount(Map<String, Object> condition)throws Exception;

	/**
	 *获取收藏夹总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int getDirtoryCount(Map<String, Object> condition)throws Exception;

	/**
	 * 获取收藏夹分页列表
	 * @param condition
	 * @param string
	 * @param pageCount
	 * @param curpage
	 * @return
	 * @throws Exception
	 */
	public List<CDirectory> getDirtoryPagingList(Map<String, Object> condition,
			String string, int pageCount, int curpage)throws Exception;

	/**
	 * 查询搜索历史记录
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CSearchHis getSearchHistory(String id)throws Exception;

}
