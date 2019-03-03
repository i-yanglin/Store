package com.itheima.service.impl;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.MailUtils;

public class UserServiceImpl implements UserService{
  //�û�ע��
	public void regist(User user) throws Exception{
		UserDao dao = new UserDaoImpl();
		dao.add(user);
		//�����ʼ�
		
		//email���ռ��˵�ַ   emailMsg���ʼ�������
		String emailMsg ="��ӭ��ע���Ϊ���ǵ�һԱ��<a href='http://localhost/Sstore/user?method=acitive&code="+user.getCode()+"'>��μ���</a>";
		MailUtils.sendMail(user.getEmail(), emailMsg);
	}

	/*
	 * �û�����
	 * */
	@Override
	public User active(String code) throws Exception    {
		 UserDao dao = new UserDaoImpl();
		 //1.ͨ��code��ȡһ���û�
		 User user = dao.getByCode(code);
		 
		 //2.�ж��û��Ƿ�Ϊ��
		 if(user ==null) {
			 return null;
		 }
		 //3.�޸��û�״̬   �����û�״̬��
		 user.setState(1);
		 
		 dao.update(user);
		return user;
	}
	
	//�û���¼

	@Override
	public User login(String username, String password) throws Exception {
		 UserDao dao = new UserDaoImpl();
		
		 return  dao.getByUsernameAndPwd(username,password);
		 
	}
}
