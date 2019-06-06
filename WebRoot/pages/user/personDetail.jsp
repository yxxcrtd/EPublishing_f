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
	if(document.getElementById("country").value=="" || document.getElementById("country").value==0){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgUserDetail.Prompt.country'/>",1,'error');
		document.getElementById("country").focus();
		return;
	}
	if(document.getElementById("organizationType").value=="" || document.getElementById("organizationType").value==0){
		art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgUserDetail.Prompt.organizationType'/>",1,'error');
		document.getElementById("organizationType").focus();
		return;
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
    <jsp:include page="/pages/menu?mid=changeinfo&type=2" flush="true" />
   
    <div class="perRight">
     <form:form id="form" commandName="form" action="personSubmit">
    	<h1><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Instaccount.Label.orgUserDetail'/></h1>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="regTable mt10">
           <c:forEach items="${list}" var="c" varStatus="index"> 
            <c:if test="${c.display!='select'&&c.code!='freetax'&&c.code!='businessCode'&&c.code!='email'&&c.code!='emailCheck'}">
            <c:if test="${c.must==1}"> 
          <tr>
            <td align="right" class="tleft"><c:if test="${c.must!=1}"> <span class="red">*</span></c:if><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：</td>
            <td colspan="3">
               	<input type="hidden" name="typePropIds" value="${c.id}"/>            		
				<input type="text" id="${c.code}" name="propsValue" value="${form.values[c.code]}" style="vertical-align:middle;"/>
            </td>
          </tr>
          </c:if>
          </c:if> 
          <c:if test="${c.display!='select'&&c.code=='emailCheck'}">
           		<input type="hidden" name="typePropIds" value="${c.id}"/>
           		<input type="hidden" id="${c.code}" name="propsValue" value="<c:if test="${form.values[c.code]==null}">1</c:if><c:if test="${form.values[c.code]!=null}">${form.values[c.code]}</c:if>"/>
          </c:if>
        <c:if test="${c.display=='select' && c.svalue=='orgTypeMap'}">
          <tr>
            <td align="right" class="tleft"><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：</td>
            <td colspan="3">
       			<input type="hidden" name="typePropIds" value="${c.id}"/>
       			<input type="hidden" name="propsValue" value="${form.values[c.code]}"/>${p.value}
           			<select id="${c.code}" name="propsValue" style="margin-top:3px;width:268px;" disabled>
               			<option><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Label.Select"/></option>
               				 <c:forEach items="${form.orgTypeMap}" var="p">
	        					 <option value="${p.key}" <c:if test="${p.key==form.values[c.code]}"> selected</c:if>>${p.value}</option>
							 </c:forEach>
                   </select></td>
          </tr>
          </c:if>
          <c:if test="${c.display=='select' && c.svalue=='countryMap'}">
           <tr>
            <td align="right" class="tleft"><c:if test="${c.must!=1}"> <span class="red">*</span></c:if><ingenta-tag:LanguageTag sessionKey='lang' key='${c.key}'/>：</td>
            <td colspan="3">
            <input type="hidden" name="typePropIds" value="${c.id}"/>
            <select id="${c.code}" name="propsValue" style="margin-top:3px;width:268px;">
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
            <td align="right">&nbsp;</td>
            <td colspan="3"><p class="mt10"><a href="javascript:void(0)" class="orgingA" onclick="apply();"><ingenta-tag:LanguageTag sessionKey='lang' key='Global.Button.Submit'/></a></p></td>
          </tr>
      </table>
      <input type="hidden" name="userName" value="${form.userName}"/>
      <input type="hidden" name="userType" value="2"/>
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
</div>
		
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>
