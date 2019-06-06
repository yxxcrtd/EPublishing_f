<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

	<div class="personLeft">
		<!-- 个人-专家 用户菜单   1-个人;5-专家      start -->
		<c:if test="${type=='1'||type=='4'||type=='5'}">
	    	<div class="perLeftPart">
	        	<h1><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Information"/></h1>
		           <c:if test="${mid=='info'}"><p><c:if test="${type=='1'}"><a href="${ctx}/pages/orgUser/form/orgUserDetail" class="perLiA"></c:if><c:if test="${type=='5'}"><a href="${ctx}/pages/expertUser/form/expertDetail" class="perLiA"></c:if><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Information.Information"/></a></p></c:if>
		           <c:if test="${mid!='info'}"><p><c:if test="${type=='1'}"><a href="${ctx}/pages/orgUser/form/orgUserDetail"></c:if><c:if test="${type=='5'}"><a href="${ctx}/pages/expertUser/form/expertDetail"></c:if><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Information.Information"/></a></p></c:if>
		           <c:if test="${mid=='chpd'}"><p><a href="${ctx}/pages/user/form/resetPwd" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Information.Password"/></a></p></c:if>
		           <c:if test="${mid!='chpd'}"><p><a href="${ctx}/pages/user/form/resetPwd"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Information.Password"/></a></p></c:if>
	        </div> 
	        <div class="perLeftPart">
	        	<h1><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Search.Administration"/></h1>
		           <c:if test="${mid=='mage'}"><p><a href="${ctx}/pages/user/form/searchHistory?type=1" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Search.Administration"/></a></p></c:if>
		           <c:if test="${mid!='mage'}"><p><a href="${ctx}/pages/user/form/searchHistory?type=1"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Search.Administration"/></a></p></c:if>
	        </div> 
	        <div class="perLeftPart">
	    		<h1><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.order.management.title"/></h1>
	    		<c:if test="${mid=='order'}"><p><a href="${ctx }/pages/order/form/list" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.list"/></a></p></c:if>
	            <c:if test="${mid!='order'}"><p><a href="${ctx }/pages/order/form/list"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.list"/></a></p></c:if>
	            <c:if test="${mid=='expense'}"><p><a href="${ctx }/pages/user/form/myTranLog" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyAccount.Label.myTranLog"/></a></p></c:if>
	            <c:if test="${mid!='expense'}"><p><a href="${ctx }/pages/user/form/myTranLog"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyAccount.Label.myTranLog"/></a></p></c:if>
	    	</div>
	        <div class="perLeftPart">
	        	<h1><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Remind"/></h1>
		           <c:if test="${mid=='remid'}"><p><a href="${ctx}/pages/user/form/mySubjectAlerts?pCode=all" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Remind.Remind"/></a></p></c:if>
		           <c:if test="${mid!='remid'}"><p><a href="${ctx}/pages/user/form/mySubjectAlerts?pCode=all"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Remind.Remind"/></a></p></c:if>
		           <c:if test="${mid=='sujb'}"><p><a href="${ctx}/pages/user/form/subjectAlerts?pCode=all" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Remind.Science"/></a></p></c:if>
		           <c:if test="${mid!='sujb'}"><p><a href="${ctx}/pages/user/form/subjectAlerts?pCode=all"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Remind.Science"/></a></p></c:if>
<%-- 	        		 <c:if test="${mid!='sujb'}"><p><a href="${ctx}/pages/user/form/allSubjectAlerts?pCode=all">=====学科提醒新页面==</a></p></c:if> --%>
	        </div>
	        <div class="perLeftPart">
	        	<h1><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Collection"/></h1>
		           <c:if test="${mid=='favo'}"><p><a href="${ctx }/pages/favourites/form/favorites" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Collection.Favorite"/></a></p></c:if>
		           <c:if test="${mid!='favo'}"><p><a href="${ctx }/pages/favourites/form/favorites"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Collection.Favorite"/></a></p></c:if>
		           <c:if test="${mid=='hist'}"><p><a href="${ctx }/pages/user/form/recommend?type=1&isCn=true" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Recommend.History"/></a></p></c:if>
		           <c:if test="${mid!='hist'}"><p><a href="${ctx }/pages/user/form/recommend?type=1&isCn=true"><ingenta-tag:LanguageTag sessionKey="lang" key="Page.Frame.Personal.Recommend.History"/></a></p></c:if>
	        </div>
	    </c:if>
	    <!-- 个人-专家 用户菜单        end -->
	    <!-- 机构用户菜单   2-机构     start -->
	    <c:if test="${type=='2'}">
	    	<div class="perLeftPart">
	    		<h1><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.OrgUserDetail.Label.title1"/></h1>
	    		<c:if test="${mid=='changeinfo'}"><p><a href="${ctx}/pages/orgUser/form/personDetail" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.orgUserDetail"/></a></p></c:if>
		        <c:if test="${mid!='changeinfo'}"><p><a href="${ctx}/pages/orgUser/form/personDetail"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.orgUserDetail"/></a></p></c:if>
	    		<c:if test="${mid=='logo'}"><p><a href="${ctx }/pages/user/form/logo" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.logo"/></a></p></c:if>
	            <c:if test="${mid!='logo'}"><p><a href="${ctx }/pages/user/form/logo"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.logo"/></a></p></c:if>
           		<c:if test="${mid=='marc'}"><p><a href="${ctx }/pages/markData/form/manager" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.MarkData.list.title.download"/></a></p></c:if>
	            <c:if test="${mid!='marc'}"><p><a href="${ctx }/pages/markData/form/manager"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.MarkData.list.title.download"/></a></p></c:if>
	    	</div>
	    	<div class="perLeftPart">
	    		<h1><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.Subscribe"/></h1>
	    		<c:if test="${mid=='subscribe'}"><p><a href="${ctx}/pages/user/form/mySubscription/public?searchType=1" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.All.subscribe"/></a></p></c:if>
	            <c:if test="${mid!='subscribe'}"><p><a href="${ctx}/pages/user/form/mySubscription/public?searchType=1"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.All.subscribe"/></a></p></c:if>
	    	</div>
	    	<div class="perLeftPart">
	    		<h1><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.order.management.title"/></h1>
	    		<c:if test="${mid=='order'}"><p><a href="${ctx }/pages/order/form/inslist" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.list"/></a></p></c:if>
	            <c:if test="${mid!='order'}"><p><a href="${ctx }/pages/order/form/inslist"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.list"/></a></p></c:if>
	            <c:if test="${mid=='expense'}"><p><a href="${ctx }/pages/user/form/insTranLog" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyAccount.Label.myTranLog"/></a></p></c:if>
	            <c:if test="${mid!='expense'}"><p><a href="${ctx }/pages/user/form/insTranLog"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.MyAccount.Label.myTranLog"/></a></p></c:if>
	    	</div>
	    	<div class="perLeftPart">
	    		<h1><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.technocracy.title"/></h1>
	    		<c:if test="${mid=='Expertuser'}"><p><a href="${ctx }/pages/expertUser/form/expertManage" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.expertManage"/></a></p></c:if>
	            <c:if test="${mid!='Expertuser'}"><p><a href="${ctx }/pages/expertUser/form/expertManage"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.expertManage"/></a></p></c:if>
	            <c:if test="${mid=='register'}"><p><a href="${ctx }/pages/expertUser/form/register" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.register"/></a></p></c:if>
	            <c:if test="${mid!='register'}"><p><a href="${ctx }/pages/expertUser/form/register"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.register"/></a></p></c:if>
	    	</div>
	    	<div class="perLeftPart">
	    		<h1><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.recommend.Manager"/></h1>
	    		<c:if test="${mid=='recommended'}"><p><a href="${ctx }/pages/user/form/myrecommend?type=1&isCn=true" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.myrecommend"/></a></p></c:if>
	            <c:if test="${mid!='recommended'}"><p><a href="${ctx }/pages/user/form/myrecommend?type=1&isCn=true"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.myrecommend"/></a></p></c:if>
	    	</div>
	    	<div class="perLeftPart">
	    		<h1><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.Instaccount.Label.title5"/></h1>
	    		<c:if test="${mid=='Plays'}"><p><a href="${ctx}/pages/user/form/accesslogNew?pubtype=1&type=2&access=1&languagetype=2&btn=1" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Plays.statistical.title"/></a></p></c:if>
	            <c:if test="${mid!='Plays'}"><p><a href="${ctx}/pages/user/form/accesslogNew?pubtype=1&type=2&access=1&languagetype=2&btn=1"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Plays.statistical.title"/></a></p></c:if>
	            <c:if test="${mid=='Chinese'}"><p><a href="${ctx}/pages/user/form/accesslogNew?pubtype=1&type=2&access=1&languagetype=1&btn=1" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Chinese.statistical.title"/></a></p></c:if>
	            <c:if test="${mid!='Chinese'}"><p><a href="${ctx}/pages/user/form/accesslogNew?pubtype=1&type=2&access=1&languagetype=1&btn=1"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Chinese.statistical.title"/></a></p></c:if>
	            <c:if test="${mid=='Journal'}"><p><a href="${ctx}/pages/user/form/accesslogNew?pubtype=2&type=2&access=1&languagetype=1&btn=1" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Journal.statistical.title"/></a></p></c:if>
	            <c:if test="${mid!='Journal'}"><p><a href="${ctx}/pages/user/form/accesslogNew?pubtype=2&type=2&access=1&languagetype=1&btn=1"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.Journal.statistical.title"/></a></p></c:if>
 	            <c:if test="${mid=='JournalEn'}"><p><a href="${ctx}/pages/user/form/accesslogNew?pubtype=2&type=2&access=1&languagetype=2&btn=1" class="perLiA"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.JournalCn.statistical.title"/></a></p></c:if>
 	            <%-- <c:if test="${mid!='JournalEn'}"><p><a href="${ctx}/pages/user/form/accesslogNew?pubtype=2&type=2&access=1&languagetype=2&btn=1"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.JournalCn.statistical.title"/></a></p></c:if> --%> 
	    	</div>
	    	
	    </c:if>
	    <!-- 机构用户菜单   2-机构     end -->
	    <!-- 中图超级管理员菜单   5-机构     start -->
	    <!-- 中图超级管理员菜单   5-机构     end -->
	    
    </div>
