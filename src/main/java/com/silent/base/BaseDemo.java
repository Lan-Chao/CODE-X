package com.silent.base;

import org.openjdk.jol.info.ClassLayout;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 基本类使用
 * @author zhao
 */
public class BaseDemo {

    public static void main(String[] args) {
        Object obj = new Object();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }


    /**
     * 返回由指定数组支持的固定大小的列表。此方法作为基于数组和基于集合的API之间的桥梁，
     * 与Collection.toArray()结合使用。返回的List是可序列化并实现RandomAccess接口
     * java.util.Arrays 的一个内部类,这个内部类并没有实现集合的修改方法或者说并没有重写这些方法
     *
     * 【注意】
     * 1.使用集合的修改方法:add()、remove()、clear()会抛出异常
     * 2.传递的数组必须是对象数组，而不是基本类型
     * */
    public static void test1(){
        String[] arr = {"a", "b", "c"};
        List<String> list = Arrays.asList(arr);

        /** 推荐使用 */
        List list1 = new ArrayList<>(Arrays.asList(arr));

    }



}
