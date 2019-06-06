<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
<title><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Msg.Prompt.Title"/></title>
<link href="${ctx}/css/pub.css" rel="stylesheet" type="text/css" />
</head>

<body>
<c:if test="${form.isSuccess!=null&&form.isSuccess=='true'}">
	<script language="javascript">
      	var value='${form.returnValue}';
      	var val=value.split(",");
 		window.returnValue = val;
    </script>
</c:if>
<c:if test="${form.msg!=null}">
	<script language="javascript">
		alert('${form.msg}');
		window.close();
	</script>
</c:if>
<div class="debug">
${form.isSuccess }
${form.msg}<hr/>
${form.returnValue}<hr/>
</div>
</body>
</html>
