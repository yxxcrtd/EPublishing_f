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
<script type="text/javascript" src="${ctx }/js/jquery.highlight.js"></script>
<script type="text/javascript">
//<![data[
$(document).ready(function() {
	//出版物类型
	getTypeStat();
	//分类信息
	getSubjectStat();
	//出版社信息
	getPublisherStat();
	//出版年份
	getYearStat();
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
		document.formList.action="${ctx}/pages/publications/form/list";
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
		document.formList.action="${ctx}/pages/publications/form/list";
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
		document.formList.action="${ctx}/pages/publications/form/list";
		document.formList.submit();
	});
	
	//排序
	$("#sort").change(function(){
		var lcense = $("#lcense1").val();
		var param = $(this).val();
		$("#order1").val(param);
		$("input[name='curpage']").val(0);
		document.formList.action="${ctx}/pages/publications/form/list";
		document.formList.submit();
	});
	//条件删除
	$("p[name='conditions']").click(function(){
		var lcense = $("#lcense1").val();
		var param = $(this).attr("id");
		document.formList.action="${ctx}/pages/publications/form/list";
		if(param=="pubType_label"){
			$("#pubType1").val('');
		}else if(param=="publisher_label"){
			$("#publisher1").val(null);
			$("#publisherId1").val(null);
		}else if(param=="pubDate_label"){
			$("#pubDate1").val('');
		}else if(param=="taxonomy_label"){
			if($("#parentTaxonomy1").val()!=''&&$("#parentTaxonomy1").val()!=$("#taxonomy1").val()){
				$("#taxonomy1").val($("#parentTaxonomy1").val());
				$("#pCode1").val($("#code1").val());
				$("#code1").val('');
				document.formList.action="${ctx }/pages/subject/form/list";
			}else{
				$("#taxonomy1").val(null);
				$("#pCode1").val(null);
				$("#code1").val(null);
				$("#subParentId1").val("0");
				$("#parentTaxonomy1").val(null);
			}
		}else if(param=="taxonomyEn_label"){
			if($("#parentTaxonomyEn1").val()!=''&&$("#parentTaxonomyEn1").val()!=$("#taxonomyEn1").val()){
				$("#taxonomyEn1").val($("#parentTaxonomyEn1").val());
				$("#pCode1").val($("#code1").val());
				$("#code1").val('');
				document.formList.action="${ctx }/pages/subject/form/list";
			}else{
				$("#taxonomyEn1").val(null);
				$("#pCode1").val(null);
				$("#code1").val(null);
				$("#subParentId1").val("0");
				$("#parentTaxonomyEn1").val(null);
			}
		}else if(param=="parentTaxonomy_label"){
			$("#parentTaxonomy1").val('1');
			document.formList.action="${ctx }/pages/subject/form/list";
		}else if(param=="parentTaxonomyEn_label"){
			$("#parentTaxonomyEn1").val('1');
			document.formList.action="${ctx }/pages/subject/form/list";
		}
		$(this).css("display","none");
		$("input[name='curpage']").val(0);
		
		document.formList.submit();
	});	
	//下载列表
	$("#downList").click(function(){
		var url = "${ctx}/pages/publications/form/downList";
        url += "?searchsType="+$("#type1").val();
        url += "&searchValue="+$("#searchValue1").val();
        url += "&pubType="+$("#pubType1").val();
        url += "&publisher="+$("#publisher1").val();
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
//出版物类型
function getTypeStat(){
	var parObj=$("#type_statistic");
	$.ajax({
		type : "GET",
		async : true,    
        url: "${ctx}/pages/publications/typeStat",
        data: {
        	isLicense:'${sessionScope.selectType}',
        	code:'${form.code}',
        	type:'${form.pubType}',
        	pCode:'${form.pCode}',
        	publisherId:'${form.publisherId}',
        	subParentId:'${form.subParentId}',
        	pubYear:'${form.pubDate}',
        	order:'${form.order}'
        },
        success : function(data) { 
            	$(parObj).html(data);
            	/* $(parObj).css("text-align","left"); */
           },  
           error : function(data) {
             	$(parObj).html(data);
           }  
     });
}
//分类法
function getSubjectStat(){
	var parObj=$("#subject_statistic");
	$.ajax({
		type : "GET",
		async : true,    
        url: "${ctx}/pages/publications/subjectStat",
        data: {
        	isLicense:'${sessionScope.selectType}',
        	code:'${code}',
        	type:'${form.pubType}',
        	pCode:'${pCode}',
        	publisherId:'${form.publisherId}',
        	subParentId:'${form.subParentId}',
        	pubYear:'${form.pubDate}',
        	order:'${form.order}',
        	parentTaxonomy:"${form.parentTaxonomy }",
        	parentTaxonomyEn:"${form.parentTaxonomyEn }"
        },
        success : function(data) { 
            	$(parObj).html(data);
            	/* $(parObj).css("text-align","left"); */
           },  
           error : function(data) {
             	$(parObj).html(data);
           }  
     });
}
//出版年份
function getYearStat(){
	var parObj=$("#year_statistic");
	$.ajax({
		type : "GET",
		async : true,     
        url: "${ctx}/pages/publications/yearStat",
        data: {
        	isLicense:'${sessionScope.selectType}',
        	code:'${form.code}',
        	type:'${form.pubType}',
        	pCode:'${form.pCode}',
        	publisherId:'${form.publisherId}',
        	subParentId:'${form.subParentId}',
        	pubYear:'${form.pubDate}',
        	order:'${form.order}'
        },
        success : function(data) { 
            	$(parObj).html(data);
            	/* $(parObj).css("text-align","left") */;
           },  
           error : function(data) {
             	$(parObj).html(data);
           }  
     });
}
//出版社
function getPublisherStat(){
	var parObj=$("#publisher_statistic");
	$.ajax({
		type : "GET",
		async : true,    
        url: "${ctx}/pages/publications/publisherStat",
        data: {
        	isLicense:'${sessionScope.selectType}',
        	code:'${form.code}',
        	type:'${form.pubType}',
        	pCode:'${form.pCode}',
        	publisherId:'${form.publisherId}',
        	subParentId:'${form.subParentId}',
        	pubYear:'${form.pubDate}',
        	order:'${form.order}'
        },
        success : function(data) { 
            	$(parObj).html(data);
            	/* $(parObj).css("text-align","left"); */
           },  
           error : function(data) {
             	$(parObj).html(data);
           }  
     });
}
//左侧条件查询
function searchByCondition(type,value,a1,a2,a3,a4){
	document.formList.action="${ctx}/pages/publications/form/list";
	if(type=="type"){
		$("input[name='pubType']").val(value);
	}else if(type=="publisher"){
		$("input[name='publisher']").val(value); 
		$("input[name='publisherId']").val(a1);
	}else if(type=="pubDate"){
		$("input[name='pubDate']").val(value);
	}else if(type=="taxonomy"){
		$("input[name='taxonomy']").val(value);
		$("input[name='pCode']").val(a1);
		$("input[name='code']").val(a2);
		$("input[name='subParentId']").val(a3);
		$("input[name='parentTaxonomy']").val(a4);
		document.formList.action="${ctx }/pages/subject/form/list";
	}else if(type=="taxonomyEn"){
		$("input[name='taxonomyEn']").val(value);
		$("input[name='pCode']").val(a1);
		$("input[name='code']").val(a2);
		$("input[name='subParentId']").val(a3);
		$("input[name='parentTaxonomyEn']").val(a4);
		document.formList.action="${ctx }/pages/subject/form/list";
	}
	$("input[name='curpage']").val(0);
	$("#pageCount").val(10);
	$("#order1").val('');
	document.formList.submit();
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
		$("#seeyear").html("<span class='alph'><img src='${ctx }/images/jiantou.png' /></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</span>");
	}
}
//在线阅读
function viewPopTips(id,page,yon) {
	var url=""; 
	if(page=='0'){
		url = "${ctx}/pages/view/form/view?id="+id;
	}else{
		url = "${ctx}/pages/view/form/view?id="+id+"&nextPage="+page;
	}
	//首先Ajax查询要阅读的路径
	if(yon=='2'){
		window.location.href=url;
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
				if(s[1].indexOf('/pages/view/form/view')>0){
					window.location.href=s[1];
				}else{
					window.location.href="${ctx}/pages/view/form/view?id="+id+"&webUrl="+s[1];
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
				art.dialog.tips(s[1],1);//location.reload();
				$("#favourites_"+pid).attr("src","${ctx}/images/ico/ico13-blank.png");
				$("#favourites_div_"+pid).attr("class","blank");
				$("#favourites_div_"+pid).removeAttr("href");
				$("#favourites_div_"+pid).removeAttr("onclick");
				/* $("#favourites_div_"+pid).replaceWith($(".collected")); */
				$("#favourites_div_"+pid).attr("title","<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
			//	alert($("#favourites_div_"+pid).text());
				
			//	$("#favourites_div_"+pid).html("<img id='favourites_${p.id }' src='${ctx }/images/ico/ico13-blank.png' class='vm' /><ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
				/* $("#favourites_div_"+pid).text("<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />"); */
				
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
			<div class="chineseLeft">
				<div class="leftClassity">
			<!-- 出版物类型 -->
				<h1 class="h1Tit borBot">
					<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Publications" sessionKey="lang" />
				</h1>
				<ul id="type_statistic">
					<img src="${ctx}/images/loading.gif"/>
		        </ul>
		        </div>
		        <!-- 出版社 -->
		        <div class="leftClassity">
				<h1 class="h1Tit borBot">
					<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Publisher" sessionKey="lang" />
				</h1>
				<ul id="publisher_statistic" class="updownUl">
		          <img src="${ctx}/images/loading.gif"/>
		        </ul>
		        </div>
				<!-- 分类 -->
				<div class="leftClassity">
				<h1 class="h1Tit borBot">
					<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Subject" sessionKey="lang" />
				</h1>
				<ul id="subject_statistic" class="updownUl">
		        	<img src="${ctx}/images/loading.gif"/>
		        </ul>
		        </div>
		        <!-- 出版时间 -->
				<div class="leftClassity">
				<h1 class="h1Tit borBot">
					<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.PubDate" sessionKey="lang" />
				</h1>
				<ul id="year_statistic" class="updownUl">
					<img src="${ctx}/images/loading.gif"/>
		        </ul>
		        </div>
			</div>
			<!--左侧导航栏结束 -->

			<!--右侧列表内容-->
			<div class="chineseRight">
				
					<!-- <ul class="oh">
			            <li class="Stab StabSeleted Ctab"><input type="radio" class="vm" checked="checked"/> 全部</li>
			            <li class="Stab Ctab"><input type="radio" class="vm" checked="checked"/> 已订阅</li>
			    	</ul> -->
						<c:if test="${sessionScope.mainUser!=null || sessionScope.institution!=null}">
							
							<!-- <div class="form_in"> -->
						<ul class="oh">
							<li class="Stab StabSeleted Ctab" name="stab1"><input type="hidden" name="isLicenses" id="selectAll" value="" style="*margin-top:6px;" <c:if test="${sessionScope.selectType==null||sessionScope.selectType==''||sessionScope.selectType!='1' }">checked="checked"</c:if>/>
							<ingenta-tag:LanguageTag key="Global.Page.Select.All" sessionKey="lang"/></li>
							<li class="Stab Ctab" name="stab2"><input type="hidden" name="isLicenses" id="selectLicense" value="1" style="*margin-top:6px;" <c:if test="${sessionScope.selectType!=null&&sessionScope.selectType!=''&&sessionScope.selectType=='1' }">checked="checked"</c:if>/>
							<ingenta-tag:LanguageTag key="Global.Button.license" sessionKey="lang"/></li>
						</ul>
							<!-- </div> -->
						</c:if>
						<c:if test="${sessionScope.mainUser==null && sessionScope.institution==null}">
							<div class="form_in">
							<input type="radio" name="isLicenses" id="selectAll" value="" style="*margin-top:6px;" <c:if test="${sessionScope.selectType==null||sessionScope.selectType==''||sessionScope.selectType!='1' }">checked="checked"</c:if>/>
							<ingenta-tag:LanguageTag key="Global.Page.Select.All" sessionKey="lang"/>
							</div>
						</c:if>
						
						
				<div  class="StabContent ScontentSelected CtabContent">
					 <div class="mt5 mb5 oh">
						<span class="fl"><ingenta-tag:LanguageTag key="Page.Frame.Count.Lable.Total" sessionKey="lang"/>：&nbsp;<c:if test="${form.count==null}">0</c:if><c:if test="${form.count!=null }"><fmt:formatNumber value="${form.count }" pattern="###,###" /></c:if></span>
						<c:if test="${sessionScope.mainUser!=null && (sessionScope.mainUser.level==2 || sessionScope.mainUser.level==5)}">
						<span class="fr borSpan h20">
							<a id="downList" class="ico_new" target="_blank" title="<ingenta-tag:LanguageTag key="Global.Button.DownLoad" sessionKey="lang"/>"><img src="${ctx }/images/ico/download.png"/></a>
						</span>
						</c:if>
					  </div>
					<%-- <div class="take">
						<p>${form.count } Result(s) for ''</p>
						<c:if test="${(form.pubType!=null&&form.pubType!='')||(form.publisher!=null&&form.publisher!='')||(form.pubDate!=null&&form.pubDate!='')||(form.parentTaxonomy!=null&&form.parentTaxonomy!=''&&sessionScope.lang=='zh_CN'&&form.taxonomy!=form.parentTaxonomy)||(form.taxonomy!=null&&form.taxonomy!='')||(form.parentTaxonomyEn!=null&&form.parentTaxonomyEn!=''&&sessionScope.lang=='en_US'&&form.taxonomyEn!=form.parentTaxonomyEn)||form.taxonomyEn!=null&&form.taxonomyEn!='' }">
						<p style="float:left;"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Lable.SearchRange" sessionKey="lang" /></p>
						<div class="label_list" style="float:left; width:680px;">
							<c:if test="${form.pubType!=null&&form.pubType!='' }">
							<p name="conditions" class="label" id="pubType_label">
								<c:if test="${form.pubType==1 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==2 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==3 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option3" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==4 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option4" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==5 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option5" sessionKey="lang" /></c:if>
							</p>
							</c:if>
							<c:if test="${form.publisher!=null&&form.publisher!='' }">
							<p name="conditions" class="label" id="publisher_label">${form.publisher }</p>
							</c:if>
							<c:if test="${form.pubDate!=null&&form.pubDate!='' }">
							<p name="conditions" class="label" id="pubDate_label">${form.pubDate }</p>
							</c:if>
							<c:if test="${form.parentTaxonomy!=null&&form.parentTaxonomy!=''&&sessionScope.lang=='zh_CN'&&form.taxonomy!=form.parentTaxonomy }">
								<p name="conditions" class="label" id="parentTaxonomy_label">${form.parentTaxonomy }</p>
							</c:if>
							<c:if test="${form.taxonomy!=null&&form.taxonomy!='' }">
							<p name="conditions" class="label" id="taxonomy_label">${form.taxonomy }</p>
							</c:if>
							<c:if test="${form.parentTaxonomyEn!=null&&form.parentTaxonomyEn!=''&&sessionScope.lang=='en_US'&&form.taxonomyEn!=form.parentTaxonomyEn }">
							<p name="conditions" class="label" id="parentTaxonomyEn_label">${form.parentTaxonomyEn }</p>
							</c:if>
							<c:if test="${form.taxonomyEn!=null&&form.taxonomyEn!='' }">
							<p name="conditions" class="label" id="taxonomyEn_label">${form.taxonomyEn }</p>
							</c:if>
						</div>
						</c:if>
					</div> --%>
			
				<div class="sort" >
						<div class="fl mt10">
							<!-- <input type="checkbox" name="" value="" />全部 --><ingenta-tag:LanguageTag key="Pages.User.UserTypeManage.order" sessionKey="lang"/>
							<select id="sort">
								<option <c:if test="${form.searchOrder=='desc' }">selected="selected"</c:if> value="desc"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Order.Option2" sessionKey="lang" /></option>
								<option <c:if test="${form.searchOrder=='asc' }">selected="selected"</c:if> value="asc"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Order.Option3" sessionKey="lang" /></option>
							</select>
						</div>
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
						</div>		
						
						<!--以下 提交查询Form 开始-->
						<form:form action="${form.url}" method="post" modelAttribute="form" commandName="form" name="formList" id="formList">
							
						<%-- 	<ingenta-tag-v2:SplitTag 
								pageCount="${form.pageCount}" 
			                	count="${form.count}" 
			                	page="${form.curpage}" 
			                	url="${form.url}" 
			                	i18n="${sessionScope.lang}"
			                	method="post"
			                	formName="formList"
			                	next_ico="${ctx }/images/right_light.gif"
			                	prev_ico="${ctx }/images/left_light.gif"
							/> --%>
							
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
				</div>
				<h1 class="h1Tit borBot">
					<ingenta-tag:LanguageTag key="Page.Publications.Journal.Article.List" sessionKey="lang"/>
				</h1>
				<!--列表内容开始-->
				<div class="block">
				<c:forEach items="${pubList }" var="p" varStatus="index">
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
					<c:set var="recommand" value="${(p.recommand>0||sessionScope.mainUser.institution!=null) &&(p.subscribedIp==null||p.subscribedIp<=0)&&(p.free!=2&&p.oa!=2)}"/>	
					<c:if test="${license==false&&oa==false&&free==false }">
						<div class="fl w40 mt2">
						<!-- <p class="p_left"> -->
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
						
						<!-- </p> -->
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
						<p class="p_left">
						<img src="${ctx }/images/ico/ico_open.png" />
						<c:if test="${p.type==1 }">
							<img src="${ctx }/images/ico/ico4.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Book" sessionKey="lang" />"/>
						</c:if>
						<c:if test="${p.type==2||p.type==6||p.type==7 }">
							<img src="${ctx }/images/ico/ico3.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Journal" sessionKey="lang" />" />
						</c:if>
						<c:if test="${p.type==4 }">
							<img src="${ctx }/images/ico/ico5.png" width="14" height="14" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Article" sessionKey="lang" />" />
						</c:if>
						<%-- <a name="title" href="${ctx}/pages/publications/form/article/${p.id}?fp=3" title="${p.title }">${p.title }</a> --%>
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
					
				<div class="fl w700">	
						<p>
						<c:if test="${p.type==1}">
							<a href="${ctx}/pages/publications/form/article/${p.id}?fp=3" title="${p.title }">				
								<c:if test="${p.cover==null||p.cover=='' }">
									<img onload="AutoResizeImage(110,116,this);" src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/images/noimg.jpg" width="85" height="116" onerror="this.src='${ctx}/images/smallimg.jpg'" class="fr ml20"/>
								</c:if>
								<c:if test="${p.cover!=null&&p.cover!='' }">
									<img onload="AutoResizeImage(110,116,this);" src="${ctx}/pages/publications/form/cover?id=${p.id}" width="85" height="116" onerror="this.src='${ctx}/images/smallimg.jpg'" class="fr ml20"/>
								</c:if>	
							</a>					
						</c:if>
						<%-- <c:if test="${p.type==2||p.type==6||p.type==7}">
						<td class="tdb" width="15%" style="height:auto;">	
							<a href="${ctx}/pages/publications/form/journaldetail/${p.id}" title="${p.title }">				
								<c:if test="${p.cover==null||p.cover=='' }">
									<img onload="AutoResizeImage(110,95,this);" src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/images/noimg.jpg" style="width:1px;height:1px;float:right; margin-top:5px; margin-right:2px;"/>
								</c:if>
								<c:if test="${p.cover!=null&&p.cover!='' }">
									<img onload="AutoResizeImage(110,95,this);" src="${ctx}/pages/publications/form/cover?id=${p.id}" style="width:1px;height:1px;float:right; margin-top:5px; margin-right:2px;"/>
								</c:if>		
							</a>				
						</td>
						</c:if> --%>
						</p>
						<p><a class="a_title" name="title" href="${ctx}/pages/publications/form/article/${p.id}?fp=3" title="${p.title }">${p.title }</a></p>	
					
						<p>${p.publisher.name }</p>
					
						<c:if test="${p.type!=2 }">
						<p>
						    By ${p.author }
							<c:if test="${p.type==4||p.type==6||p.type==7 }"> in 
							<a href="${ctx}/pages/publications/form/article/${p.publications.id}?fp=3">${p.publications.title}</a>
							</c:if>(${fn:substring(p.pubDate,0,4) })
						</p>
						</c:if>
						<c:if test="${p.type==2 }">
						<p>
						<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" /> ${p.startVolume }-<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" /> ${p.endVolume }
						</p>
						</c:if>
						
						
					 
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
							
							<c:if test="${add }">
							<!-- 购买 -->
								<span class="mr20"><a href="javascript:void(0)" onclick="addToCart('${p.id}',1);" id="add_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Buy' sessionKey='lang'/>"><img src="${ctx }/images/ico/ico14-blank.png" class="vm" />添加到购物车</a></span> 
							</c:if>
							
							<!-- 获取资源 -->
						<%-- 	<span class="mr20"><a href="javascript:void(0)"><img src="${ctx }/images/ico/ico15-blue.png" class="vm" /> 获取资源</a></span> --%>
							
							
						<c:if test="${license==false&&oa==false&&free==false }">
							<span class="mr20">
							<a href="javascript:void(0)" id="resource_div" onclick="popTips2('${form.obj.id}');"><img src="${ctx }/images/ico/ico15-blue.png" class="vm" />
							<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
							</a>
							</span>
						</c:if>
						<c:if test="${license==true||oa==true||free==true }">
						<span class="mr20">
							<a href="javascript:void(0)" id="resource_div" onclick="popTips2('${form.obj.id}');"><img src="${ctx }/images/ico/ico15-green.png" class="vm" />
							<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
							</a>
							</span>
							</c:if>
							
							
							<c:if test="${recommand}">
							<!-- 推荐 -->
								<span class="mr20"><a href="javascript:void(0)" id="recommand_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' />" onclick="recommends('${p.id}');"><img src="${ctx }/images/ico/ico16-blue.png" class="vm" /><ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' /></a></span>
							</c:if>	
							
							<c:if test="${favourite }">
							<!-- 收藏 -->
								<span><a href="javascript:void(0)" class="#" id="favourites_div_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' />" onclick="addFavourites('${p.id }');"><img id="favourites_${p.id }" src="${ctx }/images/ico/ico13-blue.png" class="vm" /><ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' /></a></span> 
							</c:if>
							
							<c:if test="${sessionScope.mainUser!=null && !favourite }">
							<!-- 已收藏 -->
								<span><a style="cursor:auto" class="blank" ><img title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />" src="${ctx }/images/ico/ico13-blank.png" class="vm" /><ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' /></a></span>
							</c:if>
						</p>	
							
						<%-- 	
							<c:if test="${license==true||oa==true||free==true }">
							<!-- 在线阅读 -->
								<a class="ico_link" title="<ingenta-tag:LanguageTag key="Global.Lable.Prompt.Preview" sessionKey="lang" />" onclick="viewPopTips('${p.id}','0',<c:if test="${oa==false&&free==false}">1</c:if><c:if test="${oa==true||free==true}">2</c:if>)"><img src="${ctx }/images/eye.png" /></a>
							</c:if>
							<c:if test="${add }">
							<!-- 购买 -->
								<a class="ico_link" onclick="addToCart('${p.id}',1);" id="add_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Buy' sessionKey='lang'/>"><img src="${ctx }/images/cart.png" /></a> 
							</c:if>
							<c:if test="${favourite }">
							<!-- 收藏 -->
								<a class="ico_link" id="favourites_div_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' />" onclick="addFavourites('${p.id }');"><img id="favourites_${p.id }" src="${ctx }/images/collect.png" /></a> 
							</c:if>
							<c:if test="${sessionScope.mainUser!=null && !favourite }">
							<!-- 已收藏 -->
								<a class="ico_link" style="cursor:auto"><img title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />" src="${ctx }/images/collect_light.png" /></a>
							</c:if>
							<c:if test="${recommand}">
							<!-- 推荐 -->
								<a class="ico_link" id="recommand_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' />" onclick="recommends('${p.id}');"><img src="${ctx }/images/recommend.png" /></a>
							</c:if>							
					 --%>
				
				</div>				
				<p style="height:5px; width:1px; clear:both;">&nbsp;</p>
				</c:forEach>
				</div>
				<div class="sort" >
				<%-- <ingenta-tag-v2:SplitTag 
					pageCount="${form.pageCount}" 
                	count="${form.count}" 
                	page="${form.curpage}" 
                	url="${form.url}" 
                	i18n="${sessionScope.lang}"
                	method="post"
                	formName="formList"
                	next_ico="${ctx }/images/right_light.gif"
                	prev_ico="${ctx }/images/left_light.gif"
                	isMain="false"
                	identity="01"
				/> --%>
				
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
				</div>
				<!--列表内容结束-->
			</div>
			<!--右侧列表内容结束-->
		</div>
		<!--以上中间内容块结束-->
		</div>
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	
</body>
</html>
