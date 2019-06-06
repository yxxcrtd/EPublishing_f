package cn.digitalpublishing.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.exception.CcsException;
import cn.digitalpublishing.ep.po.BUrl;
import cn.digitalpublishing.ep.po.BUuRelation;
import cn.digitalpublishing.ep.po.CUserType;
import cn.digitalpublishing.service.CUserTypeService;
import cn.digitalpublishing.util.bean.Access;

public class CUserTypeServiceImpl extends BaseServiceImpl implements CUserTypeService {

	@Override
	public List<CUserType> getList(Map<String, Object> condition, String sort) throws Exception {
	
		List<CUserType> list = null;
		
		try {
			list = this.daoFacade.getCUserTypeDao().getList(condition, sort);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "CUserType.getList.error", e);
		}
		
		return list;
		
	}
	
	@Override
	public BUuRelation getuuRelation(Map<String,Object> condition) throws Exception{
		BUuRelation relation=null;
		try{
			List<BUuRelation> list=this.daoFacade.getUuRelationDao().getList(condition, "");
			if(list!=null && list.size()>0){
				relation=list.get(0);
			}
		} catch (Exception e) {			
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "UURelation.info.get.error", e);
		}
		return relation;
	}
	
	@Override
	public List<BUuRelation> getUuRelationList(Map<String,Object> condition)throws Exception{
		List<BUuRelation> list=null;
		try{
			list=this.daoFacade.getUuRelationDao().getList(condition, "");			
		} catch (Exception e) {			
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "UURelation.list.get.error", e);
		}
		return list;
	}

	public List<BUuRelation> getUuRelationPagingList(Map<String,Object> condition,String sort,Integer pageCount,Integer curpage)throws Exception{
		List<BUuRelation> list=null;
		try{
			list=this.daoFacade.getUuRelationDao().getPagingList(condition, sort, pageCount, curpage);			
		} catch (Exception e) {			
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	@Override
	public void loadAccessResources(String url) throws Exception {
		// TODO Auto-generated method stub
		try{
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("url", url);
			List<BUuRelation> list = this.getUuRelationList(condition);
			if(list!=null&&!list.isEmpty()){
//				System.out.println("list's size is " + list.size());
				for(BUuRelation uu:list){
					if(!Access.getResource().containsKey(uu.getUrl().getUrl())){
						Map<String,Boolean> map = new HashMap<String,Boolean>();
						Access.getResource().put(uu.getUrl().getUrl(), map);
					}
					Access.getResource().get(uu.getUrl().getUrl()).put(uu.getUserType().getId(), uu.getAccess()==1?false:true);
				}
			}
		}catch (Exception e){
			throw e;
		}
		
	}
	
	@Override
	public void insertUrl(String url) throws Exception{		
		try{
			Map<String,Object> condition=new HashMap<String,Object>();
			condition.put("url", url);
			Integer c=this.daoFacade.getUrlDao().getCount(condition);
			if(c==0){
				BUrl u=new  BUrl();
				u.setUrl(url);
				this.daoFacade.getUrlDao().insert(u);
			}
		} catch (Exception e) {			
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "UURelation.info.get.error", e);
		}		
	}

	@Override
	/**
	 * 查询用户下所具有的权限功能路径
	 */
	public List<BUrl> getUrlPagingList(Map<String, Object> condition,
			String sort, Integer pageCount, Integer curpage) throws Exception {
		List<BUrl> list=null;
		try{
			list=this.daoFacade.getUrlDao().getPagingList(condition, sort, pageCount, curpage);			
		} catch (Exception e) {			
			e.printStackTrace();
			throw e;
		}
		return list;	
	}
	
	public Integer getUrlCount(Map<String,Object> condition) throws Exception{
		Integer result=0;
		try{
			result= this.daoFacade.getUrlDao().getCount(condition);			
		} catch (Exception e) {			
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	//修改 权限路径
	public void updateUrl(BUrl obj, String id, String[] properties)
			throws Exception {
		try {
			this.daoFacade.getUrlDao().update(obj, BUrl.class.getName(), id, properties);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	//删除权限路径
	public void deleteUrl(String id) throws Exception {
		try {
			this.daoFacade.getUrlDao().delete(BUrl.class.getName(), id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	//添加权限路径

	@Override
	public void insertBUuRelation(BUuRelation obj) throws Exception {
		// TODO Auto-generated method stub
		try {
			this.daoFacade.getUuRelationDao().insert(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public void updateBUuRelation(BUuRelation obj) throws Exception{
		try {
			this.daoFacade.getUuRelationDao().update(obj, BUuRelation.class.getName(), obj.getId(), null);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
