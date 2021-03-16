package com.weiqiang.easycode.iterator;

import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * 分页数据访问
 *
 * @author weiqiang8
 * @date 2021-03-12
 */
public abstract class BasePageListAccess<T> {

    /**
     * 总条数函数
     */
    private final Supplier<Long> totalSupplier;

    /**
     * 当前页数据函数
     */
    private final BiFunction<Long, Integer, T> pageListFunction;

    /**
     * 有参构造
     *
     * @param totalSupplier    总条数函数
     * @param pageListFunction 当前页数据函数
     */
    public BasePageListAccess(Supplier<Long> totalSupplier, BiFunction<Long, Integer, T> pageListFunction) {
        this.totalSupplier = totalSupplier;
        this.pageListFunction = pageListFunction;
    }

    /**
     * 获取总条数函数
     *
     * @return 总条数函数
     */
    public Supplier<Long> getTotalSupplier() {
        return totalSupplier;
    }

    /**
     * 获取分页数据函数
     *
     * @return 分页数据函数
     */
    public BiFunction<Long, Integer, T> getPageListFunction() {
        return pageListFunction;
    }

}

