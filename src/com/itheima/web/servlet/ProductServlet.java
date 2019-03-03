package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.utils.BeanFactory;

/**
 * 商品servlet
 */
public class ProductServlet extends BaseServlet {

	
	
	public String findByPage (HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//1。获取类别，当前页，设置一个pagesize
		String cid = request.getParameter("cid");
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize=12;
		//2.调用service 返回值pagebean
		ProductService ps = (ProductService)BeanFactory.getBean("ProductService");
		PageBean<Product> bean = ps.findByPage(currPage,pageSize,cid);
		//3.将结果放入request中 请求转发
		
		request.setAttribute("pb", bean);

		return "/jsp/product_list.jsp";
	}
	
	public String getById (HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1.获取商品的id
				String pid = request.getParameter("pid");
				System.out.println("获取的商品id："+pid);
				//2.调用service
				
				//ProductService ps = 
				//(ProductService)BeanFactory.getBean("ProductService");
				ProductService ps= new ProductServiceImpl();
				Product p = ps.getById(pid);
				
				//3.将结果放入request，请求转发到页面展示
				request.setAttribute("bp", p);		
				 return "/jsp/product_info.jsp";
	}

}
