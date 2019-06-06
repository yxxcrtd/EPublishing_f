<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<c:choose>
	<c:when test="${'en_US' == sessionScope.lang && null != sessionScope.mainUser}"><style type="text/css">.nav li{ padding: 0 5px; }</style></c:when>
	<c:otherwise><style type="text/css">.nav li{ padding: 0 20px; }</style></c:otherwise>
</c:choose>

<script type="text/javascript">
	$(function(){
		for(var i=0;i<5;i++){
			var sli=$("<li>").click(function(){
				$("#searchValue").val($(this).text());
				$("#suggestUl").hide();
				searchAll('0');
			});
			$("#suggestUl").append(sli);
		}
		
		var oInput = $("#searchValue");
		oInput.seletedIndex = -1;
		
		$("#searchValue").keyup(function(evt){
			evt = (evt) ? evt : ((window.event) ? window.event : ""); //兼容IE和Firefox获得keyBoardEvent对象
			var key = evt.keyCode?evt.keyCode:evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
			// 当键盘按下的时候获取 suggestUl 里面的 li 中的值
			oInput.options = $("#suggestUl").find("li");
			if(key == 13){ 
				searchAll('0');
			} else if (38 == key) {
				clearSelectedColor(oInput);
				oInput.seletedIndex = oInput.seletedIndex - 1;
				if (0 > oInput.seletedIndex) {
					oInput.seletedIndex = oInput.options.length - 1;
				}
		    	oInput.val(oInput.options[oInput.seletedIndex].innerHTML);
				setSelectedColor(oInput);
			} else if (40 == key) {
				oInput.focus();
				clearSelectedColor(oInput);
				oInput.seletedIndex = oInput.seletedIndex + 1;
				if (oInput.seletedIndex >= oInput.options.length) {
					oInput.seletedIndex = 0;
		    	}
		    	oInput.val(oInput.options[oInput.seletedIndex].innerHTML);
				setSelectedColor(oInput);
			}else{
				var qtext=$(this).val();
				$.ajax({
						url:"${ctx}/pages/suggest",
						data:{wt:"json",q:qtext,row:5},
						success:function(data){
							$("#suggestUl li").each(function(i,item){
								$(item).hide();
								$(item).text("");
							});
							var sgStr=data.spellcheck.suggestions[1].suggestion;
							if(sgStr && sgStr.length){
								for(var i=0;i<sgStr.length;i++){
									$("#suggestUl li:eq("+i+")").text(sgStr[i]);
									$("#suggestUl li:eq("+i+")").show();
								}
								$("#suggestUl").show();
							}
						},
						dataType:"json"
				})
			}				
		});
		var This = $("#suggestUl");
		This.on("mouseleave", function() {
			This.fadeOut(2000);
		});
		
		var searchInput = $("#searchValue");
		searchInput.on("blur", function() {
			This.fadeOut(2000);
		});
	});
	
	function clearSelectedColor(target){
	    if (target.seletedIndex >= 0) {
	        target.options[target.seletedIndex].style.background = "";
	    }
	}
	function setSelectedColor(target) {
	    target.options[target.seletedIndex].style.background = "#87C1E6";
	}
</script>
<script type="text/javascript">

//<![data[
	$(document).ready(function(){
		$("a[name='language']").click(function(){
			var lang = $(this).text();			
			if(lang=="English"){
				lang = "en_US";
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
					cnpReload();
				},
				error : function(data) {
					art.dialog.tips(data,1,'error');
				}
			});
		});
		$(".login_in").click(function(){
			$(".newul").toggle();	
		});
		$(".menudown").mouseover(function(){
			$(this).children("ul").css('display','block');
		});
		$(".menudown").mouseout(function(){
			$(this).children("ul").css('display','none');
		}); 
	});

	
	
	function searchAll(type){
		 var param = $("#searchValue").val();
		 if(param==''){
		 	art.dialog.tips("<ingenta-tag:LanguageTag key='Page.Frame.Header.Pormpt.KeyWord' sessionKey='lang' />",1,'error');
		 }else{
		 	$("#type1").val(type);
		 	$("input[name='curpage']").val(0);		 	
		 	$("#searchValue1").val(param);
		 	$("#pubType1").val('');
		 	$("#publisher1").val('');
		 	$("#pubDate1").val('');
		 	$("#taxonomy1").val('');
		 	$("#taxonomyEn1").val('');
		 	$("#order1").val('');
		 	$("#code1").val('');
		 	$("#pCode1").val('');
		 	$("#publisherId1").val('');
		 	$("#subParentId1").val('');
		 	$("#parentTaxonomy1").val('');
		 	$("#parentTaxonomyEn1").val('');
		 	$("#language").val('');	
		 	var flag = "${sessionScope.specialInstitutionFlag}" ;
			var status = (flag!=""&&flag.length>0)?true:false;	
		 	if(status){
		 		$("#pubType1").val("1"); 		// 查询图书
		 		$("#notLanguage1").val(flag);	//根据后台的语言添加查询条件
				$("#local1").val("2");		//本地
		 	}		 	
		 	if(${sessionScope.selectType==1}){
		 		$("#lcense1").val("1");
		 		document.formListSearch.action="${ctx}/index/searchLicense";
		 	}else if(${sessionScope.selectType==2}){
		 		$("#lcense1").val("2");
		 		document.formListSearch.action="${ctx}/index/searchOaFree";
		 	}else{
		 		document.formListSearch.action="${ctx}/index/search";
		 	}
			document.formListSearch.submit();
		 }
	}
	function doLogin(evt){
		evt = (evt) ? evt : ((window.event) ? window.event : ""); //兼容IE和Firefox获得keyBoardEvent对象
		var key = evt.keyCode?evt.keyCode:evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
		if(key == 13){ 
			login();
		}		
	}
	
	
	// 购物车链接
	function cartList() {
		if($("#cartCount").html()=="(0)") {
			return;
		}
		window.location.href = "${ctx}/pages/cart/form/list";
	}
	function advancedSubmit1(tvalue){
		location.href=encodeURI("${ctx}/index/advancedSearchSubmit?taxonomy=" + tvalue.replace(/,/g,'、'));
	}
	function s(s) {
		location.href=encodeURI("${ctx}/index/advancedSearchSubmit?taxonomy=" + encodeURI(s));
	}
//]]-->
</script>

<div class="top">
	<c:set var="rctx" value="${ctx==null || ctx ==''?'/':ctx}" />
	<c:set var="lineheight"
		value="${sessionScope.lang=='zh_CN'?'none':'11' }" />
	<!-- <div class="fl">您好，欢迎 来到易阅通平台！ </div> -->
	<div class="fr w700">
		<ul>
			<!-- 购物车 -->
			<c:if
				test="${sessionScope.mainUser!=null&&(sessionScope.mainUserLevel==2 || sessionScope.mainUserLevel==5 || sessionScope.mainUserLevel==1)}">
				<!-- href="${ctx}/pages/cart/form/list"> -->
				<li><a href="${ctx}/pages/cart/form/list"><ingenta-tag:LanguageTag
							key="Page.Frame.Header.Lable.Cart" sessionKey="lang" /> <span
						class="red" id="cartCount"><c:if
								test="${sessionScope.totalincart!=null}">(${sessionScope.totalincart})</c:if></span></a></li>
			</c:if>

			<!-- 中英文切换 -->
			<li class="quicklic"><a name="language" href="javascript:;"><ingenta-tag:LanguageTag
						key="Global.Page.Language" sessionKey="lang" /></a></li>

			<!-- 关于我们 -->
			<li><a href="${ctx}/aboutus"><ingenta-tag:LanguageTag
						key="setting.content.aboutus" sessionKey="lang" /></a></li>
			<!-- 帮助 -->
			<li><a href="${ctx }/help"><ingenta-tag:LanguageTag
						sessionKey="lang" key="setting.content.help" /></a></li>

			<!-- 个人中心 -->
			<c:if test="${sessionScope.mainUser!=null}">
				<%--  <c:if test="${sessionScope.mainUserLevel!=2}"> --%>
				<li class="meaudown"><a href="javascript:void(0)"><ingenta-tag:LanguageTag
							sessionKey="lang" key="setting.content.center" /></a>
					<ul class="personal">
						<li>
							<p>
								<a class="grey"><ingenta-tag:LanguageTag sessionKey="lang"
										key="Page.Frame.Personal.Information" /></a>
							</p>
							<p>
								<a
									href="<c:if test="${sessionScope.mainUserLevel==1 || sessionScope.mainUserLevel==5 }">${ctx }/pages/expertUser/form/expertDetail</c:if><c:if test="${sessionScope.mainUserLevel==2 }">${ctx }/pages/orgUser/form/orgUserDetail</c:if>"><ingenta-tag:LanguageTag
										sessionKey="lang"
										key="Page.Frame.Personal.Information.Information" /></a>
							</p>
							<p>
								<a href="${ctx}/pages/user/form/resetPwd"><ingenta-tag:LanguageTag
										sessionKey="lang"
										key="Page.Frame.Personal.Information.Password" /></a>
							</p>
						</li>
						<li>
							<p>
								<a class="grey"><ingenta-tag:LanguageTag sessionKey="lang"
										key="Page.Frame.Personal.Search.Administration" /></a>
							</p>
							<p>
								<a href="${ctx }/pages/user/form/searchHistory?type=1"><ingenta-tag:LanguageTag
										sessionKey="lang"
										key="Page.Frame.Personal.Search.Administration" /></a>
							</p>
						</li>
						<li>
							<p>
								<a class="grey"><ingenta-tag:LanguageTag sessionKey="lang"
										key="Pages.order.management.title" /></a>
							</p>
							<p>
								<a href="${ctx }/pages/order/form/list"><ingenta-tag:LanguageTag
										sessionKey="lang" key="Pages.User.Instaccount.Label.list" /></a>
							</p>
							<p>
								<a href="${ctx }/pages/user/form/myTranLog"><ingenta-tag:LanguageTag
										sessionKey="lang" key="Pages.User.Instaccount.Label.myTranLog" /></a>
							</p>
						</li>
						<li>
							<p>
								<a class="grey"><ingenta-tag:LanguageTag sessionKey="lang"
										key="Pages.Alerts.Lable.Alerts.Titles" /></a>
							</p>
							<p>
								<a href="${ctx}/pages/user/form/mySubjectAlerts?pCode=all"><ingenta-tag:LanguageTag
										sessionKey="lang" key="Page.Frame.Personal.Remind.Remind" /></a>
							</p>
							<p>
								<a href="${ctx}/pages/user/form/subjectAlerts?pCode=all"><ingenta-tag:LanguageTag
										sessionKey="lang" key="Page.Frame.Personal.Remind.Science" /></a>
							</p>
						</li>
						<li class="noBorBot">
							<p>
								<a class="grey"><ingenta-tag:LanguageTag sessionKey="lang"
										key="Page.Frame.Personal.CollectionManager" /></a>
							</p>
							<p>
								<a href="${ctx }/pages/favourites/form/favorites"><ingenta-tag:LanguageTag
										sessionKey="lang"
										key="Page.Frame.Personal.Collection.Favorite" /></a>
							</p>
							<p>
								<a href="${ctx }/pages/user/form/recommend?type=1&isCn=true"><ingenta-tag:LanguageTag
										sessionKey="lang" key="Page.Frame.Personal.Recommend.History" /></a>
							</p>
						</li>
					</ul></li>
			</c:if>
			<c:if test="${sessionScope.mainUser!=null }">
				<li>
					<p>
						<a onclick="loginout();" href="javascript:void(0)"><span></span>
						<ingenta-tag:LanguageTag key="Page.Frame.Left.Link.loginOut"
								sessionKey="lang" /></a>
					</p>
				</li>
				<li class="meaudown"><a class="login_in"
					title="${sessionScope.mainUser.name }">
						${sessionScope.mainUser.name } </a>
					<ul>
						<li class="quick_loginlib"><c:if
								test="${sessionScope.mainUserLevel!=4} ">
								<p>
									<a
										href="<c:if test="${sessionScope.mainUserLevel==1 || sessionScope.mainUserLevel==5 }">${ctx }/pages/expertUser/form/expertDetail</c:if><c:if test="${sessionScope.mainUserLevel==2 }">${ctx }/pages/orgUser/form/orgUserDetail</c:if>">
										<span>>></span>
									<ingenta-tag:LanguageTag sessionKey="lang"
											key="Pages.User.MyAccount.Label.expertDetail" />
									</a>
								</p>
							</c:if> <%-- <p><a onclick="loginout();"><span>>></span><ingenta-tag:LanguageTag key="Page.Frame.Left.Link.loginOut" sessionKey="lang" /></a></p> --%>
						</li>
					</ul></li>
			</c:if>
			<%-- </c:if> --%>
			<c:if test="${sessionScope.mainUser==null}">
				<!-- 注册 -->
				<li><a href="${ctx}/pages/user/form/register" class="login_in"><ingenta-tag:LanguageTag
							key="Page.Login.Link.Register" sessionKey="lang" /></a></li>
				<li><a href="${ctx}/pages/user/form/newLogin"><ingenta-tag:LanguageTag
							key="Page.Frame.Left.Lable.Tips5" sessionKey="lang" /></a></li>

				<!-- 登录 -->
				<%-- 	        	<li class="meaudown"><a href="javascript:void(0)" class="login_in"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Tips5" sessionKey="lang" /></a> --%>
				<!-- 	            	<ul class="loginPop"> -->
				<!-- 	                	<li> -->
				<!-- 	                    	<table width="100%" border="0" cellspacing="0" cellpadding="0"> -->
				<!-- 	                          <tr> -->
				<%-- 	                            <td class="tr"><ingenta-tag:LanguageTag key="Pages.Index.Lable.Login.Name" sessionKey="lang" />：&nbsp;</td> --%>
				<!-- 	                            <td><input id="loginuid" type="text" name="name" onkeydown="doLogin(event);"/></td> -->
				<!-- 	                          </tr> -->
				<!-- 	                          <tr> -->
				<%-- 	                            <td class="tr"><ingenta-tag:LanguageTag key="Pages.Index.Lable.Login.Password" sessionKey="lang" />：&nbsp;</td> --%>
				<!-- 	                            <td><input id="loginpwd" type="password" name="password" onkeydown="doLogin(event);"/></td> -->
				<!-- 	                          </tr> -->
				<!-- 	                          <tr> -->
				<!-- 	                            <td colspan="2"> -->
				<%-- 	                            	<span><a href="${ctx}/pages/user/form/findPwd" class="blue" style="margin-left: 5px;"><ingenta-tag:LanguageTag key="Page.Frame.Left.Link.Forget" sessionKey="lang" /></a></span> --%>
				<%-- 	                                <c:if test="${sessionScope.lang == 'en_US'}"><br /></c:if> --%>
				<!-- 	                                <span style="margin-left: 5px;"> -->
				<!-- 	                                	<input id="remember" type="checkbox"/> -->
				<%-- 	                                	<ingenta-tag:LanguageTag key="Page.Frame.Left.Link.Remember" sessionKey="lang" /> --%>
				<!-- 	                                </span> -->
				<!-- 	                            </td> -->
				<!-- 	                          </tr> -->
				<!-- 	                          <tr> -->
				<%-- 	                            <td align="center" colspan="2"><a class="a_BlueBig" href="javascript:void(0)" onclick="login();"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Tips5" sessionKey="lang" /></a></td> --%>
				<!-- 	                          </tr> -->
				<!-- 	                          <tr> -->
				<!-- 	                            <td colspan="2" class="pl10 pt10"><ingenta-tag:LanguageTag key="Page.Login.Link.LoginOther" sessionKey="lang" /></td>  -->
				<!-- 	                          </tr> -->
				<!-- 	                          <tr> -->
				<!-- 	                            <td colspan="2"><p> -->
				<%-- 	                                <span class="ml10"><a href="javascript:void(0)"><img src="${ctx}/images/ico/sina.png" width="20" height="20" /></a></span> --%>
				<%-- 	                                <span class="ml10"><a href="javascript:void(0)"><img src="${ctx}/images/ico/qq.png" width="20" height="20" /></a></span> --%>
				<!-- 	                                 
				<!--                    <span class="ml10"><a href="javascript:void(0)"><img src="${ctx}/images/ico/ico_logo.png" width="20" height="20" /></a></span> -->
				<!-- 	                                -->
				<!-- 										</p></td> -->
				<!-- 								</tr> -->
				<!-- 							</table> -->
				<!-- 						</li> -->
				<!-- 					</ul></li> -->
			</c:if>
		</ul>
	</div>
</div>
<form:form action="${form.url}" method="post" modelAttribute="form"
	commandName="form" name="formListSearch" id="formListSearch">
	<form:hidden path="searchsType" id="type1" />
	<form:hidden path="searchValue" id="searchValue1" />
	<form:hidden path="searchValue2" id="searchValue2" />
	<form:hidden path="searchsType2" id="searchsType2" />
	<form:hidden path="pubType" id="pubType1" />
	<form:hidden path="language" id="language1" />
	<form:hidden path="publisher" id="publisher1" />
	<form:hidden path="pubDate" id="pubDate1" />
	<form:hidden path="taxonomy" id="taxonomy1" />
	<form:hidden path="taxonomyEn" id="taxonomyEn1" />
	<form:hidden path="searchOrder" id="order1" />
	<form:hidden path="lcense" id="lcense1" />

	<form:hidden path="code" id="code1" />
	<form:hidden path="pCode" id="pCode1" />
	<form:hidden path="publisherId" id="publisherId1" />
	<form:hidden path="subParentId" id="subParentId1" />
	<form:hidden path="parentTaxonomy" id="parentTaxonomy1" />
	<form:hidden path="parentTaxonomyEn" id="parentTaxonomyEn1" />
	<form:hidden path="keywordCondition" id="keywordCondition1" />
	<form:hidden path="notKeywords" id="notKeywords1" />
	<form:hidden path="author" id="author1" />
	<form:hidden path="title" id="title1" />
	<form:hidden path="curpage" id="curpage" />
	<form:hidden path="pageCount" id="pageCount" />
	<form:hidden path="fullText" id="fullText1" />
	<form:hidden path="nochinese" id="nochinese" />
	<form:hidden path="local" id="local1" />
	<form:hidden path="notLanguage" id="notLanguage1" />

</form:form>
<div class="second">
	<div class="logo">
		<span class="fl mr20"> <a href="${rctx}"><img
				src="${ctx}/images/logo.png" width="159" height="33" /></a>
		</span> <span class="fl"> <c:if test="${insInfo!=null }">
				<c:if test="${insInfo.logo!=null}">
					<a href="${insInfo.logoUrl }" target="_blank"> <img
						title="${insInfo.name }" src='/upload/institution/logo/${insInfo.logo}' width="150"
						height="40" />
					</a>
				</c:if>
				<c:if test="${insInfo.logo==null || insInfo.logo==''}">
					<a target="_blank" href="${insInfo.logoUrl }" class="pic-wrap">${insInfo.logoNote}</a>
				</c:if>
				<c:if test="${insInfo.logoNote==null || insInfo.logoNote==''}">
					<a target="_blank" href="${insInfo.logoUrl }" class="pic-wrap">${insInfo.name}</a>
				</c:if>
			</c:if>
		</span>
	</div>
	<div class="nav">
		<ul>
			<li class="meaudown"
				<c:if test="${sessionScope.lang == 'en_US'}"></c:if>><a
				href="javascript:;">A-Z</a>
				<ul class="letter">
					<%
						for (int i = 65; i < 91; i++) {
							char a = (char) i;
					%>
					<li><a
						href="${ctx}/index/advancedSearchSubmit?prefixWord=<%=a %>"><%=a%></a>
					<li>
						<%
							}
						%>
					
					<li><a href="${ctx}/index/advancedSearchSubmit?prefixWord=0">0-9</a></li>
				</ul></li>

			<li class="meaudown"
				<c:if test="${'en_US' == sessionScope.lang}"></c:if>><a
				href="javascript:;"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Header.Lable.COCTitle" /></a>
				<ul class="cnpeClass">
					<div id="category"></div>
				</ul> <!--             	 <ul class="cnpeClass"> --> <%-- 	            	 <c:forEach items="${subList}" var="s" varStatus="index"> --%>
				<%-- 	            	 	<c:if test="${sessionScope.lang=='zh_CN'}"> --%>
				<%-- 	            	 		<li><a href="javascript:;" onclick="s('${s.code} ${s.name}')">${s.code} ${s.name} </a></li> --%>
				<%-- 	            	 	</c:if> --%> <%-- 	            	 	<c:if test="${sessionScope.lang!='zh_CN'}"> --%>
				<%-- 	            	 		<li><a href="javascript:;" onclick="s('${s.code} ${s.nameEn}')">${s.code} ${s.nameEn} </a></li> --%>
				<%-- 	            	 	</c:if> --%> <%-- 	            	 </c:forEach> --%>
				<!--                 </ul> --></li>

			<c:if test="${'0' != show}">
				<li class="meaudown">
					<a href="javascript:void(0)"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Left.Lable.Publications" /></a>
					<ul <c:if test="${sessionScope.lang == 'en_US'}">style="width: 210px;"</c:if>>
						<div id="menu"></div>
					</ul>
				</li>
			</c:if>
			
			<c:if test="${sessionScope.mainUser!=null}">
				<%--  <c:if test="${sessionScope.mainUserLevel!=1 && sessionScope.mainUserLevel!=5}"> --%>
				<c:if test="${sessionScope.mainUserLevel == 2}">
					<li class="meaudown"
						<c:if test="${sessionScope.lang == 'en_US'}"></c:if>><a
						href="javascript:void(0)"><ingenta-tag:LanguageTag
								sessionKey="lang" key="Page.Frame.Header.Lable.IC" /></a>
						<ul class="organization">
							<li>
								<p>
									<a class="grey"><ingenta-tag:LanguageTag sessionKey="lang"
											key="Pages.User.Instaccount.Label.contentTitle5" /></a>
								</p>
								<p>
									<a href="${ctx}/pages/orgUser/form/personDetail"><ingenta-tag:LanguageTag
											sessionKey="lang"
											key="Pages.User.Instaccount.Label.orgUserDetail" /></a>
								</p>
								<p>
									<a href="${ctx}/pages/user/form/logo"><ingenta-tag:LanguageTag
											sessionKey="lang" key="Pages.User.Instaccount.Label.logo" /></a>
								</p>
								<p>
									<a href="${ctx }/pages/markData/form/manager"><ingenta-tag:LanguageTag
											sessionKey="lang" key="Pages.User.Instaccount.Label.MARC" /></a>
								</p>
							</li>
							<li>
								<p>
									<a class="grey"><ingenta-tag:LanguageTag sessionKey="lang"
											key="Pages.User.Instaccount.Label.Subscribe" /></a>
								</p>
								<p>
									<a
										href="${ctx}/pages/user/form/mySubscription/public?searchType=1"><ingenta-tag:LanguageTag
											sessionKey="lang"
											key="Pages.User.Instaccount.Label.All.subscribe" /></a>
								</p>
								<p>
									<a
										href="${ctx}/pages/user/form/mySubscription/public?searchType=1"><ingenta-tag:LanguageTag
											sessionKey="lang" key="Pages.User.Instaccount.Label.book" /></a>
								</p>
								<p>
									<a
										href="${ctx}/pages/user/form/mySubscription/public?searchType=2"><ingenta-tag:LanguageTag
											sessionKey="lang" key="Pages.User.Instaccount.Label.journal" /></a>
								</p> <%--                    <p><a href="${ctx}/pages/user/form/mySubscription/public?searchType=7"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.journal.Period"/></a></p>
 --%>
								<p>
									<a
										href="${ctx}/pages/user/form/mySubscription/public/collection?searchType=99"><ingenta-tag:LanguageTag
											sessionKey="lang"
											key="Pages.User.Instaccount.Label.collection" /></a>
								</p>
							</li>
							<li>
								<p>
									<a class="grey"><ingenta-tag:LanguageTag sessionKey="lang"
											key="Pages.order.management.title" /></a>
								</p>
								<p>
									<a href="${ctx}/pages/order/form/inslist"><ingenta-tag:LanguageTag
											sessionKey="lang" key="Pages.User.Instaccount.Label.list" /></a>
								</p>
								<p>
									<a href="${ctx}/pages/user/form/insTranLog"><ingenta-tag:LanguageTag
											sessionKey="lang"
											key="Pages.User.Instaccount.Label.myTranLog" /></a>
								</p>
							</li>
							<li class="noBorBot">
								<p>
									<a class="grey"><ingenta-tag:LanguageTag sessionKey="lang"
											key="Pages.technocracy.title" /></a>
								</p>
								<p>
									<a href="${ctx}/pages/expertUser/form/expertManage"> <ingenta-tag:LanguageTag
											sessionKey="lang"
											key="Pages.User.Instaccount.Label.expertManage" />
									</a>
								</p>
								<p>
									<a href="${ctx}/pages/expertUser/form/register"> <ingenta-tag:LanguageTag
											sessionKey="lang" key="Pages.User.Instaccount.Label.register" />
									</a>
								</p>
							</li>
							<li class="noBorBot">
								<p>
									<a class="grey"><ingenta-tag:LanguageTag
											key="Pages.User.Instaccount.Label.recommend.Manager"
											sessionKey="lang" /></a>
								</p>
								<p>
									<a href="${ctx }/pages/user/form/myrecommend?type=1&isCn=true">
										<ingenta-tag:LanguageTag
											key="Pages.User.Instaccount.Label.myrecommend"
											sessionKey="lang" />
									</a>
								</p>
							</li>
							<li class="noBorBot" style="width:114px;">
								<p>
									<a class="grey"><ingenta-tag:LanguageTag sessionKey="lang"
											key="Pages.User.Instaccount.Label.title5" /></a>
								</p>
								<p>
									<a
										href="${ctx}/pages/user/form/accesslogNew?pubtype=1&type=2&access=1&languagetype=2&btn=1"><ingenta-tag:LanguageTag
											key="Pages.Plays.statistical.title" sessionKey="lang" /></a>
								</p>
								<p>
									<a
										href="${ctx}/pages/user/form/accesslogNew?pubtype=1&type=2&access=1&languagetype=1&btn=1"><ingenta-tag:LanguageTag
											key="Pages.Chinese.statistical.title" sessionKey="lang" /></a>
								</p>
								<p>
									<a
										href="${ctx}/pages/user/form/accesslogNew?pubtype=2&type=2&access=1&languagetype=1&btn=1"><ingenta-tag:LanguageTag
											key="Pages.Journal.statistical.title" sessionKey="lang" /></a>
								</p> 
								<%-- <p><a href="${ctx}/pages/user/form/accesslogNew?pubtype=2&type=2&access=1&languagetype=2&btn=1"><ingenta-tag:LanguageTag key="Pages.JournalCn.statistical.title" sessionKey="lang"/></a></p> --%>
							</li>
						</ul></li>
				</c:if>
			</c:if>
		</ul>
	</div>
	<div class="searchIndex">
		<ul>
			<li><input type="text" class="searchInput" id="searchValue" /></li>
			<ul class="seaOver" id="suggestUl" style="display: none;"></ul>
			<li class="searImg">
				<%-- <a href="javascript:void(0)"><img src="${ctx}/images/search2.gif" /></a> --%>

				<input type="image" onclick="searchAll('0');"
				src="${ctx }/images/search2.gif"
				title="<ingenta-tag:LanguageTag key="Page.Frame.Header.Lable.Search" sessionKey="lang" />" />

				<ul class="searClassify">
					<li><a href="javascript:void(0)" onclick="searchAll('0');"><ingenta-tag:LanguageTag
								key="Page.Frame.Header.Lable.FullText" sessionKey="lang" /></a></li>
					<li><a href="javascript:void(0)" onclick="searchAll('1');"><ingenta-tag:LanguageTag
								key="Page.Frame.Header.Lable.Title" sessionKey="lang" /></a></li>
					<li><a href="javascript:void(0)" onclick="searchAll('2');"><ingenta-tag:LanguageTag
								key="Page.Frame.Header.Lable.Author" sessionKey="lang" /></a></li>
					<li><a href="javascript:void(0)" onclick="searchAll('3');"><ingenta-tag:LanguageTag
								key="Page.Frame.Header.Lable.ISBN" sessionKey="lang" /></a></li>
					<li><a href="javascript:void(0)" onclick="searchAll('4');"><ingenta-tag:LanguageTag
								key="Page.Frame.Left.Lable.Publisher" sessionKey="lang" /></a></li>
				</ul>
			</li>
			<li class="advSearch"><a href="${ctx }/index/advancedSearch"><ingenta-tag:LanguageTag
						key="Page.Frame.Header.Lable.AdvSearch" sessionKey="lang" /></a>
				<ul class="searClassify">
					<li><a href="${ctx }/index/advancedSearch"><ingenta-tag:LanguageTag
								key="Page.Frame.Header.Lable.AdvSearch" sessionKey="lang" /></a></li>
					<c:if
						test="${sessionScope.mainUser!=null&&(sessionScope.mainUserLevel==2 || sessionScope.mainUserLevel==5 || sessionScope.mainUserLevel==1)}">
						<li><a href="${ctx }/pages/user/form/searchHistory?type=1"><ingenta-tag:LanguageTag
									sessionKey="lang" key="Pages.User.SearchHistory.tag1" /></a></li>
					</c:if>
					<li><a href="${ctx }/help#hl1"><ingenta-tag:LanguageTag
								key="setting.search.help" sessionKey="lang" /></a></li>
				</ul></li>
		</ul>
	</div>
</div>

