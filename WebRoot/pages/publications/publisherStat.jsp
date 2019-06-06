<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<c:forEach items="${pubStatistic}" var="p" varStatus="index">
	<c:if test="${index.index<=5 }">
		<li class="oh">
			<a href="javascript:void(0)" onclick="searchByCondition('publisher','${p.publisher.name}','${p.publisher.id}')" title="${p.publisher.name}">
				<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
				<span class="write">${p.publisher.name}</span><span class="fr">[${p.id}]</span>
			</a>
		</li>
	</c:if>
	<c:if test="${index.index>5 }">
		<li name="pubmore" style="display: none;" class="oh">
			<a href="javascript:void(0)" onclick="searchByCondition('publisher','${p.publisher.name}','${p.publisher.id}')" title="${p.publisher.name}">
				<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
				<span class="write">${p.publisher.name}</span><span class="fr">[${p.id}]</span>
			</a>
		</li>
		<c:if test="${(index.index+1)==fn:length(pubStatistic) }">
			<li class="oh">
				<a id="seepub" style="cursor:pointer" onclick="seePubMore();" class="more">
					<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span><span class="write">
					<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" />...</span>
				</a>
				<input type="hidden" id="morePubStatus" value="1"/>
			</li>
		</c:if>
	</c:if>
</c:forEach>