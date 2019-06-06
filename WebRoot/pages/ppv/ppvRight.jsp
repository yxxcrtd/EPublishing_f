<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--分页条开始-->
        <div class="sort1" >
	       <jsp:include page="../pageTag/pageTag.jsp">
				<jsp:param value="${form }" name="form"/>
		   </jsp:include>
	   </div>
       <!--分页条结束--> 
       <!-- 资源列表开始 -->
        <h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Page.Publications.Journal.Article.List" sessionKey="lang" /></h1>
        <!----- 区块开始 ------>
        <div class="block">
            <c:forEach items="${pccList }" var="p">
            <c:set var="inLicense">${p.publications.inLicense>0||p.publications.insInLicense>0||p.publications.oa==2 }</c:set>
            <c:set var="inDetail" >${p.publications.inDetail>0||p.publications.insInDetail>0||p.publications.free==2 }</c:set>
            <c:set var="favourite" value="${sessionScope.mainUser!=null && p.publications.favorite<=0 }"/>
            <div class="fl w40 mt2">
                <c:if test="${inLicense}">
                <img src="${ctx }/images/ico/ico_open.png" width="16" height="16" />
                </c:if>
                <c:if test="${!inLicense}">
                <img src="${ctx }/images/ico/ico_close.png" width="16" height="16" />
                </c:if>
                <!-- 产品类型图标 -->
				<!-- 期刊 -->
				<c:if test="${p.publications.type==2 }">
				<img src="${ctx }/images/ico/ico3.png"      width="14" height="14" />
				</c:if>
				<!-- 图书 -->
				<c:if test="${p.publications.type==1 }">
				<img src="${ctx }/images/ico/ico4.png"      width="14" height="14" />
				</c:if>
				<!--其他-->
				<c:if test="${p.publications.type!=1&& p.publications.type!=2}">
				<img src="${ctx }/images/ico/ico5.png"      width="14" height="14" />
				</c:if>
            </div>
            <div class="fl w700" style="margin-bottom: 15px;>
                <p class="omit w640"><a title="${p.publications.title }" href="${ctx}/pages/publications/form/article/${p.publications.id}">${p.publications.title }</a></p>
                <p><ingenta-tag:LanguageTag key="Page.Index.Index.Lable.JournalInfo" sessionKey="lang" />：Volume ${p.publications.startVolume } - Volume ${p.publications.endVolume }</p>
                <p><a href='${ctx }/index/search?type=4&isAccurate=1&searchValue2=${p.publications.publisherName }'>${p.publications.publisherName }</a></p>
                <p>
                <!-- 已登录 -->
                <c:if test="${sessionScope.mainUser!=null}">
                <!-- 加入购物车开始 -->
                <c:if test="${!inLicense}">
                <span class="mr20" id="add_${p.publications.id}"><a href="javascript:void(0)" onclick="addToCart('${p.publications.id}',1);"  title="<ingenta-tag:LanguageTag key='Page.Publications.Lable.Buy' sessionKey='lang'/>"><img src="${ctx }/images/ico/ico14-blank.png" class="vm" />
                <ingenta-tag:LanguageTag key='Page.Publications.Lable.Buy' sessionKey='lang'/></a></span>
                <!-- 加入购物车结束 -->
                <!-- 获取资源开始 -->
                <!-- 已订阅 -->
                <c:if test="${inLicense}">
                <span class="mr20"><a href="javascript:void(0)" class="green" onclick="popTips2('${p.publications.id}');"><img src="${ctx }/images/ico/ico15-green.png" class="vm" /> 
                <ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" /></a></span>
                </c:if>
                <c:if test="${!inLicense}">
                <span class="mr20"><a href="javascript:void(0)" onclick="popTips2('${p.publications.id}');"><img src="${ctx }/images/ico/ico15-blue.png" class="vm" /> 
                <ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" /></a></span>
                </c:if>
                <!-- 未订阅 -->
                <!-- 获取资源结束 -->
                	<c:if test="${favourite }">
						<span class="mr20"><a href="javascript:void(0)" id="favourites_div_${p.publications.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' />" onclick="addFavourites('${p.publications.id }');"><img id="favourites_${p.publications.id }" src="${ctx }/images/ico/ico13-blue.png" class="vm" /><ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' /></a></span> 
						</c:if>
							<!-- 已收藏 -->
						<c:if test="${sessionScope.mainUser!=null && !favourite }">
						  <span class="mr20"><a href="javascript:void(0)" class="blank"><img title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />" src="${ctx }/images/ico/ico13-blank.png" class="vm" /> <ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' /></a></span>
					</c:if>
                <!-- 推荐开始 -->
                <c:if test="${sessionScope.mainUser.level==2||sessionScope.institution!=null }">
                <span class="mr20"><a href="javascript:void(0)" onclick="popTips('${p.publications.id}');"><img src="${ctx }/images/ico/ico16-blue.png" class="vm" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Link.Recommend" sessionKey="lang" /></a></span>
                </c:if>
                <!-- 推荐结束 -->
                </c:if>
                </c:if>
                <!-- 未登录 -->
                <c:if test="${sessionScope.mainUser==null}">
                <c:if test="${sessionScope.mainUser.level!=2||sessionScope.institution==null }">
                <span class="mr20"><a href="javascript:void(0)" onclick="popTips2('${p.publications.id}');"><img src="${ctx }/images/ico/ico15-blue.png" class="vm" />
                <ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" /></a></span>
                </c:if>
                <c:if test="${inLicense}">
                <span class="mr20"><a href="javascript:void(0)" class="green" onclick="popTips2('${p.publications.id}');"><img src="${ctx }/images/ico/ico15-green.png" class="vm" /> 
                <ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" /></a></span>
                </c:if>
                <c:if test="${!inLicense}">
                <c:if test="${sessionScope.mainUser.level==2||sessionScope.institution!=null }">
                <span class="mr20"><a href="javascript:void(0)" onclick="popTips('${p.publications.id}');"><img src="${ctx }/images/ico/ico16-blue.png" class="vm" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Link.Recommend" sessionKey="lang" /></a></span>
                </c:if>
                </c:if>
                </c:if>
                </p>
            </div>
            </c:forEach>
        </div>
        <!---- 区块结束 ----->
        <!-- 资源列表结束 -->  
       <!--分页条开始-->
    <jsp:include page="../pageTag/pageTag.jsp">
			<jsp:param value="${form }" name="form"/>
	</jsp:include>
       <!--分页条结束-->  