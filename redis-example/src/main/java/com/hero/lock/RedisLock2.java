package com.hero.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


@Component
public class RedisLock2 implements MyLock {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁操作
     * @param key
     * @param value 可以使用uuid，将来用来验证是否是自己加的锁
     * @param timeout
     * @param timeUnit
     * @return
     */
    public boolean tryLock(String key, String value, long timeout, TimeUnit timeUnit) {
        return redisTemplate.opsForValue()
                .setIfAbsent(key, value, timeout, timeUnit);
    }

    /**
     * 解锁操作
     * @param key
     * @param value 可以使用uuid，验证是否是自己加的锁
     */
    public boolean unlock(String key, String value) {
        DefaultRedisScript<Long> unlockScript = new DefaultRedisScript();
        unlockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("unlock.lua")));
        unlockScript.setResultType(Long.class);
        Long result = redisTemplate.execute(unlockScript, Arrays.asList(key), value);
        return result == 1 ? true:false;
    }
}
