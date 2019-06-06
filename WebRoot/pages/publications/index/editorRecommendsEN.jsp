 <%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/tools.jsp"%>
 <!-- 编辑推送开始 -->
 <div class="oh h460">
 	<h1 class="h1Tit borBot"><a class="ico1"><ingenta-tag:LanguageTag key="Global.Label.Editor_Push" sessionKey="lang" /></a></h1>
	<div class="featureContainer">
		<div class="feature">
			<div class="black h450">
				 <div id="botton-scroll1">
					<ul class=featureUL>
						 <c:forEach items="${editorRecommendsList}" var="p" varStatus="index">
						 <c:if test="${index.index % 4==0}">
	                        <li class=featureBox>
	                            <!-- 一个滚动条内容开始 -->
	                     		<div class="oh">
	                     </c:if>
	                          		<div class="enBlock">
		                          		<h1><a class="a_title" title="${index.index}${p.publications.title}" href="${ctx}/pages/publications/form/article/${p.publications.id}"> ${fn:replace(fn:replace(fn:replace(p.publications.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}</a></h1>
		                              	<img src="${ctx}/pages/publications/form/cover?t=1&id=${p.publications.id}" onerror="this.src='${ctx}/images/noimg.jpg'"  width="95" height="129" class="fl mr10"/>
		                              	<c:if test="${not empty p.publications.author}">
										<p>By <c:set var="authors" value="${fn:split(p.publications.author,',')}" ></c:set>
											<c:forEach items="${authors}" var="a" >
												<a href='${ctx }/index/search?type=2&isAccurate=1&searchValue2=${a}'>${a}</a>&nbsp;
											</c:forEach>
										</p>
										</c:if>
										<c:if test="${not empty p.publications.publisher.name}">
										<p class="mt20"><a href='${ctx }/index/search?type=4&isAccurate=1&searchValue2=${p.publications.publisher.name }'>${p.publications.publisher.name}</a><c:if test="${fn:substring(p.publications.pubDate,0,4)!=null }">(${fn:substring(p.publications.pubDate,0,4) })</c:if></p>
										</c:if>
	                          		</div>
	                        <c:if test="${index.index!=0 && (index.index+1) % 4==0}">
	                      		</div>
	                     	 <!-- 一个滚动条内容结束 -->
	                        </li>
	                        </c:if>
                        </c:forEach>
					</ul>
                </div>
                <!-- /botton-scroll -->
		</div><!-- /block -->
	          <a class="prev1" href="javascript:;">Previous</a>
              <a class="next1" href="javascript:;">Next</a> 
		</div>
    <!-- /feature -->
	</div>            
</div>
  <!-- 编辑推送结束 -->