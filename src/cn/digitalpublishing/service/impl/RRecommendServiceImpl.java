package cn.digitalpublishing.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.digitalpublishing.ep.po.RRecommend;
import cn.digitalpublishing.ep.po.RRecommendDetail;
import cn.digitalpublishing.service.RRecommendService;

public class RRecommendServiceImpl extends BaseServiceImpl implements RRecommendService {


	public List<RRecommend> getRecommendList(Map<String, Object> condition, String sort)throws Exception {
		List<RRecommend> list = null;
		try {
			list = this.daoFacade.getrRecommendDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "recommend.list.get.error", e);//获取推荐信息列表失败！
		}
		return list;
	}
	public  List<RRecommend> getRecommendPagingList(Map<String, Object> condition, String sort,Integer pageCount, Integer page)throws Exception{
		List<RRecommend> list = null;
		try {
			list = this.daoFacade.getrRecommendDao().getPagingList(condition, sort,pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "recommend.paginglist.get.error", e);//获取推荐信息分页列表失败！
		}
		return list;
	}
	public void insertRecommend(RRecommend recommend,RRecommendDetail detail,Map<String,Object> condition)throws Exception{
		try{
			List<RRecommend> list=this.getRecommendList(condition, "");
			String rid;
			if(list==null || list.size()<=0){//没有相同且isOrder为1的荐购信息
				this.daoFacade.getrRecommendDao().insert(recommend);
				detail.setRecommend(recommend);
			}else{
				list.get(0).setRecommendDetailCount(list.get(0).getRecommendDetailCount()+1);
				list.get(0).setProCount(list.get(0).getProCount()+recommend.getProCount());
				this.daoFacade.getrRecommendDao().update(list.get(0),RRecommend.class.getName(),list.get(0).getId(),null);
				detail.setRecommend(list.get(0));
			}
			this.daoFacade.getrRecommendDetailDao().insert(detail);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "recommend.info.add.error", e);//新增推荐信息失败！
		}
	}
	public  List<RRecommendDetail> getRecommendDetailPagingList(Map<String, Object> condition, String sort,Integer pageCount, Integer page)throws Exception{
		List<RRecommendDetail> list = null;
		try {
			list = this.daoFacade.getrRecommendDetailDao().getPagingList(condition, sort,pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "recommend.detail.paginglist.get.error", e);//新增推荐信息失败！
		}
		return list;
	}
	public List<RRecommendDetail> getRecommendDetailList(Map<String,Object> condition) throws Exception{
		List<RRecommendDetail> list = null;
		try {
			list = this.daoFacade.getrRecommendDetailDao().getList(condition, "");
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "recommend.detail.list.get.error", e);//新增推荐信息失败！
		}
		return list;
	}
	@Override
	public Integer getRecommendDetailCount(Map<String, Object> condition)
			throws Exception {
		Integer num = 0;
		try {
			num = this.daoFacade.getrRecommendDetailDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "recommend.detail.info.count.error", e);//获取推荐详情信息总数失败！
		}
		return num;
	}
	@Override
	public Integer getRecommendCount(Map<String, Object> condition)	throws Exception {
		Integer num = 0;
		try {
			num = this.daoFacade.getrRecommendDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "recommend.info.count.error", e);//获取推荐信息总数失败！
		}
		return num;
	}
	
	public void suspendRecommend(String recommendId,String  particulars)throws Exception{
		try{
			Map<String,Object> condition= new HashMap<String,Object>();
			condition.put("rid",recommendId );
			List<RRecommendDetail> detailList=this.daoFacade.getrRecommendDetailDao().getParticularsList(condition, "");
			if(detailList!=null && detailList.size()>0){
				for(RRecommendDetail detail:detailList){
					detail.setParticulars(particulars);
					this.daoFacade.getrRecommendDetailDao().update(detail, RRecommendDetail.class.getName(), detail.getId(), null);
				}
			}			
			RRecommend recommend=(RRecommend)this.daoFacade.getrRecommendDao().get(RRecommend.class.getName(), recommendId);
			recommend.setIsOrder(3);
			recommend.setParticulars(particulars);
			this.daoFacade.getrRecommendDao().update(recommend, RRecommend.class.getName(), recommendId, null);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "Global.Prompt.set.error", e);//设置失败
		}
	}
	@Override
	public List<RRecommendDetail> getParticularsList(
			Map<String, Object> condition, String sort) throws Exception {
		List<RRecommendDetail> list = null;
		try {
			list = this.daoFacade.getrRecommendDetailDao().getParticularsList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "recommend.detail.paginglist.get.error", e);//新增推荐信息失败！
		}
		return list;
	}
}
