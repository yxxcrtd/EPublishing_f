<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>弹出层</title>
<style type="text/css">
*{
	margin:0;
	padding:0;
}
html body {
	font-family: Microsoft YaHei, Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 22px;
	color: #636363;
	background:url(../images/bodyBg.gif) repeat-x;
}
a{
	text-decoration:none;
	color:#0081cb;
}
.pop{
	width:340px;
	border:1px solid #ddd;
	box-shadow:0px 2px 4px #666;
	text-align:center;
	margin:20px auto;
}
.addContDiv{
	padding:0 50px 40px 50px;
	width:240px;
}
.ico{
	color:#FFF;
	padding:6px 15px 6px 32px;
	margin-right:5px;
}
.ico_cart{
	background:#008cd6 url(images/ico/ico14.png) no-repeat 10px center;
}
.ico_reading{
	background:#008cd6 url(images/ico/ico-reading.png) no-repeat 10px center;
}
.ico_download{
	background:#008cd6 url(images/ico/ico-download.png) no-repeat 10px center;
}
.ico_recommed{
	background:#008cd6 url(images/ico/ico16.png) no-repeat 10px center;
}
.blueA,.blueB,.blueC{
	padding:1px 15px;
	color:#fff;
	background:#008cd6;
}
.blueB{
	padding:6px 15px;
}
.mt30{
	margin-top:30px;
}
.mr5{
	margin-right:5px;
}
.ml30{
	margin-left:30px;
}
.mr30{
	margin-right:30px;
}
.pb50{
	padding-bottom:50px;
}
.a_det{
	height:40px;
	text-align:right;
	padding:5px 8px 0 0;
}



</style>

<script language="javascript">
	function recommendSubmit() {
		 $.ajax({		
			type : "POST",
			url : "${ctx}/pages/recommend/form/submit",
			data : {
				pubid : $("#pubid").val(),
				note : $("#rnote").val(),
				r_ : new Date().getTime()
			},
			success : function(data) {
				var s = data.split(":");
				if (s[0] == "success") {
					art.dialog.tips(s[1],1);
					confirmTerm();
				}else{
					alert(s[1],1,'error'); 
				}
			},
			error : function(data) {
				art.dialog.tips(data,1,'error');
			}
		}); 
	}
	function addToCart(pid, ki,Staust) {
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
					if(Staust==1){
						$("#add_cart").css("display","none");
						$("#cartCount").html("["+s[2]+"]");
						$("#priceTd").html("<ingenta-tag:LanguageTag key='Page.Publications.Article.Lable.Buying' sessionKey='lang' />");
					}else if(Staust==2){
						$("#add_cart_"+pid).hide();
						$("#cartCount").html("["+s[2]+"]");
						$("#priceTd").html("<ingenta-tag:LanguageTag key='Page.Publications.Article.Lable.Buying' sessionKey='lang' />");
					}
				}else{
					art.dialog.tips(s[1],1,'error');
				}
			},
			error : function(data) {
				art.dialog.tips(data,1,'error');
			}
		});
	}
	function downLoad(){
		$.ajax({
			type : "POST",
			url :"" ,
			data : {
			}, 
		})
	}
</script>
</head>

 <%-- <a id="favourites_div_${form.obj.id}" class="link gret_collect" onclick="addFavourites('${form.obj.id}',1);">
 --%>

<body>
<div class="pop">
	<div class="a_det"><a href="javascript:void(0)"><img src="images/caohao.png"/></a></div>
    <div class="addContDiv"> 
    
       <c:if test="${pageCode=p2}">
        <p><ingenta-tag:LanguageTag key="Page.Publications.Lable.Pop1" sessionKey="lang" /></p>
       <!--  您未购买此资源，是否要将此资源加入到购物车中 -->
        <p class="mt30"><span class="mr5"><a href="javascript:void(0)" class="ico ico_cart" onclick="addToCart('${form.obj.id}','${form.obj.kind},1')"><ingenta-tag:LanguageTag key="Page.Publications.Lable.Buy" sessionKey="lang"/></a></span></p>
        </c:if>
        <c:if test="${pageCode=p1} ">           
         <p><ingenta-tag:LanguageTag key="Page.Publications.Lable.Pop2" sessionKey="lang"/></p>
          <p class="mt30"><span class="mr5"><a href="javascript:void(0)" class="ico ico_reading"><ingenta-tag:LanguageTag key="Global.Button.DownLoad.Page" sessionKey="lang"/></a></span> <span class="mr5">
          <a href="javascript:void(0)" class="ico ico_download"><ingenta-tag:LanguageTag key="Global.Button.DownLoad" sessionKey="lang"/></a></span>
        </c:if>
        <c:if test="${pageCode=p3} ">
        <p><ingenta-tag:LanguageTag key="Page.Publications.Lable.Pop3" sessionKey="lang"/></p>
        <p class="mt30"><span class="mr5"><a href="javascript:void(0)" class="ico ico_recommed" onclick="recommendSubmit()"><ingenta-tag:LanguageTag keu="Page.Index.Search.Link.Recommend" sessionKey="lang"/></a></span></p>
        </c:if>
    </div>
</div>
  
<%-- <div class="pop">
	<div class="a_det"><a href="javascript:void(0)"><img src="images/caohao.png"/></a></div>
    <div class="addContDiv">
        <p><ingenta-tag:LanguageTag key="Page.Publications.Lable.Pop2" sessionKey="lang"/></p>
        <p class="mt30"><span class="mr5"><a href="javascript:void(0)" class="ico ico_reading">阅读</a></span> <span class="mr5"><a href="javascript:void(0)" class="ico ico_download">下载</a></span>
        </p>
    </div>
</div> --%>

<%-- <div class="pop">
	<div class="a_det"><a href="javascript:void(0)"><img src="images/caohao.png"/></a></div>
    <div class="addContDiv">
        <p><ingenta-tag:LanguageTag key="Page.Publications.Lable.Pop3" sessionKey="lang"/></p>
        <p class="mt30"><span class="mr5"><a href="javascript:void(0)" class="ico ico_recommed">推荐</a></span></p>
    </div>
</div> --%>

<!-- <div class="pop">
	<div class="a_det"><a href="javascript:void(0)"><img src="images/caohao.png"/></a></div>
    <div class="addContDiv">
        <p>您未购买此资源，请选择您要对资源进行的操作</p>
        <p class="mt30"><span class="mr5"><a href="javascript:void(0)" class="ico ico_reading">购买</a></span> <span class="mr5"><a href="javascript:void(0)" class="ico ico_download">推荐</a></span></p>
    </div>
</div> -->
</body>
</html>
