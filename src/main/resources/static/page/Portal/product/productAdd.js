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
                return "标题不能为空";
            }
        },
        content : function(val){
            if(val == ''){
                return "内容不能为空";
            }
        }
    })*/
    form.on("submit(addNews)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //提交信息
         $.post("../../../saveProduct",{
             name : $(".newsName").val(),  //标题
             cpabstract : $(".abstract").val(),  //摘要
             content : layedit.getContent(editIndex).split('<audio controls="controls" style="display: none;"></audio>')[0],  //文章内容
             img : $(".thumbImg").attr("src"),  //缩略图
             classify : data.field.ProductType,    //分类
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
    
    //删除 分类
    form.on("submit(delproducttype)",function(data){
    	$.post("../../../delProductType",{typeId : data.field.ProductType},function(res){
    		if(res == 0){
    			producttypelist();
    		}else {
    			layer.alert("删除失败！");
			}
         })
        return false;
    })
    
    //添加 类别
    form.on("submit(producttype)",function(data){
    	if(data.field.addproductType != "" && data.field.addproductType != null){
	    	$.post("../../../addProducttype",{producttype : data.field.addproductType},function(res){
	    		if(res == 0){
	    			producttypelist();
	    		}else {
	    			layer.alert("添加失败！");
				}
	         })
    	}
        return false;
    })
    //获取产品类别
    function producttypelist(){
    	$.get("../../../getProducttypeList", function (R) {
    		html = '';
    		if(R != undefined && R.length > 0){
    			for(var i=0; i < R.length; i++){
    				html += '<p><input type="radio" name="ProductType" value="'+R[i].id+'" title="'+R[i].name+'" lay-skin="primary" /></p>';
    			}
    		}
    		$("#producttypelist").empty();
    		$("#producttypelist").html(html);
    		form.render();
    	});
    }
    producttypelist();

    //创建一个编辑器
    var editIndex = layedit.build('news_content',{
        height : 535,
        uploadImage : {
            url : "../../../portalImg"
        }
    });

})