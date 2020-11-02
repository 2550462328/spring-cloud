package org.mengyun.tcctransaction.feign.enhance;

/**
 * @author xiaobzhou
 * date 2019-03-08 10:05
 */
public interface FeignClientProxyAware {

    void setFeignClientProxy(Object feignClientProxy);
}