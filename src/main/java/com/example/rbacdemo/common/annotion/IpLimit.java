package com.example.rbacdemo.common.annotion;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IpLimit {
    /** 最小请求间隔 */
    int minInterval() default -1;
    /** 最大请求次数 */
    int maxCount() default -1;
    /** 超出最大请求次数后多久后允许重新请求 */
    int retrySeconds() default 30 * 60;
}
