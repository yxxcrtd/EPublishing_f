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
<script type="text/javascript">
function dealSubject(subjectId){
	        if($("#deal_"+subjectId).prop("checked")==true){
	        	$("#frequency_"+subjectId).removeAttr("disabled");
	        }else{
	            $("#frequency_"+subjectId).attr("disabled","false");
	        }
		}
		
		function apply(){
			var s = 0;
			$("input[name='deals']").each(function(){
		     	if($(this).attr("checked")){
		     		s++;
		     		closed;
		     	}
	     	});
	     	if(s==0){
	     		alert("<ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Alerts.Prompt.checked" sessionKey="lang" />");
	     	}else{
	     		document.getElementById("form").submit();
	     	}
			
		}
</script>
</head>

<body>
	<!--以下top state -->
	<jsp:include page="/pages/header/headerData" />
	<!--以上top end -->

	<div class="main personMain">
	<c:if test="${sessionScope.mainUserLevel!=2 }">
    	<jsp:include page="/pages/menu?mid=sujb&type=5" flush="true"/>
    </c:if>
    <c:if test="${sessionScope.mainUserLevel==2 }">
    	<jsp:include page="/pages/menu?mid=sujb&type=1" flush="true"/>
    </c:if>
    <div class="perRight">
    
    <form:form action="alertsSubmit" method="post" commandName="form" id="form">
			<c:if test="${form.msg!=null&&form.msg != ''}">
				<script type="text/javascript">
				   art.dialog.tips('${form.msg}',1,'success');
				</script>
			</c:if>
    	<h1><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Alerts.Setting.Title1" sessionKey="lang" /></h1>
    	
    			<div class="t_list">
		    		<p class="mt20 mb10">
		    		<span>
		    			<c:if test="${list!= null || fn:length(list) != 0}">
		    				<a name="save" href="javascript:void(0)" class="orgingA" onclick="apply();"><ingenta-tag:LanguageTag key="Global.Button.Submit" sessionKey="lang" /></a>
				        </c:if>
				    </span>
		    		</p>
		        </div>
    	
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="curTable">
          <tr>
          	<td align="center" class="atdtop"><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Subject.Theme" sessionKey="lang" /></td>
            <td width="100" align="center" class="atdtop"><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Subject.Subscription" sessionKey="lang" /></td>
            <td width="180" align="center" class="atdtop"><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Frequency" sessionKey="lang" /></td>
          </tr>
          <tbody>
		             <c:forEach items="${list }" var="p" varStatus="index">
				        <tr>
				         	<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if> tdname">
				         		<span class="pl20">
				         		<a href="${ctx }/pages/user/form/subjectAlerts?pCode=${p.code}" title="<c:if test="${sessionScope.lang=='en_US' }">${p.nameEn }</c:if><c:if test="${sessionScope.lang!='en_US' }">${p.name }</c:if>">
				         		<c:if test="${sessionScope.lang=='en_US' }">${p.nameEn }</c:if><c:if test="${sessionScope.lang!='en_US' }">${p.name }</c:if>
				         		</a>
				         		</span>
				         	</td>
					        <td align="center" class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
					        	<input type="checkbox" name="deals" value="${p.id}" id="deal_${p.id}" onclick="dealSubject('${p.id}')"/>
					        </td>
					        <td align="center" class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
								<select id="frequency_${p.id}" name="frequencys" disabled="disabled" style="width: 75px;">
								     <option value="2"><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Day" sessionKey="lang"/></option>
								     <option value="3"><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Week" sessionKey="lang"/></option>
								     <option value="4"><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Month" sessionKey="lang"/></option>
								</select>
							</td>
							<td style="display: none;"><input type="hidden" name="treeCodes" value="${p.treeCode}"/></td>
				        </tr>
					 </c:forEach>
					 </tbody>
        </table> 
        <input type="hidden" name="alertsType" value="2"/> 
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
