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
		function queryList(){
			document.getElementById("form").action="";
			document.getElementById("page").value="0";
			document.getElementById("form").submit();
		}
		
		function dividePage(targetPage){
			if(targetPage<0){return;}
			document.formList.action="${ctx}/pages/user/form/myTranLog?pageCount="+${form.pageCount}+"&curpage="+targetPage;
			document.formList.submit();
		}
		
		function GO(obj){
			document.formList.action="${ctx}/pages/user/form/myTranLog?pageCount="+$(obj).val();
			document.formList.submit();
		}
		</script>
	</head>
	<body>

		<!--以下top state -->
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--以上top end -->
		<form:form action="myTranLog" method="post" commandName="form" id="form" >
		<!--以下中间内容块开始-->
		<div class="main personMain">
		<!--左侧菜单开始 -->
		<c:if test="${sessionScope.mainUserLevel!=2 }">
    	<jsp:include page="/pages/menu?mid=expense&type=5" flush="true"/>
   		</c:if>
    	<c:if test="${sessionScope.mainUserLevel==2 }">
    	<jsp:include page="/pages/menu?mid=expense&type=1" flush="true"/>
    	</c:if>
	    <!--左侧菜单结束-->
		    <div class="perRight">
		    <h1><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.title"/></h1>
		       	  <p class="mb10"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Label.Pay"/>
	          				<span class="blue"><fmt:formatNumber value="${0-form.totalSpending}" pattern="0.00"/></span>
						<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Label.balance"/>
							<span class="red"><fmt:formatNumber value="${form.balance}" pattern="0.00"/></span>
		          </p>
		        
		        	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="curTable">
		              <tr>
		              	<td width="190" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Table.Label.no"/></td>
				        <td width="190" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Table.Label.type"/></td>
				        <td width="190" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Table.Label.money"/></td>
				        <td align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Table.Label.date"/></td>						        						        	
		              </tr>
		             <c:forEach items="${list }" var="l" varStatus="index">
				        <tr>
							<td align="center" class="<c:if test="${index.index%2==0 }">atdbody03</c:if><c:if test="${index.index%2==1 }">btdbody03</c:if>">
						        <span>
						        	<c:if test="${l.orderCode!=null&&l.orderCode!=''}">
						        	<a href="${ctx }/pages/order/form/detail?id=${l.order.id}">${l.orderCode}</a>
						        	</c:if>
						        	<c:if test="${l.orderCode==null||l.orderCode==''}">
						        	<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Table.Label.type1"/>
						        	</c:if>
						        </span>
						    </td>
					        <td align="center" class="<c:if test="${index.index%2==0 }">atdbody03</c:if><c:if test="${index.index%2==1 }">btdbody03</c:if>"><!-- 类型 1-存款 2-预存消费 3-支付宝消费 4-支票消费 5-预存冻结 -->
					        	<c:if test="${l.type==1}"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Table.Label.type1"/></c:if>
					        	<c:if test="${l.type==2 && l.amount<0}"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Table.Label.type2"/></c:if>
					        	<c:if test="${l.type==2 && l.amount>=0}"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Table.Label.type2_1"/></c:if>
					        	<c:if test="${l.type==3 && l.amount<0}"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Table.Label.type3"/></c:if>
					        	<c:if test="${l.type==3 && l.amount>=0}"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Table.Label.type3_1"/></c:if>
					        	<c:if test="${l.type==4 && l.amount<0}"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Table.Label.type4"/></c:if>
					        	<c:if test="${l.type==4 && l.amount>=0}"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Table.Label.type4"/></c:if>
					        	<c:if test="${l.type==5 && l.amount<0}"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Table.Label.type5"/></c:if>
					        	<c:if test="${l.type==5 && l.amount>=0}"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyTranLog.Table.Label.type5_1"/></c:if>
					        </td>
					        <td align="center" class="<c:if test="${index.index%2==0 }">atdbody03</c:if><c:if test="${index.index%2==1 }">btdbody03</c:if>">
					        <c:if test="${l.amount>=0}">
					        	<span style="color:red">
					        		<fmt:formatNumber value="${l.amount}" pattern="0.00"/>
					        	</span>
					        </c:if>
					        <c:if test="${l.amount<0}">
					        	<span style="color:green">
					        		<fmt:formatNumber value="${l.amount}" pattern="0.00"/>
					        	</span>
					        </c:if>
					        </td>
					        <td align="center" class="<c:if test="${index.index%2==0 }">atdbody03</c:if><c:if test="${index.index%2==1 }">btdbody03</c:if>">
					        	<fmt:formatDate value="${l.createdon}" pattern="yyyy-MM-dd"/>
					        </td>								        
				        </tr>
					 </c:forEach>
				</table>
				<jsp:include page="../pageTag/pageTag.jsp">
			       <jsp:param value="${form }" name="form"/>
	            </jsp:include>				
			</div>
	    <!--左侧内容结束-->
	    
	    </div>
		<!--以上中间内容块结束-->
		</form:form>
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
