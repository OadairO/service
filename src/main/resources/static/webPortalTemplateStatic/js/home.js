

function hotproduct(){
	var R = ajaxAsyncGet("../../getProductTale?userid="+globalUserData.id+"&limit=4");
	html = '';
	$("#hotproduct").empty();
	if(R != undefined && R.data.length > 0){
		for(var i=0; i < R.data.length; i++){
			 html += '<li class="">';
            html += '<div class="widget widget-article widget-shadow">';
            html += '<div class="widget-header cover overlay overlay-hover">';
            html += '<img class="cover-image overlay-scale" src="'+R.data[i].productImg+'" alt="">';
            html += '</div>';
            html += '<div class="widget-body">';
            html += '<h3 class="widget-title">';
            html += R.data[i].productName;
            html += '</h3>';
            html += '<p class="des">';
            html += getAbstract(R.data[i].abstract);
            html += '</p>';
            html += '<div class="widget-body-footer">';
            html += '<a class="btn btn-info waves-effect waves-light" href="productshow.html?product='+R.data[i].id+'">';
            html += '查看详情';
            html += '</a>';
            /*html += '<div class="widget-actions pull-right">';
            html += '<a href="#"​>关注人数：57</a>';
            html += '</div>';*/
            html += '</div>';
            html += '</div>';
            html += '</div>';
            html += '</li>';
		}
	}
	$("#hotproduct").html(html);
}
hotproduct();

function getAbstract(abstract){
	if(abstract.length > 30){
		abstract = abstract.substring(0,30)+"....";
	}else{
		var s = 50 - abstract.length;
		for(i=0;i<s;i++){
			abstract = abstract + "..";
		}
	}
	return abstract;
}

//获取产品类别
function producttypelist(){
	var R = ajaxAsyncGet("../../getProducttypeList?userid="+globalUserData.id);
	html = '';
	if(R != undefined && R.length > 0){
		for(var i=0; i < R.length; i++){
            html += '<li>';
            html += '<a href="product.html?cple='+R[i].id+'" title="'+R[i].name+'">';
            html += '<h3>';
            html += R[i].name;
            html += '</h3></a></li>';
		}
	}
	$("#producttypelist").empty();
	$("#producttypelist").html(html);
}
producttypelist();



//行业资讯
function hezxlb(){
	var url ="../../getProductTale?news=true&cple=2&userid="+globalUserData.id+"&limit=8";
	var R = ajaxAsyncGet(url).data;
	html = '';
	if(R != undefined && R.length > 0){
		for(var i=0; i < R.length; i++){
            html += '<li class="">';
            html += '<span class="badge badge-radius badge-default color">';
            html += i+1;
            html += ' </span>';
            html += ' <a href="newsshow.html?productId='+R[i].id+'" title="'+R[i].productName+'" target="_self">';
            html += R[i].productName;
            html += '</a>';
            html += '<span>[';
            html += R[i].productTime.substring(5,10);
            html += ']</span></li>';
		}
	}
	$("#hezxlb").empty();
	$("#hezxlb").html(html);
}
hezxlb();

//行业资讯
function imgnewsslick(){
	var url ="../../getProductTale?news=true&cple=1&userid="+globalUserData.id+"&limit=8";
	var R = ajaxAsyncGet(url).data;
	html = '';
	if(R != undefined && R.length > 0){
		for(var i=0; i < R.length; i++){
            html += '<div >';
            html += '<a href="newsshow.html?productId='+R[i].id+'" title="'+R[i].productName+'" target="_self">';
            html += '<div class="text">';
            html += '<span class="icon">';
            html += '<img src="picture/1514428334.png" alt="">';
            html += '</span>';
            html += '<h3>';
            html += '<span>';
            html += '01';
            html += '</span>';
            html += R[i].productName;
            html += ' </h3>';
            html += '<p class="des">';
            html += R[i].abstract;
            html += '</p>';
            html += '</div>';
            html += '<img class="cover-image" src="picture/1-1p1031k1590-l.jpg" sizes="(max-width: 991px) 991px" alt="">';
            html += '</a>';
            html += '</div>';
		}
	}
	$("#imgnews-slick").empty();
	$("#imgnews-slick").html(html);
}
imgnewsslick();


function indexprolist(){
	var url ="../../getProductTale?userid="+globalUserData.id+"&limit=8";
	var R = ajaxAsyncGet(url);
	tabledata = R;
	html = '';
	$("#indexprolist").empty();
	if(R != undefined && R.data.length > 0){
		for(var i=0; i < R.data.length; i++){
            html += '<li>';
            html += '<div class="widget">';
            html += '<figure class="widget-header cover">';
            html += '<a href="productshow.html?product='+R.data[i].id+'" title="'+R.data[i].productName+'" target="_self">';
            html += '<img class="cover-image"  src="'+R.data[i].productImg+'" style="height:300px;" alt="'+R.data[i].productName+'" />';
            html += '</a>';
            html += '</figure>';
            html += '<h4 class="widget-title">';
            html += '<a href="productshow.html?product='+R.data[i].id+'" title="'+R.data[i].productName+'" target="_self">';
            html += R.data[i].productName;
            /*html += '<span><i class="fa fa-eye"></i>121人浏览</span>';*/
            html += '</a>';
            html += '</h4>';
            html += '</div></li>';
		}
	}
	$("#indexprolist").html(html);
}
indexprolist();