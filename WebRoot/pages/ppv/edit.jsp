<%@ page language="java" pageEncoding="UTF-8" %>

<html>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <daxtech-tag:CssTag/>
    <daxtech-tag:JsTag/>
    <base target="_self"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="${ctx}/js/jquery-1.8.2.js"></script>
    <script language="javaScript" src="<%=request.getContextPath()%>/js/EmailUtil.js"></script>
    <script type="text/javascript" src="${ctx }/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${ctx }/ueditor/ueditor.all.js"></script>
    <link rel="stylesheet" href="${ctx}/uploadify/uploadify.css">
    <script type="text/javascript" src="${ctx}/uploadify/swfobj.js"></script>
    <script type="text/javascript" src="${ctx}/uploadify/uploadify.js"></script>


    <title>出版物信息</title>
    <script type="text/javascript">
        $(function(){
            $("#pdfPath").uploadify({
                'uploader'           :'${ctx}/uploadify/uploadify.swf',
                'script'			: '${ctx}/pages/product/form/upoladFile',
                'scriptData'		: {
                    'name'		: 'pdfPath'
                },
                'buttonImg'			: '${ctx}/uploadify/browse.gif',
                'cancelImg'			: '${ctx}/uploadify/dele.gif',
                //'hideButton'		: true,
                'height'			: '20',
                'width'				: '80',
                'auto'				: true,
                'sizeLimit'			: 0,
                'fileDesc'			: 'pdf',
                'fileExt'			: '*.pdf',
                onComplete			: function(event, queueId, fileObj, response, data) {
                    $("#zspdf").val(response);
//                    strResourceId.attr("value", response);
                }
            });
            $("#dbfPath").uploadify({
                'uploader'           :'${ctx}/uploadify/uploadify.swf',
                'script'			: '${ctx}/pages/product/form/upoladFile',
                'scriptData'		: {
                    'name'		: 'sdpdf'
                },
                'buttonImg'			: '${ctx}/uploadify/browse.gif',
                'cancelImg'			: '${ctx}/uploadify/dele.gif',
                //'hideButton'		: true,
                'height'			: '20',
                'width'				: '80',
                'auto'				: true,
                'sizeLimit'			: 1024 * 1024 * 1024,
                'fileDesc'			: 'pdf',
                'fileExt'			: '*.pdf',
                onComplete			: function(event, queueId, fileObj, response, data) {
                    $("#shidupdf").val(response);
//                    strResourceId.attr("value", response);
                }
            });
        });
        function subVo() {
            if($("#title").val()==''){
                $("#titleMsg").html('出版物名称不能空!');
                return ;
            }
            var subs = $("input[name='subIds'][type='hidden']");
            if (subs.length == 0) {
                alert("请选择分类！");
                $("#subSel").focus();
                return;
            }
            document.getElementById("form").submit();
        }
        function delSubject(obj) {
            $(obj).remove();
        }
        function addSubject() {
            var id = $("#subSel").val();
            if (id == null || id == "" || id == "0") {
                alert("<ingenta-tag:LanguageTag sessionKey='lang' key='Pages.Journal.Edit.Prompt.Subject'/>");
                $("#subSel").focus();
                return;
            }
            $("#selectedBox").show();
            var idobj = $("#sub_" + id);
            if (idobj.length > 0) {
                //alert("已选择该分类，不可重复选择");
                $("#subSel").focus();
                return;
            }
            var txt = $("#subSel").find("option:selected").text();
            txt = $.trim(txt).replace(/^-*/, "");
            var htmlcode = "<span onclick='delSubject(this)' style='border: 1px solid #BDC0C5;margin:3px;cursor:pointer' title='<ingenta-tag:LanguageTag sessionKey='lang' key='Global.Prompt.Delete'/>' id='sub_" + id + "'>" + txt + "<input type='hidden' name='subIds' value='" + id + "'/ ></span>";
            $("#subjectBox").append(htmlcode);
        }

    </script>
    <link href="../css/index.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<form:form id="form" commandName="form" action="editSubmit" enctype="multipart/form-data">
    <div class="bodyDiv">
        <div class="bodyBg">
            <c:if test="${form.id==null||form.id=='0'||form.id==''}">
                <h3>新增出版物信息</h3>
            </c:if>
            <c:if test="${form.id!=null&&form.id!='0'&&form.id!=''}">
                <h3>修改出版物信息</h3>
            </c:if>
            <table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" class="tab02">
                <tr class="eve">
                    <th width="25%">出版物名称<span class="red">*</span>：</th>
                    <td width="75" align="left">
                        <input type="text" name="obj.title" value="${form.obj.title}" id="title">
                        <input type="hidden" name="obj.id" value="${form.obj.id}">
                        <input type="hidden" name="obj.status" value="${form.obj.status}">
                        <br/>
                        <span id="titleMsg"></span>
                    </td>
                </tr>
                <tr>
                    <th width="25%">艺术家姓名：</th>
                    <td width="75%" align="left"><input type="text" name="obj.artistsName"
                                                        value="${form.obj.artistsName}" id="artistsName"></td>
                </tr>
                <tr>
                    <th width="25%">作者：</th>
                    <td width="75%" align="left"><input type="text" name="obj.author" value="${form.obj.author}"
                                                        id="author"></td>
                </tr>
                <tr>
                    <th width="25%">封面图：</th>
                    <td width="75%" align="left">
                        <input type="file" name="file" id="file"/>
                        ${form.obj.coverPath}
                        <form:hidden path="obj.coverPath"/>
                    </td>
                </tr>
                                <tr>
                    <th width="25%">PDF：</th>
                    <td width="75%" align="left">
                        <input type="file" name="pdfPath" value="" id="pdfPath"/>
                        ${form.obj.pdfPath}
                        <form:hidden path="obj.pdfPath" id="zspdf"/>
                    </td>
                </tr>
                <tr>
                    <th width="25%">试读PDF：</th>
                    <td width="75%" align="left">
                        <input type="file" name="dbfPath" value="" id="dbfPath"/>
                        ${form.obj.shidu}
                        <form:hidden path="obj.shidu" id="shidupdf"/>
                    </td>
                </tr>
                <tr>
                    <th width="25%">出版年份：</th>
                    <td width="75%" align="left"><input type="text" name="obj.pubDate" value="${form.obj.pubDate}"
                                                        id="pubDate"></td>
                </tr>
                <tr>
                    <th width="25%">出版社：</th>
                    <td width="75%" align="left"><input type="text" name="obj.publisher" value="${form.obj.publisher}"
                                                        id="publisher"></td>
                </tr>
                <tr>
                    <th width="25%">开本：</th>
                    <td width="75" align="left"><input type="text" name="obj.formats" value="${form.obj.formats}"
                                                       id="formats"></td>
                </tr>
                <tr>
                    <th width="25%">页数：</th>
                    <td width="75%" align="left"><input type="text" name="obj.pages" value="${form.obj.pages}"
                                                        id="pages"></td>
                </tr>
                <tr>
                    <th width="25%">定价<span class="red">*</span>：</th>
                    <td width="75%" align="left"><input type="text" name="obj.price" value="${form.obj.price}"
                                                        id="price"></td>
                </tr>

                <tr id="selectedBox"
                    <c:if test="${form.obj.id==null||form.obj.id=='0'||form.obj.id==''}">style="display:none"</c:if>>
                    <th width="20%">已选分类：
                    </th>
                    <td id="subjectBox">
                        <c:forEach items="${pcslist}" var="s">
      	<span onclick="delSubject(this)" style="border: 1px solid #BDC0C5;margin:3px;cursor:pointer"
              title="<ingenta-tag:LanguageTag sessionKey='lang' key='Global.Prompt.Delete'/>" id="sub_${s.subject.id }">
      		${s.subject.name}
      		<input type="hidden" name="subIds" value="${s.subject.id }"/>
      	</span>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <th width="20%">备选分类：
                    </th>
                    <td>
                        <select id="subSel" style="width:80%">
                            <option value="0" selected><ingenta-tag:LanguageTag sessionKey="lang"
                                                                                key="Global.Prompt.Select"/></option>
                            <c:forEach items="${subList}" var="c">
                                <option value="${c.id}">
                                    <c:if test="${fn:length(c.treeCode)==6 }">${c.name}</c:if>
                                    <c:if test="${fn:length(c.treeCode)>6 }">
                                        <c:forEach begin="0" end="${fn:length(c.treeCode)-6 }"
                                                   varStatus="sta">-</c:forEach>${c.name}${c.code}
                                    </c:if>
                                </option>
                            </c:forEach>
                        </select>
                        <input type="button" onclick="addSubject()" value="添加"/>
                    </td>
                </tr>
                <tr>
                    <th width="20%">简介：</th>
                    <td align="left" colspan="3">
                        <script name="obj.summary" id="content" type="text/plain"
                                style="width:100%;height:300px">${form.obj.summary}</script>
                        <script type="text/javascript">
                            //实例化编辑器
                            ue = UE.getEditor('content', {
                                //关闭字数统计
                                wordCount: false,
                                //关闭elementPath
                                elementPathEnabled: false
                            });
                        </script>
                </tr>
            </table>
        </div>
    </div>
    <div align="center" class="mtp10">
        <input type="button" name="save" value="<ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.Save"/>"
               class="devil_button" onclick="subVo()"/>
        <input type="button" name="close" value="<ingenta-tag:LanguageTag sessionKey="lang" key="Global.Button.Close"/>"
               class="devil_button" onclick="window.close();"/>
    </div>
    <form:hidden path="id"/>
</form:form>
</body>
</body>
</html>
