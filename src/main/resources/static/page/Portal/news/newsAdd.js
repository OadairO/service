layui.use(['form','layer','layedit','laydate','upload'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;

    //用于同步编辑器内容到textarea
    layedit.sync(editIndex);

    //上传缩略图
    upload.render({
        elem: '.thumbBox',
        url: '../../../addPortalImg',
        method : "POST",  
        done: function(res, index, upload){
            $('.thumbImg').attr('src',res.xdurl);
            $('.thumbBox').css("background","#fff");
        }
    });

    //格式化时间
    function filterTime(val){
        if(val < 10){
            return "0" + val;
        }else{
            return val;
        }
    }
  /*form.verify({
        newsName : function(val){
            if(val == ''){
                return "文章标题不能为空";
            }
        },
        content : function(val){
            if(val == ''){
                return "文章内容不能为空";
            }
        }
    })*/
    form.on("submit(addNews)",function(data){
    	//截取文章内容中的一部分文字放入文章摘要
        var abstract = layedit.getText(editIndex).substring(0,50)+"....";
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //提交信息
         $.post("../../../saveProduct",{
             name : $(".newsName").val(),  //文章标题
             cpabstract : abstract,  //文章摘要
             content : layedit.getContent(editIndex).split('<audio controls="controls" style="display: none;"></audio>')[0],  //文章内容
             img : $(".thumbImg").attr("src"),  //缩略图
             classify : data.field.ProductType,    //文章分类
             news : true,
         },function(res){
        	 if(res == 0){
        		 setTimeout(function(){
        	            top.layer.close(index);
        	            top.layer.msg("文章添加成功！");
        	            layer.closeAll("iframe");
        	            //刷新父页面
        	            parent.location.reload();
        	        },500);
        	 }else {
        		 layer.alert("添加错误！");
			}
         })
       
        return false;
    })

    //预览
    form.on("submit(look)",function(){
        layer.alert("测试");
        return false;
    })
    
    //创建一个编辑器
    var editIndex = layedit.build('news_content',{
        height : 535,
        uploadImage : {
            url : "../../../portalImg"
        }
    });

})