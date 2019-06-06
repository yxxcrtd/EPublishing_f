package cn.digitalpublishing.service.impl;

import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.exception.CcsException;
import cn.digitalpublishing.ep.po.BService;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.service.BServiceService;

public class BServiceServiceImpl extends BaseServiceImpl implements
             BServiceService {

	@Override
	public void insertObj(BService obj) throws Exception {
		try {
			this.getDaoFacade().getbServiceDao().insert(obj);
		} catch (Exception e) {
			throw new Exception("新增服务信息失败！");
		}
	}

	@Override
	public void deleteObj(String id) throws Exception {
		try {
			this.daoFacade.getbServiceDao().delete(
					BService.class.getName(), id);
		} catch (Exception e) {
			throw new Exception("删除服务信息失败！");
		}
	}

	@Override
	public void updateObj(BService obj, String id, String[] properties)
			throws Exception {
		try {
			this.daoFacade.getbServiceDao().update(obj,
					BService.class.getName(), id, properties);
		} catch (Exception e) {
			throw new Exception("修改服务信息失败！");
		}

	}

	@Override
	public BService getObjById(String id) throws Exception {
		try {
			return (BService) this.daoFacade.getbServiceDao().get(
					BService.class.getName(), id);
		} catch (Exception e) {
			throw new Exception("获取服务信息失败！");
		}
	}

	@Override
	public List<BService> getList(Map<String, Object> condition,
			String sort) throws Exception {
		try {
			return this.daoFacade.getbServiceDao().getList(condition,
					sort);
		} catch (Exception e) {
			throw new Exception("获取服务列表信息失败！");
		}
	}

	@Override
	public List<BService> getPagingList(Map<String, Object> condition,
			String sort, Integer pageCount, Integer curPage) throws Exception {
		try {
			return this.daoFacade.getbServiceDao().getPagingList(
					condition, sort, pageCount, curPage);
		} catch (Exception e) {
			throw new Exception("获取服务分页列表信息失败！");
		}
	}

	@Override
	public int getCount(Map<String, Object> condition) throws Exception {
		try {
			return this.daoFacade.getbServiceDao().getCount(condition);
		} catch (Exception e) {
			throw new Exception("获取服务列表信息失败！");
		}
	}
	/**
	 * 简单分页列表
	 * @param condition
	 * @param sort
	 * @param pageCount
	 * @param page
	 * @param user
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BService> getPubSimplePageList(Map<String,Object> condition,String sort,Integer pageCount,Integer page)throws Exception{
		List<BService> list = null ;
		try {
			list = this.daoFacade.getbServiceDao().getSimplePagingList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "service.paginglist.get.error", e);//获取分页列表信息失败！
		}
		return list;
	}
}
