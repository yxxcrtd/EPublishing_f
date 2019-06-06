package cn.digitalpublishing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.digitalpublishing.facade.DaoFacade;
import cn.digitalpublishing.remote.service.RestService;
import cn.digitalpublishing.search.PagesIndexService;
import cn.digitalpublishing.service.BaseService;

public class BaseServiceImpl implements BaseService{
	
	protected DaoFacade daoFacade;
	@Autowired
    @Qualifier("pagesIndexService")
    protected PagesIndexService pagesIndexService;
	@Autowired
    @Qualifier("restService")
    protected RestService restService;

	public DaoFacade getDaoFacade() {
		return daoFacade;
	}

	public void setDaoFacade(DaoFacade daoFacade) {
		this.daoFacade = daoFacade;
	}
}
