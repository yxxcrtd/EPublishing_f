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
	<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/common.css" rel="stylesheet" type="text/css" />
<link href="css/scroll.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script src="js/jquery.superslide.2.1.1.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="/pages/header/headerData" flush="true" />
	<div class="main personMain h700">
	    <h1 class="botH1">重置密码</h1>
	    <p>请到 ${form.email} 查阅来易阅通的邮件, 从邮件重设你的密码。</p>       
	</div>
	<!-- 底部的版权信息 -->
	<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
	<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
</body>
</html>