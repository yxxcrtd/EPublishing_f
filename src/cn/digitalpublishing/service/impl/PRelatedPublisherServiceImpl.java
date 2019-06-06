package cn.digitalpublishing.service.impl;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.PRelatedPublisher;
import cn.digitalpublishing.service.PRelatedPublisherService;

public class PRelatedPublisherServiceImpl extends BaseServiceImpl implements PRelatedPublisherService{

	@Override
	public void insert(PRelatedPublisher obj) throws Exception {
		try {
			this.daoFacade.getpRelatedPublisherDao().insert(obj);
		} catch (Exception e) {
			throw new Exception("操作失败！");
		}
	}

	@Override
	public void delete(String id) throws Exception {
		try {
			this.daoFacade.getpRelatedPublisherDao().delete(PRelatedPublisher.class.getName(), id);
		} catch (Exception e) {
			throw new Exception("操作失败！");
		}
	}

	@Override
	public void update(PRelatedPublisher obj, String id, String[] properties)
			throws Exception {
		try {
			this.daoFacade.getpRelatedPublisherDao().update(obj, PRelatedPublisher.class.getName(), id, properties);
		} catch (Exception e) {
			throw new Exception("操作失败！");
		}
	}

	@Override
	public PRelatedPublisher getById(String id) throws Exception {
		try {
			return (PRelatedPublisher)this.daoFacade.getpRelatedPublisherDao().get(PRelatedPublisher.class.getName(), id);
		} catch (Exception e) {
			throw new Exception("操作失败！");
		}
	}

	@Override
	public List<PRelatedPublisher> getList(Map<String, Object> condition, String sort)
			throws Exception {
		try {
			return this.daoFacade.getpRelatedPublisherDao().getList(condition, sort);
		} catch (Exception e) {
			throw new Exception("操作失败！");
		}
	}

	@Override
	public List<PRelatedPublisher> getPagingList(Map<String, Object> condition,
			String sort, Integer pageCount, Integer page) throws Exception {
		try {
			return this.daoFacade.getpRelatedPublisherDao().getPagingList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new Exception("操作失败！");
		}
	}

	@Override
	public Integer getCount(Map<String, Object> condition) throws Exception {
		try {
			return this.daoFacade.getpRelatedPublisherDao().getCount(condition);
		} catch (Exception e) {
			throw new Exception("操作失败！");
		}
	}

	@Override
	public void syncToEPUB(String id) throws Exception {
		try {
			this.daoFacade.getpRelatedPublisherDao().SYNCTOCNPE(id);
		} catch (Exception e) {
			throw e;
		}
	}

	
}
