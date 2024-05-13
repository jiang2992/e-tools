package org.jiang.tools.lock;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自旋公平锁
 * 公平锁、可重入
 * 适用于执行时间较短或竞争不激烈的代码片段
 *
 * @author Bin
 * @since 1.0.0
 */
public class SpinFairLock implements Lock {

    /**
     * 当前持有锁的号码
     */
    private final AtomicInteger currentNum = new AtomicInteger(0);

    /**
     * 排队排队的最大号码
     */
    private final AtomicInteger ticketNum = new AtomicInteger(0);

    /**
     * 记录锁重入次数
     */
    private int count = 0;

    /**
     * 各线程存放自己所申请的排队号码
     */
    private static final ThreadLocal<Integer> threadLocalNum = new ThreadLocal<>();

    @Override
    public void lock() {
        Integer num = threadLocalNum.get();
        if (num != null && num == currentNum.get()) {
            count++;
            return;
        }

        // 申请号码
        num = getTicketNum();
        threadLocalNum.set(num);
        // 自旋等待
        while (num != this.currentNum.get()) {
            Thread.yield();
        }
    }

    @Override
    public void unlock() {
        Integer num = threadLocalNum.get();
        if (num != currentNum.get()) {
            return;
        }
        if (count > 0) {
            count--;
            return;
        }
        threadLocalNum.remove();
        if (num == Integer.MAX_VALUE) {
            currentNum.set(0);
        } else {
            currentNum.set(num + 1);
        }
    }

    private int getTicketNum() {
        ticketNum.compareAndSet(Integer.MAX_VALUE, 0);
        return ticketNum.getAndIncrement();
    }

}
