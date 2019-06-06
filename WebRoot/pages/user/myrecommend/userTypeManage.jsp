<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
		<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
		<%@ include file="/common/tools.jsp"%>
		<script type="text/javascript" src="${ctx }/js/PopUpLayer.js"></script>
	<script type="text/javascript">
	</script>
	<!-- 弹出层样式  开始-->
<style type="text/css">
#windownbg {
	display: none;
	position: absolute;
	width: 100%;
	height: 100%;
	background: #000;
	top: 0;
	left: 0;
}

#windown-box {
	position: fixed;
	_position: absolute;
	border: 5px solid #E9F3FD;
	background: #FFF;
	text-align: left;
}

#windown-title {
	position: relative;
	height: 30px;
	border: 1px solid #A6C9E1;
	overflow: hidden;
}

#windown-title h2 {
	position: relative;
	left: 10px;
	top: 5px;
	font-size: 14px;
	color: #666;
}

#windown-close {
	position: absolute;
	right: 10px;
	top: 8px;
	width: 10px;
	height: 16px;
	text-indent: -10em;
	overflow: hidden;
	cursor: pointer;
}

#windown-content-border {
	position: relative;
	top: -1px;
	border: 1px solid #A6C9E1;
	padding: 5px 0 5px 5px;
}

#windown-content img,#windown-content iframe {
	display: block;
}

#windown-content .loading {
	position: absolute;
	left: 50%;
	top: 50%;
	margin-left: -8px;
	margin-top: -8px;
}
</style>
<script type="text/javascript">
	//******************************弹出层--开始*********************************//
	/*
	 *弹出本页指定ID的内容于窗口
	 *id 指定的元素的id
	 *title: window弹出窗的标题
	 *width: 窗口的宽,height:窗口的高
	 */
	 function edittype(id)
	{
			document.getElementById("form").action="editUserType";
			document.getElementById("pid").value=id;
			document.getElementById("form").submit();
	}
	function viewtype(id)
	{
			document.getElementById("form").action="viewUserType";
			document.getElementById("pid").value=id;
			document.getElementById("form").submit();
	}
	 
	function showTipsWindown(title, id, width, height) {
		tipsWindown(title, "id:" + id, width, height, "true", "", "true", id);
	}
	function confirmTerm() {
		parent.closeWindown();
	}
	function viewPopTips(id,name) {
		$("#tid").val(id);
		showTipsWindown(name,'viewContent', 330, 155);
	}
</script>
<!--弹出层样式 结束  -->
	<%@ include file="/common/ico.jsp"%></head>
	<body>
	<jsp:include page="/pages/header/headerData" flush="true" />
	<form:form action="editUserType" method="post" commandName="form" id="form">
	<c:if test="${form.msg!=null&&form.msg != ''}">
		<script language="javascript">
			art.dialog.tips('${form.msg}');
		</script>
	</c:if>
		
		<!--定义01 mainContainer 内容区开始-->
		<div class="mainContainer">
			<!--定义 0101 头部边框-->
			<div class="borderContainer">
				<!--定义 0102 左边内容区域 开始-->
				<div class="leftContainer">
					<div class="clear"></div>
					<div class="clear"></div>
					<div class="accountbox">
						<div class="accountfont"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.title'/></div>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
					<div class="accountbox">
						<div class="accountdetail">
							<table style="text-align:left;table-layout: fixed;WORD-BREAK: keep-all; WORD-WRAP: break-word;" cellpadding="0"	cellspacing="0">
						        <tr class="ordertitle">
						        <td style="text-align:left;width:30%"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.Table.Label.code'/></td>
						        <td style="text-align:left;width:40%"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.Table.Label.name'/></td>
						        <td style="text-align:center;width:30%"><ingenta-tag:LanguageTag sessionKey='lang' key='Global.Prompt.Operating'/></td>
						        </tr>
						       
						        <c:forEach items="${list}" var="t">
						         <tr>
								        <td>${t.code}</td>
								        <td>${t.name}</td>
								        <td>
								        <a id="editType" class="a_gret" onclick="edittype('${t.id}')"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.Button.restType'/></a>
								        </td>
								        <td>
								        <a id="view" class="a_gret" onclick="viewtype('${t.id}')"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.Button.view'/></a>
							        	</td>
							        	 </tr>
						        </c:forEach>
						       
					        </table>
				            <!--移动窗口开始-->
							<div style="display:none;">
							<div id="viewContent">
								<div class="mainlist">
									<!--内容开始-->
									<ul>
									<table style="text-align:left" cellpadding="0"	cellspacing="0" border="0" width="100%">
								        <tr class="ordercontent">
									        <td colspan="2"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.OrgManage.view.amount"/>&nbsp;<input type="text" id="tt" onkeyup="$('#amount').val(this.value)"/></td>
								        </tr>
								        <tr class="ordercontent">
									        <td colspan="2"><a id="submit1" onclick="submit();" class="a_gret"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.Submit"/></a>
									        &nbsp;&nbsp;<a id="confirmTerm"  onclick="confirmTerm();" class="a_gret"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.Close"/></a></td>
								        </tr>
							        </table>
									</ul>
									<!--内容结束-->
								</div>
							</div>
							<!--simTestContent end-->
						</div>
						<!--移动窗口结束-->
				        </div>
					</div>
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
		<form:hidden path="pid"/>
		</form:form>
	</body>
</html>
