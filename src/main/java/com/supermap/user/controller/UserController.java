package com.supermap.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermap.log.entity.UserOperationLog;
import com.supermap.log.service.LogService;
import com.supermap.too.DaoToo;
import com.supermap.too.StringTool;
import com.supermap.too.TableVoo;
import com.supermap.user.entity.UserEntity;
import com.supermap.user.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @ClassName: UserController 
* @Description: 用户 操作
* @author yuyao
* @date 2018年3月6日 上午11:17:55
 */
@RestController
public class UserController {
	
	@Autowired
	DaoToo daoToo;
	@Autowired
	UserService userService;
	@Autowired
	LogService logService;
	
	/**
	* @Title: getTreeList 
	* @Description: 获取用户列表
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getUserList")
	public TableVoo getUserList(TableVoo table,UserEntity user,HttpServletRequest request) {
		JSONObject u = userService.getUserBySession(request);
		if(u == null )return table;
		if(u.get("areaCode") == null )return table;
		return userService.getUserList(table, user,u.get("areaCode").toString());
	}
	/**
	* @Title: getUserRole 
	* @Description: 获取用户角色列表
	* @param @return    设定文件 
	* @return JSONArray    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getUserRole")
	public JSONArray getUserRole(boolean gx) {
		return userService.getUserRole(gx);
	}
	
	/**
	* @Title: getUserArea 
	* @Description: 获取用户区域
	* @param @param areacode
	* @param @return    设定文件 
	* @return JSONArray    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getUserArea")
	public JSONArray getUserArea(String areacode) {
		return userService.getUserArea(areacode);
	}
	
	/**
	* @Title: getLogUser 
	* @Description: 获取登录用户的信息
	* @param @param reques
	* @param @return    设定文件 
	* @return JSONObject    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getLogUser")
	public JSONObject getLogUser(HttpServletRequest request) {
		return userService.getUserBySession(request);
	}
	
	/**
	* @Title: isLog 
	* @Description: 判断是否已经登录
	* @param @param request
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/isLog")
	public JSONObject isLog(HttpServletRequest request) {
		 JSONObject user = userService.getUserBySession(request);
		 if(user!=null) {
			 user.remove("password");
			 return user;
		 }
		return null;
	}
	/**
	* @Title: delUser 
	* @Description: 注销用户
	* @param @param userid
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/delUser")
	public int delUser(String userid,HttpServletRequest request) {
		JSONObject user = userService.getUserBySession(request);
		if(user == null || !StringTool.isNotNull(userid))return -1;
		
		int msg = userService.delUser(userid);
		
		 if(msg == 0) {//操作日记
			 UserOperationLog log = logService.getLog(user,request);
			 log.setCzexplain("用户注销操作：注销ID-"+userid);
			 log.setList("t_user");
			 logService.saveLog(log);
		 }
		 
		return userService.delUser(userid);
	}
	
	/**
	* @Title: userAudit 
	* @Description: 用户审核
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/userAudit")
	public boolean userAudit(String userId,Integer state,HttpServletRequest request) {
		JSONObject user = userService.getUserBySession(request);
		if(user == null || !StringTool.isNotNull(userId) || state == null)return false;
		
		 boolean msg = userService.userAudit(userId, state);
		 
		 if(msg) {//操作日记
			 UserOperationLog log = logService.getLog(user,request);
			 log.setCzexplain("用户审核操作：审核ID-"+userId+",审核结果-"+state);
			 log.setList("t_user");
			 logService.saveLog(log);
		 }
		return msg;
	}
	
	/**
	* @Title: saveUser 
	* @Description: 用户注册信息
	* @param @param user
	* @param @param request
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/saveUser")
	public int saveUser(UserEntity user,HttpServletRequest request) {
		int msg = -1;
		msg = userService.saveUser(user, request);
		if(msg == 0)userService.log(user.getLoginName(), user.getPassword(), request);
		return msg;
	}
	/**
	* @Title: getUserLoginName 
	* @Description: 判断登录名是否重复
	* @param @param loginName
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getUserLoginName")
	public boolean getUserLoginName(String loginName) {
		return userService.getUserLoginName(loginName);
	}
	
	/**
	* @Title: log 
	* @Description: 模拟登录
	* @param @param userName
	* @param @param password
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/log")
	public int log(String loginName,String password,HttpServletRequest reques) {
		int msg = userService.log(loginName, password, reques);
		
		if(msg == 0) {//操作日记
			JSONObject user = userService.getUserBySession(reques);
			 UserOperationLog log = logService.getLog(user,reques);
			 log.setCzexplain("用户登录操作");
			 logService.saveLog(log);
		 }
		return msg;
	}
}
