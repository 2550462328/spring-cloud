package cn.ictt.zhanghui.eureka_provider.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 令牌算法限流的注解类
 * @author ZhangHui
 * @date 2019/7/9
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LxRateLimit {
    /**
     * @author ZhangHui
     * @date 2019/7/5
     * @return java.lang.String
     */
    String value() default "";

    /**
     * 每秒向桶中放入令牌的数量 qps 即每秒可执行次数  默认最大即不做限流
     * 
     * @author ZhangHui
     * @date 2019/7/5
     * @return double
     */
    long perSecond() default Long.MAX_VALUE;

    /**
     * 获取令牌的等待时间 默认0
     * @author ZhangHui
     * @date 2019/7/5
     * @return int
     */
    int timeOut() default 0;

    /**
     * 超时时间单位
     * @author ZhangHui
     * @date 2019/7/5
     * @return java.util.concurrent.TimeUnit
     */
    TimeUnit timeOutUnit() default TimeUnit.MILLISECONDS;
}
