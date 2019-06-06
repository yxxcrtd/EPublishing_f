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
		$(document).ready(
		function(){
			$("#download").click(function(){
				var startMonth = $("#startMonth").val();		
				var endMonth = $("#endMonth").val();
			//	alert('${form.type}'+"--END--"+endMonth+"-->"+parseInt(startMonth,10)+"-->"+parseInt(endMonth,10));
				if(parseInt(startMonth,10)>parseInt(endMonth,10)){
					art.dialog.tips('<ingenta-tag:LanguageTag sessionKey="lang" key="Page.User.accesslog.Prompt.month" />',1,'error');
				}else{
					var url = "";
					var type = '${form.type}';
					if(type=='1'){
						url = "${ctx}/pages/user/form/downloadLog";
					}else if(type=='2'){
						url = "${ctx}/pages/user/form/downloadLogPage";
					}else if(type=='3'){
						url = "${ctx}/pages/user/form/downloadLogSearch";
					}
					<c:if test="${pubType!=null}">
					if(type!=3){
						url+="?pubtype=${pubType}&year="+$("#year").val()+"&startMonth="+startMonth+"&endMonth="+endMonth+"&r_="+new Date().getTime();
					}
					</c:if>
					<c:if test="${pubType==null}">
					url += "?year="+$("#year").val()+"&startMonth="+startMonth+"&endMonth="+endMonth+"&r_="+new Date().getTime();
					</c:if>
					window.location.href=url;
				}
			});	
			
			$("#query").click(
			function(){
				var startMonth = $("#startMonth").val();		
				var endMonth = $("#endMonth").val();
				if(parseInt(startMonth,10)>parseInt(endMonth,10)){
					art.dialog.tips('<ingenta-tag:LanguageTag sessionKey="lang" key="Page.User.accesslog.Prompt.month" />',1,'error');
				}else{
					var url = "";
					var type = '${form.type}';				
					url += "?type=" + type + "&year="+$("#year").val()+"&startMonth="+startMonth+"&endMonth="+endMonth+"&r_="+new Date().getTime();
					window.location.href=url;
				}
			});	
			
			$("td").mousemove(function (e) {
					if($(this).find("div.pp").length==1){
						var obj=$(this);
			            var sT =obj.offset().top;
			            var sL = obj.offset().left + obj.width() + 20;
			            
			            $(this).find("div.pp").css({left:sL,top:sT});
						$(this).find("div.pp").show();	            
		            }
		    });
			$("td").mouseout(function (e) {
					if($(this).find("div.pp").length==1){
					$(this).find("div.pp").hide();		
					}
			});		
		});
		</script>
	</head>
	<body>
	<div class="big">
		<!--以下top state -->
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--以上top end -->
		<form:form action="" commandName="form" id="form" name="form">
		<!--以下中间内容块开始-->
		<div class="main">
		    <div class="tablepage">
		    <h1>
				<c:set var="ss"><%out.print(request.getParameter("type")); %></c:set>
				<c:set var="pubtype"><%out.print(request.getParameter("pubtype")); %></c:set>
		        <c:if test="${ss=='1' }">
		        <c:if test="${pubtype=='1'}">
		        <ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Instaccount.Label.accessCount1'/>
		        </c:if>
		        <c:if test="${pubtype=='2'}">
		        <ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Instaccount.Label.accessCount2'/>
		        </c:if>
		        </c:if>	
		        <c:if test="${ss=='2' }">
		         <c:if test="${pubtype=='1'}">
		        	<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Instaccount.Label.viewCount1'/>
		         </c:if>
		         <c:if test="${pubtype=='2'}">
		        	<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Instaccount.Label.viewCount2'/>
		         </c:if>
		         </c:if>
		        <c:if test="${ss=='3' }">
		        	<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.User.Instaccount.Label.searchCount'/>
		        </c:if>
			</h1>
		    	<div class="t_list">
		       	  <p>
		          <span>
		          	<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.Access.Select.Lable.Year'/>：&nbsp;
					<select id="year" style="width:100px;vertical-align: middle; *margin-top: 8px;">				
							<c:forEach items="${form.yearList}" var="years">
								<option <c:if test="${years==year}">selected</c:if> value="${years }">${years}</option>
							</c:forEach>
					</select> 
					<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.Access.Select.Lable.StartMonth'/>：&nbsp;
					<select id="startMonth" style="width:100px;vertical-align: middle; *margin-top: 8px;">				
							<c:forEach var="sMonth" items="${form.monthList}">
								<option <c:if test="${sMonth==startMonth}">selected</c:if> value="${sMonth }">${sMonth}</option>
							</c:forEach>
					</select> 
					<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.Access.Select.Lable.EndMonth'/>：&nbsp;
					<select id="endMonth" style="width:100px;vertical-align: middle; *margin-top: 8px;">				
							<c:forEach var="eMonth"  items="${form.monthList}">
								<option <c:if test="${eMonth==endMonth}">selected</c:if> value="${eMonth }">${eMonth}</option>
							</c:forEach>
					</select> 
					<a href="javascript:void(0)" class="a_gret" id="query"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.Search"/></a>
					<a href="javascript:void(0)" class="a_gret" id="download"><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.DownLoad"/></a>
		          	</span>
		          </p>
		        </div>
		        <div class="page_table">
		        	<table cellspacing="0" cellpadding="0" style="width:100%;">
		            <thead>
		              <tr>
		               <c:if test="${pubtype=='1'}">
		              	<td class="atdtop" width="15%">ISBN</td>
		              </c:if>
		              <c:if test="${pubtype=='2'}">
		              	<td class="atdtop" width="15%">ISSN</td>
		              </c:if>
		              <c:if test="${pubtype=='null' || pubtype == ''}">
		              	<td class="atdtop" width="15%">ISBN/ISSN</td>
		              </c:if>
					    <c:if test="${ss=='3'}">
					    <td class="atdtop" width="17%">User activity</td>
					    </c:if>
					    <td class="atdtop">Total</td>
					    <c:forEach var="n" begin="${startMonth}" end="${endMonth}" step="1">
					    	<c:if test="${n==1}">
					    		<c:set var="month" value="Jan" />
					    	</c:if>
					    	<c:if test="${n==2}">
					    		<c:set var="month" value="Feb" />
					    	</c:if>
					    	<c:if test="${n==3}">
					    		<c:set var="month" value="Mar" />
					    	</c:if>
					    	<c:if test="${n==4}">
					    		<c:set var="month" value="Apr" />
					    	</c:if>
					    	<c:if test="${n==5}">
					    		<c:set var="month" value="May" />
					    	</c:if>
					    	<c:if test="${n==6}">
					    		<c:set var="month" value="Jun" />
					    	</c:if>
					    	<c:if test="${n==7}">
					    		<c:set var="month" value="Jul" />
					    	</c:if>
					    	<c:if test="${n==8}">
					    		<c:set var="month" value="Aug" />
					    	</c:if>
					    	<c:if test="${n==9}">
					    		<c:set var="month" value="Sep" />
					    	</c:if>
					    	<c:if test="${n==10}">
					    		<c:set var="month" value="Oct" />
					    	</c:if>
					    	<c:if test="${n==11}">
					    		<c:set var="month" value="Nov" />
					    	</c:if>
					    	<c:if test="${n==12}">
					    		<c:set var="month" value="Dec" />
					    	</c:if>
					    	<td class="atdtop">${month}</td>
					    </c:forEach> 
		              </tr>
		             </thead>
		             <tbody>
		             <c:forEach items="${list}" var="o" varStatus="index">
				        <tr>
					        <td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if> tdname">
					        	<span>
					        	<a href="${ctx}/pages/publications/form/article/${o.publications.id}">${o.publications.code}</a>
					        	</span>				    	
					    	</td>
						    <c:if test="${ss=='3'}">
						    	<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if> tdname" title="${o.activity}"><span style="width: 200px;">${o.activity}</span></td>
						    </c:if>
						    <td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
						    	${o.month1+o.month2+o.month3+o.month4+o.month5+o.month6+o.month7+o.month8+o.month9+o.month10+o.month11+o.month12}
						    </td>
						    <c:forEach var="n" begin="${startMonth}" end="${endMonth}" step="1">
						    	<c:if test="${n==1}">
						    		<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
						    			<c:if test="${o.month1==null}">0</c:if>
						    			<c:if test="${o.month1!=null}">${o.month1}</c:if>
						    		</td>
						    	</c:if>
						    	<c:if test="${n==2}">
						    		<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
						    			<c:if test="${o.month2==null}">0</c:if>
						    			<c:if test="${o.month2!=null}">${o.month2}</c:if>
						    		</td>
						    	</c:if>
						    	<c:if test="${n==3}">
						    		<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
						    			<c:if test="${o.month3==null}">0</c:if>
						    			<c:if test="${o.month3!=null}">${o.month3}</c:if>
						    		</td>
						    	</c:if>
						    	<c:if test="${n==4}">
						    		<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
						    			<c:if test="${o.month4==null}">0</c:if>
						    			<c:if test="${o.month4!=null}">${o.month4}</c:if>
						    		</td>
						    	</c:if>
						    	<c:if test="${n==5}">
						    		<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
						    			<c:if test="${o.month5==null}">0</c:if>
						    			<c:if test="${o.month5!=null}">${o.month5}</c:if>
						    		</td>
						    	</c:if>
						    	<c:if test="${n==6}">
						    		<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
						    			<c:if test="${o.month6==null}">0</c:if>
						    			<c:if test="${o.month6!=null}">${o.month6}</c:if>
						    		</td>
						    	</c:if>
						    	<c:if test="${n==7}">
						    		<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
						    			<c:if test="${o.month7==null}">0</c:if>
						    			<c:if test="${o.month7!=null}">${o.month7}</c:if>
						    		</td>
						    	</c:if>
						    	<c:if test="${n==8}">
						    		<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
						    			<c:if test="${o.month8==null}">0</c:if>
						    			<c:if test="${o.month8!=null}">${o.month8}</c:if>
						    		</td>
						    	</c:if>
						    	<c:if test="${n==9}">
						    		<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
						    			<c:if test="${o.month9==null}">0</c:if>
						    			<c:if test="${o.month9!=null}">${o.month9}</c:if>
						    		</td>
						    	</c:if>
						    	<c:if test="${n==10}">
						    		<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
						    			<c:if test="${o.month10==null}">0</c:if>
						    			<c:if test="${o.month10!=null}">${o.month10}</c:if>
						    		</td>
						    	</c:if>
						    	<c:if test="${n==11}">
						    		<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
						    			<c:if test="${o.month11==null}">0</c:if>
						    			<c:if test="${o.month11!=null}">${o.month11}</c:if>
						    		</td>
						    	</c:if>
						    	<c:if test="${n==12}">
						    		<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if>">
						    			<c:if test="${o.month12==null}">0</c:if>
						    			<c:if test="${o.month12!=null}">${o.month12}</c:if>
						    		</td>
						    	</c:if>
						    </c:forEach>
				        </tr>
					 </c:forEach>
					 </tbody>
		             <tfoot>
		              <tr>
		                <td colspan="${endMonth+3}" class="f_tda">
		                <ingenta-tag-v3:SplitTag first_ico="${ctx }/images/ico_left1.gif"
		                	last_ico="${ctx }/images/ico_right1.gif" 
		                	prev_ico="${ctx }/images/ico_left.gif" 
		                	next_ico="${ctx }/images/ico_right.gif" 
		                	method="post"
		                	formName="form"
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
	    <!--右侧菜单开始 -->
	    <jsp:include page="/pages/menu?mid=count" flush="true"/>
	    <!--右侧菜单结束-->
	    </div>
		<!--以上中间内容块结束-->
		</form:form>
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
