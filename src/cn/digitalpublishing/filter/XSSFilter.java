package cn.digitalpublishing.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;

@SuppressWarnings({ "serial", "unused", "rawtypes" })
public class XSSFilter extends HttpServlet implements Filter {

	private FilterConfig filterConfig;
	private String POLICY_FILE_LOCATION = (this.getClass().getClassLoader().getResource("").getPath() + "cn/digitalpublishing/resource/antisamy-ebay-1.4.4.xml").replace("%20", " ");

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			//			System.out.println("*******************POLICY*******************:"+POLICY_FILE_LOCATION);
			Policy policy = Policy.getInstance(POLICY_FILE_LOCATION); // Create Policy object

			AntiSamy as = new AntiSamy(); // Create AntiSamy object

			//			boolean result=true;
			//获取参数
			Enumeration enu = request.getParameterNames();
			int find = 0;
			while (enu.hasMoreElements()) {
				String paraName = (String) enu.nextElement();
				String[] vals = request.getParameterValues(paraName);
				for (String val : vals) {
					CleanResults cr = as.scan(val, policy, AntiSamy.SAX); // Scan dirtyInput
					if (cr.getNumberOfErrors() > 0) {
						//参数中存在非法信息
						find++;
						System.out.println(val);
						break;
					}
				}
				if (find > 0) {
					System.out.println("---存在非法信息-----");
					request.setAttribute("msg", false);
					break;
				}
			}
			chain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
