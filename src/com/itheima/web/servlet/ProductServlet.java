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
 * ��Ʒservlet
 */
public class ProductServlet extends BaseServlet {

	
	
	public String findByPage (HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//1����ȡ��𣬵�ǰҳ������һ��pagesize
		String cid = request.getParameter("cid");
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize=12;
		//2.����service ����ֵpagebean
		ProductService ps = (ProductService)BeanFactory.getBean("ProductService");
		PageBean<Product> bean = ps.findByPage(currPage,pageSize,cid);
		//3.���������request�� ����ת��
		
		request.setAttribute("pb", bean);

		return "/jsp/product_list.jsp";
	}
	
	public String getById (HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1.��ȡ��Ʒ��id
				String pid = request.getParameter("pid");
				System.out.println("��ȡ����Ʒid��"+pid);
				//2.����service
				
				//ProductService ps = 
				//(ProductService)BeanFactory.getBean("ProductService");
				ProductService ps= new ProductServiceImpl();
				Product p = ps.getById(pid);
				
				//3.���������request������ת����ҳ��չʾ
				request.setAttribute("bp", p);		
				 return "/jsp/product_info.jsp";
	}

}
