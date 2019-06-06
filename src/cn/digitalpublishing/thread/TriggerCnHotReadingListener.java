package cn.digitalpublishing.thread;

import org.springframework.beans.factory.annotation.Autowired;

import cn.digitalpublishing.redis.dao.BookDao;
/**
 * 二级页面-中文电子书-热读资源
 * @author zhouwenqian
 *
 */
public class TriggerCnHotReadingListener {
	@Autowired
	private BookDao bookDao;

	public void writeRedis() {
		Thread thread = new TriggerCnHotReading(bookDao);
		thread.start();
	}
}
