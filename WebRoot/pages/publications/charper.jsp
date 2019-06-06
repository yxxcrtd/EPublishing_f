<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
<%@ include file="/common/tools.jsp"%>
<script type="text/javascript" src="${ctx }/js/PopUpLayer.js"></script>
<script language="javascript">
	/* 
	alert("浏览器显示区域高度"+$(window).height());
	alert("浏览器显示区域宽度"+$(window).width());
	alert("获取页面的文档高度"+$(document).height());
	alert("获取页面的文档宽度"+$(document).width()); 
	*/
	//左侧条件查询
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
					art.dialog.tips(s[1],1);
					confirmTerm();
				}else{
					alert(s[1],1,'error');
				}
			},
			error : function(data) {
				art.dialog.tips(data,1,'error');
			}
		}); 
	}
	
	//获取资源弹出层调用
	function popTips2(pid) {
	/* 	showTipsWindown("",
				'simTestContent', $(window).width()*0.6, $(window).height()*0.65); */
				/* alert(pid); */
				art.dialog.open("${ctx}/pages/publications/form/getResource?pubid="+pid,{id : "getResourceId",title:"",top: 200,width: 340, height: 200,lock:true}); /* <ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/> */
	}
	function addFavourites(pid,staus) {
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
					if(staus==1){
						$("#favourites_div_"+pid).removeAttr("onclick");
						$("#favourites_div_"+pid).attr("title","<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
						$("#favourites_div_"+pid).attr("class","ico ico_collection2");	
						$("#favourites_div_"+pid).css("cursor","auto");
					}else if(staus==2){
						$("#favourites_div_chaper"+pid).removeAttr("onclick");
						$("#favourites_div_chaper"+pid).attr("title","<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
						$("#faveimg_"+pid).attr("src","${ctx}/images/collect_light.png");
					}
				}else{
					art.dialog.tips(s[1],1,'error');
				}
			},
			error : function(data) {
				art.dialog.tips(data,1,'error');
			}
		});
	}
	
	function addToCart(pid, ki,Staust) {
		var price = $("#price_" + pid).val();
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
					if(Staust==1){
						$("#add_cart").css("display","none");
						$("#cartCount").html("["+s[2]+"]");
						$("#priceTd").html("<ingenta-tag:LanguageTag key='Page.Publications.Article.Lable.Buying' sessionKey='lang' />");
					}else if(Staust==2){
						$("#add_cart_"+pid).hide();
						$("#cartCount").html("["+s[2]+"]");
						$("#priceTd").html("<ingenta-tag:LanguageTag key='Page.Publications.Article.Lable.Buying' sessionKey='lang' />");
					}
				}else{
					art.dialog.tips(s[1],1,'error');
				}
			},
			error : function(data) {
				art.dialog.tips(data,1,'error');
			}
		});
	}
	
	
	
</script>
<script type="text/javascript">
$(document).ready(function(e) {
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

</script>
<script type="text/javascript">
function searchMe(a,type){
	var searchVal=$(a).text();
	var url='';
	if(type=='author'){
		url='${ctx }/index/search?type=2&isAccurate=1&searchValue2='+searchVal;
	}else if(type=='publisher'){
		url='${ctx }/index/search?type=2&searchsType=4&searchValue2='+searchVal;
	}
	window.location.href=url;
}
	//<![data[
	function senfe(e) {
		var s = 1.2;
		var s2 = 8;
		var obj = e.parentNode;
		var oh = parseInt(obj.offsetHeight);
		var h = parseInt(obj.scrollHeight);
		var nh = oh;

		if (obj.getAttribute("oldHeight") == null) {
			obj.setAttribute("oldHeight", oh);
		} else {
			var oldh = Math.ceil(obj.getAttribute("oldHeight"));
		}

		var reSet = function() {
			if (oh < h) {
				e.innerHTML = "- <ingenta-tag:LanguageTag key="Page.Index.Search.Desc.Hide" sessionKey="lang" />";
				if (nh < h) {
					nh = Math.ceil(h - (h - nh) / s);
					obj.style.height = nh + "px";
				} else {
					window.clearInterval(IntervalId);
				}
			} else {
				e.innerHTML = "+ <ingenta-tag:LanguageTag key="Page.Index.Search.Desc.Show" sessionKey="lang" />";
				if (nh > oldh) {
					nhh = Math.ceil((nh - oldh) / s2);
					nh = nh - nhh;
					obj.style.height = nh + "px";
				} else {
					window.clearInterval(IntervalId);
				}
			}
		}
		var IntervalId = window.setInterval(reSet, 10);
	}
//]]-->
</script>

<script type="text/javascript">
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
	//弹出层调用
	function popTips(pid) {
	/* 	showTipsWindown("",
				'simTestContent', $(window).width()*0.6, $(window).height()*0.65); */
				art.dialog.open("${ctx}/pages/recommend/form/edit?pubid="+pid,{title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/>",top: 100,width: 700, height: 400,lock:true});
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
		var of=false;
		<c:if test="${(form.obj.free!=null&&form.obj.free==2) || (form.obj.oa!=null&&form.obj.oa==2) }">
			of=true;
		</c:if>  
          //首先Ajax查询要阅读的路径
	if(of){
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
				//	window.location.href="${ctx}/pages/view/form/view?id="+id+"&webUrl="+s[1];
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
<!--弹出层样式 结束  -->

<%@ include file="/common/ico.jsp"%></head>
<body id="uboxstyle">

	<jsp:include page="/pages/header/headerData" flush="true" />
	<!--定义01 mainContainer 内容区开始-->
	<div class="main personMain">
    
        
        <c:set var="add1" value="${pricelist!=null&&fn:length(pricelist)>0&&form.obj.free!=2&&form.obj.oa!=2&&sessionScope.mainUser!=null && form.obj.subscribedUser<=0&&(form.obj.buyInDetail<=0&&form.obj.exLicense>=0)}"/>
		<c:if test="${add1==false }">
			<c:set var="add" value="false"/>
		</c:if>
		<c:if test="${add1==true &&form.obj.subscribedIp>0 }">
			<c:if test="${sessionScope.mainUser.institution.id==sessionScope.institution.id&&sessionScope.mainUser.level==2 }">
			<c:set var="add" value="false"/>
			</c:if>
			<c:if test="${sessionScope.mainUser.institution.id==sessionScope.institution.id &&sessionScope.mainUser.level!=2 }">
			<c:set var="add" value="true"/>
			</c:if>
			<c:if test="${sessionScope.mainUser.institution.id!=sessionScope.institution.id}">
			<c:set var="add" value="true"/>
			</c:if>
		</c:if>
		<c:if test="${add1==true &&(form.obj.subscribedIp==null||form.obj.subscribedIp<=0) }">
			<c:set var="add" value="true"/>
		</c:if>
		<c:if test="${add1==false }">
			<c:set var="add" value="false"/>
		</c:if>
			<c:set var="favourite" value="${sessionScope.mainUser!=null&&form.obj.favorite<=0 }"/>
			<c:set var="buyBook" value="${form.subscribedIp>0||form.subscribedUser>0||form.free==2||form.oa==2 }"/>
			<c:set var="recommand" value="${sessionScope.institution!=null && (form.obj.recommand>0||sessionScope.mainUser.institution!=null) &&(form.obj.subscribedIp==null||form.obj.subscribedIp<=0)&&(form.obj.free!=2&&form.obj.oa!=2)}"/>	
		 	<c:set var="license" value="${form.obj.subscribedIp>0||form.obj.subscribedUser>0||form.obj.free==2||form.obj.oa==2 }"/>
		<!--列表内容开始-->
		 <div class="h2_list">
          
       
		<c:if test="${buyBook==false&&license==false }"><div class="h1_list"></c:if>	
        <c:if test="${license==true||buyBook==true }"><div class="h2_list"></c:if>
          <div class="oh f14">   
         	<div class="w90 fl mr10 tr">   	
	          	<c:if test="${license==true ||buyBook==true}"><img src="${ctx }/images/ico/ico_open.png" class="vm"/></c:if>
				<c:if test="${buyBook==false&&license==false }"><img src="${ctx }/images/ico/ico_close.png" class="vm"/></c:if>	
				<img src="/images/ico/infor.png" class="vm" />
			</div>
           <div >${form.obj.title}
           <c:if test="${!((form.obj.subscribedIp!=null||form.obj.subscribedUser!=null)&&(form.obj.subscribedIp>0||form.obj.subscribedUser>0))&&form.obj.buyInDetail>0}">
			<font style="color: green;font-weight: bold;"><ingenta-tag:LanguageTag key="Page.Publications.Article.Lable.Buying" sessionKey="lang" /></font>
			</c:if>
			<c:if test="${(form.obj.subscribedIp!=null||form.obj.subscribedUser!=null)&&(form.obj.subscribedIp>0||form.obj.subscribedUser>0) }" >
				<font style="color: green;font-weight: bold;"><ingenta-tag:LanguageTag key="Page.Publications.Article.Lable.Subscribe" sessionKey="lang" /></font>
			</c:if>
			</div>
          </div>
          <c:if test="${(form.obj.latest!=null&&form.obj.latest>0)||(form.obj.free!=null&&form.obj.free==2)||(form.obj.oa!=null&&form.obj.oa==2)||(form.obj.inCollection>0)}">
          <p class="p_right">
          	<c:if test="${license==false}"> 
          	<%-- <c:if test="${form.obj.latest!=null&&form.obj.latest>0}"><img src="${ctx}/images/n.png"></c:if> --%> 
          	<c:if test="${form.obj.free!=null&&form.obj.free==2 }"><img src="${ctx}/images/ico/f.png"></c:if>  
          	<c:if test="${form.obj.oa!=null&&form.obj.oa==2 }"><img src="${ctx}/images/ico/o.png"></c:if>
          	<%-- <c:if test="${form.obj.inCollection>0 }"><img src="${ctx}/images/c.png"></c:if> --%>
          	</c:if> 
          	<c:if test="${license==true}"> 
          	<%-- <c:if test="${form.obj.latest!=null&&form.obj.latest>0}"><img src="${ctx}/images/n.png"></c:if> --%> 
          	<c:if test="${form.obj.free!=null&&form.obj.free==2 }"><img src="${ctx}/images/ico/f.png"></c:if>  
          	<c:if test="${form.obj.oa!=null&&form.obj.oa==2 }"><img src="${ctx}/images/ico/o.png"></c:if>
          	<%-- <c:if test="${form.obj.inCollection>0 }"><img src="${ctx}/images/c.png"></c:if> --%>
          	</c:if>
          	</p>
          </c:if>
          </div>  
        
	<div class="oh">
        <div class="prodDetal">
            <div class="oh">
          		<div class="fl pridDetalCont">
          		<c:forEach items="${list }" var="p" varStatus="index">
                    <p class="blockP">
                    	<span class="w100 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.BookName"/>：</span>
                        <span>
	                        <a href="${ctx}/pages/publications/form/article/${p.publications.id}">
	                        	${p.publications.title }
	                        </a>
                        </span>
                    </p>
                    <c:if test="${p.author!=null}">
                    <p class="blockP">
                    	<span class="w100 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.author"/>：</span>
			            <span>${p.author}</span>
					</p>
					</c:if>
                    <p class="blockP">
				        <span class="w100 tr"><ingenta-tag:LanguageTag key="Pages.publications.article.Label.publisher" sessionKey="lang" />：</span>
				        <span><a href="javascript:;" onclick="searchMe(this,'publisher')">${p.publisher.name }</a></span>
                    </p>
                    
                      <p class="blockP">
                      	<span class="w100 tr">E-ISBN：</span>
	             		<span>${p.publications.code}</span>
                      </p>
                      <c:if test="${p.publications.sisbn !=null && p.publications.sisbn !='' }">
			            <p class="blockP">
			            <span class="w110 tr">S-ISBN：&nbsp;</span>
			            <span>${p.publications.sisbn}</span>
			            </p>
			          </c:if>
			          <c:if test="${p.publications.hisbn !=null && p.publications.hisbn !='' }">
			        	 <p class="blockP">
			            <span class="w110 tr">P-ISBN：&nbsp;</span>
			            <span>${p.publications.hisbn}</span>
			      		 </p>
			          </c:if>
			          	<p class="blockP">
			          		<span class="w100 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.CLC"/>：</span>
							<span>
							<c:set var="csName"></c:set>
							<c:set var="names"></c:set>					
							<c:forEach items="${p.publications.csList }" var="cs" varStatus="a">
							<c:set var="csName">${cs.subject.code }  <c:if test="${sessionScope.lang=='zh_CN' }">${cs.subject.name}</c:if><c:if test="${sessionScope.lang=='en_US' }">${cs.subject.nameEn }</c:if></c:set>
							<c:set var="names">${names }${csName }</c:set>
				            <c:if test="${fn:length(p.csList)!=(a.index+1) }"><c:set var="names">${names };</c:set></c:if>
							</c:forEach>					
							${p.publications.pubSubject }
							</span>
			          	</p>
			          	<p class="blockP">
			          		<span class="w100 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.Language"/>：</span>
			            	<span>${p.lang }</span>
			          	</p>
			          	
			        </c:forEach>
        				<c:if test='${sessionScope.mainUser!=null}'>
			          		<%-- <c:if test="${pricelist!=null && fn:length(pricelist)>0 && fn:length(pricelist)<2}">
								<p class="blockP">
									<span class="w110 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.PurchasePrice"/>：</span>
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
							</c:if> --%>
							  <p class="blockP">
			          		  <span class="w110 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.price"/>：</span>
			             	  <span>${form.obj.lcurr}&nbsp;<fmt:formatNumber value="${form.obj.listPrice}" pattern="0.00" /></span>
			          	      </p>
							<c:if test="${pricelist!=null && fn:length(pricelist)>=2 }">
								<p class="blockP">
									<span class="w110 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.PurchasePrice"/>：</span>
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
<%--                       	<c:if test="${license==true||buyBook==true }"> --%>
<!--                       	<p class="blockP"> -->
<%-- 							<a class="link gret_eye" onclick="viewPopTips('${form.obj.id}','0')"> --%>

<%-- 								<ingenta-tag:LanguageTag key="Page.Pop.Title.OLRead" sessionKey="lang" /> --%>
<!-- 							</a> -->
<!-- 							</p>				 -->
<%-- 						</c:if> --%>
<%-- 			          	<c:if test="${license==false&&priceStaus==false}"> --%>
<!-- 			          	<p class="blockP"> -->
<%-- 			          	<a class="cat_link cat_light_link" href="javascript:void(0)" id="add_cart" onclick="addToCart('${form.chaperID}',1)">购买章节</a>  --%>
<!-- 						</p> -->
<%-- 						</c:if> --%>
						<c:if test="${license==true||buyBook==true }">
							<a href="javascript:void(0)" id="resource_div" class="ico ico_doin" onclick="popTips2('${form.obj.id}');">
								<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
							</a>
						</c:if>
						
						<c:if test="${license==false }">
							<span>
							<a href="javascript:void(0)" id="resource_div" class="ico ico_do" onclick="popTips2('${form.obj.id}');">
							<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
							</a>
							</span>
						</c:if>
						<c:if test="${recommand}">
							<span>
							<a  href="javascript:void(0)" id="recommend_div" class="ico ico_recommed" onclick="popTips('${form.obj.id}');">
							<ingenta-tag:LanguageTag key="Page.Index.Search.Button.Recommed" sessionKey="lang" />
							</a>
							</span>
						</c:if>
						<c:if test="${favourite}">
							<span class="favourite2" id="${form.obj.id}">
							  	<a href="javascript:;" class="ico ico_collection">
							  		<span><ingenta-tag:LanguageTag key="Page.Index.Search.Link.Favorite" sessionKey="lang" /></span>
							  	</a>
							</span>
						</c:if>
						<c:if test="${sessionScope.mainUser!=null && !favourite}">
							<span class="favourite2" id="${form.obj.id}">
								<a href="javascript:;" class="ico ico_collection2">
									<span><ingenta-tag:LanguageTag key="Page.Index.Search.Link.collected" sessionKey="lang" /></span>
								</a>
							</span>
						</c:if>
			          </p>     
			        </div>
        		</div>
        	</div>
        
        <!-- <p style="height:1px; width:1px; clear:both;">&nbsp;</p>   -->      
		
        <!--列表内容结束--> 
   		<div class="mt10">
	      <c:if test="${form.obj.remark!=null && form.obj.remark!=''&& form.obj.remark!='[无简介]' }">
	     
	        <h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Pages.publications.article.Lable.Description" sessionKey="lang" /></h1>
	         <p class="fontFam">     
	          ${form.obj.remark}
	         </p>
	      </c:if>
	     </div>
      <c:if test="${Plist!=null && fn:length(Plist)>0 }">
      
      <div class="mt10 fontFam">
        <h1 class="h1Tit borBot"><ingenta-tag:LanguageTag key="Pages.publications.Chrper.All" sessionKey="lang" /></h1>
        <p class="mb5"><img src="${ctx}/images/ico/ico5-black.png" width="13" height="13" class="fl mr10 mt5" />${form.obj.title}</p>
        <c:forEach items="${Plist }" var="PA" varStatus="index">         
          <c:set var="favouritechaper" value="${sessionScope.mainUser!=null&&PA.favorite<=0 }"/>
	       <c:set var="recommandchaper" value="${sessionScope.institution!=null && (PA.recommand>0||sessionScope.mainUser.institution!=null) &&(PA.subscribedIp==null||PA.subscribedIp<=0)&&(PA.free!=2&&PA.oa!=2)}"/>		
	      
          <p class="mb5"><img src="${ctx}/images/ico/ico5.png" width="13" height="13" class="fl mr10 mt5" />
          <a href="${ctx}/pages/publications/form/charperView?id=${PA.id}&pid=${PA.publications.id}">${ PA.title}
          </a></p>
          <%-- <p class="intro">作者：${ PA.author}</p>
          <p class="chapt_author">页码：${PA.startPage} – ${PA.endPage}</p> --%>
          <%-- <p class="chapt_author">
          <span>
	          	<c:if test="${favouritechaper&&form.favorite<=0 }">
				<a id="favourites_div_chaper${PA.id}" class="" onclick="addFavourites('${PA.id}',2);" ><img id="faveimg_${PA.id}" src="${ctx}/images/collect.png" /></a>
				</c:if>
				<c:if test="${sessionScope.mainUser!=null && (!favouritechaper||form.favorite>0) }">
				<a id="favourites_div_chaper${PA.id}" class="">
					<img src="${ctx}/images/collect_light.png" />
				</a>
						</c:if>
	         </span>
	         
	          <span>
	          <c:if test="${(PA.buyInDetail==0&&PA.subscribedIp==0&&PA.subscribedUser==0)||buyBook==false }">
	          <a id="add_cart_${PA.id}" onclick="addToCart('${PA.id}',1,2)"><img src="${ctx}/images/cart.png" /></a>
	          </c:if>
	          </span>
			
	          <span>
	            <c:if test="${recommandchaper&&buyBook==false}">
	          	<a id="recommend_div"  onclick="popTips('${PA.id}');"><img src="${ctx}/images/recommend.png" /></a>
	          	</c:if>
	          </span>
        <span><a href="javascript:void(0)"><img src="${ctx}/images/download.png" /></a></span>
        </p> --%>
      
        </c:forEach>   
	  </div>
	  </c:if>
      <!-- <div class="chapter">
        <h1 class="chapt_title"><span>关于本书</span></h1>
        <div class="chapt_cont">
           <ul class="chapt_cont_ul">
           	<li><h1>书名：</h1>
            	 <P>Adsorption mode Column Batch adsorption</P>
                    <h1>E - ISBN：</h1>
                    <p>9781934559536</p>
                    <h1>H - ISBN：</h1>
                    <p>9781932603545</p>
            </li>
            <li><h1>分类：</h1>
            	 <P> R1 预防医学、卫生学</P>
                    <h1>作者：</h1>
                    <p>Alan J. Thompson</p>
                    <h1>出版社：</h1>
                    <p> Demos Health</p>
            </li>
            <li><h1>出版年份：</h1>
            	 <P>2006</P>
                 <h1>价格：</h1>
            	 <P>USD 24.95</P>
                  <h1>语言：</h1>
            	 <P>ENG</P>  
            </li>
           </ul>
           
        </div>
      </div> -->
     
      <c:if test="${sessionScope.mainUser != null && sessionScope.mainUser.level ==5 && subedInslist!=null && fn:length(subedInslist)>0}">
      <div class="chapter">
        <h1 class="chapt_title"><span><ingenta-tag:LanguageTag key="Pages.publications.article.Lable.Buyers" sessionKey="lang" /></span></h1>
        <div class="chapt_cont">
          <c:forEach items="${subedInslist }" var="s" varStatus="index">                
             <div class="chapt_cont" <c:if test="${index.index==fn:length(subedInslist)-1 }">style="border-bottom:none"</c:if>>
			     ${s.name}
			 </div>      
            </c:forEach>
        </div>
      </div>
      </c:if>
    </div>
 </div> 
	<div class="boderBottom"></div>
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
	</div>
</body>
<form:hidden id="pubid" path="form.obj.id" />
</html>
