package com.hero.rmi.service.impl;

import com.hero.rmi.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {
    //构造
    public UserServiceImpl() throws RemoteException {
        super();
    }
    @Override
    public String sayHello(String name) {
        return "金莲say你好: " + name;
    }
}
