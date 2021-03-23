package com.weiqiang.easycode;

import com.weiqiang.easycode.iterator.PageList;
import com.weiqiang.easycode.iterator.PageListSupport;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author weiqiang8
 * @date 2021-03-23
 */
public class TestPageList {
    /**
     * 模拟待分组的大量数据
     */
    private static final String[] str = {"a1", "b1", "c1", "d1", "e1", "f1", "g1"};

    private static final Object object = new Object();

    public static void main(String[] args) {

        // 按照defaultPageSize做为分组
        PageList.of(PageListSupport.of(getTotal(), getBiFunction()), 3).forEach(System.out::println);
    }

    /**
     * 获取总条数函数
     */
    public static Supplier<Long> getTotal() {
        return () -> (long) str.length;
    }

    /**
     * 获取一组数据的函数
     */
    public static BiFunction<Integer, Integer, List<String>> getBiFunction() {
        return (pre, next) -> {
            System.out.println(pre + "::" + next);
            return Arrays.asList(str[new Random().nextInt(str.length - 1)], str[new Random().nextInt(str.length - 1)]);
        };
    }

    @Test
    public void test(){
        String str = null;

        if(null == str){
            System.out.println("asd");
        }



    }
}
