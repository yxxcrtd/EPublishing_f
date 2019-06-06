package cn.digitalpublishing.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.exception.CcsException;
import cn.digitalpublishing.ep.po.CUserProp;
import cn.digitalpublishing.service.CUserPropService;


public class CUserPropServiceImpl extends BaseServiceImpl implements CUserPropService {
	
	
	
	@Override
	public List<CUserProp> getCUserPropList (Map<String, Object> condition, String sort) throws Exception {
		
		try {
			return this.daoFacade.getCUserPropDao().getCUserPropList(condition);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "user.list.get.error", e);//获取用户属性信息列表失败
		}
		
	}

	@Override
	public CUserProp getCUserProp(String id) throws Exception {
		
		try {
			return (CUserProp) this.daoFacade.getCUserPropDao().get(CUserProp.class.getName(), id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "CUserProp.get.error",e);//获取用户属性信息失败
		}
	}

	@Override
	public void updateCUserProp(Object obj,String className,Serializable id,String[] properties) throws Exception {
		try {
			this.daoFacade.getCUserPropDao().update(obj, className, id, properties);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "CUserProp.updateCUserProp.error", e);
		}
	}

	@Override
	public void insertCUserProp(Object obj) throws Exception {
		try {
			this.daoFacade.getCUserPropDao().insert(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "CUserProp.insertCUserProp.error", e);
		}
	}
	
}
