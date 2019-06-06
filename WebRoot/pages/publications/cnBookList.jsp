<%@page import="java.io.File"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv='X-UA-Compatible' content='IE=edge' />
<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
<%@ include file="/common/tools.jsp"%>
<%@ include file="/common/ico.jsp"%>
<script type="text/ecmascript">
			$(document).ready(function(e) {
				getHotWords();
				//getResourceAll();//资源总数
				getEditorRecommends();
 				getLastPubs();
 				getHotReading();
				$(".meaudown").mouseover(function(){
				  $(this).children("ul").css('display','block');
			  });
			    $(".meaudown").mouseleave(function(){
				  $(this).children("ul").css('display','none');
			  });
			})

			// 最新资源	
				function getLastPubs(){
					var parObj=$("#lastPubs");
					$.ajax({
						type : "POST",   
				        url: "${ctx}/pages/publications/lastPubsBook",
				        data:{
				        	isCn:"true",
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
			//编辑推荐
			function getEditorRecommends(){
				var parObj=$("#editorRecommends");
				$.ajax({
					type : "POST",   
			        url: "${ctx}/cnBookEditor",
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
		function getHotReading(){
				var parObj=$("#hot_reading");
				$.ajax({
					type : "POST",   
			        url: "${ctx}/cnBookHotReading",
			        success : function(response) { 
		             	$(parObj).html(response);
		             	$(parObj).css("text-align","left");
		            },  
		            error : function(response) {
		              	$(parObj).html(response);
		            }  
		      });
			}
			

			//获取资源数量列表
// 			function getResourceAll(){
// 				var parObj=$("#resource_all");
// 				$.ajax({
// 					type : "POST",   
// 			        url: "${ctx}/pages/publications/readingBook",
// 			        data: {			        	
// 			        	w:300
// 			        },
// 			        success : function(data) { 
// 		             	$(parObj).html(data);
// 		             	$(parObj).css("text-align","left");
// 		            },  
// 		            error : function(data) {
// 		              	$(parObj).html(data);
// 		            }  
// 		      });
// 			}
			
			//热词查询
			function getHotWords(){
				var parObj=$("#hot_wordss");
				$.ajax({
					type : "POST",   
			        url: "${ctx}/pages/index/cnHotWordss",
			        success : function(data) { 
		             	$(parObj).html(data);
		             	$(parObj).css("text-align","left");
		            },  
		            error : function(data) {
		              	$(parObj).html(data);
		            }  
		      });
			}
			
			function searchByCondition(type,value){
				if(type=="searchValue"){
					if(${sessionScope.selectType==1}){
			 			window.location.href="${ctx}/index/searchLicense?searchValue="+value+"&lcense="+1;
				 	}else{
				 		window.location.href="${ctx}/index/search?searchValue="+value;
				 	}
				}
			}

			function s(s) {
				location.href=encodeURI("${ctx}/index/advancedSearchSubmit?pubType=1&isCn=true&taxonomy=" + encodeURI(s));
			}
</script>
	</head>
	
	<body>
		<jsp:include page="/pages/header/headerData" />
	
		<div class="main">
			<div class="chineseLeft">
				<div class="titClassity">
					<p class="p1">
						<a href="javascript:void(0)"><img src="${ctx}/images/ico/ico4.png" width="16" height="16" class="vm" />
							<ingenta-tag:LanguageTag key="Pages.Cart.Type.EbookCn" sessionKey="lang" />
						</a>
					</p>
					<p class="p2"><ingenta-tag:LanguageTag key="Global.Label.Total_Resources" sessionKey="lang" />：<span>${count}</span></p>
				</div>
				<!-- 检索热词 -->
<!-- 	             <div class="mb20"> -->
<!-- 	            	<h1 class="h1Tit borBot"> -->
<%-- 	                	<span class="titFb"><a class="ico5" href="javascript:void(0)"><ingenta-tag:LanguageTag key="Global.Label.Search_Hot_Words" sessionKey="lang" /></a></span> --%>
<!-- 	                </h1> -->
<!-- 	                 <div class="hotwords oh" id="hot_wordss"> -->
<!-- 	               </div> -->
<!--            		 </div> -->
         		 <div class="leftClassity">
					<h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Global.Label.Subject_Categories" sessionKey="lang" /></h1>
					<ul>
						<c:forEach items="${subList}" var="s" varStatus="index">
							<c:if test="${sessionScope.lang=='zh_CN'}">
								<li><a href="${ctx}/index/advancedSearchSubmit?taxonomy=${s.code} ${s.name }&pubType=1&isCn=true" >${s.code} ${s.name}</a></li>
							</c:if>
							<c:if test="${sessionScope.lang!='zh_CN'}">
								<li><a href="${ctx}/index/advancedSearchSubmit?taxonomyEn=${s.code} ${s.nameEn }&pubType=1&isCn=true" >${s.code} ${s.nameEn} </a></li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
				
				<!-- 二级中文 - 合作出版社推荐 -->				
				<% if (new File(request.getSession().getAttribute("path") + "press" + File.separator + "press_chinese.html").exists()) { %>
					<div class="logoList mt30">
						<h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Global.Label.RelatedPublisher" sessionKey="lang" /></h1>
						<div id="press_chinese"></div>
					</div>
				<% } %>
			
			</div>

			<!-- 右侧内容开始 -->
			<div class="chineseRight">
				<div class="mb30 oh">
					<script type="text/javascript">
					<!--
					$(function() {
						$(".mb30").load("/upload/ad/ad_chinese.html");
					});
					//-->
					</script>
				</div>
				<div class="scrollDiv">
					<!-- 编辑推荐 -->
					<div id="editorRecommends"></div>
						
					<!-- 最新资源 -->
					<div id="lastPubs"></div>
					
					<!-- 热读资源 -->
					<div id="hot_reading"></div>
					<!-- 最新资源-->
					<div id="lastPubs"></div>
				</div>
			</div>
		</div>

		
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>