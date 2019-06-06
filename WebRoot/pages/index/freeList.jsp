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
	function dividePage(targetPage){
		if(targetPage<0){return;}
		document.formList.action="${ctx}/pages/index/freePub?allfree=true&type=${param.type}&freelang=${param.freelang}&publisher=${param.publisher}&pDate=${param.pDate}&pageCount="+${form.pageCount}+"&curpage="+targetPage;
		document.formList.submit();
	}
	
	function GO(obj){
		document.formList.action="${ctx}/pages/index/freePub?allfree=true&type=${param.type}&freelang=${param.freelang}&publisher=${param.publisher}&pDate=${param.pDate}&pageCount="+$(obj).val();
		document.formList.submit();
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
						<li class="oh"><span class="fl"><a href="${ctx}/pages/index/freePub?allfree=true&type=1"
								class="ico_book"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" /></a></span> <span class="fr"><a href="${ctx}/pages/index/freePub?allfree=true&type=1">[${bookCount }]</a></span>
						</li>
					</c:if>
					<%-- <c:if test="${chapterCount!=null && chapterCount!=0}">
						<li class="oh"><span class="fl"><a href="javascript:void(0)"
								class="ico_capt"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option3" sessionKey="lang" /></a></span> <span class="fr"><a href="javascript:void(0)">[${chapterCount }]</a></span>
						</li>
					</c:if> --%>
					<c:if test="${jouranlCount!=null && jouranlCount!=0}">
						<li class="oh"><span class="fl"><a href="${ctx}/pages/index/freePub?allfree=true&type=2"
								class="ico_jour"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></a></span> <span class="fr"><a href="${ctx}/pages/index/freePub?allfree=true&type=2">[${jouranlCount }]</a></span>
						</li>
					</c:if>
					<c:if test="${articleCount!=null && articleCount!=0}">
						<li class="oh"><span class="fl"><a href="${ctx}/pages/index/freePub?allfree=true&type=4"
								class="ico_capt"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option4" sessionKey="lang" /></a></span> <span class="fr"><a href="${ctx}/pages/index/freePub?allfree=true&type=4">[${articleCount }]</a></span>
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
					<span class="fl"><a href="${ctx}/pages/index/freePub?allfree=true&type=${param.type}&freelang=${lang.key}"class="ico_nomal">${lang.key }</a></span> <span class="fr"><a href="${ctx}/pages/index/freePub?allfree=true&type=${param.type}&freelang=${lang.key}">[${lang.value }]</a></span>
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
					<c:set var="s" value="1"/>
					<c:forEach items="${publisherMap }" var="publisher"
						varStatus="index">
						<li class="oh"><span class="fl"><a href="${ctx}/pages/index/freePub?allfree=true&type=${param.type}&freelang=${param.freelang}&publisher=${publisher.key}"
								class="ico_nomal omit w140">${publisher.key } </a></span> <span
							class="fr"><a href="${ctx}/pages/index/freePub?allfree=true&type=${param.type}&freelang=${param.freelang}&publisher=${publisher.key}">[${publisher.value }]</a></span></li>
					<c:set var="s">${s+1 }</c:set>
					</c:forEach>
				</ul>
				<c:if test="${s>6}">
				<span class="updownMore"><a href="${ctx}/pages/index/freePub?allfree=true&type=${param.type}&freelang=${param.freelang}" class="ico_nomal"><ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" />...</a></span>
				</c:if>
			</div>
			<div class="leftClassity">
				<h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.PubDate" sessionKey="lang" /></h1>
				<ul class="updownUl">
				<c:set var="s" value="1"/>
					<c:forEach items="${pubDateMap }" var="pDate"
						varStatus="index" begin="">
					<li class="oh"><span class="fl"><a href="${ctx}/pages/index/freePub?allfree=true&type=${param.type}&freelang=${param.freelang}&publisher=${param.publisher}&pDate=${pDate.key}"
							class="ico_nomal omit w140">${pDate.key }</a></span> <span class="fr"><a
							href="${ctx}/pages/index/freePub?allfree=true&type=${param.type}&freelang=${param.freelang}&publisher=${param.publisher}&pDate=${pDate.key}">[${pDate.value }]</a></span></li>
					<c:set var="s">${s+1 }</c:set>
					</c:forEach>
				</ul>
				<c:if test="${s>6}">
				<span class="updownMore"><a href="${ctx}/pages/index/freePub?allfree=true&type=${param.type}&freelang=${param.freelang}&publisher=${param.publisher}" class="ico_nomal"><ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" />...</a></span>
				</c:if>
			</div>
		</div>
			<!--左侧导航栏结束 -->
			<!--右侧列表内容-->
			<div class="chineseRight">
				<h1 class="indexHtit">
		             <span class="fl titFb"><a class="ico1" href="javascript:void(0)"><ingenta-tag:LanguageTag key="Global.Label.freePub" sessionKey="lang"/></a></span>
		             
		        </h1>
				<c:if test="${list!=null&&fn:length(list)>0 }">
				<c:forEach items="${list}" var="p" varStatus="index">
						<div class="block">
			               	 <div class="fl w40 mt2">
			               		<img width="14" height="14" src="${ctx}/images/ico/f.png" />
			                    <c:if test="${p.type==1}"><img width="14" height="14" src="${ctx}/images/ico/ico4.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Book" sessionKey="lang" />" /></c:if>
								<c:if test="${p.type==4 || p.type==3}"><img width="14" height="13" src="${ctx}/images/ico/ico5.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Article" sessionKey="lang" />" /></c:if>
								<c:if test="${p.type==2||p.type==6||p.type==7}"><img width="14" height="14" src="${ctx}/images/ico/ico3.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Journal" sessionKey="lang" />" /></c:if>
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

</body>
</html>
