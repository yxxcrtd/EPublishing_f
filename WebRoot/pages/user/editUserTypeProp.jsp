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
	<script type="text/javascript" src="${ctx }/js/PopUpLayer.js"></script>
	<script type="text/javascript" src="js/jquery-1.8.2.js" ></script>
	<script type="text/javascript">
	
	function back0(){
		document.getElementById("form").action="viewUserType";
		document.getElementById("form").submit();
	}
	
	function save0(){
		if(document.getElementById("code").value==""||document.getElementById("key").value==""||document.getElementById("order").value=="")
		{
			alert("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypePropManage.Prompt.alert'/>");
			return;
		}else{
			document.getElementById("form").action="submitEditUserTypeProp";
			document.getElementById("form").submit();
		}
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
	function submit(){
		var id = $("#tid").val();
		var amount = $("#amount").val();
		var ft = /^(-?\d+)(\.\d+)?$/;
		if(!ft.test(amount)){
			alert("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Prompt.amount'/>");
			return ;
		}else{
		$.ajax({
			type : "POST",
			async : false,
			url : "${ctx}/pages/user/form/saveTransation",
			data : {
				id : id,
				amount : amount,
				r_ : new Date().getTime()
			},
			success : function(data) {
				var s = data.split(":");
				if (s[0] == "success") {
					alert(s[1]);
					window.location.reload(true);
	//				art.dialog.tips(s[1],1);
					//alert(s[1]);
					//window.location.reload(true);
				}else{
					alert(s[1]);
	//				art.dialog.tips(s[1],1,'error');
				}
			},
			error : function(data) {
				alert(data);
			}
		}); 
		}
	}
	</script>
	</head>
	<body>
	<jsp:include page="/pages/header/headerData" flush="true" />
	<form:form action="submitEditUserTypeProp" method="post" commandName="form" id="form">
		
		<!--定义01 mainContainer 内容区开始-->
		<div class="mainContainer">
			<!--定义 0101 头部边框-->
			<div class="borderContainer">
				<!--定义 0102 左边内容区域 开始-->
				<div class="leftContainer">
					<div class="clear"></div>
					<div class="clear"></div>
					<div class="accountbox">
						<div class="accountfont"></div>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
					<div class="accountbox">
						<div class="accountdetail">
							<table style="text-align:left" cellpadding="0"	cellspacing="0" border="0" width="100%">
								<tr>
								<td><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.code'/></td>
								<td><form:input type="text" name="code" id="code" path="obj.code" style="width:120px"/></td>
								</tr>
						        <tr>
						        <td><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.name'/></td>
						        <td><form:input type="text" name="key" id="key" path="obj.key" style="width:120px"/></td>
						        </tr>
						        <tr>
						        <td><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.status'/></td>
						        <td>
						        <form:select id="status" name="status" path="obj.status" style="margin-top:3px;width:120px;">
		        				<option value="1" <c:if test="${form.obj.status==1}">selected="selected"</c:if>><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.status1'/></option>
		        				<option value="2" <c:if test="${form.obj.status==2}">selected="selected"</c:if>><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.status2'/></option>
		        				</form:select>
		        				</td>
						        </tr>
						        <tr>
						        <td><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.order'/></td>
						        
						         <td><form:input type="text" name="order" id="order" path="obj.order" onkeyup="value=value.replace(/[^0-9]/g,'')" onpaste="value=value.replace(/[^0-9]/g,'')" oncontextmenu = "value=value.replace(/[^0-9]/g,'')" style="width:120px"/></td>
						        </tr>
						        <tr>
						        <td><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.style'/></td>
						         <td>
						        <form:select id="display" name="display" path="obj.display" style="margin-top:3px;width:120px;">
		        				<option value="text" <c:if test="${form.obj.display=='text'}">selected="selected"</c:if>>text</option>
		        				<option value="select" <c:if test="${form.obj.display=='select'}">selected="selected"</c:if>>select</option>
		        				</form:select>
		        				</td>
						        </tr>
						        <tr>
						        <td><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.sourse'/></td>
						        <td>
						        <form:select id="stype" name="stype" path="obj.stype" style="margin-top:3px;width:120px;">
		        				<option value="1" <c:if test="${form.obj.stype=='1'}">selected="selected"</c:if>><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.sourse1'/></option>
		        				<option value="2" <c:if test="${form.obj.stype=='2'}">selected="selected"</c:if>><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.sourse2'/></option>
		        				</form:select>
		        				</td>
						        </tr>
						        <tr>
						        <td><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.svalue'/></td>
						        <td><form:input type="text" name="svalue" id="svalue" path="obj.svalue" style="width:120px"/></td>
						        </tr>
						        <tr>
						        <td><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.must'/></td>
						        <td>
						        <form:select id="must" name="must" path="obj.must" style="margin-top:3px;width:120px;">
		        				<option value="1" <c:if test="${form.obj.must==1}">selected="selected"</c:if> ><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.must2'/></option>
		        				<option value="2" <c:if test="${form.obj.must==2}">selected="selected"</c:if> ><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.must1'/></option>
		        				</form:select>
		        				</td>
						        </tr>
						        <tr>
						        <td>
						        <input type="button" class="bton02" id="edit" style="width:40px" value="<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.Button.save'/>" onclick="save0()"/>&nbsp;&nbsp;
								<input type="button" class="bton02" id="back" style="width:40px" value="<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserTypeManage.Button.quxiao'/>" onclick="back0()" />
								</td>
						        </tr>
						        </table>
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
		<form:hidden path="id"/>
		<form:hidden path="pid"/>
		</form:form>
	</body>
</html>
