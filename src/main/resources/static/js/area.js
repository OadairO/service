//初始数据
var $form;
var form;
var $;
layui.use(['jquery', 'form'], function() {
	 $ = layui.jquery;
    form = layui.form;
    $form = $('form');
   
    loadProvince();
})
 //加载省数据
function loadProvince() {
	$.get(localhostPaht+"getUserArea", function (addressData) {
	    var proHtml = '';
	    for (var i = 0; i < addressData.length; i++) {
	        proHtml += '<option value="' + addressData[i].areacode + '">' + addressData[i].areaname + '</option>';
	    }
	    //初始化省数据
	    $form.find('select[name=province]').append(proHtml);
	    form.render();
	    form.on('select(province)', function(data) {
	        $form.find('select[name=area]').html('<option value="">请选择省</option>');
	        var value = data.value;
	        if (value > 0) {
	            loadCity(value);
	        } else {
	            //$form.find('select[name=city]').parent().hide();
	        }
	    });
	})
}
 //加载市数据
function loadCity(city) {
	$.get(localhostPaht+"getUserArea?areacode="+city, function (citys) {
		 var cityHtml = '<option value="">请选择市</option>';
		    for (var i = 0; i < citys.length; i++) {
		        cityHtml += '<option value="' + citys[i].areacode +'">' + citys[i].areaname + '</option>';
		    }
		    $form.find('select[name=city]').html(cityHtml);
		    form.render();
		    form.on('select(city)', function(data) {
		        var value = data.value;
		        if (value > 0) {
		            loadArea(value);
		        } else {
		           // $form.find('select[name=area]').parent().hide();
		        }
		    });
	})
}
 //加载县/区数据
function loadArea(area) {
	$.get(localhostPaht+"getUserArea?areacode="+area, function (areas) {
		if(areas.length <= 0){
			//$form.find('select[name=area]').parent().hide();
		}else {
			var areaHtml = '<option value="">请选择县/区</option>';
		    for (var i = 0; i < areas.length; i++) {
		        areaHtml += '<option value="' + areas[i].areacode + '">' + areas[i].areaname + '</option>';
		    }
		    $form.find('select[name=area]').html(areaHtml);
		    form.render();
		    form.on('select(area)', function(data) {
		        //console.log(data);
		    });
		}
	})
}
   
