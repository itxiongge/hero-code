package com.hero.servlet;

/**
 * Servlet规范之响应规范
 */
public interface HeroResponse {
    // 将响应写入到Channel
    void write(String content) throws Exception;
}
