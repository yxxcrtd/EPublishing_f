package cn.digitalpublishing.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.exception.CcsException;
import cn.digitalpublishing.ep.po.CUserTypeProp;
import cn.digitalpublishing.service.CUserTypePropService;

public class CUserTypePropServiceImpl extends BaseServiceImpl implements CUserTypePropService {

	@Override
	public List<CUserTypeProp> getList(Map<String, Object> condition, String sort) throws Exception {
		
		List<CUserTypeProp>  list = null ; 
	
		try {
			list = this.daoFacade.getcUserTypePropDao().getList(condition, sort);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "CUserTypeProp.getList.error", e);
		}
		
		return list ;
		
		
	}

	@Override
	public CUserTypeProp getCUserTypeProp(String id) throws Exception {
		
		try {
			return (CUserTypeProp) this.daoFacade.getcUserTypePropDao().get(CUserTypeProp.class.getName(), id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "CUserTypeProp.getCUserTypeProp.error", e);
		}
	}

	@Override
	public void updateCUserTypeProp(Object obj, String className, Serializable id, String[] properties) throws Exception {
		
		try {
			this.daoFacade.getcUserTypePropDao().update(obj, className, id, properties);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "CUserTypeProp.updateCUserTypeProp.error", e);
		}
	}

}
