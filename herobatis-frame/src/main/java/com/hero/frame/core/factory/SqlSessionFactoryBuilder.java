package com.hero.frame.core.factory;

import com.hero.frame.core.entity.Configuration;
import com.hero.frame.core.entity.SqlSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * 工厂对象构建器对象，负责构建工厂对象，解析配置文件
 */
public class SqlSessionFactoryBuilder {

    /**
     * 构建工厂对象
     * 参数：SqlMapConfig.xml配置文件的输入流对象
     */
    public SqlSessionFactory build(InputStream inputStream) throws DocumentException {
        Configuration configuration = new Configuration();
        //解析配置文件
        loadXmlConfig(configuration,inputStream);
        return new SqlSessionFactory(configuration);
    }

    /**
     * 解析框架使用者传入的配置文件
     */
    private void loadXmlConfig(Configuration configuration,InputStream inputStream) throws DocumentException {
        //创建解析XML文件对象SAXReader
        SAXReader saxReader = new SAXReader();
        //读取SqlMapConfig.xml配置文件流资源，获取文档对象
        Document document = saxReader.read(inputStream);
        //获取SqlMapConfig.xml 配置文件内所有property标签元素
        List<Element> selectNodes = document.selectNodes("//property");
        //循环解析property标签内容，抽取配置信息
        for (Element element : selectNodes) {
            String name = element.attributeValue("name");
            if ("driver".equals(name)){//数据库驱动
                configuration.setDriver(element.attributeValue("value"));
            } else if ("url".equals(name)){//数据库地址
                configuration.setUrl(element.attributeValue("value"));
            } else if ("username".equals(name)){//用户名
                configuration.setUsername(element.attributeValue("value"));
            } else if ("password".equals(name)){//密码
                configuration.setPassword(element.attributeValue("value"));
            }
        }
        //解析SqlMapConfig.xml 映射器配置信息
        List<Element> list = document.selectNodes("//mapper");
        for (Element element : list) {
            //SQL映射配置文件路径
            String resource = element.attributeValue("resource");
            //解析SQL映射配置文件
            loadSqlConfig(resource,configuration);
        }
    }
    /**
     * 解析SQL配置文件
     */
    private void loadSqlConfig(String resource, Configuration configuration) throws DocumentException {
        //根据SQL映射配置文件路径，读取流资源。classpath路径下
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        //创建解析XML文件的对象SAXReader
        SAXReader saxReader = new SAXReader();
        //读取UserMapper.xml配置文件文档对象
        Document document = saxReader.read(inputStream);
        //获取文档对象根节点：<mapper namespace="test">
        Element rootElement = document.getRootElement();
        //取出根节点的命名空间
        String namespace = rootElement.attributeValue("namespace");
        //获取当前SQL映射文件所有查询语句标签
        List<Element> selectNodes = document.selectNodes("//select");
        //循环解析查询标签select，抽取SQL语句
        for (Element element : selectNodes) {
            //查询语句唯一标识
            String id = element.attributeValue("id");
            //当前查询语句返回结果集对象类型
            String resultType = element.attributeValue("resultType");
            //查询语句
            String sql = element.getText();
            //创建Mapper对象
            SqlSource mapper = new SqlSource();
            mapper.setSql(sql);
            mapper.setResultType(resultType);
            //在configuration中设置mapper类，key：(命名空间+.+SQL语句唯一标识符)
            configuration.getSqlSourceMap().put(namespace+"."+id,mapper);
        }
    }
}
