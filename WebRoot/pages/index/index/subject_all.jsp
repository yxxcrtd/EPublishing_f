<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<c:if test="${list!=null&&fn:length(list)>0 }">
<%for(int i=1;i<=3;i++){
	int begin =0;
	int end = 0;
	if(i==1){
		begin = 0;
		end = 6;
	}else{
		begin = (i-1)*8-1;
		end = i*8-2;
	}
%>
	<div class="liClass">
	<ul>
	<c:forEach items="${list }" var="p" varStatus="index" begin="<%=begin %>" end="<%=end %>">
		<li>
			<c:if test="${sessionScope.lang=='en_US' }">
			<a href="javascript:void(0)" onclick="advancedSubmit('${p.code} ${p.nameEn }')" title="${p.code } ${p.nameEn}">
				<span class="alph">${p.code }</span>
				<span class="write">
				${p.nameEn }
				</span>
			</a>
			</c:if>
			<c:if test="${sessionScope.lang=='zh_CN' }">
			<a href="javascript:void(0)" onclick="advancedSubmit('${p.code} ${p.name }')" title="${p.code } ${p.name}">
				<span class="alph">${p.code }</span>
				<span class="write">
				${p.name}
				</span>
			</a>
			</c:if>
		</li>
	</c:forEach>
	</ul>
	</div>
<%
}%>

</c:if>
<c:if test="${list==null||fn:length(list)<=0 }">
<li>
<ingenta-tag:LanguageTag key="Global.Label.Prompt.No.Subject" sessionKey="lang"/>
</li>
</c:if>
