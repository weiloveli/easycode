package com.weiqiang.easycode.iterator;

import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * 分段集合
 *
 * @author weiqiang8
 * @date 2021-03-12
 */
public class PageList<T extends List<?>> extends BasePageList<T> implements Iterable<T> {

    /**
     * 分页数据
     */
    protected T pageList;

    /**
     * 总条数
     */
    protected Long total;

    /**
     * 总页数
     */
    protected Long pageSize;

    /**
     * 当前页数
     */
    protected Integer currentPageNumber = 0;

    /**
     * 默认一组数据的条数
     */
    protected Integer defaultPageSize = 1000;

    /**
     * 构造器
     *
     * @param totalSupplier    获取总条数函数
     * @param pageListFunction 获取当前页数据函数
     */
    public PageList(Supplier<Long> totalSupplier,
                    BiFunction<Integer, Integer, T> pageListFunction) {
        super(totalSupplier, pageListFunction);
        initialize();
    }

    /**
     * 构造器
     *
     * @param totalSupplier    获取总条数函数
     * @param pageListFunction 获取当前页数据函数
     * @param defaultPageSize  默认每页条数
     */
    public PageList(Supplier<Long> totalSupplier,
                    BiFunction<Integer, Integer, T> pageListFunction,
                    Integer defaultPageSize) {
        super(totalSupplier, pageListFunction);
        this.defaultPageSize = defaultPageSize;
        initialize();
    }

    /**
     * 初始化方法
     * <ul>
     *     <li>初始化总条数</li>
     *     <li>初始化总页数</li>
     * </ul>
     */
    private void initialize() {
        setTotal();
        setPageSize();
    }

    @Override
    public void setTotal() {
        Assert.isTrue((this.total = getTotalSupplier().get()) >= 0, "The total number must be greater than 0");
    }

    @Override
    public void setPageSize() {
        this.pageSize = Double.valueOf(Math.ceil(total.doubleValue() / defaultPageSize.doubleValue())).longValue();
    }

    /**
     * 获取PageList实例
     *
     * @param totalSupplier    获取总条数函数
     * @param pageListFunction 获取当前页数据函数
     */
    @SuppressWarnings("unused")
    public static <S extends List<?>> PageList<S> of(Supplier<Long> totalSupplier,
                                                     BiFunction<Integer, Integer, S> pageListFunction) {
        return new PageList<>(totalSupplier, pageListFunction);
    }

    /**
     * 获取PageList实例
     *
     * @param totalSupplier    获取总条数函数
     * @param pageListFunction 获取当前页数据函数
     * @param defaultPageSize  默认每页条数
     */
    @SuppressWarnings("unused")
    public static <S extends List<?>> PageList<S> of(Supplier<Long> totalSupplier,
                                                     BiFunction<Integer, Integer, S> pageListFunction,
                                                     Integer defaultPageSize) {
        return new PageList<>(totalSupplier, pageListFunction, defaultPageSize);
    }

    @SuppressWarnings("unchecked")
    private class Itr<K> implements Iterator<K> {

        @Override
        public boolean hasNext() {
            return pageSize >= currentPageNumber + 1;
        }

        @Override
        public K next() {
            pageList = getPageListFunction().apply(currentPageNumber, defaultPageSize);
            currentPageNumber++;
            return (K) pageList;
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new Itr<>();
    }

}
