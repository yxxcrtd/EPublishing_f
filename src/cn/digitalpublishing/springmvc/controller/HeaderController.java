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
import cn.digitalpublishing.ep.po.BConfiguration;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.BIpRange;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.springmvc.form.index.IndexForm;
import cn.digitalpublishing.util.web.IpUtil;

/**
 * 顶部 Controller
 */
@Controller
@RequestMapping("/pages/header")
public class HeaderController extends BaseController {

	@RequestMapping("headerData")
	public ModelAndView headerData(HttpServletRequest request, HttpServletResponse response, IndexForm form) throws Exception {
		String forwardString = "frame/header";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Map<String, Object> condition = form.getCondition();
			// condition.put("type", 2); // 1、基础参数；2、页面显示参数
			List<BConfiguration> list = this.configureService.getConfigureList(condition, "");
			Map<String, Object> result = new HashMap<String, Object>();
			if (list != null && list.size() > 0) {
				for (BConfiguration bConfiguration : list) {
					result.put(bConfiguration.getCode(), bConfiguration);
				}
			}
			BInstitution ins = null;

			// 刷新购物车数量
			if (request.getSession().getAttribute("mainUser") != null) {
				CUser user = (CUser) request.getSession().getAttribute("mainUser");
				Map<String, Object> cartCondition = new HashMap<String, Object>();
				cartCondition.put("status", 4);
				cartCondition.put("userid", user.getId());
				cartCondition.put("parentId", "0");
				cartCondition.put("orderNull", "1");
				request.getSession().setAttribute("totalincart", this.oOrderService.getOrderDetailCount(cartCondition));
				if (user.getInstitution() != null) {
					ins = this.configureService.getInstitution(user.getInstitution().getId());
					model.put("insInfo", ins);
				}
			}
			Map<String, Object> conditionIns = new HashMap<String, Object>();
			conditionIns.put("ip", IpUtil.getLongIp(IpUtil.getIp(request)));
			List<BIpRange> ipList = this.configureService.getIpRangeList(conditionIns, "");

			if (ipList != null && ipList.size() > 0) {
				ins = ipList.get(0).getInstitution();
				model.put("insInfo", ins);
			}

			//Map<String, Object> subCondition = new HashMap<String, Object>();
			//subCondition.put("treeCodeLength", 6);
			//List<BSubject> subList = this.bSubjectService.getSubList(subCondition, " order by a.order ");
			//model.put("subList", subList);

			model.put("map", result);
			model.put("form", form);
		} catch (Exception e) {
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

}
