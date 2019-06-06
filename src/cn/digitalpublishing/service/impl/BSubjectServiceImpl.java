package cn.digitalpublishing.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.exception.CcsException;
import cn.digitalpublishing.ep.po.BSubject;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.PCsRelation;
import cn.digitalpublishing.service.BSubjectService;

public class BSubjectServiceImpl extends BaseServiceImpl implements BSubjectService {

	public List<BSubject> getSubList(Map<String, Object> condition, String sort)
			throws Exception {
		List<BSubject> list = null;
		try {
			list = this.daoFacade.getbSubjectDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "Subject.info.list.error", e);//获取分类信息失败！
		}
		return list;
	}
	public List<BSubject> getSubList2(Map<String, Object> condition, String sort)
			throws Exception {
		List<BSubject> list = null;
		try {
			list = this.daoFacade.getbSubjectDao().getList2(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "Subject.info.list.error", e);//获取分类信息失败！
		}
		return list;
	}

	public List<PCsRelation> getSubPubPageList(Map<String, Object> condition,
			String sort, int pageCount, int curpage,CUser user,String ip) throws Exception {
		List<PCsRelation> list = null;
		try {
			list = this.daoFacade.getpCsRelationDao().getPagingList(condition,sort,  pageCount, curpage,user,ip);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "Subject.info.Product.page.error", e);//获取分类下产品信息失败！
		}
		return list;
	}

	public int getSubPubCount(Map<String, Object> condition) throws Exception {
		int num = 0;
		try {
			num = this.daoFacade.getpCsRelationDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "Subject.info.Product.count.error", e);//获取分类下产品总数失败！
		}
		return num;
	}

	public List<PCsRelation> getSubPubList(Map<String, Object> condition,
			String sort) throws Exception {
		List<PCsRelation> list = null;
		try {
			list = this.daoFacade.getpCsRelationDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "Subject.info.Product.page.error", e);//获取分类下产品信息失败！
		}
		return list;
	}

	public List<BSubject> getSubColListCount(Map<String, Object> condition,
			String sort) throws Exception {
		List<BSubject> list = null;
		try {
			list = this.daoFacade.getbSubjectDao().getSubColListCount(condition,sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "Subject.info.collection.Product.page.error", e);//获取分类下产品包中产品信息失败！
		}
		return list;
	}

	@Override
	public List<BSubject> getSubOrderListCount(Map<String, Object> condition,
			String sort) throws Exception {
		List<BSubject> list = null;
		try {
			list = this.daoFacade.getbSubjectDao().getSubOrderListCount(condition,sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "Subject.info.list.get.error", e);//获取分类列表总数失败
		}
		return list;
	}

	@Override
	public void updateSubject(BSubject obj, String id, String[] properties)throws Exception {
		try {
			this.daoFacade.getbSubjectDao().update(obj, BSubject.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "Subject.info.update.error", e);//更新分类信息失败！
		}
	}

	@Override
	public void insertSubject(BSubject obj) throws Exception {
		try {
			this.daoFacade.getbSubjectDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt(): "Subject.info.add.error", e);//添加分类信息失败！
		}
	}
	@Override
	public List<BSubject> getStatisticList(Map<String, Object> condition,String sort)throws Exception{
		List<BSubject> list = null;
		try {
			list = this.daoFacade.getbSubjectDao().getStatisticList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt(): "Subject.statisticlist.get.error", e);//获取分类统计信息失败
		}
		return list;
	}
	@Override
	public List<BSubject> getStatisticSubAllList(Map<String, Object> condition,String sort)throws Exception{
		List<BSubject> list = null;
		try {
			list = this.daoFacade.getbSubjectDao().getStatisticAllList(condition,"");
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt(): "Subject.statisticlist.get.error", e);//获取分类统计信息失败
		}
		return list;
	}
	@Override
	public BSubject getSubject(String id)throws Exception{
		BSubject obj = null;
		try{
			obj = (BSubject)this.daoFacade.getbSubjectDao().get(BSubject.class.getName(), id);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt(): "Subject.info.get.error", e);//获取分类信息失败
		}
		return obj;
	}
	
	public BSubject getSubjectByCode(String code)throws Exception{
		BSubject obj = null;
		List<BSubject> list = null;
		try{
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("subCode",code);
			list = this.daoFacade.getbSubjectDao().getList(condition, "");
			if(list!=null&&!list.isEmpty()&&list.size()==1){
				obj = list.get(0);
			}
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt(): "Subject.info.get.error", e);//获取分类信息失败
		}
		return obj;
	}
	
	public List<PCsRelation>  getTops(Map<String,Object> condition,String sort, int pageCount, int curpage,CUser user,String ip)throws Exception{
		List<PCsRelation> list=null;
		try{
			list = this.daoFacade.getpCsRelationDao().getTops(condition, sort, pageCount);			
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt(): "Subject.info.get.error", e);//获取分类信息失败
		}
		return list;
	}
	@Override
	public void deleteSubject(String id) throws Exception {
		try {
			this.daoFacade.getbSubjectDao().delete(BSubject.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt(): "Subject.info.delete.error", e);//删除分类信息失败
		}
	}
	@Override
	public List<BSubject> getSubjectByListCode(String code) throws Exception {
		List<BSubject> list = null;
		try{
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("treeCodeLength",6);
			condition.put("subTypeTOO", 1);//主要分类*/
			list = this.daoFacade.getbSubjectDao().getSubjectByListCode(condition, "");
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt(): "Subject.info.get.error", e);//获取分类信息失败
		}
		return list;
	}
	
	public Integer getCountForAlerts(Map<String,Object> condition)throws Exception{
		int num = 0;
		try {
			num = this.daoFacade.getpCsRelationDao().getCountForAlerts(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "Subject.info.Product.count.error", e);//获取分类下产品总数失败！
		}
		return num;
	}
}
