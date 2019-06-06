package cn.digitalpublishing.service.impl;

import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.exception.CcsException;
import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.ep.po.RRecommendDetail;
import cn.digitalpublishing.service.LogAOPService;


public class LogAOPServiceImpl extends BaseServiceImpl implements LogAOPService {

	public void addLog(LAccess access)throws Exception{
		try{
			this.daoFacade.getlAccessDao().insert(access);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.add.error", e);//写入日至信息失败！
		}
	}
	public List<LAccess> getLogOfYear(Map<String,Object> condition,String sort)throws Exception{
		try{			
			List<LAccess> list=this.daoFacade.getlAccessDao().getCounterList(condition, sort);
			return list;
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
	}
	public List<LAccess> getLogOfYearPaging(Map<String,Object> condition,String sort,int pageCount, int curpage)throws Exception{
		try{			
			List<LAccess> list=this.daoFacade.getlAccessDao().getCounterPagingList(condition, sort,pageCount,curpage);
			return list;
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
	}
	@Override
	public List<LAccess> getLogOfYearToSearch(Map<String, Object> condition,
			String sort) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterListToSearch(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	public List<LAccess> getLogOfYearToSearchPaging(Map<String, Object> condition,String sort,int pageCount, int curpage)throws Exception{
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterPagingListToSearch(condition, sort,pageCount,curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	@Override
	public List<LAccess> getLogOfYearToPage(Map<String, Object> condition,
			String sort) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterListToPage(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	public List<LAccess> getLogOfYearToPagePaging(Map<String, Object> condition,String sort,int pageCount,int curpage) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterPagingListToPage(condition, sort,pageCount,curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	@Override
	public List<LAccess> getLogOfYearForSource(Map<String,Object> condition,String sort)throws Exception{
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterListForSource(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	
	@Override
	public List<LAccess> getLogOfYearCountForSource(Map<String,Object> condition)throws Exception{
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterListCountForSource(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	
	@Override
	public List<LAccess> getLogOfYearPagingForSource(Map<String,Object> condition,String sort,int pageCount, int curpage)throws Exception{
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterPagingListForSource(condition, sort, pageCount, curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	
	@Override
	public List<LAccess> getLogOfPubForSource(Map<String,Object> condition,String sort)throws Exception{
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getPubCounterForSource(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	
	@Override
	public List<LAccess> getLogOfPubCountForSource(Map<String,Object> condition)throws Exception{
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getPubCounterCountForSource(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	
	@Override
	public List<LAccess> getLogOfPubPagingForSource(Map<String,Object> condition,String sort,int pageCount, int curpage)throws Exception{
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getPubCounterPagingForSource(condition, sort, pageCount, curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	
	public List<RRecommendDetail> getCounterForPublisher(Map<String,Object> condition,String sort)throws Exception{
		List<RRecommendDetail> list=null;
		try{
			list=this.daoFacade.getrRecommendDetailDao().getCounterForPublisher(condition, sort);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	
	public List<RRecommendDetail> getCounterPagingForPublisher(Map<String,Object> condition,String sort,int pageCount, int curpage)throws Exception{
		List<RRecommendDetail> list=null;
		try{
			list=this.daoFacade.getrRecommendDetailDao().getCounterPagingForPublisher(condition, sort,pageCount,curpage);
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "recommend.counter.get.error", e);//获取推荐信息统计失败
		}
		return list;
	}
	
	public List<RRecommendDetail> getCounterCountForPublisher(Map<String,Object> condition)throws Exception{
		List<RRecommendDetail> list = null;
		try {
			list = this.daoFacade.getrRecommendDetailDao().getCounterCountForPublisher(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "recommend.counter.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	
	public Integer getCount(Map<String,Object> condition,String group) throws Exception{
		Integer result=0;
		try {
			result = this.daoFacade.getlAccessDao().getGroupCount(condition,group);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return result;
	}
	
	public Integer getNormalCount(Map<String,Object> condition) throws Exception{
		Integer result=0;
		try {
			result = this.daoFacade.getlAccessDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return result;
	}
	
	public List<LAccess> getTopList(Map<String,Object> condition,Integer number) throws Exception{
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getTopList(condition,number);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	@Override
	public List<LAccess> isExistLog(Map<String, Object> condition)throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().isExistLogForPage(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	@Override
	public LAccess getLogInfo(String id) throws Exception {
		LAccess access = null;
		try {
			access = (LAccess)this.daoFacade.getlAccessDao().get(LAccess.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return access;
	}
	@Override
	public void updateLogInfo(LAccess laccess,String id ,String[] properties) throws Exception {
		try {
			this.daoFacade.getlAccessDao().update(laccess, LAccess.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.update.error", e);//更新日至信息失败！
		}
	}
	
	public List<LAccess> getLogOfYearToSearchPaging2(Map<String, Object> condition,String sort,int pageCount, int curpage)throws Exception{
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterPagingListToSearch3(condition, sort,pageCount,curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	public List<LAccess> getLogOfYearToPagePaging2(Map<String, Object> condition,String sort,int pageCount,int curpage) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterPagingListToPage3(condition, sort,pageCount,curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	public List<LAccess> getLogOfYearToPagePaging4(Map<String, Object> condition,String sort,int pageCount,int curpage) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterPagingListToPage4(condition, sort,pageCount,curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	@Override
	public List<LAccess> getLogOfYearPaging2(Map<String, Object> condition,String sort, int pageCount, int curpage) throws Exception {
		try{			
			List<LAccess> list=this.daoFacade.getlAccessDao().getCounterPagingList3(condition, sort,pageCount,curpage);
			return list;
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
	}
	public List<LAccess> getLogOfYear3(Map<String, Object> condition,String sort) throws Exception {
		try{			
			List<LAccess> list=this.daoFacade.getlAccessDao().getCounterList3(condition, sort);
			return list;
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
	}
	
	public List<LAccess> getLogOfYear2(Map<String,Object> condition,String sort)throws Exception{
		try{			
			List<LAccess> list=this.daoFacade.getlAccessDao().getCounterList2(condition, sort);
			return list;
		}catch(Exception e){
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
	}
	@Override
	public List<LAccess> getLogOfYearToSearch2(Map<String, Object> condition,String sort) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterListToSearch2(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	
	@Override
	public List<LAccess> getLogOfYearToPage2(Map<String, Object> condition,String sort) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterListToPage2(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	@Override
	public List<LAccess> getLogOfHotWords(Map<String, Object> condition,String sort, int pageCount, int curPage) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getLogOfHotWords(condition,sort,pageCount,curPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<LAccess> getcnLogOfHotWords(Map<String, Object> condition,String sort, int pageCount, int curPage) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getcnLogOfHotWords(condition,sort,pageCount,curPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<LAccess> getLogOfHotReading(Map<String, Object> condition,String sort, int pageCount, int curPage) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getLogOfHotReading(condition,sort,pageCount,curPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<LAccess> getLogOfRecentlyRead(Map<String, Object> condition,String sort, int pageCount, int curPage) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getLogOfRecentlyRead(condition,sort,pageCount,curPage);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public void deleteAccess(String id)throws Exception{
		try {
			this.daoFacade.getlAccessDao().delete(LAccess.class.getName(), id);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	public Integer getCount2(Map<String,Object> condition,String group) throws Exception{
		Integer result=0;
		try {
			result = this.daoFacade.getlAccessDao().getGroupCount2(condition,group);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return result;
	}
	public List<LAccess> getJournalReport1(Map<String, Object> condition,String sort,int pageCount,int curpage) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getJournalReport1(condition, sort,pageCount,curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	public List<LAccess> getJournalReportYOP(Map<String, Object> condition,String sort,int pageCount,int curpage) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getJournalReportYOP(condition, sort,pageCount,curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	@Override
	public List<LAccess> getJournalReport2(Map<String, Object> condition,String sort, int pageCount, int curpage) throws Exception {
		List<LAccess> list=null;
		try {
			list=this.daoFacade.getlAccessDao().getJournalReport2(condition, sort, pageCount, curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "log.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}
	@Override
	public List<LAccess> getJournalHotReading(Map<String, Object> condition,
			String sort, int pageCount, int curPage) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getJournalHotReading(condition,sort,pageCount,curPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<LAccess> getLogOfHotReadingForRecommad(Map<String, Object> condition,String sort, int pageCount, int curPage) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getLogOfHotReadingForRecommad(condition,sort,pageCount,curPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
