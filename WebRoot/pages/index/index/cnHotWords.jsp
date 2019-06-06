<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<c:if test="${list!=null&&fn:length(list)>0 }">
<c:forEach items="${list }" var="p" varStatus="index">
	<c:if test="${fn:length(p.activity)>30}">
		<a class="span9" href="javascript:void(0)" onclick="searchByCondition('searchValue','${p.activity }')" title="${p.activity }">
			${p.activity }
		</a>
	</c:if>
	<c:if test="${fn:length(p.activity)<=30}">
			<a class="span${index.index+1 }" href="javascript:void(0)" onclick="searchByCondition('searchValue','${p.activity }')" title="${p.activity }">
			${p.activity }
			</a>
	</c:if>
</c:forEach>
</c:if>
<c:if test="${list==null||fn:length(list)<=0 }">
<div>
<ingenta-tag:LanguageTag key="Global.Label.Prompt.No.HotWords" sessionKey="lang"/>
</div>
</c:if>
