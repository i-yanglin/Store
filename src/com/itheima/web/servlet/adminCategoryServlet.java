package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 

import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
 
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
 

/**
 * Servlet implementation class adminCategoryServlet
 */
public class adminCategoryServlet extends BaseServlet {
 
	//展示所有的分类
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.调用categoryService 查询所有的分类信息，返回值 list
		CategoryService cs = (CategoryService)BeanFactory.getBean("CategoryService");
		List<Category> cList = cs.findAll();
		//2.将list放入request域中  请求转发即可
			
			
			request.setAttribute("list", cList);
			return "/admin/category/list.jsp";
		
		
	}  
	//跳转到添加页面
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "/admin/category/add.jsp";
	}
	
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//1.接受cname
		String cname=request.getParameter("cname");
		
		//2.封装category
		Category category  = new Category();
		category.setCid(UUIDUtils.getId());
		category.setCname(cname);
		//3.调用service 完成添加操作
		CategoryService cs = (CategoryService)BeanFactory.getBean("CategoryService");
		cs.add(category);
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		return null;
	}
	
	
	
	
	
	public String getById(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String cid = request.getParameter("cid");
		
		CategoryService as = (CategoryService)BeanFactory.getBean("CategoryService");
	   Category category =	as.getById(cid);
		request.setAttribute("bean", category);
		return "/admin/category/edit.jsp";
	}

	public String update(HttpServletRequest request, HttpServletResponse response) throws Exception{
	 
		Category category =new Category();
		category.setCid(request.getParameter("cid"));
		category.setCname(request.getParameter("cname"));
		
		CategoryService cs = (CategoryService)BeanFactory.getBean("CategoryService"); 
		cs.update(category);
		
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		return null;
	}
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String cid = request.getParameter("cid");
		
		CategoryService cs =(CategoryService)BeanFactory.getBean("CategoryService");
		 cs.delete(cid);
		 response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
	}

}

