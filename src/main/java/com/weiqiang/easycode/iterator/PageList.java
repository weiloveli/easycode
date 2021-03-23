package com.weiqiang.easycode.iterator;

import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.List;

/**
 * 分段集合
 *
 * @param <T> List集合类型
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
    protected Integer currentPageNumber = 1;

    /**
     * 默认一组数据的条数
     */
    protected Integer defaultPageSize = 1000;

    /**
     * 构造器
     *
     * @param pageListSupport 获取总条数函数
     */
    public PageList(PageListSupport<T> pageListSupport) {
        super(pageListSupport);
        // 初始化总条数和总页数
        initialize();
    }

    /**
     * 构造器
     *
     * @param pageListSupport {@link PageListSupport}
     * @param defaultPageSize 默认每页条数
     */
    public PageList(PageListSupport<T> pageListSupport, Integer defaultPageSize) {
        super(pageListSupport);
        this.defaultPageSize = defaultPageSize;
        // 初始化总条数和总页数
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

    /**
     * 设置总条数
     */
    public void setTotal() {
        Assert.isTrue((this.total = pageListSupport.getTotalSupplier().get()) >= 0, "The total number must be greater than 0");
    }

    /**
     * 设置总页数
     */
    public void setPageSize() {
        this.pageSize = Double.valueOf(Math.ceil(total.doubleValue() / defaultPageSize.doubleValue())).longValue();
    }

    /**
     * 获取PageList实例
     *
     * @param pageListSupport {@link PageListSupport}
     * @return PageList实例
     */
    @SuppressWarnings("unused")
    public static <T extends List<?>> PageList<T> of(PageListSupport<T> pageListSupport) {
        return new PageList<>(pageListSupport);
    }

    /**
     * 获取PageList实例
     *
     * @param defaultPageSize 默认每页条数
     * @param pageListSupport {@link PageListSupport}
     * @return PageList实例
     */
    @SuppressWarnings("unused")
    public static <T extends List<?>> PageList<T> of(PageListSupport<T> pageListSupport,
                                                     Integer defaultPageSize) {
        return new PageList<>(pageListSupport, defaultPageSize);
    }

    @SuppressWarnings("unchecked")
    private class Itr<K> implements Iterator<K> {

        @Override
        public boolean hasNext() {
            return pageSize >= currentPageNumber;
        }

        @Override
        public K next() {
            pageList = pageListSupport.getPageListBiFunc().apply(currentPageNumber, defaultPageSize);
            currentPageNumber++;
            return (K) pageList;
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new Itr<>();
    }

}
