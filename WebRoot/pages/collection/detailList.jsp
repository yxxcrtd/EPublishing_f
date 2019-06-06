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
//<![data[

function addToCart(cid,ki){
		$.ajax({
  			type : "POST",  
			url: "${ctx}/pages/cart/form/add",
			data: {
				collectionId:cid,
				kind:ki,
				r_ : new Date().getTime()},
			success : function(data) {  
			    var s = data.split(":");
			    	     
			    if(s[0]=="success"){
			    	art.dialog.tips(s[1],1);//location.reload();
			    	$("#addCarts").css("display","none");
			    	$("#cartCount").html("["+s[2]+"]");
			    }else{
			    	art.dialog.tips(s[1],1,'error');
			    }
			},  
			error : function(data) {  
			    art.dialog.tips(data,1,'error');
			}  
		});
	}
	
	//在线阅读
	function viewPopTips(id,page,yon) {
		var url="";
		if(page=='0'){
			url = "${ctx}/pages/view/form/view?id="+id;
		}else{
			url = "${ctx}/pages/view/form/view?id="+id+"&nextPage="+page;
		}
		//首先Ajax查询要阅读的路径
		if(yon=='2'){
			art.dialog.open(url,{title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.OLRead" sessionKey="lang" />",width: $(window).width()*0.8,height: $(window).height()*0.9,lock: true,close:function(){
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
					}});
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
					art.dialog.open(s[1],{title:"<ingenta-tag:LanguageTag key="Page.Pop.Title.OLRead" sessionKey="lang" />",width: $(window).width()*0.8,height: $(window).height()*0.9,lock: true,close:function(){
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
					}});
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
//]]-->
</script>
</head>
<body>
	<div class="big">
		<!--以下top state -->
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--以上top end -->
		<!--以下中间内容块开始-->
		<div class="main">
			<!-- 产品包介绍部分开始 -->
			<c:if test="${form.obj.id != ppvCollId }">
			<div class="pagetitle">
		    	<div class="pagecont">
		        	<h1>${pccList[0].collection.name }</h1>
		            <p>${pccList[0].collection.desc }</p>
		        </div>
		        <div id="addCarts" class="pagecart">
		        	<c:if test="${sessionScope.mainUser!=null}">
						<c:if test="${sessionScope.mainUserLevel!=null&&sessionScope.mainUserLevel==2 }">
							<p><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.publications.article.Label.price"/>
							${pccList[0].collection.currency}&nbsp;<fmt:formatNumber value="${pccList[0].collection.price}" pattern="0.00" />
							</p>
			        	<p class="pagecart_buy">
			        		<a href="javascript:void(0)" class="a_gret" onclick="addToCart('${pccList[0].collection.id}',2)"><ingenta-tag:LanguageTag key="Page.Index.Search.Link.Buy" sessionKey="lang" /></a>
			        	</p>
			        	</c:if>
			        </c:if>
			        <c:if test="${sessionScope.mainUser==null}">
			        	<p>
			        	<ingenta-tag:LanguageTag key="Page.Login.Info.String1" sessionKey="lang" />
			        	<a style="color:#00668f;font-weight:bold" href="${ctx}/pages/user/form/register">
			        	<ingenta-tag:LanguageTag key="Page.Login.Link.Register" sessionKey="lang" />
			        	</a><ingenta-tag:LanguageTag key="Page.Login.Info.String2" sessionKey="lang" />
			        	<a href="${ctx}/pages/user/form/register" style="cursor: pointer; color:#88272e;font-weight:bold" title="" id="signinlink">
			        	<ingenta-tag:LanguageTag key="Page.Login.Link.Login" sessionKey="lang" /></a>
			        	<ingenta-tag:LanguageTag key="Page.Login.Info.String3" sessionKey="lang" />
			        	</p>
					</c:if>
		        </div>
		    </div>
		    </c:if>
			<!-- 产品包介绍部分结束 -->
			<!--左侧导航栏 -->
			<div class="main_nav">
			<!-- 出版物类型 -->
				<h1>
					<span style="float:left"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Publications" sessionKey="lang" /></span>
				</h1>
				<ul id="type_statistic">
		          	<c:if test="${type2>0 }">
		          		<li>
		          			<a href="${ctx }/pages/collection/form/list?id=${form.id}&type=2"  >
		          				<span class="alph"><img src="${ctx }/images/ico_03.png" /></span>
		          				<span class="write">
		          				<ingenta-tag:LanguageTag key="Page.Collection.Lable.Ejournal" sessionKey="lang" />
		          				</span>
		          				[${type2}]
		          			</a>
		          		</li>
		          	</c:if>
             		<c:if test="${type1>0 }">
             			<li>
             				<a href="${ctx }/pages/collection/form/list?id=${form.id}&type=1">
             					<span class="alph"><img src="${ctx }/images/ico_04.png" /></span>
             					<span class="write">
             					<ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option1" sessionKey="lang" />
             					</span>
             					[${type1}]
             				</a>
             			</li>
             		</c:if>
		        </ul>
		        <!-- 出版社 -->
				<h1>
					<span style="float:left"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Publisher" sessionKey="lang" /></span>
				</h1>
				<ul id="publisher_statistic">
		          	<c:forEach items="${pubStatistic}" var="p" varStatus="index">
		          		<c:if test="${p.collectionPubContent>0}">
			            	<li>
			            		<a href="${ctx }/pages/collection/form/list?id=${form.id}&publisherId=${p.id }&_r=${_r}">
			            			<span class="alph"><img src="${ctx }/images/jiantou.png" /></span>
			            			<span class="write">
			            			${p.name}
			            			</span>
			            			[${p.collectionPubContent}]
			            		</a>
			            	</li>
		            	</c:if>
		            </c:forEach>
		        </ul>
				<!-- 分类 -->
				<h1>
					<span style="float:left"><ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Subject" sessionKey="lang" /></span>
				</h1>
				<ul id="subject_statistic">
		        	<c:forEach items="${subList }" var="sub" varStatus="index">
		          		<c:if test="${sub.countPP>0 }">
		          			<li>
		          				<a href="${ctx }/pages/collection/form/list?subId=${sub.id}&id=${form.id}"  >
		          				<span class="alph">${sub.code  }</span>
				          		<span class="write">
					          		<c:if test="${sessionScope.lang=='zh_CN' }">${sub.name}</c:if>
					          		<c:if test="${sessionScope.lang=='en_US' }">${sub.nameEn}</c:if>
				          		</span>
				          		[${sub.countPP }]
				          		</a>
				          	</li>
		          		</c:if>
		          	</c:forEach>
		        </ul>
			</div>
			<!--左侧导航栏结束 -->

			<!--右侧列表内容-->
			<div class="main_content">
				<h1>
					<ingenta-tag:LanguageTag key="Page.Publications.Journal.Article.List" sessionKey="lang"/>
				</h1>
				<c:forEach items="${pccList }" var="p" varStatus="index">
				    <!--  license已订阅 IP-->
					<c:set var="license">${(p.publications.subscribedIp!=null||p.publications.subscribedUser!=null)&&(p.publications.subscribedIp>0||p.publications.subscribedUser>0) }</c:set>
					<!--  news最新标志-->
					<c:set var="news">${p.publications.latest!=null&&p.publications.latest>0 }</c:set>
					<!--  oa是否开源1、不开源 2、开源-->
					<c:set var="oa">${p.publications.oa!=null&&p.publications.oa==2 }</c:set>
					<!-- free是否免费1、不免费 ；2、免费 -->
					<c:set var="free">${p.publications.free!=null&&p.publications.free==2 }</c:set>
					<!-- collection是否在产品包中 1-不在 2-在  -->
					<c:set var="collection">${p.publications.inCollection!=null&&p.publications.inCollection>0 }</c:set>
					
					<c:set var="add1" value="${p.publications.priceList!=null&&fn:length(p.publications.priceList)>0&&p.publications.free!=2&&p.publications.oa!=2&&sessionScope.mainUser!=null && p.publications.subscribedUser<=0&&(p.publications.buyInDetail<=0&&p.publications.exLicense>=0)}"/>
					<c:if test="${add1==false }">
						<c:set var="add" value="false"/>
					</c:if>
					<c:if test="${add1==true &&p.publications.subscribedIp>0 }">
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
					<c:if test="${add1==true &&(p.publicationssubscribedIp==null||p.publicationssubscribedIp<=0) }">
						<c:set var="add" value="true"/>
					</c:if>
					<c:if test="${add1==false }">
						<c:set var="add" value="false"/>
					</c:if>
					<c:set var="favourite" value="${sessionScope.mainUser!=null&&p.publications.favorite<=0 }"/>
					<c:set var="recommand" value="${(p.publications.recommand>0||sessionScope.mainUser.institution!=null) &&(p.publications.subscribedIp==null||p.publications.subscribedIp<=0)&&(p.publications.free!=2&&p.publications.oa!=2)}"/>
				<!--列表内容开始-->
				<c:if test="${license==false&&oa==false&&free==false }">
		          <div class="h1_list">
		          <p class="p_left journ_pleft" style="width:550px">
					<img src="${ctx }/images/close.png" />
		      		<a name="title" title="${p.publications.title }" href="${ctx}/pages/publications/form/article/${p.publications.id}">
		      			${p.publications.title }
		      		</a>
			      	<p>
			        <c:if test="${news==true || free==true || oa==true  || collection==true}">
			          <p class="p_right">	     
			          		<%-- <c:if test="${news==true }"><img src="${ctx }/images/n.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.New" sessionKey="lang" />"/></c:if> --%>
							<c:if test="${free==true }"><img src="${ctx }/images/ico/f.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Free" sessionKey="lang" />"/></c:if>
							<c:if test="${oa==true }"><img src="${ctx }/images/ico/o.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.OA" sessionKey="lang" />"/></c:if>
							<%-- <c:if test="${collection==true }"><img src="${ctx }/images/c.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Collection" sessionKey="lang" />"/></c:if> --%>     	  
			          </p>
			          </c:if>
		          </div>
		        </c:if>
		        <c:if test="${license==true||oa==true||free==true }">
					<div class="h2_list">
					<p class="p_left journ_pleft">
						<img src="${ctx }/images/open.png" />
			      		<a name="title" title="${p.publications.title }" href="${ctx}/pages/publications/form/article/${p.publications.id}">
			      			${p.publications.title }
			      		</a>
			      	<p>
			        <c:if test="${news==true || free==true || oa==true  || collection==true}">
			          <p class="p_right">	     
			          		<%-- <c:if test="${news==true }"><img src="${ctx }/images/n.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.New" sessionKey="lang" />"/></c:if> --%>
							<c:if test="${free==true }"><img src="${ctx }/images/ico/f.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Free" sessionKey="lang" />"/></c:if>
							<c:if test="${oa==true }"><img src="${ctx }/images/ico/o.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.OA" sessionKey="lang" />"/></c:if>
							<%-- <c:if test="${collection==true }"><img src="${ctx }/images/c.png" title="<ingenta-tag:LanguageTag key="Page.Frame.Left.Lable.Collection" sessionKey="lang" />"/></c:if> --%>     	  
			          </p>
			          </c:if>
		          </div>
				</c:if>	
				<table width="100%">
					<tr>
						<td width="75%" class="tdb">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" style="float:left;">
				              <tr>
				                <td valign="middle" class="tda" name="author">
									<c:if test="${p.publications.type!=2 }">
									<ingenta-tag:LanguageTag key="Page.Index.Index.Lable.Author" sessionKey="lang" />：&nbsp;
									</c:if>
									<c:if test="${p.publications.type==2 }">
									<ingenta-tag:LanguageTag key="Page.Index.Index.Lable.JournalInfo" sessionKey="lang" />：&nbsp;
									</c:if>
				                </td>
				                <td valign="middle" class="tdb">
									<c:if test="${p.publications.type!=2 }">${p.publications.authorAlias }<c:if test="${p.publications.type==4 }"> in <a href="${ctx}/pages/publications/form/article/${p.publications.publications.id}">${p.publications.publications.title}</a></c:if>(${fn:substring(p.publications.pubDate,0,4) })</c:if>
									<c:if test="${p.publications.type==2 }">Volume ${p.publications.startVolume }-Volume ${p.publications.endVolume }</c:if>
								</td>
				              </tr>
				              <tr>
				                <td valign="middle" class="tda"><ingenta-tag:LanguageTag key="Page.Index.Index.Lable.Publisher" sessionKey="lang" />：&nbsp;</td>
				                <td valign="middle" class="tdb" name="publisher">
				                	${p.publications.publisherName }
								</td>
				              </tr>
				              <c:if test="${p.publications.remark!=null && p.publications.remark!='' }">
				              <tr>
				                <td valign="top" class="tda" style="vertical-align:top;"><ingenta-tag:LanguageTag key="Pages.Index.Lable.Abstract" sessionKey="lang" />：&nbsp;</td>
				                <td valign="top" class="tdc" style="vertical-align:top;">
				                	<div style="height:20px;overflow: hidden;"><a style="cursor: pointer;" onclick="senfe(this);">+ <ingenta-tag:LanguageTag key="Page.Index.Search.Desc.Show" sessionKey="lang" /></a>
									<p>
									${p.publications.remark}
									</p>
									</div>							
				                </td>
				            </tr> 
				            </c:if>
				          </table>
						</td>
						<td class="tdb">
							<c:if test="${p.publications.cover!=null&&p.publications.cover!='' }">
							<a href="${ctx}/pages/publications/form/article/${p.publications.id}" title="${p.publications.title }">
								<img height="120" width="98"  style="float:right; margin-top:5px; margin-right:2px;" 
									src="${ctx}/pages/publications/form/cover?id=${p.publications.id}" onerror="this.src='${ctx}/images/smallimg.jpg'"/>
							</a>
						  </c:if>
						</td>
					</tr>
				</table>
		          
		          
		          <p style="height:1px; width:1px; clear:both;">&nbsp;</p>
		          <!--列表内容结束-->
		          </c:forEach>
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
			</div>
			<!--右侧列表内容结束-->
		</div>
		<!--以上中间内容块结束-->
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
