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
 
	//չʾ���еķ���
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.����categoryService ��ѯ���еķ�����Ϣ������ֵ list
		CategoryService cs = (CategoryService)BeanFactory.getBean("CategoryService");
		List<Category> cList = cs.findAll();
		//2.��list����request����  ����ת������
			
			
			request.setAttribute("list", cList);
			return "/admin/category/list.jsp";
		
		
	}  
	//��ת�����ҳ��
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "/admin/category/add.jsp";
	}
	
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//1.����cname
		String cname=request.getParameter("cname");
		
		//2.��װcategory
		Category category  = new Category();
		category.setCid(UUIDUtils.getId());
		category.setCname(cname);
		//3.����service �����Ӳ���
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

