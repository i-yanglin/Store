package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.service.CategoryService;
import com.itheima.service.ProductService;
import com.itheima.service.impl.CategoryServiceImpl;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.utils.JsonUtil;

import java.util.*;
import com.itheima.domain.*;

public class IndexServlet extends BaseServlet {
	
	public String index(HttpServletRequest request, HttpServletResponse response)    {
	 //ȥ���ݿ��в�ѯ���� ��Ʒ��������Ʒ�������Ƿ���request��������ת��
		ProductService ps = new ProductServiceImpl();
		List<Product> newList = null;
		List<Product> hotList =null;
		try {
			newList = ps.findNew();
			hotList = ps.findHot();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//������list��������
		
		request.setAttribute("newList", newList);
		request.setAttribute("hotList", hotList);
		
		
		return "/jsp/index.jsp";
	}
	 
}
