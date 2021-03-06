package com.supermap.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

//拦截器添加跨域支持
@Component
public class CORSFilter implements Filter {

	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		   HttpServletResponse response = (HttpServletResponse) servletResponse;
		   HttpServletRequest request = (HttpServletRequest) servletRequest;  
		   response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));  
	        response.setHeader("Access-Control-Allow-Credentials", "true");  
	        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
	        response.setHeader("Access-Control-Max-Age", "3600");  
	        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");  
	        
	       filterChain.doFilter(request, response);
	       
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}


}
