package com.supermap.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermap.system.config.UserSession;
import com.supermap.too.DaoToo;
import com.supermap.too.MysqlEntity;
import com.supermap.too.SqlCx;
import com.supermap.too.StringTool;
import com.supermap.too.TableToo;
import com.supermap.too.TableVoo;
import com.supermap.user.entity.UserEntity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class UserService {

	
	public static JSONArray rolrArray = new JSONArray();
	
	@Autowired
	UserSession userSession;
	@Autowired
	DaoToo daoToo;
	@Autowired
	TableToo tableToo;
	/**
	* @Title: getUserBySession 
	* @Description: 获取session 中的用户信息
	* @param @param request
	* @param @return    设定文件 
	* @return JSONObject    返回类型 
	* @throws
	 */
	public JSONObject getUserBySession(HttpServletRequest request) {
		JSONObject user = null;
		if(request == null)return user;
		 HttpSession si = request.getSession();
		if(si == null)return user;
		String sessionId = si.getId();
		if(StringTool.isNotNull(sessionId)) {
			 HttpSession ss = UserSession.sessionMap.get(sessionId);
			 if(ss == null)return user;
			Object se = ss.getAttribute("user");
			if(se != null) user = JSONObject.fromObject(se);
		}
		 return user;
	}
	/**
	* @Title: saveUser 
	* @Description: 添加用户信息
	* @param @param user
	* @param @param request
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int saveUser(UserEntity user,HttpServletRequest request) {
		
		try {
			MysqlEntity st = new MysqlEntity();
			st.setTableName("t_user");
			 StringBuilder sql = new StringBuilder();
			 sql.append("INSERT INTO t_user (loginName,showName,username,password,mobile,areaCode)VALUES(");
			 sql.append("'"+user.getLoginName()+"',");
			 sql.append("'"+user.getLoginName()+"',");
			 sql.append("'"+user.getUsername()+"',");
			 sql.append("'"+StringTool.getMD5(user.getPassword())+"',");
			 sql.append("'"+user.getMobile()+"',");
			 if(StringTool.isNotNull(user.getArea())) {
				 sql.append("'"+user.getArea()+"')");
			 }else if (StringTool.isNotNull(user.getCity())) {
				 sql.append("'"+user.getCity()+"')");
			 }else if (StringTool.isNotNull(user.getProvince())) {
				 sql.append("'"+user.getProvince()+"')");
			 }
			 
			st.setSql(sql.toString());
			daoToo.potCx(st, SqlCx.mysql); 
			
			st.setSql("select * from t_user where loginName = '" + user.getLoginName() +"'");
			daoToo.potCx(st, SqlCx.mysql).get("data");
			
			JSONArray newuser = JSONArray.fromObject(daoToo.potCx(st, SqlCx.mysql).get("data")); 
			
			String useerid = JSONObject.fromObject(newuser.get(0)).get("id").toString();
			
			if(useerid!=null) {
				st.setSql("INSERT INTO t_user_role (userid,roleid)values('"+useerid+"','"+user.getRole()+"')");
				
				 String zt = daoToo.potCx(st, SqlCx.mysql).get("code").toString(); 
				 if("0".equals(zt)) {
					 return 0;
				 }else {//数据回滚
					 st.setSql("DELETE FROM t_user_role WHERE loginName = '" + user.getLoginName() +"'");
					 daoToo.potCx(st, SqlCx.mysql);
				}
			}
			return 0;
		} catch (Exception e) {
			return -1;
		}
		
	}
	/**
	* @Title: delUser 
	* @Description: 注销用户
	* @param @param userid
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int delUser(String userid) {
		 MysqlEntity st = new MysqlEntity();
		 JSONObject cv = new JSONObject();
		 JSONObject uv = new JSONObject();
		 
		 st.setTableName("t_user");
		 cv.put("id", userid);
		 st.setConditionValue(cv);
		 uv.put("del_flg", "1");
		 st.setUpdateValue(uv);
		 
		 JSONObject obj = daoToo.potCx(st, SqlCx.fastUpdate);
		 
	     if("0".equals(obj.get("code").toString()))return 0;
		return -1;
	}
	
	/**
	* @Title: getUserLoginName 
	* @Description: 判断 登录名是否重复
	* @param @param loginName
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean getUserLoginName(String loginName) {
		MysqlEntity st = new MysqlEntity();
		st.setTableName("sys_menu");
		st.setSql("select loginName from t_user where loginName = '" + loginName +"'");
		JSONArray json = JSONArray.fromObject(daoToo.potCx(st, SqlCx.mysql).get("data")); 
		if(json.size()>0 && json.get(0) != null ) return false;
		return true;
	}
	
	/**
	* @Title: getTreeList 
	* @Description: 获取用户列表
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	public TableVoo getUserList(TableVoo table,UserEntity user,String areaCode) {
		
     	StringBuilder sql = new StringBuilder();
     	StringBuilder con = new StringBuilder(); 
     	StringBuilder ggzd = new StringBuilder(); 
     	
     	con.append("SELECT COUNT(1) count FROM (");
     	con.append("SELECT u.id FROM t_user u ");
     	
     	sql.append("SELECT u.*,r.id as roleid,r.name as roleName,r.code as roleCode,a.areaname FROM t_user u ");
     	
     	ggzd.append("LEFT JOIN  t_user_role ur ON u.id = ur.userid ");
     	ggzd.append("LEFT JOIN t_role r ON ur.roleid = r.id ");
     	ggzd.append("LEFT JOIN t_area a ON u.areaCode = a.areacode ");
     	ggzd.append("WHERE u.del_flg = 0 AND u.username != 'admin' ");
     	if(StringTool.isNotNull(areaCode)) {
     		if(!"0".equals(areaCode)) {
     			ggzd.append("AND u.areaCode REGEXP '^"+areaCode+"' ");
     		}
     	}
     	if(StringTool.isNotNull(user.getUsername()))ggzd.append("AND u.username = '"+user.getUsername()+"' ");
     	if(StringTool.isNotNull(user.getLoginName()))ggzd.append("AND u.loginName = '"+user.getLoginName()+"' ");
     	if(StringTool.isNotNull(user.getMobile()))ggzd.append("AND u.mobile = '"+user.getMobile()+"' ");
     	if(StringTool.isNotNull(user.getState()))ggzd.append("AND u.state = "+user.getState()+" ");
     	if(StringTool.isNotNull(user.getRoleName()))ggzd.append("AND r.id = "+user.getRoleName()+" ");
     	if(StringTool.isNotNull(user.getArea())) {
     		ggzd.append("AND u.areaCode REGEXP '^"+user.getArea()+"' ");
     	}else if(StringTool.isNotNull(user.getCity())){
     		ggzd.append("AND u.areaCode REGEXP '^"+user.getCity()+"' ");
		}else if(StringTool.isNotNull(user.getProvince())){
			ggzd.append("AND u.areaCode REGEXP '^"+user.getProvince()+"' ");
		}
     	
     	con.append(ggzd + ") a");
     	
     	sql.append(ggzd);
     	sql.append("ORDER BY u.createtime DESC ");
     	
		return tableToo.getTbale(table, sql.toString(), con.toString(), "t_user");
	}
	/**
	* @Title: userAudit 
	* @Description: 用户审核
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean userAudit(String userId,int state) {
		 MysqlEntity st = new MysqlEntity();
		 JSONObject cv = new JSONObject();
		 st.setTableName("t_user");
		 cv.put("id", userId);
		 st.setConditionValue(cv);
		 JSONObject uv = new JSONObject();
		 uv.put("state", state);
		 st.setUpdateValue(uv);
		 JSONObject obj = daoToo.potCx(st, SqlCx.fastUpdate);
	     if("0".equals(obj.get("code").toString()))return true;
		return false;
	}
	/**
	* @Title: getUserRole 
	* @Description: 获取角色列表
	* @param @return    设定文件 
	* @return JSONArray    返回类型 
	* @throws
	 */
	public JSONArray getUserRole(boolean gx) {
		if(rolrArray == null || gx) {
			MysqlEntity st = new MysqlEntity();
			st.setTableName("sys_menu");
			st.setSql("select * from t_role where del_flg = 0");
			JSONArray json = JSONArray.fromObject(daoToo.potCx(st, SqlCx.mysql).get("data")); 
			if(json != null ) rolrArray = json;
		}
		return rolrArray;
	}
	/**
	* @Title: getUserArea 
	* @Description: 获取用户区域
	* @param @param areacode
	* @param @return    设定文件 
	* @return JSONArray    返回类型 
	* @throws
	 */
	public JSONArray getUserArea(String areacode) {
		JSONArray areaArray = new JSONArray();
		MysqlEntity st = new MysqlEntity();
		st.setTableName("t_area");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM t_area ");
		if(!StringTool.isNotNull(areacode)) {
			sql.append("WHERE LENGTH(areacode)= 2");
		}else {
			sql.append("WHERE LENGTH(areacode)= "+(areacode.length()+2));
			sql.append(" AND areacode REGEXP '^"+areacode+"'");
		}
		st.setSql(sql.toString());
		JSONArray json = JSONArray.fromObject(daoToo.potCx(st, SqlCx.mysql).get("data")); 
		if(json != null ) areaArray = json;
		return areaArray;
	}
	
	/**
	* @Title: log 
	* @Description: 登录逻辑
	* @param @param loginName
	* @param @param password
	* @param @param reques
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public int log(String loginName,String password,HttpServletRequest reques) {
		if(loginName == null || password == null)return -1;
		 String md5 = StringTool.getMD5(password);
		 StringBuilder sql = new StringBuilder();
		 sql.append("select * from t_user where loginName = '");
		 sql.append(loginName + "' ");
		 MysqlEntity st = new MysqlEntity();
		 st.setTableName("sys_menu");
		 st.setSql(sql.toString());
		 Object fhsj = daoToo.potCx(st, SqlCx.mysql).get("data");
		 if(fhsj != null) {
			 JSONArray jsonArray = JSONArray.fromObject(fhsj); 
			 if(jsonArray.size()==0)return 2;
			 if(JSONObject.fromObject(jsonArray.get(0)).get("password") == null)return 2;
			 if(JSONObject.fromObject(jsonArray.get(0)).get("state") == null)return 1;
			 
			 String userpassword = JSONObject.fromObject(jsonArray.get(0)).get("password").toString();
			 String state = JSONObject.fromObject(jsonArray.get(0)).get("state").toString();
			 if(!"0".equals(state))return 1;
			 if(JSONObject.fromObject(jsonArray.get(0)).get("password")!=null && md5.equals(userpassword)) {
				 //登录成功
				 //查询是否已经登录
				 reques.getSession().removeAttribute("user");
				 reques.getSession().setAttribute("user", jsonArray.get(0));
				 String sessionid = UserSession.userMap.get(loginName);    
				 if(sessionid != null && !sessionid.equals("")){ //已经登录
				     //注销在线用户,如果session id 相同，不销毁。    
				     if(!sessionid.equals(reques.getSession().getId())){  
				    	 userSession.destroyed(loginName, reques);  
				         userSession.created(loginName, reques); 
				    }
				 }else{  
					 //未登录 添加登录信息
				     userSession.created(loginName, reques);
				 } 
				 return 0;
			 }else {
				 return -2;
			}
		 }
		 return -1;
		
	}
	
}
