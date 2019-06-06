package cn.digitalpublishing.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

import cn.com.daxtech.framework.model.Param;
import cn.digitalpublishing.ep.po.BIpRange;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;
import cn.digitalpublishing.util.web.IpUtil;

@SuppressWarnings({ "serial", "unused", "unchecked" })
public class UserFilter extends HttpServlet implements Filter {

	private FilterConfig filterConfig;
	private static final Pattern requestFilter = Pattern.compile("(/pages/(?!cart/)[^/]+|index)");
	private ServiceFactory serviceFactory = (ServiceFactory) new ServiceFactoryImpl();

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chin) throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			String ctx = req.getContextPath();
			String domain = req.getServerName();
			HttpSession session = req.getSession();
			String path = req.getServletPath();
			Object isO = Param.getParam("system.config").get("isOpenToAll");
			Boolean isOpenToAll = isO != null && "true".equals(isO.toString().toLowerCase());//是否对所有用户开放 true 是，false 否（IP范围外用户必须登录）
			//Boolean isOpenToAll=true;

			if (!path.contains("/receiveEpubFile") && !path.contains("/commonUpload") && !path.contains("/language") && !path.endsWith("/login") && !path.endsWith("/login_1.jsp") && !path.endsWith("/login_zh.jsp") && !path.endsWith("/login_en.jsp") && !path.contains("/css") && !path.contains("/js") && !path.contains("/images") && !path.endsWith(".xml")) {//请求类型判断：不是
				if (session.getAttribute("isFristRequest") == null || "yes".equals(session.getAttribute("isFristRequest"))) {//第一次请求判断：如果是
					session.setAttribute("isFristRequest", "no");

					//登陆机构默认用户
					String ip = IpUtil.getIp(req);
					//验证IP范围
					Map<String, Object> condition = new HashMap<String, Object>();
					condition.put("ip", IpUtil.getLongIp(ip));
					List<BIpRange> ipList = this.serviceFactory.getConfigureService().getIpRangeList(condition, "");
					BIpRange ipRange = new BIpRange();
					if (ipList != null && !ipList.isEmpty()) {//IP范围内判断：如果在IP范围内，设置成默认订阅范围内
						ipRange = ipList.get(0);
						if (isOpenToAll)
							session.setAttribute("selectType", session.getAttribute("selectType") == null ? "1" : session.getAttribute("selectType"));
						/**
						 * 根据机构信息查询用户信息 查询level为3的* 1-普通用户（用户受IP范围限制）
						 * 2-特殊用户（用户不受IP范围限制） 3-默认用户 （该用户每个机构只有一个）
						 */
						condition.put("level", 3);
						condition.put("institutionId", ipRange.getInstitution() == null ? "" : ipRange.getInstitution().getId());
						List<CUser> userList = this.serviceFactory.getCUserService().getUserList(condition, "");
						if (userList != null && userList.size() > 0) {
							CUser user = userList.get(0);
							session.setAttribute("institution", user.getInstitution());
							session.setAttribute("ipUserId", user.getId());
							if (session.getAttribute("recommendUser") == null) {
								session.setAttribute("recommendUser", user);
							}
							login(session, user);
						} //登陆机构默认用户结束

						//登陆Cookie中保存的用户
						Cookie[] cookies = req.getCookies();
						if (cookies != null && cookies.length > 0) {//登陆Cookie用户开始
							for (Cookie cook : cookies) {
								if ("account".equals(cook.getName())) {
									if (cook.getValue() != null && !"".equals(cook.getValue())) {
										String[] ids = cook.getValue().split(",");
										for (int i = 0; i < ids.length; i++) {
											String[] cookvalue = ids[i].split(":");
											if (cookvalue.length != 2) {
												continue;
											}

											CUser user = this.serviceFactory.getCustomService().getUser(cookvalue[1]);
											if (user != null) {
												if (ids.length - i <= 1) {
													session.setAttribute("mainUser", user);
													session.setAttribute("mainUserLevel", user.getLevel());
												}
												//---------------荐购用户登录session处理
												if (session.getAttribute("mainUser") != null) {//用户在IP范围内				
													session.setAttribute("recommendUser", session.getAttribute("mainUser"));
												} else if (session.getAttribute("ipUserId") != null) {
													Map<String, CUser> users = (HashMap<String, CUser>) session.getAttribute("otherUser");
													session.setAttribute("recommendUser", users.get(session.getAttribute("ipUserId")));
												} else {
													session.setAttribute("recommendUser", null);
												}
												//登录其他用户
												login(session, user);
											}
										}
									}
									break;
								}
							}
						} //登陆Cookie用户结束

						//拼接页面显示用的HTML代码
						/*
						 * Map<String,CUser>
						 * users=(HashMap<String,CUser>)session.getAttribute(
						 * "otherUser"); if(users!=null && users.size()>0){
						 * Iterator it = users.entrySet().iterator(); //取得键对象
						 * 
						 * String org =""; String ins = ""; String admin="";
						 * while (it.hasNext()){ Map.Entry pairs =
						 * (Map.Entry)it.next(); CUser
						 * user1=(CUser)pairs.getValue(); if(1==user1.getLevel()
						 * || 5==user1.getLevel()){ ins+=
						 * "<div class=\"loginboxChild\">" + user1.getName() +
						 * "</div>"; }else if(2==user1.getLevel() ||
						 * 3==user1.getLevel()){ org +=
						 * "<div class=\"loginboxChild\">" + user1.getName() +
						 * "</div>"; }else if(4==user1.getLevel()){ admin +=
						 * "<div class=\"loginboxChild\">" + user1.getName() +
						 * "</div>"; } } String cleardiv=
						 * "<div class=\"clear\"></div>"; if(admin!=null &&
						 * admin.length()>1){ admin =
						 * "<div class=\"loginboxParent\">"
						 * +Lang.getLanguage("Controller.User.login.label.admin"
						 * ,session.getAttribute("lang").toString())+"</div>"+
						 * admin +cleardiv ;//超级管理员： } if(org!=null &&
						 * org.length()>1){ org =
						 * "<div class=\"loginboxParent\">"+Lang.getLanguage(
						 * "Controller.User.login.label.insUser",
						 * session.getAttribute("lang").toString())+"</div>"+org
						 * + cleardiv;//机构用户： } if(ins!=null && ins.length()>1){
						 * ins ="<div class=\"loginboxParent\">"
						 * +Lang.getLanguage(
						 * "Controller.User.login.label.person",
						 * session.getAttribute("lang").toString())+"</div>"+ins
						 * +cleardiv;//个人用户： } //若用户所属机构的信息中包含logo，取出 String
						 * logo=null; if( ipRange.getInstitution()!=null &&
						 * ipRange.getInstitution().getLogo()!=null){ Boolean
						 * hasurl=false,hasnote=false; logo="";
						 * if(ipRange.getInstitution().getLogoUrl()!=null &&
						 * !"".equals(ipRange.getInstitution().getLogoUrl().trim
						 * ()) ){ hasurl=true; logo+=
						 * "<a target=\"_blank\" href=\"" +
						 * ipRange.getInstitution().getLogoUrl()+ "\" >"; }
						 * if(ipRange.getInstitution().getLogoNote()!=null &&
						 * !"".equals(ipRange.getInstitution().getLogoNote().
						 * trim()) ){ hasnote=true; } logo+=
						 * "<img style=\"width:187px;height:35px; margin-bottom:13px;\"  src=\""
						 * +(ctx!=null&&!"".equals(ctx)?ctx:"http://"+domain)+
						 * ipRange.getInstitution().getLogo() + "\" title=\""+
						 * (hasnote
						 * ?ipRange.getInstitution().getLogoNote():"")+"\"/>";
						 * logo+=(hasurl?"</a>":"") ;
						 * System.out.println("XXXXXXXXXXXXXXXXLogo:"+logo);
						 * session.setAttribute("logoinfo", logo); }
						 * //session.setAttribute("logininfo",admin + org +
						 * ins); }
						 */
						chin.doFilter(request, response);
					} else {//IP范围内判断：如果不在IP范围内
						if (!isOpenToAll) {
							//						req.getRequestDispatcher("/pages/login_1.jsp").forward(req, res);
							if (req.getSession().getAttribute("lang") != null) {
								if ("zh_CN".equalsIgnoreCase(req.getSession().getAttribute("lang").toString())) {
									req.getRequestDispatcher("/pages/login_zh.jsp").forward(req, res);
								} else {
									req.getRequestDispatcher("/pages/login_en.jsp").forward(req, res);
								}
								return;
							} else {
								Locale defaultLocale = Locale.getDefault();
								String country = defaultLocale.getCountry();//返回国家地区代码
								if (country.equalsIgnoreCase("cn")) {
									req.getRequestDispatcher("/pages/login_zh.jsp").forward(req, res);
								} else {
									req.getRequestDispatcher("/pages/login_en.jsp").forward(req, res);
								}
								return;
							}
						} else {
							session.setAttribute("selectType", session.getAttribute("selectType") == null ? "" : session.getAttribute("selectType"));
							chin.doFilter(request, response);
						}
						//						res.sendRedirect("pages/login_1.jsp");//跳转到信息提示页面！！
					} //IP范围内判断：结束
				} else {//第一次请求判断：如果不是
					if (session.getAttribute("institution") == null && session.getAttribute("mainUser") == null) {
						Locale defaultLocale = Locale.getDefault();
						String country = defaultLocale.getCountry();//返回国家地区代码
						if (!isOpenToAll) {
							if (country.equalsIgnoreCase("cn")) {
								req.getRequestDispatcher("/pages/login_zh.jsp").forward(req, res);
							} else {
								req.getRequestDispatcher("/pages/login_en.jsp").forward(req, res);
							}
							return;
						} else {
							session.setAttribute("selectType", session.getAttribute("selectType") == null ? "" : session.getAttribute("selectType"));
							chin.doFilter(request, response);
						}
					} else {
						session.setAttribute("selectType", session.getAttribute("selectType") == null ? "1" : session.getAttribute("selectType"));
						chin.doFilter(request, response);
					}
				} //第一次请求判断：结束
			} else {//请求类型判断：如果是
				chin.doFilter(request, response);
			} //请求类型判断：结束
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	private void login(HttpSession session, CUser user) throws Exception {

		//-------------其他用户session处理
		if (session.getAttribute("otherUser") == null) {
			Map<String, CUser> otheruser = new HashMap<String, CUser>();
			otheruser.put(user.getId(), user);
			session.setAttribute("otherUser", otheruser);//副登陆session中无值时直接插入
		} else {
			Map<String, CUser> users = (HashMap<String, CUser>) session.getAttribute("otherUser");
			users.put(user.getId(), user);
			session.setAttribute("otherUser", users);
		}

	}
}
