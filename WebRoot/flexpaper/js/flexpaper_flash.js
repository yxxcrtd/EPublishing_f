var docViewer;

function getDocViewer(){
    if(docViewer)
    	return docViewer;
    else
    	docViewer = window.FlexPaperViewer_Instance.getApi();
    	
    return docViewer;
}

/**
*
* FlexPaper constructor (name of swf, name of placeholder, config)
*
*/
window.FlexPaperViewer = window.$f = function() {
	var config = arguments[2].config;
	
	window.FlexPaperViewer_Instance = flashembed(arguments[1], {
	
			src: arguments[0],
			version: [9, 0]
			}, {
			SwfFile : escape(config.DOC),
			//Scale : config.Scale, 
			ZoomTransition : config.ZoomTransition,
			ZoomTime : config.ZoomTime,
			ZoomInterval : config.ZoomInterval,
			//FitPageOnLoad : config.FitPageOnLoad,
			FitWidthOnLoad : config.FitWidthOnLoad,
			PrintEnabled : config.PrintEnabled,
			SelectEnabled : config.SelectEnabled,
			Reference:config.Reference,
			SearchString:config.SearchString,
			FullScreenAsMaxWindow : config.FullScreenAsMaxWindow,
			ProgressiveLoading : config.ProgressiveLoading,
			MinZoomSize : config.MinZoomSize,
			MaxZoomSize : config.MaxZoomSize,
			SearchMatchAll : config.SearchMatchAll,
			InitViewMode : config.InitViewMode,
			  
			ViewModeToolsVisible : config.ViewModeToolsVisible,
			ZoomToolsVisible : config.ZoomToolsVisible,
			NavToolsVisible : config.NavToolsVisible,
			CursorToolsVisible : config.CursorToolsVisible,
			SearchToolsVisible : config.SearchToolsVisible,
			
			SearchString : config.SearchString,
			Reference : config.Reference,
			SelectVisible : config.SelectVisible,
			localeChain: config.localeChain,
			WMode :config.WMode,
			key : config.key,
			localeChain : config.localeChain
	});
};



/**
 * Handles the event of external links getting clicked in the document. 
 *
 * @example onExternalLinkClicked("http://www.google.com")
 *
 * @param String link
 */
function onExternalLinkClicked(link){
	$("#txt_eventlog").val('onExternalLinkClicked:' + link + '\n' + $("#txt_eventlog").val());	
}

/**
 * Recieves progress information about the document being loaded
 *
 * @example onProgress( 100,10000 );
 *
 * @param int loaded
 * @param int total
 */
function onProgress(loadedBytes,totalBytes){
	$("#txt_progress").val('onProgress:' + loadedBytes + '/' + totalBytes + '\n');	
}

/**
 * Handles the event of a document is in progress of loading
 *
 */
function onDocumentLoading(){
	$("#txt_eventlog").val('onDocumentLoading' + '\n' + $("#txt_eventlog").val());	
}

/**
 * Receives messages about the current page being changed
 *
 * @example onCurrentPageChanged( 10 );
 *
 * @param int pagenum
 */
function onCurrentPageChanged(pagenum){
	$.ajax({
		type : "POST",  
        url: "viewAjax",
        data: {
        	pubId:$("#pubId11").val(),
        	pageNum:pagenum,
        	licenseId:$("#licenseId11").val(),
        	count:$("#pageCount11").val(),
        	readCount:$("#readCount11").val(),
        	isCopy:$("#isCopy11").val(),
			r_ : new Date().getTime()
        },
        success : function(abc) {  
        	var s = eval(abc);
        	//生成笔记列表
          if(s[0].noteList!=null){
          	var arr = new Array();
          	arr = s[0].noteList;
          	var sss = "";
          	for(var i=0;i<arr.length;i++){
          		sss += "<p id=\"nodes_"+arr[i].id+"\">";
          		sss += "<span class=\"book_l\">";
          		sss += "<a style=\"margin: 0px;padding: 0px;border-bottom: none;background: none;\" onclick=\"getDocViewer().gotoPage('"+arr[i].pages.number+"')\">"+arr[i].pages.number+"__"+arr[i].noteContent+"</a>"
          		sss +="</span>";
          		sss += "<span class=\"book_r\"><a title=\""+delBut+"\" onclick=\"deleteNote('"+arr[i].id+"')\"><img src=\""+ctx+"/images/cao.gif\"/></a></span>";
          		sss += "</p>";
             }
             $("#noteList").html(sss);
           }else{
             $("#noteList").html('');
           }
          //======
		  if(s[0].noteId==null||s[0].noteId==""){
	  		$("#isNote").val('');
		  	$("#content").val('');
          	$("#checks").html(noteLength_yes+" <strong>1000</strong> "+ noteLength_number).css('color', ''); 
		  }else{
		  	$("#isNote").val(s[0].noteId);
		  	$("#content").val(s[0].noteContent);
		  	if(s[0].noteContent.length<=1000){
          		$("#checks").html(noteLength_yes+" <strong>"+(1000-s[0].noteContent.length)+"</strong> "+noteLength_number).css('color', ''); 
          	}else{
          		$("#checks").html(noteLength_no+" <strong>"+(s[0].noteContent.length-1000)+"</strong> "+noteLength_number).css('color', '#FF0000'); 
          	}
		  }
		  if(s[0].isCopy=='true'){	          	
	          	$("#btn_copy").css("display","none");
	          	getDocViewer().switchSelect(true);
          }else if(s[0].isCopy=='false'){
          		$("#btn_copy").css("display","block");	    	   
	    	    getDocViewer().switchSelect(false);
	      }
		  if(s[0].isDownload=='true'){	 
			  $("#bbbbb").css("display","block"); 
		  }else if(s[0].isDownload=='false'){
			  $("#bbbbb").css("display","none");
		  }
		  if(s[0].isPrint=='true'){	 
			  $("#bbbbb").css("display","block"); 
		  }else if(s[0].isPrint=='false'){
			  $("#bbbbb").css("display","none");
		  }
        },  
        error : function(abc) {
        }  
	});
	$("#txt_eventlog").val('onCurrentPageChanged:' + pagenum + '\n' + $("#txt_eventlog").val());
}

/**
 * Receives messages about the document being loaded
 *
 * @example onDocumentLoaded( 20 );
 *
 * @param int totalPages
 */
function onDocumentLoaded(totalPages){
	$("#txt_eventlog").val('onDocumentLoaded:' + totalPages + '\n' + $("#txt_eventlog").val());
}

/**
 * Receives error messages when a document is not loading properly
 *
 * @example onDocumentLoadedError( "Network error" );
 *
 * @param String errorMessage
 */
function onDocumentLoadedError(errMessage){
	$("#txt_eventlog").val('onDocumentLoadedError:' + errMessage + '\n' + $("#txt_eventlog").val());
}

/**
 * Receives error messages when a document has finished printed
 *
 * @example onDocumentPrinted();
 *
 */
function onDocumentPrinted(){
	$("#txt_eventlog").val('onDocumentPrinted\n' + $("#txt_eventlog").val());
}


/** 
 * 
 * FlexPaper embedding functionality. Based on FlashEmbed
 *
 */

(function() {
		
	var IE = document.all,
		 URL = 'http://www.adobe.com/go/getflashplayer',
		 JQUERY = typeof jQuery == 'function', 
		 RE = /(\d+)[^\d]+(\d+)[^\d]*(\d*)/,
		 GLOBAL_OPTS = { 
			// very common opts
			width: '100%',
			height: '100%',		
			id: "_" + ("" + Math.random()).slice(9),
			
			// flashembed defaults
			allowfullscreen: true,
			allowFullScreenInteractive: true,
			allowscriptaccess: 'always',
			quality: 'high',	
			
			// flashembed specific options
			version: [3, 0],
			onFail: null,
			expressInstall: null, 
			w3c: false,
			cachebusting: false  		 		 
	};
	
	// version 9 bugfix: (http://blog.deconcept.com/2006/07/28/swfobject-143-released/)
	if (window.attachEvent) {
		window.attachEvent("onbeforeunload", function() {
			__flash_unloadHandler = function() {};
			__flash_savedUnloadHandler = function() {};
		});
	}
	
	// simple extend
	function extend(to, from) {
		if (from) {
			for (var key in from) {
				if (from.hasOwnProperty(key)) {
					to[key] = from[key];
				}
			}
		} 
		return to;
	}	

	// used by asString method	
	function map(arr, func) {
		var newArr = []; 
		for (var i in arr) {
			if (arr.hasOwnProperty(i)) {
				newArr[i] = func(arr[i]);
			}
		}
		return newArr;
	}

	window.flashembed = function(root, opts, conf) {
	
		// root must be found / loaded	
		if (typeof root == 'string') {
			root = document.getElementById(root.replace("#", ""));
		}
		
		// not found
		if (!root) { return; }
		
		root.onclick = function(){return false;}
		
		if (typeof opts == 'string') {
			opts = {src: opts};	
		}

		return new Flash(root, extend(extend({}, GLOBAL_OPTS), opts), conf); 
	};	
	
	// flashembed "static" API
	var f = extend(window.flashembed, {
		
		conf: GLOBAL_OPTS,
	
		getVersion: function()  {
			var fo, ver;
			
			try {
				ver = navigator.plugins["Shockwave Flash"].description.slice(16); 
			} catch(e) {
				
				try  {
					fo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");
					ver = fo && fo.GetVariable("$version");
					
				} catch(err) {
                try  {
                    fo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");
                    ver = fo && fo.GetVariable("$version");  
                } catch(err2) { } 						
				} 
			}
			
			ver = RE.exec(ver);
			return ver ? [ver[1], ver[3]] : [0, 0];
		},
		
		asString: function(obj) { 

			if (obj === null || obj === undefined) { return null; }
			var type = typeof obj;
			if (type == 'object' && obj.push) { type = 'array'; }
			
			switch (type){  
				
				case 'string':
					obj = obj.replace(new RegExp('(["\\\\])', 'g'), '\\$1');
					
					// flash does not handle %- characters well. transforms "50%" to "50pct" (a dirty hack, I admit)
					obj = obj.replace(/^\s?(\d+\.?\d+)%/, "$1pct");
					return '"' +obj+ '"';
					
				case 'array':
					return '['+ map(obj, function(el) {
						return f.asString(el);
					}).join(',') +']'; 
					
				case 'function':
					return '"function()"';
					
				case 'object':
					var str = [];
					for (var prop in obj) {
						if (obj.hasOwnProperty(prop)) {
							str.push('"'+prop+'":'+ f.asString(obj[prop]));
						}
					}
					return '{'+str.join(',')+'}';
			}
			
			// replace ' --> "  and remove spaces
			return String(obj).replace(/\s/g, " ").replace(/\'/g, "\"");
		},
		
		getHTML: function(opts, conf) {

			opts = extend({}, opts);
			
			/******* OBJECT tag and it's attributes *******/
			var html = '<object width="' + opts.width + 
				'" height="' + opts.height + 
				'" id="' + opts.id + 
				'" name="' + opts.id + '"';
			
			if (opts.cachebusting) {
				opts.src += ((opts.src.indexOf("?") != -1 ? "&" : "?") + Math.random());		
			}			
			
			if (opts.w3c || !IE) {
				html += ' data="' +opts.src+ '" type="application/x-shockwave-flash"';		
			} else {
				html += ' classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"';	
			}
			
			html += '>'; 
			
			/******* nested PARAM tags *******/
			if (opts.w3c || IE) {
				html += '<param name="movie" value="' +opts.src+ '" />'; 	
			} 
			
			// not allowed params
			opts.width = opts.height = opts.id = opts.w3c = opts.src = null;
			opts.onFail = opts.version = opts.expressInstall = null;
			
			for (var key in opts) {
				if (opts[key]) {
					html += '<param name="'+ key +'" value="'+ opts[key] +'" />';
				}
			}	
		
			/******* FLASHVARS *******/
			var vars = "";
			
			if (conf) {
				for (var k in conf) { 
					if (conf[k]) {
						var val = conf[k]; 
						vars += k +'='+ (/function|object/.test(typeof val) ? f.asString(val) : val) + '&';
					}
				}
				vars = vars.slice(0, -1);
				html += '<param name="flashvars" value=\'' + vars + '\' />';
			}
			
			html += "</object>";	
			
			return html;				
		},
		
		isSupported: function(ver) {
			return VERSION[0] > ver[0] || VERSION[0] == ver[0] && VERSION[1] >= ver[1];			
		}		
		
	});
	
	var VERSION = f.getVersion(); 
	
	function Flash(root, opts, conf) {  
	                                                
		// version is ok
		if (f.isSupported(opts.version)) {
			root.innerHTML = f.getHTML(opts, conf);
			
		// express install
		} else if (opts.expressInstall && f.isSupported([6, 65])) {
			root.innerHTML = f.getHTML(extend(opts, {src: opts.expressInstall}), {
				MMredirectURL: location.href,
				MMplayerType: 'PlugIn',
				MMdoctitle: document.title
			});	
			
		} else {
			
			// fail #2.1 custom content inside container
			if (!root.innerHTML.replace(/\s/g, '')) {
				/* root.innerHTML = 
					"<h2>Flash version " + opts.version + " or greater is required</h2>" + 
					"<h3>" + 
						(VERSION[0] > 0 ? "Your version is " + VERSION : "You have no flash plugin installed") +
					"</h3>" + 
					
					(root.tagName == 'A' ? "<p>Click here to download latest version</p>" : 
						"<p>Download latest version from <a href='" + URL + "'>here</a></p>");
				*/
				var pageHost = ((document.location.protocol == "https:") ? "https://" :	"http://");
				
				root.innerHTML = "<a href='http://www.adobe.com/go/getflashplayer'><img src='" 
										+ pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>";
											
				if (root.tagName == 'A') {	
					root.onclick = function() {
						location.href = URL;
					};
				}				
			}
			
			// onFail
			if (opts.onFail) {
				var ret = opts.onFail.call(this);
				if (typeof ret == 'string') { root.innerHTML = ret; }	
			}			
		}
		
		// http://flowplayer.org/forum/8/18186#post-18593
		if (IE) {
			window[opts.id] = document.getElementById(opts.id);
		} 
		
		// API methods for callback
		extend(this, {
				
			getRoot: function() {
				return root;	
			},
			
			getOptions: function() {
				return opts;	
			},

			
			getConf: function() {
				return conf;	
			}, 
			
			getApi: function() {
				return root.firstChild;	
			}
			
		}); 
	}
	
	// setup jquery support
	if (JQUERY) {
		
		// tools version number
		jQuery.tools = jQuery.tools || {version: '1.2.5'};
		
		jQuery.tools.flashembed = {  
			conf: GLOBAL_OPTS
		};	
		
		jQuery.fn.flashembed = function(opts, conf) {		
			return this.each(function() { 
				$(this).data("flashembed", flashembed(this, opts, conf));
			});
		}; 
	} 
	
})();