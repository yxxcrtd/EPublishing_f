// JavaScript Document
function scrollDoor(){
}

scrollDoor.prototype = {
onlyMenu : function(menus,openClass,closeClass){ // only menu no have content
		var _this = this;
		for(var i = 0 ; i < menus.length ; i++)
		{	
			_this.$(menus[i]).flag = ++this.value;
			_this.$(menus[i]).value = i;
			_this.$(menus[i]).onclick = function(){										
				for(var j = 0 ; j < menus.length ; j++)
				{						
					_this.$(menus[j]).className = closeClass;
					//_this.$(divs[j]).style.display = "none";					
				}				
				_this.$(menus[this.value]).className = openClass;	
				//_this.$(divs[this.value]).style.display = "block";				
			}
		}
		
		
	},
sd : function(menus,divs,openClass,closeClass){//鼠标经过切换
	var _this = this;
	if(menus.length != divs.length)
	{
		alert("菜单层数量和内容层数量不一样!");
		return false;
	}				
	for(var i = 0 ; i < menus.length ; i++)
	{	
		_this.$(menus[i]).value = i;				
		_this.$(menus[i]).onclick = function(){
					
			for(var j = 0 ; j < menus.length ; j++)
			{						
				_this.$(menus[j]).className = closeClass;
				_this.$(divs[j]).style.display = "none";
			}
			_this.$(menus[this.value]).className = openClass;	
			_this.$(divs[this.value]).style.display = "block";				
		}
	}
 },
 
sdc : function(menus,divs,openClass,closeClass){//鼠标点击切换
	var _this = this;
	if(menus.length != divs.length)
	{
		alert("菜单层数量和内容层数量不一样!");
		return false;
	}				
	for(var i = 0 ; i < menus.length ; i++)
	{	
		_this.$(menus[i]).value = i;				
		_this.$(menus[i]).onclick = function(){
					
			for(var j = 0 ; j < menus.length ; j++)
			{						
				_this.$(menus[j]).className = closeClass;
				_this.$(divs[j]).style.display = "none";
			}
			_this.$(menus[this.value]).className = openClass;	
			_this.$(divs[this.value]).style.display = "block";				
		}
	}
 },
 
  
$ : function(oid){
	if(typeof(oid) == "string")
	return document.getElementById(oid);
	return oid;
}
}







    //-->
	
	
	
	
	var last_div;  //多个内容联动的显示隐藏，打开某个另外的收起
        function click_b(divs,menus,divDisplay,o)
        {
			for(var i = 0 , j = divs.length ; i < j ; i++){
				//alert(divDisplay + "|" + divs[i]);
				if(divDisplay != divs[i]){
					document.getElementById(divs[i]).style.display = "none";
				}
			}
			for(var i = 0 , j = menus.length ; i < j ; i++){
				//alert(divDisplay + "|" + divs[i]);
				if(o.id != menus[i]){
					document.getElementById(menus[i]).className = "hshow_off";
				}
			}
			
            var obj = document.getElementById(divDisplay);
            if(obj.style.display != "block")
            {
				o.className = "hshow_on";
                if (last_div) last_div.style.display = "none";
                obj.style.display = "block";
                last_div = obj;
            }
            else
            {	o.className = "hshow_off";
				
                obj.style.display = "none";
                last_div = null;
            }
        }


	var last_div2;  //针对单个内容的显示隐藏
        function click_a(divDisplay)
        {
						
            var obj = document.getElementById(divDisplay);
            if(obj.style.display != "block")
            {
                if (last_div2) last_div2.style.display = "none";
                obj.style.display = "block";
                last_div2 = obj;
            }
            else
            {					
                obj.style.display = "none";
                last_div2 = null;
            }
        }
