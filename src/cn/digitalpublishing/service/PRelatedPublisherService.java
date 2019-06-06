package cn.digitalpublishing.service;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.PRelatedPublisher;

public interface PRelatedPublisherService extends BaseService{
	
	public void insert(PRelatedPublisher obj) throws Exception;
	public void delete(String id) throws Exception;
	public void update(PRelatedPublisher obj,String id,String[] properties) throws Exception;
	public PRelatedPublisher getById(String id) throws Exception;
	public List<PRelatedPublisher> getList(Map<String,Object> condition,String sort) throws Exception;
	public List<PRelatedPublisher> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page) throws Exception;
	public Integer getCount(Map<String,Object> condition) throws Exception;
    public void syncToEPUB(String id)throws Exception;
}
