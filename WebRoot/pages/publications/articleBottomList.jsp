<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="i" value="0"></c:set>

<c:if test="${toplist !=null}">
	<br />
	<div class="mt10">
		<h1 class="h1Tit borBot">
			<ingenta-tag:LanguageTag key="Pages.publications.article.Lable.Best" sessionKey="lang" />
		</h1>
		<div class="oh">
			<c:forEach items="${toplist}" var="p" varStatus="index">
				<c:if test="${currentId != p.id}"><c:set var="i" value="${i+1}"></c:set></c:if>
				<c:if test="${5 > i && currentId != p.id}">
					<div class="enBlock" <c:if test="${index.index==fn:length(toplist)-1}">style="border-bottom:none"</c:if>>
						<h1 class="newOmit"><a href="${ctx}/pages/publications/form/article/${p.id}">${p.title}</a></h1>
						<a href="${ctx}/pages/publications/form/article/${p.id}">
							<img src="${ctx}/pages/publications/form/cover?id=${p.id}" width="95" height="129" onerror="this.src='${ctx}/images/noimg.jpg'" class="fl mr10" />
						</a>
						<p class="mb10" style="width: 180px; height: 50px; overflow: hidden;" title="${p.author}">By ${p.author}</p>
						<p class="mb10">${fn:substring(p.date, 0, 4)}-${fn:substring(p.date, 4, 6)}<c:if test="${0 < fn:substring(p.date, 6, 8)}">-</c:if>${fn:substring(p.date, 6, 8)}</p>
						<p class="mb10"><a href="javascript:;" onclick="s('${p.publisher}')">${p.publisher}</a></p>
						<p class="chapt_author">
							<c:if test="${license==true}"><img src="${ctx}/images/eye.png" style="vertical-align: middle" /></c:if>
							<c:if test="${free==true}"><img src="${ctx}/images/ico/f.png" style="vertical-align: middle" /></c:if>
							<c:if test="${oa==true}"><img src="${ctx}/images/ico/o.png" style="vertical-align: middle" /></c:if>
						</p>
					</div>
				</c:if>
			</c:forEach>
		</div>
	</div>
</c:if>
<script type="text/ecmascript">
<!--
function s(s) {
	location.href=encodeURI("${request.contextPath}/index/search?type=2&searchsType=4&isAccurate=1&searchValue2=" + encodeURI(s));
}
//-->
</script>