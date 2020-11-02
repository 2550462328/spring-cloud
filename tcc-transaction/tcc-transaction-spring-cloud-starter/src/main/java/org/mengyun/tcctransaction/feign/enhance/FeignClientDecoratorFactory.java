package org.mengyun.tcctransaction.feign.enhance;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;

/**
 * @author xiaobzhou
 * date 2019-03-07 23:30
 */
public class FeignClientDecoratorFactory {

    private static final Logger LOG = LoggerFactory.getLogger(FeignClientDecoratorFactory.class);

    public static Object getDecorator(Object bean, String beanName, ApplicationContext ctx) {
        Object wrapBean = bean;
        try {
            // @FeignClient标注的接口被注入spring容器时,名称为接口全类名
            Class classType = ClassUtils.forName(beanName, null);
            Annotation ann = classType.getAnnotation(FeignClient.class);
            if (null != ann) {
                wrapBean = createDecorator(bean, classType, ctx);
                LOG.info("FeignClient proxy bean class[" + beanName + "] was wrapped as class[" + wrapBean.getClass() + "]");
            }
        } catch (ClassNotFoundException ignored) {
            // do noting 忽略bean名称不是全类名的bean,报出的ClassNotFoundException
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wrapBean;
    }

    private static Object createDecorator(Object bean, Class classType, ApplicationContext ctx)
            throws NotFoundException, CannotCompileException {
        Class decoratorClassType = FeignClientDecoratorClassGenerator.generate(classType);
        Object wrapBean = BeanUtils.instantiateClass(decoratorClassType);
        ((FeignClientProxyAware) wrapBean).setFeignClientProxy(bean);
        FeignResponseHandler feignResponseHandler = ctx.getBean(FeignResponseHandler.class);
        ((FeignResponseHandlerAware) wrapBean).setFeignResponseHandler(feignResponseHandler);
        return wrapBean;
    }
}