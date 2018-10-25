package com.zjg.dev.util;


/**
 * 主键生成器。该类使用单例模式。
 */
public class PKGenerator {
    /**
     * 静态不变单例变量。
     */
    private static final PKGenerator instance = new PKGenerator();
    /**
     * 存储主机IP左移48位后的值。
     */
    private long key;

    /**
     * 获得当前主机IP的后3位（如192.168.0.112中的112），左移30位后存入变量lastTransIP中 。
     * 可以自定义配置（分布式主机）
     *
     * @roseuid 3EF6A8F0023D
     */
    private PKGenerator() {
        makeKey(IPUitls.localSeed());
    }

    /**
     * @return com.adtech.util.PKGenerator
     * @roseuid 3EF6A8F00251
     */
    public static PKGenerator getInstance() {
        return instance;
    }

    public static long generateKey() {
        return instance.getKey();
    }

    /**
     * 生成key
     *
     * @param seed
     */
    public synchronized void makeKey(int seed) {
        long lastTransIP = ((long) seed) << 30;
        long longTime = System.currentTimeMillis();
        key = longTime | lastTransIP;
    }

    /**
     * 返回唯一的键。获得当前时间的毫秒数位与lastTransIP的值。
     *
     * @return long
     * @roseuid 3EF6A8F00265
     */
    public synchronized long getKey() {
        return ++key;
    }

}
