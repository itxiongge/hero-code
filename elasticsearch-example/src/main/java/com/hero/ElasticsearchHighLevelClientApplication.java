package com.hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration;

@SpringBootApplication(exclude = RestClientAutoConfiguration.class)
public class ElasticsearchHighLevelClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchHighLevelClientApplication.class, args);
    }

}
