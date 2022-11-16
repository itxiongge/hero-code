package com.hero;

import com.alibaba.fastjson.JSON;
import com.hero.mapper.UserMapper;
import com.hero.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.hero.Utils.HeroPager;

public class HeroPagerExample {

    private UserMapper userMapper;

    private SqlSession sqlSession;

    @Before
    public void init() throws IOException {
        // 获取配置文件输入流
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        // 通过SqlSessionFactoryBuilder的build()方法创建SqlSessionFactory实例
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 调用openSession()方法创建SqlSession实例
        sqlSession = sqlSessionFactory.openSession();
        // 获取UserMapper代理对象
        userMapper = sqlSession.getMapper(UserMapper.class);
        //初始化一些数据
        initUsers(userMapper);
    }

    private void initUsers(UserMapper userMapper) {
        userMapper.deleteAll();

        for (int i = 1; i < 20; i++) {
            User user = User.builder().id(Long.valueOf(i)).name("刘备【" + i + "】").age(11).address("蜀国").build();
            userMapper.save(user);
        }
        sqlSession.commit();
    }

    @Test
    public void testPageInterceptor() {
        HeroPager pager = new HeroPager();
        pager.setPageSize(5);
        pager.setFull(true);
        List<User> users = userMapper.getUserPageable(pager);
        System.out.println("总数据量：" + pager.getTotalCount() + ",总页数：" + pager.getTotalPage()+ "，当前查询数据：" + JSON.toJSONString(users));
        sqlSession.close();
    }

}
