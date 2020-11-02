package cn.ictt.zhanghui.eureka_provider.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: ZhangHui
 * @description: 这是自定义切面信息
 * @date: 2019/6/20
 */
@Aspect
@Component
public class MyAspect {
    @Pointcut("execution(* cn.ictt.zhanghui.eureka_provider_payservice.service..*(..))")
    public void myMethod(){

    }

    @Around(value = "myMethod()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint){
        Object object = null;
        System.out.println("joinPoint.getSignature().getName() = " + joinPoint.getSignature().getName());
        System.out.println("joinPoint.getArgs() = " + joinPoint.getArgs().toString());
        System.out.println("joinPoint.getSourceLocation() = " + joinPoint.getSourceLocation());
        System.out.println("joinPoint.getTarget().getClass() = " + joinPoint.getTarget().getClass());
        try {
            object = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("=======================================================");
        System.out.println("joinPoint.getStaticPart().getSourceLocation() = " + joinPoint.getStaticPart().getSourceLocation());
        System.out.println("joinPoint.getStaticPart().getSignature().getName() = " + joinPoint.getStaticPart().getSignature().getName());
        System.out.println("joinPoint.getStaticPart().getKind() = " + joinPoint.getStaticPart().getKind());
        System.out.println("joinPoint.getStaticPart().getId() = " + joinPoint.getStaticPart().getId());
        return object;
    }
}
