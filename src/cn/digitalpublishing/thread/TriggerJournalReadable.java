package cn.digitalpublishing.thread;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.redis.dao.BookDao;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;

/**
 * 二级页面-期刊页-可读资源
 * 
 * @author zhouwenqian
 *
 */
public class TriggerJournalReadable extends Thread {
	private ServiceFactory serviceFactory;
	private BookDao bookDao;

	public TriggerJournalReadable(BookDao bd) {
		this.serviceFactory = (ServiceFactory) new ServiceFactoryImpl();
		this.bookDao = bd;
	}

	@Override
	public void run() {
		tigger();
	}

	private void tigger() {
		System.out.println("外文电子期刊可读资源更新开始!-------->  当前时间：" + new Date());
		try {

			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("available", 3);// 这是过滤图书是政治原因的条件
			condition.put("ptype", 2);// 类型-2
			condition.put("isOaorFree", 2);// 免费或者开源
			condition.put("languageEn", "ch%");
			condition.put("f", 1);
			List<PPublications> list = serviceFactory.getPPublicationsService().getpubListIsNew(condition, " ", 24, 0);
			if (list.size() >= 24) {
				bookDao.delete("journalReadbleList");
				for (PPublications p : list) {
					System.out.println("'" + p.getId() + "',");
					bookDao.zadd("journalReadbleList", p.getId() + p.getTitle() + "##oa##" + p.getOa() + "##free##" + p.getFree() + "##publisher##" + p.getPublisher().getName() + "##start##" + p.getStartVolume() + "##end##" + p.getEndVolume());
				}
			}

		} catch (Exception e)

		{
			e.printStackTrace();
		}

		System.out.println("外文电子期刊可读资源更新完成!-------->  当前时间：" + new Date());
	}
}
