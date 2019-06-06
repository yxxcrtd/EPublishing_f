package cn.digitalpublishing.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.digitalpublishing.ep.po.CUser;

public class CartInterceptor extends ActionInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

		System.out.println("Cart拦截器结束。。。。");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView mv) throws Exception {
		System.out.println("Cart拦截器工作。。。。");
		String s = "------- Inteceptor --------: HelloWord !";
		System.out.println(s);
	}

	@SuppressWarnings("unused")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		// TODO Auto-generated method stub  
		System.out.println("Cart拦截器开启。。。。");
		Integer level = null;
		CUser user = null;
		String result = "";
		if (request.getSession().getAttribute("mainUser") != null) {
			user = (CUser) request.getSession().getAttribute("mainUser");
		}
		level = user == null ? null : user.getLevel();
		//		if(1==level||5==level)
		//		{
		//			result="error:"+Lang.getLanguage("Interceptor.Cart.preHandle.buy.error",request.getSession().getAttribute("lang").toString());//购买功能未对个人用户开通"
		//			response.setContentType("text/html;charset=UTF-8");
		//			PrintWriter out = response.getWriter();
		//			out.print(result);
		//			out.flush();
		//			out.close();
		//			return false;
		//		}
		return true;
	}

}
