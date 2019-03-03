package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.sun.jndi.toolkit.ctx.StringHeadTail;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

/**
 * ���ﳵģ��
 */
public class CartServlet extends BaseServlet {
	
	
	public void clear(HttpServletRequest request, HttpServletResponse response) throws Exception {
		getCart(request).clearCart();
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
	}
	
	
	public Cart getCart(HttpServletRequest request) {
	Cart cart =(Cart)	request.getSession().getAttribute("cart");
	//�жϹ��ﳵ�Ƿ�Ϊ��
	if(cart ==null) {
		//����һ��cart
		cart = new Cart();  
		//��ӵ�session
		request.getSession().setAttribute("cart", cart);
	}
		return cart;
	}
 
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
	//1.��ȡpid�ͻ�ȡ����
	String pid=	request.getParameter("pid");
	int count=Integer.parseInt(request.getParameter("count"));	
		
		//2.����productService��ͨ��pid��ȡһ����Ʒ
		ProductService ps  =(ProductService)BeanFactory.getBean("ProductService");
		Product product = ps.getById(pid);
		
		//3.��װ��CartItem
		
  CartItem cartItem = 	new CartItem(product,count);
		
		//4.��ӵ����ﳵ��
	Cart cart=	getCart(request);
		cart.add2Cart(cartItem);
		
		//5.�ض���
		
		//response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		request.setAttribute("cart", cart);		
		 return "/jsp/cart.jsp";
		//return null;
	}
	
	//�ӹ��ﳵ���Ƴ�������
	public String remove(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String pid = request.getParameter("pid");
		 getCart(request).removeFromCart(pid);
		
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}

}
