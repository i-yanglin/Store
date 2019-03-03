package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import com.itheima.myconventer.MyConventer;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.MD5Utils;
import com.itheima.utils.UUIDUtils;
import com.itheima.constant.Constant;
import com.itheima.domain.User;

import javafx.scene.chart.PieChart.Data;
import sun.print.resources.serviceui;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	
//该方法在  head.jsp中被调用   其页面上的 对应 点击  “注册 ”
 public String registUI(HttpServletRequest request,HttpServletResponse response) 
		 throws ServletException,IOException {
	 return "/jsp/register.jsp";
 }
 
 //regist.jsp 中  form表单提交 其中的方法
 public String regist(HttpServletRequest request,HttpServletResponse response) 
		 throws Exception{
	 
	 User user = new User();
	 
	 ConvertUtils.register(new MyConventer(),Date.class );
	 //封装用户数据
	 BeanUtils.populate(user, request.getParameterMap());
	 
	 user.setUid(UUIDUtils.getId());
	 user.setCode(UUIDUtils.getCode());
	 
	// user.setPassword(MD5Utils.md5(user.getPassword()));
	 
	 UserService  service  = new UserServiceImpl() ;
	 service.regist(user);
	 
	 //页面请求转发
	 request.setAttribute("msg", "用户注册已成功");
	 return "/jsp/msg.jsp";
 }
 
 
 
 /*
  * 跳转到登录页面
  */
 public String loginUI(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
	 return "/jsp/login.jsp";
 }
 /*
      * 用户登录
  */
 public String login(HttpServletRequest request,HttpServletResponse response) throws Exception{
	//1.获取用户名和密码
	 String username = request.getParameter("username");
	 String password = request.getParameter("password");
	// password = MD5Utils.md5(password);
	 
	 //2.调用service完成登录操作 返回user
	 UserService s = new UserServiceImpl();
	 User user = s.login(username,password);
	 
	 //3。判断用户
	 if(user==null) {
		 request.setAttribute("msg", "用户名密码不匹配");
		  
		  return "/jsp/login.jsp";
	 }else {
		 if(Constant.USER_IS_ACTIVE!=user.getState()) {
			 request.setAttribute("msg", "用户名未激活");
			 
			 return "/jsp/login.jsp";
		 }
	 }
	 
	 
	 //4.将user放入session中，重定向
	 request.getSession().setAttribute("user", user);
	 response.sendRedirect(request.getContextPath()+"/");
	 	 
	 return null;
 
 }
 
 /*
  * 用户激活
  */
		 
 
 public String active(HttpServletRequest request,HttpServletResponse response)  
		 throws Exception{
	 //1.获取激活码
	 String code = request.getParameter("code");
	 
	 
	 //2.调用service完成激活
	 
	 UserService service = new UserServiceImpl();
	 User user = service.active(code);
	 
	 if(user == null) {
		 //通过激活码，没有找到用户
		 request.setAttribute("msg", "请重新激活");
	 }
	 else {
		 //添加信息
		 request.setAttribute("msg", "激活成功");
	 }
	 
	 
	 return "/jsp/msg.jsp";
	 
	 
  
 }
}

