<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
    <title><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Error.Prompt.Title"/></title>
    <%@ include file="/common/tools.jsp"%>
	<%@ include file="/common/ico.jsp"%>
  <style type="text/css">
.search_word ul{
background:#f8f8f8;
border-left:1px solid #bcbcbc;
border-right:1px solid #bcbcbc;
border-bottom:1px solid #bcbcbc;
width:86px;
display:none;
position:absolute;
z-index:5;
margin-left:380px;
margin-top:55px;
margin-left:380px\9;
>margin-left/*IE5.5*/:-105px;
>margin-top/*IE5.5*/:53px;
}
*+html .search_word ul{
margin-left:-103px;
margin-top:53px;
}
@media screen and (-webkit-min-device-pixel-ratio:0){.search_word ul{margin-left:380px;}}
.search_help ul{
margin-top:-7px;
margin-left:484px;
margin-left:484x\9;
_margin-left:-93px;
_margin-top:56px;
}
*+html .search_help ul{
margin-left:-92px;
margin-top:55px;
}
@media screen and (-webkit-min-device-pixel-ratio:0){.search_help ul{margin-left:484px;}}
</style>
  </head>
  
  <body>
<jsp:include page="/pages/header/headerData" />
	<!--定义01 mainContainer 内容区开始-->
	<div class="main" style="width : 1000px; height: 350px;">
		<div class="login" style="width: 800px; float: left;">
			<h1>
				<c:if test="${prompt!=null}">${prompt}</c:if>
				<c:if test="${prompt==null}">
					<ingenta-tag:LanguageTag sessionKey="lang"
						key="Pages.Error.Prompt.Title" />
				</c:if>
			</h1>

			<div class="zhuce_xinxi" style="text-align: center;float:none;padding-top: 100px;">
				<p>&nbsp;</p>
				<p class="p_mail">
					<c:if test="${message!=null}">${message}</c:if>
					<c:if test="${form.msg!=null}">${form.msg}</c:if>
				</p>
			</div>

		</div>
		<!--左侧内容结束-->
		
		<!-- 右侧列表开始 -->
		<div style="width:180px; float: right;">
			<c:if test="${sessionScope.mainUser!=null}">
				<c:if test="${mid!=null}"><jsp:include
						page="/pages/menu?mid=${mid}" flush="true" /></c:if>
				<c:if test="${mid==null}"><jsp:include page="/pages/menu"
						flush="true" /></c:if>
			</c:if>
<%-- 			<c:if test="${sessionScope.mainUser==null}"><%@ include --%>
<%-- 					file="/pages/frame/login.jsp"%></c:if> --%>
		</div>
		<!-- 右侧列表结束 -->
	</div>
	<!--以下 提交查询Form 开始-->
	<c:if test="${form==null }">
		<form action="" method="post" name="form" id="formList">
			<input type="hidden" name="searchsType" id="type1" /> <input
				type="hidden" name="searchValue" id="searchValue1" /> <input
				type="hidden" name="pubType" id="pubType1" /> <input type="hidden"
				name="publisher" id="publisher1" /> <input type="hidden"
				name="pubDate" id="pubDate1" /> <input type="hidden" name="taxonomy"
				id="taxonomy1" /> <input type="hidden" name="taxonomyEn"
				id="taxonomyEn1" /> <input type="hidden" name="searchOrder"
				id="order1" /> <input type="hidden" name="lcense" id="lcense1" /> <input
				type="hidden" name="code" id="code1" /> <input type="hidden"
				name="pCode" id="pCode1" /> <input type="hidden" name="publisherId"
				id="publisherId1" /> <input type="hidden" name="subParentId"
				id="subParentId1" /> <input type="hidden" name="parentTaxonomy"
				id="parentTaxonomy1" /> <input type="hidden" name="parentTaxonomyEn"
				id="parentTaxonomyEn1" />
		</form>
	</c:if>
	<c:if test="${form!=null }">
		<form:form action="${form.url}" method="post" modelAttribute="form"
			commandName="form" name="formList" id="formList">
			<form:hidden path="searchsType" id="type1" />
			<form:hidden path="searchValue" id="searchValue1" />
			<form:hidden path="pubType" id="pubType1" />
			<form:hidden path="language" id="language1" />
			<form:hidden path="publisher" id="publisher1" />
			<form:hidden path="pubDate" id="pubDate1" />
			<form:hidden path="taxonomy" id="taxonomy1" />
			<form:hidden path="taxonomyEn" id="taxonomyEn1" />
			<form:hidden path="searchOrder" id="order1" />
			<form:hidden path="lcense" id="lcense1" />

			<form:hidden path="code" id="code1" />
			<form:hidden path="pCode" id="pCode1" />
			<form:hidden path="publisherId" id="publisherId1" />
			<form:hidden path="subParentId" id="subParentId1" />
			<form:hidden path="parentTaxonomy" id="parentTaxonomy1" />
			<form:hidden path="parentTaxonomyEn" id="parentTaxonomyEn1" />
		</form:form>
	</c:if>
	<!--以上 提交查询Form 结束-->

	<!-- 底部的版权信息 -->
	<c:if test="${sessionScope.lang == 'zh_CN'}">
		<div id="footer_zh_CN"></div>
	</c:if>
	<c:if test="${sessionScope.lang == 'en_US'}">
		<div id="footer_en_US"></div>
	</c:if>
</body>
</html>
