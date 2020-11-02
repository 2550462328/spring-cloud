package cn.ictt.zhanghui.eureka_provider.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyAnnotation {
    public String name() default "zhanghui";
    public String id();
}
