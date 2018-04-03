
var localhostPaht = "http://localhost:8080/";
var globalUserData;

//检查用户是否登录
function islog(){
	$.get(localhostPaht + "isLog", function (R) {
		if(R == "" || R == null || R == undefined ){
			  if (window != top){
				  top.location.href = localhostPaht + "login.html";  
			  }else {
	    		  window.location.href = localhostPaht + "login.html";
				}
		  }else {
			  globalUserData = R;
		}
	});
}
var wlpathname = window.location.pathname;
if(wlpathname.indexOf("login.html") == -1)islog();
