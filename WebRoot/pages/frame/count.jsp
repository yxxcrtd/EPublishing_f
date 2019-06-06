<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<c:forEach items="${pubCountList}" var="c" varStatus="index">
	<c:if test="${index.index<=5 }">
		<li>${c.saleDate} --------- ${c.total}</li>
	</c:if>
	<c:if test="${index.index>5 }">
		<li name="countmore" style="display: none;">
			${c.saleDate} --------- ${c.total}
		</li>
		<c:if test="${(index.index+1)==fn:length(pubCountList) }">
			<li><a id="seecount" style="cursor:pointer" onclick="seeCountMore();" class="more"><ingenta-tag:LanguageTag key="Page.Frame.Count.Lable.More" sessionKey="lang" />...</a>
			<input type="hidden" id="moreCountStatus" value="1"/>
			</li>
		</c:if>
	</c:if>
</c:forEach>
<li>
	<span><ingenta-tag:LanguageTag key="Page.Frame.Count.Lable.Total" sessionKey="lang" />: ${form.count+90000}</span><!-- 216260 -->
</li>
		

