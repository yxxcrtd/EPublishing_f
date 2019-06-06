package cn.digitalpublishing.thread;

import java.util.Date;
import java.util.List;

import cn.digitalpublishing.ep.po.ResourcesSum;
import cn.digitalpublishing.redis.dao.BookDao;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;

public class TriggerIndexStat extends Thread {

	private ServiceFactory serviceFactory;
	private BookDao bookDao;

	public TriggerIndexStat(BookDao bd) {
		this.serviceFactory = (ServiceFactory) new ServiceFactoryImpl();
		this.bookDao = bd;
	}

	@Override
	public void run() {
		tigger();
	}

	private void tigger() {
		System.out.println("首页资源总数更新开始 - 当前时间：" + new Date());

		try {
			// 中文
			bookDao.delete("stat_zh_CN");
			List<ResourcesSum> sum = serviceFactory.getResourcesSumService().getList(null, "");
			bookDao.setValueByKey("stat_zh_CN", "<div class=\"mb50 oh\"><h1 class=\"indexHtit\"><span class=\"titFb\"><a class=\"ico4\" href=\"javascript:;\">资源总数</a></span></h1><p class=\"mb5\">资源总数 <span>" + sum.get(0).getSumCount() + "</span></p><a href=\"/index/advancedSearchSubmit?pubType=1\"><p class=\"data\"><span class=\"d_name\">图书</span><span class=\"d_number\">" + (sum.get(0).getBookCount() + sum.get(0).getBookCountEn()) + "</span></p></a><a href=\"/index/advancedSearchSubmit?pubType=2\"><p class=\"data\"><span class=\"d_name\">期刊</span><span class=\"d_number\">" + sum.get(0).getJournalsCount() + "</span></p></a><a href=\"/index/advancedSearchSubmit?pubType=4\"><p class=\"data\"><span class=\"d_name\">文章</span><span class=\"d_number\">" + sum.get(0).getArticleCount() + "</span></p></a></div>");

			// 英文
			bookDao.delete("stat_en_US");
			bookDao.setValueByKey("stat_en_US", "<div class=\"mb50 oh\"><h1 class=\"indexHtit\"><span class=\"titFb\"><a class=\"ico4\" href=\"javascript:;\">Total</a></span></h1><p class=\"mb5\">Total <span>" + sum.get(0).getSumCount() + "</span></p><a href=\"/index/advancedSearchSubmit?pubType=1\"><p class=\"data\"><span class=\"d_name\">Book</span><span class=\"d_number\">" + (sum.get(0).getBookCount() + sum.get(0).getBookCountEn()) + "</span></p></a><a href=\"/index/advancedSearchSubmit?pubType=2\"><p class=\"data\"><span class=\"d_name\">Journal</span><span class=\"d_number\">" + sum.get(0).getJournalsCount() + "</span></p></a><a href=\"/index/advancedSearchSubmit?pubType=4\"><p class=\"data\"><span class=\"d_name\">Article</span><span class=\"d_number\">" + sum.get(0).getArticleCount() + "</span></p></a></div>");
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("首页资源总数更新完成！ - 当前时间：" + new Date());
	}

}
