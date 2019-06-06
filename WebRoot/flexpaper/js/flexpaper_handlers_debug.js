/**
 █▒▓▒░ The FlexPaper Project

 This file is part of FlexPaper.

 FlexPaper is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, version 3 of the License.

 FlexPaper is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with FlexPaper.  If not, see <http://www.gnu.org/licenses/>.

 For more information on FlexPaper please see the FlexPaper project
 home page: http://flexpaper.devaldi.com
 */

$(function() {
    /**
     * Handles the event of external links getting clicked in the document.
     *
     * @example onExternalLinkClicked("http://www.google.com")
     *
     * @param String link
     */
    jQuery('#documentViewer').bind('onExternalLinkClicked',function(e,link){
        jQuery("#txt_eventlog").val('onExternalLinkClicked:' + link + '\n' + jQuery("#txt_eventlog").val());
        window.open(link,'_flexpaper_exturl');
    });

    /**
     * Recieves progress information about the document being loaded
     *
     * @example onProgress( 100,10000 );
     *
     * @param int loaded
     * @param int total
     */
    jQuery('#documentViewer').bind('onProgress',function(e,loadedBytes,totalBytes){
        jQuery("#txt_progress").val('onProgress:' + loadedBytes + '/' + totalBytes + '\n');
    });

    /**
     * Handles the event of a document is in progress of loading
     *
     */
    jQuery('#documentViewer').bind('onDocumentLoading',function(e){
        jQuery("#txt_eventlog").val('onDocumentLoading' + '\n' + jQuery("#txt_eventlog").val());
    });

    /**
     * Handles the event of a document is in progress of loading
     *
     */
    jQuery('#documentViewer').bind('onPageLoading',function(e,pageNumber){
        jQuery("#txt_eventlog").val('onPageLoading:' + pageNumber + '\n' + jQuery("#txt_eventlog").val());
    });

    /**
     * Receives messages about the current page being changed
     *
     * @example onCurrentPageChanged( 10 );
     *
     * @param int pagenum
     */
    jQuery('#documentViewer').bind('onCurrentPageChanged',function(e,pagenum){
    	//页面改变之后重新加载当前页的标签笔记拷贝情况
    	jQuery.ajax({
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
	          		sss += "<a style=\"margin: 0px;padding: 0px;border-bottom: none;background: none;\" onclick=\"$FlexPaper('documentViewer').gotoPage('"+arr[i].pages.number+"')\">"+arr[i].pages.number+"__"+arr[i].noteContent+"</a>"
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
		          	$("#bbbbb").css("display","inline-block");
		          	$("#copy_but").css("margin-left","70px");
	          }else{
	          		$("#copy_but").removeAttr("style");
		    	    $("#bbbbb").css("display","none");
		      }
            },  
            error : function(abc) {
            }  
		});
        jQuery("#txt_eventlog").val('onCurrentPageChanged:' + pagenum + '\n' + jQuery("#txt_eventlog").val());
    });

    /**
     * Receives messages about the document being loaded
     *
     * @example onDocumentLoaded( 20 );
     *
     * @param int totalPages
     */
    jQuery('#documentViewer').bind('onDocumentLoaded',function(e,totalPages){
        jQuery('#documentViewer').show();

        jQuery("#txt_eventlog").val('onDocumentLoaded:' + totalPages + '\n' + jQuery("#txt_eventlog").val());
    });

    /**
     * Receives messages about the page loaded
     *
     * @example onPageLoaded( 1 );
     *
     * @param int pageNumber
     */
    jQuery('#documentViewer').bind('onPageLoaded',function(e,pageNumber){
        jQuery("#txt_eventlog").val('onPageLoaded:' + pageNumber + '\n' + jQuery("#txt_eventlog").val());
    });

    /**
     * Receives messages about the page loaded
     *
     * @example onErrorLoadingPage( 1 );
     *
     * @param int pageNumber
     */
    jQuery('#documentViewer').bind('onErrorLoadingPage',function(e,pageNumber){
        jQuery("#txt_eventlog").val('onErrorLoadingPage:' + pageNumber + '\n' + jQuery("#txt_eventlog").val());
    });

    /**
     * Receives error messages when a document is not loading properly
     *
     * @example onDocumentLoadedError( "Network error" );
     *
     * @param String errorMessage
     */
    jQuery('#documentViewer').bind('onDocumentLoadedError',function(e,errMessage){
        jQuery("#txt_eventlog").val('onDocumentLoadedError:' + errMessage + '\n' + jQuery("#txt_eventlog").val());
    });

    /**
     * Receives error messages when a document has finished printed
     *
     * @example onDocumentPrinted();
     *
     */
    jQuery('#documentViewer').bind('onDocumentPrinted',function(e){
        jQuery("#txt_eventlog").val('onDocumentPrinted\n' + jQuery("#txt_eventlog").val());
    });
});