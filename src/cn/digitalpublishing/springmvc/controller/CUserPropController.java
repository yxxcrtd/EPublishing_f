
package cn.digitalpublishing.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.ResultObject;
import cn.com.daxtech.framework.util.ObjectUtil;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.CUserProp;

@Controller
@RequestMapping("/rest/userprop")
public class CUserPropController extends BaseController {
	
	@RequestMapping(value = "/getUserPropList")
	public void getUserPropList (HttpServletRequest request, Model model) throws Exception {
	
		ResultObject<CUserProp> result = null;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("sendStatus", 1);
		try {
//			List<CUserProp> list = this.cUserPropService.getCUserPropList(condition, "");
//			if (list != null) {
//				ObjectUtil<CUserProp> util = new ObjectUtil<CUserProp>();
//				for (int i = 0; i < list.size(); i++) {
//					util.setNull(list.get(i), new String[] { Set.class.getName() });
//				}
//			}
//			result = new ResultObject<CUserProp>(1, list, Lang.getLanguage("Controller.User.getUserList.query.success", request.getSession().getAttribute("lang").toString()));// "获取人员列表成功！");
		} catch (Exception e) {
			result = new ResultObject<CUserProp>(2, (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString())
					: e.getMessage());
		}
		model.addAttribute("target", result);
		
	}
	
}
