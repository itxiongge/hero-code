package com.hero.frame.core.entity;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private String driver;
    private String url;
    private String username;
    private String password;
    private Map<String, SqlSource> sqlSourceMap = new HashMap<>();

    public Map<String, SqlSource> getSqlSourceMap() {
        return sqlSourceMap;
    }

    public void setSqlSourceMap(Map<String, SqlSource> sqlSourceMap) {
        this.sqlSourceMap = sqlSourceMap;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
