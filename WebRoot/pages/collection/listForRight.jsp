<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>	
		
			<!-- 资源列表开始 -->
			<h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Page.Publications.Journal.Article.List" sessionKey="lang" />></h1>
			<!----- 区块开始 ------>
			<c:forEach items="${pccList }" var="p" varStatus="index">
				<c:set var="license">${p.publications.inLicense>0||p.publications.insInLicense>0||form.inLicense}</c:set>
				<c:set var="detail">${p.publications.inDetail>0||p.publications.insInDetail>0||form.inOrderDetail}</c:set> 
				<!--  oa是否开源1、不开源 2、开源-->
				<c:set var="oa">${p.publications.oa!=null&&p.publications.oa==2}</c:set>
				<!-- free是否免费1、不免费 ；2、免费 -->
				<c:set var="free">${p.publications.free!=null&&p.publications.free==2 }</c:set>
				<c:set var="favourite" value="${p.publications.favorite>0 ||p.publications.insfavorite>0 }" />
				<c:set var="recommand" value="${(p.publications.recommand>0||sessionScope.mainUser.institution!=null) &&(!license)&&(p.publications.free!=2&&p.publications.oa!=2)}" />
				<div class="block">
				<!-- 锁头小图标 -->
					<div class="fl w40 mt2">
					<!-- 未登录 -->
					<c:if test="${sessionScope.mainUser==null}">
						<img src="${ctx }/images/ico/ico_close.png" width="16" height="16" /> 
					</c:if>
					<!-- 已登录 -->
					<c:if test="${sessionScope.mainUser!=null}">
					<!-- 已购买、开源、免费 -->
					<c:if test="${license==true||oa==true||free==true }">
					<img src="${ctx }/images/ico/ico_open.png" width="16" height="16" /> 
					</c:if>
					<!-- 未购买 -->
				    <c:if test="${license==false&&oa==false&&free==false }">
					<img src="${ctx }/images/ico/ico_close.png" width="16" height="16" /> 
					</c:if>
					</c:if>
					<!-- 产品类型图标 -->
					<!-- 期刊 -->
					<c:if test="${p.publications.type==2||p.publications.type==6||p.publications.type==7 }">
					<img src="${ctx }/images/ico/ico3.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Journal" sessionKey="lang" />" />
					</c:if>
					<!-- 图书 -->
					<c:if test="${p.publications.type==1 }">
					<img src="${ctx }/images/ico/ico4.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Book" sessionKey="lang" />" />
					</c:if>
					<!--其他-->
					<c:if test="${p.publications.type!=1&& p.publications.type!=2&& p.publications.type!=6&& p.publications.type!=7}">
					<img src="${ctx }/images/ico/ico5.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Article" sessionKey="lang" />" />
					</c:if>
					</div>
					<div class="fl w700">
					    <!-- 名称 -->
						<p>
						<a class="a_title" title="${p.publications.title }" href="${ctx}/pages/publications/form/article/${p.publications.id}">${p.publications.title }</a>
						</p>
						<!-- 作者 -->
						<p><c:if test="${p.publications.type!=2 }">
						    <c:if test="${p.publications.authorAlias!=null }">
						    <c:set var="authors" value="${fn:split(p.publications.authorAlias,',')}" ></c:set>
			                <c:forEach items="${authors}" var="a" >
			                <a href='${ctx }/index/search?type=2&isAccurate=1&searchValue2=${a}'>${a}</a> &nbsp;
			                </c:forEach>
			                </c:if>
			                <c:if test="${p.publications.type==4 }"> 
			                in <a href="${ctx}/pages/publications/form/article/${p.publications.publications.id}">${p.publications.publications.title}</a>
			                </c:if>
			                (${fn:substring(p.publications.pubDate,0,4) })
			                </c:if>
						    <c:if test="${p.publications.type==2 }">Volume ${p.publications.startVolume }-Volume ${p.publications.endVolume }</c:if>
						</p>
						<!-- 出版社 -->
						<p><a href='${ctx }/index/search?type=2&isAccurate=1&searchValue2=${p.publications.publisherName }'>${p.publications.publisherName }</a></p>
						<!-- 功能按钮显示 -->
						<c:if test="${sessionScope.mainUser!=null}">
						<p>
							<!-- 未购买 -->
							<c:if test="${license==false&&oa==false&&free==false&&detail==false  }">
							<span class="mr20" id="add_${p.publications.id}"><a href="javascript:void(0)" onclick="addToCart('${p.publications.id}',1);"  title="<ingenta-tag:LanguageTag key='Page.Publications.Lable.Buy' sessionKey='lang'/>"><img src="${ctx }/images/ico/ico14-blank.png" class="vm" />
                            <ingenta-tag:LanguageTag key='Page.Publications.Lable.Buy' sessionKey='lang'/></a></span>
							</c:if>
							<c:if test="${license==false&&oa==false&&free==false  }">
							<span class="mr20"><a href="javascript:void(0)" onclick="popTips2('${p.publications.id}');"><img src="${ctx }/images/ico/ico15-blue.png" class="vm" /> 
                            <ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" /></a></span>
							<c:if test="${recommand }">
							<span class="mr20"><a href="javascript:void(0)" onclick="popTips('${p.publications.id}');"><img src="${ctx }/images/ico/ico16-blue.png" class="vm" /> 
                            <ingenta-tag:LanguageTag key="Page.Index.Search.Button.Recommed" sessionKey="lang" /></a></span>
							</c:if>
							</c:if>
							<c:if test="${license||oa||free}">
							<span class="mr20"><a href="javascript:void(0)" class="green" onclick="popTips2('${p.publications.id}');"><img src="${ctx }/images/ico/ico15-green.png" class="vm" /> 
                            <ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" /></a></span>
							</c:if>
							
							<c:if test="${favourite}">
								<span class="favourite" id="${p.publications.id}">
									<a href="javascript:;" class="blank">
										<img src="${ctx}/images/favourite.png" class="vm" /><span><ingenta-tag:LanguageTag key="Page.Index.Search.Link.collected" sessionKey="lang" /></span>
                            		</a>
                            	</span>
							</c:if>
							<c:if test="${!favourite}">
								<span class="favourite" id="${p.publications.id}">
									<a href="javascript:;">
										<img src="${ctx}/images/unfavourite.png" class="vm" /><span><ingenta-tag:LanguageTag key="Page.Index.Search.Button.Favourite" sessionKey="lang" /></span>
									</a>
								</span>
							</c:if>
							
						</p>
						</c:if>
					</div>
				</div>
			</c:forEach>
			<!---- 区块结束 ----->
			<input type="hidden" id="pageCount" value="${form.pageCount }"/>
			<!--分页条开始-->
			<jsp:include page="../pageTag/pageTag.jsp">
			<jsp:param value="${form }" name="form"/>
			</jsp:include>
			<!--分页条结束-->
<script type="text/javascript">
$(function() {
	$(".favourite").on("click", function() {
		var This = $(this);
		This.each(function() {
			$.get("${ctx}/pages/favourites/form/commit", { pubId : This.attr("id") }, function(data) {
				if ("success" == data) {
					This.find("a").attr("class", "blank");
					This.find("img").attr("src", "${ctx}/images/favourite.png");
					This.find("span").html("<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
					//art.dialog.tips('<ingenta-tag:LanguageTag key='Controller.Favourites.commit.success' sessionKey='lang' />', 1, 'success');
				} else if ("del" == data) {
					This.find("a").attr("class", "");
					This.find("img").attr("src", "${ctx}/images/unfavourite.png");
					This.find("span").html("<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' />");
					//art.dialog.tips('<ingenta-tag:LanguageTag key='Controller.Favourites.commit.cancel' sessionKey='lang' />', 1, '');
				}
			});
		});
	});
});
</script>