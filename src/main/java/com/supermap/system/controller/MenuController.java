package com.supermap.system.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermap.log.entity.UserOperationLog;
import com.supermap.log.service.LogService;
import com.supermap.system.entity.MenuToo;
import com.supermap.system.service.MenuService;
import com.supermap.too.TableVoo;
import com.supermap.user.service.UserService;

import net.sf.json.JSONObject;

/**
* @ClassName: MenuController 
* @Description: 菜单操作
* @author yuyao
* @date 2018年3月6日 上午9:14:10
 */

@RestController
public class MenuController {

	@Autowired
	MenuService menuService;
	
	@Autowired
	LogService logService;
	
	@Autowired
	UserService userService;
	
	/**
	* @Title: getMenuList 
	* @Description: 获取菜单列表
	* @param @param table
	* @param @return    设定文件 
	* @return Table    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getMenuList")
	public TableVoo getMenuList(TableVoo table) {
		 
		return menuService.getMenuList(table);
	}
	/**
	* @Title: getTopMenu 
	* @Description: 获取顶级菜单
	* @param @param reques
	* @param @return    设定文件 
	* @return ArrayList<MenuToo>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getTopMenu")
	public ArrayList<MenuToo> getTopMenu(HttpServletRequest reques) {
		return menuService.getTopMenu(reques);
	}
	/**
	* @Title: getMenu 
	* @Description: 获取二三级菜单
	* @param @param reques
	* @param @return    设定文件 
	* @return HashMap<String,ArrayList<MenuToo>>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getMenu")
	public HashMap<String, ArrayList<MenuToo>> getMenu(HttpServletRequest reques) {
		return menuService.getMenu(reques);
	}
	
	/**
	* @Title: saveMenu 
	* @Description: 添加菜单
	* @param @param menu
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/saveMenu")
	public int saveMenu(String menuId, String parentName,String parentId,String type,
			String name,String url,String order_num,String icon,HttpServletRequest reques) {
		
		JSONObject user = userService.getUserBySession(reques);
		
		if(user == null)return -1;
		
		int msg = menuService.saveMenu(menuId,parentName, parentId, type, name, url, order_num, icon);
		
		 if(msg == 0) {//操作日记
			 UserOperationLog log = logService.getLog(user,reques);
			 log.setList("sys_menu");
			 log.setCzexplain("添加菜单操作操作：菜单名称-"+parentName+",菜单地址："+url);
			 logService.saveLog(log);
		 }
		return msg;
	}
	/**
	* @Title: delMenu 
	* @Description: 删除菜单
	* @param @param id
	* @param @param reques
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/delMenu")
	public int delMenu(String id,HttpServletRequest reques) {
		JSONObject user = userService.getUserBySession(reques);
		if(user == null)return -1;
		
		 int msg = menuService.delMenu(id);
		 
		 if(msg == 0) {//操作日记
			 UserOperationLog log = logService.getLog(user,reques);
			 log.setList("sys_menu");
			 log.setCzexplain("删除菜单操作：菜单ID-"+id);
			 logService.saveLog(log);
		 }
		 
		return msg;
	}
	
}
