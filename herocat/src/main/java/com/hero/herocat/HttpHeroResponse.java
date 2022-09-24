package com.hero.herocat;

import com.hero.servlet.HeroResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.internal.StringUtil;

/**
 * HeroCat中对Servlet规范的默认实现
 */
public class HttpHeroResponse implements HeroResponse {

    private HttpRequest request;
    private ChannelHandlerContext context;

    public HttpHeroResponse(HttpRequest request, ChannelHandlerContext context) {
        this.request = request;
        this.context = context;
    }

    @Override
    public void write(String content) throws Exception {

        // 处理content为空的情况
        if (StringUtil.isNullOrEmpty(content)) {
            return;
        }

        // 创建响应对象
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                // 根据响应体内容大小为response对象分配存储空间
                Unpooled.wrappedBuffer(content.getBytes("UTF-8")));

        // 获取响应头
        HttpHeaders headers = response.headers();
        // 设置响应体类型
        headers.set(HttpHeaderNames.CONTENT_TYPE, "text/json");
        // 设置响应体长度
        headers.set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        // 设置缓存过期时间
        headers.set(HttpHeaderNames.EXPIRES, 0);
        // 若HTTP请求是长连接，则响应也使用长连接
        if (HttpUtil.isKeepAlive(request)) {
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        // 将响应写入到Channel
        context.writeAndFlush(response);
    }
}
