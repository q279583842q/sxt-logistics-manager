package com.sxt.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.github.pagehelper.PageHelper;

public class Test1 {

	public static void main(String[] args) throws IOException {
		// 获取配置文件
		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
		// 通过加载配置文件获取SqlSessionFactory对象
		// 将PageHelper拦截器加载到了拦截器链中
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		// 获取SqlSession对象 获取了一个Executor的增强代理类
		SqlSession session = factory.openSession();
		// 将 pageNum和pageSize封装到了 Page对象中，同时将Page对象保存到了当前线程的本地变量中的。
		PageHelper.startPage(1, 5);
		session.selectList("com.bobo.UserMapper.query");
		
	}

}
