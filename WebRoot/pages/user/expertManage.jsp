<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
		<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
		<%@ include file="/common/tools.jsp"%>
	<%@ include file="/common/ico.jsp"%></head>
	
	<script type="text/javascript">
	function queryList(){
		document.getElementById("form").action="expertManage";
		document.getElementById("form").submit();
	}
	function changeStatus(id){
		var altContent="";
		var status = $("#status_"+id).attr("value");
		if(status=="1"){
			altContent = "<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Prompt.status2'/>";
		}else{
			altContent = "<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Prompt.status1'/>";
		}
		if(window.confirm(altContent)){
			$.ajax({
				type : "POST", 
				async : false,   
	           	url: "<%=request.getContextPath()%>/pages/expertUser/form/changeStatus",
	           	data: {
	           		id:id,
	           		status:status,
					r_ : new Date().getTime()
				},
	           	success : function(data) {  
	           		var returnVal = data.split(":");
	           		art.dialog.tips(returnVal[1],1);
	           		if(returnVal[0]=="true"){
	            		if(status==1){
	            			$("#btn_"+id).text("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Label.status1'/>");
	            			$("#status_"+id).attr("value","2");
	            			$("#st_lab_"+id).html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Label.status2'/>");
	            			$("#btn_"+id).attr("class","a_cancel");
	            		}else{
	            			$("#btn_"+id).text("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Label.status2'/>");
	            			$("#status_"+id).attr("value","1");
	            			$("#st_lab_"+id).html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Label.statusNull'/>");
	            			$("#btn_"+id).attr("class","a_gret");
	            		}  
            		}
            	},  
            	error : function(data) {  
            	}  
            });
		}
	}
	function resetPwd(id){
		if(window.confirm("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Prompt.resetPwd'/>")){
			/* document.getElementById("form").action="resetPwdByAdmin";
			document.getElementById("id").value=id;
			document.getElementById("page").value=document.getElementById("txtPage").value-1;
			document.getElementById("form").submit(); */
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
	
	function dividePage(targetPage){
		if(targetPage<0){return;}
		document.getElementById("form").action="${ctx}/pages/expertUser/form/expertManage?pageCount="+${form.pageCount}+"&curpage="+targetPage;
		document.getElementById("form").submit();
	}
	
	function GO(obj){
		document.getElementById("form").action="${ctx}/pages/expertUser/form/expertManage?pageCount="+$(obj).val();
		document.getElementById("form").submit();
	}
	</script>
	<c:if test="${form.msg!=null&&form.msg != ''}">
		<script language="javascript">
			art.dialog.tips('${form.msg}');
		</script>
	</c:if>
	<body>
	<jsp:include page="/pages/header/headerData" flush="true" />

	
	<div class="main personMain">
		<!--定义 0101 头部边框-->
		<!--定义 0102 左边内容区域 开始-->
		<jsp:include page="/pages/menu?mid=Expertuser&type=2" flush="true" />
			<div class="perRight">
			<form:form action="expertManage" method="post" commandName="form" id="form">
				
				<h1>
				<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.title'/>
				</h1>
							       
			       	  <p class="blockP">			       	   
						<span class="w60 tr"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Label.name'/>：</span>
						<span class="w170"><form:input path="userName" id="userName" /></span>
				        <span class="w80 tr"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Label.userName'/>：</span>
						<span class="w170"><form:input path="uid" id="uid" /></span>
				        <span class="w80 tr"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Label.status'/>：</span>
						<span class="w170"><select id="status" name="status" >
							     <option value="" <c:if test="${form.status==''}">selected="selected"</c:if>><ingenta-tag:LanguageTag sessionKey='lang' key='Global.Page.Select.All'/></option>
							     <option value="1" <c:if test="${form.status=='1'}">selected="selected"</c:if>><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Label.status2'/></option>
							     <option value="2" <c:if test="${form.status=='2'}">selected="selected"</c:if>><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Label.statusNull'/></option>
							</select>
				        </span>
				        </p>
				      <p class="mb20 pl20">
				        <a class="orgingB" href="javascript:void(0)" onclick="queryList();" ><ingenta-tag:LanguageTag sessionKey='lang' key='Global.Button.Search'/></a>
			          </p>				          	
			        
					
		        	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="curTable">
			            
			              <tr>
					        <td width="90" align="center"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Table.Label.name'/></td>
					       	<td align="center"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Table.Label.uid'/></td>
					        <td width="140" align="center"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Table.Label.Department'/></td>
					        <td width="100" align="center"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Table.Label.Recommend'/></td>
					        <td width="80" align="center"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Table.Label.status'/></td>
					        <td width="100" align="center"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Table.Label.create'/></td>
					        <td width="450" align="center"><ingenta-tag:LanguageTag sessionKey='lang' key='Global.Prompt.Operating'/></td>
			              </tr>
			             
			            
			            
			             <c:forEach items="${list }" var="o" varStatus="index">
			             	<c:set var="cssWord" value="${index.index%2==0?'a':'b' }"/>
			              	<tr>
						        <td class="${cssWord}bodytd" align="center">${o.user.name}</td>
						        <td class="${cssWord}bodytd" align="center">${o.uid}</td>
						        <td class="${cssWord}bodytd" align="center">${o.updatedby}</td>						        
						        <td class="${cssWord}bodytd" align="center">${o.user.updatedby}/${o.user.createdby}</td>
						        
						        <td id="st_lab_${o.id}" class="${cssWord}bodytd" align="center">
						        	<c:if test="${o.status==1 }"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Label.status2'/></c:if>
						        	<c:if test="${o.status==2 }"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Label.statusNull'/></c:if>								
								</td>
						        <td class="${cssWord}bodytd" align="center"><fmt:formatDate value="${o.createdon}" pattern="yyyy-MM-dd"/></td>
						        <td class="${cssWord}bodytd" align="center">
						        	<input id="status_${o.id}" type="hidden" value="<c:if test="${o.status==1}">2</c:if><c:if test="${o.status==2}">1</c:if>"/> 
							        <c:if test="${o.status==2}">
						        		<span><a id="btn_${o.id}" href="javascript:void(0)" onclick="changeStatus('${o.id}')" class="grayA"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Button.status2'/></a></span>
						        	</c:if>
							        <c:if test="${o.status==1}">
							        	<span><a id="btn_${o.id}" href="javascript:void(0)" onclick="changeStatus('${o.id}')" class="grayA"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Button.status1'/></a></span>
						        	</c:if>
						        	<span><a id="resetPwd_${o.id}" href="javascript:void(0)" onclick="resetPwd('${o.id}')" class="blueA"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.ExpertManage.Button.resetPwd'/></a></span>
						        	
					        	</td>
					        </tr>
			              </c:forEach>
				
			             
			              <%-- <tr style="border-left-style: hidden;border-right-style: hidden;border-bottom-style: hidden;">
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
			               </tr> --%>
			             
					</table>
	        	
					<form:hidden path="id"/>
					</form:form>
					<jsp:include page="../pageTag/pageTag.jsp">
			            <jsp:param value="${form }" name="form"/>
	                </jsp:include>
				</div>
				<!--定义 0102 左边内容区域 结束-->
				<!--定义 0103 右边内容区域 开始-->
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
				
				<!--定义 0103 右边内容区域 结束-->
		</div>
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
		
	</body>
</html>
