<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/tools.jsp"%>

	 <div class="oh h450" style="height: 360px">
        	<h1 class="h1Tit borBot">
        		<span ><a class="ico1" href="javascript:void(0)"><ingenta-tag:LanguageTag key="Pages.Publications.PayPerView" sessionKey="lang" /></a></span>
				<span class="fr" style="font-size:13px;"><a href="${ctx}/pages/collection/form/getPPV">more>></a></span>
        	</h1>
			<div class="featureContainer">
				<div class="feature">
					<div class="black h420">
                       	<div id="botton-scroll4">
                           	<ul class=featureUL>
								<li class=featureBox>
								<c:forEach items="${pccList }" var="p" varStatus="index">
									<c:if test="${4 > index.index}">
									 <c:set var="inLicense">${p.publications.inLicense>0||p.publications.insInLicense>0||p.publications.oa==2 }</c:set>
	            					 <c:set var="inDetail" >${p.publications.inDetail>0||p.publications.insInDetail>0||p.publications.free==2 }</c:set>
									<!----- 区块开始 ------>
	                               	 <div class="block">
	                                    <div class="fl w40 mt2">
												<c:if test="${inLicense}">
								                <img src="${ctx }/images/ico/ico_open.png" width="16" height="16" />
								                </c:if>
								                <c:if test="${!inLicense}">
								                <img src="${ctx }/images/ico/ico_close.png" width="16" height="16" />
								                </c:if>
								                <!-- 产品类型图标 -->
												<!-- 期刊 -->
												<c:if test="${p.publications.type==2||p.publications.type==6||p.publications.type==7 }">
												<img src="${ctx }/images/ico/ico3.png"      width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Journal" sessionKey="lang" />" />
												</c:if>
												<!-- 图书 -->
												<c:if test="${p.publications.type==1 }">
												<img src="${ctx }/images/ico/ico4.png"      width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Book" sessionKey="lang" />" />
												</c:if>
												<!--其他-->
												<c:if test="${p.publications.type!=1&& p.publications.type!=2&& p.publications.type!=6&& p.publications.type!=7}">
												<img src="${ctx }/images/ico/ico5.png"      width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Article" sessionKey="lang" />" />
												</c:if>
	                                    </div>
	                                    <div class="fl w640">
	                                    	<!-- 出版物标题 -->
		                                    <p class="omit w640">
		                                    	<a class="a_title" title="${p.publications.title }" href="${ctx}/pages/publications/form/article/${p.publications.id}">${p.publications.title }</a>
											</p>
	                                    	<!-- 所属期刊信息 -->
	                                        <c:if test="${p.publications.type==2 }">
											<p>
											<ingenta-tag:LanguageTag key="Page.Index.Index.Lable.JournalInfo" sessionKey="lang" />：Volume ${p.publications.startVolume } - Volume ${p.publications.endVolume }
											</p>
											</c:if>
	                                        <!-- 出版社 -->
	                                        <p>
												<a href='${ctx }/index/search?type=4&isAccurate=1&searchValue2=${p.publications.publisherName }'>${p.publications.publisherName }</a>
											</p>
	                                    </div>
	                               	</div>
	                                <!---- 区块结束 ----->
	                                </c:if>
                                </c:forEach>
								</li>
							</ul>
						</div>
					 </div>
				</div>
		 </div>
	</div>
