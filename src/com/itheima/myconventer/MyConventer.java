package com.itheima.myconventer;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

import sun.java2d.pipe.SpanShapeRenderer.Simple;

public class MyConventer implements Converter{

	@Override
	public Object convert(Class arg0, Object value) {
		  // class 要转成的类型
		
		  //
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-ddd");
		try {
			Date date = (Date) sdf.parse((String)value);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}

}
