<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
<%@ include file="/common/tools.jsp"%>
<%@ include file="/common/ico.jsp"%>
<link rel="stylesheet" href="${ctx}/css/highlight.css" type="text/css"></link>
<script type="text/javascript" src="${ctx }/js/jquery.highlight.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	//分页功能
	function dividePage(targetPage){
		if(targetPage<0){return;}
		document.getElementById("form").action="${ctx}/pages/publications/lastPubs?news=true&pageCount="+${form.pageCount}+"&curpage="+targetPage;
		document.getElementById("form").submit();
	}
	function GO(obj){
		document.getElementById("form").action="${ctx}/pages/publications/lastPubs?news=true&pageCount="+$(obj).val();
		document.getElementById("form").submit();
	}
	//获取资源弹出层调用
	function popTips2(pid) {
		alert(pid);
	}
}
</script>
</head>
<body>
		<!--以下top state -->
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--以上top end -->
		<!--以下中间内容块开始-->
		<div class="main">
			<!--左侧导航栏 -->
			<div class="chineseLeft"><!-- <div id="searchResultPage" style="display:none"></div> -->
			<div class="leftClassity">
				<h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Publications" sessionKey="lang" /></h1>
				<ul>
					<c:if test="${bookCount!=null && bookCount!=0}">
						<li class="oh"><span class="fl"><a href="javascript:void(0)"
								class="ico_book"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" /></a></span> <span class="fr"><a href="javascript:void(0)">[${bookCount }]</a></span>
						</li>
					</c:if>
					<%-- <c:if test="${chapterCount!=null && chapterCount!=0}">
						<li class="oh"><span class="fl"><a href="javascript:void(0)"
								class="ico_capt"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option3" sessionKey="lang" /></a></span> <span class="fr"><a href="javascript:void(0)">[${chapterCount }]</a></span>
						</li>
					</c:if> --%>
					<c:if test="${jouranlCount!=null && jouranlCount!=0}">
						<li class="oh"><span class="fl"><a href="javascript:void(0)"
								class="ico_jour"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></a></span> <span class="fr"><a href="javascript:void(0)">[${jouranlCount }]</a></span>
						</li>
					</c:if>
					<c:if test="${articleCount!=null && articleCount!=0}">
						<li class="oh"><span class="fl"><a href="javascript:void(0)"
								class="ico_capt"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option4" sessionKey="lang" /></a></span> <span class="fr"><a href="javascript:void(0)">[${articleCount }]</a></span>
						</li>
					</c:if>
				</ul>

			</div>
			
			<div class="leftClassity">
			<c:if test="${fn:length(langMap) > 0}">
				<h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Language" sessionKey="lang" /></h1>
				<ul>
					<c:forEach items="${langMap }" var="lang" varStatus="index">
					<li class="oh">
					<span class="fl"><a href="javascript:void(0)"class="ico_nomal">${lang.key }</a></span> <span class="fr"><a href="javascript:void(0)">[${lang.value }]</a></span>
					</li>
					</c:forEach>
					<!-- <li class="oh"><span class="fl"><a href="javascript:void(0)"
							class="ico_nomal">ENG</a></span> <span class="fr"><a href="javascript:void(0)">[568]</a></span>
					</li> -->
				</ul>
			</c:if>
			</div>
			
			<div class="leftClassity">
				<h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Publisher" sessionKey="lang" /></h1>
				<ul class="updownUl">
					<c:forEach items="${publisherMap }" var="publisher"
						varStatus="index">
						<li class="oh"><span class="fl"><a href="javascript:void(0)"
								class="ico_nomal omit w140">${publisher.key } </a></span> <span
							class="fr"><a href="javascript:void(0)">[${publisher.value }]</a></span></li>
					</c:forEach>
				</ul>
				<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" />...</a></span>
			</div>
			<div class="leftClassity">
				<h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.PubDate" sessionKey="lang" /></h1>
				<ul class="updownUl">
					<c:forEach items="${pubDateMap }" var="pDate"
						varStatus="index" begin="">
					<li class="oh"><span class="fl"><a href="javascript:void(0)"
							class="ico_nomal omit w140">${pDate.key }</a></span> <span class="fr"><a
							href="javascript:void(0)">[${pDate.value }]</a></span></li>
					</c:forEach>
				</ul>
				<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" />...</a></span>
			</div>
		</div>
			<!--左侧导航栏结束 -->
			<!--右侧列表内容-->
			<div class="chineseRight">
				<h1 class="indexHtit">
		             <span class="fl titFb"><a class="ico1" href="javascript:void(0)"><ingenta-tag:LanguageTag key="Global.Label.New_Resources" sessionKey="lang"/></a></span>
		             
		        </h1>
				<c:if test="${list!=null&&fn:length(list)>0 }">
				<c:forEach items="${list}" var="p" varStatus="index">
					<c:set var="license">${(p.subscribedIp!=null||p.subscribedUser!=null)&&(p.subscribedIp>0||p.subscribedUser>0) }</c:set>
					<c:set var="oa">${p.oa!=null&&p.oa==2 }</c:set>
					<c:set var="free">${p.free!=null&&p.free==2 }</c:set>
					<c:set var="collection">${p.inCollection!=null&&p.inCollection>0 }</c:set>
					<c:set var="add1" value="${p.priceList!=null&&fn:length(p.priceList)>0&&p.free!=2&&p.oa!=2&&sessionScope.mainUser!=null && p.subscribedUser<=0&&(p.buyInDetail<=0&&p.exLicense>=0)}"/>
					<c:if test="${add1==false }">
						<c:set var="add" value="false"/>
					</c:if>
					<c:if test="${add1==true && p.subscribedIp>0 }">
						<c:if test="${sessionScope.mainUser.institution.id==sessionScope.institution.id&&sessionScope.mainUser.level==2 }">
						<c:set var="add" value="false"/>
						</c:if>
						<c:if test="${sessionScope.mainUser.institution.id==sessionScope.institution.id &&sessionScope.mainUser.level!=2 }">
						<c:set var="add" value="true"/>
						</c:if>
						<c:if test="${sessionScope.mainUser.institution.id!=sessionScope.institution.id}">
						<c:set var="add" value="true"/>
						</c:if>
					</c:if>
					<c:if test="${add1==true &&(p.subscribedIp==null||p.subscribedIp<=0) }">
						<c:set var="add" value="true"/>
					</c:if>
					<c:if test="${add1==false }">
						<c:set var="add" value="false"/>
					</c:if>
					<c:set var="favourite" value="${sessionScope.mainUser!=null&&p.favorite<=0 }"/>
					<c:set var="recommand" value="${(p.recommand>0 || sessionScope.mainUser.institution!=null) &&(p.subscribedIp==null||p.subscribedIp<=0)&&(p.free!=2&&p.oa!=2)}"/>	
						<div class="block">
			               	 <div class="fl w40 mt2">
			               	 <c:if test="${license==true||oa==true||free==true }">
			               		<c:if test="${license==true }"><img width="14" height="14" src="${ctx }/images/ico/ico_open.png" /></c:if>
			               		<c:if test="${free==true }"><img width="14" height="14" src="${ctx }/images/ico/f.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Free" sessionKey="lang" />"/></c:if>
								<c:if test="${oa==true }"><img width="14" height="14" src="${ctx }/images/ico/o.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.OA" sessionKey="lang" />"/></c:if>
			               	 </c:if>
			               		<c:if test="${license==false }"><img width="14" height="14" src="${ctx }/images/ico/ico_close.png" /></c:if>
			                    <c:if test="${p.type==1}"><img width="14" height="14" src="${ctx}/images/ico/ico4.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Book" sessionKey="lang" />" /></c:if>
								<c:if test="${p.type==4 || p.type==3}"><img width="14" height="13" src="${ctx}/images/ico/ico5.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Article" sessionKey="lang" />" /></c:if>
								<c:if test="${p.type==2||p.publications.type==6||p.publications.type==7}"><img width="14" height="14" src="${ctx}/images/ico/ico3.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Journal" sessionKey="lang" />" /></c:if>
			               	</div>
		                    <div class="fl w640">
			                    <p>
			                    <a class="a_title" title="${p.title}" href="${ctx}/pages/publications/form/article/${p.id}">
			                		${fn:replace(fn:replace(fn:replace(p.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}
			                	</a>
			                	</p>
			                	<c:if test="${not empty p.author}">
			                		<p>By <c:set var="authors" value="${fn:split(p.author,',')}" ></c:set>
			                <c:forEach items="${authors}" var="a" >
			                <a href='${ctx }/index/search?type=2&isAccurate=1&searchValue2=${a}'>${a}</a> &nbsp;
			                </c:forEach></p>
			                	</c:if> 
			                	<p><c:if test="${not empty p.publisher.name}"><a href="javascript:void(0)" onclick="searchByCondition('publisher','${p.publisher.name }','${p.publisher.id}');">${p.publisher.name}</a></c:if> <c:if test="${not empty fn:substring(p.pubDate,0,4)}">(${fn:substring(p.pubDate,0,4) })</c:if></p>
			                	<c:if test="${p.type==2  && not empty p.startVolume && not empty p.endVolume}">
						<p>
						<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" /> ${p.startVolume }-<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" /> ${p.endVolume }
						</p>
						</c:if>
							<p>
				<%-- 			<c:if test="${license==true||oa==true||free==true }">
							<!-- 在线阅读 -->
								<span class="mr20"><a href="javascript:void(0)" title="<ingenta-tag:LanguageTag key="Global.Lable.Prompt.Preview" sessionKey="lang" />" onclick="viewPopTips('${p.id}','0',<c:if test="${oa==false&&free==false}">1</c:if><c:if test="${oa==true||free==true}">2</c:if>)"><img src="${ctx }/images/ico/reading.png" class="vm" /><ingenta-tag:LanguageTag key="Global.Lable.Prompt.Preview" sessionKey="lang" /></a></span>
							</c:if> --%>
							<c:if test="${add }">
							<!-- 购买 -->
								<span class="mr20"><a href="javascript:void(0)" onclick="addToCart('${p.id}',1);" id="add_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Buy' sessionKey='lang'/>"><img src="${ctx }/images/ico/ico14-blank.png" class="vm" />添加到购物车</a></span> 
							</c:if>
							<!-- 获取资源 -->
						<c:if test="${license==false&&oa==false&&free==false }">
							<span class="mr20">
							<a href="javascript:void(0)"  id="resource_div" onclick="pop('${p.id}');"><img src="${ctx }/images/ico/ico15-blue.png" class="vm" />
							<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
							</a>
							</span>
						</c:if>
						<c:if test="${license==true||oa==true||free==true }">
						<c:if test="${p.type==1 }">
							<span class="mr20">
								<a href="javascript:void(0)"  id="resource_div"  onclick="viewPopTips('${p.id}','0',<c:if test="${oa==false&&free==false}">1</c:if><c:if test="${oa==true||free==true}">2</c:if>)"><img src="${ctx }/images/ico/ico15-green.png" class="vm" />
								<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
								</a>
							</span>
						</c:if>
						<c:if test="${p.type==4 }">
							<span class="mr20">
								<a href="javascript:void(0)"  id="resource_div1"  onclick="pop('${p.id}');"><img src="${ctx }/images/ico/ico15-green.png" class="vm" />
								<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
								</a>
							</span>
						</c:if>
						</c:if>
						<c:if test="${sessionScope.mainUser.institution!=null &&sessionScope.mainUser.institution!='' }">
							<c:if test="${recommand}">
							<!-- 推荐 -->
								<span class="mr20"><a href="javascript:void(0)" id="recommand_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' />" onclick="recommends('${p.id}');"><img src="${ctx }/images/ico/ico16-blue.png" class="vm" /><ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' /></a></span>
							</c:if>	
						</c:if>
						<c:if test="${sessionScope.mainUser!=null}">
								<!-- 收藏 -->
							<c:if test="${!favourite }">
							<span><a href="javascript:void(0)" id="favourites_div_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' />" onclick="addFavourites('${p.id }');"><img id="favourites_${p.id }" src="${ctx }/images/ico/ico13-blue.png" class="vm" /><ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' /></a></span> 
							</c:if>
								<!-- 已收藏 -->
							<c:if test="${favourite}">
							  <span><a href="javascript:void(0)" class="blank"><img title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />" src="${ctx }/images/ico/ico13-blank.png" class="vm" /> <ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' /></a></span>
							</c:if>
						</c:if>
						</p>	
		                    </div>
		                </div>
				</c:forEach>
				</c:if>
				<c:if test="${list==null||fn:length(list)<=0 }">
						<ingenta-tag:LanguageTag key="Global.Label.Prompt.No.Product" sessionKey="lang"/>
				</c:if>
				
				<!--分页条开始-->
			     	<jsp:include page="../pageTag/pageTag.jsp">
						<jsp:param value="${form }" name="form"/>
					</jsp:include>
			    <!--分页条结束-->
			
			<!--右侧列表内容结束-->
			</div>
		</div>
		<!--以上中间内容块结束-->
		
		<!--以下 提交查询Form 开始-->
			<form:form action="${form.url}" method="post" modelAttribute="form" commandName="form" name="formList" id="formList">
				<form:hidden path="searchsType" id="type1"/>
				<form:hidden path="searchValue" id="searchValue1"/>
				<form:hidden path="pubType" id="pubType1"/>
				<form:hidden path="language" id="language1"/>
				<form:hidden path="publisher" id="publisher1"/>
				<form:hidden path="pubDate" id="pubDate1"/>
				<form:hidden path="taxonomy" id="taxonomy1"/>
				<form:hidden path="taxonomyEn" id="taxonomyEn1"/>
				<form:hidden path="searchOrder" id="order1"/>
				<form:hidden path="lcense" id="lcense1"/>

				<form:hidden path="code" id="code1"/>
				<form:hidden path="pCode" id="pCode1"/>
				<form:hidden path="publisherId" id="publisherId1"/>
				<form:hidden path="subParentId" id="subParentId1"/>
				<form:hidden path="parentTaxonomy" id="parentTaxonomy1"/>
				<form:hidden path="parentTaxonomyEn" id="parentTaxonomyEn1"/>
			</form:form>
		<!--以上 提交查询Form 结束-->
		
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
<script type="text/javascript">
//获取资源
function pop(pid) {
	art.dialog.open("${ctx}/pages/publications/form/getResource?pubid="+pid,{id : "getResourceId",title:"",top: 200,width: 340, height: 200,lock:true}); /* <ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/> */
}
//推荐
function recommends(pid) {
	//先将信息放到对应的标签上title, code, type, pubSubject
	art.dialog.open("${ctx}/pages/recommend/form/edit?pubid="+pid,{title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/>",top: 100,width: 700, height: 400,lock:true});
}
//购买
function addToCart(pid, ki) {
	var price = $("#price_" + pid).val();
	$.ajax({
		type : "POST",
		url : "${ctx}/pages/cart/form/add",
		data : {
			pubId : pid,
			priceId : price,
			kind : ki,
			r_ : new Date().getTime()
		},
		success : function(data) {
			var s = data.split(":");
			if (s[0] == "success") {
				art.dialog.tips(s[1],1);//location.reload();
				$("#add_"+pid).css("display","none");
				$("#cartCount").html("["+s[2]+"]");
				$("#price_" + pid).css("display","none");
			}else{
				art.dialog.tips(s[1],1,'error');
			}
		},
		error : function(data) {
			art.dialog.tips(data,1,'error');
		}
	});
}
//收藏
function addFavourites(pid) {
	$.ajax({
		type : "POST",
		url : "${ctx}/pages/favourites/form/commit",
		data : {
			pubId : pid,
			r_ : new Date().getTime()
		},
		success : function(data) {
			var s = data.split(":");
			if (s[0] == "success") {
				art.dialog.tips(s[1],3);//location.reload();
				$("#favourites_"+pid).attr("src","${ctx}/images/ico/ico13-blank.png");
				$("#favourites_div_"+pid).removeAttr("href");
				$("#favourites_div_"+pid).removeAttr("onclick");
				$("#favourites_div_"+pid).attr("title","<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang'  />");
				$("#favourites_div_"+pid).attr("class","blank");
				$("#favourites_div_"+pid).css("cursor","auto");
			}else{
				art.dialog.tips(s[1],3,'error');
			}
		},
		error : function(data) {
			art.dialog.tips(data,3,'error');
		}
	});
}
</script>
</body>
</html>
