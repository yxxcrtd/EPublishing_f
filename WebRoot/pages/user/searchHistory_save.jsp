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
			var dm=false;
			$("#tab3").click(function(){	
				if(!dm){
					document.formList.action="${ctx}/pages/user/form/dirManager";
					document.formList.submit();
				}
			});
			
			var sh2=false;
			$("#tab2").click(function(){
				if(!sh2){
					document.formList.action="${ctx}/pages/user/form/searchHistory?type=2";
					document.formList.submit();
				}
			});
			
			var sh1=false;
			$("#tab1").click(function(){
				if(!sh1){
					document.formList.action="${ctx}/pages/user/form/searchHistory?type=1";
					document.formList.submit();
				}
			});
			
		});
		
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
							window.location.href="${ctx}/pages/user/form/searchHistory?type=2";
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
			 			$("input[name='searchValue']").val(value);
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
                <li id="tab1" class="Stab<c:if test="${form.type==1 }"> StabSeleted</c:if>"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SearchHistory.tag1"/></li>
                <li id="tab2" class="Stab<c:if test="${form.type==2 }"> StabSeleted</c:if>"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SearchHistory.tag2"/></li>
                <li id="tab3" class="Stab lastLi"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SearchDir.title"/></li>
            </ul>
        <div class="StabContent ScontentSelected">    
       	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="curTable">
            		<tr>
                	<td width="190" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SearchHistorySave.Table.Label.keyWord"/></td>
                    <td width="190" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SearchHistorySave.Table.Label.keyType"/></td>
                    <td width="190" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SearchHistorySave.Table.Label.time"/></td>
                    <td align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SearchHistorySave.Table.Label.dir"/></td>
                </tr>
               <c:forEach items="${list }" var="s" varStatus="index">
		             	<c:set var="cssWord" value="${index.index%2==0?'a':'b' }"/>
				        <tr>
					        <td class="${cssWord}bodytd  tdname" align="center">
					        	<span style="width:320px;">
					        		<a href="javascript:searchByCondition('searchValue','${s.keyword }');">${s.keyword }</a>
					        	</span>
					        </td>
							<td class="${cssWord}bodytd" align="center">
							<c:if test="${s.keyType==null||s.keyType==0 }"><ingenta-tag:LanguageTag key="Page.Frame.Header.Lable.FullText" sessionKey="lang" /></c:if>
						    <c:if test="${s.keyType!=null&&s.keyType==1 }"><ingenta-tag:LanguageTag key="Page.Frame.Header.Lable.Title" sessionKey="lang" /></c:if>
						    <c:if test="${s.keyType!=null&&s.keyType==2 }"><ingenta-tag:LanguageTag key="Page.Frame.Header.Lable.Author" sessionKey="lang" /></c:if>
						    <c:if test="${s.keyType!=null&&s.keyType==3 }"><ingenta-tag:LanguageTag key="Page.Frame.Header.Lable.ISBN" sessionKey="lang" /></c:if>
						    <c:if test="${s.keyType!=null&&s.keyType==4 }"><ingenta-tag:LanguageTag key="Page.Frame.Header.Lable.Publisher" sessionKey="lang" /></c:if>
							</td>
							<td class="${cssWord}bodytd" align="center">
								<fmt:formatDate value="${s.createOn }" pattern="yyyy-MM-dd HH:mm"/>
							</td>
							<td class="${cssWord}bodytd" align="center">
								<select id="change_${form.type }_${s.id }" style="width: 100px;vertical-align: middle; *margin-top: 5px;" name="change" onchange="save('${s.id }',this.value,2)">
									<option value="">-------</option>
									<option value="0" <c:if test="${form.type==2&&(s.directory.id==''|| s.directory.id==null)}"> selected="selected"</c:if>><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SearchHistorySave.Table.Select.label"/></option>
									<c:forEach items="${dirList }" var="d" varStatus="vindex">
										<option value="${d.id }" <c:if test="${s.directory.id==d.id}"> selected="selected"</c:if>>${d.name }</option>
									</c:forEach>
								</select>
								<c:if test="${form.type==2 }"><a href="javascript:void(0)" onclick="save('${s.id }',this.value,3)" class="grayA"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.Delete"/></a></c:if>
							</td>
				        </tr>
				</c:forEach>
				
        </table>
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
</div>
</div>
    <input type="hidden" id="dirName"/>
	<input type="hidden" id="id"/>
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>