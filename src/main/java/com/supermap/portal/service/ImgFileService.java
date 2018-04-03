package com.supermap.portal.service;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.supermap.portal.entity.Imgs;
import com.supermap.too.DaoToo;
import com.supermap.too.MysqlEntity;
import com.supermap.too.SqlCx;
import com.supermap.too.StringTool;

import net.sf.json.JSONObject;
/**
* @ClassName: ImgFileService 
* @Description: 图片文件操作
* @author yuyao
* @date 2018年3月27日 上午10:18:40
 */
@Service
public class ImgFileService {
	
	@Autowired
	DaoToo daoToo;
	
	//log4j2 日子管理
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());  

    /**
    * @Title: addImg 
    * @Description: 添加保存 数据
    * @param @param file
    * @param @param url
    * @param @return    设定文件 
    * @return Msg    返回类型 
    * @throws
     */
    public Imgs addImg(Imgs img,MultipartFile file) {
    	
    	 boolean isOk = writeLocal(file,img);
    	 if(isOk) {
		 	MysqlEntity st = new MysqlEntity();
			JSONObject uv = JSONObject.fromObject(img);
			st.setTableName("portal_imgs");
			 //添加
		    st.setUpdateValue(uv);
			st.setUpdate(false);
			JSONObject obj = daoToo.potCx(st, SqlCx.fastInsert);
			if(obj.get("code") != null &&"0".equals(obj.get("code").toString()))return img;
    	 }
    	return null;
	}
    
    /**
    * @Title: getImgMsg 
    * @Description: 获取文件信息
    * @param @param file
    * @param @param sourceType
    * @param @param url
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
	public Imgs getImgMsg(MultipartFile file,String url,String userid,HttpServletRequest request) {
		  
		Imgs img = new Imgs();
		
		 //获取文件类型
		String fileType=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		img.setType(fileType);
		StringBuffer fileName= new StringBuffer();
		fileName.append(StringTool.getFileName());
		fileName.append(".");
		fileName.append(img.getType());
		img.setImgName(fileName.toString());
		img.setSize(file.getSize());
		img.setUrl(url);
		img.setId(StringTool.getUUID());
		img.setTime(StringTool.getSystemDateTimeString());
		
		int i = url.indexOf("/webPortalImgs");
		
		img.setXdurl(url.substring(i,url.length())+"/"+img.getImgName());
		img.setOperateUserId(userid);
		return img;
	}
	
	/**
	* @Title: writeLocal 
	* @Description: 把文件写入本地 
	* @param @param imgfile    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public boolean writeLocal(MultipartFile imgfile,Imgs img) {
		
		if(isExist(img.getUrl())) {
			
			File file = new File(img.getUrl(),img.getImgName());
			try {
				//文件写入
				imgfile.transferTo(file);
				zoomImageScale(file, img.getUrl() +"/" + img.getImgName(), img.getWidth(),img.getHeight());
				return true;
			} catch (Exception e) {
				logger.error("文件写入异常:" +img.getUrl() +"/" + img.getImgName());
				return false;
			} 
		}
			
		return false;
		
	}
	
	/**
	 * 
	* @Title: isExist 
	* @Description: 判断对应的sourceType文件是否存在 不存在则创建文件目录
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	private boolean isExist(String filePath) {
		File dir = new File(filePath);
		if (dir.exists()) {// 判断目录是否存在
			return true;
		}else {
			if (dir.mkdirs()) {// 创建目标目录
				return true;
			} else {
				logger.error("文件目录创建异常:" + filePath);
				return false;
			}
		}
		
	}
	/**
	* @Title: zoomImageScale 
	* @Description: 图片压缩算法
	* @param @param imgFile
	* @param @param newpath
	* @param @param newwhidth
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public static void zoomImageScale(File imgFile,String newpath,int newwhidth,int newHeigth) throws Exception{
		
		if(!imgFile.canRead()){
			return;
		}
		
		BufferedImage bufferedimage = ImageIO.read(imgFile);
		
		if(null == bufferedimage){
			return;
		}
		
		int originalWidth = bufferedimage.getWidth();
		int originalHeigth = bufferedimage.getHeight();
		if(newHeigth == 0) {
			double scale = (double)originalWidth / (double)newwhidth;  //缩放比例
			 newHeigth = (int) (originalHeigth / scale);
		}
		zoomImageUtils(imgFile,newpath,bufferedimage,newwhidth,newHeigth);
		
	}
	
	private static void zoomImageUtils(File imageFile,String newPath,BufferedImage bufferedimage,int width,int height)throws Exception{
		
		String suffix = StringUtils.substringAfterLast(imageFile.getName(), ".");
		
		//处理PNG背景变黑问题
		if(suffix !=null && (suffix.trim().toLowerCase().endsWith("png") || suffix.trim().toLowerCase().endsWith("gif"))){
			BufferedImage to = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = to.createGraphics();
			to = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
			g2d.dispose();
			
			g2d = to.createGraphics();
			Image from = bufferedimage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
			g2d.drawImage(from, 0, 0, null);
			g2d.dispose();
			
			ImageIO.write(to, suffix, new File(newPath));
		}else {
			BufferedImage newImg = new BufferedImage(width, height, bufferedimage.getType());
			Graphics g = newImg.getGraphics();
			g.drawImage(bufferedimage, 0, 0, width,height,null);
			g.dispose();
			ImageIO.write(newImg, suffix, new File(newPath));
		}
	}
	
}
