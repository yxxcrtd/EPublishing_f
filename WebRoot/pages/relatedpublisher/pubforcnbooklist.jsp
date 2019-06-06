<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

			<div class="logoList mt30">
				<h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Global.Label.RelatedPublisher" sessionKey="lang"/></h1>
				<c:forEach items="${list }" var="list" varStatus="status">
				<p>
				   <a href="${list.url }"><img src="/upload/${list.picPath }" width="175" height="72" onerror="this.src='${ctx}/images/nopic.jpg'"/></a>
				</p>
				</c:forEach>
			</div>
