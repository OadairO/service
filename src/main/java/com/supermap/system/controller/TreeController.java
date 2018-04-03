package com.supermap.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermap.too.DaoToo;
import com.supermap.too.MysqlEntity;
import com.supermap.too.SqlCx;

/**
* @ClassName: TreeController 
* @Description: 树操作
* @author yuyao
* @date 2018年3月6日 上午10:16:21
 */
@RestController
public class TreeController {

	@Autowired
	DaoToo daoToo;
	
	/**
	* @Title: getTreeList 
	* @Description: 获取树列表
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getTreeList")
	public Object getTreeList() {
		
		MysqlEntity st = new MysqlEntity();
		st.setTableName("sys_menu");
		st.setSql("select id,pId,name,url,top,open,type from sys_menu ");
		return daoToo.potCx(st, SqlCx.mysql);
	}
	
}
