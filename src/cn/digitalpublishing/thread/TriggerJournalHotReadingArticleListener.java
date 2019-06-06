package cn.digitalpublishing.thread;

import org.springframework.beans.factory.annotation.Autowired;

import cn.digitalpublishing.redis.dao.BookDao;

/**
 * 二级页面-期刊页-热读文章
 * 
 * @author zhouwenqian
 *
 */
public class TriggerJournalHotReadingArticleListener {
	@Autowired
	private BookDao bookDao;

	public void writeRedis() {
		Thread thread = new TriggerJournalHotReadingArticle(bookDao);
		thread.start();
	}
}
