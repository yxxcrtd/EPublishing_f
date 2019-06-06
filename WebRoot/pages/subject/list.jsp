<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
		<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
		<%@ include file="/common/tools.jsp"%>
	<%@ include file="/common/ico.jsp"%></head>
	<body>
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--定义01 mainContainer 内容区开始-->
		<div class="mainContainer">
			<!--定义 0101 头部边框-->
			<div class="borderContainer">
				      <!--定义 0102 左边内容区域 开始-->
      <div class="leftContainer">
        <div class="listTitle"><ingenta-tag:LanguageTag key="Page.Publications.Subject.Lable.Theme" sessionKey="lang" /></div>
        
        <div class="clear"></div>
      
        <!--列表开始-->
        <div class="subjectList">
          <ul>
            <c:forEach items="${list }" var="p" varStatus="index">
          <li><span>${p.code }</span><a href="${ctx }/pages/subject/form/list?pCode=${p.code }&isLicense=${sessionScope.selectType}"><c:if test="${sessionScope.lang=='zh_CN' }">${p.name}</c:if><c:if test="${sessionScope.lang=='en_US' }">${p.nameEn }</c:if> [${p.countPP }]</a></li>
          </c:forEach>
          </ul>
        </div>
        <div class="clear"></div>
      </div>
				<!--定义 0102 左边内容区域 结束-->

				<!--定义 0103 右边内容区域 开始-->
				<%@ include file="/pages/frame/left.jsp"%>
				<!--定义 0103 右边内容区域 结束-->

			</div>
		</div>
		<div class="boderBottom"></div>
		<!--定义01 mainContainer 内容区结束-->
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>
