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
<script type="text/javascript" src="${ctx }/js/jquery.highlight.js"></script>
<script type="text/javascript">
//<![data[
//推荐
function recommends(pid) {
	//先将信息放到对应的标签上title, code, type, pubSubject
	art.dialog.open("${ctx}/pages/recommend/form/edit?pubid="+pid,{title:"<ingenta-tag:LanguageTag key='Page.Pop.Title.Recommend' sessionKey='lang'/>",top: 100,width: 700, height: 400,lock:true});
}
//购买
function addToCart(pid, ki) {
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
				$("#add_"+pid).css("display","none");
				$("#cartCount").html("["+s[2]+"]");
				$("#price_" + pid).css("display","none");
			}else{
				art.dialog.tips(s[1],1,'error');
			}
		},
		error : function(data) {
			art.dialog.tips(data,1,'error');
		}
	});
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
				art.dialog.tips(s[1],1);//location.reload();
				$("#favourites_"+pid).attr("src","${ctx}/images/collect_light.png");
				$("#favourites_div_"+pid).removeAttr("href");
				$("#favourites_div_"+pid).removeAttr("onclick");
				$("#favourites_div_"+pid).attr("title","<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
				$("#favourites_div_"+pid).css("cursor","auto");
			}else{
				art.dialog.tips(s[1],1,'error');
			}
		},
		error : function(data) {
			art.dialog.tips(data,1,'error');
		}
	});
}
//]]-->
</script>
</head>
<body>
	<div class="big">
		<!--以下top state -->
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--以上top end -->
		<!--以下中间内容块开始-->
		<div class="main">
			<!--右侧列表内容-->
		    <div class="main_content" style="width:1090px;">
		   	  <h1><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Publications.Database.Lable.Info"/></h1>
		   	  	<c:forEach items="${list }" var="p">
		   	  		<c:set var="add" value="${(p.priceList!=null||fn:length(p.priceList)>0)&&p.free!=2&&p.oa!=2&&sessionScope.mainUser!=null && p.subscribedUser<=0&&(p.buyInDetail<=0&&p.exLicense>=0) }"/>
  					<c:set var="favourite" value="${sessionScope.mainUser!=null&&(p.favorite==null||p.favorite<=0) }"/>
   					<c:set var="recommand" value="${(p.recommand>0||sessionScope.mainUser.institution!=null) &&(p.subscribedIp==null||p.subscribedIp<=0)&&(p.free!=2&&p.oa!=2)}"/>
			        <!--列表内容开始-->
			        <div class="h2_list" style="width:1090px;">
				        <p class="p_left journ_pleft" title="${p.title }">
				        	<img src="${ctx }/images/shuju.png">
				        	${p.title }
				        </p>
				        <c:if test="${(p.latest!=null&&p.latest>0)||(p.oa!=null&&p.oa==2)||(p.free!=null&&p.free==2)||(p.inCollection!=null&&p.inCollection>0) }">
					        <p class="p_right">
						       <%--  <c:if test="${p.latest!=null&&p.latest>0 }"><img src="${ctx }/images/n.png" style="vertical-align:middle"/></c:if> --%>
		                    	<c:if test="${p.oa!=null&&p.oa==2 }"><img src="${ctx }/images/ico/o.png" style="vertical-align:middle"/></c:if>
		                    	<c:if test="${p.free!=null&&p.free==2 }"><img src="${ctx }/images/ico/f.png" style="vertical-align:middle"/></c:if>
		                    	<%-- <c:if test="${p.inCollection!=null&&p.inCollection>0 }"><img src="${ctx }/images/c.png" style="vertical-align:middle"/></c:if> --%>
					        </p>
				        </c:if>
			        </div>
		        	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="list_table">
		            	<tr>
		             <td width="15%" valign="top">
		             	<img onload="AutoResizeImage(140,140,this);" src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if><c:if test="${p.cover!=null&&p.cover!='' }">/${p.cover}</c:if><c:if test="${p.cover==null||p.cover=='' }">/images/noimg.jpg</c:if>" onerror="this.src='${ctx}/images/noimg.jpg'"/>
		             </td>
		             <td width="60%">
		             	<p>
		             		<strong>
		             			<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.database.Label.publisher"/>：&nbsp;
		             		</strong>
							${p.publisher.name }
						</p>
		                <p>
		                	<strong>
		                		<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.database.Label.subject"/>：&nbsp;
		                	</strong>
		                	<c:if test="${sessionScope.lang=='zh_CN'||p.pubSubjectEn==null }">${p.pubSubject }</c:if>
							<c:if test="${sessionScope.lang=='en_US'&&p.pubSubjectEn!=null }">${p.pubSubjectEn }</c:if>
		                </p>
		                <p>
		                	<strong>
		                		<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.database.Label.createOn"/>：&nbsp;
		                	</strong>
		                	<fmt:formatDate value="${p.createOn }" pattern="yyyy-MM-dd"/>
		                </p>
		                <c:if test="${add||favourite||recommand}">
		                <p>
		                	<strong>
		                		&nbsp;
		                	</strong>
							<c:if test="${p.oa!=2||p.free!=2}">
							<c:if test="${p.priceList!=null&&fn:length(p.priceList)>0 }">
		                	<!-- 价格列表 -->
		                	<select name="price_${p.id }" id="price_${p.id }" class="select_box">
								<c:forEach items="${p.priceList }" var="pr" varStatus="indexPr">
									<option value="${pr.id }"><c:if test="${pr.type==2 }">L</c:if><c:if test="${pr.type==1 }">P</c:if>${pr.complicating}-${pr.price }${pr.currency }</option>
								</c:forEach>
							</select>
							</c:if>
							</c:if>
							<c:if test="${add }">
							<!-- 购买 -->
							  <c:if test="${p.priceList!=null&&fn:length(p.priceList)>0 }">
								<a class="ico_link" onclick="addToCart('${p.id}',1);" id="add_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Buy' sessionKey='lang'/>"><img src="${ctx }/images/cart.png" /></a>
							  </c:if>
							</c:if>
							<c:if test="${favourite }">
							<!-- 收藏 -->
								<a class="ico_link" id="favourites_div_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' />" onclick="addFavourites('${p.id }');"><img id="favourites_${p.id }" src="${ctx }/images/collect.png" /></a> 
							</c:if>
							<c:if test="${sessionScope.mainUser!=null && !favourite }">
							<!-- 已收藏 -->
								<a class="ico_link"  style="cursor:auto;"><img src="${ctx }/images/collect_light.png" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />"/></a>
							</c:if>
							<c:if test="${recommand}">
							<!-- 推荐 -->
								<a class="ico_link" id="recommand_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' />" onclick="recommends('${p.id}');"><img src="${ctx }/images/recommend.png" /></a>
							</c:if>
		                </p>
		                </c:if>
		                <c:if test="${p.remark!=null && p.remark!='' }">
			                <p>
			                	<strong>&nbsp;</strong>
		                		<div style="height:20px;overflow: hidden;*margin-top:20px;"><a style="cursor: pointer;" onclick="senfe(this);">+ <ingenta-tag:LanguageTag key="Page.Index.Search.Desc.Show" sessionKey="lang" /></a>
								<p class="clearcss">
								${fn:replace(fn:replace(fn:replace(p.remark,"&lt;","<"),"&gt;",">"),"&amp;","&")}  		
								</p>
								</div>
							</p>
						</c:if>
		             </td>
		             <td width="25%">
		             	<c:if test="${p.institutionList!=null }">
							<c:forEach items="${p.institutionList }" var="i">
								<p>
									<img src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if><c:if test="${i.logo!=null&&i.logo!='' }">/${i.logo}</c:if><c:if test="${i.logo==null||i.logo=='' }">/images/noimg.jpg</c:if>" alt=""  title="${i.name }" />
								</p>
							</c:forEach>
						</c:if>
		             </td>
		             </tr>   
		          	</table>
		            <p style="height:1px; width:1px; clear:both;">&nbsp;</p>
		            <!--列表内容结束-->
		         </c:forEach>
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
					<form:hidden path="order" id="order1"/>
					<form:hidden path="lcense" id="lcense1"/>
					
					<form:hidden path="code" id="code1"/>
					<form:hidden path="pCode" id="pCode1"/>
					<form:hidden path="publisherId" id="publisherId1"/>
					<form:hidden path="subParentId" id="subParentId1"/>
					<form:hidden path="parentTaxonomy" id="parentTaxonomy1"/>
					<form:hidden path="parentTaxonomyEn" id="parentTaxonomyEn1"/>
				</form:form>
				<!--以上 提交查询Form 结束-->
		    </div>
		    <!--右侧列表内容结束-->
		</div>
		<!--以上中间内容块结束-->

		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</div>
</body>
</html>
