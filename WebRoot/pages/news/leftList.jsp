<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
	
    	 <h1 class="f14"><ingenta-tag:LanguageTag key="Global.Label.News" sessionKey="lang"/></h1>
    	 <ul class="newsdot">
    	 <c:forEach items="${list }" var="list" varStatus="status">
    	 <li>
    	 <span>
    	 <a href="javascript:void(0)" onclick="getNewsById('${list.id}')" id="${list.id }" <c:if test="${list.id==form.newsId}">class="blank"</c:if>>${list.title }</a></span><span class="fr f10"><fmt:formatDate value="${list.createDate}" pattern="yyyy-MM-dd"/>
    	 </span>
    	 </li>
    	 </c:forEach>
         </ul>
         <c:set var="tPages" >
         <c:if test="${form.count%form.pageCount==0 }"><fmt:formatNumber type="number" value="${form.count/form.pageCount}"/></c:if>
         <c:if test="${form.count%form.pageCount!=0 }"><fmt:formatNumber type="number" value="${(form.count-form.count%form.pageCount)/form.pageCount+1}"/></c:if>
         </c:set>
         <span class="number tr">
         <c:if test="${form.curpage!=0 }"><a href="javascript:void(0)" onclick="changeNews('${form.curpage-1}')"><img src="${ctx }/images/prev.gif" class="vm"/>上一页</a></c:if>
         <c:if test="${tPages>form.curpage+1 }">
         <a href="javascript:void(0)" onclick="changeNews('${form.curpage+1}')">下一页<img src="${ctx }/images/next.gif" class="vm"/></a>
         </c:if>
         </span>
