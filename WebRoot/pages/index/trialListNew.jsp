<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
<%@ include file="/common/tools.jsp"%>
<%@ include file="/common/ico.jsp"%>
<link rel="stylesheet" href="${ctx}/css/highlight.css" type="text/css"></link>
<script type="text/javascript" src="${ctx }/js/jquery.highlight.js"></script>
<script type="text/javascript">
//条件删除
$("a[name='conditions']").click(function(){
	var param = $(this).attr("id");
	if(param=="pubType_label"){
		$("#pubType1").val('');
	}else if(param=="publisher_label"){
		$("#publisher1").val('');
	}else if(param=="pubDate_label"){
		$("#pubDate1").val('');
	}else if(param.indexOf("taxonomy_label")==0){
	/* $("#taxonomy1").val(''); */
	var tax=$(this).text().trim();
	var allTax = $("#taxonomy1").val();
	allTax=allTax.replace(tax,'').replace(',,',',');
	allTax=allTax.replace(/^,+/,'');
	allTax=allTax.replace(/,+$/,'');
	$("#taxonomy1").val(allTax);
	}else if(param=="taxonomyEn_label"){
		$("#taxonomyEn1").val('');
	}else if(param=="language_label"){
		$("#language1").val('');
	}
	$(this).css("display","none");
	$("input[name='curpage']").val(0);
	document.formList.action="${ctx}/pages/index/trialList";
	document.formList.submit();
});	
//左侧条件查询
function searchByCondition(type,value){
	if(type=="type"){
		$("input[name=pubType]").val(value);
	}else if(type=="publisher"){
		$("input[name=publisher]").val(value); 
	}else if(type=="pubDate"){
		$("input[name=pubDate]").val(value);
	}else if(type=="taxonomy"){
		var tax=$("input[name=taxonomy]").val();
		var s=$("#taxonomy1").val();
		var strs= new Array(); 
		    strs=s.split(","); 
		    for (i=0;i<strs.length ;i++ ) 
		    { 
		    	if(strs[i]==value){
		    		verify=1;
		    		strs.remove(i); 
		    	}
		    }
		    b = strs.join("-");
		    document.getElementsByName("template").value=b;
		    if(tax){
    			$("input[name=taxonomy]").val(tax+","+value);
    		}else{
    			$("input[name=taxonomy]").val(value);	
    		}
	    		
	}else if(type=="taxonomyEn"){
		$("input[name=taxonomyEn]").val(value);
	}else if(type=="language"){
		$("input[name=language]").val(value);
	}
	var lcense = $("#lcense1").val();
	$("input[name='curpage']").val(0);
	$("#pageCount").val(10);
	document.formList.action="${ctx}/pages/index/trialList";
	document.formList.submit();
}
//在线阅读
 function viewPopTips(id,page,yon) {
	var url="";
	var tmp=window.open("about:blank","","scrollbars=yes,resizable=yes,channelmode") ;          
        //tmp.focus() ;
	if(page=='0'){
		url = "${ctx}/pages/view/form/view?id="+id;
	}else{
		url = "${ctx}/pages/view/form/view?id="+id+"&nextPage="+page;
	}
	//首先Ajax查询要阅读的路径
	if(yon=='2'){
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
 function dividePage(val){
	 if(val<0){return;}
	 $("#curpage2").val(val);
	 document.formList.submit();
 }
 function GO(obj){
	 $("#pageCount2").val($(obj).val());
	 document.formList.submit();
	}
	 
//获取资源弹出层调用
function popTips2(pid) {
/* 	showTipsWindown("",
			'simTestContent', $(window).width()*0.6, $(window).height()*0.65); */
			/* alert(pid); */
			art.dialog.open("${ctx}/pages/publications/form/getResource?pubid="+pid,{id : "getResourceId",title:"",top: 200,width: 340, height: 200,lock:true}); /* <ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/> */
}
//收藏
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
				art.dialog.tips(s[1],3);//location.reload();
				$("#favourites_"+pid).attr("src","${ctx}/images/favourite.png");
				$("#favourites_div_"+pid).removeAttr("href");
				$("#favourites_div_"+pid).removeAttr("onclick");
				$("#favourites_div_"+pid).attr("title","<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang'  />");
				$("#favourites_div_"+pid).attr("class","blank");
				$("#favourites_div_"+pid).css("cursor","auto");
			}else{
				art.dialog.tips(s[1],3,'error');
			}
		},
		error : function(data) {
			art.dialog.tips(data,3,'error');
		}
	});
}
</script>
</head>
<body>
	
		<!--以下top state -->
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--以上top end -->
		<!--以下中间内容块开始-->
		<div class="main">
			<!--左侧导航栏 -->
			<div class="chineseLeft"><!-- <div id="searchResultPage" style="display:none"></div> -->
			<div class="leftClassity">
			<c:if test="${facetFields!=null&&fn:length(facetFields)>0 }">
				<c:forEach items="${facetFields }" var="fac" varStatus="index">
					<c:if test="${fac.name!='pubDate'&&fac.name!='taxonomy'&&fac.name!='taxonomyEn' && fac.name!='publisher'}">
						<c:if test="${fac.name=='type' }">
							<h1 class="h1Tit borBot">
								<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Publications" sessionKey="lang" />
							</h1>
						</c:if>						
						<ul>
						<c:if test="${fac.name!='taxonomy'&&fac.name!='taxonomyEn'}">
						
						<c:forEach items="${ typeList}" var="count" varStatus="index2">
							<c:if test="${count.count>0 }">
								<c:if test="${fac.name=='type' && (count.name==1 || count.name==2 || count.name==3 || count.name==4 || count.name==5 || count.name==7)}">
									<li class="oh">
									<a href="javascript:void(0)" onclick="searchByCondition('${fac.name}','${count.name }')">
										<c:if test="${count.name==1 }">
										<span class="fl"><img src="${ctx }/images/ico/ico4.png" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" /></span></c:if>
										<c:if test="${count.name==2 || count.name==7}">
										<span class="fl"><img src="${ctx }/images/ico/ico3.png" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></span></c:if>
										<c:if test="${count.name==3 }">
										<span class="fl"><img src="${ctx }/images/ico/infor.png" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option3" sessionKey="lang" /></span></c:if>
										<c:if test="${count.name==4 }">
										<span class="fl"><img src="${ctx }/images/ico/ico5.png" alt="期刊" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option4" sessionKey="lang" /></span></c:if>
										<c:if test="${count.name==5 }">
										<span class="fl"><img src="${ctx }/images/ico/ico2.png" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option5" sessionKey="lang" /></span></c:if>
										<%-- <c:if test="${count.name==7 }">
										<span class="fl"><img src="${ctx }/images/ico/ico3.png" /> <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></span></c:if> --%>
										<span class="fr">[${count.count}]</span>
									</a>
									</li>
								</c:if>								
							</c:if>							
						</c:forEach>
						</c:if>
						</ul>
					</c:if>
				</c:forEach>
				</c:if>
				
				<c:if test="${facetFields==null||fn:length(facetFields)<=0 }">
					<h1>
						<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Publications" sessionKey="lang" />
					</h1>					
				</c:if>
				</div>
					<div class="leftClassity">
					<h1 class="h1Tit borBot">
						<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Language" sessionKey="lang" />
					</h1>	
					<ul class="updownUl">
					<c:set var="s" value="1"/>
					<c:forEach items="${languageList }" var="count" varStatus="index">
					<c:if test="${ count.count >0}">
						<li class="oh">
							<a href="javascript:void(0)" onclick="searchByCondition('language','${count.name}')" title="${fn:toUpperCase(count.name)}">
								<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
								<span class="write">${fn:toUpperCase(count.name) }</span><span class="fr">[${count.count }]</span>
							</a>
						</li>
					<c:set var="s">${s+1 }</c:set>
					</c:if>
					</c:forEach>					
					</ul>
					<c:if test="${s>6}">
					<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</a></span>
					</c:if>
					</div>
				
				
					<div class="leftClassity">
					<h1 class="h1Tit borBot">
						<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Publisher" sessionKey="lang" />
					</h1>
					<ul class="updownUl">
					<c:set var="s" value="1"/>
					<c:forEach items="${publisherList }" var="count" varStatus="index">
					<c:if test="${count.count>0 }">
						<li class="oh">
						<a href="javascript:void(0)" onclick="searchByCondition('publisher','${count.name }')" title="${count.name }">
							<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
							<span class="write">
								<c:set var="publisherName" value="${count.name }" />
								<c:choose>  
								    <c:when test="${fn:length(publisherName) > 12}">  
								        <c:out value="${fn:substring(publisherName, 0, 12)}..." />  
								    </c:when>  
								   <c:otherwise>  
								      <c:out value="${publisherName}" />  
								    </c:otherwise>  
								</c:choose>  
							</span><span class="fr">[${count.count }]</span>
							</a>
						</li>		
					<c:set var="s">${s+1 }</c:set>
					</c:if>
					</c:forEach>
					</ul>
					<c:if test="${s>6}">
					<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</a></span>
					</c:if>
					</div>
				
					<div class="leftClassity">
					<c:if test="${sessionScope.lang=='zh_CN' }">
					<h1 class="h1Tit borBot">
						<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Subject" sessionKey="lang" />
					</h1>
					<ul  class="updownUl">
					<c:set var="i" value="1"/>
					<c:forEach items="${taxonomyList }" var="count" varStatus="index">
						<c:if test="${count.count>0 }">
								<li class="oh">
								<a href="javascript:void(0)" onclick="searchByCondition('taxonomy','${count.name }')" title="${count.name }">
									<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
									<span class="write"> 
										<c:set var="taxonomyName" value="${count.name }" />
										<c:choose>  
										    <c:when test="${fn:length(taxonomyName) > 14}">  
										        <c:out value="${fn:substring(taxonomyName, 0, 14)}..." />  
										    </c:when>  
										   <c:otherwise>  
										      <c:out value="${taxonomyName}" />  
										    </c:otherwise>  
										</c:choose>  
									</span>
									<span class="fr">[${count.count }]</span>
								</a>
								</li>
							<c:set var="i" >${i+1 }</c:set>
						</c:if>						
					</c:forEach>					
					</ul>
					<c:if test="${i>6}">
					<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</a></span>
					</c:if>
					</c:if>
					<c:if test="${sessionScope.lang=='en_US' }">
					<h1 class="h1Tit borBot">
						<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Subject" sessionKey="lang" />
					</h1>
					<ul class="updownUl">
					<c:set var="i" value="1"/>
					<c:forEach items="${taxonomyEnList }" var="count" varStatus="index">
						<c:if test="${count.count>0 }">
								<li class="oh">
								<a href="javascript:void(0)" onclick="searchByCondition('taxonomyEn','${count.name }')" title="${count.name }">
									<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
									<span class="write"> 
										<c:set var="taxonomyName" value="${count.name }" />
										<c:choose>  
										    <c:when test="${fn:length(taxonomyName) > 20}">  
										        <c:out value="${fn:substring(taxonomyName, 0, 20)}..." />  
										    </c:when>  
										   <c:otherwise>  
										      <c:out value="${taxonomyName}" />  
										    </c:otherwise>  
										</c:choose>
									</span>
									<span class="fr">[${count.count }]</span>
								</a>
								</li>
							<c:set var="i" >${i+1 }</c:set>
						</c:if>						
					</c:forEach>					
					</ul>
					<c:if test="${i>6}">
					<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</a></span>
					</c:if>
					</c:if>
					</div>
					<div class="leftClassity">
				<h1 class="h1Tit borBot">
					<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.PubDate" sessionKey="lang" />
				</h1>
				<ul class="updownUl">
					<c:set var="k" value="1"/>
          			<c:forEach items="${pubDateMap }" var="p" varStatus="index">
          				<c:if test="${p.value>0}">
          					<c:if test="${'0000' != p.key}">
          						<li  class="oh">
					          		<a href="javascript:void(0)" onclick="searchByCondition('pubDate','${p.key }')">
					          			<span class="alph"><img src="${ctx }/images/ico/ico10.png" /></span>
					          			<span class="write">${p.key}</span><span class="fr">[${p.value }]</span>
									</a>
								</li>
							</c:if>
          					<c:set var="k">${k+1 }</c:set>
          				</c:if>
          			</c:forEach>
				</ul>
				<c:if test="${k>6}">
					<span class="updownMore"><a href="javascript:void(0)" class="ico_nomal"><ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...</a></span>
				</c:if>
			</div>
			</div>
			<!--左侧导航栏结束 -->
			<!--右侧列表内容-->
			<div class="chineseRight">
				
						
				<div  class="StabContent ScontentSelected CtabContent">	
					<div class="take">
						<c:if test="${(form.pubType!=null&&form.pubType!='')||(form.publisher!=null&&form.publisher!='')||(form.pubDate!=null&&form.pubDate!='')||(form.taxonomy!=null&&form.taxonomy!='')||(form.taxonomyEn!=null&&form.taxonomyEn!='')||(form.language!=null&&form.language!='') }">
						<div style="float:left;"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Lable.SearchRange" sessionKey="lang" />:&nbsp;&nbsp;&nbsp;&nbsp;</div>
						<div class="label_list" >
							<c:if test="${form.pubType!=null&&form.pubType!='' }">
							<a href="javascript:void(0)" name="conditions" class="label" id="pubType_label">
								<c:if test="${form.pubType==1 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==2 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==3 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option3" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==4 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option4" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==5 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option5" sessionKey="lang" /></c:if>
								<c:if test="${form.pubType==7 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></c:if>
								
							</a>
							</c:if>
							<c:if test="${form.publisher!=null&&form.publisher!='' }">
							<a href="javascript:void(0)" name="conditions" class="label" id="publisher_label">${form.publisher }</a>
							</c:if>
							<c:if test="${form.pubDate!=null&&form.pubDate!='' }">
							<a href="javascript:void(0)" name="conditions" class="label" id="pubDate_label">${form.pubDate }</a>
							</c:if>
							<c:if test="${form.taxonomy!=null&&form.taxonomy!='' }">
							<a href="javascript:void(0)" name="conditions" class="label" id="taxonomy_label">${form.taxonomy }</a>
							</c:if>
							<c:if test="${form.taxonomyEn!=null&&form.taxonomyEn!='' }">
							<a href="javascript:void(0)" name="conditions" class="label" id="taxonomyEn_label">${form.taxonomyEn }</a>
							</c:if>
								
							<c:if test="${form.language!=null&&form.language!='' }">
							<a href="javascript:void(0)" name="conditions" class="label" id="language_label">${fn:toUpperCase(form.language) }</a>
							</c:if>
						</div>
						</c:if>
					</div>
				 <form:form action="${form.url}" method="post" modelAttribute="form" commandName="form" name="formList" id="formList">
					<form:hidden path="searchsType" id="type1"/>
					<form:hidden path="searchValue" id="searchValue1"/>
					<form:hidden path="pubType" id="pubType1"/>
					<form:hidden path="language" id="language1"/>
					<form:hidden path="publisher" id="publisher1"/>
					<form:hidden path="pubDate" id="pubDate1"/>
					<form:hidden path="taxonomy" id="taxonomy1"/>
					<form:hidden path="taxonomyEn" id="taxonomyEn1"/>
			        <form:hidden path="curpage" id="curpage2"/>
		    		<form:hidden path="pageCount" id="pageCount2"/>
				</form:form>
				
				<h1 class="indexHtit">
		             <span class="fl titFb"><a class="ico1" href="javascript:void(0)"><ingenta-tag:LanguageTag key="Global.Label.TrialPub" sessionKey="lang"/></a></span>
		             
		        </h1>
				<!--列表内容开始-->
				<div class="block">
				<c:forEach items="${list }" var="p" varStatus="index">
						<div class="block">
			             	 <div class="fl w40 mt2">
			               		<img width="13" height="13" src="${ctx}/images/ico/t.png" />
			                    <c:if test="${p.publications.type==1}"><img width="13" height="13" src="${ctx}/images/ico/ico4.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Book" sessionKey="lang" />" /></c:if>
								<c:if test="${p.publications.type==4 || p.publications.type==3}"><img width="13" height="13" src="${ctx}/images/ico/ico5.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Article" sessionKey="lang" />" /></c:if>
								<c:if test="${p.publications.type==2||p.publications.type==6||p.publications.type==7}"><img width="13" height="13" src="${ctx}/images/ico/ico3.png" title="<ingenta-tag:LanguageTag key="Pages.Index.Lable.Journal" sessionKey="lang" />" /></c:if>
			               	</div>
		                    <div class="fl w640">
			                    <p>
			                    <a class="a_title" title="${p.publications.title}" href="${ctx}/pages/publications/form/article/${p.publications.id}">
			                		${fn:replace(fn:replace(fn:replace(p.publications.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}
			                	</a>
			                	</p>
			                	<c:if test="${not empty p.publications.author}">
			                		<p>By <c:set var="authors" value="${fn:split(p.publications.author,',')}" ></c:set>
			                <c:forEach items="${authors}" var="a" >
			                <a href='${ctx }/index/search?type=2&isAccurate=1&searchValue2=${a}'>${a}</a> &nbsp;
			                </c:forEach></p>
			                	</c:if> 
			                	<p><c:if test="${not empty p.publications.publisher.name}"><a href="javascript:void(0)" onclick="searchByCondition('publisher','${p.publications.publisher.name }','${p.publications.publisher.id}');">${p.publications.publisher.name}</a></c:if> <c:if test="${not empty fn:substring(p.publications.pubDate,0,4)}">(${fn:substring(p.publications.pubDate,0,4) })</c:if></p>
			                	<c:if test="${p.publications.type==2  && not empty p.publications.startVolume && not empty p.publications.endVolume}">
						<p>
						<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" /> ${p.publications.startVolume }-<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" /> ${p.publications.endVolume }
						</p>
						</c:if>
						 <p>
							<span class="mr20">
								<a href="javascript:void(0)"  id="resource_div"  onclick="popTips2('${p.publications.id}');"><img src="${ctx }/images/ico/ico15-green.png" class="vm" />
									<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
								</a>
							</span>
						</p>
		                </div>
	                   
		                </div>
		               
				</c:forEach>
			</div>
			<jsp:include page="../pageTag/pageTag.jsp">
			        <jsp:param value="${form }" name="form"/>
                </jsp:include>	
			<!--右侧列表内容结束-->
			</div>
		</div>
		<!--以上中间内容块结束-->
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
</body>
</html>
