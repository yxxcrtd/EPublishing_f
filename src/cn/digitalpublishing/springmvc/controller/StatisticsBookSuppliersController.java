package cn.digitalpublishing.springmvc.controller;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.SSupplier;
import cn.digitalpublishing.po.BSource;
import cn.digitalpublishing.springmvc.form.product.SSupplierForm;

@Controller
@RequestMapping("/pages/BookSuppliers")
public class StatisticsBookSuppliersController extends BaseController {
	
	/**
	 * 提供商Toc下载统计列表
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/form/managerToc")
	public ModelAndView managerToc(HttpServletRequest request,HttpServletResponse response,SSupplierForm form){
		String forwardString="logs/entireStatisticalJournal";
		Map<String,Object> model = new HashMap<String,Object>();
		try {
			List<SSupplier> list=null;
			/*HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("userStaffId", "ff8080814081643801408675ad6303cc");
			request.getSession().setAttribute("loginInfoMap", map);*/

			if(request.getSession().getAttribute("loginInfoMap")!=null){
			
				Map<String,Object> loginInfoMap = (HashMap<String,Object>)request.getSession().getAttribute("loginInfoMap");
				if(loginInfoMap.get("userStaffId")!=null && !"0".equals(loginInfoMap.get("userStaffId"))){
					BSource source=this.configureService.getBSource(loginInfoMap.get("userStaffId").toString());
					if(source!=null){
						String pubType=request.getParameter("pubtype");//1-图书2-期刊
						form.setUrl(request.getRequestURL().toString());
							Map<String, Object> condition = new HashMap<String,Object>();
							condition.put("sourceId", source.getId());
							condition.put("staryear", form.getStartyear());
							condition.put("endyear",form.getEndtyear());
							if(pubType!=null && "1".equals(pubType)){
								condition.put("pubtypes", new Integer[] { 1, 3 });
								forwardString="logs/tocbook";
							}else if(pubType!=null && "2".equals(pubType)){
								condition.put("pubtypes", new Integer[] { 2, 4 });
								forwardString="logs/tocjournal";
							}
								form.setType(Integer.parseInt(pubType));
								form.setCount(this.bookSuppliersService.getSSupplierCountGroupby(condition, " group by a.sourceid,a.contentid,a.title,a.type,a.author,a.isbn,a.issn,a.eissn,a.pubName,a.platform "));
								list=this.bookSuppliersService.getTocList(condition, " group by a.sourceid,a.contentid,a.title,a.type,a.author,a.isbn,a.issn,a.eissn,a.pubName,a.platform ", form.getPageCount(),form.getCurpage());
						}
					}
				}
			
			model.put("list", list);
			model.put("form", form);
		} catch (Exception e) {
			form.setMsg((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		
		return new ModelAndView(forwardString, model);
	}
	/**
	 * 提供商搜索统计列表
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/form/managerSearch")
	public ModelAndView managerSearch(HttpServletRequest request,HttpServletResponse response,SSupplierForm form){
		String forwardString="logs/entireStatisticalJournal";
		Map<String,Object> model = new HashMap<String,Object>();
		try {
			List<SSupplier> list=null;
			/*HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("userStaffId", "ff8080813ca7cd1d013d43b066750009");
			request.getSession().setAttribute("loginInfoMap", map);*/
			
			if(request.getSession().getAttribute("loginInfoMap")!=null){
				
				Map<String,Object> loginInfoMap = (HashMap<String,Object>)request.getSession().getAttribute("loginInfoMap");
				if(loginInfoMap.get("userStaffId")!=null && !"0".equals(loginInfoMap.get("userStaffId"))){
					BSource source=this.configureService.getBSource(loginInfoMap.get("userStaffId").toString());
					if(source!=null){
						String pubType=request.getParameter("pubtype");//1-图书2-期刊
						form.setUrl(request.getRequestURL().toString());
							Map<String, Object> condition = new HashMap<String,Object>();
							condition.put("sourceId", source.getId());
							condition.put("staryear", form.getStartyear());
							condition.put("endyear",form.getEndtyear());
							if(pubType!=null && "1".equals(pubType)){
								condition.put("pubtypes", new Integer[] { 1, 3 });
								forwardString="logs/searchesbook";
							}else if(pubType!=null && "2".equals(pubType)){
								condition.put("pubtypes", new Integer[] { 2, 4 });
								forwardString="logs/searchesjournal";
							}
								form.setType(Integer.parseInt(pubType));
								form.setCount(this.bookSuppliersService.getSSupplierCountGroupby(condition, " group by a.sourceid,a.contentid,a.title,a.type,a.author,a.isbn,a.issn,a.eissn,a.pubName,a.platform "));
								list=this.bookSuppliersService.getSearchList(condition, " group by a.sourceid,a.contentid,a.title,a.type,a.author,a.isbn,a.issn,a.eissn,a.pubName,a.platform ", form.getPageCount(),form.getCurpage());
						}
					}
				}
			
			model.put("list", list);
			model.put("form", form);
		} catch (Exception e) {
			form.setMsg((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		
		return new ModelAndView(forwardString, model);
	}
	/**
	 * 提供商全文访问统计列表
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/form/managerAccess")
	public ModelAndView managerAccess(HttpServletRequest request,HttpServletResponse response,SSupplierForm form){
		String forwardString="logs/entireStatisticalJournal";
		Map<String,Object> model = new HashMap<String,Object>();
		try {
			List<SSupplier> list=null;
			/*HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("userStaffId", "ff8080813ca7cd1d013d43b066750009");
			request.getSession().setAttribute("loginInfoMap", map);*/
			
			if(request.getSession().getAttribute("loginInfoMap")!=null){
				
				Map<String,Object> loginInfoMap = (HashMap<String,Object>)request.getSession().getAttribute("loginInfoMap");
				if(loginInfoMap.get("userStaffId")!=null && !"0".equals(loginInfoMap.get("userStaffId"))){
					BSource source=this.configureService.getBSource(loginInfoMap.get("userStaffId").toString());
					if(source!=null){
						String pubType=request.getParameter("pubtype");//1-图书2-期刊
						form.setUrl(request.getRequestURL().toString());
							Map<String, Object> condition = new HashMap<String,Object>();
							condition.put("sourceId", source.getId());
							condition.put("staryear", form.getStartyear());
							condition.put("endyear",form.getEndtyear());
							if(pubType!=null && "1".equals(pubType)){
								condition.put("pubtypes", new Integer[] { 1, 3 });
								forwardString="logs/entireStatistical";
							}else if(pubType!=null && "2".equals(pubType)){
								condition.put("pubtypes", new Integer[] { 2, 4 });
								forwardString="logs/entireStatisticalJournal";
							}
								//区分期刊和图书
								form.setType(Integer.parseInt(pubType));
								form.setCount(this.bookSuppliersService.getSSupplierCountGroupby(condition, " group by a.sourceid,a.contentid,a.title,a.type,a.author,a.isbn,a.issn,a.eissn,a.pubName,a.platform "));
								list=this.bookSuppliersService.getFullAccessList(condition, " group by a.sourceid,a.contentid,a.title,a.type,a.author,a.isbn,a.issn,a.eissn,a.pubName,a.platform ", form.getPageCount(),form.getCurpage());
						}
					}
				}
			
			model.put("list", list);
			model.put("form", form);
		} catch (Exception e) {
			form.setMsg((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		
		return new ModelAndView(forwardString, model);
	}
	/**
	 * 提供商下载统计列表
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/form/managerDownload")
	public ModelAndView managerDownload(HttpServletRequest request,HttpServletResponse response,SSupplierForm form){
		String forwardString="logs/entireStatisticalJournal";
		Map<String,Object> model = new HashMap<String,Object>();
		try {
			List<SSupplier> list=null;
			/*HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("userStaffId", "ff8080814084e493014086580c2920d1");
			request.getSession().setAttribute("loginInfoMap", map);*/
			
			if(request.getSession().getAttribute("loginInfoMap")!=null){
				
				Map<String,Object> loginInfoMap = (HashMap<String,Object>)request.getSession().getAttribute("loginInfoMap");
				if(loginInfoMap.get("userStaffId")!=null && !"0".equals(loginInfoMap.get("userStaffId"))){
					BSource source=this.configureService.getBSource(loginInfoMap.get("userStaffId").toString());
					if(source!=null){
						String pubType=request.getParameter("pubtype");//1-图书2-期刊
						form.setUrl(request.getRequestURL().toString());
							Map<String, Object> condition = new HashMap<String,Object>();
							condition.put("sourceId", source.getId());
							condition.put("staryear", form.getStartyear());
							condition.put("endyear",form.getEndtyear());
							if(pubType!=null && "1".equals(pubType)){
								condition.put("pubtypes", new Integer[] { 1, 3 });
								forwardString="logs/downloadbook";
							}else if(pubType!=null && "2".equals(pubType)){
								condition.put("pubtypes", new Integer[] { 2, 4 });
								forwardString="logs/downloadjournal";
							}
						
								//区分期刊和图书
								form.setType(Integer.parseInt(pubType));
								form.setCount(this.bookSuppliersService.getSSupplierCountGroupby(condition, " group by a.sourceid,a.contentid,a.title,a.type,a.author,a.isbn,a.issn,a.eissn,a.pubName,a.platform "));
								list=this.bookSuppliersService.getDownloadList(condition, " group by a.sourceid,a.contentid,a.title,a.type,a.author,a.isbn,a.issn,a.eissn,a.pubName,a.platform ", form.getPageCount(),form.getCurpage());
						}
					}
				}
			
			model.put("list", list);
			model.put("form", form);
		} catch (Exception e) {
			form.setMsg((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		
		return new ModelAndView(forwardString, model);
	}
	/**
	 * 提供商拒访统计列表
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/form/managerRefused")
	public ModelAndView managerRefused(HttpServletRequest request,HttpServletResponse response,SSupplierForm form){
		String forwardString="logs/entireStatisticalJournal";
		Map<String,Object> model = new HashMap<String,Object>();
		try {
			List<SSupplier> list=null;
			/*HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("userStaffId", "ff8080814084e493014086580c2920d1");
			request.getSession().setAttribute("loginInfoMap", map);*/
			
			if(request.getSession().getAttribute("loginInfoMap")!=null){
				
				Map<String,Object> loginInfoMap = (HashMap<String,Object>)request.getSession().getAttribute("loginInfoMap");
				if(loginInfoMap.get("userStaffId")!=null && !"0".equals(loginInfoMap.get("userStaffId"))){
					BSource source=this.configureService.getBSource(loginInfoMap.get("userStaffId").toString());
					if(source!=null){
						String pubType=request.getParameter("pubtype");//1-图书2-期刊
						form.setUrl(request.getRequestURL().toString());
							Map<String, Object> condition = new HashMap<String,Object>();
							condition.put("sourceId", source.getId());
							condition.put("staryear", form.getStartyear());
							condition.put("endyear",form.getEndtyear());
							if(pubType!=null && "1".equals(pubType)){
								condition.put("pubtypes", new Integer[] { 1, 3 });
								forwardString="logs/refusebook";
							}else if(pubType!=null && "2".equals(pubType)){
								condition.put("pubtypes", new Integer[] { 2, 4 });
								forwardString="logs/refusejournal";
							}
								//区分期刊和图书
								form.setType(Integer.parseInt(pubType));
								form.setCount(this.bookSuppliersService.getSSupplierCountGroupby(condition, " group by a.sourceid,a.contentid,a.title,a.type,a.author,a.isbn,a.issn,a.eissn,a.pubName,a.platform "));
								list=this.bookSuppliersService.getFullRefusedList(condition, " group by a.sourceid,a.contentid,a.title,a.type,a.author,a.isbn,a.issn,a.eissn,a.pubName,a.platform ", form.getPageCount(),form.getCurpage());
						}
					}
				}
			
			model.put("list", list);
			model.put("form", form);
		} catch (Exception e) {
			form.setMsg((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		
		return new ModelAndView(forwardString, model);
	}
	/**
	 * 下载全文访问excel记录
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/downloadDownload")
	public ModelAndView downloadDownload(HttpServletRequest request,HttpServletResponse response,SSupplierForm form) throws Exception{
		try {
			List<SSupplier> list=null;
			/***测试用的***/
			/*HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("userStaffId", "ff8080814084e493014086580c2920d1");
			
			request.getSession().setAttribute("loginInfoMap", map);*/
			
			if(request.getSession().getAttribute("loginInfoMap")!=null){
				@SuppressWarnings("unchecked")
				Map<String,Object> loginInfoMap = (HashMap<String,Object>)request.getSession().getAttribute("loginInfoMap");
				if(loginInfoMap.get("userStaffId")!=null && !"0".equals(loginInfoMap.get("userStaffId"))){
					BSource source=this.configureService.getBSource(loginInfoMap.get("userStaffId").toString());
					if(source!=null){
						String pubType=request.getParameter("pubtype");//1-图书2-期刊
						form.setUrl(request.getRequestURL().toString());
						Date date= new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
						String dateString = formatter.format(date);//当前年
						dateString=dateString+"-"+"01";
						String dateyear=sdf.format(date);//当前年月
						String eyear=sdf1.format(date);//当前年
						String staryear=form.getStartyear()!=null&&!"".equals(form.getStartyear())?form.getStartyear():dateString;
						String endyear=form.getEndtyear()!=null&&!"".equals(form.getEndtyear())?form.getEndtyear():eyear;
						Map<String, Object> condition = new HashMap<String,Object>();
						condition.put("sourceId", source.getId());
						condition.put("staryear", staryear);//默认下载当前年月
						condition.put("endyear",endyear);
						if(pubType!=null && "1".equals(pubType)){
							condition.put("pubtypes", new Integer[] { 1, 3 });
						}else if(pubType!=null && "2".equals(pubType)){
							condition.put("pubtypes", new Integer[] { 2, 4 });
						}
						Map<String, Object> xMap = new LinkedHashMap<String, Object>();// X 轴坐标  key- contentID value- x
						Map<String, Object> yMap = new LinkedHashMap<String, Object>();// Y 轴坐标  key- 月-年 value- y
						Map<String, Object> downloadMap= new LinkedHashMap<String, Object>();//结果集 总数 -每月分别统计总数
						String [] str={"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};// 月份数组
							
						list=this.bookSuppliersService.getSuppList(condition, " order by a.contentid,a.year,a.month ");
							
						if(list!=null&&list.size()>0){
							int flag=0;// 1-图书  2-期刊 0-初始值
							int numX=9;//行号
							int numY=7;//列号
							int count=0;	
							/*生成excel start  heqing.yang  2014-11-02  */
							String time =StatisticsBookSuppliersController.getNowbatchCooe();
							//输出的excel文件工作表名
							String worksheet="";
							yMap.put("count", numY);
							StringBuffer sbb= new StringBuffer();/*生成excel start  heqing.yang  2014-11-02  */
							if(pubType!=null&&"2".equals(pubType)){
								worksheet="JR1.CNPEADING."+time;
								sbb.append("Journal Report 1 (R4);");
								flag=2;
							}else if(pubType!=null&&"1".equals(pubType)){
								worksheet="BR1.CNPEADING."+time;
								sbb.append("Book Report 1 (R4);");
								flag=1;
							}
								sbb.append(" ;");
								sbb.append(" ;");
								sbb.append("Period covered by Report:;");
								sbb.append("yyyy-mm-dd to yyyy-mm-dd;");
								sbb.append("Date run:;");
								sbb.append("yyyy-mm-dd;");
								
								StringBuffer sb= new StringBuffer();
								if(flag==2){
									sb.append("Journal;");
									sb.append("Publisher;");
									sb.append("Platform;");
									sb.append("Journal DOI;");
									sb.append("Proprietary Identifier;");
									
									sb.append("Print ISSN;");
									sb.append("Online ISSN;");
									sb.append("Reporting Period Total;"); 
								}else if(flag==1){
									sb.append(" ;");
									sb.append("Publisher;");
									sb.append("Platform;");
									sb.append("Book DOI;");
									sb.append("Proprietary Identifier;");
									sb.append("ISBN;");
									sb.append("ISSN;");
									sb.append("Reporting Period Total;");
								}
								
//								2013-01   2014-09
								String[] sy=staryear.split("-");
								String[] ey=endyear.split("-");
								Integer start=Integer.parseInt(sy[0]);
								Integer end=Integer.parseInt(ey[0]);
								int sTartMonth=1;
								int eNdMonth=12;
								for(int j=start;j<=end;j++){
									
									if(sTartMonth!=Integer.parseInt(sy[1])){
										sTartMonth=Integer.parseInt(sy[1]);
									}
									 if(j==end){
										if(eNdMonth!=Integer.parseInt(ey[1])){
											eNdMonth=Integer.parseInt(ey[1]);
										} 
									 } 
									 for(int k=sTartMonth;k<=eNdMonth;k++){
										String month = "";
										numY++;// Y轴
										month=str[k];//月份一维数组
										sb.append(month + "-" + j + ";");
										String dete = String.valueOf(k);
										if (dete.length() == 1) {
											dete = "0" + dete;
										}
										yMap.put(j+"-"+dete, numY);
									 }
										sTartMonth=1;
								}
								
								StringBuffer sbf= new StringBuffer();
								if(flag==1){
									sbf.append("Total for all titles;");
								}else if(flag==2){
									sbf.append("Total for all journals;");
								}
								
								sbf.append(" ;");
								if(flag==1){
									sbf.append(" ;");
								}else if(flag==2){
									sbf.append("Platform Z ;");
								}
								
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");//总数列
								
								String title[] = sb.toString().split(";");
								String titles[]=sbb.toString().split(";");
								String titsbf[]=sbf.toString().split(";");
								WritableWorkbook workbook;
								OutputStream os = response.getOutputStream();
								response.reset();// 清空输出流
								response.setHeader("Content-disposition", "attachment; filename=" + worksheet + ".xls");// 设定输出文件头
								response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
								workbook = Workbook.createWorkbook(os);
								WritableSheet sheet = workbook.createSheet(worksheet, 0);
								
								int num=0;
								for(int i=0;i<titles.length;i++){
									sheet.addCell(new Label(0, i, titles[i]));
									num++;
								}
								for (int i = 0; i < num; i++) {
									if(i==0){
										if(pubType!=null&&"2".equals(pubType)){
											sheet.addCell(new Label(1, i, "Number of Successfull Full-Text Article Requests by Month and Journal"));
										}else if(pubType!=null&&"1".equals(pubType)){
											sheet.addCell(new Label(1, i, "Number of Successfull T	Title Requests by Month and Title"));
										}
										
									}else if(i==1){
										sheet.addCell(new Label(1,i , " "));
									}else if(i==2){
										sheet.addCell(new Label(1,i , " "));
									}else if(i==3){
										sheet.addCell(new Label(1,i , "Cnpereading.com"));
									}else if(i==4){
										sheet.addCell(new Label(1,i , dateyear));//条件值
									}else if(i==5){
										sheet.addCell(new Label(1,i , " "));
									}else if(i==6){
										String s =StatisticsBookSuppliersController.getNowbatchCooe();
										sheet.addCell(new Label(1,i, s));
									}
								}
								
								for (int i =0 ; i < title.length; i++) {
									
									// Label(列号,行号 ,内容 )
									sheet.addCell(new Label(i, num, title[i]));
								}
								for (int i =0 ; i < titsbf.length; i++) {
									
									// Label(列号,行号 ,内容 )
									sheet.addCell(new Label(i, num+1, titsbf[i]));
								}
								int counta =0;//统计大总数
								int countb =0;//统计每月大总数
								for(int i=0;i<list.size();i++){
									if(xMap!=null&&xMap.containsKey(list.get(i).getPubId())){
										
										count+=list.get(i).getDownload()!=null?list.get(i).getDownload():0;
										counta=list.get(i).getDownload()!=null?list.get(i).getDownload():0;
										countb=list.get(i).getDownload()!=null?list.get(i).getDownload():0;
										if(downloadMap.containsKey(list.get(i).getSdate())){//存在相同月分的值相加  每月一列总计
											downloadMap.put(list.get(i).getSdate(), Integer.parseInt(downloadMap.get(list.get(i).getSdate()).toString())+countb);
										}else{
											downloadMap.put(list.get(i).getSdate(), list.get(i).getDownload()!=null?list.get(i).getDownload():0);
										}
										downloadMap.put("downloadCount", Integer.parseInt(downloadMap.get("downloadCount").toString())+counta);//总数
										
										sheet.addCell(new Label(Integer.parseInt(yMap.get(list.get(i).getSdate()).toString()), Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()),String.valueOf(list.get(i).getDownload()!=null?list.get(i).getDownload():0)));
										sheet.addCell(new Label(Integer.parseInt(yMap.get("count").toString()), Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), String.valueOf(count)));
									}else{
										count=0;
										xMap.put(list.get(i).getPubId(),numX );//行号
										
										//生成excel
										sheet.addCell(new Label(0, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getTitle()!=null?list.get(i).getTitle():""));
										sheet.addCell(new Label(1, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getPubName()!=null?list.get(i).getPubName():""));
										sheet.addCell(new Label(2, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getPlatform()!=null?list.get(i).getPlatform():""));
										sheet.addCell(new Label(3, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), ""));
										sheet.addCell(new Label(4, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), ""));
										if(flag==1){
											sheet.addCell(new Label(5, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getIsbn()!=null?list.get(i).getIsbn():""));
											sheet.addCell(new Label(6, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getIssn()!=null?list.get(i).getIssn():""));
										}else if (flag==2){
											sheet.addCell(new Label(5, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getIssn()!=null?list.get(i).getIssn():""));
											sheet.addCell(new Label(6, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getEissn()!=null?list.get(i).getEissn():""));
										}
										sheet.addCell(new Label(Integer.parseInt(yMap.get(list.get(i).getSdate()).toString()), Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()),String.valueOf(list.get(i).getDownload()!=null?list.get(i).getDownload():0)));
										count+=list.get(i).getDownload()!=null?list.get(i).getDownload():0;
										counta=list.get(i).getDownload()!=null?list.get(i).getDownload():0;//每月总数大统计
										countb=list.get(i).getDownload()!=null?list.get(i).getDownload():0;
										if(downloadMap.containsKey("downloadCount")){
											downloadMap.put("downloadCount", Integer.parseInt(downloadMap.get("downloadCount").toString())+counta);//总数
										}else{
											downloadMap.put("downloadCount", counta);//总数
										}
										
										if(downloadMap.containsKey(list.get(i).getSdate())){//存在相同月分的值相加
											downloadMap.put(list.get(i).getSdate(), Integer.parseInt(downloadMap.get(list.get(i).getSdate()).toString())+countb);
										}else{
											downloadMap.put(list.get(i).getSdate(), countb);
										}
										numX++;
									
									}
									sheet.addCell(new Label(Integer.parseInt(yMap.get(list.get(i).getSdate()).toString()), 8, downloadMap.get(list.get(i).getSdate()).toString()));//每月大总数
									sheet.addCell(new Label(Integer.parseInt(yMap.get("count").toString()), 8, downloadMap.get("downloadCount").toString()));//大总数
								}
									
								workbook.write();
								workbook.close();
								os.close();
						}	
						}
						}
					}
					
				
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	/**
	 * 下载全文访问excel记录
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/downloadAccess")
	public ModelAndView downloadAccess(HttpServletRequest request,HttpServletResponse response,SSupplierForm form) throws Exception{
		try {
			List<SSupplier> list=null;
			/***测试用的***/
			/*HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("userStaffId", "ff8080814084e493014086580c2920d1");
			
			request.getSession().setAttribute("loginInfoMap", map);*/
			
			if(request.getSession().getAttribute("loginInfoMap")!=null){
				@SuppressWarnings("unchecked")
				Map<String,Object> loginInfoMap = (HashMap<String,Object>)request.getSession().getAttribute("loginInfoMap");
				if(loginInfoMap.get("userStaffId")!=null && !"0".equals(loginInfoMap.get("userStaffId"))){
					BSource source=this.configureService.getBSource(loginInfoMap.get("userStaffId").toString());
					if(source!=null){
						String pubType=request.getParameter("pubtype");//1-图书2-期刊
						form.setUrl(request.getRequestURL().toString());
						Date date= new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
						String dateString = formatter.format(date);//当前年
						dateString=dateString+"-"+"01";
						String dateyear=sdf.format(date);//当前年月
						String eyear=sdf1.format(date);//当前年
						String staryear=form.getStartyear()!=null&&!"".equals(form.getStartyear())?form.getStartyear():dateString;
						String endyear=form.getEndtyear()!=null&&!"".equals(form.getEndtyear())?form.getEndtyear():eyear;
						Map<String, Object> condition = new HashMap<String,Object>();
						condition.put("sourceId", source.getId());
						condition.put("staryear", staryear);//默认下载当前年月
						condition.put("endyear",endyear);
						if(pubType!=null && "1".equals(pubType)){
							condition.put("pubtypes", new Integer[] { 1, 3 });
						}else if(pubType!=null && "2".equals(pubType)){
							condition.put("pubtypes", new Integer[] { 2, 4 });
						}
						Map<String, Object> xMap = new LinkedHashMap<String, Object>();// X 轴坐标  key- contentID value- x
						Map<String, Object> yMap = new LinkedHashMap<String, Object>();// Y 轴坐标  key- 月-年 value- y
						Map<String, Object> accessMap= new LinkedHashMap<String, Object>();//结果集 总数 -每月分别统计总数
						String [] str={"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};// 月份数组
							
						list=this.bookSuppliersService.getSuppList(condition, " order by a.contentid,a.year,a.month ");
							
						if(list!=null&&list.size()>0){
							int flag=0;// 1-图书  2-期刊 0-初始值
							int numX=9;//行号
							int numY=7;//列号
							int count=0;	
							/*生成excel start  heqing.yang  2014-11-02  */
							String time =StatisticsBookSuppliersController.getNowbatchCooe();
							//输出的excel文件工作表名
							String worksheet="";
							yMap.put("count", numY);
							StringBuffer sbb= new StringBuffer();/*生成excel start  heqing.yang  2014-11-02  */
							if(pubType!=null&&"2".equals(pubType)){
								worksheet="JR1.CNPEADING."+time;
								sbb.append("Journal Report 1 (R4);");
								flag=2;
							}else if(pubType!=null&&"1".equals(pubType)){
								worksheet="BR1.CNPEADING."+time;
								sbb.append("Book Report 1 (R4);");
								flag=1;
							}
								sbb.append(" ;");
								sbb.append(" ;");
								sbb.append("Period covered by Report:;");
								sbb.append("yyyy-mm-dd to yyyy-mm-dd;");
								sbb.append("Date run:;");
								sbb.append("yyyy-mm-dd;");
								
								StringBuffer sb= new StringBuffer();
								if(flag==2){
									sb.append("Journal;");
									sb.append("Publisher;");
									sb.append("Platform;");
									sb.append("Journal DOI;");
									sb.append("Proprietary Identifier;");
									
									sb.append("Print ISSN;");
									sb.append("Online ISSN;");
									sb.append("Reporting Period Total;"); 
								}else if(flag==1){
									sb.append(" ;");
									sb.append("Publisher;");
									sb.append("Platform;");
									sb.append("Book DOI;");
									sb.append("Proprietary Identifier;");
									sb.append("ISBN;");
									sb.append("ISSN;");
									sb.append("Reporting Period Total;");
								}
								
//								2013-01   2014-09
								String[] sy=staryear.split("-");
								String[] ey=endyear.split("-");
								Integer start=Integer.parseInt(sy[0]);
								Integer end=Integer.parseInt(ey[0]);
								int sTartMonth=1;
								int eNdMonth=12;
								for(int j=start;j<=end;j++){
									
									if(sTartMonth!=Integer.parseInt(sy[1])){
										sTartMonth=Integer.parseInt(sy[1]);
									}
									 if(j==end){
										if(eNdMonth!=Integer.parseInt(ey[1])){
											eNdMonth=Integer.parseInt(ey[1]);
										} 
									 } 
									 for(int k=sTartMonth;k<=eNdMonth;k++){
										String month = "";
										numY++;// Y轴
										month=str[k];//月份一维数组
										sb.append(month + "-" + j + ";");
										String dete = String.valueOf(k);
										if (dete.length() == 1) {
											dete = "0" + dete;
										}
										yMap.put(j+"-"+dete, numY);
									 }
										sTartMonth=1;
								}
								
								StringBuffer sbf= new StringBuffer();
								if(flag==1){
									sbf.append("Total for all titles;");
								}else if(flag==2){
									sbf.append("Total for all journals;");
								}
								
								sbf.append(" ;");
								if(flag==1){
									sbf.append(" ;");
								}else if(flag==2){
									sbf.append("Platform Z ;");
								}
								
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");//总数列
								
								String title[] = sb.toString().split(";");
								String titles[]=sbb.toString().split(";");
								String titsbf[]=sbf.toString().split(";");
								WritableWorkbook workbook;
								OutputStream os = response.getOutputStream();
								response.reset();// 清空输出流
								response.setHeader("Content-disposition", "attachment; filename=" + worksheet + ".xls");// 设定输出文件头
								response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
								workbook = Workbook.createWorkbook(os);
								WritableSheet sheet = workbook.createSheet(worksheet, 0);
								
								int num=0;
								for(int i=0;i<titles.length;i++){
									sheet.addCell(new Label(0, i, titles[i]));
									num++;
								}
								for (int i = 0; i < num; i++) {
									if(i==0){
										if(pubType!=null&&"2".equals(pubType)){
											sheet.addCell(new Label(1, i, "Number of Successfull Full-Text Article Requests by Month and Journal"));
										}else if(pubType!=null&&"1".equals(pubType)){
											sheet.addCell(new Label(1, i, "Number of Successfull T	Title Requests by Month and Title"));
										}
										
									}else if(i==1){
										sheet.addCell(new Label(1,i , " "));
									}else if(i==2){
										sheet.addCell(new Label(1,i , " "));
									}else if(i==3){
										sheet.addCell(new Label(1,i , "Cnpereading.com"));
									}else if(i==4){
										sheet.addCell(new Label(1,i , dateyear));//条件值
									}else if(i==5){
										sheet.addCell(new Label(1,i , " "));
									}else if(i==6){
										String s =StatisticsBookSuppliersController.getNowbatchCooe();
										sheet.addCell(new Label(1,i, s));
									}
								}
								
								for (int i =0 ; i < title.length; i++) {
									
									// Label(列号,行号 ,内容 )
									sheet.addCell(new Label(i, num, title[i]));
								}
								for (int i =0 ; i < titsbf.length; i++) {
									
									// Label(列号,行号 ,内容 )
									sheet.addCell(new Label(i, num+1, titsbf[i]));
								}
								int counta =0;//统计大总数
								int countb =0;//统计每月大总数
								for(int i=0;i<list.size();i++){
									if(xMap!=null&&xMap.containsKey(list.get(i).getPubId())){
										
										count+=list.get(i).getFullAccess()!=null?list.get(i).getFullAccess():0;
										counta=list.get(i).getFullAccess()!=null?list.get(i).getFullAccess():0;
										countb=list.get(i).getFullAccess()!=null?list.get(i).getFullAccess():0;
										if(accessMap.containsKey(list.get(i).getSdate())){//存在相同月分的值相加  每月一列总计
											accessMap.put(list.get(i).getSdate(), Integer.parseInt(accessMap.get(list.get(i).getSdate()).toString())+countb);
										}else{
											accessMap.put(list.get(i).getSdate(), list.get(i).getFullAccess()!=null?list.get(i).getFullAccess():0);
										}
										accessMap.put("accessCount", Integer.parseInt(accessMap.get("accessCount").toString())+counta);//总数
										
										sheet.addCell(new Label(Integer.parseInt(yMap.get(list.get(i).getSdate()).toString()), Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()),String.valueOf(list.get(i).getFullAccess()!=null?list.get(i).getFullAccess():0)));
										sheet.addCell(new Label(Integer.parseInt(yMap.get("count").toString()), Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), String.valueOf(count)));
									}else{
										count=0;
										xMap.put(list.get(i).getPubId(),numX );//行号
										
										//生成excel
										sheet.addCell(new Label(0, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getTitle()!=null?list.get(i).getTitle():""));
										sheet.addCell(new Label(1, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getPubName()!=null?list.get(i).getPubName():""));
										sheet.addCell(new Label(2, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getPlatform()!=null?list.get(i).getPlatform():""));
										sheet.addCell(new Label(3, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), ""));
										sheet.addCell(new Label(4, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), ""));
										if(flag==1){
											sheet.addCell(new Label(5, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getIsbn()!=null?list.get(i).getIsbn():""));
											sheet.addCell(new Label(6, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getIssn()!=null?list.get(i).getIssn():""));
										}else if (flag==2){
											sheet.addCell(new Label(5, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getIssn()!=null?list.get(i).getIssn():""));
											sheet.addCell(new Label(6, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getEissn()!=null?list.get(i).getEissn():""));
										}
										sheet.addCell(new Label(Integer.parseInt(yMap.get(list.get(i).getSdate()).toString()), Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()),String.valueOf(list.get(i).getFullAccess()!=null?list.get(i).getFullAccess():0)));
										count+=list.get(i).getFullAccess()!=null?list.get(i).getFullAccess():0;
										counta=list.get(i).getFullAccess()!=null?list.get(i).getFullAccess():0;//每月总数大统计
										countb=list.get(i).getFullAccess()!=null?list.get(i).getFullAccess():0;
										if(accessMap.containsKey("accessCount")){
											accessMap.put("accessCount", Integer.parseInt(accessMap.get("accessCount").toString())+counta);//总数
										}else{
											accessMap.put("accessCount", counta);//总数
										}
										
										if(accessMap.containsKey(list.get(i).getSdate())){//存在相同月分的值相加
											accessMap.put(list.get(i).getSdate(), Integer.parseInt(accessMap.get(list.get(i).getSdate()).toString())+countb);
										}else{
											accessMap.put(list.get(i).getSdate(), countb);
										}
										numX++;
									
									}
									sheet.addCell(new Label(Integer.parseInt(yMap.get(list.get(i).getSdate()).toString()), 8, accessMap.get(list.get(i).getSdate()).toString()));//每月大总数
									sheet.addCell(new Label(Integer.parseInt(yMap.get("count").toString()), 8, accessMap.get("accessCount").toString()));//大总数
								}
									
								workbook.write();
								workbook.close();
								os.close();
						}	
						}
						}
					}
					
				
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	/**
	 * 下载拒绝访问excel记录
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/downloadRefused")
	public ModelAndView downloadRefused(HttpServletRequest request,HttpServletResponse response,SSupplierForm form) throws Exception{
		try {
			List<SSupplier> list=null;
			/***测试用的***/
			/*HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("userStaffId", "ff8080814084e493014086580c2920d1");
			
			request.getSession().setAttribute("loginInfoMap", map);*/
			
			if(request.getSession().getAttribute("loginInfoMap")!=null){
				@SuppressWarnings("unchecked")
				Map<String,Object> loginInfoMap = (HashMap<String,Object>)request.getSession().getAttribute("loginInfoMap");
				if(loginInfoMap.get("userStaffId")!=null && !"0".equals(loginInfoMap.get("userStaffId"))){
					BSource source=this.configureService.getBSource(loginInfoMap.get("userStaffId").toString());
					if(source!=null){
						String pubType=request.getParameter("pubtype");//1-图书2-期刊
						form.setUrl(request.getRequestURL().toString());
						Date date= new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
						String dateString = formatter.format(date);//当前年
						dateString=dateString+"-"+"01";
						String dateyear=sdf.format(date);//当前年月
						String eyear=sdf1.format(date);//当前年
						String staryear=form.getStartyear()!=null&&!"".equals(form.getStartyear())?form.getStartyear():dateString;
						String endyear=form.getEndtyear()!=null&&!"".equals(form.getEndtyear())?form.getEndtyear():eyear;
						Map<String, Object> condition = new HashMap<String,Object>();
						condition.put("sourceId", source.getId());
						condition.put("staryear", staryear);//默认下载当前年月
						condition.put("endyear",endyear);
						if(pubType!=null && "1".equals(pubType)){
							condition.put("pubtypes", new Integer[] { 1, 3 });
						}else if(pubType!=null && "2".equals(pubType)){
							condition.put("pubtypes", new Integer[] { 2, 4 });
						}
						Map<String, Object> xMap = new LinkedHashMap<String, Object>();// X 轴坐标  key- contentID value- x
						Map<String, Object> yMap = new LinkedHashMap<String, Object>();// Y 轴坐标  key- 月-年 value- y
						Map<String, Object> refusedMap= new LinkedHashMap<String, Object>();//结果集 总数 -每月分别统计总数
						String [] str={"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};// 月份数组
							
						list=this.bookSuppliersService.getSuppList(condition, " order by a.contentid,a.year,a.month ");
							
						if(list!=null&&list.size()>0){
							int flag=0;// 1-图书  2-期刊 0-初始值
							int numX=9;//行号
							int numY=7;//列号
							int count=0;	
							/*生成excel start  heqing.yang  2014-11-02  */
							String time =StatisticsBookSuppliersController.getNowbatchCooe();
							//输出的excel文件工作表名
							String worksheet="";
							yMap.put("count", numY);
							StringBuffer sbb= new StringBuffer();/*生成excel start  heqing.yang  2014-11-02  */
							if(pubType!=null&&"2".equals(pubType)){
								worksheet="JR-C7.CNPEREADING"+time;
								sbb.append("Journal Report 2 (R4);");
								flag=2;
							}else if(pubType!=null&&"1".equals(pubType)){
								worksheet="BR-C7.CNPEREADING"+time;
								sbb.append("Book Report 3 (R4);");
								flag=1;
							}
								sbb.append(" ;");
								sbb.append(" ;");
								sbb.append("Period covered by Report:;");
								sbb.append("yyyy-mm-dd to yyyy-mm-dd;");
								sbb.append("Date run:;");
								sbb.append("yyyy-mm-dd;");
								
								StringBuffer sb= new StringBuffer();
								if(flag==2){
									sb.append("Journal;");
									sb.append("Publisher;");
									sb.append("Platform;");
									sb.append("Journal DOI;");
									sb.append("Proprietary Identifier;");
									
									sb.append("Print ISSN;");
									sb.append("Online ISSN;");
									sb.append("Reporting Period Total;"); 
								}else if(flag==1){
									sb.append(" ;");
									sb.append("Publisher;");
									sb.append("Platform;");
									sb.append("Book DOI;");
									sb.append("Proprietary Identifier;");
									sb.append("ISBN;");
									sb.append("ISSN;");
									sb.append("Reporting Period Total;");
								}
								
//								2013-01   2014-09
								String[] sy=staryear.split("-");
								String[] ey=endyear.split("-");
								Integer start=Integer.parseInt(sy[0]);
								Integer end=Integer.parseInt(ey[0]);
								int sTartMonth=1;
								int eNdMonth=12;
								for(int j=start;j<=end;j++){
									
									if(sTartMonth!=Integer.parseInt(sy[1])){
										sTartMonth=Integer.parseInt(sy[1]);
									}
									 if(j==end){
										if(eNdMonth!=Integer.parseInt(ey[1])){
											eNdMonth=Integer.parseInt(ey[1]);
										} 
									 } 
									 for(int k=sTartMonth;k<=eNdMonth;k++){
										String month = "";
										numY++;// Y轴
										month=str[k];//月份一维数组
										sb.append(month + "-" + j + ";");
										String dete = String.valueOf(k);
										if (dete.length() == 1) {
											dete = "0" + dete;
										}
										yMap.put(j+"-"+dete, numY);
									 }
										sTartMonth=1;
								}
								
								StringBuffer sbf= new StringBuffer();
								if(flag==1){
									sbf.append("Total for all titles;");
								}else if(flag==2){
									sbf.append("Total for all journals;");
								}
								
								sbf.append(" ;");
								if(flag==1){
									sbf.append(" ;");
								}else if(flag==2){
									sbf.append("Platform Z ;");
								}
								
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");//总数列
								
								String title[] = sb.toString().split(";");
								String titles[]=sbb.toString().split(";");
								String titsbf[]=sbf.toString().split(";");
								WritableWorkbook workbook;
								OutputStream os = response.getOutputStream();
								response.reset();// 清空输出流
								response.setHeader("Content-disposition", "attachment; filename=" + worksheet + ".xls");// 设定输出文件头
								response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
								workbook = Workbook.createWorkbook(os);
								WritableSheet sheet = workbook.createSheet(worksheet, 0);
								
								int num=0;
								for(int i=0;i<titles.length;i++){
									sheet.addCell(new Label(0, i, titles[i]));
									num++;
								}
								for (int i = 0; i < num; i++) {
									if(i==0){
										if(pubType!=null&&"2".equals(pubType)){
											sheet.addCell(new Label(1, i, "Access Denied to Full-Text Articles by Month, Journal and Category"));
										}else if(pubType!=null&&"1".equals(pubType)){
											sheet.addCell(new Label(1, i, "Access Denied to Content Items by Month,Title and Category"));
										}
										
									}else if(i==1){
										sheet.addCell(new Label(1,i , " "));
									}else if(i==2){
										sheet.addCell(new Label(1,i , " "));
									}else if(i==3){
										sheet.addCell(new Label(1,i , "Cnpereading.com"));
									}else if(i==4){
										sheet.addCell(new Label(1,i , dateyear));//条件值
									}else if(i==5){
										sheet.addCell(new Label(1,i , " "));
									}else if(i==6){
										String s =StatisticsBookSuppliersController.getNowbatchCooe();
										sheet.addCell(new Label(1,i, s));
									}
								}
								
								for (int i =0 ; i < title.length; i++) {
									
									// Label(列号,行号 ,内容 )
									sheet.addCell(new Label(i, num, title[i]));
								}
								for (int i =0 ; i < titsbf.length; i++) {
									
									// Label(列号,行号 ,内容 )
									sheet.addCell(new Label(i, num+1, titsbf[i]));
								}
								int counta =0;//统计大总数
								int countb =0;//统计每月大总数
								for(int i=0;i<list.size();i++){
									if(xMap!=null&&xMap.containsKey(list.get(i).getPubId())){
										
										count+=list.get(i).getFullRefused()!=null?list.get(i).getFullRefused():0;
										counta=list.get(i).getFullRefused()!=null?list.get(i).getFullRefused():0;
										countb=list.get(i).getFullRefused()!=null?list.get(i).getFullRefused():0;
										if(refusedMap.containsKey(list.get(i).getSdate())){//存在相同月分的值相加  每月一列总计
											refusedMap.put(list.get(i).getSdate(), Integer.parseInt(refusedMap.get(list.get(i).getSdate()).toString())+countb);
										}else{
											refusedMap.put(list.get(i).getSdate(), list.get(i).getFullRefused()!=null?list.get(i).getFullRefused():0);
										}
										refusedMap.put("refusedCount", Integer.parseInt(refusedMap.get("refusedCount").toString())+counta);//总数
										
										sheet.addCell(new Label(Integer.parseInt(yMap.get(list.get(i).getSdate()).toString()), Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()),String.valueOf(list.get(i).getFullRefused()!=null?list.get(i).getFullRefused():0)));
										sheet.addCell(new Label(Integer.parseInt(yMap.get("count").toString()), Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), String.valueOf(count)));
									}else{
										count=0;
										xMap.put(list.get(i).getPubId(),numX );//行号
										
										//生成excel
										sheet.addCell(new Label(0, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getTitle()!=null?list.get(i).getTitle():""));
										sheet.addCell(new Label(1, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getPubName()!=null?list.get(i).getPubName():""));
										sheet.addCell(new Label(2, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getPlatform()!=null?list.get(i).getPlatform():""));
										sheet.addCell(new Label(3, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), ""));
										sheet.addCell(new Label(4, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), ""));
										if(flag==1){
											sheet.addCell(new Label(5, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getIsbn()!=null?list.get(i).getIsbn():""));
											sheet.addCell(new Label(6, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getIssn()!=null?list.get(i).getIssn():""));
										}else if (flag==2){
											sheet.addCell(new Label(5, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getIssn()!=null?list.get(i).getIssn():""));
											sheet.addCell(new Label(6, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getEissn()!=null?list.get(i).getEissn():""));
										}
										sheet.addCell(new Label(Integer.parseInt(yMap.get(list.get(i).getSdate()).toString()), Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()),String.valueOf(list.get(i).getFullRefused()!=null?list.get(i).getFullRefused():0)));
										count+=list.get(i).getFullRefused()!=null?list.get(i).getFullRefused():0;
										counta=list.get(i).getFullRefused()!=null?list.get(i).getFullRefused():0;//每月总数大统计
										countb=list.get(i).getFullRefused()!=null?list.get(i).getFullRefused():0;
										if(refusedMap.containsKey("refusedCount")){
											refusedMap.put("refusedCount", Integer.parseInt(refusedMap.get("refusedCount").toString())+counta);//总数
										}else{
											refusedMap.put("refusedCount", counta);//总数
										}
										
										if(refusedMap.containsKey(list.get(i).getSdate())){//存在相同月分的值相加
											refusedMap.put(list.get(i).getSdate(), Integer.parseInt(refusedMap.get(list.get(i).getSdate()).toString())+countb);
										}else{
											refusedMap.put(list.get(i).getSdate(), countb);
										}
										numX++;
									
									}
									sheet.addCell(new Label(Integer.parseInt(yMap.get(list.get(i).getSdate()).toString()), 8, refusedMap.get(list.get(i).getSdate()).toString()));//每月大总数
									sheet.addCell(new Label(Integer.parseInt(yMap.get("count").toString()), 8, refusedMap.get("refusedCount").toString()));//大总数
								}
									
								workbook.write();
								workbook.close();
								os.close();
						}	
						}
						}
					}
					
				
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	/**
	 * 下载tocexcel记录
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/downloadToc")
	public ModelAndView downloadToc(HttpServletRequest request,HttpServletResponse response,SSupplierForm form) throws Exception{
		try {
			List<SSupplier> list=null;
			/***测试用的***/
			/*HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("userStaffId", "ff8080814081643801408675ad6303cc");
			
			request.getSession().setAttribute("loginInfoMap", map);*/
			
			if(request.getSession().getAttribute("loginInfoMap")!=null){
				@SuppressWarnings("unchecked")
				Map<String,Object> loginInfoMap = (HashMap<String,Object>)request.getSession().getAttribute("loginInfoMap");
				if(loginInfoMap.get("userStaffId")!=null && !"0".equals(loginInfoMap.get("userStaffId"))){
					BSource source=this.configureService.getBSource(loginInfoMap.get("userStaffId").toString());
					if(source!=null){
						String pubType=request.getParameter("pubtype");//1-图书2-期刊
						form.setUrl(request.getRequestURL().toString());
						Date date= new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
						String dateString = formatter.format(date);//当前年
						dateString=dateString+"-"+"01";
						String dateyear=sdf.format(date);//当前年月
						String eyear=sdf1.format(date);//当前年
						String staryear=form.getStartyear()!=null&&!"".equals(form.getStartyear())?form.getStartyear():dateString;
						String endyear=form.getEndtyear()!=null&&!"".equals(form.getEndtyear())?form.getEndtyear():eyear;
						Map<String, Object> condition = new HashMap<String,Object>();
						condition.put("sourceId", source.getId());
						condition.put("staryear", staryear);//默认下载当前年月
						condition.put("endyear",endyear);
						if(pubType!=null && "1".equals(pubType)){
							condition.put("pubtypes", new Integer[] { 1, 3 });
						}else if(pubType!=null && "2".equals(pubType)){
							condition.put("pubtypes", new Integer[] { 2, 4 });
						}
						Map<String, Object> xMap = new LinkedHashMap<String, Object>();// X 轴坐标  key- contentID value- x
						Map<String, Object> yMap = new LinkedHashMap<String, Object>();// Y 轴坐标  key- 月-年 value- y
						Map<String, Object> tocMap= new LinkedHashMap<String, Object>();//结果集 总数 -每月分别统计总数
						String [] str={"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};// 月份数组
							
						list=this.bookSuppliersService.getSuppList(condition, " order by a.contentid,a.year,a.month ");
							
						if(list!=null&&list.size()>0){
							int flag=0;// 1-图书  2-期刊 0-初始值
							int numX=9;//行号
							int numY=7;//列号
							int count=0;	
							/*生成excel start  heqing.yang  2014-11-02  */
							String time =StatisticsBookSuppliersController.getNowbatchCooe();
							//输出的excel文件工作表名
							String worksheet="";
							yMap.put("count", numY);
							StringBuffer sbb= new StringBuffer();/*生成excel start  heqing.yang  2014-11-02  */
							if(pubType!=null&&"2".equals(pubType)){
								worksheet="JR1.CNPEADING."+time;
								sbb.append("Journal Report 1 (R4);");
								flag=2;
							}else if(pubType!=null&&"1".equals(pubType)){
								worksheet="BR1.CNPEADING."+time;
								sbb.append("Book Report 1 (R4);");
								flag=1;
							}
								sbb.append(" ;");
								sbb.append(" ;");
								sbb.append("Period covered by Report:;");
								sbb.append("yyyy-mm-dd to yyyy-mm-dd;");
								sbb.append("Date run:;");
								sbb.append("yyyy-mm-dd;");
								
								StringBuffer sb= new StringBuffer();
								if(flag==2){
									sb.append("Journal;");
									sb.append("Publisher;");
									sb.append("Platform;");
									sb.append("Journal DOI;");
									sb.append("Proprietary Identifier;");
									
									sb.append("Print ISSN;");
									sb.append("Online ISSN;");
									sb.append("Reporting Period Total;"); 
								}else if(flag==1){
									sb.append(" ;");
									sb.append("Publisher;");
									sb.append("Platform;");
									sb.append("Book DOI;");
									sb.append("Proprietary Identifier;");
									sb.append("ISBN;");
									sb.append("ISSN;");
									sb.append("Reporting Period Total;");
								}
								
//								2013-01   2014-09
								String[] sy=staryear.split("-");
								String[] ey=endyear.split("-");
								Integer start=Integer.parseInt(sy[0]);
								Integer end=Integer.parseInt(ey[0]);
								int sTartMonth=1;
								int eNdMonth=12;
								for(int j=start;j<=end;j++){
									
									if(sTartMonth!=Integer.parseInt(sy[1])){
										sTartMonth=Integer.parseInt(sy[1]);
									}
									 if(j==end){
										if(eNdMonth!=Integer.parseInt(ey[1])){
											eNdMonth=Integer.parseInt(ey[1]);
										} 
									 } 
									 for(int k=sTartMonth;k<=eNdMonth;k++){
										String month = "";
										numY++;// Y轴
										month=str[k];//月份一维数组
										sb.append(month + "-" + j + ";");
										String dete = String.valueOf(k);
										if (dete.length() == 1) {
											dete = "0" + dete;
										}
										yMap.put(j+"-"+dete, numY);
									 }
										sTartMonth=1;
								}
								
								StringBuffer sbf= new StringBuffer();
								if(flag==1){
									sbf.append("Total for all titles;");
								}else if(flag==2){
									sbf.append("Total for all journals;");
								}
								
								sbf.append(" ;");
								if(flag==1){
									sbf.append(" ;");
								}else if(flag==2){
									sbf.append("Platform Z ;");
								}
								
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");//总数列
								
								String title[] = sb.toString().split(";");
								String titles[]=sbb.toString().split(";");
								String titsbf[]=sbf.toString().split(";");
								WritableWorkbook workbook;
								OutputStream os = response.getOutputStream();
								response.reset();// 清空输出流
								response.setHeader("Content-disposition", "attachment; filename=" + worksheet + ".xls");// 设定输出文件头
								response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
								workbook = Workbook.createWorkbook(os);
								WritableSheet sheet = workbook.createSheet(worksheet, 0);
								
								int num=0;
								for(int i=0;i<titles.length;i++){
									sheet.addCell(new Label(0, i, titles[i]));
									num++;
								}
								for (int i = 0; i < num; i++) {
									if(i==0){
										if(pubType!=null&&"2".equals(pubType)){
											sheet.addCell(new Label(1, i, "Number of Successfull Full-Text Article Requests by Month and Journal"));
										}else if(pubType!=null&&"1".equals(pubType)){
											sheet.addCell(new Label(1, i, "Number of Successfull T	Title Requests by Month and Title"));
										}
										
									}else if(i==1){
										sheet.addCell(new Label(1,i , " "));
									}else if(i==2){
										sheet.addCell(new Label(1,i , " "));
									}else if(i==3){
										sheet.addCell(new Label(1,i , "Cnpereading.com"));
									}else if(i==4){
										sheet.addCell(new Label(1,i , dateyear));//条件值
									}else if(i==5){
										sheet.addCell(new Label(1,i , " "));
									}else if(i==6){
										String s =StatisticsBookSuppliersController.getNowbatchCooe();
										sheet.addCell(new Label(1,i, s));
									}
								}
								
								for (int i =0 ; i < title.length; i++) {
									
									// Label(列号,行号 ,内容 )
									sheet.addCell(new Label(i, num, title[i]));
								}
								for (int i =0 ; i < titsbf.length; i++) {
									
									// Label(列号,行号 ,内容 )
									sheet.addCell(new Label(i, num+1, titsbf[i]));
								}
								int counta =0;//统计大总数
								int countb =0;//统计每月大总数
								for(int i=0;i<list.size();i++){
									if(xMap!=null&&xMap.containsKey(list.get(i).getPubId())){
									
										count+=list.get(i).getToc()!=null?list.get(i).getToc():0;
										counta=list.get(i).getToc()!=null?list.get(i).getToc():0;
										countb=list.get(i).getToc()!=null?list.get(i).getToc():0;
										if(tocMap.containsKey(list.get(i).getSdate())){//存在相同月分的值相加  每月一列总计
											tocMap.put(list.get(i).getSdate(), Integer.parseInt(tocMap.get(list.get(i).getSdate()).toString())+countb);
										}else{
											tocMap.put(list.get(i).getSdate(), list.get(i).getToc()!=null?list.get(i).getToc():0);
										}
										tocMap.put("tocCount", Integer.parseInt(tocMap.get("tocCount").toString())+counta);//总数
										
										sheet.addCell(new Label(Integer.parseInt(yMap.get(list.get(i).getSdate()).toString()), Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()),String.valueOf(list.get(i).getToc()!=null?list.get(i).getToc():0)));
										sheet.addCell(new Label(Integer.parseInt(yMap.get("count").toString()), Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), String.valueOf(count)));
									}else{
										count=0;
										xMap.put(list.get(i).getPubId(),numX );//行号
										
										//生成excel
										sheet.addCell(new Label(0, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getTitle()!=null?list.get(i).getTitle():""));
										sheet.addCell(new Label(1, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getPubName()!=null?list.get(i).getPubName():""));
										sheet.addCell(new Label(2, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getPlatform()!=null?list.get(i).getPlatform():""));
										sheet.addCell(new Label(3, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), ""));
										sheet.addCell(new Label(4, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), ""));
										if(flag==1){
											sheet.addCell(new Label(5, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getIsbn()!=null?list.get(i).getIsbn():""));
											sheet.addCell(new Label(6, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getIssn()!=null?list.get(i).getIssn():""));
										}else if (flag==2){
											sheet.addCell(new Label(5, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getIssn()!=null?list.get(i).getIssn():""));
											sheet.addCell(new Label(6, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getEissn()!=null?list.get(i).getEissn():""));
										}
										sheet.addCell(new Label(Integer.parseInt(yMap.get(list.get(i).getSdate()).toString()), Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()),String.valueOf(list.get(i).getToc()!=null?list.get(i).getToc():0)));
										count+=list.get(i).getToc()!=null?list.get(i).getToc():0;
										counta=list.get(i).getToc()!=null?list.get(i).getToc():0;//每月总数大统计
										countb=list.get(i).getToc()!=null?list.get(i).getToc():0;
										if(tocMap.containsKey("tocCount")){
											tocMap.put("tocCount", Integer.parseInt(tocMap.get("tocCount").toString())+counta);//总数
										}else{
											tocMap.put("tocCount", counta);//总数
										}
										
										if(tocMap.containsKey(list.get(i).getSdate())){//存在相同月分的值相加
											tocMap.put(list.get(i).getSdate(), Integer.parseInt(tocMap.get(list.get(i).getSdate()).toString())+countb);
										}else{
											tocMap.put(list.get(i).getSdate(), countb);
										}
										numX++;
									}
									sheet.addCell(new Label(Integer.parseInt(yMap.get(list.get(i).getSdate()).toString()), 8, tocMap.get(list.get(i).getSdate()).toString()));//每月大总数
									sheet.addCell(new Label(Integer.parseInt(yMap.get("count").toString()), 8, tocMap.get("tocCount").toString()));//大总数
								}
									
								workbook.write();
								workbook.close();
								os.close();
						}	
						}
						}
					}
					
				
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	/**
	 * 下载Searchexcel记录
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/downloadSearch")
	public ModelAndView downloadSearch(HttpServletRequest request,HttpServletResponse response,SSupplierForm form) throws Exception{
		try {
			List<SSupplier> list=null;
			/***测试用的***/
			/*HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("userStaffId", "ff8080813ca7cd1d013d43b066750009");
			
			request.getSession().setAttribute("loginInfoMap", map);*/
			
			if(request.getSession().getAttribute("loginInfoMap")!=null){
				@SuppressWarnings("unchecked")
				Map<String,Object> loginInfoMap = (HashMap<String,Object>)request.getSession().getAttribute("loginInfoMap");
				if(loginInfoMap.get("userStaffId")!=null && !"0".equals(loginInfoMap.get("userStaffId"))){
					BSource source=this.configureService.getBSource(loginInfoMap.get("userStaffId").toString());
					if(source!=null){
						String pubType=request.getParameter("pubtype");//1-图书2-期刊
						form.setUrl(request.getRequestURL().toString());
						Date date= new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
						String dateString = formatter.format(date);//当前年
						dateString=dateString+"-"+"01";
						String dateyear=sdf.format(date);//当前年月
						String eyear=sdf1.format(date);//当前年
						String staryear=form.getStartyear()!=null&&!"".equals(form.getStartyear())?form.getStartyear():dateString;
						String endyear=form.getEndtyear()!=null&&!"".equals(form.getEndtyear())?form.getEndtyear():eyear;
						Map<String, Object> condition = new HashMap<String,Object>();
						condition.put("sourceId", source.getId());
						condition.put("staryear", staryear);//默认下载当前年月
						condition.put("endyear",endyear);
						if(pubType!=null && "1".equals(pubType)){
							condition.put("pubtypes", new Integer[] { 1, 3 });
						}else if(pubType!=null && "2".equals(pubType)){
							condition.put("pubtypes", new Integer[] { 2, 4 });
						}
						Map<String, Object> xMap = new LinkedHashMap<String, Object>();// X 轴坐标  key- contentID value- x
						Map<String, Object> yMap = new LinkedHashMap<String, Object>();// Y 轴坐标  key- 月-年 value- y
						Map<String, Object> searchMap= new LinkedHashMap<String, Object>();//结果集 总数 -每月分别统计总数
						String [] str={"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};// 月份数组
							
						list=this.bookSuppliersService.getSuppList(condition, " order by a.contentid,a.year,a.month ");
						if(list!=null&&list.size()>0){
							int flag=0;// 1-图书  2-期刊 0-初始值
							int numX=9;//行号
							int numY=8;//列号
							int count=0;	
							/*生成excel start  heqing.yang  2014-11-02  */
							String time =StatisticsBookSuppliersController.getNowbatchCooe();
							//输出的excel文件工作表名
							String worksheet="";
							yMap.put("count", numY);
							StringBuffer sbb= new StringBuffer();
							if(pubType!=null&&"2".equals(pubType)){
								worksheet="JR5.CNPEREADING"+time;
								sbb.append("Journal Report 5 (R4);");
								flag=2;
							}else if(pubType!=null&&"1".equals(pubType)){
								worksheet="BR5.CNPEREADING"+time;
								sbb.append("Book Report 5 (R4);");
								flag=1;
							}
								sbb.append(" ;");
								sbb.append(" ;");
								sbb.append("Period covered by Report:;");
								sbb.append("yyyy-mm-dd to yyyy-mm-dd;");
								sbb.append("Date run:;");
								sbb.append("yyyy-mm-dd;");
								
								StringBuffer sb= new StringBuffer();
								if(flag==2){
									sb.append("Journal;");
									sb.append("Publisher;");
									sb.append("Platform;");
									sb.append("Journal DOI;");
									sb.append("Proprietary Identifier;");
									
									sb.append("Print ISSN;");
									sb.append("Online ISSN;");
									sb.append("User activity;");
									sb.append("Reporting Period Total;"); 
								}else if(flag==1){
									sb.append(" ;");
									sb.append("Publisher;");
									sb.append("Platform;");
									sb.append("Book DOI;");
									sb.append("Proprietary Identifier;");
									sb.append("ISBN;");
									sb.append("ISSN;");
									sb.append("User activity;");
									sb.append("Reporting Period Total;");
								}
							//	2013-01   2014-09
								String[] sy=staryear.split("-");
								String[] ey=endyear.split("-");
								Integer start=Integer.parseInt(sy[0]);
								Integer end=Integer.parseInt(ey[0]);
								int sTartMonth=1;
								int eNdMonth=12;
								for(int j=start;j<=end;j++){
									
									if(sTartMonth!=Integer.parseInt(sy[1])){
										sTartMonth=Integer.parseInt(sy[1]);
									}
									 if(j==end){
										if(eNdMonth!=Integer.parseInt(ey[1])){
											eNdMonth=Integer.parseInt(ey[1]);
										} 
									 } 
									 for(int k=sTartMonth;k<=eNdMonth;k++){
										String month = "";
										numY++;// Y轴
										month=str[k];//月份一维数组
										sb.append(month + "-" + j + ";");
										String dete = String.valueOf(k);
										if (dete.length() == 1) {
											dete = "0" + dete;
										}
										yMap.put(j+"-"+dete, numY);
									 }
										sTartMonth=1;
								}
								
								StringBuffer sbf= new StringBuffer();
								if(flag==1){
									sbf.append("Total searches;");
								}else if(flag==2){
									sbf.append("Total searches journals;");
								}
								
								sbf.append(" ;");
								if(flag==1){
									sbf.append(" ;");
								}else if(flag==2){
									sbf.append("Platform Z ;");
								}
								
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");
								sbf.append(" ;");//总数列
								
								String title[] = sb.toString().split(";");
								String titles[]=sbb.toString().split(";");
								String titsbf[]=sbf.toString().split(";");
								WritableWorkbook workbook;
								OutputStream os = response.getOutputStream();
								response.reset();// 清空输出流
								response.setHeader("Content-disposition", "attachment; filename=" + worksheet + ".xls");// 设定输出文件头
								response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
								workbook = Workbook.createWorkbook(os);
								WritableSheet sheet = workbook.createSheet(worksheet, 0);
								
								int num=0;
								for(int i=0;i<titles.length;i++){
									sheet.addCell(new Label(0, i, titles[i]));
									num++;
								}
								for (int i = 0; i < num; i++) {
									if(i==0){
										if(pubType!=null&&"2".equals(pubType)){
											sheet.addCell(new Label(1, i, "Total Searches by Month and Title"));
										}else if(pubType!=null&&"1".equals(pubType)){
											sheet.addCell(new Label(1, i, "Total Searches by Month and Title"));
										}
										
									}else if(i==1){
										sheet.addCell(new Label(1,i , " "));
									}else if(i==2){
										sheet.addCell(new Label(1,i , " "));
									}else if(i==3){
										sheet.addCell(new Label(1,i , "Cnpereading.com"));
									}else if(i==4){
										sheet.addCell(new Label(1,i , dateyear));//条件值
									}else if(i==5){
										sheet.addCell(new Label(1,i , " "));
									}else if(i==6){
										String s =StatisticsBookSuppliersController.getNowbatchCooe();
										sheet.addCell(new Label(1,i, s));
									}
								}
								
								for (int i =0 ; i < title.length; i++) {
									
									// Label(列号,行号 ,内容 )
									sheet.addCell(new Label(i, num, title[i]));
								}
								for (int i =0 ; i < titsbf.length; i++) {
									
									// Label(列号,行号 ,内容 )
									sheet.addCell(new Label(i, num+1, titsbf[i]));
								}
								int counta =0;//统计大总数
								int countb =0;//统计每月大总数
								for(int i=0;i<list.size();i++){
								if(xMap!=null&&xMap.containsKey(list.get(i).getPubId())){
									
									count+=list.get(i).getSearch()!=null?list.get(i).getSearch():0;
									counta=list.get(i).getSearch()!=null?list.get(i).getSearch():0;
									countb=list.get(i).getSearch()!=null?list.get(i).getSearch():0;
									if(searchMap.containsKey(list.get(i).getSdate())){//存在相同月分的值相加  每月一列总计
										searchMap.put(list.get(i).getSdate(), Integer.parseInt(searchMap.get(list.get(i).getSdate()).toString())+countb);
									}else{
										searchMap.put(list.get(i).getSdate(), list.get(i).getSearch()!=null?list.get(i).getSearch():0);
									}
									searchMap.put("searchCount", Integer.parseInt(searchMap.get("searchCount").toString())+counta);//总数
									sheet.addCell(new Label(Integer.parseInt(yMap.get(list.get(i).getSdate()).toString()), Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()),String.valueOf(list.get(i).getSearch()!=null?list.get(i).getSearch():0)));
									sheet.addCell(new Label(Integer.parseInt(yMap.get("count").toString()), Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), String.valueOf(count)));
								}else{
									count=0;
									xMap.put(list.get(i).getPubId(),numX );//行号
									
									//生成excel
									sheet.addCell(new Label(0, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getTitle()!=null?list.get(i).getTitle():""));
									sheet.addCell(new Label(1, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getPubName()!=null?list.get(i).getPubName():""));
									sheet.addCell(new Label(2, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getPlatform()!=null?list.get(i).getPlatform():""));
									sheet.addCell(new Label(3, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), ""));
									sheet.addCell(new Label(4, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), ""));
									if(flag==1){
										sheet.addCell(new Label(5, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getIsbn()!=null?list.get(i).getIsbn():""));
										sheet.addCell(new Label(6, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getIssn()!=null?list.get(i).getIssn():""));
									}else if (flag==2){
										sheet.addCell(new Label(5, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getIssn()!=null?list.get(i).getIssn():""));
										sheet.addCell(new Label(6, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()), list.get(i).getEissn()!=null?list.get(i).getEissn():""));
									}
									sheet.addCell(new Label(7, Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()),"Regular Searches"));
									sheet.addCell(new Label(Integer.parseInt(yMap.get(list.get(i).getSdate()).toString()), Integer.parseInt(xMap.get(list.get(i).getPubId()).toString()),String.valueOf(list.get(i).getSearch()!=null?list.get(i).getSearch():0)));
									count+=list.get(i).getSearch()!=null?list.get(i).getSearch():0;
									counta=list.get(i).getSearch()!=null?list.get(i).getSearch():0;//每月总数大统计
									countb=list.get(i).getSearch()!=null?list.get(i).getSearch():0;
									if(searchMap.containsKey("searchCount")){
										searchMap.put("searchCount", Integer.parseInt(searchMap.get("searchCount").toString())+counta);//总数
									}else{
										searchMap.put("searchCount", counta);//总数
									}
									if(searchMap.containsKey(list.get(i).getSdate())){//存在相同月分的值相加
										searchMap.put(list.get(i).getSdate(), Integer.parseInt(searchMap.get(list.get(i).getSdate()).toString())+countb);
									}else{
										searchMap.put(list.get(i).getSdate(), countb);
									}
									numX++;
								}
								sheet.addCell(new Label(Integer.parseInt(yMap.get(list.get(i).getSdate()).toString()), 8, searchMap.get(list.get(i).getSdate()).toString()));//每月大总数
								sheet.addCell(new Label(Integer.parseInt(yMap.get("count").toString()), 8, searchMap.get("searchCount").toString()));//大总数
							}
								
							
							workbook.write();
							workbook.close();
							os.close();
							}
							}
						}
				}
	
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	
	
	
	


	/**
	   * 流水号生成规则为日期的yyyyMMddhhmmss形式
	   * 
	   * @param strDate
	   * @return
	   */
	public static String getNowbatchCooe() {  
		java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
	    String  s = format2.format(new Date()); 
		return s; 
		}

}
