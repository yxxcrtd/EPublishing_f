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
	<form:form action="myrecommend" method="post" commandName="form" id="form" >
		
		<!--定义01 mainContainer 内容区开始-->
		<div class="mainContainer">
			<!--定义 0101 头部边框-->
			<div class="borderContainer">
				<!--定义 0102 左边内容区域 开始-->
				<div class="leftContainer">
					<div class="clear"></div>
					<div class="clear"></div>
					<div class="accountbox">
						<div class="accountfont"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Statistics.title"/>推荐信息统计</div>
						<div class="clear"></div>
						<div class="accountdetail">
							<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Statistics.Label.status"/>推荐状态：
							<form:select path="isOrder" >
								<form:option value=""><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Page.Select.All"/></form:option>
								<form:option value="1"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Statistics.Label.nobuy"/>未购买</form:option>
								<form:option value="2"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Statistics.Label.buy"/>已购买</form:option>				        		
				        	</form:select>
				        	<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Statistics.Label.pubName"/>出版物名称：
				        	<form:input path="pubTitle" />
				        	<blockquote><a class="a_gret" onclick="queryList()"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.Search"/></a></blockquote>
						</div>
					</div>
					<div class="clear"></div>
					<div class="accountbox">
						<div class="accountdetail">
							<table align="left" cellpadding="0"	cellspacing="0" width="100%">
						        <tr class="ordertitle">
							        <td><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Recommend.Table.Lable.Name"/></td>
							        <td><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Recommend.Table.Lable.ISBN"/></td>
							        <td><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Recommend.Table.Lable.RCount"/></td>
							        <td><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Recommend.Table.Lable.IsOrder"/></td>					        	
						        </tr>						
						        <c:forEach items="${list }" var="r" varStatus="index">
							        <tr class="<c:if test="${index.index%2 == 1}">ordercontent</c:if><c:if test="${index.index%2 == 0}">ordercontent gray</c:if>">
								        <td><a href="${ctx}/pages/publications/form/article/${r.publications.id}">${r.publications.title}</a></td>
								        <td>${r.publications.code}</td>
								        <td><a href="${ctx}/pages/user/form/myrecommend/detail/${r.id}">${r.recommendDetailCount}</a></td>
								        <td>
								        	<c:if test="${r.isOrder==1}"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Statistics.Label.nobuy"/></c:if>
								            <c:if test="${r.isOrder==2}"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Statistics.Label.buy"/></c:if>
								        </td>								      							        
							        </tr>
						        </c:forEach>						     
					        </table>
				        </div>
					</div>
		
				<div>
				<zhima-tag:SplitTag page="${form.curpage}" pageCount="${form.pageCount}" count="${form.count}" formName="form" cssName="turnPageBottom" method="post"/>
				</div>
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
	</form:form>	
	</body>
</html>
