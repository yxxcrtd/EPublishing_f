<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/tools.jsp"%>
<html>
	<head>
	<%
    response.setContentType("application/x-excel");
    String filename="subscription_log" + request.getAttribute("year").toString();
   	String dfileName = java.net.URLEncoder.encode(filename,"utf-8") + ".xls"; 
   	response.setHeader("Content-Disposition","attachment;filename="+dfileName);
    response.setContentType("application/x-excel");
	%> 
	</head>
	<body>
		<table width="100%" border="1" cellpadding="0" cellspacing="1" class="devil_table">
  		<tr>
    		<th width="150">Title</th>
    		<th width="150">Author</th>
		    <th width="100">PubDate</th>
		    <th width="100">Price</th>
		    <th width="150">ISBN/ISSN</th>
		    <th width="300">Remark</th>
			<th width="150">Date of purchase</th>
		    <th width="100">Type</th>
		    <th width="150">Begin</th>
			<th width="150">End</th>
  		</tr>
  		<c:forEach items="${list}" var="o" varStatus="index">
	  	<tr>
    		<td width="150">${o.publications.title}</td>
    		<td width="150">${o.publications.author}</td>
		    <td width="100">${o.publications.pubDate}</td>
		    <td width="100">${o.publications.listPrice}</td>
		    <td width="150">'${o.publications.code}</td>
		    <td width="300">${o.publications.remark}</td>	
		    <td width="150"><fmt:formatDate value="${o.createdon}" pattern="yyyy-MM-dd"/></td>	
		    <c:if test="${o.type==1}">
				<td width="100"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.type1"/></td>
				<td width="150"> </td>
				<td width="150"> </td>
			</c:if>
			<c:if test="${o.type==2}">
				<td width="100"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.type2"/></td>
				<td width="150"><fmt:formatDate value="${o.startTime}" pattern="yyyy-MM-dd"/></td>								        
				<td width="150"><fmt:formatDate value="${o.endTime}" pattern="yyyy-MM-dd"/></td>								        
			</c:if>	   
  		</tr>
  		</c:forEach>
	</table>
	</body>
</html>