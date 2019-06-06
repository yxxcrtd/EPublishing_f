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
						<div class="favouritefont"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.EditConfigure.title1'/>基础信息增加</div>
						</c:if>
						<c:if test="${form.id!=null&&form.id!='' }">
						<div class="favouritefont"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.EditConfigure.title2'/>基础信息修改</div>
						</c:if>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
					<form:form action="editSubmit" commandName="form" id="form" method="post">
					<div class="accountbox">
						<div class="accountdetail">
							<table align="left" cellpadding="0"	cellspacing="0" width="100%">
								<tr class="favouritecontent">
								<td>配置编码：</td>
								<td><form:input path="obj.code" /></td>
								</tr>
								<tr class="favouritecontent">
								<td>配置名称：</td>
								<td><form:input path="obj.key" /></td>
								</tr>
								<tr class="favouritecontent">
								<td>配置取值：</td>
								<td><form:input path="obj.val" /></td>
								</tr>
								<tr class="favouritecontent">
								<td>配置类型：</td>
								<td><form:select path="obj.type">
									<form:option value="1">基础参数</form:option>
									<form:option value="2">基础显示</form:option>
								</form:select></td>
								</tr>
								<tr class="favouritecontent">
								<td>基础详情：</td>
								<td>
									<form:hidden path="content"/>
        	<FCK:editor id="content" width="100%" height="450"
				fontNames="宋体;黑体;隶书;楷体_GB2312;Arial;Comic Sans MS;Courier 
New;Tahoma;Times New Roman;Verdana"
				imageBrowserURL="/FCKeditor-2.3/FCKeditor/editor/filemanager/browser/default/browser.html?
Type=Image&Connector=connectors/jsp/connector"
				linkBrowserURL="/FCKeditor-2.3/FCKeditor/editor/filemanager/browser/default/browser.html?
Connector=connectors/jsp/connector"
				flashBrowserURL="/FCKeditor-2.3/FCKeditor/editor/filemanager/browser/default/browser.html?
Type=Flash&Connector=connectors/jsp/connector"
				imageUploadURL="/FCKeditor-2.3/FCKeditor/editor/filemanager/upload/simpleuploader?Type=Image"
				linkUploadURL="/FCKeditor-2.3/FCKeditor/editor/filemanager/upload/simpleuploader?Type=File"
				flashUploadURL="/FCKeditor-2.3/FCKeditor/editor/filemanager/upload/simpleuploader?Type=Flash">

			</FCK:editor>
			<form:hidden path="obj.content"/>
								</td>
								</tr>
								<tr>
									<td><a href="javascript:void(0)" onclick="submit();">提交</a></td>
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
