package org.jiang.tools.text.id;

import org.jiang.tools.exception.BadArgumentException;
import org.jiang.tools.lock.SpinLock;

/**
 * 雪花算法工厂类
 * <br> 生成长度为18的整数，在大多数情况下，先后生成的数会形成递增趋势
 * <br> 示例：SnowflakeFactory.create("1001").get()
 *
 * @author Bin
 * @since 1.1.6
 */
public class SnowflakeFactory {

    /**
     * 起始的时间戳
     */
    private final static long START_TIMESTAMP = 1609459200000L;

    /**
     * 每部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12;
    private final static long NODE_BIT = 10;
    private final static long TIMESTAMP_BIT = 41;

    /**
     * 每部分的最大值
     */
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    private final static long MAX_WORKER_ID = ~(-1L << NODE_BIT);

    /**
     * 每部分向左的位移
     */
    private final static long WORKER_LEFT = SEQUENCE_BIT;
    private final static long TIMESTAMP_LEFT = SEQUENCE_BIT + NODE_BIT;

    private final long nodeId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    private final SpinLock LOCK = new SpinLock();

    public SnowflakeFactory(long nodeId) {
        if (nodeId > MAX_WORKER_ID || nodeId < 0) {
            throw new BadArgumentException("Worker ID can't be greater than " + MAX_WORKER_ID + " or less than 0");
        }
        this.nodeId = nodeId;
    }

    /**
     * 创建工厂对象
     *
     * @param nodeId 节点ID
     * @return 工厂对象
     */
    public static SnowflakeFactory create(long nodeId) {
        return new SnowflakeFactory(nodeId);
    }

    /**
     * 获取下一个值
     * <br> 这是一个线程安全的方法，不允许时间回拨
     * <br> 每个节点可以每毫秒可以生成最多1024个值
     *
     * @return 长度为18的整数
     */
    public long get() {
        // 通过自旋锁来保证线程同步
        LOCK.lock();

        long timestamp = System.currentTimeMillis();

        // 防止时间回拨导致重复
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                // 通过自旋来暂停1毫秒
                timestamp = System.currentTimeMillis();
                while (timestamp <= lastTimestamp) {
                    timestamp = System.currentTimeMillis();
                }
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        long id = ((timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT)
                | (nodeId << WORKER_LEFT)
                | sequence;

        LOCK.unlock();
        return id;
    }

}
