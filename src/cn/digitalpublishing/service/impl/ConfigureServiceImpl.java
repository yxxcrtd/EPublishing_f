package cn.digitalpublishing.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.exception.CcsException;
import cn.digitalpublishing.ep.po.BConfiguration;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.BIpRange;
import cn.digitalpublishing.ep.po.BPublisher;
import cn.digitalpublishing.ep.po.BToken;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.CUserProp;
import cn.digitalpublishing.ep.po.CUserType;
import cn.digitalpublishing.ep.po.CUserTypeProp;
import cn.digitalpublishing.ep.po.LUserAlertsLog;
import cn.digitalpublishing.ep.po.MMarkData;
import cn.digitalpublishing.ep.po.PPage;
import cn.digitalpublishing.ep.po.PPriceType;
import cn.digitalpublishing.po.BSource;
import cn.digitalpublishing.service.ConfigureService;

public class ConfigureServiceImpl extends BaseServiceImpl implements ConfigureService{	
	
	/**
	 * -------------------------------------------出版商管理-------------------------------------------
	 */

	public BPublisher getPublisher(String id) throws Exception {
		// TODO Auto-generated method stub
		BPublisher obj = null;
		try{
			obj = (BPublisher)this.daoFacade.getbPublisherDao().get(BPublisher.class.getName(), id);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "publisher.info.get.error", e);//获取出版商信息失败！
		}
		return obj;
	}
	
	public void insertPublisher(BPublisher obj)throws Exception{
		try{
			this.daoFacade.getbPublisherDao().insert(obj);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "publisher.info.insert.error", e);//获取出版商信息失败！
		}
	}
	
	public void updatePublisher(BPublisher obj,String id,String[] properties)throws Exception{
		try{
			this.daoFacade.getbPublisherDao().update(obj,BPublisher.class.getName(),id, properties);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "publisher.info.update.error", e);//获取出版商信息失败！
		}
	}

	public Integer getPublisherCount(Map<String, Object> condition)throws Exception {
		// TODO Auto-generated method stub
		Integer num = 0;
		try{
			num = this.daoFacade.getbPublisherDao().getCount(condition);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "publisher.count.get.error", e);//获取出版商信息总数失败！
		}
		return num;
	}

	public List<BPublisher> getPublisherList(Map<String, Object> condition,String sort) throws Exception {
		// TODO Auto-generated method stub
		List<BPublisher> list = null;
		try{
			list = this.daoFacade.getbPublisherDao().getList(condition, sort);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "publisher.list.get.error", e);//获取出版商信息列表失败！
		}
		return list;
	}

	public List<BPublisher> getPublisherPagingList(Map<String, Object> condition, String sort, Integer pageCount,Integer page) throws Exception {
		// TODO Auto-generated method stub
		List<BPublisher> list = null;
		try{
			list = this.daoFacade.getbPublisherDao().getPagingList(condition, sort, pageCount, page);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt()	: "publisher.paginglist.get.error", e);//获取出版商信息分页列表失败！
		}
		return list;
	}

	/* *
	 * (non-Javadoc)
	 * IP范围列表查询
	 * @see cn.digitalpublishing.service.ConfigureService#getIpRangeList(java.util.Map, java.lang.String)
	 */
	@Override
	public List<cn.digitalpublishing.ep.po.BIpRange> getIpRangeList(Map<String, Object> condition, String sort)
			throws Exception {
		List<cn.digitalpublishing.ep.po.BIpRange> list = null;
		try {
			list = this.daoFacade.getbIpRangeDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt()	: "ipRange.list.get.error", e);//获取Ip范围信息分页列表失败！
		}
		return list;
	}

	@Override
	public void updateIpRange(BIpRange obj, String id, String[] properties)
			throws Exception {
		try {
			this.daoFacade.getbIpRangeDao().update(obj,BIpRange.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt()	: "IpRange.list.update.error", e);//更新Ip范围信息分页列表失败！
		}
	}

	@Override
	public void insertIpRange(BIpRange obj) throws Exception {
		try {
			this.daoFacade.getbIpRangeDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt()	: "IpRange.list.add.error", e);//新增Ip范围信息分页列表失败！
		}
	}
	
	@Override
	public void deleteIpRange(String id) throws Exception {
		try {
			this.daoFacade.getbIpRangeDao().delete(BIpRange.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt()	: "IpRange.list.delete.error", e);//删除Ip范围信息分页列表失败！
		}
	}
	
	@Override
	public BIpRange getIpRange(String id) throws Exception{
		BIpRange obj = null;
		try{
			Object o = this.daoFacade.getbIpRangeDao().get(BIpRange.class.getName(), id);
			if(o!=null){
				obj=(BIpRange)o;
			}
		}catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt()	: "ipRange.list.get.error", e);//获取Ip范围信息分页列表失败！
		}
		return obj;
	}

	@Override
	public void updateInstitution(BInstitution obj, String id,
			String[] properties) throws Exception {
		try {
			this.daoFacade.getbInstitutionDao().update(obj, BInstitution.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "Institution.info.update.error", e);//更新机构信息失败！
		}
	}
	
	@Override
	public BInstitution getInstitution(String id)throws Exception{
		BInstitution obj = null;
		try{
			obj = (BInstitution)this.daoFacade.getbInstitutionDao().get(BInstitution.class.getName(), id);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "Institution.info.get.error", e);//获取机构信息失败！
		}
		return obj;
	}
	
	@Override
	public void insertInstitution(BInstitution obj) throws Exception {
		try {
			this.daoFacade.getbInstitutionDao().insert(obj);
			CUser user=new CUser();
			user.setName(obj.getName()+"(默认用户)");
			user.setInstitution(obj);
			user.setLevel(3);
			user.setUserType(new CUserType());
			user.getUserType().setId("3");//默认用户类型的ID为3
			user.setStatus(1);
			user.setCreatedon(new Date());
			user.setUpdatedon(new Date());
			this.daoFacade.getcUserDao().insert(user);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "Institution.info.add.error", e);//插入机构信息失败！
		}
	}
	
	@Override
	public void deleteInstitution(String id) throws Exception{
		try {
			Map<String,Object> condition =new HashMap<String,Object>();
			condition.put("institutionId", id);
			condition.put("notlevel", 3);
			if(this.daoFacade.getcUserDao().getCount(condition)>0){
				throw new CcsException("institution.info.delete.error.userexist");
			}
			try{
				Map<String,Object> uCondition =new HashMap<String,Object>();
				uCondition.put("institutionId", id);
				uCondition.put("level", 3);
				List<CUser> userList=this.daoFacade.getcUserDao().getList(uCondition, "");
				if(userList!=null && !userList.isEmpty()){
					this.daoFacade.getcUserDao().delete(CUser.class.getName(), userList.get(0).getId());
				}
			}catch(Exception e){
				throw new CcsException("institution.info.delete.error.userexist");
			}
			this.daoFacade.getbInstitutionDao().delete(BInstitution.class.getName(), id);
			
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "Institution.info.delete.error", e);//删除机构信息失败！
		}
	}

	@Override
	public void updatePage(PPage obj, String id, String[] properties)
			throws Exception {
		try {
			this.daoFacade.getpPageDao().update(obj,PPage.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "page.info.update.error", e);//更新页信息失败
		}
	}

	@Override
	public void insertPage(PPage obj) throws Exception {
		try {
			this.daoFacade.getpPageDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "page.info.add.error", e);//新增页信息失败
		}
	}

	@Override
	public void deletePage(Map<String, Object> condition) throws Exception {
		try {
			this.daoFacade.getpPageDao().deleteByCondition(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "page.info.delete.error", e);//删除页信息失败
		}
	}

	@Override
	public List<BConfiguration> getConfigurePagingList(
			Map<String, Object> condition, String sort, int pageCount,
			int curpage) throws Exception {
		List<BConfiguration> list = null;
		try {
			list = this.daoFacade.getbConfigurationDao().getPagingList(condition, sort, pageCount, curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "conf.paginglist.get.error", e);//获取设置信息分页列表失败
		}
		return list;
	}

	@Override
	public int getConfigureCount(Map<String, Object> condition)
			throws Exception {
		int num = 0;
		try {
			num = this.daoFacade.getbConfigurationDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "conf.count.get.error", e);//获取设置信息总数失败
		}
		return num;
	}

	@Override
	public BConfiguration getConfigure(String id) throws Exception {
		BConfiguration obj = null;
		try {
			obj = (BConfiguration)this.daoFacade.getbConfigurationDao().get(BConfiguration.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "conf.info.get.error", e);//获取设置信息失败
		}
		return obj;
	}

	@Override
	public void updateConfigure(BConfiguration obj, String id,
			String[] properties) throws Exception {
		try {
			this.daoFacade.getbConfigurationDao().update(obj, BConfiguration.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "conf.info.update.error", e);//更新设置信息失败
		}
	}

	@Override
	public void addConfigure(BConfiguration obj) throws Exception {
		try {
			this.daoFacade.getbConfigurationDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "conf.info.add.error", e);//添加设置信息失败
		}
	}

	@Override
	public List<BConfiguration> getConfigureList(Map<String, Object> condition,
			String sort) throws Exception {
		List<BConfiguration> list = null;
		try {
			list = this.daoFacade.getbConfigurationDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "conf.list.add.error", e);//获取设置信息列表失败
		}
		return list;
	}

	@Override
	public void updateMarkData(MMarkData obj, String id, String[] properties)
			throws Exception {
		try {
			this.daoFacade.getMMarkDataDao().update(obj, MMarkData.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "conf.mark.info.updata.error", e);//更新Mark数据失败！
		}
	}

	@Override
	public void insertMarkData(MMarkData obj) throws Exception {
		try {
			this.daoFacade.getMMarkDataDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "conf.mark.info.add.error", e);//添加Mark数据失败！
		}
	}

	@Override
	public List<BInstitution> getInstitutionList(Map<String, Object> condition,
			String sort) throws Exception {
		List<BInstitution> list = null;
		try {
			list = this.daoFacade.getbInstitutionDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "conf.Institution.info.list.error", e);//获取机构信息列表时失败！
		}
		return list;
	}

	@Override
	public int getMarkDataCount(Map<String, Object> condition) throws Exception {
		int num = 0;
		try {
			num = this.daoFacade.getMMarkDataDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "conf.mark.info.count.error", e);//获取Mark数据信息总数时失败！
		}
		return num;
	}

	@Override
	public List<MMarkData> getMarkDataPagingList(Map<String, Object> condition,
			String sort, int pageCount, int curpage) throws Exception {
		List<MMarkData> list = null;
		try {
			list = this.daoFacade.getMMarkDataDao().getPagingList(condition,sort,pageCount,curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "conf.mark.info.page.list.error", e);//获取Mark数据信息分页列表时失败！
		}
		return list;
	}

	@Override
	public MMarkData getMarkData(String id) throws Exception {
		MMarkData obj = null;
		try {
			obj = (MMarkData)this.daoFacade.getMMarkDataDao().get(MMarkData.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "conf.mark.info.get.error", e);//获取Mark数据信息时失败！
		}
		return obj;
	}

	@Override
	public void deleteMarkData(String id) throws Exception {
		try {
			this.daoFacade.getMMarkDataDao().delete(MMarkData.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "conf.mark.info.delete.error", e);//删除Mark数据信息时失败！
		}
	}

	@Override
	public List<BPublisher> getPublisherStatistic(Map<String, Object> condition)
			throws Exception {
		List<BPublisher> list = null;
		try {
			list = this.daoFacade.getbPublisherDao().getPublisherStatistic(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "publisher.list.get.error", e);//删除Mark数据信息时失败！
		}
		return list;
	}
	/**
	 * -------------------------------------------价格类型信息管理-------------------------------------------
	 */
	
	public void insertPriceType(PPriceType obj) throws Exception {
		// TODO Auto-generated method stub
		try{
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("code",obj.getCode());
			//实现禁止新增的情况
//			if(this.daoFacade.getPriceTypeDao().getCount(condition)>0){
//				throw new CcsException("platform.info.unique.add.error");
//			}
			this.daoFacade.getPriceTypeDao().insert(obj);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt():"pricetype.info.add.error", e);//新增价格类型信息失败！
		}
		
	}

	public void deletePriceType(String id) throws Exception {
		// TODO Auto-generated method stub
		try{
//			Map<String,Object> condition = new HashMap<String,Object>();
//			condition.put("pricetypeId",id);
			//实现是否允许删除的判断
			this.daoFacade.getPriceTypeDao().delete(PPriceType.class.getName(), id);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "pricetype.info.delete.error", e);//删除价格类型信息失败！
		}
	}

	public PPriceType getPriceType(String id) throws Exception {
		// TODO Auto-generated method stub
		PPriceType obj = null;
		try{
			obj = (PPriceType)this.daoFacade.getPriceTypeDao().get(PPriceType.class.getName(), id);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "pricetype.info.get.error", e);//获取价格类型信息失败！
		}
		return obj;
	}

	public Integer getPriceTypeCount(Map<String, Object> condition) throws Exception {
		// TODO Auto-generated method stub
		Integer num = 0;
		try{
			num = this.daoFacade.getPriceTypeDao().getCount(condition);
		}catch(Exception e){
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "pricetype.count.get.error", e);//获取价格类型信息总数失败！
		}
		return num;
	}

	public List<PPriceType> getPriceTypeList(Map<String, Object> condition,String sort) throws Exception {
		// TODO Auto-generated method stub
		List<PPriceType> list = null;
		try{
			list = this.daoFacade.getPriceTypeDao().getList(condition, sort);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "pricetype.list.get.error", e);//获取价格类型信息列表失败！
		}
		return list;
	}

	public List<PPriceType> getPriceTypePagingList(Map<String, Object> condition,String sort, Integer pageCount, Integer page) throws Exception {
		// TODO Auto-generated method stub
		List<PPriceType> list = null;
		try{
			list = this.daoFacade.getPriceTypeDao().getPagingList(condition, sort, pageCount, page);
		}catch(Exception e){
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt()	: "pricetype.paginglist.get.error", e);//获取价格类型信息分页列表失败！
		}
		return list;
	}

	public void updatePriceType(PPriceType obj, String id, String[] properties)throws Exception {
		// TODO Auto-generated method stub
		try{
//			Map<String,Object> condition = new HashMap<String,Object>();
//			condition.put("code",obj.getCode());
//			condition.put("uniqueId",obj.getId());
//			//什么情况禁止更新价格类型信息
//			if(this.daoFacade.getPriceTypeDao().getCount(condition)>0){
//				throw new CcsException("pricetype.info.unique.update.error");
//			}
			this.daoFacade.getPriceTypeDao().update(obj, PPriceType.class.getName(), id, properties);
		}catch(Exception e){
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "pricetype.info.update.error", e);//价格类型信息更新成功！
		}
	}
	
	public List<CUserType> getUserTypeList(Map<String, Object> condition, String sort)throws Exception{
		List<CUserType> list = null;
		try{
			list = this.daoFacade.getCUserTypeDao().getList(condition, sort);
		}catch(Exception e){
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt()	: "pricetype.paginglist.get.error", e);//获取价格类型信息分页列表失败！
		}
		return list;
	}
	
	public CUserType getUserType(Map<String, Object> condition)throws Exception{
		CUserType c = null;
		try{
			 c = this.daoFacade.getCUserTypeDao().getList(condition, null).get(0);
		}catch(Exception e){
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt()	: "pricetype.paginglist.get.error", e);//获取价格类型信息分页列表失败！
		}
		return c;
	}
	public List<CUserTypeProp> getUserTypeProp(Map<String, Object> condition, String sort)throws Exception{
		List<CUserTypeProp> list = null;
		try{
			list = this.daoFacade.getcUserTypePropDao().getList(condition, sort);
		}catch(Exception e){
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt()	: "pricetype.paginglist.get.error", e);//获取价格类型信息分页列表失败！
		}
		return list;
	}
	
	public void updateUserTypeProp(CUserTypeProp obj, String id, String[] properties)throws Exception {
		// TODO Auto-generated method stub
		try{
			this.daoFacade.getcUserTypePropDao().update(obj, CUserTypeProp.class.getName(), id, properties);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "pricetype.info.update.error", e);//价格类型信息更新成功！
		}
	}
	
	public void addUserTypeProp(CUserTypeProp obj)throws Exception{
		try{
			this.daoFacade.getcUserTypePropDao().insert(obj);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt():"pricetype.info.add.error", e);//新增价格类型信息失败！
		}
	}
	
	public void deleteUserTypeProp(String id)throws Exception{
		try{
			this.daoFacade.getcUserTypePropDao().delete(CUserTypeProp.class.getName(), id);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt():"pricetype.info.add.error", e);//新增价格类型信息失败！
		}
	}
	
	public void batchSaveRelation(String[] orders,String[] ids)
			throws Exception {
		try{
			if(ids!=null&&ids.length>0){
				for(int i=0;i<ids.length;i++){
					if(!"0".equals(ids[i])){
						Map<String,Object> condition = new HashMap<String,Object>();
						condition.put("aid", ids[i]);
						CUserTypeProp prop = this.daoFacade.getcUserTypePropDao().getList(condition, null).get(0);
						prop.setOrder(Integer.valueOf(orders[i]));
						this.daoFacade.getcUserTypePropDao().update(prop, CUserTypeProp.class.getName(), ids[i], null);
					}
				}
			}else{
				throw new CcsException("Subject.relation.not.exist");//不存在分类法信息
			}
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "Subject.relation.batch.save.error", e);//批量保存分类法映射关系信息失败！
		}
	}
	
	public List<CUserProp> getCUserProp(Map<String, Object> condition)throws Exception{
		List<CUserProp> list = null;
		try{
			list = this.daoFacade.getCUserPropDao().getCUserPropList(condition);
		}catch(Exception e){
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ?((CcsException)e).getPrompt()	: "pricetype.paginglist.get.error", e);//获取价格类型信息分页列表失败！
		}
		return list;
	}
	
	public void updateUserType(CUserType obj,String id,String[] properties)throws Exception{
		try{
			this.daoFacade.getcUserTypeDao().update(obj, CUserType.class.getName(), id, properties);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "pricetype.info.update.error", e);//价格类型信息更新成功！
		}
	}

	@Override
	public void addUserAlertsLog(LUserAlertsLog obj) throws Exception {
		try {
			this.daoFacade.getlUserAlertsLogDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "UserAlertsLog.info.save.error", e);//订阅提醒信息保存失败！
		}
	}
	public BSource getBSource(String id) throws Exception {
		BSource obj = null;
		try{
			obj = (BSource)this.daoFacade.getbSourceDao().get(BSource.class.getName(), id);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt():"bsource.info.get.error", e);//获取来源信息失败！
		}
		return obj;
	}
	public List<BSource> getBSourceList(Map<String, Object> condition, String sort) throws Exception {
		List<BSource> list = null;
		try{
			list = this.daoFacade.getbSourceDao().getList(condition, sort);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt():"bsource.info.list.error", e);//获取来源列表信息失败！
		}
		return list;
	}
	public BInstitution getBInstitution(String id) throws Exception {
		BInstitution obj = null;
		try{
			obj = (BInstitution)this.daoFacade.getbInstitutionDao().get(BInstitution.class.getName(), id);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt():"binstitution.info.get.error", e);//获取来源信息失败！
		}
		return obj;
	}
	/**
	 * 生成一个Token
	 * @param type
	 * @param userId
	 * @throws Exception
	 */
	public BToken createToken(int type,String userId)throws Exception{
		BToken token=null;
		try{
			token=new BToken();
			token.setCreateOn(new Date());
			token.setStatus(BToken.STATUS_OPEN);
			token.setType(type);
			token.setUserId(userId);
			this.daoFacade.getbTokenDao().insert(token);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return token;
	}
	/**
	 * 尝试获取一个有效Token
	 * @param tokenId
	 * @Param type	类型 1 找回密码
	 * @return
	 * @throws Exception
	 */
	public BToken getVaildToken(String tokenId,int type)throws Exception{
		BToken token=null;
		try{
			if(tokenId!=null && !"".equals(tokenId)){
				Map<String,Object> condition=new HashMap<String, Object>();
				condition.put("id",tokenId);
				condition.put("type", type);
				condition.put("status", BToken.STATUS_OPEN);
				condition.put("withinMinutes", 60);//60分钟失效
				token=this.daoFacade.getbTokenDao().getToken(condition, "");
			}			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return token;
	}
	/**
	 * 将一个Token状态设置为失效
	 * @param tokenId
	 * @throws Exception
	 */
	public void disableToken(String tokenId)throws Exception{
		try{
			BToken token = (BToken)this.daoFacade.getbTokenDao().get(BToken.class.getName(),tokenId);
			if(token!=null){
				token.setStatus(BToken.STATUS_CLOSE);
				this.daoFacade.getbTokenDao().update(token, BToken.class.getName(), tokenId, null);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Integer getIpCount(Map<String, Object> conds) throws Exception {
		// TODO Auto-generated method stub
		Integer num = 0;
		try {
			num = this.daoFacade.getbIpRangeDao().getCount(conds);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return num;
	}
}	
	
