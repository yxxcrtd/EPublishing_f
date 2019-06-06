package cn.digitalpublishing.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.SOnsale;
import cn.digitalpublishing.springmvc.form.index.IndexForm;

@Controller
@RequestMapping("/pages/left")
public class LeftController extends BaseController {
	@RequestMapping(value="/leftData")
	public ModelAndView leftData(HttpServletRequest request,HttpServletResponse response, IndexForm form)throws Exception {
		String forwardString="frame/count";
		Map<String,Object> model = new HashMap<String,Object>();
		try{
			Map<String,Object> condition=form.getCondition();
			form.setUrl(request.getRequestURL().toString());
			//获取最新的产品数量（根据上架日期统计）
			condition.put("total", 0);
			form.setCount(this.statisticService.getSOnsaleTotal(condition));
			//form.setCount(this.pPublicationsService.getPubCount(condition));
			List<SOnsale> sOnsalePagingList = this.statisticService.getSOnsalePagingList(condition, " order by a.saleDate desc ", 20, 0);
			//List<PPublications> pubCountByDateList = this.pPublicationsService.getPubCountPageList(condition, " group by a.createDate order by a.createDate desc", 20, 0);
			model.put("pubCountList", sOnsalePagingList);
			model.put("form", form);
		}catch(Exception e){
            request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		return new ModelAndView(forwardString, model);
	}
	
	//将上一次的查询条件去掉
	private void removeCondition(Map<String,Object> map ,String condition){
		if(CollectionsUtil.exist(map, condition)){
			map.remove(condition);
		}
	}
}
