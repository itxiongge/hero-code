package com.hero.lock;

import com.hero.DataApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.scripting.support.StaticScriptSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataApplication.class)
public class RedisLockDemo {
    @Autowired
    private RedisLock1 redisLock1;
    @Autowired
    private RedisLock2 redisLock2;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testLock1() throws IOException {
        for (int i = 0; i < 100; i++) {
            int a = i;
            new Thread(()->{
                String tName = "t" + a;
                //加锁操作
                String uuid = UUID.randomUUID().toString();
                try{
                    while(true) {//自旋
                        if (redisLock1.tryLock("distributedlock", uuid, 100, TimeUnit.SECONDS)) {
                            System.out.println(tName + "加锁成功");
                            System.out.println(tName + " working...");
                            break;
                        } else {
                            //加锁不成功
                            System.out.println(tName + "加锁失败");
                            Thread.sleep(10);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //解铃还须系铃人
                    boolean result = redisLock1.unlock("distributedlock", uuid);
                    if (result) {
                        System.out.println(tName + "解锁成功");
                    } else {
                        System.out.println(tName + "解锁失败");
                    }
                }
            }, "t" + a).start();
        }
        System.in.read();
    }
    @Test
    public void testLock2() throws IOException {
        for (int i = 0; i < 100; i++) {
            int a = i;
            new Thread(()->{
                String tName = "t" + a;
                //加锁操作
                String uuid = UUID.randomUUID().toString();
                try{
                    while(true) {
                        if (redisLock2.tryLock("distributedlock", uuid, 100, TimeUnit.SECONDS)) {
                            System.out.println(tName + "加锁成功");
                            System.out.println(tName + " working...");
                            break;
                        } else {
                            //加锁不成功
                            System.out.println(tName + "加锁失败");
                            Thread.sleep(10);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    boolean result = redisLock2.unlock("distributedlock", uuid);
                    if (result) {
                        System.out.println(tName + "解锁成功");
                    } else {
                        System.out.println(tName + "解锁失败");
                    }
                }
            }, "t" + a).start();
        }
        System.in.read();
    }



    @Test
    public void testLuaScript() throws IOException {
//        DefaultRedisScript<String> unlockScript = new DefaultRedisScript();
//        unlockScript.setScriptSource(new StaticScriptSource("return redis.call('get',KEYS[1])"));
//        unlockScript.setResultType(String.class);
//        String result = redisTemplate.execute(unlockScript, Arrays.asList("hero"), "123");
//        System.out.println(result);

        DefaultRedisScript<Long> unlockScript = new DefaultRedisScript();
        unlockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("unlock.lua")));
        unlockScript.setResultType(Long.class);
        System.out.println(unlockScript.getScriptAsString());
        Long result = redisTemplate.execute(unlockScript, Arrays.asList("hero"), "123");
        System.out.println(result);
        //System.in.read();
    }
}
