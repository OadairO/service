layui.use(['table'],function(){
	var table = layui.table;
	//系统日志
    table.render({
        elem: '#logs',
        url :'../../getLogList',
        cellMinWidth : 95,
        page : true,
        height : "full-20",
        limit : 20,
        limits : [10,15,20,25],
        id : "systemLog",
        cols : [[
           /* {type: "checkbox", fixed:"left", width:50},*/
            {field: 'library', title: '操作库', width:150,align:'center'},
            {field: 'list', title: '操作表',width:100,align:'center'},
            {field: 'userid',title: '操作人ID', width:100,align:"center"},
            {field: 'username',title: '操作人', width:150,align:"center"},
            {field: 'address', title: '用户区域',width:100,align:'center'},
            {field: 'logip', title: '操作IP',width:150,align:'center'},
            {field: 'operationtime', title: '操作时间',width:300,align:'center'},
            {field: 'czexplain', title: '操作说明',width:500,align:'left'}
        ]]
    });
    
})
