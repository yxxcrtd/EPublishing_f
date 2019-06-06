package cn.digitalpublishing.service;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.PRecord;

public interface PRecordService extends BaseService {
	/**
	 * 新增pdf
	 * @param obj
	 * @throws Exception
	 */
	public void addRecord(PRecord obj)throws Exception;
	
	/**
	 * 获取pdf
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PRecord getPRecord(String id)throws Exception;
	/**
	 * 删除pdf
	 * @param id
	 * @param path
	 * @throws Exception
	 */
	public void deletePRecord(String id,String path)throws Exception;
	
	/**
	 * 获取列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<PRecord> getPRecordList(Map<String,Object> condition,String sort)throws Exception;
	/**
	 * 获取分页列表
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PRecord> getPRecordPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception;
	/**
	 * 获取总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getPRecordCount(Map<String,Object> condition)throws Exception;
	/**
	 * 按照源文件Id删除
	 * @param sourceId
	 * @throws Exception
	 */
	public void deleteBySourceId(String sourceId,String userId)throws Exception;
}
