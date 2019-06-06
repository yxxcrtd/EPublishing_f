<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>



<h1 class="indexHtit">
	<span class="titFb"><a class="ico2" href="javascript:void(0)"><ingenta-tag:LanguageTag key="Global.Label.Service" sessionKey="lang" /></a></span>
</h1>

	<c:forEach items="${list}" var="list" varStatus="index">
			<p>
				<a class="a_title" href="${ctx }/pages/service/form/serviceList?serviceId=${list.id}">${list.title}</a><br /> 
			    <span class="omit w230">${list.content}</span>
			</p>
	</c:forEach>




 