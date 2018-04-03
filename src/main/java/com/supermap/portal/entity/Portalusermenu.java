package com.supermap.portal.entity;
/**
* @ClassName: Portalusermenu 
* @Description: 门户用户菜单
* @author yuyao
* @date 2018年3月23日 上午9:19:37
 */
public class Portalusermenu {

	private String id; //主键ID
	private String userid; //用户id
	private String menuid; //菜单ID
	private String del; //是否启用
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}

}
