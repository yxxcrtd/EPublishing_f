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
    	$(document).ready(function(){
		$("#delete").click(function(){
			//获取选中
			var dels = $("input[name='del']:checked");
				if(dels.size()>0){
					if(confirm("<ingenta-tag:LanguageTag key="Pages.Alerts.Prompt.Alerts.Delete" sessionKey="lang"/>")){
							var str="";  
							var i = 1;
							dels.each(function(){ 
								if(i==dels.size()){
									str+=$(this).val();
								} else{
									str+=$(this).val()+",";
									i++;
								}
							});  
							var url = "${ctx}/pages/user/form/alertsDelete?dels="+str+"&r_="+new Date().getTime();
							window.location.href=url;
						
					}else{
						return;
					}
				}else{
					art.dialog.tips("<ingenta-tag:LanguageTag key="Pages.Alerts.Prompt.Alerts.Delete.UnSelected1" sessionKey="lang"/>",1,'error');
				}
			});
		}); 
		function saveChange(pid){			
			$.ajax({		
			type : "POST",
			url : "${ctx}/pages/user/form/alertsSave",
			data : {
				id : pid,
				frequency : $("#frequency_"+pid).find("option:selected").val(),
				r_ : new Date().getTime()
			},
			success : function(data) {
				var s = data.split(":");
				if (s[0] == "success") {
					art.dialog.tips(s[1],1);
				}else{
					art.dialog.tips(s[1],1,'error');
				}
			},
			error : function(data) {
				art.dialog.tips(data,1,'error');
			}
		}); 
		}
	</script>
</head>

<body>

	<!--以下top state -->
	<jsp:include page="/pages/header/headerData" />
	<!--以上top end -->
	
<div class="main personMain">
	<c:if test="${sessionScope.mainUserLevel!=2 }">
    	<jsp:include page="/pages/menu?mid=remid&type=5" flush="true"/>
    </c:if>
    <c:if test="${sessionScope.mainUserLevel==2 }">
    	<jsp:include page="/pages/menu?mid=remid&type=1" flush="true"/>
    </c:if>
    <div class="perRight">
   <form:form action="mySubjectAlerts" method="post" commandName="form" id="form">
    	<c:if test="${form.msg!=null&&form.msg != ''}">
			<script type="text/javascript">
			   art.dialog.tips('${form.msg}',1,'success');
			</script>
		</c:if>
    
    	<h1><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Alerts.Title1" sessionKey="lang"/></h1>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="curTable">
          <tr>
            <td width="100" align="center"><a href="javascript:void(0)" id="delete" class="grayA"><ingenta-tag:LanguageTag key="Global.Button.Delete" sessionKey="lang" /></a></td>
            <td align="center">	<ingenta-tag:LanguageTag key="Pages.Alerts.Lable.NAME" sessionKey="lang"/></td>
            <td width="230" align="center">	<ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Frequency" sessionKey="lang"/></td>
          </tr>
          
          
             <tbody>
		             <c:forEach items="${list}" var="p" varStatus="index">
				        <tr>
				        	<c:set var="cl">${index.index%2==0?"a":"b" }</c:set>
							<td align="center" class="${cl }bodytd">
								<input type="checkbox" name="del" id="del_${p.id }" value="${p.id }"/>
							</td>
					        <td class="${cl }bodytd tdname">
					        	<span class="pl20">
						        	<c:if test="${sessionScope.lang!='en_US' }">${p.subject.name}</c:if>
						        	<c:if test="${sessionScope.lang=='en_US' }">${p.subject.nameEn}</c:if>
					        	</span>
					        </td>
					        <td align="center" class="${cl }bodytd">
								<select id="frequency_${p.id}" name="frequencys" style="width: 60%">
							     <option value="2" <c:if test="${p.frequency=='2'}">selected="selected"</c:if>><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Day" sessionKey="lang"/></option>
							     <option value="3" <c:if test="${p.frequency=='3'}">selected="selected"</c:if>><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Week" sessionKey="lang"/></option>
							     <option value="4" <c:if test="${p.frequency=='4'}">selected="selected"</c:if>><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Month" sessionKey="lang"/></option>
								</select>
								<a onclick="saveChange('${p.id}')" title="<ingenta-tag:LanguageTag key="Pages.Alerts.Frequency.Mondify" sessionKey="lang"/>"><img src="${ctx}/images/save.png" width="17" height="17" class="newVm"/></a>
							</td>
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
