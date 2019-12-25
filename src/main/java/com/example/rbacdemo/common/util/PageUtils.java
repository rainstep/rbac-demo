package com.example.rbacdemo.common.util;

public class PageUtils {
    public static int getOffset(int pageNum, int pageSize) {
        return (pageNum - 1) * pageSize;
    }
}
