<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
<!--
$(document).ready(function(){
	$("#content").bind("input propertychange", function() { 
		strLenCalc($(this), 'checklen', 2000);
	}); 
	initMenu();
});
function initMenu() {
  $('#menu ul').hide();
  $('#menu ul:first').show();
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
           	art.dialog.tips(s[0].info);
           	if(s[0].msg=='success'){
           		//删除
           		$("#nodes_"+id).css("display","none");
           	}  
          },  
          error : function(data) {  
    			art.dialog.tips(data,1,'error');
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
	            	curPageId:$('#curPageId').val(),
	            	sourceId:'${toolForm.id}',
	            	articleId:'${toolForm.articleId}',
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
            	id:'${toolForm.id}',
            	articleId:'${toolForm.articleId}',
            	value:$("#searchView").val(),
				r_ : new Date().getTime()
            },
            success : function(data) {
	            var s = eval(data);
	            var html ="<dt><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Label.Result'/>";
	            if(s[0].count==undefined){
	            	html += "0";
	            }else{
	            	html += s[0].count;
	            }
	            html += "</dt>";
	            var ss = s[0].result;
	            $.each(ss,function(i,item ){
                	html += "<dd><a href=\"#\" onclick=\"go(true,'goto','"+ss[i].pageNumber+"')\"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Label.First'/>"+ss[i].pageNumber+"<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Label.Pages'/></a></dd>";
				});
				$("#search_list").html(html); 
		    },  
		    error : function(data) {  
		        var s = eval(data);
		      alert(s[0].error);
		    }  
		});
	}
}
//拷贝
function copy(){
	var currPage = $("#curr").val();
	$.ajax({
		type : "POST",  
        url: "${ctx}/pages/view/form/viewAjax",
        data: {
        	id:'${toolForm.id}',
        	articleId:'${toolForm.articleId}',
        	count:'${toolForm.count}',
        	licenseId:'${toolForm.licenseId}',
        	institutionId:'${toolForm.institutionId}',
        	nextPage:currPage,
        	isCopy:true,
        	readCount:'${toolForm.readCount}',
			r_ : new Date().getTime()
        },
        success : function(data) {  
          var s = eval(data);
          config.config.SwfFile='${ctx}/pages/view/form/page?id='+s[0].currPageId;
          config.config.SelectVisible=s[0].isCopy;
          if(s[0].isCopy=='true'){
          	$("#bbbbb").css("display","inline-block");
          	$("#dir").val(s[0].swf);
          }
          $("#documentViewer").FlexPaperViewer(config);
       	  $("#copyCount").html("<ingenta-tag:LanguageTag sessionKey="lang" key='Content.View.now.copy.page'/>："+s[0].copyCount);
        },  
        error : function(data) {  
          var s = eval(data);
         alert(s[0].error);
        }  
	});
}
//下载
function downloadaaaaa(){
	var dir = $("#dir").val();
	window.location.href="${ctx}/pages/view/form/download?dir="+dir;
}
//-->
</script>
	<c:if test="${sessionScope.mainUser!=null }">
   	<li><a><span class="alph"><img src="${ctx }/images/ico_01.gif" style="margin-top:0; margin-left:-18px;" /></span>
   	<span class="write"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.Label.Record"/></span></a>
    <ul class="tag">
       <li>
         <div>
         <p class="book_b">
         	<c:if test="${toolForm.record!=null&&toolForm.record.pages.number>0 }">
         	<a id="label1" onclick="getDocViewer().gotoPage('${toolForm.record.pages.number}');" style="margin: 0px;padding: 0px;border-bottom: none;background: none;" >
				<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.lable.lable1"/>：${toolForm.record.pages.number }
			</a>
			</c:if>
            <c:if test="${toolForm.record==null||toolForm.record.pages.number<=0 }">
            <a id="label1" style="margin: 0px;padding: 0px;border-bottom: none;background: none;" >
            	<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.lable.nolable"/>
			</a>
			</c:if>
		</p>
         <p class="book_a">
			<a onClick="addLabels();" class="a_gret"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.lable.button"/></a>
		</p>
         </div>
       </li>
    </ul>
    </li>
    <li><a href="javascript:void(0)"><span class="alph"><img src="${ctx }/images/ico_02.gif" style="margin-top:0; margin-left:-18px;"/></span>
    <span class="write"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.Label.Notes"/></span></a>
    <input type="hidden" name="isNote" id="isNote" value="${toolForm.notes!=null?toolForm.notes.id:'' }"/>
    <ul class="tag">
       <li>
         <div>
         	<div id="noteList">
				<c:forEach items="${toolForm.noteList }" var="nn">
				<p>
				<span class="book_l" id="nodes_${nn.id }">
				<a onClick="getDocViewer().gotoPage('${nn.pages.number}')">${nn.pages.number}_${nn.noteContent}</a>
				</span>
				<span class="book_r">
				<a><img src="${ctx }/images/cao.gif" title="<ingenta-tag:LanguageTag sessionKey='lang' key='Global.Button.Delete'/>" onClick="deleteNote('${nn.id}')"/></a></span>
				</p>
				</c:forEach>
         	</div>
	         <p class="book_cont">
				<c:if test="${toolForm.notes!=null&&fn:length(toolForm.notes.noteContent)>1000 }">
					<span id="checks" style="color:#FF0000;">
					<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteLength.no'/><strong>${fn:length(toolForm.notes.noteContent)-1000 }</strong><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteLength.number'/>
					</span>
				</c:if>
				<c:if test="${toolForm.notes==null||fn:length(toolForm.notes.noteContent)<=1000 }">
					<span id="checks">
						<c:if test="${toolForm.notes!=null&&fn:length(toolForm.notes.noteContent)<=1000 }"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteLength.yes'/> <strong>${1000-fn:length(toolForm.notes.noteContent) }</strong> <ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteLength.number'/></c:if>
						<c:if test="${toolForm.notes==null }"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteLength.yes'/> <strong>1000</strong> <ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteLength.number'/></c:if>
					</span>
				</c:if>
			</p>
	        <p>
				<textarea name="content" id="content"><c:if test="${toolForm.notes!=null }">${toolForm.notes.noteContent }</c:if></textarea>
			</p>
	        <p class="book_a">
	        	<a name="save" class="a_gret" onClick="addNotes();"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.note.button"/></a>
			</p>
         </div>
       </li>
    </ul>
    </li>
    </c:if>
    <li><a href="javascript:void(0)"><span class="alph"><img src="${ctx }/images/ico_03.gif" style="margin-top:0; margin-left:-18px;"/></span>
    <span class="write"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.Label.Copy"/></span></a>
    <ul class="tag">
       <li>
         <div>
         <p class="book_b"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.copy.Prompt"/>${toolForm.readCount }</a></p>
         <p class="book_b" id="copyCount"><ingenta-tag:LanguageTag sessionKey="lang" key="Content.View.now.copy.page"/>：<c:if test="${sessionScope.copyCount==null }">0</c:if><c:if test="${sessionScope.copyCount!=null }">${sessionScope.copyCount }</c:if></a></p>
         <p class="book_a">
         <a class="a_gret" onClick="copy()"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.copy.Button"/></a>
         &nbsp;&nbsp;<a href="javascript:void(0)" class="reading_online_btn" onClick="downloadaaaaa()" id="bbbbb" style="display: none;"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.DownLoad"/></a>
							<input type="hidden" id="dir"/>
         </p>
         </div>
       </li>
    </ul>
    </li>
    <li><a href="javascript:void(0)"><span class="alph"><img src="${ctx }/images/ico_04.gif" style="margin-top:0; margin-left:-18px;"/></span>
    <span class="write"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.view.Label.Search"/></span></a>
    <ul class="tag">
       <li>
         <div>
         <p>
         <input class="a_input" type="text" id="searchView"/>
         <a href="javascript:void(0)" class="a_cxu" onClick="aaaa();">
         <ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.Search"/></a></p>
         <div id="search_list"></div>
         <p class="book_b">搜索结果：252</p>
         <p class="book_a"><a href="javascript:void(0)" class="a_gret">添加书签</a></p>
         <p>
         <a href="javascript:void(0)" class="a_pages">第1页</a> <a href="javascript:void(0)" class="a_pages">第2页</a> <a href="javascript:void(0)" class="a_pages">第3页</a>
         <a href="javascript:void(0)" class="a_pages">第1页</a> <a href="javascript:void(0)" class="a_pages">第2页</a> <a href="javascript:void(0)" class="a_pages">第3页</a>
         <a href="javascript:void(0)" class="a_pages">第1页</a> <a href="javascript:void(0)" class="a_pages">第2页</a> <a href="javascript:void(0)" class="a_pages">第3页</a>
         </p>
         </div>
       </li>
    </ul>
    </li>
