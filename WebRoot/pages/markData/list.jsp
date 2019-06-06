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
	function dividePage(targetPage){
		if(targetPage<0){return;}
		document.formList.action="${ctx}/pages/markData/form/manager?pageCount="+${form.pageCount}+"&curpage="+targetPage;
		document.formList.submit();
	}

	function GO(obj){
		document.formList.action="${ctx}/pages/markData/form/manager?pageCount="+$(obj).val();
		document.formList.submit();
	}
</script>
<!--客户案例开始-->

</head>

<body>

<jsp:include page="/pages/header/headerData" />

<div class="main personMain">

 <jsp:include page="/pages/menu?mid=marc&type=2" flush="true" />

    <div class="perRight">
    	<h1><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.MarkData.list.title'/></h1>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="curTable">
          <tr>
            <td width="120" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Label.SerialNumber"/></td>
            <td align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Markdata.list.Table.name"/></td>
            <td width="150" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Markdata.list.Table.createOn"/></td>
            <td width="80" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Markdata.list.Table.status"/></td>
            <td width="120" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Prompt.Operating"/></td>
          </tr>
            <c:forEach items="${list }" var="c" varStatus="index">
			             <c:set var="cssWord" value="${index.index%2==0?'a':'b' }"/>     
         				  <tr>
			                <td class="${cssWord}bodytd" align="center">${index.index + form.pageCount*form.curpage + 1}</td>
			                <td class="${cssWord}bodytd tdname" title="${c.name}"><span class="omit w260 pl10">${c.name}</span></td>
			                <td class="${cssWord}bodytd" align="center">
					        	<fmt:formatDate value="${c.createOn}" pattern="yyyy-MM-dd HH:mm:ss"/>
					        </td>
					        <td class="${cssWord}bodytd" align="center">
								<c:set var="status"><c:out value="${c.status }"></c:out></c:set>${form.statusMap[status]}
							</td>
							 <td class="${cssWord}bodytd" align="center">
							 	<c:if test="${c.path!=null&&c.path!='' }">
								<a class="a_gret" href="${ctx}/pages/markData/form/download?id=${c.id}" ><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.DownLoad"/></a>
								</c:if>
							</td>		                
			              </tr>
			       </c:forEach>
        </table>  
        <!--分页条开始-->
        <div class="pagelink">
           <jsp:include page="../pageTag/pageTag.jsp">
		       <jsp:param value="${form }" name="form"/>
	      </jsp:include>
       </div>
       <!--分页条结束-->   
    </div>
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
		
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>
