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
<script type="text/javascript">
$(document).ready(function(){
initNews('0','${form.newsId}');
getNewsById('${form.newsId}');
$("a[name='whyGray']").click(function(){

});
});
function getNewsById(val){
$(".blank").removeClass("blank");
$("#"+val+"").addClass("blank"); 
$.ajax({
		type : "POST",    
	    url: "${ctx}/pages/news/form/forNewsList",
	    data: {
			  newsId:val      	
			  },
	    success : function(data) {
		          $("#one_news").html(data);
		      },  
		          error : function(data) {
		           }  
		      });
}
function changeNews(targetPage){
				$.ajax({
					type : "POST",    
			        url: "${ctx}/pages/news/form/list",
			        data: {
			        curpage:targetPage,
			        newsId:''    	
			        },
			        success : function(data) {
		             	$("#newslist").html(data);
		            },  
		            error : function(data) {
		            alert("");
		            }  
		      });
}
function GO(tPages){
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
}
function initNews(targetPage,newsId){
				$.ajax({
					type : "POST",    
			        url: "${ctx}/pages/news/form/list",
			        data: {
			        curpage:targetPage,
			        newsId:newsId      	
			        },
			        success : function(data) {
		             	$("#newslist").html(data);
		            },  
		            error : function(data) {
		            alert("咋回事");
		            }  
		      });
}
</script>
</head>

<body>
<jsp:include page="/pages/header/headerData"  />
<!-- 中间内容部分开始 -->
<div class="main">
	<!-- 左侧内容开始 -->
    <div class="chineseLeft oh" id="newslist">
    </div>
    <!-- 左侧内容结束 -->
    <!-- 右侧内容开始 -->
    <div class="chineseRight" id="one_news">
         <div id="free_pub"  class="resource">
           <img src="${ctx}/images/loading.gif"/>
         </div>
    </div>
    <!-- 右侧内容结束 -->
</div>
<!-- 中间内容部分结束 -->


		
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>
