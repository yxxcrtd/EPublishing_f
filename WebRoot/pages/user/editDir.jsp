<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://fckeditor.net/tags-fckeditor" prefix="FCK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
		<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
		<%@ include file="/common/tools.jsp"%>
	<script type="text/javascript">
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
	<%@ include file="/common/ico.jsp"%></head>
	<body>
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--定义01 mainContainer 内容区开始-->
		<div class="mainContainer">
			<!--定义 0101 头部边框-->
			<div class="borderContainer">
				<!--定义 0102 左边内容区域 开始-->
				<div class="leftContainer">
					<div class="clear"></div>
					<div class="clear"></div>
					<div class="favouritebox">
						<c:if test="${form.id==null||form.id=='' }">
						<div class="favouritefont"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.EditDir.title1"/></div>
						</c:if>
						<c:if test="${form.id!=null&&form.id!='' }">
						<div class="favouritefont"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.EditDir.title2"/></div>
						</c:if>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
					<form:form action="editDirSubmit" commandName="form" id="form" method="post">
					<div class="accountbox">
						<div class="accountdetail">
							<table style="text-align:left" cellpadding="0"	cellspacing="0" border="0" width="100%">
								<tr class="ordertitle">
								<td><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.EditDir.Label.name"/></td>
								<td><form:input path="obj.code" /></td>
								</tr>
								<tr>
									<td><a class="a_gret" onclick="submit();" ><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.Submit"/></a></td>
								</tr>
					        </table>
				        </div>
					</div>
					<form:hidden path="obj.id" />
					<form:hidden path="id"/>
					</form:form>
				</div>
				<!--定义 0102 左边内容区域 结束-->

				<!--定义 0103 右边内容区域 开始-->
				<%@ include file="/pages/frame/left.jsp"%>
				<!--定义 0103 右边内容区域 结束-->

			</div>
		</div>
		<div class="boderBottom"></div>
		<!--定义01 mainContainer 内容区结束-->
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>
