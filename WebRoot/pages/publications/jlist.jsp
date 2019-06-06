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
	<script type="text/javascript">
	$(document).ready(function(){
			setStyle();			
			$("a[name='sort']").click(function(){
				 setStyle();	
				 var param = $(this).text();
				 var url = "${form.azUrl}";
				 if(param!="#"){
					 if(url.lastIndexOf("?")==-1){
					 	url += "?order="+param;
					 }else{
					 	url += "&order="+param;
					 }
				}
			 	window.location.href=url;
			});	
			$("a[name='sort2']").click(function(){
				setStyle();	
				var param = $(this).attr("id");
				var url = "${form.azUrl}";
				if(param!="#"){
					 if(url.lastIndexOf("?")==-1){
					 	url += "?orderDesc="+param;
					 }else{
					 	url += "&orderDesc="+param;
					 }
				}
				window.location.href=url;
			});
	});
	function setStyle(){
		$("a[name='sort']").each(function(){
			if($(this).text()=="${form.order}"){
				$(this).attr("style","text-decoration:none");
			}			
		});
		$("a[name='sort2']").each(function(){
			$("#${form.orderDesc}").attr("style","text-decoration:none");
		});
	}
	</script>
		<!-- 弹出层样式  开始-->
<style type="text/css">
#windownbg {
	display: none;
	position: absolute;
	width: 100%;
	height: 100%;
	background: #000;
	top: 0;
	left: 0;
}

#windown-box {
	position: fixed;
	_position: absolute;
	border: 5px solid #E9F3FD;
	background: #FFF;
	text-align: left;
}

#windown-title {
	position: relative;
	height: 30px;
	border: 1px solid #A6C9E1;
	overflow: hidden;
}

#windown-title h2 {
	position: relative;
	left: 10px;
	top: 5px;
	font-size: 14px;
	color: #666;
}

#windown-close {
	position: absolute;
	right: 10px;
	top: 8px;
	width: 10px;
	height: 16px;
	text-indent: -10em;
	overflow: hidden;
	cursor: pointer;
}

#windown-content-border {
	position: relative;
	top: -1px;
	border: 1px solid #A6C9E1;
	padding: 5px 0 5px 5px;
}

#windown-content img,#windown-content iframe {
	display: block;
}

#windown-content .loading {
	position: absolute;
	left: 50%;
	top: 50%;
	margin-left: -8px;
	margin-top: -8px;
}
</style>
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
	//弹出层调用
	function popTips(pid) {
		//先将信息放到对应的标签上title, code, type, pubSubject
		art.dialog.open("${ctx}/pages/recommend/form/edit?pubid="+pid,{title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/>",top: 100,width: 700, height: 400,lock:true});
	}
	//******************************弹出层--结束*********************************//
</script>
<script type="text/javascript">
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
</script>


	<%@ include file="/common/ico.jsp"%></head>
	<body>
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--定义01 mainContainer 内容区开始-->
		<div class="mainContainer">
			<!--定义 0101 头部边框-->
			<div class="borderContainer">

<style type="text/css">
	<!--
		.main {
			float: left;
			width: 610px;
			
		}
		.content {
			width: 100%;
			*width: 70%;
			height: 20px;
			overflow: hidden;
			font-family:arial;
			line-height:18px;
			vertical-align:middle;
			margin-top:-5px;
			word-wrap: break-word;
		}
		.c1 {height: 170px;}
		.c2 {height: 408px;}
		.main span.button {
			float: left;
			height: 20px;
			font-size: 12px;
			cursor: pointer;
			color:#000;
			border-bottom:1px solid #333;
			
		}
	-->
	</style>
	<script type="text/javascript">
	//<![data[
		function senfe(e) {
			var s = 1.2;
            var s2 = 8;
			var obj = e.parentNode;
			var oh = parseInt(obj.offsetHeight);
			var h = parseInt(obj.scrollHeight);
			var nh = oh;
	              
			if(obj.getAttribute("oldHeight") == null){
				obj.setAttribute("oldHeight", oh);
			}else{
				var oldh = Math.ceil(obj.getAttribute("oldHeight"));
			}
			var reSet = function(){
				if (oh<h) {
					e.innerHTML = "- <ingenta-tag:LanguageTag key="Page.Index.Search.Desc.Hide" sessionKey="lang" />";
					if(nh < h){
						nh = Math.ceil(h-(h-nh)/s);
						obj.style.height = nh+"px";
					}else{
						window.clearInterval(IntervalId);
					}
				} else {
	                e.innerHTML = "+ <ingenta-tag:LanguageTag key="Page.Index.Search.Desc.Show" sessionKey="lang" />";
	                if(nh > oldh){
						nhh = Math.ceil((nh-oldh)/s2);
	                    nh = nh-nhh;
						obj.style.height = nh+"px";
					}else{
						window.clearInterval(IntervalId);
					}
	            }				
			}
			var IntervalId = window.setInterval(reSet,10);
			
      };
      
			
	//]]-->
</script>


<style type="text/css">
/* * {
    font-family: Verdana, Helvetica;
    font-size: 10pt;
}*/
.highslide-html {
    
}
.highslide-html-blur {
    border: 1px solid #E7E8EA;
}
.highslide-html-content {
	position: absolute;
    display: none;
}
.highslide-display-block {
    display: block;
}
.highslide-display-none {
    display: none;
}

.control {
	float: right;
    display: block;
    /*position: relative;*/
	margin: 0 5px;
	font-size: 12px;
	text-decoration: none;
	color: #FB5520;
}
.control:hover {
	color: #FB5520 !important;
}
.highslide-move {
    cursor: move;
}
.number{
	font-size:12px;
	color:#FB5520;
	padding:0 3px 0 3px;
}
.number a:link,.number a:visited{
	font-size:12px;
	color:#FB5520;
	font-weight:bold;
	padding:0 3px 0 3px;
	text-decoration:none;
}
.number a:hover{

	text-decoration:underline;
}
.zhushouleft{
	border-bottom:1px solid #FFF;
	border-top:1px solid #FFF;
	border-right:1px solid #FFF;
}
.zhushouright{
	border-bottom:1px solid #FFF;
	border-top:1px solid #FFF;
}
.zhushoubottom{
	border-bottom:1px solid #FFF;
	border-right:1px solid #FFF;
}
.zhushoutop{
	border-bottom:1px solid #FFF;
}
.biaotistyle{padding-left:20px;padding-top:20px;font-size:12px; color:#898989;font-weight:bold;text-align:left;}
.detailstyle a:link,.detailstyle a:visited{font-size:12px;color:#000;text-decoration:none}
.detailstyle a:hover{text-decoration:underline}

</style>
<!--ajks样式结束-->
	<!--定义 0102 左边内容区域 开始-->
      <div class="leftContainer">
<div class="listTitle">Journals</div>
<div class="clearit"></div>
<div class="summery"></div>
<div class="clearit"></div><!--定义 左边分类列表 开始-->
<div class="subClass">
<p><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Publications.Journal.Filter.Subject" /></p>
<div class="clear"></div>
<ul><!--  -->
<c:forEach items="${subStatistic}" var="subs" varStatus="index">

      <li><a href="${ctx }/pages/publications/form/journal?sub=${subs.id}">${subs.code }&nbsp;&nbsp;<c:if test="${sessionScope.lang=='zh_CN' }">${subs.name}</c:if><c:if test="${sessionScope.lang=='en_US' }">${subs.nameEn }</c:if>[${subs.type }]</a></li>

</c:forEach>
  </ul>
<div class="clear"></div>
</div><!--定义 左边分类列表 结束--><!--定义 右侧图书详细信息列表 开始-->
<div class="resultsContainer">
<div class="resultsnav">
<p style="width:100%">1 - ${form.pageCount} of ${form.count} <ingenta-tag:LanguageTag key="Page.Publications.DetailList.Lable.Results" sessionKey="lang" /></p>
<div class="a_to_z" >
	<ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Select.Sort" sessionKey="lang" />：
	<a name="sort2" id="titleAsc" href="javascript:void(0);"><ingenta-tag:LanguageTag key="Page.Publications.list.Order.title" sessionKey="lang" /></a>
	<a name="sort2" id="pubDesc" href="javascript:void(0);"><ingenta-tag:LanguageTag key="Page.Publications.list.Order.pubdate.desc" sessionKey="lang" /></a>
</div>
<div class="clear"></div>
<div class="a_to_z">
	<a href="javascript:void(0);" name="sort">#</a>
	<a href="javascript:void(0);" name="sort">A</a>
	<a href="javascript:void(0);" name="sort">B</a>
	<a href="javascript:void(0);" name="sort">C</a>
	<a href="javascript:void(0);" name="sort">D</a>
	<a href="javascript:void(0);" name="sort">E</a>
	<a href="javascript:void(0);" name="sort">F</a>
	<a href="javascript:void(0);" name="sort">G</a>
	<a href="javascript:void(0);" name="sort">H</a>
	<a href="javascript:void(0);" name="sort">I</a>
	<a href="javascript:void(0);" name="sort">J</a>
	<a href="javascript:void(0);" name="sort">K</a>
	<a href="javascript:void(0);" name="sort">L</a>
	<a href="javascript:void(0);" name="sort">M</a>
	<a href="javascript:void(0);" name="sort">N</a>
	<a href="javascript:void(0);" name="sort">O</a>
	<a href="javascript:void(0);" name="sort">P</a>
	<a href="javascript:void(0);" name="sort">Q</a>
	<a href="javascript:void(0);" name="sort">R</a>
	<a href="javascript:void(0);" name="sort">S</a>
	<a href="javascript:void(0);" name="sort">T</a>
	<a href="javascript:void(0);" name="sort">U</a>
	<a href="javascript:void(0);" name="sort">V</a>
	<a href="javascript:void(0);" name="sort">W</a>
	<a href="javascript:void(0);" name="sort">X</a>
	<a href="javascript:void(0);" name="sort">Y</a>
	<a href="javascript:void(0);" name="sort">Z</a>
</div>
<div class="clear"></div></div>
<div class="clear"></div>
<div class="separatedList">
          <div class="clear"></div>
          <ul>
          <c:forEach items="${pubList}" var="p" varStatus="index">
          	<c:set var="license">${(p.subscribedIp!=null||p.subscribedUser!=null)&&(p.subscribedIp>0||p.subscribedUser>0) }</c:set>
						<c:set var="news">${p.latest!=null&&p.latest>0 }</c:set>
						<c:set var="oa">${p.oa!=null&&p.oa==2 }</c:set>
						<c:set var="free">${p.free!=null&&p.free==2 }</c:set>
						<c:set var="collection">${p.inCollection!=null&&p.inCollection>0 }</c:set>
						<c:set var="add1" value="${p.priceList!=null&&fn:length(p.priceList)>0&&p.free!=2&&p.oa!=2&&sessionScope.mainUser!=null && p.subscribedUser<=0&&(p.buyInDetail<=0&&p.exLicense>=0)}"/>
						<c:if test="${add1==false }">
							<c:set var="add" value="false"/>
						</c:if>
						<c:if test="${add1==true &&p.subscribedIp>0 }">
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
						<c:if test="${add1==true &&(p.subscribedIp==null||p.subscribedIp<=0) }">
							<c:set var="add" value="true"/>
						</c:if>
						<c:if test="${add1==false }">
							<c:set var="add" value="false"/>
						</c:if>
						<c:set var="favourite" value="${sessionScope.mainUser!=null&&p.favorite<=0 }"/>
						<c:set var="recommand" value="${(p.recommand>0||sessionScope.mainUser.institution!=null) &&(p.subscribedIp==null||p.subscribedIp<=0)&&(p.free!=2&&p.oa!=2)}"/>	
						         					
							<li class="sublications  <c:if test="${license==false&&oa==false&&free==false }">yellow</c:if><c:if test="${license==true||oa==true||free==true }">green</c:if>">
								 <c:if test="${sessionScope.mainUser!=null}">
		               			<div class="check_box">
			   	   					<input type="checkbox" name="publicationsIds" value="${p.id}" onclick="getPid()" <c:if test="${favourite==false}">checked</c:if>/>
			   	   				</div>
		   	   					</c:if>
								 
								<div class="new_title" <c:if test="${sessionScope.mainUser==null }">style="padding-left:0px;"</c:if>>
									<span  class="<c:if test="${license==false&&free==false&&oa==false }">lock</c:if><c:if test="${license==true||free==true||oa==true }">unlock</c:if>"></span>
									<b>
									<c:if test="${p.type==1 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" /></c:if>
					               	<c:if test="${p.type==2 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></c:if>
					               	<c:if test="${p.type==3 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option3" sessionKey="lang" /></c:if>
					               	<c:if test="${p.type==4 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option4" sessionKey="lang" /></c:if>
					               	<c:if test="${p.type==5 }"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option5" sessionKey="lang" /></c:if>
									</b>
									<div class="title_link">
										<c:if test="${news==true }"><img src="${ctx }/images/icon01.jpg" style="vertical-align:middle"/></c:if>
										<c:if test="${free==true }"><img src="${ctx }/images/icon04.jpg" style="vertical-align:middle"/></c:if>
										<c:if test="${oa==true }"><img src="${ctx }/images/icon02.jpg" style="vertical-align:middle"/></c:if>
										<c:if test="${collection==true }"><img src="${ctx }/images/icon05.jpg" style="vertical-align:middle"/></c:if>
									</div>
								</div>
								<dl class="book_face">
									<dd>
									<a href="${ctx}/pages/publications/form/article/${p.id}">
									<c:if test="${p.cover==null||p.cover=='' }"><img src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/images/placeholdercover_small.gif" onload="AutoResizeImage(140,140,this);" style="width:1px;height:1px;" onerror="this.src='${ctx}/images/noimg.jpg'"/></c:if>
									<c:if test="${p.cover!=null&&p.cover!='' }"><img src="${ctx}/pages/publications/form/cover?id=${p.id}" onload="AutoResizeImage(140,140,this);" style="width:1px;height:1px;" onerror="this.src='${ctx}/images/noimg.jpg'"/></c:if>
									</a>
									</dd>
								</dl>
								<div class="new_name"><a href="${ctx}/pages/publications/form/article/${p.id}">${p.title }</a></div>
								<div class=publistcontent>
									<c:if test="${p.type!=2 }">${p.author }<c:if test="${p.type==4 }">...in <a href="${ctx}/pages/publications/form/article/${p.publications.id}">${p.publications.title}</a></c:if>(${fn:substring(p.pubDate,0,4) })</c:if>
									<c:if test="${p.type==2 }">Volume ${p.startVolume }-Volume ${p.endVolume }</c:if>
								</div>
								<div class=publistcontent>
									<span><b><a href="${ctx}/pages/publications/form/${p.publisher.id}/list">${p.publisher.name }</a></b>
									</span>
								</div>
								<c:if test="${add||favourite||recommand}">						
								<div class="publistcontent">
									<c:if test="${p.priceList!=null && fn:length(p.priceList)>0&&add==true}">
										<select id="price_${p.id }" style="vertical-align:middle">
											<c:forEach items="${p.priceList }" var="pr" varStatus="indexPr">
											<option value="${pr.id }"><c:if test="${pr.type==2 }">L</c:if><c:if test="${pr.type==1 }">P</c:if>${pr.complicating}-${pr.price }${pr.currency }</option>
											</c:forEach>
										</select>
									</c:if>
									<c:if test="${add||favourite||recommand}">
						            	<c:if test="${add }">
											<img src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/images/cart.png" style="vertical-align:middle;margin: 5px 0;cursor:pointer;" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Buy' sessionKey='lang' />" onclick="addToCart('${p.id}',1)"/>
											
										</c:if>
										<c:if test="${favourite }">
										   	<img id="favourites_${p.id }" src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/images/favorite.png" style="vertical-align:middle;margin: 5px 0;cursor:pointer;" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' />" onclick="addFavourites('${p.id }')"/>
										   	
										</c:if>
										<c:if test="${recommand}">
											<img id="recommand_${p.id }" src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/images/recommend.png" style="vertical-align:middle;margin: 5px 0;cursor:pointer;" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' />" onclick="popTips('${p.id}')"/>
											
										</c:if>
								</div>
								</c:if>
								<div class="flat">
									<ul>
										<li>
											<div class="main" id="neirong">
												<div class="content">
													<a class="button" onclick="senfe(this);">+<ingenta-tag:LanguageTag key="Page.Index.Search.Desc.Show" sessionKey="lang" /></a>
													${p.remark}
												</div>
											</div>
										</li>
									</ul>
									<div class="clear"></div>
								</div></li>
           </c:forEach>
            
            
            
             <div class="clear"></div>
          </ul>
        </div><!--移动窗口开始-->
<div style="display: none;">
<div id="simTestContent">
<div class="mainlist"><!--内容开始-->
<ul>
  <table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tbody>
    <tr>
      <td style="width: 20%;">Institution Name:</td>
      <td style="width: 80%;"></td></tr>
    <tr>
      <td style="width: 20%;">Title:</td>
      <td style="width: 80%;" id="title"></td></tr>
    <tr>
      <td style="width: 20%;">ISBN / ISSN:</td>
      <td style="width: 80%;" id="code"></td></tr>
    <tr>
      <td style="width: 20%;">Type:</td>
      <td style="width: 80%;" id="type"></td></tr>
    <tr>
      <td style="width: 20%;">CLC Subjects:</td>
      <td style="width: 80%;" id="subName"></td></tr>
    <tr>
      <td style="width: 20%;">Recommended by:</td>
      <td style="width: 80%;"></td></tr>
    <tr>
      <td style="width: 20%;">Identity:</td>
      <td style="width: 80%;">								Non-Expert							</td></tr>
    <tr>
      <td style="width: 20%;">Note:</td>
      <td style="width: 80%;"><textarea style="width: 70%; height: 150px;" onkeyup="$('#rnote').val(this.value)"></textarea></td></tr>
    <tr>
      <td style="width: 100%; text-align: center;" colspan="2"><input id="pubid" type="hidden"><input class="bton01" onclick="recommendSubmit()" value="Recommendation" type="button">&nbsp;&nbsp;
        								<input class="bton01" onclick="confirmTerm()" value="Cancel" type="button"></td></tr></tbody></table></ul><!--内容结束--></div></div><!--simTestContent end--></div><!--移动窗口结束-->
<div class="clear"></div>
<div class="publistcontent">
     <ingenta-tag:SplitTag page="${form.curpage}" pageCount="${form.pageCount}" count="${form.count}" formName="form" method="get" showNum="5" url="${form.url}" i18n="${sessionScope.lang}"/>
</div>
<div class="clear"></div></div><!--定义 右侧图书详细信息列表 结束-->
<div class="clear"></div></div>
      <!--定义 0102 左边内容区域 结束-->

				<!--定义 0103 右边内容区域 开始-->
				<%@ include file="/pages/frame/left.jsp"%>
				<!--定义 0103 右边内容区域 结束-->

			</div>
		</div>
		<div class="boderBottom"></div>
		<!--定义01 mainContainer 内容区结束-->
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
		<textarea id="rnote" style="display:none"></textarea>
	</body>
</html>
