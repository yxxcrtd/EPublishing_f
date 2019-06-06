<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>



<h1 class="indexHtit">
	<span class="titFb"><a class="ico2" href="javascript:void(0)"><ingenta-tag:LanguageTag key="Global.Label.Service" sessionKey="lang" /></a></span>
</h1>

	<c:forEach items="${list}" var="p" varStatus="index">
			<p>
				<a href="javascript:void(0)">${p.title}</a><br /> <span>${p.content}</span>
			</p>
	</c:forEach>




 