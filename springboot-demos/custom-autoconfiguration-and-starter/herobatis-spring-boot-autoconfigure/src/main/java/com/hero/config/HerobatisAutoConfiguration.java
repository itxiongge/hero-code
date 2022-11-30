package com.hero.config;

import com.hero.frame.core.factory.SqlSessionFactory;
import com.hero.frame.core.factory.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.InputStream;

/**
 * Herobatis的自动配置类
 * 注入配置后的SqlSessionFactory对象到IoC容器，谁依赖starter坐标，就注入到哪里去
 * 可以通过herobatis.frame.enable开启和关闭
 */
@Configuration
@ConditionalOnClass(name = "com.hero.frame.core.factory.SqlSessionFactory")
@Import(DatabaseProperties.class)
public class HerobatisAutoConfiguration {

    /**
     * 注入核心对象到Spring的容器中
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "herobatis.frame.enable",havingValue = "true",matchIfMissing = true)
    public SqlSessionFactory initFactory(){
        SqlSessionFactory sqlSessionFactory = null;
        try {
            //1.创建SqlSessionFactoryBuilder对象
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            //2.builder对象构建工厂对象
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml");
            sqlSessionFactory = builder.build(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return sqlSessionFactory;
    }

}
