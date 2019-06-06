package cn.digitalpublishing.thread;

import org.springframework.beans.factory.annotation.Autowired;

import cn.digitalpublishing.redis.dao.BookDao;
/**
 * 二级页面-中文电子书-编辑推荐
 * @author zhouwenqian
 *
 */
public class TriggerCnBookEditorListener {
	@Autowired
	private BookDao bookDao;

	public void writeRedis() {
		Thread thread = new TriggerCnBookEditor(bookDao);
		thread.start();
	}
}
