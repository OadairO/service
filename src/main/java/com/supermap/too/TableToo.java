package com.supermap.too;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @ClassName: TableToo 
* @Description: 表格数据工具
* @author yuyao
* @date 2018年3月23日 上午9:29:03
 */
@Service
public class TableToo {

	@Autowired
	DaoToo daoToo;
	
	/**
	* @Title: getTbale 
	* @Description: 表格封装工具
	* @param @param table
	* @param @param val
	* @param @param con
	* @param @param tableName
	* @param @return    设定文件 
	* @return TableVoo    返回类型 
	* @throws
	 */
	public TableVoo getTbale(TableVoo table,String val,String con,String tableName) {
		int ks = table.getLimit() * (table.getPage()-1);
     	//计算起始位置
     	if(ks < 0) {
     		ks = 0;
     	}
     	try {
     		MysqlEntity st = new MysqlEntity();
    		st.setTableName(tableName);
    		st.setSql(val+" LIMIT "+ ks + "," + table.getLimit() + ";");
    		
    		table.setData(daoToo.potCx(st, SqlCx.mysql).get("data"));
    		
    		st.setSql(con);
    		
    		JSONArray json = JSONArray.fromObject(daoToo.potCx(st, SqlCx.mysql).get("data")); 
    		table.setCount(JSONObject.fromObject(json.get(0)).get("count"));
    		
		} catch (Exception e) {
			System.out.println("表格数据封装异常！"+e.getMessage());
			return table;
		}
		return table;
	}
}
