<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv='X-UA-Compatible' content='IE=edge'/>
    <title>CNPIEC eReading: Introducing CNPIEC eReading</title>
    <%@ include file="/common/tools.jsp"%>
<script language="javascript">
function apply(){
	var email = document.getElementById("email").value;
	
	var uid = document.getElementById("uid").value;
	if(uid.replace(/\s+/g,"")==""&&email.replace(/\s+/g,"")==""){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.FindPwd.Prompt.info'/>",1,'error');
		return;
	}else if(email.replace(/\s+/g,"")!=""){
		
	  	var myreg = /^([a-zA-Z0-9]+[_|\-|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
           if(!myreg.test(email)){
                art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.FindPwd.Prompt.tip'/>\n\n<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.FindPwd.Prompt.effemail'/>",1,'error');
                //myreg.focus();
             	return;
        	}
	
	}
		$.post("${ctx}/pages/user/form/findPwdSubmit", {uid:document.getElementById("uid").value,email:document.getElementById("email").value},
			function (data, textStatus){
				art.dialog.tips(data,1,'error');
			}
		);
		
}
</script>
<%@ include file="/common/ico.jsp"%></head>
<body>

  <jsp:include page="/pages/header/headerData" flush="true" />
  <!--定义01 mainContainer 内容区开始-->
  <div class="main personMain h700">
    <!--定义 0101 头部边框-->
    
      <!--定义 0102 左边内容区域 开始-->

        <h1 class="botH1"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.FindPwd.title'/></h1>
      

		<%-- <div class="zhuce_xinxi" style="width:80%; margin-left:100px;">
		<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.FindPwd.Label.title.detail'/> --%>
		
		<p><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.FindPwd.Alert1'/></p>
    	<p class="red"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.FindPwd.Alert2'/></p>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="regTable mt10">				
				<%-- <tr>
					<td class="tleft"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.FindPwd.Label.userName'/>：&nbsp;
					</td>
					<td><input type="text" id="uid"/></td>
				</tr> --%>
				<input type="hidden" id="uid"/>
				<tr>
					<td width="40" align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.FindPwd.Label.email'/>:&nbsp;
					</td>
					<td colspan="3"><input type="text" id="email"/><span class="red"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.FindPwd.Alert3'/></span></td>
				</tr>	
				<tr>
					<td align="right">&nbsp;</td>
					<td  colspan="3">
						 <p class="mt10"><a href="javascript:void(0)" class="orgingA" onclick="apply();"><ingenta-tag:LanguageTag sessionKey='lang' key='Global.Button.Submit'/></a></p>
					</td>
				</tr>		
			</table>
		<!-- </div> -->
        
       

      <!--定义 0102 左边内容区域 结束-->
      

  </div>
  
  
  
  
  
  
  
  
  <div class="boderBottom"></div>
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
  <input type="hidden" name="userType" value="1"/><!-- 用户类型为1：个人用户 -->

</body>
</html>
