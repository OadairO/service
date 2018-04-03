 
//获取产品类别
function producttypelist(){
	var R = ajaxAsyncGet("../../getProducttypeList?userid="+globalUserData.id);
	html = '';
	if(R != undefined && R.length > 0){
		for(var i=0; i < R.length; i++){
            html += '<li>';
            html += '<a href="javascript:void(0);" class="link" onclick="producttype('+R[i].id+')">';
            html += R[i].name;
            html += '</a>';
            html += '</li>';
		}
	}
	$("#producttypelist").empty();
	$("#producttypelist").html(html);
}

producttypelist();

function producttype(id){
	getProductTale(undefined,id);
	metpager();
}
var tabledata;
//获取产品列表
function getProductTale(page,cple){
	var url ="../../getProductTale?userid="+globalUserData.id+"&limit=8";
	if(page != undefined){
		url = url +"&page="+page;
	}
	if(cple != null){
		url = url+"&cple="+cple;
	}
	var R = ajaxAsyncGet(url);
	tabledata = R;
	html = '';
	$("#met-grid").empty();
	if(R != undefined && R.data.length > 0){
		for(var i=0; i < R.data.length; i++){
            html += '<li class=" shown">';
            html += '<div class="widget widget-shadow">';
            html += '<figure class="widget-header cover">';
            html += '<a href="productshow.html?product='+R.data[i].id+'" title="'+R.data[i].productName+'" target="_self">';
            html += '<img class="cover-image tableImg"  src="'+R.data[i].productImg+'" alt="'+R.data[i].productName+'" />';
            html += '</a>';
            html += '</figure>';
            html += '<div class="widget-body">';
            html += '<h4 class="widget-title">'+R.data[i].productName+'</h4><p>';
            html += getAbstract(R.data[i].abstract);
            html += '</p><div class="widget-body-footer">';
            html += '<div class="widget-actions pull-right">';
            html += '<a href="productshow.html?product='+R.data[i].id+'" title="'+R.data[i].productName+'">';
            /*html += '<i class="icon wb-eye" aria-hidden="true"></i>';
            html += '<span>36</span>';*/
            html += '</a>';
            html += '</div>';
            html += '<a href="productshow.html?product='+R.data[i].id+'" title="'+R.data[i].productName+'" target="_self" class="btn btn-outline btn-primary btn-squared">';
            html += '查看详情';
            html += '</a>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
            html += '</li>';
		}
	}
	$("#met-grid").html(html);
}

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

var cple = getParam("cple");
if(cple != undefined){
	getProductTale(undefined,cple);
}else {
	getProductTale(undefined,null);
}

function metpager(){
	$("#pagination").pagination({
		currentPage: tabledata.page,
		totalPage: tabledata.count/8,
		isShow: true,
		count: tabledata.count,
		homePageText: "首页",
		endPageText: "尾页",
		prevPageText: "上一页",
		nextPageText: "下一页",
		callback: function(current) {
			getProductTale(current,null);
		}
	});
}
metpager();


