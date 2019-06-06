<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
		<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
		<%@ include file="/common/tools.jsp"%>
		<%@ include file="/common/ico.jsp"%>
		<script type="text/javascript">
		$(function(){
			 $(":radio").click(function(){
				document.getElementById("form").action="accessManage";
				document.getElementById("page").value="0";
				document.getElementById("form").submit();
			});	
			$("input[type='checkbox'][class='cb']").click(function(){
				var cb=$(this).attr("checked");
				if(cb=='checked'){
					$(this).next().val('1:'+ $(this).val());
				}else{
					$(this).next().val('2:'+ $(this).val());
				}
			});		
		});
		function saveRelation(){
			document.getElementById("form").action="saveRelation";
			document.getElementById("page").value="0";
			document.getElementById("form").submit();
		}		
		</script>
	</head>
	<body>
	<div class="big">
		<!--以下top state -->
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--以上top end -->
		<form:form action="accessManage" method="post" commandName="form" id="form">
		<c:if test="${form.msg!=null&&form.msg != ''}">
			<script type="text/javascript">
				art.dialog.tips('${form.msg}');
			</script>
		</c:if>
		<!--以下中间内容块开始-->
		<div class="main">
		    <div class="tablepage">
		    <h1>页面访问权限管理</h1>
		    	<div class="t_list">
		       	  <p>
		          <span>
					<input type="radio" name="userRols" style="width:auto;height:auto;" value="4" <c:if test="${form.userRols==4 }">checked</c:if> />平台管理员
		          </span>
		          <span>
		          	<input type="radio" name="userRols" style="width:auto;height:auto;" value="2" <c:if test="${form.userRols==2 }">checked</c:if>/>机构管理员	
		          </span>	
		          <span>
		          	<input type="radio" name="userRols" style="width:auto;height:auto;" value="5" <c:if test="${form.userRols==5 }">checked</c:if>/>专家用户
		          </span>
		          <span>
		          	<input type="radio" name="userRols" style="width:auto;height:auto;" value="1" <c:if test="${form.userRols==1 }">checked</c:if>/>普通注册用户
		          </span>	
		          <span>
		          	<input type="radio" name="userRols" style="width:auto;height:auto;" value="3" <c:if test="${form.userRols==3 }">checked</c:if>/>机构默认用户
		          </span>
		          <span>
		          	<a onclick="saveRelation();" class="a_gret">保存设置</a>
		          </span>
		          </p>
		        </div>
		        
		        <div class="page_table">
		        	<table cellspacing="0" cellpadding="0" style="width:100%;">
		            <thead>
		              <tr>
				        <td class="atdtop">选择</td>
						<td class="atdtop">URL</td>
		              </tr>
		             </thead>
		             <tbody>
		             <c:forEach items="${list }" var="u" varStatus="index">
		             	<c:set var="cssWord" value="${index.index%2==0?'a':'b' }"/>
				        <tr>
					        <td class="${cssWord}bodytd"><input class="cb" type="checkbox" style="width:auto;height:auto;" <c:if test="${u.isaccess==1 }" >checked="checked"</c:if> value="${u.id}">
					        <input type="hidden" name="urlIds" value="${u.isaccess}:${u.id}"/>
					        </td>
					        <td class="${cssWord}bodytd">${u.url}</td>					        
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
		
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
		
	</div>	
	</body>
</html>
