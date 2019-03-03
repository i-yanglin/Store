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
 * 购物车模块
 */
public class CartServlet extends BaseServlet {
	
	
	public void clear(HttpServletRequest request, HttpServletResponse response) throws Exception {
		getCart(request).clearCart();
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
	}
	
	
	public Cart getCart(HttpServletRequest request) {
	Cart cart =(Cart)	request.getSession().getAttribute("cart");
	//判断购物车是否为空
	if(cart ==null) {
		//创建一个cart
		cart = new Cart();  
		//添加到session
		request.getSession().setAttribute("cart", cart);
	}
		return cart;
	}
 
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
	//1.获取pid和获取数量
	String pid=	request.getParameter("pid");
	int count=Integer.parseInt(request.getParameter("count"));	
		
		//2.调用productService，通过pid获取一个商品
		ProductService ps  =(ProductService)BeanFactory.getBean("ProductService");
		Product product = ps.getById(pid);
		
		//3.组装成CartItem
		
  CartItem cartItem = 	new CartItem(product,count);
		
		//4.添加到购物车中
	Cart cart=	getCart(request);
		cart.add2Cart(cartItem);
		
		//5.重定向
		
		//response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		request.setAttribute("cart", cart);		
		 return "/jsp/cart.jsp";
		//return null;
	}
	
	//从购物车中移除购物项
	public String remove(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String pid = request.getParameter("pid");
		 getCart(request).removeFromCart(pid);
		
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}

}
