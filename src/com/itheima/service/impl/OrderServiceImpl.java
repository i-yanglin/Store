package com.itheima.service.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.dao.OrderDao;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;
import com.itheima.domain.User;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {

	@Override
	public void add(Order order) throws Exception {
	  
		try {
			   //1.��������
		DataSourceUtils.startTransaction();
		
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		
		//2.��order�����һ������
		od.add(order);
		
		
		//3.��orderitem�����n������
		for(OrderItem oi:order.getItems()) {
			od.addItem(oi);
		}
		//4.������
		DataSourceUtils.commitAndClose();
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	
		
	}
	
	//��ҳ��ѯ
	@Override
	public PageBean<Order> findAllPage(int currPage, int pageSize,User user) throws Exception {
		OrderDao od =(OrderDao) BeanFactory.getBean("OrderDao");
		
		//��ѯ��ǰҳ����
		List<Order> list =od.findAllPage(currPage,pageSize,user.getUid()); 
		
		//��ѯ������
		int totalCount =od.getTotalCount(user.getUid()); 
		return new PageBean<Order>(list,currPage,pageSize,totalCount);
	}

	@Override
	public Order getById(String oid) throws Exception {

		 OrderDao od = (OrderDao)BeanFactory.getBean("OrderDao");
		 
		return od.getById(oid);
	}

	@Override
	public void update(Order order) throws Exception {
		OrderDao od = (OrderDao)BeanFactory.getBean("OrderDao");
		od.update(order);
	}

}
