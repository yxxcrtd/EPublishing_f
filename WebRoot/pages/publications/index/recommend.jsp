<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/tools.jsp"%>

<!--推荐期刊开始 -->
        <div class="oh h350">
				<h1 class="h1Tit borBot">
					<a class="ico1" href="javascript:void(0)"><ingenta-tag:LanguageTag key="Pages.Publications.Recommend.Journal" sessionKey="lang" /></a>
				</h1>
				<div class="featureContainer">
					<div class="feature">
						<div class="black h320">
							 <div id="botton-scroll2">
								<ul class=featureUL>
								 <c:forEach items="${list}" var="p" varStatus="index">
								<c:if test="${index.index % 8==0}">
									<li class=featureBox>
										<!-- 一个滚动条内容开始 -->
										<div class="oh">
											<div class="block jourList" style="margin-bottom: 10px;">
								</c:if>
												<div class="oh w350 fl">
                                                    <div class="fl w40 mt2">
                                                    <c:set var="license" value="${p.publications.subscribedIp>0||p.publications.subscribedUser>0||p.publications.free==2||p.publications.oa==2 }"/>
                                                        <c:if test="${license==true }"><img src="${ctx}/images/ico/ico_open.png" width="16" height="16" /></c:if>
                                                        <c:if test="${license==false }"><img src="${ctx}/images/ico/ico_close.png" width="16" height="16" /></c:if>
                                                        <img src="${ctx}/images/ico/ico3.png" width="13" height="13" />
                                                    </div>
                                                    <div class="fl w300">
                                                        <p class="omit">
														<a class="a_title" title="${index.index}${p.publications.title}" href="${ctx}/pages/publications/form/article/${p.publications.id}">
										                		${fn:replace(fn:replace(fn:replace(p.publications.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}
										                	</a>
														</p>
                                                        <p class="omit"><a href="javascript:void(0)" onclick="searchByCondition('publisher','${p.publications.publisher.name }','${p.publications.publisher.id}');">${p.publications.publisher.name}</a></p>
                                                        <c:if test="${p.publications.type==2 }">
														<p>
															<ingenta-tag:LanguageTag key="Page.Index.Index.Lable.JournalInfo" sessionKey="lang" />：Volume ${p.publications.startVolume } - Volume ${p.publications.endVolume }
														</p>
														</c:if>
                                                    </div>
                                                </div>
												
												
									 <c:if test="${index.index!=0 && (index.index+1) % 8==0}">
											</div>
										</div> <!-- 一个滚动条内容结束 -->
									</li>
									</c:if>
									</c:forEach>
								</ul>
							</div>
							<!-- /botton-scroll -->
						</div>
						<!-- /block -->
						 <a class="prev2 chPrev1" href="javascript:void(0)">Previous</a>
                          <a class="next2 chNext1" href="javascript:void(0)">Next</a> 
					</div>
					<!-- /feature -->
				</div>
			</div>
        <!-- 推荐期刊结束 -->