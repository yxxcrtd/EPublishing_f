package cn.digitalpublishing.springmvc.controller;

import java.io.File;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import cn.ccsit.restful.tool.Converter;
import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.model.Param;
import cn.com.daxtech.framework.model.ResultObject;
import cn.com.daxtech.framework.util.ObjectUtil;
import cn.digitalpublishing.ep.po.PPage;
import cn.digitalpublishing.ep.po.PPublications;

@Controller
@RequestMapping("/pages/page")
public class PPageController extends BaseController {
	/**
	 * 数据接口
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/syspagereturns", method = RequestMethod.POST)
	public void syspagereturns(HttpServletRequest request, Model model){
		ResultObject<PPublications> result=null;
		try{
			String objJson = request.getParameter("publications").toString();
			Converter<cn.digitalpublishing.ep.po.PPublications> converter=new Converter<cn.digitalpublishing.ep.po.PPublications>();
			PPublications obj=(PPublications)converter.json2Object(objJson, PPublications.class.getName());
			List<PPage> list=this.pPublicationsService.getPageList(obj.getId());
			if(list!=null&&list.size()>0){
				result=new ResultObject<PPublications>(1,Lang.getLanguage("Controller.PPrice.insert.manage.success",request.getSession().getAttribute("lang").toString()));//"价格信息维护成功！");//"出版商信息维护成功！");
			}else{
				result=new ResultObject<PPublications>(3,Lang.getLanguage("Controller.PPrice.insert.manage.success",request.getSession().getAttribute("lang").toString()));//"价格信息维护成功！");//"出版商信息维护成功！");
			}
	
		}catch(Exception e){
			e.printStackTrace();
			result=new ResultObject<PPublications>(2,Lang.getLanguage("Controller.PPrice.insert.manage.error",request.getSession().getAttribute("lang").toString()));//"价格信息维护失败！");//"出版商信息维护失败！");
		}
		model.addAttribute("target",result);
	}
	/**
	 * 数据接口
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(HttpServletRequest request, Model model){
		ResultObject<PPage> result=null;
		try{
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); //1-insert 2-update
			Integer operNum = Integer.valueOf(request.getParameter("operNum").toString()); //0是首次操作，将删除对方的全部数，其他的数字将直接插入
			Converter<PPage> converter=new Converter<PPage>();
			PPage obj=(PPage)converter.json2Object(objJson, PPage.class.getName());
			if(operNum<=0){
				//删除全部数据
				/** 因为页面有 Notes 和 Records 不能删除
				Map<String,Object> condition = new HashMap<String,Object>();
				condition.put("sourceId", obj.getPublications().getId());
				this.configureService.deletePage(condition);
				**/
			}
			PPage existPage = this.pPublicationsService.getPages(obj.getId());
			if(existPage==null){
				operType="1";
			}else{
				operType="2";
			}
			if("2".equals(operType)){
				this.configureService.updatePage(obj,obj.getId(), null);
			}else if ("1".equals(operType)){
				this.configureService.insertPage(obj);
			}else{
				//删除
			}
			//-****************************创建索引*START******************************
			/** 所有创建索引的工作都由DCC执行此处隐掉
			if(obj.getPdf()!=null&&!"".equals(obj.getPdf())){
				String bpath = Param.getParam("pdf.directory.config").get("dir").replace("-", ":");
				String filePath = bpath+obj.getPdf();
				File pdf = new File(filePath);
				if(pdf.exists()){
					String nr = "";
					PDDocument doc = PDDocument.load(filePath);
					PDFTextStripper stripper = new PDFTextStripper();
					nr = stripper.getText(doc);
					//nr = nr.replace(" ", "");
					obj.setFullText(nr);
					doc.close();
					this.pagesIndexService.indexPages(obj);
				}
			}
			**/
			//-****************************创建索引*END******************************
			
			ObjectUtil<PPage> util=new ObjectUtil<PPage>();
			obj=util.setNull(obj, new String[]{Set.class.getName()});
			if(obj.getPublications()!=null){
				ObjectUtil<PPublications> util1=new ObjectUtil<PPublications>();
				PPublications parent = obj.getPublications();
				parent=util1.setNull(parent, new String[]{Set.class.getName(),List.class.getName()});
				parent.setPriceList(null);
				obj.setPublications(parent);
			}
			obj.setFullText("");
			result=new ResultObject<PPage>(1,obj,Lang.getLanguage("Controller.Ppage.insert.manage.success",request.getSession().getAttribute("lang").toString()));//"分页信息维护成功！");
		}catch(Exception e){
			System.out.println("XXXXXXXXXXXXXXXXXX Error Start XXXXXXXXXXXXXXXXXXXXX");
			e.printStackTrace();
			System.out.println("XXXXXXXXXXXXXXXXXX Error End XXXXXXXXXXXXXXXXXXXXXXX");
			result=new ResultObject<PPage>(2,Lang.getLanguage("Controller.Ppage.insert.manage.error",request.getSession().getAttribute("lang").toString()));//"分页信息维护失败！");
		}
		model.addAttribute("target",result);
	}
	
}
