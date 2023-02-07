package com.hero.test;

import com.hero.service.UserService;

import java.util.Iterator;
import java.util.ServiceLoader;

public class JavaSPIDemo {
    public static void main(String[] args) {
        // 加载提供者配置文件，创建提供者类加载器
        ServiceLoader<UserService> loader = ServiceLoader.load(UserService.class);
        // ServiceLoader本身就是一个迭代器
        Iterator<UserService> it = loader.iterator();
        // 迭代加载每一个实现类，并生成每一个提供者对象
        while (it.hasNext()) {
            UserService service = it.next();
            service.doSth();
        }
    }
}
