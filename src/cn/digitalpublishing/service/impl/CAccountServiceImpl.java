package cn.digitalpublishing.service.impl;

import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.exception.CcsException;
import cn.digitalpublishing.ep.po.CAccount;
import cn.digitalpublishing.service.CAccountService;

public class CAccountServiceImpl extends BaseServiceImpl implements CAccountService {

	@Override
	public List<CAccount> getList(Map<String, Object> condition, String sort) throws Exception {
		List<CAccount> list = null;
		try {
			list = this.daoFacade.getcAccountDao().getList(condition, sort);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "CAccount.getList.error", e);
		}
		return list;
	}

}
