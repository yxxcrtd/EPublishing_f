package cn.digitalpublishing.springmvc.controller;

import java.io.File;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.pdfbox.PDFReader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.springframework.orm.hibernate3.HibernateJdbcException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.ccsit.restful.tool.Converter;
import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.Param;
import cn.com.daxtech.framework.util.ObjectUtil;
import cn.digitalpublishing.ep.po.BIpRange;
import cn.digitalpublishing.ep.po.BPDACounter;
import cn.digitalpublishing.ep.po.BPDAInfo;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.CUserProp;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.LLicenseIp;
import cn.digitalpublishing.ep.po.LUserAlertsLog;
import cn.digitalpublishing.ep.po.OOrder;
import cn.digitalpublishing.ep.po.OOrderDetail;
import cn.digitalpublishing.ep.po.PCcRelation;
import cn.digitalpublishing.ep.po.PCollection;
import cn.digitalpublishing.ep.po.PPrice;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.ep.po.RRecommend;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;
import cn.digitalpublishing.springmvc.form.order.OOrderDetailForm;
import cn.digitalpublishing.springmvc.form.order.OOrderForm;
import cn.digitalpublishing.util.web.DateUtil;
import cn.digitalpublishing.util.web.IpUtil;
import cn.digitalpublishing.util.web.MathHelper;
import cn.digitalpublishing.util.web.TokenProcessor;
import cn.digitalpublishing.util.xml.EPubHelper;

import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import com.zhima.server.model.ResultObject;


@Controller
@RequestMapping("/pages/order")
public class OOrderController extends BaseController {

	/**
	 * 订单列表管理
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response, HttpSession session, OOrderForm form) throws Exception {
		String forwardString = "order/list";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = session.getAttribute("mainUser") == null ? null : (CUser) session.getAttribute("mainUser");
			if (user == null) {
				request.setAttribute("prompt", Lang.getLanguage("Controller.BInstitutionUpload.edit.tip", request.getSession().getAttribute("lang").toString()));// "提示");
				request.setAttribute("message", Lang.getLanguage("Controller.Cart.checkOut.message.noLogin", request.getSession().getAttribute("lang").toString()));// "您无权进行该操作");
				forwardString = "frame/result";
				return new ModelAndView(forwardString, model);
			}
			Integer level = user.getLevel();
			String dr = request.getParameter("dr");
			String startTime = "";
			String endTime = "";
			if (dr == null || "0".equals(dr)) {

			} else if ("1".equals(dr)) {// 一周内
				endTime = DateUtil.getNowDate("yyyy-MM-dd");
				Date sTime = DateUtil.getDealedDate(new Date(), 0, 0, -7, 0, 0, 0);
				startTime = DateUtil.getDateStr("yyyy-MM-dd", sTime);
			} else if ("2".equals(dr)) {// 一月内
				endTime = DateUtil.getNowDate("yyyy-MM-dd");
				Date sTime = DateUtil.getDealedDate(new Date(), 0, -1, 0, 0, 0, 0);
				startTime = DateUtil.getDateStr("yyyy-MM-dd", sTime);
			} else if ("3".equals(dr)) {// 三月内
				endTime = DateUtil.getNowDate("yyyy-MM-dd");
				Date sTime = DateUtil.getDealedDate(new Date(), 0, -3, 0, 0, 0, 0);
				startTime = DateUtil.getDateStr("yyyy-MM-dd", sTime);
			}
			// CUser user = new CUser();
			// user.setId("086389937b1f1030a8ee2610e0325a2b");
			if (user != null) {
				form.setUrl(request.getRequestURL().toString());
				Map<String, Object> condition = form.getCondition();
				condition.put("allStatus", form.getAllStatus() == null ? "1,2" : form.getAllStatus());
				condition.put("userId", user.getId());
				condition.put("startTime", startTime);
				condition.put("endTime", endTime);
				form.setCount(this.oOrderService.getOrderCount(condition));
				List<OOrder> list = this.oOrderService.getOrderPagingList(condition, " order by a.createdon desc ", form.getPageCount(), form.getCurpage());
				form.setAllStatus(condition.get("allStatus").toString());
				model.put("list", list);
				model.put("dr", dr);
				model.put("level", level);
			}
		} catch (Exception e) {
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 查看机构订单
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/inslist")
	public ModelAndView inslist(HttpServletRequest request, HttpServletResponse response, HttpSession session, OOrderForm form) throws Exception {
		String forwardString = "order/orderList";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CUser user = session.getAttribute("mainUser") == null ? null : (CUser) session.getAttribute("mainUser");
			if (user == null) {
				request.setAttribute("prompt", Lang.getLanguage("Controller.BInstitutionUpload.edit.tip", request.getSession().getAttribute("lang").toString()));// "提示");
				request.setAttribute("message", Lang.getLanguage("Controller.Cart.checkOut.message.noLogin", request.getSession().getAttribute("lang").toString()));// "您无权进行该操作");
				forwardString = "frame/result";
				return new ModelAndView(forwardString, model);
			}
			Integer level = user.getLevel();
			String dr = request.getParameter("dr");
			String startTime = "";
			String endTime = "";
			if (dr == null || "0".equals(dr)) {

			} else if ("1".equals(dr)) {// 一周内
				endTime = DateUtil.getNowDate("yyyy-MM-dd");
				Date sTime = DateUtil.getDealedDate(new Date(), 0, 0, -7, 0, 0, 0);
				startTime = DateUtil.getDateStr("yyyy-MM-dd", sTime);
			} else if ("2".equals(dr)) {// 一月内
				endTime = DateUtil.getNowDate("yyyy-MM-dd");
				Date sTime = DateUtil.getDealedDate(new Date(), 0, -1, 0, 0, 0, 0);
				startTime = DateUtil.getDateStr("yyyy-MM-dd", sTime);
			} else if ("3".equals(dr)) {// 三月内
				endTime = DateUtil.getNowDate("yyyy-MM-dd");
				Date sTime = DateUtil.getDealedDate(new Date(), 0, -3, 0, 0, 0, 0);
				startTime = DateUtil.getDateStr("yyyy-MM-dd", sTime);
			}
			// CUser user = new CUser();
			// user.setId("086389937b1f1030a8ee2610e0325a2b");
			if (user != null) {
				form.setUrl(request.getRequestURL().toString());
				Map<String, Object> condition = form.getCondition();
				condition.put("allStatus", form.getAllStatus() == null ? "1,2" : form.getAllStatus());
				condition.put("userId", user.getId());
				condition.put("startTime", startTime);
				condition.put("endTime", endTime);
				form.setCount(this.oOrderService.getOrderCount(condition));
				List<OOrder> list = this.oOrderService.getOrderPagingList(condition, " order by a.createdon desc ", form.getPageCount(), form.getCurpage());
				form.setAllStatus(condition.get("allStatus").toString());
				model.put("list", list);
				model.put("dr", dr);
				model.put("level", level);
			}
		} catch (Exception e) {
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 订单详细信息
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/detail")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, HttpSession session, OOrderForm form) throws Exception {
		String forwardString = "order/detailList";
		form.setPaytype(request.getParameter("paytype"));// 用于页面的带值用的
		Map<String, Object> model = new HashMap<String, Object>();
		// 总价格
		double tPrice = 0d;
		List<CUserProp> propList = null;
		try {
			if (form.getId() != null) {
				form.setUrl(request.getRequestURL().toString());
				// 根绝ID查询订单信息
				OOrder order = this.oOrderService.getOrder(form.getId());
				Map<String, Object> con = new HashMap<String, Object>();
				con.put("orderId", order.getId());
				List<OOrderDetail> detailList = this.oOrderService.getOrderDetailList(con, "");
				if (detailList != null && detailList.size() > 0) {
					model.put("detailSendStatus", detailList.get(0).getStatus());
				}
				model.put("order", order);
				// 根据条件查询订单明细
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("orderId", order.getId());
				condition.put("parentId", form.getParentId() == null ? "0" : form.getParentId());
				List<OOrderDetail> list = this.oOrderService.getDetailList(condition, "");
				for (OOrderDetail detail : list) {
					tPrice = MathHelper.add(tPrice, detail.getSalePriceExtTax());// 计算总价
				}
				// 加入获取用户属性操作 刘冶
				form.setCountryMap(Param.getParam("country.value", true, request.getSession().getAttribute("lang").toString()));
				form.setProvinceMap(Param.getParam("province.value", true, request.getSession().getAttribute("lang").toString()));
				CUser user = (CUser) session.getAttribute("mainUser");
				condition.put("userId", user.getId());
				propList = this.customService.getUserPropList(condition, " order by a.must desc,a.order");
				if (propList != null && !propList.isEmpty()) {
					for (int i = 0; i < propList.size(); i++) {
						form.getValues().put(propList.get(i).getCode(), propList.get(i).getVal());
					}
				}
				condition.clear();// 获取用户属性结束 刘冶
				model.put("detailList", list);
				model.put("tPrice", tPrice);
				model.put("form", form);
				model.put("propList", propList);
			}
		} catch (Exception e) {
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	/**
	 * 订单详细信息
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/insdetail")
	public ModelAndView insdetail(HttpServletRequest request, HttpServletResponse response, HttpSession session, OOrderForm form) throws Exception {
		String forwardString = "order/insdetailList";
		Map<String, Object> model = new HashMap<String, Object>();
		// 总价格
		double tPrice = 0d;
		List<CUserProp> propList = null;
		try {
			if (form.getId() != null) {
				form.setUrl(request.getRequestURL().toString());
				// 根绝ID查询订单信息
				OOrder order = this.oOrderService.getOrder(form.getId());
				Map<String, Object> con = new HashMap<String, Object>();
				con.put("orderId", order.getId());
				List<OOrderDetail> detailList = this.oOrderService.getOrderDetailList(con, "");
				if (detailList != null && detailList.size() > 0) {
					model.put("detailSendStatus", detailList.get(0).getStatus());
				}
				model.put("order", order);
				// 根据条件查询订单明细
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("orderId", order.getId());
				condition.put("parentId", form.getParentId() == null ? "0" : form.getParentId());
				List<OOrderDetail> list = this.oOrderService.getDetailList(condition, "");
				for (OOrderDetail detail : list) {
					tPrice = MathHelper.add(tPrice, detail.getSalePriceExtTax());// 计算总价
				}
				// 加入获取用户属性操作 刘冶
				form.setCountryMap(Param.getParam("country.value", true, request.getSession().getAttribute("lang").toString()));
				form.setProvinceMap(Param.getParam("province.value", true, request.getSession().getAttribute("lang").toString()));
				CUser user = (CUser) session.getAttribute("mainUser");
				condition.put("userId", user.getId());
				propList = this.customService.getUserPropList(condition, " order by a.must desc,a.order");
				if (propList != null && !propList.isEmpty()) {
					for (int i = 0; i < propList.size(); i++) {
						form.getValues().put(propList.get(i).getCode(), propList.get(i).getVal());
					}
				}
				condition.clear();// 获取用户属性结束 刘冶
				model.put("detailList", list);
				model.put("tPrice", tPrice);
				model.put("form", form);
				model.put("propList", propList);
			}
		} catch (Exception e) {
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/form/submit")
	public ModelAndView orderSubmit(HttpServletRequest request, HttpServletResponse response, HttpSession session, OOrderDetailForm form) throws Exception {

		String forwardString = "order/detailList";
		String alipay = request.getParameterMap().containsKey("alipay") ? request.getParameter("alipay") : null;
		Map<String, Object> model = new HashMap<String, Object>();
		OOrderForm of = new OOrderForm();
		of.setAlipay(alipay);
		Date date = new Date();
		try {
			boolean msg = (Boolean) (request.getAttribute("msg") == null ? true : request.getAttribute("msg"));
			if (msg) {
				TokenProcessor tokemProcessor = TokenProcessor.getInstance();
				if (tokemProcessor.isTokenValid(request)) {// 0Token 有效
					if (session.getAttribute("mainUser") != null) {
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
						// 修改用户
						CUser user = (CUser) session.getAttribute("mainUser");
						// 验证邮箱
						/*
						 * 页面上不显示用户邮箱 所以不需要进行验证
						 * if(this.customService.checkEmailExist(user.getId(),
						 * emailValue)){ request.setAttribute("prompt", Lang.getLanguage(
						 * "Controller.Oorder.orderSubmit.prompt.alreadyEmail.error" ,
						 * request.getSession().getAttribute("lang").toString())
						 * );//"邮箱重复提示"); request.setAttribute("message",Lang.getLanguage(
						 * "Controller.Oorder.orderSubmit.message.alreadyEmail.error" ,
						 * request.getSession().getAttribute("lang").toString())
						 * );//"该邮箱已存在，请重新填写"); forwardString="frame/result"; return new
						 * ModelAndView(forwardString,model); }
						 */
						user.setUpdatedon(date);
						user.setSendStatus(1);
						this.customService.updateUser(user, user.getId(), null);
						// 修改用户属性
						this.updateUserProp(form.getTypePropIds(), form.getPropsValue());
						Map<String, Object> condition = new HashMap<String, Object>();
						condition.put("status", 4);
						condition.put("userid", user.getId());
						condition.put("parentId", "0");
						condition.put("orderNull", "1");
						List<OOrderDetail> list = this.oOrderService.getDetailList(condition, " order by a.createdon desc ");
						if (list != null && list.size() > 0) {
							double balance = this.oOrderService.getUserBalance(user.getId());
							if ("1".equals(form.getPaytype())) {// 预存账户支付
								double totalprice = 0d;
								for (OOrderDetail d : list) {
									totalprice = MathHelper.add(totalprice, d.getSalePriceExtTax());// 计算总价
								}
								if (balance < totalprice) {// 预存账户余额不足
									request.setAttribute("prompt", Lang.getLanguage("Controller.Oorder.orderSubmit.prompt.order.error", request.getSession().getAttribute("lang").toString()));// "提交订单提示");
									request.setAttribute(
											"message",
											Lang.getLanguage("Controller.Oorder.orderSubmit.message1.order.error", request.getSession().getAttribute("lang").toString()) + balance
													+ Lang.getLanguage("Controller.Oorder.orderSubmit.message2.order.error", request.getSession().getAttribute("lang").toString()));// //"您的预存账户余额为￥"++"&nbsp;&nbsp;不足以完成本次支付，请充值后重试。");
									forwardString = "frame/result";
									model.put("form", form);
									return new ModelAndView(forwardString, model);
								}
							}
							// 生成订单并将订单号回写到订单明细中，然后生成消费记录
							String orderId = this.oOrderService.insertOrderWithDetails(request, list, user, IpUtil.getIp(request), Integer.valueOf(form.getPaytype()));
							request.getSession().removeAttribute("totalincart");
							of.setId(orderId);
							if ("1".equals(form.getPaytype())) {// 预存账户支付且余额足够，直接生成license并加入索引
								for (OOrderDetail d : list) {
									// 添加冻结记录，金额为结算价取负
									this.oOrderService.addLockedTransation(d, MathHelper.sub(0d, d.getSettledPrice()), date);
									// if(d.getPrice().getPublications().getLocal()==2){
									// if(d.getCollection()!=null){
									// this.insertLicenseFromCollection(request,d);
									// }else{
									// this.insertLicenseFromOODetail(request,d);
									// }
									// }
									if (d.getCollection() == null && d.getPrice() != null && d.getPrice().getPublications() != null && d.getPrice().getPublications().getId() != null) {
										this.pPublicationsService.addBuyTimes(d.getPrice().getPublications().getId());
									}
								}
							}
							tokemProcessor.reset(request);// 令Token失效
						} else {
							request.setAttribute("prompt", Lang.getLanguage("Controller.Oorder.orderSubmit.prompt.order.error", request.getSession().getAttribute("lang").toString()));// "提交订单提示");
							request.setAttribute("message", Lang.getLanguage("Controller.Oorder.orderSubmit.message.noPub.error", request.getSession().getAttribute("lang").toString()));// "您的购物车中没有商品，请添加商品后重试");
							forwardString = "frame/result";
							model.put("form", form);
							return new ModelAndView(forwardString, model);
						}
					}
				} else {
					request.setAttribute("prompt", Lang.getLanguage("Controller.Oorder.orderSubmit.prompt.alreadyOrder.error", request.getSession().getAttribute("lang").toString()));// "重复提交提示");
					request.setAttribute("message", Lang.getLanguage("Controller.Oorder.orderSubmit.message.alreadyOrder.error", request.getSession().getAttribute("lang").toString()));// "订单已经生成请等待受理，请勿重复提交");
					forwardString = "frame/result";
					model.put("form", form);
					return new ModelAndView(forwardString, model);
				}
			} else {
				request.setAttribute("prompt", Lang.getLanguage("Controller.Oorder.orderSubmit.prompt.order.error", request.getSession().getAttribute("lang").toString()));// "提交订单提示");
				request.setAttribute("message", Lang.getLanguage("Conteoller.Global.prompt.info", request.getSession().getAttribute("lang").toString()));// 订单保存失败！
				forwardString = "frame/result";
				model.put("form", form);
				return new ModelAndView(forwardString, model);
			}
		} catch (Exception e) {
			if (e instanceof HibernateJdbcException) {

				SQLException ex = ((HibernateJdbcException) e).getSQLException();
				if (ex != null) {
					if (ex.getMessage().indexOf("value too large") > 0) {
						request.setAttribute("message", Lang.getLanguage("Global.Prompt.String.Too.Long", request.getSession().getAttribute("lang").toString()));
					}
				} else {
					request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
				}
			} else {
				// request.setAttribute("message", (e instanceof CcsException) ?
				// Lang.getLanguage(((CcsException) e).getPrompt(),
				// request.getSession().getAttribute("lang").toString()) :
				// e.getMessage());
				request.setAttribute("message", Lang.getLanguage("Controller.Oorder.orderSubmit.prompt.order.Alipay3", request.getSession().getAttribute("lang").toString()));
			}
			request.setAttribute("prompt", Lang.getLanguage("Controller.Oorder.orderSubmit.prompt.order.error", request.getSession().getAttribute("lang").toString()));// "提交订单提示");
			// request.setAttribute("message",e.getMessage());
			forwardString = "frame/result";
			model.put("form", form);
			return new ModelAndView(forwardString, model);
		}
		return this.detail(request, response, session, of);

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
	 * 获取订单List 未发送的接口
	 * 
	 * @param request
	 * @param model
	 * @throws Exception
	 *             by ruixue cheng
	 */
	@RequestMapping(value = "/orderList")
	public void getOrderList(HttpServletRequest request, Model model) throws Exception {
		ResultObject<OOrder> result = null;
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("sendStatus", 1);
			List<OOrder> list = this.oOrderService.getOrderList(condition, " order by a.createdon ");

			if (list != null && !list.isEmpty()) {
				ObjectUtil<OOrder> util = new ObjectUtil<OOrder>();
				ObjectUtil<CUser> uutil = new ObjectUtil<CUser>();
				for (int i = 0; i < list.size(); i++) {
					util.setNull(list.get(i), new String[] { Set.class.getName(), List.class.getName() });
					if (list.get(i).getUser() != null) {
						uutil.setNull(list.get(i).getUser(), new String[] { Set.class.getName(), List.class.getName() });
					}//
				}
			}
			result = new ResultObject<OOrder>(1, list, Lang.getLanguage("Controller.Oorder.getOrderList.query.success", request.getSession().getAttribute("lang").toString()));// "获取订单列表成功！");
		} catch (Exception e) {
			result = new ResultObject<OOrder>(2, (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
		}
		model.addAttribute("target", result);
	}

	@RequestMapping(value = "/orderDetailList/{orderId}")
	public void getListOrderdetail(HttpServletRequest request,Model model,@PathVariable String orderId){
		ResultObject<OOrderDetail> result= null;
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("sendStatus", 1);
			condition.put("parentId", "0");
			condition.put("orderId", orderId);
			List<OOrderDetail> list= this.oOrderService.getOrderDetailsList(condition, "");
			if (list != null && !list.isEmpty()) {
				ObjectUtil<OOrderDetail> util = new ObjectUtil<OOrderDetail>();
				ObjectUtil<PPublications> putil = new ObjectUtil<PPublications>();
				ObjectUtil<CUser> uutil = new ObjectUtil<CUser>();
				ObjectUtil<OOrder> outil = new ObjectUtil<OOrder>();
				ObjectUtil<PPrice> prutil = new ObjectUtil<PPrice>();
				ObjectUtil<PCollection>pcutil = new ObjectUtil<PCollection>(); 
				
				
				
				for (int i = 0; i < list.size(); i++) {
					util.setNull(list.get(i), new String[] { Set.class.getName(), List.class.getName() });
					if (list.get(i).getPrice() != null) {
						prutil.setNull(list.get(i).getPrice(), new String[] { Set.class.getName(), List.class.getName() });
						if (list.get(i).getPrice().getPublications() != null) {
							putil.setNull(list.get(i).getPrice().getPublications(), new String[] { Set.class.getName(), List.class.getName() });
						}
					}
					if(list.get(i).getCollection()!=null){
						pcutil.setNull(list.get(i).getCollection(), new String[] { Set.class.getName(), List.class.getName()});
					}
					if (list.get(i).getUser() != null) {
						uutil.setNull(list.get(i).getUser(), new String[] { Set.class.getName(), List.class.getName() });
					}
					if (list.get(i).getOrder() != null) {
						outil.setNull(list.get(i).getOrder(), new String[] { Set.class.getName(), List.class.getName() });
					}
				}
			}
			
			result = new ResultObject<OOrderDetail>(1, list,Lang.getLanguage("Controller.Oorder.getOrderDetailList.query.success", request.getSession().getAttribute("lang").toString()));// "获取订单详情列表成功！
			
		} catch (Exception e) {
			// TODO: handle exception
			result = new ResultObject<OOrderDetail>(2, (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
		}
		model.addAttribute("target", result);
		
	}
	


	/**
	 * 处理订单细节
	 * 
	 * @param request
	 * @param model
	 *            by ruixue cheng
	 */
	@RequestMapping(value = "/processOrderDetail", method = RequestMethod.POST)
	public void processOrderDetail(HttpServletRequest request, Model model) {
		ResultObject<OOrderDetail> result = null;
		try {
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); // 1-insert
																			// 2-update
			Converter<OOrderDetail> converter = new Converter<OOrderDetail>();
			OOrderDetail obj = (OOrderDetail) converter.json2Object(objJson, OOrderDetail.class.getName());
			obj = this.oOrderService.processOrderDetail(operType, obj);
			ObjectUtil<OOrderDetail> util = new ObjectUtil<OOrderDetail>();
			obj = util.setNull(obj, new String[] { Set.class.getName(), List.class.getName() });
			result = new ResultObject<OOrderDetail>(1, Lang.getLanguage("Controller.Oorder.processOrderDetail.manage.success", request.getSession().getAttribute("lang").toString()));// "订单细节维护成功！");
		} catch (Exception e) {
			result = new ResultObject<OOrderDetail>(2, Lang.getLanguage("Controller.Oorder.processOrderDetail.manage.error", request.getSession().getAttribute("lang").toString()));// "订单细节维护失败！");
		}
		model.addAttribute("target", result);
	}

	/**
	 * 处理订单
	 * 
	 * @param request
	 * @param model
	 *            by ruixue cheng
	 */
	@RequestMapping(value = "/processOrder", method = RequestMethod.POST)
	public void processOrder(HttpServletRequest request, Model model) {
		ResultObject<OOrder> result = null;
		try {
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); // 1-insert
																			// 2-update
			Converter<OOrder> converter = new Converter<OOrder>();
			OOrder obj = (OOrder) converter.json2Object(objJson, OOrder.class.getName());
			OOrder epOrder = this.oOrderService.getOrder(obj.getId());
			if ("2".equals(operType)) {
				if (obj.getStatus() != null && obj.getStatus() > 0) {
					epOrder.setStatus(obj.getStatus());// 更新订单的处理状态
				}
				if (obj.getSendStatus() != null && obj.getSendStatus() > 0) {
					epOrder.setSendStatus(obj.getSendStatus());// 更新订单的发送状态
				}
				this.oOrderService.updateOrder(epOrder, obj.getId(), null);
			} else if ("1".equals(operType)) {
				// 写入
			} else {
				// 删除
			}
			ObjectUtil<OOrder> util = new ObjectUtil<OOrder>();
			obj = util.setNull(obj, new String[] { Set.class.getName() });
			result = new ResultObject<OOrder>(1, Lang.getLanguage("Controller.Oorder.processOrder.manage.success", request.getSession().getAttribute("lang").toString()));// "订单维护成功！");//"订单信息维护成功！");
		} catch (Exception e) {
			result = new ResultObject<OOrder>(2, Lang.getLanguage("Controller.Oorder.processOrder.manage.error", request.getSession().getAttribute("lang").toString()));// "订单维护失败！");//"订单信息维护失败！");
		}
		model.addAttribute("target", result);
	}

	private void insertLicenseFromOODetail(HttpServletRequest request, OOrderDetail detail) throws Exception {
		try {
			LLicense license = new LLicense();
			Date d = new Date();
			CUser user = this.customService.getUser(detail.getUser().getId());
			license.setUser(user);
			license.setAccessUIdType(user.getLevel() == 2 ? 2 : 1);
			license.setPublications(detail.getPrice().getPublications());
			license.setCode(detail.getPrice().getPublications().getCode());
			license.setCreatedby(detail.getUser().getName());
			license.setCreatedon(d);
			license.setStatus(1);
			license.setComplicating(detail.getPrice().getComplicating());// 并发数
			if (detail.getPrice().getPublications().getLocal() == 1) {// 不是本地资源
				license.setReadUrl(detail.getPrice().getPublications().getWebUrl());
			} else {// 是本地资源
				String accessUrl = Param.getParam("system.config").get("accessUrl");
				accessUrl = accessUrl.replace("-", ":");
				license.setReadUrl(accessUrl + "/pages/view/form/view?id=" + detail.getPrice().getPublications().getId());
			}
			if (detail.getPrice().getType() == 1) {// 用户选择的购买价格为永久授权
				license.setType(1);// 永久授权
				license.setStartTime(d);
			} else {
				// 限时授权部分暂缓
				license.setType(2);// 限时授权
				Date date = new Date();
				license.setStartTime(date);
				Date date2 = DateUtil.getUtilDate(DateUtil.getEndMonthDate(date, detail.getPrice().getEffective()), "yyyy-MM-dd");
				license.setEndTime(date2);
				// license.setType(obj.getPrice().getType());//商品属于一个产品包，类型为永久授权
				// license.setStartTime(new Date());
				// license.setEndTime(new Date());

				// 限时授权出版物，将进行续费提醒
				LUserAlertsLog log = new LUserAlertsLog();
				log.setUserName(user.getName());// 用户名
				log.setPublicationsName(detail.getPrice().getPublications().getTitle());// 出版物名称
				log.setIsbn(detail.getPrice().getPublications().getCode());
				log.setEmail(user.getEmail());// 用户邮箱
				log.setCreatedon(new Date());
				Date date3 = DateUtil.getUtilDate(DateUtil.getEndMonthDate(date, detail.getPrice().getEffective() - 1), "yyyy-MM-dd");
				log.setAlertDate(DateUtil.getDateStr("yyyy-MM-dd", date3));// 提醒日期
																			// 提前一个月
				log.setAlertStatus(1);// 提醒状态 1、未提醒 2、已提醒 3、提醒失败
				log.setAlertType(1);// 提醒类型 1、续费提醒 2、最新章节、文章
				log.setUser(user);
				log.setPublications(detail.getPrice().getPublications());
				this.configureService.addUserAlertsLog(log);
			}
			if (detail.getName() != null && !"".equals(detail.getName())) {
				String[] userinfo = detail.getName().split("\n");
				if (userinfo.length == 3) {
					license.setLicenseUId(userinfo[0]);
					license.setLicensePwd(userinfo[1]);
					license.setAccessType(Integer.valueOf(userinfo[2].toString()));
				}
			}

			this.customService.insertLicense(license);
			// 增加一次成功购买次数
			ServiceFactory serviceFactory = (ServiceFactory) new ServiceFactoryImpl();
			serviceFactory.getPPublicationsService().addBuyTimes(license.getPublications().getId());

			LLicense ll = this.customService.getLicense(license.getId());
			// 查询产品信息

			// 数据库暂时不进行索引
			if (detail.getPrice().getPublications().getType() != 5) {
				this.licenseIndexService.indexLicenses(ll);
			}
			if (detail.getUser().getLevel() == 2) {// 机构管理员用户
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("institutionId", this.customService.getUser(detail.getUser().getId()).getInstitution().getId());
				List<BIpRange> ipr = this.configureService.getIpRangeList(condition, "");
				if (ipr != null && ipr.size() > 0) {
					for (BIpRange ip : ipr) {
						LLicenseIp lip = new LLicenseIp();
						lip.setStartIp(ip.getStartIp());
						lip.setSip(ip.getSip());
						lip.setEndIp(ip.getEndIp());
						lip.setEip(ip.getEip());
						lip.setLicense(license);
						this.customService.insertLicenseIp(lip);
					}
				}
			}
			if (detail.getOrder().getPayType() == 1) {
				this.oOrderService.addStoredTransation(detail, d);
			} else {
				this.oOrderService.addOtherTransation(detail, d);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void insertLicenseFromCollection(HttpServletRequest request, OOrderDetail detail) throws Exception {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("collectionId", detail.getCollection().getId());
			List<PCcRelation> pcc = this.pPublicationsService.getPccListSimple(condition);

			if (pcc != null && pcc.size() > 0) {
				Date date = new Date();
				for (PCcRelation relation : pcc) {
					PPublications pub = relation.getPublications();
					LLicense license = new LLicense();
					license.setUser(detail.getUser());
					license.setPublications(pub);
					license.setCode(pub.getCode());
					license.setCreatedby(detail.getUser().getName());
					license.setCreatedon(date);
					license.setStatus(1);
					if (relation.getEffective() != null && relation.getEffective() == 0) {
						license.setType(1);// 永久授权
					} else {
						license.setType(2);
					}
					license.setComplicating(relation.getComplicating());// 并发
					license.setStartTime(date);
					if (license.getType() == 2) {
						Date endDate = DateUtil.getUtilDate(DateUtil.getEndMonthDate(date, relation.getEffective()), "yyyy-MM-dd");
						license.setEndTime(endDate);
					}
					String accessUrl = Param.getParam("system.config").get("accessUrl");
					accessUrl = accessUrl.replace("-", ":");
					license.setReadUrl(accessUrl + "/pages/view/form/view?id=" + pub.getId());
					// 限时授权部分暂缓
					this.customService.insertLicense(license);
					// 增加一次成功购买次数
					ServiceFactory serviceFactory = (ServiceFactory) new ServiceFactoryImpl();
					serviceFactory.getPPublicationsService().addBuyTimes(license.getPublications().getId());

					LLicense ll = this.customService.getLicense(license.getId());
					this.licenseIndexService.indexLicenses(ll);
					if (detail.getUser().getLevel() == 2) {// 机构管理员用户
						Map<String, Object> condition2 = new HashMap<String, Object>();
						condition.put("institutionId", this.customService.getUser(detail.getUser().getId()).getInstitution().getId());
						List<BIpRange> ipr = this.configureService.getIpRangeList(condition2, "");
						if (ipr != null && ipr.size() > 0) {
							for (BIpRange ip : ipr) {
								LLicenseIp lip = new LLicenseIp();
								lip.setStartIp(ip.getStartIp());
								lip.setSip(ip.getSip());
								lip.setEndIp(ip.getEndIp());
								lip.setEip(ip.getEip());
								lip.setLicense(license);
								this.customService.insertLicenseIp(lip);
							}
						}
					}
				}
				if (detail.getOrder().getPayType() == 1) {
					this.oOrderService.addStoredTransation(detail, date);
				} else {
					this.oOrderService.addOtherTransation(detail, date);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping("/form/download")
	public ModelAndView download(HttpServletRequest request, HttpServletResponse response, OOrderForm form) throws Exception {
		String forwardString = "order/download";
		Map<String, Object> model = new HashMap<String, Object>();
		List<OOrder> list2 = new ArrayList<OOrder>();
		try {
			CUser user = request.getSession().getAttribute("mainUser") == null ? null : (CUser) request.getSession().getAttribute("mainUser");
			String dr = request.getParameter("dr");
			String startTime = "";
			String endTime = "";
			if (dr == null || "0".equals(dr)) {

			} else if ("1".equals(dr)) {// 一周内
				endTime = DateUtil.getNowDate("yyyy-MM-dd");
				Date sTime = DateUtil.getDealedDate(new Date(), 0, 0, -7, 0, 0, 0);
				startTime = DateUtil.getDateStr("yyyy-MM-dd", sTime);
			} else if ("2".equals(dr)) {// 一月内
				endTime = DateUtil.getNowDate("yyyy-MM-dd");
				Date sTime = DateUtil.getDealedDate(new Date(), 0, -1, 0, 0, 0, 0);
				startTime = DateUtil.getDateStr("yyyy-MM-dd", sTime);
			} else if ("3".equals(dr)) {// 三月内
				endTime = DateUtil.getNowDate("yyyy-MM-dd");
				Date sTime = DateUtil.getDealedDate(new Date(), 0, -3, 0, 0, 0, 0);
				startTime = DateUtil.getDateStr("yyyy-MM-dd", sTime);
			}

			if (user != null) {// 机构管理员或中图管理员
				Map<String, Object> condition = form.getCondition();
				condition.put("allStatus", form.getAllStatus() == null ? "1,2" : form.getAllStatus());
				condition.put("userId", user.getId());
				condition.put("startTime", startTime);
				condition.put("endTime", endTime);
				List<OOrder> list = this.oOrderService.getOrderList(condition, " order by a.createdon desc ");
				if (list != null && list.size() > 0) {
					for (OOrder oOrder : list) {
						Map<String, Object> con = new HashMap<String, Object>();
						con.put("orderId", oOrder.getId());
						con.put("userid", user.getId());
						con.put("parentId", "0");
						List<OOrderDetail> ld = this.oOrderService.getOrderDetailList(con, "");
						oOrder.setDetailList(ld);
						list2.add(oOrder);
					}
				} else {
					throw new CcsException("Controller.Order.download.noInfo.error");// 没有数据
				}
			} else {
				throw new CcsException("Controller.Order.download.noUser.error");// 未登录
			}

			// 输出的excel文件工作表名
			String worksheet = "sheet1";
			// excel工作表的标题
			String[] title = { "序号", "订单号", "订单日期" };
			String[] title2 = { "E-ISBN", "H-ISBN", "P-ISBN", "标题", "作者", "出版商", "发行日期", "币种", "价格", "订单状态" };
			WritableWorkbook workbook;
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename=Order_Details.xls");// 设定输出文件头
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型

			workbook = Workbook.createWorkbook(os);

			WritableSheet sheet = workbook.createSheet(worksheet, 0);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat.setAlignment(jxl.format.Alignment.CENTRE);

			for (int i = 0; i < title.length; i++) {
				sheet.mergeCells(i, 0, i, 1);
				// Label(列号,行号 ,内容 )
				sheet.addCell(new Label(i, 0, title[i]));
			}
			sheet.mergeCells(title.length, 0, title.length + title2.length - 1, 0);
			sheet.addCell(new Label(title.length, 0, "订单明细", cellFormat));
			for (int i = 0; i < title2.length; i++) {
				int realC = i + title.length;
				sheet.addCell(new Label(realC, 1, title2[i]));
			}
			int row = 2;
			Integer oNum = 1;
			for (OOrder o : list2) {
				sheet.mergeCells(0, row, 0, row + o.getDetailList().size() - 1);
				sheet.addCell(new Label(0, row, oNum.toString()));
				sheet.mergeCells(1, row, 1, row + o.getDetailList().size() - 1);
				sheet.addCell(new Label(1, row, o.getCode()));
				sheet.mergeCells(2, row, 2, row + o.getDetailList().size() - 1);
				sheet.addCell(new Label(2, row, DateUtil.getDateStr("yyyy-MM-dd hh:mm:ss", o.getCreatedon())));
				for (OOrderDetail d : o.getDetailList()) {
					Boolean isCollection = d.getCollection() != null;
					String pCode = isCollection ? d.getCollection().getCode() : d.getPrice().getPublications().getCode();
					String pTitle = isCollection ? d.getCollection().getName() : d.getPrice().getPublications().getTitle();
					sheet.addCell(new Label(3, row, pCode));
					sheet.addCell(new Label(4, row, isCollection ? "" : d.getPrice().getPublications().getHisbn()));
					sheet.addCell(new Label(5, row, isCollection ? "" : d.getPrice().getPublications().getSisbn()));
					sheet.addCell(new Label(6, row, pTitle));
					sheet.addCell(new Label(7, row, isCollection ? "" : d.getPrice().getPublications().getAuthor()));
					sheet.addCell(new Label(8, row, isCollection ? "" : d.getPrice().getPublications().getPublisher().getName()));
					sheet.addCell(new Label(9, row, isCollection ? "" : d.getPrice().getPublications().getPubDate()));
					sheet.addCell(new Label(10, row, d.getCurrency()));
					sheet.addCell(new Label(11, row, d.getSalePrice().toString()));
					String orderDetailStatus = "";
					switch (d.getStatus()) {
					case 1:
						orderDetailStatus = "未处理";
						break;
					case 2:
						orderDetailStatus = "已付款未开通";
						break;
					case 3:
						orderDetailStatus = "已付款已开通";
						break;
					case 4:
						orderDetailStatus = "处理中";
						break;
					case 5:
						orderDetailStatus = "已完成";
						break;
					case 10:
						orderDetailStatus = "未付款已开通";
						break;
					case 99:
						orderDetailStatus = "已取消";
						break;
					default:
						break;
					}
					sheet.addCell(new Label(12, row, orderDetailStatus));
					row++;
				}
				oNum++;
			}

			workbook.write();
			workbook.close();
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
			return new ModelAndView(forwardString, model);
		}
		return null;
	}

	@RequestMapping(value = "/syncLicense", method = RequestMethod.POST)
	public void syncLicense(HttpServletRequest request, Model model) {
		ResultObject<LLicense> result = null;
			try {
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); // 1-insert
																			// 2-update
			Converter<LLicense> converter = new Converter<LLicense>();
			LLicense obj = (LLicense) converter.json2Object(objJson, LLicense.class.getName());
			CUser user = null;
			if (obj.getUser() != null && obj.getUser().getId() != null) {
				user = this.customService.getUser(obj.getUser().getId());
			}

			LLicense license = null;
			Map<String, Object> lCondition = new HashMap<String, Object>();
			lCondition.put("lId", obj.getId());
			lCondition.put("isTrial", "0");// 无论是否试用，重要！
			List<LLicense> llist = this.customService.getLicenseList(lCondition, "");

			if (llist != null && llist.size() == 1) {
				license = llist.get(0);
			}
			if (obj.getPublications() != null) {
				PPublications pp = this.pPublicationsService.getPublications(obj.getPublications().getId());

				if ("2".equals(operType)) {
					if (license != null) {
						this.oOrderService.updateLicense(obj, obj.getId(), null);
						if (pp != null && pp.getType() != 5) {// 数据库
							// 删除索引
							this.licenseIndexService.deleteIndexById(obj.getId());
						}
					}
				} else if ("1".equals(operType)) {
					if (pp.getStatus() == 2) {// 只有上架的图书、期刊、章节、文章、数据库才能增加全文索引
						// if ((pp.getLocal() != null && pp.getLocal() == 2) ||
						// (pp.getPublications().getLocal() != null &&
						// pp.getPublications().getLocal() == 2)) {// 只有本地资源才能增加全文索引
						if ((pp.getLocal() != null && pp.getLocal() == 2) || (null != pp.getPublications() ? (pp.getPublications().getLocal() != null && pp.getPublications().getLocal() == 2) : false)) {// 只有本地资源才能增加全文索引
							String nr = "";
							String bpath = Param.getParam("pdf.directory.config").get("dir").replace("-", ":");
							String filePath = bpath + pp.getPdf();
							File pdf = new File(filePath);

							if (pdf.exists()) {
								if (pp.getFileType() == null || pp.getFileType() == 0 || pp.getFileType() == 1) {// 获取PDF全文
									/*
									 * PDDocument doc = PDDocument.load(filePath);
									 * PDFTextStripper stripper = new PDFTextStripper();
									 * pp.setDoi(nr); doc.close();
									 */

									/** 修改为IText工具读取 - start */
									com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(filePath); // 读取pdf所使用的输出流
									int num = reader.getNumberOfPages();// 获得页数
									String fullText = "";
									for (int i = 1; i <= num; i++) {
										try {
											fullText += com.itextpdf.text.pdf.parser.PdfTextExtractor.getTextFromPage(reader, i);
										} catch (RuntimeException e) {
											continue;
										}
									}
									pp.setDoi(fullText);
									reader.close();
									/** 修改为Itext工具读取 - end */
								} else if (pp.getFileType() == 2) {// 获取EPUB全文
									EPubHelper epubHelper = new EPubHelper(filePath);
									pp.setDoi(epubHelper.getText());
								}
							}
						}
					}
					if (license == null) {
						this.customService.insertLicense(obj);
						if (user != null && user.getLevel() == 2) {// 机构管理员用户
							Map<String, Object> condition = new HashMap<String, Object>();
							condition.put("institutionId", user.getInstitution().getId());
							List<BIpRange> ipr = this.configureService.getIpRangeList(condition, "");
							if (ipr != null && ipr.size() > 0) {
								for (BIpRange ip : ipr) {
									LLicenseIp lip = new LLicenseIp();
									lip.setStartIp(ip.getStartIp());
									lip.setSip(ip.getSip());
									lip.setEndIp(ip.getEndIp());
									lip.setEip(ip.getEip());
									lip.setLicense(obj);
									this.customService.insertLicenseIp(lip);
								}
							}
						}

						if (pp != null && ((pp.getJournalType() != null && pp.getJournalType() == 2 && pp.getType() == 7) || pp.getType() == 1 || pp.getType() == 3 || pp.getType() == 4)) {

							// 创建索引
							obj.setPublications(pp);
							this.licenseIndexService.indexLicenses(obj);
						}

					} else {
						this.customService.updateLicense(obj, obj.getId(), null);
						if (pp != null && ((pp.getJournalType() != null && pp.getJournalType() == 2 && pp.getType() == 7) || pp.getType() == 1 || pp.getType() == 3 || pp.getType() == 4)) {
							// 创建索引
							obj.setPublications(pp);
							this.licenseIndexService.indexLicenses(obj);
						}
					}

					// 查询一遍
					Map<String, Object> mm = new HashMap<String, Object>();
					mm.put("lId", obj.getId());
					mm.put("isTrial", null);
					List<LLicense> licenseList = this.pPublicationsService.getLicenseList(mm, "");
					obj = licenseList.get(0);
					Map<String, Object> condition = new HashMap<String, Object>();
					PPublications pub = this.pPublicationsService.getPublications(obj.getPublications().getId());
					condition.put("pubId", pub.getId());
					condition.put("userId", user.getId());
					List<LUserAlertsLog> alertslist = this.oOrderService.getLUserAlertsLogList(condition, "");
					if (alertslist != null && alertslist.size() > 0) {
						LUserAlertsLog alert = alertslist.get(0);
						alert.setUserName(user.getName());
						alert.setUser(user);
						alert.setPublicationsName(pub.getTitle());
						alert.setIsbn(pub.getCode());
						alert.setEmail(user.getEmail());
						alert.setPublications(pub);
						alert.setAlertDate(DateUtil.getDateStr("yyyy-MM-dd", DateUtil.getUtilDate(DateUtil.getEndMonthDate(obj.getStartTime(), obj.getTrialPeriod() - 1), "yyyy-MM-dd")));
						alert.setAlertStatus(1);
						alert.setAlertType(1);
						this.oOrderService.updateLUserAlertsLog(alert, LUserAlertsLog.class.getName(), alert.getId(), null);// 插入提醒记录
					} else {
						if (obj.getType() == 2) {
							// 限时
							LUserAlertsLog alert = new LUserAlertsLog();
							alert.setUserName(user.getName());
							alert.setUser(user);
							alert.setPublicationsName(pub.getTitle());
							alert.setIsbn(pub.getCode());
							alert.setEmail(user.getEmail());
							alert.setPublications(pub);
							alert.setAlertDate(DateUtil.getDateStr("yyyy-MM-dd", DateUtil.getUtilDate(DateUtil.getEndMonthDate(obj.getStartTime(), obj.getTrialPeriod() - 1), "yyyy-MM-dd")));
							alert.setAlertStatus(1);
							alert.setAlertType(1);
							this.configureService.addUserAlertsLog(alert);// 插入提醒记录
						}
					}
				} else if ("3".equals(operType)) {
					// 3是专为OA和免费设置的
					if (license == null) {
						this.customService.insertLicense(obj);
					} else {
						this.customService.updateLicense(obj, obj.getId(), null);
					}
				}
			} else {
				// 产品包,
				// Map<String,Object> con = new HashMap<String,Object>();
				// con.put("collectionId",obj.getCollection().getId());
				// con.put("userId", user.getId());
				// List<LLicense> lList =
				// this.pPublicationsService.getLicenseList(con, "");
				if ("2".equals(operType)) {
					if (license != null) {
						this.oOrderService.updateLicense(obj, obj.getId(), null);
					}
				} else if ("1".equals(operType)) {
					if (license == null) {
						this.customService.insertLicense(obj);
						if (user != null && user.getLevel() == 2) {// 机构管理员用户
							Map<String, Object> condition = new HashMap<String, Object>();
							condition.put("institutionId", user.getInstitution().getId());
							List<BIpRange> ipr = this.configureService.getIpRangeList(condition, "");
							if (ipr != null && ipr.size() > 0) {
								for (BIpRange ip : ipr) {
									LLicenseIp lip = new LLicenseIp();
									lip.setStartIp(ip.getStartIp());
									lip.setSip(ip.getSip());
									lip.setEndIp(ip.getEndIp());
									lip.setEip(ip.getEip());
									lip.setLicense(obj);
									this.customService.insertLicenseIp(lip);
								}
							}
						}
					} else {
						this.customService.updateLicense(obj, obj.getId(), null);
					}
				} else if ("3".equals(operType)) {
					// 3是专为OA和免费设置的
					if (license == null) {
						this.customService.insertLicense(obj);
					} else {
						this.customService.updateLicense(obj, obj.getId(), null);
					}
				}
			}
			// }
			ObjectUtil<LLicense> util = new ObjectUtil<LLicense>();
			util.setNull(obj, new String[] { Set.class.getName() });
			if (obj.getUser() != null) {
				ObjectUtil<CUser> uutil = new ObjectUtil<CUser>();
				uutil.setNull(obj.getUser(), new String[] { Set.class.getName(), List.class.getName() });
			}
			if (obj.getPublications() != null) {
				ObjectUtil<PPublications> putil = new ObjectUtil<PPublications>();
				putil.setNull(obj.getPublications(), new String[] { Set.class.getName(), List.class.getName() });
			}
			if (obj.getPprice() != null) {
				ObjectUtil<PPrice> putil = new ObjectUtil<PPrice>();
				putil.setNull(obj.getPprice(), new String[] { Set.class.getName(), List.class.getName() });
			}
			if (obj.getCollection() != null) {
				ObjectUtil<PCollection> putil = new ObjectUtil<PCollection>();
				putil.setNull(obj.getCollection(), new String[] { Set.class.getName(), List.class.getName() });
			}
			result = new ResultObject<LLicense>(1, obj, Lang.getLanguage("Controller.LLicense.insert.success", request.getSession().getAttribute("lang").toString()));// "License信息维护成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResultObject<LLicense>(2, Lang.getLanguage("Controller.LLicense.insert.error", request.getSession().getAttribute("lang").toString()));// "License信息维护失败！");
		}
		model.addAttribute("target", result);
	}

	@RequestMapping(value = "/syncPDA", method = RequestMethod.POST)
	public void syncPDA(HttpServletRequest request, Model model) {
		ResultObject<BPDAInfo> result = null;
		try {
			String objJson = request.getParameter("obj").toString();
			String operType = request.getParameter("operType").toString(); // 1-insert
																			// 2-update
			Converter<BPDAInfo> converter = new Converter<BPDAInfo>();
			BPDAInfo obj = (BPDAInfo) converter.json2Object(objJson, BPDAInfo.class.getName());
			if ("2".equals(operType)) {
				this.oOrderService.updatePDAInfo(obj, obj.getId());
			} else if ("1".equals(operType)) {
				this.oOrderService.insertPDAInfo(obj);
			} else if ("3".equals(operType)) {
				this.oOrderService.deletePDAInfo(obj.getId());
			}
			result = new ResultObject<BPDAInfo>(1, Lang.getLanguage("Controller.Oorder.syncPDA.success", request.getSession().getAttribute("lang").toString()));// PDA信息同步成功
		} catch (Exception e) {
			result = new ResultObject<BPDAInfo>(2, Lang.getLanguage("Controller.Oorder.syncPDA.failed", request.getSession().getAttribute("lang").toString()));// PDA信息同步失败
		}
		model.addAttribute("target", result);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/createLicense")
	public void addLicenseIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String licenseId = request.getParameter("l");
			LLicense license = this.pPublicationsService.getLicense(licenseId);
			if (license != null) {
				if (license.getPublications().getStatus() == 2) {// 只有上架的图书、期刊、章节、文章、数据库才能增加全文索引
					if (license.getPublications().getLocal() == 2) {// 只有本地资源才能增加全文索引
						String nr = "";
						String bpath = Param.getParam("pdf.directory.config").get("dir").replace("-", ":");
						String filePath = bpath + license.getPublications().getPdf();
						File pdf = new File(filePath);
						if (pdf.exists()) {
							if (license.getPublications().getFileType() == null || license.getPublications().getFileType() == 1) {// 获取PDF全文
								PDDocument doc = PDDocument.load(filePath);
								PDFTextStripper stripper = new PDFTextStripper();
								nr = stripper.getText(doc);
								license.getPublications().setDoi(nr);
								doc.close();
							} else if (license.getPublications().getFileType() == 2) {// 获取EPUB全文
								EPubHelper epubHelper = new EPubHelper(filePath);
								license.getPublications().setDoi(epubHelper.getText());
							}
						}
					}
				}
				this.licenseIndexService.indexLicenses(license);
				response.getWriter().print("OK");
			} else {
				response.getWriter().print("Not Find");
			}
		} catch (Exception e) {
			response.getWriter().print("Failed");
		}
	}

	@RequestMapping(value = "/alipaySubmit")
	public ModelAndView alipaySubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OOrder order = this.oOrderService.getOrder(request.getParameter("orderId"));
		if (order != null) {
			OOrderDetailForm.setAlipayMap(Param.getParam("content.alipay.map"));
			String payment_type = "1";// 支付类型--必填，不能修改

			String notify_url = OOrderDetailForm.getAlipayMap().get("notify_url").replace("-", ":");// 服务器异步通知页面路径--需http://格式的完整路径，不能加?id=123这类自定义参数

			String return_url = OOrderDetailForm.getAlipayMap().get("return_url").replace("-", ":");// 页面跳转同步通知页面路径--需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

			String seller_email = OOrderDetailForm.getAlipayMap().get("seller_email");// 卖家支付宝帐户--必填

			String subject = order.getCode();// OOrderDetailForm.getAlipayMap().get("subject");//订单名称--必填

			String body = OOrderDetailForm.getAlipayMap().get("body");// 订单描述

			String show_url = OOrderDetailForm.getAlipayMap().get("show_url").replace("-", ":") + order.getId();// 商品展示地址--需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html

			String partner = OOrderDetailForm.getAlipayMap().get("partner");// 合作身份者ID，以2088开头由16位纯数字组成的字符串

			// String key = OOrderDetailForm.getAlipayMap().get("key");// 商户的私钥

			// String log_path =
			// OOrderDetailForm.getAlipayMap().get("log_path");//
			// 调试用，创建TXT日志文件夹路径

			String input_charset = OOrderDetailForm.getAlipayMap().get("input_charset");// 字符编码格式
																						// 目前支持
																						// gbk
																						// 或
																						// utf-8

			// String sign_type =
			// OOrderDetailForm.getAlipayMap().get("sign_type");// 签名方式 不需修改

			// 商户订单号-- 商户网站订单系统中唯一订单号，必填
			String out_trade_no = order.getCode();
			// 付款金额--必填
			String total_fee = order.getSalePriceExtTax().toString();

			/** 配置文件里没有配置的信息 End **/
			// 防钓鱼时间戳--若要使用请调用类文件submit中的query_timestamp函数
			String anti_phishing_key = "";
			// 客户端的IP地址--非局域网的外网IP地址，如：221.0.0.1
			String exter_invoke_ip = "";
			/** 配置文件里没有配置的信息 Start **/

			// 把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", "create_direct_pay_by_user");// 默认的
			sParaTemp.put("partner", partner);
			sParaTemp.put("_input_charset", input_charset);
			sParaTemp.put("payment_type", payment_type);
			sParaTemp.put("notify_url", notify_url);
			sParaTemp.put("return_url", return_url);
			sParaTemp.put("seller_email", seller_email);
			sParaTemp.put("out_trade_no", out_trade_no);
			sParaTemp.put("subject", subject);
			sParaTemp.put("total_fee", total_fee);
			sParaTemp.put("body", body);
			sParaTemp.put("show_url", show_url);
			sParaTemp.put("anti_phishing_key", anti_phishing_key);
			sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

			// 建立请求
			String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("htmlcode", sHtmlText);
			return new ModelAndView("order/alipayapi", model);
		}
		return null;
	}

	@RequestMapping(value = "/alipayReturn")
	public ModelAndView alipayReturn(HttpServletRequest request, HttpServletResponse response, OOrderForm form) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String forwardString = "order/return_url";
		try {
			// 获取支付宝GET过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}

			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");// 商户订单号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");// 支付宝交易号
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");// 交易状态
			// 计算得出通知验证结果
			boolean verify_result = AlipayNotify.verify(params);
			if (verify_result) {// 验证成功
				if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
					Map<String, Object> Ordercondition = new HashMap<String, Object>();
					Ordercondition.put("code", out_trade_no);
					List<OOrder> listOrder = this.oOrderService.getOrderList(Ordercondition, "");
					if (listOrder != null && listOrder.size() > 0) {
						// 根绝ID查询订单信息
						for (OOrder order : listOrder) {
							model.put("order", order);
							// 根据条件查询订单明细
							Map<String, Object> condition = new HashMap<String, Object>();
							condition.put("orderId", order.getId());
							// condition.put("parentId",
							// form.getParentId()==null?"0":form.getParentId());
							List<OOrderDetail> list = this.oOrderService.getDetailList(condition, "");
							request.setAttribute("message", Lang.getLanguage("Alipay.success", request.getSession().getAttribute("lang").toString()));
							model.put("detailList", list);
						}
					}
				}
			}
		} catch (Exception e) {
			request.setAttribute("message", (e instanceof CcsException) ? Lang.getLanguage(((CcsException) e).getPrompt(), request.getSession().getAttribute("lang").toString()) : e.getMessage());
			forwardString = "error";
		}
		return new ModelAndView(forwardString, model);
	}

	@RequestMapping(value = "/alipayNotify")
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		try {
			// 获取支付宝GET过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");// 商户订单号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");// 支付宝交易号
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");// 交易状态
			// 计算得出通知验证结果
			boolean verify_result = AlipayNotify.verify(params);
			if (verify_result) {// 验证成功
				if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
					// 跟新同步支付宝回执信息
					this.oOrderService.alipayReturnNotity(out_trade_no, trade_no);
				}
				response.getWriter().print("success");// 不能更改success
			} else {
				response.getWriter().print("fail"); // 不能更改fail
			}
		} catch (Exception e) {
			response.getWriter().print("fail");// 不能更改fail
		}

	}

	@RequestMapping(value = "/getCnpeLicenseStatus", method = RequestMethod.POST)
	public void isPubExist(HttpServletRequest request, Model model) {
		ResultObject<Integer> result = null;
		try {
			Integer status = 0;
			String licenseId = request.getParameter("id").toString();
			if (licenseId != null && !"".equals(licenseId)) {
				LLicense ll = this.customService.getLicense(licenseId);
				if (ll != null) {
					status = ll.getStatus();
				}
			}
			result = new ResultObject<Integer>(1, status, "");
		} catch (Exception e) {
			result = new ResultObject<Integer>(2, "");
		}
		model.addAttribute("target", result);
	}

}
