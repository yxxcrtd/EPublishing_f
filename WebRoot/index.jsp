<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
    <head>
        <title>FlexPaper AdaptiveUI JSP Example</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1,width=device-width" />
        <style type="text/css" media="screen">
			html, body	{ height:100%; }
			body { margin:0; padding:0; overflow:auto; }
			#flashContent { display:none; }
        </style>

		<link rel="stylesheet" type="text/css" href="${ctx}/flexpaper/css/flexpaper.css" />
		<script type="text/javascript" src="${ctx}/flexpaper/js/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/flexpaper/js/jquery.extensions.min.js"></script>
		<script type="text/javascript" src="${ctx}/flexpaper/js/flexpaper.js"></script>
		<script type="text/javascript" src="${ctx}/flexpaper/js/flexpaper_handlers_debug.js"></script>
    </head>
    <body><%=request.getRemoteAddr()%>
    	 <table border="0" width="230">
        	<tr><th>Event Log</th></tr>
        	<tr><td><textarea rows=6 cols=28 id="txt_eventlog" style="width:350px;font-size:9px;" wrap="off"></textarea></td></tr>
        	<tr><td><input type=text style="width:350px;" id="txt_progress" value=""></td></tr>
	    </table>
		<div id="documentViewer"  style="position:absolute;left:10px;top:300px;width:770px;height:500px"></div>
		<script type="text/javascript">
			function getDocumentUrl(document){
				var numPages = 10;
				var url = "{pages/view/form/page?pubId={doc}&format={format}&pageNum=[*,0],{numPages}}";
				url = url.replace("{doc}",document);
				url = url.replace("{numPages}",numPages);
				return url;
				//return "view.jsp?doc={doc}&format={format}&page={page}".replace("{doc}",document);     
			}
			function getDocQueryServiceUrl(document){
				return "swfsize.jsp?doc={doc}&page={page}".replace("{doc}",document);
			}
			var startDocument = "";

			function append_log(msg){
				$('#txt_eventlog').val(msg+'\n'+$('#txt_eventlog').val());
			}

			String.format = function() {
				var s = arguments[0];
				for (var i = 0; i < arguments.length - 1; i++) {
					var reg = new RegExp("\\{" + i + "\\}", "gm");
					s = s.replace(reg, arguments[i + 1]);
				}
			
				return s;
			};

			$('#documentViewer').FlexPaperViewer({
				 config : {
					 DOC : escape(getDocumentUrl('ff8080813f95699c013f98088aa70002')),
					 Scale : 0.6, 
					 ZoomTransition : 'easeOut',
					 ZoomTime : 0.5, 
					 ZoomInterval : 0.1,
					 FitPageOnLoad : true,
					 FitWidthOnLoad : false, 
					 FullScreenAsMaxWindow : false,
					 ProgressiveLoading : false,
					 MinZoomSize : 0.2,
					 MaxZoomSize : 5,
					 SearchMatchAll : false,
					 RenderingOrder : 'flash,flash',

					 ViewModeToolsVisible : true,
					 ZoomToolsVisible : true,
					 NavToolsVisible : true,
					 CursorToolsVisible : true,
					 SearchToolsVisible : true,

					 DocSizeQueryService : "swfsize.jsp?doc=" + startDocument,
					 jsDirectory : '${ctx}/flexpaper/js/',
					 localeDirectory : '${ctx}/flexpaper/locale/',

					 JSONDataType : 'jsonp',
					 key : '@4516c44a3b7f5ec2893$cc3be1a9ff661cfdef6',

					 WMode : 'window',
					 localeChain: 'en_US'
					 }
			});
		</script>
       
   </body>
</html>