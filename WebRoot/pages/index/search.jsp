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
//<![data[
$(document).ready(function() {
	//高亮搜索结果
	//high();
	//订阅情况
/* 	$("input[name='isLicenses']").click(function(){
		var lcense = $("#lcense1").val();
		var param = $(this).attr("id");
		if(param=="selectAll"){
			$("#lcense1").val("");
			$("#pageCount").val(10);
			$("#order1").val('');
		}else if(param=="selectLicense"){
			$("#lcense1").val("1");
			$("#pageCount").val(10);
			$("#order1").val('');
		}
		$("input[name='curpage']").val(0);
		if($("#lcense1").val()=="1"){
			document.formListSearch.action="${ctx}/index/searchLicense";
		}else{
			document.formListSearch.action="${ctx}/index/search";
		}
		document.formListSearch.submit();
	}); */
	
	$("li[name='stab1']").click(function(){
		var searchsFlag = $("#searchsFlag").val();
		$("input[name='searchsType']").val(searchsFlag);
		var searchsWord = $("#searchsWord").val();
		$("input[name='searchValue2']").val(searchsWord);
		var lcense = $("#lcense1").val();
		$("input[name='isLicenses']").attr("id","selectAll");
		var param = $("input[name='isLicenses']").attr("id");
		if(param=="selectAll"){
			$("#lcense1").val("");
			$("#order1").val('');
		}else if(param=="selectLicense"){
			$("#lcense1").val("1");
			$("#order1").val('');
		}else if(param=="selectFree"){
			$("#lcense1").val("2");
			$("#order1").val('');
		}
		$("input[name='curpage']").val(0);
		document.formListSearch.action="${ctx}/index/search";
		document.formListSearch.submit();
	});
	
	$("li[name='stab2']").click(function(){
		var searchsFlag = $("#searchsFlag").val();
		$("input[name='searchsType']").val(searchsFlag);
		var searchsWord = $("#searchsWord").val();
		$("input[name='searchValue2']").val(searchsWord);
		var lcense = $("#lcense1").val();
		$("input[name='isLicenses']").attr("id","selectLicense");
		var param = $("input[name='isLicenses']").attr("id");
		if(param=="selectAll"){
			$("#lcense1").val("");
			$("#order1").val('');
		}else if(param=="selectLicense"){
			$("#lcense1").val("1");
			$("#order1").val('');
		}else if(param=="selectFree"){
			$("#lcense1").val("2");
			$("#order1").val('');
		}
		$("input[name='curpage']").val(0);
		document.formListSearch.action="${ctx}/index/searchLicense";
		document.formListSearch.submit();
	});
	
	$("li[name='stab3']").click(function(){
		var searchsFlag = $("#searchsFlag").val();
		$("input[name='searchsType']").val(searchsFlag);
		var searchsWord = $("#searchsWord").val();
		$("input[name='searchValue2']").val(searchsWord);
		var lcense = $("#lcense1").val();
		$("input[name='isLicenses']").attr("id","selectFree");
		var param = $("input[name='isLicenses']").attr("id");
		if(param=="selectAll"){
			$("#lcense1").val("");
			$("#order1").val('');
		}else if(param=="selectLicense"){
			$("#lcense1").val("1");
			$("#order1").val('');
		}else if(param=="selectFree"){
			$("#lcense1").val("2");
			$("#order1").val('');
		}
		$("input[name='curpage']").val(0);
		document.formListSearch.action="${ctx}/index/searchOaFree";
		document.formListSearch.submit();
	});
	//排序
	$("#sort").change(function(){
		sortChange($(this).val());
	});
	$("#sort2").change(function(){
		sortChange($(this).val());
	});
	
	//条件删除
	$("a[name='conditions']").click(function(){
		var searchsFlag = $("#searchsFlag").val();
		$("input[name='searchsType']").val(searchsFlag);
		var searchsWord = $("#searchsWord").val();
		$("input[name='searchValue2']").val(searchsWord);
		var lcense = $("#lcense1").val();
		var param = $(this).attr("id");
		if(param=="pubType_label"){
			$("#pubType1").val('');
		}else if(param=="publisher_label"){
			$("#publisher1").val('');
		}else if(param=="pubDate_label"){
			$("#pubDate1").val('');
		}else if(param.indexOf("taxonomy_label")==0){
		/* $("#taxonomy1").val(''); */
		var tax=$(this).text().trim();
		var allTax = $("#taxonomy1").val();
		allTax=allTax.replace(tax,'').replace(',,',',');
		allTax=allTax.replace(/^,+/,'');
		allTax=allTax.replace(/,+$/,'');
		$("#taxonomy1").val(allTax);
		}else if(param=="taxonomyEn_label"){
			$("#taxonomyEn1").val('');
		}else if(param=="language_label"){
			$("#language1").val('');
		}
		$(this).css("display","none");
		$("input[name='curpage']").val(0);
		
		if(${sessionScope.selectType==1}){
			$("#lcense").val("1");
			document.formListSearch.action="${ctx}/index/searchLicense";
		}else if(${sessionScope.selectType==2}){
			$("#lcense").val("2");
			document.formListSearch.action="${ctx}/index/searchOaFree";
		}else{
			document.formListSearch.action="${ctx}/index/search";
		}
		document.formListSearch.submit();
	});	
	//下载列表
	$("#downList").click(function(){
		var url;
		if(${sessionScope.selectType==1}){
	 		url = "${ctx}/index/searchLicenseDownList";
	 	}else if(${sessionScope.selectType==2}){
	 		url = "${ctx}/index/searchOAFreeDownList";
	 	}else{
	 		url = "${ctx}/index/searchDownList";
	 	}
        url += "?searchsType="+$("#type1").val();
        url += "&searchValue2="+$("#searchValue1").val();
        url += "&pubType="+$("#pubType1").val();
        url += "&publisher="+$("#publisher1").val();
        url += "&language="+$("#language1").val();
        url += "&pubDate="+$("#pubDate1").val();
        	
        url += "&taxonomy="+$("#taxonomy1").val();
        url += "&taxonomyEn="+$("#taxonomyEn1").val();
        url += "&searchOrder="+$("#order1").val();
        url += "&lcense="+$("#lcense1").val();
        	
        url += "&code="+$("#code1").val();
        url += "&pCode="+$("#pCode1").val();
        url += "&publisherId="+$("#publisherId1").val();
        url += "&subParentId="+$("#subParentId1").val();
        url += "&parentTaxonomy="+$("#parentTaxonomy1").val();
        url += "&parentTaxonomyEn="+$("#parentTaxonomyEn1").val();
        url += "&curPage="+$("#curpage").val();
        url += "&pageCount="+$("#pageCount").val();
        window.location.href=url;
	});
});
function sortChange(v){
	var lcense = $("#lcense1").val();
	var param = v;
	$("#order1").val(param);
	$("input[name='curpage']").val(0);
	if(lcense=="1"){
		document.formListSearch.action="${ctx}/index/searchLicense";
	}else if(lcense=="2"){
		document.formListSearch.action="${ctx}/index/searchOaFree";
	}else{
		document.formListSearch.action="${ctx}/index/search";
	}
	document.formListSearch.submit();
}
//高亮显示
function high(){
	var fullText = new Array();
	var titleext = new Array();
	var authorText = new Array();
	var isbnText = new Array();
	var publisherText = new Array();
	var remarkText = new Array();
	
	fullText = [${form.keyMap['fullText']}];
	titleext = [${form.keyMap['title']}];
	authorText = [${form.keyMap['author']}];
	isbnText = [${form.keyMap['isbn']}];
	publisherText = [${form.keyMap['publisher']}];
	remarkText = [${form.keyMap['remark']}];
	if('${form.searchsType}'=='0'){ 
		$("a[name='title']").highlight(titleext);
		$("td[name='author']").highlight(authorText);
		$("td[name='isbn']").highlight(isbnText);
		$("td[name='publisher']").highlight(publisherText);
		$("td[name='remark']").highlight(remarkText);
	}else if('${form.searchsType}'=='1'){
		$("a[name='title']").highlight(titleext);
	}else if('${form.searchsType}'=='2'){
		$("td[name='author']").highlight(authorText);
	}else if('${form.searchsType}'=='3'){
		$("td[name='isbn']").highlight(isbnText);
	}else if('${form.searchsType}'=='4'){
		$("td[name='publisher']").highlight(publisherText);
	}else{
		$("a[name='title']").highlight(titleext);
		$("td[name='author']").highlight(authorText);
		$("td[name='isbn']").highlight(isbnText);
		$("td[name='publisher']").highlight(publisherText);
		$("td[name='remark']").highlight(remarkText);
	}
}
//左侧条件查询
function searchByCondition(type,value){
	var searchsFlag = $("#searchsFlag").val();
	$("input[name='searchsType']").val(searchsFlag);
	var searchsWord = $("#searchsWord").val();
	$("input[name='searchValue2']").val(searchsWord);
	if(type=="type"){
		$("input[name=pubType]").val(value);
	}else if(type=="publisher"){
		$("input[name=publisher]").val(value); 
	}else if(type=="pubDate"){
		$("input[name=pubDate]").val(value);
	}else if(type=="taxonomy"){
		var tax=$("input[name=taxonomy]").val();
		var s=$("#taxonomy1").val();
		var strs= new Array(); 
		    strs=s.split(","); 
		    for (i=0;i<strs.length ;i++ ) 
		    { 
		    	if(strs[i]==value){
		    		verify=1;
		    		strs.remove(i); 
		    	}
		    }
		    b = strs.join("-");
		    document.getElementsByName("template").value=b;
		    if(tax){
    			$("input[name=taxonomy]").val(tax+","+value);
    		}else{
    			$("input[name=taxonomy]").val(value);	
    		}
	    		
	}else if(type=="taxonomyEn"){
		$("input[name=taxonomyEn]").val(value);
	}else if(type=="language"){
		$("input[name=language]").val(value);
	}
	var lcense = $("#lcense1").val();
	$("input[name='curpage']").val(0);
	$("#order1").val('');
	if(lcense=='1'){
		document.formListSearch.action="${ctx}/index/searchLicense";
	}else if(lcense=="2"){
		document.formListSearch.action="${ctx}/index/searchOaFree";
	}else{
		document.formListSearch.action="${ctx}/index/search";
	}
	document.formListSearch.submit();
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
 function dividePage(val){
	 if(val<0){return;}
	 $("#curpage").val(val);
	 document.formListSearch.submit();
 }
 function GO(obj){
	 $("#pageCount").val($(obj).val());
	 $("#curpage").val(0);
	 //searchByCondition('','');
	 document.formListSearch.submit();
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
				$("#add_"+pid).attr("class", "blank");
				$("#add_"+pid).parent().html("<img class='vm' src='${ctx}/images/ico/ico14-grey.png'><ingenta-tag:LanguageTag key='Page.Publications.Lable.Buy' sessionKey='lang' />");
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
				$("#favourites_"+pid).attr("src","${ctx}/images/favourite.png");
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
//]]-->
</script>
<script type="text/javascript">
function searchMe(a,type){
	var searchVal=$(a).text();
	var url='';
	if(type=='author'){
		url='${ctx }/index/search?type=2&isAccurate=1&searchValue2='+searchVal;
	}else if(type=='publisher'){
		url='${ctx }/index/search?type=2&searchsType2=4&searchValue2='+searchVal;
	}
	window.location.href=url;
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
			<c:if test="${facetFields!=null&&fn:length(facetFields)>0 }">
				<c:forEach items="${facetFields }" var="fac" varStatus="index">
					<c:if test="${fac.name!='pubDate'&&fac.name!='taxonomy'&&fac.name!='taxonomyEn' && fac.name!='publisher'}">
						<c:if test="${fac.name=='type' }">
							<h1 class="h1Tit borBot">
								<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Publications" sessionKey="lang" />
							</h1>
						</c:if>						
						<ul>
						<c:if test="${fac.name!='taxonomy'&&fac.name!='taxonomyEn'}">
						
						<c:forEach items="${ typeList}" var="count" varStatus="index2">
							<c:if test="${count.count>0 }">
								<c:if test="${fac.name=='type' && (count.name==1 || count.name==2 || count.name==3 || count.name==4 || count.name==5 || count.name==7)}">
									<li class="oh">
									<a href="javascript:void(0)" onclick="searchByCondition('${fac.name}','${count.name }')">
										<c:if test="${count.name==1 }">
										<span class="fl"><img src="${ctx }/images/ico/ico4.png" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" /></span></c:if>
										<c:if test="${count.name==2 || count.name==7}">
										<span class="fl"><img src="${ctx }/images/ico/ico3.png" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></span></c:if>
										<c:if test="${count.name==3 }">
										<span class="fl"><img src="${ctx }/images/ico/infor.png" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option3" sessionKey="lang" /></span></c:if>
										<c:if test="${count.name==4 }">
										<span class="fl"><img src="${ctx }/images/ico/ico5.png" alt="期刊" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option4" sessionKey="lang" /></span></c:if>
										<c:if test="${count.name==5 }">
										<span class="fl"><img src="${ctx }/images/ico/ico2.png" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option5" sessionKey="lang" /></span></c:if>
										<%-- <c:if test="${count.name==7 }">
										<span class="fl"><img src="${ctx }/images/ico/ico3.png" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></span></c:if> --%>
										<span class="fr">[${count.count}]</span>
									</a>
									</li>
								</c:if>								
							</c:if>							
						</c:forEach>
						</c:if>
						</ul>
					</c:if>
				</c:forEach>
				</c:if>
				
				<c:if test="${facetFields==null||fn:length(facetFields)<=0 }">
					<h1>
						<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Publications" sessionKey="lang" />
					</h1>					
				</c:if>
				</div>
					<div class="leftClassity">
					<h1 class="h1Tit borBot">
						<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Language" sessionKey="lang" />
					</h1>	
					<ul class="updownUl">
					<c:set var="s" value="1"/>
					<c:forEach items="${languageList }" var="count" varStatus="index">
					<c:if test="${ count.count >0}">
						<c:if test="${count.name!=null && count.name!='' }">
						<li class="oh">
							<a href="javascript:void(0)" onclick="searchByCondition('language','${count.name}')" title="${fn:toUpperCase(count.name)}">
								<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
								<span class="write">${fn:toUpperCase(count.name) }</span><span class="fr">[${count.count }]</span>
							</a>
							
						</li>
						</c:if>
					<c:set var="s">${s+1 }</c:set>
					</c:if>
					</c:forEach>					
					</ul>
					<c:if test="${s>6}">
					<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</a></span>
					</c:if>
					</div>
				
				
					<div class="leftClassity">
					<h1 class="h1Tit borBot">
						<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Publisher" sessionKey="lang" />
					</h1>
					<ul class="updownUl">
					<c:set var="s" value="1"/>
					<c:forEach items="${publisherList }" var="count" varStatus="index">
					<c:if test="${count.count>0 }">
						<li class="oh">
						<a href="javascript:void(0)" onclick="searchByCondition('publisher','${count.name }')" title="${count.name }">
							<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
							<span class="write">
								<c:set var="publisherName" value="${count.name }" />
								<c:choose>  
								    <c:when test="${fn:length(publisherName) > 12}">  
								        <c:out value="${fn:substring(publisherName, 0, 12)}..." />  
								    </c:when>  
								   <c:otherwise>  
								      <c:out value="${publisherName}" />  
								    </c:otherwise>  
								</c:choose>  
							</span><span class="fr">[${count.count }]</span>
							</a>
						</li>		
					<c:set var="s">${s+1 }</c:set>
					</c:if>
					</c:forEach>
					</ul>
					<c:if test="${s>6}">
					<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</a></span>
					</c:if>
					</div>
				
					<div class="leftClassity">
					<c:if test="${sessionScope.lang=='zh_CN' }">
					<h1 class="h1Tit borBot">
						<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Subject" sessionKey="lang" />
					</h1>
					<ul  class="updownUl">
					<c:set var="i" value="1"/>
					<c:forEach items="${taxonomyList }" var="count" varStatus="index">
						<c:if test="${count.count>0 }">
								<li class="oh">
								<a href="javascript:void(0)" onclick="searchByCondition('taxonomy','${count.name }')" title="${count.name }">
									<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
									<span class="write"> 
										<c:set var="taxonomyName" value="${count.name }" />
										<c:choose>  
										    <c:when test="${fn:length(taxonomyName) > 14}">  
										        <c:out value="${fn:substring(taxonomyName, 0, 14)}..." />  
										    </c:when>  
										   <c:otherwise>  
										      <c:out value="${taxonomyName}" />  
										    </c:otherwise>  
										</c:choose>  
									</span>
									<span class="fr">[${count.count }]</span>
								</a>
								</li>
							<c:set var="i" >${i+1 }</c:set>
						</c:if>						
					</c:forEach>					
					</ul>
					<c:if test="${i>6}">
					<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</a></span>
					</c:if>
					</c:if>
					<c:if test="${sessionScope.lang=='en_US' }">
					<h1 class="h1Tit borBot">
						<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Subject" sessionKey="lang" />
					</h1>
					<ul class="updownUl">
					<c:set var="i" value="1"/>
					<c:forEach items="${taxonomyEnList }" var="count" varStatus="index">
						<c:if test="${count.count>0 }">
								<li class="oh">
								<a href="javascript:void(0)" onclick="searchByCondition('taxonomyEn','${count.name }')" title="${count.name }">
									<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
									<span class="write"> 
										<c:set var="taxonomyName" value="${count.name }" />
										<c:choose>  
										    <c:when test="${fn:length(taxonomyName) > 20}">  
										        <c:out value="${fn:substring(taxonomyName, 0, 20)}..." />  
										    </c:when>  
										   <c:otherwise>  
										      <c:out value="${taxonomyName}" />  
										    </c:otherwise>  
										</c:choose>
									</span>
									<span class="fr">[${count.count }]</span>
								</a>
								</li>
							<c:set var="i" >${i+1 }</c:set>
						</c:if>						
					</c:forEach>					
					</ul>
					<c:if test="${i>6}">
					<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</a></span>
					</c:if>
					</c:if>
					</div>
					<div class="leftClassity">
				<h1 class="h1Tit borBot">
					<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.PubDate" sessionKey="lang" />
				</h1>
				<ul class="updownUl">
					<c:set var="k" value="1"/>
          			<c:forEach items="${pubDateMap }" var="p" varStatus="index">
          				<c:if test="${p.value>0}">
          					<c:if test="${'0000' != p.key}">
          						<li  class="oh">
					          		<a href="javascript:void(0)" onclick="searchByCondition('pubDate','${p.key }')">
					          			<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
					          			<span class="write">${p.key}</span><span class="fr">[${p.value }]</span>
									</a>
								</li>
							</c:if>
          					<c:set var="k">${k+1 }</c:set>
          				</c:if>
          			</c:forEach>
				</ul>
				<c:if test="${k>6}">
					<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</a></span>
				</c:if>
			</div>
			</div>
			<!--左侧导航栏结束 -->
			<!--右侧列表内容-->
			<div class="chineseRight">
				
						
						<ul class="oh">
							<li class="Stab <c:if test="${current == 'search'}">StabSeleted</c:if> <c:if test="${sessionScope.mainUser==null ||sessionScope.mainUser=='' || sessionScope.institution==null}">Ctab1</c:if><c:if test="${sessionScope.mainUser!=null|| sessionScope.institution!=null}">Ctab</c:if>" name="stab1">
							<input type="hidden" name="isLicenses" id="selectAll" value="" style="*margin-top:4px;" 
							<c:if test="${form.lcense==null||form.lcense!=1 }">checked="checked"</c:if>/>
								<ingenta-tag:LanguageTag key="Global.Page.Select.All" sessionKey="lang"/>  ( <span id="all"><img src="${ctx}/images/loading1.gif"/></span> )</li>
							<li class="Stab <c:if test="${current == 'searchOaFree'}">StabSeleted</c:if> <c:if test="${sessionScope.mainUser==null ||sessionScope.mainUser=='' || sessionScope.institution==null}">Ctab1</c:if><c:if test="${sessionScope.mainUser!=null|| sessionScope.institution!=null}">Ctab</c:if>" name="stab3">
								<input type="hidden" name="isLicenses" id="selectFree" value="2" style="*margin-top:4px;" 
								<c:if test="${(form.lcense!=null&&form.lcense!='')||form.lcense==2 }">checked="checked"</c:if>/>
								<ingenta-tag:LanguageTag key="Global.Button.OaFree" sessionKey="lang"/>  ( <span id="oaFree"><img src="${ctx}/images/loading1.gif"/></span> )
							</li>
							<c:if test="${sessionScope.mainUser!=null || sessionScope.institution!=null}">
							<li class="Stab <c:if test="${current == 'searchLicense'}">StabSeleted</c:if> Ctab" name="stab2">
							<input type="hidden" name="isLicenses" id="selectLicense" value="1" style="*margin-top:4px;" 
							<c:if test="${form.lcense!=null&&form.lcense==1 }">checked="checked"</c:if>/>
								<ingenta-tag:LanguageTag key="Global.Button.license" sessionKey="lang"/>  ( <span id="lcenseIns"><img src="${ctx}/images/loading1.gif"/></span> )</li>
							</c:if>
						</ul>	
						
						
						<%-- <div class="form_in">
							<input type="radio" name="isLicenses" id="selectAll" value="" style="*margin-top:6px;" <c:if test="${form.lcense==null||form.lcense!=1 }">checked="checked"</c:if>/>
								<ingenta-tag:LanguageTag key="Global.Page.Select.All" sessionKey="lang"/>
							<c:if test="${sessionScope.mainUser!=null || sessionScope.institution!=null}">
							<input type="radio" name="isLicenses" id="selectLicense" value="1" style="*margin-top:6px;" <c:if test="${form.lcense!=null&&form.lcense==1 }">checked="checked"</c:if>/>
								<ingenta-tag:LanguageTag key="Global.Button.license" sessionKey="lang"/>
							</c:if>
						</div> --%>
						
				<div  class="StabContent ScontentSelected CtabContent">	
					<div class="mt5 mb5 oh">
						<span class="fl">
							<c:if test="${form.searchValue2!=null&&form.searchValue2!='' }">
								<c:if test="${sessionScope.lang=='zh_CN'}">
									<c:if test="${queryCount > 1000}">
									<p>关键字'${form.searchValue2}'命中了${queryCount }个结果，仅显示命中关键字的前1000条资源 </p>
									</c:if>
									<c:if test="${queryCount <= 1000}">
									<p>关键字'${form.searchValue2 }'命中了${queryCount }个结果</p>
									</c:if>
								</c:if>
								<c:if test="${sessionScope.lang=='en_US'}">
									<c:if test="${queryCount > 1000}">
									<p>Keyword '${form.searchValue2 }' hit ${queryCount } results ,Display only a keyword before 1000 resources </p>
									</c:if>
									<c:if test="${queryCount <= 1000}">
									<p>Keyword '${form.searchValue2 }' hit ${queryCount } results </p>
									</c:if>
								</c:if>
							</c:if>
							<input type="hidden" id="searchsFlag" value="${form.searchsType }"/>
							<input type="hidden" id="searchsWord" value="${form.searchValue2 }"/>
							
						</span>
						<c:if test="${sessionScope.mainUser!=null && sessionScope.mainUser.level==2}">
						<span class="fr borSpan h20">
							<a id="downList" href="javascript:void(0)" class="ico_new" title="<ingenta-tag:LanguageTag key="Pages.view.Label.Download_Catalog" sessionKey="lang"/>">
								<img src="${ctx }/images/ico/download.png"/><ingenta-tag:LanguageTag key="Pages.view.Label.Download_Catalog" sessionKey="lang"/>
							</a>
							
						</span>
						</c:if>
					</div>
					<div class="take">
						<c:if test="${((sessionScope.specialInstitutionFlag==null||sessionScope.specialInstitutionFlag=='')&&form.pubType!=null&&form.pubType!='')||(form.publisher!=null&&form.publisher!='')||(form.pubDate!=null&&form.pubDate!='')||(form.taxonomy!=null&&form.taxonomy!='')||(form.taxonomyEn!=null&&form.taxonomyEn!='')||(form.language!=null&&form.language!='') }">
 						<div style="float:left;"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Lable.SearchRange" sessionKey="lang" />:&nbsp;&nbsp;&nbsp;&nbsp;</div>
						<div class="label_list" >
							<c:if test="${sessionScope.specialInstitutionFlag eq null||sessionScope.specialInstitutionFlag eq '' }">
							<c:if test="${form.pubType!=null&&form.pubType!=''}">
								<a href="javascript:void(0)" name="conditions" class="label" id="pubType_label">
								<c:if test="${form.pubType==1 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==2 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==3 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option3" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==4 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option4" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==5 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option5" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==7 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></c:if>
							</a>
							</c:if>
							</c:if>
							<c:if test="${form.publisher!=null&&form.publisher!='' }">
							<a href="javascript:void(0)" name="conditions" class="label" id="publisher_label">${form.publisher }</a>
							</c:if>
							<c:if test="${form.pubDate!=null&&form.pubDate!='' }">
							<a href="javascript:void(0)" name="conditions" class="label" id="pubDate_label">${form.pubDate }</a>
							</c:if>
							<c:if test="${form.taxonomy!=null&&form.taxonomy!='' }">
							<a href="javascript:void(0)" name="conditions" class="label" id="taxonomy_label">${form.taxonomy }</a>
							</c:if>
							<c:if test="${form.taxonomyEn!=null&&form.taxonomyEn!='' }">
							<a href="javascript:void(0)" name="conditions" class="label" id="taxonomyEn_label">${form.taxonomyEn }</a>
							</c:if>
							<c:if test="${form.language!=null&&form.language!='' }">
							<a href="javascript:void(0)" name="conditions" class="label" id="language_label">${fn:toUpperCase(form.language) }</a>
							</c:if>
						</div>
						</c:if>
					</div>
				<c:if test="${fn:length(list) >0}">	
				<div class="sort" >
					<div class="fl mt10">
						<!-- <input type="checkbox" name="" value="" />全部 --> <ingenta-tag:LanguageTag key="Pages.User.UserTypeManage.order" sessionKey="lang"/>
						<select id="sort" style="width:95px;">
							<option <c:if test="${form.searchOrder=='' }">selected="selected"</c:if> value=""><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Order.Option1" sessionKey="lang" /></option>
							<option <c:if test="${form.searchOrder=='desc' }">selected="selected"</c:if> value="desc"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Order.Option2" sessionKey="lang" /></option>
							<option <c:if test="${form.searchOrder=='asc' }">selected="selected"</c:if> value="asc"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Order.Option3" sessionKey="lang" /></option>
						</select>
					</div>
							<jsp:include page="../pageTag/pageTag.jsp">
					        <jsp:param value="${form }" name="form"/>
			                </jsp:include>
					<!--以下 提交查询Form 开始-->
					<!--以上 提交查询Form 结束-->
				</div>
				</c:if>
				<h1 class="h1Tit borBot">
					<ingenta-tag:LanguageTag key="Page.Publications.Journal.Article.List" sessionKey="lang" />
				</h1>
				<!--列表内容开始-->
				<div class="block">
				<c:if test="${fn:length(list) >0}">
				<c:forEach items="${list }" var="p" varStatus="index">
					<c:set var="license">${(p.subscribedIp!=null||p.subscribedUser!=null)&&(p.subscribedIp>0||p.subscribedUser>0) }</c:set>
					<c:set var="news">${p.latest!=null&&p.latest>0 }</c:set>
					<c:set var="oa">${p.oa!=null&&p.oa==2 }</c:set>
					<c:set var="free">${p.free!=null&&p.free==2 }</c:set>
					<c:set var="collection">${p.inCollection!=null&&p.inCollection>0 }</c:set>
					<c:set var="add1" value="${p.priceList!=null&&fn:length(p.priceList)>0&&p.free!=2&&p.oa!=2&&sessionScope.mainUser!=null && p.subscribedUser<=0&&(p.buyInDetail<=0&&p.exLicense>=0)}"/>
					<c:if test="${add1==false }">
						<c:set var="add" value="false"/>
					</c:if>
					<c:if test="${add1==true &&p.subscribedIp>0 }">
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
					<c:if test="${license==false&&oa==false&&free==false }">
						<div class="fl w40 mt2">
							<p class="p_left">
							<img src="${ctx }/images/ico/ico_close.png" />
							
							<c:if test="${p.type==1 }">
								<img src="${ctx }/images/ico/ico4.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Book" sessionKey="lang" />" />
							</c:if>
							<c:if test="${p.type==2||p.type==6||p.type==7 }">
								<img src="${ctx }/images/ico/ico3.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Journal" sessionKey="lang" />" />
							</c:if>
							<c:if test="${p.type==4 }">
								<img src="${ctx }/images/ico/ico5.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Article" sessionKey="lang" />" />
							</c:if>
							<%-- <a name="title" href="${ctx}/pages/publications/form/article/${p.id}?sv=${form.searchValue}&fp=2" title="${p.title }">${p.title }</a> --%>
							</p>
							<c:if test="${news==true||free==true||oa==true||collection==true }">
							<p class="p_right">
								<%-- <c:if test="${news==true }"><img src="${ctx }/images/n.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.New" sessionKey="lang" />"/></c:if> --%>
								<c:if test="${free==true }"><img src="${ctx }/images/ico/f.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Free" sessionKey="lang" />"/></c:if>
								<c:if test="${oa==true }"><img src="${ctx }/images/ico/o.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.OA" sessionKey="lang" />"/></c:if>
								<%-- <c:if test="${collection==true }"><img src="${ctx }/images/c.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Collection" sessionKey="lang" />"/></c:if> --%>
							</p>
							</c:if>
						</div>
					</c:if>
					<c:if test="${license==true||oa==true||free==true }">
						<div class="fl w40 mt2">
							<c:if test="${free==true }"><img width="14" height="14" src="${ctx }/images/ico/f.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Free" sessionKey="lang" />"/></c:if>
							<c:if test="${oa==true }"><img width="14" height="14" src="${ctx }/images/ico/o.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.OA" sessionKey="lang" />"/></c:if>
							<c:if test="${license==true }"><img width="14" height="14" src="${ctx }/images/ico/ico_open.png" /></c:if>
							<c:if test="${p.type==1 }">
								<img src="${ctx }/images/ico/ico4.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Book" sessionKey="lang" />" />
							</c:if>
							<c:if test="${p.type==2||p.type==6||p.type==7 }">
								<img src="${ctx }/images/ico/ico3.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Journal" sessionKey="lang" />" />
							</c:if>
							<c:if test="${p.type==4 }">
								<img src="${ctx }/images/ico/ico5.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Article" sessionKey="lang" />" />
							</c:if>
						</div>
					</c:if>	
							
					<div class="fl w700" style="margin-bottom: 15px;">	
						<p>
							<c:if test="${p.type==1}">
								<a href="${ctx}/pages/publications/form/article/${p.id}?fp=3&sv=${form.searchValue}" title="${fn:replace(p.title,"\"","\'")}}">				
									<c:if test="${p.cover!=null&&p.cover!='' }">
										<img  src="${ctx}/pages/publications/form/cover?t=1&id=${p.id}" width="85" height="116"  class="fr ml20"/>
									</c:if>	
								</a>					
							</c:if>
							<c:if test="${p.type==2||p.type==6||p.type==7}">
								<a href="${ctx}/pages/publications/form/journaldetail/${p.id}" title="${fn:replace(p.title,"\"","\'")}">				
									<c:if test="${p.cover!=null&&p.cover!='' }">
										<img  src="${ctx}/pages/publications/form/cover?t=1&id=${p.id}" style="width:1px;height:1px;float:right; margin-top:5px; margin-right:2px;" />
									</c:if>		
								</a>				
							</c:if>
						</p>
						<p>
							<a class="a_title" name="title" href="${ctx}/pages/publications/form/article/${p.id}?fp=3&sv=${form.searchValue}" title="${fn:replace(p.title,"\"","\'")}">${fn:replace(fn:replace(fn:replace(p.title, "&lt;", "<"),"&gt;",">"),"&amp;","&")}</a>
						</p>	

						<c:if test="${p.type!=2 }">
						<p>
						    By
						    <c:set var="authors" value="${fn:split(p.author,',')}" ></c:set>
			                <c:forEach items="${authors}" var="a" >
			                <a href="javascript:void(0)" onclick="searchMe(this,'author')">${a}</a> &nbsp;
			                </c:forEach>
							<c:if test="${p.type==4||p.type==6||p.type==7 }"> in 
							<a href="${ctx}/pages/publications/form/article/${p.publications.id}?fp=3&sv=${form.searchValue}">${p.publications.title}</a>
							</c:if>
						</p>
						</c:if>
						<c:if test="${p.type==2 }">
						<p>
						<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" /> ${p.startVolume }-<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" /> ${p.endVolume }
						</p>
						</c:if>
						<p>
						 <a href="javascript:void(0)" onclick="searchMe(this,'publisher')">${p.publisher.name}</a>(${fn:substring(p.pubDate,0,4) })
						</p>
						<p>
						  <c:if test="${p.type==4||p.type==6||p.type==7||p.type==2 }">
						   ISSN:${p.code}
						  </c:if>
						</p>
						<c:if test="${p.journalType==2}">
						<p>
					     <ingenta-tag:LanguageTag key="Page.Frame.Periodic.type" sessionKey="lang" />:
					     <c:if test="${p.periodicType==1}"><ingenta-tag:LanguageTag key="Page.Frame.Periodic.type.Weekly" sessionKey="lang" /></c:if>
					     <c:if test="${p.periodicType==2}"><ingenta-tag:LanguageTag key="Page.Frame.Periodic.type.Semi.monthly" sessionKey="lang" /></c:if>
					     <c:if test="${p.periodicType==3}"><ingenta-tag:LanguageTag key="Page.Frame.Periodic.type.monthly" sessionKey="lang" /></c:if>
					     <c:if test="${p.periodicType==4}"><ingenta-tag:LanguageTag key="Page.Frame.Periodic.type.Quarterly" sessionKey="lang" /></c:if>
					     <c:if test="${p.periodicType==5}"><ingenta-tag:LanguageTag key="Page.Frame.Periodic.type.Semi.annual" sessionKey="lang" /></c:if>
					     <c:if test="${p.periodicType==6}"><ingenta-tag:LanguageTag key="Page.Frame.Periodic.type.annual" sessionKey="lang" /></c:if>
					     <c:if test="${p.periodicType==7}"><ingenta-tag:LanguageTag key="Page.Frame.Periodic.type.Other" sessionKey="lang" /></c:if>
						</p>
					     </c:if>
						<p>
						  <c:if test="${p.type==1}">
						   ISBN:${p.code}
						  </c:if>
						</p>
					<%-- 
					<c:if test="${p.remark!=null  && p.remark!='' }">
					<tr>
					<td colspan="2">
						<table border="0">
						<tr>
							<td valign="top" class="tda" style="vertical-align:top;"><ingenta-tag:LanguageTag key="Pages.Index.Lable.Abstract" sessionKey="lang" />：&nbsp;</td>
							<td valign="top" class="tdc"  style="vertical-align:top;" name="remark">
							<div style="height:20px;overflow: hidden;"><a style="cursor: pointer;" onclick="senfe(this);">+ <ingenta-tag:LanguageTag key="Page.Index.Search.Desc.Show" sessionKey="lang" /></a>
							<p class="clearcss">
							${fn:replace(fn:replace(fn:replace(p.remark,"&lt;","<"),"&gt;",">"),"&amp;","&")}  			
							</p>
							</div>
							</td>
						</tr>
						</table>						
					</td>
					</tr>
					</c:if>
					 --%>
					
					
						
						
						<%-- 	<c:if test="${p.priceList!=null && fn:length(p.priceList)>0&&add==true}">
							<!-- 价格列表 -->
								<select name="price_${p.id }" id="price_${p.id }" class="select_box" style="float:left; height:24px">
									<c:forEach items="${p.priceList }" var="pr" varStatus="indexPr">
									<option value="${pr.id }"><c:if test="${pr.type==2 }">L</c:if><c:if test="${pr.type==1 }">P</c:if>${pr.complicating}-${pr.price }${pr.currency }</option>
									</c:forEach>
								</select>
							</c:if> --%>
						<p>
				<%-- 			<c:if test="${license==true||oa==true||free==true }">
							<!-- 在线阅读 -->
								<span class="mr20"><a href="javascript:void(0)" title="<ingenta-tag:LanguageTag key="Global.Lable.Prompt.Preview" sessionKey="lang" />" onclick="viewPopTips('${p.id}','0',<c:if test="${oa==false&&free==false}">1</c:if><c:if test="${oa==true||free==true}">2</c:if>)"><img src="${ctx }/images/ico/reading.png" class="vm" /><ingenta-tag:LanguageTag key="Global.Lable.Prompt.Preview" sessionKey="lang" /></a></span>
							</c:if> --%>
							<!-- 无购买权限 -->
							<c:if test="${sessionScope.mainUser!=null }">
								<c:if test="${!add && license==false && oa==false && free==false}">
									<span class="mr20">
										<img src="${ctx }/images/ico/ico14-grey.png" class="vm" /><ingenta-tag:LanguageTag key='Page.Publications.Lable.Buy' sessionKey="lang"/>
									</span>
								</c:if>
							</c:if>
							<c:if test="${add }">
							<!-- 购买 -->
								<span class="mr20">
									<a href="javascript:void(0)" onclick="addToCart('${p.id}',1);" id="add_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Buy' sessionKey='lang'/>">
										<img src="${ctx }/images/ico/ico14-blank.png" class="vm" />
											<ingenta-tag:LanguageTag key='Page.Publications.Lable.Buy' sessionKey="lang"/>
									</a>
								</span> 
							</c:if>
							<!-- 获取资源 -->
						<c:if test="${license==false&&oa==false&&free==false }">
							<span class="mr20">
							<a href="javascript:void(0)"  id="resource_div" onclick="popTips2('${p.id}');"><img src="${ctx }/images/ico/ico15-blue.png" class="vm" />
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
								<a href="javascript:void(0)"  id="resource_div1"  onclick="popTips2('${p.id}');"><img src="${ctx }/images/ico/ico15-green.png" class="vm" />
								<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
								</a>
							</span>
						</c:if>
						</c:if>
							<c:if test="${recommand}">
							<!-- 推荐 -->
								<span class="mr20"><a href="javascript:void(0)" id="recommand_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' />" onclick="recommends('${p.id}');">
								<img src="${ctx }/images/ico/ico16-blue.png" class="vm" /><ingenta-tag:LanguageTag key="Page.Index.Search.Link.Recommend" sessionKey="lang" /></a></span>
							</c:if>
							
							<!-- 收藏 -->
							<c:if test="${favourite}">
								<span class="mr20 favourite" id="${p.id}">
									<a href="javascript:;" class="">
										<img src="${ctx }/images/unfavourite.png" class="vm" /><span><ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' /></span>
									</a>
								</span> 
							</c:if>
							
							<!-- 已收藏 -->
							<c:if test="${sessionScope.mainUser!=null && !favourite }">
							  <span class="mr20 favourite" id="${p.id}">
							  	<a href="javascript:void(0)" class="blank">
							  		<img src="${ctx }/images/favourite.png" class="vm" /><span><ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' /></span>
							  	</a>
							  </span>
							</c:if>
							
							<!-- 检索权重值： -->
							<c:if test="${p.activity!=null}">
								<font style="color: #FFFFFF;">
									${p.activity}
								</font>
							</c:if>
						</p>
				</div>	
				<p style="height:1px; width:1px; clear:both;">&nbsp;</p>
				</c:forEach>
				</c:if>
				<c:if test="${fn:length(list) <=0}">
					<br/><br/>
					<p align="center">
						<b>
							<c:if test="${form.lcense==null || form.lcense==''}">
								<ingenta-tag:LanguageTag key="Page.All.Alert" sessionKey="lang" />
									<font color="red">${form.searchValue}</font>
								<ingenta-tag:LanguageTag key="Page.All.Alert1" sessionKey="lang" />
							</c:if>
							<c:if test="${form.lcense==2 || form.lcense=='2' }">
								<ingenta-tag:LanguageTag key="Page.OaFree.Alert" sessionKey="lang" />
									<font color="red">${form.searchValue}</font>
								<ingenta-tag:LanguageTag key="Page.OaFree.Alert1" sessionKey="lang" />
							</c:if>
							<c:if test="${form.lcense==1 || form.lcense=='1' }">
								<ingenta-tag:LanguageTag key="Page.SearchLicense.Alert" sessionKey="lang" />
									<font color="red">${form.searchValue}</font>
								<ingenta-tag:LanguageTag key="Page.SearchLicense.Alert1" sessionKey="lang" />
							</c:if>
					</p>
					<br/>
				</c:if>
				</div>
				<!--列表内容结束-->
				<%--
				<div class="sort" >
				 <ingenta-tag-v2:SplitTag 
					pageCount="${form.pageCount}" 
                	count="${form.count}" 
                	page="${form.curpage}" 
                	url="${form.url}" 
                	i18n="${sessionScope.lang}"
                	method="post"
                	formName="formListSearch"
                	next_ico="${ctx }/images/right_light.gif"
                	prev_ico="${ctx }/images/left_light.gif"
                	isMain="false"
                	identity="01"
				/> 
			</div>
			--%>
			<jsp:include page="../pageTag/pageTag.jsp">
			        <jsp:param value="${form }" name="form"/>
                </jsp:include>	
			<!--右侧列表内容结束-->
			</div>
		</div>
		<!--以上中间内容块结束-->
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
<script type="text/javascript">
	$(function() {
		var searchsFlag = $("#searchsFlag").val();
		$("input[name='searchsType']").val(searchsFlag);
		var searchsWord = $("#searchsWord").val();
		$("input[name='searchValue2']").val(searchsWord);
		$.ajax({
		    type: 'post',
		    url: '${ctx}/index/allByAjax',
		    data: $("#formListSearch").serialize(),
		    success : function(data) {
		    	$("#all").html(data);
		    }
		});
		$.ajax({
		
		    type: 'post',
		    url: '${ctx}/index/oaFreeByAjax',
		    data: $("#formListSearch").serialize(),
		    success : function(data) {
		    	$("#oaFree").html(data);
		    }
		});
		$.ajax({
		    type: 'post',
		    url: '${ctx}/index/lcenseInsByAjax',
		    data: $("#formListSearch").serialize(),
		    success : function(data) {
		    	$("#lcenseIns").html(data);
		    }
		});
	});
</script>
</body>
</html>
