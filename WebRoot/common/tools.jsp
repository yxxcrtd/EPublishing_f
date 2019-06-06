<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">var ctxs = "<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>";</script>
<link type="text/css" rel="stylesheet" href="${ctx}/css/base.css?${d}" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/common.css?${d}" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/scroll.css?${d}" />
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.js?${d}"></script>
<script type="text/javascript" src="${ctx}/js/jquery.jslides.js?${d}"></script>
<script type="text/javascript" src="${ctx}/js/downMenu.js?${d}"></script>
<script type="text/javascript" src="${ctx}/js/calculate.js?${d}"></script>
<script type="text/javascript" src="${ctx}/js/cbscalendar.js?${d}"></script>
<script type="text/javascript" src="${ctx}/js/jquery.formatCurrency-1.4.0.js?${d}"></script>
<script type="text/javascript" src="${ctx}/js/common.js?${d}"></script>
<script type="text/javascript" src="${ctx}/js/lrscroll.js?${d}"></script>
<script type="text/javascript" src="${ctx}/js/jquery.superslide.2.1.1.js?${d}"></script>
<script type="text/javascript" src="${ctx}/js/artDialog.source.js?${d}&skin=default"></script>
<script type="text/javascript" src="${ctx}/js/plugins/iframeTools.source.js?${d}"></script>
<script type="text/javascript">
<!--
$(function() {
    $.ajaxSetup({ cache: false });
    $("#ad_index_top").load("/upload/ad/ad_index_top.html");
	$("#free_zh_CN").load("/upload/free_zh_CN.html");
	$("#free_en_US").load("/upload/free_en_US.html");
    $("#hotReading_zh_CN").load("/upload/hotReading_zh_CN.html");
    $("#hotReading_en_US").load("/upload/hotReading_en_US.html");
	$("#news").load("/upload/news/news.html");
	$("#press_chinese").load("/upload/press/press_chinese.html");
	$("#press_english").load("/upload/press/press_english.html");
	$("#press_journal").load("/upload/press/press_journal.html");
	$("#ad_index_bottom").load("/upload/ad/ad_index_bottom.html");
	
	// 以下是 Redis 的实现
	$("#category").load("/category");
	$("#subject").load("/subject");
	$("#footer_zh_CN, #footer_en_US").load("/footer");
	$("#hotWords").load("/hotWords");
	$("#stat_zh_CN").load("/stat_zh_CN");
	$("#stat_en_US").load("/stat_en_US");
	$("#menu").load("/menu");
	$("#service").load("/service");
});
//-->
</script>
		
<script type="text/javascript">
$(function() {
	reg=/<\/?[a-zA-Z\-][a-zA-Z0-9\-]*[^<>]*>/g;
	$("*[title]").each(function(){
		$(this).attr('title',$(this).attr('title').replace(reg,''));
	});
		
	// 收藏和取消收藏
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

	function login(){
		$.ajax({
  			type : "POST",  
			url: "${ctx}/pages/user/form/login",
			data: {
				uid:$("#loginuid").val(),
				pwd:$("#loginpwd").val(),
				rmb:$("#remember").attr("checked"),
				r_ : new Date().getTime()
			},
			success : function(data) {  
			    var s = data.split(":");			     
			    if(s[0]=="success"){
			    	/* <c:if test="${ctx!=''}">
			    		window.location.href="${ctx}";
			    	</c:if>
			    	<c:if test="${ctx==''}">
			    		window.location.href="${domain}";
			    	</c:if> */
			    	art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='user.login.prompt.success'/>",1,'success');
			    	cnpReload();
			    }else{
			    	art.dialog.tips(s[1],1,'error');
			    }			    
			},  
			error : function(data) {  
			    art.dialog.tips(data,1,'error');
			}  
		});
	}
	function loginout(){
		$.ajax({
  			type : "POST",  
			url: "${ctx}/pages/user/form/loginout",
			data: {r_ : new Date().getTime()},
			success : function(data) { 
			 	var s = data.split(":");
			 	if(s[0]=="error"){
			    	art.dialog.tips(s[1],1,'error');
			    }else{
			    	
			    	art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Controller.User.logoutRedirect.prompt.success'/>",1,'success');
			    	<c:if test="${ctx!=''}">
			    		location="${ctx}";
			    	</c:if>
			    	<c:if test="${ctx==''}">
			    		location="${domain}";
			    	</c:if>
			    }	
			},  
			error : function(data) {  
			    art.dialog.tips(data,1,'error');
			}  
		});
	}
	var ctx = '${ctx}';
	
	artDialog.tips = function (content,events,status,time) {
		var s  = '<div class="inline" style="padding: 0 1em;font-size: 12px;">';
		s += content ;
		s += '</div>';
		var icon="succeed";
		if(status=='error'){
			icon="warning";
		}
		var t =artDialog({
			id: 'Tips',
			title: "<ingenta-tag:LanguageTag sessionKey='lang' key='Global.Lable.Prompt'/>",
			top:100,
			icon: icon,
			cancel: false,
			fixed: true,
			lock: true
		})
		.content(s)
		.time(time || 2);
		if(events==2){
			setTimeout("window.location.reload(true)",1.5*1000);
		}else if(events==3){
			setTimeout("art.dialog.close()",1.5*1000);
		}
		return t;
	};
</script>
<script type="text/javascript">
function AutoResizeImage(maxWidth,maxHeight,objImg){
	var img = new Image();
	img.src = objImg.src;
	var hRatio;
	var wRatio;
	var Ratio = 1;
	var w = img.width;
	var h = img.height;
	wRatio = maxWidth / w;
	hRatio = maxHeight / h;
	if (maxWidth ==0 && maxHeight==0){
		Ratio = 1;
	}else if (maxWidth==0){//
		Ratio = hRatio;
	}else if (maxHeight==0){
		Ratio = wRatio;
	}else {
		Ratio = (wRatio<=hRatio?wRatio:hRatio);
	}
	
		w = w * Ratio;
		h = h * Ratio;
	
	objImg.height = h;
	objImg.width = w;
	$(objImg).css("width",w);
	$(objImg).css("height",h);
}

function senfe(e) {
		var s = 1.2;
		var s2 = 8;
		var obj = e.parentNode;
		var oh = parseInt(obj.offsetHeight);
		var h = parseInt(obj.scrollHeight);
		var nh = oh;

		if (obj.getAttribute("oldHeight") == null) {
			obj.setAttribute("oldHeight", oh);
		} else {
			var oldh = Math.ceil(obj.getAttribute("oldHeight"));
		}
		var reSet = function() {
			if (oh < h) {
				e.innerHTML = "- <ingenta-tag:LanguageTag key="Page.Index.Search.Desc.Hide" sessionKey="lang" />";
				if (nh < h) {
					nh = Math.ceil(h - (h - nh) / s);
					obj.style.height = nh + "px";
				} else {
					window.clearInterval(IntervalId);
				}
			} else {
				e.innerHTML = "+ <ingenta-tag:LanguageTag key="Page.Index.Search.Desc.Show" sessionKey="lang" />";
				if (nh > oldh) {
					nhh = Math.ceil((nh - oldh) / s2);
					nh = nh - nhh;
					obj.style.height = nh + "px";
				} else {
					window.clearInterval(IntervalId);
				}
			}
		}
		var IntervalId = window.setInterval(reSet, 10);
	}
	function cnpReload(){
		var isSRP=$("#searchResultPage");
		if(isSRP && isSRP.length){
			$("#formList").submit();
		}else{
			window.location.reload(true);
		}
	}
	function dividePage(val){
	var url=window.location.href;
	if(url.indexOf("?")==-1){
		url=url+"?curpage="+val+"";
	}else{
		if(url.indexOf("curpage=")==-1){
			url=url+"&curpage="+val+"";
		}else{
			url=url.substring(0,url.length-val.length)+val;
		}
	}
	window.location.href=url;
	}
</script>
