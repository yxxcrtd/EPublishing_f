<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/tools.jsp"%>

<div class="oh h350">
	<h1 class="h1Tit borBot">
		<a class="ico1" ><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Label.Readable_Resource"/></a>
	</h1>
	<div class="featureContainer">
		<div class="feature">
			<div class="black h320" >
				<div id="botton-scroll1">
					<ul class=featureUL >
					 <c:forEach items="${list}" var="p" varStatus="index">
					 <c:if test="${index.index % 8==0}">
						<li class=featureBox>
						<!-- 一个滚动条内容开始 -->
						<div class="block jourList" >
					 </c:if>
						<div class="oh w350 fl" style="margin-bottom: 7px;">
		                    <div class="fl w40 mt2">
		                    	<c:if test="${p.free==2 && p.oa==2 || p.free==2}"><img src="${ctx}/images/ico/f.png" width="14" height="14" /></c:if>
		           				<c:if test="${p.oa==2 }"><img src="${ctx}/images/ico/o.png" width="14" height="14" /></c:if>
		           				<img src="${ctx}/images/ico/ico3.png" width="13" height="13" />
		                    </div>
	                        <div class="fl w300">
	                           <p>
	                           	<a class="a_title" title="${index.index}${p.title}" href="${ctx}/pages/publications/form/article/${p.id}" >
		                           <c:choose>  
									    <c:when test="${fn:length(p.title) > 22}">  
									        <c:out value="${fn:substring(p.title, 0, 22)}..." />  
									    </c:when>  
									   <c:otherwise>  
									      <c:out value="${p.title}" />  
									    </c:otherwise>  
									</c:choose> 
	                           	</a>
	                           </p>
	                           <p>${p.publisher.name}</p>
	                           <p><ingenta-tag:LanguageTag key="Page.Index.Index.Lable.JournalInfo" sessionKey="lang" />：<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" /> ${p.startVolume }-<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" /> ${p.endVolume }</p>
	                       </div>
						</div>
					 <c:if test="${index.index!=0 && (index.index+1) % 8==0}">
						</div> <!-- 一个滚动条内容结束 -->
						</li>
					</c:if>
					</c:forEach>
				</ul>
				</div>
			</div>
			<a class="prev1 chPrev1" style="top: -200px;" href="javascript:;">Previous</a>
			<a class="next1 chNext1" style="top: -200px;" href="javascript:;" >Next</a> 
		</div>
	</div>
</div>            
