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
changeList();
});

//查询类型
//1,直接通过collectionId查询
//2,产品类型查询
function changeList(){
var colId='${form.collectionId}';
var publisherId = $("#publisherId").val();
var subjectId = $("#subjectId").val();
var langId = $("#langId").val();
var pageCount=$("#pageTag").val();
		$.ajax({
				type : "POST",    
			    url: "${ctx}/pages/collection/form/listForPPV",
			    data: {
			    collectionId:colId,
			    publisherId:publisherId,  
			    subjectId:subjectId,
			    langId:langId,
			    pageCount:pageCount	
			    },
			    success : function(data) { 
		        $("#rightList").html(data);
		        },  
		        error : function(data) {
		        }  
		      });
}

function dividePage(targetPage){
if(targetPage<0){return;}
var colId='${form.collectionId}';
var publisherId = $("#publisherId").val();
var subjectId = $("#subjectId").val();
var langId = $("#langId").val();
var pageCount=$("#pageTag").val();
				$.ajax({
					type : "POST",    
			        url: "${ctx}/pages/collection/form/listForPPV",
			        data: {
			        collectionId:colId,
			        curpage:targetPage,
			        publisherId:publisherId,  
			        subjectId:subjectId,
			        langId:langId,
			        pageCount:pageCount
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
$("#pageTag").val($(obj).val());
changeList();
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
				var s = data.split(":");
				if (s[0] == "success") {
					art.dialog.tips(s[1],3);//location.reload();
					$("#favourites_"+pid).attr("src","${ctx}/images/ico/ico13-blank.png");
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
	
	function doRemove(obj,type){
	$(obj).remove();
	$("#"+type+"Id").val("");
	if($("#publisherId").val()==""&&$("#subjectId").val()==""&&$("#langId").val()==""){$("#searchDiv").hide();}
	changeList();
	}
	
	function addObj(type,name,id){
	$("#"+type+"").remove();
	var htmlCode="<a href='#' onclick=doRemove(this,'"+type+"') id="+type+">"+name+"</a>";
	$("#searchDiv").append(htmlCode);
	$("#"+type+"Id").val(id);
	$("#searchDiv").show();
	changeList();
	}
</script>
</head>

<body>
<jsp:include page="/pages/header/headerData" flush="true" />
<!-- 中间内容部分开始 -->
<div class="main">
<!-- 左侧内容开始 -->
    <div class="chineseLeft">
      <div class="leftClassity">
        <h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Pages.publications.article.Label.Language" sessionKey="lang" /></h1>
        <ul>
        <c:forEach items="${pubList1}" var="vo">
            <li class="oh">
          	<span class="fl"><a href="javascript:void(0)" class="ico_nomal omit w140" onclick="addObj('lang','${vo.lang }','${vo.lang }')">${fn:toUpperCase(vo.lang)}</a></span>
            <span class="fr"><a href="javascript:void(0)" onclick="addObj('lang','${vo.lang }','${vo.lang }')">[${vo.eachCount }]</a></span>
          </li>
        </c:forEach>
        </ul>
      </div>
      <div class="leftClassity">
        <h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Pages.publications.article.Label.publisher" sessionKey="lang" /></h1>
        <ul class="updownUl">
        <c:forEach items="${pubList}" var="vo">
            <li class="oh">
          	<span class="fl"><a href="javascript:void(0)" class="ico_nomal omit w140" onclick="addObj('publisher','${vo.publisher.name}','${vo.publisher.id}')">${vo.publisher.name }</a></span>
            <span class="fr"><a href="javascript:void(0)" onclick="addObj('publisher','${vo.publisher.name}','${vo.publisher.id}')">[${vo.eachCount }]</a></span>
          </li>
        </c:forEach>
        </ul>
        <span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" />...</a></span>
      </div>
      <div class="leftClassity">
        <h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Subject" sessionKey="lang" /></h1>
        <ul class="updownUl">
        <c:forEach items="${pcsList}" var="vo">
            <li class="oh">
          	<span class="fl"><a href="javascript:void(0)" class="ico_nomal omit w140" onclick="addObj('subject','${vo.subject.name}','${vo.subject.id}')"><span class="w25 dsB fl">${vo.subject.code }</span>${vo.subject.name}</a></span>
            <span class="fr"><a href="javascript:void(0)" onclick="addObj('subject','${vo.subject.name}','${vo.subject.id}')">[${vo.eachCount }]</a></span>
          </li>
        </c:forEach>
        </ul>
        <span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang" /> ...</a></span>
      </div>
      
    </div>
       <div id="searchDiv" class="take" style="display: none"><span>搜索范围：</span>
       <input type="hidden" id="publisherId" value="${form.publisherId }"/>
       <input type="hidden" id="subjectId" value="${form.subjectId }"/>
       <input type="hidden" id="langId" value="${form.langId }"/>
       <input type="hidden" id="pageTag" value="10"/>
       </div>
    <!-- 左侧内容结束 -->
     <!-- 右侧内容开始 -->
    <div class="chineseRight" id="rightList">
		<img src="${ctx}/images/loading.gif"/>
	</div>
   <!-- 右侧内容结束 -->
         
</div>
    
<!-- 中间内容部分结束 -->


<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
</body>
</html>
