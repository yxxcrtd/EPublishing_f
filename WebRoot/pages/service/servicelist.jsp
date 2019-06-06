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
$(document).ready(function(){
getServiceById('${form.serviceId}');
$("a[name='whyGray']").click(function(){
$(".perLiA").removeClass("perLiA");
$(this).addClass("perLiA");
});
});

function getServiceById(val){
$.ajax({
		type : "POST",    
	    url: "${ctx}/pages/service/form/forServiceList",
	    data: {
			  serviceId:val      	
			  },
	    success : function(data) {
		          $("#one_service").html(data);
		      },  
		          error : function(data) {
		           }  
		      });
}
</script>
</head>

<body>
<!-- 顶部开始 -->
<jsp:include page="/pages/header/headerData"  />
<!-- 顶部结束 -->
<div class="main personMain">
	<div class="personLeft">
    	<div class="perLeftPart">
    	<c:forEach items="${list }" var="list" varStatus="status">
    	<p><a href="javascript:void(0)" name="whyGray" <c:if test="${list.id==form.serviceId }">class="perLiA"</c:if> onclick="getServiceById('${list.id}')">${list.title }</a></p>
    	
    	</c:forEach>
        </div>
    </div>
    <div class="perRight" id="one_service">
         <div id="free_pub"  class="resource">
           <img src="${ctx}/images/loading.gif"/>
         </div>
    </div>     
</div>
</div>
		
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>
