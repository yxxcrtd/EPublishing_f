<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
		<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
		<%@ include file="/common/tools.jsp"%>
	<%@ include file="/common/ico.jsp"%></head>
	<script type="text/javascript">
	
	function apply(){
		if(document.getElementById("code").value==""){
			art.dialog.tips("<ingenta-tag:LanguageTag key="Pages.Prompt.ISBN.NoFill" sessionKey="lang"/>",1,'error');
			document.getElementById("code").focus();
			return;
		}
		document.getElementById("form").submit();
	}
	
	</script>
	<body>
	<form:form action="pushAlerts" method="post" commandName="form" id="form">
	<c:if test="${form.msg!=null&&form.msg != ''}">
		<script type="text/javascript">
			alert('${form.msg}');
		</script>
	</c:if>
		<%@ include file="/pages/frame/header.jsp"%>
		<!--定义01 mainContainer 内容区开始-->
		<div class="mainContainer">
			<!--定义 0101 头部边框-->
			<div class="borderContainer">
				      <!--定义 0102 左边内容区域 开始-->
      <div class="leftContainer">
        <div class="listTitle"><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Subject.Title" sessionKey="lang" /></div>
        
        <div class="clear"></div>
      
        <!--列表开始-->
        <div class="formContainer">
          <ul>
            <li><span><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.ISBN" sessionKey="lang" />: <font style="color:#ff0000;">*</font></span>
            <p><form:textarea path="code" id="code"/></p></li>
          </ul>
        </div>
        <div class="clear"></div>
        <a class="a_gret" onclick="apply();"><ingenta-tag:LanguageTag key="Global.Button.Submit" sessionKey="lang" /></a>
      </div>
				<!--定义 0102 左边内容区域 结束-->

				<!--定义 0103 右边内容区域 开始-->
				<%@ include file="/pages/frame/left.jsp"%>
				<!--定义 0103 右边内容区域 结束-->

			</div>
		</div>
		<div class="boderBottom"></div>
		<!--定义01 mainContainer 内容区结束-->
		<%@ include file="/pages/frame/bottom.jsp"%>
		 <input type="hidden" name="alertsType" value="2"/>
		</form:form>
	</body>
</html>
