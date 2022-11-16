package com.hero;

import com.hero.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisExample {
    /**
     * 学习框架：
     *   1. 导包，SpringBoot自动导包！
     *   2. 配置，SpringBoot自动配置AutoConfiguration
     *   3. 核心API【框架暴露的接口，接口怎么使用！】
     */
    @Test
    public void test() throws IOException {
        //1.创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //2.Builder对象构建工厂对象
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
        //3.工厂对象Factory打开SqlSession会话
        //MyBatis提供的Open-Api： SqlSession对外接口
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //4.SqlSession会话对象执行SQL语句，selectList(命名空间+查询语句唯一标识)
        List<User> users = sqlSession.selectList("test.findAll");
        //5.打印查询结果
        for (User u : users) {
            System.out.println(u);
        }
        //6.关闭SqlSession会话
        sqlSession.close();
    }
}
