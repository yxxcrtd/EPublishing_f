<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="mb30 oh">
        	<!--滚动条内容块开始-->
            <div class="outer jourOuter">
                <ul class="inner">
            <c:forEach items="${list }" var="list" varStatus="status">
            <li>
            <!-- 图片广告 -->
            <c:if test="${list.category==1 }"><a href="${list.url }" target="_blank"><img src="/upload/${list.file}" width="760" height="127" onerror="this.src='${ctx}/images/nopic.jpg'"/></a></c:if>
            <!-- 代码广告 -->
            <c:if test="${list.category==2 }"><a href="${list.url }" target="_blank">${list.content }</a></c:if>
            <!-- 文字广告 -->
            <c:if test="${list.category==3 }"><p class="f20 tc"><a href="${list.url }" target="_blank">${list.content }</a></p></c:if>
            </li>
            </c:forEach>
            </ul>
            <a class="prev" href="javascript:void(0)"></a>
            <a class="next" href="javascript:void(0)"></a>
            <div class="btn jourBtn">
                    <ul></ul>
            </div>
            </div>
            <!--滚动条内容块结束-->
</div>
