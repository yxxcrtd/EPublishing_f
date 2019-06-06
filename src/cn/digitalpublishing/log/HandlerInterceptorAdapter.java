package cn.digitalpublishing.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.portlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public abstract class HandlerInterceptorAdapter implements HandlerInterceptor {

	/**
	 * This implementation always returns <code>true</code>.
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	/**
	 * This implementation is empty.
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * This implementation is empty.
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
