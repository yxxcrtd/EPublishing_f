package cn.digitalpublishing.thread;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.redis.dao.BookDao;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;

public class TriggerIndexHotWords extends Thread {

	private ServiceFactory serviceFactory;
	private BookDao bookDao;

	public TriggerIndexHotWords(BookDao bd) {
		this.serviceFactory = (ServiceFactory) new ServiceFactoryImpl();
		this.bookDao = bd;
	}

	@Override
	public void run() {
		tigger();
	}

	private void tigger() {
		System.out.println("首页检索热词更新开始 - 当前时间：" + new Date());

		try {
			bookDao.delete("hotWords");

			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("type", 3); // 操作类型1-访问摘要 2-访问内容 3-检索
			String ins_Id = "";
			condition.put("institutionId", ins_Id);
			condition.put("keywordNotNull", 1);
			condition.put("createOn", -30); // 30 天前的搜索数据
			List<LAccess> list = serviceFactory.getLogAOPService().getLogOfHotWords(condition, " GROUP BY a.activity ORDER BY count(*) DESC ", 9, 0);
			// <a class="span1" href="javascript:;"
			// onclick="searchByCondition('searchValue', '美国科研出版社')"
			// title="美国科研出版社">美国科研出版社</a>
			bookDao.setValueByKey("hotWords", "");
			for (int i = 0; i < list.size(); i++) {
				bookDao.append("hotWords", "<a class=\"span" + (i + 1) + "\" href=\"index/search?type=0&isAccurate=1&searchValue2=" + list.get(i).getActivity() + "\"  title=\"" + list.get(i).getActivity() + "\">" + list.get(i).getActivity() + "</a>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("首页检索热词更新完成！ - 当前时间：" + new Date());
	}

}
