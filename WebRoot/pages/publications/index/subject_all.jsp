<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<c:if test="${list!=null&&fn:length(list)>0 }">
	<div class="leftClassity">
		<h1 class="h1Tit borBot">
			<ingenta-tag:LanguageTag key="Global.Label.Subject_Categories"
				sessionKey="lang" />
		</h1>
		<ul>
			<c:forEach items="${list }" var="p" varStatus="index">
			
				<li><c:if test="${sessionScope.lang=='en_US' }">
						<a href="javascript:void(0)" onclick="advancedSubmit('${p.code} ${p.nameEn }')"
							title="${p.code } ${p.nameEn}"> <span class="alph">${p.code }</span>
							<span class="write"> ${p.nameEn }<!-- [${p.countPP }] -->
						</span>
						</a>
					</c:if> <c:if test="${sessionScope.lang=='zh_CN' }">
						<a href="javascript:void(0)" onclick="advancedSubmit('${p.code} ${p.name }')"
							title="${p.code } ${p.name}"> <span class="alph">${p.code }</span>
							<span class="write"> ${p.name}<!-- [${p.countPP }] -->
						</span>
						</a>
					</c:if></li>
			</c:forEach>
		</ul>
	</div>
</c:if>
<c:if test="${list==null||fn:length(list)<=0 }">
	<li><ingenta-tag:LanguageTag key="Global.Label.Prompt.No.Subject"
			sessionKey="lang" /></li>
</c:if>
