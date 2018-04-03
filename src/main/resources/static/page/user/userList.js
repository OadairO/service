layui.config({
    base : "../../js/"
}).use(['form','layer','table','laytpl','jquery'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //用户列表
    var tableIns = table.render({
        elem: '#userList',
        url : '../../getUserList',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 20,
        id : "userListTable",
        cols : [[
           /* {type: "checkbox", fixed:"left", width:50},*/
            {field: 'username', title: '用户名', minWidth:100, align:"center"},
            {field: 'loginName', title: '登陆名', minWidth:100, align:'center'},
            /*{field: 'showName', title: '显示名', align:'center'},*/
            {field: 'mobile', title: '电话', align:'center'},
            {field: 'address', title: '详细地址', align:'center'},
            {field: 'areaname', title: '所在区域', align:'center'},
            /*{field: 'Email', title: '邮箱', align:'center'},*/
            {field: 'HeadImage', title: '头像', align:'center'},
            {field: 'roleName', title: '角色', align:'center'},
            /*{field: 'Introduction', title: '简介', align:'center'},*/
            {field: 'state', title: '状态', align:'center',templet:function(d){
                if(d.state == 0){
                    return "通过";
                }else if(d.state == 1){
                    return "未通过";
                }else if(d.state == 2){
                    return "黑名单";
                }else if(d.state == -1){
                    return "待审核";
                }
            }},
            /*{field: 'createtime', title: '创建时间', align:'center',minWidth:150},*/
            {title: '操作', minWidth:175, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });

    //监听提交  刷新表格
	 form.on('submit(reload)', function(data){
		 table.reload('userListTable', {
			  url: '../../getUserList',
			  where: data.field //设定异步数据接口的额外参数
			  //,height: 300
			});
		return false;
	  });
    
    //添加用户
    function addUser(edit,view){
        var index = layui.layer.open({
            title : "用户操作",
            type : 2,
            content : "userAdd.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find("#username").val(edit.username);  //用户名
                    body.find("#loginName").val(edit.loginName);  //登录名
                    body.find("#showName").val(edit.showName);//显示名称
                    body.find("#mobile").val(edit.mobile);//电话
                    body.find("#address").val(edit.address);//区域
                    body.find("#Email").val(edit.Email);//邮箱
                    body.find("#roleCod").val(edit.roleName);//角色
                    body.find("#HeadImage").val(edit.HeadImage);//头像
                    body.find("#Introduction").val(edit.Introduction);//简介
                    //body.find(".userSex input[value="+edit.userSex+"]").prop("checked","checked");  //性别
                    form.render();
                }
                if(view){
                    body.find("#username").attr("disabled","disabled");   //用户名
                    body.find("#loginName").attr("disabled","disabled");   //登录名
                    body.find("#showName").attr("disabled","disabled"); //显示名称
                    body.find("#mobile").attr("disabled","disabled"); //电话
                    body.find("#address").attr("disabled","disabled"); //区域
                    body.find("#Email").attr("disabled","disabled"); //邮箱
                    body.find("#HeadImage").attr("disabled","disabled"); //头像
                    body.find("#Introduction").attr("disabled","disabled"); //简介
                    //body.find(".userSex input[value="+edit.userSex+"]").prop("checked","checked");  //性别
                    form.render();
                }
                /*setTimeout(function(){
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)*/
            }
        })
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        })
    }
    $(".addNews_btn").click(function(){
        addUser();
    })

    //列表操作
    table.on('tool(userList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            addUser(data,false);
        }else if(layEvent === 'audit'){ //用户审核
        	if(data.state == -1){
        		var index = layui.layer.open({
                    title : "用户审核",
                    type : 2,
                    content : "audit.html",
                    success : function(layero, index){
                        var body = layui.layer.getChildFrame('body', index);
                        	body.find("#userId").val(data.id);  //用户ID
                            body.find("#username").val(data.username);  //用户名
                            body.find("#loginName").val(data.loginName);  //登录名
                            body.find("#showName").val(data.showName);//显示名称
                            body.find("#mobile").val(data.mobile);//电话
                            body.find("#address").val(data.address);//区域
                            body.find("#Email").val(data.Email);//邮箱
                            body.find("#roleCod").val(data.roleName);//角色
                           /* body.find("#HeadImage").val(data.HeadImage);//头像*/
                            body.find("#Introduction").val(data.Introduction);//简介
                            form.render();
                    }
                })
                   layui.layer.full(index);
   		        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
   		        $(window).on("resize",function(){
   		            layui.layer.full(index);
   		        })
        	}else{
        		 layer.msg("该用户已被审核！");
        	}
        }else if(layEvent === 'del'){ //用户审核
        	layer.confirm('确定要注销'+data.username+'？',{icon:3, title:'提示信息'},function(index){
               $.get("../../delUser",{userid : data.id },function(data){
            	   if(0 == data){
            		   tableIns.reload();
                       layer.close(index);
            	   }else {
            		   layer.msg("操作失败！");
            	   }
                })
            });
        }
    });
    
   //获取角色列表
    $.get("../../getRole", function (data) {
  	  $("#roleName option").remove();
  	  $("#roleName").append("<option value=''>不限</option>");
  	  	$.each(data, function(i,val) {  
  		  $("#roleName").append("<option value='"+val.id+"'>"+val.name+"</option>");
  		}); 
  	  form.render();
    });
    
})

