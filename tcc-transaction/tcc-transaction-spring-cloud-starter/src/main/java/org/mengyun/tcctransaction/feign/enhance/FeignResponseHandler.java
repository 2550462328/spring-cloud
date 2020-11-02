package org.mengyun.tcctransaction.feign.enhance;

/**
 * @author xiaobzhou
 * date 2019-03-11 22:06
 */
public interface FeignResponseHandler {

    String HANDLE_METHOD_NAME = "handle";

    /**
     * 处理feignClient返回结果
     *
     * @param feignRes feignClient返回结果
     */
    void handle(Object feignRes);
}