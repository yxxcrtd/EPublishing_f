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
	var username = document.getElementById("userName").value;
	if(username.replace(/\s+/g,"")==""){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.Prompt.name'/>",1,'error');
		document.getElementById("userName").focus();
		return;
	}
	if(document.getElementById("country").value=="" || document.getElementById("country").value==0){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.Prompt.country'/>",1,'error');
		document.getElementById("country").focus();
		return;
	}
	if(document.getElementById("institutionId").value=="" || document.getElementById("institutionId").value==0){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.Prompt.institutionId'/>",1,'error');
		document.getElementById("institutionId").focus();
		return;
	}
	if(document.getElementById("telephone").value=="" || document.getElementById("telephone").value==0){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.Prompt.telephone'/>",1,'error');
		document.getElementById("telephone").focus();
		return;
	}
	var email = document.getElementById("email").value;
	if(email.replace(/\s+/g,"")==""){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.Prompt.email'/>",1,'error');
		document.getElementById("email").focus();
		return;
	}else{
		
	  	var myreg = /^([a-zA-Z0-9]+[_|\-|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
           if(!myreg.test(email)){
                art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.Prompt.tip'/>\n\n<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.Prompt.effemail'/>",1,'error');
                document.getElementById("email").focus();
             	return false;
        	}
	
	}
	if(document.getElementById("email").value !=  document.getElementById("emailCheck").value){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.Prompt.twoemail'/>",1,'error');
		return;
	}
	if(document.getElementById("organizationType").value=="" || document.getElementById("organizationType").value==0){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.Prompt.organizationType'/>",1,'error');
		document.getElementById("organizationType").focus();
		return;
	}
	if(document.getElementById("uid").value==""){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.Prompt.uid'/>",1,'error');
		document.getElementById("uid").focus();
		return;
	}
	if(document.getElementById("pwd").value==""){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.Prompt.pwd'/>",1,'error');
		document.getElementById("pwd").focus();
		return;
	}
	document.getElementById("form").submit();
}

</script>
<!--客户案例结束-->
<c:if test="${form.msg!=null&&form.msg != ''}">
	<script language="javascript">
	$(function(){
		var msg='${form.msg}';
		if(msg!=null && msg!=''){
			art.dialog.tips(msg);
		}
	});
		
	</script>
</c:if>

<%@ include file="/common/ico.jsp"%></head>
<body>
<div class="big">
<jsp:include page="/pages/header/headerData" flush="true" />
<div class="main">
<form:form id="form" commandName="form" action="registerSubmit">

    <div class="tablepage">
    <h1><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.title'/></h1>
    	<div class="zhuce">
    	<div class="zhuce_xinxi" style="margin-left:150px;">
    	<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.Label.prompt'/>
       	  <table width="100%" cellspacing="0" cellpadding="0" border="0">			
			<tr>
			    <td class="tleft"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.Label.name'/>：&nbsp;</td>
			    <td><input type="text" id="userName" name="userName" value="${form.userName}"/> <span>*</span></td>
		    </tr>
		    <c:forEach items="${list}" var="c" varStatus="index">
            	<input type="hidden" name="typePropIds" value="${c.id}"/>
            	<c:if test="${c.display!='select'&&c.code!='freetax'}">
					<tr>
						<td class="tleft"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：&nbsp;</td>
						<td><input type="text" id="${c.code}" name="propsValue" value="${form.values[c.code]}"/><c:if test="${c.must!=1}"> <span>*</span></c:if></td>
					</tr>
            	</c:if>
            	<c:if test="${c.display!='select'&&c.code=='freetax'}">
            		<input type="hidden" id="${c.code}" name="propsValue" value="<c:if test="${form.values[c.code]==null}">1</c:if><c:if test="${form.values[c.code]!=null}">${form.values[c.code]}</c:if>"/>
            	</c:if>
            	<c:if test="${c.display=='select' && c.svalue=='orgTypeMap'}">
            		<tr>
            			<td class="tleft"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：&nbsp;</td>
            			<td>
		            	<select id="${c.code}" name="propsValue" >
		            		<option value="0"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Label.Select"/></option>
			        		<c:forEach items="${form.orgTypeMap}" var="p">
			        			<option value="${p.key}" <c:if test="${p.key==form.values[c.code]}"> selected</c:if>>${p.value}</option>
							</c:forEach>
	       				</select>
	       				<c:if test="${c.must!=1}"><span>*</span></c:if>
	       				</td>
	       			</tr>
            	</c:if>
            	<c:if test="${c.display=='select' && c.svalue=='countryMap'}">
            		<tr>
	            		<td class="tleft"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：&nbsp;</td>
	            		<td>
		            		<select id="${c.code}" name="propsValue" style="margin-top:3px;">
			        		<option value="0"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Label.Select"/></option>
			        		<c:forEach items="${form.countryMap}" var="p">
			        			<option value="${p.key}" <c:if test="${p.key==form.values[c.code]}"> selected</c:if>>${p.value}</option>
							</c:forEach>
		       			</select>
		       			<c:if test="${c.must!=1}"><span>*</span></c:if>
		       			</td>
	       			</tr>
            	</c:if>
            	<c:if test="${c.display=='select' && c.svalue=='BInstitution'}">
            		<tr>
	            		<td class="tleft"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：&nbsp;</td>
	            		<td>
		            		<select id="${c.code}" name="propsValue" style="margin-top:3px;">
				        		<option value="0"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Label.Select"/></option>
				        		<c:forEach items="${form.institutionList}" var="l">
				        			<c:if test="${l.code!='CNPIEC'}">
				        				<option value="${l.id}" <c:if test="${l.id==form.values[c.code]}"> selected</c:if>>${l.name}</option>
				        			</c:if>
								</c:forEach>
			       			</select>
			       			<c:if test="${c.must!=1}"><span>*</span></c:if>
			       		</td>
		       		</tr>
	            </c:if>
			</c:forEach>
			<tr>
			    <td class="tleft"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.Label.userName'/>：&nbsp;</td>
			    <td><form:input path="account.uid" id="uid"/><span>*</span></td>
		    </tr>
		    <tr>
			    <td class="tleft"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.Label.passwd'/>：&nbsp;</td>
			    <td><form:password path="account.pwd" id="pwd"/><span>*</span></td>
		    </tr>
		    <tr>
		    	<td>&nbsp;</td>
		    	<td><input id="sendMail" name="sendMail" type="checkbox" checked="checked" style="width:auto;height:auto;"/><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgRegistration.Label.sendMail'/></td>
		    </tr>
		    <tfoot>
			  <tr>
			    <td>&nbsp;</td>
			    <td style="padding-top:20px"><a style="margin-left:80px;margin-top:30px;" class="a_gret"  onclick="apply();"><ingenta-tag:LanguageTag sessionKey='lang' key='Global.Button.Register'/></a>
			  </tr>
			  </tfoot>	
			  </table>	
        </div>
        </div>
    </div>

    <!--左侧内容结束-->
    <!-- 右侧列表开始 -->
    <jsp:include page="/pages/menu?mid=user_manager" flush="true" />
    <!-- 右侧列表结束 -->
    
  <input type="hidden" name="userType" value="2"/><!-- 用户类型为2：机构用户 -->
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

 </div>
</body>
</html>
