<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/tools.jsp"%>
<div class="oh h550">
	<h1 class="h1Tit borBot">
		<a class="ico1"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Label.New_Resources" /></a>
	</h1>
	<div class="featureContainer">
		<div class="feature">
			<div class="black">
				<div id="botton-scroll2">
					<ul class=featureUL>
						<c:forEach items="${list}" var="p" varStatus="index">
							<c:if test="${index.index % 8==0}"><li class=featureBox><div class="oh"></c:if>
							<div class="bookBlock">
								<c:if test="${p.cover!=null&&p.cover!='' }">
									<a href="${ctx}/pages/publications/form/article/${p.id}">
										<img src="${ctx}/pages/publications/form/cover?t=1&id=${p.id}" width="95" height="130" class="mr10 imgbor" />
									</a>
								</c:if>
								<c:if test="${p.cover==null||p.cover=='' }">
									<a href="${ctx}/pages/publications/form/article/${p.id}">
										<div class="imgMoren">
										<p>${p.title}</p>
										</div>
									</a>
								</c:if>
								
								<p class="bookTit">
									<a class="a_title" title="${p.title}" href="${ctx}/pages/publications/form/article/${p.id}">
										${fn:replace(fn:replace(fn:replace(p.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}
									</a>
								</p>
								<p class="bookTit">${p.author}</p>
							</div>
							<c:if test="${index.index!=0 && (index.index+1) % 8==0}"></div></li></c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
			<a class="prev2" href="javascript:void(0)">Previous</a>
			<a class="next2" href="javascript:void(0)">Next</a>
		</div>
	</div>
</div>