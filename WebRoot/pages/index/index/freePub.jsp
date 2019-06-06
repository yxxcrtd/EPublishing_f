<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
		<h1 class="indexHtit">
             <span class="fl titFb"><a class="ico1" ><ingenta-tag:LanguageTag key="Global.Label.freePub" sessionKey="lang"/></a></span>
             <span class="fr"><a href="${ctx}/free?allfree=true" >more >></a></span>
             <!-- <span class="fr"><a href="javascript:void(0)" onclick="toFreeList()">more >></a></span> -->
        </h1>
		<c:if test="${list!=null&&fn:length(list)>0}">
		<c:forEach items="${list}" var="p" varStatus="index">
				<div class="block">
	               	<div class="fl w40 mt2">
	               		<img src="${ctx}/images/ico/f.png" width="16" height="16" />
	                    <c:if test="${p.type==1}"><img width="13" height="13" src="${ctx}/images/ico/ico4.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Book" sessionKey="lang" />" /></c:if>
				        <c:if test="${p.type==2 || p.type==6|| p.type==7}"><img width="13" height="13" src="${ctx}/images/ico/ico3.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Journal" sessionKey="lang" />" /></c:if>
				        <c:if test="${p.type==4||p.type==3 }"><img width="13" height="13" src="${ctx}/images/ico/ico5.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Article" sessionKey="lang" />" /></c:if>
	               	</div>
                    <div class="fl w640">
	                    <p>
	                    <a class="a_title" title="${p.title}" href="${ctx}/pages/publications/form/article/${p.id}">
	                		${fn:replace(fn:replace(fn:replace(p.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}
	                	</a>
	                	</p>
				<c:if test="${not empty p.author}">
					<p>By <c:set var="authors" value="${fn:split(p.author,',')}" ></c:set>
			                <c:forEach items="${authors}" var="a" >
			                <a href='${ctx }/index/search?type=2&isAccurate=1&searchValue2=${a}'>${a}</a>&nbsp;
			                </c:forEach></p>
				</c:if>
				<p>
					<a href="${ctx}/index/search?type=4&isAccurate=1&searchValue2=${p.publisher.name}">${p.publisher.name}</a>
					<c:if test="${not empty fn:substring(p.pubDate,0,4)}">(${fn:substring(p.pubDate,0,4) })</c:if>
				</p>
				<c:if test="${p.type==2 && not empty p.startVolume && not empty p.endVolume}">
				<p>
				<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" /> ${p.startVolume }-<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" /> ${p.endVolume }
				</p>
				</c:if>
			</div>
                </div>
		</c:forEach>
		</c:if>
		<c:if test="${list==null||fn:length(list)<=0 }">
				<ingenta-tag:LanguageTag key="Global.Label.Prompt.No.Product" sessionKey="lang"/>
		</c:if>