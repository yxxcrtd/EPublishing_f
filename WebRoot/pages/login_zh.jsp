<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
<%@ include file="/common/ico.jsp"%>
<title>CNPIEC eReading</title>
<%@ include file="/common/tools.jsp"%>
<link href="${ctx}/css/login.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/soChange.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/github.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/login_zh.css" rel="stylesheet" type="text/css" />
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
	
	$(document).ready(function(){
		$("a[name='language']").click(function(){
			var lang = $(this).text();
			var url = "${ctx }/pages/login_zh.jsp";	
			if(lang=="English"){
				lang = "en_US";
				url = "${ctx }/pages/login_en.jsp";	
			}else{
				lang = "zh_CN";
			}
			$.ajax({
				type : "POST",
				url : "${ctx}/language",
				data : {
					language : lang,
					r_ : new Date().getTime()
				},
				success : function(data) {
					window.location.href = url;
				},
				error : function(data) {
					art.dialog.tips(data,1,'error');
				}
			});
		});
		document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	         login();
	     }
		};
	});
        </script>
</head>

<body>
	<div id="logo"><a href="javascript:void(0)"><img onload="AutoResizeImage(200,44,this);" src="${ctx}/images/cnpiec_logo_en_US.png" width="200"
			height="44" /></a>
	  <div id="lang"><a name="language" href="javascript:void(0)">中文</a><span style="float:left">|</span><a name="language" href="javascript:void(0)">English</a></div>
	</div>
	<div class="topBg">
		<div class="loginDiv">
			<div class="content_right">
				<div class="adPic">
					<img src="${ctx}/images/login_ad.png" width="489" height="131" />
					<p>易阅通（CNPeReading）平台是中图公司全新开发的数字资源采选与访问一站式平台，通过与国际知名出版商、集成商的广泛合作，CNPeReading平台集成了海量外文数字资源资源，为图书馆客户和最终用户提供数字资源创新解决方案。</p>
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
                <dt>帐号</dt>
					<dd>
						<input type="text" style="color:#999" tabindex="1" id="uid" class="textfield" />
					</dd>
				</dl>
				<dl class="form">
                 <dt>密码</dt>
					<dd>
						<input type="password" tabindex="2" style="color:#999" id="pwd"
							class="textfield" />
					</dd>
				</dl>
				<BR/>
				<button class="login_btn" type="button" tabindex="3" onclick="login();">登&nbsp;&nbsp;录</button>
			</form>
			<div class="cls"></div>
		</div>
	</div>
	<div class="container">
		<div class="teasers">
			<div class="teaser">
				<img class="teaser-illustration" alt="发现 使用 管理 一站式体验"
					src="${ctx}/images/communicate.png" width="225" height="100"/>
					<h3>发现 使用 管理 一站式体验</h3>
			</div>
			<div class="teaser">
				<img class="teaser-illustration" alt="聚合优质资源 创新服务模式"
					src="${ctx}/images/devices.png" width="225" height="100"/>
					<h3>聚合优质资源 创新服务模式</h3>
			</div>
			<div class="teaser">
				<img class="teaser-illustration" alt="为图书馆量身定制 数字资源平台"
					src="${ctx}/images/network.png" width="225" height="100"/>
					<h3>为图书馆量身定制 数字资源平台</h3>
			</div>
		</div>
	</div>


	<div id="footer">
		<div class="container clearfix">
        <div class="footer_right"><a href="javascript:void(0)">关于我们</a><a href="javascript:void(0)">服务条款</a></div>
			<p class="right">
			  <span>中国图书进出口（集团）总公司</span>&nbsp;©&nbsp;版权所有&nbsp;2013 
		  </p>
			<ul id="legal">
				<li>技术支持</li>
				<li><a href="javascript:void(0)"><img src="${ctx}/images/logo2.png"
						width="139" height="35" />
				</a>
				</li>
			</ul>
            
		</div>
		<!-- /.container -->
	</div>
</body>
</html>
