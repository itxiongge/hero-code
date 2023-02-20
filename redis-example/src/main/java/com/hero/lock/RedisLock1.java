package com.hero.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 特点：
 *  互斥性
 *  非公平锁
 *  不可重入
 */
@Component
public class RedisLock1 implements MyLock {
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
        //setIfAbsent 相当于 SET key value EX 秒数 NX
        //   EX  seconds：为键设置过期时间，单位为秒。
        //   NX：只有当键不存在时，才可设置成功，用于添加。
        return redisTemplate.opsForValue()
                .setIfAbsent(key, value, timeout, timeUnit);
    }

    /**
     * 解锁操作
     * @param key disturbutedlock
     * @param value 可以使用uuid，验证是否是自己加的锁
     */
    public boolean unlock(String key, String value) {
        //校验是否是自己的锁【解锁的判断过程不是原子性的】
        if (value.equals(redisTemplate.opsForValue().get(key))) {//get,跨网络
            //如果是的话解锁
            return redisTemplate.delete(key);//del
        }
        return false;
    }
}
