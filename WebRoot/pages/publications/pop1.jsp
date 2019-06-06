<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/tools.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>弹出层</title>
<script type="text/javascript" src="${ctx }/js/PopUpLayer.js"></script>
<script language="javascript">
function addToCart(pid, ki) {
	$.ajax({
		type : "POST",
		url : "${ctx}/pages/cart/form/add",
		data : {
			pubId : pid,
			kind : ki,
			r_ : new Date().getTime()
		},
		success : function(data) {
			var s = data.split(":");
			if (s[0] == "success") {
				art.dialog.tips(s[1],1);//location.reload();
				parent.document.getElementById("cartCount").innerHTML="["+s[2]+"]";
			}else{
				art.dialog.tips(s[1],1,'error');
			}
		},
		error : function(data) {
			art.dialog.tips(data,1,'error');
		}
	});
}
function close1(){
	if(parent.art.dialog.list['getResourceId']===undefined){
		art.dialog.close();
	}else{
		parent.art.dialog.list['getResourceId'].close();
	}
	
}
// 弹出层调用
function popTips(pid) {
	art.dialog.open("${ctx}/pages/recommend/form/edit?pubid=" + pid, {title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/>",top: 100, width: 500, lock:true});
}
</script>

<script type="text/javascript">

//在线阅读弹出层调用
function viewPopTips(id,page) {
	var url="";
	         
        //tmp.focus() ;
	if(page=='0'){
		url = "${ctx}/pages/view/form/view?id="+id;
	}else{
		url = "${ctx}/pages/view/form/view?id="+id+"&nextPage="+page;
	}
	var of=false;
	<c:if test="${(pub.free!=null&&pub.free==2) || (pub.oa!=null&&pub.oa==2) }">
		of=true;
	</c:if>  
      //首先Ajax查询要阅读的路径
if(of){
	//window.location.href=url;
	var tmp=window.open("about:blank","","scrollbars=yes,resizable=yes,channelmode") ; 
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
			//	window.location.href="${ctx}/pages/view/form/view?id="+id+"&webUrl="+s[1];
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
// 下载的跳转
function down() {
	var totalLenth = window.location.href.length;
	var did = window.location.href.substr(totalLenth - 32, totalLenth);
	window.location.href = "${ctx}/pages/view/form/download?isFull=true&pubId=" + did;
}
</script>

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
	background:#008cd6 url(../../../images/ico/ico14.png) no-repeat 10px center;
}
.ico_reading{
	background:#008cd6 url(../../../images/ico/ico-reading.png) no-repeat 10px center;
}
.ico_download{
	background:#008cd6 url(../../../images/ico/ico-download.png) no-repeat 10px center;
}
.ico_recommed{
	background:#008cd6 url(../../../images/ico/ico16.png) no-repeat 10px center;
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


</head>

 <%-- <a id="favourites_div_${form.obj.id}" class="link gret_collect" onclick="addFavourites('${form.obj.id}',1);">
 --%>
	<c:set var="readPubId" value="${pub.type==3?pub.publications.id:pub.id}"/>
      <c:set var="readPubStartPage" value="${pub.type==3?pub.startPage:0}"/>
<body>
	<c:if test="${pageCode == 's1'}">
    <div class="addContDiv" align="center" style="position: relative;top:50px;">
        <p><ingenta-tag:LanguageTag key="Page.Publications.Lable.Pops1" sessionKey="lang"/></p>
        <p class="mt30"><span class="mr5"><a  href="javascript:void(0)" class="blueB" onclick="close1()">
         				<ingenta-tag:LanguageTag key="Pages.Cart.Lable.Button1" sessionKey="lang"/></a></span></p>
        
    </div>
    </c:if>
    <c:if test="${pageCode == 's2'}">
    <div class="addContDiv" align="center" style="position: relative;top:50px;">
        <p><ingenta-tag:LanguageTag key="Page.Publications.Lable.Pops2" sessionKey="lang"/></p>
        <p class="mt30"><span class="mr5"><a  href="javascript:void(0)" class="blueB" onclick="close1()">
         				<ingenta-tag:LanguageTag key="Pages.Cart.Lable.Button1" sessionKey="lang"/></a></span></p>
        
    </div>
    </c:if>
	<c:if test="${pageCode == 'p2'}">
    <div class="addContDiv" align="center" style="position: relative;top:50px;">
        <p><ingenta-tag:LanguageTag key="Page.Publications.Lable.Pop1" sessionKey="lang"/></p>
         <p class="mt30"><span class="mr5"><a href="javascript:void(0)" class="ico ico_cart" onclick="addToCart('${id}',1)">
         				<ingenta-tag:LanguageTag key="Page.Publications.Lable.Buy" sessionKey="lang"/></a></span></p>
    </div>
    </c:if>
    <c:if test="${pageCode == 'p3'}">
    <div class="addContDiv" align="center" style="position: relative;top:50px;">
        <p><ingenta-tag:LanguageTag key="Page.Index.Search.Alert.RecommedAlert" sessionKey="lang" /></p>
        				 <p class="mt30">
        					<span class="mr5">
							<a href="javascript:void(0)" id="recommend_div" class="ico ico_recommed" onclick="popTips('${pub.id}');">
								<ingenta-tag:LanguageTag key="Page.Index.Search.Button.Recommed" sessionKey="lang" />
							</a>
							</span>
        				</p>
        				
    </div>
    </c:if>


<c:if test="${pageCode == 'p1'}">
    <div class="addContDiv" align="center" style="position: relative;top:50px;">
        <p><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Publications.Lable.Pop2"/></p>
        <p class="mt30">
        	<span class="mr5">
        		<a href="javascript:void(0)" class="ico ico_reading" onclick="viewPopTips('${readPubId}','${readPubStartPage}')"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Publications.Lable.Read" /></a>
        	</span>
			<span class="mr5">
				<a href="javascript:;" class="ico ico_download" onclick="down();"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.DownLoad"/></a>
			</span>
        </p>
    </div>
</c:if>

     <c:if test="${pageCode == 'p4'}">
    <div class="addContDiv" align="center" style="position: relative;top:50px;">
        <p><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Publications.Lable.Pop4"/></p>
        <p class="mt30"><span class="mr5"><a href="javascript:void(0)" class="ico ico_cart" onclick="addToCart('${id}',1)"><ingenta-tag:LanguageTag key="Page.Publications.Lable.Buy" sessionKey="lang"/></a></span>
			<span class="mr5"><a href="javascript:void(0)" class="ico ico_recommed" onclick="popTips('${pub.id}');"><ingenta-tag:LanguageTag key="Page.Index.Search.Button.Recommed" sessionKey="lang" /></a></span>
        </p>
    </div>
    </c:if>
    <c:if test="${pageCode == 'p5'}">
    <div class="addContDiv" align="center" style="position: relative;top:50px;">
        <p><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Publications.Lable.Pop2"/></p>
         <p class="mt30"><span class="mr5"><a href="javascript:void(0)" class="ico ico_reading" onclick="viewPopTips('${readPubId}','${readPubStartPage}')"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Publications.Lable.Read" /></a></span></p>
    </div>
    </c:if>
     <c:if test="${pageCode == 'p6'}">
    <div class="addContDiv" align="center" style="position: relative;top:80px;">
        <p><font size="4px"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Publications.Lable.Pop5"/></p>
    </div>
    </c:if>
    
</body>
</html>
