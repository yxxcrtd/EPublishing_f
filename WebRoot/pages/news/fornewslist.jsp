<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
    	<div class="newListTit">
            <h1 class="f14 mb5">${obj.title }</h1>
            <p>
            <ingenta-tag:LanguageTag key="Global.Label.PubDate" sessionKey="lang"/>：<fmt:formatDate value="${obj.createDate}" pattern="yyyy-MM-dd"/>     
            <ingenta-tag:LanguageTag key="Global.Label.Author"  sessionKey="lang"/>：${obj.author }     
            <ingenta-tag:LanguageTag key="Global.Label.NewsSource"  sessionKey="lang"/>：${obj.source }</p>
        </div>
        <div class="fontFam">
          ${obj.content }
        </div>