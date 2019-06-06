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
<script type="text/javascript" src="${ctx }/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${ctx }/ueditor/ueditor.all.js"></script>
<script type="text/javascript">
$(document).ready(function(){
changeContent();
});

function changeContent(){
if($("#category").val()=="1"){
$("#contentA").show();
$("#contentB").hide();
}else{
$("#contentB").show();
$("#contentA").hide();
}
}
var ue;
function subVo()
{
if($("#title").val()==""){
alert("请输入广告名称！");
return;
}
if($("#category").val()=="1"){
$("#content").val($("#contentA1").val());
}else{
$("#content").val(ue.getContent());
}
var reg=/^(\d|\D)+(.jpg|.gif|.jpeg){1}$/;
if(!reg.test($("#file").val())){
art.dialog.tips("请选择.jep,.jpeg,.gif文件进行上传！");
return;
}
document.getElementById("form").submit();
}
function goBack(){
window.location.href="${ctx}/pages/advertisement/form/list";
}
</script>
</head>

<body>
<form:form id="form" commandName="form" action="save" enctype="multipart/form-data">
    <div class="bodyDiv">
        <div class="bodyBg">
            <c:if test="${form.id==null||form.id=='0'||form.id==''}">
                <h3>新增广告信息</h3>
            </c:if>
            <c:if test="${form.id!=null&&form.id!='0'&&form.id!=''}">
                <h3>修改广告信息</h3>
            </c:if>
            <table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" class="tab02">
                <tr class="eve">
                    <th width="25%">广告名称<span class="red">*</span>：</th>
                    <td width="75" align="left">
                        <input type="text" name="obj.title" value="${obj.title}" id="title"/>
                        <input type="hidden" name="id" value="${form.id}"/>
                        <span id="titleMsg"></span>
                    </td>
                </tr>
                <tr>
                    <th width="25%">广告类型：</th>
                    <td width="75%" align="left">
                    	<select name="obj.category" id="category" onchange="changeContent()">
						<option value="1" <c:if test="${obj.category==1 }">selected='selected'</c:if>>图片类</option>
						<option value="2" <c:if test="${obj.category==2 }">selected='selected'</c:if>>代码类</option>
						<option value="3" <c:if test="${obj.category==3 }">selected='selected'</c:if>>文字类</option>
						</select>
                    </td>
                </tr>
                <tr>
                    <th width="25%">广告地址：</th>
                    <td width="75%" align="left"><input type="text" name="obj.url" id="url"/></td>
                </tr>
                <tr>
                    <th width="25%">广告内容：</th>
                    <td align="left" id="contentA">
                    <input type="hidden" name="obj.content" id="content"/>
                    <textarea rows="3" cols="50" id="contentA1">${obj.content}</textarea>
                    </td>
                    <td align="left" id="contentB" colspan="3">
                    <script id="contenB1" type="text/plain" style="width:75%;height:100px" >${obj.content}</script>
				    <script type="text/javascript">
						//实例化编辑器
						 ue = UE.getEditor('contenB1',{
						//关闭字数统计
						 wordCount:false,
					    //关闭elementPath
						 elementPathEnabled:false,
					    //中英文
						 lang:<c:if test="${sessionScope.lang==null||sessionScope.lang=='zh_CN'}">'zh-cn'</c:if><c:if test="${sessionScope.lang=='en_US'}">'en'</c:if>
					      });
			        </script>
				 </td>
                </tr>
                <tr>
                    <th width="25%">投放位置：</th>
                    <td width="75%" align="left">
                    <!-- 首页顶部（可滚动）2-首页底部（单图，不可滚动）3-二级中文页面 4-二级英文页面 5-二级期刊页面【大于1就动】 -->
                    	<form:select path="obj.position" id="position">
						<form:option value="1">首页顶部</form:option>
						<form:option value="3">二级中文页面</form:option>
						<form:option value="4">二级英文页面</form:option>
						<form:option value="5">二级期刊页面</form:option>
						</form:select>
                </tr>
                <tr>
                    <th width="25%">广告状态：</th>
                    <td width="75%" align="left">
                    <!-- 1-投放中（上架（默认））2-未投放（下架）3-已过期 -->
                        <form:select path="obj.status" id="status">
						<form:option value="1">投放中</form:option>
						<form:option value="2">未投放</form:option>
						<form:option value="3">已过期</form:option>
						</form:select>
                    </td>
                </tr>
                <tr>
                    <th width="25%">开始时间：</th>
                    <td width="75%" align="left"><input type="text" name="startTime" id="statrTime" value="2014-12-12" readonly="readonly"/></td>
                </tr>
                <tr>
                    <th width="25%">结束时间：</th>
                    <td width="75" align="left"><input  type="text" name="endTime"  id="endTime" value="2014-12-13" readonly="readonly"/></td>
                </tr>
                <tr>
                    <th width="25%">图片：</th>
                    <td width="75%" align="left">
                    <input type="file" name="file" id="file"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div align="center" class="mtp10">
    
        <input type="button" name="save" value="保存" class="devil_button" onclick="subVo()"/>
        <input type="button" name="back" value="返回" class="devil_button" onclick="goBack()"/>
    </div>
</form:form>
</body>
</body>
</html>
