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

/**
 * 二级页面-期刊页-推荐期刊
 * 
 * @author zhouwenqian
 *
 */
public class TriggerJournalRecommend extends Thread {
	private ServiceFactory serviceFactory;
	private BookDao bookDao;

	public TriggerJournalRecommend(BookDao bd) {
		this.serviceFactory = (ServiceFactory) new ServiceFactoryImpl();
		this.bookDao = bd;
	}

	@Override
	public void run() {
		tigger();
	}

	private void tigger() {
		System.out.println("外文电子期刊推荐期刊更新开始!-------->  当前时间：" + new Date());
		try {

			List<BInstitution> binsList = serviceFactory.getConfigureService().getInstitutionList(null, "");
			Map<String, Object> ucMap = new HashMap<String, Object>();
			ucMap.put("ava", 2);
			ucMap.put("pStatus", 2);
			ucMap.put("pType", 2); // 期刊
			ucMap.put("languageEn", "ch%");
			ucMap.put("f", 1);
			for (BInstitution ins : binsList) {
				ucMap.put("noLicense", true);
				ucMap.put("noOrder", true);
				ucMap.put("pInsId", ins.getId());
				List<LAccess> list = serviceFactory.getLogAOPService().getLogOfHotReadingForRecommad(ucMap, " group by b.id order by count(a.id) desc ", 24, 0);
				if (list.size() >= 24) {
					bookDao.delete(ins.getId() + "__JournalRecommend");
					for (LAccess a : list) {
						bookDao.zadd(ins.getId() + "__JournalRecommend", a.getPublications().getId() + a.getPublications().getTitle() + "##publisher##" + a.getPublications().getPublisher().getName() + "##start##" + a.getPublications().getStartVolume() + "##end##" + a.getPublications().getEndVolume() + "##license##1" + "##oa##" + a.getPublications().getOa() + "##free##" + a.getPublications().getFree());
					}

				} else {
					bookDao.delete("JournalRecommend");
					ucMap.remove("noLicense");
					ucMap.remove("noOrder");
					ucMap.remove("pInsId");
					list = serviceFactory.getLogAOPService().getLogOfHotReadingForRecommad(ucMap, " group by b.id order by count(a.id) desc ", 24, 0);
					for (LAccess a : list) {
						System.out.println("'" + a.getPublications().getId() + "',");
						bookDao.zadd("JournalRecommend", a.getPublications().getId() + a.getPublications().getTitle() + "##publisher##" + a.getPublications().getPublisher().getName() + "##start##" + a.getPublications().getStartVolume() + "##end##" + a.getPublications().getEndVolume() + "##license##0" + "##oa##" + a.getPublications().getOa() + "##free##" + a.getPublications().getFree());
					}

				}
			}

		} catch (Exception e)

		{
			e.printStackTrace();
		}

		System.out.println("外文电子期刊推荐期刊更新完成!-------->  当前时间：" + new Date());
	}
}
