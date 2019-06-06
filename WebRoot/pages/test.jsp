<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript" src="/EPublishing/js/jquery-1.8.2.js"></script>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv='X-UA-Compatible' content='IE=edge'/>

  </head>
  
  <body>    
<iframe id="content1" src="/EPublishing/pages/login.jsp"  frameborder="1" width="800" height="600">
  	
</iframe>

	<script  type="text/javascript"> 
	var flag=true;
         iframeLoaded(document.getElementById("content1"),malert);

		function iframeLoaded(iframeEl, callback) {
        if(iframeEl.attachEvent) {
            iframeEl.attachEvent("onload", function() {
                if(flag && callback && typeof(callback) == "function") {      
                flag=false;         		
                    callback();                    
                }
            });
        } else {
            iframeEl.onload = function() {
                if(flag &&  callback && typeof(callback) == "function") {
                flag=false;
                    callback();
                }
            }
        }         
    }
    function malert(){
    	document.getElementById("content1").setAttribute("src", "https://www.dawsonera.com/search?q=9781849776714+AND+PRC&sType=ALL&searchFrom=0&sortBy=0&windowScroll=0");
    }

   </script>  
         
  </body>
</html>
