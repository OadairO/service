package com.supermap.portal.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermap.portal.too.FileToo;
import com.supermap.too.DaoToo;
import com.supermap.too.MysqlEntity;
import com.supermap.too.SqlCx;
import com.supermap.too.StringTool;
import com.supermap.too.TableToo;
import com.supermap.too.TableVoo;

import net.sf.json.JSONObject;

/**
* @ClassName: PortalService 
* @Description: 门户逻辑层
* @author yuyao
* @date 2018年3月26日 下午5:18:27
 */
@Service
public class PortalService {
	
	@Autowired
	DaoToo daoToo;
	@Autowired
	TableToo tableToo;
	/**
	* @Title: saveProduct 
	* @Description: 添加产品
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int saveProduct(String name,String cpabstract,String content,
			String img,String classify,String userid,String userName,Boolean news) {
		MysqlEntity st = new MysqlEntity();
		st.setTableName("portal_product");
		JSONObject uv = new JSONObject();
		uv.put("productName", StringTool.getURLEncoder(name));
		uv.put("abstract", StringTool.getURLEncoder(cpabstract));
		uv.put("content", StringTool.getURLEncoder(content));
		uv.put("productImg", img);
		if(news != null && news) {
			uv.put("newsType", classify);
		}else {
			uv.put("classifyid", classify);
		}
		uv.put("userid", userid);
		uv.put("userName", userName);
		 //添加
	    st.setUpdateValue(uv);
		JSONObject obj = daoToo.potCx(st, SqlCx.fastInsert);
		if(obj.get("code") != null &&"0".equals(obj.get("code").toString()))return 0;
		return -1;
	}
	
	/**
	* @Title: getProductTale 
	* @Description: 获取产品列表
	* @param @param table
	* @param @param userid
	* @param @return    设定文件 
	* @return TableVoo    返回类型 
	* @throws
	 */
	public TableVoo getProductTale(TableVoo table,String userid,String cple,Boolean news) {
		StringBuilder sql = new StringBuilder();
     	StringBuilder con = new StringBuilder(); 
     	StringBuilder ggzd = new StringBuilder(); 
     	
     	con.append("SELECT COUNT(1) count FROM portal_product ");
     	sql.append("SELECT id,productName,userid,userName,abstract,productStatus,newsType,classifyid,productImg,productTop,content,");
     	sql.append("date_format(productTime,'%Y-%m-%d %H:%i:%s') as productTime FROM portal_product ");
     	ggzd.append("WHERE userid = '"+userid+"'");
     	if(news != null && news) {
     		ggzd.append(" AND newsType > 0 ");
     		if(StringTool.isNotNull(cple))ggzd.append(" AND newsType ="+cple);
     	}else {
     		ggzd.append(" AND newsType = 0 ");
     		if(StringTool.isNotNull(cple))ggzd.append(" AND classifyid ="+cple);
		}
     	con.append(ggzd);
     	sql.append(ggzd);
     	sql.append(" ORDER BY productTime DESC");
		
     	return tableToo.getTbale(table, sql.toString(), con.toString(), "portal_product");
	}
	/**
	* @Title: getPreviousProductById 
	* @Description: 查询上一条数据
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	public Object getPreviousProductById(String productId,Boolean news) {
		MysqlEntity st = new MysqlEntity();
		st.setTableName("portal_product");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,productName FROM portal_product WHERE id = ");
		if(news != null && news) {
			sql.append("(SELECT id FROM portal_product WHERE newsType > 0 AND id < '"+productId+"' ");
		}else {
			sql.append("SELECT id from portal_product WHERE id < '"+productId+"' ");
		}
		sql.append("ORDER BY id DESC LIMIT 1)");
		JSONObject data = daoToo.potCx(st, SqlCx.mysql);
		if(data.get("code") != null &&"0".equals(data.get("code").toString()))return data.get("data");
		return null;
	}
	/**
	* @Title: getNextProductById 
	* @Description: 查询下一条数据
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	public Object getNextProductById(String productId,Boolean news) {
		MysqlEntity st = new MysqlEntity();
		st.setTableName("portal_product");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,productName FROM portal_product WHERE id = ");
		if(news != null && news) {
			sql.append("(SELECT id FROM portal_product WHERE newsType > 0 AND id > '"+productId+"' ");
		}else {
			sql.append("SELECT id from portal_product WHERE id > '"+productId+"' ");
		}
		sql.append("ORDER BY id DESC LIMIT 1)");
		JSONObject data = daoToo.potCx(st, SqlCx.mysql);
		if(data.get("code") != null &&"0".equals(data.get("code").toString()))return data.get("data");
		return null;
	}
	
	/**
	* @Title: getProductById 
	* @Description: 通过ID获取产品信息
	* @param @param productId
	* @param @param userid
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	public Object getProductById(String productId,String userid) {
		MysqlEntity st = new MysqlEntity();
		st.setTableName("portal_product");
		st.setSql("SELECT * FROM portal_product WHERE productStatus = 0 AND userid = '"+userid+"'"+" AND id='"+productId+"'");
		JSONObject data = daoToo.potCx(st, SqlCx.mysql);
		if(data.get("code") != null &&"0".equals(data.get("code").toString()))return data.get("data");
		return null;
	}
	
	/**
	* @Title: addProducttype 
	* @Description: 添加类别 
	* @param @param producttype
	* @param @param userid
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	public int addProducttype(String producttype,String userid) {
		MysqlEntity st = new MysqlEntity();
		st.setTableName("portal_product_type");
		JSONObject uv = new JSONObject();
		uv.put("name", producttype);
		uv.put("userid", userid);
		 //添加
	    st.setUpdateValue(uv);
		st.setUpdate(false);
		JSONObject obj = daoToo.potCx(st, SqlCx.fastInsert);
		if(obj.get("code") != null &&"0".equals(obj.get("code").toString()))return 0;
		return -1;
	}
	/**
	* @Title: delProductType 
	* @Description: 删除类别
	* @param @param typeId
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int delProductType(String typeId) {
		MysqlEntity st = new MysqlEntity();
		st.setTableName("portal_product_type");
		JSONObject cv = new JSONObject();
		cv.put("id", typeId);
		JSONObject uv = new JSONObject();
		uv.put("del", "1");
	    st.setUpdateValue(uv);
	    st.setConditionValue(cv);
		JSONObject obj = daoToo.potCx(st, SqlCx.fastUpdate);
		if(obj.get("code") != null &&"0".equals(obj.get("code").toString()))return 0;
		return -1;
	}
	
	/**
	* @Title: getProducttypeList 
	* @Description: 获取类别列表
	* @param @param userid
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	public Object getProducttypeList(String userid) {
		MysqlEntity st = new MysqlEntity();
		st.setTableName("portal_product_type");
		st.setSql("SELECT * FROM portal_product_type WHERE del = 0 AND userid = '"+userid+"'");
		JSONObject data = daoToo.potCx(st, SqlCx.mysql);
		if(data.get("code") != null &&"0".equals(data.get("code").toString()))return data.get("data");
		return null;
	}
	
	/**
	* @Title: getPortaFile 
	* @Description: 创建门户目录
	* @param @param user    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void getPortaFile(JSONObject user) {
		//父路径
		String fpath = getXmPath()+"/static/page/webPortalTemplate";
		//子路径
		String zpath = getUserPortaPath(user);
		
		try {
			File file = new File(zpath);
			//判断是否已存在
			if(file.exists()) {
				//已存在 删除文件
				FileToo.delFolder(zpath);
			}
			FileToo.copy(fpath, zpath);
			
			FileToo.createJsonFile(user.toString(), zpath+"/json", "portalUser");
		} catch (Exception e) {
			System.out.println("文件拷贝异常：" + e.getMessage());
		}
	}
	
	/**
	* @Title: getUserPortaPath 
	* @Description: 获取用户门户路径
	* @param @param user
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String getUserPortaPath(JSONObject user) {
		//子路径
		return getXmPath()+"/static/webPortal/"+user.get("loginName").toString();
	}
	/**
	* @Title: getUserPortaImgsPath 
	* @Description: 获取图片路径
	* @param @param user
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String getUserPortaImgsPath(JSONObject user) {
		//子路径
		String dz= getXmPath();
		int i = dz.indexOf("webapps");
		 dz = dz.substring(0, i+7);
		return dz + "/webPortalImgs/"+user.get("loginName").toString();
	}
	
	
	/**
	* @Title: getXmPath 
	* @Description: 获取项目路径    
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public String getXmPath() {
		File f = new File(this.getClass().getResource("/").getPath());
        return f.toString();
	}
}
