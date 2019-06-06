package cn.digitalpublishing.service.impl;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.PNews;
import cn.digitalpublishing.service.NewsService;

public class NewsServiceImpl extends BaseServiceImpl implements NewsService{

	@Override
	public void insert(PNews obj) throws Exception {
		try {
			this.daoFacade.getpNewsDao().insert(obj);
		} catch (Exception e) {
			throw new Exception("操作失败！");
		}
	}

	@Override
	public void delete(String id) throws Exception {
		try {
			this.daoFacade.getpNewsDao().delete(PNews.class.getName(), id);
		} catch (Exception e) {
			throw new Exception("操作失败！");
		}
	}

	@Override
	public void update(PNews obj, String id, String[] properties)
			throws Exception {
		try {
			this.daoFacade.getpNewsDao().update(obj, PNews.class.getName(), id, properties);
		} catch (Exception e) {
			throw new Exception("操作失败！");
		}
	}

	@Override
	public PNews getById(String id) throws Exception {
		try {
			return (PNews)this.daoFacade.getpNewsDao().get(PNews.class.getName(), id);
		} catch (Exception e) {
			throw new Exception("操作失败！");
		}
	}

	@Override
	public List<PNews> getList(Map<String, Object> condition, String sort)
			throws Exception {
		try {
			return this.daoFacade.getpNewsDao().getList(condition, sort);
		} catch (Exception e) {
			throw new Exception("操作失败！");
		}
	}

	@Override
	public List<PNews> getPagingList(Map<String, Object> condition,
			String sort, Integer pageCount, Integer page) throws Exception {
		try {
			return this.daoFacade.getpNewsDao().getPagingList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new Exception("操作失败！");
		}
	}

	@Override
	public Integer getCount(Map<String, Object> condition) throws Exception {
		try {
			return this.daoFacade.getpNewsDao().getCount(condition);
		} catch (Exception e) {
			throw new Exception("操作失败！");
		}
	}

/*	@Override
	public void syncToEPUB(String id) throws Exception {
		try {
			this.daoFacade.getpNewsDao().SYNCTOCNPE(id);
		} catch (Exception e) {
			throw e;
		}
		
	}*/

	
}
