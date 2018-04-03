package com.supermap.too;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

/**
* @ClassName: DaoToo 
* @Description: 统一数据访问工具类
* @author yuyao
* @date 2018年2月27日 下午4:55:55
 */
@Service
public class DaoToo {
	
	public static ResourceBundle rb;
	
	public DaoToo(){
		//读取application.properties 文件内容 获取img地址
		rb = ResourceBundle.getBundle("application", Locale.getDefault());
	}
	
	/**
	* @Title: cx 
	* @Description: 执行查询
	* @param @param st
	* @param @param sqlcx
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public JSONObject getCx(MysqlEntity st,SqlCx sqlcx) {
		StringBuilder sql = new StringBuilder();
		sql.append(rb.getString("api.ysurl")+sqlcx.toString());
		sql.append(getCxcs(st));
		 String fhcs = HttpRequest.get(sql.toString());
		 JSONObject json = JSONObject.fromObject(fhcs);  
		return json;
	}
	
	public JSONObject potCx(MysqlEntity st,SqlCx sqlcx) {
		StringBuilder sql = new StringBuilder();
		sql.append(rb.getString("api.ysurl")+sqlcx.toString());
		 String fhcs = HttpRequest.sendPost(sql.toString(), getCxcs(st));
		 JSONObject json = JSONObject.fromObject(fhcs);  
		return json;
	}
	
	/**
	* @Title: getUel 
	* @Description: 拼接访问参数
	* @param @param se
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String getCxcs(MysqlEntity se) {
		 StringBuilder sql = new StringBuilder();
		 sql.append("update="+se.isUpdate());
		 if(StringTool.isNotNull(se.getWarehouse())) {
			 sql.append("&warehouse="+se.getWarehouse());
		 }else {
			 sql.append("&warehouse="+rb.getString("api.default.warehouse"));
		}
		 sql.append("&conditionValue="+se.getConditionValue());
		 sql.append("&updateValue="+se.getUpdateValue());
		 sql.append("&sql="+se.getSql());
		 sql.append("&tableName="+se.getTableName());
		return sql.toString();
	}
	
	
}
