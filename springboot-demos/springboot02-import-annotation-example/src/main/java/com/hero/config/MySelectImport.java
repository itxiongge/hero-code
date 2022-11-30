package com.hero.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 批量导入对象到容器的类
 */
public class MySelectImport implements ImportSelector {
    //返回需要导入Spring容器中的所有的对象全限定名称
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        String[] strings = new String[]{
                "com.hero.service.UserServiceImpl",
                "com.hero.pojo.User"
        };
        return strings;
    }
}
