<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
		<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
		<c:set var="ctx">/EPublishing</c:set>
		<meta name="viewport" content="width=device-width">
        <meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="stylesheet" href="${ctx}/epubReaderDemo/css/normalize.css">
        <link rel="stylesheet" href="${ctx}/epubReaderDemo/css/main.css">
        <link rel="stylesheet" href="${ctx}/epubReaderDemo/css/popup.css">
        <link rel="stylesheet" href="${ctx}/epubReaderDemo/css/scroll.css">
        <link rel="stylesheet" href="${ctx}/epubReaderDemo/css/menu.css">
       	<script src="${ctx}/epubReaderDemo/js/libs/jquery/jquery-2.0.3.js"></script>
		<script src="${ctx}/epubReaderDemo/js/libs/jquery/jquery.mousewheel.min.js"></script>
		<script src="${ctx}/epubReaderDemo/js/libs/jquery/JQueryRC4.js"></script>
		<script>
            "use strict";
            
            document.onreadystatechange = function () {  
              if (document.readyState == "complete") {
                EPUBJS.filePath = "${ctx}/epubReaderDemo/js/libs/";
                EPUBJS.cssPath = "${ctx}/epubReaderDemo/css/";
                EPUBJS.pubId="${pub.id}";
				
                window.Reader = ePubReader("${ctx}${epubPath}/", { reload: true });
              }  
            };
        
          </script>
        <script type="text/javascript">
        function getNoteList(callback){
        	var id="${pub.id}";
        	if(id){        	
				$.ajax({
					type : "POST",
					async : false,    
			        url: "${ctx}/pages/view/form/getEpubNote",
			        data : {
			        	sourceId : '${form.id}'
			        },
			        success : function(data) { //alert(data);
			        	var json = eval(data);
			        	callback(json);
		            }
		             
			     });
			}
		}
		 $(document).ready(function(e) {
		     $(".scroll-bar-h").hover(function(e){
				 $(".scroll-bar-inner").css("opacity","1");
				 $(".scroll-bar").css("opacity","1");
				 $(".progress-bar").css("background","#4e4e4e");
				 $(".ic-progress-point").css("background-position","-32px -56px");
				 $(".bubble-wrap").css("display","block");
			 },
			 function(e){
				 $(".scroll-bar-inner").css("opacity","0.3");
				 $(".scroll-bar").css("opacity","0.3");
				 $(".progress-bar").css("background","#b1baba");
				 $(".ic-progress-point").css("background-position","0 -56px");
				 $(".bubble-wrap").css("display","none");
			 });
			 /* $(".date").hover(function(e){
				$(this).next(".text_span").toggle("fast");
			 }); */			
		});
		//删除笔记
		function deleteNote(tag,callback){
			var id="${pub.id}";
			if(id){
				$.ajax({
					type : "POST",  
			        url: "${ctx}/pages/view/form/deleteEpubNote",
			        data: {
			        	sourceId:id,
			        	tag:tag,
						r_ : new Date().getTime()
			         },
			         success : function(data) {
			           	var s = eval(data);
			           	alert(s[0].info);
			           	if(s[0].msg=='success'){
			           		//删除列表项
			           		callback(tag);     	
			           	}  
			          },  
			          error : function(data) {  
			    			alert(data,1,'error');
			    	  }  
				});
			}
		}
		//添加笔记
		function addNotes(note,callback){
			var content = note.context;
			var id="${pub.id}";
			//alert($("#checks").css("color")=='rgb(255, 0, 0)');
			if(content==''){
				alert("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteNull'/>");
			}else if(id==''){
				alert("出版物id不能为空");
			}else{
				$.ajax({
					type : "POST",  
		            url: "${ctx}/pages/view/form/addEPubNote",
		            data: {
		            	tag:note.tag,
		            	sourceId:id,
		            	noteContent:content,
						r_ : new Date().getTime()
		            },
		            success : function(data) {  
			              var s = eval(data);
			              if(s[0].success!=null&&s[0].success!=undefined){
				              alert(s[0].success);
				              callback(note);	
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
			var currPage2=getDocViewer().getCurrPage();
			if( currPage2!=0){
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
		}		
		</script>
        
        <!--<script async src="epubjs/libs/jquery.touchy.min.js"></script>-->
        
        <!-- zip -->
        <script src="${ctx}/epubReaderDemo/libs/zip/zip.js"></script>
        <script src="${ctx}/epubReaderDemo/libs/zip/zip-fs.js"></script>
        <script src="${ctx}/epubReaderDemo/libs/zip/zip-ext.js"></script>
        <script src="${ctx}/epubReaderDemo/libs/zip/inflate.js"></script>
        <script src="${ctx}/epubReaderDemo/libs/zip/mime-types.js"></script>
        
        
        <!-- Render -->
        <script src="${ctx}/epubReaderDemo/libs/underscore/underscore.js"></script>
        <script src="${ctx}/epubReaderDemo/libs/rsvp/rsvp.js"></script>
        <script src="${ctx}/epubReaderDemo/libs/fileStorage/fileStorage.min.js"></script>

        <script src="${ctx}/epubReaderDemo/src/base.js"></script>
        <script src="${ctx}/epubReaderDemo/src/core.js"></script>
        <script src="${ctx}/epubReaderDemo/src/unarchiver.js"></script>
        <script src="${ctx}/epubReaderDemo/src/parser.js"></script>
        <script src="${ctx}/epubReaderDemo/src/hooks.js"></script>
        <script src="${ctx}/epubReaderDemo/src/book.js"></script>
        <script src="${ctx}/epubReaderDemo/src/chapter.js"></script>
        <script src="${ctx}/epubReaderDemo/src/renderer.js"></script>
        <script src="${ctx}/epubReaderDemo/src/replace.js"></script>
        <script src="${ctx}/epubReaderDemo/src/epubcfi.js"></script>
        
        <!-- Hooks -->
        <script async src="${ctx}/epubReaderDemo/hooks/default/transculsions.js"></script>
        <script async src="${ctx}/epubReaderDemo/hooks/default/endnotes.js"></script>
        <script async src="${ctx}/epubReaderDemo/hooks/default/smartimages.js"></script>

        
        <!-- Reader -->        
        <script src="${ctx}/epubReaderDemo/reader/reader.js"></script>
		<script src="${ctx}/epubReaderDemo/reader/plugins/search.js"></script>
        <script src="${ctx}/epubReaderDemo/reader/controllers/controls_controller.js"></script>
        <script src="${ctx}/epubReaderDemo/reader/controllers/meta_controller.js"></script>
        <script src="${ctx}/epubReaderDemo/reader/controllers/reader_controller.js"></script>
        <script src="${ctx}/epubReaderDemo/reader/controllers/settings_controller.js"></script>
        <script src="${ctx}/epubReaderDemo/reader/controllers/sidebar_controller.js"></script>
        <script src="${ctx}/epubReaderDemo/reader/controllers/toc_controller.js"></script>
        <script src="${ctx}/epubReaderDemo/reader/controllers/notes_controller.js"></script>
        <script src="${ctx}/epubReaderDemo/reader/controllers/scroll_controller.js"></script>
        <script src="${ctx}/epubReaderDemo/reader/controllers/menu_controller.js"></script>
        <!-- Full Screen -->
        <script src="${ctx}/epubReaderDemo/js/libs/screenfull.min.js"></script>
        
        <!-- Highlights -->
        <script src="${ctx}/epubReaderDemo/js/libs/jquery.highlight.js"></script>
        <script async src="${ctx}/epubReaderDemo/hooks/extensions/highlight.js"></script>
        <script src="${ctx}/epubReaderDemo/js/test.js"></script>
    </head>
	<body style="width:1050px">
    <div >
      <div id="sidebar">
        <div id="panels">
          <input id="searchBox" placeholder="search" type="search">
          
          <a id="show-Search" class="show_view icon-search" data-view="Search">Search</a>
          <a id="show-Toc" class="show_view icon-list-1 active" data-view="Toc">TOC</a>
          <!-- <a id="show-Bookmarks" class="show_view icon-bookmark" data-view="Bookmarks">Bookmarks</a> -->
          <a id="show-Notes" class="show_view icon-edit" data-view="Notes">Notes</a>
         
        </div>
        <div id="tocView">
        </div>
        <div id="searchView">
          <ul id="searchResults"></ul>
        </div>
        <!-- <div id="bookmarksView">
          <ul id="bookmarks"></ul>
        </div> -->
        <div id="notesView">
          <ul id="notes"></ul>
        </div>
      </div>
      <div id="main">        
        <div id="titlebar">
          <div id="opener">
            <a id="slider" class="icon-menu">Menu</a>
          </div>
          <div id="metainfo">
            <span id="book-title"></span>
            <span id="title-seperator">&nbsp;&nbsp;–&nbsp;&nbsp;</span>
            <span id="chapter-title"></span>
          </div>
          <div id="title-controls">
            <a id="bookmark" class="icon-bookmark-empty">Bookmark</a>
            <a id="setting" class="icon-cog">Settings</a>
            <a id="fullscreen" class="icon-resize-full">Fullscreen</a>
          </div>
        </div>
        
        <div id="divider"></div>
        <div id="prev" class="arrow">‹</div>
        <div id="viewer"></div>
        <div id="next" class="arrow">›</div>
        
        <div id="loader"><img src="${ctx}/epubReaderDemo/img/loader.gif"></div>
      </div>
      <div id="menu" class="box">
    	<ul id="menuUl" class="addul">
        	<li class="add"><a id="btAddNote" href="javascript:void(0)" class="addlia">添加笔记</a></li>
            <li><a id="btDelNote" href="javascript:void(0)" class="addlib">删除笔记</a></li>
            <li><a id="btAddMark" href="javascript:void(0)" class="addlic">添加书签</a></li>
        </ul>
        <div id="textNote" class="add_text"><textarea id="noteContext" class="text"></textarea></div>
      </div>
      <div class="modal md-effect-1" id="settings-modal">
          <div class="md-content">
              <h3>Settings</h3>
              <div>
                  <p>
                    <!-- <input type='radio' name='fontSize' value='x-small'><span class='xsmall'>Extra Small</span><br>
                    <input type='radio' name='fontSize' value='small'><span class='small'>Small</span><br>
                    <input type='radio' name='fontSize' value='medium'><span class='medium'>Medium</span><br>
                    <input type='radio' name='fontSize' value='large'><span class='large'>Large</span><br>
                    <input type='radio' name='fontSize' value='x-large'><span class='xlarge'>Extra Large</span> -->
                    <input type="checkbox" id="sidebarReflow" name="sidebarReflow">Reflow text when sidebars are open.</input>
                  </p>
              </div>
              <div class="closer icon-cancel-circled"></div>
          </div>
      </div>
      <div id="scrollBar" class="scroll-bar-h">
			<div class="scroll-bar-inner"></div>
		    <div class="scroll-bar">
		    	<div class="progress-bar"></div>
		    </div>
		    <div class="bubble-wrap">
		    	<div class="arrow-point"></div>
		        <div class="inner">
		        	<span id="textBubble"></span>
		        </div>
		    </div>
		</div>
      </div>
      <div class="overlay"></div>
    </body>
</html>
