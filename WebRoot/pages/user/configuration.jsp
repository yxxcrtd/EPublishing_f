<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
		<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
		<%@ include file="/common/tools.jsp"%>
	<script type="text/javascript">
		
	</script>
	<%@ include file="/common/ico.jsp"%></head>
	<body>
		<jsp:include page="/pages/header/headerData" flush="true" />
		<!--定义01 mainContainer 内容区开始-->
		<div class="mainContainer">
			<!--定义 0101 头部边框-->
			<div class="borderContainer">
				<!--定义 0102 左边内容区域 开始-->
				<div class="leftContainer">
					<div class="clear"></div>
					<div class="clear"></div>
					<div class="favouritebox">
						<div class="favouritefont">基础信息维护</div>
						<div class="clear"></div>
						<div><a href="${ctx }/pages/configuration/form/edit">新增</a></div>
					</div>
					<div class="clear"></div>
					<div class="accountbox">
						<div class="accountdetail">
							<table align="left" cellpadding="0"	cellspacing="0" width="100%">
								<tr class="favouriteLable">
								<td align="center" width="8%">序号</td>
								<td align="center" width="20%">配置编码</td>
								<td align="center" width="20%">配置名称</td>
								<td align="center" width="18%">配置类型</td>
								<td align="center" width="34%">操作</td>
								</tr>
							    <c:forEach items="${list }" var="c" varStatus="index">
							    <tr class="<c:if test="${index.index%2 == 1}">favouritecontent</c:if><c:if test="${index.index%2 == 0}">favouritecontent gray</c:if>">
									<td align="center">${index.index+1 }</td>
									<td align="center">${c.code }</td>
								    <td align="center"><a href="${c.val}">${c.key }</a></td>
								    <td align="center">
								    <c:if test="${c.type==1}">基础参数</c:if>
								    <c:if test="${c.type==2}">基础显示</c:if>
								    </td>
								    <td>
								    	<a href="${ctx }/pages/configuration/form/edit?eid=${c.id}">修改</a>
								    </td>
							    </tr>
						        </c:forEach>
					        </table>
				        </div>
					</div>
				</div>
				<!--定义 0102 左边内容区域 结束-->

				<!--定义 0103 右边内容区域 开始-->
				<%@ include file="/pages/frame/left.jsp"%>
				<!--定义 0103 右边内容区域 结束-->

			</div>
		</div>
		<div class="boderBottom"></div>
		<!--定义01 mainContainer 内容区结束-->
		<!-- 底部的版权信息 -->
		<c:if test="${sessionScope.lang == 'zh_CN'}"><div id="footer_zh_CN"></div></c:if>
		<c:if test="${sessionScope.lang == 'en_US'}"><div id="footer_en_US"></div></c:if>
	</body>
</html>
