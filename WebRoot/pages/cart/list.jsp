<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.io.File"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
<%@ include file="/common/tools.jsp"%>
<%@ include file="/common/ico.jsp"%>
</head>
<script language="javascript">
	function deleteItem(pid,item){
	art.dialog({
                   content:"<ingenta-tag:LanguageTag key='Pages.Cart.Lable.Delete' sessionKey='lang' /> ?",
                   okVal:'<ingenta-tag:LanguageTag key="Pages.Cart.Lable.Button1" sessionKey="lang" />',  
                   height:160,
                   width:300,
                   ok: function () {
                   		$.ajax({
						  			type : "POST",  
									url: "${ctx}/pages/cart/form/delete",
									data: { id:item,
										   r_ : new Date().getTime()
								},
								success : function(data) {  
								    var s = data.split(":");
								    if(s[0]=="success"){
								    	art.dialog.tips(s[1],2);//location.reload();			    	
								    }else{
								    	art.dialog.tips(s[1],2,'error');	
								    }
								},  
								error : function(data) {  
								    art.dialog.tips(data,2,'error');
								}  
							});
                   
                    },
	               cancelVal:'<ingenta-tag:LanguageTag key="Pages.Cart.Lable.Button2" sessionKey="lang" />',cancel:true,
                   lock:true
                   });
	}
	function deleteAll(){
		       art.dialog({
                   content:"<ingenta-tag:LanguageTag key='Pages.Cart.Lable.Delete' sessionKey='lang' /> ?",
                   okVal:'<ingenta-tag:LanguageTag key="Pages.Cart.Lable.Button1" sessionKey="lang" />',    
                   ok: function () { 
                   var detailIds="";
	$("input[name='detailIds']").each(function(){
	detailIds+=this.value+",";
	});
		$.ajax({
  			type : "POST",  
			url: "${ctx}/pages/cart/form/deleteAll",
			data: { 
		   detailIds  : detailIds,
				   r_ : new Date().getTime()
			},
			success : function(data) {  
			    var s = data.split(":");
			    if(s[0]=="success"){
			    	art.dialog.tips(s[1],2);//location.reload();			    	
			    }else{
			    	art.dialog.tips(s[1],2,'error');	
			    }
			},  
			error : function(data) {  
			    art.dialog.tips(data,2,'error');
			}  
		});
                   },
                   cancelVal:'<ingenta-tag:LanguageTag key="Pages.Cart.Lable.Button2" sessionKey="lang" />',cancel:true,
                   lock:true
                   });
	
	};
	
	function apply(){
		/* var val=$('input:radio[name="paytype"]:checked').val();
            if(val==null){
                alert("<ingenta-tag:LanguageTag key="Pages.Cart.Prompt.Pay.Select" sessionKey="lang" />");
                return false;
            }  */
		document.getElementById("form").submit();
	}
</script>
<body>
<!--以下top state -->
<jsp:include page="/pages/header/headerData" />
<!--以上top end -->

<div class="main">
<form:form id="form" commandName="form" action="${ctx}/pages/cart/form/checkout">
	<div class="cartNav">
    	<ul>
    	    <c:if test="${fn:length(list)==0 }">
            <div align="center">
            <c:if test="${sessionScope.lang == 'zh_CN'}"><span></br></br>您的购物车内暂时没有资源，您可以去<a href="${ctx }/">首页</a>挑选喜欢的资源。</br></br></span></c:if>
            <c:if test="${sessionScope.lang == 'en_US'}"><span></br></br>You currently have no items in your shopping cart, <a href="${ctx }/">browse and find some items to add to your cart now!</a></br></br></span></c:if>
            </div>
            </c:if>
    	    <c:if test="${fn:length(list)!=0 }">
            <li><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Complete_order" sessionKey="lang" /></li>
            <li><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Settlement_Center" sessionKey="lang" /></li>
            <li class="cartNavLi"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Title" sessionKey="lang" /></li>
            </c:if>
        </ul>
    </div>
    <c:set var="isSpecial">${1==2 }</c:set>
    <c:forEach items="${list }" var="l" varStatus="index">
    　  <c:if test='${l.price.code=="special_price" }'>
   <c:set var="isSpecial">${1==1 }</c:set> 
   </c:if>
   </c:forEach>
   <c:if test="${isSpecial }">
     <div class="note">
    	<img src="${ctx }/images/ico/ico_20.png" class="vm" /> <ingenta-tag:LanguageTag key="Pages.Cart.Lable.Alert" sessionKey="lang" />
    </div>
   </c:if>
     <c:if test="${fn:length(list)!=0 }">
     <div class="cartCont oh">
    	<p class="oh mb10">
        	<span class="fl"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.myCart" sessionKey="lang" /> <span><c:if test="${sessionScope.totalincart!=null}">[${sessionScope.totalincart}]</c:if></span> <ingenta-tag:LanguageTag key="Pages.Cart.Lable.myCart1" sessionKey="lang" /></span>
            <span class="fr">
            <c:if test="${fn:length(list)>0 }">
            <a href="javascript:void(0)" class="mr10" onclick="deleteAll();"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Delete_All" sessionKey="lang" /></a>
            </c:if>
             >> <a href="${ctx }/"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Continue.Shopping" sessionKey="lang" /></a></span>
        </p>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="cartTable">
          <tr width="20%" class="trTop">
            <!-- <td width="140" align="center">全选</td> -->
            <td width="170" align="center"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Name" sessionKey="lang" /></td>
            <td width="170" align="center">ISBN/ISSN</td>
            <td width="140" align="center">&nbsp;</td>
            <td width="140" align="center"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Subtotle" sessionKey="lang" /></td>
            <td width="140" align="center"><ingenta-tag:LanguageTag key="Global.Prompt.Operating" sessionKey="lang" /></td>
          </tr>
          <c:forEach items="${list }" var="l" varStatus="index">
          <input type="hidden" name="detailIds" value="${l.id }"/>
          <tr class="trBody">
            <!-- <td align="center"><input type="checkbox" /></td> -->
            <c:if test="${l.collection==null }">
            <td >
            	<p>
	            	<c:if test="${l.price.publications.type!=3}">
	            		<a href="${ctx}/pages/publications/form/article/${l.price.publications.id}">${l.name}</a>
	            	</c:if>
            		<c:if test="${l.price.publications.type==3}">
	            		<a href="${ctx}/pages/publications/form/charperView?id=${l.price.publications.id}&pid=${l.price.publications.publications.id}">${l.name}</a>
	            	</c:if>
            	</p>
				<c:if test='${l.price.publications.author!=null&& l.price.publications.author!=""}'>
				<p>By  
					<c:set var="authorName" value="${l.price.publications.author }" />
					<c:choose>  
					    <c:when test="${fn:length(authorName) > 20}">  
					        <c:out value="${fn:substring(authorName, 0, 20)}..." />  
					    </c:when>  
					   <c:otherwise>  
					      <c:out value="${authorName}" />  
					    </c:otherwise>  
					</c:choose>  
				</p>
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
            <td align="center">${l.price.publications.code} </td>
            <td align="center">
            	<c:if test="${l.price.publications.cover!=null&&l.price.publications.cover!='' }">
            	<a href="${ctx}/pages/publications/form/article/${l.price.publications.id}">
            		<img src="${ctx}/pages/publications/form/cover?t=1&id=${l.price.publications.id}" width="46" height="63"  class="imgbor" />
            	</a>
            	</c:if>
            </td>
            <td align="center">
            <c:if test='${l.price.code=="special_price" }'>--</c:if>        
            <c:if test='${l.price.code!="special_price" }'>￥${l.salePriceExtTax}</c:if>
            
            </td>
            <td align="center"><a href="javascript:void(0)" onclick="deleteItem('${l.price.publications.id }','${l.id}')" class="a_cancel"><ingenta-tag:LanguageTag key="Global.Button.Delete" sessionKey="lang" /></a></td>
          </tr>
          </c:forEach>
        </table>
        </c:if>
        <c:if test="${fn:length(list)>0 }">
		<p class="tr f14 mt10"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Totle" sessionKey="lang" />：&nbsp;<span><c:if test="${isSpecial&&form.totalPrice==0}">--</c:if><c:if test="${!isSpecial||form.totalPrice>0}">￥${form.totalPrice }</c:if></span></p>
        <p class="tr mt10"><a  class="orgingA" href="javascript:void(0)" onclick="apply();"><ingenta-tag:LanguageTag key="Pages.Cart.Button.Settle" sessionKey="lang" />&gt;&gt;</a></p>
        </c:if>
    </div>
    <c:if test="${fn:length(list)>0 }">
    <div class="cartList">
    <p class="f14 fb mb10"><ingenta-tag:LanguageTag key="Pages.Cart.Lable.Bought" sessionKey="lang" /></p>
    
    <!-- 购买过此书的人还购买过 -->
    <% System.out.println(request.getSession().getAttribute("path") + "cart_A.html");%>
    <% if (!new File(request.getSession().getAttribute("path") + "cart_A.html").exists()) {%>
                        <jsp:include page="/pages/cart/form/getCartPagingListDown" flush="true">
						<jsp:param value="${form.subtype }" name="subtype"/>
						</jsp:include>
					<% }  %>
	<div class="oh" id="down">
	${form.subtype }
		<script type="text/javascript">
						<!--
						$(function() {
						    $("#down").load("/upload/cart_${form.subtype}.html");
						});
						//-->
		 </script>
    </div>
    
    </div>
    </c:if>
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

