package org.mengyun.tcctransaction.feign.enhance;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;

/**
 * @author xiaobzhou
 * date 2019-03-07 22:47
 */
public class FeignClientProxyDecoratePostProcessor implements BeanPostProcessor, Ordered {

    public static final int DEFAULT_ORDER = 500;

    private ApplicationContext ctx;

    public FeignClientProxyDecoratePostProcessor(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return FeignClientDecoratorFactory.getDecorator(bean, beanName, ctx);
    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }
}