package cn.digitalpublishing.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.CAccount;

public interface CAccountService extends BaseService{

	/**
	 * 获取登录信息
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 * by    Ma Guoqing
	 */
	public List<CAccount> getList(Map<String,Object> condition,String sort) throws Exception;
	/**
	 * 获取用户登录对象
	 * @param id
	 * @return
	 * @throws Exception
	 * by    Ma Guoqing
	 */
	//public CAccount getCAccount(String id) throws Exception;
	/**
	 * 更新用户登录信息
	 * @param obj
	 * @param className
	 * @param id
	 * @param properties
	 * @throws Exception
	 * by    Ma Guoqing
	 */
//	public void updateCAccount(Object obj, String className, Serializable id, String[] properties) throws Exception;
	
}
