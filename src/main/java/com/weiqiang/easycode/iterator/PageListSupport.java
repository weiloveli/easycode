package com.weiqiang.easycode.iterator;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * 分页支持
 *
 * @param <T> List集合类型
 * @author weiqiang8
 * @date 2021-03-23
 */
public class PageListSupport<T extends List<?>> {

    /**
     * 总条数函数
     */
    private final Supplier<Long> totalSupplier;

    /**
     * 当前页数据函数
     */
    private BiFunction<Integer, Integer, T> pageListBiFunc;

    /**
     * 创建PageListSupport实例
     *
     * @param totalSupplier  总条数获取函数
     * @param pageListBiFunc 每页数据函数
     */
    public static <T extends List<?>> PageListSupport<T> of(Supplier<Long> totalSupplier, BiFunction<Integer, Integer, T> pageListBiFunc) {
        return new PageListSupport<>(totalSupplier, pageListBiFunc);
    }

    /**
     * 构造器
     *
     * @param totalSupplier  总条数获取函数
     * @param pageListBiFunc 每页数据函数
     */
    public PageListSupport(Supplier<Long> totalSupplier, BiFunction<Integer, Integer, T> pageListBiFunc) {
        this.totalSupplier = totalSupplier;
        this.pageListBiFunc = pageListBiFunc;
    }

    /**
     * 构造器
     *
     * @param totalSupplier 总条数获取函数
     */
    @SuppressWarnings("unused")
    public PageListSupport(Supplier<Long> totalSupplier) {
        this.totalSupplier = totalSupplier;
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
     * 获取分页数函数
     *
     * @return 分页数函数
     */
    public BiFunction<Integer, Integer, T> getPageListBiFunc() {
        return pageListBiFunc;
    }

}
