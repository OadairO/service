package com.supermap.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermap.log.entity.UserOperationLog;
import com.supermap.log.service.LogService;
import com.supermap.system.service.RoleService;
import com.supermap.too.TableVoo;
import com.supermap.user.service.UserService;

import net.sf.json.JSONObject;

/**
* @ClassName: RoleController 
* @Description: 角色控制
* @author yuyao
* @date 2018年3月6日 下午4:23:57
 */
@RestController
public class RoleController {

	@Autowired
	RoleService roleService;
	
	@Autowired
	LogService logService;
	
	@Autowired
	UserService userService;
	
	/**
	* @Title: getTreeList 
	* @Description: 获取角色列表
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getRoleList")
	public TableVoo getRoleList(TableVoo table) {
		
		return roleService.getRoleList(table);
	}
	
	@RequestMapping(value = "/updateRole")
	public int updateRole(String name,String roleId,String code,String cdData,
			HttpServletRequest reques) {
		
		JSONObject user = userService.getUserBySession(reques);
		if(user == null)return -1;
		
		cdData = cdData.substring(0,cdData.length() - 1);
		String[] array = cdData.split(",");  
		int zt = roleService.updateRole(name, roleId, code, array);
		
		 if(zt == 0) {//操作日记
			 UserOperationLog log = logService.getLog(user,reques);
			 log.setList("t_role");
			 log.setCzexplain("修改角色操作:角色："+name+"，绑定菜单：id_"+array.toString());
			 logService.saveLog(log);
		 }
		
		return zt;
	}
	
	@RequestMapping(value = "/getUserRoleList")
	public Object getUserRoleList(String id) {
		return roleService.getUserRoleList(id);
	}
	
	@RequestMapping(value = "/getRole")
	public Object getRole() {
		return roleService.getRoleList();
	}
	
}
