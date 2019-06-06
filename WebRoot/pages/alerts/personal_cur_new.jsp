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
	<script type="text/javascript">
	//删除当前学科提醒
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
							var url = "${ctx}/pages/user/form/alertsDeleteNew?dels="+str+"&r_="+new Date().getTime();
							window.location.href=url;
						
					}else{
						return;
					}
				}else{
					art.dialog.tips("<ingenta-tag:LanguageTag key="Pages.Alerts.Prompt.Alerts.Delete.UnSelected1" sessionKey="lang"/>",1,'error');
				}
			});
		}); 
	//修改当前学科提醒的频率
	function saveChange(pid){			
		$.ajax({		
		type : "POST",
		url : "${ctx}/pages/user/form/alertsSaveNew",
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
	//当前学科提醒全选
	function allCheck(check){
	　　　　var checkbox=document.getElementsByName("del");
	　　　　if(check.checked){
	　　　　　　for(var i=0;i<checkbox.length;i++){
	　　　　　　　　checkbox[i].checked="checked";
	　　　　　　}    	
	　　　　}else{
	　　　　　　for(var i=0;i<checkbox.length;i++){
	　　　　　　　　checkbox[i].checked="";
	　　　　　　} 
	　　　　}
	　　}
	function getSubList(e,name,id,cssType){
			var a_html = $(e).html();
			var s = 1.2;
			var s2 = 8;
			var obj = e.parentNode;
			var oh = parseInt(obj.offsetHeight);
			var h = parseInt(obj.scrollHeight);
			var nh = oh;
	
			var status = $("#ss_"+id).val();
	
			if (obj.getAttribute("oldHeight") == null) {
				obj.setAttribute("oldHeight", oh);
			} else {
				var oldh = Math.ceil(obj.getAttribute("oldHeight"));
			}
			if (a_html.indexOf("+")>=0) {
				if(status<=0){
				$("#scList_"+id).css("display","block");
				//利用Ajax查询
				$.ajax({
						type : "POST",
						async : false,
						url : "${ctx}/pages/user/form/subListAjax",
						data : {
							dirId : id,
							name : name,
							type : 2,
							cssType : cssType,
							r_ : new Date().getTime()
						},
						success : function(data) {
							var s = data.split("^");
							if(s[0]=="success"){
								$("#scList_"+id).html(s[1]);
								$("#scList_"+id).css({ "text-align" : "center" });
								oh = parseInt(obj.offsetHeight);
								h = parseInt(obj.scrollHeight);
								nh = oh;
							}else{
								alert(s[1]);
							}
						},
						error : function(data) {
							alert(data);
						}
					}); 
				}
			}else{
				$("#scList_"+id).css("display","none");
			}
	
			var reSet = function() {
				if (a_html.indexOf("+")>=0) {
					e.innerHTML = "- "+name;
					if (nh < h) {
						nh = Math.ceil(h - (h - nh) / s);
						obj.style.height = nh + "px";
					} else {
						window.clearInterval(IntervalId);
					}
				} else {
					e.innerHTML = "+ "+name;
					if (nh > oldh) {
						nhh = Math.ceil((nh - oldh) / s2);
						nh = nh - nhh;
						obj.style.height = nh + "px";
					} else {
						window.clearInterval(IntervalId);
					}
				}
			}
			var IntervalId = window.setInterval(reSet, 10);
	}
	
	
	//新增学科提醒全选
	function dealsAllCheck(check){
	　　　　var checkbox=document.getElementsByName("deals");
	　　　　if(check.checked){
	　　　　　　for(var i=0;i<checkbox.length;i++){
	　　　　　　　　checkbox[i].checked="checked";
	　　　　　　}    	
	　　　　}else{
	　　　　　　for(var i=0;i<checkbox.length;i++){
	　　　　　　　　checkbox[i].checked="";
	　　　　　　} 
	　　　　}
	　　}
	//新增学科提醒提交
	function apply(){
		var s = 0;
		$("input[name='deals']").each(function(){
	     	if($(this).attr("checked")){
	     		s++;
	     		closed;
	     	}
     	});
     	if(s==0){
     		art.dialog.tips("<ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Alerts.Prompt.checked" sessionKey="lang" />",1,"error",2);
     	}else{
     		document.getElementById("form").submit();
     	}
		
	}
	
	
	</script>
</head>

<body>
	<jsp:include page="/pages/header/headerData" flush="true" />
<div class="main personMain">
	<div class="personLeft">
    	<c:if test="${sessionScope.mainUserLevel!=2 }">
    		<jsp:include page="/pages/menu?mid=sujb&type=5" flush="true"/>
    	</c:if>
	    <c:if test="${sessionScope.mainUserLevel==2 }">
	    	<jsp:include page="/pages/menu?mid=sujb&type=1" flush="true"/>
	    </c:if>
    </div>
    <div class="perRight">
    	<h1><b><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Alerts.Title1" sessionKey="lang" /></b></h1>
        <p><ingenta-tag:LanguageTag key="Pages.SubjectAlerts.Alert1" sessionKey="lang" /></p>
        <p class="mt10 mb10"><a href="#newSubjectAlert" class="blueB"><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Alerts.Setting.Title1" sessionKey="lang" /></a></p>
        <p><ingenta-tag:LanguageTag key="Pages.SubjectAlerts.Alert2" sessionKey="lang" />${fn:length(mylist) }</p>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="noborTab">
          <tr>
            <td class="borTd"><ingenta-tag:LanguageTag key="Pages.SubjectAlerts.Title1" sessionKey="lang" /></td>
            <td class="borTd"><ingenta-tag:LanguageTag key="Pages.SubjectAlerts.Title2" sessionKey="lang" /></td>
            <td class="borTd"><input type="checkbox" class="vm" name="all" onclick="allCheck(this)"/><ingenta-tag:LanguageTag key="Pages.SubjectAlerts.Title3" sessionKey="lang" /></td>
            <td class="borTd"><a href="javascript:;" class="grayA" id="delete"><ingenta-tag:LanguageTag key="Global.Button.Delete" sessionKey="lang" /></a></td>
          </tr>
          <c:if test="${fn:length(mylist)>0}">	
	         <c:forEach items="${mylist}" var="p" varStatus="index">
		        <tr>
		        	<c:set var="cl">${index.index%2==0?"a":"b" }</c:set>
					
			        <td width="400px">
			        	<c:if test="${sessionScope.lang!='en_US' }">${p.subject.name}</c:if>
			        	<c:if test="${sessionScope.lang=='en_US' }">${p.subject.nameEn}</c:if>
			        </td>
			        <td width="260px">
						<select id="frequency_${p.id}" name="frequencys" style="width:100px;">
					     <option value="2" <c:if test="${p.frequency=='2'}">selected="selected"</c:if>><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Day" sessionKey="lang"/></option>
					     <option value="3" <c:if test="${p.frequency=='3'}">selected="selected"</c:if>><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Week" sessionKey="lang"/></option>
					     <option value="4" <c:if test="${p.frequency=='4'}">selected="selected"</c:if>><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Month" sessionKey="lang"/></option>
						</select>
						<a onclick="saveChange('${p.id}')" title="<ingenta-tag:LanguageTag key="Pages.Alerts.Frequency.Mondify" sessionKey="lang"/>"><img src="${ctx}/images/save.png" width="17" height="17" class="newVm"/></a>
					</td>
					<td>
						<input type="checkbox" name="del" id="del_${p.id }" value="${p.id }"/>
					</td>
		        </tr>
			 </c:forEach>
		 </c:if>
		 <c:if test="${fn:length(mylist) <=0}">	
		 <tr align="center">
		 	<td colspan="3" class="red"><ingenta-tag:LanguageTag key="Pages.SubjectAlerts.Alert3" sessionKey="lang" /></td>
		 </tr>
		 </c:if>
        </table>
      	<span><hr style="border:1px solid  #008cd6; margin-top: 50px" /></span>
      	<form:form action="alertsSubmitNew" method="post" commandName="form" id="form">
        <h1 class="pt30" id="newSubjectAlert"><b><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Alerts.Setting.Title1" sessionKey="lang" /></b></h1>
        <p><ingenta-tag:LanguageTag key="Pages.SubjectAlerts.Alert5" sessionKey="lang" /></p>
        <p class="mt10 mb10"><a href="javascript:;" onclick="apply()" class="blueB"><ingenta-tag:LanguageTag key="Global.Button.Save" sessionKey="lang" /></a></p>
        <p><ingenta-tag:LanguageTag key="Pages.SubjectAlerts.Alert6" sessionKey="lang" />
        	<select name="frequencysAll">
        		 <option value="0"><ingenta-tag:LanguageTag key="Pages.SubjectAlerts.Title4" sessionKey="lang" /></option>
            	 <option value="2"><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Day" sessionKey="lang"/></option>
			     <option value="3"><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Week" sessionKey="lang"/></option>
			     <option value="4"><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Month" sessionKey="lang"/></option>
            </select> <ingenta-tag:LanguageTag key="Pages.SubjectAlerts.Alert7" sessionKey="lang" /></p>
        <div class="oh ml5">
      
       		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="noborTab">
       		 <tr>
            <td class="borTd"><ingenta-tag:LanguageTag key="Pages.SubjectAlerts.Title1" sessionKey="lang" /></td>
            <td class="borTd"><ingenta-tag:LanguageTag key="Pages.SubjectAlerts.Title2" sessionKey="lang" /></td>
            <td class="borTd"><input type="checkbox" class="vm" name="dealsAll" onclick="dealsAllCheck(this)"/><ingenta-tag:LanguageTag key="Pages.SubjectAlerts.Title3" sessionKey="lang" /></td>
          </tr>
          		<c:if test="${fn:length(subjectList)>0}">	
	        	<c:forEach items="${subjectList }" var="p" varStatus="index">
			        <tr>
			         	<td width="400px">
			         		<input type="hidden" id="ss_${c.id }" value="0"/>
			         		<a onclick="getSubList(this,'${p.name}','${p.id }','bodytd')" href="javascript:;" title="<c:if test="${sessionScope.lang=='en_US' }">${p.nameEn }</c:if><c:if test="${sessionScope.lang!='en_US' }">${p.name }</c:if>">
			         		<c:if test="${sessionScope.lang=='en_US' }">${p.nameEn }</c:if><c:if test="${sessionScope.lang!='en_US' }">${p.name }</c:if>
			         		</a>
			         		<div class="weihu" id="scList_${c.id }" style="display: none;">
										<img src="${ctx}/images/loading.gif"/>
							</div>
			         	</td>
				        <td width="260px">
							<select id="frequency_${p.id}" name="frequencys" style="width:100px;">
							     <option value="2"><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Day" sessionKey="lang"/></option>
							     <option value="3"><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Week" sessionKey="lang"/></option>
							     <option value="4"><ingenta-tag:LanguageTag key="Pages.Alerts.Lable.Month" sessionKey="lang"/></option>
							</select>
						</td>
						 <td width="10%">
							
				        	<input type="checkbox" name="deals" value="${p.id}" id="deal_${p.id}" onclick="dealSubject('${p.id}')"/>
				        	
				        </td>
						<td style="display: none;"><input type="hidden" name="treeCodes" value="${p.treeCode}"/></td>
			        </tr>
				 </c:forEach>
				 </c:if>
				  <c:if test="${fn:length(subjectList) <=0}">	
					 <tr align="center">
					 	<td colspan="3" class="red"><ingenta-tag:LanguageTag key="Pages.SubjectAlerts.Alert4" sessionKey="lang" /></td>
					 </tr>
				 </c:if>
			</table>
			  <input type="hidden" name="alertsType" value="2"/> 
         
            </div>
        </div><!-- 到此结束 -->
        </form:form> 
    </div>
</div>
	<!-- 底部的版权信息 -->
	<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
	<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
</body>
</html>
