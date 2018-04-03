
layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;
//菜单列表
var tableIns = table.render({
    elem: '#menuList',
    url : "../../../getPortalMenuList",
    cellMinWidth : 95,
    page : true,
    height : "full-125",
    limits : [10,15,20,25],
    limit : 20,
    id : "menuListTable",
    cols : [[
        {field: 'name', title: '菜单名称', minWidth:50, align:"center"},
        {field: 'url', title: '地址', minWidth:100, align:'center'},
        {field: 'del', title: '是否开启',minWidth:40,  align:'center',templet:function(d){
           if(d.del == 0){
        	   return '<input type="checkbox" value="'+d.id+'" name="controlKg" lay-filter="controlKg" lay-skin="switch" lay-text="是|否" checked>'
           }else {
        	   return '<input type="checkbox" value="'+d.id+'" name="controlKg" lay-filter="controlKg" lay-skin="switch" lay-text="是|否">'
           }
        }},
        {title: '操作', minWidth:150, templet:'#menuListBar',fixed:"right",align:"center"}
    ]]
});

//列表操作
table.on('tool(menuList)', function(obj){
    var layEvent = obj.event,
        data = obj.data;
    if(layEvent === 'edit'){ //编辑
    	 layer.alert("测试！")
    } else if(layEvent === 'look'){ //预览
    	console.log(data);
    	if(data.del != "0"){
    		 layer.alert("功能还未开启！")
    	}else{
    		url = localhostPaht + "webPortal/"+globalUserData.loginName+"/"+data.url;
    		parent.open(url);
    	}
        
    }
});

form.on('switch(portal)', function(data){
	 var index = layer.msg('修改中，请稍候',{icon: 16,time:false,shade:0.8});
	    setTimeout(function(){
	        layer.close(index);
	        if(data.elem.checked){
	        	PortalUserMenuTJ('../../../savePortalUserMenu');
	        }else{
	        	PortalUserMenuTJ('../../../delPortalUserMenu');
	        }
	    },500);
})

function PortalUserMenuTJ(url){
	$.ajax({
        type: "get",
        url: url, 
        async: false,
        //dataType: "json",
        success: function (R) {
       	 if(R == 0){
       		 layer.msg('操作成功！');
       		 table.reload('menuListTable', {
      			  url: '../../../getPortalMenuList'
      			});
       	 }else {
       		 layer.msg('操作错误，请联系管理员！');
			}
        },
        error: function () {
        	 layer.msg('请求错误！');
        }
    });
}

form.on('switch(controlKg)', function(data){
    var index = layer.msg('修改中，请稍候',{icon: 16,time:false,shade:0.8});
    setTimeout(function(){
        layer.close(index);
        if(data.elem.checked){
        	controlKg(data.value,0);
        }else{
        	controlKg(data.value,1);
        }
    },500);
});
    
function controlKg(menuid,zt){
	$.ajax({
        type: "get",
        url: '../../../updatePortalMenu?menuid='+menuid+"&zt="+zt, 
        async: false,
        success: function (R) {
       	 if(R == 0){
       		 layer.msg('修改成功！');
       		/* table.reload('menuListTable', {
      			  url: '../../getPortalMenuList'
      			});*/
       	 }else {
       		 layer.msg('操作错误，请联系管理员！');
			}
        },
        error: function () {
        	 layer.msg('请求错误！');
        }
    });
}

    
})

