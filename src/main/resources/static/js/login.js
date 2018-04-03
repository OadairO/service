layui.use(['form','layer','jquery'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer
        $ = layui.jquery;

    $(".registertz").click(function(){
    	layer.open({
            type: 1,
            shade: false,
            title: '用户注册',
            area: ['500px', '500px'],
            shadeClose: true, //点击遮罩关闭
            content: $('#register'),
            end:function(){
                $("#register").hide();
            },
        });
    	//$("#login").hide();
    	//$("#register").show();
    })
    $(".passwordRecovertz").click(function(){
        layer.msg("还未开放该功能！",{
            time:5000
        });
    })
    $(".logintz").click(function(){
        $("#login").show();
        $("#register").hide();
    })
    
    	//获取角色列表
	  $.get("getRole", function (data) {
		  $("#roleCod option").remove();
		  $.each(data, function(i,val) {  
			  $("#roleCod").append("<option value='"+val.id+"'>"+val.name+"</option>");
			}); 
		  form.render();
	  });

    //登录按钮
    form.on("submit(login)",function(obj){
    	$(this).text("登录中...").attr("disabled","disabled").addClass("layui-disabled");
         var data = obj.field;
         var  loginName = data.loginName;
         var  password = data.password;
        $.ajax({
            type: "post",
            url: 'log', 
            data: {"loginName":loginName,"password":password},
            async: false,
            //dataType: "json",
            success: function (R) {
                if (R == 0) {
                	setTimeout(function(){
                        window.location.href = "page/index.html";
                    },1000);
                } else if(R == -1){
                	 layer.msg('登录错误！');
                	 $(this).text("登录中");
                }
                else if(R == 1){
               	 layer.msg('用户还未通过审核，请登录管理员审核！');
               }
                else if(R == 2){
               	 layer.msg('账号或密码错误！');
               }
            },
            error: function () {
            	 layer.msg('登录错误！请联系管理员！');
            }
        });
        form.render();
        return false;
    })

    //注册按钮
    form.on("submit(register)",function(data){
    	 var loginName = $("#ZCLoginName").val();
    	 var username = $("#ZCUsername").val();
    	 var mobile = $("#ZCmobile").val();
    	 var area = $("#ZCarea").val();
    	 var city = $("#city").val();
    	 var province = $("#province").val();
    	 var password = $("#password2").val();
    	 var role = $("#roleCod").val();
    	if(ZCarea == null || ZCarea == ""){
    		layer.msg('请选择详细的区域信息！');
    		return false;
    	}
    	if($("#password1").val() != $("#password2").val()){
    		layer.msg('两次输入的密码不相同！');
    		return false;
    	}
    	$.ajax({
            type: "get",
            url: 'getUserLoginName?loginName='+loginName, 
            async: false,
            success: function (R) {
                if (!R) {
                	layer.msg('当前账户已被注册！');
                	return false;
                } else{
                	 // $(this).text("注册中...").attr("disabled","disabled").addClass("layui-disabled");
                    $.ajax({
                        type: "post",
                        url: 'saveUser', 
                        data: {"loginName":loginName,"username":username,"mobile":mobile,
                        	"city":city,"province":province,"area":area,"password":password,"role":role},
                        async: false,
                        //dataType: "json",
                        success: function (R) {
                            if (R == 0) {
                            	/*setTimeout(function(){
                                    window.location.href = localhostPaht +"/page/index.html";
                                },1000);*/
                            	layer.msg('注册成功！');
                            }else if(R == -1){
                            	layer.msg('注册失败，请联系管理员！');
                            } else {
                            	layer.msg('系统错误，请联系管理员！');
                            }
                        },
                        error: function () {
                        	layer.msg('注册失败，请联系管理员！');
                        }
                    });
                }
            }
        });
    	
        return false;
    })

    //表单输入效果
    $(".loginBody .input-item").click(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function(){
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function(){
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() != ''){
            $(this).parent().addClass("layui-input-active");
        }else{
            $(this).parent().removeClass("layui-input-active");
        }
    })
})

