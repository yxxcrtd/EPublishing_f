package cn.digitalpublishing.service;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.PNews;

public interface NewsService extends BaseService{
	
	public void insert(PNews obj) throws Exception;
	public void delete(String id) throws Exception;
	public void update(PNews obj,String id,String[] properties) throws Exception;
	public PNews getById(String id) throws Exception;
	public List<PNews> getList(Map<String,Object> condition,String sort) throws Exception;
	public List<PNews> getPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer page) throws Exception;
	public Integer getCount(Map<String,Object> condition) throws Exception;
	//public void syncToEPUB(String id)throws Exception;
}
