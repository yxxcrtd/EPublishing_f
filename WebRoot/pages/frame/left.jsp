<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
  	function seeCountMore(){
		var status = $("#moreCountStatus").val();
		if(status=="1"){
			$("li[name='countmore']").css("display","block");
			$("#moreCountStatus").val('2');
			$("#seecount").html("<ingenta-tag:LanguageTag key='Page.Frame.Count.Lable.Less' sessionKey='lang'/>...");
		}else{
			$("li[name='countmore']").css("display","none");
			$("#moreCountStatus").val('1');
			$("#seecount").html("<ingenta-tag:LanguageTag key='Page.Frame.Count.Lable.More' sessionKey='lang'/>...");
		}
	}
	function getStat(){
		var parObj=$("#update_statistic");
		$.ajax({
			type : "POST",
			async : false,    
	        url: "${ctx}/pages/left/leftData",
	        data: {},
	        success : function(data) { 
             	$(parObj).html(data);
            },  
            error : function(data) {
              	$(parObj).html(data);
            }  
      });
	}
	$(document).ready(function(){
			
				getStat();
			
	});
</script>
<!--定义 0103 右边内容区域 开始-->
<div class="rightContainer">
	<div id="signedbox" class="loginbox"></div>
	<div class="clear"></div>
	<div id="signedlink" class="signedlink">
	<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Tips1" sessionKey="lang" />
	<a href="${ctx}/pages/user/form/register" style="color:#00668f;font-weight:bold">
	<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Tips2" sessionKey="lang" /></a>
	<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Tips3" sessionKey="lang" /><br>
	<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Tips4" sessionKey="lang" /><a onclick="$('#loginbox').show()" style="cursor: pointer; color:#88272e;font-weight:bold">
	<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Tips5" sessionKey="lang" /></a>
	<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Tips6" sessionKey="lang" />.</div>
	<div class="clear"></div>
	<div id="institutionlogobox" class="institutionlogobox"></div>
	
	<div class="clear"></div>
	<div id="signoutlinkdiv" style="display:none;margin-left:12px;*margin-left:24px">
		<a id="signoutlink" onclick="loginout()" title="" style="cursor: pointer; color: #88272E; border-bottom: 1px solid #88272E;"><ingenta-tag:LanguageTag key="Page.Frame.Left.Link.loginOut" sessionKey="lang" /></a>
		<a id="signinlink" onclick="$('#loginbox').show()" title="" style="cursor: pointer; color: #88272E; border-bottom: 1px solid #88272E;"><ingenta-tag:LanguageTag key="Page.Frame.Left.Link.AddLogin" sessionKey="lang" /></a>
	</div>			
	<div id ="loginbox" class="formInput">
		<span style="float:left;margin-left:17px"><ingenta-tag:LanguageTag key="Pages.Index.Lable.Login.Name" sessionKey="lang" /></span>
		<input id="loginuid" type="text" name="value1"/>
		<div class="clearit"></div>
		<span style="float:left;margin-left:17px"><ingenta-tag:LanguageTag key="Pages.Index.Lable.Login.Password" sessionKey="lang" /></span>
		<input id="loginpwd" type="password" name="value1"/>
		<div class="clearit"></div>
		<ul>
			<li>
				<a href="${ctx}/pages/user/form/findPwd"><ingenta-tag:LanguageTag key="Page.Frame.Left.Link.Forget" sessionKey="lang" /></a>
			</li>
			<li>
				<input id="remember" type="checkbox" value="<ingenta-tag:LanguageTag key="Global.Button.Cancel" sessionKey="lang" />" />
				<span>&nbsp;<ingenta-tag:LanguageTag key="Page.Frame.Left.Link.Remember" sessionKey="lang" /></span>
			</li>
		</ul>
		<div class="clearit"></div>
		<div class="button">
			<a onclick="login();" href="javascript:void(0)">
			<p>
				<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Tips5" sessionKey="lang" />
			</p>
			<img title="" 
				src="${ctx}/images/picnic_button_right.png"/></a>
			<a onclick="$('#loginuid').val('');$('#loginpwd').val('')" href="javascript:void(0)">
			<p>
				<ingenta-tag:LanguageTag key="Global.Button.Clear" sessionKey="lang" />
			</p>
			<img title="" 
				src="${ctx}/images/picnic_button_right.png"/></a>
		</div>
	</div>
	
	<div class="clear"></div>
	
	<div id="management" class="box">
		<div class="boxInner">
			<p><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Account" sessionKey="lang" /></p>
			<div id="role1"><a href="${ctx}/pages/user/form/myaccount" title="" ><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.MyAccount" sessionKey="lang" /></a></div>
			<div id="role2"><a href="${ctx}/pages/user/form/myaccount" title="" ><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.InsAccount" sessionKey="lang" /></a></div>
			<div id="role4"><a href="${ctx}/pages/user/form/myaccount" title="" ><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.CNPIEC" sessionKey="lang" /></a></div>
		</div>
	</div>
	
	<div id="register" class="box">
		<div class="boxInner">
			<p>
				<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Tips7" sessionKey="lang" />:
			</p>
			<div><a href="${ctx}/pages/user/form/register"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Tips7" sessionKey="lang" /></a></div> 
			<!--<div><a href="${ctx}/pages/orgUser/form/register">注册</a></div>
			<div><a href="${ctx}/pages/expertUser/form/register">注册</a></div>-->
		</div>
	</div>

	<div class="clear"></div>
	
	<div class="box">
		<div class="boxInner">
			<p>
				<ingenta-tag:LanguageTag key="Page.Frame.Count.Lable.LastUpdate" sessionKey="lang" />:
			</p>
			<ul id="update_statistic" style="text-align:left;margin:10px 0 5px 10px;">
				
				<img src="${ctx}/images/loading.gif"/>

			</ul>
			<div class="clear"></div>
		</div>
	</div>
	
	<c:if test="${sessionScope.mainUser!=null}">
	<div class="clear"></div>
	<div class="box">
		<div class="boxInner">
			<p style="background-color:#6aadd7;">
				<b><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Tools" sessionKey="lang" /></b>
			</p>
			<ul>
				<li>
					<a href="${ctx}/pages/user/form/subjectAlerts?pCode=all"><ingenta-tag:LanguageTag key="Page.Frame.Left.Link.AlertSetting" sessionKey="lang" /></a>
				</li>
				<li>
					<a href="${ctx}/pages/user/searchHistory.jsp"><ingenta-tag:LanguageTag key="Page.Frame.Left.Link.SavedSearch" sessionKey="lang" /></a>
				</li>
				<li>
					<a href="${ctx}/pages/favourites/form/favorites"><ingenta-tag:LanguageTag key="Page.Frame.Left.Link.Favorites" sessionKey="lang" /></a>
				</li>
				<li>
					<a href="${ctx}/pages/user/form/mySubscription/all"><ingenta-tag:LanguageTag key="Pages.User.Subscription.All" sessionKey="lang" /></a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	</c:if>
	<div class="clear"></div>
	<div class="box">
		<div class="boxInner">
			<p>
				<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Type" sessionKey="lang" />:
			</p>
			<ul>
				<li>
					<a><img src="${ctx}/images/icon01.jpg"/><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.New" sessionKey="lang" /></a>
				</li>
				<li>
					<a><img src="${ctx}/images/icon02.jpg"/><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.OA" sessionKey="lang" /></a>
				</li>
				<!-- 
				<li>
					<a><img src="${ctx}/images/icon03.jpg"/><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.License" sessionKey="lang" /></a>
				</li>
				 -->
				<li>
					<a><img src="${ctx}/images/icon04.jpg"/><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Free" sessionKey="lang" /></a>
				</li>
				<li>
					<a><img src="${ctx}/images/icon05.jpg"/><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Collection" sessionKey="lang" /></a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

</div>
<!--定义 0103 右边内容区域 结束-->
