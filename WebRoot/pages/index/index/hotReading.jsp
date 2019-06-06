<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<h1 class="indexHtit">
	<span class="fl titFb"><a class="ico1" ><ingenta-tag:LanguageTag key="Global.Label.Hot_Reading_Resources" sessionKey="lang"/></a></span>
    <span class="fr"><!-- <a href="javascript:void(0)">more &gt;&gt;</a> --></span>
</h1>
<c:if test="${pubList!=null&&fn:length(pubList)>0 }">
	<c:forEach items="${pubList}" var="p" varStatus="index">
		<c:set var="license">${(p.publications.subscribedIp!=null||p.publications.subscribedUser!=null)&&(p.publications.subscribedIp>0||p.publications.subscribedUser>0) }</c:set>
		<c:set var="oa">${p.publications.oa!=null&&p.publications.oa==2 }</c:set>
		<c:set var="free">${p.publications.free!=null&&p.publications.free==2 }</c:set>
		<div class="block">
        	<div class="fl w40 mt2">
        		<c:if test="${license==false&&oa==false&&free==false }">
                <img src="${ctx}/images/ico/ico_close.png" width="16" height="16">
                </c:if>
                <c:if test="${license==true||oa==true||free==true }">
                <img src="${ctx}/images/ico/ico_open.png" width="16" height="16">
                </c:if>         
                <c:if test="${p.publications.type==1}"><img width="13" height="13" src="${ctx}/images/ico/ico4.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Book" sessionKey="lang" />" /></c:if>
				<c:if test="${p.publications.type==2 || p.publications.type==6|| p.publications.type==7}"><img width="13" height="13" src="${ctx}/images/ico/ico3.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Journal" sessionKey="lang" />" /></c:if>
				<c:if test="${p.publications.type==4 }"><img width="13" height="13" src="${ctx}/images/ico/ico5.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Article" sessionKey="lang" />" /></c:if>
            </div>
            <div class="fl w640">
                <p><a class="a_title" title="${p.publications.title}" href="${ctx}/pages/publications/form/article/${p.publications.id}">
                	${fn:replace(fn:replace(fn:replace(p.publications.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}
                	</a>
                </p>
                <c:if test="${not empty p.publications.author}">
					<p>By <c:set var="authors" value="${fn:split(p.publications.author,',')}" ></c:set>
			                <c:forEach items="${authors}" var="a" >
			                <a href='${ctx }/index/search?type=2&isAccurate=1&searchValue2=${a}'>${a}</a>&nbsp;
			                </c:forEach></p>
				</c:if>
				<c:if test="${not empty p.publications.publisher.name}">
					<p><a href='${ctx }/index/search?type=2&searchsType=4&searchValue2="${p.publications.publisher.name}"'>${p.publications.publisher.name}</a><c:if test="${fn:substring(p.publications.pubDate,0,4)!=null }">(${fn:substring(p.publications.pubDate,0,4) })</c:if></p>
				</c:if>
				<c:if test="${p.publications.type==2 && not empty p.publications.startVolume && not empty p.publications.endVolume}">
					<p>
					<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" /> ${p.publications.startVolume }-<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" /> ${p.publications.endVolume }
					</p>
				</c:if>
            </div>
        </div>
		</c:forEach>
</c:if>
<c:if test="${pubList==null||fn:length(pubList)<=0 }">
	<ingenta-tag:LanguageTag key="Global.Label.Prompt.No.Product" sessionKey="lang"/>
</c:if>