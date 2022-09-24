package com.hero.herocat;

import com.hero.servlet.HeroRequest;
import com.hero.servlet.HeroResponse;
import com.hero.servlet.HeroServlet;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.internal.StringUtil;

import java.io.File;
import java.io.FileInputStream;

/**
 * HeroCat中对Servlet规范的默认实现
 * 注意：HeroCat不能放在中文目录下！
 */
public class DefaultHeroServlet extends HeroServlet {
    @Override
    public void doGet(HeroRequest request, HeroResponse response) throws Exception {
        // http://localhost:8080/aaa/bbb/userservlet?name=xiong
        // path：/aaa/bbb/userservlet?name=xiong
        String uri = request.getUri();
        response.write("404 - no this servlet : " + (uri.contains("?")?uri.substring(0,uri.lastIndexOf("?")):uri));
    }


    @Override
    public void doPost(HeroRequest request, HeroResponse response) throws Exception {
        doGet(request, response);
    }
}
