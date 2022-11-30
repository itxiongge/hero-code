package com.hero;

import com.hero.pojo.Dog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ConditionalAnnApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ConditionalAnnApplication.class, args);
        Dog dog = context.getBean(Dog.class);
        System.out.println(dog);
    }

}
