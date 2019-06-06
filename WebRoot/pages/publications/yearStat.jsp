<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<c:forEach items="${yearStatistic}" var="p" varStatus="index">
	<c:if  test="${p.pubDate!=null && fn:length(p.pubDate)==4}">
		<c:if test="${index.index<=5 }">
			<li class="oh">
				<a href="javascript:void(0)" onclick="searchByCondition('pubDate','${p.pubDate }')">
          			<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
          			<span class="write">${p.pubDate}</span><span class="fr">[${p.id}]</span>
          		</a>
			</li>
		</c:if>
		<c:if test="${index.index>5 }">
			<li name="yearmore" style="display: none;" class="oh">
				<a href="javascript:void(0)" onclick="searchByCondition('pubDate','${p.pubDate }')">
	       			<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
	       			<span class="write">${p.pubDate}</span><span class="fr">[${p.id}]</span>
          		</a>
			</li>
			<c:if test="${(index.index+1)==fn:length(yearStatistic) }">
				<li class="oh">
				<a id="seeyear" style="cursor:pointer" onclick="seeYearMore();" class="more">
					<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
					<span class="write"><ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" />...</span>
				</a>
				<input type="hidden" id="moreYearStatus" value="1"/>
				</li>
			</c:if>
		</c:if>
	</c:if>
</c:forEach>