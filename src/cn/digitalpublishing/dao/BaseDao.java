package cn.digitalpublishing.dao;

import java.io.Serializable;
import cn.com.daxtech.framework.orm.hibernate3.dao.IHibernateDAO;

public class BaseDao<T, ID extends Serializable> {

	protected IHibernateDAO<T, ID> hibernateDao;

	public IHibernateDAO<T, ID> getHibernateDao() {
		return hibernateDao;
	}

	public void setHibernateDao(IHibernateDAO<T, ID> hibernateDao) {
		this.hibernateDao = hibernateDao;
	}
	
}