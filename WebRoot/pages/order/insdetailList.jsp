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
	<jsp:include page="/pages/menu?mid=Expertuser&type=2" flush="true" />
    <div class="perRight">
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
