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
<script type="text/javascript" src="${ctx}/js/checkPwd.js"></script>
<!--客户案例开始-->
<script type="text/javascript">
function apply(){
	var userName = document.getElementById("userName").value;
	if(userName.replace(/\s+/g,"")==""){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgUserDetail.Prompt.name'/>",1,'error');
		document.getElementById("userName").focus();
		return;
	}
	if(document.getElementById("country").value=="" || document.getElementById("country").value==0){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgUserDetail.Prompt.country'/>",1,'error');
		document.getElementById("country").focus();
		return;
	}
// 	if(document.getElementById("institutionId").value=="" || document.getElementById("institutionId").value==0){
// 		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgUserDetail.Prompt.institutionId'/>",1,'error');
// 		document.getElementById("institutionId").focus();
// 		return;
// 	}
	if(document.getElementById("telephone").value=="" || document.getElementById("telephone").value==0){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgUserDetail.Prompt.telephone'/>",1,'error');
		document.getElementById("telephone").focus();
		return;
	}
	var email = document.getElementById("email").value;
	if(email.replace(/\s+/g,"")==""){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgUserDetail.Prompt.email'/>",1,'error');
		document.getElementById("email").focus();
		return;
	}else{
		
	  	var myreg = /^([a-zA-Z0-9]+[_|\-|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
           if(!myreg.test(email)){
                art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgUserDetail.Prompt.prompt'/>\n\n<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgUserDetail.Prompt.effemail'/>",1,'error');
                document.getElementById("email").focus();
             	return false;
        	}
	
	}
	if(document.getElementById("email").value !=  document.getElementById("emailCheck").value){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgUserDetail.Prompt.twoemail'/>",1,'error');
		return;
	}
	if(document.getElementById("organizationType").value=="" || document.getElementById("organizationType").value==0){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgUserDetail.Prompt.organizationType'/>",1,'error');
		document.getElementById("organizationType").focus();
		return;
	}
	if(document.getElementById("userDepartment")!=null){
		var userDepartment = document.getElementById("userDepartment").value;
		if(userDepartment.replace(/\s+/g,"")!=""){
		  	var myreg = /^[a-zA-Z\u4e00-\u9fa5]+$/;
	        if(!myreg.test(userDepartment)){
	          art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.department'/>");
	          document.getElementById("userDepartment").focus();
	          return false;
	        }
	        if(userDepartment.length>40){
	         	art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.department'/>");
	          	document.getElementById("userDepartment").focus();
	        	return false;
	        }
		
		}
	}
	
	if(document.getElementById("userTitle")!=null){
		var userTitle = document.getElementById("userTitle").value;
		if(userTitle.replace(/\s+/g,"")!=""){
		  	var myreg = /^[a-zA-Z\u4e00-\u9fa5]+$/;
	        if(!myreg.test(userTitle)){
	          art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.post'/>");
	          document.getElementById("userTitle").focus();
	          return false;
	        }
	        if(userTitle.length>40){
	         	art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.post'/>");
	          	document.getElementById("userTitle").focus();
	        	return false;
	        }
		
		}
	}
	
	if(document.getElementById("userTelephone")!=null){
		var userTelephone = document.getElementById("userTelephone").value;
		if(userTelephone.replace(/\s+/g,"")!=""){
		  	var myreg = /(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)|(13\d{9}$)|(15[0135-9]\d{8}$)|(18[267]\d{8}$)/;
	        if(!myreg.test(userTelephone)){
	          art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.tele'/>");
	          document.getElementById("userTelephone").focus();
	          return false;
	        }
	        if(userTelephone.length>40){
	         	art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.tele'/>");
	          	document.getElementById("userTelephone").focus();
	        	return false;
	        }
		
		}
	}
	
	document.getElementById("form").submit();
}

</script>
<c:if test="${form.msg!=null&&form.msg != ''}">
	<script language="javascript">
	$(document).ready(function(){
		var isSucc='${form.isSuccess}';
		if(isSucc=='false'){
			art.dialog.tips('${form.msg}',1,'error');
		}else{
			art.dialog.tips('${form.msg}',1);
		}
	});
	</script>
</c:if>
<!--客户案例结束-->
</head>

<body>
<!--以下top state -->
	<jsp:include page="/pages/header/headerData" />
	<!--以上top end -->

<div class="main personMain">
    <jsp:include page="/pages/menu?mid=info&type=1" flush="true" />
   
    <div class="perRight">
     <form:form id="form" commandName="form" action="detailSubmit">
    	<h1><ingenta-tag:LanguageTag sessionKey='lang' key='userProp.info.update'/></h1>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="regTable mt10">
          <tr>
            <td width="120" align="right"><span class="red">*</span><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgUserDetail.Label.name'/>：</td>
            <td colspan="3"><input type="text" id="userName" name="userName" value="${form.userName}"/></td>
          </tr>
          
          
           <c:forEach items="${list}" var="c" varStatus="index"> 
            <c:if test="${c.display!='select'&&c.code!='freetax'&&c.code!='businessCode'&&c.code!='email'&&c.code!='emailCheck'}">
          <c:if test="${c.must!=1}">
          <tr>
            <td align="right" class="tleft"><c:if test="${c.must!=1}"> <span class="red">*</span></c:if><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：</td>
            <td colspan="3">
                  	<input type="hidden" name="typePropIds" value="${c.id}"/>            		
					<input type="text" id="${c.code}" name="propsValue" value="${form.values[c.code]}" style="vertical-align:middle;"/>
					
                </td>
          </tr>
          </c:if>
          </c:if> 
          
          <c:if test="${c.display!='select'&&c.code=='email'}">
          <tr>
            <td align="right" class="tleft"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：</td>
            <td colspan="3">
            		${c.val}
            		<input type="hidden" name="typePropIds" value="${c.id}"/>
            		<input type="hidden" id="${c.code}" name="propsValue" value="<c:if test="${form.values[c.code]==null}">1</c:if><c:if test="${form.values[c.code]!=null}">${form.values[c.code]}</c:if>"/>
           </td>
          </tr>
          </c:if>
             <c:if test="${c.display!='select'&&c.code=='emailCheck'}">
            		<input type="hidden" name="typePropIds" value="${c.id}"/>
            		<input type="hidden" id="${c.code}" name="propsValue" value="<c:if test="${form.values[c.code]==null}">1</c:if><c:if test="${form.values[c.code]!=null}">${form.values[c.code]}</c:if>"/>
          </c:if>
        <c:if test="${c.display=='select' && c.svalue=='orgTypeMap'}">
          <tr style="display: none">
            <td align="right" class="tleft"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：</td>
            <td colspan="3">
            			<input type="hidden" name="typePropIds" value="${c.id}"/>
            			<input type="hidden" name="propsValue" value="${form.values[c.code]}"/>
            			${p.value}
            			<select id="${c.code}" name="propsValue" style="margin-top:3px;width:268px;"  disabled>
                   			 <option><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Label.Select"/></option>
                   				 <c:forEach items="${form.orgTypeMap}" var="p">
			        			<option value="${p.key}" <c:if test="${p.key==form.values[c.code]}"> selected</c:if>>${p.value}</option>
							</c:forEach>
                </select></td>
          </tr>
          </c:if>
          <c:if test="${c.display=='select' && c.svalue=='BInstitution'}">
          <tr>
            <td align="right" class="tleft"><c:if test="${c.must!=1}"> <span class="red">*</span></c:if><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：</td>
            <td colspan="3">
            			<input type="hidden" name="typePropIds" value="${c.id}"/>
            			<input type="hidden" name="propsValue" value="${form.values[c.code]}"/>
            			<select name="propsValue"  id="${c.code}" style="margin-top:3px;width:268px;"  disabled>
                    <option value="0"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Label.Select"/></option>
                    <c:forEach items="${form.institutionList}" var="l">
			        			<option value="${l.id}" <c:if test="${l.id==form.values[c.code]}"> selected</c:if>>${l.name}</option>
							</c:forEach>
                </select>
                <c:if test="${c.must!=1}"> </c:if>
                </td>
          </tr>
          </c:if>
          <c:if test="${c.display=='select' && c.svalue=='countryMap'}">
           <tr style="display: none">
            <td align="right" class="tleft"><c:if test="${c.must!=1}"> <span class="red">*</span></c:if><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：</td>
            <td colspan="3">
            <input type="hidden" name="typePropIds" value="${c.id}"/>
            <select id="${c.code}" name="propsValue" style="margin-top:3px;">
                    <option value="0"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Label.Select"/></option>
                   <c:forEach items="${form.countryMap}" var="p">
				        			<option value="${p.key}" <c:if test="${p.key==form.values[c.code]}"> selected</c:if>>${p.value}</option>
					</c:forEach>
                </select>
                <c:if test="${c.must!=1}"> </c:if>
                </td>
          </tr>
          </c:if>
         </c:forEach>
      	<tr>
		   <td align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Label.department'/>：&nbsp;</td>
		   <td colspan="3">
		    	<input type="text" id="userDepartment" name="userDepartment" value="${form.userDepartment}"/> 
		   </td>
		</tr>
		<tr>
		   <td align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Label.title'/>：&nbsp;</td>
		   <td colspan="3">
		       	<input type="text" id="userTitle" name="userTitle" value="${form.userTitle}"/> 
		  </td>
		</tr>
		<c:if test="${form.userType==1 }">
		<tr>
		  <td align="right"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Registration.Label.tele'/>：&nbsp;</td>
		  <td colspan="3">
		   	<input type="text" id="userTelephone" name="userTelephone" value="${form.userTelephone}"/> 
		  </td>
		</tr>
		</c:if>
        <tr>
            <td align="right">&nbsp;</td>
            <td colspan="3"><p class="mt10"><a href="javascript:void(0)" class="orgingA" onclick="apply();"><ingenta-tag:LanguageTag sessionKey='lang' key='Global.Button.Submit'/></a></p></td>
          </tr>
      </table>
      <input type="hidden" name="userType" value="1"/>
      </form:form> 
    </div>
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
