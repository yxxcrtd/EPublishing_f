/* 
   Simple JQuery Accordion menu.
   HTML structure to use:

   <ul id="menu">
     <li><a href="#">Sub menu heading</a>
     <ul>
       <li><a href="http://site.com/">Link</a></li>
       <li><a href="http://site.com/">Link</a></li>
       <li><a href="http://site.com/">Link</a></li>
       ...
       ...
     </ul>
     <li><a href="#">Sub menu heading</a>
     <ul>
       <li><a href="http://site.com/">Link</a></li>
       <li><a href="http://site.com/">Link</a></li>
       <li><a href="http://site.com/">Link</a></li>
       ...
       ...
     </ul>
     ...
     ...
   </ul>

Copyright 2007 by Marco van Hylckama Vlieg

web: http://www.i-marco.nl/weblog/
email: marco@i-marco.nl

Free for non-commercial use
*/

function initMenu() {
	 // $(".menu div").attr("class","menuH1");
	 // $(".menu ul").css("display","none");
	  //$('#menu ul:first').show();
	  $(".menu div h1 a").click(
	  function() {
			  $(".menu div[class='menuH1_select']").attr("class","menuH1");
			 // if($(".menu ul").is(':visible')){
			//	  $(".menu ul").css("display","none");
			 // }
		      var checkElement = $(this).parent().parent().next();
		      var checkElement2 = $(this).parent().parent();
		      if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
		    	$(".menu ul:visible").first().slideUp("normal");
		        return false;
		       }
		      if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
		    	  checkElement2.attr("class","menuH1_select");
		    	  $(".menu ul:visible").first().slideUp("normal");
		    	  checkElement.first().slideDown("normal");
		    	  return false;
		       }
		  }
   );
 }
$(document).ready(function() {initMenu();});