layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //用户列表
    var tableIns = table.render({
        elem: '#roleList',
        url : '../../getRoleList',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 20,
        id : "userListTable",
        cols : [[
          /*  {type: "checkbox", fixed:"left", width:50},*/
            {field: 'name', title: '角色名', minWidth:100, align:"center"},
            {field: 'code', title: '角色编码', minWidth:100, align:'center'},
            {field: 'del_flg', title: '状态', align:'center',templet:function(d){
                if(d.del_flg == 0){
                    return "启用";
                }else if(d.del_flg == 1){
                    return "已注销";
                }
            }},
            {title: '操作', minWidth:175, templet:'#roleListBar',fixed:"right",align:"center"}
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
    table.on('tool(roleList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            if (data.id != null) {
            	var urlv=encodeURI("../page/role/roleEdit.html?id="+data.id+"&name="+data.name+"&code="+data.code);
            	var urlv=encodeURI(urlv);
            	parent.layer.open({
                    type: 2,
                    title: '修改',
                    shadeClose: false,
                    shade: [0.3, '#000'],
                    maxmin: true, //开启最大化最小化按钮
                    area: ['893px', '600px'],
                    content:urlv
                });
            }
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此用户？',{icon:3, title:'提示信息'},function(index){
                    tableIns.reload();
                    layer.close(index);
            });
        }
    });

})

 	/**跳转到添加页面*/
    function add(url) {
        //$("body").load(url);
        //type：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
        parent.layer.open({
            type: 2,
            title: '添加',
            shadeClose: false,
            shade: [0.3, '#000'],
            maxmin: true, //开启最大化最小化按钮
            area: ['893px', '600px'],
            content: url,

        });
    }
    /**跳转到修改页面*/
    function edit(table_id, url) {
        var id = getSelectedRow(table_id, url);
        if (id != null) {
             parent.layer.open({
                type: 2,
                title: '修改',
                shadeClose: false,
                shade: [0.3, '#000'],
                maxmin: true, //开启最大化最小化按钮
                area: ['893px', '600px'],
                content: url + "/" + id,
            });
        }
    }

//数据渲染对象
var Render = {
    //渲染状态列
    customState: function (rowdata,renderData, index, value) {

        if(value == "正常"){
            return '<span style="color:green">'+value+'</span>';
        }
        if(value == "禁用"){
            return '<span style="color:red">'+value+'</span>';
        }
        return value;
    },
    //渲染操作方法列
    customIcon: function (rowdata,renderData, index, value) {
        if (value == "" || value == null) {
            return "";
        }
        var result = '<i class="' + value + ' fa-lg"></i>';
        return result;
    }

};
