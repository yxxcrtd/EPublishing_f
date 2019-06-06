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

public class TriggerEnBookEditor extends Thread {

	private ServiceFactory serviceFactory;
	private BookDao bookDao;

	public TriggerEnBookEditor(BookDao bd) {
		this.serviceFactory = (ServiceFactory) new ServiceFactoryImpl();
		this.bookDao = bd;
	}

	@Override
	public void run() {
		tigger();
	}

	private void tigger() {
		System.out.println("外文电子书编辑推荐更新开始 - 当前时间：" + new Date());

		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			Map<String, Object> ucMap = new HashMap<String, Object>();
			ucMap.put("type", 2);
			ucMap.put("ava", 2);
			ucMap.put("pStatus", 2);
			ucMap.put("pType", 1);
			ucMap.put("noLicense", true);
			ucMap.put("noOrder", true);
			ucMap.put("isCn", false);
			ucMap.put("f", 1);
			List<BInstitution> binsList = serviceFactory.getConfigureService().getInstitutionList(condition, "");
			List<LAccess> editorRecommendsList = new ArrayList<LAccess>();
			for (BInstitution ins : binsList) {
				ucMap.put("pInsId", ins == null ? null : ins.getId());
				editorRecommendsList = serviceFactory.getLogAOPService().getLogOfHotReadingForRecommad(ucMap, " group by b.id order by count(a.id) desc ", 12, 0);
				if (editorRecommendsList.size() >= 12) {
					bookDao.delete(ins.getId() + "__Editor__EN");
					for (LAccess a : editorRecommendsList) {
						bookDao.zadd(ins.getId() + "__Editor__EN", a.getPublications().getId() + a.getPublications().getTitle() + "##author##" + a.getPublications().getAuthor() + "##publisher##" + a.getPublications().getPublisher().getName() + "##cover##" + a.getPublications().getCover());
					}
				} else {
					bookDao.delete("enBookEditor");
					ucMap.remove("pInsId");
					ucMap.remove("noLicense");
					editorRecommendsList = serviceFactory.getLogAOPService().getLogOfHotReadingForRecommad(ucMap, " group by b.id order by count(a.id) desc ", 12, 0);
					for (LAccess a : editorRecommendsList) {
						bookDao.zadd("enBookEditor", a.getPublications().getId() + a.getPublications().getTitle() + "##author##" + a.getPublications().getAuthor() + "##publisher##" + a.getPublications().getPublisher().getName() + "##cover##" + a.getPublications().getCover());
					}
				}
			}
		} catch (

		Exception e)

		{
			e.printStackTrace();
		}

		System.out.println("外文电子书编辑推荐更新完成！- 当前时间：" + new Date());
	}

}
