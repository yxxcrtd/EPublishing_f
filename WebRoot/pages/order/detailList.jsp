<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
<%@ include file="/common/tools.jsp"%>
<%@ include file="/common/ico.jsp"%>
<script type="text/javascript">
			   
			$(document).ready(function(){
			/* alert('${form.alipay}');
			alert('${order.payType}'); */
				var sum = 0.00;
				var alipay1=3;
				$("input[name='statusPrice']").each(function(){
	               sum = add(sum,$(this).attr("value"));
            	});
            	$("#saleTotalPrice").html(sum);
            	$("#saleTotalPrice").formatCurrency({symbol:'￥'});
            	if(alipay1=="${form.alipay}"&&alipay1=="${order.payType}"){
            	sayHello();
            	}
			});
			function sayHello(){  
              // alert("Hello");  
               window.location.href="${ctx}/pages/order/alipaySubmit?orderId=${order.id}";
             } 
			
		</script>
</head>

<body>
<!--以下top state -->
<jsp:include page="/pages/header/headerData" />
<!--以上top end -->
<div class="main personMain">
   <%--      <c:if test="${level!=2 }">
	    	<jsp:include page="/pages/menu?mid=Expertuser&type=5" flush="true" />
	    </c:if>
	    <c:if test="${level==2 }">
	    	<jsp:include page="/pages/menu?mid=Expertuser&type=1" flush="true" />
	    </c:if> --%>
	    <div class="cartNav">
    	<ul>
            <li class="cartNavLi"><a href="javascript:void(0)"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Complete_order" sessionKey="lang" /></a></li>
            <li ><a href="${ctx}/pages/cart/form/checkout"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Settlement_Center" sessionKey="lang" /></a></li>
            <li ><a href="${ctx}/pages/cart/form/list"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Title" sessionKey="lang" /></a></li>
        </ul>
</div>
    <div class="perRight" style="width:100%;">
    	<h1><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Odetails" sessionKey="lang" /></h1>
         <div class="borderDiv noTop">
                    <p class="blockP">
                        <span class="w100 tr"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.OCode" sessionKey="lang" />：</span>
                        <span class="w200">${order.code}</span>
                        <span class="w100 tr"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Date" sessionKey="lang" />：</span>
                        <span class="w200"><fmt:formatDate value="${order.createdon }" pattern="yyyy-MM-dd HH:mm"/></span>
                    </p>
                    <p class="blockP">
                        <span class="w100 tr"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.TotalPrice" sessionKey="lang" />：</span>
                        <span class="w200" id="saleTotalPrice"></span>
                        <span class="w100 tr"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Payment" sessionKey="lang" />：</span>
                        <span class="w200">
							<c:if test="${order.payType==1 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Payment.Stored" sessionKey="lang" /></c:if>
							<c:if test="${order.payType==2 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Payment.OffLine" sessionKey="lang" /></c:if>
							<c:if test="${order.payType==3 }"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.PayMent.Alipay" sessionKey="lang" /></c:if>
						</span>
                    </p>
                    <p class="blockP">
                        <span class="w100 tr"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status" sessionKey="lang" />：</span>
                        <span class="w200">
							<c:if test="${order.status==1 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status.Option1" sessionKey="lang" /></c:if>
							<c:if test="${order.status==2 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status.Option2" sessionKey="lang" /> </c:if>
							<c:if test="${order.status==3 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status.Option3" sessionKey="lang" /></c:if>
						</span>
				<c:if test="${order.payType==3&&(order.status==2||order.status==1) &&order.tradeNo==null&&detailSendStatus!=2&&detailSendStatus!=10}">
					 <p class="blockP">
					  <tr>
		    				<td align="center"><a   href="${ctx}/pages/order/alipaySubmit?orderId=${order.id}"> <span class="w100 tr">支付</span></a> 
		    				</td>
		    		</tr>
		    		</p>
		    	</c:if> 
                    </p>
                </div>
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="cartTable">
                  <tr class="trTop">
                    <td width="360" align="center"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Pub" sessionKey="lang" /></td>
                    <td width="120" align="center"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Type" sessionKey="lang" /></td>
                    <td width="80" align="center"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Count" sessionKey="lang" /></td>
                    <td width="100" align="center"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Price" sessionKey="lang" /></td>
                    <td align="center"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status" sessionKey="lang" /></td>
                  </tr>
                  <c:forEach items="${detailList }" var="d" varStatus="index">
                 	 <tr class="trBody">
				        <c:set var="tdCss"><c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2!=0 }">bbodytd</c:if></c:set>
					        <td align="left" class="pl10">
						        <c:if test="${d.itemType==99}">
						        	<a href="${ctx }/pages/collection/form/list?id=${d.collection.id}">${d.name }</a>
						        	<%-- ${d.name } --%><br />
						        	By ${d.price.publications.author }<br />
						        	${d.price.publications.publisher.name }
						        </c:if>
						        <c:if test="${d.itemType!=99 }">
						        <a href="${ctx}/pages/publications/form/article/${d.price.publications.id}">${d.name}</a>
						        <%-- ${d.name } --%><br />
						        By ${d.price.publications.author }<br />
						        ${d.price.publications.publisher.name }
						        </c:if>
					        </td>
					        <td align="center">
					        	<c:if test="${d.itemType==1 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" /></c:if>
					        	<c:if test="${d.itemType==2 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></c:if>
					        	<c:if test="${d.itemType==3 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option3" sessionKey="lang" /></c:if>
					        	<c:if test="${d.itemType==4 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option4" sessionKey="lang" /></c:if>
					        	<c:if test="${d.itemType==5 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option5" sessionKey="lang" /></c:if>
					        	<c:if test="${d.itemType==6 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option6" sessionKey="lang" /></c:if>
					        	<c:if test="${d.itemType==7 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option7" sessionKey="lang" /></c:if>
					        	
					        	<c:if test="${d.itemType==99 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option99" sessionKey="lang" /></c:if>
					        </td>
					        <td  align="center">${d.quantity }</td>
					        <td  align="center">￥<fmt:formatNumber value="${d.salePriceExtTax }" pattern="0.00"/></td>
					        <td  align="center">
					        	<c:if test="${d.status!=99 }"><input type="hidden" name="statusPrice" value="${d.salePriceExtTax }"/></c:if>
								<c:if test="${d.status==1 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Detail.Status.Option1" sessionKey="lang" /></c:if>
			        			<c:if test="${d.status==2 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Detail.Status.Option2" sessionKey="lang" /></c:if>
			        			<c:if test="${d.status==3 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Detail.Status.Option3" sessionKey="lang" /></c:if>
			        			<c:if test="${d.status==4 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Detail.Status.Option4" sessionKey="lang" /></c:if>
			        			<c:if test="${d.status==10 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Detail.Status.Option10" sessionKey="lang" /></c:if>
			        			<c:if test="${d.status==99 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Detail.Status.Option5" sessionKey="lang" /></c:if>
							</td>
				        </tr>
			        </c:forEach>
			        <tr style="border-left-style: hidden;border-right-style: hidden;border-bottom-style: hidden;">
			                <td colspan="5" class="f_tda">
			                <ingenta-tag-v3:SplitTag first_ico="${ctx }/images/ico_left1.gif"
			                	last_ico="${ctx }/images/ico_right1.gif" 
			                	prev_ico="${ctx }/images/ico_left.gif" 
			                	next_ico="${ctx }/images/ico_right.gif" 
			                	method="post"
			                	formName="form"
			                	pageCount="${form.pageCount}" 
			                	count="${form.count}" 
			                	page="${form.curpage}" 
			                	url="${form.url}" 
			                	i18n="${sessionScope.lang}"/>
			                </td>
			               </tr>
                </table>
            <div class="cartList">
    	<p class="f14 fb mb10"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Tips.Contact1" sessionKey="lang" /></p>
        <table width="98%" border="0" cellspacing="0" cellpadding="0" class="regTable mt10">
        <c:set var="flag">0</c:set>
		  <c:forEach items="${propList}" var="c" varStatus="index">
            	<c:if test="${c.display!='select'&&c.code!='freetax'&&c.code!='businessCode'&&c.code!='notes' }">            		
					<c:if test="${c.code!='emailCheck' && c.code!='email'  }">
					<input type="hidden" name="typePropIds" value="${c.id}"/>
					<c:if test="${flag==0 }"><tr></c:if>					
						<td width="160" style="text-align:right;"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>： <c:if test="${c.must!=1}"> </c:if></td>
						<td width="300"><input type="text" id="${c.code}" name="propsValue" value="${form.values[c.code]}" style="vertical-align:middle; border: 0;"/><!-- <span style="color: #900;margin:0 4px;">*</span> --></td>
					<c:if test="${flag==1 }"></tr></c:if>
            		</c:if>
            		<c:set var="flag">${1-flag}</c:set>
            	</c:if>
            	<c:if test="${c.display=='select' && c.svalue=='countryMap' }">
            		<input type="hidden" name="typePropIds" value="${c.id}"/>
            		<c:if test="${flag==0 }"><tr></c:if>		
            			<td width="160" align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>： <c:if test="${c.must!=1}"></c:if></td>
            			<td width="300">
            				<div style="width: 300px; overflow: hidden; background-color: #fff;">
			            		<select id="${c.code}" name="propsValue" style="margin-top:3px; border:0; width:320px; background-color: #fff;">
            				</div>
			        		<option value="0"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Label.Select"/></option>
			        		<c:forEach items="${form.countryMap}" var="p">
			        			<option value="${p.key}" <c:if test="${p.key==form.values[c.code]}"> selected</c:if>>${p.value}</option>
							</c:forEach>	
			       			</select>
			       			<!-- <span style="color: #900;margin:0 4px;">*</span> -->
			       		</td>
			       	<c:if test="${flag==1 }"></tr></c:if>		
			       	<c:set var="flag">${1-flag}</c:set>
            	</c:if>
            	<c:if test="${c.display=='select' && c.svalue=='provinceMap' }">
            		<input type="hidden" name="typePropIds" value="${c.id}"/>
            		<c:if test="${flag==0 }"><tr></c:if>		
            			<td width="80" align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>： <c:if test="${c.must!=1}"></c:if></td>
            			<td>
		            		<select id="${c.code}" name="propsValue" style="margin-top:3px; border:0;">
			        		<option value="0"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Label.Select"/></option>
			        		<c:forEach items="${form.provinceMap}" var="p">
			        			<option value="${p.key}" <c:if test="${p.key==form.values[c.code]}"> selected</c:if>>${p.value}</option>
							</c:forEach>	
			       			</select>
			       			<!-- <span style="color: #900;margin:0 4px;">*</span> -->
			       		</td>
			       	<c:if test="${flag==1 }"></tr></c:if>		
			       	<c:set var="flag">${1-flag}</c:set>
            	</c:if>
            	
			</c:forEach>	
			<c:if test="${flag==1}"><td>&nbsp;</td></tr></c:if>
			<c:forEach items="${propList}" var="c" varStatus="index">
				<c:if test="${c.display!='select' && c.code=='notes' }">
					<input type="hidden" name="typePropIds" value="${c.id}"/>
					<tr>
						<td width="80" align="right" valign="top"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>： <c:if test="${c.must!=1}"> <span>*</span></c:if></td>
						<td colspan="3"><textarea class="textare" id="${c.code}"  style=" border:0;" name="propsValue">${form.values[c.code]}</textarea></td>
					</tr>
				</c:if>         	
			</c:forEach>
        </table>
    </div>
     <div class="cartList">
    	<p class="f14 fb mb10"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Payment" sessionKey="lang" /></p>
    	
	            	<c:if test="${form.paytype==1}">
				       <div class="borBot">
				        	<p class="mb5"><input type="radio" value="1" name="paytype" class="vm mr5" <c:if test="${form.paytype==1 }">checked="checked"</c:if>/>
				        	<ingenta-tag:LanguageTag key="Pages.Cart.Lable.PayMent.Deposit" sessionKey="lang" /></p>
				            <p class="ml20 mb5"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Label.balance"/>：<span>￥<fmt:formatNumber value="${form.balance}" pattern="0.00"/></span></p>
				            <p class="ml20 mb15"><ingenta-tag:LanguageTag key="Pages.Cart.Info.Deposit" sessionKey="lang" /></p>
				        </div>          		
	            	</c:if>
	            	<c:if test="${form.paytype==2}">
					    <div class="borBot">
				        	<p class="mb10 mt10"><input type="radio" value="2" name="paytype" <c:if test="${form.paytype==2||form.paytype==null }">checked="checked"</c:if>/> 
					        	<ingenta-tag:LanguageTag key="Pages.Cart.Info.Check" sessionKey="lang" /></p>
				        </div>          		
	            	</c:if>
	            	<c:if test="${form.paytype==3}">
					     <div class="borBot">
				        	<p class="mb10 mt10"><input type="radio" value="3" name="paytype" <c:if test="${form.paytype!=2&&form.paytype==3}">checked="checked"</c:if>/> 
					        	<ingenta-tag:LanguageTag key="Pages.Cart.Lable.PayMent.Alipay" sessionKey="lang" />
					        </p>
				        </div>
	            	</c:if>
    	</div>
    </div>
</div>
  
   <!--以下 提交查询Form 开始-->
		<form:form action="${form.url}" method="post" modelAttribute="form" commandName="form" name="formList" id="formList">
			<form:hidden path="searchsType" id="type1"/>
			<form:hidden path="searchValue" id="searchValue1"/>
			<form:hidden path="pubType" id="pubType1"/>
			<form:hidden path="language" id="language1"/>
			<form:hidden path="publisher" id="publisher1"/>
			<form:hidden path="pubDate" id="pubDate1"/>
			<form:hidden path="taxonomy" id="taxonomy1"/>
			<form:hidden path="taxonomyEn" id="taxonomyEn1"/>
			<form:hidden path="searchOrder" id="order1"/>
			<form:hidden path="lcense" id="lcense1"/>
			
			<form:hidden path="code" id="code1"/>
			<form:hidden path="pCode" id="pCode1"/>
			<form:hidden path="publisherId" id="publisherId1"/>
			<form:hidden path="subParentId" id="subParentId1"/>
			<form:hidden path="parentTaxonomy" id="parentTaxonomy1"/>
			<form:hidden path="parentTaxonomyEn" id="parentTaxonomyEn1"/>
		</form:form>
		<!--以上 提交查询Form 结束-->

		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
</body>
</html>
