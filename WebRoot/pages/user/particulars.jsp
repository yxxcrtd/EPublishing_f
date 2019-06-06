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
		var rid=$("#rid").val();
		var particulars=$("#particulars").val();
		if(document.getElementById("particulars").value==0  && document.getElementById("particulars").value==""){
		alert("<ingenta-tag:LanguageTag sessionKey='lang' key='Order.Button.Suspend.particulars.null'/>");
		return false;
	} 
		if(particulars!=null){
		$.ajax({
			type : "POST",
			url : "${ctx}/pages/recommend/form/suspend",
			data : {				
				id : rid,
		particulars:particulars,
				r_ : new Date().getTime()
			},
			success : function(data) {
				var s = data.split(":");
				if(s[0]=="success"){					
						art.dialog({icon:'succeed',title:'<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.MyRecommend.Label.isOrder4'/>',
								cancel: false,
								fixed: true,
								lock: true})
					.content(s[1])
					.time(1.5);
					setTimeout("art.dialog.close();",1.5*1000);
					$("#page", window.parent.document).val($("#txtPage", window.parent.document).val()-1);
					$("#form", window.parent.document).submit();
				}else{
					art.dialog({icon:'warning',title:'<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.MyRecommend.Label.isOrder4'/>',
								cancel: false,
								fixed: true,
								lock: true})
					.content(s[1])
					.time(2);
				}
				
			},
			error : function(data) {
				art.dialog({icon:'warning',title:'error<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.MyRecommend.Label.isOrder4'/>',
								cancel: false,
								fixed: true,
								lock: true})
					.content(data)
					.time(1.5);
			}
		});
		}
		
	}

function confirmTerm() {
	art.dialog.close();
}
</script>
<style type="text/css">
table th{text-align:right;vertical-align：top;}
table td{padding-left:10px;vertical-align：top;text-align:left;}

</style>
</head>
<body>
<table width="100%" border="0"  cellspacing="1" cellpadding="0" class="table_line01" style="line-height:24px;margin-top:15px;" >
  

  <tr>
    <th style="vertical-align:top"><ingenta-tag:LanguageTag sessionKey="lang" key="Order.Button.Suspend.particulars"/>&nbsp;：&nbsp;</th>
    <td>
	    <textarea name="particulars" id="particulars" cols="45" rows="4" class="favorite_input" style="width:460px" onkeyup="$('#rnote').val(this.value)"></textarea>
    </td>
  </tr>
  
  <tr>
    <th style="padding-top:20px; "></th>
    <td style="padding-top:20px; padding-left:100px;">
        <a style="margin-right:10px;" href="javascript:void(0)" onclick="recommendSubmit()" class="a_gret"><ingenta-tag:LanguageTag key="Page.Index.Search.Button.Recommed.Submit.particulars" sessionKey="lang" /></a>
		<a href="javascript:void(0)" onclick="confirmTerm()" class="ico_noIcoOver"><ingenta-tag:LanguageTag key="Global.Button.Cancel" sessionKey="lang" /></a>
    </td>
  </tr>
</table>
<textarea id="rnote" style="display:none"></textarea>
<input type="hidden" name="rid" id="rid" value="${param.rid }"/>

</body>
</html>
