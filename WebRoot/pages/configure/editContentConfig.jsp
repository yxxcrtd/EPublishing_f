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
		<script type="text/javascript" src="${ctx }/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" src="${ctx }/ueditor/ueditor.all.js"></script>
		<script type="text/javascript">
		var ue;
		$(document).ready(function(){
			if('${form.msg}'!='null'&&'${form.msg}'!=''){
				alert('${form.msg}');
				if('${form.isSuccess}'=='true'){
					window.location.href="${ctx}/pages/configuration/form/manager";
				}
			}
		});
		function submit(){
			document.getElementById("form").submit();
		}
		</script>
	</head>
	<body>
	<div class="big">
		<!--以下top state -->
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--以上top end -->
		<!--以下中间内容块开始-->
		  <div class="main">
		  	<div class="login">
		     <h1>
		     	<ingenta-tag:LanguageTag sessionKey="lang" key="${form.obj.key }"/><ingenta-tag:LanguageTag key="Page.Configure.Lable.Infor.Manage" sessionKey="lang" />
		     </h1>
		     <div class="t_list">
		     	<p>
		     		<span>
		     			<a class="a_gret" onclick="submit();"><ingenta-tag:LanguageTag key="Global.Button.Save" sessionKey="lang" /></a>
		     		</span>
		     	</p>
		     </div>
		  <div class="page_table">
		        <form:form action="editSubmit" commandName="form" id="form" method="post">
			        <form:hidden path="obj.code" />
					<form:hidden path="obj.val" />
					<form:hidden path="obj.type"/>
					<form:hidden path="yyyy" />
					<form:hidden path="obj.key" />
					<form:hidden path="obj.id" />
					<form:hidden path="id"/>
					<c:set var="re"><c:if test="${form.obj.id=='content1' }">site_info</c:if><c:if test="${form.obj.id!='content1' }">help</c:if></c:set>
					<form:hidden path="mid" value="${re }"/>
		           <table width="100%" border="0" cellspacing="0" cellpadding="0">
		           <thead>
					  <tr>
					    <td>&nbsp;</td>
					    <td>
							<script name="obj.content" id="content" type="text/plain" style="width:100%;height:600px">${form.content}</script>
							 <script type="text/javascript">
								//实例化编辑器
							    ue = UE.getEditor('content',{
							    	//关闭字数统计
						            wordCount:false,
						            //关闭elementPath
						            elementPathEnabled:false,
						            //中英文
						            lang:<c:if test="${sessionScope.lang==null||sessionScope.lang=='zh_CN'}">'zh-cn'</c:if><c:if test="${sessionScope.lang=='en_US'}">'en'</c:if>
							    });
							</script>
						</td>
					    </tr>
					    </thead>
					</table>
		         </form:form>
		        </div>
		    </div>
		    <!--左侧内容结束-->
		    <!--右侧菜单开始 -->
		    
		   <%--  <jsp:include page="/pages/menu?mid=${re }" flush="true"/> --%>
		    <!--右侧菜单结束-->
		  </div>
		  <!--以上中间内容块结束-->
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
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</div>	
	</body>
</html>
