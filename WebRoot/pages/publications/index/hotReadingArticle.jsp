<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/tools.jsp"%>

        <!-- 热读资源开始 -->
        <div class="oh h450">
        	<h1 class="h1Tit borBot"><a class="ico1" href="javascript:void(0)"><ingenta-tag:LanguageTag key="Page.Publications.Hotreading" sessionKey="lang" /></a></h1>
            <div class="featureContainer">
                <div class="feature">
                    <div class="black h420">
                         <div id="botton-scroll3">
                            <ul class=featureUL>
                               <c:forEach items="${hotlist}" var="p" varStatus="index">
							<c:if test="${index.index % 4==0}">
                              <li class=featureBox>
                                  <!-- 一个滚动条内容开始 -->
                            <div class="oh">
                            </c:if>
                                <%-- <div class="bookBlock">
                                     <img src="${p.publications.cover }" width="95" height="130" class="mr10 imgbor"/> 
                                    <p>${p.publications.cover }</p>
                                   
                                     <p class="bookTit"> <a title="${index.index}${p.publications.title}"
																href="${ctx}/pages/publications/form/article/${p.publications.id}">
																${fn:replace(fn:replace(fn:replace(p.publications.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}
															</a></p>
                                     <p class="bookTit">${p.publications.author }</p>
                                </div> --%>
                                
                                <div class="block">
                                    <div class="fl w40 mt2">
                                       <c:set var="license" value="${p.publications.subscribedIp>0||p.publications.subscribedUser>0||p.publications.free==2||p.publications.oa==2 }"/>
                                           <c:if test="${license==true }"><img src="${ctx}/images/ico/ico_open.png" width="16" height="16" /></c:if>
                                           <c:if test="${license==false }"><img src="${ctx}/images/ico/ico_close.png" width="16" height="16" /></c:if>
                                       <img src="${ctx}/images/ico/ico5.png" width="13" height="13" />
                                    </div>
                                  <div class="fl w640">
                                    <p class="omit w640"><a class="a_title" title="${index.index}${p.publications.title}" href="${ctx}/pages/publications/form/article/${p.publications.id}" >${fn:replace(fn:replace(fn:replace(p.publications.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}</a></p>
                                       <c:if test="${not empty p.publications.author}">
                                        <p>By 
											<c:set var="tauthors" value="${fn:split(p.publications.author,',')}" ></c:set>
									        <c:forEach items="${tauthors}" var="ta" >
									        	<a href='${ctx}/index/search?type=2&isAccurate=1&searchValue2=${ta}'>${ta}</a> &nbsp;
									        </c:forEach>
										</p>
										</c:if>
                                        <p>
											<a href="${ctx}/pages/publications/form/journaldetail/${p.publications.publications.id}">${p.publications.publications.title}</a>,
									        ${p.publications.year }<%-- -${p.publications.month}  --%>,
									        <ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" />
									        ${p.publications.volumeCode },
											<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Issue" sessionKey="lang" />
											${p.publications.issueCode }, 
											<%-- <ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.PageRange" sessionKey="lang" />  --%>
											${p.publications.startPage }-${p.publications.endPage}
										</p>
                                        <p><a onclick="searchByCondition('publisher','${p.publications.publisher.name }','${p.publications.publisher.id}')">${p.publications.publisher.name }</a></p>
                                    </div>
                                </div>
                                
                                     <c:if test="${index.index!=0 && (index.index+1) % 4==0}">
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