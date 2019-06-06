<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/tools.jsp"%>

        <!-- 热读资源开始 -->
        <div class="oh h550">
        	<h1 class="h1Tit borBot"><a class="ico1" href="javascript:void(0)"><ingenta-tag:LanguageTag key="Global.Label.Hot_Reading_Resources" sessionKey="lang" /></a></h1>
            <div class="featureContainer">
                <div class="feature">
                    <div class="black">
                        <div id="botton-scroll3">
                            <ul class=featureUL>
                               <c:forEach items="${hotlist}" var="p" varStatus="index">
							<c:if test="${index.index % 8==0}">
                              <li class=featureBox>
                                  <!-- 一个滚动条内容开始 -->
                            <div class="oh">
                            </c:if>
                                <div class="bookBlock">
                                     <img src="${ctx}/pages/publications/form/cover?t=1&id=${p.publications.id}" width="95" height="130" onerror="this.src='${ctx}/images/noimg.jpg'" class="mr10 imgbor"/> 
                                   <%--  <p>${p.publications.cover }</p> --%>
                                   
                                   
                 <%--          
                 		<c:if test="${p.publications.cover==null||p.publications.cover=='' }">
							<img onload="AutoResizeImage(130,95,this);" src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/imagesimg.jpg" style="width:1px;height:1px;float:right; margin-top:5px; margin-right:2px;"class="mr10 imgbor"/>
						</c:if>
						<c:if test="${p.publications.cover!=null&&p.publications.cover!='' }">
							<img onload="AutoResizeImage(130,95,this);" src="${ctx}/pages/publications/form/cover?t=1&id=${p.publications.id}" style="width:1px;height:1px;float:right; margin-top:5px; margin-right:2px;"class="mr10 imgbor"/>
						</c:if>	 
						
					--%>
                                   
                                   
                                     <p class="bookTit"> <a class="a_title" title="${index.index}${p.publications.title}"
																href="${ctx}/pages/publications/form/article/${p.publications.id}">
																${fn:replace(fn:replace(fn:replace(p.publications.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}
															</a></p>
                                     <p class="bookTit">${p.publications.author }</p>
                                </div>
                                     <c:if test="${index.index!=0 && (index.index+1) % 8==0}">
                            </div>
                            <!-- 一个滚动条内容结束 -->
                              </li>
                              </c:if>
                              </c:forEach >
                             </ul>
                           </div>
                          <!-- /botton-scroll -->
                        </div><!-- /block -->
                            <a class="prev3" href="javascript:void(0)">Previous</a>
                          <a class="next3" href="javascript:void(0)">Next</a> 
                 </div>
                        <!-- /feature -->
    		</div>            
        </div>
        <!-- 热读资源结束 -->