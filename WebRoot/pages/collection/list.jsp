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
$(document).ready(function(){

getList();
});

function getList(){
var pageCount = $("#pageTag").val();
		$.ajax({
				type : "POST",    
			    url: "${ctx}/pages/collection/form/manager",
			    data: {
			    pageCount:pageCount,
			    curpage:${form.curpage}
			    },
			    success : function(data) { 
		        $("#middle").html(data);
		        },  
		        error : function(data) {
		        }  
		      });
}

/* function dividePage(targetPage){
alert("this:"+targetPage);
if(targetPage<0){return;}
var pageCount=$("#pageTag").val();
				$.ajax({
					type : "POST",    
			        url: "${ctx}/pages/collection/form/listForPPV",
			        data: {
			       pageCount:pageCount,
			       curpage:targetPage
			        },
			        success : function(data) {
		             	$("#middle").html(data);
		            },  
		            error : function(data) {
		            alert("");
		            }  
		      });
} */
function GO(obj){
$("#pageTag").val($(obj).val());
getList();
}
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
        	order:'${form.searchOrder}'
        },
        success : function(data) { 
            	$(parObj).html(data);
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
        	order:'${form.searchOrder}',
        	parentTaxonomy:"${form.parentTaxonomy }",
        	parentTaxonomyEn:"${form.parentTaxonomyEn }"
        },
        success : function(data) { 
            	$(parObj).html(data);
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
        	order:'${form.searchOrder}'
        },
        success : function(data) { 
            	$(parObj).html(data);
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
        	order:'${form.searchOrder}'
        },
        success : function(data) { 
            	$(parObj).html(data);
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
	$("#curpage").val(0);
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
		$("#seepub").html("<span class='alph'>></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.Less' sessionKey='lang'/>...</span>");
	}else{
		$("li[name='pubmore']").css("display","none");
		$("#morePubStatus").val('1');
		$("#seepub").html("<span class='alph'>></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</span>");
	}
}
//分类法更多
function seeSubMore(){
	var status = $("#moreStatus").val();
	if(status=="1"){
		$("li[name='more']").css("display","block");
		$("#moreStatus").val('2');
		$("#see").html("<span class='alph'>></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.Less' sessionKey='lang'/>...</span>");
	}else{
		$("li[name='more']").css("display","none");
		$("#moreStatus").val('1');
		$("#see").html("<span class='alph'>></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</span>");
	}
}
//出版年更多
function seeYearMore(){
	var status = $("#moreYearStatus").val();
	if(status=="1"){
		$("li[name='yearmore']").css("display","block");
		$("#moreYearStatus").val('2');
		$("#seeyear").html("<span class='alph'>></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.Less' sessionKey='lang'/>...</span>");
	}else{
		$("li[name='yearmore']").css("display","none");
		$("#moreYearStatus").val('1');
		$("#seeyear").html("<span class='alph'>></span><span class='write'><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</span>");
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
		art.dialog.open(url,{title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.OLRead" sessionKey="lang" />",width: $(window).width()*0.8,height: $(window).height()*0.9,lock: true,close:function(){
					$.ajax({
						type : "POST",
						async : false,
						url : "${ctx}/pages/publications/form/release",
						data : {
							id : id,
							r_ : new Date().getTime()
						},
						success : function(data) {
						},
						error : function(data) {
						}
					});
				}});
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
				art.dialog.open(s[1],{title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.OLRead" sessionKey="lang" />",width: $(window).width()*0.8,height: $(window).height()*0.9,lock: true,close:function(){
					$.ajax({
						type : "POST",
						async : false,
						url : "${ctx}/pages/publications/form/release",
						data : {
							id : id,
							r_ : new Date().getTime()
						},
						success : function(data) {
						},
						error : function(data) {
						}
					});
				}});
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
//]]-->
</script>
</head>

<body>
<jsp:include page="/pages/header/headerData" flush="true" />
<!-- 中间内容部分开始 -->
<div class="main" id="middle">
<!-- <img src="${ctx}/images/loading.gif"/> -->
</div>	
<!-- 中间内容部分结束 -->	
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
		<input type="hidden" id="pageTag" value="10"/>
</body>
</html>
