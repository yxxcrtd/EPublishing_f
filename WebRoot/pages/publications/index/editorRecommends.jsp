<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/tools.jsp"%>

 <!-- 编辑推荐 -->
 <div class="oh h550">
	<h1 class="h1Tit borBot"><a class="ico1"><ingenta-tag:LanguageTag key="Global.Label.Editor_Push" sessionKey="lang" /></a></h1>
	<div class="featureContainer">
		<div class="feature">
			<div class="black">
				<div id="botton-scroll1">
					<ul class=featureUL>
						<c:forEach items="${editorRecommendsList}" var="p" varStatus="index">
							<c:if test="${index.index % 8==0}">
								<li class=featureBox>
									<div class="oh">
							</c:if>
							
							<div class="bookBlock">
								<img src="<#if p.cover??>${request.contextPath}/pages/publications/form/cover?t=1&id=${p.publications.id}<#else>${request.contextPath}/images/noimg.jpg</#if>" width="95" height="130" class="mr10 imgbor"/> 
								<p class="bookTit"><a class="a_title" title="${index.index}${p.publications.title}" href="${ctx}/pages/publications/form/article/${p.publications.id}">${fn:replace(fn:replace(fn:replace(p.publications.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}</a></p>
                                <p class="bookTit">${p.publications.author }</p>
							</div>
							
							<c:if test="${index.index!=0 && (index.index+1) % 8==0}">
									</div>
								</li>
							</c:if>
						</c:forEach >
					</ul>
				</div>
			</div>
			<a class="prev1" href="javascript:;">Previous</a>
			<a class="next1" href="javascript:;">Next</a> 
		</div>
	</div>            
</div>
