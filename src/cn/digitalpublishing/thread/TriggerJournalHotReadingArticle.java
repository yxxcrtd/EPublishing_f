package cn.digitalpublishing.thread;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.redis.dao.BookDao;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;

/**
 * 二级页面-期刊页-热读文章
 * 
 * @author zhouwenqian
 *
 */
public class TriggerJournalHotReadingArticle extends Thread {
	private ServiceFactory serviceFactory;
	private BookDao bookDao;

	public TriggerJournalHotReadingArticle(BookDao bd) {
		this.serviceFactory = (ServiceFactory) new ServiceFactoryImpl();
		this.bookDao = bd;
	}

	@Override
	public void run() {
		tigger();
	}

	private void tigger() {
		System.out.println("外文电子期刊热读文章更新开始!-------->  当前时间：" + new Date());
		try {

			List<BInstitution> binsList = serviceFactory.getConfigureService().getInstitutionList(null, "");

			Map<String, Object> hotcondition = new HashMap<String, Object>();
			hotcondition.put("type", 2);
			hotcondition.put("pubStatus", 2);// 上架
			hotcondition.put("languageEn", "ch%");
			hotcondition.put("pubtype", 4);// 文章
			hotcondition.put("f", 1);
			for (BInstitution ins : binsList) {
				bookDao.delete(ins.getId() + "__JournalHotReadingArticle");
				hotcondition.put("institutionId", ins.getId());
				hotcondition.put("license", "true");// IP范围内时仅显示license有效的出版物，ip范围外不考虑license是否有效
				List<LAccess> hotlist = serviceFactory.getLogAOPService().getJournalHotReading(hotcondition, " group by a.publications.id order by count(*) desc ", 12, 0);

				if (hotlist != null && !hotlist.isEmpty() && hotlist.size() >= 12) {
					for (LAccess aa : hotlist) {
						PPublications p = serviceFactory.getPPublicationsService().findByIdNew(aa.getPublications().getId());
						bookDao.zadd(ins.getId() + "__JournalHotReadingArticle", p.getId() + p.getTitle() + "##parentId##" + p.getPublications().getId() + "##parentTitle##" + p.getPublications().getTitle() + "##year##" + p.getYear() + "##volumeCode##" + p.getVolumeCode() + "##issueCode##" + p.getIssueCode() + "##startPage##" + p.getStartPage() + "##endPage##" + p.getEndPage() + "##publisher##" + p.getPublisher().getName());

					}
				} else {
					bookDao.delete("JournalHotReadingArticle");
					hotcondition.remove("institutionId");
					hotcondition.remove("license");// IP范围内时仅显示license有效的出版物，ip范围外不考虑license是否有效
					hotlist = serviceFactory.getLogAOPService().getJournalHotReading(hotcondition, " group by a.publications.id order by count(*) desc ", 12, 0);
					for (LAccess aa : hotlist) {
						PPublications p = serviceFactory.getPPublicationsService().findByIdNew(aa.getPublications().getId());
						bookDao.zadd("JournalHotReadingArticle", p.getId() + p.getTitle() + "##parentId##" + p.getPublications().getId() + "##parentTitle##" + p.getPublications().getTitle() + "##year##" + p.getYear() + "##volumeCode##" + p.getVolumeCode() + "##issueCode##" + p.getIssueCode() + "##startPage##" + p.getStartPage() + "##endPage##" + p.getEndPage() + "##publisher##" + p.getPublisher().getName());

					}
				}
			}
		} catch (

		Exception e)

		{
			e.printStackTrace();
		}

		System.out.println("外文电子期刊热读文章更新完成!-------->  当前时间：" + new Date());
	}
}
