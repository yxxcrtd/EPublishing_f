package cn.digitalpublishing.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.CUserTypeProp;

public interface CUserTypePropService extends BaseService{
	
	/**
	 * 获取用户类型属性信息
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 * by    Ma Guoqing
	 */
	public List<CUserTypeProp> getList(Map<String,Object> condition ,String sort) throws Exception;

	/**
	 * 获取 用户类型属性 对象
	 * @param id
	 * @return
	 * @throws Exception
	 * by    Ma Guoqing
	 */
	public CUserTypeProp getCUserTypeProp(String id) throws Exception;
	
	/**
	 * 更新用户类型属性
	 * @param obj
	 * @param className
	 * @param id
	 * @param properties
	 * @throws Exception
	 * by    Ma Guoqing
	 */
	public void updateCUserTypeProp(Object obj,String className,Serializable id,String[] properties) throws Exception;
}
