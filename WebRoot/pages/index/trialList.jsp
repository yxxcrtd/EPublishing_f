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
//试用资源左侧查询条件 
function searchTrailList(type,value){
	if(type=="type"){
		$("input[name=pubType]").val(value);
		$("#pageCount").val(10);
	
	}else if(type=="publisher"){
		$("input[name=publisher]").val(value);
		$("#pageCount").val(10);
	}else if(type=="pubDate"){
		$("input[name=pubDate]").val(value);
	}
	//else if(type=="taxonomy"){
// 		$("input[name=taxonomy]").val(value);
// 	}else if(type=="taxonomyEn"){
// 		$("input[name=taxonomyEn]").val(value);
// 	}
// 	else if(type=="trialLang"){
// 		$("input[name=language2]").val(value);
// 	}
	$("#curpage").val(0);
	document.formList.action="${ctx}/pages/index/trialList";
	document.formList.submit();
}
</script>
<script type="text/javascript">
function sortChange(v){
	var lcense = $("#lcense1").val();
	var param = v;
	$("#order1").val(param);
	$("input[name='curpage']").val(0);
	if(lcense=="1"){
		document.formList.action="${ctx}/index/searchLicense";
	}else if(lcense=="2"){
		document.formList.action="${ctx}/index/searchOaFree";
	}else{
		document.formList.action="${ctx}/index/search";
	}
	document.formList.submit();
}




//语种更多
function seeLangMore(){
	var status = $("#moreLangStatus").val();
	if(status=="1"){
		$("li[name='langmore']").css("display","block");
		$("#moreLangStatus").val('2');
		$("#seelang").html("<span class='alph'><img src='${ctx }/images/jiantou.png' /></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.Less' sessionKey='lang'/>...</span>");
	}else{
		$("li[name='langmore']").css("display","none");
		$("#moreLangStatus").val('1');
		$("#seelang").html("<span class='alph'><img src='${ctx }/images/jiantou.png' /></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</span>");
	}
}
//出版社更多
function seePubMore(){
	var status = $("#morePubStatus").val();
	if(status=="1"){
		$("li[name='pubmore']").css("display","block");
		$("#morePubStatus").val('2');
		$("#seepub").html("<span class='alph'><img src='${ctx }/images/jiantou.png' /></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.Less' sessionKey='lang'/>...</span>");
	}else{
		$("li[name='pubmore']").css("display","none");
		$("#morePubStatus").val('1');
		$("#seepub").html("<span class='alph'><img src='${ctx }/images/jiantou.png' /></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</span>");
	}
}
//分类法更多
function seeSubMore(){
	var status = $("#moreStatus").val();
	if(status=="1"){
		$("li[name='more']").css("display","block");
		$("#moreStatus").val('2');
		$("#see").html("<span class='alph'><img src='${ctx }/images/jiantou.png' /></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.Less' sessionKey='lang'/>...</span>");
	}else{
		$("li[name='more']").css("display","none");
		$("#moreStatus").val('1');
		$("#see").html("<span class='alph'><img src='${ctx }/images/jiantou.png' /></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</span>");
	}
}
//出版年更多
function seeYearMore(){
	var status = $("#moreYearStatus").val();
	if(status=="1"){
		$("li[name='yearmore']").css("display","block");
		$("#moreYearStatus").val('2');
		$("#seeyear").html("<span class='alph'><img src='${ctx }/images/jiantou.png' /></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.Less' sessionKey='lang'/>...</span>");
	}else{
		$("li[name='yearmore']").css("display","none");
		$("#moreYearStatus").val('1');
		$("#seeyear").html("<span class='alph'>></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</span>");
	}
}
//在线阅读
 function viewPopTips(id,page,yon) {
	var url="";
	var tmp=window.open("about:blank","","scrollbars=yes,resizable=yes,channelmode") ;          
        //tmp.focus() ;
	if(page=='0'){
		url = "${ctx}/pages/view/form/view?id="+id;
	}else{
		url = "${ctx}/pages/view/form/view?id="+id+"&nextPage="+page;
	}
	//首先Ajax查询要阅读的路径
	if(yon=='2'){
		//window.location.href=url;
		  tmp.location=url;
	}else{
	$.ajax({
		type : "POST",
		async : false,
		url : "${ctx}/pages/publications/form/getUrl",
		data : {
			id : id,
			nextPage:page,
			r_ : new Date().getTime()
		},
		success : function(data) {
			var s = data.split(";");
			if (s[0] == "success") {
				if(s[1].indexOf('/pages/view/form/view')>=0){
					//window.location.href=s[1];
					tmp.location=s[1];
				}else{
					//window.location.href="${ctx}/pages/view/form/view?id="+id+"&webUrl="+s[1];
				    tmp.location="${ctx}/pages/view/form/view?id="+id+"&webUrl="+s[1];
				}
			}else if(s[0] == "error"){
				art.dialog.tips(s[1],1,'error');
			}
		},
		error : function(data) {
			art.dialog.tips(data,1,'error');
		}
	});
	}
} 

//推荐
function recommends(pid) {
	//先将信息放到对应的标签上title, code, type, pubSubject
	art.dialog.open("${ctx}/pages/recommend/form/edit?pubid="+pid,{title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/>",top: 100,width: 700, height: 400,lock:true});
}
//获取资源弹出层调用
function popTips2(pid) {
/* 	showTipsWindown("",
			'simTestContent', $(window).width()*0.6, $(window).height()*0.65); */
			/* alert(pid); */
			art.dialog.open("${ctx}/pages/publications/form/getResource?pubid="+pid,{id : "getResourceId",title:"",top: 200,width: 340, height: 200,lock:true}); /* <ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/> */
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
				$("#favourites_div_"+pid).attr("title","<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
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
	function dividePage(targetPage){
		if(targetPage<0){return;}
		document.formList.action="${ctx}/pages/index/trialList?type=${param.type}&freelang=${param.freelang}&publisher=${param.publisher}&pDate=${param.pDate}&pageCount="+${form.pageCount}+"&curpage="+targetPage;
		document.formList.submit();
	}
	
	function GO(obj){
		document.formList.action="${ctx}/pages/index/trialList?type=${param.type}&freelang=${param.freelang}&publisher=${param.publisher}&pDate=${param.pDate}&pageCount="+$(obj).val();
		document.formList.submit();
	}
//]]-->
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
						<li class="oh"><span class="fl"><a  href="${ctx}/pages/index/trialList?type=1"
								class="ico_book"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" /></a></span> <span class="fr"><a href="javascript:void(0)">[${bookCount }]</a></span>
						</li>
					</c:if>
					<%-- <c:if test="${chapterCount!=null && chapterCount!=0}">
						<li class="oh"><span class="fl"><a href="javascript:void(0)"
								class="ico_capt"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option3" sessionKey="lang" /></a></span> <span class="fr"><a href="javascript:void(0)">[${chapterCount }]</a></span>
						</li>
					</c:if> --%>
					<c:if test="${jouranlCount!=null && jouranlCount!=0}">
						<li class="oh"><span class="fl"><a href="${ctx}/pages/index/trialList?type=2"
								class="ico_jour"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></a></span> <span class="fr"><a href="javascript:void(0)">[${jouranlCount }]</a></span>
						</li>
					</c:if>
					<c:if test="${articleCount!=null && articleCount!=0}">
						<li class="oh"><span class="fl"><a href="${ctx}/pages/index/trialList?type=4"
								class="ico_capt"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option4" sessionKey="lang" /></a></span> <span class="fr"><a href="javascript:void(0)">[${articleCount }]</a></span>
						</li>
					</c:if>
				</ul>

			</div>
			<div class="leftClassity">
				<h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Language" sessionKey="lang" /></h1>
				<ul>
					<c:forEach items="${langMap }" var="lang" varStatus="index">
					<li class="oh">
					<span class="fl"><a href="${ctx}/pages/index/trialList?freelang=${lang.key}&type=${param.type}"  class="ico_nomal">${fn:toUpperCase(lang.key)}</a></span> 
					<span class="fr">
						<a href="javascript:void(0)">[${lang.value }]</a>
					</span>
					</li>
					</c:forEach>
					<!-- <li class="oh"><span class="fl"><a href="javascript:void(0)"
							class="ico_nomal">ENG</a></span> <span class="fr"><a href="javascript:void(0)">[568]</a></span>
					</li> -->
				</ul>
			</div>
			<div class="leftClassity">
				<h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Publisher" sessionKey="lang" /></h1>
				<ul class="updownUl">
					<c:set var="s" value="1"/>
					<c:forEach items="${publisherMap }" var="publisher"
						varStatus="index">
						<li class="oh"><span class="fl"><a  href="${ctx}/pages/index/trialList?publisher=${publisher.key}&type=${param.type}&freelang=${param.freelang}"
								class="ico_nomal omit w140">${publisher.key }</a></span> <span
							class="fr"><a href="javascript:void(0)" >[${publisher.value }]</a></span></li>
					<c:set var="s">${s+1 }</c:set>
					</c:forEach>
				</ul>
				<c:if test="${s>6}">
				<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" />...</a></span>
				</c:if>
			</div>
			<div class="leftClassity">
				<h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.PubDate" sessionKey="lang" /></h1>
				<ul class="updownUl">
					<c:set var="s" value="1"/>
					<c:forEach items="${pubDateMap }" var="pDate"
						varStatus="index" begin="">
					<li class="oh"><span class="fl"><a href="${ctx}/pages/index/trialList?pDate=${pDate.key}&type=${param.type}&freelang=${param.freelang}&publisher=${param.publisher}"
							class="ico_nomal omit w140">${pDate.key }</a></span> <span class="fr"><a
							href="javascript:void(0)">[${pDate.value }]</a></span></li>
					<c:set var="s">${s+1 }</c:set>
					</c:forEach>
				</ul>
				<c:if test="${s>6}">
				<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" />...</a></span>
				</c:if>
			</div>
		</div>
			<!--左侧导航栏结束 -->
			<!--右侧列表内容-->
			<div class="chineseRight">
				<h1 class="indexHtit">
		             <span class="fl titFb"><a class="ico1" href="${ctx}/pages/index/trialList"><ingenta-tag:LanguageTag key="Global.Label.TrialPub" sessionKey="lang"/></a></span>
		             
		        </h1>
				<c:if test="${list!=null&&fn:length(list)>0 }">
				<c:forEach items="${list}" var="p" varStatus="index">
						<div class="block">
			             	 <div class="fl w40 mt2">
			               		<img width="13" height="13" src="${ctx}/images/ico/t.png" />
			                    <c:if test="${p.type==1}"><img width="13" height="13" src="${ctx}/images/ico/ico4.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Book" sessionKey="lang" />" /></c:if>
								<c:if test="${p.type==4 || p.type==3}"><img width="13" height="13" src="${ctx}/images/ico/ico5.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Article" sessionKey="lang" />" /></c:if>
								<c:if test="${p.type==2||p.type==6||p.type==7}"><img width="13" height="13" src="${ctx}/images/ico/ico3.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Journal" sessionKey="lang" />" /></c:if>
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
						 	<c:if test="${p.type!=1 }">
							<span class="mr20">
								<a href="javascript:void(0)"  id="resource_div"  onclick="popTips2('${p.id}');"><img src="${ctx }/images/ico/ico15-green.png" class="vm" />
									<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
								</a>
							</span>
							</c:if>
							<c:if test="${p.type==1 }">
							<span class="mr20">
								<a href="javascript:void(0)"  id="resource_div"  onclick="viewPopTips('${p.id}','0',2)"><img src="${ctx }/images/ico/ico15-green.png" class="vm" />
									<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
								</a>
							</span>
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
				<form:hidden path="pubType" id="pubType2"/>
				<form:hidden path="language" id="language2"/>
				<form:hidden path="pubDate" id="pubDate2"/>
				<form:hidden path="taxonomy" id="taxonomy2"/>
				<form:hidden path="taxonomyEn" id="taxonomyEn2"/>
				<form:hidden path="publisher" id="publisher2"/>
<%-- 				<form:hidden path="curpage" id="curpage"/> --%>
<%--     			<form:hidden path="pageCount" id="pageCount"/> --%>
			</form:form>
		<!--以上 提交查询Form 结束-->
		
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>

</body>
</html>
