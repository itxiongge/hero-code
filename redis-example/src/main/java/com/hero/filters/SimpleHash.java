package com.hero.filters;

/**
 * 简单的哈希函数实现
 */
public class SimpleHash {
    private int cap;
    private int seed;

    public SimpleHash(int cap, int seed) {
        this.cap = cap;
        this.seed = seed;
    }

    public int hash(String value) {
        int result = 0;
        int len = value.length();
        for (int i = 0; i < len; i++) {
            result = seed * result + value.charAt(i);//哈希函数的计算方式
        }
        return (cap - 1) & result;
    }
}
