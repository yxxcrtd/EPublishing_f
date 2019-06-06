<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
<%@ include file="/common/tools.jsp"%>
<script language="javascript">
$(document).ready(function(){
	switchTagCase("tagCase2", "contentCase2");
});
</script>
<!--客户案例开始-->
<style type="text/css">
<!--
* {
	margin: 0;
	padding: 0
}

#containerCase {
	text-align: center;
	width: 670px;
	*width: 630px;
	padding: 0px;
	margin-left: 20px;
}

#containerCase #titleCase {
	height: 29px;
	margin: 0 auto;
	border-bottom: 1px solid #ccc;
}

#containerCase #titleCase li {
	float: left;
	list-style-type: none;
	width: 129px;
	height: 29px;
	line-height: 29px;
	vertical-align: middle;
	font-size: 14px;
	font-family: "Microsoft YaHei";
}

#containerCase #titleCase ul {
	width: 99%;
	height: 29px;
	padding-left: 10px;
}

#containerCase #titleCase a {
	text-decoration: none;
	color: #333;
	font-family: "Microsoft YaHei";
	display: block;
	width: auto;
	background: url(${ctx}/images/tabTitle.gif) no-repeat left -29px;
	padding: 0px;
	cursor: pointer;
}

#containerCase #titleCase #tagCase1 a:hover {
	text-decoration: none;
	color: #0c4398;
	font-family: "Microsoft YaHei";
	display: block;
	width: auto;
	background: url(${ctx}/images/tabTitle.gif) no-repeat left 0px;
	padding: 0px;
	cursor: pointer;
}

#containerCase #titleCase #tagCase2 a:hover {
	text-decoration: none;
	color: #0c4398;
	font-family: "Microsoft YaHei";
	display: block;
	width: auto;
	background: url(${ctx}/images/tabTitle.gif) no-repeat left 0px;
	padding: 0px;
	cursor: pointer;
}
#containerCase #titleCase #tagCase2 span {
	margin-left: 20px;
}

#containerCase #titleCase #tagCase3 a:hover {
	text-decoration: none;
	color: #0c4398;
	font-family: "Microsoft YaHei";
	display: block;
	width: auto;
	background: url(${ctx}/images/tabTitle.gif) no-repeat left 0px;
	padding: 0px;
	cursor: pointer;
}

#containerCase #titleCase #tagCase4 a:hover {
	text-decoration: none;
	color: #0c4398;
	font-family: "Microsoft YaHei";
	display: block;
	width: auto;
	background: url(${ctx}/images/tabTitle.gif) no-repeat left 0px;
	padding: 0px;
	cursor: pointer;
}

#containerCase #titleCase .selectCaseli1 {
	text-decoration: none;
	color: #0c4398;
	font-family: "Microsoft YaHei";
	font-weight: bold;
	display: block;
	width: auto;
	background: url(${ctx}/images/tabTitle.gif) no-repeat left 0px;
	cursor: pointer;
}

#containerCase #titleCase .selectCaseli2 {
	text-decoration: none;
	color: #0c4398;
	font-family: "Microsoft YaHei";
	font-weight: bold;
	display: block;
	width: auto;
	background: url(${ctx}/images/tabTitle.gif) no-repeat left 0px;
	cursor: pointer;
}

#containerCase #titleCase .selectCaseli3 {
	text-decoration: none;
	color: #0c4398;
	font-family: "Microsoft YaHei";
	font-weight: bold;
	display: block;
	width: auto;
	background: url(${ctx}/images/tabTitle.gif) no-repeat left 0px;
	cursor: pointer;
}

#containerCase #titleCase .selectCaseli4 {
	text-decoration: none;
	color: #0c4398;
	font-family: "Microsoft YaHei";
	font-weight: bold;
	display: block;
	width: auto;
	background: url(${ctx}/images/tabTitle.gif) no-repeat left 0px;
	cursor: pointer;
}

#containerCase #contentCase ul {
	margin: 2px;
}

#containerCase #contentCase li {
	margin: 5px;
}

#containerCase #contentCase li img {
	margin: 0px;
	display: block;
}

#containerCase #contentCase {
	height: 29px;
}

.hidecontentCase {
	display: none;
}
-->
</style>
<script language="javascript">
	function switchTagCase(tagCase, contentCase) {
		for (var i = 1; i < 3; i++) {
			if ("tagCase" + i == tagCase) {
				document.getElementById(tagCase).getElementsByTagName("a")[0].className = "selectCaseli"
						+ i;
				document.getElementById(tagCase).getElementsByTagName("a")[0]
						.getElementsByTagName("span")[0].className = "selectCasespan"
						+ i;
			} else {
				document.getElementById("tagCase" + i)
						.getElementsByTagName("a")[0].className = "";
				document.getElementById("tagCase" + i)
						.getElementsByTagName("a")[0]
						.getElementsByTagName("span")[0].className = "";
			}
			if ("contentCase" + i == contentCase) {
				document.getElementById(contentCase).className = "";
			} else {
				document.getElementById("contentCase" + i).className = "hidecontentCase";
			}
			document.getElementById("contentCase").className = contentCase;
		}
	}
</script>
<!--客户案例结束-->
<%@ include file="/common/ico.jsp"%></head>
<body>
	<jsp:include page="/pages/header/headerData" flush="true" />
	<!--定义01 mainContainer 内容区开始-->
	<div class="mainContainer">
		<!--定义 0101 头部边框-->
		<div class="borderContainer">
			<!--定义 0102 左边内容区域 开始-->
			<div class="leftContainer">
				<div class="listTitle">${form.obj.title}</div>
				<div class="clear"></div>

				<div id="containerCase">
					<!--@3->02 滑动门 标题 开始-->
					<div id="titleCase">
						<ul>
							<li id="tagCase2"><a
								onclick="switchTagCase('tagCase2','contentCase2');this.blur();"
								class="selectCaseli2"><span class="selectCasespan2"><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SearchHistory.tag1"/></span>
							</a></li>
							<li id="tagCase1"><a
								onclick="switchTagCase('tagCase1','contentCase1');this.blur();"><span><ingenta-tag:LanguageTag sessionKey="lang" key="Pages.User.SearchHistory.tag2"/></span>
							</a></li>
						</ul>
					</div>
					<!--@3->02 滑动门 标题 结束-->
					<!--@3->03 滑动门 内容 开始-->
					<div id="contentCase" class="contentCase2">
						<!--@3->03->01 滑动门 新闻中心 开始-->
						<div id="contentCase2">
							<jsp:include page="/pages/user/form/searchHistory?type=1" />
						</div>
						<!--@3->03->01 滑动门 新闻中心 结束-->

						<!--@3->03->02 滑动门 行业方案 开始-->
						<div id="contentCase1" class="hidecontentCase">
							<div class="articleInfo">
								<jsp:include page="/pages/user/form/searchHistory?type=2" />
							</div>
							<div class="clear"></div>
							<div class="pubmetadata"></div>
						</div>

					</div>
					<!--@3->03 滑动门 内容 结束-->
				</div>


				<div class="clear"></div>
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
