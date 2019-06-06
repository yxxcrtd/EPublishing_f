package cn.digitalpublishing.service.impl;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.ResourcesSum;
import cn.digitalpublishing.service.ResourcesSumService;

public class ResourcesSumServiceImpl extends BaseServiceImpl implements ResourcesSumService{

	@Override
	public List<ResourcesSum> getList(Map<String, Object> condition, String sort)
			throws Exception {
		List<ResourcesSum> list = null;
		try {
			list = daoFacade.getResourcesSumDao().getList(condition, sort);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List<ResourcesSum> getPagingList(Map<String, Object> condition,
			String sort, Integer pageCount, Integer page) throws Exception {
		List<ResourcesSum> list = null;
		try {
			list= daoFacade.getResourcesSumDao().getPagingList(condition, sort, pageCount, page);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public Integer getCount(Map<String, Object> condition) throws Exception {
		Integer num = 0;
		try {
			num=daoFacade.getResourcesSumDao().getCount(condition);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return num;
	}

	@Override
	public void insertRecommend(ResourcesSum obj) throws Exception {
		try {
			this.daoFacade.getResourcesSumDao().insert(obj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public void updateResourcesSum(ResourcesSum obj, String id, String[] properties)
			throws Exception {
		try {
			this.daoFacade.getResourcesSumDao().update(obj, ResourcesSum.class.getName(), id, properties);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	

}
