package com.weiqiang.easycode.iterator;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * 分页数据抽象类
 *
 * @author weiqiang8
 * @date 2021-03-12
 */
public abstract class BasePageList<T extends List<?>> extends BasePageListAccess<T> {

    /**
     * 构造器
     *
     * @param totalSupplier    获取总条数函数
     * @param pageListFunction 分页数据流程
     */
    public BasePageList(Supplier<Long> totalSupplier, BiFunction<Long, Integer, T> pageListFunction) {
        super(totalSupplier, pageListFunction);
    }

    /**
     * 设置总条数抽象方法
     */
    public abstract void setTotal();

    /**
     * 设置总页数抽象方法
     */
    public abstract void setPageSize();

}
