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
<script type="text/javascript" src="${ctx }/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="${ctx }/js/SimpleCanleder.js"></script>
<link href="${ctx}/css/SimpleCanleder.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		$(document).ready(function() {
			$(".test").simpleCanleder();  
		});
		function queryList() {
			//document.getElementById("form").action="";
			//document.getElementById("page").value = "0";
			document.getElementById("form").submit();
		}
		function queryList1() {
			var startMonth = $("#startMonth").val();		
			var endMonth = $("#endMonth").val();
			if(parseInt(startMonth,10)>parseInt(endMonth,10)){
				art.dialog.tips('<ingenta-tag:LanguageTag sessionKey="lang" key="Page.User.accesslog.Prompt.month" />',1,'error');
			}else{
				$("#page").val(0);
				
				var actionArr=["managerAccess","managerToc","managerRefused","managerDownload","managerSearch"];
				var action=actionArr[$("#btn").val()-1];
				$("#form").attr("action",action);
				$("#form").submit();
			}
			
		}
		
		function downloadXls(){
			$("#pubtype").val(2);
			$("#languagetype").val(2);
			/* document.getElementById("page").value="0"; */
			var str=${tabIndex}
			if(str==1){//全文
				$("#btn").val(1);
				document.getElementById("form").action="downloadAccess";
				document.getElementById("form").submit();
			}else if(str==2){//toc
				$("#btn").val(2);
				document.getElementById("form").action="downloadToc";
				document.getElementById("form").submit();
			}else if(str==3){//拒访
				$("#btn").val(3);
				document.getElementById("form").action="downloadRefused";
				document.getElementById("form").submit();
			}else if(str==4){//下载
				$("#btn").val(4);
				document.getElementById("form").action="downloadDownload";
				document.getElementById("form").submit();
			}else if(str==5){//搜索
				$("#btn").val(5);
				document.getElementById("form").action="downloadSearch";
				document.getElementById("form").submit();
			}
		}

		function addToCart(pid, ki, rid) {
			var price = $("#price_" + pid).val();
			$
					.ajax({
						type : "POST",
						url : "${ctx}/pages/cart/form/add",
						data : {
							pubId : pid,
							priceId : price,
							kind : ki,
							recId : rid,
							r_ : new Date().getTime()
						},
						success : function(data) {
							var s = data.split(":");
							if (s[0] == "success") {
								art.dialog.tips(s[1], 1);//location.reload();
								$("#add_" + rid).css("display", "none");
								$("#cartCount").html("[" + s[2] + "]");
								$("#price_" + rid).css("display", "none");
								$("#suspend_" + rid).css("display", "none");
								$("#status_" + rid)
										.html(
												"<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.MyRecommend.Label.isOrder3'/>");
							} else {
								art.dialog.tips(s[1], 1, 'error');
							}
						},
						error : function(data) {
							art.dialog.tips(data, 1, 'error');
						}
					});
		}

		function popSuspend(rid) {
			art.dialog.open(
							"${ctx}/pages/user/particulars.jsp?rid=" + rid,
							{
								title : "<ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend.particulars" sessionKey="lang"/>",
								top : 100,
								width : 700,
								height : 200,
								lock : true
							});
		}

		function showMessage(title, data) {
			art.dialog({
				title : title,
				content : data.replace("[dyh]", "'").replace("[syh]", '"'),
				lock : true,
				width : 500
			});
		}
		/* 
		function loadFunction(){
			$.ajax({
				type : "POST",
				url : "${ctx}/pages/user/form/myrecommend/",
				data:{
					type: 1
				},
				success : function(response, stutas, xhr) {
					$("#cnPubTable1").html(response);
				}
			});
		} */
		
		function initFunction(){
		//	reset();
			$("#type").val(1);
			$("#isCn").val(true);
		}
		
		function reset(){
			$("#isOrder").attr("checked","checked");
			$("#sort").attr("checked","checked");
			$("#pubTitle").attr("value","");
			$("#pubCode").attr("value","");
		}
		$(function(){
			
			$("#but1").click(function(){
				$("#startyear").val("");
				$("#endtyear").val("");
				$("#pubtype").val(2);
				$("#languagetype").val(2);
				$("#btn").val(1);
				document.getElementById("form").action="managerAccess";
				queryList();
			});
			$("#but2").click(function(){
				$("#startyear").val("");
				$("#endtyear").val("");
				$("#pubtype").val(2);
				$("#languagetype").val(2);
				$("#btn").val(2);
				document.getElementById("form").action="managerToc";
				queryList();
			});
			$("#but3").click(function(){
				$("#startyear").val("");
				$("#endtyear").val("");
				$("#pubtype").val(2);
				$("#languagetype").val(2);
				$("#btn").val(3);
				document.getElementById("form").action="managerRefused";
				queryList();
			});
			$("#but4").click(function(){
				$("#startyear").val("");
				$("#endtyear").val("");
				$("#pubtype").val(2);
				$("#languagetype").val(2);
				$("#btn").val(4);
				document.getElementById("form").action="managerDownload";
				queryList();
			});
			$("#but5").click(function(){
				$("#startyear").val("");
				$("#endtyear").val("");
				$("#pubtype").val(2);
				$("#languagetype").val(2);
				$("#btn").val(5);
				document.getElementById("form").action="managerSearch";
				queryList();
			});
		
		});
		
		function dividePage(targetPage){
			if(targetPage<0){return;}
			document.getElementById("form").action="?pageCount="+${form.pageCount}+"&curpage="+targetPage+"&btn="+${tabIndex};
			document.getElementById("form").submit();
		}
		
		function GO(obj){
			document.getElementById("form").action="${ctx}/pages/user/form/accesslogNew?pageCount="+$(obj).val();
			document.getElementById("form").submit();
		}
	</script>
</head>
	<body>
	<!--定义 0101 头部边框-->
	<jsp:include page="/pages/header/headerData" flush="true" />
	<div class="main personMain">
		<!--定义 0102 左边内容区域 开始-->
		<jsp:include page="/pages/menu?mid=JournalEn&type=2" flush="true" />
		<!--定义 0102 左边内容区域 结束-->
		
		<!--定义 0103 右边内容区域 开始-->
			<div class="perRight">
				<div class="StabedPanels" >
				 <ul class="oh">
	                <li class="Stab<c:if test="${tabIndex==1}"> StabSeleted</c:if> Vtab" id="but1"><ingenta-tag:LanguageTag key="Pages.User.Full" sessionKey="lang" /></li>
	                <li class="Stab<c:if test="${tabIndex==2}"> StabSeleted</c:if> Vtab" id="but2"><ingenta-tag:LanguageTag key="Page.User.Toc.Statistics" sessionKey="lang" /></li>
	                <li class="Stab<c:if test="${tabIndex==3}"> StabSeleted</c:if> Vtab" id="but3"><ingenta-tag:LanguageTag key="Page.User.Refused.Statistics" sessionKey="lang" /></li>
	                <li class="Stab<c:if test="${tabIndex==4}"> StabSeleted</c:if> Vtab" id="but4"><ingenta-tag:LanguageTag key="Page.User.Download.Statistics" sessionKey="lang" /></li>
	                <li class="Stab<c:if test="${tabIndex==5}"> StabSeleted</c:if> Vtab" id="but5"><ingenta-tag:LanguageTag key="Pages.User.Instaccount.Label.searchCount" sessionKey="lang" /></li>
            	</ul>
				
				<div class="StabContent ScontentSelected">
				<form:form action="managerAccess" method="post" commandName="form" id="form" >
					<input type="hidden" id="btn" name="btn" value="${tabIndex}"/> 
					<input type="hidden" id="pubtype" name="pubtype" value="${form.pubType}"/>
					<input type="hidden" id="languagetype" name="languagetype" value="${form.languagetype}"/>
					
					<p class="blockP">
					 
                 <span class="w80 tr"><ingenta-tag:LanguageTag sessionKey="lang " key="Pages.User.trialManage.Table.Label.StartTime"/>：</span>
              
               	<span class="w160">
                	 	<form:input readonly="true" class="test" id="startyear" path="startyear" style="width:80px;"/>
				</span> 
                 <span class="w80 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.trialManage.Table.Label.EndTime" />：</span>
                 <span class="w160">
                	 	<form:input readonly="true"  class="test" id="endtyear" path="endtyear" style="width:80px;"/>
					  </span> 
					</p>
					
					<p class="mb15">
            		  <span><a href="javascript:void(0)" class="orgingB" onclick="queryList1();"><ingenta-tag:LanguageTag sessionKey='lang' key='Global.Button.Search'/></a></span>
                	  <span><a href="javascript:void(0)" class="orgingB ml10" onclick="downloadXls();"><ingenta-tag:LanguageTag key="Pages.view.Label.Download" sessionKey="lang" /></a></span>
           		   </p>
					<table width="99%" border="0" cellspacing="0" cellpadding="0" class="curTable">
						<tr>
			            	<td width="250" align="center">Title</td>
			            	<c:if test="${form.pubType=='1'}">
			            	<td align="center">ISBN</td>
			            	</c:if>
			            	<c:if test="${form.pubType=='2' }">
			            	<td align="center">ISSN</td>
			            	</c:if>
			                <td width="200" align="center">Total</td>
	                
	           		    </tr>
	           		       <c:forEach items="${list}" var="o" varStatus="index">
	            	  	<tr>
			                <td align="center">${o.title}</td>
			                <c:if test="${o.type==1||o.type==3}">
			                <td align="center">${o.isbn}</td>
			                </c:if>
			                <c:if test="${o.type!=1&&o.type!=3}">
			                <td align="center">${o.issn}</td>
			                </c:if>
			                <td align="center">
			                <c:if test="${o.count==null }">
				    		 0
				  			</c:if>
				    		<c:if test="${o.count!=null }">
				    	    ${o.count}
				   			</c:if>
			                </td>
	                    </tr>
	                    </c:forEach>
             		 <!--分页条开始-->
             		  <%-- <tfoot>
          				<tr style="border-left-style: hidden;border-right-style: hidden;border-bottom-style: hidden;">
							<td colspan="7" class="f_tda"><ingenta-tag-v3:SplitTag
								first_ico="${ctx }/images/ico_left1.gif"
								last_ico="${ctx }/images/ico_right1.gif"
								prev_ico="${ctx }/images/ico_left.gif"
								next_ico="${ctx }/images/ico_right.gif" method="post"
								formName="form" pageCount="${form.pageCount}"
								count="${form.count}" page="${form.curpage}" url="${form.url}"
								i18n="${sessionScope.lang}" /></td>
					   </tr>
					  </tfoot> --%>	
          			 <!--分页条结束-->   
				
           			</table>
           			
					</form:form>
					<jsp:include page="../pageTag/pageTag.jsp">
			          <jsp:param value="${form }" name="form"/>
	                </jsp:include>
	        	</div>
	        	
	        </div>
			</div>
			<!--定义 0103 右边内容区域 结束-->
				
		</div>
		<!--以下 提交查询Form 开始-->
		<form:form action="${form.url}" method="post" modelAttribute="form" commandName="form" name="formList" id="formList">
			<form:hidden path="searchsType" id="type1"/>
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
		</form:form>
		<!--以上 提交查询Form 结束-->
		<!--定义01 mainContainer 内容区结束-->
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>
