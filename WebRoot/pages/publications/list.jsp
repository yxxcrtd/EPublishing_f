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
			<link rel="stylesheet" type="text/css" media="screen" href="${ctx}/css/select2css.css" /> 
		<script type="text/javascript" src="${ctx }/js/select2css.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
			getSubjectStat();
			getYearStat();
			getTypeStat();
			//getPublisherStat();
			$("a[name='sort']").each(function(){
				if($(this).text()=="${form.letter}"){
					$(this).attr("style","text-decoration:none");
				}			
			});
			$("a[name='sort2']").each(function(){
				$("#${form.orderDesc}").attr("style","text-decoration:none");
			});
			$("#selectAll").click(function(){
				if($(this).attr("checked")){
	     			$("input[name='publicationsIds']").attr("checked",true);
	     		}else{
	     			$("input[name='publicationsIds']").attr("checked",false);
	     		}
			});
		});
		function getSubjectStat(){
		var parObj=$("#subject_statistic");
		$.ajax({
			type : "POST",
			async : false,    
	        url: "${ctx}/pages/publications/subjectStat",
	        data: {
	        	isLicense:'${sessionScope.selectType}',
	        	code:'${form.code}',
	        	type:'${form.type}',
	        	pCode:'${form.pCode}',
	        	publisherId:'${publisherid}',
	        	subParentId:'${form.subParentId}',
	        	pubYear:'${form.pubYear}',
	        	order:'${form.order}'
	        },
	        success : function(data) { 
             	$(parObj).html(data);
            },  
            error : function(data) {
              	$(parObj).html(data);
            }  
      });
	}
	function getTypeStat(){
		var parObj=$("#type_statistic");
		$.ajax({
			type : "GET",
			async : true,    
	        url: "${ctx}/pages/publications/typeStat",
	        data: {
	        	isLicense:'${sessionScope.selectType}',
	        	code:'${form.code}',
	        	type:'${form.type}',
	        	pCode:'${form.pCode}',
	        	publisherId:'${publisherid}',
	        	subParentId:'${form.subParentId}',
	        	pubYear:'${form.pubYear}',
	        	order:'${form.order}'
	        },
	        success : function(data) { 
             	$(parObj).html(data);
            },  
            error : function(data) {
              	$(parObj).html(data);
            }  
      });
	}
	function getYearStat(){
		var parObj=$("#year_statistic");
		$.ajax({
			type : "POST",
			async : false,    
	        url: "${ctx}/pages/publications/yearStat",
	        data: {
	        	isLicense:'${sessionScope.selectType}',
	        	code:'${form.code}',
	        	type:'${form.type}',
	        	pCode:'${form.pCode}',
	        	publisherId:'${publisherid}',
	        	subParentId:'${form.subParentId}',
	        	pubYear:'${form.pubYear}',
	        	order:'${form.order}'
	        },
	        success : function(data) { 
             	$(parObj).html(data);
            },  
            error : function(data) {
              	$(parObj).html(data);
            }  
      });
	}
	function getPublisherStat(){
		var parObj=$("#publisher_statistic");
		$.ajax({
			type : "POST",
			async : false,    
	        url: "${ctx}/pages/publications/publisherStat",
	        data: {
	        	isLicense:'${sessionScope.selectType}',
	        	code:'${form.code}',
	        	type:'${form.type}',
	        	pCode:'${form.pCode}',
	        	publisherId:'${form.publisherId}',
	        	subParentId:'${form.subParentId}',
	        	pubYear:'${form.pubYear}',
	        	order:'${form.order}'
	        },
	        success : function(data) { 
             	$(parObj).html(data);
            },  
            error : function(data) {
              	$(parObj).html(data);
            }  
      });
	}
	function seeMore(){
		var status = $("#moreStatus").val();
		if(status=="1"){
			$("li[name='more']").css("display","block");
			$("#moreStatus").val('2');
			$("#see").html("<ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.Less' sessionKey='lang'/>...");
		}else{
			$("li[name='more']").css("display","none");
			$("#moreStatus").val('1');
			$("#see").html("<ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...");
		}
	}
	function seeYearMore(){
		var status = $("#moreYearStatus").val();
		if(status=="1"){
			$("li[name='yearmore']").css("display","block");
			$("#moreYearStatus").val('2');
			$("#seeyear").html("<ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.Less' sessionKey='lang'/>...");
		}else{
			$("li[name='yearmore']").css("display","none");
			$("#moreYearStatus").val('1');
			$("#seeyear").html("<ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...");
		}
	}
	function seePubMore(){
		var status = $("#morePubStatus").val();
		if(status=="1"){
			$("li[name='pubmore']").css("display","block");
			$("#morePubStatus").val('2');
			$("#seepub").html("<ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.Less' sessionKey='lang'/>...");
		}else{
			$("li[name='pubmore']").css("display","none");
			$("#morePubStatus").val('1');
			$("#seepub").html("<ingenta-tag:LanguageTag key='Page.Publications.DetailList.Link.More' sessionKey='lang'/>...");
		}
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
	//在线阅读
	function viewPopTips(id,page,yon) {
		var url="";
		if(page=='0'){
			url = "${ctx}/pages/view/form/view?id="+id;
		}else{
			url = "${ctx}/pages/view/form/view?id="+id+"&nextPage="+page;
		}
		//首先Ajax查询要阅读的路径
		if(yon=='2'){
			art.dialog.open(url,{title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.OLRead" sessionKey="lang" />",width: $(window).width()*0.8,height: $(window).height()*0.9,lock: true,close:function(){
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
					}});
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
					art.dialog.open(s[1],{title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.OLRead" sessionKey="lang" />",width: $(window).width()*0.8,height: $(window).height()*0.9,lock: true,close:function(){
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
					}});
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
								$("#select_price_" + pid).css("display","none");
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
								$("#favourites_check_"+pid).attr("checked",true);
								$("#favourites_"+pid).attr("src","${ctx}/images/collect_light.png");
								$("#favourites_"+pid).removeAttr("onclick");
								$("#favourites_"+pid).attr("title","<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
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
      var pubStr = "";
  	  var srcStr = "";
      var pubIds=new Array(); 
      function getPid(){
      		pubStr="";
            $("input[name='publicationsIds']").each(function() {  
                if ($(this).attr("checked")) {  
                    pubIds.push($(this).val());
                    pubStr += $(this).val()+"@";
                }  
            });
      }
      function getSid(){
      		srcStr = "";
            $("input[name='srcIds']").each(function() {  
                srcStr += $(this).val()+"@";
            });
      }
      function batchSub(){      
      		getSid();
      		getPid();
      		if(pubIds!=null && pubIds.length>0){
	          	$.ajax({
					type : "POST",
					url : "${ctx}/pages/favourites/form/batchCommit",
					data : {
					    srcIds : srcStr,
						pubIds : pubStr,
						r_ : new Date().getTime()
					},
					success : function(data) {
						var s = data.split(":");
						if (s[0] == "success") {
							art.dialog.tips(s[1],1);//location.reload();
							
							var shows= new Array(); //定义一数组
							shows=srcStr.split("@"); //字符分割      
							for (i=0;i<shows.length ;i++ ){
								$("#favourites_"+shows[i]).css("display","block"); 
							}
							var hids= new Array();
							hids=pubStr.split("@");
							for (i=0;i<hids.length ;i++ ){    
								$("#favourites_"+hids[i]).css("display","none");
							}
							
						}else{
							art.dialog.tips(s[1],1,'error');
						}
					},
					error : function(data) {
						art.dialog.tips(data,1,'error');
					}
				});
			}else{
				art.dialog.tips("<ingenta-tag:LanguageTag key="Pages.Favorite.Prompt.Not.Selected" sessionKey="lang" />",1,'error');
			}
		}	
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
        <div class="listTitle">${publisher.name}</div>
        <div class="companyLogo"><c:if test="${publisher.logo!=null&&publisher.logo!='' }"><img style="width:1px;height:1px;" src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/${publisher.logo}" onload="AutoResizeImage(250,120,this)" /></c:if></div>
        <div class="clearit"></div>
        
        <!--定义 左边分类列表 开始-->
        <div class="subClass">
        	<c:if test="${sessionScope.mainUser!=null || sessionScope.institution!=null}">
        	<p><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Publications.License.Lable"/></p>
          	<div class="clear"></div>
	          <ul>
	          	<li><a <c:if test="${sessionScope.selectType!=null&&sessionScope.selectType=='' }">style="color:#ee8200;"</c:if> href="${ctx }/pages/publications/form/${publisherid}/list?sub=${parentId}&ptype=${ptype}&pubYear=${pubYear}&isLicense=&r_=${r_}"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Page.Select.All"/></a></li>
	          	<li><a <c:if test="${sessionScope.selectType==null||sessionScope.selectType==1 }">style="color:#ee8200;"</c:if> href="${ctx }/pages/publications/form/${publisherid}/list?sub=${parentId}&ptype=${ptype}&pubYear=${pubYear}&isLicense=1&r_=${r_}"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.license"/></a></li>
	          </ul>
          	<div class="clear"></div>
          	</c:if>
          <p><ingenta-tag:LanguageTag key="Page.Publications.DetailList.Lable.Sub" sessionKey="lang" />：</p>
          <div class="clear"></div>
          <ul id="subject_statistic">
          	<img src="${ctx}/images/loading.gif"/>
          </ul>
          <div class="clear"></div>
          
          <p><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Filter.Type" sessionKey="lang" />：</p>
          <div class="clear"></div>
          <ul id="type_statistic">
          	<img src="${ctx}/images/loading.gif"/>
          </ul>
       		
          <div class="clear"></div>
          <p><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Filter.PubDate" sessionKey="lang" />：</p>
          <div class="clear"></div>
          <ul id="year_statistic">
			<img src="${ctx}/images/loading.gif"/>
          </ul>
          
        </div>
        <!--定义 左边分类列表 结束-->
        
        <!--定义 右侧图书详细信息列表 开始-->
        <div class="resultsContainer">
          <div class="resultsnav">		<!--  ${form.curCount} -->
            <p>1 - ${form.pageCount} of ${form.count}<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Lable.Results" sessionKey="lang" /></p>
            <div class="clearit"></div>
            <div class="clear"></div>
            <div class="a_to_z">
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list">#</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=A">A</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=B">B</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=C">C</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=D">D</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=E">E</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=F">F</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=G">G</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=H">H</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=I">I</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=J">J</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=K">K</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=L">L</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=M">M</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=N">N</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=O">O</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=P">P</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=Q">Q</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=R">R</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=S">S</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=T">T</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=U">U</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=V">V</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=W">W</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=X">X</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=Y">Y</a>
                <a name="sort" title="" href="${ctx}/pages/publications/form/${publisherid }/list?letter=Z">Z</a>
                </div>
                <div class="clear"></div>
                <div class="a_to_z">
                <ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Select.Sort" sessionKey="lang" />：
		        <a name="sort2" id="upDesc" href="${ctx}/pages/publications/form/${publisherid }/list?letter=${form.letter}&orderDesc=upDesc" ><ingenta-tag:LanguageTag key="Page.Publications.list.Order.update.desc" sessionKey="lang" /></a>
		        <a name="sort2" id="pubDesc" href="${ctx}/pages/publications/form/${publisherid }/list?letter=${form.letter}&orderDesc=pubDesc" ><ingenta-tag:LanguageTag key="Page.Publications.list.Order.pubdate.desc" sessionKey="lang" /></a>
		          </div>
                <div class="clear"></div>
          </div>
          <div class="clear"></div>
          <div class="separatedList">
            <div class="clear"></div>
            <c:if test="${sessionScope.mainUser!=null}">
            <div class="chosen_bar"> <span>
			  <input name="" type="checkbox" class="selectAll" value="" id="selectAll"/>
			  <label for="selectAll"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Lable.Select.All"/></label>
			  </span> <a href="javascript:void(0)" id="submit" onclick="batchSub()"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Publication.Button.bulk.Favorite"/></a></div>
            </c:if>
            <ul id="uboxstyle">
            			<c:forEach items="${list }" var="p" varStatus="index">
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
						<c:set var="favourite" value="${sessionScope.mainUser!=null&&(p.favorite==null||p.favorite<=0) }"/>
						<c:set var="recommand" value="${(p.recommand>0||sessionScope.mainUser.institution!=null ) &&(p.subscribedIp==null||p.subscribedIp<=0)&&(p.free!=2&&p.oa!=2)}"/>	
						         					
							<li class="sublications  <c:if test="${license==false&&oa==false&&free==false }">yellow</c:if><c:if test="${license==true||oa==true||free==true }">green</c:if>">
								 
								<div class="new_title" <c:if test="${sessionScope.mainUser==null }">style="padding-left:0px;"</c:if>>
									<c:if test="${sessionScope.mainUser!=null }">
									<span class="check_box">
								     <input id="favourites_check_${p.id }" type="checkbox" name="publicationsIds" value="${p.id}" onclick="getPid()" <c:if test="${favourite==false}">checked</c:if>/>
								     <input type="hidden" name="srcIds" value="${p.id}"/>
								     </span>
								     </c:if>
									<span class="<c:if test="${license==false&&free==false&&oa==false }">lock</c:if><c:if test="${license==true||free==true||oa==true }">unlock</c:if>"></span>
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
									<c:if test="${p.cover!=null&&p.cover!='' }"><img style="width:1px;height:1px;" src="${ctx}/pages/publications/form/cover?id=${p.id}" onload="AutoResizeImage(140,140,this);" onerror="this.src='${ctx}/images/noimg.jpg'"/></c:if></a>
									</dd>
								</dl>
								<div class="new_name"><a href="${ctx}/pages/publications/form/article/${p.id}">${p.title }</a></div>
								<div class="publistcontent author">
									<c:if test="${p.type!=2 }">${p.author }<c:if test="${p.type==4 }"> in <a href="${ctx}/pages/publications/form/article/${p.publications.id}">${p.publications.title}</a></c:if>(${fn:substring(p.pubDate,0,4) })</c:if>
									<c:if test="${p.type==2 }">Volume ${p.startVolume }-Volume ${p.endVolume }</c:if>
								</div>
								<div class=publistcontent>
									<span><b><a href="${ctx}/pages/publications/form/${p.publisher.id}/list">${p.publisher.name }</a></b>
									</span>
								</div>
								<c:if test="${(license==true||oa==true||free==true)&&p.type!=2 }">
								<div class=publistcontent>
									<span>
									<b>
									<c:if test="${p.type==4 }">
										<a href="${ctx}/pages/publications/form/download?id=${p.id}" target="_blank" style="margin-right:20px;">>><ingenta-tag:LanguageTag key="Global.Lable.Prompt.Download.PDF" sessionKey="lang" /></a>
									</c:if>
									<a href="javascript:void(0)" onclick="viewPopTips('${p.id}','0',<c:if test="${oa==false&&free==false}">1</c:if><c:if test="${oa==true||free==true}">2</c:if>)">>><ingenta-tag:LanguageTag key="Global.Lable.Prompt.Preview" sessionKey="lang" /></a></b>
									</span>
								</div>
								</c:if>
								<c:if test="${add||favourite||recommand||(sessionScope.mainUser!=null && !favourite)}">
								<div class="publistcontent">
									<c:if test="${p.priceList!=null && fn:length(p.priceList)>0&&add==true}">
										<select name="price_${p.id }" id="price_${p.id }" class="select_box">
											<c:forEach items="${p.priceList }" var="pr" varStatus="indexPr">
											<option value="${pr.id }"><c:if test="${pr.type==2 }">L</c:if><c:if test="${pr.type==1 }">P</c:if>${pr.complicating}-${pr.price }${pr.currency }</option>
											</c:forEach>
										</select>
									</c:if>
						            	<c:if test="${add }">
											<img id="add_${p.id }" src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/images/cart.png" style="vertical-align:middle;margin: 5px 0;cursor:pointer;" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Buy' sessionKey='lang' />" onclick="addToCart('${p.id}',1)"/>
											
										</c:if>
										<c:if test="${favourite }">
										   	<img id="favourites_${p.id }" src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/images/favorite.png" style="vertical-align:middle;margin: 5px 0;cursor:pointer;" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' />" onclick="addFavourites('${p.id }')"/>
										   
										</c:if>
										<c:if test="${sessionScope.mainUser!=null && !favourite }">
										   	<img src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/images/collect_light.png" style="vertical-align:middle;margin: 5px 0;" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />" />
										   
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
												<div class="content clearcss">
													<a class="button" onclick="senfe(this);">+<ingenta-tag:LanguageTag key="Page.Index.Search.Desc.Show" sessionKey="lang" /></a>
													${fn:replace(fn:replace(fn:replace(p.remark,"&lt;","<"),"&gt;",">"),"&amp;","&")}  
								
												</div>
											</div>
										</li>
									</ul>
									<div class="clear"></div>
								</div></li>
							</c:forEach>
             
            </ul>
          </div>
          <div class="clear"></div>
          <div class="publistcontent">
            <ingenta-tag:SplitTag page="${form.curpage}" pageCount="${form.pageCount}" count="${form.count}" formName="form" method="get" showNum="5" url="${form.url}" i18n="${sessionScope.lang}"/>
          </div>
        </div>
     	<!--移动窗口开始-->
	<div style="display:none;">
		<div id="simTestContent">
			<div class="mainlist">
				<!--内容开始-->
				<ul>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td style="width:20%;"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.OrgName" sessionKey="lang" />:</td>
							<td style="width:80%;">${sessionScope.institution.name}</td>
						</tr>
						<tr>
							<td style="width:20%;"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Title" sessionKey="lang" />:</td>
							<td id="title" style="width:80%;"></td>
						</tr>
						<tr>
							<td style="width:20%;"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Code" sessionKey="lang" />:</td>
							<td id="code" style="width:80%;"></td>
						</tr>
						<tr>
							<td style="width:20%;"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Type" sessionKey="lang" />:</td>
							<td id="type1" style="width:80%;"></td>
						</tr>
						<tr>
							<td style="width:20%;"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.COC" sessionKey="lang" />:</td>
							<td id="subName" style="width:80%;"></td>
						</tr>
						<tr>
							<td style="width:20%;"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Person" sessionKey="lang" />:</td>
							<td style="width:80%;">${sessionScope.recommendUser.name}</td>
						</tr>
						<tr>
							<td style="width:20%;"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Identity" sessionKey="lang" />:</td>
							<td style="width:80%;"><c:if test="${sessionScope.recommendUser.level==5}"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Identity.Expert" sessionKey="lang" /></c:if>
								<c:if test="${sessionScope.recommendUser.level!=5}"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Identity.NotExpert" sessionKey="lang" /></c:if>
							</td>
						</tr>
						<tr>
							<td style="width:20%;"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Note" sessionKey="lang" />:</td>
							<td style="width:80%;"><textarea style="width:70%;height:150px;" onkeyup="$('#rnote').val(this.value)"></textarea></td>
						</tr>
						<tr>
							<td style="width:100%;text-align:center;" colspan="2">
								<input type="hidden" id="pubid"/>
								<a onclick="recommendSubmit()" class="g_gret"><ingenta-tag:LanguageTag key="Page.Index.Search.Button.Recommed" sessionKey="lang" /></a>&nbsp;&nbsp;
								<a onclick="confirmTerm()" class="g_cancel"><ingenta-tag:LanguageTag key="Global.Button.Cancel" sessionKey="lang" /></a>
							</td>
						</tr>
					</table>
				</ul>
				<!--内容结束-->
			</div>
		</div>
		<!--simTestContent end-->
	</div>
	<!--移动窗口结束-->
        <!--定义 右侧图书详细信息列表 结束-->
  <div class="clear"></div>      
      </div>
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
