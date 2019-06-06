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
		function queryList() {
			//document.getElementById("form").action="";
			//document.getElementById("page").value = "0";
			document.getElementById("form").submit();
		}

		function addToCart(pid, ki, rid) {
			var price = $("#price_" + pid).val();
			$
					.ajax({
						type : "POST",
						url : "${ctx}/pages/cart/form/add",
						data : {
							pubId : pid,
							priceId : price,
							kind : ki,
							recId : rid,
							r_ : new Date().getTime()
						},
						success : function(data) {
							var s = data.split(":");
							if (s[0] == "success") {
								art.dialog.tips(s[1], 1);//location.reload();
								$("#add_" + rid).css("display", "none");
								$("#cartCount").html("[" + s[2] + "]");
								$("#price_" + rid).css("display", "none");
								$("#suspend_" + rid).css("display", "none");
								$("#status_" + rid)
										.html(
												"<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.MyRecommend.Label.isOrder3'/>");
							} else {
								art.dialog.tips(s[1], 1, 'error');
							}
						},
						error : function(data) {
							art.dialog.tips(data, 1, 'error');
						}
					});
		}

		function popSuspend(rid) {
			art.dialog.open(
							"${ctx}/pages/user/particulars.jsp?rid=" + rid,
							{
								title : "<ingenta-tag:LanguageTag key="Page.Pop.Title.Recommend.particulars" sessionKey="lang"/>",
								top : 100,
								width : 700,
								height : 200,
								lock : true
							});
		}

		function showMessage(title, data) {
			art.dialog({
				title : title,
				content : data.replace("[dyh]", "'").replace("[syh]", '"'),
				lock : true,
				width : 500
			});
		}
		/* 
		function loadFunction(){
			$.ajax({
				type : "POST",
				url : "${ctx}/pages/user/form/myrecommend/",
				data:{
					type: 1
				},
				success : function(response, stutas, xhr) {
					$("#cnPubTable1").html(response);
				}
			});
		} */
		
		function initFunction(){
		//	reset();
		//	$("#type").val(1);
		//	$("#isCn").val(true);
			$("#sort").val(0);
			
		}
		
		function reset(){
//			$("#isOrder").attr("checked","checked");
//			$("a[name='radio']").attr("class","borderA mr5");
//			$("#borderA").attr("class","borderA mr5 borderACur");
			$("#isOrder").val("");
			$("#sort").val(0);
			$("#pubTitle").attr("value","");
			$("#pubCode").attr("value","");
		}
		$(function(){
			//loadFunction();
			/* $("input[name='cnIsOrder']").click(function(){
				var v=$(this).val();
				$("#cnOrder").val(v);
			});
			$("input[name='cnSort']").click(function(){
				var v=$(this).val();
				$("#snSortHiden").val(v);
			});
			$("#cnQuery").click(function(){
				var title =$("#cnPubTitle").val();
				var code =$("#cnPubCode").val();
				var status=$("#cnOrder").val();
				var sort=$("#snSortHiden").val();				
				$.ajax({
					type : "POST",
					data : {
						pubTitle : title,
						pubCode : code,
						isOrder : status,
						sort : sort,
						isCn : 'true'
					},
					url : "${ctx}/pages/user/form/myrecommend/",					
					success : function(response, stutas, xhr) {
						$("#cnPubTable1").html(response);
					},
					error:function(){
						alert("刷新失败");
					}
				});
			}); */
			
			//initFunction();
			
			$("#but1").click(function(){
				reset();
				$("#type").val(1);
				$("#isCn").val(true);
				queryList();
			});
			$("#but2").click(function(){
				reset();
				$("#type").val(1);
				$("#isCn").val(false);
				queryList();
			});
			
	 		
			$("#but3").click(function(){
				reset();
				$("#type").val(2);
				$("#isCn").val(true);
				queryList();
			}); 
			
			$("#but4").click(function(){
				reset();
				$("#type").val(2);
				$("#isCn").val(false);
				queryList();
			});
			
			$("#but5").click(function(){
				reset();
				$("#type").val(4);
				$("#isCn").attr("value","");
				queryList();
			});
		
		});
		
		function selectStats(type){
			$("a[name='radio']").attr("class","borderA mr5");
			$("#borderA"+type).attr("class","borderA mr5 borderACur");
			$("#isOrder").val(type);
		}
		
		function selectSort(type){
			$("a[name='selectSort']").attr("class","borderA mr5");
			$("#sortborderA"+type).attr("class","borderA mr5 borderACur");
			$("#sort").val(type);
		}
		
		function dividePage(targetPage){
			if(targetPage<0){return;}
			document.getElementById("form").action="${ctx}/pages/user/form/myrecommend?pageCount="+${form.pageCount}+"&curpage="+targetPage;
			document.getElementById("form").submit();
		}
		
		function GO(obj){
			document.getElementById("form").action="${ctx}/pages/user/form/myrecommend?pageCount="+$(obj).val();
			document.getElementById("form").submit();
		}
	</script>
</head>
	<body>
	<jsp:include page="/pages/header/headerData" flush="true" />
	<div class="main personMain">
    	<jsp:include page="/pages/menu?mid=recommended&type=2" flush="true" />
		<!--定义 0101 头部边框-->
		<!--定义 0102 左边内容区域 开始-->
			<div class="perRight">
			
				<div class="StabedPanels" >
				<ul class="oh">
                <li class="Stab<c:if test="${tabIndex==1 }"> StabSeleted</c:if> Ptab" id="but1"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Publications.Collection.Chinese"/></li>
                <li class="Stab<c:if test="${tabIndex==2 }"> StabSeleted</c:if> Ptab" id="but2"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Publications.Collection.Forgin"/></li>
                <%-- <li class="Stab<c:if test="${tabIndex==3 }"> StabSeleted</c:if> Ptab" id="but3"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Cart.Type.journalCn"/></li> --%>
                 <li class="Stab<c:if test="${tabIndex==4 }"> StabSeleted</c:if> Ptab" id="but4"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Cart.Type.journalEn"/></li>
                <li class="Stab<c:if test="${tabIndex==5 }"> StabSeleted</c:if> lastLi Ptab" id="but5"><ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Article" sessionKey="lang" /></li>
            	</ul>
				<div class="StabContent ScontentSelected">
				<form:form action="myrecommend" method="post" commandName="form" id="form" >
					<input type="hidden" id="type" name="type" value="${form.type}"/>
					<input type="hidden" id="isCn" name="isCn" value="${isCn}"/>
					<div class="borderDiv noTop" id="tab1">
					  <p class="blockP">
			          	<span class="w100 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyRecommend.Label.pubName"/>：</span>
				        <span class="w200"><form:input path="pubTitle" type="text" id="pubTitle" value=""/></span>	
			        	<span class="w100 tr">
			        		<c:if test="${tabIndex==3 || tabIndex==4}">ISSN</c:if>
			        		<c:if test="${tabIndex==1 || tabIndex==2}">
			        			<ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyRecommend.Label.isbn"/>
			        		</c:if>：</span>
				        <span class="w200"><form:input path="pubCode" type="text" id="pubCode" value=""/></span>			        	
			        	
			          </p>
			          
			        <%--   			    
			       	  <p class="mb10 bookmarkP">
			       	   <!-- <p class="blockP"> -->
				        <span class="w100 tr" style="display: block;float: left;margin-right: 3px;"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyRecommend.Label.status"/>：</span>
				        <span><form:radiobutton path="isOrder" id="isOrder" name="isOrder" checked="checked" cssStyle="vertical-align: middle;margin-top: -4px;" value="" /><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Page.Select.All"/></span>
						<span><form:radiobutton path="isOrder" name="isOrder" cssStyle="vertical-align: middle;margin-top: -4px;" value="1" /><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyRecommend.Label.isOrder1"/></span>
						<span><form:radiobutton path="isOrder" name="isOrder" cssStyle="vertical-align: middle;margin-top: -4px;" value="2"/><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyRecommend.Label.isOrder3"/></span>
						<span><form:radiobutton path="isOrder" name="isOrder" cssStyle="vertical-align: middle;margin-top: -4px;" value="3"/><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyRecommend.Label.isOrder4"/></span>
						<span><form:radiobutton path="isOrder" name="isOrder" cssStyle="vertical-align: middle;margin-top: -4px;" value="4"/><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyRecommend.Label.isOrder2"/></span>
						<input type="hidden" id="cnOrder" value="" />
			          </p>	 --%>
			          
			          <p class="blockP">
                        <span class="w100 tr"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyRecommend.Label.status"/>：</span>
                        <span><a href="javascript:void(0)" class="borderA mr5<c:if test="${form.isOrder==null || form.isOrder==''}"> borderACur</c:if>" name="radio" id="borderA" onclick="selectStats('');"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Page.Select.All"/></a></span>
                        <span><a href="javascript:void(0)" class="borderA mr5<c:if test="${form.isOrder!=null && form.isOrder=='1'}"> borderACur</c:if>" name="radio" id="borderA1" onclick="selectStats('1');"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyRecommend.Label.isOrder1"/></a></span>
                        <span><a href="javascript:void(0)" class="borderA mr5<c:if test="${form.isOrder!=null && form.isOrder=='2'}"> borderACur</c:if>" name="radio" id="borderA2" onclick="selectStats('2');"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyRecommend.Label.isOrder3"/></a></span> 
                        <span><a href="javascript:void(0)" class="borderA mr5<c:if test="${form.isOrder!=null && form.isOrder=='3'}"> borderACur</c:if>" name="radio" id="borderA3" onclick="selectStats('3');"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyRecommend.Label.isOrder4"/></a></span> 
                        <span><a href="javascript:void(0)" class="borderA mr5<c:if test="${form.isOrder!=null && form.isOrder=='4'}"> borderACur</c:if>" name="radio" id="borderA4" onclick="selectStats('4');"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyRecommend.Label.isOrder2"/></a></span> 
                        <input type="hidden" id="isOrder" name="isOrder" value="${form.isOrder }"/>
                     </p>
			          
			          <p class="pl30 mb5">
			          	<a class="orgingB" href="javascript:void(0)" onclick="queryList();"><ingenta-tag:LanguageTag sessionKey='lang' key='Global.Button.Search'/></a>
			          </p>	
			          <c:if test="${form.level==2||form.level==4}">
			        <%--   <p class="mb10 bookmarkP">
			        	<span class="w100 tr" style="display: block;float: left;margin-right: 3px;"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Recommend.Lable.Sort"/>：</span>
				        <span><input type="radio" id="sort" name="sort" class="mv" value="0" checked="checked" style="vertical-align: middle;margin-top: -4px;" <c:if test="${form.sort==0}"> checked="checked"</c:if>/><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Recommend.Sort.Option.Professional"/></span>
				        <span><input type="radio" name="sort" class="mv" value="1" style="vertical-align: middle;margin-top: -4px;" <c:if test="${form.sort==1}"> checked="checked"</c:if>/><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Recommend.Sort.Option.All"/></span>
			        	<input type="hidden" id="snSortHiden" value="0"/>
			        	</p> --%>
			        	
			        	<p class="blockP">
                        <span class="w100 tr"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.Recommend.Lable.Sort'/>：</span>
                        <span><a href="javascript:void(0)" class="borderA mr5<c:if test="${form.sort==null||form.sort==0}"> borderACur</c:if>" name="selectSort" id="sortborderA0" onclick="selectSort(0)"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.Recommend.Sort.Option.Professional'/></a></span>
                        <span><a href="javascript:void(0)" class="borderA mr5<c:if test="${form.sort!=null&&form.sort==1}"> borderACur</c:if>" name="selectSort" id="sortborderA1" onclick="selectSort(1)"><ingenta-tag:LanguageTag sessionKey='lang' key='Pages.Recommend.Sort.Option.All'/></a></span> 
                        <input type="hidden" id="sort" name="sort" value="${form.sort }"/>
                    	</p>
			        	
			        	</c:if>
			        	</div>
			        	<div id="cnPubTable1">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="cartTable">
							<tr class="trTop">
								<c:if test="${form.level!=2}">
									<td width="120" align="center"><ingenta-tag:LanguageTag	sessionKey="lang" key="Pages.Recommend.Table.Lable.InsName" /></td>
								</c:if>
								<td width="230" align="center"><ingenta-tag:LanguageTag	sessionKey="lang" key="Pages.Recommend.Table.Lable.Name" /></td>
								<td width="120" align="center">ISBN/ISSN</td>
								<td width="80" align="center"><ingenta-tag:LanguageTag	sessionKey="lang" key="Pages.Recommend.Table.Lable.Type" /></td>
								<c:if test="${form.level==1 || form.level==5}">
									<td width="100" align="center"><ingenta-tag:LanguageTag	sessionKey="lang" key="Pages.Recommend.Table.Lable.Date" /></td>
								</c:if>
								<c:if test="${form.level==2 ||form.level==4}">
									<td width="80" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Recommend.Table.Lable.RCount" /></td>
								</c:if>
								<td width="80" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Recommend.Table.Lable.IsOrder" /></td>
								<c:if test="${form.level==2}">
									<td width="80" align="center"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Recommend.Table.Price" /></td>
								</c:if>
								<c:if test="${form.level==2}">
									<td align="center"><ingenta-tag:LanguageTag	sessionKey="lang" key="Global.Prompt.Operating" /></td>
								</c:if>
							</tr>
							<c:if test="${form.level==1 || form.level==5}">
								<c:forEach items="${list }" var="r" varStatus="index">
									<c:set var="cssWord" value="${index.index%2==0?'a':'b' }" />
									<tr class="trBody">
										<td align="center">${r.recommend.institution.name}</td>
										<td align="left"><span style="width:270px">${r.recommend.publications.title}<c:if test="${r.recommend.publications.type==1||r.recommend.publications.type==4}"><br />By ${r.recommend.publications.author}</c:if><br />${r.recommend.publications.publisher.name}</span></td>
										<td align="center">${r.recommend.publications.code}</td>
										<td align="center"><c:if test="${r.recommend.publications.type==1}">
												<ingenta-tag:LanguageTag sessionKey="lang"
													key="Page.Index.Search.Lable.Finded4.Option1" />
											</c:if> <c:if test="${r.recommend.publications.type==2}">
												<ingenta-tag:LanguageTag sessionKey="lang"
													key="Page.Index.Search.Lable.Finded4.Option2" />
											</c:if> <c:if test="${r.recommend.publications.type==3}">
												<ingenta-tag:LanguageTag sessionKey="lang"
													key="Page.Index.Search.Lable.Finded4.Option3" />
											</c:if> <c:if test="${r.recommend.publications.type==4}">
												<ingenta-tag:LanguageTag sessionKey="lang"
													key="Page.Index.Search.Lable.Finded4.Option4" />
											</c:if> <c:if test="${r.recommend.publications.type==5}">
												<ingenta-tag:LanguageTag sessionKey="lang"
													key="Page.Index.Search.Lable.Finded4.Option5" />
											</c:if> <c:if test="${r.recommend.publications.type==6}">
												<ingenta-tag:LanguageTag sessionKey="lang"
													key="Page.Index.Search.Lable.Finded4.Option6" />
											</c:if> <c:if test="${r.recommend.publications.type==7}">
												<ingenta-tag:LanguageTag sessionKey="lang"
													key="Page.Index.Search.Lable.Finded4.Option7" />
											</c:if></td>

										<td align="center"><fmt:formatDate value="${r.createdon}"
												pattern="yyyy-MM-dd" /></td>
										<td align="center"><c:if test="${r.recommend.isOrder==3}">
												<ingenta-tag:LanguageTag sessionKey="lang"
													key="Pages.User.MyRecommend.Label.isOrder4" />
										
					        				&nbsp;
					        				<c:if
													test="${r.particulars!=null && fn:trim(r.particulars)!='' }">
													<a
														title="<ingenta-tag:LanguageTag sessionKey="lang" key="Order.Button.Suspend.particulars"/>"
														onclick="showMessage('${r.recommend.institution.name}','${fn:replace(fn:replace(fn:trim(r.particulars),"\'","[dyh]"),"\"","[syh]")}')"><img
														src="${ctx}/images/msg.png" /></a>
												</c:if>
												<c:if
													test="${r.particulars==null && fn:trim(r.particulars)=='' }">
								                     &nbsp;
								             </c:if>

											</c:if> <c:if test="${r.recommend.isOrder!=3}">
												<c:if
													test="${r.recommend.isOrdered==0 && r.recommend.isOrdering == 0}">
													<ingenta-tag:LanguageTag sessionKey="lang"
														key="Pages.User.MyRecommend.Label.isOrder1" />
												</c:if>
												<c:if
													test="${r.recommend.isOrdered==0 && r.recommend.isOrdering >0 }">
													<ingenta-tag:LanguageTag sessionKey="lang"
														key="Pages.User.MyRecommend.Label.isOrder3" />
												</c:if>
												<c:if test="${r.recommend.isOrdered>0}">
													<ingenta-tag:LanguageTag sessionKey="lang"
														key="Pages.User.MyRecommend.Label.isOrder2" />
												</c:if>
											</c:if></td>
									</tr>
								</c:forEach>
							</c:if>

							<c:if test="${form.level==2 || form.level==4}">
								<c:forEach items="${list }" var="r" varStatus="index">
									<c:set var="cssWord" value="${index.index%2==0?'a':'b' }" />
									<tr class="trBody">
										<c:if test="${form.level!=2}">
											<td align="center">${r.institution.name}</td>
										</c:if>
										<td align="left"><span style="width:270px"><a title="${index.index}${r.publications.title}" href="${ctx}/pages/publications/form/article/${r.publications.id}"> ${fn:replace(fn:replace(fn:replace(r.publications.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}</a>

<c:if test="${r.publications.type==1||r.publications.type==4}"><br />By ${r.publications.author}</c:if><br />${r.publications.publisher.name}</span></td>
                                        <c:if test="${r.publications.type==1||r.publications.type==4 ||r.publications.type==2}">
										<td align="center">${fn:split(r.publications.code,'|')[0]}</td>
										</c:if>
										 <c:if test="${r.publications.type==7}">
										<td align="center">${r.publications.code}</td>
										</c:if>
										
										<td align="center"><c:if test="${r.publications.type==1}">
												<ingenta-tag:LanguageTag sessionKey="lang"
													key="Page.Index.Search.Lable.Finded4.Option1" />
											</c:if> <c:if test="${r.publications.type==2}">
												<ingenta-tag:LanguageTag sessionKey="lang"
													key="Page.Index.Search.Lable.Finded4.Option2" />
											</c:if> <c:if test="${r.publications.type==3}">
												<ingenta-tag:LanguageTag sessionKey="lang"
													key="Page.Index.Search.Lable.Finded4.Option3" />
											</c:if> <c:if test="${r.publications.type==4}">
												<ingenta-tag:LanguageTag sessionKey="lang"
													key="Page.Index.Search.Lable.Finded4.Option4" />
											</c:if> <c:if test="${r.publications.type==5}">
												<ingenta-tag:LanguageTag sessionKey="lang"
													key="Page.Index.Search.Lable.Finded4.Option5" />
											</c:if> <c:if test="${r.publications.type==6}">
												<ingenta-tag:LanguageTag sessionKey="lang"
													key="Page.Index.Search.Lable.Finded4.Option6" />
											</c:if> <c:if test="${r.publications.type==7}">
												<ingenta-tag:LanguageTag sessionKey="lang"
													key="Page.Index.Search.Lable.Finded4.Option7" />
											</c:if></td>
										<td align="center"><a
											href="${ctx}/pages/user/form/myrecommend/detail/${r.id}">${r.proCount}
												/ ${r.recommendDetailCount}</a></td>
										<td align="center" id="status_${r.id}"><c:if
												test="${r.isOrder==3}">
												<ingenta-tag:LanguageTag sessionKey="lang"
													key="Pages.User.MyRecommend.Label.isOrder4" />
											</c:if> <c:if test="${r.isOrder!=3}">
												<c:if test="${r.isOrdered==0 && r.isOrdering == 0}">
													<ingenta-tag:LanguageTag sessionKey="lang"
														key="Pages.User.MyRecommend.Label.isOrder1" />
												</c:if>
												<c:if test="${r.isOrdered==0 && r.isOrdering >0 }">
													<ingenta-tag:LanguageTag sessionKey="lang"
														key="Pages.User.MyRecommend.Label.isOrder3" />
												</c:if>
												<c:if test="${r.isOrdered>0}">
													<ingenta-tag:LanguageTag sessionKey="lang"
														key="Pages.User.MyRecommend.Label.isOrder2" />
												</c:if>
											</c:if></td>
										<c:if test="${form.level==2}">
											<td align="center"><c:if
													test="${r.publications.status==2}">
													<c:if
														test="${form.level==2 && r.isOrdering==0 && r.isOrdered==0 && r.isOrder!=3}">
														<c:if
															test="${r.publications.priceList!=null&&fn:length(r.publications.priceList)>0 }">
															<select id="price_${r.id}" style="width:80px">
																<c:forEach items="${r.publications.priceList }" var="pr"
																	varStatus="indexPr">
																	<option value="${pr.id }"><c:if
																			test="${pr.type==2 }">L</c:if><c:if
																			test="${pr.type==1 }">P</c:if>${pr.complicating}-${pr.price }${pr.currency }</option>
																</c:forEach>
															</select>
														</c:if>
										${r.lastDate}
										<c:if
															test="${r.publications.priceList==null||fn:length(r.publications.priceList)<=0 }">
															<!--  <ingenta-tag:LanguageTag key="Page.Index.Search.Lable.NoPrice" sessionKey="lang" /> -->
											
										</c:if>
													</c:if>
													<c:if
														test="${form.level!=2 || r.isOrdering>0 || r.isOrdered>0 || r.isOrder==3}">
								        	&nbsp;
								        </c:if>
												</c:if> <c:if test="${r.publications.status==1}">已下架</c:if></td>
										</c:if>
										<c:if test="${form.level==2}">
											<td align="center"><c:if
													test="${form.level==2 && r.isOrdering==0 && r.isOrdered==0 && r.isOrder!=3}">
													<c:if
														test="${fn:length(r.publications.priceList)>0 && r.publications.status==2}">
														<img style="cursor: pointer;" id="add_${r.id}"
															<%-- src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/images/tab_cary.png" --%>
															src="/images/tab_cary.png"
															title="<ingenta-tag:LanguageTag key='Global.Button.AddtoCart' sessionKey='lang' />"
															onclick="addToCart('${r.publications.id}',1,'${r.id}')" />
													</c:if>
						        			&nbsp;
								        	<img style="cursor: pointer;" id="suspend_${r.id}"
								        				<%-- src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/images/tab_o.png" --%>
														src="/images/tab_o.png"
														title="<ingenta-tag:LanguageTag key='Order.Button.Suspend' sessionKey='lang' />"
														onclick="popSuspend('${r.id}')" />
												</c:if> <c:if
													test="${form.level!=2 || r.isOrdering>0 || r.isOrdered>0 || r.isOrder==3}">
								        	&nbsp;
					        				<c:if
														test="${r.particulars!=null && fn:trim(r.particulars)!='' }">
														<a
															title="<ingenta-tag:LanguageTag sessionKey="lang" key="Order.Button.Suspend.particulars"/>"
															onclick="showMessage('${r.publications.title}','${fn:replace(fn:replace(fn:trim(r.particulars),"\'","[dyh]"),"\"","[syh]")}')"><img
															src="${ctx}/images/msg.png" /></a>
													</c:if>
													<c:if
														test="${r.particulars==null && fn:trim(r.particulars)=='' }">
								                     &nbsp;
								             </c:if>
												</c:if></td>
										</c:if>
									</tr>
								</c:forEach>
							</c:if>
							
								<%-- <tr style="border-left-style: hidden;border-right-style: hidden;border-bottom-style: hidden;">
								<td colspan="7" class="f_tda"><ingenta-tag-v3:SplitTag
										first_ico="${ctx }/images/ico_left1.gif"
										last_ico="${ctx }/images/ico_right1.gif"
										prev_ico="${ctx }/images/ico_left.gif"
										next_ico="${ctx }/images/ico_right.gif" method="post"
										formName="form" pageCount="${form.pageCount}"
										count="${form.count}" page="${form.curpage}" url="${form.url}"
										i18n="${sessionScope.lang}" /></td>
							</tr> --%>
							
						</table>
						<!-- <div class="pagelink"> 
							
						</div> -->
					</div>
					</form:form>
					<jsp:include page="../pageTag/pageTag.jsp">
			          <jsp:param value="${form }" name="form"/>
	                </jsp:include>
	        	</div>
	        	
	        </div>
					
					
				</div>
				<!--定义 0102 左边内容区域 结束-->
				<!--定义 0103 右边内容区域 开始-->
				
				<!--定义 0103 右边内容区域 结束-->
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
		<!--定义01 mainContainer 内容区结束-->
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>
