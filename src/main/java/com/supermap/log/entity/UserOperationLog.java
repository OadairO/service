package com.supermap.log.entity;

import com.supermap.too.DaoToo;

/**
* @ClassName: UserOperationLog 
* @Description: 操作日记 
* @author yuyao
* @date 2018年3月20日 上午9:34:24
 */
public class UserOperationLog {

	private String library; //操作库
	private String list; //操作表
	private String userid; //操作用户ID
	private String username; //操作用户姓名
	private String address; //用户区域
	private String addressname; //区域名称
	private String logip;//登录IP
	private String czexplain; //操作说明
	
	public UserOperationLog() {
		this.library = DaoToo.rb.getString("api.default.warehouse");
		this.list = "-1";
		this.userid = "-1";
		this.username = "-1";
		this.address = "-1";
		this.addressname = "-1";
		this.czexplain = "-1";
		this.logip = "-1";
	}
	public UserOperationLog(String list, String userid, String username, String address,
			String addressname, String czexplain) {
		super();
		this.list = list;
		this.userid = userid;
		this.username = username;
		this.address = address;
		this.addressname = addressname;
		this.czexplain = czexplain;
	}


	public String getLibrary() {
		return library;
	}
	public void setLibrary(String library) {
		this.library = library;
	}
	public String getList() {
		return list;
	}
	public void setList(String list) {
		this.list = list;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddressname() {
		return addressname;
	}
	public void setAddressname(String addressname) {
		this.addressname = addressname;
	}
	public String getCzexplain() {
		return czexplain;
	}
	public void setCzexplain(String czexplain) {
		this.czexplain = czexplain;
	}
	public String getLogip() {
		return logip;
	}
	public void setLogip(String logip) {
		this.logip = logip;
	}
	@Override
	public String toString() {
		return "UserOperationLog [library=" + library + ", list=" + list + ", userid=" + userid + ", username="
				+ username + ", address=" + address + ", addressname=" + addressname + ", logip=" + logip
				+ ", czexplain=" + czexplain + "]";
	}
}
