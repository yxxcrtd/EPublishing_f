package cn.digitalpublishing.service;

import java.util.List;
import java.util.Map;
import cn.digitalpublishing.ep.po.ResourcesSum;

public interface ResourcesSumService extends BaseService{
	/**
	 * 获取总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<ResourcesSum> getList(Map<String,Object> condition,String sort)throws Exception;
	/**
	 * 获取分页信息
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<ResourcesSum> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception;
	/**
	 * 获取总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getCount(Map<String,Object> condition)throws Exception;
	/**
	 * 根据条件插入ResourcesSum
	 */
	public void insertRecommend(ResourcesSum obj)throws Exception;
	/**
	 * 更新ResourcesSum信息
	 */
	public void updateResourcesSum(ResourcesSum obj,String id,String[] properties) throws Exception;

}
