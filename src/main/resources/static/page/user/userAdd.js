layui.config({
    base : "../../js/"
}).use(['form','layer','upload','jquery',"address"],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquer,
    	upload = layui.upload;
    
    //上传头像
    upload.render({
        elem: '.userFaceBtn',
        url: '../../json/userface.json',
        method : "get",
        done: function(res, index, upload){
            $('#userFace').attr('src',res.data["../images/face1.jpg"].src);
            window.sessionStorage.setItem('userFace',res.data["../images/face1.jpg"].src);
        }
    });     
    
    var form = layui.form,
    $ = layui.jquery,
    address = layui.address;
    //获取省信息
    address.provinces();
    var userInfo = new Object();
    userInfo.province = 50;
    userInfo.city = "";
    //填充省份信息，同时调取市级信息列表
    $.get("../../json/address.json", function (addressData) {
        $(".userAddress select[name='province']").val(userInfo.province); //省
        var value = userInfo.province;
        if (value > 0) {
            address.citys(addressData[$(".userAddress select[name='province'] option[value='"+userInfo.province+"']").index()-1].childs);
            citys = addressData[$(".userAddress select[name='province'] option[value='"+userInfo.province+"']").index()-1].childs;
        } else {
            $('.userAddress select[name=city]').attr("disabled","disabled");
        }
        $(".userAddress select[name='city']").val(userInfo.city); //市
        //填充市级信息，同时调取区县信息列表
        var value = userInfo.city;
        if (value > 0) {
            address.areas(citys[$(".userAddress select[name=city] option[value='"+userInfo.city+"']").index()-1].childs);
        } else {
            $('.userAddress select[name=area]').attr("disabled","disabled");
        }
        $(".userAddress select[name='area']").val(userInfo.area); //区
        form.render();
    })
    for(key in userInfo){
        if(key.indexOf("like") != -1){
            $(".userHobby input[name='"+key+"']").attr("checked","checked");
        }
    }
    form.render();
    $("#roleCod").attr("disabled","disabled");
	$("#province").attr("disabled","disabled");  
            
    form.on("submit(addUser)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // 实际使用时的提交信息
        // $.post("上传路径",{
        //     userName : $(".userName").val(),  //登录名
        //     userEmail : $(".userEmail").val(),  //邮箱
        //     userSex : data.field.sex,  //性别
        //     userGrade : data.field.userGrade,  //会员等级
        //     userStatus : data.field.userStatus,    //用户状态
        //     newsTime : submitTime,    //添加时间
        //     userDesc : $(".userDesc").text(),    //用户简介
        // },function(res){
        //
        // })
        setTimeout(function(){
            top.layer.close(index);
            top.layer.msg("用户添加成功！");
            layer.closeAll("iframe");
            //刷新父页面
            parent.location.reload();
        },2000);
        return false;
    })

    //格式化时间
    function filterTime(val){
        if(val < 10){
            return "0" + val;
        }else{
            return val;
        }
    }
    //定时发布
    var time = new Date();
    var submitTime = time.getFullYear()+'-'+filterTime(time.getMonth()+1)+'-'+filterTime(time.getDate())+' '+filterTime(time.getHours())+':'+filterTime(time.getMinutes())+':'+filterTime(time.getSeconds());

})

