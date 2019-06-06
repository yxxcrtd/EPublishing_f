<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglibs.jsp"%>
<html>
  <head>
    <title>Redirect</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  <%@ include file="/common/ico.jsp"%></head>
  <body>
  	<% 
  		String msg = request.getAttribute("errmsg").toString();
  		if(msg!=null){
  			String[] msgs = msg.split(";");
  			request.setAttribute("prompt",cn.com.daxtech.framework.Internationalization.Lang.getLanguage(msgs[0],request.getSession().getAttribute("lang").toString()));
  			request.setAttribute("message",cn.com.daxtech.framework.Internationalization.Lang.getLanguage(msgs[1],request.getSession().getAttribute("lang").toString()));
  			RequestDispatcher rd = request.getRequestDispatcher("frame/result.jsp");
  			rd.forward(request,response);
  		}else{
  			response.sendRedirect("index");
  		}
  	%>
  </body>
</html>
