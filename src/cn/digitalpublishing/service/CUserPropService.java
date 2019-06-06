package cn.digitalpublishing.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.CUserProp;


public interface CUserPropService extends BaseService{
	
	/**
	 * 发送用户属性信息
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 * by    Ma Guoqing
	 */
	public List<CUserProp> getCUserPropList(Map<String, Object> condition, String sort) throws Exception;
	
	/**
	 * 获取用户属性
	 * @param id
	 * @return
	 * @throws Exception
	 * by    Ma Guoqing
	 */
	public CUserProp getCUserProp (String id) throws Exception;

	/**
	 * 更新用户属性信息
	 * @param obj
	 * @param className
	 * @param id
	 * @param properties
	 * @throws Exception
	 * by    Ma Guoqing
	 */
	public void updateCUserProp(Object obj,String className,Serializable id,String[] properties) throws Exception;
	
	/**
	 * 插入用户属性信息
	 * @param obj
	 * @throws Exception
	 *        by Ma Guoqing
	 */
	public void insertCUserProp(Object obj) throws Exception;
}
