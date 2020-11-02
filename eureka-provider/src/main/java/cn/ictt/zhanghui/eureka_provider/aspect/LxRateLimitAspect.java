package cn.ictt.zhanghui.eureka_provider.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.stereotype.Component;

import cn.ictt.zhanghui.eureka_provider.annotation.LxRateLimit;
import cn.ictt.zhanghui.eureka_provider.pojo.User;

import com.google.common.util.concurrent.RateLimiter;

import javax.validation.constraints.Min;

/**
 * @author: ZhangHui
 * @description: 使用aop + 自定义注释实现限流操作 系统会以一个恒定的速度往桶里放入令牌，而如果请求需要被处理，则需要先从桶里获取一个令牌 ,当桶里没有令牌可取时，则拒绝服务。 当桶满时，新添加的令牌被丢弃或拒绝
 * @date: 2019/7/8
 */
@Aspect
@Component
public class LxRateLimitAspect {
    private final static Logger logger = LoggerFactory.getLogger(LxRateLimitAspect.class);

    @Pointcut("execution(* cn.ictt.zhanghui.eureka_provider.service..*(..))")
    public void myMethod() {}

    private static int a = 1;
    /**
     * 存放每个线程的rateLimiter
     */
    private Map<String, RateLimiter> rateMaps = new ConcurrentHashMap<>();

    /**
     * 创建一个不限流的RateLimiter
     */
    // private RateLimiter rateLimiter = RateLimiter.create(Double.MAX_VALUE);

    // 出错时返回的userList
    private static final List<User> DEFAULT_USERLIST = null;

    /**
     * 带有@LxRateLimit 注释的切面
     */
    @Pointcut("@annotation(cn.ictt.zhanghui.eureka_provider.annotation.LxRateLimit)")
    public void lxLimitMethod() {}

    @Around("lxLimitMethod()")
    public Object aroundNotice(ProceedingJoinPoint joinPoint) throws Throwable {
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

        if (method.isAnnotationPresent(LxRateLimit.class)) {
            // 获取目标方法的LxRateLimit注解值
            LxRateLimit lxRateLimit = method.getAnnotation(LxRateLimit.class);

            // 从rateMaps中取RateLimiter， 如果不存在则新增
            String key = targetName + "." + methodName + "." + Arrays.toString(argsType);
            RateLimiter rateLimiter = rateMaps.get(key);
            if (rateLimiter == null) {
                // 获取限定的流量
                // 为了防止并发
                rateMaps.putIfAbsent(key,
                    RateLimiter.create(lxRateLimit.perSecond(), lxRateLimit.timeOut(), lxRateLimit.timeOutUnit()));
                rateLimiter = rateMaps.get(key);
            }

            if (!rateLimiter.tryAcquire()) {
                logger.error("服务器繁忙，请稍后重试！");
                return DEFAULT_USERLIST;
            }
        }
        return joinPoint.proceed();
    }

    // @Around(value = "myMethod()")
    // public Object aroundMethod(ProceedingJoinPoint joinPoint){
    // Object object = null;
    // System.out.println("joinPoint.getSignature().getName() = " + joinPoint.getSignature().getName());
    // System.out.println("joinPoint.getArgs() = " + joinPoint.getArgs().toString());
    // System.out.println("joinPoint.getSourceLocation() = " + joinPoint.getSourceLocation());
    // System.out.println("joinPoint.getTarget().getClass() = " + joinPoint.getTarget().getClass());
    // try {
    // object = joinPoint.proceed();
    // } catch (Throwable throwable) {
    // throwable.printStackTrace();
    // }
    // System.out.println("=======================================================");
    // System.out.println("joinPoint.getStaticPart().getSourceLocation() = " +
    // joinPoint.getStaticPart().getSourceLocation());
    // System.out.println("joinPoint.getStaticPart().getSignature().getName() = " +
    // joinPoint.getStaticPart().getSignature().getName());
    // System.out.println("joinPoint.getStaticPart().getKind() = " + joinPoint.getStaticPart().getKind());
    // System.out.println("joinPoint.getStaticPart().getId() = " + joinPoint.getStaticPart().getId());
    // return object;
    // }

}
