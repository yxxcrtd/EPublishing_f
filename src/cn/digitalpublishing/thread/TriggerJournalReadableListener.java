package cn.digitalpublishing.thread;

import org.springframework.beans.factory.annotation.Autowired;

import cn.digitalpublishing.redis.dao.BookDao;
/**
 * 二级页面-期刊页-可读资源
 * @author zhouwenqian
 *
 */
public class TriggerJournalReadableListener {
	@Autowired
	private BookDao bookDao;

	public void writeRedis() {
		Thread thread = new TriggerJournalReadable(bookDao);
		thread.start();
	}
}
