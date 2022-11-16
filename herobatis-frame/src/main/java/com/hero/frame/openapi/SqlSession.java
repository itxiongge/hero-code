package com.hero.frame.openapi;

import java.util.List;

/**
 * 对外暴露SqlSession接口
 * 接口中定义常见的JDBC操作：增删改查
 */
public interface SqlSession {
    /**
     * 查询所有用户
     * T 代表泛型类型，T(type缩写)
     */
    <T> List<T> selectList(String sql) throws Exception;
}
