package cn.digitalpublishing.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;
import cn.digitalpublishing.util.io.MD5Util;
import cn.digitalpublishing.util.web.IpUtil;

@SuppressWarnings({ "serial", "unused" })
public class ComplicatingFilter extends HttpServlet implements Filter {

	private FilterConfig filterConfig;
	private static final Pattern requestFilter = Pattern.compile("(/pages/(?!cart/)[^/]+|index)");
	private ServiceFactory serviceFactory = (ServiceFactory) new ServiceFactoryImpl();

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chin) throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			HttpSession session = req.getSession();
			String path = req.getServletPath();

			if (path.contains("/pages/complication/close")) {
				//不在阅读页，清空
				String md5Str = "";
				Cookie[] cookies = req.getCookies();
				if (cookies != null && cookies.length > 0) {
					for (Cookie cookie : cookies) {
						if ("readCookie".equals(cookie.getName())) {
							md5Str = cookie.getValue();
							break;
						}
					}
				}
				if ("".equals(md5Str)) {
					md5Str = MD5Util.getEncryptedPwd(session.getId());
					Cookie c = new Cookie("readCookie", md5Str);
					c.setMaxAge(60 * 60 * 24 * 365);
					c.setPath(req.getContextPath());
					res.addCookie(c);
				}
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("ip", IpUtil.getLongIp(IpUtil.getIp(req)));
				condition.put("status", 1);
				/*
				 * List<LLicenseIp> list =
				 * this.serviceFactory.getPPublicationsService().
				 * getLicenseIpList(condition,"");
				 * if(list!=null&&list.size()>0){ for(LLicenseIp
				 * licenseIp:list){ LLicense license = licenseIp.getLicense();
				 * if(license.getComplicating()!=null&&license.getComplicating()
				 * !=0){//即有并发限制 Map<String,Object> uCon = new HashMap<String,
				 * Object>(); uCon.put("licenseId", license.getId());
				 * uCon.put("macId", md5Str);
				 * this.serviceFactory.getPPublicationsService().
				 * deleteComplicatingByCondition(uCon); } } }else{ CUser user =
				 * session.getAttribute("mainUser")==null?null:(CUser)session.
				 * getAttribute("mainUser"); if(user!=null){ Map<String,Object>
				 * map2 = new HashMap<String,Object>(); map2.put("status", 1);
				 * map2.put("userid", user.getId()); List<LLicense> list2 =
				 * this.serviceFactory.getPPublicationsService().getLicenseList(
				 * map2,""); if(list2!=null&&list2.size()>0){ for (LLicense
				 * license : list2) {
				 * if(license.getComplicating()!=null&&license.getComplicating()
				 * !=0){//即有并发限制 Map<String,Object> uCon = new HashMap<String,
				 * Object>(); uCon.put("licenseId", license.getId());
				 * uCon.put("macId", md5Str);
				 * this.serviceFactory.getPPublicationsService().
				 * deleteComplicatingByCondition(uCon); } } } } }
				 */
				Map<String, Object> compMap = (Map<String, Object>) session.getAttribute("compMap");
				if (compMap != null && !compMap.isEmpty()) {
					for (String key : compMap.keySet()) {
						Map<String, Object> uCon = new HashMap<String, Object>();
						uCon.put("licenseId", key);
						uCon.put("macId", md5Str);
						uCon.put("endTime", null);
						this.serviceFactory.getPPublicationsService().getEnd(uCon);
					}
				}
				chin.doFilter(request, response);
			} else {
				chin.doFilter(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
