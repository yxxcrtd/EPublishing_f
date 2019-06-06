<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
/* 	function toNewsList(){
		window.location.href="${ctx}/pages/publications/lastPubs?news=true";
	}; */
</script>
<h1 class="indexHtit">
             <span class="fl titFb"><a class="ico1"><ingenta-tag:LanguageTag key="Global.Label.New_Resources" sessionKey="lang"/></a></span>
             <span class="fr"><a href="${ctx}/index/advancedSearchSubmit?lcense=1&subFlag=1&newFlag=true" >more >></a></span>
             <%-- <span class="fr"><a href="${ctx}/pages/publications/lastMorePubs" >more >></a></span> --%>
        </h1>
		<c:if test="${list!=null&&fn:length(list)>0 }">
		<c:forEach items="${list}" var="p" varStatus="index">
		<c:set var="license">${(p.publications.subscribedIp!=null||p.publications.subscribedUser!=null)&&(p.publications.subscribedIp>0||p.publications.subscribedUser>0) }</c:set>
		<c:set var="oa">${p.publications.oa!=null&&p.publications.oa==2 }</c:set>
		<c:set var="free">${p.publications.free!=null&&p.publications.free==2 }</c:set>
				<div class="block">
	               	<div class="fl w30 mt2">
	                	
	                    <c:if test="${p.publications.type==1}"><img width="13" height="13" src="${ctx}/images/ico/ico4.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Book" sessionKey="lang" />" /></c:if>
						<c:if test="${p.publications.type==4 || p.publications.type==2}"><img width="13" height="13" src="${ctx}/images/ico/ico5.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Article" sessionKey="lang" />" /></c:if>
					
	               	</div>
                    <div class="fl w640">
	                    <p>
	                    <a class="a_title" title="${p.publications.title}" href="${ctx}/pages/publications/form/article/${p.publications.id}">
	                		${fn:replace(fn:replace(fn:replace(p.publications.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}
	                	</a>
	                	</p>
	                	<c:if test="${not empty p.publications.author}">
					<p>
						By
						<c:set var="authors" value="${fn:split(p.publications.author,',')}"></c:set>
						<c:forEach items="${authors}" var="a">
							<a
								href='${ctx }/index/search?type=2&isAccurate=1&searchValue2=${a}'>${a}</a>&nbsp;
			                </c:forEach>
					</p>
				</c:if>
				<c:if test="${not empty p.publications.publisher.name}">
							<p><a href='${ctx }/index/search?type=2&searchsType=4&searchValue2="${p.publications.publisher.name}"'>${p.publications.publisher.name}</a><c:if
									test="${not empty fn:substring(p.publications.pubDate,0,4)}">(${fn:substring(p.publications.pubDate,0,4) })</c:if>
							</p>
						</c:if>
                    </div>
                </div>
		</c:forEach>
		</c:if>
<c:if test="${list==null||fn:length(list)<=0 }">
	<ingenta-tag:LanguageTag key="Global.Label.Prompt.No.Product" sessionKey="lang"/>
</c:if>	