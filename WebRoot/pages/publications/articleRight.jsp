<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="i" value="0"></c:set>

<div class="prodRight f14">
	<h1 class="fb"><ingenta-tag:LanguageTag key="Page.Publications.Collection.Also" sessionKey="lang" /></h1>
	<c:choose>
		<c:when test="${null != list && 0 < fn:length(list)}">
			<c:forEach items="${list}" var="list" varStatus="status">
				<c:if test="${currentId != list.id}"><c:set var="i" value="${i+1}"></c:set></c:if>
				<c:if test="${currentId == list.id}"><c:set var="a" value="${i+1}"></c:set></c:if>
				<c:if test="${7 > i && currentId != list.id}">
					<div class="mt15">
						<a href="${ctx}/pages/publications/form/article/${list.id}">
							<img src="${ctx}/pages/publications/form/cover?t=2&id=${list.id}" width="95" height="129" onerror="this.src='${ctx}/images/noimg.jpg'" />
						</a>
						<p><a title="${list.title}" href="${ctx}/pages/publications/form/article/${list.id}">${list.title}</a></p>
					</div>
				</c:if>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<h1 class="fb"><br /><ingenta-tag:LanguageTag key="Page.Publications.Collection.Also.Nil" sessionKey="lang" /></h1>
		</c:otherwise>
	</c:choose>
</div>