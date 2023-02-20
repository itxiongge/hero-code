package com.hero.lock;

import java.util.concurrent.TimeUnit;


public interface MyLock {

    boolean tryLock(String key, String value, long timeout, TimeUnit timeUnit);

    boolean unlock(String key, String value);
}
