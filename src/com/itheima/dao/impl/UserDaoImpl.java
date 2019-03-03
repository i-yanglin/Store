package com.itheima.dao.impl;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.DataSourceUtils;
import com.itheima.web.servlet.UserServlet;


public class UserDaoImpl implements UserDao{
	
  public void add(User user)throws SQLException{
	  QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
	  
	  String sql="insert into user values(?,?,?,?,?,?,?,?,?,?);";
	  queryRunner.update(sql,user.getUid(),user.getUsername(),
			  user.getPassword(),
			  user.getName(),user.getEmail(),
			  user.getTelephone(),
			  user.getBirthdaty(),user.getSex(),
			  user.getState(),user.getCode());
  }
  /*
    通过激活码获取一个用户
   */

@Override
public User getByCode(String code) throws Exception {
	QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
	String sql = "select * from user where code=? limit 1";

	return qr.query(sql, new BeanHandler<>(User.class),code);
}
/*
修改用户
 */
@Override
public void update(User user) throws Exception {
	
	QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
	String sql = "update user set username=?,password=?,name=?,email=?,birthday=?,state=?,code=? where uid=?";
	qr.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),
			user.getBirthdaty(),user.getState(),null,user.getUid());
	
	
	
}

@Override
public User getByUsernameAndPwd(String username, String password) throws Exception {
	 QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
	 String sql= "select * from user where username=? and password=? limit  1";
	 return qr.query(sql, new BeanHandler<>(User.class),username,password);
	 
}
  
  
  
}
