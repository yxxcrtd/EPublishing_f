package cn.digitalpublishing.springmvc.controller;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.orm.hibernate3.HibernateJdbcException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.Param;
import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.CAccount;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.CUserProp;
import cn.digitalpublishing.ep.po.CUserType;
import cn.digitalpublishing.ep.po.CUserTypeProp;
import cn.digitalpublishing.springmvc.form.custom.OrgUserForm;
import cn.digitalpublishing.util.web.RandomCodeUtil;

@Controller
@RequestMapping("/pages/orgUser")
public class OrgUserController extends BaseController {
	@RequestMapping(value = "/form/register")
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response, OrgUserForm form) throws Exception {
		String forwardString = "user/orgRegistration";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			form.setUrl(request.getRequestURL().toString());
			Map<String, Object> condition = form.getCondition();
			// 查询“机构”类型属性数据
			condition.put("userTypeCode", "2");
			condition.put("status", 1);
			form.setCountryMap(Param.getParam("country.value", true, request.getSession().getAttribute("lang").toString()));
			List<CUserTypeProp> list = this.customService.getUserTypePropList(condition, " order by a.must desc,a.order ");
			model.put("list", list);
			form.setOrgTypeMap(Param.getParam("organization.type.value", true, request.getSession().getAttribute("lang").toString()));
			form.setInstitutionList(this.customService.getInstitutionList(null, null));
			if (form.getPropsValue() != null) {
				if (list != null && !list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						form.getValues().put(list.get(i).getCode(), form.getPropsValue()[i]);
					}
				}
			}
			model.put("form", form);
		} catch (Exception e) {
			request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.register.prompt.success", request.getSession().getAttribute("lang").toString()));// "注册失败提示");
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/form/registerSubmit")
	public ModelAndView registerSubmit(HttpServletRequest request, HttpServletResponse response, OrgUserForm form) throws Exception {
		String forwardString = "frame/result";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			boolean msg = (Boolean) (request.getAttribute("msg") == null ? true : request.getAttribute("msg"));
			if (msg) {
				form.setUrl(request.getRequestURL().toString());
				// 验证账号Uid的唯一性
				boolean uidUnique = this.customService.checkUidExist(null, form.getAccount().getUid());
				if (uidUnique) {
					form.setMsg(Lang.getLanguage("Controller.OrgUser.registerSubmit.msg.alreadyName.error", request.getSession().getAttribute("lang").toString()));// "已经存在该用户名，请重新输入！");
					return register(request, response, form);
				}
				// 验证邮箱的唯一性
				String emailValue = "";
				String insId = "";
				for (int i = 0; i < form.getTypePropIds().length; i++) {
					// 根据页面传的类型属性id获取类型属性详细信息，并存储到用户属性中。
					CUserTypeProp typeProp = this.customService.getUserTypePropById(form.getTypePropIds()[i].toString());
					if (typeProp != null && typeProp.getCode().equals("email")) {
						emailValue = form.getPropsValue()[i].toString();
					}
					if (typeProp != null && typeProp.getCode().equals("institutionId")) {
						insId = form.getPropsValue()[i].toString();
					}
				}
				if (this.customService.checkEmailExist(null, emailValue)) {
					form.setMsg(Lang.getLanguage("Controller.OrgUser.registerSubmit.msg.alreadyEmail.error", request.getSession().getAttribute("lang").toString()));// "已经存在该邮箱，请重新输入！");
					return register(request, response, form);
				}

				// 新增用户信息
				CUser user = this.insertUser(form.getUserName(), form.getUserType(), insId);
				// 新增用户属性信息
				this.insertUserProp(form.getTypePropIds(), form.getPropsValue(), user);
				// 新增账户信息

				String initialPwd = form.getAccount().getPwd();// RandomCodeUtil.generateRandomCode(10);
				CAccount account = this.insertAccount(form.getAccount().getUid(), initialPwd, user);
				// 给注册用户发送确认邮件
				String sm = request.getParameter("sendMail");

				if (sm != null && "on".equals(sm)) {
					form.setSendpwd(OrgUserForm.PWD_SEND);
				} else {
					form.setSendpwd(OrgUserForm.PWD_NO_SEND);
				}
				boolean isSuccess = true;
				if (form.getSendpwd() == OrgUserForm.PWD_SEND) {
					isSuccess = this.sendEmail(emailValue, user.getName(), account.getUid(), initialPwd, Param.getParam("mail.template.orgRegister", true).get("orgRegister"), request.getSession().getAttribute("lang").toString());
					if (isSuccess) {
						request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.registerSubmit.prompt.success", request.getSession().getAttribute("lang").toString()));// "注册成功提示");
						request.setAttribute("message", Lang.getLanguage("Controller.OrgUser.registerSubmit.register.email", request.getSession().getAttribute("lang").toString()));// "注册成功，请查收账号邮件并及时修改初始密码！");
					} else {
						request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.registerSubmit.prompt.success", request.getSession().getAttribute("lang").toString()));// "注册成功提示");
						request.setAttribute("message", Lang.getLanguage("Controller.OrgUser.registerSubmit.register.noEmail", request.getSession().getAttribute("lang").toString()));// "注册成功，但邮件发送失败，请联系管理员！");
					}
				} else {
					request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.registerSubmit.prompt.success", request.getSession().getAttribute("lang").toString()));// "注册成功提示");
					request.setAttribute("message", Lang.getLanguage("Controller.OrgUser.registerSubmit.register.notSendemail", request.getSession().getAttribute("lang").toString()));// "注册成功，请查收账号邮件并及时修改初始密码！");
				}
			} else {
				request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.registerSubmit.prompt.error", request.getSession().getAttribute("lang").toString()));// "注册失败提示");
				request.setAttribute("message", Lang.getLanguage("Conteoller.Global.prompt.info", request.getSession().getAttribute("lang").toString()));// "注册失败");
			}
		} catch (Exception e) {
			request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.registerSubmit.prompt.error", request.getSession().getAttribute("lang").toString()));// "注册失败提示");
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			return register(request, response, form);
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/form/registerCheck")
	public ModelAndView registerCheck(HttpServletRequest request, HttpServletResponse response, OrgUserForm form) throws Exception {
		String forwardString = "frame/result";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String accountId = request.getParameter("accountId");
			CAccount account = this.customService.getAccountById(accountId);
			if (account != null) {
				account.setStatus(1);
				this.customService.updateAccount(account, accountId, null);
				CUser user = this.customService.getUser(account.getUser().getId());
				user.setStatus(1);
				this.customService.updateUser(user, account.getUser().getId(), null);
				request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.registerCheck.prompt.success", request.getSession().getAttribute("lang").toString()));// "激活成功提示");
				request.setAttribute("message", Lang.getLanguage("Controller.OrgUser.registerCheck.message.activation.success", request.getSession().getAttribute("lang").toString()));// "账号激活成功！");
			} else {
				request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.registerCheck.prompt.error", request.getSession().getAttribute("lang").toString()));// "激活失败提示");
				request.setAttribute("message", Lang.getLanguage("Controller.OrgUser.registerCheck.message.activation.error", request.getSession().getAttribute("lang").toString()));// "不存在该账户，激活失败！");
			}
		} catch (Exception e) {
			request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.registerCheck.prompt.success", request.getSession().getAttribute("lang").toString()));// "激活失败提示");
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 新增用户信息
	 * 
	 * @param name
	 * @param userTypeId
	 * @return
	 * @throws Exception
	 */
	private CUser insertUser(String name, String userTypeId, String insId) throws Exception {
		CUser user = new CUser();
		user.setCreatedon(new Date());
		user.setName(name);
		user.setStatus(1);
		user.setLevel(2);
		user.setSendStatus(1);
		CUserType userType = new CUserType();
		userType.setId(userTypeId);
		user.setUserType(userType);

		BInstitution institution = new BInstitution();
		institution.setId(insId);
		user.setInstitution(institution);

		this.customService.insertCUser(user);
		return user;
	}

	/**
	 * 新增账户信息 密码为当前日期初始密码
	 * 
	 * @param uid
	 * @param pwd
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private CAccount insertAccount(String uid, String pwd, CUser user) throws Exception {
		CAccount account = new CAccount();
		account.setCreatedon(new Date());
		account.setUser(user);
		account.setUid(uid);
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		pwd = md5.encodePassword(pwd, uid);
		account.setPwd(pwd);
		account.setStatus(1);
		this.customService.insertAccount(account);
		return account;
	}

	/**
	 * 新增用户属性信息，同时返回用户email，用于用户邮箱激活功能
	 * 
	 * @param propIds
	 * @param propValues
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private void insertUserProp(Object[] propIds, Object[] propValues, CUser user) throws Exception {
		for (int i = 0; i < propIds.length; i++) {
			CUserProp userProp = new CUserProp();
			userProp.setUser(user);
			// 根据页面传的类型属性id获取类型属性详细信息，并存储到用户属性中。
			CUserTypeProp typeProp = this.customService.getUserTypePropById(propIds[i].toString());
			userProp.setUserTypeProp(typeProp);
			userProp.setCode(typeProp.getCode());
			userProp.setKey(typeProp.getKey());
			userProp.setVal(propValues[i].toString());
			userProp.setOrder(i);
			userProp.setDisplay(typeProp.getDisplay());
			userProp.setStype(typeProp.getStype());
			userProp.setMust(typeProp.getMust());
			userProp.setSvalue(typeProp.getSvalue());
			userProp.setCreatedon(new Date());
			this.customService.insertCUserProp(userProp);
		}
	}

	/**
	 * 用户注册成功后向用户注册时填写的邮箱发送邮件进行激活
	 * 
	 * @param email
	 * @param userName
	 * @param accountId
	 * @return
	 */
	private boolean sendEmail(String email, String userName, String uid, String initialPwd, String templateName, String lang) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String today = formatter.format(new Date());
		Map<String, String> body = new HashMap<String, String>();
		body.put("username", userName);
		body.put("content1", Lang.getLanguage("Controller.OrgUser.sendEmail.content1", lang));// "您好");
		body.put("content2", Lang.getLanguage("Controller.OrgUser.sendEmail.content2", lang));// "欢迎你注册中图书苑，请点及时修改初始账户密码!");
		body.put("date", today);
		body.put("uid", uid);
		body.put("pwd", initialPwd);
		Map<String, String> title = new HashMap<String, String>();
		title.put("username", userName);
		return this.sendMail.sendMail(title, body, "EPublishing", email, "A1B2", templateName);
	}

	@RequestMapping(value = "/form/findPwd")
	public ModelAndView findPwd(HttpServletRequest request, HttpServletResponse response, OrgUserForm form) throws Exception {
		String forwardString = "user/findPwd";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			form.setUrl(request.getRequestURL().toString());
		} catch (Exception e) {
			request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.findPwd.prompt.error", request.getSession().getAttribute("lang").toString()));// "找回密码失败提示");
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/form/findPwdSubmit")
	public void findPwdSubmit(HttpServletRequest request, HttpServletResponse response, OrgUserForm form) throws Exception {
		String result = Lang.getLanguage("Controller.OrgUser.findPwdSubmit.email.success", request.getSession().getAttribute("lang").toString());// "请查收修改密码邮件!";
		String uidPage = request.getParameter("uid");
		String emailPage = request.getParameter("email");
		Map<String, Object> condition = new HashMap<String, Object>();

		if (uidPage != "" && emailPage != "") {// 通过uid和email找回密码
			condition.put("uid", uidPage);
			List<CAccount> accounts = this.customService.getAccountList(condition, null);
			condition.put("email", emailPage);
			condition.put("code", "email");
			List<CUserProp> userProps = this.customService.getUserPropList(condition, null);
			boolean flag = true;// 用于判断校验是否通过
			if (accounts == null || accounts.size() == 0) {
				result = Lang.getLanguage("Controller.OrgUser.findPwdSubmit.noName.error", request.getSession().getAttribute("lang").toString());// "不存在该用户名!";
				flag = false;
			} else if (userProps == null || userProps.size() == 0) {
				result = Lang.getLanguage("Controller.OrgUser.findPwdSubmit.noEmail.error", request.getSession().getAttribute("lang").toString());// "不存在该邮箱!";
				flag = false;
			}
			if (flag) {
				// 发邮件
				// boolean sendSuccess =
				// this.sendEmail(userProps.get(0).getVal(),
				// userProps.get(0).getUser().getName(),
				// accounts.get(0).getId(),Param.getParam("mail.template.findPwd",true).get("findPwd"));
			}
		} else if (uidPage != "") {// 通过uid找回密码
			condition.put("uid", uidPage);
			List<CAccount> accounts = this.customService.getAccountList(condition, null);
			if (accounts == null || accounts.size() == 0) {
				result = Lang.getLanguage("Controller.OrgUser.findPwdSubmit.noName.error", request.getSession().getAttribute("lang").toString());// "不存在该用户名!";
			} else {
				// 发邮件
				CAccount account = accounts.get(0);
				condition.remove("email");
				condition.put("userId", account.getUser().getId());
				condition.put("code", "email");
				List<CUserProp> userProps = this.customService.getUserPropList(condition, null);
				CUserProp userProp = userProps.get(0);

				// boolean sendSuccess = this.sendEmail(userProp.getVal(),
				// userProp.getUser().getName(), account.getId(),
				// Param.getParam("mail.template.findPwd",true).get("findPwd"));
			}
		} else if (emailPage != "") {// 通过email找回密码
			condition.put("email", emailPage);
			condition.put("code", "email");
			List<CUserProp> userProps = this.customService.getUserPropList(condition, null);
			if (userProps == null || userProps.size() == 0) {
				result = Lang.getLanguage("Controller.OrgUser.findPwdSubmit.noEmail.error", request.getSession().getAttribute("lang").toString());// "不存在该邮箱!";
			} else {
				// 发邮件
				CUserProp userProp = userProps.get(0);
				condition.put("userId", userProp.getUser().getId());
				List<CAccount> accounts = this.customService.getAccountList(condition, null);
				// boolean sendSuccess = this.sendEmail(userProp.getVal(),
				// userProp.getUser().getName(), accounts.get(0).getId(),
				// Param.getParam("mail.template.findPwd",true).get("findPwd"));
			}
		}

		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
		}
	}

	@RequestMapping(value = "/form/resetPwd")
	public ModelAndView resetPwd(HttpServletRequest request, HttpServletResponse response, OrgUserForm form) throws Exception {
		String forwardString = "user/resetPwd";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String accountId = request.getParameter("accountId");
			CAccount account = this.customService.getAccountById(accountId);
			if (account != null) {
				account.setPwd("");
				form.setAccount(account);
			} else {
				request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.resetPwd.prompt.error", request.getSession().getAttribute("lang").toString()));// "重置失败提示");
				request.setAttribute("message", Lang.getLanguage("Controller.OrgUser.resetPwd.noName.error", request.getSession().getAttribute("lang").toString()));// "不存在该账户，重置失败！");
			}
			model.put("form", form);
		} catch (Exception e) {
			request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.resetPwd.prompt.error", request.getSession().getAttribute("lang").toString()));// "重置失败提示");
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/form/resetPwdSubmit")
	public ModelAndView resetPwdSubmit(HttpServletRequest request, HttpServletResponse response, OrgUserForm form) throws Exception {
		String forwardString = "frame/result";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CAccount account = this.customService.getAccountById(form.getAccount().getId());
			if (account != null) {
				String pwd = form.getAccount().getPwd();
				Md5PasswordEncoder md5 = new Md5PasswordEncoder();
				pwd = md5.encodePassword(pwd, account.getId());
				account.setPwd(pwd);
				this.customService.updateAccount(account, account.getId(), null);
				request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.resetPwdSubmit.prompt.success", request.getSession().getAttribute("lang").toString()));// "重置成功提示");
				request.setAttribute("message", Lang.getLanguage("Controller.OrgUser.resetPwdSubmit.message.success", request.getSession().getAttribute("lang").toString()));// "密码重置成功！");
			} else {
				request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.resetPwdSubmit.prompt.error", request.getSession().getAttribute("lang").toString()));// "重置失败提示");
				request.setAttribute("message", Lang.getLanguage("Controller.OrgUser.resetPwdSubmit.message.noAcc.error", request.getSession().getAttribute("lang").toString()));// "不存在该账户！");
			}
		} catch (Exception e) {
			request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.resetPwdSubmit.prompt.error", request.getSession().getAttribute("lang").toString()));// "重置失败提示");
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 维护用户个人资料
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/orgUserDetail")
	public ModelAndView orgUserDetail(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrgUserForm form) throws Exception {
		String forwardString = "user/orgUserDetail";
		Map<String, Object> model = new HashMap<String, Object>();
		List<CUserProp> list = null;
		try {
			CUser user1 = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			if (null == user1) {
				request.setAttribute("prompt", Lang.getLanguage("Controller.BInstitutionUpload.edit.tip", request.getSession().getAttribute("lang").toString()));// "提示");
				request.setAttribute("message", Lang.getLanguage("Controller.Cart.checkOut.message.noLogin", request.getSession().getAttribute("lang").toString()));// "您无权进行该操作");
				forwardString = "frame/result";
				return new ModelAndView(forwardString, model);
			}
			form.setUrl(request.getRequestURL().toString());
			Map<String, Object> condition = form.getCondition();
			if (session.getAttribute("mainUser") != null) {
				CUser user = (CUser) session.getAttribute("mainUser");
				form.setUserName(user.getName());
				form.setUserTitle(user.getTitle());
				form.setUserTelephone(user.getTelephone());
				form.setUserDepartment(user.getDepartment());
				form.setUserType(user.getUserType().getId());
				form.setCountryMap(Param.getParam("country.value", true, request.getSession().getAttribute("lang").toString()));
				// 查询“机构”类型属性数据
				condition.put("userId", user.getId());
				list = this.customService.getUserPropList(condition, " order by a.must desc,a.order");
				form.setOrgTypeMap(Param.getParam("organization.type.value", true, request.getSession().getAttribute("lang").toString()));
				form.setInstitutionList(this.customService.getInstitutionList(null, null));
			}
			/**
			 * 将数据返回页面
			 */
			if (form.getPropsValue() != null) {// 修改个人资料验证不通过将form表单值返回页面
				if (list != null && !list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						form.getValues().put(list.get(i).getCode(), form.getPropsValue()[i]);
					}
				}
			} else { // 修改个人资料将数据库数据返回页面
				if (list != null && !list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						form.getValues().put(list.get(i).getCode(), list.get(i).getVal());
					}
				}
			}

			model.put("list", list);
			model.put("form", form);
		} catch (Exception e) {
			request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.orgUserDetail.prompt.error", request.getSession().getAttribute("lang").toString()));// "机构资料维护失败提示");
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 个人资料维护请求提交
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/detailSubmit")
	public ModelAndView detailSubmit(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrgUserForm form) throws Exception {
		String forwardString = "user/orgUserDetail";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			boolean msg = (Boolean) (request.getAttribute("msg") == null ? true : request.getAttribute("msg"));
			if (msg) {
				form.setUrl(request.getRequestURL().toString());
				// 验证邮箱的唯一性
				String emailValue = "";
				for (int i = 0; i < form.getTypePropIds().length; i++) {
					// 根据页面传的类型属性id获取类型属性详细信息，并存储到用户属性中。
					CUserProp userProp = this.customService.getUserPropById(form.getTypePropIds()[i].toString());
					if (userProp != null && userProp.getCode().equals("email")) {
						emailValue = form.getPropsValue()[i].toString();
						break;
					}
				}

				if (session.getAttribute("mainUser") != null) {
					// 修改用户
					CUser user = (CUser) session.getAttribute("mainUser");
					// 验证邮箱
					//					if (this.customService.checkEmailExist(user.getId(), emailValue)) {
					//						form.setMsg(Lang.getLanguage("Controller.OrgUser.detailSubmit.msg.alreadyEmail.error", request.getSession().getAttribute("lang").toString()));// "已经存在该邮箱，请重新输入！");
					//						return new ModelAndView("redirect:/pages/orgUser/form/orgUserDetail");
					//					}
					user.setUpdatedon(new Date());
					user.setName(form.getUserName());
					user.setDepartment(form.getUserDepartment());
					user.setTelephone(form.getUserTelephone());
					user.setTitle(form.getUserTitle());
					this.customService.updateUser(user, user.getId(), null);
					// 修改用户属性
					this.updateUserProp(form.getTypePropIds(), form.getPropsValue());
					// request.setAttribute("prompt",
					// Lang.getLanguage("Controller.OrgUser.detailSubmit.prompt.success",
					// request.getSession().getAttribute("lang").toString()));//"维护机构资料成功提示");
					// request.setAttribute("message",
					// Lang.getLanguage("Controller.OrgUser.detailSubmit.message.success",
					// request.getSession().getAttribute("lang").toString()));//"维护机构资料成功!");
					form.setIsSuccess("true");
					form.setMsg(Lang.getLanguage("Controller.OrgUser.detailSubmit.message.success", request.getSession().getAttribute("lang").toString()));
				}
			} else {
				// request.setAttribute("prompt",
				// Lang.getLanguage("Controller.User.registerSubmit.prompt.error",
				// request.getSession().getAttribute("lang").toString()));//"注册失败提示");
				// request.setAttribute("message",Lang.getLanguage("Conteoller.Global.prompt.info",
				// request.getSession().getAttribute("lang").toString()));//"注册失败");
				form.setIsSuccess("false");
				form.setMsg(Lang.getLanguage("Conteoller.Global.prompt.info", request.getSession().getAttribute("lang").toString()));
			}
		} catch (Exception e) {
			if (e instanceof HibernateJdbcException) {

				SQLException ex = ((HibernateJdbcException) e).getSQLException();
				if (ex != null) {
					if (ex.getMessage().indexOf("value too large") > 0) {
						form.setMsg(Lang.getLanguage("Global.Prompt.String.Too.Long", request.getSession().getAttribute("lang").toString()));
						// return orgUserDetail(request,response,session,form);
					}
				}
			} else {
				form.setIsSuccess("false");
				form.setMsg((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			}
			// request.setAttribute("prompt",
			// Lang.getLanguage("Controller.OrgUser.detailSubmit.prompt.error",
			// request.getSession().getAttribute("lang").toString()));//"维护机构资料失败提示");
			// request.setAttribute("message",(e instanceof
			// CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			form.setPropsValue(null);
			return new ModelAndView("redirect:/pages/orgUser/form/orgUserDetail");
		}
		form.setPropsValue(null);
		return new ModelAndView("redirect:/pages/orgUser/form/orgUserDetail");
	}

	/**
	 * 修改用户属性信息
	 * 
	 * @param propIds
	 * @param propValues
	 * @return
	 * @throws Exception
	 */
	private void updateUserProp(Object[] propIds, Object[] propValues) throws Exception {
		for (int i = 0; i < propIds.length; i++) {
			CUserProp userProp = new CUserProp();
			// 根据页面传的类型属性id获取类型属性详细信息，并存储到用户属性中。
			userProp.setVal(propValues[i].toString());
			userProp.setUpdatedon(new Date());
			this.customService.updateCUserProp(userProp, propIds[i].toString(), null);
		}
	}

	/**
	 * 专家信息管理
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/orgManage")
	public ModelAndView orgManage(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrgUserForm form) throws Exception {
		String forwardString = "user/orgManage";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			form.setUrl(request.getRequestURL().toString());
			if (session.getAttribute("mainUser") != null) {
				CUser user = (CUser) session.getAttribute("mainUser");
				CUserType userType = this.customService.getUserTypeByCode("2");// 获取对应的机构类型
				Map<String, Object> condition = form.getCondition();
				// 查询“机构”类型属性数据
				condition.put("userTypeId", userType.getId());
				// condition.put("institutionId",user.getInstitution().getId());
				form.setCount(this.customService.getAccountCount(condition, null));
				List<CAccount> list = this.customService.getAccountPagingList(condition, " order by a.createdon desc ", form.getPageCount(), form.getCurpage());
				model.put("list", list);
			}
			model.put("form", form);
		} catch (Exception e) {
			request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.orgManage.prompt.error", request.getSession().getAttribute("lang").toString()));// "机构管理失败提示");
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 管理员重置账户密码
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/resetPwdByAdmin")
	public void resetPwdByAdmin(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrgUserForm form) throws Exception {
		String result = "false:" + Lang.getLanguage("Global.Lable.Prompt.ReLogin", request.getSession().getAttribute("lang").toString());
		try {
			form.setUrl(request.getRequestURL().toString());
			if (session.getAttribute("mainUser") != null) {
				CUser user = (CUser) session.getAttribute("mainUser");

				String accountId = request.getParameter("id");
				CAccount account = this.customService.getAccountById(accountId);

				Map<String, Object> condition = form.getCondition();
				condition.put("userId", account.getUser().getId());
				List<CUserProp> userPropList = this.customService.getUserPropList(condition, " order by a.order");

				String emailValue = "";
				for (CUserProp up : userPropList) {
					if (up != null && up.getCode().equals("email")) {
						emailValue = up.getVal();
						break;
					}
				}
				// 给注册用户发送确认邮件
				// 用当前日期作为重置密码
				String initialPwd = RandomCodeUtil.generateRandomCode(10);
				Md5PasswordEncoder md5 = new Md5PasswordEncoder();
				String pwd = md5.encodePassword(initialPwd, account.getUid());
				account.setPwd(pwd);
				account.setUpdatedon(new Date());
				this.customService.updateAccount(account, accountId, null);

				boolean isSuccess = this.sendEmail(emailValue, user.getName(), account.getUid(), initialPwd, Param.getParam("mail.template.orgRegister", true).get("orgRegister"), request.getSession().getAttribute("lang").toString());
				if (isSuccess) {
					result = "true:" + Lang.getLanguage("Controller.OrgUser.resetPwd.msg.email.success", request.getSession().getAttribute("lang").toString());// "重置成功，请查收账号密码重置邮件！");
				} else {
					result = "false:" + (Lang.getLanguage("Controller.OrgUser.resetPwd.msg.noEmail.success", request.getSession().getAttribute("lang").toString()));// "重置成功，但邮件发送失败，请联系管理员！");
				}
				condition.remove("userId");
			}
		} catch (Exception e) {
			result = "false:" + ((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
		// return orgManage(request, response,session, form);
	}

	/**
	 * 改变账户状态（激活/停用）
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/changeStatus")
	public void changeStatus(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrgUserForm form) throws Exception {
		String result = "";
		try {
			form.setUrl(request.getRequestURL().toString());
			if (session.getAttribute("mainUser") != null) {
				CUser loginUser = (CUser) session.getAttribute("mainUser");

				int status = Integer.valueOf(request.getParameter("status"));
				String accountId = request.getParameter("id");
				CAccount account = this.customService.getAccountById(accountId);
				account.setStatus(status);
				account.setUpdatedon(new Date());
				account.setUpdatedby(account.getUser().getId());
				this.customService.updateAccount(account, accountId, null);

				CUser user = account.getUser();
				user.setStatus(status);
				user.setUpdatedon(new Date());
				user.setUpdatedby(loginUser.getId());
				this.customService.updateUser(user, user.getId(), null);

				if (status == 1) {
					result = "true:" + Lang.getLanguage("Controller.OrgUser.changeStatus.activation.success", request.getSession().getAttribute("lang").toString());// 账户激活成功!";
				} else {
					result = "true:" + Lang.getLanguage("Controller.OrgUser.changeStatus.stop.success", request.getSession().getAttribute("lang").toString());// 账户停用成功!";
				}

				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print(result);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			result = "false:" + Lang.getLanguage("Controller.OrgUser.changeStatus.oper.error", request.getSession().getAttribute("lang").toString());// 操作失败!";
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
		}
	}

	/**
	 * 改变账户交税状态（交税/免税）
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/changeFaxStatus")
	public void changeFaxStatus(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrgUserForm form) throws Exception {
		String result = "";
		try {
			form.setUrl(request.getRequestURL().toString());
			if (session.getAttribute("mainUser") != null) {
				String accountId = request.getParameter("id");
				CAccount account = this.customService.getAccountById(accountId);
				Map<String, Object> condition = form.getCondition();
				condition.put("userId", account.getUser().getId());
				condition.put("code", "freetax");
				List<CUserProp> list = this.customService.getUserPropList(condition, null);
				if (list != null && list.size() != 0) {
					CUserProp userProp = list.get(0);
					userProp.setVal(request.getParameter("faxStatus"));
					this.customService.updateCUserProp(userProp, userProp.getId(), null);
				}

				if (Integer.valueOf(request.getParameter("faxStatus")) == 1) {
					result = "true:" + Lang.getLanguage("Controller.OrgUser.changeFaxStatus.Payment.success", request.getSession().getAttribute("lang").toString());// 交税设置成功!";
				} else {
					result = "true:" + Lang.getLanguage("Controller.OrgUser.changeFaxStatus.free.success", request.getSession().getAttribute("lang").toString());// 免税设置成功!";
				}

				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print(result);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			result = "false:" + Lang.getLanguage("Controller.OrgUser.changeFaxStatus.oper.error", request.getSession().getAttribute("lang").toString());// 操作失败!";
			form.setMsg((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
		}
	}

	/**
	 * 维护用户机构资料
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/personDetail")
	public ModelAndView personDetail(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrgUserForm form) throws Exception {
		String forwardString = "user/personDetail";
		Map<String, Object> model = new HashMap<String, Object>();
		List<CUserProp> list = null;
		try {
			form.setUrl(request.getRequestURL().toString());
			Map<String, Object> condition = form.getCondition();
			if (session.getAttribute("mainUser") != null) {
				CUser user = (CUser) session.getAttribute("mainUser");
				form.setUserName(user.getName());
				form.setCountryMap(Param.getParam("country.value", true, request.getSession().getAttribute("lang").toString()));
				form.setProvinceMap(Param.getParam("province.value", true, request.getSession().getAttribute("lang").toString()));
				// 查询“机构”类型属性数据
				condition.put("userId", user.getId());
				list = this.customService.getUserPropList(condition, " order by a.must desc,a.order");
				form.setOrgTypeMap(Param.getParam("organization.type.value", true, request.getSession().getAttribute("lang").toString()));
				form.setInstitutionList(this.customService.getInstitutionList(null, null));
			} else {
				request.setAttribute("prompt", Lang.getLanguage("Controller.BInstitutionUpload.edit.tip", request.getSession().getAttribute("lang").toString()));// "提示");
				request.setAttribute("message", Lang.getLanguage("Controller.Cart.checkOut.message.noLogin", request.getSession().getAttribute("lang").toString()));// "您无权进行该操作");
				forwardString = "frame/result";
			}
			/**
			 * 将数据返回页面
			 */
			if (form.getPropsValue() != null) {// 修改个人资料验证不通过将form表单值返回页面
				if (list != null && !list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						form.getValues().put(list.get(i).getCode(), form.getPropsValue()[i]);
					}
				}
			} else { // 修改个人资料将数据库数据返回页面
				if (list != null && !list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						form.getValues().put(list.get(i).getCode(), list.get(i).getVal());
					}
				}
			}

			model.put("list", list);
			model.put("form", form);
		} catch (Exception e) {
			System.out.println((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			request.setAttribute("prompt", Lang.getLanguage("Controller.OrgUser.personDetail.prompt.error", request.getSession().getAttribute("lang").toString()));// "机构资料维护失败提示");
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 个人资料维护请求提交
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/personSubmit")
	public ModelAndView personSubmit(HttpServletRequest request, HttpServletResponse response, HttpSession session, OrgUserForm form) throws Exception {
		try {
			boolean msg = (Boolean) (request.getAttribute("msg") == null ? true : request.getAttribute("msg"));
			if (msg) {
				form.setUrl(request.getRequestURL().toString());
				// 验证邮箱的唯一性
				String emailValue = "";
				for (int i = 0; i < form.getTypePropIds().length; i++) {
					// 根据页面传的类型属性id获取类型属性详细信息，并存储到用户属性中。
					CUserProp userProp = this.customService.getUserPropById(form.getTypePropIds()[i].toString());
					if (userProp != null && userProp.getCode().equals("email")) {
						emailValue = form.getPropsValue()[i].toString();
						break;
					}
				}

				if (session.getAttribute("mainUser") != null) {
					// 修改用户
					CUser user = (CUser) session.getAttribute("mainUser");
					// 验证邮箱
					if (this.customService.checkEmailExist(user.getId(), emailValue)) {
						form.setMsg(Lang.getLanguage("Controller.OrgUser.detailSubmit.msg.alreadyEmail.error", request.getSession().getAttribute("lang").toString()));// "已经存在该邮箱，请重新输入！");
						return new ModelAndView("redirect:/pages/orgUser/form/personDetail");
					}
					user.setUpdatedon(new Date());
					user.setName(form.getUserName());
					this.customService.updateUser(user, user.getId(), null);
					// 修改用户属性
					this.updateUserProp(form.getTypePropIds(), form.getPropsValue());
					form.setIsSuccess("true");
					form.setMsg(Lang.getLanguage("Controller.OrgUser.detailSubmit.message.success", request.getSession().getAttribute("lang").toString()));
				}
			} else {
				form.setIsSuccess("false");
				form.setMsg(Lang.getLanguage("Conteoller.Global.prompt.info", request.getSession().getAttribute("lang").toString()));
			}
		} catch (Exception e) {
			if (e instanceof HibernateJdbcException) {

				SQLException ex = ((HibernateJdbcException) e).getSQLException();
				if (ex != null) {
					if (ex.getMessage().indexOf("value too large") > 0) {
						form.setMsg(Lang.getLanguage("Global.Prompt.String.Too.Long", request.getSession().getAttribute("lang").toString()));
						// return orgUserDetail(request,response,session,form);
					}
				}
			} else {
				form.setIsSuccess("false");
				form.setMsg((e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			}
			// request.setAttribute("prompt",
			// Lang.getLanguage("Controller.OrgUser.detailSubmit.prompt.error",
			// request.getSession().getAttribute("lang").toString()));//"维护机构资料失败提示");
			// request.setAttribute("message",(e instanceof
			// CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			form.setPropsValue(null);
			return new ModelAndView("redirect:/pages/orgUser/form/personDetail");
		}
		form.setPropsValue(null);
		return new ModelAndView("redirect:/pages/orgUser/form/personDetail");
	}

}
