var globalUserData;
var globalMenuData;

function islog(){
	$.ajax({
        type: "get",
        url: "json/portalUser.json", 
        async: false,
        success: function (R) {
        	if(R == "" || R == null || R == undefined ){
  			  if (window != top){
  				  top.location.href = "../../404.html";  
  			  }else {
  	    		  window.location.href = "../../404.html";
  				}
	  		  }else {
	  			  globalUserData = R;
	  			  getMenu(globalUserData.id);
	  		}
        }
    });
}
islog();
function getMenu(userid){
	$.ajax({
        type: "get",
        url: "../../getPortalMenu?userid="+userid, 
        async: false,
        success: function (R) {
        	globalMenuData = R;
        }
    });
}
//ajax同步请求
function ajaxAsyncGet(url){
	var data = null;
	$.ajax({
        type: "GET",
        url: url, 
        async: false,
        success: function (R) {
        	data = R;
        },
        fail: function (status) {
            alert("请求失败！"+url);
        }
    });
	return data;
}
//ajax异步请求
function ajaxGet(url){
	var data = null;
	$.ajax({
        type: "GET",
        url: url, 
        success: function (R) {
        	data = R;
        },
        fail: function (status) {
            alert("请求失败！"+url);
        }
    });
	return data;
}
//ajax同步请求
function ajaxAsyncPost(){
	
}
//ajax异步请求
function ajaxPost(){
	
}

function getParam(paramName) { 
    paramValue = "", isFound = !1; 
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) { 
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0; 
        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++ 
    } 
    return paramValue == "" && (paramValue = null), paramValue 
} 

