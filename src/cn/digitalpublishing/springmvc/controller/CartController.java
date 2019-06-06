package cn.digitalpublishing.springmvc.controller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.daxtech.framework.Internationalization.Lang;
import cn.com.daxtech.framework.exception.CcsException;
import cn.com.daxtech.framework.model.Param;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.CUserProp;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.ep.po.OExchange;
import cn.digitalpublishing.ep.po.OOrderDetail;
import cn.digitalpublishing.ep.po.PCcRelation;
import cn.digitalpublishing.ep.po.PCollection;
import cn.digitalpublishing.ep.po.PCsRelation;
import cn.digitalpublishing.ep.po.PPrice;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.ep.po.RRecommend;
import cn.digitalpublishing.springmvc.form.order.OOrderDetailForm;
import cn.digitalpublishing.util.io.FileUtil;
import cn.digitalpublishing.util.web.IpUtil;
import cn.digitalpublishing.util.web.MathHelper;
import cn.digitalpublishing.util.web.TokenProcessor;

@Controller
@RequestMapping("/pages/cart")
public class CartController extends BaseController {

	/**
	 * 购物车商品列表
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/form/list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,HttpSession session, OOrderDetailForm form)throws Exception {
		String forwardString="cart/list";
		Map<String,Object> model = new HashMap<String,Object>();
		try{
			if(session.getAttribute("mainUser")!=null){	
				
				CUser user=(CUser)session.getAttribute("mainUser");
				
				if(user!=null){
				Map<String,Object> conditionPayment=new HashMap<String, Object>();
					conditionPayment.put("userTypeId", user.getLevel().toString());
					form.setPayments(this.customService.getPaymentList(conditionPayment," order by a.payType "));
				}
				if(form.getPaytype()==null||"".equals(form.getPaytype())){
					if("2".equals(user.getUserType().getId())){
						form.setPaytype("2");
					}else{
						form.setPaytype("3");
					}
				}
				form.setUrl(request.getRequestURL().toString());
				form.setIp(IpUtil.getIp(request));
				Map<String,Object> condition =form.getCondition();
				condition.put("status", 4);
				condition.put("userid", user.getId());
				condition.put("parentId","0");
				condition.put("orderNull", "1");
				form.setCount(this.oOrderService.getOrderDetailCount(condition));
				List<OOrderDetail> slist = this.oOrderService.getDetailList(condition," order by a.createdon desc ");
				//跟新明细中
				if(slist!=null&&slist.size()>0){
					this.createoorder(slist, form, request.getSession().getAttribute("lang").toString());
				}
				List<OOrderDetail> list = this.oOrderService.getDetailList(condition," order by a.createdon desc ");
				Double totalprice=0d;
				String tips="";
				String tips1="";
				String collstring1="";
				String collstring2="";
				if(list!=null && list.size()>0){
					for(OOrderDetail d:list){						
						totalprice=MathHelper.add(totalprice,d.getSalePriceExtTax());//计算总价	
						if(d.getCollection()!=null){//是产品包							
							//检查包中的子产品是否与曾经购买或正在购买的单品重复
							Map<String,Object> map=new HashMap<String,Object>();
							map.put("collectionId",d.getCollection().getId());
							List<PCcRelation> pcclist=this.pPublicationsService.getPccList(map, "",user,form.getIp());							
							if(pcclist!=null && pcclist.size()>0){
								String tmp="";
								for(PCcRelation relation:pcclist){//包中的子产品是否与曾经购买或正在购买的单品重复
									if(relation.getPublications().getSubscribedUser()>0||relation.getPublications().getBuyInDetail()>0){
										tmp=tmp.concat("<font class=\"pubTips\" style=\"padding-left:15px;\"><a href=\"");//拼接子产品ISBN
										tmp=tmp.concat(request.getContextPath());
										tmp=tmp.concat("/pages/publications/form/article/");
										tmp=tmp.concat(relation.getPublications().getId());
										tmp=tmp.concat("\">");
										tmp=tmp.concat(relation.getPublications().getCode());
										tmp=tmp.concat("</a></font><br>");
									}
								}
								if(!"".equals(tmp)){
									tips=tips.concat("<font class=\"collTips\">");//拼接子产品所属的包名
									tips=tips.concat(d.getName());
									tips=tips.concat("</font><br>");
									tips=tips.concat(tmp);
								}	
							}//检查包中的子产品是否与曾经购买或正在购买的单品重复 结束							

							//检查购物车中的包是否有重复
							System.out.println(d.getCollection().getId());
							if(collstring1.indexOf(d.getCollection().getId())>=0){				
								if(collstring2.indexOf(d.getCollection().getId())<0){
									collstring2=collstring2.concat(d.getCollection().getId());
									String tmp="<font class=\"collTips\">";
									tmp=tmp.concat(d.getName());
									tmp=tmp.concat("</font><br>");
									tips1=tips1.concat(tmp);
								}
							}
							collstring1=collstring1.concat(d.getCollection().getId().concat("|"));
							//检查购物车中的包是否有重复 结束	
						}
					}
					if(!"".equals(tips1)){
						model.put("colltips","<div ><span class=\"tipstitle\" style=\"padding-left:10px;\">"+Lang.getLanguage("Controller.Cart.list.tips",request.getSession().getAttribute("lang").toString())+"</span><div class=\"tipscontent\" style=\"margin-left:40px;\">"+tips1 + "</div></div>");//以下产品包被重复购买
					}else{
						model.put("colltips", null);
					}	
					if(!"".equals(tips)){
						model.put("pubtips", "<div ><span class=\"tipstitle\" style=\"padding-left:10px;\">"+Lang.getLanguage("Controller.Cart.list.collection.tips",request.getSession().getAttribute("lang").toString())+"</span><div class=\"tipscontent\" style=\"margin-left:40px;\">"+tips + "</div></div>");//产品包中的以下商品已购买或已添加至购物车
					}else{
						model.put("pubtips", null);
					}	
					
				}//if(list!=null && list.size()>0)
				form.setTotalPrice(totalprice);
				Double balance=this.oOrderService.getUserBalance(user.getId());
				model.put("balance",balance);
				model.put("list",list);
				/**
				 * 获取第一个资源的分类带到前台
				 */
				if(list.size()>0) {
					PPublications pub = null;
					String id = "";
					for (int i = 0; i < list.size(); i++) {
						if (null != list.get(i).getPrice()) {
							id = list.get(i).getPrice().getPublications().getId();
							pub=this.pPublicationsService.getPublications(id);
							break;
						}
					}
					
					condition.put("pppppId", pub.getId());
					List<PCsRelation> pcsList=this.bSubjectService.getSubPubList(condition, "");
					if(pcsList.size()>0){
						form.setSubtype(pcsList.get(0).getSubject().getCode().substring(0,1).toUpperCase());
					}else{
						form.setSubtype("A");
					}
				}
			}else{
				forwardString="frame/result";
				request.setAttribute("prompt", Lang.getLanguage("Controller.Cart.list.Competence.prompt",request.getSession().getAttribute("lang").toString()));//"访问受限提示");
				request.setAttribute("message",Lang.getLanguage("Controller.Cart.list.Competence.message",request.getSession().getAttribute("lang").toString()));//"对不起，您不能访问此页面");
			}
//			model.put("form", form);
		}catch(Exception e){
			e.printStackTrace();
            request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="error";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}
	@RequestMapping(value="/form/getCartPagingListDown")
	public ModelAndView getCartPagingListDown(HttpServletRequest request,HttpServletResponse response,HttpSession session, OOrderDetailForm form)throws Exception {
		String forwardString="cart/down";
		Map<String,Object> condition = new HashMap<String,Object>();
		Map<String,Object> model = new HashMap<String,Object>();
		try {
			List<PPublications> pubList=new ArrayList<PPublications>(6);
			Map<String,Object> map = new HashMap<String,Object>();
			condition.clear();
			String strMark="A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
			String[] ary=strMark.split(",");
			for (int i = 0; i < ary.length; i++) {
				pubList.clear();
				condition.put("mark", ary[i]);
				List<LLicense> licList=this.customService.getCartPagingListDown(condition, "GROUP BY a.publications.id ORDER BY cast(count(*) as int) DESC", 6, form.getCurpage());
				for (int k = 0; k < licList.size(); k++) {
					PPublications pub= this.pPublicationsService.getPublications(licList.get(k).getPublications().getId());
					pubList.add(pub);
				}
				if(form.getSubtype().equals(ary[i])){
					model.put("downList", pubList);
				}
				map.put("downList", pubList);
				map.put("form", form);
				map.put("request", request);
				String path = Param.getParam("config.website.path").get("path").replace("-",":");
				FileUtil.generateHTML("pages/ftl", "cartDown.ftl", "cart_"+ary[i]+".html", map, request.getSession().getServletContext(), path);
			}
			return new ModelAndView(forwardString,model);
		} catch (Exception e) {
			throw e;
		}
	}
	@RequestMapping(value="/form/checkout")
	public ModelAndView checkOut(HttpServletRequest request,HttpServletResponse response,HttpSession session, OOrderDetailForm form)throws Exception {
		String forwardString="order/checkout";
		Map<String,Object> model = new HashMap<String,Object>();
		List<CUserProp> list = null;
		try{
			form.setUrl(request.getRequestURL().toString());
			Map<String,Object> condition =form.getCondition();
			if(session.getAttribute("mainUser")!=null){
				
				CUser user=(CUser)session.getAttribute("mainUser");	
				if(user!=null){
					Map<String,Object> conditionPayment=new HashMap<String, Object>();
						conditionPayment.put("userTypeId", user.getLevel().toString());
						form.setPayments(this.customService.getPaymentList(conditionPayment," order by a.payType "));
					}
				condition.put("userId",user.getId());
				list = this.customService.getUserPropList(condition, " order by a.must desc,a.order");
				form.setCountryMap(Param.getParam("country.value",true,request.getSession().getAttribute("lang").toString()));				
				form.setProvinceMap(Param.getParam("province.value",true,request.getSession().getAttribute("lang").toString()));
				form.setIp(IpUtil.getIp(request));
				form.setBalance(this.oOrderService.getUserBalance(user.getId()));
				Map<String,Object> cmap =form.getCondition();
				cmap.put("status", 4);
				cmap.put("userid", user.getId());
				cmap.put("parentId","0");
				cmap.put("orderNull", "1");
				form.setCount(this.oOrderService.getOrderDetailCount(cmap));
				List<OOrderDetail> slist = this.oOrderService.getDetailList(cmap," order by a.createdon desc ");
				//跟新明细中
				this.createoorder(slist, form, request.getSession().getAttribute("lang").toString());
				List<OOrderDetail> listdetails = this.oOrderService.getDetailList(cmap," order by a.createdon desc ");
				double totalprice=0d;
				if(listdetails!=null && listdetails.size()>0){					
					for(OOrderDetail d:listdetails){						
						totalprice=MathHelper.add(totalprice,d.getSalePriceExtTax());//计算总价	
					}				
					form.setTotalPrice(totalprice);
					model.put("listdetails",listdetails);					
					if(form.getPropsValue()!=null){// 修改个人资料验证不通过将form表单值返回页面
						if(list!=null&&!list.isEmpty()){
							for(int i = 0 ;i<list.size();i++){
								form.getValues().put(list.get(i).getCode(), form.getPropsValue()[i]);
							}
						}
					}else{						  //修改个人资料将数据库数据返回页面
						if(list!=null&&!list.isEmpty()){
							for(int i = 0 ;i<list.size();i++){
								form.getValues().put(list.get(i).getCode(), list.get(i).getVal());
							}
						}
					}
					//生成Token序列
					TokenProcessor tokemProcessor=TokenProcessor.getInstance();
			        tokemProcessor.saveToken(request);
			        String token=(String)request.getSession().getAttribute(tokemProcessor.FORM_TOKEN_KEY);
			        request.setAttribute("form_token_key", tokemProcessor.FORM_TOKEN_KEY);
			        request.setAttribute("token", token);
			        
					model.put("list", list);
					model.put("form", form);
				}else{
					request.setAttribute("prompt", Lang.getLanguage("Controller.Cart.checkOut.order.prompt",request.getSession().getAttribute("lang").toString()));//"确认订单提示");
		            request.setAttribute("message",Lang.getLanguage("Controller.Cart.checkOut.message.noPub",request.getSession().getAttribute("lang").toString()));//"对不起，您还没有选择商品");
					forwardString="frame/result";
				}			
			}else{
				request.setAttribute("prompt", Lang.getLanguage("Controller.Cart.checkOut.access.prompt",request.getSession().getAttribute("lang").toString()));//"访问受限提示");
	            request.setAttribute("message",Lang.getLanguage("Controller.Cart.checkOut.message.noLogin",request.getSession().getAttribute("lang").toString()));//"对不起，您还没有登陆，暂时无法访问该页面");
				forwardString="frame/result";
			}
		}catch(Exception e){
			request.setAttribute("prompt", Lang.getLanguage("Controller.Cart.checkOut.person.prompt",request.getSession().getAttribute("lang").toString()));//"个人资料维护失败提示");
            request.setAttribute("message",(e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
			forwardString="frame/result";
		}
		model.put("form", form);
		return new ModelAndView(forwardString, model);
	}
	
		
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/form/add")
	public void addToCart(HttpServletRequest request,HttpServletResponse response,HttpSession session, OOrderDetailForm form)throws Exception {
		String result="";
		try{
			if(session.getAttribute("mainUser")!=null){
				form.setIp(IpUtil.getIp(request));
				form.setQuantity(1);
				CUser user=(CUser)session.getAttribute("mainUser");
				//查询用户是否是免税用户
				Map<String ,Object> condition=new HashMap<String,Object>();
				condition.put("userId", user.getId());
				List<CUserProp> props=this.customService.getUserPropList(condition,"");
				if(props!=null && props.size()>0){						
					for(CUserProp prop:props){
						if(prop.getCode()!=null && "freetax".equals(prop.getCode())){
							if("2".equals(prop.getVal())){
								form.setIsFreeTax(1);//用户为免税用户
								break;
							}
						}
					}
				}
				if(form.getKind()==1){
					PPrice price=this.pPublicationsService.getPriceByCondition(form.getCondition());
					if(price!=null){//用户提交的是合法的价格ID		
						PPublications ppub = this.pPublicationsService.getPublications(form.getPubId());
						if(ppub!=null){
							Map<String,Object> map = new HashMap<String,Object>();
							map.put("id", form.getPubId());
							List<PPublications> ls = new ArrayList<PPublications>();
							if(ppub.getType()==5){
								ls = this.pPublicationsService.getDatabaseList(map,"", user,form.getIp());	
							}else if("2,4,6,7".indexOf(ppub.getType())>-1){
								ls = this.pPublicationsService.getArticleList(map,"", user,form.getIp());	
							}else{
								ls = this.pPublicationsService.getPubList(map,"", user,form.getIp());	
							}
							if(ls!=null && !ls.isEmpty()){
								PPublications publication=ls.get(0);
								if(publication.getStatus() != null && publication.getStatus()==2){//已上架
									if(publication.getSubscribedUser()<=0&&(publication.getBuyInDetail()<=0&&publication.getExLicense()>=0)){
										OOrderDetail detail=createOrderDetail(user, publication, price, form,request.getSession().getAttribute("lang").toString());								
										this.oOrderService.insertOrderDetail(detail);
										cartManager(request.getSession(), user.getId(), 1);//购物车中商品数量+1
										result="success:"+Lang.getLanguage("Controller.Cart.addToCart.success",request.getSession().getAttribute("lang").toString())+":"+request.getSession().getAttribute("totalincart");//添加产品到购物车成功";
									}else if(publication.getType()==2){
										//期刊可以按照价格的不同进行重复购买，
										Map<String,Object> detailCondition = new HashMap<String,Object>();
										detailCondition.put("priceId", price.getId());
										//如果是机构管理员用户，则直接通过机构Id查询
										if(user.getLevel()==2){
											detailCondition.put("institutionId", user.getInstitution().getId());
										}else{
											//其他用户根据用户自己的Id查询，是否可以购买
											detailCondition.put("userid", user.getId());
										}
										//查询购物车中是否存在
//										detailCondition.put("orderNull", "1");//没有生成订单的明细
										detailCondition.put("statusArry", new Integer[]{1,2,4});//状态 1-未处理 2-已付款未开通 3-已付款已开通 4-处理中 10-未付款已开通  99-已取消
										List<OOrderDetail> odetailList = this.oOrderService.getDetailListForAddCrat(detailCondition);
										if(odetailList!=null&&odetailList.size()>0){
											result="error:"+Lang.getLanguage("Controller.Cart.addToCart.pubAlready.error",request.getSession().getAttribute("lang").toString());//该商品在购物车中或已购买！";
										}else{
											//在明细中没有找到
											//查找Licesne，License中是否有有效的 1-有效 2-无效
											detailCondition.put("status", 1);
											List<LLicense> licenseList = this.oOrderService.getLicenseForAddCart(detailCondition);
											if(licenseList!=null&&licenseList.size()>0){
												result="error:"+Lang.getLanguage("Controller.Cart.addToCart.pubAlready.error",request.getSession().getAttribute("lang").toString());//该商品在购物车中或已购买！";
											}else{
												//添加到明细中
												OOrderDetail detail=createOrderDetail(user, publication, price, form,request.getSession().getAttribute("lang").toString());								
												this.oOrderService.insertOrderDetail(detail);
												cartManager(request.getSession(), user.getId(), 1);//购物车中商品数量+1
												result="success:"+Lang.getLanguage("Controller.Cart.addToCart.success",request.getSession().getAttribute("lang").toString())+":"+request.getSession().getAttribute("totalincart");//添加产品到购物车成功";
											}
										}
									}else{
										result="error:"+Lang.getLanguage("Controller.Cart.addToCart.pubAlready.error",request.getSession().getAttribute("lang").toString());//该商品在购物车中或已购买！";
									}
								}else{
									//产品未上架
									result="error:"+Lang.getLanguage("Controller.Cart.addToCart.pubOff.error",request.getSession().getAttribute("lang").toString());//该产品未上架！";
								}
							}else{
								//产品未找到
								result="error:"+Lang.getLanguage("Controller.Cart.addToCart.noPub.error",request.getSession().getAttribute("lang").toString());//该产品未找到！";
							}
						}else{
							//产品未找到
							result="error:"+Lang.getLanguage("Controller.Cart.addToCart.noPub.error",request.getSession().getAttribute("lang").toString());//该产品未找到！";
						}
					}else{
						//用户提交了非法的价格信息
						result="error:"+Lang.getLanguage("Controller.Cart.addToCart.price.error",request.getSession().getAttribute("lang").toString());//该价格不正确！";
					}
				}else if(form.getKind()==2){
					PCollection collection=this.pPublicationsService.getCollection(form.getCollectionId()) ;
					if(collection!=null){							
						OOrderDetail detail=createOrderDetail(user, collection,form,request.getSession().getAttribute("lang").toString());
						this.oOrderService.insertOrderDetailCollection(detail);
						cartManager(request.getSession(), user.getId(), 1);//购物车数量+1
						result="success:"+Lang.getLanguage("Controller.Cart.addToCart.col.success",request.getSession().getAttribute("lang").toString())+":"+request.getSession().getAttribute("totalincart");//添加产品包到购物车成功";
					}else{
						//产品包未找到
						result="error:"+Lang.getLanguage("Controller.Cart.addToCart.noCol.error",request.getSession().getAttribute("lang").toString());//该产品包未找到！";
					}
				}else{
					//用户提交了非法的产品Kind值
					result="error:"+Lang.getLanguage("Controller.Cart.addToCart.noKind.error",request.getSession().getAttribute("lang").toString());//非法的Kind值！";
				}
			}else{
				result="error:"+Lang.getLanguage("Controller.Cart.addToCart.noLogin.error",request.getSession().getAttribute("lang").toString());//您没有登陆，请登陆后重试！";
			}
		}catch(Exception e){
			result="error:"+((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
		try {
		      response.setContentType("text/html;charset=UTF-8");
			  PrintWriter out = response.getWriter();
			  out.print(result);
			  out.flush();
			  out.close();
	    } catch (Exception e) {
	    	form.setMsg(e.getMessage());
	    }	
	}
	@RequestMapping(value="/form/delete")
	public void deleteItem(HttpServletRequest request,HttpServletResponse response,HttpSession session, OOrderDetailForm form)throws Exception {
		String result="success:"+Lang.getLanguage("Controller.Cart.deleteItem.delete.success",request.getSession().getAttribute("lang").toString());//删除商品成功！";
		try{
			if(session.getAttribute("mainUser")!=null){
				CUser user=(CUser)session.getAttribute("mainUser");
				this.oOrderService.deleteOrderDetail(form.getId());
				result="success:"+Lang.getLanguage("Controller.Cart.deleteItem.delete.success",request.getSession().getAttribute("lang").toString());//删除商品成功！";
				cartManager(request.getSession(), user.getId(), -1);
			}
		}catch(Exception e){
			result="error:"+((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
		try {
		      response.setContentType("text/html;charset=UTF-8");
			  PrintWriter out = response.getWriter();
			  out.print(result);
			  out.flush();
			  out.close();
	    } catch (Exception e) {
	    	form.setMsg((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
	    }	
	}
	
	@RequestMapping(value="/form/deleteAll")
	public void deleteAllItem(HttpServletRequest request,HttpServletResponse response,HttpSession session, OOrderDetailForm form)throws Exception {
		String result="success:"+Lang.getLanguage("Controller.Cart.deleteItem.delete.success",request.getSession().getAttribute("lang").toString());//删除商品成功！";
		try{
		/*	if(session.getAttribute("mainUser")!=null){
				CUser user=(CUser)session.getAttribute("mainUser");
				Map<String,Object> condition =form.getCondition();
				condition.put("status", 4);
				condition.put("userid", user.getId());
				condition.put("parentId","0");
				condition.put("orderNull", "1");
				List<OOrderDetail> list = this.oOrderService.getDetailList(condition," order by a.createdon desc ");
				this.oOrderService.deleteOrderDetail(list);
				result="success:"+Lang.getLanguage("Controller.Cart.deleteItem.delete.success",request.getSession().getAttribute("lang").toString());//删除商品成功！";
				cartManager(request.getSession(), user.getId(), -1);
			}*/
			if(session.getAttribute("mainUser")!=null){
				CUser user=(CUser)session.getAttribute("mainUser");
				String[] detailsId=form.getDetailIds().split(",");
				for (int i = 0; i < detailsId.length; i++) {
					if(!detailsId[i].equals(""))
					this.oOrderService.deleteOrderDetail(detailsId[i]);
				}
				result="success:"+Lang.getLanguage("Controller.Cart.deleteItem.delete.success",request.getSession().getAttribute("lang").toString());//删除商品成功！";
				cartManager(request.getSession(), user.getId(), -1);
			}

		}catch(Exception e){
			result="error:"+((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
		}
		try {
		      response.setContentType("text/html;charset=UTF-8");
			  PrintWriter out = response.getWriter();
			  out.print(result);
			  out.flush();
			  out.close();
	    } catch (Exception e) {
	    	form.setMsg((e instanceof CcsException)?Lang.getLanguage(((CcsException)e).getPrompt(),request.getSession().getAttribute("lang").toString()):e.getMessage());
	    }	
	}
	
	
	private OOrderDetail createOrderDetail(CUser user,PPublications pub,PPrice price,OOrderDetailForm form,String lang)throws Exception{
		try{
			OOrderDetail detail=new OOrderDetail();
			detail.setPrice(new PPrice());
			detail.getPrice().setId(price.getId());
			detail.setUser(user);
			detail.setQuantity(form.getQuantity());//数量
			detail.setCreatedby(user.getName());
			detail.setCreatedon(new Date());
			detail.setUpdatedby(user.getName());
			detail.setUpdatedon(new Date());
			detail.setName(pub.getTitle());
			detail.setIp(form.getIp());
			detail.setCurrency("RMB");
			detail.setItemType(pub.getType());
			detail.setStatus(4);
			detail.setProductCode(pub.getCode());
			detail.setSendStatus(1);//1未同步。线下订单的同步用的 by zhoudong 2015-06-19;
			//若传入的参数中包含有推荐信息id则将此id存入数据库中以关联	荐购与订单信息
			if(form.getRecId()!=null && !"".equals(form.getRecId())){
				detail.setRecommend(new RRecommend());
				detail.getRecommend().setId(form.getRecId());
			}
			if(!"RMB".equals(price.getCurrency())){
				Map<String,Object> condition =new HashMap<String,Object>();
				condition.put("scurr", "RMB");
				condition.put("xcurr", price.getCurrency());
				OExchange exchange= this.oOrderService.getExchangeByCondition(condition);
			   //计算汇率转换后的价格
				if(exchange!=null){
					Double rate=Double.valueOf(exchange.getRate());	        
			        detail.setSalePrice(round(MathHelper.mul(price.getPrice(), rate)));
				}else{
					throw new Exception(Lang.getLanguage("Controller.Cart.createOrderDetail.noCurr.error",lang) + price.getCurrency());//"未找到对应的汇率RMB->
				}
			}else{        
		        detail.setSalePrice(round(price.getPrice()));	         
			}
			
			//计算税额
			if(form.getIsFreeTax()==1){
				detail.setTax(0d);
			}else{  
				detail.setTax(round(MathHelper.mul(detail.getSalePrice(),0.13)));
			}
			//最终售价
			detail.setSalePriceExtTax(MathHelper.add(detail.getSalePrice(),detail.getTax()));
			//结算价 默认等于最终售价
			detail.setSettledPrice(detail.getSalePriceExtTax());
			return detail;
		}catch(Exception e){
			//e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 实时更新购物车 价钱
	 * @param slist
	 * @param form
	 * @param lang
	 * @throws Exception
	 */
	private void createoorder(List<OOrderDetail> slist,OOrderDetailForm form,String lang)throws Exception{
		try{
			if(slist==null || slist.isEmpty())
				return;
			//查询用户是否是免税用户
			Map<String ,Object> Usercondition=new HashMap<String,Object>();
			if(slist!=null&&slist.size()>0){
				Usercondition.put("userId", slist.get(0).getUser().getId());
				List<CUserProp> props=this.customService.getUserPropList(Usercondition,"");
				if(props!=null && props.size()>0){						
					for(CUserProp prop:props){
						if(prop.getCode()!=null && "freetax".equals(prop.getCode())){
							if("2".equals(prop.getVal())){
								form.setIsFreeTax(1);//用户为免税用户
								break;
							}
						}
					}
				}
				for(Integer i=0;i<slist.size();i++){
					Double p=0d;
					String curr="RMB";
					if(slist.get(i).getCollection()!=null){
						curr=slist.get(i).getCollection().getCurrency();
						p=Double.valueOf(slist.get(i).getCollection().getPrice());
					}else{
						curr=slist.get(i).getPrice().getCurrency();
						p=slist.get(i).getPrice().getPrice();
					}
					
					
					if(!"RMB".equals(curr.toUpperCase())){
						Map<String,Object> condition =new HashMap<String,Object>();
						condition.put("scurr", "RMB");
						condition.put("xcurr", curr.toUpperCase());
						OExchange exchange= this.oOrderService.getExchangeByCondition(condition);
					   //计算汇率转换后的价格
						if(exchange!=null){
							Double rate=Double.valueOf(exchange.getRate());	        
							slist.get(i).setSalePrice(round(MathHelper.mul(p, rate)));
						}else{
							throw new Exception(Lang.getLanguage("Controller.Cart.createOrderDetail.noCurr.error",lang) + curr.toUpperCase());//"未找到对应的汇率RMB->
						}
					}else{        
						slist.get(i).setSalePrice(round(p));	         
					}
				
					//计算税额
					if(form.getIsFreeTax()==1){
						slist.get(i).setTax(0d);
					}else{  
						slist.get(i).setTax(round(MathHelper.mul(slist.get(i).getSalePrice(),0.13)));
					}
				//最终售价
					slist.get(i).setSalePriceExtTax(MathHelper.add(slist.get(i).getSalePrice(),slist.get(i).getTax()));
					//结算价 默认等于最终售价
					slist.get(i).setSettledPrice(slist.get(i).getSalePriceExtTax());
					this.oOrderService.updateOrderDetail(slist.get(i), slist.get(i).getId(), null);
				}
			}
		}catch(Exception e){
			//e.printStackTrace();
			throw e;
		}
	}
	
	private OOrderDetail createOrderDetail(CUser user,PCollection collection,OOrderDetailForm form,String lang)throws Exception{
		try{
			OOrderDetail detail=new OOrderDetail();
			detail.setUser(user);
			detail.setQuantity(form.getQuantity());//数量
			detail.setCreatedby(user.getName());
			detail.setCreatedon(new Date());
			detail.setUpdatedby(user.getName());
			detail.setUpdatedon(new Date());
			detail.setName(collection.getName());
			detail.setIp(form.getIp());
			detail.setCurrency("RMB");
			detail.setItemType(99);
			detail.setStatus(4);
			detail.setProductCode(collection.getCode());		
			detail.setCollection(collection);
			//若传入的参数中包含有推荐信息id则将此id存入数据库中以关联	荐购与订单信息
			if(form.getRecId()!=null && !"".equals(form.getRecId())){
				detail.setRecommend(new RRecommend());
				detail.getRecommend().setId(form.getRecId());
			}
			Double price=Double.valueOf(collection.getPrice());
			if(!"RMB".equalsIgnoreCase(collection.getCurrency())){
				Map<String,Object> condition =new HashMap<String,Object>();
				condition.put("scurr", "RMB");
				condition.put("xcurr", collection.getCurrency().toUpperCase());
				OExchange exchange= this.oOrderService.getExchangeByCondition(condition);
			   //计算汇率转换后的价格
				if(exchange!=null){
					Double rate=Double.valueOf(exchange.getRate());				
			        detail.setSalePrice(round(MathHelper.mul(price,rate)));
				}else{
					throw new Exception(Lang.getLanguage("Controller.Cart.createOrderDetail.noCurr.error",lang) + collection.getCurrency().toUpperCase());//"未找到指定的汇率RMB->" +
				}
			}else{        
		        detail.setSalePrice(round(price));	         
			}
			
			//计算税额
			if(form.getIsFreeTax()==1){
				detail.setTax(0d);
			}else{
				detail.setTax(round(MathHelper.mul(detail.getSalePrice(), 0.13)));
			}
			//最终售价
			detail.setSalePriceExtTax(MathHelper.add(detail.getSalePrice(), detail.getTax()));
			//结算价 默认等于最终售价
			detail.setSettledPrice(detail.getSalePriceExtTax());
			return detail;
		}catch(Exception e){
			//e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 保留两位小数 如:3.1415 >> 3.15
	 * @param value
	 * @return
	 */
	private Double round(Double value){
		BigDecimal bd = new BigDecimal(value);  
        bd = bd.setScale(2, BigDecimal.ROUND_CEILING);  
        return bd.doubleValue();   
	}
	/**
	 * 维护购物车中的商品数量
	 * @param number
	 */
	private void cartManager(HttpSession session,String userid, Integer number)throws Exception{
		try {	
//			if(session.getAttribute("totalincart")==null){
				Map<String,Object> condition =new HashMap<String,Object>();
				condition.put("status", 4);
				condition.put("userid", userid);
				condition.put("parentId","0");
				condition.put("orderNull", "1");
				session.setAttribute("totalincart", this.oOrderService.getOrderDetailCount(condition));
//			}else{
//				Integer capacity=Integer.valueOf(session.getAttribute("totalincart").toString());
//				session.setAttribute("totalincart",capacity+number);
//			}
		} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	@RequestMapping(value="/form/listDown")
	public ModelAndView listDown(HttpServletRequest request,HttpServletResponse response,HttpSession session, OOrderDetailForm form)throws Exception {
	/*	String forwardString="cart/listDown";
		Map<String,Object> model=new HashMap<String,Object>();
		try {
			"from LLicense a where a.user.institution.id in(select * from LLicense b left join b.user c left join c.institution d where c.level=2 and b.publications.id in(?????) and b.status=2)"
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		return null;
	}
}
