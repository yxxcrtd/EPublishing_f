package cn.digitalpublishing.springmvc.controller;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.Param;
import cn.com.daxtech.framework.util.ObjectUtil;
import cn.digitalpublishing.ep.po.BPublisher;
import cn.digitalpublishing.ep.po.PPage;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.springmvc.form.ViewForm;
import cn.digitalpublishing.util.io.FileUtil;

import com.zhima.server.model.ResultObject;
import com.zhima.server.util.restful.Converter;

@Controller
@RequestMapping("/pages/epub")
public class EpubController extends BaseController {

	@RequestMapping(value="/form/search")
	public void search(HttpServletRequest request,HttpServletResponse response,HttpSession session, ViewForm form)throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			String id = form.getId();			
			map = this.pagesIndexService.selectPagesByFullText(id, form.getValue(),form.getCurpage(),form.getPageCount());
			List<Map<String,Object>> results=new ArrayList<Map<String,Object>>();
			if(map.get("count")!=null && ((Long)map.get("count"))>0){
				List<Map<String,Object>> result=(List<Map<String,Object>>)map.get("result");
				Map<String,Object> finish=new HashMap<String,Object>();
				for (int i=0;i<result.size();i++) {
					String pageId=result.get(i).get("id").toString();
					PPage pg=this.pPublicationsService.getPages(pageId);
					if(pg!=null){
						String pn=pg.getMark().split("#")[0];
						if(!finish.containsKey(pn)){
							finish.put(pn, 1);							
						}else{
							Integer count=(Integer)finish.get(pn);
							finish.put(pn, count+1);
						}
					}
				}
				if(!finish.isEmpty()){
					for (String key : finish.keySet()){
						Map<String,Object> sr=new HashMap<String,Object>();
						sr.put("pageNumber",key);
						sr.put("hitCount",finish.get(key).toString());
						results.add(sr);
					}
				}
			}			
			map.put("results",results);
			String str=form.getValue();
			str=URLDecoder.decode(str,"utf-8");
			map.put("qValue",form.getValue());
		}catch(Exception e){
			throw e ;
		}
		try {
			JSONArray json = new JSONArray();
			json.add(map);
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			throw e;
//			request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
	}
	
	/**
	 * 数据接口
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/unzipEpub", method = RequestMethod.POST)
	public void unzipEpub(HttpServletRequest request, Model model){
		ResultObject<PPublications> result=null;
		try{ 
			String objJson = request.getParameter("obj").toString();
			String webRoot = request.getSession().getServletContext().getRealPath("");//网站根目录
    		String basePath=Param.getParam("pdf.directory.config").get("dir").replace("-",":");//文件根目录
    		
			Converter<PPublications> converter=new Converter<PPublications>();
			PPublications obj=(PPublications)converter.json2Object(objJson, PPublications.class.getName());
			Boolean flag=false;
			System.out.println("unzip:"+basePath + obj.getPdf());
			System.out.println("to:" + webRoot + obj.getEpubDir());
			if(FileUtil.isExist(basePath + obj.getPdf())){
    			FileUtil.unZip(basePath + obj.getPdf(), webRoot + obj.getEpubDir());
    			flag=true;
    		}
			
			ObjectUtil<PPublications> util=new ObjectUtil<PPublications>();
			obj=util.setNull(obj, new String[]{Set.class.getName(),List.class.getName()});

			if(obj.getPublications()!=null){
				util.setNull(obj.getPublications(),new String[]{Set.class.getName(),List.class.getName()});
			}
			if(obj.getVolume()!=null){
				util.setNull(obj.getVolume(),new String[]{Set.class.getName(),List.class.getName()});
			}
			if(obj.getIssue()!=null){
				util.setNull(obj.getIssue(),new String[]{Set.class.getName(),List.class.getName()});
			}
			if(obj.getPublisher()!=null){
				ObjectUtil<BPublisher> util2=new ObjectUtil<BPublisher>();
				util2.setNull(obj.getPublisher(),new String[]{Set.class.getName(),List.class.getName()});
			}
			if(flag){
				result=new ResultObject<PPublications>(1,obj,Lang.getLanguage("Controller.Publications.Rest.Maintain.Succ", request.getSession().getAttribute("lang").toString()));//"出版商信息维护成功！");
			}else{
				result=new ResultObject<PPublications>(2,Lang.getLanguage("Controller.Publications.Rest.Maintain.Failed", request.getSession().getAttribute("lang").toString()));
			}
		}catch(Exception e){
			e.printStackTrace();
			result=new ResultObject<PPublications>(2,(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):Lang.getLanguage("Controller.Publications.Rest.Maintain.Failed", request.getSession().getAttribute("lang").toString()));//"出版商信息维护失败！");
		}
		model.addAttribute("target",result);
	}
}
