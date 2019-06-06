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
	$(function() {
		document.onkeydown = function(e) {
			var evt = document.all ? window.event : e;
			if(13 == evt.keyCode) {
				signin();
			}
		}
		
	});
		function signin(){
			$.ajax({
	  			type : "POST",  
				url: "${ctx}/pages/user/form/login",
				data: {
					uid:$("#loginid").val(),
					pwd:$("#loginpw").val(),
					beforPath:$("#beforPath").val(),
					r_ : new Date().getTime(),
					rmb:$("#remember").attr("checked")
				},
				success : function(data) {  
				    var s = data.split("::");			     
				    if(s[0]=="success"){
				    	art.dialog.tips(s[1],1,'success');
				    	if (0 < s[2].indexOf("/pages/user/form/register")) {
				    		location="/";
				    	} else if(0 < s[2].indexOf("/index/search")){
				    		location="/";
				    	} else if(0 < s[2].indexOf("/pages/user/form/newLogin")){
				    		location="/";
				    	}else if(0 < s[2].indexOf("/pages/user/form/newLogin")){
				    		location="/";
				    	}else if(s[2]==""){
				    		location="/";
				    	}else  {
				    		location=s[2];
				    	}
				    }else{
				    	$("#tips3").show().html(s[1]);
				    }			    
				},  
				error : function(data) {  
					$("#tips3").show().html(data);
				}  
			});
		}
		</script>
</head>
<body>
	<div class="loginTop">
		<!-- 平台logo -->
		<a href="/" class="mr20"><img src="${ctx}/images/logo_yyt.png" /></a> 
		<!-- 机构logo -->
		<c:if test="${insInfo!=null }">
        	<c:if test="${insInfo.logo!=null}">
			<a href="${insInfo.logoUrl }" target="_blank">
				<img title="${insInfo.name }" src='/upload/institution/logo/${insInfo.logo}'  width="150" height="40" />
			</a>
       		</c:if>
       		<c:if test="${insInfo.logo==null || insInfo.logo==''}">
        	<a target="_blank" href="${insInfo.logoUrl }" class="pic-wrap1">${insInfo.logoNote}</a>
        	</c:if> 
        	<c:if test="${insInfo.logoNote==null || insInfo.logoNote==''}">
        	<a target="_blank" href="${insInfo.logoUrl }" class="pic-wrap1">${insInfo.name}</a>
        	</c:if> 
        </c:if>
        
	</div>
	<div class="logoCont">
		<div class="fl"><img src="${ctx}/images/banner_login1.jpg" /></div>
	    <div class="fr logoFr">
	    	<div class="logoDiv">
	    	<h1><ingenta-tag:LanguageTag key="Page.Login.Link.LoginNew" sessionKey="lang" /></h1>
	    		<p><span id="tips3"></span></P>
	            <p><input type="text" id="loginid"/></p>
	            <p><input type="password" id="loginpw"/></p>
	            <p class="oh" style="width: 320px;">
	            	<span class="fl">
	            		<input id="remember" type="checkbox" /><ingenta-tag:LanguageTag key="Page.Login.Link.RememberMe" sessionKey="lang" />
	            	</span> 
	            	<span class="fr mr50">
	            		<a href="${ctx}/pages/user/form/findPwd">
	            			<ingenta-tag:LanguageTag key="Page.Frame.Left.Link.Forget" sessionKey="lang" />
	            		</a>
	            	</span>
	            </p>
	             <span> <input type="hidden" id="beforPath" value="${beforPath }" size="200"/></span>
	            <p>
	            	<a href="javascript:;" class="logoa" onclick="signin();">
	            		<ingenta-tag:LanguageTag key="Page.Login.Button.Login" sessionKey="lang" />
	            	</a>
	            </p>
	            <p class="tr" style="width: 320px;"><a href="${ctx}/pages/user/form/register" class="mr50"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Register" sessionKey="lang" /></a>
	            </p>
	        </div>
<%-- 	        <p><ingenta-tag:LanguageTag key="Page.Login.Link.OtherLogin" sessionKey="lang" /></p> --%>
<%-- 	        <p><a href="#"><img src="${ctx}/images/ico/qq.png" /></a> <a href="#"><img src="${ctx}/images/ico/sina.png" /></a></p> --%>
	        <br /><br />
	   
	    </div>
	</div>
	<!-- 版权信息 -->
	<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
	<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
</body>
</html>
