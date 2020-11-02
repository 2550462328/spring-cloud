package cn.ictt.zhanghui.eureka_provider.annotation;

import java.lang.annotation.*;
/**
 * 计数器算法限流的注释类
 * @author ZhangHui
 * @date 2019/7/9
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShLimit {
    /**
     * 功能描述 1秒内允许访问的个数/Semaphore的信号量
     */
     int value() default 100;
}
