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
		<script type="text/javascript">
		function queryList(){
			document.getElementById("form").action="";
			document.getElementById("page").value="0";
			document.getElementById("form").submit();
		}
		function changeStatus(id){
			var altContent="";
			var status = $("#status_"+id).attr("value");
			if(status=="1"){
				altContent = "<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Prompt.status1'/>";
			}else{
				altContent = "<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Prompt.status2'/>"
			}
			if(window.confirm(altContent)){
				$.ajax({
					type : "POST", 
					async : false,   
		           	url: "<%=request.getContextPath()%>/pages/orgUser/form/changeStatus",
		           	data: {
		           		id:id,
		           		status:status,
						r_ : new Date().getTime()
		           	},
		           	success : function(data) {  
		           		var returnVal = data.split(":");
		           		art.dialog.tips(returnVal[1]);
		           		if(returnVal[0]=="true"){
		            		if(status==1){
		            			$("#btn_"+id).text("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Label.status2'/>");
		            			$("#status_"+id).attr("value","2");
		            			$("#st_lab_"+id).html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Label.status1'/>");
		            			$("#btn_"+id).attr("a_gret");
		            		}else{
		            			$("#btn_"+id).text("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Label.status1'/>");
		            			$("#status_"+id).attr("value","1");
		            			$("#st_lab_"+id).html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Label.status2'/>");
		            			$("#btn_"+id).attr("a_cancel");
		            		}  
		            		queryList();
	            		}
	            	},  
	            	error : function(data) {
	            	}  
	            });
			}
		}
		function resetPwd(id){
			if(window.confirm("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Prompt.resetPwd'/>")){
				$.ajax({
					type : "POST", 
					async : false,   
		           	url: "<%=request.getContextPath()%>/pages/user/form/resetPwdByAdmin",
		           	data: {
		           		id:id,
						r_ : new Date().getTime()
		           	},
		           	success : function(data) {  
		           		var returnVal = data.split(":");
		           		if(returnVal[0]=="true"){
		           			art.dialog.tips(returnVal[1]);
	            		}else{
	            			art.dialog.tips(returnVal[1],1,'error');
	            		}
	            	},  
	            	error : function(data) {  
	            	}  
	            });
			}
		}
		
		function changeFaxStatus(id){
			var altContent="";
			var faxStatus = $("#Fax_"+id).attr("value");
			if(faxStatus=="1"){
				altContent = "<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Prompt.taxStatus1'/>";
			}else{
				altContent = "<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Prompt.taxStatus2'/>";
			}
			if(window.confirm(altContent)){
				$.ajax({
					type : "POST", 
					async : false,   
		           	url: "<%=request.getContextPath()%>/pages/orgUser/form/changeFaxStatus",
		           	data: {
		           		id:id,
		           		faxStatus:faxStatus,
						r_ : new Date().getTime()
					},
		           	success : function(data) {  
		           		var returnVal = data.split(":");
		           		art.dialog.tips(returnVal[1]);
		           		if(returnVal[0]=="true"){
		            		if(faxStatus==1){
		            			$("#Fax_"+id).attr("value","2");
		            			$("#TaxStatus_"+id).html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Button.taxStatus1'/>");
		            			$("#faxBtn_"+id).text("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Button.taxStatus2'/>");
		            			$("#faxBtn_"+id).attr("class","a_cancel");
		            		}else{
		            			$("#Fax_"+id).attr("value","1");
		            			$("#TaxStatus_"+id).html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Button.taxStatus2'/>");
		            			$("#faxBtn_"+id).text("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Button.taxStatus1'/>");
		            			$("#faxBtn_"+id).attr("class","a_gret");
		            		}  
		            		//queryList();
	            		}
	            	},  
	            	error : function(data) {  
	            	}  
	            });
			}
		}
		//预存
		var a ;
		function viewPopTips(id,name) {
			$("#tid").val(id);
			var ht = $("#viewContent").html();
			a = art.dialog({title:"<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Prompt.amount.title'/>《"+name+"》",content:ht,width: 280, height: 80,lock:true});
		}
		function submit(){
			var id = $("#tid").val();
			var amount = $("#amount").val();
			var ft = /^(-?\d+)(\.\d+)?$/;
			if(!ft.test(amount)){
				art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Prompt.amount'/>",1,"error");
				return ;
			}else if(amount<0){
				art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Prompt.amount.negative'/>",1,"error");
				return ;
			}else{
			$.ajax({
				type : "POST",
				async : false,
				url : "${ctx}/pages/user/form/saveTransation",
				data : {
					id : id,
					amount : amount,
					r_ : new Date().getTime()
				},
				success : function(data) {
					var s = data.split(":");
					if (s[0] == "success") {
						art.dialog.tips(s[1],1);
						a.close();
					}else{
						art.dialog.tips(s[1],1,"error");
					}
				},
				error : function(data) {
					art.dialog.tips(data,1,"error");
				}
			}); 
			}
		}
		</script>
	</head>
	<body>
	<div class="big">
		<!--以下top state -->
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--以上top end -->
		<form:form action="orgManage" method="post" commandName="form" id="form">
		<c:if test="${form.msg!=null&&form.msg != ''}">
			<script type="text/javascript">
				art.dialog.tips('${form.msg}');
			</script>
		</c:if>
		<!--以下中间内容块开始-->
		<div class="main">
		    <div class="tablepage">
		    <h1><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.title'/></h1>
		    	<div class="t_list">
		       	  <p>
		          <span>
					<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.UserManage.Label.userName"/>：&nbsp;
					<form:input path="userName" id="userName" cssStyle="vertical-align:middle;width:100px"/>
		          </span>
		          <span>
		          	<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.UserManage.Label.accName"/>：&nbsp;
					<form:input path="uid" id="uid" cssStyle="vertical-align:middle;width:100px"/>
		          </span>
		          
		          <span>
		          	<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Label.status'/>：&nbsp;
					<select id="status" name="status" style="vertical-align:middle;width:100px">
					     <option value="" <c:if test="${form.status==''}">selected="selected"</c:if>><ingenta-tag:LanguageTag sessionKey='lang' key='Global.Page.Select.All'/></option>
					     <option value="1" <c:if test="${form.status=='1'}">selected="selected"</c:if>><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Label.status2'/></option>
					     <option value="2" <c:if test="${form.status=='2'}">selected="selected"</c:if>><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Label.statusnull'/></option>
					</select>
		          </span>
		          <span>
		          	<a href="javascript:void(0)" onclick="queryList();" class="a_gret"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.Search"/></a>
		          </span>
		          </p>
		        </div>
		        
		        <div class="page_table">
		        	<table cellspacing="0" cellpadding="0" style="width:100%;">
		            <thead>
		              <tr>
		              	<td class="atdtop"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Table.Label.name'/></td>
				        <td class="atdtop"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Table.Label.username'/></td>
				        <td class="atdtop" style="width:40px"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Table.Label.status'/></td>
				        <td class="atdtop"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Table.Label.ins'/></td>
				        <td class="atdtop" style="width:40px"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Table.Label.taxStatus'/></td>
				        <td class="atdtop"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Table.Label.createOn'/></td>
				        <td class="atdtop"><ingenta-tag:LanguageTag sessionKey='lang' key='Global.Prompt.Operating'/></td>
		              </tr>
		             </thead>
		             <tbody>
		             <c:forEach items="${list }" var="o" varStatus="index">
		             	<c:set var="cssWord" value="${index.index%2==0?'a':'b' }"/>
				        <tr>
					        <td class="${cssWord}bodytd">
					        	${o.user.name}
					        </td>
					        <td class="${cssWord}bodytd">
					        	${o.uid}
					        </td>
					        <td class="${cssWord}bodytd" id="st_lab_${o.id}">
								<c:if test="${o.status==1 }"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Label.status2'/></c:if>
			        			<c:if test="${o.status==2 }"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Label.statusnull'/></c:if>
							</td>
							<td title="${o.user.institution.name }" class="${cssWord}bodytd">
								${o.user.institution.name}
							</td>
							<td class="${cssWord}bodytd" id="TaxStatus_${o.id }">
					        <c:if test="${o.createdby==2}">
					        		<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Button.taxStatus2'/>
					        	</c:if>
						        <c:if test="${o.createdby==1}">
					        		<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Button.taxStatus1'/>
					        	</c:if>
					        </td>
					        <td class="${cssWord}bodytd">
					        	<fmt:formatDate value="${o.createdon}" pattern="yyyy-MM-dd"/>
					        </td>
					        
					        <td class="${cssWord}bodytd">
					        	<input id="status_${o.id}" type="hidden" value="<c:if test="${o.status==1}">2</c:if><c:if test="${o.status==2}">1</c:if>"/> 
					        	<input id="Fax_${o.id}" type="hidden" value="<c:if test="${o.createdby==1}">2</c:if><c:if test="${o.createdby==2}">1</c:if>"/> 
						        <a class="a_gret" onclick="viewPopTips('${o.user.id}','${o.user.institution.name }')"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Button.Stored'/></a>
						        <c:if test="${o.status==2}">
					        		<a class="a_gret" id="btn_${o.id}" onclick="changeStatus('${o.id}')"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Button.status2'/></a>
					        	</c:if>
						        <c:if test="${o.status==1}">
						        	<a class="a_cancel" id="btn_${o.id}" onclick="changeStatus('${o.id}')"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Button.status1'/></a>
					        	</c:if>
					        	<c:if test="${o.createdby==2}">
					        		<a class="a_gret" id="faxBtn_${o.id}" onclick="changeFaxStatus('${o.id}')"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Button.taxStatus1'/></a>
					           	</c:if>
						        <c:if test="${o.createdby==1}">
						        	<a class="a_cancel" id="faxBtn_${o.id}" onclick="changeFaxStatus('${o.id}')"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Button.taxStatus2'/></a>
					        	</c:if>
					        	<a class="a_gret" id="resetPwd_${o.id}" onclick="resetPwd('${o.id}')"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.OrgManage.Button.restPwd'/></a>
					        	
				        	</td>
				        </tr>
					 </c:forEach>
					 </tbody>
		             <tfoot>
		              <tr>
		                <td colspan="7" class="f_tda">
		                <ingenta-tag-v3:SplitTag first_ico="${ctx }/images/ico_left1.gif"
		                	last_ico="${ctx }/images/ico_right1.gif" 
		                	prev_ico="${ctx }/images/ico_left.gif" 
		                	next_ico="${ctx }/images/ico_right.gif" 
		                	method="post"
		                	formName="form"
		                	pageCount="${form.pageCount}" 
		                	count="${form.count}" 
		                	page="${form.curpage}" 
		                	url="${form.url}" 
		                	i18n="${sessionScope.lang}"/>
		                </td>
		               </tr>
		             </tfoot>
				</table>
		        </div>	
			</div>
	    <!--左侧内容结束-->
	    <!--右侧菜单开始 -->
	    <jsp:include page="/pages/menu?mid=user_manager" flush="true"/>
	    <!--右侧菜单结束-->
	    </div>
		<!--以上中间内容块结束-->
		</form:form>
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
		<!--移动窗口开始-->
         <div style="display: none;">
		 <div id="viewContent">
			<div class="mainlist">
				<!--内容开始-->
				<ul>
				<table style="text-align:left" cellpadding="0"	cellspacing="0" border="0" width="100%">
			        <tr class="ordercontent">
				        <td colspan="2"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.OrgManage.view.amount"/>&nbsp;<input type="text" id="tt" onkeyup="$('#amount').val(this.value)"/></td>
			        </tr>
			        <tr class="ordercontent">
				        <td colspan="2"><a id="submit1" onclick="submit();" class="a_gret"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.Submit"/></a></td>
			        </tr>
		        </table>
				</ul>
				<!--内容结束-->
			</div>
		</div>
		<!--simTestContent end-->
		</div>
		<!--移动窗口结束-->
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
		<input type="hidden" name="tid" id="tid"/>
		<input type="hidden" id="amount"/>
	</div>	
	</body>
</html>
