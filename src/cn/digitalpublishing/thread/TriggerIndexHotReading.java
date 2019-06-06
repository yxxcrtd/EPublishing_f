package cn.digitalpublishing.thread;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.redis.dao.BookDao;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;

public class TriggerIndexHotReading extends Thread {

	private ServiceFactory serviceFactory;
	private BookDao bookDao;

	public TriggerIndexHotReading(BookDao bd) {
		this.serviceFactory = (ServiceFactory) new ServiceFactoryImpl();
		this.bookDao = bd;
	}

	@Override
	public void run() {
		tigger();
	}

	private void tigger() {
		System.out.println("首页热读资源更新开始 - 当前时间：" + new Date());

		try {
			bookDao.delete("indexHotReading");

			Map<String, Object> condition = new HashMap<String, Object>();
			List<BInstitution> binsList = serviceFactory.getConfigureService().getInstitutionList(condition, "");
			condition.put("type", 2); // 操作类型：1-访问摘要；2-访问内容；3-检索
			condition.put("pubStatus", 2);
			condition.put("f", 1);// 海外资源
			for (BInstitution i : binsList) {
				condition.put("institutionId", i.getId());
				condition.put("license", "true"); // IP范围内时仅显示license有效的出版物，IP范围外不考虑license是否有效
				List<LAccess> list = serviceFactory.getLogAOPService().getLogOfHotReading(condition, " group by a.publications.id order by count(*) desc ", 5, 0);
				if (3 < list.size()) {
					bookDao.delete(i.getId());
					for (LAccess a : list) {
						System.out.println("插入Redis -> 机构ID：" + i.getId() + " - 机构名称：" + i.getName() + " - 资源ID：" + a.getPublications().getId() + " - 资源名称：" + a.getPublications().getTitle() + " - 资源作者：" + a.getPublications().getAuthor() + " - 出版时间：" + a.getPublications().getPubDate() + " - 出版社：" + a.getPublications().getPublisher().getName());
						bookDao.zadd(i.getId(), a.getPublications().getId() + a.getPublications().getTitle() + "@@@@@author@@@@@" + a.getPublications().getAuthor() + "@@@@@pubdate@@@@@" + a.getPublications().getPubDate() + "@@@@@publisher@@@@@" + a.getPublications().getPublisher().getName() + "@@@@@type@@@@@" + a.getPublications().getType());
					}
				} else {
					List<String> indexHotReading = bookDao.getSet("indexHotReading");
					if (0 == indexHotReading.size()) {
						condition.remove("license");
						condition.remove("institutionId");
						list = serviceFactory.getLogAOPService().getLogOfHotReading(condition, " group by a.publications.id order by count(*) desc ", 5, 0);
						for (LAccess a : list) {
							bookDao.zadd("indexHotReading", a.getPublications().getId() + a.getPublications().getTitle() + "@@@@@author@@@@@" + a.getPublications().getAuthor() + "@@@@@pubdate@@@@@" + a.getPublications().getPubDate() + "@@@@@publisher@@@@@" + a.getPublications().getPublisher().getName() + "@@@@@type@@@@@" + a.getPublications().getType());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("首页热读资源更新完成！ - 当前时间：" + new Date());
	}

}
