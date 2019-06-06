<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/tools.jsp"%>
<html>
	<head>
	<%
    response.setContentType("application/x-excel");
    String filename="Search_Statistics_" + request.getAttribute("year").toString() ;
   	String dfileName = java.net.URLEncoder.encode(filename,"utf-8") + ".xls"; 
   	response.setHeader("Content-Disposition","attachment;filename="+dfileName);
    response.setContentType("application/x-excel");
    Integer endMonth = Integer.valueOf(request.getAttribute("endMonth").toString());
    Integer startMonth = Integer.valueOf(request.getAttribute("startMonth").toString());
	%> 
	</head>
	<body>
		<table width="100%" border="1" cellpadding="0" cellspacing="1" class="devil_table">
  		<tr>
    		<th width="150">Title</th>
    		<th width="150">Publisher</th>
		    <th width="100">Platform</th>
		    <th width="50">Book DOI</th>
		    <th width="50">Propriet Identifier</th>
		    <th width="150">ISBN</th>
		    <th width="150">ISSN</th>
		    <th width="50">User activity</th>
		    <th width="50">Reporting Period Total</th>
		   	<%
		    	for(int i=startMonth;i<=endMonth;i++){
		    		String month = "";
		    		if(i==1){
		    			month="Jan";
		    		}
		    		if(i==2){
		    			month="Feb";
		    		}
		    		if(i==3){
		    			month="Mar";
		    		}
		    		if(i==4){
		    			month="Apr";
		    		}
		    		if(i==5){
		    			month="May";
		    		}
		    		if(i==6){
		    			month="Jun";
		    		}
		    		if(i==7){
		    			month="Jul";
		    		}
		    		if(i==8){
		    			month="Aug";
		    		}
		    		if(i==9){
		    			month="Sep";
		    		}
		    		if(i==10){
		    			month="Oct";
		    		}
		    		if(i==11){
		    			month="Nov";
		    		}
		    		if(i==12){
		    			month="Dec";
		    		}
		    %>
		    <th><%=month%>-${form.year}</th>
		    <%} %>
  		</tr>
  		<c:forEach items="${list }" var="o" varStatus="index">
	  	<tr>
    		<td width="150">${o.publications.title}</td>
    		<td width="150">${o.publications.publisher.name}</td>
		    <td width="100">CNPE</td>
		    <td width="50"></td>
		    <td width="50"></td>
		    <td width="150"><c:if test="${fn:contains('2,4,6,7',o.publications.type)==false}">'${o.publications.code}</c:if></td>
		    <td width="150"><c:if test="${fn:contains('2,4,6,7',o.publications.type)==true}">'${o.publications.code}</c:if></td>
		    <td width="50">'${o.activity}</td>
		    <td width="50">${o.year}</td>
		   	<%
		    	for(int i=startMonth;i<=endMonth;i++){
		    		if(i==1){
		    			%>
		    				<td>${o.month1}</td>
		    			<%
		    		}
		    		if(i==2){
		    			%>
		    				<td>${o.month2}</td>
		    			<%
		    		}
		    		if(i==3){
		    			%>
		    				<td>${o.month3}</td>
		    			<%
		    		}
		    		if(i==4){
		    			%>
		    				<td>${o.month4}</td>
		    			<%
		    		}
		    		if(i==5){
		    			%>
		    				<td>${o.month5}</td>
		    			<%
		    		}
		    		if(i==6){
		    			%>
		    				<td>${o.month6}</td>
		    			<%
		    		}
		    		if(i==7){
		    			%>
		    				<td>${o.month7}</td>
		    			<%
		    		}
		    		if(i==8){
		    			%>
		    				<td>${o.month8}</td>
		    			<%
		    		}
		    		if(i==9){
		    			%>
		    				<td>${o.month9}</td>
		    			<%
		    		}
		    		if(i==10){
		    			%>
		    				<td>${o.month10}</td>
		    			<%
		    		}
		    		if(i==11){
		    			%>
		    				<td>${o.month11}</td>
		    			<%
		    		}
		    		if(i==12){
		    			%>
		    				<td>${o.month12}</td>
		    			<%
		    		}
		    %>
		   	<%} %>
  		</tr>
  		</c:forEach>
	</table>
	</body>
</html>