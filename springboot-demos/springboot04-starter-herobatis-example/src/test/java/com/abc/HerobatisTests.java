package com.abc;

import com.abc.pojo.User;
import com.hero.frame.core.factory.SqlSessionFactory;
import com.hero.frame.openapi.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class HerobatisTests {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    void selectList() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> users = sqlSession.selectList("com.abc.dao.UserMapper.findAll");

        users.stream().forEach(System.out::println);
    }

}
