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
<script type="text/javascript" src="${ctx}/js/checkPwd.js"></script>
<!--客户案例开始-->
<script type="text/javascript">
	function apply(){		
		var f = document.getElementById("file").value;
		if(f.replace(/\s+/g,"")==""){
			if(!confirm("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.Prompt.Institution.Upload.Check.file'/>")){
		     	return;
			}		
		}
		document.getElementById("form").submit();		
	}
	</script>
</head>

<body>
	<!--以下top state -->
	<jsp:include page="/pages/header/headerData" />
	<!--以上top end -->
<div class="main personMain">
	<div class="personLeft">
	<jsp:include page="/pages/menu?mid=logo&type=2" flush="true" />
    </div>
    <div class="perRight">
    <form:form id="form" commandName="form" action="uploadSubmit" method="post" enctype="multipart/form-data">
	<c:if test="${form.msg!=null}">
	<script language="javascript">
		$(function(){
			art.dialog.tips('${form.msg}');			
		});
	</script>
	</c:if>
    	<h1><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SetLogo.title"/></h1>
    	 <p><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SetLogo.title.detail"/></p>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="regTable mt10">
          <tr>
            <td width="120" align="right" class="tleft"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SetLogo.Label.text"/>：</td>
            <td colspan="3"><form:input type="text" path="obj.logoNote" id="logoNote" /></td>
          </tr>
          <tr>
            <td width="120" align="right" class="tleft"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SetLogo.Label.URL"/>：</td>
            <td colspan="3"><form:input type="text" path="obj.logoUrl" id="logoUrl"/></td>
          </tr>
          <tr>
            <td width="120" align="right" class="tleft"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SetLogo.Label.Image"/>：</td>
            <td colspan="3"><c:if test="${null!=form.obj.logo}"><image src="/upload/institution/logo/${form.obj.logo}" width="150" height="40" /></c:if><form:input type="file" path="file" id="file"/></td>

            
          </tr>
          <tr>
            <td align="right">&nbsp;</td>
            <td colspan="3"><p class="mt10"><a href="javascript:void(0)" class="orgingA" onclick="apply()"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Lable.Upload"/></a></p></td>
          </tr>
        </table>
        <form:hidden path="id" />
       </form:form> 
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
