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
		$(function() {
			var sh1=false;
			$("#tab1").click(function(){
				if(!sh1){
					document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=1";
					document.formList.submit();
				}
			});
		
			var sh2=false;
			$("#tab2").click(function(){
				if(!sh2){
					document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=2";
					document.formList.submit();
				}
			});

			var dm=false;
			$("#tab3").click(function(){
				if(!dm){
					document.formList.action="${ctx}/pages/user/form/mySubscription/public/collection?searchType=99";
					document.formList.submit();
				}
			});
			var qi=false;
			$("#tab4").click(function(){
				if(!qi){
					document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=7";
					document.formList.submit();
				}
			});
			
		});
		function queryList(){
			document.getElementById("form").action="";
			document.getElementById("form").submit();
		}
		function downLog(range,searchType,type,value,value2){
			if(searchType!=99){
				if(type==1){
					window.location.href="${ctx}/pages/user/form/Log?range="+range+"&searchType="+searchType+"&isCn="+value;
				}else if(type==2){
					window.location.href="${ctx}/pages/user/form/Log?range="+range+"&searchType="+searchType+"&pType="+value2+"&isCn="+value;
				}else{
					window.location.href="${ctx}/pages/user/form/Log?range="+range+"&searchType="+searchType;
				}
			}else{
				if(type==1){
					window.location.href="${ctx}/pages/user/form/collection/mySubscriptionLog?range="+range+"&searchType="+searchType+"&isCn="+value;
				}else if(type==2){
					window.location.href="${ctx}/pages/user/form/collection/mySubscriptionLog?range="+range+"&searchType="+searchType+"&pType="+value2;
				}else{
			 		window.location.href="${ctx}/pages/user/form/collection/mySubscriptionLog?range="+range+"&searchType="+searchType;
			 	}
			}
		}
		
        function cnFunc(){
	        if(document.getElementById("cn").checked){
	        	document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=1&isCn=true";
	        }else{
	        	document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=1";
	        }
        	document.formList.submit();
		}
		
		function enFunc(){
			if(document.getElementById("en").checked){
				document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=1&isCn=false";
			}else{
				document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=1";
			}
			document.formList.submit();
		}
		
		function jourFuncAll(){
			document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=2&pType=1";	
			document.formList.submit();
		}
		function jourFunc(){
			if(document.getElementById("cnYes").checked){
				document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=2&pType=1&isCn=true";
			}else{
				document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=2";
			}
			
			document.formList.submit();
		}
		
		function jourFuncEn(){
			if(document.getElementById("cnFal").checked){
				document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=2&pType=1&isCn=false";	
			}else{
				document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=2";
			}
			
			document.formList.submit();
		}
		
		function articleFunc(){
			if(document.getElementById("ptype2").checked){
				document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=2&pType=2";
			}else{
				document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=2";
			}
			
			document.formList.submit();
		} 
	    function articleFunc1(){
			document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=2&pType=3";
			document.formList.submit();
		} 
		function jourFunQi(){
			document.formList.action="${ctx}/pages/user/form/mySubscription/public?searchType=2&pType=7";
			document.formList.submit();
		}
		
		function dividePage(targetPage){
			if(targetPage<0){return;}
			/* if(${form.searchType}=="1"){
				document.getElementById("form").action="${ctx}/pages/user/form/mySubscription/public?searchType=1&pageCount="+${form.pageCount}+"&curpage="+targetPage;
				document.getElementById("form").submit();
			}else{
				document.getElementById("form").action="${ctx}/pages/user/form/mySubscription/public?searchType=2&pageCount="+${form.pageCount}+"&curpage="+targetPage;
				document.getElementById("form").submit();
			} */
			
			var str=$("input[type='checkbox']:checked").val();
			
			if(document.getElementById("ptype2")!=null&&document.getElementById("ptype2").checked){
				str=${pType}+"";
			}
			var ctx="";
			if(str!=null){
				ctx="${ctx}/pages/user/form/mySubscription/public?searchType="+${form.searchType}+"&pageCount="+${form.pageCount}+"&curpage="+targetPage+"&pType="+str;
			}else{
				if( (document.getElementById("cn")!=null ||document.getElementById("en")!=null) && (document.getElementById("cn").checked || document.getElementById("en").checked)){
					if(document.getElementById("cn").checked){
						ctx="${ctx}/pages/user/form/mySubscription/public?searchType="+${form.searchType}+"&isCn=true"+"&pageCount="+${form.pageCount}+"&curpage="+targetPage;
					}else if(document.getElementById("en").checked){
						ctx="${ctx}/pages/user/form/mySubscription/public?searchType="+${form.searchType}+"&isCn=false"+"&pageCount="+${form.pageCount}+"&curpage="+targetPage;
					}
				}else{
					ctx="${ctx}/pages/user/form/mySubscription/public?searchType="+${form.searchType}+"&pageCount="+${form.pageCount}+"&curpage="+targetPage;
				}
			}
			document.getElementById("form").action=ctx;
			document.getElementById("form").submit();
		}
		
		function GO(obj){
			/* if(${form.searchType}=="1"){
				document.getElementById("form").action="${ctx}/pages/user/form/mySubscription/public?searchType=1&pageCount="+$(obj).val();
				document.getElementById("form").submit();
			}else{
				document.getElementById("form").action="${ctx}/pages/user/form/mySubscription/public?searchType=2&pageCount="+$(obj).val();
				document.getElementById("form").submit();
			} */
			if( (document.getElementById("cn")!=null ||document.getElementById("en")!=null) && (document.getElementById("cn").checked || document.getElementById("en").checked)){
				if(document.getElementById("cn").checked){
					document.getElementById("form").action="${ctx}/pages/user/form/mySubscription/public?searchType="+${form.searchType}+"&isCn=true"+"&pageCount="+$(obj).val();
	        	}else if(document.getElementById("en").checked){
	        		document.getElementById("form").action="${ctx}/pages/user/form/mySubscription/public?searchType="+${form.searchType}+"&isCn=false"+"&pageCount="+$(obj).val();
	        	}
        	}else{
        		document.getElementById("form").action="${ctx}/pages/user/form/mySubscription/public?searchType="+${form.searchType}+"&pageCount="+$(obj).val();
        	}
			document.getElementById("form").submit();
		}
	</script>
</head>

<body>
<jsp:include page="/pages/header/headerData" flush="true" />
   
 		
<div class="main personMain">
    	 <jsp:include page="/pages/menu?mid=subscribe&type=2" flush="true" />
    <div class="perRight">
    <form:form action="${form.who}" method="post" commandName="form" id="form" >
     <input type="hidden" id="isCn" name="isCn" value="${isCn}"/>
    <input type="hidden" id="pType" name="pType" value="${pType }"/>
    <%-- <form:hidden path="searchType"/> --%>
		<form:hidden path="range" />
   	  <div class="StabedPanels" >
            <ul class="oh">
                <li id="tab1" class="Stab <c:if test="${1 == form.searchType}">StabSeleted</c:if>"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.book2"/></li>
                <li id="tab2" class="Stab <c:if test="${2 == form.searchType}">StabSeleted</c:if>"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.journal2"/></li>
                <li id="tab3" class="Stab  <c:if test="${99 == form.searchType}">StabSeleted</c:if>"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.collection2"/></li>
           </ul>
      <div class="StabContent ScontentSelected">
      <div class="StabContent ScontentSelected">
           	<c:if test="${type==1 }">
           	<p class="mb10"><a href="javascript:void(0)" class="orgingA" onclick="downLog('${form.range}','${form.searchType}','${type }','${isCn }',null)"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.DownLoad"/></a></p>
              <p class="mb10">
            	<span class="mr10"><input type="checkbox" id="cn" class="vm"<c:if test="${isCn==true }">checked="checked"</c:if> onclick="cnFunc();"/><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.chinese"/></span>
                <span><input type="checkbox" id="en" class="vm"<c:if test="${isCn==false&&isCn!='' }">checked="checked"</c:if> onclick="enFunc();"/> <ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.forgin"/></span>
            </p>
        </c:if>
        <c:if test="${type==2}">    
            <p class="mb10"><a href="javascript:void(0)" class="orgingA" onclick="downLog('${form.range}','${form.searchType}','${type }','${isCn }','${pType }')"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.DownLoad"/></a></p>
              	<%--  中文电子期刊	暂时屏蔽
              	<p class="mb10">
	            	<span class="mr10"><input type="checkbox" value="${pType}" class="vm"<c:if test="${pType==1&&isCn==true }">checked="checked"</c:if> onclick="jourFunc();"/><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.journalCn"/></span> 
	            	<span class="mr10"><input type="checkbox" value="${pType}" class="vm"<c:if test="${pType==1&&isCn==false }">checked="checked"</c:if> onclick="jourFuncEn();"/><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.journalEn"/></span>
	               <span><input type="checkbox" value="${pType}"  class="vm"<c:if test="${pType==2 }">checked="checked"</c:if> onclick="articleFunc();"/> <ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.article"/></span>
	               <span><input type="checkbox"  value="${pType}" class="vm"<c:if test="${pType==3}">checked="checked"</c:if> onclick="articleFunc1();"/> <ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.journal.Period"/></span>
            	</p>
            	  --%>
            	 <p class="mb10">
	            	
	            </p>
	            <p class="mb10">
	            	<%-- <span class="mr10"><input id="cnYes" type="checkbox" value="${pType}" class="vm"<c:if test="${pType==1&&isCn==true }">checked="checked"</c:if> onclick="jourFunc();"/><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.journalCn"/></span> --%> 
	            	<span class="mr10"><input id="cnFal" type="checkbox" value="${pType}" class="vm"<c:if test="${pType==1&&isCn==false }">checked="checked"</c:if> onclick="jourFuncEn();"/><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.journalEn"/></span>
	                <span><input id="ptype2" type="checkbox" class="vm"<c:if test="${pType==2 }">checked="checked"</c:if> onclick="articleFunc();"/> <ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.article"/></span>
	                <%-- <span><input type="checkbox" class="vm" value="${pType}" <c:if test="${pType==7 }">checked="checked"</c:if> onclick="jourFunQi();"/> <ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.Period1.Period"/></span> 
	                <span class="mr10"><input id="ptype1" type="checkbox" class="vm"<c:if test="${pType==1&&isCn!=true&&isCn!=false }">checked="checked"</c:if> onclick="jourFuncAll();"/><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.journal"/></span>
	                --%>
	           </p>
        </c:if>
    
        </div>  
       <table width="99%" border="0" cellspacing="0" cellpadding="0" class="cartTable">
            <tr class="trTop">
                <td width="200" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.pubName"/></td>
                <td width="190" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.type"/></td>
                <c:if test="${type==1 }">
                 <td width="190" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.Isbn"/></td>
                 <td width="190" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.Pisbn"/></td>
                </c:if>
                    <c:if test="${type==2}">
                 <td width="190" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.Issn"/></td>
                 <td width="190" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.Eissn"/></td>
                </c:if>
                <td width="190" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.start"/></td>
                <td width="190" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.end"/></td>
            </tr>
           <c:forEach items="${list }" var="l" varStatus="index">
		             <c:set var="cssWord" value="${index.index%2==0?'a':'b' }"/>
		             <c:if test="${l.publications!=null }">
				        <tr class="trBody">
					        <td class="${cssWord}bodytd tdname" align="left">
					        <span style="width:430px">
					       	 	<c:if test="${l.publications.type!=3}">
					        	<a href="${ctx}/pages/publications/form/article/${l.publications.publications.id}" title="${l.publications.title}">${fn:replace(fn:replace(fn:replace(l.publications.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}</a>
					        	</c:if>
					        	<c:if test="${l.publications.type==3}">
					        	<a href="${ctx}/pages/publications/form/charperView?id=${l.publications.publications.id}&pid=${l.publications.id}">${fn:replace(fn:replace(fn:replace(l.publications.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}</a>
					        	</c:if>
					        </span>
			 		        </td>
					        <c:if test="${l.type==1}">
								<td class="${cssWord}bodytd" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.type1"/></td>
								 <c:if test="${type==1 }">
					                 <td class="${cssWord}bodytd" align="center">${l.publications.code}</td>
					                 <td class="${cssWord}bodytd" align="center">${l.publications.sisbn}</td>
                		        </c:if> <c:if test="${type==2 }">
					                 <td class="${cssWord}bodytd" align="center">${l.publications.code}</td>
					                 <td class="${cssWord}bodytd" align="center">${l.publications.eissn}</td>
                		        </c:if>
								<td class="${cssWord}bodytd" align="center"> </td>
								<td class="${cssWord}bodytd" align="center"> </td>
							</c:if>
					        <c:if test="${l.type==2}">
					        	<c:if test="${l.isTrial==1}">
					        		<td class="${cssWord}bodytd" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Lable.Trial"/></td>
					        	</c:if>
					        	<c:if test="${l.isTrial==null || l.isTrial==2}">
					        		<td class="${cssWord}bodytd" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.type2"/></td>
					        	</c:if>
					        	 <c:if test="${type==1 }">
					                 <td class="${cssWord}bodytd" align="center">${l.publications.code}</td>
					                 <td class="${cssWord}bodytd" align="center">${l.publications.sisbn}</td>
                		        </c:if> <c:if test="${type==2 }">
					                 <td class="${cssWord}bodytd" align="center">${l.publications.code}</td>
					                 <td class="${cssWord}bodytd" align="center">${l.publications.eissn}</td>
                		        </c:if>
								<td class="${cssWord}bodytd" align="center"><fmt:formatDate value="${l.startTime}" pattern="yyyy-MM-dd"/></td>								        
				        		<td class="${cssWord}bodytd" align="center"><fmt:formatDate value="${l.endTime}" pattern="yyyy-MM-dd"/></td>								        
					        </c:if>
				        </tr>
				        </c:if>
				        <c:if test="${l.publications==null }">
				        <tr>
					        <td class="<c:if test="${index.index%2==0 }" >atdbody01</c:if><c:if test="${index.index%2==1 }">btdbody01</c:if>">
					        <span>
					        <a href="${ctx}/pages/collection/form/list?id=${l.collection.id}" title="${l.collection.name }">${l.collection.name}</a>
					        </span>
					        </td>
					        <c:if test="${l.type==1}">
								<td class="${cssWord}bodytd" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.type1"/></td>
								<td class="${cssWord}bodytd" align="center"></td>
								<td class="${cssWord}bodytd" align="center"></td>
							</c:if>
					        <c:if test="${l.type==2}">
					        	<c:if test="${l.isTrial==1}">
					        		<td class="${cssWord}bodytd" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Lable.Trial"/></td>
					        	</c:if>
					        	<c:if test="${l.isTrial==null || l.isTrial==2}">
					        		<td class="${cssWord}bodytd" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Subscription.Table.Label.type2"/></td>
					        	</c:if>					        	
								<td class="${cssWord}bodytd" align="center"><fmt:formatDate value="${l.startTime}" pattern="yyyy-MM-dd"/></td>								        
				        		<td class="${cssWord}bodytd" align="center"><fmt:formatDate value="${l.endTime}" pattern="yyyy-MM-dd"/></td>								        
					        </c:if>
				        </tr>
				        </c:if>
					 </c:forEach>
          </table>
     		<div>
		 		<jsp:include page="../pageTag/pageTag.jsp">
          			<jsp:param value="${form }" name="form"/>
              	</jsp:include>	
      		</div>
       </div>
       </div>
       
      </div>
    </form:form>
                       
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
</div>

		<!-- 首页底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>