package com.weiqiang.easycode.iterator;

import java.util.List;

/**
 * 分页数据抽象类
 *
 * @param <T> List集合类型
 * @author weiqiang8
 * @date 2021-03-12
 */
public abstract class BasePageList<T extends List<?>> {

    /**
     * 分页集合的支持类
     */
    protected PageListSupport<T> pageListSupport;

    /**
     * 构造器
     *
     * @param pageListSupport 获取总条数函数
     */
    public BasePageList(PageListSupport<T> pageListSupport) {
        this.pageListSupport = pageListSupport;
    }

}
