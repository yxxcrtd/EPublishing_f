package cn.digitalpublishing.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.digitalpublishing.util.web.DaxRequestWrapper;

@SuppressWarnings("serial")
public class PaginationFilter extends HttpServlet implements Filter {

	private static final Pattern PAGENUMBER = Pattern.compile("^-?\\d+$");//^-?[1-9]\d*$,^(\\d+)$

	private String curpage = "0";

	private String pageCount = "10";

	private String pageCountRange = "";

	private String url = "error.jsp";

	private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
		this.curpage = filterConfig.getInitParameter("curpage");
		this.pageCount = filterConfig.getInitParameter("pageCount");
		this.pageCountRange = filterConfig.getInitParameter("pageCountRange");
		this.url = filterConfig.getInitParameter("url");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chin) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		try {
			DaxRequestWrapper wrapper = new DaxRequestWrapper(req);
			if (req.getRequestURL().toString().indexOf("/pages") > 0) {
				String curpage = (String) req.getParameter("curpage");
				String pageCount = (String) req.getParameter("pageCount");
				//不允许负数，默认成1
				if (curpage != null) {
					Matcher match = PAGENUMBER.matcher(curpage);
					if (!match.find()) {
						throw new CcsException("paging.curpage.Incorrect");
					}
					if (Integer.valueOf(curpage) <= 0) {
						curpage = this.curpage;
					}
				}
				//不允许负数，默认成10
				if (pageCount != null) {
					Matcher match = PAGENUMBER.matcher(pageCount);
					if (!match.find()) {
						throw new CcsException("paging.pageCount.Incorrect");
					}
					if (Integer.valueOf(pageCount) <= 0) {
						pageCount = this.pageCount;
					} else {
						if (!"".equals(this.pageCountRange)) {
							String countTemp = "";
							String[] pageCounts = this.pageCountRange.split(",");
							for (String count : pageCounts) {
								Matcher m = PAGENUMBER.matcher(pageCount);
								if (!m.find()) {
									throw new CcsException("paging.pageCountRange.Incorrect");
								}
								if (count.trim().equals(pageCount)) {
									countTemp = pageCount;
									break;
								}
							}
							if ("".equals(countTemp)) {
								pageCount = this.pageCount;
							} else {
								pageCount = countTemp;
							}
						}
					}
				}
				if (curpage != null) {
					wrapper.addParameter("curpage", curpage);
				}
				if (pageCount != null) {
					wrapper.addParameter("pageCount", pageCount);
				}
			}
			chin.doFilter((HttpServletRequest) wrapper, response);
		} catch (Exception e) {
			req.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), req.getSession().getAttribute("lang").toString()) : e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher(this.url);
			rd.forward(req, res);
		}
	}

	@Override
	public void destroy() {
		this.curpage = "0";
		this.pageCount = "10";
		this.pageCountRange = "";
		this.url = "error.jsp";
	}
}
