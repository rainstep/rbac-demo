package com.example.rbacdemo.common.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static <E> List<E> sum(List<? extends E> list1, List<? extends E> list2) {

        return org.apache.commons.collections4.ListUtils.sum(list1, list2);
    }

    public static <E> List<E> union(List<? extends E> list1, List<? extends E> list2) {
        return org.apache.commons.collections4.ListUtils.union(list1, list2);
    }

    public static <E> List<E> subtract(List<E> list1, List<? extends E> list2) {
        return org.apache.commons.collections4.ListUtils.subtract(list1, list2);
    }

    public static <E> List<E> intersection(List<? extends E> list1, List<? extends E> list2) {
        return org.apache.commons.collections4.ListUtils.intersection(list1, list2);
    }

    public static <E> boolean isEmpty(List<? extends E> list) {
        return list == null || list.size() == 0;
    }

    public static <E> boolean isNotEmpty(List<? extends E> list) {
        return list != null && list.size() > 0;
    }

    public static <T> List<T> arrayToList(T[] arr) {
        if (arr == null) return null;
        List<T> list = new ArrayList<>(arr.length);
        for (T t : arr) {
            list.add(t);
        }
        return list;
    }
}
