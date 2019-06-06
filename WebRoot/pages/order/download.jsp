<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/tools.jsp"%>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%
    response.setContentType("application/x-excel");
    String filename="订单明细";
   	String dfileName = java.net.URLEncoder.encode(filename,"utf-8") + ".xls"; 
   	response.setHeader("Content-Disposition","attachment;filename="+dfileName);
    response.setContentType("application/x-excel");
	%> 
	</head>
	<body>
		<table width="619" height="219" border="1">
  		<tr>
  			<td>序号</td>
    		<td width="39" height="108">订单号</td>
    		<td width="39" height="108">订单日期</td>
		    <td width="320">
		    	<table width="319" height="104" border="1">
			      <tr>
			          <td colspan="10" align="center">订单明细</td>
			      </tr>
			      <tr>
				      <td>E-ISBN</td>
				      <td>H-ISBN</td>
				      <td>P-ISBN</td>
				      <td>标题</td>
				      <td>作者</td>
				      <td>出版商</td>
				      <td>发行日期</td>
				      <td>币种</td>
				      <td>价格</td>
				      <td>订单状态</td>
		      	  </tr>
		    	</table>
		  	</td>
  		</tr>
  		<c:forEach items="${list }" var="o" varStatus="index">
	  	<tr>
	  		<td>${index.index+1 }</td>
    		<td width="39" height="108">'${o.code }</td>
    		<td width="39" height="108"><fmt:formatDate value="${o.createdon}" pattern="yyyy-MM-dd HH:mm"/></td>
		    <td width="320">
		    	<table width="319" height="104" border="1">
		    	<c:forEach items="${o.detailList }" var="d" varStatus="index2">
		    	  <tr>
				      <td><c:if test="${d.price.publications.code!=null&&d.price.publications.code!='' }">'${d.price.publications.code }</c:if>
				      	<c:if test="${d.price.publications.code==null||d.price.publications.code=='' }">'${d.collection.code }</c:if>
					  </td>
				      <td>'${d.price.publications.hisbn }</td>
				      <td>'${d.price.publications.sisbn }</td>
				      <td>
				      <c:if test="${d.price.publications.title !=null &&d.price.publications.title !=''}">'${d.price.publications.title }</c:if>
				       <c:if test="${d.price.publications.title ==null||d.price.publications.title =='' }">'${d.collection.name }</c:if>
				      </td>
				      <td>${d.price.publications.author }</td>
				      <td>${d.price.publications.publisher.name }</td>
				      <td>${d.price.publications.pubDate }</td>
				      <td>${d.currency}</td>
				      <td>'${d.salePrice }</td>
				      <td>
				      <c:if test="${d.status==1 }">未处理</c:if>
				      <c:if test="${d.status==2 }">已付款未开通</c:if>
				      <c:if test="${d.status==3 }">已付款已开通</c:if>
				      <c:if test="${d.status==4 }">处理中</c:if>
				      <c:if test="${d.status==5 }">已完成</c:if>
				      <c:if test="${d.status==10 }">未付款已开通</c:if>
				      <c:if test="${d.status==99 }">已取消</c:if>
				      </td>
		      	  </tr>
		      	 </c:forEach>
		    	</table>
		  	</td>
  		</tr>
  		</c:forEach>
	</table>
	</body>
</html>