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
	 * ��ѯ���еķ���
	 */
	@Override
	public List<Category> findAll() throws Exception{

		//1.�������������
		 InputStream is=CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml");
		 CacheManager cm = CacheManager.create(is);
		
		//2.��ȡָ���Ļ���
	    Cache cache= cm.getCache("categoryCache");
		//3.ͨ��ָ���Ļ����ȡ����,
		Element element=cache.get("clist");
		
		//4.�ж������Ƿ�Ϊ��
		List<Category> list = null;
		if(element ==null) {
			//�����ݿ��л�ȡ
			 CategoryDao cd = new CategoryDaoImpl();
			list= cd.findAll();
			
			//��list���뻺��
			cache.put(new Element("clist",list));
		System.out.println("������û�����ݣ���ȥ���ݿ��л�ȡ");
		}else {
			//ֱ�ӷ���
			list = (List<Category>)element.getObjectValue();
			System.out.println("������������");
		}
	
		 
		return list;
	}

	//��ӷ���
	@Override
	public void add(Category category) throws Exception {
		 CategoryDao cd = (CategoryDao)BeanFactory.getBean("CategoryDao");
		 cd.add(category);
		//���»���

			//1.�������������
			 InputStream is=CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml");
			 CacheManager cm = CacheManager.create(is);
			//2.��ȡָ���Ļ���
			 Cache cache = cm.getCache("categoryCache");
			 //3.���
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
		//��ջ���

			//1.�������������
			 InputStream is=CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml");
			 CacheManager cm = CacheManager.create(is);
			//2.��ȡָ���Ļ���
			 Cache cache = cm.getCache("categoryCache");
			 //3.���
			 cache.remove("clist");
	}

	@Override
	public void delete(String cid) throws Exception {
		try {
		DataSourceUtils.startTransaction();
		//2������Ʒ
		ProductDao pd=(ProductDao)BeanFactory.getBean("ProductDao");
		pd.updateCid(cid);
		//3ɾ������
		
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
