package cn.digitalpublishing.service;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.BSubject;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.PCsRelation;

public interface BSubjectService extends BaseService {

	/**
	 * 获取分类列表
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<BSubject> getSubList(Map<String,Object> condition,String sort)throws Exception;
	/**
	 * 获取分类列表2 （查询列表 不进行统计）
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<BSubject> getSubList2(Map<String,Object> condition,String sort)throws Exception;

	/**
	 * 根据分类和产品关联表查询产品
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param curpage
	 * @return
	 */
	public List<PCsRelation> getSubPubPageList(Map<String, Object> condition,String sort, int pageCount, int curpage,CUser user ,String ip)throws Exception;

	/**
	 * 根据分类和产品关联表查询产品总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int getSubPubCount(Map<String, Object> condition)throws Exception;

	/**
	 * 根据分类和产品关联表查询产品列表
	 * @param condition
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public List<PCsRelation> getSubPubList(Map<String, Object> condition,
			String sort)throws Exception;

	/**
	 * 查询产品包中产品分类个数
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<BSubject> getSubColListCount(Map<String, Object> condition,
			String sort)throws Exception;

	/**
	 * 用于出版物A-Z/查询
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<BSubject> getSubOrderListCount(Map<String, Object> condition,String sort) throws Exception;
	/**
	 * 更新分类
	 * @param obj
	 * @param id
	 * @param properties
	 * @throws Exception
	 */
	public void updateSubject(BSubject obj, String id, String[] properties)throws Exception;

	/**
	 * 添加分类
	 * @param obj
	 * @throws Exception
	 */
	public void insertSubject(BSubject obj)throws Exception;
	/**
	 * 获取分类统计
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<BSubject> getStatisticList(Map<String, Object> condition,String sort)throws Exception;
	/**
	 * 获取全部分类统计
	 * @param condition
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<BSubject> getStatisticSubAllList(Map<String, Object> condition,String sort)throws Exception;
	/**
	 * 获取分类法
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BSubject getSubject(String id)throws Exception;
	/**
	 * 获取分类法
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public BSubject getSubjectByCode(String code)throws Exception;
	/**
	 * 获取分类法下所有子级
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<BSubject> getSubjectByListCode(String code)throws Exception;
	/**
	 * 获取指定条数列表
	 * @param condition
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public List<PCsRelation> getTops(Map<String,Object> condition,String sort, int pageCount, int curpage,CUser user,String ip)throws Exception;
	/**
	 * 删除分类信息
	 * @param id
	 * @throws Exception
	 */
	public void deleteSubject(String id) throws Exception;
	/**
	 * 获取需提醒的新产品数量
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getCountForAlerts(Map<String,Object> condition)throws Exception;
}
