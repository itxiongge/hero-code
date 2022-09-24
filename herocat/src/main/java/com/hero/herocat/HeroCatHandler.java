package com.hero.herocat;

import com.hero.servlet.HeroRequest;
import com.hero.servlet.HeroResponse;
import com.hero.servlet.HeroServlet;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.internal.StringUtil;

import java.util.Map;

/**
 * HeroCat服务端处理器
 *
 *   1）从用户请求URI中解析出要访问的Servlet名称
 *   2）从nameToServletMap中查找是否存在该名称的key。若存在，则直接使用该实例，否则执行第3）步
 *   3）从nameToClassNameMap中查找是否存在该名称的key，若存在，则获取到其对应的全限定性类名，
 *      使用反射机制创建相应的serlet实例，并写入到nameToServletMap中，若不存在，则直接访问默认Servlet
 *
 */
public class HeroCatHandler extends ChannelInboundHandlerAdapter {
    private Map<String, HeroServlet> nameToServletMap;//线程安全  servlet--> 对象
    private Map<String, String> nameToClassNameMap;//线程不安全  servlet--> 全限定名称

    public HeroCatHandler(Map<String, HeroServlet> nameToServletMap, Map<String, String> nameToClassNameMap) {
        this.nameToServletMap = nameToServletMap;
        this.nameToClassNameMap = nameToClassNameMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            String uri = request.uri();

            // 从请求中解析出要访问的Servlet名称
            //aaa/bbb/twoservlet?name=aa
            String servletName = "";
            if (uri.contains("?") && uri.contains("/")){
                servletName= uri.substring(uri.lastIndexOf("/") + 1, uri.indexOf("?"));
            }


            HeroServlet servlet = new DefaultHeroServlet();
            //第一次访问，Servlet是不会被加载的
            //初始化加载的只是类全限定名称，懒加载
            //如果访问Servlet才会去初始化它对象
            if (nameToServletMap.containsKey(servletName)) {
                servlet = nameToServletMap.get(servletName);
            } else if (nameToClassNameMap.containsKey(servletName)) {
                // double-check，双重检测锁：为什么要在锁前判断一次，还要在锁后继续判断一次？
                if (nameToServletMap.get(servletName) == null) {
                    synchronized (this) {
                        if (nameToServletMap.get(servletName) == null) {
                            // 获取当前Servlet的全限定性类名
                            String className = nameToClassNameMap.get(servletName);
                            // 使用反射机制创建Servlet实例
                            servlet = (HeroServlet) Class.forName(className).newInstance();
                            // 将Servlet实例写入到nameToServletMap
                            nameToServletMap.put(servletName, servlet);
                        }
                    }
                }
            } //  end-else if

            // 代码走到这里，servlet肯定不空
            HeroRequest req = new HttpHeroRequest(request);
            HeroResponse res = new HttpHeroResponse(request, ctx);
            // 根据不同的请求类型，调用servlet实例的不同方法
            if (request.method().name().equalsIgnoreCase("GET")) {
                servlet.doGet(req, res);
            } else if(request.method().name().equalsIgnoreCase("POST")) {
                servlet.doPost(req, res);
            }
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
