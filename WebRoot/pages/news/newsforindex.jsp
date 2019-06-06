<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<h1 class="indexHtit">
	<span class="fl titFb"><a class="ico3"><ingenta-tag:LanguageTag key="Global.Label.News" sessionKey="lang"/></a></span>
	<span class="fr mt10 mr5 newListA"><a href="javascript:;"><img src="images/ico/ico12.png" /></a></span>
</h1>
<ul class="dot">
	<c:forEach items="${list }" var="list" varStatus="status">
		<li>
			<span class="fl omit w210"><a href="${ctx}/pages/news/form/newsList?newsId=${list.id}" title="${list.title}">${list.title}</a></span>
			<span class="fr f10"><fmt:formatDate value="${list.createDate}" pattern="yyyy-MM-dd"/></span>
		</li>
	</c:forEach>
</ul>