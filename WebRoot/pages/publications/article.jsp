<%@page import="java.io.File"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
<%@ include file="/common/tools.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/flexpaper/css/flexpaper.css?${d}" />
	<link href="${ctx}/css/reset.css?${d}" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/css/common.css?${d}" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/css/index.css?${d}" rel="stylesheet" type="text/css" />
		<link rel="styleSheet" type="text/css" href="${ctx}/css/jquery.fancybox.css?${d}" />

<%@ include file="/common/ico.jsp"%>

<script type="text/javascript">
//<![data[
function advancedSubmit(tvalue) {
	<c:if test="${sessionScope.lang!='en_US' }">
	$("#taxonomy1").val(tvalue);
	</c:if>
	<c:if test="${sessionScope.lang!='zh_CN' }">
	$("#taxonomyEn1").val(tvalue);
	</c:if>
	$("#type1").val('');
	$("#publisher1").val('');
		$("#pubDate1").val(''); 	
		$("#pCode1").val('');
		$("#publisherId1").val('');
		$("#subParentId1").val('');
		$("#parentTaxonomy1").val('');
		$("#parentTaxonomyEn1").val('');
		$("#curpage").val(0);
	$("#pageCount").val(10);
	document.formList.action="${ctx}/index/advancedSearchSubmit";
	document.formList.submit();
}
//]]-->
</script>
</head>
<body id="uboxstyle">
	<input type="hidden" id="pubId" value="${id }"/>
	<jsp:include page="/pages/header/headerData" flush="true" />
	<!--定义01 mainContainer 内容区开始-->
	<div class="main personMain">
    
      <div class="sort">  
   		<c:set var="objnews">${form.obj.latest!=null&&form.obj.latest>0 }</c:set>
		<c:set var="objoa">${form.obj.oa!=null&&form.obj.oa==2 }</c:set>
		<c:set var="objfree">${form.obj.free!=null&&form.obj.free==2 }</c:set>
		 <c:set var="objlicense">${(form.obj.subscribedIp!=null||form.obj.subscribedUser!=null)&&(form.obj.subscribedIp>0||form.obj.subscribedUser>0) }</c:set>
		<c:set var="objcollection">${form.obj.inCollection!=null&&form.obj.inCollection>0 }</c:set>
      	<c:set var="readPubId" value="${form.obj.type==3?form.obj.publications.id:form.obj.id}"/>
      	<c:set var="readPubStartPage" value="${form.obj.type==3?form.obj.startPage:0}"/>
        <c:set var="add1" value="${pricelist!=null&&fn:length(pricelist)>0&&form.obj.free!=2&&form.obj.oa!=2&&sessionScope.mainUser!=null && form.obj.subscribedUser<=0&&(form.obj.buyInDetail<=0&&form.obj.exLicense>=0)}"/>
		<c:if test="${add1==false }">
			<c:set var="add" value="false"/>
		</c:if>
		<c:if test="${add1==true &&form.obj.subscribedIp>0 }">
			<c:if test="${sessionScope.mainUser.institution.id==sessionScope.institution.id&&sessionScope.mainUser.level==2 }">
			<c:set var="add" value="false"/>
			</c:if>
			<c:if test="${sessionScope.mainUser.institution.id==sessionScope.institution.id &&sessionScope.mainUser.level!=2 }">
			<c:set var="add" value="true"/>
			</c:if>
			<c:if test="${sessionScope.mainUser.institution.id!=sessionScope.institution.id}">
			<c:set var="add" value="true"/>
			</c:if>
		</c:if>
		<c:if test="${add1==true &&(form.obj.subscribedIp==null||form.obj.subscribedIp<=0) }">
			<c:set var="add" value="true"/>
		</c:if>
			<c:set var="favourite" value="${sessionScope.mainUser!=null&&form.obj.favorite<=0 }"/>
			<c:set var="recommand" value="${sessionScope.institution!=null && (form.obj.recommand>0||sessionScope.mainUser.institution!=null) &&(form.obj.subscribedIp==null||form.obj.subscribedIp<=0)&&(form.obj.free!=2&&form.obj.oa!=2)}"/>	
		 	<c:set var="license" value="${form.obj.subscribedIp>0||form.obj.subscribedUser>0||form.obj.free==2||form.obj.oa==2 }"/>
		<!--列表内容开始-->
		
		<c:if test="${license==false }"><div class="h1_list"></div></c:if>	
        <c:if test="${license==true }"><div class="h2_list"></div></c:if>
                    	
          	<span>
	          <c:if test="${objnews==true || objfree==true || objoa==true  || objcollection==true}">
				<c:if test="${objfree==true }">
					<img src="${ctx }/images/ico/f.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Free" sessionKey="lang" />" />
				</c:if>
				<c:if test="${objoa==true }">
					<img src="${ctx }/images/ico/o.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.OA" sessionKey="lang" />" />
				</c:if>
			</c:if>
			<c:if
				test="${objlicense==true }">
				<img src="${ctx }/images/ico/ico_open.png" class="vm" />
			</c:if>
			<c:if
				test="${objlicense==false && objfree==false && objoa==false }">
				<img src="${ctx }/images/ico/ico_close.png" class="vm" />
			</c:if>
	          
	          
				<c:if test="${form.obj.type==1 }">
					<img src="${ctx }/images/ico/ico4.png" class="vm" />
				</c:if>
				<c:if test="${form.obj.type==3 }">
					<img src="${ctx }/images/ico/infor.png" class="vm" />
				</c:if>
			</span>	
           <span class="blue fb f14">
           ${form.obj.title}
           <c:if test="${form.obj.subTitle!=null}">：${form.obj.subTitle}</c:if>
           <c:if test="${form.obj.edition!=null}">-- ${form.obj.edition}</c:if>
           <c:if test="${form.obj.series!=null}">( ${form.obj.series} )</c:if>
           </span>
           <c:if test="${!((form.obj.subscribedIp!=null||form.obj.subscribedUser!=null)&&(form.obj.subscribedIp>0||form.obj.subscribedUser>0))&&form.obj.buyInDetail>0}">
				<font style="color: green;font-weight: bold;"><ingenta-tag:LanguageTag key="Page.Publications.Article.Lable.Buying" sessionKey="lang" /></font>
		   </c:if>
		   <c:if test="${(form.obj.subscribedIp!=null||form.obj.subscribedUser!=null)&&(form.obj.subscribedIp>0||form.obj.subscribedUser>0) }" >
				<font style="color: green;font-weight: bold;"><ingenta-tag:LanguageTag key="Page.Publications.Article.Lable.Subscribe" sessionKey="lang" /></font>
		   </c:if>
        </div>
        
        <div class="oh">
        	<div class="prodLeft">
        		<div class="prodDetal">
        		<div class="oh">
        			<div class="fl w520 pridDetalCont">
        			
	        			<c:if test="${form.obj.available==5}">	
		        			<p class="backgrYellow">
		        				<img src="${ctx}/images/ico/ico_20.png" />
		        				<font style="color: #F00;"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.available"/></font>
		        			</p>
		        			<br />
	        			</c:if>
	        			
        				<!-- 书名隐藏 
        				<p class="blockP">
	                    	<span class="w110 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.BookName"/>：</span>
	                        <span class="omit w400">${form.obj.title}</span>
                        </p>
                        -->
                        <c:if test="${not empty form.obj.author}">
                        <p class="blockP">
                        	<span class="w110 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.author"/>：</span>
                        	<span style="width:400px;word-wrap:break-word;">
	                        	<c:if test="${not empty form.obj.author}">
				            	 <c:set var="authors" value="${fn:split(form.obj.author,',')}" ></c:set>
			               		 <c:forEach items="${authors}" var="a" >
		                		 <a href='${ctx }/index/search?type=2&isAccurate=1&searchValue2=${a}'>${a}</a> &nbsp;
			                	 </c:forEach> 
			                	</c:if>
		                	</span>
                        </p>
						</c:if>
                        <p class="blockP">
                        	 <span class="w110 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.publisher"/>：</span>
			            	 <span style="width:400px;word-wrap:break-word;"><a href='${ctx }/index/search?type=2&searchsType2=4&searchValue2="${form.obj.publisher.name }"'>${form.obj.publisher.name}</a></span>
		                     <input type="hidden" name="searchValue2"  value="${form.obj.publisher.name}"/>
                        </p>
                        <c:if test="${form.obj.pubDate !=null && form.obj.pubDate !='' }">
                        <p class="blockP">
                        	<span class="w110 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.pubDate"/>：</span>
			                <span>${form.obj.pubDate}</span>
                        </p>
                        </c:if>
                         <p class="blockP">
			            <span class="w110 tr">E-ISBN：&nbsp;</span>
			            <span>${form.obj.code}</span>
			            </p>
                        <c:if test="${form.obj.sisbn !=null && form.obj.sisbn !='' }">
			            <p class="blockP">
			            <span class="w110 tr">S-ISBN：&nbsp;</span>
			            <span>${form.obj.sisbn}</span>
			            </p>
			          </c:if>
			          <c:if test="${form.obj.hisbn !=null && form.obj.hisbn !='' }">
			        	 <p class="blockP">
			            <span class="w110 tr">P-ISBN：&nbsp;</span>
			            <span>${form.obj.hisbn}</span>
			      		 </p>
			          </c:if>
			          	<p class="blockP">
			          		<span class="w110 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.CLC"/>：</span>
							<span style="width:400px;word-wrap:break-word;">
							<c:set var="csName"></c:set>
							<c:set var="names"></c:set>					
							<c:forEach items="${form.obj.csList }" var="cs" varStatus="a">
							<c:set var="csName">${cs.subject.code }  <c:if test="${sessionScope.lang=='zh_CN' }">${cs.subject.name}</c:if><c:if test="${sessionScope.lang=='en_US' }">${cs.subject.nameEn }</c:if></c:set>
							<c:set var="names">${names }${csName }</c:set>
				            <c:if test="${fn:length(form.obj.csList)!=(a.index+1) }"><c:set var="names">${names };</c:set></c:if>
							</c:forEach>					
							${names }
							</span>
			          	</p>
			          	<c:if test="${form.obj.keywords!=null}">
				        <p class="blockP">
				           <span class="w110 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.Keyword"/>：</span>
				           <span style="width:400px;word-wrap:break-word;"><a href="javascript:;" onclick="sk('${fn:replace(fn:replace(fn:replace(fn:replace(form.obj.keywords,";",""),"][",","),"["," "),"]","")} ')">
				              ${fn:replace(fn:replace(fn:replace(fn:replace(form.obj.keywords,";",""),"][",","),"["," "),"]","")}  
				           </a>
				        </p>
				        </c:if>
			          	<p class="blockP">
			          		<span class="w110 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.Language"/>：</span>
			            	<span>${form.obj.lang }</span>
			          	</p>
			          	<c:if test="${form.obj.free!=2&&form.obj.oa!=2}">
			          	<p class="blockP">
			          		<span class="w110 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.price"/>：</span>
			             	<span>
			             		USD&nbsp;
								<c:choose>
									<c:when test="${'USD' == form.obj.lcurr}"><fmt:formatNumber value="${form.obj.listPrice}" pattern="0.00" /></c:when>
									<c:when test="${'CNY' == form.obj.lcurr || 'RMB' == form.obj.lcurr}"><fmt:formatNumber value="${form.obj.listPrice * coeff * coeff1}" pattern="0.00" /></c:when>
									<c:otherwise><fmt:formatNumber value="${cny}" pattern="0.00" /></c:otherwise>
								</c:choose>
			             	</span>
			             	<span style="color:#FFFFFF;">${form.obj.lcurr}-${form.obj.listPrice}</span>
			          	</p>
			          	</c:if>
			          	
			          	<c:if test='${sessionScope.mainUser!=null}'>
			          		<%-- <c:if test="${pricelist!=null && fn:length(pricelist)>0 && fn:length(pricelist)<2}">
								<p class="blockP">
									<span class="w110 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.PurchasePrice"/>：</span>
									<span> 
										<c:forEach items="${pricelist}" var="pr" varStatus="indexPr">
											<c:if test="${pr.price!=0}">
												<c:if test="${pr.type==2 }">L</c:if>
												<c:if test="${pr.type==1 }">P</c:if>${pr.complicating}-${pr.price }${pr.currency }
											</c:if>
											<c:if test="${pr.price==0}">-</c:if>
										</c:forEach>
									</span>
								</p>
							</c:if> --%>
							<%--应王超要求图书隐藏销售价格by zhoudong 2015/06/09 <c:if test="${pricelist!=null && fn:length(pricelist)>=2 }">
								<p class="blockP">
									<span class="w110 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.PurchasePrice"/>：</span>
									<span> 
										<select id="price_${form.obj.id }" name="price_${form.obj.id}" >
											<c:forEach items="${pricelist}" var="pr" varStatus="indexPr">
													<option value="${pr.id }" >
														<c:if test="${pr.price!=0}">
															<c:if test="${pr.type==2 }">L</c:if>
															<c:if test="${pr.type==1 }">P</c:if>${pr.complicating}-${pr.price }${pr.currency }
														</c:if>
														<c:if test="${pr.price==0}">-</c:if>
													</option>
											</c:forEach>
										</select>
									</span>
								</p>
							</c:if> --%>
						</c:if>
			         
			          <%-- <c:if test='${sessionScope.mainUser!=null}'>
			          <c:if test="${pricelist!=null && fn:length(pricelist)>0 && add==true }">
			          <tr>
			            <td valign="middle" class="tda"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Price" sessionKey="lang" />：&nbsp;</td>
			            <td valign="middle" class="tdb">			
					  		<select id="price_${form.obj.id }"  name="price_${form.obj.id}" >
							<c:forEach items="${pricelist}" var="pr" varStatus="indexPr">
							<option value="${pr.id }"><c:if test="${pr.type==2 }">L</c:if><c:if test="${pr.type==1 }">P</c:if>${pr.complicating}-${pr.price }${pr.currency }</option>
							</c:forEach>
							</select>
						</td>
			          </tr>             
			          </c:if>				       
			          </c:if>  --%>
			         
			          <p class="mt10">
			          <%-- 	<c:if test="${form.obj.subscribedIp>0||form.obj.subscribedUser>0||form.obj.free==2||form.obj.oa==2 }">
							<a class="link gret_eye" onclick="viewPopTips('${readPubId}','${readPubStartPage}')">
								<ingenta-tag:LanguageTag key="Page.Pop.Title.OLRead" sessionKey="lang" />
							</a>				
						</c:if> --%>
			          	<c:if test="${pricelist!=null && fn:length(pricelist)>0 &&add==true }">
			          		<span>
							  <a href="javascript:void(0)" class="ico ico_cart" id="add_cart" onclick="addToCart('${form.obj.id}',1,1)">
							  	<ingenta-tag:LanguageTag key="Page.Index.Search.Link.AddToCart" sessionKey="lang" />
							  </a>
						    </span>
						</c:if>
						<%-- <span><a href="javascript:void(0)" class="ico ico_do" onclick="getResource('${form.obj.id}')"><ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" /></a></span> --%>
				<%-- 	<c:if test="${getResource}"> --%>
					<c:if test="${license==false }">
							<span>
							<a href="javascript:void(0)" id="resource_div" class="ico ico_do" onclick="popTips2('${form.obj.id}');">
							<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
							</a>
							</span>
						</c:if>
						<c:if test="${license==true }">
						<c:if test="${form.obj.type==1}">
						<span>
							<a href="javascript:void(0)" id="resource_div1" class="ico ico_doin" onclick="viewPopTips('${readPubId}','${readPubStartPage}')">
								<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
							</a>
							</span>
							</c:if>
							<c:if test="${form.obj.type==4}">
							<a href="javascript:void(0)" id="resource_div" class="ico ico_doin" onclick="popTips2('${form.obj.id}');">
							<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
							</a>
							</c:if>
							</c:if>
						<c:if test="${recommand}">
							<span>
							<a  href="javascript:void(0)" id="recommend_div" class="ico ico_recommed" onclick="popTips('${form.obj.id}');">
							<ingenta-tag:LanguageTag key="Page.Index.Search.Button.Recommed" sessionKey="lang" />
							</a>
							</span>
						</c:if>
						
						<c:if test="${favourite}">
							<span class="favourite2" id="${form.obj.id}">
							  	<a href="javascript:;" class="ico ico_collection">
							  		<span><ingenta-tag:LanguageTag key="Page.Index.Search.Link.Favorite" sessionKey="lang" /></span>
							  	</a>
							</span>
						</c:if>
						<c:if test="${sessionScope.mainUser!=null && !favourite}">
							<span class="favourite2" id="${form.obj.id}">
								<a href="javascript:;" class="ico ico_collection2">
									<span><ingenta-tag:LanguageTag key="Page.Index.Search.Link.collected" sessionKey="lang" /></span>
								</a>
							</span>
						</c:if>
			          </p>     
			        </div>
        		
        		<div class="fr">
        			<c:if test="${null != form.obj.cover && '' != form.obj.cover}">
        				<a class="table_img" href="/filetemp${form.obj.cover}">
        			</c:if>
        			
	        			<c:choose>
							<c:when test="${null == form.obj.cover || '' == form.obj.cover}">
								<div class="imgMorenMid">
									<p title='${form.obj.title}'>${form.obj.title}</p>
								</div>
							</c:when>
							
							<c:otherwise>
								<img width="137" src="${ctx}/pages/publications/form/cover?t=2&id=${form.obj.id}" onerror="this.src='${ctx}/images/noimg.jpg'" />
							</c:otherwise>
						</c:choose>
						
					<c:if test="${null != form.obj.cover || '' != form.obj.cover}">
						</a>
					</c:if>
        		</div>
        	</div>
        
        <!--列表内容结束--> 
     <!-- ***************************************正常显示章节列表start************************************************** -->
      <c:if test="${form.obj.remark!=null && form.obj.remark!=''&& form.obj.remark!='[无简介]' }">
      <div class="mt10">
        <h1 class="h1Tit borBot"><span><ingenta-tag:LanguageTag key="Pages.publications.article.Lable.Description" sessionKey="lang" /></span></h1>
        <p class="fontFam">        
          ${fn:replace(fn:replace(fn:replace(form.obj.remark,"&lt;","<"),"&gt;",">"),"&amp;","&")}  
        </p>
      </div>
      </c:if>
      <c:if test="${list!=null && fn:length(list)>0 }">
      <div class="mt10">
        <h1 class="h1Tit borBot"><span><ingenta-tag:LanguageTag key="Pages.publications.article.Lable.TOC" sessionKey="lang" /></span></h1>
        <c:forEach items="${list }" var="p" varStatus="index">         	
        	<c:if test="${form.chaperShow==1}">
        	<div style="margin-left:${p.browsePrecent}px" class=" ${p.fullText}" <c:if test="${index.index==fn:length(list)-1 }">style="border-bottom:none"</c:if>>
	          <p class="chapt_author_p" >
	          <c:if test="${form.obj.local==2 && (form.obj.subscribedIp>0||form.obj.subscribedUser>0||form.obj.free==2||form.obj.oa==2) }"> 
	          	<c:if test="${p.startPage>0}">
	        		<%-- 	<c:if test="${license==true }"><img src="${ctx }/images/ico/ico_open.png" class="vm"/></c:if>
						<c:if test="${license==false }"><img src="${ctx }/images/ico/ico_close.png" class="vm"/></c:if> --%>
			   			 <a href="javascript:void(0)" onclick="viewPopTips('${p.publications.id}','${p.startPage}')">${p.title}</a>
	          	</c:if>    	
              	<c:if test="${p.startPage<=0}">${p.title}</c:if>
              	</c:if>
                 
              <c:if test="${form.obj.local==1||!(form.obj.subscribedIp>0||form.obj.subscribedUser>0||form.obj.free==2||form.obj.oa==2)}">
               	<c:if test="${p.startPage>0}">
	               	<a href="javascript:void(0)" >${p.title}</a>
		        </c:if> 
		            <c:if test="${p.startPage<=0}">${p.title}</c:if>
              </c:if> 
	          </p>    
	          
	          <c:if test="${(p.fileType!=null && (p.fileType==1 || p.fileType==0)) && p.startPage>0}">   
	          <p class="chapt_author_p2" >
		          <ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.PageRange" sessionKey="lang" />：&nbsp;
		          <span>
		          <c:if test="${p.endPage!=null }">
	              	${p.startPage} – ${p.endPage}
	              </c:if>                            
	              <c:if test="${p.endPage==null }">
	              	${p.startPage}
	              </c:if>
	              </span>
	          </p>
	          </c:if>
              <p>
              	<c:if test="${p.homepage==2}">
              		<c:if test="${license==false}">
         			     	<!-- 获取资源 -->
         			     	<c:if test="${add }">
							<!-- 购买 -->
								<span class="mr20"><a href="javascript:void(0)" onclick="addToCart('${p.id}',1);"  title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Buy' sessionKey='lang'/>"><img src="${ctx }/images/ico/ico14-blank.png" class="vm" />添加到购物车</a></span> 
							</c:if>
							
         			     	<span class="mr20">
							<a href="javascript:void(0)" id="resource_div" title="<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />" onclick="popTips2('${p.id}');"><img id="favourites_${p.id }" src="${ctx }/images/ico/ico15-blue.png" class="vm" />
							<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
							</a>
							</span>
         			     	
							<c:if test="${recommand}">
							<!-- 推荐 -->
								<span class="mr20"><a href="javascript:void(0)" id="recommand_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' />" onclick="recommends('${p.id}');"><img src="${ctx }/images/ico/ico16-blue.png" class="vm" /><ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' /></a></span>
							</c:if>	
							
							<c:if test="${favourite }">
							<!-- 收藏 -->
								<span><a href="javascript:void(0)" id="favourites_div_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' />" onclick="addFavourites('${p.id }');"><img id="favourites_${p.id }" src="${ctx }/images/ico/ico13-blue.png" class="vm" /><ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' /></a></span> 
							</c:if>
							
							<c:if test="${sessionScope.mainUser!=null && !favourite }">
							<!-- 已收藏 -->
								<span><a style="cursor:auto" class="collected" ><img title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />" src="${ctx }/images/ico/ico13-blank.png" class="vm" /><ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' /></a></span>
							</c:if>
						</c:if>
				 </c:if>	
              </p>
	        </div> 
	        </c:if>
	       <!-- ***************************************正常显示章节列表end************************************************** --> 
	       <c:if test="${form.chaperShow==2}">
	       <c:set var="favouritechaper" value="${sessionScope.mainUser!=null&&p.favorite<=0 }"/>
	       <c:set var="recommandchaper" value="${sessionScope.institution!=null && (p.recommand>0||sessionScope.mainUser.institution!=null) &&(p.subscribedIp==null||p.subscribedIp<=0)&&(p.free!=2&&p.oa!=2)}"/>	
		   <div class="mt10">
	          <h1 class="h1Tit borBot"><a href="${ctx}/pages/publications/form/charperView?id=${p.id}&pid=${p.publications.id}">
	         <c:if test="${license==true||p.subscribedIp>0||p.subscribedUser>0||p.free==2||p.oa==2 }"><img src="${ctx }/images/ico/ico_open.png" /></c:if>
			 <c:if test="${license==false&&p.subscribedIp==0&&p.subscribedUser==0 }"><img src="${ctx }/images/ico/ico_close.png" /></c:if>
			 ${ p.title}
			 </a>
	          </h1>
	          <c:if test="${p.author!=null && p.author !='' }">
		          <p class="intro">
			          <ingenta-tag:LanguageTag key="Global.Label.Author" sessionKey="lang" />：
			          ${ p.author}
		          </p>
	          </c:if>
	          <c:if test="${p.remark!=null && p.remark !='' && p.remark !='[无简介]' }">
                <p class="intro">
	                <div style="margin-left:30px;height:20px;overflow: hidden;"><a style="cursor: pointer;" onclick="senfe(this);">+ <ingenta-tag:LanguageTag key="Page.Index.Search.Desc.Show" sessionKey="lang" /></a>
						<p>
							${p.remark}
						</p>
					</div>
				</p>
              </c:if>
	          <p><ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.PageRange" sessionKey="lang" />：
	          ${p.startPage} – ${p.endPage}</p>
	          <p class="mt5">
	           <span>
		           <!-- 添加到购物车 -->
		           	<c:if test="${add }">
						<span class="mr20"><a href="javascript:void(0)" onclick="addToCart('${p.id}',1);" id="add_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Buy' sessionKey='lang'/>"><img src="${ctx }/images/ico/ico14-blank.png" class="vm" />添加到购物车</a></span> 
					</c:if>
	           		<!-- 获取资源 -->
		           <c:if test="${objlicense==false&&objoa==false&&objfree==false }">
						<span class="mr20">
						<a href="javascript:void(0)"  id="resource_div" onclick="popTips2('${p.id}');"><img src="${ctx }/images/ico/ico15-blue.png" class="vm" />
						<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
						</a>
						</span>
					</c:if>
	           		<c:if test="${objlicense==true||objoa==true||objfree==true }">
						<c:if test="${p.type==1 }">
							<span class="mr20">
								<a href="javascript:void(0)"  id="resource_div"  onclick="viewPopTips('${p.id}','0',<c:if test="${oa==false&&free==false}">1</c:if><c:if test="${oa==true||free==true}">2</c:if>)"><img src="${ctx }/images/ico/ico15-green.png" class="vm" />
								<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
								</a>
							</span>
						</c:if>
						<c:if test="${p.type==4 }">
							<span class="mr20">
								<a href="javascript:void(0)"  id="resource_div1"  onclick="popTips2('${p.id}');"><img src="${ctx }/images/ico/ico15-green.png" class="vm" />
								<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
								</a>
							</span>
						</c:if>
				</c:if>
				<!-- 推荐 -->
	           	<c:if test="${recommand}">
					<span class="mr20"><a href="javascript:void(0)" id="recommand_${p.id }" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' />" onclick="recommends('${p.id}');"><img src="${ctx }/images/ico/ico16-blue.png" class="vm" /><ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' /></a></span>
				</c:if>

				<!-- 收藏 -->
				<c:if test="${0 == p.favorite &&favouritechaper}">
					<span class="favourite" id="${p.id}">
						<a href="javascript:;">
							<img id="favourites_${p.id}" src="${ctx}/images/unfavourite.png" class="vm" />
							<span><ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' /></span>
						</a>
					</span> 
				</c:if>
				<!-- 已收藏 -->
				<c:if test="${sessionScope.mainUser!=null && (1 == p.favorite) }">
					<span class="favourite" id="${p.id}">
						<a href="javascript:;" class="blank">
							<img src="${ctx}/images/favourite.png" class="vm" />
							<span><ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' /></span>
						</a>
					</span>
				</c:if>

	         </span>
	         
	         <%--  <span><a href="javascript:void(0)"><img src="${ctx}/images/download.png" /></a></span> 暂时屏蔽 章节下载按钮 功能待完善 --%>
	          </p>
           </div>
	       </c:if>
	       <!-- ***************************************正常显示章节列表end************************************************** --> 
        </c:forEach>        
      </div>
      </c:if>
      <c:if test="${form.obj.activity!=null && form.obj.activity!='' }">
      <div class="chapter">
        <h1 class="chapt_title"><span><ingenta-tag:LanguageTag key="Pages.publications.article.Lable.Promotion" sessionKey="lang" /></span></h1>
        <div class="chapt_cont">
          ${form.obj.activity}
        </div>
      </div>
      </c:if>
      
<!-- 同类热销 -->
<div id="bottomByRedis"></div>

			
      </div>
      </div>
      
         <!-- 浏览过本书的用户还看过 -->
         <c:if test="${1 == form.obj.type}">
			<div class="prodRight f14">
				<jsp:include page="/pages/publications/form/forArticleRightByRedis">
	        		<jsp:param value="${form.obj.id}" name="id" />
	        		<jsp:param value="${form.obj.title}" name="title" />
	        	</jsp:include>
			</div>
		</c:if>
		
    </div>
	</div>
	<div class="boderBottom"></div>
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

	<script type="text/javascript" src="${ctx}/js/jquery.fancybox-1.3.1.js"></script>
	<script type="text/javascript" src="${ctx}/js/PopUpLayer.js"></script>
	<script language="javascript">
	$(document).ready(function(e) {
 	  	getCount();
		$(".meaudown").mouseover(function(){
		  $(this).children("ul").css('display','block');
	  });
	    $(".meaudown").mouseleave(function(){
		  $(this).children("ul").css('display','none');
	  });
	    
		$("a.table_img").fancybox({
			'padding'			: 10,
			'margin'			: 18,
			'speedIn'			: 2000,
			'speedOut'			: 1000,
			'changeSpeed'		: 1000,
			'autoDimensions'	: true,
			'centerOnScroll'	: true,
			'hideOnOverlayClick': true,
			'hideOnContentClick': false,
			'transitionIn'		: 'elastic',
			'transitionOut'		: 'elastic',
			'titleShow' 		: false
		});
		
		getBottomByRedis("${form.obj.id}");

		// 收藏和取消收藏
		$(".favourite2").on("click", function() {
			var This = $(this);
			$.get("${ctx}/pages/favourites/form/commit", { pubId : This.attr("id") }, function(data) {
				if ("success" == data) {
					This.find("a").attr("class", "ico ico_collection2");
					This.find("img").attr("src", "${ctx}/images/favourite.png");
					This.find("span").html("<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
					art.dialog.tips('<ingenta-tag:LanguageTag key='Controller.Favourites.commit.success' sessionKey='lang' />', 1, 'success');
				} else if ("del" == data) {
					This.find("a").attr("class", "ico ico_collection");
					This.find("img").attr("src", "${ctx}/images/unfavourite.png");
					This.find("span").html("<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' />");
					art.dialog.tips('<ingenta-tag:LanguageTag key='Controller.Favourites.commit.cancel' sessionKey='lang' />', 1, '');
				}
			});
		});
	});
	
	function getCount(){
		var testExp=/^\d+$/;
		$(".chapt_author_p2 span").each(function(i,item){
			var pagNubStr=$(item).text().trim();
			var numbers=pagNubStr.split("–");
			var range=1;
			if(numbers){
				var spage=numbers[0].trim().match(testExp)?parseInt(numbers[0].trim()):numbers[0];
				if(numbers.length==2){
					var epage= numbers[1].trim().match(testExp)?parseInt(numbers[1].trim()):numbers[1];
					range=epage-spage+1;					
				}
				$(item).text(pagNubStr+" ("+range+")");
			}
		})
		/* var sPage = $("#sPage").val();
		var ePage = $("#ePage").val();
		var testExp=/^\d+$/;
		if(sPage.match(testExp) && ePage.match(testExp)){
			var s = parseInt(sPage);
			var e = parseInt(ePage);
			var c = e>=s?e-s+1:0;
			$("#chapt_author_p2").html("("+ c + ")");
		}else{
			$("#chapt_author_p2").append("-");
	 	}*/
	}
	
	// 右侧列表
	function getBottomByRedis(id){
		var parObj=$("#bottomByRedis");
		$.ajax({
			type : "POST",  
	        url: "${ctx}/pages/publications/form/forArticleBottomListByRedis",
	        data: {
	        	id : id
	        },
	        success : function(data) { 
	         	$(parObj).html(data);
	         	// $("#bottomList").load("/upload/article/${form.obj.id}_b.html");
	        }
	  });
	}
	//右侧列表
	function getRightList(){
		var parObj=$("#rightList");
		$.ajax({
			type : "POST",  
	        url: "${ctx}/pages/publications/form/forArticleRight",
	        success : function(data) { 
	         	$(parObj).html(data);
//	         	$(parObj).css("text-align","left");
	        },  
	        error : function(data) {
	          	$(parObj).html(data);
	        }  
	  });
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
		$("#page").val(0);
		$("#pageCount").val(10);
		$("#order1").val('');
		$("#lcense1").val('${sessionScope.selectType}');
		document.formList.submit();
	}
	function recommendSubmit() {
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
					art.dialog.tips(s[1],1);
					confirmTerm();
				}else{
					alert(s[1],1,'error');
				}
			},
			error : function(data) {
				art.dialog.tips(data,1,'error');
			}
		}); 
	}
	
	function addToCart(pid, ki,Staust) {
		var price = $("#price_" + pid).val();
		$.ajax({
			type : "POST",
			url : "${ctx}/pages/cart/form/add",
			data : {
				pubId : pid,
				priceId : price,
				kind : ki,
				r_ : new Date().getTime()
			},
			success : function(data) {
				var s = data.split(":");
				if (s[0] == "success") {
					art.dialog.tips(s[1],1);//location.reload();
					$("#add_"+pid).css("display","none");
					$("#cartCount").html("["+s[2]+"]");
					$("#price_" + pid).css("display","none");
					//cnpReload();
				}else{
					art.dialog.tips(s[1],1,'error');
				}
			},
			error : function(data) {
				art.dialog.tips(data,1,'error');
			}
		});
	}
	
	function addFavourites(pid,staus) {
		$.ajax({
			type : "POST",
			url : "${ctx}/pages/favourites/form/commit",
			data : {
				pubId : pid,
				r_ : new Date().getTime()
			},
			success : function(data) {
				var s = data.split(":");
				if (s[0] == "success") {
					art.dialog.tips(s[1],1);//location.reload();
					if(staus==1){
						$("#favourites_div_"+pid).removeAttr("onclick");
						$("#favourites_div_"+pid).attr("title","<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
						$("#favourites_div_"+pid).attr("class","ico ico_collection2");	
						$("#favourites_div_"+pid).css("cursor","auto");
					}else if(staus==2){
						$("#favourites_div_chaper"+pid).removeAttr("onclick");
						$("#favourites_div_chaper"+pid).attr("title","<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
						$("#faveimg_"+pid).attr("src","${ctx}/images/collect_light.png");
					}
				}else{
					art.dialog.tips(s[1],1,'error');
				}
			},
			error : function(data) {
				art.dialog.tips(data,1,'error');
			}
		});
	}
</script>




<script type="text/javascript">
	//<![data[
	function senfe(e) {
		var s = 1.2;
		var s2 = 8;
		var obj = e.parentNode;
		var oh = parseInt(obj.offsetHeight);
		var h = parseInt(obj.scrollHeight);
		var nh = oh;

		if (obj.getAttribute("oldHeight") == null) {
			obj.setAttribute("oldHeight", oh);
		} else {
			var oldh = Math.ceil(obj.getAttribute("oldHeight"));
		}

		var reSet = function() {
			if (oh < h) {
				e.innerHTML = "- <ingenta-tag:LanguageTag key="Page.Index.Search.Desc.Hide" sessionKey="lang" />";
				if (nh < h) {
					nh = Math.ceil(h - (h - nh) / s);
					obj.style.height = nh + "px";
				} else {
					window.clearInterval(IntervalId);
				}
			} else {
				e.innerHTML = "+ <ingenta-tag:LanguageTag key="Page.Index.Search.Desc.Show" sessionKey="lang" />";
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
//]]-->
</script>

<script type="text/javascript">
	//******************************弹出层--开始*********************************//
	/*
	 *弹出本页指定ID的内容于窗口
	 *id 指定的元素的id
	 *title: window弹出窗的标题
	 *width: 窗口的宽,height:窗口的高
	 */
	function showTipsWindown(title, id, width, height) {
		tipsWindown(title, "id:" + id, width, height, "true", "", "true", id);
	}
	function confirmTerm() {
		parent.closeWindown();
	}
	//在线阅读起关闭
	function confirmTerm2(id) {
		parent.closeWindown();
		$.ajax({
			type : "POST",
			async : false,
			url : "${ctx}/pages/publications/form/release",
			data : {
				id : id,
				r_ : new Date().getTime()
			},
			success : function(data) {
			},
			error : function(data) {
			}
		});
	}
	
	//推荐
	function recommends(pid) {
		//先将信息放到对应的标签上title, code, type, pubSubject
		art.dialog.open("${ctx}/pages/recommend/form/edit?pubid="+pid,{title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/>",top: 100,width: 700, height: 400,lock:true});
	}
	
	//弹出层调用
	function popTips(pid) {
	/* 	showTipsWindown("",
				'simTestContent', $(window).width()*0.6, $(window).height()*0.65); */
				art.dialog.open("${ctx}/pages/recommend/form/edit?pubid="+pid,{title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/>",top: 100,width: 700, height: 400,lock:true});
	}
	//获取资源弹出层调用
	function popTips2(pid) {
	/* 	showTipsWindown("",
				'simTestContent', $(window).width()*0.6, $(window).height()*0.65); */
				/* alert(pid); */
				art.dialog.open("${ctx}/pages/publications/form/getResource?pubid="+pid,{id : "getResourceId",title:"",top: 200,width: 340, height: 200,lock:true}); /* <ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend" sessionKey="lang"/> */
	}
	//关键词
	function sk(s) {
		location.href=encodeURI("${ctx}/index/advancedSearchSubmit?searchValue=" + encodeURI(s));
	}
	//在线阅读弹出层调用
	function viewPopTips(id,page) {
		var url="";
		          
            //tmp.focus() ;
		if(page=='0'){
			url = "${ctx}/pages/view/form/view?id="+id;
		}else{
			url = "${ctx}/pages/view/form/view?id="+id+"&nextPage="+page;
		}
		var of=false;
		<c:if test="${(form.obj.free!=null&&form.obj.free==2) || (form.obj.oa!=null&&form.obj.oa==2) }">
			of=true;
		</c:if>  
          //首先Ajax查询要阅读的路径
	if(of){
		//window.location.href=url;
		var tmp=window.open("about:blank","","scrollbars=yes,resizable=yes,channelmode") ;
          tmp.location=url;
	}else{
	$.ajax({
		type : "POST",
		async : false,
		url : "${ctx}/pages/publications/form/getUrl",
		data : {
			id : id,
			nextPage:page,
			r_ : new Date().getTime()
		},
		success : function(data) {
			var s = data.split(";");
			if (s[0] == "success") {
			var tmp=window.open("about:blank","","scrollbars=yes,resizable=yes,channelmode") ;
				if(s[1].indexOf('/pages/view/form/view')>=0){
					//window.location.href=s[1];
					tmp.location=s[1];
				}else{
				//	window.location.href="${ctx}/pages/view/form/view?id="+id+"&webUrl="+s[1];
					tmp.location="${ctx}/pages/view/form/view?id="+id+"&webUrl="+s[1];
				}
			}else if(s[0] == "error"){
				art.dialog.tips(s[1],1,'error');
			}
		},
			error : function(data) {
				art.dialog.tips(data,1,'error');
			}
		});
		}
	}
	//******************************弹出层--结束*********************************//
</script>
<!--弹出层样式 结束  -->


</body>
<form:hidden id="pubid" path="form.obj.id" />
</html>
