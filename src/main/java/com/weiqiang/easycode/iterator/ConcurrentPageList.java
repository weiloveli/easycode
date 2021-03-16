package com.weiqiang.easycode.iterator;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 支持并发执行的分页列表
 *
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
     * @param totalSupplier    总条数获取函数
     * @param pageListFunction 分页数据获取函数
     * @param executor         线程池
     */
    public ConcurrentPageList(Supplier<Long> totalSupplier,
                              BiFunction<Long, Integer, T> pageListFunction,
                              Executor executor) {
        super(totalSupplier, pageListFunction);
        this.executor = executor;
    }

    /**
     * 构造器
     *
     * @param totalSupplier    总条数获取函数
     * @param pageListFunction 分页数据获取函数
     * @param defaultPageSize  每页条数
     * @param executor         线程池
     */
    public ConcurrentPageList(Supplier<Long> totalSupplier,
                              BiFunction<Long, Integer, T> pageListFunction,
                              Integer defaultPageSize,
                              Executor executor) {
        super(totalSupplier, pageListFunction, defaultPageSize);
        this.executor = executor;
    }

    /**
     * 获取并发分页实例
     *
     * @param totalSupplier    获取总条数函数
     * @param pageListFunction 获取当前页数据函数
     * @param defaultPageSize  每页条数
     * @param executor         线程池
     * @return 并发分页实例
     */
    public static <S extends List<?>> ConcurrentPageList<S> of(Supplier<Long> totalSupplier,
                                                               BiFunction<Long, Integer, S> pageListFunction,
                                                               Integer defaultPageSize,
                                                               Executor executor) {
        return new ConcurrentPageList<>(totalSupplier, pageListFunction, defaultPageSize, executor);
    }

    /**
     * 获取PageList实例
     *
     * @param totalSupplier    获取总条数函数
     * @param pageListFunction 获取当前页数据函数
     * @param executor         线程池
     * @return 并发分页实例
     */
    @SuppressWarnings("unused")
    public static <S extends List<?>> ConcurrentPageList<S> of(Supplier<Long> totalSupplier,
                                                               BiFunction<Long, Integer, S> pageListFunction,
                                                               Executor executor) {
        return new ConcurrentPageList<>(totalSupplier, pageListFunction, executor);
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
