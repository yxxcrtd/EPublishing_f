package cn.digitalpublishing.service.impl;

import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.exception.CcsException;
import cn.digitalpublishing.ep.po.SOnsale;
import cn.digitalpublishing.service.StatisticService;

public class StatisticServiceImpl extends BaseServiceImpl implements StatisticService {

	@Override
	public void deleteSOnsale(String date) throws Exception {
		// TODO Auto-generated method stub
		try{
			this.daoFacade.getSOnsaleDao().delete(SOnsale.class.getName(), date);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "SOnsale.info.delete.error", e);//删除上线统计信息失败！
		}
	}

	@Override
	public SOnsale getSOnsale(String date) throws Exception {
		// TODO Auto-generated method stub
		SOnsale obj = null;
		try{
			obj=(SOnsale)this.daoFacade.getSOnsaleDao().get(SOnsale.class.getName(), date);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "SOnsale.info.get.error", e);//获取上线统计信息失败！
		}
		return obj;
	}

	@Override
	public Integer getSOnsaleCount(Map<String, Object> condition)throws Exception {
		// TODO Auto-generated method stub
		Integer num = 0;
		try{
			num=this.daoFacade.getSOnsaleDao().getCount(condition);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "SOnsale.total.number.get.error", e);//获取上线统计信息总数失败！
		}
		return num;
	}

	@Override
	public List<SOnsale> getSOnsaleList(Map<String, Object> condition,String sort) throws Exception {
		// TODO Auto-generated method stub
		List<SOnsale> list = null;
		try{
			list=this.daoFacade.getSOnsaleDao().getList(condition, sort);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "SOnsale.list.get.error", e);//获取上线统计信息列表失败！
		}
		return list;
	}

	@Override
	public List<SOnsale> getSOnsalePagingList(Map<String, Object> condition,String sort, int pageCount, int page) throws Exception {
		// TODO Auto-generated method stub
		List<SOnsale> list = null;
		try{
			list=this.daoFacade.getSOnsaleDao().getPagingList(condition, sort, pageCount, page);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "SOnsale.paging.list.get.error", e);//获取上线统计信息分页列表失败！
		}
		return list;
	}

	@Override
	public void insertSOnsale(SOnsale obj) throws Exception {
		// TODO Auto-generated method stub
		try{
			this.daoFacade.getSOnsaleDao().insert(obj);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "SOnsale.info.add.error", e);//新增上线统计信息失败！
		}
	}

	@Override
	public void updateSOnsale(SOnsale obj, String date, String[] properties)throws Exception {
		// TODO Auto-generated method stub
		try{
			this.daoFacade.getSOnsaleDao().update(obj, SOnsale.class.getName(), date, properties);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "SOnsale.info.update.error", e);//更新上线统计信息失败！
		}
	}

	@Override
	public void decreaseSOnsale(String date) throws Exception {
		// TODO Auto-generated method stub
		try{
			SOnsale obj = this.getSOnsale(date);
			if(obj==null){
				obj = new SOnsale();
				obj.setSaleDate(date);
				int num = 0;
				num = num - 1;
				obj.setTotal(num);
				this.insertSOnsale(obj);
			}else{
				int num = obj.getTotal()-1;
				obj.setTotal(num);
				this.updateSOnsale(obj, date, null);
			}
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "SOnsale.info.decrease.error", e);//减少上线统计信息失败！
		}
	}

	@Override
	public void increaseSOnsale(String date) throws Exception {
		// TODO Auto-generated method stub
		try{
			SOnsale obj = this.getSOnsale(date);
			if(obj==null){
				obj = new SOnsale();
				obj.setSaleDate(date);
				int num = 0;
				num = num + 1;
				obj.setTotal(num);
				this.insertSOnsale(obj);
			}else{
				int num = obj.getTotal()+1;
				obj.setTotal(num);
				this.updateSOnsale(obj, date, null);
			}
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "SOnsale.info.increase.error", e);//增加上线统计信息失败！
		}
	}

	@Override
	public Integer getSOnsaleTotal(Map<String, Object> condition)throws Exception {
		Integer num = 0;
		try{
			num=this.daoFacade.getSOnsaleDao().getTotal(condition);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt(): "SOnsale.total.sum.get.error", e);//获取上线统计信息总数失败！
		}
		return num;
	}

}
