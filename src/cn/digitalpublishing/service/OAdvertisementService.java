package cn.digitalpublishing.service;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.OAdvertisement;

/**
 * 广告 OAdvertisementService
 * 
 * @author LiuYe
 * 
 */
public interface OAdvertisementService extends BaseService {

	/**
	 * 新增数据
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public void insertObj(OAdvertisement obj) throws Exception;

	/**
	 * 删除广告数据
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteObj(String id) throws Exception;

	/**
	 * 修改广告数据ByID
	 * 
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updateObj(OAdvertisement obj, String id, String[] properties)
			throws Exception;

	/**
	 * 通过ID获取广告
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public OAdvertisement getObjById(String id) throws Exception;

	/**
	 * 获取广告列表
	 * 
	 * @param condtion
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<OAdvertisement> getList(Map<String, Object> condition,
			String sort) throws Exception;

	/**
	 * 获取广告分页列表
	 * 
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curPage
	 * @return
	 * @throws Exception
	 */
	public List<OAdvertisement> getPagingList(Map<String, Object> condition,
			String sort, Integer pageCount, Integer curPage) throws Exception;

	/**
	 * 获取总数
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getCount(Map<String, Object> condition) throws Exception;
}
