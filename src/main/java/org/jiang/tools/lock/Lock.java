package org.jiang.tools.lock;

/**
 * 锁
 *
 * @author Bin
 * @since 1.0.0
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
