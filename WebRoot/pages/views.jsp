<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
	<%@ include file="/common/tools.jsp"%>
	<%@ include file="/common/ico.jsp"%>
	<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/flexpaper/css/flexpaper.css" />
	<script type="text/javascript" src="${ctx}/flexpaper/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/flexpaper/js/jquery.extensions.min.js"></script>
	<script type="text/javascript" src="${ctx}/flexpaper/js/flexpaper_flash.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery-heartbeat.js"></script>
	<script type="text/javascript">
	//<![data[
	var noteLength_yes = "<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteLength.yes'/>";
	var noteLength_number = "<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteLength.number'/>";
	var noteLength_no = "<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteLength.no'/>";
	var delBut = "<ingenta-tag:LanguageTag sessionKey='lang' key='Global.Button.Delete'/>";
	var ctx = "<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>";
	$(function() { 
		//获取快捷工具
//		getQuickTools();
	 	//获取章节列表
		getTocList();
		$("#content").bind("input propertychange", function() { 
		strLenCalc($(this), 'checklen', 2000);
		}); 
		initMenu();
		
		var onmousewheel = (function () {
            if (window.addEventListener) { 
				return function (el, sType, fn, capture) { 
					el.addEventListener(sType, fn, (capture)); 
				}; 
			} else if (window.attachEvent) { 
				return function (el, sType, fn, capture) { 
					el.attachEvent("on" + sType, fn); 
				}; 
			} else { 
				return function () { }; 
			} 
        })()
		, mousewheel = (/Firefox/i.test(navigator.userAgent)) ? "DOMMouseScroll" : "mousewheel"; 
        function flashMousewhee() { 
            var o = getDocViewer(); 
            if (!o) 
                return false; 
            onmousewheel(o, mousewheel, function (event) { 
                e = window.event || event; 
                stopDefault(e); 
                var detail = (!!e.detail ? e.detail / -6 : e.wheelDelta / 120); 
				var d= {delta: detail};
                o.jsWheelZoom(d); 
            }, false); 
        }; 
        function stopDefault(e) {

            if (e && e.preventDefault) {
                e.preventDefault();
            }
            else {
                window.event.returnValue = false;
            }
            return false;
        }; flashMousewhee();
	});
	function addMouseWheelListener(){  
	   var flash = getDocViewer(); 
	   var ua = navigator.userAgent; 
	   console.log(ua.indexOf("Firefox"));
	   if(ua.indexOf("Firefox") > -1){ 
			 flash.addEventListener('DOMMouseScroll',onWheelZoom,false);
	   }else if(ua.indexOf("MSIE") == -1){  
		   flash.addEventListener('mousewheel',onWheelZoom,false);
	   }else{  
		   flash.attachEvent('onmousewheel',onWheelZoom);
	   } 
	   flash.attachEvent('onmousewheel',onWheelZoom);    
	} 
	//获取章节列表
	function getTocList(){
		var parObj=$("#chapter_label");
		$.ajax({
			type : "POST",
			async : false,    
	        url: "${ctx}/pages/view/form/tocList",
	        data : {
	        	id : '${form.id}',
	        	publicationsTitle : "${publicationsTitle}",
	        },
	        success : function(data) { alert(data);
	        	if($.trim(data)!=''){alert('a');
	            	$(parObj).html(data);
	            	$(parObj).css("text-align","left");
	            }else{alert('b');
	            	$(parObj).hide();
	            	$(parObj).prev("h1").hide();
	            }
	           }, 
	           error : function(data) {
	             	$(parObj).html(data);
	           }  
	     });
	}

function initMenu() {
  $('#menu ul').hide();
//  $('#menu ul:first').show();
  $('#menu li a').click(
    function() {
      var checkElement = $(this).next();
      if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
        return false;
        }
      if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
        $('#menu ul:visible').slideUp('normal');
        checkElement.slideDown('normal');
        return false;
        }
      }
    );
  }
//检查笔记长度
function strLenCalc(obj, checklen, maxlen) { 
	var v = obj.val(), charlen = 0, maxlen = !maxlen ? 1000 : maxlen, curlen = maxlen, len = v.length;
	for(var i = 0; i < v.length; i++) {
		if(v.charCodeAt(i) < 0 || v.charCodeAt(i) > 255) { 
			curlen -= 1; 
		} 
	}
	if(curlen >= len) { 
		$("#checks").html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteLength.yes'/> <strong>"+Math.floor((curlen-len)/2)+"</strong> <ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteLength.number'/>").css('color', ''); 
	} else { 
		$("#checks").html("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteLength.no'/> <strong>"+Math.ceil((len-curlen)/2)+"</strong> <ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteLength.number'/>").css('color', '#FF0000'); 
	} 
}
//删除笔记
function deleteNote(id){
	$.ajax({
		type : "POST",  
        url: "${ctx}/pages/view/form/deleteNote",
        data: {
        	id:id,
			r_ : new Date().getTime()
         },
         success : function(data) {
           	var s = eval(data);
           	alert(s[0].info);
           	if(s[0].msg=='success'){
           		//删除
           		$("#nodes_"+id).css("display","none");
           		$("#isNote").val('');           	
           	}  
          },  
          error : function(data) {  
    			alert(data,1,'error');
    	  }  
	});
}
//添加笔记
function addNotes(){
	var content = $.trim($('#content').val());
	//alert($("#checks").css("color")=='rgb(255, 0, 0)');
	if(content==''){
		alert("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteNull'/>");
	}else if($("#checks").css("color")=='rgb(255, 0, 0)'){
		alert("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteLength'/>");
	}else{
		$.ajax({
				type : "POST",  
	            url: "${ctx}/pages/view/form/addNote",
	            data: {
	            	pageNum:getDocViewer().getCurrPage(),
	            	sourceId:'${form.id}',
	            	noteContent:$('#content').val(),
	            	id:$('#isNote').val(),
					r_ : new Date().getTime()
	            },
	            success : function(data) {  
	              var s = eval(data);
	              if(s[0].success!=null&&s[0].success!=undefined){
		              alert(s[0].success);
		              $("#isNote").val(s[0].noteId);  
		              
		              var curNum = getDocViewer().getCurrPage();
				      
				   	  var newp='<p id="nodes_'+ s[0].noteId +'"><span class="book_l">'
				   	  +'<a onclick=\"getDocViewer().gotoPage(\''+ curNum +'\')\" style=\"margin: 0px;padding: 0px;border-bottom: none;background: none;\">'+curNum+'__'+content+'</a>'
			              +'</span><span class=\"book_r\"><a onclick=\"deleteNote(\''+ s[0].noteId +'\')" title=\"'+delBut+'\"><img src=\"${ctx}/images/cao.gif\"/></a></span></p>'; 
				   			var pArr=$("#noteList").find("p");
				   			if(pArr!=null && pArr.length>0){	   		
					   		for(var i=0;i<pArr.length;i++){
					   			var text=$(pArr[i]).text();
					   			var tArr= text.trim().split('__');
					   			if(curNum<tArr[0]){
					   				$(pArr[i]).before(newp);
					   				flag=1;
					   				break;
					   			}else if(curNum==tArr[0]){
					   				$(pArr[i]).html(newp);
					   				flag=1;
					   				break;
					   			}else if(i==pArr.length-1){
									$(pArr[i]).after(newp);	   			
					   			}	   			
					   		}	   		
					   	}else{
					   		$("#noteList").append(newp);
					   	}					
		          }else{
			          	if(s[0].error!=undefined){
			          		alert(s[0].error);
			          	}
		          }
            },  
            error : function(data) {  
              var s = eval(data);
              alert(s[0].error);
            }  
		});
	}
}
//添加标签
function addLabels(){
	var currPage = $("#curr").val();
	$.ajax({
		type : "POST",  
        url: "${ctx}/pages/view/form/addLable",
        data: {
        	sourceId:$("#pubId11").val(),
        	pageNum:getDocViewer().getCurrPage(),
			r_ : new Date().getTime()
        },
        success : function(data) {  
        	var s = data.split(":");
        	alert(s[1]);  
        	if(s[0]=="success"){
        		var curNum = getDocViewer().getCurrPage();
        		$("#label1").bind("click",function(){
					getDocViewer().gotoPage(curNum);
				});
        		$("#label1").html("<ingenta-tag:LanguageTag sessionKey="lang" key='Pages.view.lable.lable1'/>："+curNum);
        	}
        },  
        error : function(data) {  
          alert(data);
        }  
	});
}
//搜索
function aaaa(){
	if($("#searchView").val()!=''){
		$.ajax({
 				type : "POST",  
            url: "${ctx}/pages/view/form/search",
            data: {
            	id:'${form.id}',
            	value:$("#searchView").val(),
				r_ : new Date().getTime()
            },
            success : function(data) {
	            var s = eval(data);
	            var resultHtml ="<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Label.Result'/>：";
	            if(s[0].count==undefined){
	            	resultHtml += "0";
	            }else{
	            	resultHtml += s[0].count;
	            }
	            $("#search_result").html(resultHtml);
	            var ss = s[0].result;
	            var listHtml = "";
	            $.each(ss,function(i,item ){
                	listHtml += "<a onclick=\"getDocViewer().gotoAndSearch('"+ss[i].pageNumber+"','"+ $("#searchView").val() +"')\"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Label.First'/>"+ss[i].pageNumber+"<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Label.Pages'/></a>";
				});
				$("#search_list").html(listHtml); 
		    },  
		    error : function(data) {  
		      alert(sdata);
		    }  
		});
	}
}
//拷贝
function copy(){
	var pagenum = getDocViewer().getCurrPage();
	$.ajax({
		type : "POST",  
        url: "${ctx}/pages/view/form/tocCopy",
        data: {
        	pubId:$("#pubId11").val(),
           	pageNum:pagenum,
           	licenseId:$("#licenseId11").val(),
           	count:$("#pageCount11").val(),
           	readCount:$("#readCount11").val(),
        	isCopy:true,
        	readCount:'${form.readCount}',
			r_ : new Date().getTime()
        },
        success : function(data) {  
          var s = eval(data);
          if(s[0].isCopy=='true'){          	
          	$("#btn_copy").css("display","none");          	
          	 getDocViewer().switchSelect(true);
          } else{
          	alert(s[0].error);
          } 
       	  $("#copyCount").html("<ingenta-tag:LanguageTag sessionKey='lang' key='Content.View.now.copy.page'/>："+s[0].copyCount);
        },  
        error : function(data) {  
          var s = eval(data);
          alert(s[0].error);
        }  
	});
}
//下载
function downloadaaaaa(){
	var pagenum = getDocViewer().getCurrPage();
	$.ajax({
		type : "POST",  
        url: "${ctx}/pages/view/form/ajaxDownload",
        data: {
        	pubId:$("#pubId11").val(),
           	pageNum:pagenum,
           	licenseId:$("#licenseId11").val(),
           	count:$("#pageCount11").val(),
			r_ : new Date().getTime()
        },
        success : function(data) {  
          var s = eval(data);
          if(s[0].isDownload=='true'){
          	window.location.href="${ctx}/pages/view/form/download?pubId="+$("#pubId11").val()+"&pageNum="+getDocViewer().getCurrPage();
          } else{
          	$("#bbbbb").css("display","none");
          	alert(s[0].error);
          } 
       	  $("#downloadCount").html("<ingenta-tag:LanguageTag sessionKey='lang' key='Content.View.now.download.page'/>："+s[0].downloadCount);
        },  
        error : function(data) {  
          var s = eval(data);
          alert(s[0].error);
        }  
	});	
}
//打印
function ajaxPrint(){
	$.ajax({
		type : "POST",  
        url: "${ctx}/pages/view/form/ajaxPrint",
        data: {
        	pubId:$("#pubId11").val(),
           	pageNum:$("#printStr").val(),
           	licenseId:$("#licenseId11").val(),
           	count:$("#pageCount11").val(),
			r_ : new Date().getTime()
        },
        success : function(data) {  
          var s = eval(data);
          if(s[0].isPrint=='true'){          	
          	getDocViewer().printPaperRange(s[0].printTask);
          } else{
          	alert(s[0].error);
          } 
       	  $("#printCount").html("<ingenta-tag:LanguageTag sessionKey='lang' key='Content.View.now.print.page'/>："+s[0].printCount);
        },  
        error : function(data) {  
          var s = eval(data);
          alert(s[0].error);
        }  
	});	
}
function closeReadWindow(){
	$.ajax({
		type : "POST",  
        url: "${ctx}/pages/complication/close",
        data: {
			r_ : new Date().getTime()
        },
        success : function(data) {
        	history.back();//location.href="${ctx}"==""?"/":"${ctx}";
        },  
        error : function(data) { 
        	history.back();//location.href="${ctx}"==""?"/":"${ctx}";
        }  
	});	
}

//]]-->	 
</script>
<c:if test="${beatInterval!=null && beatInterval!='' && beatInterval!='0'}">
<script type="text/javascript">
$(document).ready(function(){
	$.jheartbeat.set({
		 url: "${ctx}/pages/view/form/beat?l=${form.licenseId }", // The URL that jHeartbeat will retrieve
		 delay: ${fn:trim(beatInterval)}000, // How often jHeartbeat should retrieve the URL
		 div_id: "test_div"}, // Where the data will be appended.
		 function (){
		  
		 });
});
</script>
</c:if>
<style type="text/css">
.search_word ul{
background:#f8f8f8;
border-left:1px solid #bcbcbc;
border-right:1px solid #bcbcbc;
border-bottom:1px solid #bcbcbc;
width:86px;
display:none;
position:absolute;
z-index:5;
margin-left:380px;
margin-top:55px;
margin-left:380px\9;
>margin-left/*IE5.5*/:-105px;
>margin-top/*IE5.5*/:53px;
}
*+html .search_word ul{
margin-left:-103px;
margin-top:53px;
}
@media screen and (-webkit-min-device-pixel-ratio:0){.search_word ul{margin-left:380px;}}
.search_help ul{
margin-top:-7px;
margin-left:484px;
margin-left:484x\9;
_margin-left:-93px;
_margin-top:56px;
}
*+html .search_help ul{
margin-left:-92px;
margin-top:55px;
}
@media screen and (-webkit-min-device-pixel-ratio:0){.search_help ul{margin-left:484px;}}
</style>
</head>
<body>
	<div class="big">
		<!--以下top state -->
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--以上top end -->
		<!--以下中间内容块开始-->
		<div class="main">
		<c:if test="${webUrl!=null&&webUrl!='' }">
		<!-- IFream开始-->
		<iframe scrolling="yes" id="" height="500" width="1100" src="${webUrl }">
    	</iframe>
		<!-- IFream结束 -->
		</c:if>
		<c:if test="${webUrl==null||webUrl=='' }">
			<!-- 左侧内容开始  -->
		  	<div class="read_content" style="height:800px;">		  	
		    	<a class="a_gret" style="float:right; margin:10px 5px 0 0;" onclick="closeReadWindow()">关闭阅读</a>
		    	<h1>${form.publicationsTitle }</h1>
		    	
		        <div id="documentViewer" class="flexpaper_viewer flash_plugin"></div>
		        <script type="text/javascript">
					function getDocumentUrl(document){
						var numPages = ${form.count};
						var url = "{page?pubId={doc}&format=swf&pageNum=[*,0],{numPages}}";
						url = url.replace("{doc}",document);
						url = url.replace("{numPages}",numPages);
						return url;
					}
					function getDocQueryServiceUrl(document){
						return "swfsize.jsp?doc={doc}&page={page}".replace("{doc}",document);
					}
					var startDocument = "";
		
					function append_log(msg){
						$('#txt_eventlog').val(msg+'\n'+$('#txt_eventlog').val());
					}
		
					String.format = function() {
						var s = arguments[0];
						for (var i = 0; i < arguments.length - 1; i++) {
							var reg = new RegExp("\\{" + i + "\\}", "gm");
							s = s.replace(reg, arguments[i + 1]);
						}
						return s;
					};
		
					var fp = new FlexPaperViewer(	
						 '${ctx}/flexpaper/FlexPaperViewer.swf',
						 'documentViewer', { config : {
						 /* DOC : "page?pubId=${form.id}&format=swf&pageNum=1", */
						 /* DOC : /EPublishing/xxx.swf , */
						 DOC : getDocumentUrl('${form.id}'),
						/*  Scale : 0.6,  */
						 ZoomTransition : 'easeOut',
						 ZoomTime : 0.5,
						 ZoomInterval : 0.2,
						/*  FitPageOnLoad : true, */
						 FitWidthOnLoad : true,
						 PrintEnabled:false,
  						 SelectEnabled:false,

						 FullScreenAsMaxWindow : false,
						 ProgressiveLoading : false,
						 MinZoomSize : 0.2,
						 MaxZoomSize : 5,
						 SearchMatchAll : false,
						 InitViewMode : 'window',
						 
						 ViewModeToolsVisible : true,
						 ZoomToolsVisible : true,
						 NavToolsVisible : true,
						 CursorToolsVisible : true,
						 SearchToolsVisible : true,
  						 SearchString : '',
	  					 Reference : 'The reference is test',
	  					 SelectVisible : true,
  						 localeChain: '${sessionScope.lang}',
  						 WMode : 'transparent',
  						 key : '@4516c44a3b7f5ec2893$cc3be1a9ff661cfdef6'
					}});
				</script>
		    </div>
		    <!--左侧内容结束-->
		    <!--右侧内容开始 -->
		    <div class="readlist">
		    	<h1><ingenta-tag:LanguageTag sessionKey='lang' key='Page.view.Lable.tools'/></h1>
		    	<ul id="menu">
			    	<c:if test="${sessionScope.mainUser!=null }">
				   	<li><a><span class="alph"><img src="${ctx }/images/ico_01.gif" style="margin-top:0; margin-left:-18px;" /></span>
				   	<span class="write"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.Label.Record"/></span></a>
				    <ul class="tag">
				       <li>
				         <div>
				         <p class="book_b">
				         	<c:if test="${form.record!=null&&form.record.pages.number>0 }">
				         	<a id="label1" onclick="getDocViewer().gotoPage('${form.record.pages.number}');" style="margin: 0px;padding: 0px;border-bottom: none;background: none;" >
								<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.lable.lable1"/>：${form.record.pages.number }
							</a>
							</c:if>
				            <c:if test="${form.record==null||form.record.pages.number<=0 }">
				            <a id="label1" style="margin: 0px;padding: 0px;border-bottom: none;background: none;" >
				            	<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.lable.nolable"/>
							</a>
							</c:if>
						</p>
				         <p class="book_a">
							<a onclick="addLabels();" class="a_gret"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.lable.button"/></a>
						</p>
				         </div>
				       </li>
				    </ul>
				    </li>
				    <li><a><span class="alph">
				    <input type="hidden" id="isNote" value=""/>
				    <img src="${ctx }/images/ico_02.gif" style="margin-top:0; margin-left:-18px;"/>
				    </span>
				    <span class="write"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.Label.Notes"/></span></a>
				    <ul class="tag">
				       <li>
				         <div>
				         	<div id="noteList"></div>
					         <p class="book_cont" id="checks"></p>
					        <p>
								<textarea name="content" id="content"></textarea>
							</p>
					        <p class="book_a">
					        	<a name="save" class="a_gret" onclick="addNotes();"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.note.button"/></a>
							</p>
				         </div>
				       </li>
				    </ul>
				    </li>
				    </c:if>
				    <c:if test="${form.readCount !=null && form.readCount>0}">
				    <li><a><span class="alph"><img src="${ctx }/images/ico_03.gif" style="margin-top:0; margin-left:-18px;"/></span>
				    <span class="write"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.Label.Copy"/></span></a>
				    <ul class="tag">
				       <li>
				         <div>
					         <p class="book_b"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.copy.Prompt"/>：${form.readCount }</p>					         
					         <p class="book_b" id="copyCount"><ingenta-tag:LanguageTag sessionKey="lang" key="Content.View.now.copy.page"/>：${copyCount}</p>
					         <p id="copy_but" class="book_a">
					         <a id="btn_copy" class="a_gret" onclick="copy()" style="margin-right: 4px;">
					         	<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.copy.Button"/>
					         </a>					         
					         </p>
				         </div>
				       </li>
				    </ul>
				    </li>
				    </c:if>
				    <c:if test="${form.downloadCount !=null && form.downloadCount>0}">
				    <li><a><span class="alph"><img src="${ctx }/images/ico_03.gif" style="margin-top:0; margin-left:-18px;"/></span>
				    <span class="write"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.Label.Download"/></span></a>
				    <ul class="tag">
				       <li>
				         <div>
					         <p class="book_b"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.download.Prompt"/>：${form.downloadCount }</p>					         
					         <p class="book_b" id="downloadCount"><ingenta-tag:LanguageTag sessionKey="lang" key="Content.View.now.download.page"/>：${downloadCount}</p>
					         <p id="copy_but" class="book_a">	
					         <a class="a_gret" onclick="downloadaaaaa()" id="bbbbb" >
					         	<ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.DownLoad"/>
					         </a>	
					         <c:if test="${pubType==4 && downloadPrecent==100}">			         
						         <a class="a_gret" href="${ctx}/pages/view/form/download?pubId=${form.id}&isFull=true" id="downFull" >
						         	<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.View.Button.DownLoad.Full"/>
						         </a>
					         </c:if>
					         </p>
				         </div>
				       </li>
				    </ul>
				    </li>
				    </c:if>
				    <c:if test="${form.printCount !=null && form.printCount>0}">
				    <li><a><span class="alph"><img src="${ctx }/images/ico_03.gif" style="margin-top:0; margin-left:-18px;"/></span>
				    <span class="write"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.Label.Print"/></span></a>
				    <ul class="tag">
				       <li>
				         <div>
					         <p class="book_b"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.print.Prompt"/>：${form.printCount }</p>					         
					         <p class="book_b" id="printCount"><ingenta-tag:LanguageTag sessionKey="lang" key="Content.View.now.print.page"/>：${printCount}</p>
					         <p>					         
					         <input class="a_input" type="text" id="printStr"/>		
					         			         
					         <a class="a_cxu" onclick="ajaxPrint()" id="bt_print" >
					         	<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.Label.Print"/>
					         </a>
					         </p>
				         </div>
				       </li>
				    </ul>
				    </li>
				    </c:if>
				    <li><a><span class="alph"><img src="${ctx }/images/ico_04.gif" style="margin-top:0; margin-left:-18px;"/></span>
				    <span class="write"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.Label.Search"/></span></a>
				    <ul class="tag">
				       <li>
				         <div>
				         <p>
				         <input class="a_input" type="text" id="searchView"/>
				         <a class="a_cxu" onclick="aaaa();">
				         <ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.Search"/></a></p>
				         <div ></div>
				         <p id="search_result" class="book_b"></p>
				         <p id="search_list"></p>
				         </div>
				       </li>
				    </ul>
				    </li>
			    </ul>
			    
                <c:if test="${form.type!=4}">
    			<h1><ingenta-tag:LanguageTag sessionKey='lang' key='Page.view.Lable.Chapter'/></h1>
    			<ul class="chapter tree" id="chapter_label " style="text-align: center;">
		        	<img src="${ctx}/images/loading.gif"/>
		        </ul>
		        </c:if>
		           
		           <c:if test="${form.type==4}">
		           </c:if>
		        
    		</div>
		    <!--右侧内容结束 -->
		    </c:if>
		</div>
		<!--以上中间内容块结束-->
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
	<input type="hidden" id="pubId11" value="${form.id }"/>
	<input type="hidden" id="licenseId11" value="${form.licenseId }"/>
	<input type="hidden" id="pageCount11" value="${form.count }"/>
	<input type="hidden" id="readCount11" value="${form.readCount }"/>
	<input type="hidden" id="isCopy11" value="${form.isCopy }"/>
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
      </div>
   </body>
</html>