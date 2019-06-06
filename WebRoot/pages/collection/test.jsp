<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv='X-UA-Compatible' content='IE=edge' />
<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
<%@ include file="/common/tools.jsp"%>
<%@ include file="/common/ico.jsp"%>
<script type="text/javascript" src="${ctx }/js/jquery.highlight.js"></script>
<script type="text/javascript">
$(document).ready(function(){
changeList("1","");
// 收藏和取消收藏
$(".favourite").on("click", function() {
	var This = $(this);
	This.each(function() {
		$.get("${ctx}/pages/favourites/form/commit", { pubId : This.attr("id") }, function(data) {
			if ("success" == data) {
				This.find("a").attr("class", "blank");
				This.find("img").attr("src", "${ctx}/images/favourite.png");
				This.find("span").html("<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
				art.dialog.tips('<ingenta-tag:LanguageTag key='Controller.Favourites.commit.success' sessionKey='lang' />', 1, 'success');
			} else if ("del" == data) {
				This.find("a").attr("class", "");
				This.find("img").attr("src", "${ctx}/images/unfavourite.png");
				This.find("span").html("<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' />");
				art.dialog.tips('<ingenta-tag:LanguageTag key='Controller.Favourites.commit.cancel' sessionKey='lang' />', 1, '');
			}
		});
	});
});
});

function checkCart(cid,ki){
		$.ajax({
  			type : "POST",  
			url: "${ctx}/pages/collection/form/getInLicense",
			data: {
				collectionId:cid
				  },
			    success : function(data) {  
			    if(data!=""){
			       art.dialog({
                   content:"在当前打包集中，您已经购买过以下产品：<br>"+data+"是否继续购买？",
                   okVal:'继续',    
                   ok: function () { 
                   addToCart1(cid,ki); 
                   },
                   cancelVal:'返回',cancel:true,
                  lock:true,resize:true,fixed:false});
                  return;
			    };
			     addToCart1(cid,ki);
			    },  
			    error : function(data) {  
			    art.dialog.tips(data,1,'error');
			    }  
		});
 


}
function addToCart1(cid,ki){
		$.ajax({
  			type : "POST",  
			url: "${ctx}/pages/cart/form/add",
			data: {
				collectionId:cid,
				kind:ki,
				r_ : new Date().getTime()},
			    success : function(data) {  
			    var s = data.split(":");
			    if(s[0]=="success"){
			    	art.dialog.tips(s[1],1);
			    	$("#buyindetail").css("display","none");
			    	$(".buyindetails").css("display","none");
			    	$("#cartCount").html("["+s[2]+"]");
			    }else{
			    	art.dialog.tips(s[1],1,'error');
			    }
			},  
			error : function(data) {  
			    art.dialog.tips(data,1,'error');
			}  
		});
	}
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
				art.dialog.tips(s[1],1);
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
//查询类型
//1,直接通过collectionId查询
//2,产品类型查询

function changeList(type,val){
if(type>99){
val=$("#searchInput").val();
}
var id='${form.collectionId}';
		$.ajax({
				type : "POST",    
			    url: "${ctx}/pages/collection/form/listForRight",
			    data: {
			    collectionId:id,
			    marktype:type,
			    typeValue:val,
			    inLicense:'${form.inLicense}',
			    inOrderDetail:'${form.inOrderDetail}'      	
			    },
			    success : function(data) { 
		        $("#rightList").html(data);
		        },  
		        error : function(data) {
		        }  
		      });
}
function dividePage(targetPage){
var id='${form.collectionId}';
var pageCount=$("#pageCount").val();
				$.ajax({
					type : "POST",    
			        url: "${ctx}/pages/collection/form/listForRight",
			        data: {
			        collectionId:id,
			        curpage:targetPage,
			        pageCount:pageCount,
			        inLicense:'${form.inLicense}',
			        inOrderDetail:'${form.inOrderDetail}' 
			        },
			        success : function(data) {
		             	$("#rightList").html(data);
		            },  
		            error : function(data) {
		            alert("");
		            }  
		      });
}
function GO(obj){
$("#pageCount").val($(obj).val());
dividePage(0);
}
/* function GO(tPages){
var reg=/^\d+$/;
if(!reg.test($("#pageTag").val())){
alert("请输入正确的页码");
return;
}
var pageMark = $("#pageTag").val()-1;
if(eval($("#pageTag").val())>eval(tPages)){
pageMark = tPages-1;
}else if(eval($("#pageTag").val())<1){
pageMark = 0;
}
changeNews(pageMark);
} */

    //获取资源
	function popTips2(pid) {
	/* 	showTipsWindown("",
				'simTestContent', $(window).width()*0.6, $(window).height()*0.65); */
				/* alert(pid); */
				art.dialog.open("${ctx}/pages/publications/form/getResource?pubid="+pid,{id : "getResourceId",title:"",top: 200,width: 340, height: 200,lock:true}); /* <ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/> */
	}
	//推荐
		function popTips(pid) {
	/* 	showTipsWindown("",
				'simTestContent', $(window).width()*0.6, $(window).height()*0.65); */
				art.dialog.open("${ctx}/pages/recommend/form/edit?pubid="+pid,{title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/>",top: 100,width: 700, height: 400,lock:true});
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
			    $("#"+pid+"").css("display","none");
				var s = data.split(":");
				if (s[0] == "success") {
					art.dialog.tips(s[1],1);//location.reload();
				}else{
					art.dialog.tips(s[1],1,'error');
				}
			},
			error : function(data) {
				art.dialog.tips(data,1,'error');
			}
		});
	}
</script>
</head>
<body>
	<jsp:include page="/pages/header/headerData" flush="true" />
	<!-- 中间内容部分开始 -->
	<div class="main">
		<div class="prod fontFam">
			<div class="fl w850">
				<p>${pCollection.name}</p>
				<p>
					<span class="mr10"><ingenta-tag:LanguageTag key='Page.Publications.Collection.Chinese' sessionKey='lang'/>：${form.cnBook}</span> 
					<span class="mr10"><ingenta-tag:LanguageTag key='Page.Publications.Collection.Forgin' sessionKey='lang'/>：${form.enBook}</span>
					<span class="mr10"><ingenta-tag:LanguageTag key='Page.Collection.Lable.Ejournal' sessionKey='lang'/>：${form.jounary}</span>
					<span class="mr10"><ingenta-tag:LanguageTag key='Page.Index.Search.Lable.Finded4.Option7' sessionKey='lang'/>：${form.issue }</span>
				</p>
				<p>
				<ingenta-tag:LanguageTag key='Pages.publications.article.Lable.Description' sessionKey='lang'/>：</p>
				<p>
				${pCollection.desc}
                </p>
			</div>
			<div class="fr w120 tc">
				<p>
					<img src="${ctx }/${pCollection.cover}" width="85" height="116" onerror="this.src='${ctx}/images/smallimg.jpg'"/>
				</p>
				<p class="mt5" id="buyindetail">
				<!-- 已登录 -->
				<c:if test="${sessionScope.mainUser !=null }">
				<!-- 未购买 -->
				<c:if test="${!form.inLicense&&!form.inOrderDetail }">
				<a href="javascript:void(0)" onclick="checkCart('${form.collectionId}',2)"><img src="${ctx }/images/ico/ico14-blank.png" class="vm"/>
                <ingenta-tag:LanguageTag key='Page.Publications.Lable.Buy' sessionKey='lang'/></a>
				<p>
				<ingenta-tag:LanguageTag key='Pages.collection.view.Table.price' sessionKey='lang'/>：
				${pCollection.currency}&nbsp;<fmt:formatNumber value="${pCollection.price}" pattern="0.00" /></p>
				</c:if>
				</c:if>
				</p>
				
			</div>
		</div>
		<!-- 左侧内容开始 -->
		<div class="chineseLeft">
		<c:if test="${sessionScope.mainUser !=null }">
		<c:if test="${form.inLicense}">
			<div class="searchIndex"
				style="width:210px;border:1px solid #ddd; margin-bottom:15px;">
				<ul>
					<li><input type="text" class="searchInput" id="searchInput"
						style="width:168px; " /></li>
					<li class="searImg"><a href="javascript:void(0)" onclick='changeList(100,"")'><img
							src="${ctx }/images/search2.gif" /></a>
						<ul class="searClassify"
							style="margin-top:1px; margin-left:-47px;">
							<li><a href="javascript:void(0)" onclick='changeList(100,"")'><ingenta-tag:LanguageTag key='Page.Frame.Header.Lable.FullText' sessionKey='lang'/></a></li>
							<li><a href="javascript:void(0)" onclick='changeList(101,"")'><ingenta-tag:LanguageTag key='Page.Index.AdvSearch.Lable.InTitle' sessionKey='lang'/></a></li>
							<li><a href="javascript:void(0)" onclick='changeList(102,"")'><ingenta-tag:LanguageTag key='Page.Index.Index.Lable.Author' sessionKey='lang'/></a></li>
							<li><a href="javascript:void(0)" onclick='changeList(103,"")'>ISBN/ISSN</a></li>
							<li><a href="javascript:void(0)" onclick='changeList(104,"")'><ingenta-tag:LanguageTag key='Pages.collection.view.Table.publisher' sessionKey='lang'/></a></li>
						</ul></li>
				</ul>
			</div>
			</c:if>
			</c:if>
			<div class="leftClassity">
				<h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key='Page.Order.Checkout.Lable.Pub' sessionKey='lang'/></h1>
				<ul class="updownUl">
				<c:set var="k" value="1"/>
			    <c:forEach items="${typeStatic }" var="p">
			    <c:if test="${p.effective>0}">
			    <c:set var="k">${k+1 }</c:set>
			    <li class="oh">
			    <span class="fl">
			    <a href="javascript:void(0)" onclick="changeList('2','${p.publications.type}')" class="ico_nomal omit w140">
				    <c:if test="${p.publications.type==1}">
				    	<ingenta-tag:LanguageTag key='Pages.User.Subscription.book' sessionKey='lang'/>
				    </c:if>
				    <c:if test="${p.publications.type==2}">
				    	<ingenta-tag:LanguageTag key='Pages.Index.Lable.Journal' sessionKey='lang'/>
				    </c:if>
				    <c:if test="${p.publications.type==4}">
				    	<ingenta-tag:LanguageTag key='Pages.Index.Lable.Article' sessionKey='lang'/>
				    </c:if>
				    <c:if test="${p.publications.type==7}">
				    	<ingenta-tag:LanguageTag key='Page.Index.Search.Lable.Finded4.Option7' sessionKey='lang'/>
				    </c:if>
			    </a>
			    </span> 
			    <span class="fr">
			    <a href="javascript:void(0)" onclick="changeList('2','${p.publications.type}')">[${ p.effective}]</a>
			    </span>
			    </li>
			    </c:if>
				</c:forEach>
				</ul>
				<c:if test="${k>6}">
				<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</a></span>
				</c:if>
			</div>
			<div class="leftClassity">
				<h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key='Page.Frame.Left.Lable.Language' sessionKey='lang'/></h1>
				<ul class="updownUl">
				<c:set var="k" value="1"/>
			    <c:forEach items="${langStatic }" var="p">
			    <c:if test="${p.effective>0}">
			    <c:set var="k">${k+1 }</c:set>
			    <li class="oh">
			    <span class="fl">
			    <a href="javascript:void(0)" onclick="changeList('3','${p.publications.lang}')" class="ico_nomal omit w140">
			    ${p.publications.lang}
			    </a>
			    </span> 
			    <span class="fr">
			    <a href="javascript:void(0)" onclick="changeList('3','${p.publications.lang}')">[${ p.effective}]</a>
			    </span>
			    </li>
			    </c:if>
				</c:forEach>
				</ul>
				<c:if test="${k>6}">
				<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</a></span>
				</c:if>
			</div>
			<div class="leftClassity">
				<h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key='Page.Index.Index.Lable.Publisher' sessionKey='lang'/></h1>
				<ul class="updownUl">
				<c:set var="k" value="1"/>
			    <c:forEach items="${pubStatistic }" var="p">
			    <c:if test="${p.collectionPubContent>0}">
			    <c:set var="k">${k+1 }</c:set>
			    <li class="oh">
			    <span class="fl">
			    <a href="javascript:void(0)" onclick="changeList('4','${p.id}')" class="ico_nomal omit w140">
			    ${p.name }
			    </a>
			    </span> 
			    <span class="fr">
			    <a href="javascript:void(0)" onclick="changeList('4','${p.id}')">[${ p.collectionPubContent}]</a>
			    </span>
			    </li>
			    </c:if>
				</c:forEach>
				</ul>
				<c:if test="${k>6}">
				<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</a></span>
				</c:if>
			</div>
			<div class="leftClassity">
				<h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key='Page.Frame.Left.Lable.Subject' sessionKey='lang'/></h1>
				<ul class="updownUl">
				<c:set var="k" value="1"/>
				<c:forEach items="${subList }" var="sub" varStatus="index">
				<c:if test="${sub.countPP>0 }">
				<c:set var="k">${k+1 }</c:set>
				<li class="oh">
				<span class="fl">
				<a href="javascript:void(0)" class="ico_nomal omit w140" onclick="changeList('5','${sub.id}')">
				<span class="w25 dsB fl">${sub.code }</span>
				<c:if test="${sessionScope.lang=='zh_CN' }">${sub.name}</c:if>
			    <c:if test="${sessionScope.lang=='en_US' }">${sub.nameEn}</c:if>
                </a>
				</span> 
			    <span class="fr"><a href="javascript:void(0)" onclick="changeList('5','${sub.id}')">[${ sub.countPP }]</a></span></li>
				</c:if>
				</c:forEach>
				</ul>
				<c:if test="${k>6}">
				<span class="updownMore"><a href="javascript:void(0)" onclick="changeList('5','${sub.id}')" class="ico_nomal"><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</a></span>
				</c:if>
			</div>
		</div>
		<!-- 左侧内容结束 -->
		<!-- 右侧内容开始 -->
		<div class="chineseRight" id="rightList">
		<img src="${ctx}/images/loading.gif"/>
		</div>
		<!-- 资源列表结束 -->
		<!-- 右侧内容结束 -->
	</div>
	<!-- 中间内容部分结束 -->


		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>
