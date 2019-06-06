package cn.digitalpublishing.service;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.RRecommend;
import cn.digitalpublishing.ep.po.RRecommendDetail;

public interface RRecommendService extends BaseService {

	/**
	 * 查询荐购列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public  List<RRecommend> getRecommendList(Map<String, Object> condition, String sort)throws Exception;
	
	/**
	 * 查询荐购分页列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public  List<RRecommend> getRecommendPagingList(Map<String, Object> condition, String sort,Integer pageCount, Integer page)throws Exception;

	/**
	 * 根据条件插入Recommend和RecommendDetail信息
	 * @param recommend
	 * @param detail
	 * @param condition
	 * @throws Exception
	 */
	public void insertRecommend(RRecommend recommend,RRecommendDetail detail,Map<String,Object> condition)throws Exception;
	
	/**
	 * 查询荐购详情分页列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public  List<RRecommendDetail> getRecommendDetailPagingList(Map<String, Object> condition, String sort,Integer pageCount, Integer page)throws Exception;
	/**
	 * 获取暂缓购买信息详情
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<RRecommendDetail> getParticularsList(Map<String,Object> condition,String sort)throws Exception;
	/**
	 * 查询荐购详情列表
	 * @param condition
	 * @return
	 */
	public List<RRecommendDetail> getRecommendDetailList(Map<String,Object> condition) throws Exception;
	/**
	 * 获取荐购详情总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getRecommendDetailCount(Map<String,Object> condition)throws Exception;
	/**
	 * 获取荐购总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getRecommendCount(Map<String,Object> condition)throws Exception;
	/**
	 * 暂缓购买
	 * @param recommendId
	 * @throws Exception
	 */
	public void suspendRecommend(String recommendId,String particulars)throws Exception;	
	
}
