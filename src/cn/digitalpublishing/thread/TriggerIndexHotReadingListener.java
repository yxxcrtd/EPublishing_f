package cn.digitalpublishing.thread;

import org.springframework.beans.factory.annotation.Autowired;

import cn.digitalpublishing.redis.dao.BookDao;

public class TriggerIndexHotReadingListener {

	@Autowired
	private BookDao bookDao;

	public void writeRedis() {
		Thread thread = new TriggerIndexHotReading(bookDao);
		thread.start();
	}

}
