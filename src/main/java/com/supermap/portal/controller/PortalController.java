package com.supermap.portal.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.supermap.portal.entity.Imgs;
import com.supermap.portal.service.ImgFileService;
import com.supermap.portal.service.PortalService;
import com.supermap.too.Msg;
import com.supermap.too.StringTool;
import com.supermap.too.TableVoo;
import com.supermap.user.service.UserService;

import net.sf.json.JSONObject;


/**
* @ClassName: PortalController 
* @Description: 门户对应接口
* @author yuyao
* @date 2018年3月27日 上午9:45:27
 */
@RestController
public class PortalController {
	
	@Autowired
	ImgFileService imgFileService;
	@Autowired
	UserService userService;
	@Autowired
	PortalService portalService;
	
	/**
	* @Title: saveProduct 
	* @Description: 添加门户产品
	* @param @param name 文章标题
	* @param @param cpabstract 文章摘要
	* @param @param content 文章内容
	* @param @param img 缩略图
	* @param @param classify 文章分类
	* @param @param request
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/saveProduct")
	public int saveProduct(String name,String cpabstract,String content,
			String img,String classify,Boolean news,HttpServletRequest request) {
		JSONObject u = userService.getUserBySession(request);
		if(u != null && u.get("id") != null) {
			return portalService.saveProduct(name, cpabstract, content, img, classify, 
					u.get("id").toString(), u.get("username").toString(),news);
		}
		return -1;
	}
	
	/**
	* @Title: getProductTale 
	* @Description: 获取产品列表
	* @param @param table
	* @param @param request
	* @param @return    设定文件 
	* @return TableVoo    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getProductTale")
	public TableVoo getProductTale(TableVoo table,HttpServletRequest request,String userid,String cple,Boolean news) {
		if(!StringTool.isNotNull(userid)) {
			JSONObject u = userService.getUserBySession(request);
			if(u != null && u.get("id") != null)return portalService.getProductTale(table,u.get("id").toString(),cple,news);
		}else {
			return portalService.getProductTale(table,userid,cple,news);
		}
		return table;
	}
	/**
	* @Title: getProductById 
	* @Description: 通过ID 获取产品详情
	* @param @param productId
	* @param @param request
	* @param @param userid
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getProductById")
	public Object getProductById(String productId,HttpServletRequest request,String userid) {
		if(!StringTool.isNotNull(userid)) {
			JSONObject u = userService.getUserBySession(request);
			if(u != null && u.get("id") != null)return portalService.getProductById(productId, u.get("id").toString());
		}else {
			return  portalService.getProductById(productId, userid);
		}
		return null;
	}
	/**
	* @Title: getPreviousProductById 
	* @Description: 查询上一条数据
	* @param @param productId
	* @param @param news
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getPreviousProductById")
	public Object getPreviousProductById(String productId,Boolean news) {
		return  portalService.getNextProductById(productId, news);
	}
	/**
	* @Title: getNextProductById 
	* @Description: 查询下一条数据
	* @param @param productId
	* @param @param news
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getNextProductById")
	public Object getNextProductById(String productId,Boolean news) {
		return  portalService.getNextProductById(productId, news);
	}
	
	/**
	* @Title: addProducttype 
	* @Description: 添加产品类别
	* @param @param producttype
	* @param @param request
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/addProducttype")
	public int addProducttype(String producttype,HttpServletRequest request) {
		JSONObject u = userService.getUserBySession(request);
		if(u != null && u.get("id") != null)return portalService.addProducttype(producttype, u.get("id").toString());
		return -1;
	}
	/**
	* @Title: delProductType 
	* @Description: 删除产品类别
	* @param @param typeId
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/delProductType")
	public int delProductType(String typeId) {
		if(typeId == null || "".equals(typeId)) return -1;
		return portalService.delProductType(typeId);
	}
	/**
	* @Title: getProducttypeList 
	* @Description: 获取产品类别列表
	* @param @param request
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getProducttypeList")
	public Object getProducttypeList(HttpServletRequest request,String userid) {
		
		if(!StringTool.isNotNull(userid)) {
			JSONObject u = userService.getUserBySession(request);	
			if(u != null && u.get("id") != null)return portalService.getProducttypeList(u.get("id").toString());
		}else {
			return portalService.getProducttypeList(userid);
		}
		return null;
	}
	
	/**
	* @Title: addPortalImg 
	* @Description: 添加标题图片
	* @param @param file
	* @param @param request
	* @param @return    设定文件 
	* @return Imgs    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/addPortalImg", method = RequestMethod.POST)
    public Imgs addPortalImg(MultipartFile file,HttpServletRequest request) {
		JSONObject user = userService.getUserBySession(request);
		if(user == null)return null;
		String url = portalService.getUserPortaImgsPath(user);
		
		Imgs img = imgFileService.getImgMsg(file, url,user.get("id").toString(),request);
    	img.setWidth(420);
    	img.setHeight(300);
		
		 img = imgFileService.addImg(img,file);
        return img;
    }
	/**
	* @Title: portalImg 
	* @Description: 添加内容图片
	* @param @param file
	* @param @param request
	* @param @return    设定文件 
	* @return HashMap<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/portalImg", method = RequestMethod.POST)
    public HashMap<String, Object> portalImg(MultipartFile file,HttpServletRequest request) {
		JSONObject user = userService.getUserBySession(request);
		if(user == null)return null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		String url = portalService.getUserPortaImgsPath(user);
		
		Imgs img = imgFileService.getImgMsg(file, url,user.get("id").toString(),request);
    	img.setWidth(1200);
    	img.setHeight(550);
    	
		img = imgFileService.addImg(img,file);
		map.put("code", 0);
		map.put("msg", Msg.SUCCEED);
		 JSONObject js = new JSONObject();
		 js.put("src", img.getXdurl());
		 js.put("title", "文章内容图片");
		map.put("data", js);
        return map;
    }

}
