package com.weiqiang.easycode.iterator;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * 支持并发执行的分页列表
 *
 * @param <T> List集合类型
 * @author weiqiang8
 * @date 2021-03-15
 */
public class ConcurrentPageList<T extends List<?>> extends PageList<T> {

    /**
     * 线程池
     */
    private final Executor executor;

    /**
     * 构造器
     *
     * @param pageListSupport 分页数据支持
     * @param executor        线程池
     */
    public ConcurrentPageList(PageListSupport<T> pageListSupport, Executor executor) {
        super(pageListSupport);
        this.executor = executor;
    }

    /**
     * 构造器
     *
     * @param pageListSupport 分页数据支持
     * @param defaultPageSize 每页条数
     * @param executor        线程池
     */
    public ConcurrentPageList(PageListSupport<T> pageListSupport, Integer defaultPageSize, Executor executor) {
        super(pageListSupport, defaultPageSize);
        this.executor = executor;
    }

    /**
     * 获取并发分页实例
     *
     * @param pageListSupport 分页数据支持
     * @param defaultPageSize 每页条数
     * @param executor        线程池
     * @return 并发分页实例
     */
    public static <T extends List<?>> ConcurrentPageList<T> of(PageListSupport<T> pageListSupport, Integer defaultPageSize, Executor executor) {
        return new ConcurrentPageList<>(pageListSupport, defaultPageSize, executor);
    }

    /**
     * 获取PageList实例
     *
     * @param pageListSupport 分页数据支持
     * @param executor        线程池
     * @return 并发分页实例
     */
    @SuppressWarnings("unused")
    public static <T extends List<?>> ConcurrentPageList<T> of(PageListSupport<T> pageListSupport, Executor executor) {
        return new ConcurrentPageList<>(pageListSupport, executor);
    }

    /**
     * 并发执行指定的消费者
     *
     * @param action 消费者
     */
    @Override
    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) {
            executor.execute(() -> action.accept(t));
        }
    }

}
