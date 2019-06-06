<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
    <!-- 右侧内容开始 -->
      <!-- 资源列表开始 -->
        <h1 class="h1Tit borBot pb5"><a href="javascript:void(0)" class="ico_packing"><ingenta-tag:LanguageTag key="Page.Frame.Header.Lable.Collection" sessionKey="lang"/></a></h1>
     	 <c:forEach items="${pcList }" var="c" varStatus="index">
     	 	<c:if test="${c.id != ppvCollId }">
					<div class="block borDashBot">
					<div class="fl w22 mt2">
					<img src="${ctx}/images/ico/ico11.png" width="15" height="15" />
						</div>
							<p>
							<a class="a_title" title="${c.name}" href="${ctx}/pages/collection/form/list?id=${c.id}">${c.name}</a>
						</p>
					<div class="fl w700">
					<p>
					<ingenta-tag:LanguageTag key="Page.Publications.Collection.Num" sessionKey="lang" />: &nbsp;
					<span class="mr20"><ingenta-tag:LanguageTag key="Page.Publications.Collection.Chinese" sessionKey="lang" /> ${c.chineseNumber}</span>   
				    <span class="mr20"><ingenta-tag:LanguageTag key="Page.Publications.Collection.Forgin" sessionKey="lang" /> ${c.forginNumber}</span>  
				    <span class="mr20"><ingenta-tag:LanguageTag key="Page.Collection.Lable.Ejournal" sessionKey="lang" /> ${c.journalNumber}</span>
				    <span class="mr20"><ingenta-tag:LanguageTag key="Page.Index.Search.Lable.Finded4.Option7" sessionKey="lang" /> ${c.type7Number}</span>
					</p>
					 <p>
					 <c:if test="${c.desc!=null&&c.desc!=''}">
					 	<ingenta-tag:LanguageTag key="Pages.Index.Lable.Abstract" sessionKey="lang" />:
					 	${fn:replace(fn:replace(c.desc,"<p>",""),"</p>","")}
					 </c:if>
					 </div>
					  <div class="fr">
						<a title="${c.name}" href="${ctx}/pages/collection/form/list?id=${c.id}">
							<c:if test="${c.cover==null||c.cover=='' }">
								<img src="<c:if test="${ctx!=''}">${ctx}</c:if><c:if test="${ctx==''}">${domain}</c:if>/images/noimg.jpg" width="52" height="71" onerror="this.src='${ctx}/images/noimg.jpg'" class="imgbor mt5 mr10"/>
							</c:if>
							<c:if test="${c.cover!=null&&c.cover!='' }">
								<img  src="/filestore${c.cover}" width="52" height="71" onerror="this.src='${ctx}/images/noimg.jpg'" class="imgbor mt5 mr10"/>
							</c:if>	
						</a>	
					 </div>
					</div>	
					</c:if>
				</c:forEach>
       
       <!--分页条开始-->
           <jsp:include page="../pageTag/pageTag.jsp">
			<jsp:param value="${form }" name="form"/>
	       </jsp:include>
       <!--分页条结束--> 