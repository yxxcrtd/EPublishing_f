package cn.digitalpublishing.service;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.BService;

/**
 * Service
 * 
 * @author LiuYe
 */
public interface BServiceService extends BaseService {

	public void insertObj(BService obj) throws Exception;

	public void deleteObj(String id) throws Exception;

	public void updateObj(BService obj, String id, String[] properties) throws Exception;

	public BService getObjById(String id) throws Exception;

	public List<BService> getList(Map<String, Object> condition, String sort) throws Exception;

	public List<BService> getPagingList(Map<String, Object> condition, String sort, Integer pageCount, Integer curPage) throws Exception;

	public int getCount(Map<String, Object> condition) throws Exception;

	public List<BService> getPubSimplePageList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception;

}
