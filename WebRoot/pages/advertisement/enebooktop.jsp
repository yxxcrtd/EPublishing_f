<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="mb30">
	        <!-- 图片广告 -->
            <c:if test="${list[0].category==1 }"><a href="${list[0].url }" target="_blank"><img src="/upload/${list[0].file}" width="760" height="115" onerror="this.src='${ctx}/images/nopic.jpg'"/></a></c:if>
            <!-- 代码广告 -->
            <c:if test="${list[0].category==2 }"><a href="${list[0].url }" target="_blank">${list[0].content }</a></c:if>
            <!-- 文字广告 -->
            <c:if test="${list[0].category==3 }"><p class="f20 tc"><a href="${list[0].url }" target="_blank">${list[0].content }</a></p></c:if>
</div>
