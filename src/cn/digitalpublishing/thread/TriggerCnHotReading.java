package cn.digitalpublishing.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.redis.dao.BookDao;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;

/**
 * 二级页面-中文电子书-热读资源
 * 
 * @author zhouwenqian
 *
 */
public class TriggerCnHotReading extends Thread {
	private ServiceFactory serviceFactory;
	private BookDao bookDao;

	public TriggerCnHotReading(BookDao bd) {
		this.serviceFactory = (ServiceFactory) new ServiceFactoryImpl();
		this.bookDao = bd;
	}

	@Override
	public void run() {
		tigger();
	}

	private void tigger() {
		System.out.println("中文电子书热读资源更新开始!-------->  当前时间：" + new Date());
		try {

			Map<String, Object> hotcondition = new HashMap<String, Object>();
			int num;
			hotcondition.put("type", 2);
			hotcondition.put("pubStatus", 2);// 上架
			hotcondition.put("pubType", 1);
			hotcondition.put("language", "ch%");
			hotcondition.put("f", "1");
			List<BInstitution> binsList = serviceFactory.getConfigureService().getInstitutionList(null, "");
			List<LAccess> cnHotReadingList = new ArrayList<LAccess>();
			for (BInstitution ins : binsList) {
				hotcondition.put("institutionId", ins == null ? null : ins.getId());
				hotcondition.put("license", "true");
				List<LAccess> hotlist = serviceFactory.getLogAOPService().getLogOfHotReading(hotcondition, " group by a.publications.id order by count(*) desc ", 24, 0);
				if (cnHotReadingList.size() >= 24) {
					bookDao.delete(ins.getId() + "__cnbookList__HotReading__CN");
					for (LAccess a : cnHotReadingList) {
						bookDao.zadd(ins.getId() + "__cnbookList__HotReading__CN", a.getPublications().getId() + a.getPublications().getTitle() + "##author##" + a.getPublications().getAuthor() + "##publisher##" + a.getPublications().getPublisher().getName() + "##cover##" + a.getPublications().getCover());
					}

				} else {
					bookDao.delete("cnbookList__HotReading__CN");
					hotcondition.remove("institutionId");
					hotcondition.remove("license");
					cnHotReadingList = serviceFactory.getLogAOPService().getLogOfHotReading(hotcondition, " group by a.publications.id order by count(*) desc ", 24, 0);
					for (LAccess a : cnHotReadingList) {
						bookDao.zadd("cnbookList__HotReading__CN", a.getPublications().getId() + a.getPublications().getTitle() + "##author##" + a.getPublications().getAuthor() + "##publisher##" + a.getPublications().getPublisher().getName() + "##cover##" + a.getPublications().getCover());
					}

				}
			}

		} catch (

		Exception e)

		{
			e.printStackTrace();
		}

		System.out.println("中文电子书热读资源更新完成!-------->  当前时间：" + new Date());
	}
}
