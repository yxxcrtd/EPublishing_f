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
//var sum = 0.00;
var alipay1=3;
//$("input[name='statusPrice']").each(function(){
//sum = add(sum,$(this).attr("value"));
//});
//$("#saleTotalPrice").html(sum);
//$("#saleTotalPrice").formatCurrency({symbol:'￥'});
if(alipay1=="${form.alipay}"&&alipay1=="${order.payType}"){
sayHello();
}});
function sayHello(){
window.location.href="${ctx}/pages/order/alipaySubmit?orderId=${order.id}";
} 
</script>
</head>

<body>
<jsp:include page="/pages/header/headerData" />
<div class="main">
	<div class="cartNav">
    	<ul>
            <li class="cartNavLi"><a href=""><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Complete_order" sessionKey="lang" /></a></li>
            <li><a href=""><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Settlement_Center" sessionKey="lang" /></a></li>
            <li><a href=""><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Title" sessionKey="lang" /></a></li>
        </ul>
    </div>
    <div class="cartCont h700">
    	<h1 class="f14 fb mb10"><ingenta-tag:LanguageTag key="Pages.User.MyTranLog.Table.Label.Alert" sessionKey="lang" /></h1>
        <p><ingenta-tag:LanguageTag key="Pages.User.MyTranLog.Table.Label.Success" sessionKey="lang" /></p>
		<p><span><ingenta-tag:LanguageTag key="Pages.User.MyTranLog.Table.Label.no" sessionKey="lang" />：${order.code}</span>      <span class="ml30"><ingenta-tag:LanguageTag key="Pages.User.MyTranLog.Table.Label.Pay" sessionKey="lang" />：<fmt:formatNumber value="${tPrice }" pattern="0.00"/>元</span></p>
        <p class="mt20 fb f14 mb5"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Odetails" sessionKey="lang" /></p>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="cartTable">
          <tr class="trTop">
            <td align="center"><ingenta-tag:LanguageTag key="Pages.publications.article.Label.BookName" sessionKey="lang" /></td>
            <td width="200" align="center">ISBN/ISSN</td>
            <td width="170" align="center">&nbsp;</td>
            <td width="140" align="center"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Subtotle" sessionKey="lang" /></td>
          </tr>
          <c:forEach items="${detailList }" var="detail" varStatus="status">
            <tr class="trBody">
            <td>
            <p><a href="${ctx}/pages/publications/form/article/${detail.price.publications.id}">${detail.name }</a></p>
			<p><c:if test='${detail.price.publications.author!=null&& detail.price.publications.author!=""}'>${detail.price.publications.author }</c:if></p>
            <p>d.price.publications.publisher.name</p>
			</td>
            <td align="center">
            <c:if test='${detail.price.publications.code!=null&&detail.price.publications.code!=""}'>${detail.price.publications.code}</c:if>
            <c:if test='${(detail.price.publications.eissn!=null)&&(detail.price.publications.code==null||detail.price.publications.code=="")}'>${detail.price.publications.eissn}</c:if>
            </td>
            <td align="center">
                    <c:if test="${detail.price.publications.cover==null||detail.price.publications.cover=='' }">
						<img onload="AutoResizeImage(46,63,this);" style="width:46px;height:63px"  src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/images/noimg.jpg" onerror="this.src='${ctx}/images/smallimg.jpg'" class="imgbor"/>
					</c:if>
					<c:if test="${detail.price.publications.cover!=null&&detail.price.publications.cover!='' }">
						<img onload="AutoResizeImage(46,63,this);"  style="width:46px;height:63px"  src="${ctx}/pages/publications/form/cover?id=${detail.price.publications.id}" onerror="this.src='${ctx}/images/smallimg.jpg'" class="imgbor"/>
					</c:if> 
            </td>
            <td align="center">￥<fmt:formatNumber value="${detail.salePriceExtTax }" pattern="0.00"/></td>
          </tr>
          </c:forEach>
        </table>
        <div class="mt20">
            <p class="f14 fb mb10"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Tips.Contact" sessionKey="lang" /></p>
                   <table width="100%" border="0" cellspacing="0" cellpadding="0" class="regTable mt10">
                   <c:set var="flag">0</c:set>
		         <c:forEach items="${propList}" var="c" varStatus="status">
		           <c:if test="${flag%2==0 }"><tr></c:if>
            	   <c:if test="${c.display!='select'&&c.code!='freetax'&&c.code!='businessCode'&&c.code!='notes'&&c.code!='emailCheck' && c.code!='email' }">
						<td width="80" align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>： </td>
						<td>${form.values[c.code]}</td>
            		<c:set var="flag">${flag+1}</c:set>
            	   </c:if>
            	   <c:if test="${c.display=='select' && c.svalue=='countryMap' }">
            			<td width="80" align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>： </td>
            			<td>
			        		<c:forEach items="${form.countryMap}" var="p">
			        			<c:if test="${p.key==form.values[c.code]}"> ${p.value}</c:if>
							</c:forEach>
			       		</td>	
			       	<c:set var="flag">${flag+1}</c:set>
            	    </c:if>
            	    <c:if test="${c.display=='select' && c.svalue=='provinceMap' }">
            			<td width="80" align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>： </td>
            			<td>
			        		<c:forEach items="${form.provinceMap}" var="p">
			        			<c:if test="${p.key==form.values[c.code]}"> ${p.value}</c:if>
							</c:forEach>
			       		</td>	
			       	<c:set var="flag">${flag+1}</c:set>
            	    </c:if>
            	    <c:if test="${flag%2==0||status.index+1==fn:length(list) }"></tr></c:if>
			     </c:forEach>
			     <c:forEach items="${propList}" var="c" varStatus="index">
			        <c:if test="${flag%2==0 }"><tr></c:if>
				    <c:if test="${c.display!='select' && c.code=='notes' }">
					 <tr>
						<td width="80" align="right" valign="top"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：</td>
						<td>${form.values[c.code]}</td>
					 </tr>
				    </c:if> 
				   <c:if test="${flag%2==0||status.index+1==fn:length(list) }"></tr></c:if>        	
			    </c:forEach>
        </table>
            <p class="f14 fb mb10 mt20"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Payment" sessionKey="lang" /></p>
            <p>
            <c:if test="${order.payType==1 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Payment.Stored" sessionKey="lang" /></c:if>
			<c:if test="${order.payType==2 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Payment.OffLine" sessionKey="lang" /></c:if>
			<c:if test="${order.payType==3 }"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.PayMent.Alipay" sessionKey="lang" /></c:if>
			</p>
        </div>
    </div>
    
   
</div>
		
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>
