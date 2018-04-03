package com.supermap.system.config;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Service;

/**
* @ClassName: UserSession 
* @Description: 模拟用户单点登录
* @author yuyao
* @date 2018年3月8日 下午5:34:11
 */
@Service
public class UserSession implements HttpSessionListener{
	
	//存放 用户信息绑定 session id
	public static HashMap<String,String> userMap = new HashMap<String,String>();    
	//存放 用户session信息
    public static HashMap<String,HttpSession> sessionMap = new HashMap<String,HttpSession>();    
      
    /**
    * @Title: destroyed 
    * @Description:使session失效,并移除map  
    * @param @param username
    * @param @param request    设定文件 
    * @return void    返回类型 
    * @throws
     */
    public void destroyed(String username, HttpServletRequest request) {  
        String sessionid = userMap.get(username);  
        if(sessionid!=null) {
        	 //清空已定位的session
        	//if(sessionMap.get(sessionid)!=null)sessionMap.get(sessionid).invalidate(); 
        	sessionMap.remove(sessionid);  //清楚已定位的key
        }
        userMap.remove(username);  //清楚已定位的key
    }  
      
    /**
    * @Title: created 
    * @Description: 将session信息存入map  
    * @param @param username
    * @param @param request    设定文件 
    * @return void    返回类型 
    * @throws
     */
    public void created(String username, HttpServletRequest request) {  
        HttpSession session = request.getSession();  
        session.setMaxInactiveInterval(30*60);//设置过期时间 单位秒
        userMap.put(username, session.getId()); //绑定用户名和SESSION的ID
        sessionMap.put(session.getId(), session);    //绑定session和SESSION的ID
    }  
	  
	
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
