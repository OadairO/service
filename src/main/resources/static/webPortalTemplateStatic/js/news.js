
var tabledata;
//获取产品列表
function getProductTale(page,cple){
	var url ="../../getProductTale?news=true&userid="+globalUserData.id+"&limit=8";
	if(page != undefined){
		url = url +"&page="+page;
	}
	if(cple != null){
		url = url+"&cple="+cple;
	}
	
	tabledata = ajaxAsyncGet(url);
	html = '';
	$("#met-page-ajax").empty();
	if(tabledata != undefined && tabledata.data != null && tabledata.data.length > 0){
		for(var i=0; i < tabledata.data.length; i++){
            html += '<li>';
            html += '<h4>';
            html += '<a href="newsshow.html?productId='+tabledata.data[i].id+'"​ title="'+tabledata.data[i].productName+'" target="_self">';
            html += tabledata.data[i].productName;
            html += '</a>';
            html += '</h4>';
            html += '<p class="des">';
            html += tabledata.data[i].abstract;
            html += '</p>';
            html += '<p class="info"><span>';
            html += tabledata.data[i].productTime;
            html += '</span>';
            html += '<span>';
            html += 'admin';
            html += '</span>';
            html += '<span>';
           /* html += '<i class="icon wb-eye margin-right-5" aria-hidden="true">';
            html += '</i>109</span>';*/
            html += '</p></li>';
		}
	}
	$("#met-page-ajax").html(html);
}

var cple = getParam("cple");
if(cple != undefined){
	getProductTale(undefined,cple);
}else {
	getProductTale(undefined,null);
}

function hotproduct(){
	var R = ajaxAsyncGet("../../getProductTale?news=true&userid="+globalUserData.id+"&limit=4");
	html = '';
	$("#hotproduct").empty();
	if(R != undefined && R.data.length > 0){
		for(var i=0; i < R.data.length; i++){
            html += '<li class="list-group-item">';
            html += '<a href="newsshow.html?product='+R.data[i].id+'"​ title="'+R.data[i].productName+'" target="_self">';
            html += R.data[i].productName;
            html += '</a></li>';
		}
	}
	$("#hotproduct").html(html);
}
hotproduct();

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