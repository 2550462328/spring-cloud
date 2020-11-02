package cn.ictt.zhanghui.eureka_provider.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.ReflectUtils;

import cn.ictt.zhanghui.eureka_provider.annotation.ShLimit;
import org.springframework.stereotype.Component;

/**
 * @ClassName ShLimitAspect
 * @Description: 这是计数器算法配置限流的切面逻辑
 *                主要用来限制总并发数，比如数据库连接池大小、线程池大小、程序访问并发数等都是使用计数器算法
 * @Author: ZhangHui
 * @Date: 2019/7/9
 * @Version：1.0
 */
@Aspect
@Component
public class ShLimitAspect {
    private final static Logger logger = LoggerFactory.getLogger(ShLimitAspect.class);

    /**
     * 存放每个线程的rateLimiter
     */
    private Map<String, Semaphore> shMaps = new ConcurrentHashMap<>();

    /**
     * 带有@LxRateLimit 注释的切面
     */
    @Pointcut("@annotation(cn.ictt.zhanghui.eureka_provider.annotation.ShLimit)")
    public void shLimitMethod() {}

    @Around("shLimitMethod()")
    public void aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("拦截到对{}方法的请求", joinPoint.getSignature().getName());
        // 返回目标对象
        Object target = joinPoint.getTarget();
        String targetName = target.getClass().getName();
        // 获取当前连接点签名
        String methodName = joinPoint.getSignature().getName();
        // 获取参数列表
        Object[] arguments = joinPoint.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        // 获取参数类型数组
        Class<?>[] argsType = ReflectUtils.getClasses(arguments);
        // 获取目标方法， 考虑方法的重载引起的混乱
        Method method = targetClass.getDeclaredMethod(methodName, argsType);

        if (method.isAnnotationPresent(ShLimit.class)) {
            // 获取目标方法的ShLimit注解值
            ShLimit shLimit = method.getAnnotation(ShLimit.class);

            // 从shMaps中取Semaphore， 如果不存在则新增
            String key = targetName + "." + methodName + "." + Arrays.toString(argsType);
            Semaphore semaphore = shMaps.get(key);
            if (semaphore == null) {
                // 获取限定的流量
                // 为了防止并发
                shMaps.putIfAbsent(key, new Semaphore(shLimit.value()));
                semaphore = shMaps.get(key);
            }
            try {
                // 消耗一个令牌
                semaphore.acquire();
                // 调用代理方法
                joinPoint.proceed();
            } finally {
                if(semaphore != null){
                    semaphore.release();
                }
            }
        }
        joinPoint.proceed();
    }
}
