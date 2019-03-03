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
	
//�÷�����  head.jsp�б�����   ��ҳ���ϵ� ��Ӧ ���  ��ע�� ��
 public String registUI(HttpServletRequest request,HttpServletResponse response) 
		 throws ServletException,IOException {
	 return "/jsp/register.jsp";
 }
 
 //regist.jsp ��  form���ύ ���еķ���
 public String regist(HttpServletRequest request,HttpServletResponse response) 
		 throws Exception{
	 
	 User user = new User();
	 
	 ConvertUtils.register(new MyConventer(),Date.class );
	 //��װ�û�����
	 BeanUtils.populate(user, request.getParameterMap());
	 
	 user.setUid(UUIDUtils.getId());
	 user.setCode(UUIDUtils.getCode());
	 
	// user.setPassword(MD5Utils.md5(user.getPassword()));
	 
	 UserService  service  = new UserServiceImpl() ;
	 service.regist(user);
	 
	 //ҳ������ת��
	 request.setAttribute("msg", "�û�ע���ѳɹ�");
	 return "/jsp/msg.jsp";
 }
 
 
 
 /*
  * ��ת����¼ҳ��
  */
 public String loginUI(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
	 return "/jsp/login.jsp";
 }
 /*
      * �û���¼
  */
 public String login(HttpServletRequest request,HttpServletResponse response) throws Exception{
	//1.��ȡ�û���������
	 String username = request.getParameter("username");
	 String password = request.getParameter("password");
	// password = MD5Utils.md5(password);
	 
	 //2.����service��ɵ�¼���� ����user
	 UserService s = new UserServiceImpl();
	 User user = s.login(username,password);
	 
	 //3���ж��û�
	 if(user==null) {
		 request.setAttribute("msg", "�û������벻ƥ��");
		  
		  return "/jsp/login.jsp";
	 }else {
		 if(Constant.USER_IS_ACTIVE!=user.getState()) {
			 request.setAttribute("msg", "�û���δ����");
			 
			 return "/jsp/login.jsp";
		 }
	 }
	 
	 
	 //4.��user����session�У��ض���
	 request.getSession().setAttribute("user", user);
	 response.sendRedirect(request.getContextPath()+"/");
	 	 
	 return null;
 
 }
 
 /*
  * �û�����
  */
		 
 
 public String active(HttpServletRequest request,HttpServletResponse response)  
		 throws Exception{
	 //1.��ȡ������
	 String code = request.getParameter("code");
	 
	 
	 //2.����service��ɼ���
	 
	 UserService service = new UserServiceImpl();
	 User user = service.active(code);
	 
	 if(user == null) {
		 //ͨ�������룬û���ҵ��û�
		 request.setAttribute("msg", "�����¼���");
	 }
	 else {
		 //�����Ϣ
		 request.setAttribute("msg", "����ɹ�");
	 }
	 
	 
	 return "/jsp/msg.jsp";
	 
	 
  
 }
}

