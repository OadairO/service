package com.supermap.portal.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermap.portal.entity.PortalMenu;
import com.supermap.portal.service.PortalMenuService;
import com.supermap.portal.service.PortalService;
import com.supermap.too.TableVoo;
import com.supermap.user.service.UserService;

import net.sf.json.JSONObject;

@RestController
public class PortalMenuController {

	
	@Autowired
	PortalMenuService portalMenuService;
	@Autowired
	UserService userService;
	@Autowired
	PortalService portalService;

	/**
	* @Title: getPortalMenuList 
	* @Description: 门户菜单列表
	* @param @param table
	* @param @param request
	* @param @return    设定文件 
	* @return Table    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getPortalMenuList")
	public TableVoo getPortalMenuList(TableVoo table,HttpServletRequest request) {
		JSONObject u = userService.getUserBySession(request);
		if(u != null && u.get("id") != null)return portalMenuService.getPortalMenuList(table,u.get("id").toString());
		return table;
	}
	/**
	* @Title: getPortalMenu 
	* @Description: 获取页面菜单数据
	* @param @param reques
	* @param @return    设定文件 
	* @return ArrayList<MenuToo>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getPortalMenu")
	public ArrayList<PortalMenu> getPortalMenu(String userid) {
		if(userid != null)return portalMenuService.getPortalMenu(userid);
		return null;
	}
	
	/**
	* @Title: savePortalUserMenu 
	* @Description: 生成用户 菜单组 创建用户门户包
	* @param @param reques
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/savePortalUserMenu")
	public int savePortalUserMenu(HttpServletRequest reques) {
		JSONObject u = userService.getUserBySession(reques);
		if(u == null )return -1;
		portalService.getPortaFile(u);
		return portalMenuService.savePortalUserMenu(u.get("id").toString());
	}
	
	/**
	* @Title: savePortalMenu 
	* @Description: 添加门户菜单
	* @param @param portalMenu
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/savePortalMenu")
	public int savePortalMenu(PortalMenu portalMenu) {
		return portalMenuService.savePortalMenu(portalMenu);
	}
	/**
	* @Title: delPortalUserMenu 
	* @Description: 取消门户功能
	* @param @param reques
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/delPortalUserMenu")
	public int delPortalUserMenu(HttpServletRequest reques) {
		JSONObject u = userService.getUserBySession(reques);
		if(u != null && u.get("id")!=null)return portalMenuService.delPortalUserMenu(u.get("id").toString());
		return -1;
	}
	/**
	* @Title: updatePortalMenu 
	* @Description: 修改绑定状态
	* @param @param menuid
	* @param @param userid
	* @param @param zt
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/updatePortalMenu")
	public int updatePortalMenu(String menuid,HttpServletRequest reques,String zt) {
		JSONObject u = userService.getUserBySession(reques);
		if(u != null && u.get("id") != null)return portalMenuService.updatePortalMenu(menuid, u.get("id").toString(), zt);
		return -1;
	}
	
	/**
	* @Title: name 
	* @Description: 用户打包测试
	* @param @param reques    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/cesiPortal")
	public void name(HttpServletRequest reques) {
		JSONObject u = userService.getUserBySession(reques);
		if(u != null && u.get("id") != null)portalService.getPortaFile(u);
		
	}
	
}
