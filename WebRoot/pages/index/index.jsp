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
			$(document).ready(function() {
				var hotNum = 0;	
				var lastNum = 0;
				var readNum = 0;		
				gettitleView();
				getSubjectAll();//学科分类
				getHotWords();//热词查询
				getService(3);//服务
				getfreePub();//免费资源
				getNews(10);
				if(${sessionScope.mainUser!=null}){
					readNum = 4;
					recentlyRead(readNum);//最近阅读
				}
				/* <c:if test="${sessionScope.institution!=null }">
					hotNum = 3;
					getHotReading(hotNum);//热读资源
				</c:if> */
				hotNum = 4;
				getHotReading(hotNum);//热读资源
				
				getResourceAll();//资源总数
				var realHotNum=0;
				<c:if test="${sessionScope.institution!=null }">
				var hotReading=$("#hot_reading div.block");
				realHotNum=hotReading?hotReading.length:0;
				if(realHotNum==0){
					$("#hot_reading").hide();
				}				
				//</c:if>		
			//	lastNum = 10 - realHotNum - readNum;	
				lastNum=5;
				getLastPubs(lastNum);//最新资源
				
				
			});
			//获取最新资源
			function getLastPubs(val){
				var parObj=$("#lastPubs");
				$.ajax({
					type : "POST",   
			        url: "${ctx}/pages/publications/lastPubs",
			        data: {
			        num:val,      	
			        },
			        success : function(data) { 
		             	$(parObj).html(data);
		             	$(parObj).css("text-align","left");
		            },  
		            error : function(data) {
		              	$(parObj).html(data);
		            }  
		      });
			}
			//获取分类列表
			function getSubjectAll(){
				var parObj=$(".subject_all");
				$.ajax({
					type : "POST",  
			        url: "${ctx}/pages/subject/subjectAll",
			        success : function(data) { 
		             	$(parObj).html(data);
		             	$(parObj).css("text-align","left");
		            },  
		            error : function(data) {
		              	$(parObj).html(data);
		            }  
		      });
			}
			//热词查询
			function getHotWords(){
				var parObj=$("#hot_wordss");
				$.ajax({
					type : "POST",   
			        url: "${ctx}/pages/index/hotWords",
			        success : function(data) { 
		             	$(parObj).html(data);
		             	$(parObj).css("text-align","left");
		            },  
		            error : function(data) {
		              	$(parObj).html(data);
		            }  
		      });
			}
			//热读资源
			function getHotReading(val){
				var parObj=$("#hot_reading");
				$.ajax({
					type : "POST",   
			        url: "${ctx}/pages/index/hotReading",
			        async:false,
			        data: {
			        num:val      	
			        },
			        success : function(data) { 
		             	$(parObj).html(data);
		             	$(parObj).css("text-align","left");
		            },  
		            error : function(data) {
		              	$(parObj).html(data);
		            }  
		      });
			}
			
			//获取资源数量列表
			function getResourceAll(){
				var parObj=$("#resource_all");
				$.ajax({
					type : "POST",   
			        url: "${ctx}/pages/publications/pubsCounter",
			        data: {			        	
			        	w:300
			        },
			        success : function(data) { 
		             	$(parObj).html(data);
		             	$(parObj).css("text-align","left");
		            },  
		            error : function(data) {
		              	$(parObj).html(data);
		            }  
		      });
			}
			//最近阅读
			function recentlyRead(val){
				var parObj=$("#recently_read");
				$.ajax({
					type : "POST",    
			        url: "${ctx}/pages/index/recentlyRead",
			        data: {
			        num:val      	
			        },
			        success : function(data) { 
		             	$(parObj).html(data);
		            },  
		            error : function(data) {
		              	$(parObj).html(data);
		            }  
		      });
			}
			//服务
			function getService(val){
				$.ajax({
					type : "POST",    
			        url: "${ctx}/pages/service/form/listForIndex",
			        data: {
			        pageCount:val      	
			        },
			        success : function(data) { 
		             	$("#service_read").html(data);
		            },  
		            error : function(data) {
		            }  
		      });
			}
		    //新闻
			function getNews(val){
				$.ajax({
					type : "POST",    
			        url: "${ctx}/pages/news/form/listForIndex",
			        data: {
			        pageCount:val      	
			        },
			        success : function(data) {
		             	$("#news_read").html(data);
		            },  
		            error : function(data) {
		            }  
		      });
			}
			//左侧条件查询
			function searchByCondition(type,value,a1,a2,a3,a4){
			
				document.formList.action="${ctx}/pages/publications/form/list";
				if(type=="searchValue"){
					$("#searchValue1").val(value);
					if(${sessionScope.selectType==1}){
			 			$("#lcense1").val("1");
			 			document.formList.action="${ctx}/index/searchLicense";
				 	}else{
				 		document.formList.action="${ctx}/index/search";
				 	}
				}else if(type=="type"){
					$("input[name='pubType']").val(value);
				}else if(type=="publisher"){
					$("input[name='publisher']").val(value); 
					$("input[name='publisherId']").val(a1);
				}else if(type=="pubDate"){
					$("input[name='pubDate']").val(value);
				}else if(type=="taxonomy"){
					$("#taxonomy1").val(value);
					$("#pCode1").val(a1);
					$("#code1").val(a2);
					$("#subParentId1").val(a3);
					$("#parentTaxonomy1").val(a4);
					document.formList.action="${ctx }/pages/subject/form/list";
				}else if(type=="taxonomyEn"){
					$("#taxonomyEn1").val(value);
					$("#pCode1").val(a1);
					$("#code1").val(a2);
					$("#subParentId1").val(a3);
					$("#parentTaxonomyEn1").val(a4);
					document.formList.action="${ctx }/pages/subject/form/list";
				}
				$("#curpage").val(0);
				$("#pageCount").val(10);
				$("#order1").val('');
				$("#lcense1").val('${sessionScope.selectType}');
				$("#keywordCondition").remove();
				document.formList.submit();
			}
			function advancedSubmit(tvalue){
				
				<c:if test="${sessionScope.lang!='en_US' }">
				$("#taxonomy1").val(tvalue);
				</c:if>
				<c:if test="${sessionScope.lang!='zh_CN' }">
				$("#taxonomyEn1").val(tvalue);
				</c:if>
				$("#type1").val('');
				$("#publisher1").val('');
			 	$("#pubDate1").val(''); 	
			 	$("#pCode1").val('');
			 	$("#publisherId1").val('');
			 	$("#subParentId1").val('');
			 	$("#parentTaxonomy1").val('');
			 	$("#parentTaxonomyEn1").val('');
			 	$("#curpage").val(0);
				$("#pageCount").val(10);
				document.formList.action="${ctx}/index/advancedSearchSubmit";
				document.formList.submit();
				
			}
			//获取A-Z列表
			function gettitleView(){
				var parObj=$("#title_view");
				$.ajax({
					type : "POST",  
			        url: "${ctx}/pages/index/titleView",
			        success : function(data) { 
		             	$(parObj).html(data);
		             	$(parObj).css("text-align","left");
		            },  
		            error : function(data) {
		              	$(parObj).html(data);
		            }  
		      });
			}
			//免费资源
			function getfreePub(){
				var parObj=$("#free_pub");
				$.ajax({
					type : "POST",  
			        url: "${ctx}/pages/index/free",
			        success : function(data) { 
		             	$(parObj).html(data);
		             	$(parObj).css("text-align","left");
		            },  
		            error : function(data) {
		              	$(parObj).html(data);
		            }  
		      });
			}
			function toNewsList(){
				window.location.href="${ctx}/pages/publications/lastPubs?news=true";
			};
		</script>		
	</head>
	<body>
		<!--以下top state -->
		<jsp:include page="/pages/header/headerData"  />
		<!--以上top end -->
		<div class="banner" id="full-screen-slider">
	<!--滚动条内容块开始-->
	<jsp:include page="/pages/advertisement/form/listForIndex">
	<jsp:param name="position" value="1" />
	<jsp:param name="status" value="1" />
	</jsp:include>

    <!--滚动条内容块结束-->
</div>
<div class="main">
	<div class="oh">
    	<!------- 左侧分类内容开始 ---------->
    	<!------- 学科分类 ---------->
    	<div class="classify">
        	<h1 class="h1Tit"><ingenta-tag:LanguageTag key="Global.Label.Subject_Categories" sessionKey="lang"/></h1>
        
	        <div class="subject_all">
	       	 </div>
	      
       	 </div>
        <!------- 学科分类 结束---------->
        <!------- 按标题浏览开始 ---------->
        <div id="title_view" class="titleClassify">
			<img src="${ctx}/images/loading.gif"/>
        </div>
        <!-- <div class="titleClassify">
        	<h1 class="h1Tit">按标题浏览</h1>
            <p>
                <span><a href="javascript:void(0)">W</a></span>
                <span><a href="javascript:void(0)">X</a></span>
                <span><a href="javascript:void(0)">Y</a></span>
                <span><a href="javascript:void(0)">Z</a></span>
            </p>
        </div> -->
        <!------- 按标题浏览结束 ---------->
    </div>
    <!------- 第一部分内容结束 -------->
    <div class="oh mt10">
    	<!----- 第二部分左侧开始 -------->
    	<div class="secLeft">
            <!---- 免费资源结束 ----->
            <div id="free_pub"  class="resource">
            	<img src="${ctx}/images/loading.gif"/>
            </div> 
            <!---- 免费资源结束 ----->
            
            <!--- 热读资源开始 ----->
           <%--  <c:if test="${sessionScope.institution!=null }"> --%>
            <div id="hot_reading"  class="resource">
            	<img src="${ctx}/images/loading.gif"/>
            </div>       
           <%--  </c:if>       --%>  
            <!---- 热读资源结束 ----->
            
            <!--- 最新资源开始 ----->
                <div id="lastPubs" class="resource">
					<img src="${ctx}/images/loading.gif"/>
				</div>
            <!---- 最新资源结束 ----->
            
        </div>
        <!----- 第二部分左侧结束 -------->
        <!----- 第二部分右侧开始 -------->
        <div class="secRight">
        	<div class="serve mb50">
                 <div id="service_read" class="resource">
					<img src="${ctx}/images/loading.gif"/>					
				</div>
            </div>
          <div class="news mb50" id="news_read">
          <!-- 新闻 -->
          </div>
           	 <!-- 资源总数开始 -->
            <h1 class="indexHtit">
                	<span class="titFb"><a class="ico4" href="javascript:void(0)"><ingenta-tag:LanguageTag key="Global.Label.Total_Resources" sessionKey="lang" /></a></span>
            </h1>
            
            <div id="resource_all" class="mb50 oh" style="text-align: center;">
					<img src="${ctx}/images/loading.gif"/>					
			</div>
			<!-- 资源总数结束 -->
			<!-- 检索热词开始 -->
			<h1 class="indexHtit">
                	<span class="titFb"><a class="ico5" href="javascript:void(0)"><ingenta-tag:LanguageTag key="Global.Label.Search_Hot_Words" sessionKey="lang" /></a></span>
            </h1>
			<div  class="hotwords" id="hot_wordss" style="text-align: center;">
					<img src="${ctx}/images/loading.gif"/>
			</div>
        </div>
        <!----- 第二部分右侧结束 -------->
    </div>
</div>
	<jsp:include page="/pages/advertisement/form/listForIndex">
	<jsp:param name="position" value="2" />
	<jsp:param name="status" value="1" />
	</jsp:include>
<!--以下 提交查询Form 开始-->
		<c:if test="${sessionScope.selectType==1 }">
		<form:form action="${ctx}/index/searchLicense" method="post" commandName="form" name="formList" id="formList">
			<form:hidden path="searchsType" id="type1" />
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
			
			<input type="hidden" id="keywordCondition" name="keywordCondition" value="1"/>
		</form:form>
		</c:if>
		<c:if test="${sessionScope.selectType!=1 }">
		<form:form action="${ctx}/index/search" method="post" commandName="form" name="formList" id="formList">
			<form:hidden path="searchsType" id="type1" />
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
			
			<input type="hidden" id="keywordCondition" name="keywordCondition" value="1"/>
		</form:form>
		</c:if>
		<!--以上 提交查询Form 结束-->
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
</body>
</html>
