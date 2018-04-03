
function scMenu(){
	var ulHtml = '';
	if(globalMenuData != undefined){
		for(var i=0; i < globalMenuData.length; i++){
			if(globalMenuData[i].children.length > 0){//有子菜单
				ulHtml += ' <li class="dropdown margin-left-20">';
				ulHtml += '<a class="dropdown-toggle link " data-toggle="dropdown" data-hover="dropdown" aria-expanded="false" ';
				if(globalMenuData[i].url == null || globalMenuData[i].url == undefined){
					ulHtml += 'href="javascript:void(0)">';
				}else {
					ulHtml += 'href="'+globalMenuData[i].url+'">';
				}
				ulHtml += globalMenuData[i].name;
				ulHtml += '<span class="caret"></span>';
				ulHtml += '</a>';
				ulHtml += '<ul class="dropdown-menu dropdown-menu-right bullet">';
				for(var j=0; j < globalMenuData[i].children.length; j++){
					ulHtml += '<li>';
					ulHtml += '<a href="'+globalMenuData[i].children[j].url+'"​ class="" title="'+globalMenuData[i].children[j].name+'">';
					ulHtml += globalMenuData[i].children[j].name;
					ulHtml += '</a></li>';
				}
				ulHtml += '</ul>';
				ulHtml += '</li>';
			}else {
				ulHtml += '<li class="dropdown margin-left-20">';
				ulHtml += '<a href="'+globalMenuData[i].url+'"​ title="'+globalMenuData[i].name+'" class="link">';
				ulHtml += globalMenuData[i].name;
				ulHtml += '</a>';
				ulHtml += '<ul class="dropdown-menu dropdown-menu-right bullet"></ul>';
				ulHtml += '</li>';
			}
			
		 }
	}
	$("#useermenu").html(ulHtml);
}
scMenu();
function bthreed(){
	$("#bthreed").empty();
	html = globalUserData.username;
	$("#bthreed").html(html);
}
bthreed();
