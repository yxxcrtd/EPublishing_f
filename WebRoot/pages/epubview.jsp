<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
	});
	//获取章节列表
	function getTocList(){
		var parObj=$("#chapter_label");
		$.ajax({
			type : "POST",
			async : false,    
	        url: "${ctx}/pages/view/form/tocList",
	        data : {
	        	id : '${form.id}',
	        	publicationsTitle : "${publicationsTitle}"
	        },
	        success : function(data) { 
	            	$(parObj).html(data);
	            	$(parObj).css("text-align","left");
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
		          }else{
		          	if(s[0].error!=undefined){
		          		alert(s[0].error);
		          	}else{
		          		
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
                	listHtml += "<a onclick=\"getDocViewer().gotoPage('"+ss[i].pageNumber+"')\"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Label.First'/>"+ss[i].pageNumber+"<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Label.Pages'/></a>";
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
          }/* else{
          	$("#copy_but").removeAttr("margin-left");
          } */
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
  //打印
function turning(page){
	var p=page;
	if(p=='p'){
		p=parseInt($("#curChapter").val())-1;
	}else if(p=='n'){
		p=parseInt($("#curChapter").val())+1;
	}
	$.ajax({
		type : "POST",  
        url: "${ctx}/pages/view/form/turning",
        data: {
        	pubId:$("#pubId11").val(),
           	pageNum:p,
			r_ : new Date().getTime()
        },
        success : function(data) {  
          var s = eval(data);
          if(s[0].canTurning=='true'){          	
          	$("#contentBox").attr("src","${ctx}" + s[0].turningTo);
          	$("#curChapter").val(p);
          } else{
          	alert(s[0].error);
          }
       	  
        },  
        error : function(data) {  
          var s = eval(data);
          alert(s[0].error);
        }  
	});	
}
//]]-->	 
</script>
<script type="text/javascript">
 function SetCwinHeight(iframeObj){
  if (document.getElementById){
   if (iframeObj && !window.opera){
    if (iframeObj.contentDocument && iframeObj.contentDocument.body.offsetHeight){
     iframeObj.height = iframeObj.contentDocument.body.offsetHeight;
     $("#read_content").height=iframeObj.height;
     $("#overlay").css("height",iframeObj.height+10); 
    }else if(document.frames[iframeObj.name].document && document.frames[iframeObj.name].document.body.scrollHeight){
     iframeObj.height = document.frames[iframeObj.name].document.body.scrollHeight;
     $("#read_content").height=iframeObj.height;
    }
   }
  }
 }
 function switchLayer(key){
 	if(key){
 		var iframe=$("#contentBox");
 		$("#overlay").css("height",iframe.height()+10); 		
 	}else{
 		$("#overlay").css("height",1); 	
 	}
 }
 </script> 
</head>
<body>
	<div class="big">
		<!--以下top state -->
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--以上top end -->
		<!--以下中间内容块开始-->
		<div class="main">		
		<c:if test="${webUrl==null||webUrl=='' }">
			<!-- 左侧内容开始  -->
		  	<div id="read_content" class="read_content" style="padding:0px">
		    	<h1>${form.publicationsTitle }</h1>
		        <div style="height: 30px; background-color: rgb(245, 245, 245);text-align:center">
					<input id="fristPage" type="button" onclick="turning(${fristChapter.startPage})" style="margin:1px;width:50px;height:28px" value="首页"/>
					<input id="prevPage" type="button" onclick="turning('p')" style="margin:1px;width:50px;height:28px" value="上一页"/>
					<input id="nextPage" type="button" onclick="turning('n')" style="margin:1px;width:50px;height:28px" value="下一页"/>
					<input id="endPage" type="button" onclick="turning(${endChapter.startPage})" style="margin:1px;width:50px;height:28px" value="末页"/>
					<input type="button" onclick="switchLayer(true)" style="margin:1px;width:80px;height:28px" value="关闭拷贝"/>
					<input type="button" onclick="switchLayer(false)" style="margin:1px;width:80px;height:28px" value="打开拷贝"/>
				</div>		
		        <div>
		        <div  id="overlay" style="position:absolute;width:780px"></div>
		        <iframe  onload="SetCwinHeight(this)" width="780"  frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" src="<c:if test="${fristChapter!=null}">${ctx}${fristChapter.pdf}</c:if>" id="contentBox">
		        		
             	</iframe>
		        </div>
		        
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
    			<h1><ingenta-tag:LanguageTag sessionKey='lang' key='Page.view.Lable.Chapter'/></h1>
    			<ul class="chapter" id="chapter_label" style="text-align: center;">
		        	<img src="${ctx}/images/loading.gif"/>
		        </ul>
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
	<input type="hidden" id="curChapter" value="${fristChapter.startPage}"/>	
	<!-- 底部的版权信息 -->
	<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
	<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
      </div>
   </body>
</html>