<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
<%@ include file="/common/tools.jsp"%>
<%@ include file="/common/ico.jsp"%>
<script type="text/javascript">
 $(function() { 
		$("#textarea").bind("input propertychange", function() { 
			strLenCalc($(this), 'checklen', 400);
			}); 
		}); 
		function strLenCalc(obj, checklen, maxlen) { 
				var v = obj.val(), charlen = 0, maxlen = !maxlen ? 400 : maxlen, curlen = maxlen, len = v.length;
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
function recommendSubmit() {
		if($("#checks").css("color")=='rgb(255, 0, 0)'){
					alert("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.RecommendLength'/>");
		}else{
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
					art.dialog({icon:'succeed',title:'<ingenta-tag:LanguageTag sessionKey='lang' key='Global.Lable.Prompt'/>',
								cancel: false,
								fixed: true,
								lock: true,
								})
					.content(s[1])
					.time(1.5);
					setTimeout("confirmTerm();",1.5*1000);
				}else{
					art.dialog({icon:'warning',title:'<ingenta-tag:LanguageTag sessionKey='lang' key='Global.Lable.Prompt'/>',
								cancel: false,
								fixed: true,
								lock: true})
					.content(s[1])
					.time(2);
				}
			},
			error : function(data) {
				art.dialog({icon:'warning',title:'<ingenta-tag:LanguageTag sessionKey='lang' key='Global.Lable.Prompt'/>',
								cancel: false,
								fixed: true,
								lock: true})
					.content(data)
					.time(1.5);
			}
		}); 
		}
		//parent.art.dialog.list['getResourceId'].close();
	}

function confirmTerm() {
	if(parent.art.dialog.list['getResourceId']===undefined){
		art.dialog.close();
	}else{
		parent.art.dialog.list['getResourceId'].close();
	}
}
</script>
<style type="text/css">
/* table th{text-align:right;vertical-align：top;}
table td{padding-left:10px;vertical-align：top;text-align:left;} */

*{
	margin:0;
	padding:0;
}
html body {
	font-family: Microsoft YaHei, Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 22px;
	color: #636363;
}
a{
	text-decoration:none;
	color:#0081cb;
}
.pop{
	width:340px;
	border:1px solid #ddd;
	box-shadow:0px 2px 4px #666;
	text-align:center;
	margin:20px auto;
}
.addContDiv{
	padding:0 50px 40px 50px;
	width:240px;
}
.ico{
	color:#FFF;
	padding:6px 15px 6px 32px;
	margin-right:5px;
}
.ico_noIco{
	color:#FFF;
	padding:4px 15px;
	margin-right:5px;
	background:#008cd6;
	border:1px solid #008cd6;
}
.ico_noIcoOver{
	color:#666;
	padding:4px 15px;
	margin-right:5px;
	background:#fff;
	border:1px solid #ccc;
}
.ico_cart{
	background:#008cd6 url(images/ico/ico14.png) no-repeat 10px center;
}
.ico_reading{
	background:#008cd6 url(images/ico/ico-reading.png) no-repeat 10px center;
}
.ico_download{
	background:#008cd6 url(images/ico/ico-download.png) no-repeat 10px center;
}
.ico_recommed{
	background:#008cd6 url(images/ico/ico16.png) no-repeat 10px center;
}
.blueA,.blueB,.blueC{
	padding:1px 15px;
	color:#fff;
	background:#008cd6;
}
.blueB{
	padding:6px 15px;
}
.mt30{
	margin-top:30px;
}
.mr5{
	margin-right:5px;
}
.ml30{
	margin-left:30px;
}
.ml10{
	margin-left:10px;
}
.mr30{
	margin-right:30px;
}
.pb50{
	padding-bottom:50px;
}
.a_det{
	height:40px;
	text-align:right;
	padding:5px 8px 0 0;
}
.recPop{
	width:500px;
}
.fl{
	float:left;
}
.fb{
	font-weight:bold;
}
.f14{
	font-size:14px;
}
.oh{
	overflow:hidden;
}
.aui_title{
	background-color: white;
}

</style>
</head>

	<body>
		<textarea id="rnote" style="display:none"></textarea>
		<input type="hidden" id="pubid" value="${form.pubid }"/>
		
<div class="oh">
<table width="99%" height="100%" border="0" cellspacing="0" cellpadding="0" style="text-align:left; line-height:30px;">
	<tr>
		<td colspan="2">
			<span class="fl fb ml10 f14" style="height: 40px;">
				<ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang" />
			</span>
		</td>
	</tr>
	
  <tr>
    <td width="30%" align="right" class="fb" ><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.OrgName" sessionKey="lang" /> :&nbsp;</td>
    <c:if test="${sessionScope.institution==null && sessionScope.mainUser.institution!=null}">
    <td>${sessionScope.mainUser.institution.name}</td>
    </c:if>
    <c:if test="${sessionScope.institution!=null}">
    <td>${sessionScope.institution.name}</td>
    </c:if>
  </tr>
  <tr>
    <td align="right" valign="top" class="fb"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Title" sessionKey="lang" /> :&nbsp;</td>
    <td><span style="line-height:22px;">${form.obj.publications.title }</span></td>
  </tr>
  <tr>
    <td align="right" class="fb">ISBN/ISSN :&nbsp;</td>
    <td>${form.obj.publications.code }</td>
  </tr>
  <tr>
    <td align="right" class="fb"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Type" sessionKey="lang" /> :&nbsp;</td>
    <td>
		<c:if test="${form.obj.publications.type==1}"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" /></c:if>
		<c:if test="${form.obj.publications.type==2}"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option2" sessionKey="lang" /></c:if>
		<c:if test="${form.obj.publications.type==3}"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option3" sessionKey="lang" /></c:if>
		<c:if test="${form.obj.publications.type==4}"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option4" sessionKey="lang" /></c:if>
		<c:if test="${form.obj.publications.type==5}"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option5" sessionKey="lang" /></c:if>
		<c:if test="${form.obj.publications.type==6}"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option6" sessionKey="lang" /></c:if>
		<c:if test="${form.obj.publications.type==7}"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option7" sessionKey="lang" /></c:if>
	</td>
  </tr>
  <tr>
    <td align="right" class="fb"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.COC" sessionKey="lang" /> :&nbsp;</td>
    <td>
    <c:if test="${sessionScope.lang=='en_US'&&form.obj.publications.pubSubjectEn!=null }">${form.obj.publications.pubSubjectEn }</c:if>
    <c:if test="${sessionScope.lang!='en_US'||form.obj.publications.pubSubjectEn==null }">${form.obj.publications.pubSubject }</c:if>
    </td>
  </tr>
  <tr>
    <td align="right" class="fb"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Person" sessionKey="lang" /> :&nbsp;</td>
    <td>${form.recommendUser.name}</td>
  </tr>
  <tr>
    <td align="right" class="fb"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Identity" sessionKey="lang" /> :&nbsp;</td>
    <td>
	<c:if test="${form.recommendUser.level==5}"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Identity.Expert" sessionKey="lang" /></c:if>
	<c:if test="${form.recommendUser.level!=5}"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Identity.NotExpert" sessionKey="lang" /></c:if>
	</td>
  </tr>
  <tr>
    <td align="right" valign="top" class="fb"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Index.Search.Lable.Note"/> :&nbsp;</td>
    <td>
	    <textarea name="textarea" id="textarea" cols="45" rows="4" class="favorite_input" style="width:300px; height:70px;" onkeyup="$('#rnote').val(this.value)"></textarea>
    </td>
  </tr>
  <tr>
  	<td>&nbsp;</td>
  	<td><span id="checks">
    		<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteLength.yes'/> <strong>200</strong> <ingenta-tag:LanguageTag sessionKey='lang' key='Pages.view.Prompt.NoteLength.number'/>
		</span>
	</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>
        <span class="mr5"><a style="margin-right:10px;" href="javascript:void(0)" onclick="recommendSubmit()" class="ico_noIco"><ingenta-tag:LanguageTag key="Page.Index.Search.Button.Recommed.Submit" sessionKey="lang" /></a></span>
		<span class="mr5"><a href="javascript:void(0)" onclick="confirmTerm()" class="ico_noIcoOver"><ingenta-tag:LanguageTag key="Global.Button.Cancel" sessionKey="lang" /></a></span>
    </td>
  </tr>
</table>
		</div>
		
	</body>
</html>
