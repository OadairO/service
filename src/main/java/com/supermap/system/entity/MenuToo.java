package com.supermap.system.entity;

import java.util.ArrayList;

public class MenuToo {

	private String id;
	private String pId;
	private String title;//标题名称
	private String icon;//图标
	private String href;//地址
	private boolean spread = false;//是否选中
	private ArrayList<MenuToo> children = new ArrayList<MenuToo>();//子菜单
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public boolean isSpread() {
		return spread;
	}
	public void setSpread(boolean spread) {
		this.spread = spread;
	}
	public ArrayList<MenuToo> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<MenuToo> children) {
		this.children = children;
	}
}
