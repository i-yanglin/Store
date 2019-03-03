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
		// 查询最新商品
		
		ProductDao pdao = new ProductDaoImpl();
		
		return pdao.findNew();
	}

	@Override
	public List<Product> findHot() throws Exception {
		// 查询热门商品
ProductDao pdao = new ProductDaoImpl();
		
		return pdao.findHot();
	}
	
	
	
//查询单个商品详情
	@Override
	public Product getById(String pid) throws Exception {
	//	ProductDao pdao=(ProductDao) BeanFactory.getBean("ProductDao");
		//ProductDao pdao = new ProductDaoImpl();
		//return pdao.getById(pid);
		return new ProductDaoImpl().getById(pid);
	}
	
	
	//按类别分页查询商品

	@Override
	public PageBean<Product> findByPage(int currPage, int pageSize, String cid) throws Exception {
		ProductDao pdao = new ProductDaoImpl();
		
		//当前页数据
		List<Product> list = pdao.findByPage(currPage,pageSize,cid);
		
		//总条数
		int totalCount = pdao.getTotalCount(cid);
		
		return new PageBean<>(list,currPage,pageSize,totalCount);
	}
  
}
