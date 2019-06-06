package cn.digitalpublishing.service.impl;

import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.digitalpublishing.ep.po.PNote;
import cn.digitalpublishing.service.PNoteService;

public class PNoteServiceImpl extends BaseServiceImpl implements PNoteService {

	public void addNote(PNote obj)throws Exception {
		try {
			this.daoFacade.getpNoteDao().insert(obj);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "Note.info.add.error", e);//添加笔记信息失败！
		}
	}

	public PNote getNote(String id) throws Exception {
		PNote notes = null;
		try {
			notes = (PNote)this.daoFacade.getpNoteDao().get(PNote.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "Note.info.get.error", e);//获取笔记信息失败！
		}
		return notes;
	}

	public void deleteNote(String id) throws Exception {
		try {
			this.daoFacade.getpNoteDao().delete(PNote.class.getName(), id);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "Note.info.delete.error", e);//删除笔记信息失败！
		}
	}

	public List<PNote> getNoteList(Map<String, Object> condition,
			String sort) throws Exception {
		List<PNote> list = null;
		try {
			list = this.daoFacade.getpNoteDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	:"Note.info.list.error", e);//获取笔记信息列表失败！
		}
		return list;
	}

	public List<PNote> getNotePagingList(Map<String, Object> condition,
			String sort, Integer pageCount, Integer page) throws Exception {
		List<PNote> list = null;
		try {
			list = this.daoFacade.getpNoteDao().getPagingList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "Note.info.pagelist.error", e);//获取笔记信息分页列表失败！
		}
		return list;
	}

	public Integer getNoteCount(Map<String, Object> condition) throws Exception {
		Integer num = 0;
		try {
			num = this.daoFacade.getpNoteDao().getCount(condition);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "Note.info.count.error", e);//获取笔记信息总数失败！
		}
		return num;
	}

	public void updateNote(PNote note) throws Exception {
		try {
			this.daoFacade.getpNoteDao().update(note, PNote.class.getName(), note.getId(), null);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? e.getMessage()	: "Note.info.add.error", e);//添加笔记信息失败！
		}
	}
	
}
