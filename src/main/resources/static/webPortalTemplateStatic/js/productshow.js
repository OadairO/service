
var cpxx;

function getCpxx(){
	var product = getParam("product");
	var userid = globalUserData.id;
	if(product != undefined){
		cpxx = ajaxAsyncGet("../../getProductById?productId="+product+"&userid="+userid)[0];
	}
}
getCpxx();
//轮播图片
function metimgscarousel(){
	html = '';
	$("#met-imgs-carousel").empty();
	 html += '<div class="slick-slide lg-item-box" ';
	 html += 'data-src="'+cpxx.productImg+'"';
	 html += 'data-exthumbimage="'+cpxx.productImg+'">';
	 html += '<span>';
	 html += '<img src="'+cpxx.productImg+'" data-src="'+cpxx.productImg+'"';
	 html += 'class="img-responsive" alt="" />';
	 html += '</span>';
	 html += '</div>';
	 $("#met-imgs-carousel").html(html);
}
metimgscarousel();

function productName(){
	html = '';
	$("#productName").empty();
	 html += '<h1 class="font-weight-300">';
	 html += cpxx.productName;
	 html += '</h1>';
	 html += '<p class="description">';
	 html += cpxx.abstract;
	 html += '</p>';
	 $("#productName").html(html);
}
productName();
 
function productdetails(){
	html = '';
	$("#product-details").empty();
	 html += ' <div>';
	 html += cpxx.content;
	 html += '</div>';
	 $("#product-details").html(html);
}
productdetails();
 
function hotproduct(){
	var R = ajaxAsyncGet("../../getProductTale?userid="+globalUserData.id+"&limit=4");
	html = '';
	$("#hotproduct").empty();
	if(R != undefined && R.data.length > 0){
		for(var i=0; i < R.data.length; i++){
			console.log(R.data[i]);
            html += ' <li>';
            html += '<a href="productshow.html?product='+R.data[i].id+'" class="img" title="'+R.data[i].productName+'">';
            html += '<img data-original="'+R.data[i].productImg+'" class="cover-image"  alt="'+R.data[i].productName+'">';
            html += '</a><a href="productshow.html?product='+R.data[i].id+'" class="txt" title="'+R.data[i].productName+'">';
            html += R.data[i].productName;
            html += '</a></li>';
		}
	}
	$("#hotproduct").html(html);
}
hotproduct();









