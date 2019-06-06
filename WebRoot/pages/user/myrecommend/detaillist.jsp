<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
		<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
		<%@ include file="/common/tools.jsp"%>		
		<script type="text/javascript">
		$(function(){
			var val=$("#bookTitle").val();
		//	alert(val);
		//	$("table").before("<p>"+val+"</p>");
			$("#pTitle").text(val);
		});
			//<![data[
				function senfe(e) {
					var s = 1.2;
		                        var s2 = 8;
					var obj = e.parentNode;
					var oh = parseInt(obj.offsetHeight);
					var h = parseInt(obj.scrollHeight);
					var nh = oh;
			              
						if(obj.getAttribute("oldHeight") == null){
						obj.setAttribute("oldHeight", oh);
					}else{
						var oldh = Math.ceil(obj.getAttribute("oldHeight"));
					}
					
					
					var reSet = function(){
						if (oh<h) {
							e.innerHTML = "- 隐藏留言";
							if(nh < h){
								nh = Math.ceil(h-(h-nh)/s);
								obj.style.height = nh+"px";
							}else{
								window.clearInterval(IntervalId);
							}
						} else {
		                                        e.innerHTML = "+ 显示留言";
		                                        if(nh > oldh){
								nhh = Math.ceil((nh-oldh)/s2);
		                                                nh = nh-nhh;
								obj.style.height = nh+"px";
							}else{
								window.clearInterval(IntervalId);
							}
		                                         }
					}
					var IntervalId = window.setInterval(reSet,10);
				}
			//]]-->
			function showMessage(title,data){
				art.dialog({title:title,content: data.replace("[dyh]","'").replace("[syh]",'"'),lock:true,width:500});
			}
		</script>
	<%@ include file="/common/ico.jsp"%></head>
	<body>
	<jsp:include page="/pages/header/headerData" flush="true" />
	<div class="main personMain">
		<jsp:include page="/pages/menu?mid=recommended&type=2" flush="true" />
		<!--定义01 mainContainer 内容区开始-->
		
			<!--定义 0101 头部边框-->
			<div class="perRight">
				<!--定义 0102 左边内容区域 开始-->
				<h1>
					<ingenta-tag:LanguageTag sessionKey="lang" key="Page.Recommend.Detail.Title"/>
				</h1>
				<p id="pTitle"></p>
					
		        	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="curTable">
		            
		              <tr>
		              	<td width="120" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Recommend.Detail.Lable.Presenter"/></td>
		              	<td align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Recommend.Lable.Reason"/></td>						    
					    <td width="200" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Recommend.Detail.Lable.Presenter.email"/></td>
					    <td width="200" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Recommend.Detail.Lable.Presenter.date"/></td>
		              </tr>
		            
		            
		             <c:forEach items="${list}" var="r" varStatus="index">
		             	<input type="hidden" name="bookTitle" id="bookTitle" value="${r.recommend.publications.title }" />
				        <c:set var="cssWord" value="${index.index%2==0?'a':'b' }"/>
				        <tr>				        
					        <td align="center">
					        	${r.user.name}
					        	<%-- <c:if test="${r.remark!=null && fn:trim(r.remark)!='' }">
					        		<a title="<ingenta-tag:LanguageTag sessionKey="lang" key="Page.Index.Search.Lable.Note"/>" onclick="showMessage('${r.user.name}','${fn:replace(fn:replace(fn:trim(r.remark),"\'","[dyh]"),"\"","[syh]")}')"><img src="${ctx}/images/msg.png"/></a>
					        	</c:if> --%>
					        </td>
					        <td align="center">${r.remark}</p></td>
						    <td align="center">
						    <c:if test="${r.user.email!=null && r.user.email!=''}">${r.user.email}</c:if>
						    <c:if test="${r.user.email==null || r.user.email==''}"> </c:if>
						    </td>
						    <td align="center"><fmt:formatDate value="${r.createdon}" pattern="yyyy-MM-dd"/></td>
				        </tr>
					 </c:forEach>
					
		            <%-- 
		              <tr>
		                <td colspan="4" class="f_tda">
		                <ingenta-tag-v3:SplitTag first_ico="${ctx }/images/ico_left1.gif"
		                	last_ico="${ctx }/images/ico_right1.gif" 
		                	prev_ico="${ctx }/images/ico_left.gif" 
		                	next_ico="${ctx }/images/ico_right.gif" 
		                	method="get"
		                	pageCount="${form.pageCount}" 
		                	count="${form.count}" 
		                	page="${form.curpage}" 
		                	url="${form.url}" 
		                	i18n="${sessionScope.lang}"/>
		                </td>
		               </tr>
		             --%>
		            
				</table>
		        
				<!--定义 0102 左边内容区域 结束-->

				<jsp:include page="../../pageTag/pageTag.jsp">
						<jsp:param value="${form }" name="form"/>
				</jsp:include>

			</div>
			<!--定义 0103 右边内容区域 开始-->
			<%-- 	<jsp:include page="/pages/menu?mid=fav" flush="true" /> --%>
				<!--定义 0103 右边内容区域 结束-->
	
		</div>
		<!--定义01 mainContainer 内容区结束-->
		
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>
