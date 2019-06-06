<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%-- 
<c:if test="${code!=null&&code!=''&&pCode!=code }">
<li><a href="${ctx }/pages/subject/form/list?pCode=${code}&type=${ptype}&code=&pubYear=${pubYear}&publisherId=${publisherid}&order=${order}&isLicense=${isLicense}&r_=${r_}"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Publications.List.Link.Up"/></a></li>
</c:if> 
--%>
<!-- searchByCondition('taxonomyEn','搜索用到的分类','pCode','code','subParentId','parent搜索用到的分类') -->
<c:if test="${statistic!=null&&fn:length(statistic)>0}">
<c:if test="${sessionScope.lang=='en_US' }">
	<c:forEach items="${statistic}" var="s" varStatus="index">
		<c:if test="${index.index<=5 }">
		<li class="oh">
			<a href="javascript:void(0)" onclick="searchByCondition('taxonomyEn','${s.code} ${s.nameEn }','${s.code}','${code }','${subjectId }','${parentTaxonomyEn }')" title="${s.code} ${s.nameEn }">
				<span class="alph">${s.code}</span>
				<span class="write">${s.nameEn }</span><span class="fr">[${s.type}]</span>
			</a>
		</c:if>
		<c:if test="${index.index>5 }">
			<li name="more" style="display: none;" class="oh">
				<a href="javascript:void(0)" onclick="searchByCondition('taxonomyEn','${s.code} ${s.nameEn }','${s.code}','${code }','${subjectId }','${parentTaxonomyEn }')" title="${s.code} ${s.nameEn }">
					<span class="alph">${s.code}</span>
					<span class="write">${s.nameEn }</span><span class="fr">[${s.type}]</span>
				</a>
			<c:if test="${(index.index+1)==fn:length(statistic) }">
				<li class="oh">
					<a id="see" style="cursor:pointer" onclick="seeSubMore();" class="more">
						<span class="alph"><img src="${ctx }/images/jiantou.png" /></span>
						<span class="write">
							<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" />...
						</span>
					</a>
					<input type="hidden" id="moreStatus" value="1"/>
				</li>
			</c:if>
		</c:if>
	</c:forEach>
</c:if>
<c:if test="${sessionScope.lang=='zh_CN' }">
	<c:forEach items="${statistic}" var="s" varStatus="index">
		<c:if test="${index.index<=5 }">
		<li class="oh">
			<a href="javascript:void(0)" onclick="searchByCondition('taxonomy','${s.code} ${s.name }','${s.code}','${code }','${subjectId }','${parentTaxonomy }')" title="${s.code} ${s.name }">
				<span class="alph">${s.code}</span>
				<span class="write">${s.name }</span><span class="fr">[${s.type}]</span>
			</a>
		</c:if>
		<c:if test="${index.index>5 }">
			<li name="more" style="display: none;" class="oh">
				<a href="javascript:void(0)" onclick="searchByCondition('taxonomyEn','${s.code} ${s.name }','${s.code}','${code }','${subjectId }','${parentTaxonomy }')" title="${s.code} ${s.name }">
					<span class="alph">${s.code}</span>
					<span class="write">${s.name }</span><span class="fr">[${s.type}]</span>
				</a>
			<c:if test="${(index.index+1)==fn:length(statistic) }">
				<li class="oh">
					<a id="see" style="cursor:pointer" onclick="seeSubMore();" class="more">
						<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
						<span class="write">
							<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" />...
						</span>
					</a>
					<input type="hidden" id="moreStatus" value="1"/>
				</li>
			</c:if>
		</c:if>
	</c:forEach>
</c:if>
</c:if>