package com.hero.demos;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Demo01CRUD {
    //创建节点
    @Test
    public void demohelloworld() throws Exception {
        //1.创建Zookeeper的客户端对象，配置重试策略
        //参数1 : baseSleepTimeMs 两个次重试之间休眠时间
        //参数2 : maxRetries 最大重试次数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 3);
        String zkConnectUrl = "127.0.0.1:2181";
        //参数1: connectString服务ip地址:端口
        //参数2: retryPolicy重试策略
        //参数3: sessionTimeoutMs 会话超时时间
        //参数4: connectionTimeoutMs 连接超时时间
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkConnectUrl, retryPolicy);
        //2.开启客户端
        client.start();
        //3.创建节点对象
        client.create().forPath("/app3");
        //4.关闭客户端，释放资源
        client.close();
    }
    CuratorFramework client;

    @Before
    public void initClient() {
        //初始化Zookeeper的客户端对象client
        String connectString = "127.0.0.1:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 100);
        int sessionTimeoutMs = 60 * 1000;//当前客户端会话超时时间
        int connectionTimeoutMs = 15 * 1000;//连接超时时间
        client =  CuratorFrameworkFactory
                .newClient(connectString, sessionTimeoutMs, connectionTimeoutMs, retryPolicy);
    }
    @After
    public void destroy(){
        client.close();
    }

    @Test
    public void createNode() throws Exception {
        //3种方式，四种节点类型
        //1.开启客户端
        client.start();
        //2.创建节点对象
        //方式1: 创建空节点
        //client.create().forPath("/app2");
        //方式2: 创建有内容节点
//        client.create().forPath("/app3","app3Node".getBytes());
        //方式3: 创建多层节点
        //client.create().creatingParentsIfNeeded().forPath("/app4/a","aa".getBytes());
        //节点节点类型1 : 持久节点CreateMode.PERSISTENT
        //节点节点类型2 : 临时节点【客户端关闭则节点消失】CreateMode.EPHEMERAL
        //client.create().withMode(CreateMode.EPHEMERAL).forPath("/app5","app5Node".getBytes());
        //节点节点类型3 : 持久节点+自带序号 CreateMode.PERSISTENT_SEQUENTIAL
        //client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/app6","app6Node".getBytes());
        //节点节点类型4 : 临时节点+自带序号【客户端关闭则节点消失】CreateMode.EPHEMERAL_SEQUENTIAL
        //client.create().forPath("/app5","app5Node".getBytes());
        //client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/app6","app6Node".getBytes());

        Thread.sleep(9000);//休眠9秒，观察效果
        //3.关闭客户端，释放资源
        client.close();
    }
    @Test
    public void updateNode() throws Exception {
        //1.开启客户端
        client.start();
        //2.修改节点
        client.setData().forPath("/app1", "app1Node".getBytes());
        //3.关闭客户端，释放资源
        client.close();
    }
    @Test
    public void getNode() throws Exception {
        //1.开启客户端
        client.start();
        //2.获取节点数据
        byte[] nodeDateBytes = client.getData().forPath("/app1");
        System.out.println("节点数据 = " + new String(nodeDateBytes));
        //3.关闭客户端，释放资源
        client.close();
    }

    @Test
    public void deleteNode() throws Exception {
        //1.开启客户端
        client.start();
        //2.获取节点数据
        //方式1: 删除一个节点
        //client.delete().forPath("/app3");
        //方式2: 递归删除多个节点
        //client.delete().deletingChildrenIfNeeded().forPath("/app3");
        //方式3: 强制删除【避免一些因为网络传输导致的删除不成功】
        client.delete().guaranteed().forPath("/app2");
        //3.关闭客户端，释放资源
        client.close();
    }
}
