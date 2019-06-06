<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
<%@ include file="/common/tools.jsp"%>
<%@ include file="/common/ico.jsp"%>
<script language="javascript">
function apply(){	
	/* var email = document.getElementById("email").value;
	if(email.replace(/\s+/g,"")==""){
		art.dialog.tips("<ingenta-tag:LanguageTag key='Page.Order.Checkout.Prompt.Check.Email' sessionKey='lang' />",1,'error');
		document.getElementById("email").focus();
		return;
	}else{
		
	  	var myreg = /^([a-zA-Z0-9]+[_|\-|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
           if(!myreg.test(email)){
                art.dialog.tips("<ingenta-tag:LanguageTag key='Page.Order.Checkout.Prompt.Check.Email2' sessionKey='lang' />",1,'error');
                myreg.focus();
             	return false;
        	}
        document.getElementById("emailCheck").value=email;
	} */	
	if(document.getElementById("country")!=null&&(document.getElementById("country").value=="" || document.getElementById("country").value==0)){
		art.dialog.tips("<ingenta-tag:LanguageTag key='Page.Order.Checkout.Prompt.Check.Country' sessionKey='lang' />",1,'error');
		document.getElementById("country").focus();
		return;
	}
	document.getElementById("form").submit();
}

</script>
</head>+

<body>
<!--以下top state -->
<jsp:include page="/pages/header/headerData" />
<!--以上top end -->
<div class="main">
<form:form id="form" commandName="form" action="${ctx }/pages/order/form/submit?alipay=3">
	<div class="cartNav">
    	<ul>
            <li><a href="javascript:void(0)"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Complete_order" sessionKey="lang" /></a></li>
            <li class="cartNavLi"><a href="${ctx}/pages/cart/form/checkout"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Settlement_Center" sessionKey="lang" /></a></li>
            <li><a href="${ctx}/pages/cart/form/list"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Title" sessionKey="lang" /></a></li>
        </ul>
    </div>
    
    <c:set var="isPack">${1==2 }</c:set>
    <c:set var="isSpecial">${1==2 }</c:set>
    <c:forEach items="${listdetails }" var="l" varStatus="index">
	    　  	<c:if test='${l.price.code=="special_price" }'>
	   		<c:set var="isSpecial">${1==1 }</c:set> 
	   	</c:if>
	   	<c:if test='${l.price.category==2 }'>
	   		<c:set var="isPack">${1==1 }</c:set> 
	   	</c:if>
   </c:forEach>
   <c:if test="${isSpecial }">
     <div class="note">
    	<img src="${ctx }/images/ico/ico_20.png" class="vm" /> <ingenta-tag:LanguageTag key="Pages.Cart.Lable.Alert" sessionKey="lang" />
    </div>
   </c:if>
    <div class="cartCont oh">
    	<p class="oh mb10">
        	<span class="fl"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.myCart" sessionKey="lang" /> <span><c:if test="${sessionScope.totalincart!=null}">[${sessionScope.totalincart}]</c:if></span> <ingenta-tag:LanguageTag key="Pages.Cart.Lable.myCart1" sessionKey="lang" /></span>
            <span class="fr"><a href="${ctx}/pages/publications/form/list">>> <ingenta-tag:LanguageTag key="Pages.Cart.Lable.Continue.Shopping" sessionKey="lang" /></a></span>
        </p>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="cartTable">
          <tr class="trTop">
            <td align="center"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Name" sessionKey="lang" /></td>
            <td width="200" align="center">ISBN/ISSN</td>
            <td width="170" align="center">&nbsp;</td>
            <td width="140" align="center"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Subtotle" sessionKey="lang" /></td>
            <!-- <td width="140" align="center">操作</td> -->
          </tr>
           <c:forEach items="${listdetails }" var="l" varStatus="index">
          <tr class="trBody">
            <c:if test="${l.collection==null }">
            <td><p><a href="${ctx}/pages/publications/form/article/${l.price.publications.id}">${l.name}</a></p>
                <c:if test='${l.price.publications.author!=null&& l.price.publications.author!=""}'>
				<p>By  ${l.price.publications.author }  </p>
				</c:if>
                <p>
                <c:if test='${l.price.publications.publisher.name!=null&&l.price.publications.publisher.name!=""}'>
                ${l.price.publications.publisher.name} 
                </c:if>
                <c:if test='${l.price.publications.pubDate!=null&&l.price.publications.pubDate!="" }'>
                (${fn:substring(l.price.publications.pubDate,0,4) })
                </c:if>
                </p>
			</td>
            </c:if>  
            <c:if test="${l.collection!=null }">
            <td><p><a href="${ctx}/pages/collection/form/list?id=${l.collection.id}">${l.name }</a></p>
            <c:if test='${ l.price.publications.author!=null&&l.price.publications.author!=""}'>
				<p>By  ${l.price.publications.author }  </p>
		    </c:if>
                <p>
                <c:if test='${l.price.publications.publisher.name!=null&&l.price.publications.publisher.name!=""}'>
                ${l.price.publications.publisher.name} 
                </c:if>
                <c:if test='${l.price.publications.pubDate!=null&&l.price.publications.pubDate!="" }'>
                (${fn:substring(l.price.publications.pubDate,0,4) })
                </c:if>
                </p>
			</td>
            </c:if>
            <td align="center">${l.price.publications.code}</td>
            <td align="center">
                    <c:if test="${l.price.publications.cover==null||l.price.publications.cover=='' }">
						<img onload="AutoResizeImage(46,63,this);" style="width:46px;height:63px"  src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/images/noimg.jpg" onerror="this.src='${ctx}/images/smallimg.jpg'" class="imgbor"/>
					</c:if>
					<c:if test="${l.price.publications.cover!=null&&l.price.publications.cover!='' }">
						<img onload="AutoResizeImage(46,63,this);"  style="width:46px;height:63px"  src="${ctx}/pages/publications/form/cover?id=${l.price.publications.id}" onerror="this.src='${ctx}/images/smallimg.jpg'" class="imgbor"/>
					</c:if> 
			</td>
            <td align="center">
            	<c:if test='${l.price.code=="special_price" }'>--</c:if>        
            	<c:if test='${l.price.code!="special_price" }'>￥${l.salePriceExtTax}</c:if>
            </td>
           <%--  <td align="center"><a onclick="deleteItem('${l.price.publications.id }','${l.id}')" class="a_cancel"><ingenta-tag:LanguageTag key="Global.Button.Delete" sessionKey="lang" /></a></td> --%>
          </tr>
          </c:forEach>
        </table>
		<p class="tr f14 mt10"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Totle" sessionKey="lang" />：&nbsp;<span><c:if test="${isSpecial&&form.totalPrice==0}">--</c:if><c:if test="${!isSpecial||form.totalPrice>0}">￥${form.totalPrice }</c:if></span></p>
    </div>
    <div class="cartList">
    	<p class="f14 fb mb10"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Tips.Contact" sessionKey="lang" /></p>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="regTable mt10">
        <c:set var="flag">0</c:set>
		  <c:forEach items="${list}" var="c" varStatus="index">
            	<c:if test="${c.display!='select'&&c.code!='freetax'&&c.code!='businessCode'&&c.code!='notes' }">            		
            		<%-- 页面不允许修改邮箱 所有不显示 邮箱	            		
					<c:if test="${c.code=='emailCheck' || c.code=='email' }">
					<input type="hidden" id="${c.code}" name="propsValue" value="${form.values[c.code]}"/>
					</c:if> --%>
					<c:if test="${c.code!='emailCheck' && c.code!='email'  }">
					<input type="hidden" name="typePropIds" value="${c.id}"/>
					<c:if test="${flag==0 }"><tr></c:if>					
						<td style="text-align:right;"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>： <c:if test="${c.must!=1}"> </c:if></td>
						<td><input type="text" id="${c.code}" name="propsValue" value="${form.values[c.code]}" style="vertical-align:middle;"/><!-- <span style="color: #900;margin:0 4px;">*</span> --></td>
					<c:if test="${flag==1 }"></tr></c:if>
            		</c:if>
            		<c:set var="flag">${1-flag}</c:set>
            	</c:if>
            	<c:if test="${c.display=='select' && c.svalue=='countryMap' }">
            		<input type="hidden" name="typePropIds" value="${c.id}"/>
            		<c:if test="${flag==0 }"><tr></c:if>		
            			<td width="80" align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>： <c:if test="${c.must!=1}"></c:if></td>
            			<td>
		            		<select id="${c.code}" name="propsValue" style="margin-top:3px; ">
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
		            		<select id="${c.code}" name="propsValue" style="margin-top:3px; ">
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
			<c:forEach items="${list}" var="c" varStatus="index">
				<c:if test="${c.display!='select' && c.code=='notes' }">
					<input type="hidden" name="typePropIds" value="${c.id}"/>
					<tr>
						<td width="80" align="right" valign="top"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>： <c:if test="${c.must!=1}"> <span>*</span></c:if></td>
						<td colspan="3"><textarea class="textare" id="${c.code}" name="propsValue">${form.values[c.code]}</textarea></td>
					</tr>
				</c:if>         	
			</c:forEach>
        </table>
    </div>
    <div class="cartList">
    	<p class="f14 fb mb10"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Payment" sessionKey="lang" /></p>
    	
    	<c:forEach items="${form.payments }" var="p" varStatus="index">
	            	<c:if test="${p.payType==1&&!isPack}">
				       <div class="borBot">
				        	<p class="mb5"><input type="radio" value="1" name="paytype" class="vm mr5" <c:if test="${form.paytype==1 }">checked="checked"</c:if>/>
				        	<ingenta-tag:LanguageTag key="Pages.Cart.Lable.PayMent.Deposit" sessionKey="lang" /></p>
				            <p class="ml20 mb5"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Label.balance"/>：<span>￥<fmt:formatNumber value="${form.balance}" pattern="0.00"/></span></p>
				            <p class="ml20 mb15"><ingenta-tag:LanguageTag key="Pages.Cart.Info.Deposit" sessionKey="lang" /></p>
				        </div>          		
	            	</c:if>
	            	<c:if test="${p.payType==2}">
					    <div class="borBot">
				        	<p class="mb10 mt10"><input type="radio" value="2" name="paytype" <c:if test="${form.paytype==2||form.paytype==null }">checked="checked"</c:if>/> 
					        	<ingenta-tag:LanguageTag key="Pages.Cart.Info.Check" sessionKey="lang" /></p>
				        </div>          		
	            	</c:if>
	            	<c:if test="${p.payType==3&&!isPack}">
					     <div class="borBot">
				        	<p class="mb10 mt10"><input type="radio" value="3" name="paytype" <c:if test="${form.paytype!=2&&form.paytype==3}">checked="checked"</c:if>/> 
					        	<ingenta-tag:LanguageTag key="Pages.Cart.Lable.PayMent.Alipay" sessionKey="lang" /></p>
				        </div>
	            	</c:if>
	    </c:forEach>
    	
    	
        <c:if test="${form.paytype!=2}">
        <p class="tr h50 mt20">
        	<span class="mr5"><a href="${ctx}/pages/cart/form/list?paytype=${form.paytype}" class="blueB">&lt;&lt;<ingenta-tag:LanguageTag key="Page.Order.Checkout.Button.Return" sessionKey="lang" /></a></span>
            <span><a href="javascript:void(0)" class="orgingA" onclick="apply();"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Button.Ok" sessionKey="lang" /></a></span>
        </p>
        </c:if>
        <c:if test="${form.paytype==2}">
        <p class="tr h50 mt20">
        	<span class="mr5"><a href="${ctx}/pages/cart/form/list?paytype=${form.paytype}" class="blueB">&lt;&lt;<ingenta-tag:LanguageTag key="Page.Order.Checkout.Button.Return" sessionKey="lang" /></a></span>
            <span><a href="javascript:void(0)" class="orgingA" onclick="apply();"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Button.Ok" sessionKey="lang" /></a></span>
        </p>
        </c:if>
        <p><ingenta-tag:LanguageTag key="Pages.Cart.Info.Tips1" sessionKey="lang" /></p>
        <p><ingenta-tag:LanguageTag key="Pages.Cart.Info.Tips2" sessionKey="lang" /></p>
        <p><ingenta-tag:LanguageTag key="Pages.Cart.Info.Tips3" sessionKey="lang" /></p>
    </div>
  		<%-- <form:hidden path="paytype" /> 
		  <input type="hidden" name="${form_token_key}" value="${token}"/>
		</form:form> --%>
		<input type="hidden" name="${form_token_key}" value="${token}"/>
		</form:form>
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
