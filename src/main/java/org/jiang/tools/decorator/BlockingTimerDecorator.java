package org.jiang.tools.decorator;

import lombok.Getter;

/**
 * 阻塞的定时器装饰器
 *
 * @author Bin
 * @since 1.1.9
 */
public class BlockingTimerDecorator<T> extends BaseDecorator<T> {

    private final ResultfulCodeSegment<RunStep<T>> codeSegment;
    private final Long intervalMs;
    private final Integer maxCount;

    @Getter
    public static class RunStep<T> {

        private final boolean terminated;

        private final T result;

        private RunStep(boolean terminated, T result) {
            this.terminated = terminated;
            this.result = result;
        }

        public static <T> RunStep<T> terminated() {
            return new RunStep<>(true, null);
        }

        public static <T> RunStep<T> terminated(T result) {
            return new RunStep<>(true, result);
        }

        public static <T> RunStep<T> continued() {
            return new RunStep<>(false, null);
        }

    }

    public BlockingTimerDecorator(ResultfulCodeSegment<RunStep<T>> codeSegment) {
        this.codeSegment = codeSegment;
        this.intervalMs = 0L;
        this.maxCount = 1;
    }

    public BlockingTimerDecorator(Long intervalMs, Integer maxCount, ResultfulCodeSegment<RunStep<T>> codeSegment) {
        this.codeSegment = codeSegment;
        this.intervalMs = intervalMs;
        this.maxCount = maxCount;
    }

    @Override
    public T run() {
        for (int i = 0; i < maxCount; i++) {
            RunStep<T> runStep = codeSegment.exec();
            if (runStep.isTerminated()) {
                return runStep.getResult();
            }
            if (intervalMs > 0) {
                try {
                    Thread.sleep(intervalMs);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public static <T> T run(Long intervalMs, Integer maxCount, ResultfulCodeSegment<RunStep<T>> codeSegment) {
        BlockingTimerDecorator<T> decorator = new BlockingTimerDecorator<>(intervalMs, maxCount, codeSegment);
        return decorator.run();
    }

}
