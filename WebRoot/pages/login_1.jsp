<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
<link href="${ctx}/css/login.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/soChange.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/github.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="${ctx}/js/slider.js"></script>
<script type="text/javascript" src="${ctx}/js/frameworks.js"></script>
<script type="text/javascript" src="${ctx}/js/github.js"></script>
<script type="text/javascript">
	//得到焦点触发事件
    function OnfocusFun(element,elementvalue){
		    if(element.value==elementvalue){
        		element.value="";
        		element.style.color="#000";
    		}
		}
		//离开输入框触发事件

function OnBlurFun(element,elementvalue)
{
    if(element.value==""||element.value.replace(/\s/g,"")=="")
    {
        element.value=elementvalue;
        element.style.color="#999";
    }
}
function login(){
		$.ajax({
  			type : "POST",  
			url: "${ctx}/pages/user/form/login",
			data: {
				uid:$("#uid").val(),
				pwd:$("#pwd").val(),
			//	rmb:$("#remember").attr("checked"),
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
			    	$("#loginResult").html(s[1]);
			    }
			},  
			error : function(data) {  
			    alert(data);
			}  
		});
	}
        </script>
</head>

<body>
	<div id="logo">
		<a href="javascript:void(0)"><img src="${ctx}/images/logo.jpg" width="432"
			height="44" />
		</a>
	</div>
	<div class="topBg">
		<div class="loginDiv">
			<div class="content_right">
				<div class="adPic">
					<img src="${ctx}/images/login_ad.png" />
					<p>中图书苑（CNPeReading）平台是中图公司全新开发的电子图书采选与访问一站式平台，通过与国际知名出版商、集成商的广泛合作，CNPeReading平台集成了海量外文电子图书资源，为图书馆客户和最终用户提供电子资源创新解决方案。</p>
				</div>
			</div>
			<form class="home-signup" accept-charset="UTF-8" method="post"
				action="#" autocomplete="off">
				<div style="margin: 0px; padding: 0px; display: inline;"></div>
				<dl class="form">
					<dd>
						<span id="loginResult" style="color: red;"></span>
					</dd>
				</dl>
				<dl class="form">
					<dd>
						<input type="text" value="Pick a username" id="uid"
							onfocus="OnfocusFun(this,'Pick a username')" style="color:#999"
							onblur="OnBlurFun(this,'Pick a username')" class="textfield" />
					</dd>
				</dl>
				<dl class="form">
					<dd>
						<input type="password" value="Your email" id="pwd"
							onfocus="OnfocusFun(this,'Your email')"
							onblur="OnBlurFun(this,'Your email')" style="color:#999"
							class="textfield" />
					</dd>
				</dl>
				<dl class="form">
					<dd>
						<p class="note">Tip: use at least one number and at least 7
							characters.</p>
					</dd>
				</dl>
				<p class="signup-agreement">
					By clicking on "Sign up for free" below, you agree to the <a
						href="http://help.github.com/terms" target="_blank">Terms of
						Service</a> and the <a href="http://help.github.com/privacy"
						target="_blank">Privacy Policy</a>.
				</p>
				<button class="login_btn" type="button" onclick="login();">Login Now</button>
			</form>
			<div class="cls"></div>
		</div>
	</div>
	<div class="container">
		<div class="teasers">
			<div class="teaser">
				<img class="teaser-illustration" alt="Communicate"
					src="${ctx}/images/communicate.png" width="225" height="100"/>
					<h3>发现 使用 管理 一站式体验</h3>
			</div>
			<div class="teaser">
				<img class="teaser-illustration" alt="Devices"
					src="${ctx}/images/devices.png" width="225" height="100"/>
					<h3>聚合优质资源 创新服务模式</h3>
			</div>
			<div class="teaser">
				<img class="teaser-illustration" alt="Network"
					src="${ctx}/images/network.png" width="225" height="100"/>
					<h3>为图书馆量身定制 数字资源平台</h3>
			</div>
		</div>
	</div>


	<div id="footer">
		<div class="container clearfix">
			<p class="right">
				© 2013 <span>中国图书进出口（集团）总公司</span> All rights reserved.
			</p>
			<ul id="legal">
				<li>技术支持</li>
				<li><a href="javascript:void(0)"><img src="${ctx}/images/law_login_14.jpg"
						width="189" height="41" />
				</a>
				</li>
			</ul>
		</div>
		<!-- /.container -->
	</div>

</body>
</html>
