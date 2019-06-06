<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
		<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
		<%@ include file="/common/tools.jsp"%>
	<%@ include file="/common/ico.jsp"%>
	<script type="text/javascript">
			$(document).ready(function(){
				var sum = 0.00;
				$("input[name='statusPrice']").each(function(){
	               sum = add(sum,$(this).attr("value"));
            	});
            	$("#saleTotalPrice").html(sum);
            	$("#saleTotalPrice").formatCurrency({symbol:'￥'});
			});
		</script>
	</head>
	<body>
	<div class="big">
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--定义01 mainContainer 内容区开始-->
		<div class="main">
		    <div class="tablepage">
		    <h1><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Odetails" sessionKey="lang" /></h1>
		    
		    	<div class="page_table">
		    	<table cellspacing="0" cellpadding="0" style="width:400px;">
		    	<tr>
		    		<td class="abodytd" style="text-align:right;padding-right:10px;"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.OCode" sessionKey="lang" /></td>
		    		<td class="abodytd" style="text-align:left;padding-left:10px;">${order.code}
		    		
		    		</td>
		    	</tr>
		    	<tr>
		    		<td class="bbodytd" style="text-align:right;padding-right:10px;"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Date" sessionKey="lang" /></td>
		    		<td class="bbodytd" style="text-align:left;padding-left:10px;"><fmt:formatDate value="${order.createdon }" pattern="yyyy-MM-dd HH:mm"/></td>
		    	</tr>
		    	<tr>
		    		<td class="abodytd" style="text-align:right;padding-right:10px;"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.TotalPrice" sessionKey="lang" /></td>
		    		<td id="saleTotalPrice" class="abodytd" style="text-align:left;padding-left:10px;"></td>
		    	</tr>
		    	<tr>
		    		<td class="bbodytd" style="text-align:right;padding-right:10px;"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Payment" sessionKey="lang" /></td>
		    		<td class="bbodytd" style="text-align:left;padding-left:10px;">
		    			<c:if test="${order.payType==1 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Payment.Stored" sessionKey="lang" /></c:if>
						<c:if test="${order.payType==2 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Payment.OffLine" sessionKey="lang" /></c:if>
						<c:if test="${order.payType==3 }"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.PayMent.Alipay" sessionKey="lang" /></c:if>
					</td>
		    	</tr>
		    	<tr>
		    		<td class="abodytd" style="text-align:right;padding-right:10px;"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status" sessionKey="lang" /></td>
		    		<td class="abodytd" style="text-align:left;padding-left:10px;">
		    			<c:if test="${order.status==1 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status.Option1" sessionKey="lang" /></c:if>
						<c:if test="${order.status==2 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status.Option2" sessionKey="lang" /> </c:if>
						<c:if test="${order.status==3 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status.Option3" sessionKey="lang" /></c:if>
					</td>
		    	</tr>	
		    	<c:if test="${message!=null}">
		    	<tr>
		    	<td></td>
		    	<td align="center"><p class="p_mail">${message}</p></td>
		    	</tr></c:if>	    	
		    	</table>
		    	</div>
		        
		        <div class="page_table">
		        	<table cellspacing="0" cellpadding="0" style="width:100%;">
		            <thead>
		              <tr>
		              	<td class="atdtop" style="width:40%"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Pub" sessionKey="lang" /></td>
				        <td class="atdtop" style="width:10%"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Type" sessionKey="lang" /></td>
				        <td class="atdtop" style="width:10%"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Count" sessionKey="lang" /></td>
				        <td class="atdtop" style="width:10%"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Price" sessionKey="lang" /></td>
				        <td class="atdtop" style="width:15%"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status" sessionKey="lang" /></td>						        						        	
		              </tr>
		             </thead>
		             <tbody>
		             <c:forEach items="${detailList }" var="d" varStatus="index">
				        <c:set var="tdCss"><c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2!=0 }">bbodytd</c:if></c:set>
					        <td class="${tdCss}">
						        <c:if test="${d.itemType==99}">
						        	<a href="${ctx }/pages/collection/form/list?id=${d.collection.id}">${d.name }</a>
						        </c:if>
						        <c:if test="${d.itemType!=99 }">
						        <a href="${ctx}/pages/publications/form/article/${d.price.publications.id}">${d.name}</a>
						        </c:if>
					        </td>
					        <td class="${tdCss}">
					        	<c:if test="${d.itemType==1 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" /></c:if>
					        	<c:if test="${d.itemType==2 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></c:if>
					        	<c:if test="${d.itemType==3 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option3" sessionKey="lang" /></c:if>
					        	<c:if test="${d.itemType==4 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option4" sessionKey="lang" /></c:if>
					        	<c:if test="${d.itemType==5 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option5" sessionKey="lang" /></c:if>
					        	<c:if test="${d.itemType==6 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option6" sessionKey="lang" /></c:if>
					        	<c:if test="${d.itemType==7 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option7" sessionKey="lang" /></c:if>
					        	
					        	<c:if test="${d.itemType==99 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option99" sessionKey="lang" /></c:if>
					        </td>
					        <td class="${tdCss}">${d.quantity }</td>
					        <td class="${tdCss}">￥<fmt:formatNumber value="${d.salePriceExtTax }" pattern=".00"/></td>
					        <td class="${tdCss}">
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
					 </tbody>		             
				</table>
		        </div>	
			</div>
	    <!--左侧内容结束-->
	    <!--右侧菜单开始 -->
	    <jsp:include page="/pages/menu?mid=order" flush="true"/>
	    <!--右侧菜单结束-->
	    </div>
	    
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>	
		
		</div>
	</body>
</html>
