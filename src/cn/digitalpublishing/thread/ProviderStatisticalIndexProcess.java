package cn.digitalpublishing.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;
import cn.digitalpublishing.springmvc.form.product.BookSuppliersForm;

public class ProviderStatisticalIndexProcess extends Thread {

	private ServiceFactory serviceFactory;

	private ProviederStatisticalCounter counter;

	public ProviderStatisticalIndexProcess(ProviederStatisticalCounter counter) {
		this.serviceFactory = (ServiceFactory) new ServiceFactoryImpl();
		this.counter = counter;
	}

	@Override
	public void run() {
		Boolean b = false;
		if (b) {
			// 测试通过后取消这设置
			this.onConvertbatches();
		}
		counter.countDown();
	}

	/**
	 * 提供商统计信息 实时执行统计
	 * by heqing.yang 2015-01-24
	 * @throws Exception 
	 */
	private void onConvertbatches() {

		try {
			System.err.println("start:+++++++++++++++++Supplier+++++Supplier+++++Supplier++++----------------");
			Map<String, Object> condition = new HashMap<String, Object>();
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
			String dateString = formatter.format(date);
//			condition.put("institutionId", "4028819c427ca6d7014297799eb84b0b");
//			condition.put("accessON", 0);
			Integer count = serviceFactory.getBookSuppliersService().getSSupplierCount(condition);
			condition.clear();
//			condition.put("institutionId", "4028819c427ca6d7014297799eb84b0b");
			List<BInstitution> binsList = serviceFactory.getConfigureService().getInstitutionList(condition, "");
			if (count == 0) {
				//只在程序开始运行时统计一边全部的
				BookSuppliersForm form = new BookSuppliersForm();
				form.setEndYear(Integer.parseInt(dateString));
				for (int i = form.getStartYear(); i <= form.getEndYear(); i++) {
					form.getYearList().add(String.valueOf(i));
				}

				if (binsList != null && binsList.size() > 0) {
					for (BInstitution obj : binsList) {
						System.out.println("当前机构ID：" + obj.getId() + " Code:" + obj.getCode() + " Name:" + obj.getName() + " - " + new Date());
						for (int i = 0; i < form.getYearList().size(); i++) {
							//BInstitution obj = new BInstitution();
							//obj.setId("ff8080813c69a8fd013c6a554b350253");
							System.out.println("统计开始" + new Date());

							serviceFactory.getBookSuppliersService().getSupplierFast(1, null, null, null, form.getYearList().get(i), obj.getId());//写入toc访问统计
							System.out.println("toc访问统计" + new Date());

							serviceFactory.getBookSuppliersService().getSupplierFast(2, 1, null, null, form.getYearList().get(i), obj.getId());//写入访问成功统计
							System.out.println("访问成功" + new Date());

							serviceFactory.getBookSuppliersService().getSupplierFast(2, 2, null, null, form.getYearList().get(i), obj.getId());//写入拒绝访问统计
							System.out.println("拒绝访问" + new Date());

							serviceFactory.getBookSuppliersService().getSupplierFast(2, 2, 1, null, form.getYearList().get(i), obj.getId());//写入全文访问没有license（月）统计总数
							System.out.println("全文访问（没有License）" + new Date());

							serviceFactory.getBookSuppliersService().getSupplierFast(2, 2, 2, null, form.getYearList().get(i), obj.getId());//写入全文访问超并发数（月）统计总数
							System.out.println("全文访问（超并发数）" + new Date());

							serviceFactory.getBookSuppliersService().getSupplierFast(3, null, null, null, form.getYearList().get(i), obj.getId());//写入搜索（月）统计总数
							System.out.println("搜索统计" + new Date());

							serviceFactory.getBookSuppliersService().getSupplierFast(4, null, null, null, form.getYearList().get(i), obj.getId());//写入下载(月)统计总数
							System.out.println("下载统计" + new Date());

						}
					}
				}
				System.out.println("全部统计结束！" + new Date());

			}

			//} else {
			//for (int i = 0; i < 999999; i++) {
			//System.err.println("++++++++++++++++++++++++++++++++++++++" + i);
			//}
			//按当年当月 更新当月统计信息
			SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM");
			String sdate = sft.format(date);
			String[] str = sdate.split("-");
			String month = String.valueOf(str[1]);
			if (month.length() == 1) {
				month = "0" + month;
			}
			if (binsList != null && binsList.size() > 0) {
				for (BInstitution obj : binsList) {
					serviceFactory.getBookSuppliersService().getSupplierFast(1, null, null, month, dateString, obj.getId());//写入toc访问统计
					serviceFactory.getBookSuppliersService().getSupplierFast(2, 1, null, month, dateString, obj.getId());//写入访问成功统计
					serviceFactory.getBookSuppliersService().getSupplierFast(2, 2, null, month, dateString, obj.getId());//写入拒绝访问统计
					serviceFactory.getBookSuppliersService().getSupplierFast(2, 2, 1, month, dateString, obj.getId());//写入全文访问没有license（月）统计总数
					serviceFactory.getBookSuppliersService().getSupplierFast(2, 2, 2, month, dateString, obj.getId());//写入全文访问超并发数（月）统计总数
					serviceFactory.getBookSuppliersService().getSupplierFast(3, null, null, month, dateString, obj.getId());//写入搜索（月）统计总数
					serviceFactory.getBookSuppliersService().getSupplierFast(4, null, null, month, dateString, obj.getId());//写入下载(月)统计总数
				}
			}

			//}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			counter.countDown();
		}

	}

}
