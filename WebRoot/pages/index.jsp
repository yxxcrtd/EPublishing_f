<%@page import="java.io.File"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
		<meta http-equiv='X-UA-Compatible' content='IE=edge' />
		<%@ include file="/common/tools.jsp"%>
		<%@ include file="/common/ico.jsp"%>
	</head>

	<body>
		<!-- 所有页面引用的顶部 -->
		<jsp:include page="/pages/header/headerData" />

		<!-- 首页上的广告 -->
		<div class="banner" id="full-screen-slider">
			<div id="ad_index_top"></div>
		</div>

		<div class="main">
			<div class="oh">
				<!-- 学科分类 -->
				<div class="classify">
					<h1 class="h1Tit"><ingenta-tag:LanguageTag key="Global.Label.Subject_Categories" sessionKey="lang"/></h1>
					<div id="subject"></div>
				</div>
				
				<!-- 按标题浏览 -->
				<div class="titleClassify">
					<h1 class="h1Tit"><ingenta-tag:LanguageTag key="Global.Label.title" sessionKey="lang"/></h1>
					<p>
						<%  for (int i = 65; i < 91; i++) { char a = (char) i; %>
							<span><a href="./index/advancedSearchSubmit?prefixWord=<%=a%>"><%=a%></a></span>
						<% } %>
						<span><a href="./index/advancedSearchSubmit?prefixWord=0">0-9</a></span>
					</p>
				</div>
			</div>

			<div class="oh mt10">
				<div class="secLeft">
					<!-- 免费资源 -->
					<div class="resource"><jsp:include page="${ctx}/free" flush="true" /></div>
					
					<!-- 热读资源 -->
					<div class="resource">
						<h1 class="indexHtit"><span class="fl titFb"><a class="ico1"><ingenta-tag:LanguageTag key="Global.Label.Hot_Reading_Resources" sessionKey="lang"/></a></span></h1>
						<jsp:include page="${ctx}/indexHotReading" flush="true" />
					</div>
					
					<!-- 最新资源 -->
					<c:choose>
						<c:when test="${not empty insInfo}"><div id="lastPubs" class="resource"></div></c:when>
						<c:otherwise>
							<div class="resource">
								<h1 class="indexHtit">
									<span class="fl titFb"><a class="ico1"><ingenta-tag:LanguageTag key="Global.Label.New_Resources" sessionKey="lang"/></a></span>
									<span class="fr"><a href="${ctx}/index/advancedSearchSubmit?lcense=&subFlag=1&newFlag=true&sortFlag=desc">more >></a></span>
								</h1>
								<jsp:include page="${ctx}/indexNewest" flush="true" />
							</div>
						</c:otherwise>
					</c:choose>
					
					<!-- 最近阅读 -->
					<c:if test="${null != sessionScope.mainUser}"><div id="recently_read" class="resource"></div></c:if>
				</div>
				
				<div class="secRight">
		        	<!-- 服务 -->
		        	<div class="serve mb50">
						<h1 class="indexHtit">
							<span class="titFb"><a class="ico2" href="javascript:;"><ingenta-tag:LanguageTag key="Global.Label.Service" sessionKey="lang"/></a></span>
						</h1>
						<div id="service"></div>
		            </div>
		            
					<!-- 新闻 -->
					<c:if test="${1 == flag}">
						<div class="news mb50">
							<h1 class="indexHtit">
								<span class="fl titFb"><a class="ico3"><ingenta-tag:LanguageTag key="Global.Label.News" sessionKey="lang"/></a></span>
								<span class="fr mt10 mr5 newListA"><a href="javascript:;"><img src="images/ico/ico12.png" /></a></span>
							</h1>
							<div id="news"></div>
						</div>
					</c:if>
		          
					<!-- 资源总数 -->
					<c:choose>
						<c:when test="${sessionScope.lang == 'zh_CN'}"><div id="stat_zh_CN"></div></c:when>
						<c:otherwise><div id="stat_en_US"></div></c:otherwise>
					</c:choose>
					
					<!-- 检索热词 -->
					<h1 class="indexHtit">
						<span class="titFb"><a class="ico5"><ingenta-tag:LanguageTag key="Global.Label.Search_Hot_Words" sessionKey="lang" /></a></span>
		            </h1>
					<div class="hotwords" id="hotWords"></div>
	        	</div>
			</div>
		</div>
		
		<!-- 底部广告 -->
		<div id="ad_index_bottom"></div>
		
		<!-- 版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
		
		
		<!--以下 提交查询Form 开始-->
		<c:if test="${sessionScope.selectType==1}">
		<form:form action="${ctx}/index/searchLicense" method="post" commandName="form" name="formList" id="formList">
			<form:hidden path="searchsType" id="type1" />
			<form:hidden path="searchValue" id="searchValue2"/>
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
			<form:hidden path="searchValue" id="searchValue2"/>
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
		
		<script type="text/javascript">
		<!--
		$(function() {
			<c:if test="${not empty insInfo}">getLastPubs(5);</c:if>
			if (${null != sessionScope.mainUser}) { recentlyRead(4); }
			var realHotNum = 0;
			<c:if test="${null != sessionScope.institution}">
				var hotReading = $("#hot_reading div.block");
				realHotNum = hotReading?hotReading.length:0;
				if (realHotNum == 0) {
					$("#hot_reading").hide();
				}				
			</c:if>
		});
		
		// 获取最新资源
		function getLastPubs(val) {
			var parObj=$("#lastPubs");
			$.ajax({
				type : "POST",   
		        url: "${ctx}/pages/publications/lastPubs",
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
		
		// 最近阅读
		function recentlyRead(val) {
			var parObj = $("#recently_read");
			$.ajax({
				type : "POST",    
		        url : "${ctx}/pages/index/recentlyRead",
		        data : {
		        num : val      	
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
				if(type=="searchValue"){
					$("#searchValue2").val(value);
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
				<c:if test="${sessionScope.lang!='en_US'}">$("#taxonomy1").val(tvalue);</c:if>
				<c:if test="${sessionScope.lang!='zh_CN'}">$("#taxonomyEn1").val(tvalue);</c:if>
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
		//-->
		</script>
	</body>
</html>
