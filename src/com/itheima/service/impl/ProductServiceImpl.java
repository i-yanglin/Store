package com.itheima.service.impl;

import java.util.List;

import com.itheima.dao.ProductDao;
import com.itheima.dao.impl.ProductDaoImpl;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {

	@Override
	public List<Product> findNew() throws Exception {
		// ��ѯ������Ʒ
		
		ProductDao pdao = new ProductDaoImpl();
		
		return pdao.findNew();
	}

	@Override
	public List<Product> findHot() throws Exception {
		// ��ѯ������Ʒ
ProductDao pdao = new ProductDaoImpl();
		
		return pdao.findHot();
	}
	
	
	
//��ѯ������Ʒ����
	@Override
	public Product getById(String pid) throws Exception {
	//	ProductDao pdao=(ProductDao) BeanFactory.getBean("ProductDao");
		//ProductDao pdao = new ProductDaoImpl();
		//return pdao.getById(pid);
		return new ProductDaoImpl().getById(pid);
	}
	
	
	//������ҳ��ѯ��Ʒ

	@Override
	public PageBean<Product> findByPage(int currPage, int pageSize, String cid) throws Exception {
		ProductDao pdao = new ProductDaoImpl();
		
		//��ǰҳ����
		List<Product> list = pdao.findByPage(currPage,pageSize,cid);
		
		//������
		int totalCount = pdao.getTotalCount(cid);
		
		return new PageBean<>(list,currPage,pageSize,totalCount);
	}
  
}
