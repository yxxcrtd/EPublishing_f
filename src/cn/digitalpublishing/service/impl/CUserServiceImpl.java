package cn.digitalpublishing.service.impl;

import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.exception.CcsException;
import cn.digitalpublishing.ep.po.CDirectory;
import cn.digitalpublishing.ep.po.CFavourites;
import cn.digitalpublishing.ep.po.CSearchHis;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.service.CUserService;

public class CUserServiceImpl extends BaseServiceImpl implements CUserService {

	@Override
	public List<CUser> getUserList(Map<String, Object> condition, String sort)
			throws Exception {
		List<CUser> list = null;
		try {
			list = this.daoFacade.getcUserDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "user.list.get.error", e);//获取用户信息列表失败
		}
		return list;
	}

	@Override
	public List<CUser> getUserPagingList(Map<String, Object> condition, String sort,Integer pageCount, Integer page)throws Exception {
		List<CUser> list = null;
		try {
			list = this.daoFacade.getcUserDao().getPagingList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "user.list.get.error", e);//获取用户信息列表失败
		}
		return list;
	}
	
	@Override
	public List<CFavourites> getFavoutitesList(Map<String, Object> condition,
			String sort) throws Exception {
		List<CFavourites> list = null;
		try {
			list = this.daoFacade.getcFavouritesDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "favoutites.list.get.error", e);//获取收藏信息列表失败
		}
		return list;
	}

	@Override
	public void deleteFavorites(Map<String, Object> condition) throws Exception {
		try {
			String[] del = condition.get("dels").toString().split(",");
			String userId = condition.get("userId").toString();
			for(int i=0;i<del.length;i++){
				this.daoFacade.getcFavouritesDao().deleteBycondition(userId,del[i]);
			}
			
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "favoutites.info.delete.error", e);//删除收藏信息失败
		}
	}

	@Override
	public void addSearchHistory(CSearchHis obj) throws Exception {
		try {
			this.daoFacade.getcSearchHisDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "search.history.info.add.error", e);//新增搜索历史信息失败
		}
	}

	@Override
	public List<CSearchHis> getSearchHistoryList(Map<String, Object> condition,
			String sort) throws Exception {
		List<CSearchHis> list = null;
		try {
			list = this.daoFacade.getcSearchHisDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "search.history.list.get.error", e);//获取搜索历史信息列表失败
		}
		return list;
	}

	@Override
	public List<CDirectory> getDirtoryList(Map<String,Object> condition, String sort)
			throws Exception {
		List<CDirectory> list = null;
		try {
			list = this.daoFacade.getcDirectoryDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "search.directory.list.get.error", e);//获取搜索历史信息文件夹列表失败
		}
		return list;
	}

	@Override
	public void updateSearchHis(CSearchHis his, String id,String[] properties) throws Exception {
		try {
			this.daoFacade.getcSearchHisDao().update(his, CSearchHis.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "search.history.info.update.error", e);//更新搜索历史信息失败
		}
	}

	@Override
	public CDirectory getDirtory(String id) throws Exception {
		CDirectory obj = null ;
		try {
			obj = (CDirectory)this.daoFacade.getcDirectoryDao().get(CDirectory.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "search.directory.info.get.error", e);//获取搜索历史文件夹信息失败
		}
		return obj;
	}

	@Override
	public void updateDirectory(CDirectory obj, String id,String[] properties) throws Exception {
		try {
			this.daoFacade.getcDirectoryDao().update(obj, CDirectory.class.getName(), id, properties);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "search.directory.info.update.error", e);//更新搜索历史文件夹信息失败
		}
	}

	@Override
	public void addDirectory(CDirectory obj) throws Exception {
		try {
			this.daoFacade.getcDirectoryDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "search.directory.info.add.error", e);//新增搜索历史文件夹信息失败
		}
	}

	@Override
	public void deleteSearchHis(String id) throws Exception {
		try {
			this.daoFacade.getcSearchHisDao().delete(CSearchHis.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "search.history.info.delete.error", e);//删除搜索历史信息失败
		}
	}

	@Override
	public void deleteSearchHisByCondition(Map<String, Object> condition)
			throws Exception {
		try {
			this.daoFacade.getcSearchHisDao().deleteByCondition(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "search.history.info.delete.error", e);//删除搜索历史信息失败
		}
	}

	@Override
	public void deleteDirectory(String id) throws Exception {
		try {
			this.daoFacade.getcSearchHisDao().delete(CDirectory.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "search.directory.info.delete.error", e);//删除搜索历史文件夹信息失败
		}
	}

	@Override
	public CUser getUser(String id) throws Exception {
		CUser obj = null;
		try {
			obj = (CUser)this.daoFacade.getcUserDao().get(CUser.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "user.info.get.error", e);//未找到用户信息
		}
		return obj;
	}

	@Override
	public List<CFavourites> getFavoutitesPagingList(
			Map<String, Object> condition, String sort,int pageCount,int curPage) throws Exception {
		List<CFavourites> list = null;
		try {
			list = this.daoFacade.getcFavouritesDao().getPagingList(condition, sort, pageCount, curPage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "favoutites.list.get.error", e);//获取收藏信息列表失败
		}
		return list;
	}
	@Override
	public List<CFavourites> getfList(
			Map<String, Object> condition, String sort) throws Exception {
		List<CFavourites> list = null;
		try {
			list = this.daoFacade.getcFavouritesDao().getfList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "favoutites.list.get.error", e);//获取收藏信息列表失败
		}
		return list;
	}
	@Override
	public int getFavoutitesCount(Map<String, Object> condition)
			throws Exception {
		int num = 0;
		try {
			num = this.daoFacade.getcFavouritesDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "favoutites.list.count.error", e);//获取收藏信息总数
		}
		return num;
	}

	@Override
	public int getDirtoryCount(Map<String, Object> condition) throws Exception {
		int num = 0;
		try {
			num = this.daoFacade.getcDirectoryDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "Directory.list.count.error", e);//获取收藏夹总数
		}
		return num;
	}

	@Override
	public List<CDirectory> getDirtoryPagingList(Map<String, Object> condition,
			String sort, int pageCount, int curpage) throws Exception {
		List<CDirectory> list = null;
		try {
			list = this.daoFacade.getcDirectoryDao().getPagingList(condition, sort, pageCount, curpage);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "search.directory.list.get.error", e);//获取搜索历史信息文件夹列表失败
		}
		return list;
	}

	@Override
	public CSearchHis getSearchHistory(String id) throws Exception {
		CSearchHis obj = null;
		try {
			obj = (CSearchHis)this.daoFacade.getcSearchHisDao().get(CSearchHis.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "search.directory.list.get.error", e);//获取搜索历史信息文件夹列表失败
		}
		return obj;
	}

	@Override
	public List<CDirectory> getDirtoryList1(Map<String, Object> condition,
			String sort) throws Exception {
		List<CDirectory> list = null;
		try {
			list = this.daoFacade.getcDirectoryDao().getList1(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException)e).getPrompt()	: "search.directory.list.get.error", e);//获取搜索历史信息文件夹列表失败
		}
		return list;
	}

}
