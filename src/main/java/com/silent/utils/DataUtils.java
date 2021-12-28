package com.silent.utils;

import java.util.Collections;
import java.util.List;

/**
 * @author zhao
 * @date 2021/12/28 13:50
 */
public class DataUtils {

    /**
     * 对数组进行分页
     * @param pageNo 从1开始
     * @author zhao
     * @date 2021/3/29
     */
    public static <T> List<T> getData(List<T> data, Integer pageNo, Integer pageSize) {
        int fromIndex = (pageNo - 1) * pageSize;
        if (fromIndex >= data.size() || fromIndex < 0) {
            return Collections.emptyList();
        }
        int toIndex = pageNo * pageSize;
        if (toIndex >= data.size()) {
            toIndex = data.size();
        }
        return data.subList(fromIndex, toIndex);
    }

}
