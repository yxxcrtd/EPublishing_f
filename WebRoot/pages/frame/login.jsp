<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="signin">
<!-- 
	<div class="mainLogin">
		<h1 class="mt20 f14 fb ml10">
			<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Tips9" sessionKey="lang" />
		</h1>
		<p style="margin-top: 10px;"><div id="tips2"></div></P>
		<table width="95%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>					
					<td style="width:110px;">&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			<p class="blockP mt10">
                <span class="w60 tr"><ingenta-tag:LanguageTag key="Pages.Index.Lable.Login.Name" sessionKey="lang" />：&nbsp;</span>
                <span class="w110"><input type="text" style="width:110px;" id="loginid"></span>     
            </p>
			<p class="blockP">
                <span class="w60 tr"><ingenta-tag:LanguageTag key="Pages.Index.Lable.Login.Password" sessionKey="lang" />：&nbsp;</span>
                <span class="w110"><input type="password" style="width:110px;" id="loginpw"></span>     
            </p>
			<p class="blockP">
                <span class="w60 tr">&nbsp;</span>
                <span class="w110"><a href="javascript:void(0)" class="blueB" onclick="signin();"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Tips5" sessionKey="lang" /></a></span>     
            </p>
			</tbody>
		</table>
	</div>	
 -->
 	
<!-- 		 <div class="otheLogin"> -->
<%--         	<p><ingenta-tag:LanguageTag key="Pages.User.Other.Login" sessionKey="lang" /> </p> --%>
<!--             <p> -->
<%--                 <span><a href="javascript:void(0)"><img src="${ctx}/images/ico/sina.png" width="20" height="20" /></a></span> --%>
<%--                 <span><a href="javascript:void(0)"><img src="${ctx}/images/ico/qq.png" width="20" height="20" /></a></span> --%>
<!--                  
<!--                 <span><a href="javascript:void(0)"><img src="${ctx}/images/ico/ico_logo.png" width="20" height="20" /></a></span> -->
<!--                 -->
<!--             </p> -->
<!--         </div> -->
		<script type="text/javascript">
		function signin(){
			$.ajax({
	  			type : "POST",  
				url: "${ctx}/pages/user/form/login",
				data: {
					uid:$("#loginid").val(),
					pwd:$("#loginpw").val(),
					rmb:$("#rem").attr("checked"),
					r_ : new Date().getTime()
				},
				success : function(data) {  
				    var s = data.split(":");			     
				    if(s[0]=="success"){
				    	<c:if test="${ctx!=''}">
				    		window.location.href="${ctx}";
				    	</c:if>
				    	<c:if test="${ctx==''}">
				    		window.location.href="${domain}";
				    	</c:if>
				    }else{
				    	$("#tips2").show().html(s[1]);
				    }			    
				},  
				error : function(data) {  
					$("#tips2").show().html(data);
				}  
			});
		}
		</script>
</div>