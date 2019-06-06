<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv='X-UA-Compatible' content='IE=edge'/>
    <title>CNPIEC eReading: Introducing CNPIEC eReading</title>
     <%@ include file="/common/tools.jsp"%>
	<script type="text/javascript" src="${ctx}/js/checkPwd.js"></script>
<script language="javascript">


function apply(){
	/*
	 var userName = document.getElementById("userName").value;
	if(userName.replace(/\s+/g,"")==""){
		$("#tips1").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Prompt.name'/>");
		document.getElementById("userName").focus();
		return;
	}
	*/
	var uid = document.getElementById("uid").value;
	if(uid.replace(/\s+/g,"")==""){
		$("#tips4").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Prompt.userName'/>",1,'error');
		document.getElementById("uid").focus();
		return;
	}
	var email = document.getElementById("email").value;
	if(email.replace(/\s+/g,"")==""){
		$("#tips4").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Prompt.email'/>");
		document.getElementById("email").focus();
		return;
	}else{
		
	  	var myreg = /^([a-zA-Z0-9]+[\-|_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
           if(!myreg.test(email)){
        	   $("#tips4").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Prompt.effEmail'/>");
                myreg.focus();
             	return false;
        	}
	
	}
	/* if(document.getElementById("email").value !=  document.getElementById("emailCheck").value){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Prompt.twoEmail'/>",1,'error');
		return;
	} */
	/* if(document.getElementById("country").value=="" || document.getElementById("country").value==0){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Prompt.country'/>",1,'error');
		document.getElementById("country").focus();
		return;
	} */
	
	var pwd = document.getElementById("pwd").value;
	if(pwd.replace(/\s+/g,"")==""){
		 $("#tips4").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Prompt.pwd'/>",1,'error');
		document.getElementById("pwd").focus();
		return;
	}
	var userDepartment = document.getElementById("userDepartment").value;
	if(userDepartment.replace(/\s+/g,"")!=""){
	  	var myreg = /^[a-zA-Z\u4e00-\u9fa5]+$/;
        if(!myreg.test(userDepartment)){
          $("#tips4").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.department'/>");
          document.getElementById("userDepartment").focus();
          return false;
        }
        if(userDepartment.length>40){
         	$("#tips4").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.department'/>");
          	document.getElementById("userDepartment").focus();
        	return false;
        }
	
	}
	var userTitle = document.getElementById("userTitle").value;
	if(userTitle.replace(/\s+/g,"")!=""){
	  	var myreg = /^[a-zA-Z\u4e00-\u9fa5]+$/;
        if(!myreg.test(userTitle)){
          $("#tips4").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.post'/>");
          document.getElementById("userTitle").focus();
          return false;
        }
        if(userTitle.length>40){
         	$("#tips4").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.post'/>");
          	document.getElementById("userTitle").focus();
        	return false;
        }
	
	}
	var userTelephone = document.getElementById("userTelephone").value;
	if(userTelephone.replace(/\s+/g,"")!=""){
	  	var myreg = /(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)|(13\d{9}$)|(15[0135-9]\d{8}$)|(18[267]\d{8}$)/;
        if(!myreg.test(userTelephone)){
          $("#tips4").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.tele'/>");
          document.getElementById("userTelephone").focus();
          return false;
        }
        if(userTelephone.length>40){
         	$("#tips4").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.tele'/>");
          	document.getElementById("userTelephone").focus();
        	return false;
        }
	
	}
/* 	if(document.getElementById("pwd").value !=  document.getElementById("pwdCheck").value){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Prompt.twoPwd'/>",1,'error');
		return;
	} */
	if(!document.getElementById("agree").checked){ 
		 $("#tips4").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Prompt.agree'/>",1,'error');
	    return;
	};
	$.ajax({
 			type : "POST",  
 			url: "${ctx}/pages/user/form/registerSubmitNew",
 			data: $("#form").serialize(),
		success : function(data) {  
		    var s = data.split("::");
		    if(s[0]=="success"){
		    	art.dialog.tips(s[1],1,'success',20);
		    	location="${ctx}/";
		    }else if(s[0]=="error"){
		    	$("#tips4").show().html(s[1],1,'error');
		    }			    
		},  
		error : function(data) { 
			var s = data.split("::");	
		    if(s[0]=="success"){
		    	art.dialog.tips(s[1],1,'success',20);
		    	location="${ctx}/";
		    }else if(s[0]=="error"){
		    	 $("#tips4").show().html(s[1],1,'error');
		    }	
		}  
	});
}
</script>
<c:if test="${form.msg!=null&&form.msg != ''}">
	<script language="javascript">
	 $("#tips1").show().html('${form.msg}');
	</script>
</c:if>
<%@ include file="/common/ico.jsp"%></head>
<body>
	<jsp:include page="/pages/header/headerData" flush="true" />
	<form:form id="form" commandName="form" action="registerSubmitNew">
		<div class="main">
			<div class="newLogin">
		    	<h1><ingenta-tag:LanguageTag key="Pages.User.Register.Login" sessionKey="lang" /></h1>
		        <div class="oh">
		        	<div class="newLogoFl">
		            	<p><ingenta-tag:LanguageTag key="Pages.User.Registration.Label.prompt" sessionKey="lang" /></p>
		            	<p style="margin-top: 10px;"><div id="tips4"></div></P>
		                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="regTable mt10">
		                  <tr>
		                    <td width="120" align="right">
		                    	<span class="red">*</span> <ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Label.userName'/>：&nbsp;
		                    </td>
		                    <td colspan="3">
		                    	<form:input path="account.uid" id="uid" style="vertical-align:middle" /> 
		                    	<span class="red">
									<ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.Alert.UserName'/>
								</span>
		                    </td>
		                  </tr>
		                  	 <c:forEach items="${list}" var="c" varStatus="index">
				            	<c:if test="${c.display!='select'&& (c.code=='email')}">
				            		<input type="hidden" name="typePropIds" value="${c.id}"/>
									<tr>
										<td align="right"><span class="red">*</span><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：&nbsp;</td>
										<td colspan="3">
											<input type="text" id="${c.code}" name="propsValue" value="" style="vertical-align:middle"/><c:if test="${c.must!=1}"> </c:if>
											<span class="red">
												<ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.Alert.Email'/>
											</span>
										</td>
									</tr>
				            	</c:if>
							 </c:forEach> 
		                  <tr>
		                    <td width="120" align="right"><span class="red">*</span><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Label.pwd'/>：&nbsp;</td>
		                    <td colspan="3">
		                    	<form:password path="account.pwd" id="pwd" onkeyup="chkpwd(this)" style="vertical-align:middle"/>
								<span class="red">
									<ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.Alert.PassWord'/>
								</span>
							</td>
		                  </tr>
		                  <tr>
		                    <td align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Label.safe'/>：&nbsp;</td>
		                    <td colspan="3"><p id="chkResult" style="margin-top:7px;"></p></td>
		                  </tr>
		                  <tr>
		                    <td align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Label.name'/>：&nbsp;</td>
		                    <td colspan="3">
		                    	<input type="text" id="userName" name="userName" value=""/> 
		                    	<ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.Alert.RealName'/>
		                    </td>
		                  </tr>
		                  <tr>
		                    <td align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Label.department'/>：&nbsp;</td>
		                    <td colspan="3">
		                    	<input type="text" id="userDepartment" name="userDepartment" value=""/> 
		                    	<ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.Alert.Department'/>
		                    </td>
		                  </tr>
		                  <tr>
		                    <td align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Label.title'/>：&nbsp;</td>
		                    <td colspan="3">
		                    	<input type="text" id="userTitle" name="userTitle" value=""/> 
		                    	<ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.Alert.Post'/>
		                    </td>
		                  </tr>
		                  <tr>
		                    <td align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Label.tele'/>：&nbsp;</td>
		                    <td colspan="3">
		                    	<input type="text" id="userTelephone" name="userTelephone" value=""/> 
		                    	<ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.Alert.Tele'/>
		                    </td>
		                  </tr>
		                  <tr>
		                    <td align="right">&nbsp;</td>
		                    <td colspan="3">
		                        <input id="agree" name="agree" style="width:auto;height:auto;border:none; vertical-align: middle; *margin-bottom: 5px;" type="checkbox"  value="checked" />
		                        	<ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.Alert.Agree1'/>
		                        	《<a target="_blank" href="${ctx}/agreement"><ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.Alert.Agree2'/></a>》
		                        	<ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.Alert.Agree3'/>
		                    </td>
		                  </tr>
		                  <tr>
		                    <td align="right">&nbsp;</td>
		                    <td colspan="3">
			                    <p class="mt10">
				                    <a href="javascript:void(0)" onclick="apply();" class="registerButton">
				                    <ingenta-tag:LanguageTag sessionKey='lang' key='Global.Button.Register'/>
				                    </a>
				                </p>
			                </td>
		                  </tr>
		              </table>
		            </div>
		            <div class="newLogoFr">
		            	<div class="logoDiv pb20">
		                    <h1>
		                    	<ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.Alert.Login'/>
		                    	<a href="${ctx}/pages/user/form/newLogin"><ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.Login'/></a>
		                    	<ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.Login1'/>
		                    </h1>
		                    <p class="mt10">
		                    	<a href="${ctx}/pages/user/form/newLogin" class="logoa"><ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.Login'/></a>
		                    </p>
		                </div>
<!-- 		                <p class="mt10"> -->
<%-- 							<ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.OtherLogin'/> --%>
<!-- 						</p> -->
<!-- 		                <p class="mt10"> -->
<%-- 		                	<a href="#"><img src="${ctx}/images/ico/sina.png" class="vm" />  --%>
<%-- 		                		<ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.OtherLogin.WeiBo'/> --%>
<!-- 		                	</a> -->
<!-- 		                </p> -->
<!-- 		                <p class="mt10"> -->
<%-- 		                	<a href="#"><img src="${ctx}/images/ico/qq.png" class="vm" /> --%>
<%-- 		                	 	<ingenta-tag:LanguageTag sessionKey='lang' key='Page.Register.OtherLogin.QQ'/> --%>
<!-- 		                	</a> -->
<!-- 		                </p> -->
		            </div>
		        </div>
		    </div>
		</div>
	    <input type="hidden" name="userType" value="1"/>
	    <input type="hidden" name="msg" />
	</form:form>
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
		
	<!-- 底部的版权信息 -->
	<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
	<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>
