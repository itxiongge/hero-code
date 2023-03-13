package com.hero.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 配置类：注入高级客户端对象进入Spring容器
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
@Component
public class ElasticSearchConfiguration {

    //@Value("${elasticsearch.host}")
    private String host;
    //@Value("${elasticsearch.port}")
    private Integer port;

    /**
     * 生命周期方法
     * 初始化：initMethod
     * 销毁：destroyMethod
     */
    @Bean(destroyMethod = "close")
    public RestHighLevelClient createClient(){
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(host, port));
        return new RestHighLevelClient(restClientBuilder);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
