<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/tools.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
    <title><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Error.Prompt.Title"/></title>
  </head>
  
  <body>
    <script type="text/javascript">
    $(document).ready(function(){
    	var content = "<ingenta-tag:LanguageTag key='Global.Page.error' sessionKey='lang' />";
    	<c:if test="${message!=null&&message!=''}">
    		content = '${message}';
    	</c:if>
    	<c:if test="${form.msg!=null&&form.msg!=''}">
    		content = '${form.msg}';
    	</c:if>
    	art.dialog.tips(content,3,'error');
    });
    </script>
  </body>
</html>
