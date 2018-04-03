package com.supermap.portal.entity;

/**
* @ClassName: Imgs 
* @Description: 图片实体类
* @author yuyao
* @date 2018年3月27日 上午10:36:28
 */
public class Imgs {

	private String id;//主键ID
	
	private String imgName;//存储名称
	
	private String type;//图片类型
	
	private int width;//宽度
	
	private int height;//高度
	
	private long size;//大小
	
	private String url;//图片地址
	private String xdurl;//图片相对地址
	
	private String operateUserId;//操作ID
	
	private int logout;//是否注销 0 为有效
	
	private String time;//创建时间
	
	//初始默认值
	public Imgs() {
		this.id = "0";
		this.imgName = "0";
		this.type = "0";
		this.width = 0;
		this.height = 0;
		this.size = 0;
		this.url = "0";
		this.operateUserId = "0";
		this.logout = 0;
		this.time = "0";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getLogout() {
		return logout;
	}
	public void setLogout(int logout) {
		this.logout = logout;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOperateUserId() {
		return operateUserId;
	}
	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getXdurl() {
		return xdurl;
	}

	public void setXdurl(String xdurl) {
		this.xdurl = xdurl;
	}
	
}
