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
function insertVo(){
document.getElementById("form").action="edit";
document.getElementById("form").submit();
}
function updateVo(id){
$("#id").val(id);
document.getElementById("form").action="edit";
document.getElementById("form").submit();
}
function test(){

var dialog=art.dialog({   
    //title: '涓囪薄缃戠',   
    width: 220,   
    content: '灏婃暚鐨勯【瀹㈡湅鍙嬶紝鎮↖Q鍗′綑棰濅笉瓒�10鍏冿紝璇峰強鏃跺厖鍊�',   
    icon: 'face-sad',   
    //time: 5,
    lock:true,
    okVal: '鍏呭€�', 
    ok: function () {
    art.dialog({   
    title: '涓囪薄缃戠',   
    width: 220,   
    content: '鍏呭€兼垚鍔燂紒',   
    icon: 'face-smile',   
    time: 5,
    lock:true,
    });
    },
    cancelVal: '鍏虫満',   
    cancel: function(){
    art.dialog({   
    title: '涓囪薄缃戠',   
    width: 220,   
    content: '绌峰厜铔嬶紝鍐嶅埆鏉ヤ簡锛�',   
    icon: 'face-sad',   
    time: 5,
    lock:true,
    });
    art.dialog.tips('鎵ц鍙栨秷鎿嶄綔');
    art.dialog.alert('浜哄搧瓒婃潵瓒婁笉閭ｄ箞绋冲畾浜嗭紝璇锋鏌ワ紒');
    } 
}); 
}
function query()
{
    document.getElementById("page").value="0";
	document.getElementById("form").submit();
}
function deleteVo(id) {
        $("#id").val(id);
		document.getElementById("form").action="delete";
		document.getElementById("form").submit();
}
</script>
<link href="<%=request.getContextPath()%>/css/index.css" rel="stylesheet" type="text/css" />
</head>
<body>
  <form:form action="list" method="post" commandName="form" id="form">
  <c:if test="${form.msg!=null&&form.msg != ''}">
     <script language="javascript">
		alert('${form.msg}');
  </script>
  </c:if>
  <div class="bodyDiv bodyNew" style="position:relative;">
  <div class="pos">
  <div class="bodyBg">
  <div style="margin:5px 0px;">
      <input type="button" name="add" value="新增" class="bton03" onclick="insertVo()"/>
  </div>
  <table width="95%" border="0" cellpadding="0" cellspacing="1" class="devil_table">
  <thead>
  <tr>
  <th width="10%">广告类型</th>
  <th width="20%">广告标题</th>
  <th width="10%">投放位置</th>
  <th width="20%">广告状态</th>
  <th width="10%">点击量</th>
  <th width="30%">操作</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${list}" var="vo" varStatus="index">
  <tr class="<c:if test="${index.index%2 == 1}">odd</c:if><c:if test="${index.index%2 == 0}">eve</c:if>">
  <td align="left">
  <c:if test="${vo.category==1}">图片广告</c:if>
  <c:if test="${vo.category==2}">代码类广告</c:if>
  <c:if test="${vo.category==3}">文字广告</c:if>
  </td>
  <td align="left">${vo.title }</td>
  <td align="left">
  <c:if test="${vo.position==1}">首页顶部</c:if>
  <c:if test="${vo.position==2}">首页底部</c:if>
  <c:if test="${vo.position==3}">二级中文页面</c:if>
  <c:if test="${vo.position==4}">二级英文页面</c:if>
  <c:if test="${vo.position==5}">二级期刊页面</c:if>
  </td>
  <td align="left">
  <c:if test="${vo.status==1}">投放中</c:if>
  <c:if test="${vo.status==2}">未投放</c:if>
  <c:if test="${vo.status==3}">已过期</c:if>
  </td>
  <td align="left">${vo.view }</td>
  <td align="left">
	<input type="button" value="修改" class="bton03" onclick="updateVo('${vo.id}')"/>
	<input type="button" value="删除" class="bton03" onclick="deleteVo('${vo.id}')"/>
  </td>
  </tr>
  </c:forEach>
  </tbody>
  </table>
  <div>
  <!-- 
  <ingenta-tag:SplitTag page="${form.curpage}" pageCount="${form.pageCount}" count="${form.count}" formName="form" method="get" showNum="5" url="${form.url}" i18n="${sessionScope.lang}"/> -->
  </div>
  </div>
  </div>
  </div>
  <input type="hidden" name="id" id="id"/>
  </form:form>
</body>
</html>

