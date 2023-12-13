package org.jiang.tools.lock;

/**
 * 锁
 *
 * @author Bin
 * @date 2021/3/23 09:30
 */
public interface Lock {

    /**
     * 获取锁
     */
    void lock();

    /**
     * 释放锁
     */
    void unlock();

}
