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
		document.formList.action="${ctx}/index/advancedSearchSubmit";
		document.formList.submit();
	}); */
	
	$("li[name='stab1']").click(function(){
		var lcense = $("#lcense1").val();
		$("input[name='isLicenses']").attr("id","selectAll");
		var param = $("input[name='isLicenses']").attr("id");
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
		document.formList.action="${ctx}/index/advancedSearchSubmit";
		document.formList.submit();
	});
	
	$("li[name='stab2']").click(function(){
		var lcense = $("#lcense1").val();
		$("input[name='isLicenses']").attr("id","selectLicense");
		var param = $("input[name='isLicenses']").attr("id");
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
	$("p[name='conditions']").click(function(){
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
		document.formList.action="${ctx}/index/advancedSearchSubmit";
		document.formList.submit();
	});	
	//下载列表
	$("#downList").click(function(){
		var url = "${ctx}/index/advancedSearchDownList";
        url += "?searchsType="+$("#type1").val();
        url += "&searchValue="+$("#searchValue1").val();
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
        url += "&keywordCondition="+$("#keywordCondition1").val();
        url += "&notKeywords="+$("#notKeywords1").val();
        url += "&author="+$("#author1").val();
        url += "&title="+$("#title1").val();
        window.location.href=url;
	});
});
//左侧条件查询
function searchByCondition(type,value){
	var verify=null;
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
	$("#pageCount").val(10);
	$("#order1").val('');
	if(verify!=1){
	document.formList.action="${ctx}/index/advancedSearchSubmit";
	document.formList.submit();
	}
}
//语种更多
function seeLangMore(){
	var status = $("#moreLangStatus").val();
	if(status=="1"){
		$("li[name='langmore']").css("display","block");
		$("#moreLangStatus").val('2');
		$("#seelang").html("<span class='alph'><img src='${ctx }/images/ico/ico10.png' /></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.Less' sessionKey='lang'/>...</span>");
	}else{
		$("li[name='langmore']").css("display","none");
		$("#moreLangStatus").val('1');
		$("#seelang").html("<span class='alph'><img src='${ctx }/images/ico/ico10.png' /></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</span>");
	}
}
//出版社更多
function seePubMore(){
	var status = $("#morePubStatus").val();
	if(status=="1"){
		$("li[name='pubmore']").css("display","block");
		$("#morePubStatus").val('2');
		$("#seepub").html("<span class='alph'><img src='${ctx }/images/ico/ico10.png' /></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.Less' sessionKey='lang'/>...</span>");
	}else{
		$("li[name='pubmore']").css("display","none");
		$("#morePubStatus").val('1');
		$("#seepub").html("<span class='alph'><img src='${ctx }/images/ico/ico10.png' /></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</span>");
	}
}
//分类法更多
function seeSubMore(){
	var status = $("#moreStatus").val();
	if(status=="1"){
		$("li[name='more']").css("display","block");
		$("#moreStatus").val('2');
		$("#see").html("<span class='alph'><img src='${ctx }/images/ico/ico10.png' /></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.Less' sessionKey='lang'/>...</span>");
	}else{
		$("li[name='more']").css("display","none");
		$("#moreStatus").val('1');
		$("#see").html("<span class='alph'><img src='${ctx }/images/ico/ico10.png' /></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</span>");
	}
}
//出版年更多
function seeYearMore(){
	var status = $("#moreYearStatus").val();
	if(status=="1"){
		$("li[name='yearmore']").css("display","block");
		$("#moreYearStatus").val('2');
		$("#seeyear").html("<span class='alph'><img src='${ctx }/images/ico/ico10.png' /></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.Less' sessionKey='lang'/>...</span>");
	}else{
		$("li[name='yearmore']").css("display","none");
		$("#moreYearStatus").val('1');
		$("#seeyear").html("<span class='alph'><img src='${ctx }/images/ico/ico10.png' /></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</span>");
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
	///首先Ajax查询要阅读的路径
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
	art.dialog.open("${ctx}/pages/recommend/form/edit?pubid="+pid,{title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/>",top: 100,width: 550, height: 450,lock:true});
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
				art.dialog.tips(s[1],1);//location.reload();
				$("#favourites_"+pid).attr("src","${ctx}/images/ico/ico13-blank.png");
				$("#favourites_div_"+pid).removeAttr("href");
				$("#favourites_div_"+pid).removeAttr("onclick");
				$("#favourites_div_"+pid).attr("class","blank");
				$("#favourites_div_"+pid).attr("title","<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
		//		$("#favourites_div_"+pid).html("<img id='favourites_${p.id }' src='${ctx }/images/ico/ico13-blank.png' class='vm' /><ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
				$("#favourites_div_"+pid).css("cursor","auto");
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
	var lcense = $("#lcense1").val();
		$("#order1").val(v);
		$("input[name='curpage']").val(0);
		document.formList.action="${ctx}/index/advancedSearchSubmit";
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
								<c:if test="${fac.name=='type' && (count.name==1 || count.name==2 || count.name==3 || count.name==4 || count.name==5)}">
									<li class="oh">
									<a href="javascript:void(0)" onclick="searchByCondition('${fac.name}','${count.name }')">
										<c:if test="${count.name==1 }">
										<span class="fl"><img src="${ctx }/images/ico/ico4.png" /><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" /></span></c:if>
										<c:if test="${count.name==2 }">
										<span class="fl"><img src="${ctx }/images/ico/ico3.png" /><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></span></c:if>
										<c:if test="${count.name==3 }">
										<span class="fl"><img src="${ctx }/images/ico/infor.png" /><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option3" sessionKey="lang" /></span></c:if>
										<c:if test="${count.name==4 }">
										<span class="fl"><img src="${ctx }/images/ico/ico5.png" /><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option4" sessionKey="lang" /></span></c:if>
										<c:if test="${count.name==5 }">
										<span class="fl"><img src="${ctx }/images/ico/ico2.png" /><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option5" sessionKey="lang" /></span></c:if>
										<span class="fr">[${count.count }]</span>
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
				<ul>
				<c:set var="s" value="1"/>
				<c:forEach items="${languageList }" var="count" varStatus="index">
				<c:if test="${ count.count >0}">
				<c:if test="${s<=5 }">
					<li class="oh">
						<a href="javascript:void(0)" onclick="searchByCondition('language','${count.name}')" title="${fn:toUpperCase(count.name)}">
							<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
							<span class="write">${fn:toUpperCase(count.name) }</span><span class="fr">[${count.count }]</span>
						</a>
					</li>
				</c:if>
				<c:if test="${s>5 }">
					<li name="langmore" style="display: none;" class="oh">
					<a href="javascript:void(0)" onclick="searchByCondition('language','${count.name }')" title="${fn:toUpperCase(count.name)}">
						<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
						<span class="write">${fn:toUpperCase(count.name)}</span><span class="fr">[${count.count }]</span>
					</a>
					</li>
				</c:if>
				<c:if test="${s>5&&(index.index+1)==fn:length(languageList) }">
				<li>
					<a id="seelang" style="cursor:pointer" onclick="seeLangMore();" class="oh">
						<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
						<span class="write"><ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" />...</span>
					</a>
					<input type="hidden" id="moreLangStatus" value="1"/>
				</li>
				</c:if>
				<c:set var="s">${s+1 }</c:set>
				</c:if>
				</c:forEach>
				</ul>
				</div>
				
				<div class="leftClassity">
				<h1 class="h1Tit borBot">
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
				</ul>
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
						<c:if test="${i<=5}">
							<li class="oh">
							<a href="javascript:void(0)" onclick="searchByCondition('taxonomy','${count.name }')" title="${count.name }">
								<span class="alph">${fn:substring(count.name,0,fn:indexOf(count.name," ")+1) }</span>
								<span class="write">${fn:substring(count.name,fn:indexOf(count.name," "),fn:length(count.name)) }</span>
								<span class="fr">[${count.count }]</span>
							</a>
							</li>
						</c:if>
						<c:if test="${i>5}">
							<li name="more" style="display: none;" class="oh">
			          		<a href="javascript:void(0)" onclick="searchByCondition('taxonomy','${count.name }')"title="${count.name }">
								<span class="alph">${fn:substring(count.name,0,fn:indexOf(count.name," ")+1) }</span>
								<span class="write">${fn:substring(count.name,fn:indexOf(count.name," "),fn:length(count.name)) }</span>
								<span class="fr">[${count.count }]</span>
							</a>
							</li>
						</c:if>
						<c:set var="i" >${i+1 }</c:set>
					</c:if>
					<c:if test="${i>5&&(index.index+1)==fn:length(taxonomyList) }">
						<li class="oh"><a id="see" style="cursor:pointer" onclick="seeSubMore();">
						<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
						<span class="write">
							<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" />...
						</span>
						</a>
						<input type="hidden" id="moreStatus" value="1"/>
						</li>
					</c:if>
				</c:forEach>
				</ul>
				</c:if>
				<c:if test="${sessionScope.lang=='en_US' }">
				<h1 class="h1Tit borBot">
					<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Subject" sessionKey="lang" />
				</h1>
				<ul>
				<c:set var="i" value="1"/>
				<c:forEach items="${taxonomyEnList }" var="count" varStatus="index">
					<c:if test="${count.count>0 }">
						<c:if test="${i<=5 }">
							<li class="oh">
							<a href="javascript:void(0)" onclick="searchByCondition('taxonomyEn','${count.name }')" title="${count.name }">
								<span class="alph">${fn:substring(count.name,0,fn:indexOf(count.name," ")+1) }</span>
								<span class="write">${fn:substring(count.name,fn:indexOf(count.name," "),fn:length(count.name)) }</span>
								<span class="fr">[${count.count }]</span>
							</a>
							</li>
						</c:if>
						<c:if test="${i>5 }">
							<li name="more" style="display: none;" class="oh">
			          		<a href="javascript:void(0)" onclick="searchByCondition('taxonomyEn','${count.name }')"title="${count.name }">
								<span class="alph">${fn:substring(count.name,0,fn:indexOf(count.name," ")+1) }</span>
								<span class="write">${fn:substring(count.name,fn:indexOf(count.name," "),fn:length(count.name)) }</span>
								<span class="fr">[${count.count }]</span>
							</a>
							</li>
						</c:if>
						<c:set var="i" >${i+1 }</c:set>
					</c:if>
					<c:if test="${i>5&&(index.index+1)==fn:length(taxonomyEnList) }">
						<li class="oh"><a id="see" style="cursor:pointer" onclick="seeSubMore();">
						<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
						<span class="write">
							<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" />...
						</span>
						</a>
						<input type="hidden" id="moreStatus" value="1"/>
						</li>
					</c:if>
				</c:forEach>
				</ul>
				</c:if>
				</div>
				
				<div class="leftClassity">
				<h1>
					<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.PubDate" sessionKey="lang" />
				</h1>
				<ul>
					<c:set var="k" value="1"/>
          			<c:forEach items="${pubDateMap }" var="p" varStatus="index">
          				<c:if test="${p.value>0 }">
          					<c:if test="${k<=5 }">
          						<li  class="oh">
					          		<a href="javascript:void(0)" onclick="searchByCondition('pubDate','${p.key }')">
					          			<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
					          			<span class="write">${p.key }</span><span class="fr">[${p.value }]</span>
									</a>
								</li>
          					</c:if>
          					<c:if test="${k>5 }">
          						<li name="yearmore" style="display: none;"  class="oh">
									<a href="javascript:void(0)" onclick="searchByCondition('pubDate','${p.key }')">
										<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
										<span class="write">${p.key }</span><span class="fr">[${p.value }]</span>
									</a>
								</li>
          					</c:if>
          					<c:set var="k">${k+1 }</c:set>
          					<c:if test="${k>5&&(index.index+1)==fn:length(pubDateMap) }">
          						<li class="oh">
          						<a id="seeyear" style="cursor:pointer" onclick="seeYearMore();" class="more">
	          						<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
	          						<span class="write"><ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" />...</span>
          						</a>
								<input type="hidden" id="moreYearStatus" value="1"/>
								</li>
          					</c:if>
          				</c:if>
          			</c:forEach>
				</ul>
			</div>
			</div>
			<!--左侧导航栏结束 -->
			<!--右侧列表内容-->
			<div class="chineseRight">
				
			<h1 class="indexHtit">
				<span class="fl titFb"><a class="ico1" href="javascript:void(0)"><ingenta-tag:LanguageTag
							key="Global.Label.New_Resources" sessionKey="lang" /></a></span>
			</h1>
			<c:if test="${list!=null&&fn:length(list)>0 }">
				<c:forEach items="${list}" var="p" varStatus="index">
					<c:set var="license">${(p.subscribedIp!=null||p.subscribedUser!=null)&&(p.subscribedIp>0||p.subscribedUser>0) }</c:set>
					<c:set var="oa">${p.oa!=null&&p.oa==2 }</c:set>
					<c:set var="free">${p.free!=null&&p.free==2 }</c:set>
					<div class="block">
						<div class="fl w40 mt2">
							<c:if test="${license==false&&oa==false&&free==false }">
								<img src="${ctx}/images/ico/ico_close.png" width="16"
									height="16" />
							</c:if>
							<c:if test="${license==true||oa==true||free==true }">
								<img src="${ctx}/images/ico/ico_open.png" width="16" height="16" />
							</c:if>

							<c:if test="${p.type==1}">
								<img width="13" height="13" src="${ctx}/images/ico/ico4.png" />
							</c:if>
							<c:if test="${p.type==4 || p.type==2}">
								<img width="13" height="13" src="${ctx}/images/ico/ico5.png" />
							</c:if>

						</div>
						<div class="fl w640">
							<p>
								<a title="${p.title}"
									href="${ctx}/pages/publications/form/article/${p.id}">
									${fn:replace(fn:replace(fn:replace(p.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}
								</a>
							</p>
							<c:if test="${not empty p.author }">
					<p>
						By
						<c:set var="authors" value="${fn:split(p.author,',')}"></c:set>
						<c:forEach items="${authors}" var="a">
							<a
								href='${ctx }/index/search?type=2&isAccurate=1&searchValue2=${a}'>${a}</a>&nbsp;
			                </c:forEach>
					</p>
				</c:if>
				<c:if test="${not empty p.publisher.name}">
							<p><a href="javascript:void(0)" onclick="searchByCondition('publisher','${p.publisher.name }','${p.publisher.id}');">${p.publisher.name}</a><c:if
									test="${not empty fn:substring(p.pubDate,0,4)}">(${fn:substring(p.pubDate,0,4) })</c:if>
							</p>
						</c:if>
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
				<ingenta-tag:LanguageTag key="Global.Label.Prompt.No.Product"
					sessionKey="lang" />
			</c:if>
			<!--右侧列表内容结束-->
			<jsp:include page="../../pageTag/pageTag.jsp">
				<jsp:param value="${form }" name="form"/>
			</jsp:include>
			</div>
		</div>
		<!--以上中间内容块结束-->
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
</body>
</html>
