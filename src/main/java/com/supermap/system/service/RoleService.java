package com.supermap.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermap.too.DaoToo;
import com.supermap.too.MysqlEntity;
import com.supermap.too.SqlCx;
import com.supermap.too.TableToo;
import com.supermap.too.TableVoo;

import net.sf.json.JSONObject;

@Service
public class RoleService {

	
	@Autowired
	DaoToo daoToo;
	@Autowired
	TableToo tableToo;
	
	/**
	* @Title: getTreeList 
	* @Description: 获取角色列表
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	public TableVoo getRoleList(TableVoo table) {
		
     	StringBuilder sql = new StringBuilder();
     	sql.append("select * from t_role ");
		
     	String con = "select count(1) count from t_role ";
     	
		return tableToo.getTbale(table, sql.toString(), con, "t_role");
	}
	
	/**
	* @Title: updateRole 
	* @Description: 修改角色权限
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public int updateRole(String name,String roleId,String code,String[] cdList) {
		
		MysqlEntity st = new MysqlEntity();
		st.setTableName("t_role");
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE t_role SET ");
		sql.append("name='"+name+"',");
		sql.append("code='"+code+"' ");
		sql.append("WHERE id='"+roleId+"'");
		st.setSql(sql.toString());
		String rzt = daoToo.potCx(st, SqlCx.mysql).get("code").toString(); 
		if(!"0".equals(rzt))return -1;
		
		st.setTableName("sys_role_menu");
		st.setSql("DELETE FROM sys_role_menu WHERE role_id = '" + roleId +"'");
		String rmzt = daoToo.potCx(st, SqlCx.mysql).get("code").toString(); 
		if(!"0".equals(rmzt))return -1;
		
		sql.delete(0, sql.length());
		sql.append("INSERT INTO sys_role_menu (menu_id,role_id) values ");
		for(String cd: cdList) {
			sql.append("('"+cd+"','"+roleId+"'),");
		}
		sql.deleteCharAt(sql.length()-1); 
		st.setSql(sql.toString());
		String rmszt = daoToo.potCx(st, SqlCx.mysql).get("code").toString(); 
		if("0".equals(rmszt))return 0;
		return -1;
	}
	/**
	* @Title: getUserRoleList 
	* @Description: 通过角色获取 菜单ID
	* @param @param id
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	public Object getUserRoleList(String id) {
		
		 StringBuilder sql = new StringBuilder();
		 sql.append("select menu_id as id from sys_role_menu where role_id = ");
		 sql.append(id);
		 MysqlEntity st = new MysqlEntity();
		 st.setTableName("sys_menu");
		 st.setSql(sql.toString());
		return daoToo.potCx(st, SqlCx.mysql);
	}
	/**
	* @Title: getRoleList 
	* @Description: 获取 有效的角色 列表
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	public Object getRoleList() {
		 StringBuilder sql = new StringBuilder();
		 MysqlEntity st = new MysqlEntity();
		 st.setTableName("t_role");
		 JSONObject cv = new JSONObject();
		 cv.put("del_flg", "0");
		 st.setConditionValue(cv);
		 st.setSql(sql.toString());
		 Object ss = daoToo.potCx(st, SqlCx.fastQuery).get("data");
		return ss;
	}
}
