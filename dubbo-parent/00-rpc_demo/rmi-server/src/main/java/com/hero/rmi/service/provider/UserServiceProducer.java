package com.hero.rmi.service.provider;

import com.hero.rmi.service.UserService;
import com.hero.rmi.service.impl.UserServiceImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
//http请求没有本质区别，底层都是基于TCP/IP
public class UserServiceProducer {
    public static void main(String[] args) {
        try {
            UserService userService = new UserServiceImpl();
            LocateRegistry.createRegistry(8888);
            //暴露服务
            Naming.bind("rmi://localhost:8888/UserService",userService);
            System.out.println("=======User-金莲-提供服务=======");
        } catch (RemoteException | AlreadyBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
