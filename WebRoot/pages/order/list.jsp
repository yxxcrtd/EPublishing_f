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
			/* $("#borderA1").click(function(){
				$("#borderA1").attr("class","borderA borderACur");
				$("#dateRange").val("1");
			}); */
			
			$("#search111").click(function(){
				var str=""; 
				var allStatus = $("input[name='allStatus']:checked");
					if(allStatus.size()>0){
						var i = 1;
						allStatus.each(function(){ 
							if(i==allStatus.size()){
								str+=$(this).val();
							} else{
								str+=$(this).val()+",";
								i++;
							}
						});
				}
				var selRadio=$("#dateRange");
				
				/* var startDate = $("#startTime").val();
				var endDate = $("#endTime").val();
				var url = "${ctx}/pages/order/form/list?allStatus="+str+"&startTime="+startDate+"&endTime="+endDate+"&r_="+new Date().getTime(); */
				var url = "${ctx}/pages/order/form/list?allStatus="+str+ "&dr="+selRadio.val()+"&r_="+new Date().getTime();
				window.location.href=url;
				
			});
			$("#download").click(function(){
				var str=""; 
				var allStatus = $("input[name='allStatus']:checked");
					if(allStatus.size()>0){
						var i = 1;
						allStatus.each(function(){ 
							if(i==allStatus.size()){
								str+=$(this).val();
							} else{
								str+=$(this).val()+",";
								i++;
							}
						});
				}
				var selRadio=$("#dateRange");
				
				/* var startDate = $("#startTime").val();
				var endDate = $("#endTime").val();
				var url = "${ctx}/pages/order/form/list?allStatus="+str+"&startTime="+startDate+"&endTime="+endDate+"&r_="+new Date().getTime(); */
				var url = "${ctx}/pages/order/form/download?allStatus="+str+ "&dr="+selRadio.val()+"&r_="+new Date().getTime();
				window.location.href=url;				
			});
		});
		
		function selectTime(type){
		//	type=$("#dr").val();
			$("a[name='radio']").attr("class","borderA");
			$("#borderA"+type).attr("class","borderA borderACur");
			$("#dateRange").val(type);
		}
		
		function dividePage(targetPage){
			var str=""; 
			var allStatus = $("input[name='allStatus']:checked");
				if(allStatus.size()>0){
					var i = 1;
					allStatus.each(function(){ 
						if(i==allStatus.size()){
							str+=$(this).val();
						} else{
							str+=$(this).val()+",";
							i++;
						}
					});
			}
			var selRadio=$("#dateRange");
			if(targetPage<0){return;}
			document.formList.action="${ctx}/pages/order/form/list?pageCount="+${form.pageCount}+"&curpage="+targetPage+"&allStatus="+str+"&dr="+selRadio.val();
			document.formList.submit();
		}
		
		function GO(obj){
			var str=""; 
			var allStatus = $("input[name='allStatus']:checked");
				if(allStatus.size()>0){
					var i = 1;
					allStatus.each(function(){ 
						if(i==allStatus.size()){
							str+=$(this).val();
						} else{
							str+=$(this).val()+",";
							i++;
						}
					});
			}
			var selRadio=$("#dateRange");
			document.formList.action="${ctx}/pages/order/form/list?pageCount="+$(obj).val()+"&allStatus="+str+"&dr="+selRadio.val();
			document.formList.submit();
		}
		</script>
	</head>
	<body>
		<!--以下top state -->
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--以上top end -->
		<!--以下中间内容块开始-->
		<div class="main personMain">
		<!--左侧菜单开始 -->
		<c:if test="${level!=2 }">
	    	<jsp:include page="/pages/menu?mid=order&type=5" flush="true"/>
	    </c:if>
	    <c:if test="${level==2 }">
	    	<jsp:include page="/pages/menu?mid=order&type=1" flush="true"/>
	    </c:if>
	    
	    <!--左侧菜单结束-->
		    <div class="perRight">
		    <h1><ingenta-tag:LanguageTag key="Page.Order.List.Lable.MyOrder" sessionKey="lang" /></h1>
		       <p class="mb10 bookmarkP">
		        <span><input type="checkbox" class="vm" name="allStatus" value="1" <c:if test="${fn:indexOf(form.allStatus,'1')>-1 }"> checked="checked" </c:if>/> <ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status.Option1" sessionKey="lang" /></span>
				<span><input type="checkbox" class="vm" name="allStatus" value="2" <c:if test="${fn:indexOf(form.allStatus,'2')>-1 }"> checked="checked" </c:if>/> <ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status.Option2" sessionKey="lang" /></span>
				<span><input type="checkbox" class="vm" name="allStatus" value="3" <c:if test="${fn:indexOf(form.allStatus,'3')>-1 }"> checked="checked" </c:if>/> <ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status.Option3" sessionKey="lang" /></span>
		       </p>
		          <p class="mb10 bookmarkP">
		          <span><ingenta-tag:LanguageTag key="Page.Order.List.Button.Dates" sessionKey="lang"/>：</span>
		          <span><a href="javascript:void(0)" class="borderA<c:if test="${dr!=null && dr=='1'}"> borderACur</c:if>" id="borderA1" name="radio" onclick="selectTime('1')"><ingenta-tag:LanguageTag key="Pages.Publications.Recommed.OneWeek" sessionKey="lang" /></a></span>
		          <span><a href="javascript:void(0)" class="borderA<c:if test="${dr!=null && dr=='2'}"> borderACur </c:if>" id="borderA2" name="radio" onclick="selectTime('2')"><ingenta-tag:LanguageTag key="Pages.Publications.Recommed.OneMonth" sessionKey="lang" /></a></span>
		            <span><a href="javascript:void(0)" class="borderA<c:if test="${dr!=null && dr=='3'}"> borderACur </c:if>" id="borderA3" name="radio" onclick="selectTime('3')"><ingenta-tag:LanguageTag key="Pages.Publications.Recommed.ThreeMonth" sessionKey="lang" /></a></span>
		            <span><a href="javascript:void(0)" class="borderA<c:if test="${dr==null || dr=='0'}"> borderACur</c:if>" id="borderA0" name="radio" onclick="selectTime('0')"><ingenta-tag:LanguageTag key="Pages.Publications.Recommed.All" sessionKey="lang" /></a></span>
		          <%-- <span><input type="radio" <c:if test="${dr!=null && dr=='1'}"> checked="checked" </c:if> value="1" name="dateRange" class="vm">一周内</input></span>
		          <span><input type="radio" <c:if test="${dr!=null && dr=='2'}"> checked="checked" </c:if> value="2" name="dateRange" class="vm">一个月内</input></span>
		          <span><input type="radio" <c:if test="${dr!=null && dr=='3'}"> checked="checked" </c:if> value="3" name="dateRange" class="vm">三个月内</input></span>
		          <span><input type="radio" <c:if test="${dr==null || dr=='0'}"> checked="checked" </c:if> value="0" name="dateRange" class="vm">所有</input></span>  --%>
		          <!--<span><input onclick="new CbsCalendar(this.id)" type="text" style="width:15%;vertical-align: middle; *margin-top:8px; " name="startTime" id="startTime" value="<c:if test="${form.startTime!=null}">${form.startTime }</c:if>"/>——<input type="text" onclick="new CbsCalendar(this.id)" name="endTime" style="width:15%;vertical-align: middle;*margin-top:8px; " id="endTime" value="<c:if test="${form.endTime!=null}">${form.endTime }</c:if>"/></span>
		          -->
		          <input type="hidden" id="dateRange" name="dateRange" value="${dr }"/>
		          </p>
		          <p class="mb20 bookmarkP">
		          	<span><a href="javascript:void(0)" name="search111" id="search111" class="orgingB"><ingenta-tag:LanguageTag key="Page.Order.List.Button.Search" sessionKey="lang" /></a></span>
		          	<span><a href="javascript:void(0)" name="download" id="download" class="orgingB"><ingenta-tag:LanguageTag key="Page.Order.List.Button.download" sessionKey="lang" /></a></span>
		          </p>
		        
		        <div class="page_table">
		        	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="cartTable">
		              <tr  class="trTop">
		              	<td  width="170" align="center"><ingenta-tag:LanguageTag key="Page.Order.List.Lable.SN" sessionKey="lang" /></td>
				        <td width="180" align="center"><ingenta-tag:LanguageTag key="Page.Order.List.Lable.Date" sessionKey="lang" /></td>
				        <td width="150" align="center"><ingenta-tag:LanguageTag key="Page.Order.List.Lable.TotalPrice" sessionKey="lang" /></td>
				        <td width="120" align="center"><ingenta-tag:LanguageTag key="Page.Order.List.Lable.Count" sessionKey="lang" /></td>
				        <td align="center"><ingenta-tag:LanguageTag key="Page.Order.List.Lable.Status" sessionKey="lang" /></td>
		              </tr>
		             <c:forEach items="${list }" var="o" varStatus="index">
				        <tr class="trBody">
					        <td align="center" class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
						        <a href="${ctx}/pages/order/form/insdetail?id=${o.id}" title="${o.code}">${o.code}</a>
					        </td>
					        <td align="center" class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
					        	<fmt:formatDate value="${o.createdon}" pattern="yyyy-MM-dd HH:mm"/>
					        </td>
					        <td align="center" class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
					        	${o.currency}&nbsp;<fmt:formatNumber value="${o.salePriceExtTax }" pattern="###,###0.00"/>
					        </td>
					        <td align="center" class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
					        	${o.quantity }
					        </td>
					        <td align="center" class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
								<c:if test="${o.status==1 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status.Option1" sessionKey="lang" /></c:if>
			        			<c:if test="${o.status==2 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status.Option2" sessionKey="lang" /></c:if>
			        			<c:if test="${o.status==3 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status.Option3" sessionKey="lang" /></c:if>
			        			<c:if test="${o.status==99 }"><ingenta-tag:LanguageTag key="Page.Order.Checkout.Lable.Status.Option99" sessionKey="lang" /></c:if>
							</td>
				        </tr>
					 </c:forEach>
				</table>
				<jsp:include page="../pageTag/pageTag.jsp">
			       <jsp:param value="${form }" name="form"/>
	            </jsp:include>
		        </div>	
			</div>
	    <!--左侧内容结束-->
	    
	    </div>
		<!--以上中间内容块结束-->
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
