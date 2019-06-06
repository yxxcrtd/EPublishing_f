<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
            <div class="outer">
            <ul class="inner">
            <c:forEach items="${list }" var="list" varStatus="status">
            <li>
            <!-- 图片广告 -->
            <c:if test="${list.category==1 }"><a href="${list.url }" target="_blank"><img src="/upload/${list.file}" width="1024" height="78" onerror="this.src='${ctx}/images/nopic.jpg'"/></a></c:if>
            <!-- 代码广告 -->
            <c:if test="${list.category==2 }"><a href="${list.url }" target="_blank">${list.content }</a></c:if>
            <!-- 文字广告 -->
            <c:if test="${list.category==3 }"><p class="f20 tc"><a href="${list.url }" target="_blank">${list.content }</a></p></c:if>
            </li>
            </c:forEach>
            </ul>
            <a class="prev" href="javascript:void(0)"></a>
            <a class="next" href="javascript:void(0)"></a>
            <div class="btn">
            <ul></ul>
            </div>
            </div>
