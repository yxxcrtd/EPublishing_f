package cn.digitalpublishing.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.com.daxtech.framework.exception.CcsException;
import cn.digitalpublishing.ep.po.LAccess;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.ep.po.SSupplier;
import cn.digitalpublishing.service.StatisticsBookSuppliersService;
import cn.digitalpublishing.springmvc.form.product.BookSuppliersForm;

public class StatisticsBookSuppliersImpl extends BaseServiceImpl implements StatisticsBookSuppliersService {

	@Override
	public void getSyncLaccess() throws Exception {
		this.daoFacade.getlAccessDao().executeLaccess(1);
	}

	@Override
	public List<LAccess> getBookStatisticalList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterPagingList3(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Laccess.getBookStatisticalList.get.error", e);//获取统计信息分页列表失败！
		}
		return list;
	}

	@Override
	public Integer getLaccessCount(Map<String, Object> condition, String group) throws Exception {
		Integer num = 0;
		try {
			num = this.daoFacade.getlAccessDao().getCount(condition, group);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Laccess.count.get.error", e);//获取统计信息总数失败！
		}
		return num;
	}

	@Override
	public List<LAccess> getDownloadBookFull(Map<String, Object> condition, String sort, int pageCount, int page) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterPagingList3(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "laccess.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}

	@Override
	public List<LAccess> getDownloadRefuseBookJournal(Map<String, Object> condition, String sort, int pageCount, int page) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterPagingList4(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "laccess.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}

	@Override
	public List<LAccess> getRefuseBookJournalList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterPagingList4(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Laccess.getBookStatisticalList.get.error", e);//获取统计信息分页列表失败！
		}
		return list;

	}

	@Override
	public List<LAccess> getSearchesBookJournalList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterPagingList5(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Laccess.getBookStatisticalList.get.error", e);//获取统计信息分页列表失败！
		}
		return list;
	}

	@Override
	public List<LAccess> getDownloadSearchesBookJournal(Map<String, Object> condition, String sort, int pageCount, int page) throws Exception {
		List<LAccess> list = null;
		try {
			list = this.daoFacade.getlAccessDao().getCounterPagingList5(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "laccess.info.get.error", e);//获取日至信息失败！
		}
		return list;
	}

	@Override
	public void getSupplier(Integer type, Integer access, Integer stype, String stimes, String year, String sourceid) throws Exception {
		List<LAccess> list = null;
		boolean flag = false;
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			BookSuppliersForm form = new BookSuppliersForm();
			if (sourceid != null) {//机构ID
				condition.put("isInstitutionId", sourceid);
			}
			condition.put("year", year);
			if (stimes != null) {
				form.setStartMonth(stimes);
			}
			condition.put("startMonth", form.getStartMonth());
			condition.put("endMonth", form.getEndMonth());
			condition.put("type", type);

			switch (type) {
			case 2:
				if (stype != null) {//1-没有License,2-超出并发数
					condition.put("", stype);
				}
				condition.put("access", access);

				break;
			case 3:
				if (stype != null) {//1-常规检索 , 2-高级检索 , 3-分类法点击---！（标准检索 联邦检索）
					condition.put("", stype);
				}
				break;
			default:
				break;
			}
			list = this.daoFacade.getlAccessDao().getCounterPagingList10(condition, "", 0, 0);
			if (list != null && list.size() > 0) {
				flag = true;
			}
			if (flag) {
				for (LAccess o : list) {
					for (int i = 1; i < 13; i++) {

						String month = String.valueOf(i);
						if (month.length() == 1) {
							month = "0" + month;
						}
						int count = 0;//每月总数
						if (i == 1) {
							count += o.getMonth1() == null ? 0 : o.getMonth1();
						}
						if (i == 2) {
							count += o.getMonth2() == null ? 0 : o.getMonth2();
						}
						if (i == 3) {
							count += o.getMonth3() == null ? 0 : o.getMonth3();
						}
						if (i == 4) {
							count += o.getMonth4() == null ? 0 : o.getMonth4();
						}
						if (i == 5) {
							count += o.getMonth5() == null ? 0 : o.getMonth5();
						}
						if (i == 6) {
							count += o.getMonth6() == null ? 0 : o.getMonth6();
						}
						if (i == 7) {
							count += o.getMonth7() == null ? 0 : o.getMonth7();
						}
						if (i == 8) {
							count += o.getMonth8() == null ? 0 : o.getMonth8();
						}
						if (i == 9) {
							count += o.getMonth9() == null ? 0 : o.getMonth9();
						}
						if (i == 10) {
							count += o.getMonth10() == null ? 0 : o.getMonth10();
						}
						if (i == 11) {
							count += o.getMonth11() == null ? 0 : o.getMonth11();
						}
						if (i == 12) {
							count += o.getMonth12() == null ? 0 : o.getMonth12();
						}

						Map<String, Object> supMap = new HashMap<String, Object>();
						supMap.put("datetime", o.getYear() + "-" + month);
						supMap.put("institutionId", o.getInstitutionId());
						if (o.getPublications().getType() != 1 && o.getPublications().getType() != 3) {
							String issn = o.getPublications().getCode().length() > 9 ? o.getPublications().getCode().substring(0, 9) : o.getPublications().getCode();
							supMap.put("issn", issn);
							supMap.put("eissn", issn);
						} else {
							supMap.put("pubId", o.getPublications().getId());
						}

						List<SSupplier> supList = this.daoFacade.getSuppDao().getList(supMap, "");
						if (supList != null && supList.size() > 0) {
							SSupplier obj = supList.get(0);
							if (o.getPublications().getType() != 1 && o.getPublications().getType() != 3) {
								if (type == 1) {
									count += supList.get(0).getToc() != null && !"".equals(supList.get(0).getToc().toString()) ? supList.get(0).getToc() : 0;
									obj.setToc(count);
								} else if (type == 2 && access != null && access == 1) {
									count += supList.get(0).getFullAccess() != null && !"".equals(supList.get(0).getFullAccess().toString()) ? supList.get(0).getFullAccess() : 0;
									obj.setFullAccess(count);
								} else if (type == 2 && access != null && access == 2) {
									if ((stype != null) && stype == 1) {
										count += supList.get(0).getRefusedLicense() != null && !"".equals(supList.get(0).getRefusedLicense().toString()) ? supList.get(0).getRefusedLicense() : 0;
										obj.setRefusedLicense(count);
									} else if (stype != null && stype == 2) {
										count += supList.get(0).getRefusedConcurrent() != null && !"".equals(supList.get(0).getRefusedConcurrent().toString()) ? supList.get(0).getRefusedConcurrent() : 0;
										obj.setRefusedConcurrent(count);
									} else {
										count += supList.get(0).getFullRefused() != null && !"".equals(supList.get(0).getFullRefused().toString()) ? supList.get(0).getFullRefused() : 0;
										obj.setFullRefused(count);
									}
								} else if (type == 3) {
									if (stype != null && stype == 1) {
										count += supList.get(0).getSearchStandard() != null && !"".equals(supList.get(0).getSearchStandard().toString()) ? supList.get(0).getSearchStandard() : 0;
										obj.setSearchStandard(count);
									} else if (stype != null && stype == 2) {
										count += supList.get(0).getSearchFederal() != null && !"".equals(supList.get(0).getSearchFederal().toString()) ? supList.get(0).getSearchFederal() : 0;
										obj.setSearchFederal(count);
									} else {
										count += supList.get(0).getSearch() != null && !"".equals(supList.get(0).getSearch().toString()) ? supList.get(0).getSearch() : 0;
										obj.setSearch(count);
									}
								} else if (type == 4) {
									count += supList.get(0).getDownload() != null && !"".equals(supList.get(0).getDownload().toString()) ? supList.get(0).getDownload() : 0;
									obj.setDownload(count);
								}

							} else if (type == 1) {
								obj.setToc(count);
							} else if (type == 2 && access != null && access == 1) {
								obj.setFullAccess(count);
							} else if (type == 2 && access != null && access == 2) {
								if (stype != null && stype == 1) {
									obj.setRefusedLicense(count);
								} else if (stype != null && stype == 2) {
									obj.setRefusedConcurrent(count);
								} else {
									obj.setFullRefused(count);
								}
							} else if (type == 3) {
								if (stype != null && stype == 1) {
									obj.setSearchStandard(count);
								} else if (stype != null && stype == 2) {
									obj.setSearchFederal(count);
								} else {
									obj.setSearch(count);
								}
							} else if (type == 4) {
								obj.setDownload(count);
							}
							obj.setLang(o.getPublications().getLang() != null ? o.getPublications().getLang() : "");//语种
							this.daoFacade.getSuppDao().update(obj, SSupplier.class.getName(), obj.getId(), null);
						} else {
							SSupplier obj = new SSupplier();
							obj.setInstitutionid(o.getInstitutionId());
							obj.setTitle(o.getPublications().getTitle() != null ? o.getPublications().getTitle() : "");
							obj.setAuthor(o.getPublications().getAuthor() != null ? o.getPublications().getAuthor() : "");

							if (o.getPublications().getType() != 1 && o.getPublications().getType() != 3) {
								obj.setPubId(o.getPublications().getId());
								obj.setIssn(o.getPublications().getCode() != null ? o.getPublications().getCode() : "");
								obj.setEissn(o.getPublications().getEissn() != null ? o.getPublications().getEissn() : "");
								obj.setType(o.getPublications().getType() != null ? o.getPublications().getType() : null);

							} else {
								obj.setPubId(o.getPublications().getId() != null ? o.getPublications().getId() : "");
								obj.setIsbn(o.getPublications().getCode() != null ? o.getPublications().getCode() : "");
								obj.setType(o.getPublications().getType() != null ? o.getPublications().getType() : null);
								obj.setIssn("");
							}

							obj.setPubName(o.getPublications().getPublisher().getName() != null ? o.getPublications().getPublisher().getName() : "");
							String str = o.getYear() + "-" + month;
							obj.setSdate(str);
							obj.setYear(Integer.parseInt(o.getYear()));
							obj.setMonth(Integer.parseInt(month));
							obj.setPlatform(o.getPlatform() != null ? o.getPlatform() : "");
							//初始值赋值 0 索引查询不能有null 值 会影响查询速度 by heqing.yang 2015-01-24
							obj.setFullAccess(0);
							obj.setFullRefused(0);
							obj.setRefusedLicense(0);
							obj.setRefusedConcurrent(0);
							obj.setSearch(0);
							obj.setSearchStandard(0);
							obj.setSearchFederal(0);
							obj.setToc(0);
							obj.setDownload(0);

							if (type == 1) {
								obj.setToc(count);
							} else if (type == 2 && access != null && access == 1) {
								obj.setFullAccess(count);
							} else if (type == 2 && access != null && access == 2) {
								if (stype != null && stype == 1) {
									obj.setRefusedLicense(count);
								} else if (stype != null && stype == 2) {
									obj.setRefusedConcurrent(count);
								} else {
									obj.setFullRefused(count);
								}
							} else if (type == 3) {
								if (stype != null && stype == 1) {
									obj.setSearchStandard(count);
								} else if (stype != null && stype == 2) {
									obj.setSearchFederal(count);
								} else {
									obj.setSearch(count);
								}
							} else if (type == 4) {
								obj.setDownload(count);
							}
							obj.setLang(o.getPublications().getLang() != null ? o.getPublications().getLang() : "");//语种
							this.daoFacade.getSuppDao().insert(obj);
						}

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

	@Override
	public List<SSupplier> getSuppList(Map<String, Object> condition, String sort) throws Exception {
		List<SSupplier> list = null;
		try {
			list = this.daoFacade.getSuppDao().getList(condition, sort);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Laccess.getBookStatisticalList.get.error", e);//获取统计信息分页列表失败！
		}
		return list;
	}

	@Override
	public Integer getSSupplierCount(Map<String, Object> condition) throws Exception {
		Integer count = 0;
		try {
			count = this.daoFacade.getSuppDao().getCount(condition);
		} catch (Exception e) {
			// 获取产品信息总数失败！
			e.printStackTrace();
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Laccess.count.get.error", e);// 获取统计总数失败！
		}
		return count;
	}

	@Override
	public List<SSupplier> getTocList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<SSupplier> list = null;
		try {
			list = this.daoFacade.getSuppDao().getTocList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Laccess.getBookStatisticalList.get.error", e);//获取统计TOC信息分页列表失败！
		}
		return list;
	}

	@Override
	public List<SSupplier> getFullAccessList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<SSupplier> list = null;
		try {
			list = this.daoFacade.getSuppDao().getFullAccessList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Laccess.getBookStatisticalList.get.error", e);//获取统计TOC信息分页列表失败！
		}
		return list;
	}

	@Override
	public List<SSupplier> getFullRefusedList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<SSupplier> list = null;
		try {
			list = this.daoFacade.getSuppDao().getFullRefusedList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Laccess.getBookStatisticalList.get.error", e);//获取统计TOC信息分页列表失败！
		}
		return list;
	}

	@Override
	public List<SSupplier> getDownloadList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<SSupplier> list = null;
		try {
			list = this.daoFacade.getSuppDao().getDownloadList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Laccess.getBookStatisticalList.get.error", e);//获取统计TOC信息分页列表失败！
		}
		return list;
	}

	@Override
	public List<SSupplier> getSearchList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<SSupplier> list = null;
		try {
			list = this.daoFacade.getSuppDao().getSearchList(condition, sort, pageCount, page);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Laccess.getBookStatisticalList.get.error", e);//获取统计TOC信息分页列表失败！
		}
		return list;
	}

	@Override
	public Integer getSSupplierCountGroupby(Map<String, Object> condition, String group) throws Exception {
		Integer num = 0;
		try {
			num = this.daoFacade.getSuppDao().getGroupbyCount(condition, group);
		} catch (Exception e) {
			throw new CcsException((e instanceof CcsException) ? ((CcsException) e).getPrompt() : "Laccess.count.get.error", e);//获取统计信息总数失败！
		}
		return num;
	}

	public void getSupplierFast(Integer type, Integer access, Integer stype, String stimes, String year, String sourceid) throws Exception {
		List<LAccess> list = null;
		boolean flag = false;
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			BookSuppliersForm form = new BookSuppliersForm();
			if (sourceid != null) {//机构ID
				condition.put("isInstitutionId", sourceid);
			}
			condition.put("year", year);
			if (stimes != null) {
				form.setStartMonth(stimes);
				form.setEndMonth(stimes);
			}
			condition.put("startMonth", form.getStartMonth());
			condition.put("endMonth", form.getEndMonth());
			condition.put("type", type);

			switch (type) {
			case 2:
				if (stype != null) {//1-没有License,2-超出并发数
					condition.put("", stype);
				}
				condition.put("access", access);

				break;
			case 3:
				if (stype != null) {//1-常规检索 , 2-高级检索 , 3-分类法点击---！（标准检索 联邦检索）
					condition.put("", stype);
				}
				break;
			default:
				break;
			}
			//			condition.put("pubId", "402881b6453f65a60145446e4c1f00c5");
			list = this.daoFacade.getlAccessDao().getCounterList11(condition, "");
			if (list != null && list.size() > 0) {
				flag = true;
			}
			String[] months = new String[] { "", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
			Map<String, SSupplier> records = new HashMap<String, SSupplier>();
			StringBuffer sbuffer = new StringBuffer();
			if (flag) {
				for (LAccess o : list) {
					Integer sMonth = stimes != null ? Integer.parseInt(stimes) : 1;
					Integer eMonth = stimes != null ? sMonth : 12;
					for (int i = sMonth; i <= eMonth; i++) {

						String month = months[i];
						int count = 0;//每月总数
						if (i == 1) {
							count += o.getMonth1() == null ? 0 : o.getMonth1();
						} else if (i == 2) {
							count += o.getMonth2() == null ? 0 : o.getMonth2();
						} else if (i == 3) {
							count += o.getMonth3() == null ? 0 : o.getMonth3();
						} else if (i == 4) {
							count += o.getMonth4() == null ? 0 : o.getMonth4();
						} else if (i == 5) {
							count += o.getMonth5() == null ? 0 : o.getMonth5();
						} else if (i == 6) {
							count += o.getMonth6() == null ? 0 : o.getMonth6();
						} else if (i == 7) {
							count += o.getMonth7() == null ? 0 : o.getMonth7();
						} else if (i == 8) {
							count += o.getMonth8() == null ? 0 : o.getMonth8();
						} else if (i == 9) {
							count += o.getMonth9() == null ? 0 : o.getMonth9();
						} else if (i == 10) {
							count += o.getMonth10() == null ? 0 : o.getMonth10();
						} else if (i == 11) {
							count += o.getMonth11() == null ? 0 : o.getMonth11();
						} else if (i == 12) {
							count += o.getMonth12() == null ? 0 : o.getMonth12();
						}

						sbuffer.delete(0, sbuffer.length());
						sbuffer.append("datetime:").append(o.getYear()).append("-").append(month).append(";");
						sbuffer.append("institutionId:").append(o.getInstitutionId()).append(";");
						if (o.getPublications().getType() != 1 && o.getPublications().getType() != 3) {
							String issn = o.getPublications().getCode().length() > 9 ? o.getPublications().getCode().substring(0, 9) : o.getPublications().getCode();
							sbuffer.append("issn:").append(issn).append(";");
							sbuffer.append("eissn:").append(issn).append(";");
						} else {
							sbuffer.append("pubId:").append(o.getPublications().getId()).append(";");
						}
						String key = sbuffer.toString();
						if (records.containsKey(key)) {
							SSupplier obj = records.get(key);
							if (o.getPublications().getType() != 1 && o.getPublications().getType() != 3) {
								if (type == 1) {
									count += obj.getToc() != null && !"".equals(obj.getToc().toString()) ? obj.getToc() : 0;
									obj.setToc(count);
								} else if (type == 2 && access != null && access == 1) {
									count += obj.getFullAccess() != null && !"".equals(obj.getFullAccess().toString()) ? obj.getFullAccess() : 0;
									obj.setFullAccess(count);
								} else if (type == 2 && access != null && access == 2) {
									if ((stype != null) && stype == 1) {
										count += obj.getRefusedLicense() != null && !"".equals(obj.getRefusedLicense().toString()) ? obj.getRefusedLicense() : 0;
										obj.setRefusedLicense(count);
									} else if (stype != null && stype == 2) {
										count += obj.getRefusedConcurrent() != null && !"".equals(obj.getRefusedConcurrent().toString()) ? obj.getRefusedConcurrent() : 0;
										obj.setRefusedConcurrent(count);
									} else {
										count += obj.getFullRefused() != null && !"".equals(obj.getFullRefused().toString()) ? obj.getFullRefused() : 0;
										obj.setFullRefused(count);
									}
								} else if (type == 3) {
									if (stype != null && stype == 1) {
										count += obj.getSearchStandard() != null && !"".equals(obj.getSearchStandard().toString()) ? obj.getSearchStandard() : 0;
										obj.setSearchStandard(count);
									} else if (stype != null && stype == 2) {
										count += obj.getSearchFederal() != null && !"".equals(obj.getSearchFederal().toString()) ? obj.getSearchFederal() : 0;
										obj.setSearchFederal(count);
									} else {
										count += obj.getSearch() != null && !"".equals(obj.getSearch().toString()) ? obj.getSearch() : 0;
										obj.setSearch(count);
									}
								} else if (type == 4) {
									count += obj.getDownload() != null && !"".equals(obj.getDownload().toString()) ? obj.getDownload() : 0;
									obj.setDownload(count);
								}

							} else if (type == 1) {
								obj.setToc(obj.getToc() + count);
							} else if (type == 2 && access != null && access == 1) {
								obj.setFullAccess(obj.getFullAccess() + count);
							} else if (type == 2 && access != null && access == 2) {
								if (stype != null && stype == 1) {
									obj.setRefusedLicense(obj.getRefusedLicense() + count);
								} else if (stype != null && stype == 2) {
									obj.setRefusedConcurrent(obj.getRefusedConcurrent() + count);
								} else {
									obj.setFullRefused(obj.getFullRefused() + count);
								}
							} else if (type == 3) {
								if (stype != null && stype == 1) {
									obj.setSearchStandard(obj.getSearchStandard() + count);
								} else if (stype != null && stype == 2) {
									obj.setSearchFederal(obj.getSearchFederal() + count);
								} else {
									obj.setSearch(obj.getSearch() + count);
								}
							} else if (type == 4) {
								obj.setDownload(obj.getDownload() + count);
							}
							obj.setLang(o.getPublications().getLang() != null ? o.getPublications().getLang() : "");//语种
							records.put(key, obj);//records.get("datetime:2015-02;institutionId:ff8080813c69a8fd013c6a554b350253;issn:2326-3482;eissn:2326-3482;")
						} else {
							SSupplier obj = new SSupplier();
							obj.setLang(o.getPublications().getLang() != null ? o.getPublications().getLang() : "");//语种
							obj.setInstitutionid(o.getInstitutionId());

							if (o.getPublications().getType() != 1 && o.getPublications().getType() != 3) {
								//文章的计数都算到所属刊上
								if (o.getPublications().getType() != 2) {
									PPublications pub = (PPublications) this.daoFacade.getpPublicationsDao().get(PPublications.class.getName(), o.getPublications().getId());
									obj.setPubId(pub.getPublications().getId());
									obj.setIssn(pub.getPublications().getCode() != null ? pub.getPublications().getCode() : "");
									obj.setEissn(pub.getPublications().getEissn() != null ? pub.getPublications().getEissn() : "");
									obj.setTitle(pub.getPublications().getTitle() != null ? pub.getPublications().getTitle() : "");
									obj.setType(2);
								} else {
									obj.setPubId(o.getPublications().getId());
									obj.setIssn(o.getPublications().getCode() != null ? o.getPublications().getCode() : "");
									obj.setEissn(o.getPublications().getEissn() != null ? o.getPublications().getEissn() : "");
									obj.setTitle(o.getPublications().getTitle() != null ? o.getPublications().getTitle() : "");
									obj.setAuthor(o.getPublications().getAuthor() != null ? o.getPublications().getAuthor() : "");
									obj.setType(o.getPublications().getType());
								}
								obj.setIsbn("");
							} else {
								obj.setPubId(o.getPublications().getId() != null ? o.getPublications().getId() : "");
								obj.setIsbn(o.getPublications().getCode() != null ? o.getPublications().getCode() : "");
								obj.setType(o.getPublications().getType() != null ? o.getPublications().getType() : null);
								obj.setTitle(o.getPublications().getTitle() != null ? o.getPublications().getTitle() : "");
								obj.setAuthor(o.getPublications().getAuthor() != null ? o.getPublications().getAuthor() : "");
								obj.setIssn("");
								obj.setEissn("");
							}

							obj.setPubName(o.getPublications().getPublisher().getName() != null ? o.getPublications().getPublisher().getName() : "");
							String str = o.getYear() + "-" + month;
							obj.setSdate(str);
							obj.setYear(Integer.parseInt(o.getYear()));
							obj.setMonth(Integer.parseInt(month));
							obj.setPlatform(o.getPlatform() != null ? o.getPlatform() : "");
							//初始值赋值 0 索引查询不能有null 值 会影响查询速度 by heqing.yang 2015-01-24
							obj.setFullAccess(0);
							obj.setFullRefused(0);
							obj.setRefusedLicense(0);
							obj.setRefusedConcurrent(0);
							obj.setSearch(0);
							obj.setSearchStandard(0);
							obj.setSearchFederal(0);
							obj.setToc(0);
							obj.setDownload(0);

							if (type == 1) {
								obj.setToc(count);
							} else if (type == 2 && access != null && access == 1) {
								obj.setFullAccess(count);
							} else if (type == 2 && access != null && access == 2) {
								if (stype != null && stype == 1) {
									obj.setRefusedLicense(count);
								} else if (stype != null && stype == 2) {
									obj.setRefusedConcurrent(count);
								} else {
									obj.setFullRefused(count);
								}
							} else if (type == 3) {
								if (stype != null && stype == 1) {
									obj.setSearchStandard(count);
								} else if (stype != null && stype == 2) {
									obj.setSearchFederal(count);
								} else {
									obj.setSearch(count);
								}
							} else if (type == 4) {
								obj.setDownload(count);
							}
							records.put(key, obj);
						}

					}
				}
				if (records != null && records.size() > 0) {
					for (Entry<String, SSupplier> entry : records.entrySet()) {
						String[] kvs = entry.getKey().split(";");
						Map<String, Object> map = new HashMap<String, Object>();
						for (String kv : kvs) {
							String[] single = kv.split(":");
							map.put(single[0], single[1]);
						}
						List<SSupplier> supList = this.daoFacade.getSuppDao().getList(map, "");
						//						if(entry.getValue().getFullAccess()>0){
						//							System.out.println(entry.getValue().getFullAccess());
						//						}
						if (supList == null || supList.isEmpty()) {
							this.daoFacade.getSuppDao().insert(entry.getValue());
						} else {
							supList.get(0).setFullAccess(entry.getValue().getFullAccess() > 0 ? entry.getValue().getFullAccess() : supList.get(0).getFullAccess());
							supList.get(0).setFullRefused(entry.getValue().getFullRefused() > 0 ? entry.getValue().getFullRefused() : supList.get(0).getFullRefused());
							supList.get(0).setRefusedLicense(entry.getValue().getRefusedLicense() > 0 ? entry.getValue().getRefusedLicense() : supList.get(0).getRefusedLicense());
							supList.get(0).setRefusedConcurrent(entry.getValue().getRefusedConcurrent() > 0 ? entry.getValue().getRefusedConcurrent() : supList.get(0).getRefusedConcurrent());
							supList.get(0).setSearch(entry.getValue().getSearch() > 0 ? entry.getValue().getSearch() : supList.get(0).getSearch());
							supList.get(0).setSearchStandard(entry.getValue().getSearchStandard() > 0 ? entry.getValue().getSearchStandard() : supList.get(0).getSearchStandard());
							supList.get(0).setSearchFederal(entry.getValue().getSearchFederal() > 0 ? entry.getValue().getSearchFederal() : supList.get(0).getSearchFederal());
							supList.get(0).setToc(entry.getValue().getToc() > 0 ? entry.getValue().getToc() : supList.get(0).getToc());
							supList.get(0).setDownload(entry.getValue().getDownload() > 0 ? entry.getValue().getDownload() : supList.get(0).getDownload());
							this.daoFacade.getSuppDao().update(supList.get(0), SSupplier.class.getName(), supList.get(0).getId(), null);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
