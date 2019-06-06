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
		$(function() {
			var sh1=false;
			$("#tab1").click(function(){
				if(!sh1){
					document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=1";
					document.formList.submit();
				}
			});
		
			var sh2=false;
			$("#tab2").click(function(){
				if(!sh2){
					document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=2";
					document.formList.submit();
				}
			});

			var dm=false;
			$("#tab3").click(function(){
				if(!dm){
					document.formList.action="${ctx}/pages/user/form/mySubscription/public/collection?searchType=99";
					document.formList.submit();
				}
			});
			/* var qi=false;
			$("#tab4").click(function(){
				if(!qi){
					document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=7";
					document.formList.submit();
				}
			});
			 */
			
			
		});
		function queryList(){
			document.getElementById("form").action="";
			document.getElementById("form").submit();
		}
		function downLog(range,searchType){
			
			 	window.location.href="${ctx}/pages/user/form/collection/mySubscriptionLog";
		}
		
		function dividePage(targetPage){
			if(targetPage<0){return;}
			document.getElementById("form").action="${ctx}/pages/user/form/mySubscription/public/collection?searchType=99&pageCount="+${form.pageCount}+"&curpage="+targetPage;
			document.getElementById("form").submit();
		}
		
		function GO(obj){
			document.getElementById("form").action="${ctx}/pages/user/form/mySubscription/public/collection?searchType=99&pageCount="+$(obj).val();
			document.getElementById("form").submit();
		}
	</script>
</head>

<body>
<jsp:include page="/pages/header/headerData" flush="true" />
   
 		
<div class="main personMain">
    	 <jsp:include page="/pages/menu?mid=subscribe&type=2" flush="true" />
    <div class="perRight">
    <form:form action="${form.who}" method="post" commandName="form" id="form" >
    <%-- <input type="hidden" id="isCn" name="isCn" value="${isCn}"/> --%>
    <%-- <input type="hidden" id="pType" name="pType" value="${pType }"/> --%>
    <form:hidden path="searchType"/>
		<form:hidden path="range" />
   	  <div class="StabedPanels" >

            <ul class="oh">
                <li id="tab1" class="Stab <c:if test="${1 == form.searchType}">StabSeleted</c:if>"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.book2"/></li>
                <li id="tab2" class="Stab <c:if test="${2 == form.searchType}">StabSeleted</c:if>"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.journal2"/></li>
                <li id="tab3" class="Stab <c:if test="${4 == form.searchType}">StabSeleted</c:if>"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.collection2"/></li>
<%--                 <li id="tab4" class="Stab lastLi <c:if test="${7 == form.searchType}">StabSeleted</c:if>"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.journal.Period"/></li>
 --%>            </ul>
       <div class="StabContent ScontentSelected">
         <div class="StabContent ScontentSelected">
           	<p class="mb10"><a href="javascript:void(0)" class="orgingA" onclick="downLog('${form.range}','${form.searchType}')"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.DownLoad"/></a></p>
	   </div>  
       <table width="99%" border="0" cellspacing="0" cellpadding="0" class="cartTable">
            <tr class="trTop">
                <td width="200" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Collection.Table.Label.All"/></td>
                <td width="190" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.type"/></td>
                <td width="190" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.start"/></td>
                <td align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.end"/></td>
            </tr>
           <c:forEach items="${list }" var="l" varStatus="index">
		             <c:set var="cssWord" value="${index.index%2==0?'a':'b' }"/>
		             <c:if test="${l.publications!=null }">
				        <tr class="trBody">
					        <td class="${cssWord}bodytd tdname" align="left">
					        <span style="width:430px">
					        	<a href="${ctx}/pages/publications/form/article/${l.publications.id}?isReady=1" title="${l.publications.title}">${l.publications.title}</a>
					        </span>
					        </td>
					        <c:if test="${l.type==1}">
								<td class="${cssWord}bodytd" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.type1"/></td>
								<td class="${cssWord}bodytd" align="center"></td>
								<td class="${cssWord}bodytd" align="center"></td>
							</c:if>
					        <c:if test="${l.type==2}">
					        	<c:if test="${l.isTrial==1}">
					        		<td class="${cssWord}bodytd" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Lable.Trial"/></td>
					        	</c:if>
					        	<c:if test="${l.isTrial==null || l.isTrial==2}">
					        		<td class="${cssWord}bodytd" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.type2"/></td>
					        	</c:if>		
								<td class="${cssWord}bodytd" align="center"><fmt:formatDate value="${l.startTime}" pattern="yyyy-MM-dd"/></td>								        
				        		<td class="${cssWord}bodytd" align="center"><fmt:formatDate value="${l.endTime}" pattern="yyyy-MM-dd"/></td>								        
					        </c:if>
				        </tr>
				        </c:if>
				        <c:if test="${l.publications==null }">
				        <tr>
					        <td class="<c:if test="${index.index%2==0 }" >atdbody01</c:if><c:if test="${index.index%2==1 }">btdbody01</c:if>">
					        <span>
					        <a href="${ctx}/pages/collection/form/list?id=${l.collection.id}" title="${l.collection.name }">${l.collection.name}</a>
					        </span>
					        </td>
					        <c:if test="${l.type==1}">
								<td class="${cssWord}bodytd" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.type1"/></td>
								<td class="${cssWord}bodytd" align="center"></td>
								<td class="${cssWord}bodytd" align="center"></td>
							</c:if>
					        <c:if test="${l.type==2}">
					        	<c:if test="${l.isTrial==1}">
					        		<td class="${cssWord}bodytd" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Lable.Trial"/></td>
					        	</c:if>
					        	<c:if test="${l.isTrial==null || l.isTrial==2}">
					        		<td class="${cssWord}bodytd" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.type2"/></td>
					        	</c:if>					        	
								<td class="${cssWord}bodytd" align="center"><fmt:formatDate value="${l.startTime}" pattern="yyyy-MM-dd"/></td>								        
				        		<td class="${cssWord}bodytd" align="center"><fmt:formatDate value="${l.endTime}" pattern="yyyy-MM-dd"/></td>								        
					        </c:if>
				        </tr>
				        </c:if>
					 </c:forEach>
          </table>
      </div>
    </form:form>
                    <jsp:include page="../pageTag/pageTag.jsp">
			          <jsp:param value="${form }" name="form"/>
	                </jsp:include>	    	    
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
</div>
</div>

		<!-- 首页底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>