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
<!--客户案例开始-->
<script language="javascript">

function apply(){
	var userName = document.getElementById("userName").value;
	if(userName.replace(/\s+/g,"")==''){
	//if(document.getElementById("userName").value==""){
// 		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertRegistration.Prompt.name'/>",1,'error');
		$("#tips1").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertRegistration.Prompt.name'/>");
		$("#userName").focus();
		return;
	}
	/* var code = document.getElementById("code").value;
	if(code.replace(/\s+/g,"")=="" || code==0){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertRegistration.Prompt.no'/>",1,'error');
		document.getElementById("code").focus();
		return;
	} */
	var email = document.getElementById("email").value;
	if(email.replace(/\s+/g,"")==""){
		$("#tips1").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertRegistration.Prompt.email'/>");
		document.getElementById("email").focus();
		return;
	}else{
		
	  	var myreg = /^([a-zA-Z0-9]+[_|\-|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
           if(!myreg.test(email)){
        	   	$("#tips").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertRegistration.Prompt.effemail'/>");
                document.getElementById("email").focus();
             	return false;
        	}
	
	}
	var emailCheck = document.getElementById("emailCheck").value;
	if(emailCheck.replace(/\s+/g,"")==""){
		$("#tips1").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertRegistration.Prompt.twoemailisnull'/>");
        document.getElementById("emailCheck").focus();
     	return false;
	}
	
	
	if(document.getElementById("email").value !=  document.getElementById("emailCheck").value){
		$("#tips1").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertRegistration.Prompt.twoemail'/>");
		return;
	}
	if(document.getElementById("institutionId").value=="" || document.getElementById("institutionId").value==0){
		$("#tips1").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertRegistration.Prompt.institutionId'/>");
		document.getElementById("institutionId").focus();
		return;
	}
	if(document.getElementById("uid").value==""){
		$("#tips1").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertRegistration.Prompt.uid'/>");
		document.getElementById("uid").focus();
		return;
	}
	if(!document.getElementById("agree").checked){ 
		$("#tips1").show().html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertRegistration.Prompt.agree'/>");
	    return;
	};
	
	document.getElementById("form").submit();
}


</script>
<c:if test="${form.msg!=null&&form.msg != ''}">
	<script language="javascript">
	$(document).ready(function(){
		$("#tips1").show().html('${form.msg}');
	});		
	</script>
</c:if>
<!--客户案例结束-->


<%@ include file="/common/ico.jsp"%></head>
<body>

<jsp:include page="/pages/header/headerData" flush="true" />
<div class="main personMain">
<jsp:include page="/pages/menu?mid=register&type=2" flush="true" />
<form:form id="form" commandName="form" action="registerSubmit">

    <div class="perRight">
    <h1><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertRegistration.title'/></h1>
    	
    	
    	<p>
    		<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertRegistration.title.prompt'/>
		</p>
		
		<p style="margin-top: 10px;"><div id="tips1"></div></P>
       	
       	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="regTable mt10">			
			<tr>
			    <td width="120" align="right"><span class="red">*</span> <ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertRegistration.Label.name'/>：</td>
			    <td colspan="3"><input type="text" id="userName" name="userName" value="${form.userName}"/></td>
		    </tr>		   
		    <c:forEach items="${list}" var="c" varStatus="index">
            	<input type="hidden" name="typePropIds" value="${c.id}"/>
            	<c:if test="${c.display!='select'&&c.code!='freetax'}">
					<tr>
						<td align="right"><c:if test="${c.must!=1}"><span class="red">*</span></c:if> <ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：</td>
						<td colspan="3"><input type="text" id="${c.code}" name="propsValue" value="${form.values[c.code]}"/><c:if test="${c.must!=1}"></c:if></td>
					</tr>
            	</c:if>
            	<c:if test="${c.display!='select'&&c.code=='freetax'}">
            		<input type="hidden" id="${c.code}" name="propsValue" value="<c:if test="${form.values[c.code]==null}">1</c:if><c:if test="${form.values[c.code]!=null}">${form.values[c.code]}</c:if>"/>
            	</c:if>            	
            	<c:if test="${c.display=='select' && c.svalue=='BInstitution'}">
            		<tr style="display:none">
	            		<td align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：</td>
	            		<td colspan="3">
	            			<input type="hidden" name="propsValue" value="${sessionScope.mainUser.institution.id}"/>
		            		<select id="${c.code}" name="propsValue" disabled>
			        		<option value="0"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Label.Select"/></option>
			        		<c:forEach items="${form.institutionList}" var="l">
			        			<option value="${l.id}" <c:if test="${l.id==sessionScope.mainUser.institution.id}"> selected</c:if>>${l.name}</option>
							</c:forEach>
		       				</select>
		       				<c:if test="${c.must!=1}"></c:if>
			       		</td>
		       		</tr>
	            </c:if>
			</c:forEach>
			<tr>
			    <td align="right"><span class="red">*</span><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertRegistration.Label.userName'/>：</td>
			    <td colspan="3"><form:input path="account.uid" id="uid"/></td>
		    </tr>
		    <tr>
		    	<td align="right">&nbsp;</td>
		    	<td colspan="3"><input  id="agree" type="checkbox" checked="checked" class="vm"/>
		    	<a class="red" target="_blank" href="${ctx}/agreement">
		    	<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertRegistration.Label.agree'/>
		    	</a>
		    	</td>
		    </tr>
		   
			  <tr>
			    <td align="right">&nbsp;</td>
			    <td colspan="3"><p class="mt10"><a href="javascript:void(0)" class="orgingA" onclick="apply();"><ingenta-tag:LanguageTag sessionKey='lang' key='Global.Lable.Upload'/></a></p></td>
			  </tr>
			  	
			  </table>	
       
    </div>

    <!--左侧内容结束-->
    <!-- 右侧列表开始 -->
    
    <!-- 右侧列表结束 -->
    
  <input type="hidden" name="userType" value="5"/><!-- 用户类型为5：专家用户 -->
 </form:form>
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
 		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>

</body>
</html>
