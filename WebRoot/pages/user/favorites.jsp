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
	<script language="javascript">
	$(document).ready(function(){
	$("#download").click(function(){
	/*  var url = "${ctx}/pages/favourites/form/downloadfavorites";*/
		var type=$("select[name='ftype']").val();
		var url = '${ctx}/pages/favourites/form/downloadfavorites?ftype='+type;
		
	window.location.href=url;
	}),
	
		$("input[name='order']").click(function(){
			var url = '${ctx}/pages/favourites/form/favorites?pageCount=10&order='+$(this).val()+"&r_="+new Date().getTime();
			window.location.href=url;
		});
		$("select[name='ftype']").change(function(){
			var url = '${ctx}/pages/favourites/form/favorites?pageCount=10&ftype='+$(this).val()+"&r_="+new Date().getTime();
			window.location.href=url;
		});
		$("#delete").click(function(){
			
				var dels = $("input[name='del']:checked");
				if(dels.size()>0){
					//获取选中
					if(confirm("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Prompt.delete'/>")){
						var str="";  
						var i = 1;
						dels.each(function(){ 
							if(i==dels.size()){
								str+=$(this).val();
							} else{
								str+=$(this).val()+",";
								i++;
							}
						});  
						$.ajax({
							type : "POST", 
							async : false,   
				           	url: "${ctx}/pages/favourites/form/favorites/delete",
				           	data: {
				           		order:'${form.order}',
				           		dels:str,
								r_ : new Date().getTime()
				           	},
				           	success : function(data) {  
				           		var returnVal = data.split(":");
				           		if(returnVal[0]=="success"){
				            		art.dialog.tips(returnVal[1],2);
			            		}else{
			            			art.dialog.tips(returnVal[1],1,'error');
			            		}
			            	},  
			            	error : function(data) {  
			            	}  
			            });
					}
				}else{
					art.dialog.tips("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Prompt.deleteNull'/>",1,'error');
				}
		});
		$("#selectAll").click(function(){
				if($(this).attr("checked")){
	     			$("input[name='del']").attr("checked",true);
	     		}else{
	     			$("input[name='del']").attr("checked",false);
	     		}
			});
	});
	
	function dividePage(targetPage){
		if(targetPage<0){return;}
		document.getElementById("form").action="${ctx}/pages/favourites/form/favorites?pageCount="+${form.pageCount}+"&curpage="+targetPage;
		document.getElementById("form").submit();
	}
	
	function GO(obj){
		document.getElementById("form").action="${ctx}/pages/favourites/form/favorites?pageCount="+$(obj).val();
		document.getElementById("form").submit();
	}
</script>
	
	</head>
	<body>
	<jsp:include page="/pages/header/headerData" flush="true" />

		
		<!--定义01 mainContainer 内容区开始-->
<div class="main personMain">
	<c:if test="${sessionScope.mainUserLevel!=2 }">
    	<jsp:include page="/pages/menu?mid=favo&type=5" flush="true"/>
    </c:if>
    <c:if test="${sessionScope.mainUserLevel==2 }">
    	<jsp:include page="/pages/menu?mid=favo&type=1" flush="true"/>
    </c:if>
		
			<!--定义 0101 头部边框-->
				<!--定义 0102 左边内容区域 开始-->
				<div class="perRight">
				<form:form action="favorites" commandName="form" id="form" name="form">
					<h1><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.title'/></h1>
					<div class="borderDiv">
			       
			       
			       	  <p class="mb10 bookmarkP">
			       	   
				       <span><input type="radio" class="vm"  name="order" value="titleAsc" <c:if test="${form.order=='titleAsc' }">checked="checked"</c:if>/><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Label.titleAsc'/></span>
				       <span><input type="radio" class="vm"  name="order" value="titleDesc" <c:if test="${form.order=='titleDesc' }">checked="checked"</c:if>/><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Label.titleDesc'/></span>
				       <span><input type="radio" class="vm"  name="order" value="dateDesc" <c:if test="${form.order=='dateDesc' }">checked="checked"</c:if>/><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Label.dateDesc'/></span>
				       <span><input type="radio" class="vm"  name="order" value="dateAsc" <c:if test="${form.order=='dateAsc' }">checked="checked"</c:if>/><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Label.dateAsc'/></span>
				       <span><input type="radio" class="vm"  name="order" value="createAsc" <c:if test="${form.order=='createAsc' }">checked="checked"</c:if>/><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Label.createAsc'/></span>
				       <span><input type="radio" class="vm"  name="order" value="createDesc" <c:if test="${form.order=='createDesc' }">checked="checked"</c:if>/><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Label.createDesc'/></span>
			            <c:if test="${sessionScope.lang == 'en_US'}"><br /></c:if>
			           <span>
			             <ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Table.Label.type'/>：&nbsp;
					<select name="ftype" id="ftype">				
							<option value="0" <c:if test="${form.ftype==0}">selected="selected"</c:if>>--<ingenta-tag:LanguageTag sessionKey="lang" key="Global.Lable.Select.All"/>--</option>
							<c:forEach items="${form.type}" var="c">
	        			     <option value="${c.key}" <c:if test="${form.ftype==c.key}">selected="selected"</c:if>>${c.value}</option>
					        </c:forEach>
					</select> 
					 </span>
					 </p>
					 <p class="mb5">
					 <a href="javascript:void(0)" class="orgingB" id="download"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.DownLoad"/></a>
			          </p>	
			        </div>
					<p class="bookmarkP">
					   <span><input type="checkbox" class="vm" id="selectAll"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Lable.Select.All"/></span>
					   <span><a id="delete" href="javascript:;" class="grayA"><ingenta-tag:LanguageTag key="Global.Button.Delete" sessionKey="lang"/></a></span>
					  </p>
					  
					<!-- ******************标记******************* -->  
					
		        	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="curTable">
			            <thead>
			              <tr>
			                <td width="50" align="center">&nbsp;</td>
			                <td width="100" align="center"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Table.Label.type'/></td>
			                <td class="atdtop" align="center"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Table.Label.title'/></td>
			              </tr>
			             </thead>
			             <tbody>
			             <c:forEach items="${list }" var="f" varStatus="index">	      
			             <c:set var="cssWord" value="${index.index%2==0?'a':'b' }"/>        
			              <tr>
			                <td class="${cssWord}bodytd" align="center"><input type="checkbox" name="del" id="del_${f.publications.id }" value="${f.publications.id }"/></td>
			                <td class="${cssWord}bodytd" align="center">
								<c:if test="${f.publications.type==1}"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Table.Label.type1'/></c:if>
							    <c:if test="${f.publications.type==2}"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Table.Label.type2'/></c:if>
							    <c:if test="${f.publications.type==3}"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Table.Label.type3'/></c:if>
							    <c:if test="${f.publications.type==4}"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Table.Label.type4'/></c:if>
							    <c:if test="${f.publications.type==5}"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Table.Label.type5'/></c:if>
							    <c:if test="${f.publications.type==6}"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Table.Label.type6'/></c:if>
							    <c:if test="${f.publications.type==7}"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Favorites.Table.Label.type7'/></c:if>
							</td>
			                <td class="${cssWord}bodytd tdname">
			                <span class="pl10 omit w580">
			                	<a title="${f.publications.title }" href="${ctx}/pages/publications/form/article/${f.publications.id}" > ${fn:replace(fn:replace(fn:replace(f.publications.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}</a>
			                <%-- 	<a title="${f.publications.title }" href="${ctx}/pages/publications/form/article/${f.publications.id}" >${f.publications.title }</a> --%>
			                </span></td>			                
			              </tr>
			              </c:forEach>
			             </tbody>
			             <tfoot>
		             </tfoot>
					</table>
					</form:form>
                <jsp:include page="../pageTag/pageTag.jsp">
			       <jsp:param value="${form }" name="form"/>
	            </jsp:include>					
				</div>
				<!--定义 0102 左边内容区域 结束-->
				<!--定义 0103 右边内容区域 开始-->
				<%-- <jsp:include page="/pages/menu?mid=fav" flush="true" /> --%>
				<!--定义 0103 右边内容区域 结束-->
		
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
		</div>
		<!--以上 提交查询Form 结束-->
		<!--定义01 mainContainer 内容区结束-->
		
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>
