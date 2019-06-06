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
				if(parseInt(startMonth,10)>parseInt(endMonth,10)){
					art.dialog.tips('<ingenta-tag:LanguageTag sessionKey="lang" key="Page.User.accesslog.Prompt.month" />',1,'error');
				}else{
					var url = "";
					var type = $("#reportType").val();		
					
					url = "${ctx}/pages/user/form/downloadReport";	
					url+="?type="+ type +"&year="+$("#year").val()+"&startMonth="+startMonth+"&endMonth="+endMonth+"&r_="+new Date().getTime();
					
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
					var type = $("#reportType").val();	
					url += "?type=" + type + "&year="+$("#year").val()+"&startMonth="+startMonth+"&endMonth="+endMonth+"&r_="+new Date().getTime();
					window.location.href=url;
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
		        <ingenta-tag:LanguageTag sessionKey='lang' key='Pages.Journal.Counter.Lable.Title'/>	        
			</h1>
		    	<div class="t_list">
		       	  <p>
		          <span>
		          	<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.Journal.Counter.Lable.Type'/>：
		          	<select id="reportType" style="width:130px;vertical-align: middle; *margin-top: 8px;">				
						<option <c:if test="${counterType==1}">selected</c:if> value="1">Journal Report 1</option>
						<option <c:if test="${counterType==2}">selected</c:if> value="2">Journal Report 1 GOA</option>
						<%-- <option <c:if test="${counterType==3}">selected</c:if> value="3">Journal Report 1a</option> --%>
						<option <c:if test="${counterType==4}">selected</c:if> value="4">Journal Report 2</option>
						<%-- <option <c:if test="${counterType==5}">selected</c:if> value="5">Journal Report 3</option>--%>
						<option <c:if test="${counterType==6}">selected</c:if> value="6">Journal Report 4</option> 
						<option <c:if test="${counterType==7}">selected</c:if> value="7">Journal Report 5 YOP</option>
					</select> 
		          	<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.Access.Select.Lable.Year'/>：&nbsp;
					<select id="year" style="width:70px;vertical-align: middle; *margin-top: 8px;">				
							<c:forEach items="${form.yearList}" var="years">
								<option <c:if test="${years==year}">selected</c:if> value="${years }">${years}</option>
							</c:forEach>
					</select> 
					<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.Access.Select.Lable.StartMonth'/>：&nbsp;
					<select id="startMonth" style="width:50px;vertical-align: middle; *margin-top: 8px;">				
							<c:forEach var="sMonth" items="${form.monthList}">
								<option <c:if test="${sMonth==startMonth}">selected</c:if> value="${sMonth }">${sMonth}</option>
							</c:forEach>
					</select> 
					<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.Access.Select.Lable.EndMonth'/>：&nbsp;
					<select id="endMonth" style="width:50px;vertical-align: middle; *margin-top: 8px;">				
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
		        	<table cellspacing="0" cellpadding="0" style="width:780px;">
		            <thead>
		              <tr>
		               <c:if test="${counterType=='1' || counterType=='2' || counterType=='4' || counterType=='6'|| counterType=='7'}">
		              	<td class="atdtop" width="10%">Journal</td>		              
		              	<td class="atdtop" width="10%">Publisher</td>
		              	<td class="atdtop" width="10%">Platform</td>
		              	<td class="atdtop" width="10%">DOI</td>
		              	<td class="atdtop" width="10%">Identifier</td>
		              	<td class="atdtop" width="10%">PISSN</td>
		              	<td class="atdtop" width="10%">OISSN</td>
		              	<c:if test="${counterType=='4'}">
		              	<td class="atdtop" width="10%">Category</td>
		              	</c:if>
		              	<c:if test="${counterType=='6'}">
		              	<td class="atdtop" width="10%">Activity</td>
		              	</c:if>
		              	<c:if test="${counterType=='7'}">
		              	<td class="atdtop" width="10%">Articles</td>
		              	</c:if>
		              	<c:if test="${counterType=='1' || counterType=='2' || counterType=='4'|| counterType=='6'}">
		              	<td class="atdtop" width="10%">Total</td>
		              	</c:if>
		              	<c:if test="${counterType=='1' || counterType=='2'}">
		              	<td class="atdtop" width="10%">HTML</td>
		              	<td class="atdtop" width="10%">PDF</td>
		              	</c:if>
		              </c:if>	
		              <c:if test="${counterType=='1' || counterType=='2' || counterType=='4'|| counterType=='6'}">
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
		              </c:if>
		              <c:if test="${counterType=='7'}">	              
					    <c:forEach var="n" begin="${startYear}" end="${endYear}" step="1">					    	
					    	<td class="atdtop">${startYear+endYear-n}</td>
					    </c:forEach> 
					    </c:if>
		              </tr>
		             </thead>
		             <tbody>
		             <c:forEach items="${list}" var="o" varStatus="index">
				        <tr>
				        	 <c:if test="${counterType=='1' || counterType=='2' || counterType=='4' ||counterType=='6' ||counterType=='7'}">
					        <td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if> tdname">
					        	<span>
					        	<a href="${ctx}/pages/publications/form/article/${o.publications.publications.id}" title="${o.publications.publications.title}"><span style="width: 50px;">${o.publications.publications.title}</span></a>
					        	</span>				    	
					    	</td>						   
						    <td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if> tdname" title="${o.publications.publications.publisher.name}"><span style="width: 50px;">${o.publications.publications.publisher.name}</span></td>
						   <td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if> tdname">CNPe</td>
						   <td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if> tdname"></td>
						   <td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if> tdname"></td>
						   <td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if> tdname"></td>
						   <td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if> tdname">${o.publications.publications.code}</td>
						   <c:if test="${counterType=='4'}">
			              	<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if> tdname" title="Access denied : content item not licenced"><span style="width:50px;">Access denied : content item not licenced</span></td>
			              	</c:if>
			                <c:if test="${counterType=='6'}">
			              	<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if> tdname" width="10%">
			              	<c:if test="${o.searchType==1}"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Journal.Counter.Lable.searchType1"/></c:if>
			              	<c:if test="${o.searchType==2}"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Journal.Counter.Lable.searchType2"/></c:if>
			              	<c:if test="${o.searchType==3}"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Journal.Counter.Lable.searchType3"/></c:if>
			              	</td>
			              	</c:if>
			              	<c:if test="${counterType=='7'}">
			              	<td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if> tdname" width="10%">${o.year}</td>
			              	</c:if>
			              	<c:if test="${counterType=='1' || counterType=='2' || counterType=='4'|| counterType=='6'}">
						    <td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if> tdname">
						    	${o.month1+o.month2+o.month3+o.month4+o.month5+o.month6+o.month7+o.month8+o.month9+o.month10+o.month11+o.month12}
						    </td>
						    </c:if>
						    <c:if test="${counterType=='1' || counterType=='2'}">
						    <td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if> tdname">0</td>
						    <td class="<c:if test="${index.index%2==0 }">abodytd</c:if><c:if test="${index.index%2==1 }">bbodytd</c:if> tdname">
						    ${o.month1+o.month2+o.month3+o.month4+o.month5+o.month6+o.month7+o.month8+o.month9+o.month10+o.month11+o.month12}
						    </td>
						    </c:if>
						   </c:if>
						   <c:if test="${counterType=='1' || counterType=='2' || counterType=='4'||counterType=='6'}">
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
						   </c:if>
						   <c:if test="${counterType=='7'}">
						   <c:forEach var="n" begin="1" end="${endYear-startYear+1}" step="1">
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
						   </c:if>						   
				        </tr>
					 </c:forEach>
					 </tbody>
		             <tfoot>
		              <tr>
		                <td colspan="25" class="f_tda">
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
