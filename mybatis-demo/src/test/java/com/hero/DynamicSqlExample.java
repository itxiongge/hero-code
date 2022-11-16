package com.hero;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DynamicSqlExample {
    @Test
    public void testSqlNode() throws IOException {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //构建SqlNode
        SqlNode snl = new StaticTextSqlNode("select * from user where 1=1");
        SqlNode sn2 = new IfSqlNode(new StaticTextSqlNode("AND id=#{id}"), "id != null");
        SqlNode sn3 = new IfSqlNode(new StaticTextSqlNode("AND name=#{name}"), "name != null");
        SqlNode sn4 = new IfSqlNode(new StaticTextSqlNode(" AND phone=#{phone}"), "phone != null");
        SqlNode mixedsqlNode = new MixedSqlNode(Arrays.asList(snl, sn2, sn3, sn4));
        //创建参数对象
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", "1");
        //创建动态SQL解析上下文
        DynamicContext context = new DynamicContext(sqlSession.getConfiguration(), paramMap);
        //调用SqlNode的apply()方法解析动态SQL
        mixedsqlNode.apply(context);
        //调用DynamicContext对象的getSql()方法获取动态SQL解析后的SQL语句
        System.out.println(context.getSql());
    }
}
