<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

			<div class="logoList">
				<h1 class="h1Tit borBot">
					<a href="javascript:void(0)"><ingenta-tag:LanguageTag key="Global.Label.RelatedPublisher" sessionKey="lang"/></a>
				</h1>
				<p>
				<c:forEach items="${list }" var="list" varStatus="status" begin="0" end="3">
				<span><a href="javascript:void(0)"><img
							src="/upload/${list.picPath }" width="175" height="72" onerror="this.src='${ctx}/images/nopic.jpg'"/></a></span>
				</c:forEach>
				</p>
				<c:if test="${fn:length(list)>4 }">
				<p>
				<c:forEach items="${list }" var="list" begin="4" end="7" varStatus="status">
					<span><a href="${list.url }"><img src="/upload/${list.picPath }" width="175" height="72" onerror="this.src='${ctx}/images/nopic.jpg'"/></a></span>
				</c:forEach>
				</p>
				</c:if>
				 
				
			</div>
