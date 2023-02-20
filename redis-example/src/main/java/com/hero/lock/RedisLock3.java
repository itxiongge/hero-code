package com.hero.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class RedisLock3 {

    @Autowired
    private RedisTemplate redisTemplate;
    private DefaultRedisScript<Long> lockScript;
    private DefaultRedisScript<Long> unlockScript;

    public RedisLock3() {
        // 加载加锁的脚本
        lockScript = new DefaultRedisScript<>();
        this.lockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("reentrant-lock.lua")));
        this.lockScript.setResultType(Long.class);
        // 加载释放锁的脚本
        unlockScript = new DefaultRedisScript<>();
        this.unlockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("reentrant-unlock.lua")));
        this.unlockScript.setResultType(Long.class);
    }

    /**
     * 获取锁
     */
    public boolean tryLock(String lockName, int releaseTime, String key) {

        // 执行脚本
        Long result = (Long) redisTemplate.execute(
                lockScript,
                Collections.singletonList(lockName),
                key + Thread.currentThread().getId(),
                releaseTime);

        if (result != null && result.intValue() >= 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 解锁
     * @param lockName
     * @param key
     */
    public boolean unlock(String lockName, String key) {
        Long result = (Long)redisTemplate.execute(
                unlockScript,
                Collections.singletonList(lockName),
                key + Thread.currentThread().getId());

        if (result == null) {
            return true;
        }
        if (result.intValue() >= 1) {
            return false;
        }
        return true;
    }
}

