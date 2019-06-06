package cn.digitalpublishing.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.servlet.LoadAccessResource;
import cn.digitalpublishing.util.bean.Access;

@SuppressWarnings("serial")
public class UserAccessFilter extends HttpServlet implements Filter {

	@SuppressWarnings("unused")
	private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chin) throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			String path = req.getServletPath();
			LoadAccessResource.loadResource(path);
			//				System.out.println("path="+path);
			Boolean flag = true;

			String userTypeId = null;

			//				if(Access.getResource()==null){
			//					System.out.println("Access.getResource() is null ");
			//				}else{
			//					System.out.println("Access.getResource() is not null, it's size is  "+Access.getResource().size());
			//				}

			if (Access.getResource() != null && Access.getResource().containsKey(path)) {//没有权限设置就都能访问
				System.out.println("path is exist");
				if (session.getAttribute("mainUser") != null) {//没有用户登录就都能访问，走用户Session过期过滤器
					System.out.println("user is exist");
					CUser user = (CUser) session.getAttribute("mainUser");
					userTypeId = user.getUserType().getId();
					System.out.println("user type is " + userTypeId);
					if (userTypeId != null && !"".equals(userTypeId) && Access.getResource().get(path).containsKey(userTypeId)) {
						flag = Access.getResource().get(path).get(userTypeId);
						System.out.println("访问权限：" + Access.getResource().get(path));
						System.out.println("访问权限：" + flag);
					}
				}
			}

			if (!flag) {
				req.setAttribute("prompt", Lang.getLanguage("Url.Access.Forbidden.Prompt", req.getSession().getAttribute("lang").toString()));
				req.setAttribute("message", Lang.getLanguage("Url.Access.Not.Permit", req.getSession().getAttribute("lang").toString()));
				req.getRequestDispatcher("/pages/error.jsp").forward(request, response);
				return;
			}

			chin.doFilter(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
