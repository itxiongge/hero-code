package com.hero;

import com.hero.frame.core.factory.SqlSessionFactory;
import com.hero.frame.core.factory.SqlSessionFactoryBuilder;
import com.hero.frame.openapi.SqlSession;
import com.hero.pojo.User;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class CustomFrameTest {

    SqlSession sqlSession;
    @Before
    public void init() throws Exception{
        //1.创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //2.builder对象构建工厂对象
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
        //3.打开SqlSession会话
        sqlSession = sqlSessionFactory.openSession();
    }
    @Test
    public void test() throws Exception {
        //4.执行查询Sql语句
        List<User> users = sqlSession.selectList("com.hero.dao.UserMapper.findAll");
        //5.循环打印
        for (User u : users) {
            System.out.println(u);
        }
    }
}
