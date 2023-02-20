package com.hero.boot;

import com.hero.DataApplication;
import com.hero.service.DataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataApplication.class)
public class DataServiceTest {
    @Autowired
    private DataService dataService;

    @Test
    public void testRedis() {
        String hello = dataService.getKey("hello");
        System.out.println(hello);
    }
}
