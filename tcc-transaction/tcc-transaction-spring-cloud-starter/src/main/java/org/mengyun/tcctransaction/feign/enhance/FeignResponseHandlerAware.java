package org.mengyun.tcctransaction.feign.enhance;

/**
 * @author xiaobzhou
 * date 2019-03-11 22:43
 */
public interface FeignResponseHandlerAware {

    void setFeignResponseHandler(FeignResponseHandler feignResponseHandler);
}