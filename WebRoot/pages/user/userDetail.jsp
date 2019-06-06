<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv='X-UA-Compatible' content='IE=edge'/>
    <title>CNPIEC eReading: Introducing CNPIEC eReading</title>
    <%@ include file="/common/tools.jsp"%>
<!--客户案例开始-->
<script language="javascript">
function apply(){	

	if(document.getElementById("userName").value.trim()=="" || document.getElementById("userName").value==0){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserDetail.Prompt.username'/>",1,'error');
		document.getElementById("userName").focus();
		return;
	}
/* 	if(document.getElementById("country").value=="" || document.getElementById("country").value==0){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.UserDetail.Prompt.country'/>",1,'error');
		document.getElementById("country").focus();
		return;
	} */
	document.getElementById("form").submit();
}

</script>
<!--客户案例结束-->
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

<%@ include file="/common/ico.jsp"%></head>
<body>
<div class="big">
<jsp:include page="/pages/header/headerData" flush="true" />

<form:form id="form" commandName="form" action="detailSubmit">

  
  <!--定义01 main 内容区开始-->
  <div class="main">

      <!--定义 0102 左边内容区域 开始-->
      <div class="tablepage">
      	<h1>
			<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.UserDetail.title"/>
		</h1>
        <div class="zhuce">
			<div class="zhuce_xinxi">
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
					<thead>
						<tr>
							<td></td>
							<td height="30">
							<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.UserDetail.Label.prompt"/>
							</td>
						</tr>
					</thead>
					<tr>
						<td class="tleft"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.UserDetail.Label.name"/>：&nbsp; </td>
						<td><input type="text" id="userName" name="userName" value="${form.userName}"/><font style="color:#ff0000;">*</font></td>
					</tr>
					<c:forEach items="${list}" var="c" varStatus="index">
	            	<c:if test="${c.display!='select'&&c.code!='freetax'&&c.code!='email'&&c.code!='emailCheck'}">
	            		<input type="hidden" name="typePropIds" value="${c.id}"/>
						<tr>
						<td class="tleft"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：&nbsp; </td>
						<td><input type="text" id="${c.code}" name="propsValue" value="${form.values[c.code]}"/> <c:if test="${c.must!=1}"> <font style="color:#ff0000;">*</font></c:if></td>
						</tr>
	            	</c:if>
	            	<c:if test="${c.display!='select'&&c.code=='email'}">
	            		<tr>
	            		<td class="tleft"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：&nbsp;</td>
	            		<td>${c.val}</td>
	            		</tr>
	            		<input type="hidden" name="typePropIds" value="${c.id}"/>
	            		<input type="hidden" id="${c.code}" name="propsValue" value="<c:if test="${form.values[c.code]==null}">1</c:if><c:if test="${form.values[c.code]!=null}">${form.values[c.code]}</c:if>"/>
	            	</c:if>
	            	<c:if test="${c.display!='select'&&c.code=='freetax'}">
	            		<input type="hidden" name="typePropIds" value="${c.id}"/>
	            		<input type="hidden" id="${c.code}" name="propsValue" value="<c:if test="${form.values[c.code]==null}">1</c:if><c:if test="${form.values[c.code]!=null}">${form.values[c.code]}</c:if>"/>
	            	</c:if>
	            	<c:if test="${c.display!='select'&&c.code=='emailCheck'}">
	            		<input type="hidden" name="typePropIds" value="${c.id}"/>
	            		<input type="hidden" id="${c.code}" name="propsValue" value="<c:if test="${form.values[c.code]==null}">1</c:if><c:if test="${form.values[c.code]!=null}">${form.values[c.code]}</c:if>"/>
	            	</c:if>
	            	<c:if test="${c.display=='select' && c.svalue=='countryMap'}">
	            		<input type="hidden" name="typePropIds" value="${c.id}"/>
	            		<tr>
	            		<td class="tleft"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：&nbsp;</td>
	            		<td>
		            		<select id="${c.code}" name="propsValue">
			        		<option value="0"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Label.Select"/></option>
			        		<c:forEach items="${form.countryMap}" var="p">
			        			<option value="${p.key}" <c:if test="${p.key==form.values[c.code]}"> selected</c:if>>${p.value}</option>
							</c:forEach>
		       			</select>
		       			<c:if test="${c.must!=1}"> <font style="color:#ff0000;">*</font></c:if>
		       			</td>
		       			</tr>
	            	</c:if>
	            	<c:if test="${c.display=='select' && c.svalue=='identityMap'}">
	            		<input type="hidden" name="typePropIds" value="${c.id}"/>
	            		<tr>
	            		<td class="tleft"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：&nbsp;</td>
	            		<td>
		            		<select id="${c.code}" name="propsValue" style="margin-top:3px;;width:235px;">
			        		<c:forEach items="${form.identityMap}" var="p">
			        			<option value="${p.key}" <c:if test="${p.key==form.values[c.code]}"> selected</c:if>>${p.value}</option>
							</c:forEach>
		       			</select>
		       			<c:if test="${c.must!=1}"><font style="color:#ff0000;">*</font></c:if>
		       			</td>
		       			</tr>
	            	</c:if>
					</c:forEach>
					
					<tfoot>
						<tr>
							<td>&nbsp;</td>
							<td>
							<a class="a_gret" onclick="apply();"><ingenta-tag:LanguageTag sessionKey='lang' key='Global.Button.Submit'/></a>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>		        
        
      </div>
      <!--定义 0102 左边内容区域 结束-->
      
      <!--定义 0103 右边内容区域 开始-->
      <jsp:include page="/pages/menu?mid=info" flush="true" />
      <!--定义 0103 右边内容区域 结束-->
      

  </div>
  <!--定义01 main 内容区结束-->

  <input type="hidden" name="userType" value="1"/><!-- 用户类型为1：个人用户 -->
 </form:form>
 <!-- 底部的版权信息 -->
<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
 </div>
</body>
</html>
