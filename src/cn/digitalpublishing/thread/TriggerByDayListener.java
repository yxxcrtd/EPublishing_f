package cn.digitalpublishing.thread;

import org.springframework.beans.factory.annotation.Autowired;

import cn.digitalpublishing.redis.dao.BookDao;

public class TriggerByDayListener {

	@Autowired
	private BookDao bookDao;

	public void writeRedis() {
		Thread thread = new TriggerByDay(bookDao);
		thread.start();
	}

}
