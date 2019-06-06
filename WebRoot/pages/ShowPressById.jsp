<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
		<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
		<link type="text/css" rel="stylesheet" href="${ctx}/css/press.css" />
		<%@ include file="/common/tools.jsp"%>
		<%@ include file="/common/ico.jsp"%>
		<script type="text/javascript">
		<!--
		$(function() {
		    $.ajaxSetup({ cache: false });
		    $(".main").load("/upload/press/${id}.html");
		});
		//-->
		</script>
	</head>

	<body>
		<!-- 所有页面引用的顶部 -->
		<jsp:include page="/pages/header/headerData" />
		
		<div class="main personMain"></div>
		
		<!-- 版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
		</script>
	</body>
</html>
