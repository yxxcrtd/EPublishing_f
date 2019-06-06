<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
		<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
		<%@ include file="/common/tools.jsp"%>
		<script type="text/javascript" src="${ctx }/js/PopUpLayer.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/flexpaper/css/flexpaper.css" />
		<link href="${ctx}/css/reset.css" rel="stylesheet" type="text/css"/>
		<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css"/>
		<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
		<link rel="styleSheet" type="text/css" href="${ctx}/css/jquery.fancybox.css" />

		<%@ include file="/common/ico.jsp"%>
		
<script type="text/javascript">
$(function(e) {
	//	getRightList();
 	  //getBottomList();
 	  	getCount();
 	  	getCount1();
 	  	// 收藏和取消收藏
		$(".favourite2").on("click", function() {
			var This = $(this);
			$.get("${ctx}/pages/favourites/form/commit", { pubId : This.attr("id") }, function(data) {
				if ("success" == data) {
					This.find("a").attr("class", "ico ico_collection2");
					This.find("img").attr("src", "${ctx}/images/favourite.png");
					This.find("span").html("<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
					art.dialog.tips('<ingenta-tag:LanguageTag key='Controller.Favourites.commit.success' sessionKey='lang' />', 1, 'success');
				} else if ("del" == data) {
					This.find("a").attr("class", "ico ico_collection");
					This.find("img").attr("src", "${ctx}/images/unfavourite.png");
					This.find("span").html("<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' />");
					art.dialog.tips('<ingenta-tag:LanguageTag key='Controller.Favourites.commit.cancel' sessionKey='lang' />', 1, '');
				}
			});
		});
	});
	//******************************弹出层--开始*********************************//
	/*
	 *弹出本页指定ID的内容于窗口
	 *id 指定的元素的id
	 *title: window弹出窗的标题
	 *width: 窗口的宽,height:窗口的高
	 */
		function showTipsWindown(title, id, width, height) {
			tipsWindown(title, "id:" + id, width, height, "true", "", "true", id);
		}
		function confirmTerm() {
			parent.closeWindown();
		}
		//弹出层调用
		function popTips(pid,type) {
			//先将信息放到对应的标签上title, code, type, pubSubject
			art.dialog.open("${ctx}/pages/recommend/form/edit?pubid="+pid,{title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/>",top: 100,width: 550, height: 450,lock:true});
		}
		function getCount(){
			var testExp=/^\d+$/;
			$(".chapt_author_p2 span span").each(function(i,item){
				var pagNubStr=$(item).text().trim();
				var numbers=pagNubStr.split("-");
				//console.info(numbers);
				var range=1;
				if(numbers){
					var spage=numbers[0].trim().match(testExp)?parseInt(numbers[0].trim()):numbers[0];
					if(numbers.length==2){
						var epage= numbers[1].trim().match(testExp)?parseInt(numbers[1].trim()):numbers[1];
						range=epage-spage+1;					
					}
					$(item).text(pagNubStr+" ("+range+")");
				}
			})
		}
		function getCount1(){
			var testExp=/^\d+$/;
			$(".chapt_author_p3 ").each(function(i,item){
				var pagNubStr=$(item).text().trim();
				var numbers=pagNubStr.split("-");
				//console.info(numbers);
				var range=1;
				if(numbers){
					var spage=numbers[0].trim().match(testExp)?parseInt(numbers[0].trim()):numbers[0];
					if(numbers.length==2){
						var epage= numbers[1].trim().match(testExp)?parseInt(numbers[1].trim()):numbers[1];
						range=epage-spage+1;					
					}
					$(item).text(pagNubStr+" ("+range+")");
				}
			})
		}
	//在线阅读起关闭
	function confirmTerm2(id) {
		parent.closeWindown();
		$.ajax({
			type : "POST",
			async : false,
			url : "${ctx}/pages/publications/form/release",
			data : {
				id : id,
				r_ : new Date().getTime()
			},
			success : function(data) {
			},
			error : function(data) {
			}
		});
	}
	//获取资源弹出层调用
	function popTips2(pid) {
	alert(pid);
	/* 	showTipsWindown("",
				'simTestContent', $(window).width()*0.6, $(window).height()*0.65); */
				/* alert(pid); */
				art.dialog.open("${ctx}/pages/publications/form/getResource?pubid="+pid,{id : "getResourceId",title:"",top: 200,width: 340, height: 200,lock:true}); /* <ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/> */
	}
	//在线阅读弹出层调用
	function viewPopTips(id,page) {
		var url="";
		var tmp=window.open("about:blank","","scrollbars=yes,resizable=yes,channelmode") ;          
            //tmp.focus() ;
		if(page=='0'){
			url = "${ctx}/pages/view/form/view?id="+id;
		}else{
			url = "${ctx}/pages/view/form/view?id="+id+"&nextPage="+page;
		}
		//首先Ajax查询要阅读的路径
		if('${form.obj.free}'=='2'||'${form.obj.oa}'=='2'){
		//window.location.href=url;
		 tmp.location=url;
	}else{
	$.ajax({
		type : "POST",
		async : false,
		url : "${ctx}/pages/publications/form/getUrl",
		data : {
			id : id,
			nextPage:page,
			r_ : new Date().getTime()
		},
		success : function(data) {
			var s = data.split(";");
			if (s[0] == "success") {
				if(s[1].indexOf('/pages/view/form/view')>=0){
					//window.location.href=s[1];
					tmp.location=s[1];
				}else{
					//window.location.href="${ctx}/pages/view/form/view?id="+id+"&webUrl="+s[1];
					tmp.location="${ctx}/pages/view/form/view?id="+id+"&webUrl="+s[1];
				}
			}else if(s[0] == "error"){
				art.dialog.tips(s[1],1,'error');
			}
		},
			error : function(data) {
				art.dialog.tips(data,1,'error');
			}
		});
		}
	}
	//******************************弹出层--结束*********************************//
</script>
<script type="text/javascript">
	function searchByCondition(type,value,a1,a2,a3,a4){
		document.formList.action="${ctx}/pages/publications/form/list";
		if(type=="searchValue"){
			$("#searchValue1").val(value);
			if(${sessionScope.selectType==1}){
	 			$("#lcense1").val("1");
	 			document.formList.action="${ctx}/index/searchLicense";
		 	}else{
		 		document.formList.action="${ctx}/index/search";
		 	}
		}else if(type=="type"){
			$("input[name='pubType']").val(value);
		}else if(type=="publisher"){
			$("input[name='publisher']").val(value); 
			$("input[name='publisherId']").val(a1);
		}else if(type=="pubDate"){
			$("input[name='pubDate']").val(value);
		}else if(type=="taxonomy"){
			$("#taxonomy1").val(value);
			$("#pCode1").val(a1);
			$("#code1").val(a2);
			$("#subParentId1").val(a3);
			$("#parentTaxonomy1").val(a4);
			document.formList.action="${ctx }/pages/subject/form/list";
		}else if(type=="taxonomyEn"){
			$("#taxonomyEn1").val(value);
			$("#pCode1").val(a1);
			$("#code1").val(a2);
			$("#subParentId1").val(a3);
			$("#parentTaxonomyEn1").val(a4);
			document.formList.action="${ctx }/pages/subject/form/list";
		}
		$("#page").val(0);
		$("#pageCount").val(10);
		$("#order1").val('');
		$("#lcense1").val('${sessionScope.selectType}');
		document.formList.submit();
	}
	function addToCart(pid, ki) {
					var price = $("#priceSel").val();
					$.ajax({
						type : "POST",
						url : "${ctx}/pages/cart/form/add",
						data : {
							pubId : pid,
							priceId : price,
							kind : ki,
							r_ : new Date().getTime()
						},
						success : function(data) {
							var s = data.split(":");
							if (s[0] == "success") {
								art.dialog.tips(s[1],1);//location.reload();
								$("#add_"+pid).css("display","none");
								$("#cartCount").html("["+s[2]+"]");
								$("#priceSel").css("display","none");
							}else{
								art.dialog.tips(s[1],1,'error');
							}
						},
						error : function(data) {
							art.dialog.tips(data,1,'error');
						}
					});
				}
				function addFavourites(pid) {
					$.ajax({
						type : "POST",
						url : "${ctx}/pages/favourites/form/commit",
						data : {
							pubId : pid,
							r_ : new Date().getTime()
						},
						success : function(data) {
							var s = data.split(":");
							if (s[0] == "success") {
								art.dialog.tips(s[1],1);//location.reload();
							//	$("#favourites_"+pid).attr("src","${ctx}/images/collect_light.png");
								$("#favourites_img_"+pid).attr("src","${ctx}/images/favourite.png");
								$("#favourites_"+pid).attr("class","ico ico_collection2");
							//	$("#favourites_"+pid).attr("class","link light_collect");
								$("#favourites_"+pid).removeAttr("onclick");
								$("#favourites_"+pid).attr("title","<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
							//	$("#favourites_"+pid).html("<img id='favourites_${p.id }' src='${ctx }/images/ico/ico13-blank.png' class='vm' /><ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
								$("#favourites_"+pid).css("cursor","auto");
							}else{
								art.dialog.tips(s[1],1,'error');
							}
						},
						error : function(data) {
							art.dialog.tips(data,1,'error');
						}
					});
				}
				function recommendSubmit() {
					$.ajax({
						type : "POST",
						url : "${ctx}/pages/recommend/form/submit",
						data : {
							pubid : $("#pubid").val(),
							note : $("#rnote").val(),
							r_ : new Date().getTime()
						},
						success : function(data) {
							var s = data.split(":");
							if (s[0] == "success") {
								art.dialog.tips(s[1],1,'error');
								confirmTerm();
							}else{
								alert(s[1]);
							}
						},
						error : function(data) {
							art.dialog.tips(data,1,'error');
						}
					});
				}
			/* 	function recommendSubmit(pid) {
					
					$.ajax({
						type : "POST",
						url : "${ctx}/pages/recommend/form/submit",
						data : {
					//		pubid : $("#pubid").val(),
							pubid :pid,
							note : $("#rnote").val(),
							r_ : new Date().getTime()
						},
						success : function(data) {
							var s = data.split(":");
							if (s[0] == "success") {
								art.dialog.tips(s[1],1,'error');
								confirmTerm();
							}else{
								alert(s[1]);
							}
						},
						error : function(data) {
							art.dialog.tips(data,1,'error');
						}
					});
				}
				
				function recommendSubmittwo(pid) {
					
					$.ajax({
						type : "POST",
						url : "${ctx}/pages/recommend/form/submit",
						data : {
					//		pubid : $("#pubid").val(),
							pubid :pid,
							note : $("#rnote").val(),
							r_ : new Date().getTime()
						},
						success : function(data) {
							var s = data.split(":");
							if (s[0] == "success") {
								art.dialog.tips(s[1],1,'error');
								confirmTerm();
								$("#recommand_img_"+pid).attr("src","${ctx}/images/ico/ico16-blank.png");
								$("#recommand_"+pid).removeAttr("onclick");
						//		$("#recommand_"+pid).attr("title","<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
							}else{
								alert(s[1]);
							}
						},
						error : function(data) {
							art.dialog.tips(data,1,'error');
						}
					});
				}
				 */
				
				
</script>

	<%@ include file="/common/ico.jsp"%>
</head>
	<body id="uboxstyle">

		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--定义01 mainContainer 内容区开始-->
		<div class="main personMain">

      
      <%-- <h1><ingenta-tag:LanguageTag key="Page.Publications.Journal.Aritcle.Lable.Info" sessionKey="lang" /></h1> --%>
       		<c:set var="objnews">${form.obj.latest!=null&&form.obj.latest>0 }</c:set>
			<c:set var="objoa">${form.obj.oa!=null&&form.obj.oa==2 }</c:set>
			<c:set var="objfree">${form.obj.free!=null&&form.obj.free==2 }</c:set>
			<c:set var="objcollection">${form.obj.inCollection!=null&&form.obj.inCollection>0 }</c:set>
			<c:set var="add1" value="${form.obj.priceList!=null&&fn:length(form.obj.priceList)>0&&form.obj.free!=2&&form.obj.oa!=2&&sessionScope.mainUser!=null && form.obj.subscribedUser<=0&&(form.obj.buyInDetail<=0&&form.obj.exLicense>=0)}"/>
			<c:if test="${add1==false }">
				<c:set var="objadd" value="false"/>
			</c:if>
			<c:if test="${add1==true &&form.obj.subscribedIp>0 }">
				<c:if test="${sessionScope.mainUser.institution.id==sessionScope.institution.id&&sessionScope.mainUser.level==2 }">
				<c:set var="objadd" value="false"/>
				</c:if>
				<c:if test="${sessionScope.mainUser.institution.id==sessionScope.institution.id &&sessionScope.mainUser.level!=2 }">
				<c:set var="objadd" value="true"/>
				</c:if>
				<c:if test="${sessionScope.mainUser.institution.id!=sessionScope.institution.id}">
				<c:set var="objadd" value="true"/>
				</c:if>
			</c:if>
			<c:if test="${add1==true &&(form.obj.subscribedIp==null||form.obj.subscribedIp<=0) }">
				<c:set var="objadd" value="true"/>
			</c:if>
			<c:if test="${add1==false }">
				<c:set var="objadd" value="false"/>
			</c:if>
			<!-- 
			
			 -->
			<c:set var="objfavourite" value="${sessionScope.mainUser!=null&&form.obj.favorite<=0 }"/>
			<c:set var="objrecommand" value="${(form.obj.recommand>0||sessionScope.mainUser.institution!=null) &&(form.obj.subscribedIp==null||form.obj.subscribedIp<=0)&&(form.obj.free!=2&&form.obj.oa!=2)}"/>
	        <c:set var="objlicense">${(form.obj.subscribedIp!=null||form.obj.subscribedUser!=null)&&(form.obj.subscribedIp>0||form.obj.subscribedUser>0) }</c:set>
        <!--列表内容开始-->
        <c:if test="${objlicense==true || objfree==true || objoa==true }"><div class="h2_list"></c:if>
		<c:if test="${objlicense==false && objfree==false && objoa==false }"><div class="h1_list"></c:if> 
		
		
   <div class="oh">
   		<div class="prodDetal">
   			<div class="oh">
   			<div class="pridDetalCont cBlack">
        		<%-- <p class="blockP">
                    	<span class="w100 tr">文章标题：</span>
                        <span>${fn:replace(fn:replace(fn:replace(form.obj.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}</span>
                </p> --%>

					<div class="oh f14" style="padding-top: 15px;">
						
						<div class="fl tr mr10">
							<c:if test="${objnews==true || objfree==true || objoa==true  || objcollection==true}">
									<%-- <c:if test="${objnews==true }"><img src="${ctx }/images/n.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.New" sessionKey="lang" />"/></c:if> --%>
									<c:if test="${objfree==true }">
										<img src="${ctx }/images/ico/f.png"
											title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Free" sessionKey="lang" />" />
									</c:if>
									<c:if test="${objoa==true }">
										<img src="${ctx }/images/ico/o.png"
											title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.OA" sessionKey="lang" />" />
									</c:if>
									<%-- <c:if test="${objcollection==true }"><img src="${ctx }/images/c.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Collection" sessionKey="lang" />"/></c:if> --%>
							</c:if>
						
								<c:if
									test="${objlicense==true }">
									<img src="${ctx }/images/ico/ico_open.png" class="vm" />
								</c:if>
								<c:if
									test="${objlicense==false && objfree==false && objoa==false }">
									<img src="${ctx }/images/ico/ico_close.png" class="vm" />
								</c:if>
								<img src="${ctx }/images/ico/ico5.png" class="vm" />
							</div>
							<div class="fl w900 fb">
								${fn:replace(fn:replace(fn:replace(form.obj.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}
							</div>
						</div>


						<c:if test="${form.obj.available==5 }">
							<div style="clear:both; padding:2px 0 10px 20px;">
								<img src="${ctx }/images/ico_20.png"
									style="vertical-align:middle; margin-right:8px;" /> <span
									style="color:red"><ingenta-tag:LanguageTag
										sessionKey="lang"
										key="Pages.publications.article.Label.available" /></span>
							</div>
						</c:if>
          		 <c:if test="${not empty form.obj.author}">
                        <p class="blockP">
                        	<span class="w100 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.author"/>：</span>
                        	<c:if test="${not empty form.obj.author}">
			            	 <c:set var="authors" value="${fn:split(form.obj.author,',')}" ></c:set>
		               		 <c:forEach items="${authors}" var="a" >
	                		 <a href='${ctx }/index/search?type=2&isAccurate=1&searchValue2=${a}'>${a}</a> &nbsp;
		                	 </c:forEach> 
		                	</c:if>
                        </p>
				</c:if>
         		<p class="blockP">
	          	<span class="w100 tr"><ingenta-tag:LanguageTag key="Pages.publications.article.Label.publisher" sessionKey="lang" />：</span>
			  	<span><a href='${ctx }/index/search?type=4&isAccurate=1&searchValue2=${form.obj.publisher.name }'>${form.obj.publisher.name }</a></span>
	       		</p>
	       		
	       		<c:if test="${form.obj.eissn!=null}">
	       		<p class="blockP">
                    	<span class="w100 tr">E-ISSN：</span>
                        <span>${form.obj.eissn}</span>
                </p>
                </c:if>
	          <p class="blockP">
	            <span class="w100 tr">ISSN：</span>
	            <span>${fn:split(form.obj.publications.code,'|')[0]}</span>
	          </p>
          
          
	          <p class="blockP">
	            <span class="w100 tr"><ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Source" sessionKey="lang" />：</span>
	            <span>
	            	<a href="${ctx}/pages/publications/form/journaldetail/${form.obj.publications.id}">${fn:replace(fn:replace(fn:replace(form.obj.publications.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}</a>,
					<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" />${form.obj.volumeCode }, 
					<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Issue" sessionKey="lang" />${form.obj.issueCode }, ${form.obj.year }-${form.obj.month}, 
					<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.PageRange" sessionKey="lang" /> : ${form.obj.startPage }-${form.obj.endPage}
				</span>
	          </p>
<%-- 	         <c:if test="${form.obj.free!=2&&form.obj.oa!=2}"> --%>
<!-- 	          	 <p class="blockP"> -->
<%-- 	          		<span class="w100 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.price"/>：</span> --%>
<%-- 	             	<span>${form.obj.lcurr}<fmt:formatNumber value="${form.obj.listPrice}" pattern="0.00" /></span> --%>
<!-- 	          	</p> -->
<%--           	</c:if> --%>
			          	
          	<c:if test='${sessionScope.mainUser!=null}'>
   				<c:if test="${pricelist!=null && fn:length(pricelist)>0 && fn:length(pricelist)<2}">
					<p class="blockP">
						<span class="w100 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.PurchasePrice"/>：</span>
						<span> 
							<c:forEach items="${pricelist}" var="pr" varStatus="indexPr">
								<c:if test="${pr.price!=0}">
									<c:if test="${pr.type==2 }">L</c:if>
									<c:if test="${pr.type==1 }">P</c:if>${pr.complicating}-${pr.price }${pr.currency }
								</c:if>
								<c:if test="${pr.price==0}">-</c:if>
							</c:forEach>
						</span>
					</p>
				</c:if>
				<c:if test="${pricelist!=null && fn:length(pricelist)>=2 }">
					 <p class="blockP">
						<span class="w100 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.PurchasePrice"/>：</span>
						<span> 
							<select id="price_${form.obj.id }" name="price_${form.obj.id}" >
								<c:forEach items="${pricelist}" var="pr" varStatus="indexPr">
										<option value="${pr.id }" >
											<c:if test="${pr.price!=0}">
												<c:if test="${pr.type==2 }">L</c:if>
												<c:if test="${pr.type==1 }">P</c:if>${pr.complicating}-${pr.price }${pr.currency }
											</c:if>
											<c:if test="${pr.price==0}">-</c:if>
										</option>
								</c:forEach>
							</select>
						</span>
					</p>
				</c:if>
			</c:if>
          
          <p class="mt20">
          		<c:if test="${pricelist!=null && fn:length(pricelist)>0 }">
	          		<span>
					  <a href="javascript:void(0)" class="ico ico_cart" id="add_cart" onclick="addToCart('${form.obj.id}',1,1)">
					  	<ingenta-tag:LanguageTag key="Page.Index.Search.Link.AddToCart" sessionKey="lang" />
					  </a>
				    </span>
				</c:if>
          <c:set var="license" value="${form.obj.subscribedIp>0||form.obj.subscribedUser>0||form.obj.free==2||form.obj.oa==2 }"/>
          <c:if test="${objlicense||objadd||objfavourite||objrecommand||objoa==true||objfree==true}">
			  
			<%-- 		<c:if test="${objlicense==true||objoa==true||objfree==true}">
					
					<a class="link gret_eye" onclick="viewPopTips('${form.obj.id}','0')">
								<ingenta-tag:LanguageTag key="Page.Pop.Title.OLRead" sessionKey="lang" />
							</a>
					</c:if> --%> 
						<c:if test="${pricelist!=null && fn:length(pricelist)>0 &&add==true }">
			          		<span>
							  <a href="javascript:void(0)" class="ico ico_cart" id="add_cart" onclick="addToCart('${form.obj.id}',1,1)">
							  	<ingenta-tag:LanguageTag key="Page.Index.Search.Link.AddToCart" sessionKey="lang" />
							  </a>
						    </span>
						</c:if>
						<c:if test="${license==false }">
							<span>
							<a href="javascript:void(0)" id="resource_div" class="ico ico_do" onclick="popTips2('${form.obj.id}');">
							<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
							</a>
							</span>
						</c:if>
						<c:if test="${license==true }">
						<span>
							<a href="javascript:void(0)" id="resource_div" class="ico ico_doin" onclick="popTips2('${form.obj.id}');">
							<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
							</a>
							</span>
							</c:if>
					<c:if test="${form.obj.priceList!=null && fn:length(form.obj.priceList)>0&&objadd==true}">
						<select name="price_${form.obj.id }" id="price_${form.obj.id }" class="price">
							<c:forEach items="${form.obj.priceList }" var="pr" varStatus="indexPr">
							<option value="${pr.id }"><c:if test="${pr.type==2 }">L</c:if><c:if test="${pr.type==1 }">P</c:if>${pr.complicating}-${pr.price }${pr.currency }</option>
							</c:forEach>
						</select>
					</c:if>					
		          	<c:if test="${objadd }">
		          	<a id="add_${form.obj.id}" class="link gret_cart" onclick="addToCart('${form.obj.id}',1,'1')">
								<ingenta-tag:LanguageTag key="Page.Index.Search.Link.Buy" sessionKey="lang" />
							</a>	
					<!--  <img id="add_${form.obj.id}" src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/images/cart.png" style="vertical-align:middle;margin: 5px 0;cursor:pointer;" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Buy' sessionKey='lang' />" onclick="addToCart('${form.obj.id}',1,'1')"/>
					&nbsp;-->
					</c:if>
					
					<c:if test="${objrecommand}">
						<a id="recommand_${form.obj.id }" href="javascript:void(0)" class="ico ico_recommed" onclick="popTips('${form.obj.id}')">
								<ingenta-tag:LanguageTag key="Page.Index.Search.Link.Recommend" sessionKey="lang" />
						</a>	
						 <%-- <img id="recommand_${form.obj.id }" src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/images/recommend.png" style="vertical-align:middle;margin: 5px 0;cursor:pointer;" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' />" onclick="popTips('${form.obj.id}')"/> --%>
						&nbsp;
					</c:if>
					
					<c:if test="${objfavourite}">
						<span class="favourite2" id="${form.obj.id}">
						   	<a href="javascript:;" class="ico ico_collection">
						   		<span><ingenta-tag:LanguageTag key="Page.Index.Search.Link.Favorite" sessionKey="lang" /></span>
							</a>
						</span>
					</c:if>
					<c:if test="${sessionScope.mainUser!=null && !objfavourite}">
						<span class="favourite2" id="${form.obj.id}">
						   	<a href="javascript:;" class="ico ico_collection2">
								<span><ingenta-tag:LanguageTag key="Page.Index.Search.Link.collected" sessionKey="lang" /></span>
							</a>
						</span>
					</c:if>
			</c:if>        
             </p>
        
        <!--列表内容结束--> 
         <p class="mt10">
         <c:if test="${prevId!=null && prevId != '' }">
         <span class="mr5"><a href="${ctx}/pages/publications/form/journalarticle/${prevId}"><ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Prev" sessionKey="lang" /></a></span>
         </c:if>
         <c:if test="${prevId==null || prevId == '' }">
         <span class="mr5"><ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Prev" sessionKey="lang" /></span>
         </c:if>
         <span class="mr5"><a href="${ctx}/pages/publications/form/journaldetail/${form.obj.publications.id}?issueId=${form.obj.issue.id}"><ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Menu" sessionKey="lang" /></a></span>
          <c:if test="${nextId!=null && nextId != '' }">
          <span class="mr5"><a href="${ctx}/pages/publications/form/journalarticle/${nextId}"><ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Next" sessionKey="lang" /></a></span>
          </c:if>
          <c:if test="${nextId==null || nextId == '' }">
         <span class="mr5"><ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Next" sessionKey="lang" /></span>
         </c:if>
        </p>
      </div> 
    </div>
     
     <c:if test="${'' != form.obj.remark && form.obj.remark!=null &&form.obj.remark!='[无简介]'}">
      <div class="mt10">
        <h1 class="h1Tit borBot"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.kournal.article.Abstract"/></h1>
        <p class="fontFam">
        	${fn:replace(fn:replace(fn:replace(form.obj.remark,"&lt;","<"),"&gt;",">"),"&amp;","&")}
        </p>
      </div>
      </c:if>
      
      <%-- <c:if test="${reference!=null && fn:length(reference)>0 }">				
      <div class="chapter">
        <h1 class="chapt_title"><span><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.kournal.article.References.citations"/></span></h1>        
		 <c:forEach items="${reference}" var="r" varStatus="index">		 
		 	<div class="chapt_cont" <c:if test="${index.index==fn:length(reference)-1 }">style="border-bottom:none"</c:if>>
          		<h2>${r}</h2>
        	</div>	
		</c:forEach>		
      </div>
      </c:if> --%>
      <%-- <c:if test="${toplist!=null && fn:length(toplist)>0 }">
      <div class="chapter">
        <h1 class="chapt_title"><span><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.kournal.article.Related.content"/></span></h1>
			 <c:forEach items="${toplist}" var="t" varStatus="index">
			  	<div class="chapt_cont" <c:if test="${index.index==fn:length(toplist)-1 }">style="border-bottom:none"</c:if>>
		          <h2><span>&gt;&gt;</span><a href="${ctx}/pages/publications/form/article/${t.publications.id}">${t.publications.title }</a></h2>
		          <p class="chapt_author">
		              <ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Author" sessionKey="lang" />：&nbsp;
		              <c:set var="tauthors" value="${fn:split(t.publications.author,',')}" ></c:set>
				        <c:forEach items="${tauthors}" var="ta" >
				        	<a href='${ctx}/index/search?type=2&isAccurate=1&searchValue="${ta}"'>${ta}</a> &nbsp;
				        </c:forEach>  
				  </p>
		          <p class="chapt_author">
		          	<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Source" sessionKey="lang" />：&nbsp;
			        <ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" />
			        ${t.publications.volumeCode },
					<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Issue" sessionKey="lang" />
					${t.publications.issueCode }, 
					${t.publications.year }-${t.publications.month} ,
					<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.PageRange" sessionKey="lang" /> 
					${t.publications.startPage }-${t.publications.endPage}(${t.publications.endPage-t.publications.startPage+1})
		          </p>

		        </div>
			 
			 </c:forEach>
      </div>
      </c:if> --%>
      <c:if test="${samelist!=null && fn:length(samelist)>0 }">
      <div class="mt10">
        <h1 class="h1Tit borBot"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.kournal.article.Related.content"/></h1>
			 <c:forEach items="${samelist}" var="t" varStatus="index">
			  	<div class="block" <c:if test="${index.index==fn:length(samelist)-1 }">style="border-bottom:none"</c:if>>
		          <div class="fl w40 mt2">
                	  <%--  <img src="${ctx}/images/ico/ico_open.png" width="16" height="16" />
                	    --%>
                	    <c:set var="obj1free">${t.free!=null&&t.free==2 }</c:set>
                	    <c:set var="obj1license">${(t.subscribedIp!=null||t.subscribedUser!=null)&&(t.subscribedIp>0||t.subscribedUser>0) }</c:set>
                	    <c:set var="obj1oa">${t.oa!=null&&t.oa==2 }</c:set>
                	    
                	   <c:if test="${obj1license==true}"><img src="${ctx }/images/ico/ico_open.png" width="16" height="16"/></c:if>
                	   <c:if test="${obj1free==true}"><img src="${ctx }/images/ico/f.png" width="16" height="16"/></c:if>
                	   <c:if test="${obj1oa==true}"><img src="${ctx }/images/ico/o.png" width="16" height="16"/></c:if>
						<c:if test="${obj1license==false && obj1free==false && obj1oa==false }"><img src="${ctx }/images/ico/ico_close.png" width="16" height="16"/></c:if>
                	   
                       <img src="${ctx}/images/ico/ico5.png" width="13" height="13" />
                  </div>
                  <div class="fl w640">
                	<p><a class="a_title" href="${ctx}/pages/publications/form/article/${t.id}">${fn:replace(fn:replace(fn:replace(t.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}</a></p>
		          <p>
		              By
		              <c:set var="tauthors" value="${fn:split(t.author,',')}" ></c:set>
				        <c:forEach items="${tauthors}" var="ta" >
				        	<a href='${ctx}/index/search?type=2&isAccurate=1&searchValue2=${ta}'>${ta}</a> &nbsp;
				        </c:forEach>  
				  </p>
		          <p>
		          	<a href="${ctx}/pages/publications/form/journaldetail/${t.publications.id}">${fn:replace(fn:replace(fn:replace(t.publications.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}</a>,
			        <ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" />
			        ${t.volumeCode },
					<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Issue" sessionKey="lang" />
					${t.issueCode }, 
					${t.year }-${t.month} ,<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.PageRange" sessionKey="lang" /> :
					<span class="chapt_author_p3">
					${t.startPage }-${t.endPage} <span></span>
					</span>
		          </p>
		          <p>
		          	
				  	<span><a onclick="searchByCondition('publisher','${t.publisher.name }','${t.publisher.id}')">${t.publisher.name }</a></span>
		       		</p>
		          		<p class="mt5">
		          			<input type="hidden" id="pubid" name="pubid" value="${t.id }" />
                           
                         <%--    <span class="mr20"><a href="javascript:void(0)" class="green"><img src="${ctx}/images/ico/ico15-green.png" class="vm" /><ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" /></a></span> --%>
                            <span class="mr20">
                           <c:if test="${obj1license==false && obj1free==false && obj1oa==false }">
							<span>
							<a href="javascript:void(0)" class="blue" onclick="popTips2('${t.id}');"><img src="${ctx}/images/ico/ico15-blue.png" class="vm" />
							<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
							</a>
							</span>
						</c:if>
						<c:if test="${obj1license==true||obj1free==true || obj1oa==true }">
						<span>
							<%-- <a href="javascript:void(0)" class="green"><img src="${ctx}/images/ico/ico15-green.png" class="vm" /> --%>
							<a href="javascript:void(0)" class="green" onclick="popTips2('${t.id}');"><img src="${ctx}/images/ico/ico15-green.png" class="vm" />
							<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
							</a>
							</span>
							</c:if>
							</span>
							
                            <span class="mr20">
                            	<a id="recommand_${t.id }" href="javascript:void(0)" onclick="popTips('${t.id}');"><img src="${ctx}/images/ico/ico16-blue.png" class="vm" id="recommand_img_${t.id }"/>
									<ingenta-tag:LanguageTag key="Page.Index.Search.Link.Recommend" sessionKey="lang" />
								</a>
							</span>
							
<c:if test="${0 == t.favorite}">
	<span class="favourite" id="${t.id}">
    	<a href="javascript:;" class="">
			<img src="${ctx}/images/unfavourite.png" class="vm" /><span><ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' /></span>
		</a>
	</span>
</c:if>
<c:if test="${sessionScope.mainUser!=null && 1 == t.favorite}">
	<span class="favourite" id="${t.id}">
		<a href="javascript:;" class="blank">
			<img src="${ctx}/images/favourite.png" class="vm" /><span><ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' /></span>
		</a>
	</span>
</c:if>
                        </p>
					</div>
		        </div>
			 
			 </c:forEach>
      </div>
      </c:if>
      <%-- <div class="chapter">
        <h1 class="chapt_title"><span><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.kournal.article.info.base"/></span></h1>
        <div class="chapt_cont">
        	<ul class="chapt_cont_ul">
           	<li><h1><ingenta-tag:LanguageTag key="Pages.journal.article.Lable.Keyword" sessionKey="lang" />：&nbsp;</h1>
            	 <c:if test="${keywords!=null && fn:length(keywords)>0 }">
					<p>
					<c:forEach items="${keywords}" var="k" varStatus="index">
					${k}<br/>
					</c:forEach>
					</p>
				</c:if>            	
                    <h1><ingenta-tag:LanguageTag key="Pages.journal.article.Lable.Format" sessionKey="lang" />：&nbsp;</h1>
                    <p>PDF</p>
            </li>
            <li><h1>ISSN：</h1>
            	 <p>${form.obj.publications.code}</p>
                    <h1><ingenta-tag:LanguageTag key="Pages.journal.article.Lable.Type" sessionKey="lang" />：&nbsp;</h1>
                    <p>Research article</p>
                    <h1>DOI：&nbsp;</h1>
                    <p>${form.obj.doi}</p>
            </li>
            <li><h1><ingenta-tag:LanguageTag key="Pages.journal.article.Lable.Language" sessionKey="lang" />：&nbsp;</h1>
            	 <p>${form.obj.lang}</p>
                    
            </li>
           </ul>          	
        </div>
      </div> --%>
      </div>
      </div>
     </div>
    <!--左侧内容结束-->
    

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
		<!--定义01 mainContainer 内容区结束-->
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
		<%-- <input type="hidden" id="pubid" path="${form.obj.id}" /> --%>

	</body>
</html>
