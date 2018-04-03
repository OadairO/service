package com.supermap.log.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermap.log.entity.UserOperationLog;
import com.supermap.too.DaoToo;
import com.supermap.too.MysqlEntity;
import com.supermap.too.SqlCx;
import com.supermap.too.StringTool;
import com.supermap.too.TableToo;
import com.supermap.too.TableVoo;
import com.supermap.user.service.UserService;

import net.sf.json.JSONObject;

/**
* @ClassName: LogService 
* @Description: 操作日记记录
* @author yuyao
* @date 2018年3月20日 上午9:40:35
 */
@Service
public class LogService extends Thread{

	@Autowired
	DaoToo daoToo;
	@Autowired
	TableToo tableToo;
	@Autowired
	UserService userService;
	/**
	* @Title: saveLog 
	* @Description: 操作记录
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void saveLog(UserOperationLog log) {
		new Thread() {
            @Override
            public void run() {
            	MysqlEntity st = new MysqlEntity();
        		st.setTableName("user_operation_log");
        		JSONObject uv = JSONObject.fromObject(log);
        	    st.setUpdateValue(uv);
        		st.setUpdate(false);
        		daoToo.potCx(st, SqlCx.fastInsert);
            }
        }.start();
	}
	/**
	* @Title: getLogList 
	* @Description: 获取日记列表
	* @param @param table
	* @param @param log
	* @param @return    设定文件 
	* @return Table    返回类型 
	* @throws
	 */
	public TableVoo getLogList(TableVoo table,UserOperationLog log) {
     	StringBuilder sql = new StringBuilder();
     	 StringBuilder con = new StringBuilder(); 
     	con.append("SELECT COUNT(1) count FROM (");
     	sql.append("SELECT * FROM user_operation_log ");
     	con.append(sql);
     	sql.append("ORDER BY operationtime DESC ");
     	con.append(") a");
		 
		return tableToo.getTbale(table, sql.toString(), con.toString(), "user_operation_log");
	}
	
	/**
	* @Title: getLog 
	* @Description: 获取用户信息
	* @param @param request
	* @param @return    设定文件 
	* @return UserOperationLog    返回类型 
	* @throws
	 */
	public UserOperationLog getLog(JSONObject user,HttpServletRequest reques) {
		 UserOperationLog log = new UserOperationLog();
		 log.setLogip(StringTool.getIpAddress(reques));
		 if(StringTool.isNotNull(user.getString("areaCode")))log.setAddress(user.getString("areaCode"));
		 /*log.setAddressname(user.getString(""));*/
		 if(StringTool.isNotNull(user.getString("id")))log.setUserid(user.getString("id"));
		 if(StringTool.isNotNull(user.getString("loginName")))log.setUsername(user.getString("loginName"));
		 return log;
	}
	
}
