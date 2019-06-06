<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="oh">
<c:forEach items="${downList }" var="down" varStatus="status">
                <div class="cartListDiv">
                <img src="${ctx}/pages/publications/form/cover?t=1&id=${down.id}" width="61" height="81" onerror="this.src='${ctx}/images/smallimg.jpg'" class="imgbor fr ml30" />
                <p class="pTit"><a name="title" href="${ctx}/pages/publications/form/article/${down.id}?fp=3" title="${down.title }">${down.title }</a></p>
                <c:if test="${down.author!=null}">
                <p>By  
                <c:set var="authors" value="${fn:split(down.author,',')}" ></c:set>
			                <c:forEach items="${authors}" var="a" >
			                <a href='${ctx }/index/search?type=2&isAccurate=1&searchValue2=${a}'>${a}</a> &nbsp;
			                </c:forEach>
			    </p>
			    </c:if>
                <p>
                <c:if test="${sessionScope.lang==null||sessionScope.lang=='zh_CN'}">
                <a href='${ctx }/index/search?type=2&isAccurate=1&searchValue2=${down.publisher.name }'>${down.publisher.name }</a>
                </c:if>
                <c:if test="${sessionScope.lang=='en_US'}">
                <a href='${ctx }/index/search?type=2&isAccurate=1&searchValue2=${down.publisher.nameEn }'>${down.publisher.nameEn }</a>
                </c:if>
                   (${fn:substring(down.pubDate,0,4) })</p>
            </div>
    </c:forEach>
</div>