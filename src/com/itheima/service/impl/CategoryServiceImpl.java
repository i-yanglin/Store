package com.itheima.service.impl;

import java.io.InputStream;
import java.util.List;

import org.dom4j.bean.BeanDocumentFactory;

import com.itheima.dao.CategoryDao;
import com.itheima.dao.ProductDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.DataSourceUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
 

 

public class CategoryServiceImpl implements CategoryService {

	/**
	 * 查询所有的分类
	 */
	@Override
	public List<Category> findAll() throws Exception{

		//1.创建缓存管理器
		 InputStream is=CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml");
		 CacheManager cm = CacheManager.create(is);
		
		//2.获取指定的缓存
	    Cache cache= cm.getCache("categoryCache");
		//3.通过指定的缓存获取数据,
		Element element=cache.get("clist");
		
		//4.判断数据是否为空
		List<Category> list = null;
		if(element ==null) {
			//从数据库中获取
			 CategoryDao cd = new CategoryDaoImpl();
			list= cd.findAll();
			
			//将list放入缓存
			cache.put(new Element("clist",list));
		System.out.println("缓存中没有数据，已去数据库中获取");
		}else {
			//直接返回
			list = (List<Category>)element.getObjectValue();
			System.out.println("缓存中有数据");
		}
	
		 
		return list;
	}

	//添加分类
	@Override
	public void add(Category category) throws Exception {
		 CategoryDao cd = (CategoryDao)BeanFactory.getBean("CategoryDao");
		 cd.add(category);
		//更新缓存

			//1.创建缓存管理器
			 InputStream is=CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml");
			 CacheManager cm = CacheManager.create(is);
			//2.获取指定的缓存
			 Cache cache = cm.getCache("categoryCache");
			 //3.清空
			 cache.remove("clist");
	}

	@Override
	public Category getById(String cid) throws Exception {
		 CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		  
		
		return cd.getById(cid);
	}

	@Override
	public void update(Category category) throws Exception {
		 CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
			cd.update(category);
		//清空缓存

			//1.创建缓存管理器
			 InputStream is=CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml");
			 CacheManager cm = CacheManager.create(is);
			//2.获取指定的缓存
			 Cache cache = cm.getCache("categoryCache");
			 //3.清空
			 cache.remove("clist");
	}

	@Override
	public void delete(String cid) throws Exception {
		try {
		DataSourceUtils.startTransaction();
		//2更新商品
		ProductDao pd=(ProductDao)BeanFactory.getBean("ProductDao");
		pd.updateCid(cid);
		//3删除分类
		
		 CategoryDao cd = (CategoryDao)BeanFactory.getBean("CategoryDao");
		cd.delete(cid);
		DataSourceUtils.rollbackAndClose();
		}catch(Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}
	 

}
