<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%-- <c:if test="${list!=null}">
<c:forEach items="${list }" var="p" varStatus="index">
	<c:if test="${p.key==list[0].key}">
	<h2 style="margin-bottom:auto;">${p.key}<span>${p.val+25000}</span></h2>
	</c:if>
	<c:if test="${p.key!=list[0].key}">	
	<p class="data">
		<a href="${ctx}/index/advancedSearchSubmit?pubType=${p.type}">
			<span class="d_img" style="width:${p.width}px"></span>
			<span class="d_name">${p.key}</span>
			<c:if test="${p.type==1 }"><span class="d_number">${p.val+25000}</span></c:if>
			<c:if test="${p.type!=1 }"><span class="d_number">${p.val}</span></c:if>
		</a>
	</p>
	</c:if>
</c:forEach>
</c:if> --%>
<%--以上是因为要从DCC取同步资源总数，所以屏蔽改用下边表单 --%>



 <p class="mb5"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Count.Lable.Total"/> <span>${sumCount+85000}</span></p>
 <p class="data">
		<a href="${ctx}/index/advancedSearchSubmit?pubType=${book}">
			<span class="d_img" style="width:${bwidth}px"></span>
			<span class="d_name"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Index.Lable.Book"/></span>
			<span class="d_number">${bookCount+85000}</span>
		</a>
</p>
 <p class="data">
		<a href="${ctx}/index/advancedSearchSubmit?pubType=${journals}">
			<span class="d_img" style="width:${jwidth}px"></span>
			<span class="d_name"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Index.Lable.Journal"/></span>
			<span class="d_number">${journalsCount}</span>
		</a>
</p>
 <p class="data">
		<a href="${ctx}/index/advancedSearchSubmit?pubType=${article}">
			<span class="d_img" style="width:${awidth}px"></span>
			<span class="d_name"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Index.Lable.Article"/></span>
			<span class="d_number">${articleCount}</span>
		</a>
</p> 
