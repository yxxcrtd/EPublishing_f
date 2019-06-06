package cn.digitalpublishing.service;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.BUrl;
import cn.digitalpublishing.ep.po.BUuRelation;
import cn.digitalpublishing.ep.po.CUserType;

public interface CUserTypeService extends BaseService{

	public List<CUserType> getList(Map<String,Object> condition,String sort) throws Exception;
	
	public BUuRelation getuuRelation(Map<String,Object> condition) throws Exception;
	
	public List<BUuRelation> getUuRelationPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer curpage)throws Exception;
	
	public List<BUuRelation> getUuRelationList(Map<String,Object> condition)throws Exception;
	
	public void loadAccessResources(String url)throws Exception;

	public void insertUrl(String url) throws Exception;
	
	public List<BUrl> getUrlPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer curpage)throws Exception;
	
	public Integer getUrlCount(Map<String,Object> condition) throws Exception;
	
	public void updateUrl(BUrl obj,String id,String[] properties)throws Exception;//修改权限路径
	public void deleteUrl(String id) throws Exception; //删除权限路径
	public void insertBUuRelation(BUuRelation obj) throws Exception;
	public void updateBUuRelation(BUuRelation obj) throws Exception;
}
