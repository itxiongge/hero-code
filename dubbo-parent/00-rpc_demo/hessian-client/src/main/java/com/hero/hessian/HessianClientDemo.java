package com.hero.hessian;

import com.caucho.hessian.client.HessianProxyFactory;
import com.hero.hessian.service.UserService;

import java.net.MalformedURLException;

public class HessianClientDemo {

    public static void main(String[] args) throws MalformedURLException {
        String url =  "http://localhost:8888/api/service";
        HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
        UserService userService = (UserService) hessianProxyFactory.create(UserService.class, url);
        System.out.println(userService.sayHello("西门大官人"));
    }
}
