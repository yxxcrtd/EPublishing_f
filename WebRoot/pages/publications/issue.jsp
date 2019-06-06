<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- <script type="text/javascript">
//获取资源弹出层调用
function popTips2(downId) {
	art.dialog.open("${ctx}/pages/publications/form/getResource?pubid="+downId + "&downId=" + downId, {id : "getResourceId",title:"",top: 200,width: 340, height: 200,lock:true});
}
function addFavourites(pid) {
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
				$("#favourites_img_"+pid).attr("src","${ctx}/images/ico/ico13-blank.png");
				$("#favourites_"+pid).attr("class","ico ico_collection2");
				$("#favourites_"+pid).removeAttr("onclick");
				$("#favourites_"+pid).attr("title","<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
		//		$("#favourites_"+pid).html("<img id='favourites_${p.id }' src='${ctx }/images/ico/ico13-blank.png' class='vm' /><ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
				$("#favourites_"+pid).css("cursor","auto");								
				$("#favourites_check_"+pid).attr("checked",true);
			}else{
				art.dialog.tips(s[1],1,'error');
			}
		},
		error : function(data) {
			art.dialog.tips(data,1,'error');
		}
	});
}
function addToCart(pid, ki,type) {
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
				$("#cartCount").html("["+s[2]+"]");
				if(type=='1'){
					$("#add_"+pid).attr("class","ico ico_collection2");
					if($("#select_price_" + pid)!=null&&$("#select_price_" + pid)!=undefined ){
						$("#select_price_" + pid).css("display","none");
					}else{
						$("#price_" + pid).css("display","none");
					};
				}else{
					var pl = $("#options_ li").length;//价格长度
					//删除价格
					if(pl>1){//还有其他价格，隐藏现在购买的价格
						$("#price_"+pid+" option[value='"+price+"']").remove();
						//删除全部样式
						$("div[class='select_box']").remove();
						selects = document.getElementsByTagName('select');
						rSelects();
					}else{
						//没有价格了，隐藏购买按钮，隐藏价格
						$("#add_"+pid).css("display","none");
						$("#select_info_").css("display","none");
					}
					//$("#select_info_").css("display","none");
					//$("#selected_").css("display","none");
				}
			}else{
				$("#add_"+pid).attr("class","ico ico_collection2");
			}
		},
		error : function(data) {
			art.dialog.tips(data,1,'error');
		}
	});
}
</script> -->

		<%-- 		<c:set var="i" value="0"></c:set>
					<c:set var="objnews">${form.obj.latest!=null&&form.obj.latest>0 }</c:set>
					<c:set var="objoa">${form.obj.oa!=null&&form.obj.oa==2 }</c:set>
					<c:set var="objfree">${form.obj.free!=null&&form.obj.free==2 }</c:set>
					<c:set var="objcollection">${form.obj.inCollection!=null&&form.obj.inCollection>0 }</c:set>
					<c:set var="add1"
						value="${form.obj.priceList!=null&&fn:length(form.obj.priceList)>0&&form.obj.free!=2&&form.obj.oa!=2&&sessionScope.mainUser!=null && form.obj.subscribedUser<=0&&(form.obj.buyInDetail<=0&&form.obj.exLicense>=0)}" />
					<c:if test="${add1==false }">
						<c:set var="objadd" value="false" />
					</c:if>
					<c:if test="${add1==true &&form.obj.subscribedIp>0 }">
						<c:if
							test="${sessionScope.mainUser.institution.id==sessionScope.institution.id&&sessionScope.mainUser.level==2 }">
							<c:set var="objadd" value="false" />
						</c:if>
						<c:if
							test="${sessionScope.mainUser.institution.id==sessionScope.institution.id &&sessionScope.mainUser.level!=2 }">
							<c:set var="objadd" value="true" />
						</c:if>
						<c:if
							test="${sessionScope.mainUser.institution.id!=sessionScope.institution.id}">
							<c:set var="objadd" value="true" />
						</c:if>
					</c:if>
					<c:if
						test="${add1==true &&(form.obj.subscribedIp==null||form.obj.subscribedIp<=0) }">
						<c:set var="objadd" value="true" />
					</c:if>
					<c:if test="${add1==false }">
						<c:set var="objadd" value="false" />
					</c:if>
	
					<c:set var="objfavourite"
						value="${sessionScope.mainUser!=null&&form.obj.favorite<=0 }" />
					<c:set var="objrecommand"
						value="${(form.obj.recommand>0||sessionScope.mainUser.institution!=null) &&(form.obj.subscribedIp==null||form.obj.subscribedIp<=0)&&(form.obj.free!=2&&form.obj.oa!=2)}" />
					<c:set var="objlicense">${(form.obj.subscribedIp!=null||form.obj.subscribedUser!=null)&&(form.obj.subscribedIp>0||form.obj.subscribedUser>0) }</c:set>

				 --%>
				 
				 <script type="text/javascript">
				 //获取资源弹出层调用
				function popTips2(downId) {
					art.dialog.open("${ctx}/pages/publications/form/getResource?pubid="+downId + "&downId=" + downId, {id : "getResourceId",title:"",top: 200,width: 340, height: 200,lock:true});
				}
				 //在线阅读弹出层调用
	function viewPopTips1(id,page) {
		var url="";
		        
            //tmp.focus() ;
		if(page=='0'){
			url = "${ctx}/pages/view/form/view?id="+id;
		}else{
			url = "${ctx}/pages/view/form/view?id="+id+"&nextPage="+page;
		}
		//首先Ajax查询要阅读的路径
		if('${form.obj.free}'=='2'||'${form.obj.oa}'=='2'){
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
						//window.location.href="${ctx}/pages/view/form/view?id="+id+"&webUrl="+s[1];
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
				 function addToCart(pid, ki,type) {
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
								$("#cartCount").html("["+s[2]+"]");
								if(type=='1'){
									$("#add_"+pid).attr("class","grey");
									if($("#select_price_" + pid)!=null&&$("#select_price_" + pid)!=undefined ){
										$("#select_price_" + pid).css("display","none");
									}else{
										$("#price_" + pid).css("display","none");
									};
								}else{
									var pl = $("#options_ li").length;//价格长度
									//删除价格
									if(pl>1){//还有其他价格，隐藏现在购买的价格
										$("#price_"+pid+" option[value='"+price+"']").remove();
										//删除全部样式
										$("div[class='select_box']").remove();
										selects = document.getElementsByTagName('select');
										rSelects();
									}else{
										//没有价格了，隐藏购买按钮，隐藏价格
										$("#add_"+pid).css("display","none");
										$("#select_info_").css("display","none");
									}
									//$("#select_info_").css("display","none");
									//$("#selected_").css("display","none");
								}
							}else{
								$("#add_"+pid).attr("class","grey");
							}
						},
						error : function(data) {
							art.dialog.tips(data,1,'error');
						}
					});
				}
											$(".favourite").on("click", function() {
												var This = $(this);
												This.each(function() {
													$.get("${ctx}/pages/favourites/form/commit", { pubId : This.attr("id") }, function(data) {
														if ("success" == data) {
															This.find("a").attr("class", "blank");
															This.find("img").attr("src", "${ctx}/images/favourite.png");
															This.find("span").html("<ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' />");
															art.dialog.tips('<ingenta-tag:LanguageTag key='Controller.Favourites.commit.success' sessionKey='lang' />', 1, 'success');
														} else if ("del" == data) {
															This.find("a").attr("class", "");
															This.find("img").attr("src", "${ctx}/images/unfavourite.png");
															This.find("span").html("<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' />");
															art.dialog.tips('<ingenta-tag:LanguageTag key='Controller.Favourites.commit.cancel' sessionKey='lang' />', 1, '');
														}
													});
												});
											});
										</script>
<c:if test="${issueList!=null}">
		<c:forEach items="${issueList}" var="p" varStatus="index">
				   <div class="fl w330 mt2">
				  				 <a href="${ctx}/pages/publications/form/journaldetail/${id}?issueId=${p.id}">
				  				 	<c:if test="${p.cover!=null&&p.cover!='' }">
									<a href="${ctx}/pages/publications/form/article/${p.id}">
										<img src="${ctx}/pages/publications/form/cover?t=1&id=${p.id}" width="95" height="129" class="fl mr20" />
									</a>
								</c:if>
								<c:if test="${p.cover==null||p.cover=='' }">
									<a href="${ctx}/pages/publications/form/article/${p.id}">
										<div class="imgMoren">
										<p>${p.title}</p>
										</div>
									</a>
								</c:if>
                            	<%-- <img src="${ctx}/pages/publications/form/cover?id=${p.id}" width="95" height="129" onerror="this.src='${ctx}/images/noimg.jpg'" class="fl mr20" /> --%>
                            	</a>
                                <h1 class="newOmit"><a href="${ctx}/pages/publications/form/journaldetail/${id}?issueId=${p.id}">${p.title}</a></h1>
                                &nbsp;&nbsp;<span class="blue"><ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" />${p.volumeCode }
                                	<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Issue" sessionKey="lang" />${p.issueCode },
                                				(${p.year }-${p.month })
                                </span>
                                			
                              <%--   <p class="mt20"><a href="#"><img src="${ctx }/images/ico/ico15-blue.png" class="vm" /> 获取资源</a></p>
                                <p><a href="#"><img src="${ctx }/images/ico/ico13-blue.png" class="vm" /> 收藏</a></p>
                                <p><a href="#"><img src="${ctx }/images/ico/ico14-blank.png" class="vm" /> 添加到购物车</a></p>
                                 --%>
                                 
                                		<p>
											    <c:set var="license" value="${p.subscribedIp>0||p.subscribedUser>0||p.free==2||p.oa==2 }"/>
												<%--   <c:if test="${objlicense||objadd||objfavourite||objrecommand||objoa==true||objfree==true}"> --%>
												    
												    <c:if test="${license==false }">
														<span>
														<a href="javascript:void(0)" class="blue" onclick="popTips2('${p.id}');"><img src="${ctx}/images/ico/ico15-blue.png" class="vm" />
														<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
														</a>
														</span>
													</c:if>
													<c:if test="${license==true}">
													<span>
														<%-- <a href="javascript:void(0)" class="green"><img src="${ctx}/images/ico/ico15-green.png" class="vm" /> --%>
														<a href="javascript:void(0)" class="green" onclick="popTips2('${p.id}');"><img src="${ctx}/images/ico/ico15-green.png" class="vm" />
														<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
														</a>
														</span>
														</c:if>
												    <%-- <c:if test="${license==false }">
													<span>
													<a href="javascript:void(0)" id="resource_div"  onclick="viewPopTips1('${p.id}','0');">
													<img src="${ctx }/images/ico/ico15-green.png" class="vm" />
													<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
													</a>
													</span>
												 </c:if>
												 <c:if test="${license==true }"> 
												  <span>
													   <a href="javascript:void(0)" id="resource_div"  onclick="viewPopTips1('${p.id}','0');">
													   <img src="${ctx }/images/ico/ico15-blue.png" class="vm" />
													<ingenta-tag:LanguageTag key="Page.Publications.DetailList.Resource" sessionKey="lang" />
													</a>
													 </span>
													 </c:if> --%>
												 <%--  </c:if> --%>
												  
                                		</p>
                                		
                                		<p>		
										<%-- <c:if test="${objfavourite }">
										   	<a href="javascript:void(0)" id="favourites_${p.id}"  title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' />" onclick="addFavourites('${p.id}')">
										   	<img src="${ctx }/images/ico/ico13-blue.png" class="vm" />
													<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' />
											</a>
										</c:if>
										<c:if test="${sessionScope.mainUser!=null && !objfavourite }">		
											<span><a href="javascript:void(0)" id="favourites_${p.id}">
											<img src="${ctx }/images/ico/ico13-blank.png" class="vm" />
											<ingenta-tag:LanguageTag key="Page.Index.Search.Button.Favourite" sessionKey="lang" /></a></span>
											</a>
										</c:if> --%>
										
										<c:if test="${sessionScope.mainUser!=null && p.favorite<=0}">
			                            	<span class="favourite" id="${p.id}">
			                            		<a href="javascript:;" class="ico_link">
			                            			<img src="${ctx}/images/unfavourite.png" class="vm" /><span><ingenta-tag:LanguageTag key='Page.Index.Search.Link.Favorite' sessionKey='lang' /></span>
												</a>
											</span>
										</c:if>
										<c:if test="${sessionScope.mainUser!=null && p.favorite>0}">
											<span class="favourite" id="${p.id}">
												<a href="javascript:;" class="blank">
													<img src="${ctx}/images/favourite.png" class="vm" /><span><ingenta-tag:LanguageTag key='Page.Index.Search.Link.collected' sessionKey='lang' /></span>
												</a>
											</span>
										</c:if>
									</p>
                                		
                                		<p>
                                		<c:set var="add1"
											value="${p.priceList!=null&&fn:length(p.priceList)>0&&p.free!=2&&p.oa!=2&&sessionScope.mainUser!=null && p.subscribedUser<=0&&(p.buyInDetail<=0&&p.exLicense>=0)}" />
										<c:if test="${add1==false }">
											<c:set var="objadd" value="false" />
										</c:if>
										<c:if test="${add1==true }">
											<c:set var="objadd" value="true" />
										</c:if>
									     <c:if test="${objadd }">
											<a href="javascript:void(0)" id="add_${p.id}"  title="<ingenta-tag:LanguageTag key='Page.Publications.Lable.Buy' sessionKey='lang' />" onclick="addToCart('${p.id}',1,'1')">
												<img src="${ctx }/images/ico/ico14-blank.png" class="vm" />
								        	  	<ingenta-tag:LanguageTag key='Page.Publications.Lable.Buy' sessionKey='lang' />
								          	</a>
										</c:if>
									 	 <c:if test="${sessionScope.mainUser!=null && !objadd }">
												<span><a class="grey" href="javascript:void(0)" id="add_${p.id}">
												<img src="${ctx }/images/ico/ico14-blank.png" class="vm" />
												<ingenta-tag:LanguageTag key="Page.Publications.Lable.Buy" sessionKey="lang" /></a></span>
										</c:if> 
											</p>
											
									<p>	
										<c:if test="${objrecommand}">
											<a href="javascript:void(0)" id="recommand_${p.id}" class="ico ico_recommed" title="<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' />" onclick="popTips('${p.id}')">
													<ingenta-tag:LanguageTag key='Page.Index.Search.Link.Recommend' sessionKey='lang' />
											</a>
										</c:if>
				                    </p>
				                    
                            </div>
           </c:forEach>
           <jsp:include page="../pageTag/pageTag.jsp">
		        	<jsp:param value="${form }" name="form"/>
                </jsp:include>
</c:if>



							