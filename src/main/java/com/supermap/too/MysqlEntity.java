package com.supermap.too;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

/**
 * 
* @ClassName: MysqlEntity 
* @Description: mysql 工具实体
* @author yuyao
* @date 2017年12月29日 上午9:41:43
 */
public class MysqlEntity {
	
	private String warehouse;//数据库名
	private String tableName;//表名
	private String sql;//sql语句
	private JSONObject conditionValue = new JSONObject();//查询条件
	private JSONObject updateValue = new JSONObject();//更新值
	private boolean update = true;//是否更新数据操作 false 为 不更新
	
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		try {
			sql = URLEncoder.encode(sql, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("SQL转换异常！");
		}
		this.sql = sql;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean update) {
		this.update = update;
	}
	public JSONObject getConditionValue() {
		return conditionValue;
	}
	public void setConditionValue(JSONObject conditionValue) {
		this.conditionValue = conditionValue;
	}
	public JSONObject getUpdateValue() {
		return updateValue;
	}
	public void setUpdateValue(JSONObject updateValue) {
		this.updateValue = updateValue;
	}
}
