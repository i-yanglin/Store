package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 

 

public class BaseServlet extends HttpServlet {
	
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//req.setCharacterEncoding("UTF-8");
		try {
		Class clazz = this.getClass();
		String methodName=req.getParameter("method");
		
		 if(methodName==null) {   //3.获取方法对象
			 methodName="index";
		 }
		
			Method  method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			String path = (String) method.invoke(this, req,resp);
			if(path!=null) {
				req.getRequestDispatcher(path).forward(req, resp);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public String index(HttpServletRequest request,HttpServletResponse response) {
		return null;
	}

}
