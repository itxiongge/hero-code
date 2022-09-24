package com.hero.rpc;

import com.hero.rpc.consumer.SkuService;
import com.hero.rpc.consumer.UserService;
import com.hero.rpc.consumerStub.HeroRPCProxy;

//服务调用方
public class TestHeroRPC {
    public static void main(String [] args){

        //第1次远程调用
        SkuService skuService=(SkuService) HeroRPCProxy.create(SkuService.class);
        String resp_Msg = skuService.findByName("uid");
        System.out.println(resp_Msg);

        //第2次远程调用
        UserService userService =  (UserService) HeroRPCProxy.create(UserService.class);
        System.out.println(userService.findById());
    }
}
