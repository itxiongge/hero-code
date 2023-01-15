package com.hero.distributedlock;

public interface Lock {

    boolean lock() throws Exception;

    boolean unlock();
}
