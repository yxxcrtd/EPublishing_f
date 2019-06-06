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
		//左侧条件查询
		function searchByCondition(type,value,a1,a2,a3,a4){
			document.formList.action="${ctx}/pages/publications/form/list";
			if(type=="searchValue"){
				$("#searchValue1").val(value);
				if(${sessionScope.selectType==1}){
		 			$("#lcense1").val("1");
		 			document.formList.action="${ctx}/index/searchLicense";
		 			//document.formList.action="${ctx}/index/advancedSearchSubmit?publisher=value";
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
	<div class="big">
		<jsp:include page="/pages/header/headerData" flush="true" />
		<div class="main">
		    <div class="tablepage" style="width:98%">
		    <h1><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Filter.Publisher" sessionKey="lang" /></h1>
		    	<div class="t_list">
		       	  <p>
		          <span>
			          	<ingenta-tag:LanguageTag key="Page.Publisher.List.Tips" sessionKey="lang" />：
			      </span>      
		          </p>
		          <p>
		          <span>
		          	1 - ${form.pageCount} of ${form.count} <ingenta-tag:LanguageTag key="Page.Publications.DetailList.Lable.Results" sessionKey="lang" />
		          </span>
		          </p>
		          <p>
		          	<span style="font-size: 24px;">
		          		<a name="sort" title="" href="${ctx}/pages/publishers/form/list">#</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/a">A</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/b">B</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/c">C</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/d">D</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/e">E</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/f">F</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/g">G</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/h">H</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/i">I</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/j">J</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/k">K</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/l">L</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/m">M</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/n">N</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/o">O</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/p">P</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/q">Q</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/r">R</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/s">S</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/t">T</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/u">U</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/v">V</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/w">W</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/x">X</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/y">Y</a>
		                <a name="sort" title="" href="${ctx}/pages/publishers/form/list/z">Z</a>
		          	</span>
		          </p>
		        </div>
		        
		        <div class="page_table">
		        	<table cellspacing="0" cellpadding="0" style="width:100%;">		           
		             <tbody>		             
				        <c:forEach items="${list}" var="p" varStatus="index">	
				        <c:set var="tdCss"><c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2!=0 }">bbodytd</c:if></c:set>             
				             <tr class="<c:if test="${index.index%2 == 1}">sublications</c:if><c:if test="${index.index%2 == 0}">sublications gray</c:if>">
				              	<td class="${tdCss }" style="text-align:left;padding-left:15px">
				               		<a title="" href="${ctx}/index/advancedSearchSubmit?encode(publisher=${p.name})" <%-- onclick="searchByCondition('publisher','${p.name}','${p.id }');" --%>>${p.name}</a>
				             	</td>
				             </tr>
			             </c:forEach> 
					 </tbody>
		             <tfoot>
		              <tr>
		                <td colspan="7" class="f_tda">
		                <ingenta-tag-v3:SplitTag first_ico="${ctx }/images/ico_left1.gif"
		                	last_ico="${ctx }/images/ico_right1.gif" 
		                	prev_ico="${ctx }/images/ico_left.gif" 
		                	next_ico="${ctx }/images/ico_right.gif" 
		                	method="get"
		                	pageCount="${form.pageCount}" 
		                	count="${form.count}" 
		                	page="${form.curpage}" 
		                	url="${form.url}" 
		                	i18n="${sessionScope.lang}"/>
		                </td>
		               </tr>
		             </tfoot>
				</table>
		        </div>	
			</div>
	    <!--左侧内容结束-->	  
	    </div>		
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
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
		</div>
	</body>
</html>
