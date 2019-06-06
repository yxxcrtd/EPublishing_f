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
		<script type="text/javascript">
		
		$(function(){
			loadFunction();
			var dm=false;
			$("#tab3").click(function(){	
				if(!dm){
					$.ajax({
						  type:"POST",
						  url: "${ctx}/pages/user/form/dirManager?type=3",
						  
						  success:  function (response, stutas, xhr) {
							  dm=true;
							  $("#mb15").append(response);
						  }
					});
				}
			});
			
			var sh2=false;
			$("#tab2").click(function(){
				if(!sh2){
					$.ajax({
						  type:"POST",
						  url: "${ctx}/pages/user/form/searchHistory",
						  data:{
							  type:2
						  },
						  success:  function (response, stutas, xhr) {
							  sh2=true;
							  $("#StabContent02").html(response);
						  }
					});
				}
			});
			
			var sh1=false;
			$("#tab1").click(function(){
				
				if(!sh1){
					
					$.ajax({
						  type:"POST",
						  url: "${ctx}/pages/user/form/searchHistory",
						  data:{
							  type:1
						  },
						  success:  function (response, stutas, xhr) {
							  sh1=true;
							  $("#StabContent01").html(response);
						  }
					});
					
				}
				
			});
			
		});
		
		//下拉弹出文件夹中信息
		function senfe(e,name,id,cssType) {
			var a_html = $(e).html();
			var s = 1.2;
			var s2 = 8;
			var obj = e.parentNode;
			var oh = parseInt(obj.offsetHeight);
			var h = parseInt(obj.scrollHeight);
			var nh = oh;
	
			var status = $("#ss_"+id).val();
	
			if (obj.getAttribute("oldHeight") == null) {
				obj.setAttribute("oldHeight", oh);
			} else {
				var oldh = Math.ceil(obj.getAttribute("oldHeight"));
			}
			if (a_html.indexOf("+")>=0) {
				if(status<=0){
				$("#scList_"+id).css("display","block");
				//利用Ajax查询
				$.ajax({
						type : "POST",
						async : false,
						url : "${ctx}/pages/user/form/searchHistoryAjax",
						data : {
							dirId : id,
							name : name,
							type : 2,
							cssType : cssType,
							r_ : new Date().getTime()
						},
						success : function(data) {
							var s = data.split("^");
							if(s[0]=="success"){
								$("#scList_"+id).html(s[1]);
								$("#scList_"+id).css("text-align","left");
								oh = parseInt(obj.offsetHeight);
								h = parseInt(obj.scrollHeight);
								nh = oh;
							}else{
								alert(s[1]);
							}
						},
						error : function(data) {
							alert(data);
						}
					}); 
				}
			}else{
				$("#scList_"+id).css("display","none");
			}
	
			var reSet = function() {
				if (a_html.indexOf("+")>=0) {
					e.innerHTML = "- "+name;
					if (nh < h) {
						nh = Math.ceil(h - (h - nh) / s);
						obj.style.height = nh + "px";
					} else {
						window.clearInterval(IntervalId);
					}
				} else {
					e.innerHTML = "+ "+name;
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
		//保存
		function save(id,dirId,operType){
			var a = true;
			if(operType==3){
				//删除 有提示
				if(window.confirm("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.SearchHistorySave.Prompt.delete'/>")){
					
				}else{
				a = false;
				}
			}
			if(a){
				$.ajax({
					type : "POST",
					url : "${ctx}/pages/user/form/saveSearch",
					data : {
						id : id,
						dirId : dirId,
						operType : operType,
						r_ : new Date().getTime()
					},
					success : function(data) {
						var s = data.split(":");
						if(s[0]=="success"){
							art.dialog.tips(s[1],2);
						}else{
							art.dialog.tips(s[1],1,'error');
						}
					},
					error : function(data) {
						art.dialog.tips(data,1,'error');
					}
				});
			}
		}
		
		var aaa ;
		function viewPopTips(id) {
			var name = $("#name_"+id).val();
			$("#tt").attr("value",name);//val(name);
			$("#dirName").val(name);
			$("#id").val(id);
			
			var ht = $("#viewContent").html();
			aaa = art.dialog({title:"<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.SearchDir.Tip.title'/>",content:ht,width: 330, height: 180,lock:true});
		}
		//
		function submit(){
			var id = $("#id").val();
			var name = $("#dirName").val();
			if(name==null||name.replace(/\s+/g,"")==''){
				alert('<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SearchDir.Prompt.dirName.error"/>');
			}else if(name.length>20){
				alert('<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SearchDir.Prompt.dirName.length.error"/>');
			}else{
				$.ajax({
					type : "POST",
					async : false,
					url : "${ctx}/pages/user/form/editDirSubmit",
					data : {
						id : id,
						name : name,
						r_ : new Date().getTime()
					},
					success : function(data) {
						var s = data.split(":");
						if (s[0] == "success") {
							art.dialog.tips(s[1],1);
							aaa.close();
							tab3();
						}else{
							art.dialog.tips(s[1],1,'error');
						}
					},
					error : function(data) {
						art.dialog.tips(data,1,'error');
					}
				}); 
			}
		}
		//删除
		function deleteDir(id){
			if(window.confirm("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.SearchHistorySave.Prompt.delete'/>")){
				$.ajax({
					type : "POST",
					url : "${ctx}/pages/user/form/deleteDir",
					data : {
						id : id,
						r_ : new Date().getTime()
					},
					success : function(data) {
						var s = data.split(":");
						if(s[0]=="success"){
							art.dialog.tips(s[1],2);
						}else{
							art.dialog.tips(s[1],1,'error');
						}
					},
					error : function(data) {
						art.dialog.tips(data,1,'error');
					}
				});
			}
		}
		//左侧条件查询
			function searchByCondition(type,value,a1,a2,a3,a4){
				document.formList.action="${ctx}/pages/publications/form/list";
				if(type=="searchValue"){
					$("#searchValue1").val(value);
					if(${sessionScope.selectType==1}){
			 			$("#lcense1").val("1");
			 			document.formList.action="${ctx}/index/searchLicense";
				 	}else{
				 		document.formList.action="${ctx}/index/search";
				 	}
				}else if(type=="type"){
					$("input[name='pubType']").val(value);
				}else if(type=="publisher"){
					$("input[name='publisher']").val(value); 
					$("input[name='publisherId']").val(a1);
				}else if(type=="pubDate"){
					$("input[name='pubDate']").val(value);
				}else if(type=="taxonomy"){
					$("#taxonomy1").val(value);
					$("#pCode1").val(a1);
					$("#code1").val(a2);
					$("#subParentId1").val(a3);
					$("#parentTaxonomy1").val(a4);
					document.formList.action="${ctx }/pages/subject/form/list";
				}else if(type=="taxonomyEn"){
					$("#taxonomyEn1").val(value);
					$("#pCode1").val(a1);
					$("#code1").val(a2);
					$("#subParentId1").val(a3);
					$("#parentTaxonomyEn1").val(a4);
					document.formList.action="${ctx }/pages/subject/form/list";
				}
				$("#curpage").val(0);
				$("#pageCount").val(10);
				$("#order1").val('');
				$("#lcense1").val('${sessionScope.selectType}');
				document.formList.submit();
			}
		function loadFunction(){
			$.ajax({
				  type:"POST",
				  url: "${ctx}/pages/user/form/searchHistory",
				  data:{
					  type:1
				  },
				  success:  function (response, stutas, xhr) {
					  
					  $("#StabContent01").html(response);
				  }
			});
		}
		
		function tab3(){
			$.ajax({
				  type:"POST",
				  url: "${ctx}/pages/user/form/dirManager?type=3",
				  success:  function (response, stutas, xhr) {
					  
					  $("#mb15").html(response);
				  }
			});
		}
		</script>
	</head>
<body>
<jsp:include page="/pages/header/headerData" flush="true" />
<div class="main personMain">
    <c:if test="${sessionScope.mainUserLevel!=2 }">
    	<jsp:include page="/pages/menu?mid=mage&type=5" flush="true" />
    </c:if>
    <c:if test="${sessionScope.mainUserLevel==2 }">
    	<jsp:include page="/pages/menu?mid=mage&type=1" flush="true" />
    </c:if>
    <div class="perRight">
   	  <div class="StabedPanels" >
            <ul class="oh">
                <li id="tab1" class="Stab StabSeleted" ><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SearchHistory.tag1"/></li>
                <li id="tab2" class="Stab"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SearchHistory.tag2"/></li>
                <li id="tab3" class="Stab lastLi"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SearchDir.title"/></li>
            </ul>
           <div id="StabContent01" class="StabContent ScontentSelected">
           	
           </div>
       <div id="StabContent02" class="StabContent">
       	
       </div>
	  <%--  <form:form action="dirManager" commandName="form" id="form" name="form" method="post"> --%>
       <div class="StabContent">
       <form:form action="dirManager" commandName="form" id="form" name="form" method="post">
       <p class="mb15" id="mb15"><a href="javascript:void(0)" onclick="viewPopTips('')" class="orgingA"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.Add2"/></a></p>
       		
       </form:form>
       </div>
	  
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
		<!--移动窗口开始-->
		<div style="display:none;">
			<div id="viewContent">
					<ul>
					<table style="text-align:left;" cellpadding="0"	cellspacing="0" border="0">
				        <tr>
					        <td colspan="2">
					        	<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SearchDir.view.name"/>&nbsp;<input type="text" id="tt" onkeyup="$('#dirName').val(this.value)"/>
					        </td>
					      </tr>
					      <tr>
				        	<td colspan="2" style="text-align: center;padding-top: 20px;">
				        		<a href="javascript:void(0)" id="submit" onclick="submit();" class="a_gret"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.Submit"/></a>
				        	</td>
				        </tr>
			        </table>
					</ul>
					<!--内容结束-->
			<!--simTestContent end-->
		</div>
    </div>
</div>
</div>
    <input type="hidden" id="dirName"/>
	<input type="hidden" id="id"/>
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>
