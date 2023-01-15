package com.hero.demos;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Demo02Watch {

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

    /**
     * 1.监听节点数据变化
     */
    @Test
    public void listenNode() throws Exception {
        //1.启动客户端
        client.start();
        System.out.println("连接Zookeeper成功~~~~~~~~~~~~~~~~~~~");
        //2.创建节点监听对象NodeCache : 设置监听节点、监听回调方法
        NodeCache nodeCache = new NodeCache(client, "/hero");
        //设置监听节点
        ChildData currentData = nodeCache.getCurrentData();
        System.out.println("当前节点数据 = " + currentData);
        //开启监听
        nodeCache.start(true);
        //设置监听回调方法
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            /**
             * 如果节点数据有变化，回调当前方法
             */
            @Override
            public void nodeChanged() {
                //获取当前节点的数据
                ChildData currentData = nodeCache.getCurrentData();
                //获取最新节点名称
                String path = currentData.getPath();
                System.out.println("节点名称 = " + path);
                //获取最新节点数据
                byte[] currentDataByte = currentData.getData();
                System.out.println("修改后节点数据" + new String(currentDataByte));
                System.out.println("--------------->>");
            }
        });
        //阻塞主线程
        //从输入流中读取数据的下一个字节。
        //System.in.read();
        Thread.sleep(100000);
    }

    /**
     * 2.监听当前节点的子节点变化，不含当前节点
     */
    @Test
    public void listenSubNode() throws Exception {
        //1.启动客户端
        client.start();
        System.out.println("连接Zookeeper成功~~~~~~~~~~~~~~~~~~~");
        //2.创建节点监听对象PathChildrenCache :
        // 参数3，是否缓存数据
        PathChildrenCache childrenCache = new PathChildrenCache(client, "/hero", true);
        // 开启监听
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

        // 设置监听回调方法
        childrenCache.getListenable().addListener((client, event) -> {
            //获取修改的数据
            byte[] bytes = event.getData().getData();
            System.out.println("节点内数据 = " + new String(bytes));
            //获取被修改的子节点
            System.out.println("节点名称 = " + event.getData().getPath());
            //获取事件类型
            //PathChildrenCacheEvent.Type.CONNECTION_RECONNECTED 重新连接
            //PathChildrenCacheEvent.Type.connection_lost 连接丢失
            //PathChildrenCacheEvent.Type.connection_suspended 连接暂停
            //PathChildrenCacheEvent.Type.INITIALIZED 初始化
            //PathChildrenCacheEvent.Type.CHILD_REMOVED 子节点移除
            //PathChildrenCacheEvent.Type.CHILD_ADDED 子节点添加
            //PathChildrenCacheEvent.Type.CHILD_UPDATED 子节点修改
            PathChildrenCacheEvent.Type type = event.getType();
            System.out.println("事件触发类型 = " + type);
            System.out.println("--------------------------------------------------->>");
        });
        //3.阻塞程序
        System.in.read();
    }

    /**
     * 3.树形监听所有下级节点变化【模式1+模式2】，含节点数据变更
     */
    @Test
    public void treeCache() throws Exception {
        //1.启动客户端
        client.start();
        System.out.println("连接Zookeeper成功~~~~~~~~~~~~~~~~~~~");
        //2.创建节点监听对象TreeCache :
        TreeCache treeCache = new TreeCache(client, "/hero");
        //启动缓存
        treeCache.start();
        //添加监听回调方法
        treeCache.getListenable().addListener((client, event) -> {
            //获取修改的数据
            byte[] bytes = event.getData().getData();
            System.out.println("节点内数据 = " + new String(bytes));
            //获取被修改的子节点
            System.out.println("节点名称 = " + event.getData().getPath());
            //获取事件类型
            //TreeCacheEvent.Type.CONNECTION_RECONNECTED 重新连接
            //TreeCacheEvent.Type.connection_lost 连接丢失
            //TreeCacheEvent.Type.connection_suspended 连接暂停
            //TreeCacheEvent.Type.INITIALIZED 初始化
            //TreeCacheEvent.Type.NODE_REMOVED 子节点移除
            //TreeCacheEvent.Type.NODE_ADDED 子节点添加
            //TreeCacheEvent.Type.NODE_UPDATED 子节点修改
            TreeCacheEvent.Type type = event.getType();
            System.out.println("事件触发类型 = " + type);
            System.out.println("--------------------------------------------------->>");
        });
        //3.阻塞程序
        System.in.read();
    }

}
