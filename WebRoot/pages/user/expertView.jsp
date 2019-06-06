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
</head>
<body>
<div class="big">
  <jsp:include page="/pages/header/headerData" flush="true" />
  <!--定义01 main 内容区开始-->
  <div class="main">
  
      <!--定义 0102 左边内容区域 开始-->
      <div class="tablepage">
      	<h1><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertDetail.Label.title'/></h1>
        
        <!--列表开始-->
        <div class="page_table">
		        	<table cellspacing="0" cellpadding="0" style="width:100%;">
			            <tr>
					        <td class="atdbody01" style="text-align:right;padding-right:10px;"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertDetail.Label.name'/></td>
					        <td class="btdbody01" style="padding-left:10px;">${form.userName}</td>
						 </tr>
			             <c:forEach items="${list}" var="c" varStatus="index">
			             	<c:if test="${c.code!='emailCheck'&&c.code!='freetax'&&c.code!='institutionId'}">
								<tr>
								<td class="atdbody01" style="text-align:right;padding-right:10px;"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：</td>
								<td class="btdbody01" style="padding-left:10px;">${c.val}</td>
								</tr>
							</c:if>				
							<c:if test="${c.code=='institutionId'}">
								<tr>
								<td class="atdbody01" style="text-align:right;padding-right:10px;"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：</td>
								<td class="btdbody01" style="padding-left:10px;">${form.institutionName}</td>
								</tr>								
							</c:if>
			              </c:forEach>
				
			             </tbody>
			             
					</table>
	        	</div>
      
      </div>
      <!--定义 0102 左边内容区域 结束-->
      
      <!--定义 0103 右边内容区域 开始-->
		<jsp:include page="/pages/menu?mid=user_manager" flush="true" />
		<!--定义 0103 右边内容区域 结束-->
    
  </div>
 
  <!--定义01 mainContainer 内容区结束-->
 		 <!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
  <input type="hidden" name="userType" value="1"/><!-- 用户类型为1：个人用户 -->
  </div>
</body>
</html>
