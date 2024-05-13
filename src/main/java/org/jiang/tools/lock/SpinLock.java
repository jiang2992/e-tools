package org.jiang.tools.lock;


import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 * 非公平锁、可重入
 * 适用于执行时间较短或竞争不激烈的代码片段
 *
 * @author Bin
 * @since 1.0.0
 */
public class SpinLock implements Lock {

    private final AtomicReference<Thread> owner = new AtomicReference<>();
    private int count = 0;

    @Override
    public void lock() {
        Thread thread = Thread.currentThread();
        if (owner.get() == thread) {
            count++;
            return;
        }
        while (!owner.compareAndSet(null, thread)) {
            Thread.yield();
        }
    }

    @Override
    public void unlock() {
        Thread thread = Thread.currentThread();
        if (owner.get() != thread) {
            return;
        }
        if (count > 0) {
            count--;
            return;
        }
        owner.set(null);
    }

}
