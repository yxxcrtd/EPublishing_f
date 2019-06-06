<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<c:forEach items="${typeStatistic}" var="p" varStatus="index">
	<li class="oh">
		<a href="javascript:void(0)" onclick="searchByCondition('type','${p.type}')">
		<c:if test="${p.type==1 }">
			<span class="fl">
				<img src="${ctx }/images/ico/ico4.png" />
			
				<ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" />
			</span>
		</c:if>
		<c:if test="${p.type==2 }">
			<span class="fl">
				<img src="${ctx }/images/ico/ico3.png" />
			
				<ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" />
			</span>
		</c:if>
		<c:if test="${p.type==3 }">
			<span class="fl">
				<img src="${ctx }/images/info.png" />
			
				<ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option3" sessionKey="lang" />
			</span>
		</c:if>
		<c:if test="${p.type==4 }">
			<span class="fl">
				<img src="${ctx }/images/ico5.png" />
			
				<ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option4" sessionKey="lang" />
			</span>
		</c:if>
		<c:if test="${p.type==5 }">
			<span class="fl">
				<img src="${ctx }/images/ico_04.png" />
			
				<ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option5" sessionKey="lang" />
			</span>
		</c:if>
		<span class="fr">[${p.id}]</span>
		</a>
	</li>
</c:forEach>