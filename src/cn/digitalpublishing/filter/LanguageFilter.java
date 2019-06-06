package cn.digitalpublishing.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SuppressWarnings("serial")
public class LanguageFilter extends HttpServlet implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chin) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if (null == req.getSession().getAttribute("lang") || "".equals(req.getSession().getAttribute("lang").toString())) {
			SessionLocaleResolver sl = new SessionLocaleResolver();
			Locale locale = sl.resolveLocale(req);
			String lang = locale.getLanguage() + "_" + locale.getCountry();
			if (!"zh_CN".equals(lang) && !"en_US".equals(lang)) {
				lang = "en_US";
			}

			// req.getSession().setAttribute("lang", lang);
			req.getSession().setAttribute("lang", "en_US"); // 默认显示英文的首页
		}
		chin.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
