$(document).ready(function(e) {
	$(".meaudown").mouseover(function(){
	  $(this).children("ul").css('display','block');
  });
    $(".meaudown").mouseleave(function(){
	  $(this).children("ul").css('display','none');
  });

/*滚动条滚动固定*/
var nav=$(".scrollDiv"); //得到导航对象
var win=$(window); //得到窗口对象
var sc=$(document);//得到document文档对象。
win.scroll(function(){
  if(sc.scrollTop()>=235){
	nav.addClass("fixednav"); 
  }else{
   nav.removeClass("fixednav");
  };
  if(win.scrollTop()+sc.height()>=$(".chineseLeft").height()){
	  $(".fixednav").css("top","-750px");
  }
  //alert(win.scrollTop())
});
/*if($(".chineseLeft").height()>$(window).height()){
	  nav.addClass("fixednav").css("top","-1200px")
  }*/
/*滚动条滚动固定*/
    
/*鼠标移过某个按钮 高亮显示*/
	$(".prev,.next").hover(function(){
		$(this).fadeTo("show",0.7);
	},function(){
		$(this).fadeTo("show",0.1);
	})
	$(".outer").length && $(".outer").slide({ titCell:".btn ul", mainCell:".inner", effect:"fold", autoPlay:true, interTime : 5000, delayTime:2000 , autoPage:true});	
	
	
	$("li.Stab").click(function(){
	$(this).addClass("StabSeleted").siblings().removeClass("StabSeleted");
	var nIndex=$(this).index();
	$(this).parent().siblings().removeClass("ScontentSelected").eq(nIndex).addClass("ScontentSelected");	
	});
		
	
	
	$aLis=$(".updownUl").children("li:nth-child(n+6)");
	$aLis.hide();
	var $toggleBtn = $('span.updownMore > a');
	$toggleBtn.click(function(){
		var $oLis=$(this).parent().siblings("ul").children("li:nth-child(n+6)");
		 if($oLis.is(":visible")){
				$oLis.hide();                  
				$(this).text("更多...");                 	
		}else{
				$oLis.show();                   		
				$(this).text("更少...");                
			}
		return false;					      	
	});
	
	
	function initMenu() {
	  $('#menu ul').hide();
  	  $('#menu ul:first').show();
	  $('#menu li a').click(
		function() {
		  var checkElement = $(this).next();
		  if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
			$('#menu ul:visible').slideUp('normal');
			checkElement.slideDown('normal');
			return false;
			}else{
				$(checkElement).slideToggle('normal');
			}
		  }
		);
	  }
	$(document).ready(function() {initMenu();});

	
})