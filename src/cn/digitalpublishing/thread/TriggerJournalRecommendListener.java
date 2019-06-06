package cn.digitalpublishing.thread;

import org.springframework.beans.factory.annotation.Autowired;

import cn.digitalpublishing.redis.dao.BookDao;

/**
 * 二级页面-期刊页-推荐期刊
 * 
 * @author zhouwenqian
 *
 */
public class TriggerJournalRecommendListener {
	@Autowired
	private BookDao bookDao;

	public void writeRedis() {
		Thread thread = new TriggerJournalRecommend(bookDao);
		thread.start();
	}
}
