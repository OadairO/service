
layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;
//菜单列表
var tableIns = table.render({
    elem: '#menuList',
    url : "../../getMenuList",
    cellMinWidth : 95,
    page : true,
    height : "full-125",
    limits : [10,15,20,25],
    limit : 20,
    id : "menuListTable",
    cols : [[
       /* {type: "checkbox", fixed:"left", width:50},*/
        {field: 'name', title: '菜单名称', minWidth:50, align:"center"},
        {field: 'url', title: '地址', minWidth:100, align:'center'},
        {field: 'open', title: '是否公开',minWidth:40,align:'center',templet:function(d){
                return d.open == "0" ? "公开" : "隐藏";
            }},
        {field: 'pname', title: '上级菜单',minWidth:50, align:'center'},
        {field: 'type', title: '类型',minWidth:40,  align:'center',templet:function(d){
                return d.type == "0" ? "目录" : "菜单";
            }},
        {field: 'chkDisabled', title: '是否禁用',minWidth:40,  align:'center',templet:function(d){
                return d.chkDisabled == "false" ? "开启" : "禁用";
            }},
        {field: 'icon', title: '图标', align:'center',templet:function(d){
        		return '<span class="fa '+d.icon+' fa-lg"></span>';
        }},
        {title: '操作', minWidth:150, templet:'#menuListBar',fixed:"right",align:"center"}
    ]]
});

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
            table.reload("newsListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    key: $(".searchVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的内容");
        }
    });
    
    //列表操作
    table.on('tool(menuList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
        	addMenu(data);
        }else if(layEvent === 'del'){ //删除
        	if (data.id != null) {
        		 layer.confirm('确定删除？',{icon:3, title:'提示信息'},function(index){
        			 $.ajax({
                         type: "get",
                         url:"../../delMenu?id="+data.id,
                         async: false,
                         //dataType: "json",
                         success: function (R) {
                             if (R === 0) {
                            	 tableIns.reload();
                                 layer.close(index);
                             } else {
                             	 alert("系统错误");
                             }
                         },
                         error: function () {
                             alert("系统错误");
                         }
                     });
                 });
            }
        }
    });
    
    //添加菜单
    function addMenu(edit){
        var index = layui.layer.open({
            title : "添加菜单",
            type : 2,
            content : "menuAdd.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                	body.find("#menuId").val(edit.id);  //类型
                	if(edit.type == "0"){
                		body.find("#menuMl").attr('checked',true);//目录
                	}else{
                		body.find("#menuCd").attr('checked',true); //菜单
                	}
                    body.find("#name").val(edit.name);  //菜单名称
                    body.find("#parentName").val(edit.pname);  //上级菜单
                    body.find("#parentId").val(edit.pnameId);  //上级菜单
                    body.find("#url").val(edit.url);  //菜单URL
                    body.find("#order_num").val(edit.order_num);  //排序号
                    body.find("#icon").val(edit.icon);  //图标
                    form.render();
                }
            }
        })
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        })
    }
    $(".addMenu_btn").click(function(){
    	addMenu();
    })
    
})

//数据渲染对象
var Render = {
    /**
     *  rowdata：当前行数据
     *  index  ：当前第几行
     *  value  ：当前渲染列的值
     */
    //渲染状态列
    customState: function (rowdata,renderData, index, value) {
        if (value === 0) {
            return '<span class="layui-btn layui-btn-mini layui-btn-warm">目录</span>';
        }
        if (value === 1) {
            return '<span class="layui-btn layui-btn-mini layui-btn-sys">菜单</span>';
        }
        if (value === 2) {
            return '<span class="layui-btn layui-btn-mini layui-btn-green">按钮</span>';
        }
        if (value == "" || value == null) {
            return "";
        }
        return value;
    },
    //渲染操作方法列
    customIcon: function (rowdata,renderData, index, value) {
        if (rowdata.icon == "" || rowdata.icon == null) {
            return "";
        }
        var result = '<i class="' + rowdata.icon + ' "></i>';
        return result;
    }

};
