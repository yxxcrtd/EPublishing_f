package cn.digitalpublishing.service.impl;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.OAdvertisement;
import cn.digitalpublishing.service.OAdvertisementService;

public class OAdvertisementServiceImpl extends BaseServiceImpl implements
		OAdvertisementService {

	@Override
	public void insertObj(OAdvertisement obj) throws Exception {
		try {
			this.getDaoFacade().getoAdvertisementDao().insert(obj);
		} catch (Exception e) {
			throw new Exception("新增广告信息失败！");
		}
	}

	@Override
	public void deleteObj(String id) throws Exception {
		try {
			this.daoFacade.getoAdvertisementDao().delete(
					OAdvertisement.class.getName(), id);
		} catch (Exception e) {
			throw new Exception("删除广告信息失败！");
		}
	}

	@Override
	public void updateObj(OAdvertisement obj, String id, String[] properties)
			throws Exception {
		try {
			this.daoFacade.getoAdvertisementDao().update(obj,
					OAdvertisement.class.getName(), id, properties);
		} catch (Exception e) {
			throw new Exception("修改广告信息失败！");
		}

	}

	@Override
	public OAdvertisement getObjById(String id) throws Exception {
		try {
			return (OAdvertisement) this.daoFacade.getoAdvertisementDao().get(
					OAdvertisement.class.getName(), id);
		} catch (Exception e) {
			throw new Exception("获取广告信息失败！");
		}
	}

	@Override
	public List<OAdvertisement> getList(Map<String, Object> condition,
			String sort) throws Exception {
		try {
			return this.daoFacade.getoAdvertisementDao().getList(condition,
					sort);
		} catch (Exception e) {
			throw new Exception("获取广告列表信息失败！");
		}
	}

	@Override
	public List<OAdvertisement> getPagingList(Map<String, Object> condition,
			String sort, Integer pageCount, Integer curPage) throws Exception {
		try {
			return this.daoFacade.getoAdvertisementDao().getPagingList(
					condition, sort, pageCount, curPage);
		} catch (Exception e) {
			throw new Exception("获取广告分页列表信息失败！");
		}
	}

	@Override
	public Integer getCount(Map<String, Object> condition) throws Exception {
		try {
			return this.daoFacade.getoAdvertisementDao().getCount(condition);
		} catch (Exception e) {
			throw new Exception("获取广告列表信息失败！");
		}
	}

}
