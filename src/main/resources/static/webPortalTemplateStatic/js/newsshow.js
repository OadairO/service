var cpxx;

function getCpxx(){
	var productId = getParam("productId");
	var userid = globalUserData.id;
	if(productId != undefined){
		cpxx = ajaxAsyncGet("../../getProductById?productId="+productId+"&userid="+userid)[0];
	}
}
getCpxx();

function productName(){
	html = '';
	$("#productName").empty();
	 html += '<h1>';
	 html += cpxx.productName;
	 html += '</h1>';
	 html += '<div class="info">';
	 html += '<span>';
	 html += cpxx.productTime;;
	 html += '</span>';
	 html += '<span>';
	 html += cpxx.userName;
	 html += '</span>';
	 html += '<span>';
	 html += '<i class="icon wb-eye margin-right-5" aria-hidden="true">';
	 html += '</i></span></div>';
	 
	 $("#productName").html(html);
}
productName();


function content(){
	html = '';
	$("#content").empty();
	
	 html += '<div>';
	 html += cpxx.content;
	 html += '<div id="metinfo_additional"></div>';
	 html += '</div>';
	 
	 $("#content").html(html);
}
content();

function hotproduct(){
		var R = ajaxAsyncGet("../../getProductTale?news=true&userid="+globalUserData.id+"&limit=4");
		html = '';
		$("#hotproduct").empty();
		if(R != undefined && R.data.length > 0){
			for(var i=0; i < R.data.length; i++){
	            html += '<li class="list-group-item">';
	            html += '<a href="newsshow.html?productId='+R.data[i].id+'"​ title="'+R.data[i].productName+'" target="_self">';
	            html += R.data[i].productName;
	            html += '</a></li>';
			}
		}
		$("#hotproduct").html(html);
}
hotproduct();

function previous(){
		var R = ajaxAsyncGet("../../getPreviousProductById?productId="+cpxx.id+"&news=true");
		html = '';
		$("#previous").empty();
		if(R != undefined && R.data != null ){
	            html += '<a href="newsshow.html?productId='+R.data[0].id+'"> 上一篇:';
	            html += '<span aria-hidden="true" class="hidden-xs">';
	            html += R.data[0].productName;
	            html += '</span></a>';
		}
		$("#previous").html(html);
}
previous();
function next(){
	var R = ajaxAsyncGet("../../getNextProductById?productId="+cpxx.id+"&news=true");
	html = '';
	$("#next").empty();
	if(R != undefined && R.data != null ){
            html += '<a href="newsshow.html?productId='+R.data[0].id+'"> 下一篇:';
            html += '<span aria-hidden="true" class="hidden-xs">';
            html += R.data[0].productName;
            html += '</span></a>';
	}
	$("#next").html(html);
}
next();




