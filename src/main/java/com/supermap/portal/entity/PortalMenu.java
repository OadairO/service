package com.supermap.portal.entity;

import java.util.ArrayList;
import java.util.List;

/**
* @ClassName: PortalMenu 
* @Description: 门户菜单
* @author yuyao
* @date 2018年3月23日 上午9:18:15
 */
public class PortalMenu {

	private String id; //主键ID
	private String name; //菜单名称
	private String pid; //父级ID
	private String url; //地址
	private String order_num; //排序
	private List<PortalMenu> children = new ArrayList<PortalMenu>();//子菜单
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOrder_num() {
		return order_num;
	}
	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
	public List<PortalMenu> getChildren() {
		return children;
	}
	public void setChildren(List<PortalMenu> children) {
		this.children = children;
	}
}
