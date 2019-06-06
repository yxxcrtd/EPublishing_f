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
	/* $("input[name='isLicenses']").click(function(){
		var lcense = $("#lcense2").val();
		var param = $(this).attr("id");
		if(param=="selectAll"){
			$("#lcense2").val("");
			$("#pageCount").val(10);
			$("#order2").val('');
		}else if(param=="selectLicense"){
			$("#lcense2").val("1");
			$("#pageCount").val(10);
			$("#order2").val('');
		}
		$("input[name='curpage']").val(0);
		document.formList.action="${ctx}/index/advancedSearchSubmit";
		document.formList.submit();
	}); */
	var Request = new Object();
	Request = GetRequest();
	$("li[name='stab1']").click(function(){
		var lcense = $("#lcense2").val();
		$("input[name='isLicenses']").attr("id","selectAll");
		var param = $("input[name='isLicenses']").attr("id");
		if(param=="selectAll"){
			$("#lcense2").val("");
			$("#pubType2").val();
			$("#order2").val('');
			$("#selectflag").val('all');
		}else if(param=="selectLicense"){
			$("#lcense2").val("1");
			$("#pubType2").val();
			$("#order2").val('');
			$("#selectflag").val('license');
		}else if(param=="selectFree"){
			$("#lcense2").val("2");
			$("#pubType2").val();
			$("#order2").val('');
			$("#selectflag").val('oaFree');
		}
		$("input[name='curpage']").val(0);
		document.formList.action="${ctx}/index/advancedSearchSubmit";
		document.formList.submit();
	});
	
	$("li[name='stab3']").click(function(){
		var lcense = $("#lcense2").val();
		$("input[name='isLicenses']").attr("id","selectFree");
		var param = $("input[name='isLicenses']").attr("id");
		if(param=="selectAll"){
			$("#lcense2").val("");
			$("#pubType2").val();
			$("#order2").val('');
			$("#selectflag").val('all');
		}else if(param=="selectLicense"){
			$("#lcense2").val("1");
			$("#pubType2").val();
			$("#order2").val('');
			$("#selectflag").val('license');
		}else if(param=="selectFree"){
			$("#lcense2").val("2");
			$("#pubType2").val();
			$("#order2").val('');
			$("#selectflag").val('oaFree');
		}
		$("input[name='curpage']").val(0);
		document.formList.action="${ctx}/index/advancedSearchSubmit";
		document.formList.submit();
	});
	
	$("li[name='stab2']").click(function(){
		var lcense = $("#lcense2").val();
		$("input[name='isLicenses']").attr("id","selectLicense");
		var param = $("input[name='isLicenses']").attr("id");
		if(param=="selectAll"){
			$("#lcense2").val("");
			$("#pubType2").val();
			$("#order2").val('');
			$("#selectflag").val('all');
		}else if(param=="selectLicense"){
			$("#lcense2").val("1");
			$("#pubType2").val();
			$("#order2").val('');
			$("#selectflag").val('license');
		}else if(param=="selectFree"){
			$("#lcense2").val("2");
			$("#pubType2").val();
			$("#order2").val('');
			$("#selectflag").val('oaFree');
		}
		$("input[name='curpage']").val(0);
		document.formList.action="${ctx}/index/advancedSearchSubmit";
		document.formList.submit();
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
		var lcense = $("#lcense2").val();
		var param = $(this).attr("id");
		if(param=="pubType_label"){
			$("#pubType2").val('');
		}else if(param=="prefixWord_label"){
			$("#prefixWord").val('');			
		}else if(param=="publisher_label"){
			$("#publisher2").val('');
		}else if(param=="pubDate_label"){
			$("#pubDate2").val('');
		}else if(param=="searchValue_label"){
			$("#searchValue1").val('');
		} else if(param.indexOf("taxonomy_label")==0){
			var tax=$(this).text().trim();
			var allTax = $("#taxonomy2").val();
			allTax=allTax.replace(tax,'').replace(',,',',');
			allTax=allTax.replace(/^,+/,'');
			allTax=allTax.replace(/,+$/,'');
			$("#taxonomy2").val(allTax);
		}else if(param=="taxonomyEn_label"){
			$("#taxonomyEn2").val('');
		}else if(param=="language_label"){
			$("#language2").val('');
		}
		$(this).css("display","none");
		$("input[name='curpage']").val(0);
		document.formList.action="${ctx}/index/advancedSearchSubmit";
		document.formList.submit();
	});	
	//下载列表
	$("#downList").click(function(){
		var url = "${ctx}/index/advancedSearchDownList";
        url += "?searchsType="+$("#type2").val();
        url += "&searchValue="+$("#searchValue1").val();
        url += "&pubType="+$("#pubType2").val();
        url += "&publisher="+$("#publisher2").val();
        url += "&language="+$("#language2").val();
        url += "&pubDate="+$("#pubDate2").val();
        	
        url += "&taxonomy="+$("#taxonomy2").val();
        url += "&taxonomyEn="+$("#taxonomyEn2").val();
        url += "&searchOrder="+$("#order2").val();
        url += "&lcense="+$("#lcense2").val();
        	
        url += "&code="+$("#code2").val();
        url += "&pCode="+$("#pcode2").val();
        url += "&publisherId="+$("#publisherId2").val();
        url += "&subParentId="+$("#subParentId2").val();
        url += "&parentTaxonomy="+$("#parenttaxonomy2").val();
        url += "&parentTaxonomyEn="+$("#parenttaxonomyEn2").val();
        url += "&curPage="+$("#curpage1").val();
        url += "&pageCount="+$("#pageCount1").val();
        url += "&keywordCondition="+$("#keywordCondition2").val();
        url += "&notKeywords="+$("#notKeywords2").val();
        url += "&author="+$("#author2").val();
        url += "&title="+$("#title1").val();
        url += "&isCn="+$("#isCn").val();
        window.location.href=url;
	});
});
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

function GetRequest() {
	var url = location.search; //获取url中"?"符后的字串
	var theRequest = new Object();
	if (url.indexOf("?") != -1) {
	var str = url.substr(1);
	strs = str.split("&");
	for(var i = 0; i < strs.length; i ++) {
	theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
	}
	}
	return theRequest;
} 


function dividePage(val){
	 if(val<0){return;}
	$("#curpage1").val(val);
	document.formList.submit();
	//searchByCondition('','');
}
function GO(obj){
	$("#pageCount1").val($(obj).val());
	$("#curpage1").val(0);
	document.formList.submit();
	//searchByCondition('','');
}
//左侧条件查询
function searchByCondition(type,value){
	var verify=null;
	if(type=="type"){
		$("input[name=pubType]").val(value);
	}else if(type=="publisher"){
		//$("input[name=publisher]").val(value); 
		//增加左侧链接标识
		$("input[name=publisher]").val("_"+value); 
	}else if(type=="pubDate"){
		$("input[name=pubDate]").val(value);
	}else if(type=="taxonomy"){
		var tax=$("input[name=taxonomy]").val();
		var s=$("#taxonomy2").val();
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
	var lcense = $("#lcense2").val();
	$("input[name='curpage']").val(0);
	$("#order2").val('');
	if(verify!=1){
	document.formList.action="${ctx}/index/advancedSearchSubmit";
	document.formList.submit();
	}
}
//在线阅读
 function viewPopTips(id,page,yon) {
	var url="";
	         
        //tmp.focus() ;
	if(page=='0'){
		url = "${ctx}/pages/view/form/view?id="+id;
	}else{
		url = "${ctx}/pages/view/form/view?id="+id+"&nextPage="+page;
	}
	///首先Ajax查询要阅读的路径
	if(yon=='2'){
	var tmp=window.open("about:blank","","scrollbars=yes,resizable=yes,channelmode") ; 
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
			var tmp=window.open("about:blank","","scrollbars=yes,resizable=yes,channelmode") ; 
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
	art.dialog.open("${ctx}/pages/recommend/form/edit?pubid="+pid,{title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/>",top: 100,width: 550, height: 450,lock:true});
}

// 获取资源弹出层调用
function popTips2(pid) {
	art.dialog.open("${ctx}/pages/publications/form/getResource?pubid=" + pid, {id : "getResourceId", title: "", top: 200, lock: true});
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
				//$("#add_"+pid).css("display","none");
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
function sortChange(v){
	var lcense = $("#lcense2").val();
		$("#order2").val(v);
		$("input[name='curpage']").val(0);
		document.formList.action="${ctx}/index/advancedSearchSubmit";
		document.formList.submit();
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
						
						<c:forEach items="${fac.values }" var="count" varStatus="index2">
							<c:if test="${count.count>0 }">
								<c:if test="${fac.name=='type' && (count.name==1 || count.name==2 || count.name==3 || count.name==4 || count.name==5|| count.name==7)}">
									<li class="oh">
									<a href="javascript:void(0)" onclick="searchByCondition('${fac.name}','${count.name }')">
										<c:if test="${count.name==1 }">
										<span class="fl"><img src="${ctx }/images/ico/ico4.png" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" /></span><span class="fr">[${count.count }]</span></c:if>
										<c:if test="${count.name==2 ||count.name==7}">
										<span class="fl"><img src="${ctx }/images/ico/ico3.png" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></span><span class="fr">[${count.count }]</span></c:if>
										<c:if test="${count.name==3 }">
										<span class="fl"><img src="${ctx }/images/ico/infor.png" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option3" sessionKey="lang" /></span><span class="fr">[${count.count }]</span></c:if>
										<c:if test="${count.name==4 }">
										<span class="fl"><img src="${ctx }/images/ico/ico5.png" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option4" sessionKey="lang" /></span><span class="fr">[${count.count }]</span></c:if>
										<c:if test="${count.name==5 }">
										<span class="fl"><img src="${ctx }/images/ico/ico2.png" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option5" sessionKey="lang" /></span><span class="fr">[${count.count }]</span></c:if>
										<%-- <c:if test="${count.name==7}">
										<span class="fl"><img src="${ctx }/images/ico/ico3.png" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></span><span class="fr">[${count.count }]</span></c:if> --%>
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
				<%-- <h1 class="h1Tit borBot">
					<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Publisher" sessionKey="lang" />
				</h1>
				<ul class="updownUl">
				<c:set var="s" value="1"/>
				<c:forEach items="${publisherList }" var="count" varStatus="index">
				<c:if test="${count.count>0 }">
				<c:if test="${s<=5}">
					<li class="oh">
					<a href="javascript:void(0)" onclick="searchByCondition('publisher','${count.name }')" title="${count.name }">
						<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
						<span class="write">${count.name }</span><span class="fr">[${count.count }]</span>
						</a>
					</li>
				</c:if>
				<c:if test="${s>5}">
					<li name="pubmore" style="display: none;" class="oh">
					<a href="javascript:void(0)" onclick="searchByCondition('publisher','${count.name }')" title="${count.name }">
						<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
						<span class="write">${count.name }</span><span class="fr">[${count.count }]</span>
					</a>
					</li>
				</c:if>
				<c:if test="${s>5&&(index.index+1)==fn:length(publisherList) }">
				<li class="oh">
					<a id="seepub" style="cursor:pointer" onclick="seePubMore();" class="more">
						<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
						<span class="write"><ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" />...</span>
					</a>
					<input type="hidden" id="morePubStatus" value="1"/>
				</li>
				</c:if>
				<c:set var="s">${s+1 }</c:set>
				</c:if>
				</c:forEach>
				</ul> --%>
				<h1 class="h1Tit borBot">
					<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Publisher" sessionKey="lang" />
				</h1>
				<ul  class="updownUl">
				<c:set var="s" value="1"/>
				<c:forEach items="${publisherList }" var="count" varStatus="index">
				<c:if test="${count.count>0 }">
					<li class="oh"><a href="javascript:void(0)" onclick="searchByCondition('publisher','${count.name }')" title="${count.name }">
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
						</span>
						<span class="fr">[${count.count }]</span>
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
				
				<c:if test="${count.count!=0 }">
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
							<a href="javascript:void(0)" onclick="searchByCondition('taxonomy','${count.name}')" title="${count.name }">
								<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
								<span class="write"> 
								<c:set var="taxonomyName" value="${count.name }"/>
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
				</c:if>
				
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
						
						<c:if test="${param.subFlag ne '1' && param.subFlag ne 1}">
								<ul class="oh">
									<li class="Stab <c:if test="${current == 'search'}">StabSeleted</c:if> <c:if test="${sessionScope.mainUser==null ||sessionScope.mainUser=='' || sessionScope.institution==null}">Ctab1</c:if><c:if test="${sessionScope.mainUser!=null|| sessionScope.institution!=null}">Ctab</c:if>" name="stab1">
										<input type="hidden" name="isLicenses" id="selectAll" value="" style="*margin-top:4px;" 
										<c:if test="${form.lcense==null||form.lcense!=1 }">checked="checked"</c:if>/>
										<ingenta-tag:LanguageTag key="Global.Page.Select.All" sessionKey="lang"/>  ( <span id="all"><img src="${ctx}/images/loading1.gif"/></span> )
									</li>
									
									<li class="Stab <c:if test="${current == 'searchOaFree'}">StabSeleted</c:if> <c:if test="${sessionScope.mainUser==null ||sessionScope.mainUser=='' || sessionScope.institution==null}">Ctab1</c:if><c:if test="${sessionScope.mainUser!=null|| sessionScope.institution!=null}">Ctab</c:if>" name="stab3">
										<input type="hidden" name="isLicenses" id="selectFree" value="2" style="*margin-top:4px;" 
										<c:if test="${(form.lcense!=null&&form.lcense!='')||form.lcense==2 }">checked="checked"</c:if>/>
										<ingenta-tag:LanguageTag key="Global.Button.OaFree" sessionKey="lang"/>  ( <span id="oaFree"><img src="${ctx}/images/loading1.gif"/></span> )
									</li>
									
									<c:if test="${sessionScope.mainUser!=null || sessionScope.institution!=null}">
										<li class="Stab <c:if test="${current == 'searchLicense'}">StabSeleted</c:if> Ctab" name="stab2">
											<input type="hidden" name="isLicenses" id="selectLicense" value="1" style="*margin-top:4px;" 
											<c:if test="${form.lcense!=null&&form.lcense==1 }">checked="checked"</c:if>/>
											<ingenta-tag:LanguageTag key="Global.Button.license" sessionKey="lang"/>  ( <span id="advancedLcenseIns"><img src="${ctx}/images/loading1.gif"/></span> )
										</li>
									</c:if>
								</ul>
						</c:if>	
						<%-- <div class="form_in">
							<input type="radio" name="isLicenses" id="selectAll" value="" style="*margin-top:6px;" <c:if test="${form.lcense==null||form.lcense!=1 }">checked="checked"</c:if>/>
								<ingenta-tag:LanguageTag key="Global.Page.Select.All" sessionKey="lang"/>
							<c:if test="${sessionScope.mainUser!=null || sessionScope.institution!=null}">
							<input type="radio" name="isLicenses" id="selectLicense" value="1" style="*margin-top:6px;" <c:if test="${form.lcense!=null&&form.lcense==1 }">checked="checked"</c:if>/>
								<ingenta-tag:LanguageTag key="Global.Button.license" sessionKey="lang"/>
							</c:if>
						</div> --%>
						
				<div  class="StabContent ScontentSelected CtabContent">	
				<c:if test="${param.subFlag ne '1' && param.subFlag ne 1}">
					<div class="mt5 mb5 oh">
						<span class="fl">
							<c:if test="${form.searchValue!=null&&form.searchValue!='' }">
							<c:if test="${sessionScope.lang=='zh_CN'}">
							<p>关键字'${form.searchValue }'命中了${queryCount}个结果，仅显示命中关键字的前1000条资源 </p>
							</c:if>
							<c:if test="${sessionScope.lang=='en_US'}">
							<p>${form.count } Result(s) for '${form.searchValue }',Display only a keyword before 1000 resources </p>
							</c:if>
							</c:if>
						</span>
						<c:if test="${sessionScope.mainUser!=null && sessionScope.mainUser.level==2 }">
						<span class="fr borSpan h20">
							<a id="downList" href="javascript:void(0)" class="ico_new"  title="<ingenta-tag:LanguageTag key="Pages.view.Label.Download_Catalog" sessionKey="lang"/>">
								<img src="${ctx }/images/ico/download.png"/><ingenta-tag:LanguageTag key="Pages.view.Label.Download_Catalog" sessionKey="lang"/>
							</a>
						</span>
						</c:if>
					</div>
					</c:if>
					<c:if test="${param.subFlag ne '1' && param.subFlag ne 1}"></c:if>
						<c:if test="${((sessionScope.specialInstitutionFlag==null||sessionScope.specialInstitutionFlag=='')&&form.pubType!=null&&form.pubType!='')||(form.publisher!=null&&form.publisher!='')||(form.pubDate!=null&&form.pubDate!='')||(form.taxonomy!=null&&form.taxonomy!='')||(form.taxonomyEn!=null&&form.taxonomyEn!='')||(form.language!=null&&form.language!='') }">
					<div class="take">
						<div style="float:left;"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Lable.SearchRange" sessionKey="lang" />:&nbsp;&nbsp;&nbsp;&nbsp;</div>
						<div class="label_list" >
							<c:if test="${sessionScope.specialInstitutionFlag eq null||sessionScope.specialInstitutionFlag eq '' }">
							<c:if test="${form.pubType!=null&&form.pubType!='' }">
							<a href="javascript:void(0)" name="conditions" class="label" id="pubType_label">
								<c:if test="${form.pubType==1 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==2 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==3 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option3" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==4 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option4" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==5 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option5" sessionKey="lang" /></c:if>
							</a>
							</c:if>
							</c:if>
							<c:if test="${form.prefixWord!=null&&form.prefixWord!='' }">
							<a href="javascript:void(0)" name="conditions" class="label" id="prefixWord_label">${form.prefixWord=="0"?"0-9":form.prefixWord }</a>
							</c:if>
							
							<c:if test="${form.publisher!=null&&form.publisher!='' }">
								<c:choose>
									<c:when test="${fn:startsWith(form.publisher,'_')}">
										<a href="javascript:void(0)" name="conditions" class="label" id="publisher_label">${fn:replace(form.publisher,'_','') }</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" name="conditions" class="label" id="publisher_label">${form.publisher }</a>
									</c:otherwise>
								</c:choose>							
							</c:if>
							<c:if test="${form.pubDate!=null&&form.pubDate!='' }">
							<a href="javascript:void(0)" name="conditions" class="label" id="pubDate_label">${form.pubDate }</a>
							</c:if>
							 
							<%-- <c:if test="${form.searchValue!=null&&form.searchValue!='' }">
							<a href="javascript:void(0)" name="conditions" class="label" id="searchValue_label">${form.searchValue}</a>
							</c:if> --%>
							
							<c:if test="${form.taxonomy!=null&&form.taxonomy!='' }">
							<c:if test="${taxArr!=null && taxArr!=''}">
								<c:forEach items="${taxArr}" var="t" varStatus="index">
									<a href="javascript:void(0)" name="conditions" class="label" id="taxonomy_label${index.index}">${t}</a>
								</c:forEach>								
							</c:if>		
							</c:if>		
										
							<c:if test="${form.taxonomyEn!=null&&form.taxonomyEn!='' }">
							<c:if test="${taxArrEn!=null && taxArrEn!=''}">
								<c:forEach items="${taxArrEn}" var="t" varStatus="index">
									<a href="javascript:void(0)" name="conditions" class="label" id="taxonomy_label${index.index}">${t}</a>
								</c:forEach>								
							</c:if>
							</c:if>
										
							<c:if test="${form.language!=null&&form.language!='' }">
							<a href="javascript:void(0)" name="conditions" class="label" id="language_label">${fn:toUpperCase(form.language) }</a>
							</c:if>
						</div>
					</div>
						</c:if>
				<c:if test="${fn:length(list) >0}">	
				<c:if test="${param.subFlag ne '1' && param.subFlag ne 1 && param.subFlag ne 1}">
				<div class="sort" >
				</c:if>
				<c:if test="${param.subFlag eq '1' &&  param.subFlag eq 1 }">
				<div class="sort1" style="margin:0;padding:0;">
				</c:if>
					<c:if test="${param.subFlag ne '1' && param.subFlag ne 1}">
					<div class="fl mt10">
						<!-- <input type="checkbox" name="" value="" />全部 --> <ingenta-tag:LanguageTag key="Pages.User.UserTypeManage.order" sessionKey="lang"/>
						<select id="sort" style="width:95px;">
							<option <c:if test="${form.searchOrder=='' }">selected="selected"</c:if> value=""><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Order.Option1" sessionKey="lang" /></option>
							<option <c:if test="${form.searchOrder=='desc' }">selected="selected"</c:if> value="desc"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Order.Option2" sessionKey="lang" /></option>
							<option <c:if test="${form.searchOrder=='asc' }">selected="selected"</c:if> value="asc"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Order.Option3" sessionKey="lang" /></option>
						</select>
					</div>
					</c:if>
					<c:if test="${param.subFlag ne '1' && param.subFlag ne 1 && param.newFlag ne 'true'}">
					<jsp:include page="../pageTag/pageTag.jsp">
			        <jsp:param value="${form }" name="form"/>
	                </jsp:include>
	                </c:if>
	               </c:if>
	                <!-- 
					<div class="pagelink fr noMargin" style="margin-top:12px">
							<ingenta-tag-v3:SplitTag 
								first_ico="${ctx }/images/ico_left1.gif"
								last_ico="${ctx }/images/ico_right1.gif"
								prev_ico="${ctx }/images/ico_left.gif"
								next_ico="${ctx }/images/ico_right.gif" 
								method="get"
								pageCount="${form.pageCount}" 
								count="${form.count}"
								page="${form.curpage}" 
								url="${form.url}"
								i18n="${sessionScope.lang}" />
						</div> -->
					<!--以下 提交查询Form 开始-->
					<form:form action="${form.url}" method="post" modelAttribute="form" commandName="form" name="formList" id="formList">
						<form:hidden path="searchsType" id="type2"/>
						<form:hidden path="searchValue" id="searchValue1"/>
						<form:hidden path="pubType" id="pubType2"/>
						<form:hidden path="language" id="language2"/>
						<form:hidden path="publisher" id="publisher2"/>
						<form:hidden path="pubDate" id="pubDate2"/>
						<form:hidden path="taxonomy" id="taxonomy2"/>
						<form:hidden path="taxonomyEn" id="taxonomyEn2"/>
						<form:hidden path="searchOrder" id="order2"/>
						<form:hidden path="lcense" id="lcense2"/>
						<form:hidden path="code" id="code2"/>
						<form:hidden path="pCode" id="pcode2"/>
						<form:hidden path="publisherId" id="publisherId2"/>
						<form:hidden path="subParentId" id="subParentId2"/>
						<form:hidden path="parentTaxonomy" id="parenttaxonomy2"/>
						<form:hidden path="parentTaxonomyEn" id="parenttaxonomyEn2"/>
						<form:hidden path="keywordCondition" id="keywordCondition2"/>
				        <form:hidden path="notKeywords" id="notKeywords2"/>
				        <form:hidden path="author" id="author2"/>
				        <form:hidden path="title" id="title1"/>
				        <form:hidden path="isCn" id="isCn"/>
				        <form:hidden path="prefixWord" id="prefixWord"/>
				        <form:hidden path="curpage" id="curpage1"/>
				        <form:hidden path="pageCount" id="pageCount1"/>
				        <form:hidden path="local" id="local2" />
				        <form:hidden path="notLanguage" id="notLanguage2" />
				        <form:hidden path="sortFlag" id="sortFlag2" />
				        <form:hidden path="subFlag" id="subFlag2" />
				        <form:hidden path="newFlag" id="newFlag2" />
				        <form:hidden path="selectflag" id="selectflag" />
						<%-- <ingenta-tag-v2:SplitTag 
							pageCount="${form.pageCount}" 
		                	count="${form.count}" 
		                	page="${form.curpage}" 
		                	url="${form.url}" 
		                	i18n="${sessionScope.lang}"
		                	method="post"
		                	formName="formList"
		                	next_ico="${ctx }/images/right.gif"
		                	prev_ico="${ctx }/images/left.gif"/> --%>		                						
					</form:form>
					<!--以上 提交查询Form 结束-->
				</div>
				<h1 class="h1Tit borBot">
					<c:if test="${param.subFlag ne '1' && param.subFlag ne 1 }">
					<ingenta-tag:LanguageTag key="Page.Publications.Journal.Article.List" sessionKey="lang" />
					</c:if>
					<c:if test="${param.subFlag eq '1' &&  param.subFlag eq 1 && param.newFlag ne 'true'}">
					<p class="ico1"><ingenta-tag:LanguageTag key="Pages.User.Subscription.Public1" sessionKey="lang" /></p>
					</c:if>
					<c:if test="${param.subFlag eq '1' &&  param.subFlag eq 1 && param.newFlag eq 'true'}">
					<p class="ico1"><ingenta-tag:LanguageTag key="Global.Label.New_Resources" sessionKey="lang" /></p>
					</c:if>
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
					<c:set var="recommand" value="${(p.recommand>0 || sessionScope.mainUser.institution!=null ) &&(p.subscribedIp==null||p.subscribedIp<=0)&&(p.free!=2&&p.oa!=2)}"/>	
					<c:if test="${license==false&&oa==false&&free==false }">
						<div class="fl w40 mt2">
							<p class="p_left">
							<img width="14" height="14" src="${ctx }/images/ico/ico_close.png" />
							<c:if test="${p.type==1 }">
								<img src="${ctx }/images/ico/ico4.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Book" sessionKey="lang" />" />
							</c:if>
							<c:if test="${p.type==2 ||p.type==6||p.type==7}">
								<img src="${ctx }/images/ico/ico3.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Journal" sessionKey="lang" />" />
							</c:if>
							<%-- <c:if test="${p.type==3 }">
								<img src="${ctx }/images/ico/infor.png" width="14" height="14" />
							</c:if> --%>
							<c:if test="${p.type==4 }">
								<img src="${ctx }/images/ico/ico5.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Article" sessionKey="lang" />" />
							</c:if>
							<%-- <a name="title" href="${ctx}/pages/publications/form/article/${p.id}?sv=${form.searchValue}&fp=2" title="${p.title }">${p.title }</a> --%>
							</p>
							<%-- 
							<c:if test="${news==true||free==true||oa==true||collection==true }">
							<p class="p_right">
							--%>
								<%-- <c:if test="${news==true }"><img src="${ctx }/images/n.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.New" sessionKey="lang" />"/></c:if> --%>
								<%-- 
								<c:if test="${free==true }"><img src="${ctx }/images/ico/f.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Free" sessionKey="lang" />"/></c:if>
								<c:if test="${oa==true }"><img src="${ctx }/images/ico/o.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.OA" sessionKey="lang" />"/></c:if>
								--%>
								<%-- <c:if test="${collection==true }"><img src="${ctx }/images/c.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Collection" sessionKey="lang" />"/></c:if> --%>
							<%-- 
							</p>
							</c:if>
							--%>
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
							<c:if test="${p.type==2 ||p.type==6||p.type==7 }">
								<img src="${ctx }/images/ico/ico3.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Journal" sessionKey="lang" />" />
							</c:if>
							<%-- <c:if test="${p.type==3 }">
								<img src="${ctx }/images/ico/infor.png" width="15" height="15" />
							</c:if> --%>
							<c:if test="${p.type==4 }">
								<img src="${ctx }/images/ico/ico5.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Article" sessionKey="lang" />" />
							</c:if>
							<%-- <a name="title" href="${ctx}/pages/publications/form/article/${p.id}?sv=${form.searchValue}&fp=2" title="${p.title }">${p.title }</a> --%>
						</div>
					</c:if>	
							
					<div class="fl w700" style="margin-bottom: 15px;">	
						<p>
						<c:if test="${p.type==1}">
							<a href="${ctx}/pages/publications/form/article/${p.id}?fp=3&sv=${form.searchValue}" title="${fn:replace(p.title,"\"","\'")}">				
								<c:if test="${p.cover!=null&&p.cover!='' }">
									<img  src="${ctx}/pages/publications/form/cover?t=1&id=${p.id}" width="85" height="116"  class="fr ml20"/>
								</c:if>	
							</a>					
						</c:if>
						<c:if test="${p.type==2||p.type==6||p.type==7}">
						
							<a href="${ctx}/pages/publications/form/journaldetail/${p.id}" title="${fn:replace(p.title,"\"","\'") }">				
								<c:if test="${p.cover!=null&&p.cover!='' }">
									<img onload="AutoResizeImage(110,95,this);" src="${ctx}/pages/publications/form/cover?t=1&id=${p.id}" style="width:1px;height:1px;float:right; margin-top:5px; margin-right:2px;" />
								</c:if>        
							</a>				
						
						</c:if>
						</p>
						<p>
							<a class="a_title" name="title" href="${ctx}/pages/publications/form/article/${p.id}?fp=3&sv=${form.searchValue}" title="${fn:replace(p.title,"\"","\'")}">${fn:replace(fn:replace(fn:replace(p.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}</a>    
						</p>	
		
						<%-- <p>
						 ISSN/ISBN:${p.code}
						</p> --%>
						<!--  用于显示ISSN/ISBN的东西 -->
						<c:if test="${p.type==1}">
						<p>
					      ISBN:${p.code}
						</p>
					     </c:if>
					    <c:if test="${p.type==4||p.type==6||p.type==7 }">
						<p>
					      ISSN:${p.code}
						</p>
						</c:if>
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
						<c:if test="${p.type!=2 }">
						<p>
						    By
							<c:set var="authors" value="${fn:split(p.author,',')}" ></c:set>
			                <c:forEach items="${authors}" var="a" >
			               <%--  <a href='${ctx }/index/search?type=2&isAccurate=1&searchValue="${a}"'>${a}</a> &nbsp; --%>
			                <a href="javascript:void(0)" onclick="searchMe(this,'author')">${a}</a> &nbsp;
			                </c:forEach>
						    <!--  ${p.author } -->
							<c:if test="${p.type==4||p.type==6||p.type==7 }"> in 
							<a href="${ctx}/pages/publications/form/article/${p.publications.id}?fp=3&sv=${form.searchValue}">${p.publications.title}</a>
							</c:if>
						</p>
						</c:if>
						<c:if test="${p.type==2 }">
						<p>
						<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" /> ${p.startVolume }-<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" /> ${p.endVolume }
						</p>
						<p>
					
				<c:if test="${pcrlist1!=null||pcrlist3!=null||pcrlist5!=null }">
					相关期刊：
	          	  <td valign="middle" class="tdb">
	      			<c:forEach items="${pcrlist3 }" var="pc3" varStatus="index">
	      				<c:if test="${pc3.mark=='21' }"><p class="ml50"><%-- <img src="${ctx}/images/ico/ico21.png" class="vm mr5" />本刊自${pc3.occurTime}年起，由 --%><c:forEach items="${pcrlist2 }" var="pc2" varStatus="index">${pc2.separateCon.code }<a href="${ctx }/pages/publications/form/article/${pc2.separateCon.id}">${pc2.separateCon.title }</a></c:forEach><!-- 合刊而成 --></p></c:if>
	      			</c:forEach>
				</td>
				</c:if>
						
						</p>
						</c:if>
						<p>
						<%--  <a href='${ctx }/index/search?type=2&searchsType=4&searchValue="${p.publisher.name }"'>${p.publisher.name }</a>(${fn:substring(p.pubDate,0,4) }) --%>
						  <a href="javascript:void(0)" onclick="searchMe(this,'publisher')">${p.publisher.name}</a>(${fn:substring(p.pubDate,0,4) })
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
						<%-- 	<c:if test="${license==true||oa==true||free==true }">
							<!-- 在线阅读 -->
								<span class="mr20"><a href="javascript:void(0)" title="<ingenta-tag:LanguageTag key="Global.Lable.Prompt.Preview" sessionKey="lang" />" onclick="viewPopTips('${p.id}','0',<c:if test="${oa==false&&free==false}">1</c:if><c:if test="${oa==true||free==true}">2</c:if>)"><img src="${ctx }/images/ico/reading.png" class="vm" /><ingenta-tag:LanguageTag key="Global.Lable.Prompt.Preview" sessionKey="lang" /></a></span>
							</c:if> --%>
							<!-- 无购买权限 -->
							<c:if test="${sessionScope.mainUser!=null }">
								<c:if test="${!add && license==false && oa==false && free==false}">
									<span class="mr20">
										<img src="${ctx }/images/ico/ico14-grey.png" class="vm" /><ingenta-tag:LanguageTag key="Page.Publications.Lable.Buy" sessionKey="lang"/>
									</span>
								</c:if>
							</c:if>
							<c:if test="${add }">
							<!-- 购买 -->
								<span class="mr20"><a href="javascript:void(0)" onclick="addToCart('${p.id}',1);" id="add_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Buy' sessionKey='lang'/>"><img src="${ctx }/images/ico/ico14-blank.png" class="vm" /><ingenta-tag:LanguageTag key="Page.Publications.Lable.Buy" sessionKey="lang"/></a></span> 
							</c:if>
							<!-- 已购买 -->
<!-- 							<c:if test="${sessionScope.mainUser!=null &&!add}"> -->
<!-- 								<span class="mr20"	> -->
<!-- 									<a href="javascript:void(0)" style="cursor:auto" class="blank" ><img title="<ingenta-tag:LanguageTag key='Page.Publications.Lable.Buy' sessionKey='lang'/>" src="${ctx }/images/ico/ico14-grey.png" class="vm" /><ingenta-tag:LanguageTag key='Page.Publications.Lable.Buy' sessionKey='lang'/></a> -->
<!-- 								</span> -->
<!-- 							</c:if> -->
							<!-- 获取资源（没有购买） -->
							<c:if test="${license==false && oa==false && free==false}">
								<span class="mr20">
									<a href="javascript:;" id="resource_div" onclick="popTips2('${p.id}');"><img src="${ctx }/images/ico/ico15-blue.png" class="vm" />
										<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
									</a>
								</span>
							</c:if>
							<!-- 获取资源（免费、开源、已购买） -->
							<c:if test="${license==true||oa==true||free==true}">
								<c:if test="${p.type==1}">
									<span class="mr20">
										<a href="javascript:;" id="resource_div" onclick="viewPopTips('${p.id}','0',<c:if test="${oa==false&&free==false}">1</c:if><c:if test="${oa==true||free==true}">2</c:if>)"><img src="${ctx }/images/ico/ico15-green.png" class="vm" />
											<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
										</a>
									</span>
								</c:if>
								<c:if test="${p.type==2||p.type==6||p.type==7}">
									<span class="mr20">
										<a href="/pages/publications/form/article/${p.id}"  id="resource_div1"><img src="${ctx }/images/ico/ico15-green.png" class="vm" />
											<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
										</a>
									</span>
								</c:if>
								<c:if test="${p.type==4}">
									<span class="mr20">
										<a href="javascript:;" id="resource_div" onclick="popTips2('${p.id}');"><img src="${ctx }/images/ico/ico15-green.png" class="vm" />
											<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
										</a>
									</span>
								</c:if>
							</c:if>
							
							<!-- 推荐 -->
							<c:if test="${recommand}">
								<span class="mr20">
									<a href="javascript:;" id="recommand_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' />" onclick="recommends('${p.id}');">
										<img src="${ctx }/images/ico/ico16-blue.png" class="vm" /><ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' />
									</a>
								</span>
							</c:if>

							<!-- 收藏 <ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' /> -->
							<c:if test="${favourite}">
								<span class="mr20 favourite" id="${p.id}">
									<a href="javascript:;" class="">
										<img src="${ctx}/images/unfavourite.png" class="vm" /><span><ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' /></span>
									</a>
								</span>
							</c:if>
							
							<!-- 已收藏 -->
							<c:if test="${sessionScope.mainUser!=null && !favourite}">
								<span class="mr20 favourite" id="${p.id}">
									<a href="javascript:;" class="blank">
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
							<ingenta-tag:LanguageTag key="Page.AdvancedSearch.Alert" sessionKey="lang" />
						</b>
					</p>
				</c:if>
				</div>
				<!--列表内容结束-->
				
			<jsp:include page="../pageTag/pageTag.jsp">
			        <jsp:param value="${form }" name="form"/>
	                </jsp:include>				
			</div>
			<!--右侧列表内容结束-->
			</div>
		</div>
		<!--以上中间内容块结束-->
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
		
		<script type="text/javascript">
	
			$(function() {
				$.ajax({
				    type: 'post',
				    url: '${ctx}/index/advancedAllByAjax',
				    data: $("#formList").serialize(),
				    success : function(data) {
				    	$("#all").html(data);
				    }
				});
				$.ajax({
				    type: 'post',
				    url: '${ctx}/index/advancedOaFreeByAjax',
				    data: $("#formList").serialize(),
				    success : function(data) {
				    	$("#oaFree").html(data);
				    }
				});
				$.ajax({
				    type: 'post',
				    url: '${ctx}/index/advancedLcenseInsByAjax',
				    data: $("#formList").serialize(),
				    success : function(data) {
				    	$("#advancedLcenseIns").html(data);
				    }
				});
			});
		
		</script>
	</body>
</html>
