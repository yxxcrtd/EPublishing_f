package cn.digitalpublishing.thread;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.BSubject;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.redis.dao.BookDao;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;

public class TriggerByDay extends Thread {

	private ServiceFactory serviceFactory;
	private BookDao bookDao;

	public TriggerByDay(BookDao bd) {
		this.serviceFactory = (ServiceFactory) new ServiceFactoryImpl();
		this.bookDao = bd;
	}

	@Override
	public void run() {
		tigger();
	}

	private void tigger() {
		try {
			// 图书详情页的同类热销
			Map<String, Object> condition = new HashMap<String, Object>();
			List<BSubject> list = serviceFactory.getBSubjectService().getSubList(condition, "");
			System.out.println("分类总数：" + list.size() + " - 当前时间：" + new Date());

			for (BSubject subject : list) {
				System.out.println("当前分类：" + subject.getId() + " - " + subject.getCode() + " - " + subject.getName());
				bookDao.delete(subject.getId());
				List<PPublications> PublicationsList = serviceFactory.getPPublicationsService().getBuyTimesInfo(subject.getId(), " ORDER BY a.buyTimes DESC ", 5);
				for (PPublications pub : PublicationsList) {
					int d = null == pub.getBuyTimes() ? 0 : pub.getBuyTimes();
					System.out.println("  插入Redis：" + subject.getId() + " - " + pub.getTitle());
					bookDao.zadd(subject.getId(), String.format("%08d", d) + pub.getId() + pub.getTitle() + "@@@@@author@@@@@" + pub.getAuthor() + "@@@@@pubdate@@@@@" + pub.getPubDate() + "@@@@@publisher@@@@@" + pub.getPublisher().getName());
				}
			}
			System.out.println("同类热销更新完成！ - 当前时间：" + new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
