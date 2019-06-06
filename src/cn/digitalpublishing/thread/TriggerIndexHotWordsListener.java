package cn.digitalpublishing.thread;

import org.springframework.beans.factory.annotation.Autowired;

import cn.digitalpublishing.redis.dao.BookDao;

public class TriggerIndexHotWordsListener {

	@Autowired
	private BookDao bookDao;

	public void writeRedis() {
		Thread thread = new TriggerIndexHotWords(bookDao);
		thread.start();
	}

}
