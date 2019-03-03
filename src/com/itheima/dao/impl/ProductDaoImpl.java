package com.itheima.dao.impl;

import java.util.List;



import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.ProductDao;
import com.itheima.domain.Product;
import com.itheima.utils.DataSourceUtils;


public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findNew() throws Exception {
		// 查询最新商品
		QueryRunner qRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product order by pdate limit 9";	
		return qRunner.query(sql,new BeanListHandler<>(Product.class) );
	}

	@Override
	public List<Product> findHot() throws Exception {
		// 查询热门商品
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where is_hot = 1 order by pdate limit 9";
		
		return qr.query(sql,new BeanListHandler<>(Product.class) );
	}

	@Override
	public Product getById(String pid) throws Exception {
		 QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		 String sql = "select * from product where pid = ? limit ?";
		return qr.query(sql, new BeanHandler<Product>(Product.class),pid,1);
		
	}
	
	

	@Override
	public List<Product> findByPage(int currPage, int pageSize, String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where cid=? limit ?,?";
		
		return qr.query(sql, new BeanListHandler<>(Product.class),cid,(currPage-1)*pageSize,pageSize);
	}

	@Override
	public int getTotalCount(String cid) throws Exception {
		 QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		 String sql="select count(*) from product where cid=?";
		return ((Long)qr.query(sql, new ScalarHandler(),cid)).intValue();
	}
//更新商品的cid  为删除分类的时候准备
	@Override
	public void updateCid(String cid) throws Exception {
		QueryRunner qr = new QueryRunner();
		String sql = "update product set cid = null where cid = ?";
		qr.update(DataSourceUtils.getConnection(),sql,cid);
	}

}
