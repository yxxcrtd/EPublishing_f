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
<script type="text/javascript" src="${ctx }/js/jquery.highlight.js"></script>
<script type="text/javascript">

function advancedSearch(){
	$("#type1").val('');
 	$("#pubDate1").val(''); 	
 	$("#pCode1").val('');
 	$("#publisherId1").val('');
 	$("#subParentId1").val('');
 	$("#parentTaxonomy1").val('');
 	$("#parentTaxonomyEn1").val('');
	 <%-- 加入机构判断 -- hengrun.yuan --%>
/*  	var flag = "${sessionScope.specialInstitutionFlag}" ; 
 */ 	<%-- 判断机构是否为特殊机构 --%>
/* 	var status = (flag!=""&&flag.length>0)?true:false ; 
 	if(status){
 		$('#language2').val(flag);
 		$('#pubType2').val("1");
 		//$("#fullText2").val(" [0 TO *]");
 	}
 	
 */ 
 if($("publisher1").val()==''&&$("#searchValue").val()==''&&$("#title1").val()==''&&$("#author1").val()==''&&$("#code1").val()==''&&$("#taxonomy1").val()==''&&$("#pubType1").val()==''&&($("#pubDateStart1").val()==''||$("#pubDateEnd1").val()=='')){

		art.dialog.tips("<ingenta-tag:LanguageTag key='Page.Frame.Header.Pormpt.KeyWord' sessionKey='lang' />",1,'error');
	}else if($("#pubDateStart1").val()!==''&& $("#pubDateEnd1").val()!=''&& $("#pubDateEnd1").val()< $("#pubDateStart1").val()){//当出版年份结束时间小于开始时间，给予提示
		art.dialog.tips("<ingenta-tag:LanguageTag key='Page.AdvanceSerach.Alert' sessionKey='lang' />",1,'error');
	}else{
		document.getElementById("formList").submit();
	}
}

</script>
</head>

<body>
<jsp:include page="/pages/header/headerData" flush="true" />
<div class="main personMain">

<form:form action="advancedSearchSubmit" method="post" modelAttribute="form" commandName="form" name="formList" id="formList">
			<form:hidden path="searchsType" id="type1" />
			<form:hidden path="pubDate" id="pubDate1" />
			<form:hidden path="pCode" id="pCode1" />
			<form:hidden path="publisherId" id="publisherId1" />
			<form:hidden path="subParentId" id="subParentId1" />
			<form:hidden path="parentTaxonomy" id="parentTaxonomy1" />
			<form:hidden path="parentTaxonomyEn" id="parentTaxonomyEn1" />
			<form:hidden path="language" id="language2" />
			<form:hidden path="pubType" id="pubType2" />
			<form:hidden path="fullText" id="fullText2" />
			<form:hidden path="local" id="local2" />
			<form:hidden path="notLanguage" id="notLanguage2" />
	 <c:if test="${sessionScope.mainUser!=null }">		
	<div class="personLeft">
    	<!-- <h1 class="mt20 f14 fb ml10">用户登录</h1>
    	<p class="blockP mt10">
            <span class="w60 tr">用户名：</span>
            <span class="w110"><input type="text" style="width:110px;"></span>     
        </p>
        <p class="blockP">
            <span class="w60 tr">密码：</span>
            <span class="w110"><input type="password" style="width:110px;"></span>     
        </p>
        <p class="blockP">
            <span class="w60 tr">&nbsp;</span>
            <span class="w110"><a href="javascript:void(0)" class="blueB">登录</a></span>     
        </p> -->
       
		    <jsp:include page="/pages/menu?type=1" flush="true"/>
		    
<%-- 		    <c:if test="${sessionScope.mainUser==null }"> --%>
<%-- 		    <%@ include file="/pages/frame/login.jsp"%> --%> 
<%-- 		    </c:if> --%>
    </div>
    </c:if>
    <c:if test="${sessionScope.mainUser==null }">
    <div class="personLeft1">
    </div>
    </c:if>
    <div class="perRight">
    	<h1><ingenta-tag:LanguageTag key="Page.Frame.Header.Lable.AdvSearch" sessionKey="lang" /></h1>
        <p><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Lable.Tips1" sessionKey="lang" /></p>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="regTable mt10">
          <tr>
            <td width="120" align="right"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Lable.Keywords" sessionKey="lang" />：</td>
            <td colspan="3"><form:input type="text" path="searchValue" id="searchValue1"/></td>
          </tr>
          <tr>
            <td align="right">&nbsp;</td>
            <td colspan="3">
            	
                    <form:select path="keywordCondition">
								<form:option value="2"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Lable.Keywords.Option2" sessionKey="lang" /></form:option>
								<form:option value="1"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Lable.Keywords.Option1" sessionKey="lang" /></form:option>
					</form:select>
                </br>
                <form:checkbox type="checkbox" path="lcense" id="lcense1" value="1"  /> <ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Lable.IsFullText" sessionKey="lang" />
            </td>
          </tr>
          <tr>
            <td align="right"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Lable.InTitle" sessionKey="lang" />：</td>
            <td colspan="3"><form:input type="text" path="title" id="title1"/></td>
          </tr>
          <tr>
            <td width="120" align="right"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Lable.Filter.Author" sessionKey="lang" />：</td>
            <td colspan="3"><form:input type="text" path="author" id="author1"/></td>
          </tr>
           <tr>
            <td width="120" align="right"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Lable.Filter.Publisher" sessionKey="lang"/>：</td>
            <td colspan="3"><form:input type="text" path="publisher" id="publisher1"/></td>
          </tr>
          <tr>
            <td align="right"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Lable.Filter.Code" sessionKey="lang" />：</td>
            <td colspan="3"><form:input type="text" path="code" id="code1"/></td>
          </tr>
          <tr>
            <td align="right"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Lable.ThemeRange" sessionKey="lang" />：</td>
            <td colspan="3">
         
                    
                    <c:if test="${sessionScope.lang=='en_US' }">
						<form:select path="taxonomyEn" id="taxonomyEn1">
							<form:option value=""><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Prompt.Select"/></form:option>
							<c:forEach items="${form.subList}" var="c">
								<c:if test="${fn:length(c.treeCode)==6 }"><form:option value="${c.code} ${c.nameEn}">${c.code}&nbsp;&nbsp;${c.nameEn}</form:option></c:if>
								<c:if test="${fn:length(c.treeCode)!=6 }"><form:option value="${c.code} ${c.nameEn}">&nbsp;&nbsp;${c.code}&nbsp;&nbsp;${c.nameEn}</form:option></c:if>
							</c:forEach>
						</form:select>
						</c:if>
						<c:if test="${sessionScope.lang!='en_US' }">
							<form:select path="taxonomy" id="taxonomy1">
								<form:option value=""><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Prompt.Select"/></form:option>
								<c:forEach items="${form.subList}" var="c">
									<c:if test="${fn:length(c.treeCode)==6 }"><form:option value="${c.code} ${c.name}">${c.code}&nbsp;&nbsp;${c.name}</form:option></c:if>
									<c:if test="${fn:length(c.treeCode)!=6 }"><form:option value="${c.code} ${c.name}">&nbsp;&nbsp;${c.code}&nbsp;&nbsp;${c.name}</form:option></c:if>
								</c:forEach>
							</form:select>
						</c:if>
				
            </td>
          </tr>
  
          <tr>
						<td align="right"><ingenta-tag:LanguageTag key="Page.Collection.Lable.PubDate" sessionKey="lang"/>：</td>
						<td colspan="3">
						<ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Lable.From" sessionKey="lang" /> 
						<form:select path="pubDateStart" style="width:143px;vertical-align:middle;" id="pubDateStart1">
											<form:option value=""><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.DateRange.Option1" sessionKey="lang" /></form:option>
											<c:forEach var="years" begin="1950" end="2020" step="1">
												<form:option value="${years }">${years}</form:option>
											</c:forEach>
										</form:select> 
										<ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Lable.Arrive" sessionKey="lang" /> 
										 <form:select path="pubDateEnd" style="width:143px;vertical-align:middle;" id="pubDateEnd1">
											<form:option value=""><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.DateRange.Option1" sessionKey="lang" /></form:option>
											<c:forEach var="years" begin="1950" end="2020" step="1">
												<form:option value="${years }">${years}</form:option>
											</c:forEach>
										</form:select>
						</td>
			</tr>
          
          <tr>
            <td align="right"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Select.Sort" sessionKey="lang" />：</td>
            <td colspan="3">
                
                <form:select path="searchOrder" id="order1" >
								<form:option value=""><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Order.Option1" sessionKey="lang" /></form:option>
								<form:option value="desc"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Order.Option2" sessionKey="lang" /></form:option>
								<form:option value="asc"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Order.Option3" sessionKey="lang" /></form:option>
				</form:select>
                
            </td>
          </tr>
          <tr>
            <td align="right"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Select.Display" sessionKey="lang" />：</td>
            <td colspan="3">
            	
                <form:select path="pageCount" >
							<form:option value="10">10</form:option>
							<form:option value="20">20</form:option>
							<form:option value="50">50</form:option>
			   </form:select>
            </td>
          </tr>
          <tr>
            <td align="right">&nbsp;</td>
            <td colspan="3"><p class="mt10"><a href="javascript:void(0)" class="orgingA" onclick="advancedSearch();"><ingenta-tag:LanguageTag key="Page.Index.AdvSearch.Button.Search" sessionKey="lang" /></a></p></td>
          </tr>
      </table>
      
    </div>	
    </form:form>
</div>
		
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>
