package cn.digitalpublishing.thread;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.redis.dao.BookDao;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;
import cn.digitalpublishing.util.web.DateUtil;

public class TriggerIndexNewest extends Thread {

	private ServiceFactory serviceFactory;
	private BookDao bookDao;

	public TriggerIndexNewest(BookDao bd) {
		this.serviceFactory = (ServiceFactory) new ServiceFactoryImpl();
		this.bookDao = bd;
	}

	@Override
	public void run() {
		tigger();
	}

	private void tigger() {
		System.out.println("首页最新资源更新开始 - 当前时间：" + new Date());

		try {
			bookDao.delete("new5");
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("status", 2); // 已上架的
			condition.put("local", 2); // 本地资源
			condition.put("f", 1); // 海外资源
			condition.put("createOn", DateUtil.getMonthEndDay(new Date()));
			List<PPublications> list = serviceFactory.getPPublicationsService().getNewestPublications(condition, " ORDER BY a.createOn DESC", 5);
			for (int i = 0; i < list.size(); i++) {
				bookDao.zadd("new5", (i + 1), list.get(i).getId() + list.get(i).getTitle() + "@@@@@author@@@@@" + list.get(i).getAuthor() + "@@@@@pubdate@@@@@" + list.get(i).getPubDate() + "@@@@@publisher@@@@@" + list.get(i).getPublisher().getName() + "@@@@@type@@@@@" + list.get(i).getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("首页最新资源更新完成！ - 当前时间：" + new Date());
	}

}
