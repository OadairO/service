package com.supermap.system.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermap.system.entity.MenuToo;
import com.supermap.too.DaoToo;
import com.supermap.too.MysqlEntity;
import com.supermap.too.SqlCx;
import com.supermap.too.StringTool;
import com.supermap.too.TableToo;
import com.supermap.too.TableVoo;
import com.supermap.user.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
* @ClassName: MenuService 
* @Description: 菜单逻辑层
* @author yuyao
* @date 2018年3月11日 上午9:58:10
 */
@Service
public class MenuService {

	@Autowired
	DaoToo daoToo;
	@Autowired
	TableToo tableToo;
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
	public TableVoo getMenuList(TableVoo table) {
		
     	StringBuilder sql = new StringBuilder();
     	sql.append("SELECT a.*,b.name pname,b.id as pnameId FROM sys_menu a");
     	sql.append(" LEFT JOIN sys_menu b ON a.pId = b.id AND b.chkDisabled = false ORDER BY order_num ASC");
     	
     	String con = "select count(1) count from sys_menu where chkDisabled = false";
     	
		return tableToo.getTbale(table, sql.toString(), con, "sys_menu");
	}
	
	/**
	* @Title: getTopMenu 
	* @Description: 获取顶级菜单目录
	* @param @param reques
	* @param @return    设定文件 
	* @return ArrayList<MenuToo>    返回类型 
	* @throws
	 */
	public ArrayList<MenuToo> getTopMenu(HttpServletRequest reques) {
		JSONObject user = userService.getUserBySession(reques);
		MysqlEntity st = new MysqlEntity();
		ArrayList<MenuToo> list = new ArrayList<MenuToo>();
		if(user != null) {
			 st.setTableName("sys_menu");
			  StringBuilder sql = new StringBuilder();
			  sql.append("select b.* from (select m.menu_id from t_user_role r INNER JOIN ");
			  sql.append("sys_role_menu m on m.role_id = r.roleid where r.userid = "+user.get("id").toString()+" ) a ");
			  sql.append("INNER JOIN sys_menu b on a.menu_id = b.id and pId IS NULL  AND b.chkDisabled = false ORDER BY b.order_num");
			 
			  st.setSql(sql.toString());
		     JSONArray array = JSONArray.fromObject(daoToo.potCx(st, SqlCx.mysql).get("data"));
		     for(int i = 0;i < array.size() ;i++) {
		    	 JSONObject json = (JSONObject) array.get(i);
	    			MenuToo menu = new MenuToo();
	    			if(json.get("url")!=null)menu.setHref(json.get("url").toString());
	    			if(json.get("icon")!=null)menu.setIcon(json.get("icon").toString());
	    			if(json.get("name")!=null)menu.setTitle(json.get("name").toString());
	    			list.add(menu);
		    	}
		 }
		return list;
	}
	/**
	* @Title: getMenu 
	* @Description: 获取二三级菜单目录
	* @param @param reques
	* @param @return    设定文件 
	* @return HashMap<String,ArrayList<MenuToo>>    返回类型 
	* @throws
	 */
	public HashMap<String, ArrayList<MenuToo>> getMenu(HttpServletRequest reques) {
		 JSONObject user = userService.getUserBySession(reques);
		 HashMap<String, ArrayList<MenuToo>> map = new HashMap<String,ArrayList<MenuToo>>();
		 MysqlEntity st = new MysqlEntity();
		 if(user != null) {
			 st.setTableName("sys_menu");
			 //st.setSql("select * from sys_menu order by order_num asc ");
			  StringBuilder sql = new StringBuilder();
			  sql.append("select b.* from (select m.menu_id from t_user_role r INNER JOIN ");
			  sql.append("sys_role_menu m on m.role_id = r.roleid where r.userid = "+user.get("id").toString()+" ) a ");
			  sql.append("INNER JOIN sys_menu b on a.menu_id = b.id AND b.chkDisabled = false ORDER BY b.order_num");
			 
			  st.setSql(sql.toString());
		     JSONArray array = JSONArray.fromObject(daoToo.potCx(st, SqlCx.mysql).get("data"));
		     
		     for(int i = 0;i < array.size() ;i++) {
		    	 JSONObject json = (JSONObject) array.get(i);
		    	if(json.get("top") != null) {
		    		if(!map.containsKey(json.get("top").toString())) {
		    			map.put(json.get("top").toString(), new ArrayList<MenuToo>());
		    		}
		    		if(json.get("pId") != null) {
		    			MenuToo menu = new MenuToo();
		    			if(json.get("url")!=null)menu.setHref(json.get("url").toString());
		    			if(json.get("icon")!=null)menu.setIcon(json.get("icon").toString());
		    			if(json.get("name")!=null)menu.setTitle(json.get("name").toString());
		    			if(json.get("id")!=null) {
		    				menu.setId(json.get("id").toString());
		    				menu = getChildrenMenu(array, menu);
		    			}
			    		map.get(json.get("top").toString()).add(menu);
		    		}
		    	}
		     }
		 }
		return map;
	}
	/**
	* @Title: delMenu 
	* @Description: 注销菜单
	* @param @param id
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int zxMenu(String id) {
		 MysqlEntity st = new MysqlEntity();
		 JSONObject cv = new JSONObject();
		 st.setTableName("sys_menu");
		 cv.put("id", id);
		 st.setConditionValue(cv);
		 JSONObject uv = new JSONObject();
		 uv.put("chkDisabled", "true");
		 st.setUpdateValue(uv);
		 st.setUpdate(false);
		 JSONObject obj = daoToo.potCx(st, SqlCx.fastUpdate);
	     if("0".equals(obj.get("code").toString()))return 0;
		return -1;
	}
	
	/**
	* @Title: delMenu 
	* @Description: 注销菜单
	* @param @param id
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int delMenu(String id) {
		 MysqlEntity st = new MysqlEntity();
		 st.setTableName("sys_menu");
		 st.setSql("DELETE FROM sys_menu WHERE id = " + id);
		 st.setUpdate(false);
		 JSONObject obj = daoToo.potCx(st, SqlCx.mysql);
	     if("0".equals(obj.get("code").toString()))return 0;
		return -1;
	}
	
	/**
	* @Title: saveMenu 
	* @Description: 添加操作
	* @param @param menu
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int saveMenu(String menuId,String parentName,String parentId,String type,
			String name,String url,String order_num,String icon) {
		 MysqlEntity st = new MysqlEntity();
		 st.setTableName("sys_menu");
		  JSONObject uv = new JSONObject();
		  if(StringTool.isNotNull(parentId))  uv.put("pId", parentId);
		  if(StringTool.isNotNull(name)) uv.put("name",StringTool.getURLEncoder(name));
		  if(StringTool.isNotNull(order_num)) uv.put("order_num", order_num);
		  if(StringTool.isNotNull(icon))uv.put("icon", StringTool.getURLEncoder(icon));
		  if(StringTool.isNotNull(parentName)) uv.put("top", parentName);
		  if(type!=null && "1".equals(type)) {
			  uv.put("url", StringTool.getURLEncoder(url)); 
		  }
		  uv.put("type", type);
		  //修改
		  if(StringTool.isNotNull(menuId)) {
		    st.setUpdateValue(uv);
			st.setUpdate(false);
			JSONObject cv = new JSONObject();
			cv.put("id", menuId);
			st.setConditionValue(cv);
			JSONObject obj = daoToo.potCx(st, SqlCx.fastUpdate);
			if(obj.get("code")!=null && "0".equals(obj.get("code").toString()))return 0;
		  }else {
			 //添加
		    st.setUpdateValue(uv);
			st.setUpdate(false);
			JSONObject obj = daoToo.potCx(st, SqlCx.fastInsert);
			if(obj.get("code") != null &&"0".equals(obj.get("code").toString()))return 0;
		}
		return -1;
	}
	
	/**
	* @Title: getChildrenMenu 
	* @Description: 获取子菜单
	* @param @param array
	* @param @param fid
	* @param @param menu
	* @param @return    设定文件 
	* @return MenuToo    返回类型 
	* @throws
	 */
	public MenuToo getChildrenMenu(JSONArray array,MenuToo menu) {
		
		if(menu != null && menu.getId() != null) {
			 for(int i = 0;i < array.size() ;i++) {
		    	 JSONObject json = (JSONObject) array.get(i);
		    	 if(json.get("pId")!= null && menu.getId().equals(json.get("pId").toString())) {
		    		//获取当前菜单所有子菜单
		    		MenuToo childrenMenu = new MenuToo();
		    		if(json.get("url")!=null)childrenMenu.setHref(json.get("url").toString());
		    		if(json.get("icon")!=null)childrenMenu.setIcon(json.get("icon").toString());
		    		if(json.get("name")!=null)childrenMenu.setTitle(json.get("name").toString());
		    		if(json.get("id")!=null)childrenMenu.setId(json.get("id").toString());
		    		if(json.get("pId")!=null)childrenMenu.setpId(json.get("pId").toString());
		    		childrenMenu = getChildrenMenu(array,childrenMenu);
		    		menu.getChildren().add(childrenMenu); 
		    	 }
		     }
		}
		
		 return menu;
	}
	
}
