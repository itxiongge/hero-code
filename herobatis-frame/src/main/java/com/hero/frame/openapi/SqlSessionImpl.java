package com.hero.frame.openapi;

import com.hero.frame.core.Executor;
import com.hero.frame.core.entity.Configuration;

import java.util.List;


public class SqlSessionImpl implements SqlSession {

    //每次Sql会话连接，必须要有数据库配置信息
    private Configuration configuration;

    public SqlSessionImpl(Configuration configuration) {
        this.configuration = configuration;
    }

    public <T> List<T> selectList(String statement) throws Exception{
        Executor executor = new Executor(configuration);

        return (List<T>) executor.executeQuery(statement);
    }
}
