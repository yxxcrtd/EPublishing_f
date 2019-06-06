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
	function apply() {
		var pwd = document.getElementById("pwd").value;
		if (pwd.replace(/\s+/g, "") == "") {
			art.dialog
					.tips(
							"<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ResetPwd.Prompt.pwd'/>",
							1, 'error');
			document.getElementById("pwd").focus();
			return;
		}
		if (document.getElementById("pwd").value != document
				.getElementById("pwdCheck").value) {
			art.dialog
					.tips(
							"<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ResetPwd.Prompt.twopwd'/>",
							1, 'error');
			return;
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
    <c:if test="${sessionScope.mainUserLevel!=2 }">
    	<jsp:include page="/pages/menu?mid=chpd&type=5" flush="true"/>
    </c:if>
    <c:if test="${sessionScope.mainUserLevel==2 }">
    	<jsp:include page="/pages/menu?mid=chpd&type=1" flush="true"/>
    </c:if>
    
    <div class="perRight">
    <form:form id="form" commandName="form" action="resetPwdSubmit" method="post">
    	<h1><ingenta-tag:LanguageTag sessionKey='lang'
												key='Pages.User.ResetPwd.title2' /></h1>
    	
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="regTable mt10">
          <tr>
            <td width="120" align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ResetPwd.Label.name' />：</td>
            <td colspan="3">${form.account.uid}</td>
          </tr>
          <tr>
            <td align="right"><span class="red">*</span><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ResetPwd.Label.pwd' />：</td>
            <td colspan="3">
            <form:password path="account.pwd" id="pwd"
				onkeyup="chkpwd(this)" style="vertical-align:middle"/> 
			</td>
          </tr>
          <tr>
            <td align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ResetPwd.Label.safe' />：</td>
            <td colspan="3"><p id="chkResult" style="margin-top:7px;"></p></td>
          </tr>
          <tr>
            <td width="120" align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ResetPwd.Label.twoPwd' />：</td>
            <td colspan="3"><input type="password" id="pwdCheck" name="pwdCheck" style="vertical-align:middle"/> </td>
          </tr>
          <tr>
            <td align="right">&nbsp;</td>
            <td colspan="3"><p class="mt10"><a href="javascript:void(0)" class="orgingA" onclick="apply();"><ingenta-tag:LanguageTag sessionKey='lang' key='Global.Button.Submit'/></a></p></td>
          </tr>
      </table>
      <form:hidden path="account.id" />
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
