package com.hero.rmi.client;

import com.hero.rmi.service.UserService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class UserServiceConsumer {

    public static void main(String[] args) {
        try {
            //远程获取服务
            UserService us = (UserService)Naming.lookup("rmi://localhost:8888/UserService");
            //调用服务
            System.out.println(us.sayHello("西门大官人"));

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
