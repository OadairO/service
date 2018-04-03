package com.supermap.portal.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermap.portal.entity.PortalMenu;
import com.supermap.too.DaoToo;
import com.supermap.too.MysqlEntity;
import com.supermap.too.SqlCx;
import com.supermap.too.StringTool;
import com.supermap.too.TableToo;
import com.supermap.too.TableVoo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @ClassName: PortalMenuService 
* @Description: 门户菜单逻辑
* @author yuyao
* @date 2018年3月23日 上午9:21:50
 */
@Service
public class PortalMenuService {

	@Autowired
	DaoToo daoToo;
	@Autowired
	TableToo tableToo;
	
	/**
	* @Title: getPortalMenuList 
	* @Description: 门户菜单列表
	* @param @param table
	* @param @param request
	* @param @return    设定文件 
	* @return TableVoo    返回类型 
	* @throws
	 */
	public TableVoo getPortalMenuList(TableVoo table,String userid) {
     	StringBuilder sql = new StringBuilder();
     	StringBuilder con = new StringBuilder(); 
     	
     	con.append("SELECT COUNT(1) count FROM portal_menu ");
     	
     	sql.append("SELECT m.*,um.del FROM portal_user_menu um INNER JOIN portal_menu m on um.menuid = m.id ");
     	sql.append("WHERE um.userid = '"+userid+"'  ORDER BY m.order_num");
		return tableToo.getTbale(table, sql.toString(), con.toString(), "portal_menu");
	}
	/**
	* @Title: getPortalMenu 
	* @Description: 获取门户权限菜单列表
	* @param @param reques
	* @param @return    设定文件 
	* @return ArrayList<MenuToo>    返回类型 
	* @throws
	 */
	public ArrayList<PortalMenu> getPortalMenu(String userid) {
		MysqlEntity st = new MysqlEntity();
		//获取所有菜单
		st.setTableName("portal_menu");
		
	   ArrayList<PortalMenu> list = new ArrayList<PortalMenu>();
	   StringBuilder sql = new StringBuilder();
	   sql.append("SELECT m.* FROM portal_user_menu um INNER JOIN portal_menu m on um.menuid = m.id ");
	   sql.append("WHERE del = 0 AND um.userid = '"+userid+"' ORDER BY m.order_num" );
	   
	   st.setSql(sql.toString());
	    JSONArray array = JSONArray.fromObject(daoToo.potCx(st, SqlCx.mysql).get("data"));
	     
	     for(int i = 0;i < array.size() ;i++) {
	    	 JSONObject json = (JSONObject) array.get(i);
	    	if(json.get("pid") == null) {
	    		PortalMenu menu = new PortalMenu();
    			if(json.get("name")!=null)menu.setName(json.get("name").toString());
    			if(json.get("url")!=null)menu.setUrl(json.get("url").toString());
    			if(json.get("id")!=null) {
    				menu.setId(json.get("id").toString());
    				menu = getChildrenMenu(array, menu);
    			}
    			list.add(menu);
	    	}
	     }
		return list;
	}
	
	/**
	* @Title: getChildrenMenu 
	* @Description:递归获取子菜单
	* @param @param array
	* @param @param menu
	* @param @return    设定文件 
	* @return PortalMenu    返回类型 
	* @throws
	 */
	public PortalMenu getChildrenMenu(JSONArray array,PortalMenu menu) {
		if(menu != null) {
			 for(int i = 0;i < array.size() ;i++) {
		    	 JSONObject json = (JSONObject) array.get(i);
		    	 if(json.get("pid") != null && menu.getId().equals(json.get("pid").toString())) {
		    		//获取当前菜单所有子菜单
		    		PortalMenu childrenMenu = new PortalMenu();
		    		if(json.get("name")!=null)childrenMenu.setName(json.get("name").toString());
		    		if(json.get("url")!=null)childrenMenu.setUrl(json.get("url").toString());
		    		if(json.get("id")!=null)childrenMenu.setId(json.get("id").toString());
		    		if(json.get("pid")!=null)childrenMenu.setPid(json.get("pid").toString());
		    		childrenMenu = getChildrenMenu(array,childrenMenu);
		    		menu.getChildren().add(childrenMenu); 
		    	 }
		     }
		}
		 return menu;
	}
	
	/**
	* @Title: savePortalMenu 
	* @Description: 添加
	* @param @param portalMenu
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int savePortalMenu(PortalMenu portalMenu) {
		MysqlEntity st = new MysqlEntity();
		JSONObject uv = JSONObject.fromObject(portalMenu);
		uv.remove("id");
		if(!StringTool.isNotNull(portalMenu.getUrl())) {
			uv.remove("url");
		}
		if(!StringTool.isNotNull(portalMenu.getPid())) {
			uv.remove("pid");
		}
		uv.remove("children");
		st.setTableName("portal_menu");
		 //添加
	    st.setUpdateValue(uv);
		st.setUpdate(false);
		JSONObject obj = daoToo.potCx(st, SqlCx.fastInsert);
		if(obj.get("code") != null &&"0".equals(obj.get("code").toString()))return 0;
		return -1;
	}
	
	/**
	* @Title: savePortalUserMenu 
	* @Description: 绑定用户门户菜单
	* @param @param portalMenu
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int savePortalUserMenu(String userid) {
		MysqlEntity st = new MysqlEntity();
		//获取所有菜单
		st.setTableName("portal_menu");
		st.setSql("select id from portal_menu ");
		
		JSONArray json = JSONArray.fromObject(daoToo.potCx(st, SqlCx.mysql).get("data")); 
		if(json!=null && json.size() == 0)return -1;
		
		//删除原绑定对的菜单
		st.setTableName("portal_user_menu");
		st.setSql("DELETE FROM portal_user_menu WHERE userid = '" + userid +"'");
		String rmzt = daoToo.potCx(st, SqlCx.mysql).get("code").toString(); 
		if(!"0".equals(rmzt))return -1;
		
		//重新绑定用户菜单
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO portal_user_menu (userid,menuid) values ");
		for(int i = 0;i < json.size() ;i++) {
			 JSONObject jo = (JSONObject) json.get(i);
			 sql.append("('"+userid+"','"+jo.get("id")+"'),");
		}
		sql.deleteCharAt(sql.length()-1); 
		 //添加
		st.setUpdate(false);
		st.setSql(sql.toString());
		JSONObject obj = daoToo.potCx(st, SqlCx.mysql);
		if(obj.get("code") != null &&"0".equals(obj.get("code").toString()))return 0;
		return -1;
	}
	/**
	* @Title: delPortalUserMenu 
	* @Description: 删除绑定的菜单
	* @param @param userid
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int delPortalUserMenu(String userid) {
		MysqlEntity st = new MysqlEntity();
		//删除原绑定对的菜单
		st.setTableName("portal_user_menu");
		st.setSql("DELETE FROM portal_user_menu WHERE userid = '" + userid +"'");
		String rmzt = daoToo.potCx(st, SqlCx.mysql).get("code").toString(); 
		if(!"0".equals(rmzt))return -1;
		return 0;
	}
	
	/**
	* @Title: updatePortalMenu 
	* @Description: 修改
	* @param @param menuid
	* @param @param userid
	* @param @param zt
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int updatePortalMenu(String menuid,String userid,String zt) {
		MysqlEntity st = new MysqlEntity();
		 JSONObject cv = new JSONObject();
		 st.setTableName("portal_user_menu");
		 cv.put("menuid", menuid);
		 cv.put("userid", userid);
		 st.setConditionValue(cv);
		 JSONObject uv = new JSONObject();
		 uv.put("del", zt);
		 st.setUpdateValue(uv);
		 st.setUpdate(false);
		 JSONObject obj = daoToo.potCx(st, SqlCx.fastUpdate);
	     if("0".equals(obj.get("code").toString()))return 0;
		return -1;
	}
	
}
